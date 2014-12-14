package com.nonfamous.commom.util.web;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.nonfamous.commom.util.web.cookyjar.Cookyjar;

/**
 * @author eyeieye 一个辅助类，方便解析request对象的getParameter,getAttribute 方法
 */
public class RequestValueParse {
	private HttpServletRequest request;

	public RequestValueParse(HttpServletRequest request) {
		super();
		this.request = request;
	}

	public Cookyjar getCookyjar() {
		return (Cookyjar) request.getAttribute(Cookyjar.CookyjarInRequest);
	}

	/**
	 * 从request.getParameter中取参数
	 * 
	 * @param key
	 *            不能为null
	 * @return RequestValue 永远不会为null
	 */
	public RequestValue getParameter(String key) {
		asserKeyNotNull(key);
		return new RequestValue(this.request.getParameter(key));
	}

	/**
	 * 从request.getAttribute中取参数, 如果是个object对象，则会得到obj.toString() 后的string串
	 * 
	 * @param key
	 *            不能为null
	 * @return RequestValue 永远不会为null
	 */
	public RequestValue getAttribute(String key) {
		asserKeyNotNull(key);
		Object obj = this.request.getAttribute(key);
		if (obj == null) {
			return new RequestValue(null);
		}
		return new RequestValue(obj.toString());
	}

	/**
	 * 得到参数，先从request.getParameter取，如果没有，再到request.getAttribute 取
	 * 
	 * @param key
	 *            不能为null
	 * @return RequestValue 永远不会为null
	 */
	public RequestValue getParameterOrAttribute(String key) {
		asserKeyNotNull(key);
		RequestValue rv = this.getParameter(key);
		if (rv.isNotNull()) {
			return rv;
		}
		return this.getAttribute(key);
	}

	private void asserKeyNotNull(String key) {
		if (key == null) {
			throw new NullPointerException("key can't be null.");
		}
	}

	public String getParametersWellFormat() {
		StringBuilder sb = new StringBuilder();
		Enumeration enumer = request.getParameterNames();
		while (enumer.hasMoreElements()) {
			String name = enumer.nextElement().toString();
			String[] values = request.getParameterValues(name);
			sb.append('{').append(name).append('=');
			sb.append(Arrays.asList(values)).append('}');
		}
		return sb.toString();
	}
}
