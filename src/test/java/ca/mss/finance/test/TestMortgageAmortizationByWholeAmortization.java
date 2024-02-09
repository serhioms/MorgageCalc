package ca.mss.finance.test;

import ca.mss.finance.mc.impl.Mortgage;
import ca.mss.finance.mc.impl.MortgageAmortization;
import ca.mss.finance.mc.impl.MortgageContext;
import ca.mss.finance.mc.impl.MortgageSettings;

public class TestMortgageAmortizationByWholeAmortization extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationByWholeAmortization.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public static void main(String[] args) {
		
		int mortgeIndex = 3;

		Mortgage m = new Mortgage(new MortgageSettings());
		MortgageContext mc = mca[mortgeIndex];
		m.computate(mc);
		
		MortgageAmortization ma = new MortgageAmortization(mc, pfa[mortgeIndex]);
		
		/*
		TextPrinterHelper.printInput(System.out, context, "CANADIAN MORTGAGE CALCULATOR");
		TextPrinterHelper.printPayments(System.out, context);
		
		TextPrinterHelper.printAmortization(System.out, context, ma, AmortizationType.BY_AMORTIZATION);
*/		
	}
	

}


