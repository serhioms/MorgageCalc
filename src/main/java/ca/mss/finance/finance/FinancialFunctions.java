package ca.mss.finance.finance;

import java.math.BigDecimal;

import ca.mss.finance.excel.ExcelFunctions;
import ca.mss.finance.mc.PaymentFrequency;
import ca.mss.finance.mc.impl.MortgageDuration;
import ca.mss.finance.util.UtilMath;



public class FinancialFunctions {

	final static public String className = FinancialFunctions.class.getName();
	final static public long serialVersionUID = className.hashCode();
	
	final static public double loanPaymentRate(BigDecimal annualRate, int paymentFrequency){
		return annualRate.doubleValue()/paymentFrequency;
	}
	
	final static public double mortgageCompoundRate(BigDecimal annualRate, int compoundFrequency){
		return Math.pow(1+loanPaymentRate(annualRate, compoundFrequency), compoundFrequency)-1.0;
	}
	
	final static public BigDecimal mortgagePaymentRate(BigDecimal annualRate, int compoundFrequency, int effectiveYearPayments){
		return new BigDecimal(Math.pow(1+mortgageCompoundRate(annualRate, compoundFrequency), 1.0/effectiveYearPayments)-1.0);
	}
	
	final static public BigDecimal mortgagePaymentMonthly(BigDecimal paymentRate, BigDecimal principal, int amortizationYears){
		return mortgagePaymentRegular(paymentRate, principal, amortizationYears, PaymentFrequency.MONTHLY.paymentsPerYear);
	}

	final static public BigDecimal mortgagePaymentRegular(BigDecimal paymentRate, BigDecimal principal, int totalPayments, int yearPayments){
		return ExcelFunctions.PMT(paymentRate, principal, totalPayments).negate();
	}

	final static public BigDecimal mortgagePaymentAccelerated(BigDecimal paymentRate, BigDecimal principal, int amortizationYears, BigDecimal monthPayments){
		return mortgagePaymentMonthly(paymentRate, principal, amortizationYears).divide(monthPayments);
	}

	final static public BigDecimal mortgagePayment(PaymentFrequency pf, BigDecimal principal, BigDecimal paymentRate, MortgageDuration duration, int scale){
		switch( pf ){
		case MONTHLY:
		case SEMI_MONTHLY:
		case BI_WEEKLY:
		case WEEKLY:
			return UtilMath.round(mortgagePaymentRegular(paymentRate, principal, duration.getPaymentsNo(pf), pf.paymentsPerYear), scale);
		case ACCELERATED_BI_WEEKLY:
		case ACCELERATED_WEEKLY:
			return UtilMath.round(mortgagePaymentAccelerated(paymentRate, principal, duration.getPaymentsNo(PaymentFrequency.MONTHLY), pf.paymentsPerMonth), scale);
		default:
			throw new RuntimeException("Not supported payment type ["+pf+"]");
		}
	}


}
