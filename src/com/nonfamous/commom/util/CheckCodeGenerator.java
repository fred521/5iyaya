package com.nonfamous.commom.util;

import org.apache.commons.lang.math.RandomUtils;

/**
 * <p>
 * 六位手机校验码生成器
 * </p>
 * 
 * @author:daodao
 * @version $Id: CheckCodeGenerator.java,v 1.1 2008/07/11 00:47:11 fred Exp $
 */
public class CheckCodeGenerator {
	/**
	 * 生成六位校验码
	 * @return
	 */
	public static String generate() {
		String word = (RandomUtils.nextInt(900000) + 100000) + "";

		return word;
	}
}