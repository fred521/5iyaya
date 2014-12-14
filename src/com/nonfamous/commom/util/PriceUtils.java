package com.nonfamous.commom.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 
 * 人民币分，元之间的转换函数
 * 
 * @author fish
 * @version $Id: PriceUtils.java,v 1.2 2009/07/11 07:00:07 andy Exp $
 * 
 */
public class PriceUtils {

	private static final ThreadLocal<PriceFormats> local = new ThreadLocal<PriceFormats>();

	private static class PriceFormats {
		private NumberFormat yuanFormat = new DecimalFormat("0.00");
	}

	private static PriceFormats getPriceFormats() {
		PriceFormats pu = local.get();
		if (pu == null) {
			pu = new PriceFormats();
			local.set(pu);
		}
		return pu;
	}

	public static final double fenToYuan(long fen) {
		return ((double) fen) / 100;
	}

	public static final double fenToYuan(String fen) {
		return fenToYuan(Long.parseLong(fen));
	}

	public static final String fenToYuanString(long fen) {
		return getPriceFormats().yuanFormat.format(fenToYuan(fen));
	}
	
	public static final String fenToYuanString(long fen,double quotiety)
	{
		return getPriceFormats().yuanFormat.format(fenToYuan(fen)*quotiety);
	}
	
	public static final String fenToYuanString(String fen) {
		if(fen==null){
			return "0.00";
		}
		return getPriceFormats().yuanFormat.format(fenToYuan(fen));
	}

	public static final long yuanToFen(double y) {
		return (long) (y * 100);
	}

	public static final long yuanToFen(String y) {
		return yuanToFen(Double.parseDouble(y));
	}

	public static final String yuanToFenString(double y) {
		return Long.toString(yuanToFen(y));
	}

	public static final String yuanToFenString(String y) {
		return Long.toString(yuanToFen(y));
	}
}
