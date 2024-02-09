package ca.mss.finance.test;

import ca.mss.finance.mc.impl.Mortgage;
import ca.mss.finance.mc.impl.MortgageAmortization;
import ca.mss.finance.mc.impl.MortgageContext;
import ca.mss.finance.mc.impl.MortgageSettings;

public class TestMortgageAmortizationByTerm extends TestMortgagePayment {

	final static public String className = TestMortgageAmortizationByTerm.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public static void main(String[] args) {
		
		int mortgeIndex = 2;

		Mortgage m = new Mortgage(new MortgageSettings());
		MortgageContext mc = mca[mortgeIndex];
		m.computate(mc);
		
		MortgageAmortization ma = new MortgageAmortization(mc, pfa[mortgeIndex]);
		
		/*
		TextPrinterHelper.printInput(System.out, mc, "CANADIAN MORTGAGE CALCULATOR");
		TextPrinterHelper.printPayments(System.out, mc);
		
		TextPrinterHelper.printAmortization(System.out, mc, ma, AmortizationType.BY_TERM);
		*/
	}
	

}


