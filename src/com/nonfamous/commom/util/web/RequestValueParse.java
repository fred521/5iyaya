package com.nonfamous.commom.util.web;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.nonfamous.commom.util.web.cookyjar.Cookyjar;

/**
 * @author eyeieye һ�������࣬�������request�����getParameter,getAttribute ����
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
	 * ��request.getParameter��ȡ����
	 * 
	 * @param key
	 *            ����Ϊnull
	 * @return RequestValue ��Զ����Ϊnull
	 */
	public RequestValue getParameter(String key) {
		asserKeyNotNull(key);
		return new RequestValue(this.request.getParameter(key));
	}

	/**
	 * ��request.getAttribute��ȡ����, ����Ǹ�object�������õ�obj.toString() ���string��
	 * 
	 * @param key
	 *            ����Ϊnull
	 * @return RequestValue ��Զ����Ϊnull
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
	 * �õ��������ȴ�request.getParameterȡ�����û�У��ٵ�request.getAttribute ȡ
	 * 
	 * @param key
	 *            ����Ϊnull
	 * @return RequestValue ��Զ����Ϊnull
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
