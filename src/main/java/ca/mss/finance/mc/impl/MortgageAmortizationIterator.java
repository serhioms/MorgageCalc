package ca.mss.finance.mc.impl;

import java.math.BigDecimal;

import ca.mss.finance.excel.ExcelFunctions;
import ca.mss.finance.util.UtilDateTime;
import ca.mss.finance.mc.AmortizationType;


public class MortgageAmortizationIterator extends MortgageAmortizationIteratorFiltered {
	
	final static public String className = MortgageAmortizationIterator.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final private AmortizationType at;

	private int order;
	private MortgageAmortizationRow row;
	private MortgageAmortizationRow payrow;

	public MortgageAmortizationIterator(AmortizationTable amortization, AmortizationType at) {
		this(amortization, at, 0, 0);
	}

	public MortgageAmortizationIterator(AmortizationTable amortization, AmortizationType at, int year, int term) {
		super(amortization, year, term);
		this.at = at;
		this.order = 1;
		this.row = new MortgageAmortizationRow(amortization);
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public MortgageAmortizationRow next() {

		payrow = super.next();
		
		row.nop = order++;
		
		row.paydayPrev = payrow.paydayPrev;
		row.payday = payrow.payday;
		row.paydayNext = payrow.paydayNext;

		row.balanceIn = payrow.balanceIn;
		row.balanceInDate = payrow.balanceInDate;
		
		row.interest = payrow.interest;
		row.principal = payrow.principal;
		row.payment = payrow.payment;
		
		row.extraPayment = payrow.extraPayment;
		row.extraPaymentMo = payrow.extraPaymentMo;
		row.extraPaymentYear = payrow.extraPaymentYear;
		row.fullPayment = payrow.fullPayment;

		while( isNextPaymentSamePeriod() ) {

			if( super.hasNext() )
				payrow = super.next();
			else
				break;
			
			/* For proper calculation */
			row.interest = row.interest.add(payrow.interest);
			row.principal =  row.principal.add(payrow.principal);
			row.payment =  row.payment.add(payrow.payment);
			row.extraPayment =  row.extraPayment.add(payrow.extraPayment);
			row.fullPayment =  row.fullPayment.add(payrow.fullPayment);
			
		}

		row.paymentMo = payrow.paymentMo;
		row.extraPaymentMo = payrow.extraPaymentMo; 
		row.extraPaymentYear = payrow.extraPaymentYear; 
		row.extraPrcMo = payrow.extraPrcMo;
		row.extraPrcYear = payrow.extraPrcYear;

		row.principalPrc = row.principal.multiply(ExcelFunctions.HUNDRED).divide(row.fullPayment, BigDecimal.ROUND_UP);
		row.interestPrc = row.interest.multiply(ExcelFunctions.HUNDRED).divide(row.fullPayment, BigDecimal.ROUND_UP);
		row.extraPrc = row.extraPayment.multiply(ExcelFunctions.HUNDRED).divide(row.fullPayment, BigDecimal.ROUND_UP);
		
		row.totalInterest = payrow.totalInterest;
		row.totalPrincipal = payrow.totalPrincipal;
		row.totalPayment = payrow.totalPayment;
		row.totalExtraPayment = payrow.totalExtraPayment;
		row.totalExtraPrc = payrow.totalExtraPrc;
		row.totalFullPayment = payrow.totalFullPayment;
		row.totalPrincipalPrc = payrow.totalPrincipalPrc;
		row.totalInterestPrc = payrow.totalInterestPrc;

		row.balanceOut = payrow.balanceOut;
		row.balanceOutDate = payrow.balanceOutDate;
		
		row.balanceOutMonth = payrow.balanceOutMonth;
		row.balanceOutMonthYear = payrow.balanceOutMonthYear;
		row.balanceOutMonthDay = payrow.balanceOutMonthDay;
		row.balanceOutQuoter = payrow.balanceOutQuoter;
		row.balanceOutYear = payrow.balanceOutYear;
		row.nextBalanceOutYear = payrow.nextBalanceOutYear;
		row.balanceTerm = payrow.balanceTerm;
		row.nextBalanceTerm = payrow.nextBalanceTerm;

		row.fullPrincipal =  row.principal.add(row.extraPayment); 
		row.fullPrincipalPrc =  row.principalPrc.add(row.extraPrc); 

		row.payInMo = payrow.payInMo; 
		
		return row;
		
	}

	
	final private boolean isNextPaymentSamePeriod(){
			switch( at ){
			case BY_PAYMENT:
				return false;
			case BY_MONTH:
				return UtilDateTime.isSameMonth(payrow.paydayNext, payrow.payday);
			case BY_QUOTER:
				return UtilDateTime.isSameQuoter(payrow.paydayNext, payrow.payday);  
			case BY_YEAR:
				return UtilDateTime.isSameYear(payrow.paydayNext, payrow.payday);  
			case BY_TERM:
				return payrow.balanceTerm == payrow.nextBalanceTerm;  
			case BY_AMORTIZATION:
				return true;  
			default:
				throw new RuntimeException("Unexpected amortization type ["+at+"]");
			}
	}
	

}


