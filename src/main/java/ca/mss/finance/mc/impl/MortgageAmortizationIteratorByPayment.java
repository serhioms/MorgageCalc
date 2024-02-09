package ca.mss.finance.mc.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import ca.mss.finance.excel.ExcelFunctions;
import ca.mss.finance.mc.util.UtilMortgageCalendar;
import ca.mss.finance.util.UtilDateTime;
import ca.mss.finance.util.UtilMath;


public class MortgageAmortizationIteratorByPayment implements Iterator<MortgageAmortizationRow> {
	
	final static public String className = MortgageAmortizationIteratorByPayment.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final public MortgageAmortization amortization;
	final public MortgageContext context;

	protected BigDecimal balance = new BigDecimal("0.0");

	private int order;
	private MortgageAmortizationRow row;

	public MortgageAmortizationIteratorByPayment(MortgageAmortization amortization) {
		this.amortization = amortization;
		this.context = amortization.context;
		this.row = new MortgageAmortizationRow(amortization);
		this.balance = amortization.context.principal;
		this.order = 1;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return balance.compareTo(ExcelFunctions.ZERO) == 1;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public MortgageAmortizationRow next() {

		if( row.nop == 0 ){
			row.payday = amortization.context.getStartDate();
			row.paydayNext = UtilMortgageCalendar.getNextPayday(1, amortization.paymentFrequency, row.payday);
		} else {
			row.paydayPrev = row.payday;
			row.payday = row.paydayNext;
			row.paydayNext = UtilMortgageCalendar.getNextPayday(1, amortization.paymentFrequency, row.payday);
		}

		row.nop = order++;
		
		row.balanceIn = balance;
		row.balanceInDate = row.payday;

		row.interest = UtilMath.round(row.balanceIn.multiply(amortization.context.getAmortizationRate(amortization.paymentFrequency)), MortgageSettings.SCALE);
		row.principal = amortization.context.getPayment(amortization.paymentFrequency).subtract(row.interest);

		row.balanceOutDate = row.payday;
		row.balanceOutYear = Integer.parseInt(UtilDateTime.format(row.payday, "yyyy"));
		row.balanceOutMonth = UtilDateTime.format(row.payday, "MMM");
		row.balanceOutMonthDay = UtilDateTime.format(row.payday, "MMM d");
		row.balanceOutMonthYear = UtilDateTime.format(row.payday, "MMM yyyy");
		row.balanceOutQuoter = UtilDateTime.getQuoter(row.payday)+"/"+UtilDateTime.format(row.payday, "yy");
		row.balanceOut = row.balanceIn.subtract(row.principal);
		row.balanceTerm = (row.nop-1)/amortization.termNumberOfPayments+1;

		if( !UtilDateTime.isSameMonth(row.payday, row.paydayPrev) ){
			row.payInMo = calculateNumberOfPaymentsInMonth(row.payday, row);
		}
		
		if( row.balanceOut.compareTo(MortgageAmortization.MINIMUM_PAYMENT) <= 0 ){
			row.principal = row.principal.add(row.balanceOut);
			row.balanceOut = ExcelFunctions.ZERO;
		}
		
		row.payment = row.interest.add(row.principal);
		if( UtilDateTime.isSameMonth(row.payday, row.paydayPrev)){
			row.paymentMo = row.paymentMo.add(row.payment);
		} else {
			row.paymentMo = row.payment;
		}

		row.totalInterest = row.totalInterest.add(row.interest);
		row.totalPrincipal = row.totalPrincipal.add(row.principal);
		row.totalPayment = row.totalPayment.add(row.payment);
		
		row.principalPrc = row.principal.multiply(ExcelFunctions.HUNDRED).divide(row.payment, BigDecimal.ROUND_UP);
		row.interestPrc = row.interest.multiply(ExcelFunctions.HUNDRED).divide(row.payment, BigDecimal.ROUND_UP);
		row.totalPrincipalPrc = row.totalPrincipal.multiply(ExcelFunctions.HUNDRED).divide(row.totalPayment, BigDecimal.ROUND_UP);
		row.totalInterestPrc = row.totalInterest.multiply(ExcelFunctions.HUNDRED).divide(row.totalPayment, BigDecimal.ROUND_UP);

		/* next iteration */
		balance = row.balanceOut;
		row.nextBalanceOutYear = Integer.parseInt(UtilDateTime.format(row.paydayNext, "yyyy"));
		row.nextBalanceTerm = (order-1)/amortization.termNumberOfPayments+1;

		return row;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		/* nothing */
	}
	
	final private int calculateNumberOfPaymentsInMonth(Date payday, MortgageAmortizationRow row){
		int payInMo = 1;
		
		for(Date next=row.paydayNext;  
				UtilDateTime.isSameMonth(payday, next); 
				next=UtilMortgageCalendar.getNextPayday(1, amortization.paymentFrequency, next) ){
				payInMo++;
			}
			
		for(Date prev=row.paydayPrev;  
				UtilDateTime.isSameMonth(payday, prev); 
				prev=UtilMortgageCalendar.getNextPayday(-1, amortization.paymentFrequency, prev) ){
				payInMo++;
			}
			
		return payInMo;
	}
}


