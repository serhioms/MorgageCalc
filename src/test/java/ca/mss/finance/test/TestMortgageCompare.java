package ca.mss.finance.test;

import ca.mss.finance.mc.AmortizationType;
import ca.mss.finance.mc.impl.AmortizationTable;
import ca.mss.finance.mc.impl.MortgageContext;

public class TestMortgageCompare extends TestMortgagePayment {

	final static public String className = TestMortgageCompare.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public MortgageContext[] SELECT_MORTGAGES_TO_COMPARE = mca;
	
	/* 	Monthly, Quoterly, Annually, By Term, Full Amortization */
	final static public AmortizationType SELECT_AMORTIZATION_TYPE = AmortizationType.BY_MONTH;
	
	final static public int SELECT_YEAR_OR_ZERO = 2013; /* zero or any year from TestMortgagePayment.START_DATE to end */
	final static public int SELECT_TERM_OR_ZERO = 1; /* 0, 1, 2, 3, 4, 5, ... , TestMortgagePayment.YEAR_TERM*/

	/* Better have just 1 column but you can set here as many as you wish from AmortizationTable.C_*** */
	final static public int[] SELECT_COMPARED_COLUMNS = new int[]{
		AmortizationTable.C_PAYMENT_FULL
	};
	final static public int[] SELECT_COMPARED_COLUMNS_WIDTH = new int[]{10}; /* Must be same size as SELECT_COMPARED_COLUMNS */

	/* Better have 2 columns but you can set here as many as you wish from AmortizationTable.C_*** */
	final static public int[] SELECT_SUMMARY_COLUMNS= new int[]{
		AmortizationTable.C_BALANCE_OUT,
		AmortizationTable.C_TOTAL_FULL_PAYMENT
	};
	final static public int[] SELECT_SUMMARY_COLUMNS_WIDTH= new int[]{10,10};  /* Must be same size as SELECT_SUMMARY_COLUMNS */

	
	final static public String SELECT_COLUMN_SPLITTER = " "; /* Space or | or any other char */
	
	
	public static void main(String[] args) {
/*		
		TextPrinterHelper.printCombineCompare(System.out, 
				SELECT_MORTGAGES_TO_COMPARE, 
				SELECT_AMORTIZATION_TYPE, 
				SELECT_COMPARED_COLUMNS,
				SELECT_COMPARED_COLUMNS_WIDTH,
				SELECT_SUMMARY_COLUMNS,
				SELECT_SUMMARY_COLUMNS_WIDTH,
				SELECT_COLUMN_SPLITTER,
				SELECT_YEAR_OR_ZERO, SELECT_TERM_OR_ZERO
		);
	*/	
	}
	
}


