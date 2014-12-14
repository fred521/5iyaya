package com.nonfamous.commom.util.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author eyeieye
 * 
 */
public class RequestEncoding extends HandlerInterceptorAdapter {

	private String encoding = "UTF-8";

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, Object handler)
			throws Exception {
		request.setCharacterEncoding(encoding);
		return true;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
