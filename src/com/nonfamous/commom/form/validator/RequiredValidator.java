package com.nonfamous.commom.form.validator;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author:alan 
 * <pre>
 * 必须输入条件校验，为空则校验失败
 * </pre>
 * @version $Id: RequiredValidator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public class RequiredValidator implements Validator {

	public boolean validate(String value) {
		return StringUtils.isNotBlank(value);
	}

}
