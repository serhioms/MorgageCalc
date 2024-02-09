package ca.mss.finance.excel;

import java.math.BigDecimal;

public class ExcelFunctions {

	final public static String className = ExcelFunctions.class.getName();
	final public static long serialVersionUID = className.hashCode();
	
	final public static BigDecimal ZERO = new BigDecimal("0.0");
	final public static BigDecimal ONE = new BigDecimal("1.0");
	final public static BigDecimal HUNDRED = new BigDecimal("100.0");
	
	final public static BigDecimal PMT(BigDecimal interestRate, BigDecimal principalValue, int numberOfPeriods){
		double pow = Math.pow(interestRate.add(ONE).doubleValue(), numberOfPeriods);
		BigDecimal pow1 = new BigDecimal(pow);
		BigDecimal pow2 = new BigDecimal(pow).subtract(ExcelFunctions.ONE);
		return interestRate.multiply(principalValue.multiply(pow1.divide(pow2, BigDecimal.ROUND_UP))).negate();
	}

}
