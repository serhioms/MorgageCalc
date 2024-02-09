package ca.mss.finance.perfomance;

import java.math.BigDecimal;
import java.util.Iterator;

import ca.mss.finance.util.UtilDateTime;
import ca.mss.finance.util.UtilTimer;
import ca.mss.finance.mc.AmortizationType;
import ca.mss.finance.mc.PaymentFrequency;
import ca.mss.finance.mc.impl.Mortgage;
import ca.mss.finance.mc.impl.MortgageAmortization;
import ca.mss.finance.mc.impl.MortgageAmortizationRow;
import ca.mss.finance.mc.impl.MortgageContext;
import ca.mss.finance.mc.impl.MortgageDuration;
import ca.mss.finance.mc.impl.MortgageSettings;

public class TestPerfomanceBigDecimal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// BigDecimal, sec: 10.54,10.07,10.41,10.1
		// double, sec: 18.96,18.57,18.85,18.58
		UtilTimer timer = new UtilTimer();
		
		MortgageContext data = new MortgageContext(
				new BigDecimal("0.045"), 
				new BigDecimal("200000.0", MortgageSettings.MATH_CONTEXT),
				new MortgageDuration(30, 0),
				UtilDateTime.parse("10/03/2012", "MM/dd/yyyy"),
				new BigDecimal(5));

		Mortgage calc = new Mortgage(new MortgageSettings());

		calc.computate(data);

		MortgageAmortization amort = new MortgageAmortization(data, PaymentFrequency.ACCELERATED_WEEKLY);

		timer.start();

		for(int i=0; i<500; i++ ){
			Iterator<MortgageAmortizationRow> iter = amort.getIterator(AmortizationType.BY_PAYMENT);
	
			for(int nbr=1; iter.hasNext(); nbr++ ){
				MortgageAmortizationRow row = iter.next();
	
				//System.out.println(nbr+"\t"+row.payment+"\t"+row.principal+"\t"+row.interest+"\t"+row.balanceOut);
	
			}
		}

		System.out.println(timer.duration());

	}

}
