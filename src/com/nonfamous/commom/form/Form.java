package com.nonfamous.commom.form;

import java.util.ArrayList;

/**
 * @author:alan
 * 
 * <pre>
 * Form接口
 * </pre>
 * 
 * @version $Id: Form.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public interface Form {

	public Form colne();

	/**
	 * 本form是否校验通过
	 * 
	 * @return
	 */
	public boolean isValide();

	/**
	 * 获取form字段
	 * 
	 * @param fieldName
	 * @return
	 */
	public Field getField(String fieldName);

	/**
	 * 设置form字段
	 * 
	 * @param field
	 */
	public void setField(Field field);

	/**
	 * 设置form的字段名
	 * 
	 * @param key
	 */
	public void setKey(String key);

	/**
	 * 设置所有form字段
	 * 
	 * @param keys
	 */
	public void setKeys(ArrayList<String> keys);

	/**
	 * 获取所有的form字段名
	 * 
	 * @return
	 */
	public ArrayList getAllKeys();

	/**
	 * 获取form名称
	 * 
	 * @return
	 */
	public String getFormName();

	/**
	 * 设置fromName
	 * 
	 * @param formName
	 */
	public void setFormName(String formName);
}
