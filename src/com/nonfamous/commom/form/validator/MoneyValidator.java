//===================================================================
// Created on Jun 12, 2007
//===================================================================
package com.nonfamous.commom.form.validator;

import com.nonfamous.commom.util.StringUtils;

/**
 * <p>
 *  ½ð¶îÐ£Ñé
 * </p>
 * @author jacky
 * @version $Id: MoneyValidator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */

public class MoneyValidator extends NumberValidator {

    public boolean validate(String value) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        boolean su = super.validate(value);

        if (!su) {
            return su;
        }

        int ind = value.indexOf(".");

        if (ind != -1 && value.length() > ind + 3) {
            return false;
        }

        return true;
    }
}
