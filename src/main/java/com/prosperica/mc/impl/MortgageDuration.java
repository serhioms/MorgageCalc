package com.prosperica.mc.impl;

import com.prosperica.mc.PaymentFrequency;

public class MortgageDuration {
	
	final public int years, month;

	public MortgageDuration(int years, int month) {
		this.years = years;
		this.month = month;
	}
	
	final public int getPaymentsNo(PaymentFrequency pf){
		return getPaymentsNo(years, month, pf);
	}
	
	final public int monthes(){
		return years*12 + month;
	}
	
	final public int years(){
		return years + month/12;
	}
	
	final static public int getPaymentsNo(int years, int month, PaymentFrequency pf){
		return (years+month/12)*pf.paymentsPerYear + (month%12)*pf.paymentsPerMonth.intValue();
	}

	@Override
	public String toString() {
		return (years>0? years+" y": "")+(month>0? month+" mo": "");
	}
	
	
}
