package com.nonfamous.tang.web.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.service.MemberAuthToken;

/**
 * 会员权限的拦截器,必须配置在cookiejar拦截器后面
 * 
 * @author fish
 * @version $Id: MemberAuthTokenInterceptor.java,v 1.2 2007/06/23 15:28:58
 *          daodao Exp $
 * 
 */
public class MemberAuthTokenInterceptor extends HandlerInterceptorAdapter {

	private static final Log logger = LogFactory
			.getLog(MemberAuthTokenInterceptor.class);

	private Map<String, String[]> urlPermissionMap;

	private String splitSymbol = " ";// 配置时权限的分隔符

	private String noPermissionPage;// 无权限时跳转的页面

	public void setUrlPermissionMap(Map<String, String> orignalMap) {
		this.urlPermissionMap = new HashMap<String, String[]>();
		for (Entry<String, String> entry : orignalMap.entrySet()) {
			String[] permissions = entry.getValue().split(splitSymbol);
			List<String> perList = new ArrayList<String>();
			for (String per : permissions) {
				if (StringUtils.isNotBlank(per)) {
					perList.add(per.trim());
				}
			}
			this.urlPermissionMap.put(entry.getKey(), (String[]) perList
					.toArray(new String[] {}));
		}
		if (logger.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer("urlPermissionMap[");
			for (Entry<String, String[]> entry : this.urlPermissionMap
					.entrySet()) {
				sb.append('(').append(entry.getKey()).append(':').append(
						Arrays.toString(entry.getValue())).append(')');
			}
			sb.append(']');
			logger.debug(sb);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Cookyjar ck = (Cookyjar) request
				.getAttribute(Cookyjar.CookyjarInRequest);
		if (ck == null) {
			throw new IllegalStateException("not find cookyjar in request");
		}
		String path = request.getRequestURI();
		MemberAuthToken authToken = new MemberAuthToken(ck, urlPermissionMap);
		request.setAttribute(MemberAuthToken.MemberAuthTokenInRequest,
				authToken);
		boolean have = authToken.havePermission(path);

		if (!have) {
			String page = getNoPermissionPage(request);
			if (logger.isDebugEnabled()) {
				logger.debug("no permission so redirect to:[" + page);
			}
			response.sendRedirect(page);
		}
		return have;
	}

	private String getNoPermissionPage(HttpServletRequest request) {
		String path = request.getContextPath();
		if (StringUtils.isNotBlank(path)) {
			return this.noPermissionPage;
		}
		if (!this.noPermissionPage.startsWith("/")) {
			this.noPermissionPage = "/" + this.noPermissionPage;
		}
		return path + this.noPermissionPage;
	}

	public String getSplitSymbol() {
		return splitSymbol;
	}

	public void setSplitSymbol(String splitSymbol) {
		this.splitSymbol = splitSymbol;
	}

	public void setNoPermissionPage(String noPermissionPage) {
		this.noPermissionPage = noPermissionPage;

	}

}
