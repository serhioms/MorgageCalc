package com.prosperica.junit;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.PaymentFrequency;
import com.prosperica.mc.impl.Mortgage;
import com.prosperica.mc.impl.MortgageAmortization;
import com.prosperica.mc.impl.MortgageAmortizationRow;
import com.prosperica.mc.impl.MortgageContext;
import com.prosperica.mc.impl.MortgageDuration;
import com.prosperica.mc.impl.MortgageSettings;
import com.prosperica.util.UtilDateTime;

public class TestJPFinanceModule {

	MortgageContext data;
	Mortgage calc;

	@Before
	public void setUp() throws Exception {
		data = new MortgageContext(
				new BigDecimal("0.045"), 
				new BigDecimal("200000.0", MortgageSettings.MATH_CONTEXT), 
				new MortgageDuration(30, 0), 
				UtilDateTime.parse("10/03/2012", "MM/dd/yyyy"), 
				new BigDecimal(5));

		calc = new Mortgage(new MortgageSettings());

		calc.computate(data);
	}

	@Test
	public void Weekly() {
		assertTrue("Weekly = "+data.getPayment(PaymentFrequency.WEEKLY), data.getPayment(PaymentFrequency.WEEKLY).compareTo(new BigDecimal("232.38"))==0 );
	}
	
	@Test
	public void Monthly() {
		assertTrue("Monthly = "+data.getPayment(PaymentFrequency.MONTHLY), data.getPayment(PaymentFrequency.MONTHLY).compareTo(new BigDecimal("1008.43"))==0 );
	}

	@Test
	public void SemiMonthly() {
		assertTrue("SemiMonthly = "+data.getPayment(PaymentFrequency.SEMI_MONTHLY), data.getPayment(PaymentFrequency.SEMI_MONTHLY).compareTo(new BigDecimal("503.75"))==0 );
	}
	
	@Test
	public void BiWeekly() {
		assertTrue("BiWeekly = "+data.getPayment(PaymentFrequency.BI_WEEKLY), data.getPayment(PaymentFrequency.BI_WEEKLY).compareTo(new BigDecimal("464.97" ))==0 );
	}

	@Test
	public void AcceleratedBiWeekly() {
		assertTrue("AcceleratedBiWeekly = "+data.getPayment(PaymentFrequency.ACCELERATED_BI_WEEKLY), data.getPayment(PaymentFrequency.ACCELERATED_BI_WEEKLY).compareTo(new BigDecimal("504.22" ))==0 );
	}

	@Test
	public void AcceleratedWeekly() {
		assertTrue("AcceleratedWeekly = "+data.getPayment(PaymentFrequency.ACCELERATED_WEEKLY), data.getPayment(PaymentFrequency.ACCELERATED_WEEKLY).compareTo(new BigDecimal("252.11" ))==0 );
	}

	@Test
	public void MonthlyAmortization() {
		MortgageAmortization amort = new MortgageAmortization(data, PaymentFrequency.MONTHLY);
		Iterator<MortgageAmortizationRow> iter = amort.getIterator(AmortizationType.BY_PAYMENT);

		for(int nbr=1; iter.hasNext(); nbr++ ){
			MortgageAmortizationRow row = iter.next();

			// System.out.println(nbr+"\t"+row.payment+"\t"+row.principal+"\t"+row.interest+"\t"+row.balanceOut);
			
			switch(nbr){
			case 1:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("1008.43" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("265.37" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("743.06" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("199734.63" ))==0 );
				break;
			case 180:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("1008.43" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("515.39" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("493.04" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("132189.77" ))==0 );
				break;
			case 359:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("1008.43" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("1000.97" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("7.46" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("1005.61" ))==0 );
				break;
			case 360:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment,  row.payment.compareTo(new BigDecimal("1009.35" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal,  row.principal.compareTo(new BigDecimal("1005.61" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest,  row.interest.compareTo(new BigDecimal("3.74" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("0.0" ))==0 );
				break;
			}
		}
	}
	
	@Test
	public void SemiMonthlyAmortization() {
		MortgageAmortization amort = new MortgageAmortization(data, PaymentFrequency.SEMI_MONTHLY);
		Iterator<MortgageAmortizationRow> iter = amort.getIterator(AmortizationType.BY_PAYMENT);

		for(int nbr=1; iter.hasNext(); nbr++ ){
			MortgageAmortizationRow row = iter.next();
			
			switch(nbr){
			case 1:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("503.75" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("132.56" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("371.19" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("199867.44" ))==0 );
				break;
			case 360:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("503.75" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("257.94" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("245.81" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("132188.36" ))==0 );
				break;
			case 719:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("503.75" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("501.89" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("1.86" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("499.81" ))==0 );
				break;
			case 720:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment,  row.payment.compareTo(new BigDecimal("500.74" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal,  row.principal.compareTo(new BigDecimal("499.81" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest,  row.interest.compareTo(new BigDecimal("0.93" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("0.0" ))==0 );
				break;
			}
		}
	}
	
	
	@Test
	public void WeekleAmortization() {
		MortgageAmortization amort = new MortgageAmortization(data, PaymentFrequency.WEEKLY);
		Iterator<MortgageAmortizationRow> iter = amort.getIterator(AmortizationType.BY_PAYMENT);

		for(int nbr=1; iter.hasNext(); nbr++ ){
			MortgageAmortizationRow row = iter.next();
			
			switch(nbr){
			case 1:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("232.38" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("61.15" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("171.23" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("199938.85" ))==0 );
				break;
			case 780:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("232.38" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("119.10" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("113.28" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("132192.92" ))==0 );
				break;
			case 1559:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("232.38" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("231.97" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("0.41" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("242.15" ))==0 );
				break;
			case 1560:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment,  row.payment.compareTo(new BigDecimal("242.36" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal,  row.principal.compareTo(new BigDecimal("242.15" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest,  row.interest.compareTo(new BigDecimal("0.21" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("0.0" ))==0 );
				break;
			}
		}
	}
	
	@Test
	public void BiWeekleAmortization() {
		MortgageAmortization amort = new MortgageAmortization(data, PaymentFrequency.BI_WEEKLY);
		Iterator<MortgageAmortizationRow> iter = amort.getIterator(AmortizationType.BY_PAYMENT);

		for(int nbr=1; iter.hasNext(); nbr++ ){
			MortgageAmortizationRow row = iter.next();
			
			switch(nbr){
			case 1:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("464.97" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("122.36" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("342.61" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("199877.64" ))==0 );
				break;
			case 370:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("464.97" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("230.11" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("234.86" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("136872.57" ))==0 );
				break;
			case 779:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("464.97" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("463.39" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("1.58" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("456.15" ))==0 );
				break;
			case 780:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment,  row.payment.compareTo(new BigDecimal("456.93" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal,  row.principal.compareTo(new BigDecimal("456.15" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest,  row.interest.compareTo(new BigDecimal("0.78" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("0.0" ))==0 );
				break;
			}
		}
	}

	
	@Test
	public void AcceleratedBiWeekleAmortization() {
		MortgageAmortization amort = new MortgageAmortization(data, PaymentFrequency.ACCELERATED_BI_WEEKLY);
		Iterator<MortgageAmortizationRow> iter = amort.getIterator(AmortizationType.BY_PAYMENT);

		for(int nbr=1; iter.hasNext(); nbr++ ){
			MortgageAmortizationRow row = iter.next();
			
			switch(nbr){
			case 1:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("504.22" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("161.61" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("342.61" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("199838.39" ))==0 );
				break;
			case 333:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("504.22" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("285.27" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("218.95" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("127528.42" ))==0 );
				break;
			case 664:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("504.22" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("502.69" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("1.53" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("392.66" ))==0 );
				break;
			case 665:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment,  row.payment.compareTo(new BigDecimal("393.33" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal,  row.principal.compareTo(new BigDecimal("392.66" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest,  row.interest.compareTo(new BigDecimal("0.67" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("0.0" ))==0 );
				break;
			}
		}
	}

	
	@Test
	public void AcceleratedWeekleAmortization() {
		MortgageAmortization amort = new MortgageAmortization(data, PaymentFrequency.ACCELERATED_WEEKLY);
		Iterator<MortgageAmortizationRow> iter = amort.getIterator(AmortizationType.BY_PAYMENT);

		for(int nbr=1; iter.hasNext(); nbr++ ){
			MortgageAmortizationRow row = iter.next();
			
			switch(nbr){
			case 1:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("252.11" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("80.88" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("171.23" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("199919.12" ))==0 );
				break;
			case 666:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("252.11" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("142.89" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("109.22" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("127431.76" ))==0 );
				break;
			case 1328:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment, row.payment.compareTo(new BigDecimal("252.11" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal, row.principal.compareTo(new BigDecimal("251.79" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest, row.interest.compareTo(new BigDecimal("0.32" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("126.25" ))==0 );
				break;
			case 1329:
				assertTrue("nbr="+nbr+"\t"+"payment="+row.payment,  row.payment.compareTo(new BigDecimal("126.36" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"principal="+row.principal,  row.principal.compareTo(new BigDecimal("126.25" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"interest="+row.interest,  row.interest.compareTo(new BigDecimal("0.11" ))==0 );
				assertTrue("nbr="+nbr+"\t"+"balanceOut="+row.balanceOut, row.balanceOut.compareTo(new BigDecimal("0.0" ))==0 );
				break;
			}
		}
	}

}
