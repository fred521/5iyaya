package com.nonfamous.commom.form;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author:alan
 * 
 * <pre>
 * Form字段对象
 * </pre>
 * 
 * @version $Id: Field.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public class Field {
	private String name;

	@SuppressWarnings("unchecked")
	private ArrayList<FieldConfig> fieldConfigs = new ArrayList();

	private boolean isValid = true;

	private String message;

	private String value;

	public Field clone() {
		Field f = new Field();
		f.setFieldConfigs(this.getFieldConfigs());
		f.setMessage(null);
		f.setValue(null);
		f.setValid(true);
		f.setName(this.getName());
		return f;
	}

	public void setFieldConfigs(ArrayList<FieldConfig> fieldConfigs) {
		this.fieldConfigs = fieldConfigs;
	}

	/**
	 * 校验
	 * 
	 */
	protected void validate() {
		for (Iterator itr = fieldConfigs.iterator(); itr.hasNext();) {
			FieldConfig fieldConfig = (FieldConfig) itr.next();
			if (!fieldConfig.getValidator().validate(value)) {
				isValid = false;
				setMessage(fieldConfig.getMessage());
				break;
			}
		}
	}

	public ArrayList<FieldConfig> getFieldConfigs() {
		return fieldConfigs;
	}

	public void setFieldConfigs(FieldConfig fieldConfig) {
		this.fieldConfigs.add(fieldConfig);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isValid() {
		return isValid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

}
