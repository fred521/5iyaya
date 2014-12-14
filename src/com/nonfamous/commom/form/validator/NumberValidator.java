package com.nonfamous.commom.form.validator;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author:alan 
 * <pre>
 * 数字校验，如果输入为空，则校验也是成功的
 * </pre>
 * @version $Id: NumberValidator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public class NumberValidator implements Validator {
    private double equalTo              = Double.NaN;
    private double notEqualTo           = Double.NaN;
    private double lessThan             = Double.NaN;
    private double greaterThan          = Double.NaN;
    private double lessThanOrEqualTo    = Double.NaN;
    private double greaterThanOrEqualTo = Double.NaN;

    /**
     * 取得等于操作。
     *
     * @return 要比较的值
     */
    public double getEqualTo() {
        return equalTo;
    }

    /**
     * 设置等于操作。
     *
     * @param value 要比较的值
     */
    public void setEqualTo(double value) {
        this.equalTo = value;
    }

    /**
     * 取得不等于操作。
     *
     * @return 要比较的值
     */
    public double getNotEqualTo() {
        return notEqualTo;
    }

    /**
     * 设置不等于操作。
     *
     * @param value 要比较的值
     */
    public void setNotEqualTo(double value) {
        this.notEqualTo = value;
    }

    /**
     * 取得小于操作。
     *
     * @return 要比较的值
     */
    public double getLessThan() {
        return lessThan;
    }

    /**
     * 设置小于操作。
     *
     * @param value 要比较的值
     */
    public void setLessThan(double value) {
        this.lessThan = value;
    }

    /**
     * 取得大于操作。
     *
     * @return 要比较的值
     */
    public double getGreaterThan() {
        return greaterThan;
    }

    /**
     * 设置大于操作。
     *
     * @param value 要比较的值
     */
    public void setGreaterThan(double value) {
        this.greaterThan = value;
    }

    /**
     * 取得小于等于操作。
     *
     * @return 要比较的值
     */
    public double getLessThanOrEqualTo() {
        return lessThanOrEqualTo;
    }

    /**
     * 设置小于等于操作。
     *
     * @param value 要比较的值
     */
    public void setLessThanOrEqualTo(double value) {
        this.lessThanOrEqualTo = value;
    }

    /**
     * 取得大于等于操作。
     */
    public double getGreaterThanOrEqualTo() {
        return greaterThanOrEqualTo;
    }

    /**
     * 设置大于等于操作。
     *
     * @param value 要比较的值
     */
    public void setGreaterThanOrEqualTo(double value) {
        this.greaterThanOrEqualTo = value;
    }

    public boolean validate(String value) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        double doubleValue = Double.NaN;
        if (StringUtils.containsOnly(value, "+-.0123456789")) {
            try {
                doubleValue = Double.parseDouble(value);
            } catch (NumberFormatException e) {
            }
        }
        boolean valid = !Double.isNaN(doubleValue);
        if (valid) {
            if (!Double.isNaN(equalTo)) {
                valid &= (doubleValue == equalTo);
            }

            if (!Double.isNaN(notEqualTo)) {
                valid &= (doubleValue != notEqualTo);
            }

            if (!Double.isNaN(lessThan)) {
                valid &= (doubleValue < lessThan);
            }

            if (!Double.isNaN(greaterThan)) {
                valid &= (doubleValue > greaterThan);
            }

            if (!Double.isNaN(lessThanOrEqualTo)) {
                valid &= (doubleValue <= lessThanOrEqualTo);
            }

            if (!Double.isNaN(greaterThanOrEqualTo)) {
                valid &= (doubleValue >= greaterThanOrEqualTo);
            }
        }

        return valid;
    }
    
    public static void main(String[] args){
    	RegexpValidator nv = new RegexpValidator();
    	nv.setPattern("^([0-9]{3,6}-)?([1-9][0-9]{1,7})(-[0-9]{1,6})?$");
    	System.out.println(nv.validate("88888888"));
    	System.out.println(nv.validate("888888"));
    	System.out.println(nv.validate("571-88888888"));
    	System.out.println(nv.validate("-88888888"));
    	System.out.println(nv.validate("5-88888"));
    	System.out.println(nv.validate("88888888-244"));
    	System.out.println(nv.validate(" 333-88888888-332"));
    }
}
