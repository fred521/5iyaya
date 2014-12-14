package com.nonfamous.commom.util.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.nonfamous.commom.form.FormFactory;

/**
 * @author:alan
 * 
 * <pre>
 * FormFactoryInterceptor,如果需要校验，则需要再配置一个org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping，
 * 然后可以再对应的action中使用 request.getAttribute(FormFactory.FORM_FACTORY)拿到FormFactory这个对象
 * 不需要再每个action中注入FormFactory这个bean
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
