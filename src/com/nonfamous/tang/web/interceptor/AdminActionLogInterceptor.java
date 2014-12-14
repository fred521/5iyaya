package com.nonfamous.tang.web.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.web.util.WebUtils;

import com.nonfamous.commom.util.web.RequestValueParse;
import com.nonfamous.tang.domain.AdminLog;
import com.nonfamous.tang.service.admin.AdminLogService;
import com.nonfamous.tang.web.common.Constants;

public class AdminActionLogInterceptor extends HandlerInterceptorAdapter {
	private final Log logger = LogFactory
			.getLog(AdminActionLogInterceptor.class);

	private static final String Separator = ",";

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	private Set<String> logActions = new HashSet<String>();

	private AdminLogService adminLogService;

	/**
	 * 设置需要拦截的方法，以','作为分隔符
	 * 
	 * @param s
	 */
	public void setLogActions(String actions) {
		if (actions == null) {
			throw new NullPointerException("actions can't be null");
		}
		String[] actionsArray = actions.split(Separator);
		for (String actionName : actionsArray) {
			logActions.add(actionName.trim());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("log actions:" + this.logActions);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String path = urlPathHelper.getLookupPathForRequest(request);
		String actionName = WebUtils.extractFilenameFromUrlPath(path);
		if (this.logActions.contains(actionName)) {
			AdminLog log = this.getAdminLog(request, actionName);
			this.adminLogService.createLog(log);
			if (logger.isDebugEnabled()) {
				logger.debug("log a action[" + actionName + "]");
			}
		}
	}

	private AdminLog getAdminLog(HttpServletRequest request, String action) {
		RequestValueParse parse = new RequestValueParse(request);
		AdminLog log = new AdminLog();
		log.setAction(action);
		log.setActionIp(request.getRemoteAddr());
		log.setAdminUserId(Long.parseLong(parse.getCookyjar().get(
				Constants.AdminUserId_Cookie)));
		log.setParameter(parse.getParametersWellFormat());
		return log;
	}

	public AdminLogService getAdminLogService() {
		return adminLogService;
	}

	public void setAdminLogService(AdminLogService adminLogSerivce) {
		this.adminLogService = adminLogSerivce;
	}
}
