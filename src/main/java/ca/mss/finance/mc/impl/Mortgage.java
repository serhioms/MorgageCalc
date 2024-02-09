package ca.mss.finance.mc.impl;

import ca.mss.finance.finance.FinancialFunctions;

public class Mortgage {
	
	final static public String className = Mortgage.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private MortgageSettings settings;
	
	/**
	 * 
	 */
	public Mortgage(MortgageSettings settings) {
		this.settings = settings;
	}

	final public void computate(MortgageContext context){
		for(int i=0; i<context.paymentType.length; i++){
			context.amortizationRate[i] = FinancialFunctions.mortgagePaymentRate(context.annualRate, settings.getCompoundType().conversionPeriods, context.paymentType[i].paymentsPerYear);
			context.paymentRate[i] = FinancialFunctions.mortgagePaymentRate(context.annualRate, settings.getCompoundType().conversionPeriods, context.paymentType[i].paymentsPerYearEffective);
			context.paymentAmount[i] = FinancialFunctions.mortgagePayment(context.paymentType[i], context.principal, context.paymentRate[i], context.duration, MortgageSettings.SCALE);
		}
	}

}


