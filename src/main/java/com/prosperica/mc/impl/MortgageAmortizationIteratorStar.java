package com.prosperica.mc.impl;

import java.util.Iterator;

import com.prosperica.mc.AmortizationType;


public class MortgageAmortizationIteratorStar implements Iterator<String[]> {
	
	final static public String className = MortgageAmortizationIteratorStar.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private MortgageContext context;
	final private Iterator<MortgageAmortizationRow> iterator;
	
	private String[] row = new String[MortgageAmortization.defTitle.length];

	public MortgageAmortizationIteratorStar(MortgageAmortization amortization, AmortizationType at) {
		this(amortization, at, 0, 0);
	}

	public MortgageAmortizationIteratorStar(MortgageAmortization amortization, AmortizationType at, int year, int term) {
		this.context = amortization.context;
		this.iterator = amortization.getIterator(at, year, term);
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return iterator.hasNext();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	final public String[] next() {
	
		MortgageAmortizationRow obj = iterator.next();

		row[MortgageAmortization.C_NOP] = Integer.toString(obj.nop);
		row[MortgageAmortization.C_BALANCE_IN_DATE] = context.format(obj.balanceInDate);
		row[MortgageAmortization.C_BALANCE_IN] = context.format(obj.balanceIn);
		row[MortgageAmortization.C_INTEREST] = context.format(obj.interest);
		row[MortgageAmortization.C_INTEREST_PRC] = context.format(obj.interestPrc);
		row[MortgageAmortization.C_PRINCIPAL] = context.format(obj.principal);
		row[MortgageAmortization.C_PRINCIPAL_PRC] = context.format(obj.principalPrc);
		row[MortgageAmortization.C_PAYMENT_DATE] = context.format(obj.payday);
		
		row[MortgageAmortization.C_PAYMENT] = context.format(obj.payment);
		row[MortgageAmortization.C_PAYMENT_MO] = context.format(obj.paymentMo);
		
		row[MortgageAmortization.C_PAYMENT_EXTRA] = context.format(obj.extraPayment);
		row[MortgageAmortization.C_PAYMENT_EXTRA_MO] = context.format(obj.extraPaymentMo);
		row[MortgageAmortization.C_PAYMENT_EXTRA_YEAR] = context.format(obj.extraPaymentYear);
		row[MortgageAmortization.C_PAYMENT_EXTRA_PRC] = context.format(obj.extraPrc);
		row[MortgageAmortization.C_PAYMENT_EXTRA_PRC_MO] = context.format(obj.extraPrcMo);
		row[MortgageAmortization.C_PAYMENT_EXTRA_PRC_YEAR] = context.format(obj.extraPrcYear);
		
		row[MortgageAmortization.C_FULL_PRINCIPAL] = context.format(obj.fullPrincipal);
		row[MortgageAmortization.C_FULL_PRINCIPAL_PRC] = context.format(obj.fullPrincipalPrc);
		row[MortgageAmortization.C_PAYMENT_FULL] = context.format(obj.fullPayment);

		row[MortgageAmortization.C_TOTAL_EXTRA_PAYMENT] = context.format(obj.totalExtraPayment);
		row[MortgageAmortization.C_TOTAL_FULL_PAYMENT] = context.format(obj.totalFullPayment);
		row[MortgageAmortization.C_TOTAL_EXTRA_PRC] = context.format(obj.totalExtraPrc);
		row[MortgageAmortization.C_TOTAL_PAYMENT] = context.format(obj.totalPayment);
		row[MortgageAmortization.C_TOTAL_INTEREST] = context.format(obj.totalInterest);
		row[MortgageAmortization.C_TOTAL_INTEREST_PRC] = context.format(obj.totalInterestPrc);
		row[MortgageAmortization.C_TOTAL_PRINCIPAL] = context.format(obj.totalPrincipal);
		row[MortgageAmortization.C_TOTAL_PRINCIPAL_PRC] = context.format(obj.totalPrincipalPrc);

		row[MortgageAmortization.C_BALANCE_OUT] = context.format(obj.balanceOut);
		row[MortgageAmortization.C_BALANCE_OUT_DATE] = context.format(obj.balanceOutDate);
		row[MortgageAmortization.C_BALANCE_OUT_YEAR] = context.format(obj.balanceOutYear);
		row[MortgageAmortization.C_BALANCE_OUT_MONTH] = context.format(obj.balanceOutMonth);
		row[MortgageAmortization.C_BALANCE_OUT_MONTH_YEAR] = context.format(obj.balanceOutMonthYear);
		row[MortgageAmortization.C_BALANCE_OUT_MONTH_DAY] = context.format(obj.balanceOutMonthDay);
		row[MortgageAmortization.C_BALANCE_OUT_QUOTER] = context.format(obj.balanceOutQuoter);
		row[MortgageAmortization.C_BALANCE_TERM] = context.format(obj.balanceTerm);

		row[MortgageAmortization.C_PAY_IN_MO] = context.format(obj.payInMo);
		if( obj.paydayPrev != null )
			row[MortgageAmortization.C_PAYMENT_DATE_PREV] = context.format(obj.paydayPrev);
		row[MortgageAmortization.C_PAYMENT_DATE_NEXT] = context.format(obj.paydayNext);
		
		return row;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	final public void remove() {
		/* nothing */
	}
}