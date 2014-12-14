package com.nonfamous.commom.form.validator;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nonfamous.commom.util.StringUtils;

public class ByteLengthValidator implements Validator {
	private static final Log logger = LogFactory
			.getLog(ByteLengthValidator.class);

	private Integer min = 0;

	private Integer max = Integer.MAX_VALUE;

	public boolean validate(String value) {
		if (StringUtils.isBlank(value)) {
			return true;
		}
		int length = 0;
		try {
			length = value.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		if (length >= min.intValue() && length <= max.intValue()) {
			return true;
		}
		return false;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

}
