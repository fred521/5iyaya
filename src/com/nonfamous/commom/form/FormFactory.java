package com.nonfamous.commom.form;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:fred
 * 
 * <pre>
 * formFactory接口
 * </pre>
 * 
 * @version $Id: FormFactory.java,v 1.2 2008/11/29 02:51:49 fred Exp $
 */
public interface FormFactory {
	/**
	 * 获取校验Form
	 * 
	 * @param formName
	 * @param request
	 * @return
	 */
	public Form getForm(String formName, HttpServletRequest request);

	/**
	 * 将from对象中的内容copy到to的对象中 注意，这里的from可以是Form对象，也可以是do对象，通用。对to也是类似
	 * 
	 * @param from
	 * @param to
	 */
	public void formCopy(Object from, Object to);

	public static final String FORM_FACTORY = "FORM_FACTORY";
}