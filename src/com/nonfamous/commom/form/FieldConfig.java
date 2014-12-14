package com.nonfamous.commom.form;

import com.nonfamous.commom.form.validator.Validator;



/**
 * @author:alan
 * 
 * <pre>
 * ×Ö¶ÎµÄĞ£ÑéÅäÖÃ
 * </pre>
 * 
 * @version $Id: FieldConfig.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public class FieldConfig {

	private Validator validator;

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
