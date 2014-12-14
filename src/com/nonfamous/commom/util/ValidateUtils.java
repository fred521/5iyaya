package com.nonfamous.commom.util;

/**
 * ��֤����
 * @author liujun
 *
 */
public class ValidateUtils {
	/**
	 * ��֤�����Ƿ�Ϊ��
	 * @param obj
	 */
	public static void notNull(Object obj){
		if(obj == null)
			throw new NullPointerException("object should not be null.");
	}
	
	/**
	 * ��֤�����Ƿ�Ϊ��
	 * @param obj
	 */
	public static void notNull(Object obj, String message){
		if(obj == null)
			throw new NullPointerException(message + " should not be null.");
	}
	
	/**
	 * ��֤�ַ����Ƿ�Ϊ��
	 * @param s
	 */
	public static void notBlank(String s){
		if(s.trim().length() == 0)
			throw new IllegalArgumentException("argument should not be blank.");
	}
	
	/**
	 * ��֤�ַ����Ƿ�Ϊ��
	 * @param s
	 * @param message
	 */
	public static void notBlank(String s, String message){
		if(s.trim().length() == 0)
			throw new IllegalArgumentException(message + " should not be blank.");
	}
	
}
