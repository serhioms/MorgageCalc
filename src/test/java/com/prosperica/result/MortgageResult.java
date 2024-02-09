package com.prosperica.result;

import com.prosperica.mc.impl.Mortgage;
import com.prosperica.mc.impl.MortgageAmortization;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.impl.MortgageSettings;
import com.prosperica.test.TestMortgagePayment;

public class MortgageResult extends TestMortgagePayment {

	final static public String className = MortgageResult.class.getName();
	final static public long serialVersionUID = className.hashCode();

	public static void main(String[] args) {

		int mortgageIndex = 2;
		
		MortgageContext mc = TestMortgagePayment.mca[mortgageIndex];

		Mortgage calculator = new Mortgage(new MortgageSettings());
		
		calculator.computate(mc);
		MortgageAmortization ma = new MortgageAmortization(mc, pfa[mortgageIndex]);
		
		/*
		TextPrinterHelper.printTableTabAmortization(System.out, ma, mc, "TAB: Table, RADIO: Year", 1);
		TextPrinterHelper.printTableTabQuoter(System.out, ma, mc, "TAB: Table, RADIO: Quoter", 1, 2013);
		TextPrinterHelper.printTableTabMonth(System.out, ma, mc, "TAB: Table, RADIO: Month", 1, 2013);
		TextPrinterHelper.printTableTabPayment(System.out, ma, mc, "TAB: Table, RADIO: Payment", 1, 2013);
		*/
	}
	
}


