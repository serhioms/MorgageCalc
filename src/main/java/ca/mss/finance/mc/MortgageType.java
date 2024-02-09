package ca.mss.finance.mc;

import java.util.Locale;


public enum MortgageType {
	
	US("US", Locale.US, 12),
	CANADIAN("Canadian", Locale.CANADA, 2);

	public final String name;
	public final Locale locale;
	public final int conversionPeriods;

	private MortgageType(String name, Locale locale, int conversionPeriods) {
		this.name = name;
		this.locale = locale;
		this.conversionPeriods = conversionPeriods;
	}
	
	public static MortgageType getByName(String name) {
		final MortgageType[] types = values();
		for(MortgageType type : types) {
			if( type.getName().equalsIgnoreCase(name) ) {
				return type;
			}
		}
		
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public int getConversionPeriods() {
		return conversionPeriods;
	}
	
	public Locale getLocale() {
		return locale;
	}
}