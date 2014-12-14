package com.nonfamous.commom.util.html.filter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 
 * @author fish 
 * ����update��insert,remove�����ù��ķ�������Σ� 
 * �����ù���java�������string�������Ե�html����
 * 
 */
public class BeanHtmlFilterInterceptor implements MethodInterceptor {

	private String[] methodPrefix;

	private TextPropertiesFilter textPropertiesFilter;

	public String[] getMethodPrefix() {
		return methodPrefix;
	}

	public void setMethodPrefix(String[] methodPrefix) {
		this.methodPrefix = methodPrefix;
	}

	public TextPropertiesFilter getTextPropertiesFilter() {
		return textPropertiesFilter;
	}

	public void setTextPropertiesFilter(
			TextPropertiesFilter textPropertiesFilter) {
		this.textPropertiesFilter = textPropertiesFilter;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName();
		if (needInterceptor(methodName)) {
			Object[] args = invocation.getArguments();
			if (args != null && args.length > 0) {
				for (Object arg : args) {
					if (arg != null) {
						textPropertiesFilter.filterProperties(arg);
					}
				}
			}
		}
		return invocation.proceed();
	}

	private boolean needInterceptor(String methodName) {
		for (String prefix : this.methodPrefix) {
			if (methodName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

}
