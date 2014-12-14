package com.nonfamous.tang.service.ucenter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.nonfamous.tang.domain.result.LoginResult;
import com.nonfamous.tang.domain.result.NewMemberResult;
import com.nonfamous.tang.domain.result.UpdateMemberInfoResult;
import com.nonfamous.tang.service.home.MemberService;
import com.nonfamous.tang.service.ucenter.util.SecurityTool;

/**
 * @author frank
 * 
 */
public class UCenterServiceImpl implements UCenterService {

	private static final String UCENTER_URL = "http://www.5iyya.com/ucenter";
	private static final String USER_ANGET = "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)";

	private final Log logger = LogFactory.getLog(this.getClass());

	/* (non-Javadoc)
	 * @see com.nonfamous.tang.service.ucenter.UCenterService#userRegister(java.lang.String, java.lang.String, java.lang.String)
	 */
	public int userRegister(String username, String password, String email)
			throws UCenterException {
		if (logger.isDebugEnabled()) {
			logger.debug("userRegister invoked");
		}
		String source = "username=" + username + "&password=" + password
				+ "&email=" + email;
		String response = doGet("user", "register", source);
		if (logger.isDebugEnabled()) {
			logger.debug("parameters: " + source);
			logger.debug("response: " + response);
		}
		int res = Integer.valueOf(response);
		if (res <= 0) {
			String errMsg = "";
			switch (res) {
			case -1: {
				errMsg = "用户名不合法";
				break;
			}
			case -2: {
				errMsg = "包含要允许注册的词语";
				break;
			}
			case -3: {
				errMsg = "用户名已经存在";
				break;
			}
			case -4: {
				errMsg = "Email [" + email + "]格式有误";
				break;
			}
			case -5: {
				errMsg = "Email [" + email + "]不允许注册";
				break;
			}
			case -6: {
				errMsg = "Email [" + email + "]已经被注册";
				break;
			}
			default:{
				errMsg = "未定义";
				break;
			}
			}
			if(logger.isInfoEnabled()){
				logger.info("User [" + username + "] register in UCenter failed with error code [" + res + "].");
				logger.info(errMsg);
			}
			throw new UCenterException(errMsg, res);
		}
		else{ //用户注册成功
			if(logger.isInfoEnabled()){
				logger.info("User [" + username + "] register in UCenter successfully.");
			}
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see com.nonfamous.tang.service.ucenter.UCenterService#userSynlogin(java.lang.String)
	 */
	public String userSynlogin(String uid) throws UCenterException {
		if (logger.isDebugEnabled()) {
			logger.debug("userSynlogin invoked.");
		}
		String source = "uid=" + uid;
		String response = doGet("user", "synlogin", source);
		if (logger.isDebugEnabled()) {
			logger.debug("parameters: " + source);
			logger.debug("response: " + response);
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.nonfamous.tang.service.ucenter.UCenterService#userSynlogout(java.lang.String)
	 */
	public String userSynlogout(String uid) throws UCenterException {
		if (logger.isDebugEnabled()) {
			logger.debug("userSynlogout invoked.");
		}
		String source = "uid=" + uid;
		String response = doGet("user", "synlogout", source);
		if (logger.isDebugEnabled()) {
			logger.debug("parameters: " + source);
			logger.debug("response: " + response);
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.nonfamous.tang.service.ucenter.UCenterService#userLogin(java.lang.String, java.lang.String)
	 */
	public Map<String, String> userLogin(String username, String password)
			throws UCenterException {
		if (logger.isDebugEnabled()) {
			logger.debug("userLogin invoked.");
		}
		String source = "username=" + username + "&password=" + password;
		String response = doGet("user", "login", source);
		if (logger.isDebugEnabled()) {
			logger.debug("parameters: " + source);
			logger.debug("response: " + response);
		}
		List<Element> userinfo = this.loadXml(response);
		Map<String, String> user = new HashMap<String, String>();
		if (userinfo != null && userinfo.size() >= 3) {
			user.put("uid", userinfo.get(0).getStringValue());
			user.put("username", userinfo.get(1).getStringValue());
			user.put("password", userinfo.get(2).getStringValue());
			user.put("email", userinfo.get(3).getStringValue());
			user.put("isDuplicated", userinfo.get(4).getStringValue());
		}
		return user;
	}

	/**
	 * 用户是否存在
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> userExists(String username) throws UCenterException {
		if (logger.isDebugEnabled()) {
			logger.debug("userExists invoked.");
		}
		String source = "username=" + username;
		String response = doGet("user", "get_user", source);
		if (logger.isDebugEnabled()) {
			logger.debug("parameters: " + source);
			logger.debug("response: " + response);
		}
		List<Element> userinfo = this.loadXml(response);
		Map<String, String> user = new HashMap<String, String>();
		if(userinfo != null){
			user.put("uid", userinfo.get(0).getStringValue());
			user.put("username", userinfo.get(1).getStringValue());
			user.put("email", userinfo.get(2).getStringValue());
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see com.nonfamous.tang.service.ucenter.UCenterService#userUpdate(java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public int userUpdate(String username, String oldPwd, String newPwd,
			String email, boolean ignoreOldPwd) throws UCenterException{
		if(logger.isDebugEnabled()){
			logger.debug("userUpdate invoked.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("username=").append(username)
			.append("&oldpw=").append(oldPwd)
			.append("&newpw=").append(newPwd)
			.append("&email=").append(email)
			.append("&ignoreoldpw=").append(ignoreOldPwd ? 1 : 0);
		String source = sb.toString();
		String response = doGet("user", "edit", source);
		if (logger.isDebugEnabled()) {
			logger.debug("parameters: " + source);
			logger.debug("response: " + response);
		}
		int res = Integer.valueOf(response);
		if (res < 0 && res != -7) {
			String errMsg = "";
			switch (res) {
			case -1: {
				errMsg = "旧密码不正确";
				break;
			}
			case -4: {
				errMsg = "Email [" + email + "]格式有误";
				break;
			}
			case -5: {
				errMsg = "Email [" + email + "]不允许注册";
				break;
			}
			case -6: {
				errMsg = "Email [" + email + "]已经被注册";
				break;
			}
			case -8:{
				errMsg = "该用户受保护无权限更改";
				break;
			}
			default:{
				errMsg = "未定义";
				break;
			}
			}
			if(logger.isInfoEnabled()){
				logger.info("User [" + username + "] update in UCenter failed with error code [" + res + "].");
				logger.info(errMsg);
			}
			throw new UCenterException(errMsg, res);
		}
		else{ //用户注册成功
			if(logger.isInfoEnabled()){
				logger.info("User [" + username + "] update in UCenter successfully.");
			}
		}
		return res;
	}
	
	private HttpClient getClient() {
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost(ucenterUrl);
		return client;
	}

	private GetMethod getGetMethod(String url) {
		GetMethod post = new GetMethod(url);
		post.setRequestHeader("User-Agent", userAgent);
		return post;
	}

	/**
	 * 通过httpclient模拟提交请求
	 * 
	 * @param model
	 *            uc模块
	 * @param action
	 *            操作action
	 * @param source
	 *            加密数据
	 * @return
	 * @throws Exception
	 */
	private String doGet(String model, String action, String source)
			throws UCenterException {
		if (logger.isDebugEnabled()) {
			logger.debug("doGet invoked.");
		}
		HttpClient client = getClient();
		GetMethod post = null;
		/*
		 * NameValuePair m = new NameValuePair("m", model); NameValuePair a =
		 * new NameValuePair("a", action); NameValuePair inajax = new
		 * NameValuePair("inajax", "2"); NameValuePair appid = new
		 * NameValuePair("appid", APPID); NameValuePair input = new
		 * NameValuePair("input", getInput(source)); post.setParams(new
		 * NameValuePair[] { m, a, inajax, appid, input });
		 */
		String response = null;
		try {
			post = getGetMethod(ucenterUrl + "?m=" + model + "&a="
					+ action + "&inajax=2&appid=" + appID + "&input="
					+ URLEncoder.encode(getInput(source), "utf8"));
			client.executeMethod(post);
			response = new String(post.getResponseBodyAsString()
					.getBytes("GBK"));
		} catch (HttpException e) {
			logger.error("HttpException: " + e.getMessage());
			throw new UCenterException("UCenter Connection Exception.");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException" + e.getMessage());
			throw new UCenterException("UCenter Connection Exception.");
		} catch (IOException e) {
			logger.error("IOException" + e.getMessage());
			throw new UCenterException("UCenter Connection Exception.");
		} finally {
			post.releaseConnection();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("doGet invoked finished.");
		}
		return response;
	}

	/**
	 * 从配置文件中返回采集器节点
	 * 
	 * @param file
	 * @return
	 */
	private List<Element> loadXml(String xmlStr) {
		SAXReader reader = new SAXReader();
		Document document = null;

		try {
			document = DocumentHelper.parseText(xmlStr);

		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}

		Element root = document.getRootElement();
		return root.elements();

	}

	private String getInput(String source) {
		source += this.getAgentAndTime();
		return Authcode.encode(source, key);
	}

	/**
	 * 设置anget和time
	 * 
	 * @return
	 */
	private String getAgentAndTime() {
		return "&agent=" + SecurityTool.getMD5Code(userAgent).toLowerCase()
				+ "&time=" + String.valueOf(System.currentTimeMillis() / 1000);
	}

	// --------------------------------------------------ucenter parameters
	private String ucenterUrl = UCENTER_URL;
	private String userAgent = USER_ANGET;
	private String appID;
	private String key;

	public String getUcenterUrl() {
		return ucenterUrl;
	}

	public void setUcenterUrl(String ucenterUrl) {
		this.ucenterUrl = ucenterUrl;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
