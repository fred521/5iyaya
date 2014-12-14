package com.nonfamous.commom.form.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author:alan
 * 
 * <pre>
 *     ������ʽУ�飬������ʽΪ�ջ�����Ϊ����У��ʧ��
 * </pre>
 * 
 * @version $Id: RegexpValidator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public class RegexpValidator implements Validator {
	private String pattern;

	private Pattern reg;

	public boolean validate(String value) {
		if (StringUtils.isBlank(value)) {
			return true;
		}
		if (reg == null) {
			return false;
		}
		Matcher m = reg.matcher(value);
		return m.matches();
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		if (StringUtils.isNotBlank(pattern)) {
			this.reg = Pattern.compile(pattern);
		}
	}

}
