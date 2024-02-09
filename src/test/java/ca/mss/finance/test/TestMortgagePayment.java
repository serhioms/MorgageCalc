package ca.mss.finance.test;

import java.math.BigDecimal;
import java.util.Date;

import ca.mss.finance.mc.ExtraPaymentFrequency;
import ca.mss.finance.mc.ExtraPaymentOrder;
import ca.mss.finance.mc.PaymentFrequency;
import ca.mss.finance.mc.impl.MortgageContext;
import ca.mss.finance.mc.impl.MortgageDuration;
import ca.mss.finance.mc.impl.MortgageSettings;
import ca.mss.finance.util.UtilDateTime;

public class TestMortgagePayment {

	final static public String className = TestMortgagePayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	/**
	 * @param args
	 */

	static public BigDecimal PRINCIPAL = new BigDecimal("100000.0", MortgageSettings.MATH_CONTEXT);
	static public BigDecimal ANUAL_RATE = new BigDecimal("0.03");
	static public int AMORTIZATION_PHERIOD_YEARS = 25;
	static public Date START_DATE = UtilDateTime.parse("10/03/2012", "MM/dd/yyyy");
	static public BigDecimal YEAR_TERM = new BigDecimal(5);

	static public BigDecimal MAX_MONTH_EXTRAS_PRC = new BigDecimal("20");
	static public BigDecimal MAX_YEAR_EXTRAS_PRC = new BigDecimal("20");
	
	static public boolean MINIMIZE_MO_PAYMENTS = true;
	
	static public MortgageContext[] mca = new MortgageContext[]{
		
		new MortgageContext(ANUAL_RATE, PRINCIPAL, new MortgageDuration(AMORTIZATION_PHERIOD_YEARS, 0), START_DATE, YEAR_TERM),
					
		new MortgageContext(ANUAL_RATE, PRINCIPAL, new MortgageDuration(AMORTIZATION_PHERIOD_YEARS, 0), START_DATE, YEAR_TERM, 
					new BigDecimal("310", MortgageSettings.MATH_CONTEXT), ExtraPaymentFrequency.MONTHLY, ExtraPaymentOrder.AFTER_PAYMENTS,
					MAX_MONTH_EXTRAS_PRC, MAX_YEAR_EXTRAS_PRC, MINIMIZE_MO_PAYMENTS),
					
		new MortgageContext(ANUAL_RATE, PRINCIPAL, new MortgageDuration(AMORTIZATION_PHERIOD_YEARS, 0), START_DATE, YEAR_TERM, 
				new BigDecimal("0", MortgageSettings.MATH_CONTEXT), ExtraPaymentFrequency.MONTHLY, ExtraPaymentOrder.AFTER_PAYMENTS),		
		
		new MortgageContext(new BigDecimal("0.0285"), new BigDecimal("500"), new MortgageDuration(AMORTIZATION_PHERIOD_YEARS, 0), START_DATE, new BigDecimal("50"), 
				new BigDecimal("50000", MortgageSettings.MATH_CONTEXT), ExtraPaymentFrequency.MONTHLY, ExtraPaymentOrder.BEFORE_PAYMENTS),		
	}; 

	static public PaymentFrequency[] pfa = new PaymentFrequency[]{
		
		PaymentFrequency.ACCELERATED_BI_WEEKLY,
					
		PaymentFrequency.WEEKLY,

		PaymentFrequency.ACCELERATED_WEEKLY,

		PaymentFrequency.MONTHLY
	}; 


}


