package ca.mss.finance.mc.impl;

import ca.mss.finance.mc.AmortizationType;
import ca.mss.finance.mc.util.UtilFormat;

import java.util.Iterator;


public class MortgageAmortizationIteratorStar implements Iterator<String[]> {
	
	final static public String className = MortgageAmortizationIteratorStar.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private MortgageContext context;
	final private Iterator<MortgageAmortizationRow> iterator;
	
	private String[] row = new String[AmortizationTable.defTitle.length];

	public MortgageAmortizationIteratorStar(AmortizationTable amortization, AmortizationType at) {
		this(amortization, at, 0, 0);
	}

	public MortgageAmortizationIteratorStar(AmortizationTable amortization, AmortizationType at, int year, int term) {
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

		row[AmortizationTable.C_NOP] = Integer.toString(obj.nop);
		row[AmortizationTable.C_BALANCE_IN_DATE] = UtilFormat.format(obj.balanceInDate);
		row[AmortizationTable.C_BALANCE_IN] = UtilFormat.format(obj.balanceIn);
		row[AmortizationTable.C_INTEREST] = UtilFormat.format(obj.interest);
		row[AmortizationTable.C_INTEREST_PRC] = UtilFormat.format(obj.interestPrc);
		row[AmortizationTable.C_PRINCIPAL] = UtilFormat.format(obj.principal);
		row[AmortizationTable.C_PRINCIPAL_PRC] = UtilFormat.format(obj.principalPrc);
		row[AmortizationTable.C_PAYMENT_DATE] = UtilFormat.format(obj.payday);
		
		row[AmortizationTable.C_PAYMENT] = UtilFormat.format(obj.payment);
		row[AmortizationTable.C_PAYMENT_MO] = UtilFormat.format(obj.paymentMo);
		
		row[AmortizationTable.C_PAYMENT_EXTRA] = UtilFormat.format(obj.extraPayment);
		row[AmortizationTable.C_PAYMENT_EXTRA_MO] = UtilFormat.format(obj.extraPaymentMo);
		row[AmortizationTable.C_PAYMENT_EXTRA_YEAR] = UtilFormat.format(obj.extraPaymentYear);
		row[AmortizationTable.C_PAYMENT_EXTRA_PRC] = UtilFormat.format(obj.extraPrc);
		row[AmortizationTable.C_PAYMENT_EXTRA_PRC_MO] = UtilFormat.format(obj.extraPrcMo);
		row[AmortizationTable.C_PAYMENT_EXTRA_PRC_YEAR] = UtilFormat.format(obj.extraPrcYear);
		
		row[AmortizationTable.C_FULL_PRINCIPAL] = UtilFormat.format(obj.fullPrincipal);
		row[AmortizationTable.C_FULL_PRINCIPAL_PRC] = UtilFormat.format(obj.fullPrincipalPrc);
		row[AmortizationTable.C_PAYMENT_FULL] = UtilFormat.format(obj.fullPayment);

		row[AmortizationTable.C_TOTAL_EXTRA_PAYMENT] = UtilFormat.format(obj.totalExtraPayment);
		row[AmortizationTable.C_TOTAL_FULL_PAYMENT] = UtilFormat.format(obj.totalFullPayment);
		row[AmortizationTable.C_TOTAL_EXTRA_PRC] = UtilFormat.format(obj.totalExtraPrc);
		row[AmortizationTable.C_TOTAL_PAYMENT] = UtilFormat.format(obj.totalPayment);
		row[AmortizationTable.C_TOTAL_INTEREST] = UtilFormat.format(obj.totalInterest);
		row[AmortizationTable.C_TOTAL_INTEREST_PRC] = UtilFormat.format(obj.totalInterestPrc);
		row[AmortizationTable.C_TOTAL_PRINCIPAL] = UtilFormat.format(obj.totalPrincipal);
		row[AmortizationTable.C_TOTAL_PRINCIPAL_PRC] = UtilFormat.format(obj.totalPrincipalPrc);

		row[AmortizationTable.C_BALANCE_OUT] = UtilFormat.format(obj.balanceOut);
		row[AmortizationTable.C_BALANCE_OUT_DATE] = UtilFormat.format(obj.balanceOutDate);
		row[AmortizationTable.C_BALANCE_OUT_YEAR] = UtilFormat.format(obj.balanceOutYear);
		row[AmortizationTable.C_BALANCE_OUT_MONTH] = UtilFormat.format(obj.balanceOutMonth);
		row[AmortizationTable.C_BALANCE_OUT_MONTH_YEAR] = UtilFormat.format(obj.balanceOutMonthYear);
		row[AmortizationTable.C_BALANCE_OUT_MONTH_DAY] = UtilFormat.format(obj.balanceOutMonthDay);
		row[AmortizationTable.C_BALANCE_OUT_QUOTER] = UtilFormat.format(obj.balanceOutQuoter);
		row[AmortizationTable.C_BALANCE_TERM] = UtilFormat.format(obj.balanceTerm);

		row[AmortizationTable.C_PAY_IN_MO] = UtilFormat.format(obj.payInMo);
		if( obj.paydayPrev != null )
			row[AmortizationTable.C_PAYMENT_DATE_PREV] = UtilFormat.format(obj.paydayPrev);
		row[AmortizationTable.C_PAYMENT_DATE_NEXT] = UtilFormat.format(obj.paydayNext);
		
		return row;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	final public void remove() {
		/* nothing */
	}
}