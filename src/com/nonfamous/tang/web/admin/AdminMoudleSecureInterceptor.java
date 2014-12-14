package com.nonfamous.tang.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nonfamous.commom.util.StringUtils;
import com.nonfamous.commom.util.web.cookyjar.Cookyjar;
import com.nonfamous.tang.web.common.Constants;

public class AdminMoudleSecureInterceptor extends HandlerInterceptorAdapter {
	private static final Log logger = LogFactory
			.getLog(AdminMoudleSecureInterceptor.class);

	private String loginPage;

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
		String adminUserId = c.get(Constants.AdminUserId_Cookie);
		if (StringUtils.isBlank(adminUserId)) {
			if (logger.isDebugEnabled()) {
				logger.debug("URL[" + request.getRequestURL()
						+ "] need login user");
			}
			//response.sendRedirect(loginPage);
			response.sendRedirect(request.getContextPath() + loginPage);
			return false;
		}
		return true;
	}
}
