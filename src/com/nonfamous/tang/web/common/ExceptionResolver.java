package com.nonfamous.tang.web.common;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.nonfamous.tang.exception.ServiceException;

public class ExceptionResolver implements HandlerExceptionResolver {

	private static final Log logger = LogFactory
			.getLog(ExceptionResolver.class);

	private String errorView = "error";// 缺省的错误处理页面

	private String adminPath = "/admin/";// admin模块的前缀

	private String adminErrorView = "admin/error";// admin模块错误页面

	public String getAdminErrorView() {
		return adminErrorView;
	}

	public void setAdminErrorView(String adminErrorView) {
		this.adminErrorView = adminErrorView;
	}

	public String getAdminPath() {
		return adminPath;
	}

	public void setAdminPath(String adminPath) {
		this.adminPath = adminPath;
		if (!this.adminPath.startsWith("/")) {
			this.adminPath = "/" + this.adminPath;
		}
		if (this.adminPath.charAt(this.adminPath.length() - 1) != '/') {
			this.adminPath = this.adminPath + '/';
		}
	}

	public String getErrorView() {
		return errorView;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String errorString = null;
		if (logger.isDebugEnabled()) {
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			PrintWriter pw = new PrintWriter(bytes);
			ex.printStackTrace(pw);
			pw.close();
			errorString = bytes.toString();
			logger.debug(ex.getMessage());
		} else {
			errorString = ex.getMessage();
		}
		if (ex instanceof ServiceException) {
			request.setAttribute("error_message", ex.getMessage());
		}
		String pathInfo = request.getRequestURI();
		if (pathInfo != null && pathInfo.startsWith(adminPath)) {// admin模块异常处理
			return new ModelAndView(this.adminErrorView, "error", errorString);
		}
		return new ModelAndView(this.errorView, "error", errorString);
	}

}
