package com.nonfamous.commom.form;

import java.util.ArrayList;
import java.util.Iterator;

import com.nonfamous.commom.util.StringUtils;


/**
 * @author:alan
 * 
 * <pre>
 * 默认的Form实现
 * </pre>
 * 
 * @version $Id: DefaultForm.java,v 1.1 2008/07/11 00:46:58 fred Exp $
 */
public class DefaultForm implements Form {

	private String formName;

	private ArrayList<Field> fields = new ArrayList<Field>();

	private ArrayList<String> keys = new ArrayList<String>();

	public Form colne() {

		Form f = new DefaultForm();
		f.setFormName(this.getFormName());
		f.setKeys(this.getKeys());
		for (Iterator itr = this.getFields().iterator(); itr.hasNext();) {
			Field field = (Field) itr.next();
			f.setField(field.clone());
		}
		return f;
	}

	public boolean isValide() {
		boolean tmp = true;
		for (Iterator itr = fields.iterator(); itr.hasNext();) {
			Field field = (Field) itr.next();
			field.validate();
			if (!field.isValid()) {
				tmp = false;
			}
		}
		return tmp;
	}

	public Field getField(String fieldName) {
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		for (Iterator itr = fields.iterator(); itr.hasNext();) {
			Field field = (Field) itr.next();
			if (fieldName.equalsIgnoreCase(field.getName())) {
				return field;
			}
		}
		return null;
	}

	public ArrayList getAllKeys() {
		return keys;
	}

	public void setKey(String key) {
		keys.add(key);
	}

	public void setField(Field field) {
		fields.add(field);
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public ArrayList getFields() {
		return fields;
	}

	public ArrayList<String> getKeys() {
		return keys;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public void setKeys(ArrayList<String> keys) {
		this.keys = keys;
	}

}
