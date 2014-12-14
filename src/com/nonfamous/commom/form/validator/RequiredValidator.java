package com.nonfamous.commom.form.validator;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author:alan 
 * <pre>
 * ������������У�飬Ϊ����У��ʧ��
 * </pre>
 * @version $Id: RequiredValidator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public class RequiredValidator implements Validator {

	public boolean validate(String value) {
		return StringUtils.isNotBlank(value);
	}

}
