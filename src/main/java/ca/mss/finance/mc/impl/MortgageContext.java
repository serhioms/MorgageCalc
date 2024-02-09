package ca.mss.finance.mc.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import ca.mss.finance.excel.ExcelFunctions;
import ca.mss.finance.util.UtilDateTime;
import ca.mss.finance.mc.ExtraPaymentFrequency;
import ca.mss.finance.mc.ExtraPaymentOrder;
import ca.mss.finance.mc.PaymentFrequency;


public class MortgageContext {
	final static public String className = MortgageContext.class.getName();
	final static public long serialVersionUID = className.hashCode();
	final public BigDecimal principal;
	final public MortgageDuration duration;
	final public BigDecimal annualRate;

	final public PaymentFrequency[] paymentType;
	final public BigDecimal[] paymentAmount;
	final public BigDecimal[] paymentRate;
	final public BigDecimal[] amortizationRate;

	final public BigDecimal extraPayment;
	final public ExtraPaymentFrequency extraFrequency;
	final public ExtraPaymentOrder extraOrder; 

	final public BigDecimal maxExtraMonth;
	final public BigDecimal maxExtraYear;

	final public boolean minimizeMoPayments;

	Date startDate; 
	public BigDecimal termYears; 
	
	private final NumberFormat numberFormatter;

	public MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration duration, Date startDate, BigDecimal termYears) {
		this(annualRate, principal, duration, startDate, termYears, ExcelFunctions.ZERO, null, null, ExcelFunctions.ZERO, ExcelFunctions.ZERO, false);
	}

	public MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration duration, Date startDate, BigDecimal termYears, 
			BigDecimal extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder) {
		this(annualRate, principal, duration, startDate, termYears,  
			extraPayment, extraFrequency, extraOrder, 
			ExcelFunctions.ZERO, ExcelFunctions.ZERO, false);
	}

	public MortgageContext(BigDecimal annualRate, BigDecimal principal, MortgageDuration duration, Date startDate, BigDecimal termYears, 
			BigDecimal extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
			BigDecimal maxExtraMonth, BigDecimal maxExtraYear, boolean minimizeMoPayments) {
		this.principal = principal;
		
		this.duration = duration;
		this.annualRate = annualRate;

		this.paymentType = PaymentFrequency.values();
		this.paymentAmount = new BigDecimal[paymentType.length];
		this.paymentRate = new BigDecimal[paymentType.length];
		this.amortizationRate = new BigDecimal[paymentType.length];
		this.startDate = startDate;
		this.termYears = termYears;
		
		this.extraPayment = extraPayment;
		this.extraFrequency = extraFrequency;
		this.extraOrder = extraOrder;

		this.maxExtraMonth = maxExtraMonth;
		this.maxExtraYear = maxExtraYear;

		this.minimizeMoPayments = minimizeMoPayments;
		this.numberFormatter = NumberFormat.getInstance();
		this.numberFormatter.setMaximumFractionDigits(MortgageSettings.SCALE);
	}

	public final BigDecimal getPayment(int index){
		return paymentAmount[index];
	}

	public final BigDecimal getPayment(PaymentFrequency pt){
		return paymentAmount[pt.ordinal()];
	}
	
	public final BigDecimal getPaymentRate(PaymentFrequency pt){
		return paymentRate[pt.ordinal()];
	}

	public final BigDecimal getAmortizationRate(PaymentFrequency pt){
		return amortizationRate[pt.ordinal()];
	}

	public final String format(String s){
		return s;
	}
	
	public final String format(int n){
		return Integer.toString(n);
	}
	
	public final String format(BigDecimal d){
		return numberFormatter.format(d);
	}
	
	public final String format(Date d){
		return UtilDateTime.format(d);
	}

	/**
	 * @return the startDay
	 */
	public final Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the yearTerm
	 */
	public final BigDecimal getYearTerm() {
		return termYears;
	}

	public final PaymentFrequency getPaymentType(int index) {
		return paymentType[index];
	}
	
	public final int getPaymentTypeLength() {
		return paymentType.length;
	}
	
}