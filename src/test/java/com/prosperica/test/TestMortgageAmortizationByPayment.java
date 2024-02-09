package com.prosperica.test;

import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.impl.Mortgage;
import com.prosperica.mc.impl.MortgageAmortization;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.impl.MortgageSettings;
import com.prosperica.mc.printers.TextPrinterHelper;

public class TestMortgageAmortizationByPayment extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationByPayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int mortgeIndex = 3;

		Mortgage m = new Mortgage(new MortgageSettings());
		MortgageContext mc = mca[mortgeIndex];
		m.computate(mc);
		
		MortgageAmortization ma = new MortgageAmortization(mc, pfa[mortgeIndex]);
		
		/*
		
		TextPrinterHelper.printInput(System.out, mc, "CANADIAN MORTGAGE CALCULATOR");
		TextPrinterHelper.printPayments(System.out, mc);
		
		TextPrinterHelper.printAmortization(System.out, mc, ma, AmortizationType.BY_PAYMENT, 0, 0);
*/
	}

}


