package com.nonfamous.commom.form;

import java.util.ArrayList;

/**
 * @author:alan
 * 
 * <pre>
 * Form�ӿ�
 * </pre>
 * 
 * @version $Id: Form.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public interface Form {

	public Form colne();

	/**
	 * ��form�Ƿ�У��ͨ��
	 * 
	 * @return
	 */
	public boolean isValide();

	/**
	 * ��ȡform�ֶ�
	 * 
	 * @param fieldName
	 * @return
	 */
	public Field getField(String fieldName);

	/**
	 * ����form�ֶ�
	 * 
	 * @param field
	 */
	public void setField(Field field);

	/**
	 * ����form���ֶ���
	 * 
	 * @param key
	 */
	public void setKey(String key);

	/**
	 * ��������form�ֶ�
	 * 
	 * @param keys
	 */
	public void setKeys(ArrayList<String> keys);

	/**
	 * ��ȡ���е�form�ֶ���
	 * 
	 * @return
	 */
	public ArrayList getAllKeys();

	/**
	 * ��ȡform����
	 * 
	 * @return
	 */
	public String getFormName();

	/**
	 * ����fromName
	 * 
	 * @param formName
	 */
	public void setFormName(String formName);
}
