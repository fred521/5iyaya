package com.nonfamous.commom.form.validator;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author:alan 
 * <pre>
 * 长度校验，目前对中文和英文处理一致
 * </pre>
 * @version $Id: LengthValidator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public class LengthValidator implements Validator {

    private Integer min = 0;

    private Integer max = Integer.MAX_VALUE;

    public boolean validate(String value) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        int length = value.length();
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
