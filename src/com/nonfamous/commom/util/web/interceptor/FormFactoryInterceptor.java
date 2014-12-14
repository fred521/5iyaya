package com.nonfamous.commom.util.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nonfamous.commom.form.FormFactory;

/**
 * @author:alan
 * 
 * <pre>
 * FormFactoryInterceptor,�����ҪУ�飬����Ҫ������һ��org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping��
 * Ȼ������ٶ�Ӧ��action��ʹ�� request.getAttribute(FormFactory.FORM_FACTORY)�õ�FormFactory�������
 * ����Ҫ��ÿ��action��ע��FormFactory���bean
 * </pre>
 * 
 * @version $Id: FormFactoryInterceptor.java,v 1.1 2008/07/11 00:47:12 fred Exp $
 */
public class FormFactoryInterceptor extends HandlerInterceptorAdapter {

	private FormFactory formFactory;

	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute(FormFactory.FORM_FACTORY, formFactory);
		return true;
	}

	public void setFormFactory(FormFactory formFactory) {
		this.formFactory = formFactory;
	}

	public FormFactory getFormFactory() {
		return formFactory;
	}
	
	
}
