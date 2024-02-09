package com.prosperica.mc;

import com.prosperica.util.UtilMisc;

public enum ExtraPaymentOrder {

	BEFORE_PAYMENTS("FirstDay", "f"),
	AFTER_PAYMENTS("Last Day", "l");

	final public String title;
	final public String ident;
	
	private ExtraPaymentOrder(String title, String ident){
		this.title = title;
		this.ident = ident;
	}

	public static ExtraPaymentOrder getByIdent(String ident) {
		if( !UtilMisc.isEmpty(ident) )
			for(ExtraPaymentOrder v : values())
				if( v.ident.equalsIgnoreCase(ident) )
					return v;
		return null;
	}
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return title;
	}
}


