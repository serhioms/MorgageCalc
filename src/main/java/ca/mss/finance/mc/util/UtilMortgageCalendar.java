package ca.mss.finance.mc.util;

import java.util.Date;

import ca.mss.finance.util.UtilDateTime;
import ca.mss.finance.mc.PaymentFrequency;


public class UtilMortgageCalendar {
	
	final static public String className = UtilMortgageCalendar.class.getName();
	final static public long serialVersionUID = className.hashCode();

	final static public int SEMIMONTHLY_DAYS = 15;
	final static public int BI_WEEKLE_DAYS = 14;
	final static public int WEEKLE_DAYS = 7;

	
	final static public Date getNextPayday(int hawManyPayments, PaymentFrequency frequency, Date baseDate){
		switch( frequency ){
		case MONTHLY:
			return UtilDateTime.getDayStart(baseDate, 0, hawManyPayments, 0);
		case SEMI_MONTHLY:
			return UtilDateTime.getDayStart(baseDate, hawManyPayments*SEMIMONTHLY_DAYS, 0, 0);
		case BI_WEEKLY:
		case ACCELERATED_BI_WEEKLY:
			return UtilDateTime.getDayStart(baseDate, hawManyPayments*BI_WEEKLE_DAYS, 0, 0);
		case WEEKLY:
		case ACCELERATED_WEEKLY:
			return UtilDateTime.getDayStart(baseDate, hawManyPayments*WEEKLE_DAYS, 0, 0);
		default:
			throw new RuntimeException("Can not calculate next payday for [payment#="+hawManyPayments+"][baseDate="+UtilDateTime.format(baseDate)+"][frequency="+frequency+"]");
		}
	}

	final static public Date getPrevPayday(int hawManyPayments, PaymentFrequency frequency, Date baseDate){
		return getNextPayday(-hawManyPayments, frequency, baseDate);
	}

}


