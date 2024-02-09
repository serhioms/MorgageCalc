package com.prosperica.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author moskovsk
 * 
 */
public class UtilDateTime {

	final public static String className = UtilDateTime.class.getName();
	final public static long serialVersionUID = className.hashCode();

	final public static String DATE_FORMAT_YYYYMMDD = "yyyy/MM/dd";

	public static final Date parse(String date, String format) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			return df.parse(date);
		} catch (Exception e) {
			throw new RuntimeException("Can not parse date [" + date + "] as [" + format + "]");
		}
	}

	public static final String format(Date date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static final String format(String date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(parse(date, DATE_FORMAT_YYYYMMDD));
	}

	public static final String format(Date date) {
		return format(date, DATE_FORMAT_YYYYMMDD);
	}

	public static final Date getDayStart(Date day, int daysLater, int monthsLater, int yearsLater) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.YEAR, yearsLater);
		cal.add(Calendar.MONTH, monthsLater);
		cal.add(Calendar.DAY_OF_MONTH, daysLater);
		return cal.getTime();
	}

	public static final int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static final int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int getMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	public static final int getSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	public static final int getQuoter(Date date) {
		return getMonth(date)/3+1;
	}

	public static final int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static final int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static final int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	public static final int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	final static public boolean isSameYear(Date d1, Date d2){
		if( d1 !=null && d2 != null )
			return UtilDateTime.getYear(d1) == UtilDateTime.getYear(d2);
		else
			return false;
	}

	final static public boolean isSameQuoter(Date d1, Date d2){
		if( d1 !=null && d2 != null )
			return UtilDateTime.getMonth(d1)/3 == UtilDateTime.getMonth(d2)/3;
		else
			return false;
	}

	final static public boolean isSameMonth(Date d1, Date d2){
		if( d1 !=null && d2 != null )
			return isSameYear(d1, d2) && UtilDateTime.getMonth(d1) == UtilDateTime.getMonth(d2);
		else
			return false;
	}

	final static public boolean isSameWeek(Date d1, Date d2){
		if( d1 !=null && d2 != null )
			return isSameYear(d1, d2) && UtilDateTime.getWeek(d1) == UtilDateTime.getWeek(d2);
		else
			return false;
	}

    public static final Date now() {
        return new Date();
    }

    public static final Date getYearStart(Date date, int daysLater, int monthsLater, int yearsLater) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
        cal.add(Calendar.YEAR, yearsLater);
        cal.add(Calendar.MONTH, monthsLater);
        cal.add(Calendar.DAY_OF_MONTH, daysLater);
        return cal.getTime();
    }

    public static final Date getYearStart(Date date) {
    	return getYearStart(date, 0, 0, 0);
    }

}