package com.prosperica.util;

import java.math.BigDecimal;


public class UtilMath {
	final public static String className = UtilMath.class.getName();
	final public static long serialVersionUID = className.hashCode();
	
	final static public BigDecimal round(BigDecimal bd, int scale){
		return bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}

//	final static public BigDecimal ceil(BigDecimal bd, int scale){
//		return bd.setScale(scale, RoundingMode.CEILING);
//	}

	final public static BigDecimal max(BigDecimal a, BigDecimal b){
		return a.max(b);
	}

	final public static BigDecimal min(BigDecimal a, BigDecimal b){
		return a.min(b);
	}

	final public static int max(int a, int b){
		return (a>=b)?a:b;
	}

	final public static int min(int a, int b){
		return (a<=b)?a:b;
	}
}