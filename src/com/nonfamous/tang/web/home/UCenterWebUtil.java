package com.nonfamous.tang.web.home;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.nonfamous.tang.service.ucenter.UCenterException;
import com.nonfamous.tang.service.ucenter.UCenterService;

public class UCenterWebUtil {
	
	private UCenterWebUtil(UCenterService ucenterService){
		this.ucenterService = ucenterService;
	}
	
	private UCenterService ucenterService;
	
	private static UCenterWebUtil instance;
	
	public synchronized static UCenterWebUtil getInstance(UCenterService ucenterService){
		if(instance == null)
			instance = new UCenterWebUtil(ucenterService);
		return instance;
	}
	
	protected final Log logger = LogFactory.getLog(UCenterWebUtil.class);
	
	/**
	 * 单点登录
	 * 
	 * 判断用户是否已经在ucenter中注册过, 如果没有，则在ucenter注册该帐号
	 * 
	 * @param mv
	 * @param loginId
	 * @param password
	 * @param email
	 * @throws UCenterException
	 */
	protected void registerOrLoginUCenter(ModelAndView mv, String loginId,
			String password, String email) {
		try {
			String uid = "";
			// 判断用户是否已经在ucenter中注册过
			Map<String, String> user = ucenterService.userExists(loginId);
			if (user.size() <= 0) {// 用户没有在ucenter中注册过了
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] is not register in ucenter.");
				}
				int res = ucenterService.userRegister(loginId, password, email);
				if (res <= 0) {
					logger.error("User [" + user.get("username")
							+ "] register in UCenter failed.");
					return;
				}
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] register successfully in ucenter.");
				}
				uid = String.valueOf(res);
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] exists in ucenter.");
				}
				uid = user.get("uid");
			}

			// get syn login code from ucenter and print in page(it is a
			// fragment of Javascript)
			loginUCenter(mv, loginId);
		} catch (UCenterException e) {
			logger.error("Cannot register or login in ucenter due to: \n"
					+ "error code: " + e.getCode() + "\n" + "error message: "
					+ e.getMessage());
		}
	}

	/**
	 * 单点登录
	 * 
	 * @param mv
	 * @param loginId
	 */
	protected void loginUCenter(ModelAndView mv, String loginId) {
		try {
			// 判断用户是否已经在ucenter中注册过
			String uid = "";
			Map<String, String> user = ucenterService.userExists(loginId);
			if (user.size() <= 0) {// 用户没有在ucenter中注册过了
				if (logger.isInfoEnabled()) {
					logger.info("user [" + user.get("username")
							+ "] not exists in ucenter.");
				}
			} else {
				uid = user.get("uid");
				// get syn login code from ucenter and print in page(it is a
				// fragment of Javascript)
				String synLoginCode = ucenterService.userSynlogin(uid);
				if (logger.isInfoEnabled()) {
					logger.info("sync login code: " + synLoginCode);
				}
				mv.addObject("synLoginCode", synLoginCode);
			}
		} catch (UCenterException e) {
			logger.error("Cannot login in ucenter due to: \n" + "error code: "
					+ e.getCode() + "\n" + "error message: " + e.getMessage());
		}
	}

	/**
	 * 单点登出
	 * 
	 * @param mv
	 * @param loginId
	 */
	protected void logoutUCenter(ModelAndView mv, String loginId) {
		try {
			// 判断用户是否已经在ucenter中注册过
			Map<String, String> user = ucenterService.userExists(loginId);
			if (user.size() > 0) { // 用户已经在ucenter中注册过了
				String synLogoutCode = ucenterService.userSynlogout(user
						.get("uid"));
				mv.addObject("synLogoutCode", synLogoutCode);
			}
		} catch (UCenterException e) {
			logger.error("Cannot logout in ucenter due to: \n" + "error code: "
					+ e.getCode() + "\n" + "error message: " + e.getMessage());
		}
	}
}
