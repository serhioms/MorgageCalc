package com.prosperica.mc.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

import com.prosperica.mc.AmortizationType;
import com.prosperica.mc.PaymentFrequency;
import com.prosperica.util.UtilMisc;


public class MortgageAmortization {
	
	final static public String className = MortgageAmortization.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public int DEFAULT_COLUMN_WIDTH = 11;

	final static public BigDecimal MINIMUM_PAYMENT = new BigDecimal("10.0");

	final static public int C_NOP = 						0;
	final static public int C_BALANCE_IN_DATE = 			1;
	final static public int C_BALANCE_IN = 					2;
	final static public int C_INTEREST = 					3;
	final static public int C_INTEREST_PRC = 				4;
	final static public int C_PRINCIPAL = 					5;
	final static public int C_PRINCIPAL_PRC = 				6;
	final static public int C_PAYMENT_DATE = 				7;
	final static public int C_PAYMENT = 					8;
	final static public int C_PAYMENT_MO = 					9;
	
	final static public int C_PAYMENT_EXTRA = 				10;
	final static public int C_PAYMENT_EXTRA_MO = 			11;
	final static public int C_PAYMENT_EXTRA_YEAR = 			12;
	
	final static public int C_PAYMENT_EXTRA_PRC = 			13;
	final static public int C_PAYMENT_EXTRA_PRC_MO = 		14;
	final static public int C_PAYMENT_EXTRA_PRC_YEAR = 		15;
	
	final static public int C_FULL_PRINCIPAL = 				16;
	final static public int C_FULL_PRINCIPAL_PRC = 			17;
	final static public int C_PAYMENT_FULL = 				18;
	
	final static public int C_TOTAL_FULL_PAYMENT = 			19;
	final static public int C_TOTAL_EXTRA_PAYMENT = 		20;
	final static public int C_TOTAL_EXTRA_PRC = 			21;
	final static public int C_TOTAL_PAYMENT = 				22;
	final static public int C_TOTAL_INTEREST = 				23;
	final static public int C_TOTAL_INTEREST_PRC = 			24;
	
	final static public int C_TOTAL_PRINCIPAL = 			25;
	final static public int C_TOTAL_PRINCIPAL_PRC = 		26;
	
	final static public int C_BALANCE_OUT = 				27;
	final static public int C_BALANCE_OUT_DATE = 			28;
	final static public int C_BALANCE_OUT_YEAR = 			29;
	final static public int C_BALANCE_OUT_MONTH = 			30;
	final static public int C_BALANCE_OUT_MONTH_YEAR = 		31;
	final static public int C_BALANCE_OUT_MONTH_DAY = 		32;
	final static public int C_BALANCE_OUT_QUOTER = 			33;
	final static public int C_BALANCE_TERM = 				34;

	final static public int C_PAY_IN_MO = 					35;
	final static public int C_PAYMENT_DATE_PREV =			36;
	final static public int C_PAYMENT_DATE_NEXT =			37;

	final static public String[] defTitle = new String[]{
		"NOP",
		"InDay",
		"InBalance",
		"Interest",
		"Intrst,%",
		"Principal",
		"Prncpl,%",
		"Payday",
		"Payment",
		"PaymentMo",
		
		"ExtraPmnt",
		"ExtraMo",
		"ExtraYear",
		
		"Extra,%",
		"ExtraMo,%",
		"ExtraYe,%",
		
		"FullPrncpl",
		"FullPrncpl,%",
		"FullPmnt",
		
		"TtlFullPmnt",
		"TtlExtraPmnt",
		"TtlExtra,%",
		"TtlPayment",
		"TtlInterest",
		"TtlIntrst,%",
		
		"TtlPrincipal",
		"TtlPrncpl,%",
		
		"OutBalance",
		"OutDate",
		"OutYear",
		"OutMonth",
		"OutMonthYear",
		"OutMonthDay",
		"OutQuoter",
		"Term",
		"Pay#",
		"Prev",
		"Next"
		};

	final static public Map<String,String> title = UtilMisc.toMap(
			"NOP", "no",
			"InDay", "Start",
			"InBalance", "Balance",
			"Interest", "Interest",
			"Intrst,%", "Int,%",
			"Principal", "Principal",
			"Prncpl,%", "Pri,%",
			"Payday", "Date",
			"Payment", "Pay",
			"PaymentMo", "PayMo",

			"ExtraPmnt", "Extra",
			"ExtraMo", "ExtrMo",
			"ExtraYear", "ExtrYr",
			
			"Extra,%", "Ex,%",
			"ExtraMo,%", "ExM,%",
			"ExtraYe,%", "ExY,%",
			
			"FullPrncpl", "Principal",
			"FullPrncpl,%", "Pri,%",
			"FullPmnt", "Pay",
			
			"TtlFullPmnt", "Payment",
			"TtlExtraPmnt", "ExtraTTL",
			"TtlExtra,%", "ExTTL,%",
			"TtlPayment", "PaymentTTL",
			"TtlInterest", "InterestTTL",
			"TtlIntrst,%", "IntTTL,%",
			"TtlPrincipal", "PrincipalTTL",
			"TtlPrncpl,%", "PriTTL,%",
			
			"OutBalance", "Balance",
			"OutDate", "Date",
			"OutYear", "Year",
			"OutMonth", "Month",
			"OutMonthYear", "Date",
			"OutMonthDay", "Date",
			"OutQuoter", "Quoter",
			"Term", "Term",
			"Pay#", "P#",
			"Prev", "Prev",
			"Next", "Next"
	);
			
	final static public boolean[] isVisible = new boolean[defTitle.length];
	final static public int[] columnNumber = new int[defTitle.length];

	final public PaymentFrequency paymentFrequency;
	final public int termNumberOfPayments, totalNumberOfPayments; 
	final public MortgageContext context;
	
	public MortgageAmortization(MortgageContext context, PaymentFrequency paymentFrequency) {
		this.context = context;
		this.paymentFrequency = paymentFrequency;
		this.totalNumberOfPayments = context.duration.getPaymentsNo(paymentFrequency);
		this.termNumberOfPayments = MortgageDuration.getPaymentsNo(context.termYears.intValue(), 0, paymentFrequency);
	}

	final static public int getColumnWidth(int order){
		switch( order ){
		case C_NOP:
		case C_BALANCE_TERM:
			return 3;
		case C_BALANCE_IN_DATE:
		case C_BALANCE_OUT_DATE:
		case C_PAYMENT_DATE:
			return 9;
		case C_BALANCE_OUT_YEAR:
			return 5;
		case C_BALANCE_OUT_MONTH:
			return 4;
		case C_BALANCE_OUT_MONTH_YEAR:
		case C_BALANCE_OUT_MONTH_DAY:
		case C_BALANCE_OUT_QUOTER:
			return 6;
		default:
			return DEFAULT_COLUMN_WIDTH;
		}
	}

	final static public int getColumnWidth(){
		return DEFAULT_COLUMN_WIDTH;
	}

	final static public String getColumnTitle(int order){
		return title.get(defTitle[order]);
	}
			
	final public Iterator<String[]> getIteratorStar(AmortizationType at){
		return getIteratorStar(at, 0, 0);
	}

	final public Iterator<String[]> getIteratorStar(AmortizationType at, int year, int term){
		return new MortgageAmortizationIteratorStar(this, at, year, term);
	}

	final public Iterator<MortgageAmortizationRow> getIterator(AmortizationType at, int year, int term){
		return new MortgageAmortizationIterator(this, at, year, term);
	}

	final public Iterator<MortgageAmortizationRow> getIterator(AmortizationType at){
		return getIterator(at, 0, 0);
	}

	static final public void setVisible(int index, boolean b){
		MortgageAmortization.isVisible[index] = b;
	}

	static final public void setColumnNumber(int index, int n){
		MortgageAmortization.columnNumber[index] = n;
		MortgageAmortization.isVisible[index] = true;
	}

	static final public boolean isVisible(int index){
		return MortgageAmortization.isVisible[index];
	}
	
	static final public String columnLabels(int[] column){
		String labels = "";
		
		for(int i=0; i<column.length; i++){
			if( i > 0 )
				labels += ",";
			labels += getColumnTitle(column[i]);
		}
		
		return labels;
	}
	

}


