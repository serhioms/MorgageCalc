package ca.mss.finance.mc.impl;

import java.math.BigDecimal;
import java.util.Date;

import ca.mss.finance.excel.ExcelFunctions;
import ca.mss.finance.util.UtilDateTime;
import ca.mss.finance.mc.ExtraPaymentOrder;


public class MortgageAmortizationRow {
	
	final static public String className = MortgageAmortizationRow.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public MortgageAmortization amortization;
	final public MortgageContext context;
	
	public int nop;
	public Date balanceInDate;
	public BigDecimal balanceIn = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal interest = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal interestPrc = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal principal = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal principalPrc = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public Date payday;
	public BigDecimal payment = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal paymentMo = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal extraPayment = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal extraPaymentMo = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal extraPaymentYear = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal extraPrc = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal extraPrcMo = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal extraPrcYear = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal fullPayment = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal fullPrincipal = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal fullPrincipalPrc = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalFullPayment = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalExtraPayment = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalExtraPrc = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalPayment = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalInterest = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalInterestPrc = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalPrincipal = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal totalPrincipalPrc = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public BigDecimal balanceOut = new BigDecimal("0.0", MortgageSettings.MATH_CONTEXT);
	public Date balanceOutDate;
	public int balanceOutYear;
	public int nextBalanceOutYear; // For hasNext filtering
	public String balanceOutMonth;
	public String balanceOutMonthYear;
	public String balanceOutMonthDay;
	public String balanceOutMonthDayYear;
	public String balanceOutQuoter;
	public int balanceTerm;
	public int nextBalanceTerm;  // For hasNext filtering

	public int payInMo;
	public Date paydayPrev;
	public Date paydayNext;

	public MortgageAmortizationRow(MortgageAmortization amortization) {
		this.amortization = amortization;
		this.context = amortization.context;
	}

	final public boolean ifProcessedExtrasBefore(){
		extraPayment = ExcelFunctions.ZERO;
		if( context.extraPayment.compareTo(ExcelFunctions.ZERO) == 1 && !(context.minimizeMoPayments  && payInMo > amortization.paymentFrequency.paymentsPerMonth.intValue())){
			if( context.extraOrder == ExtraPaymentOrder.BEFORE_PAYMENTS ){
				if( nop == 1 || !UtilDateTime.isSameMonth(payday, paydayPrev) ){
					switch( context.extraFrequency ){
					case ANNUAL:
						extraPayment = (UtilDateTime.getMonth(payday)-1) == 0? context.extraPayment: ExcelFunctions.ZERO; 
						break;
					case SEMI_ANNUAL:
						extraPayment = (UtilDateTime.getMonth(payday)-1) % 6 == 0? context.extraPayment: ExcelFunctions.ZERO; 
						break;
					case QUOTERLY:
						extraPayment = (UtilDateTime.getMonth(payday)-1) % 3 == 0? context.extraPayment: ExcelFunctions.ZERO; 
						break;
					case MONTHLY:
						extraPayment = context.extraPayment; 
						break;
					}
					return true;
				}
			}
		}
		return false;
	}

	final public boolean ifProcessedExtrasAfter(){
		extraPayment = ExcelFunctions.ZERO;
		if( context.extraPayment.compareTo(ExcelFunctions.ZERO) > 0 && !(context.minimizeMoPayments  && payInMo > amortization.paymentFrequency.paymentsPerMonth.intValue())){
			if( context.extraOrder == ExtraPaymentOrder.AFTER_PAYMENTS ){
				if( !UtilDateTime.isSameMonth(payday, paydayNext) ){
					switch( context.extraFrequency ){
					case ANNUAL:
						extraPayment = UtilDateTime.getMonth(payday) == 12? context.extraPayment: ExcelFunctions.ZERO; 
						break;
					case SEMI_ANNUAL:
						extraPayment = UtilDateTime.getMonth(payday) % 6 == 0? context.extraPayment: ExcelFunctions.ZERO; 
						break;
					case QUOTERLY:
						extraPayment = UtilDateTime.getMonth(payday) % 3 == 0? context.extraPayment: ExcelFunctions.ZERO; 
						break;
					case MONTHLY:
						extraPayment =  context.extraPayment; 
						break;
					}
					return true;
				}
			}
		}
		return false;
	}

}


