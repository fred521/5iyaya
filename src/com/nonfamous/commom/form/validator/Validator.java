package com.nonfamous.commom.form.validator;


/**
 * @author:alan 
 * <pre>
 * У��ӿ���
 * ���Ҫ����һ��У����ʵ�ָýӿڣ�fromFactory�н��Զ�Ϊʵ���������������ֵ�����ʵ���������ԣ�����ͬУ����������ͬһ��
 * xml����Ŀ��
 * �磺<length-validator min="10" max="100">
 * ������Ҫ��FormFactory��createValidator������һ����������
 * </pre>
 * @version $Id: Validator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public interface Validator {
	public boolean validate(String value);
}
