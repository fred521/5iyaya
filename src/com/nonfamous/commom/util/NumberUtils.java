package com.nonfamous.commom.util;


public final class NumberUtils{
	
	public static final Integer EMPTY_INTEGER = new Integer(0);;
	public static int string2Integer(String str) {
		return (str == null) ? EMPTY_INTEGER : Integer.valueOf(str);
	}
}