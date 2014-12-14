package com.nonfamous.commom.form.validator;

import com.nonfamous.commom.util.StringUtils;

/**
 * @author:alan 
 * <pre>
 * ����У�飬�������Ϊ�գ���У��Ҳ�ǳɹ���
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
     * ȡ�õ��ڲ�����
     *
     * @return Ҫ�Ƚϵ�ֵ
     */
    public double getEqualTo() {
        return equalTo;
    }

    /**
     * ���õ��ڲ�����
     *
     * @param value Ҫ�Ƚϵ�ֵ
     */
    public void setEqualTo(double value) {
        this.equalTo = value;
    }

    /**
     * ȡ�ò����ڲ�����
     *
     * @return Ҫ�Ƚϵ�ֵ
     */
    public double getNotEqualTo() {
        return notEqualTo;
    }

    /**
     * ���ò����ڲ�����
     *
     * @param value Ҫ�Ƚϵ�ֵ
     */
    public void setNotEqualTo(double value) {
        this.notEqualTo = value;
    }

    /**
     * ȡ��С�ڲ�����
     *
     * @return Ҫ�Ƚϵ�ֵ
     */
    public double getLessThan() {
        return lessThan;
    }

    /**
     * ����С�ڲ�����
     *
     * @param value Ҫ�Ƚϵ�ֵ
     */
    public void setLessThan(double value) {
        this.lessThan = value;
    }

    /**
     * ȡ�ô��ڲ�����
     *
     * @return Ҫ�Ƚϵ�ֵ
     */
    public double getGreaterThan() {
        return greaterThan;
    }

    /**
     * ���ô��ڲ�����
     *
     * @param value Ҫ�Ƚϵ�ֵ
     */
    public void setGreaterThan(double value) {
        this.greaterThan = value;
    }

    /**
     * ȡ��С�ڵ��ڲ�����
     *
     * @return Ҫ�Ƚϵ�ֵ
     */
    public double getLessThanOrEqualTo() {
        return lessThanOrEqualTo;
    }

    /**
     * ����С�ڵ��ڲ�����
     *
     * @param value Ҫ�Ƚϵ�ֵ
     */
    public void setLessThanOrEqualTo(double value) {
        this.lessThanOrEqualTo = value;
    }

    /**
     * ȡ�ô��ڵ��ڲ�����
     */
    public double getGreaterThanOrEqualTo() {
        return greaterThanOrEqualTo;
    }

    /**
     * ���ô��ڵ��ڲ�����
     *
     * @param value Ҫ�Ƚϵ�ֵ
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
