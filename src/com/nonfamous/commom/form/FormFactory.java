package com.nonfamous.commom.form;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:fred
 * 
 * <pre>
 * formFactory�ӿ�
 * </pre>
 * 
 * @version $Id: FormFactory.java,v 1.2 2008/11/29 02:51:49 fred Exp $
 */
public interface FormFactory {
	/**
	 * ��ȡУ��Form
	 * 
	 * @param formName
	 * @param request
	 * @return
	 */
	public Form getForm(String formName, HttpServletRequest request);

	/**
	 * ��from�����е�����copy��to�Ķ����� ע�⣬�����from������Form����Ҳ������do����ͨ�á���toҲ������
	 * 
	 * @param from
	 * @param to
	 */
	public void formCopy(Object from, Object to);

	public static final String FORM_FACTORY = "FORM_FACTORY";
}