package com.nonfamous.commom.form.validator;


/**
 * @author:alan 
 * <pre>
 * 校验接口类
 * 如果要增加一种校验请实现该接口，fromFactory中将自动为实现类设置相关属性值，如果实现类有属性，必须同校验类配置再同一个
 * xml的条目中
 * 如：<length-validator min="10" max="100">
 * 另外需要在FormFactory的createValidator中增加一个创建方法
 * </pre>
 * @version $Id: Validator.java,v 1.1 2008/07/11 00:47:08 fred Exp $
 */
public interface Validator {
	public boolean validate(String value);
}
