package ca.mss.finance.mc.impl;

import ca.mss.finance.excel.ExcelFunctions;
import ca.mss.finance.mc.ExtraPaymentFrequency;
import ca.mss.finance.mc.ExtraPaymentOrder;
import ca.mss.finance.mc.PaymentFrequency;

import java.math.BigDecimal;
import java.util.Date;


public class MortgageContext {
	final static public String className = MortgageContext.class.getName();
	final static public long serialVersionUID = className.hashCode();
	final public BigDecimal principal;
	final public AmortizationPheriod duration;
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
	
	public MortgageContext(BigDecimal annualRate, BigDecimal principal, AmortizationPheriod duration, Date startDate, BigDecimal termYears) {
		this(annualRate, principal, duration, startDate, termYears, ExcelFunctions.ZERO, null, null, ExcelFunctions.ZERO, ExcelFunctions.ZERO, false);
	}

	public MortgageContext(BigDecimal annualRate, BigDecimal principal, AmortizationPheriod duration, Date startDate, BigDecimal termYears,
						   BigDecimal extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder) {
		this(annualRate, principal, duration, startDate, termYears,  
			extraPayment, extraFrequency, extraOrder, 
			ExcelFunctions.ZERO, ExcelFunctions.ZERO, false);
	}

	public MortgageContext(BigDecimal annualRate, BigDecimal principal, AmortizationPheriod duration, Date startDate, BigDecimal termYears,
						   BigDecimal extraPayment, ExtraPaymentFrequency extraFrequency, ExtraPaymentOrder extraOrder,
						   BigDecimal maxExtraMonth, BigDecimal maxExtraYear, boolean minimizeMoPayments) {
		this.principal = principal;
		
		this.duration = duration;
		this.annualRate = annualRate.compareTo(ExcelFunctions.ONE)<0? annualRate: annualRate.divide(ExcelFunctions.HUNDRED);

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

		this.minimizeMoPayments = minimizeMoPayments;;
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