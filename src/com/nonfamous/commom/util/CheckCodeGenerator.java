package com.nonfamous.commom.util;

import org.apache.commons.lang.math.RandomUtils;

/**
 * <p>
 * ��λ�ֻ�У����������
 * </p>
 * 
 * @author:daodao
 * @version $Id: CheckCodeGenerator.java,v 1.1 2008/07/11 00:47:11 fred Exp $
 */
public class CheckCodeGenerator {
	/**
	 * ������λУ����
	 * @return
	 */
	public static String generate() {
		String word = (RandomUtils.nextInt(900000) + 100000) + "";

		return word;
	}
}