package com.nonfamous.commom.util.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author eyeieye
 * 
 */
public class ResponseEncoding extends HandlerInterceptorAdapter {

	private String encoding = "UTF-8";

	private String contentType = "text/html;charset=" + encoding;

	private String pageEncodingVarName = "page_encoding";// 在页面中编码方式的变量名,缺省是page_encoding

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, Object handler)
			throws Exception {
		response.setCharacterEncoding(encoding);
		response.setContentType(contentType);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object obj, ModelAndView mv)
			throws Exception {
		if (mv != null) {
			mv.addObject(pageEncodingVarName, encoding);
		}
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
		contentType = "text/html;charset=" + encoding;
	}

	public String getPageEncodingVarName() {
		return pageEncodingVarName;
	}

	public void setPageEncodingVarName(String pageEncodingVarName) {
		this.pageEncodingVarName = pageEncodingVarName;
	}
}
