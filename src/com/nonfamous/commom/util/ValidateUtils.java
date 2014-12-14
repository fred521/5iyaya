package com.nonfamous.commom.util;

/**
 * 验证工具
 * @author liujun
 *
 */
public class ValidateUtils {
	/**
	 * 验证对象是否为空
	 * @param obj
	 */
	public static void notNull(Object obj){
		if(obj == null)
			throw new NullPointerException("object should not be null.");
	}
	
	/**
	 * 验证对象是否为空
	 * @param obj
	 */
	public static void notNull(Object obj, String message){
		if(obj == null)
			throw new NullPointerException(message + " should not be null.");
	}
	
	/**
	 * 验证字符串是否为空
	 * @param s
	 */
	public static void notBlank(String s){
		if(s.trim().length() == 0)
			throw new IllegalArgumentException("argument should not be blank.");
	}
	
	/**
	 * 验证字符串是否为空
	 * @param s
	 * @param message
	 */
	public static void notBlank(String s, String message){
		if(s.trim().length() == 0)
			throw new IllegalArgumentException(message + " should not be blank.");
	}
	
}
