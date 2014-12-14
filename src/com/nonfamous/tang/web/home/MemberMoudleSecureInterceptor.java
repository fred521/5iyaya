package com.nonfamous.tang.web.home;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.web.admin.AdminMoudleSecureInterceptor;
import com.nonfamous.tang.web.common.Constants;

public class MemberMoudleSecureInterceptor extends HandlerInterceptorAdapter {
	private static final Log logger = LogFactory
			.getLog(AdminMoudleSecureInterceptor.class);

	private String loginPage;

	private String encoding;

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, Object handler)
			throws Exception {
		Cookyjar c = (Cookyjar) request
				.getAttribute(Cookyjar.CookyjarInRequest);
		String memberId = c.get(Constants.MemberId_Cookie);
		String gotoUrl = request.getRequestURL().toString();
		String query = getPostParms(request);

		if (query != null) { // 带上登陆后要跳转的页面
			gotoUrl = gotoUrl + "?" + query;
		}
		gotoUrl = URLEncoder.encode(gotoUrl, encoding);
		if (StringUtils.isBlank(memberId)) {
			if (logger.isDebugEnabled()) {
				logger.debug("URL[" + gotoUrl + "] need login user");
			}
			// response.sendRedirect(loginPage);
			request.setAttribute("gotoUrl", gotoUrl);
			response.sendRedirect(request.getContextPath() + loginPage
					+ "?gotoUrl=" + gotoUrl);
			return false;
		}
		return true;
	}

	private String getPostParms(HttpServletRequest request) throws UnsupportedEncodingException {
		String query = null;
		StringBuffer sb = new StringBuffer();

		for (Iterator iter = request.getParameterMap().keySet().iterator(); iter
				.hasNext();) {
			String parmName = (String) iter.next();
			String parmValue = (String) request.getParameter(parmName);

			sb.append(parmName + "=" + URLEncoder.encode(parmValue, encoding) + "&");
		}

		if (sb.length() > 0) {
			int len = sb.length();
			query = sb.substring(0, len - 1); // remove the last &
		}

		return query;
	}
}
