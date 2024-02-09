package ca.mss.finance.mc;

import java.math.BigDecimal;

public enum PaymentFrequency {

	MONTHLY("Monthly", "Mo", 12, 12, 1),
	SEMI_MONTHLY("Semi-monthly", "sMo", 24, 24, 2),
	BI_WEEKLY("Bi-weekly", "BiW", 26, 26, 2),
	WEEKLY("Weekly", "Wi", 52, 52, 4),
	ACCELERATED_BI_WEEKLY("Accelerated-bi-weekly", "AccBiW", 26, 12, 2),
	ACCELERATED_WEEKLY("Accelerated-weekly", "AccWi", 52, 12, 4);
	
	
	final public int paymentsPerYear;
	final public int paymentsPerYearEffective;
	final public BigDecimal paymentsPerMonth;
	final public String title;
	final public String shortTitle;
	
	private PaymentFrequency(String title, String shortTitle, int paymentsPerYear, int paymentsPerYearEffective, int paymentsPerMonth){
		this.title = title;
		this.shortTitle = shortTitle;
		this.paymentsPerYear = paymentsPerYear;
		this.paymentsPerYearEffective = paymentsPerYearEffective;
		this.paymentsPerMonth = new BigDecimal(paymentsPerMonth);
	}

	public static PaymentFrequency getFrequencyByName(String name) {
		final PaymentFrequency[] frc = values();
		for(PaymentFrequency fr : frc) {
			if( fr.getTitle().equalsIgnoreCase(name) ) {
				return fr;
			}
		}
		
		return null;
	}

	public String getTitle() {
		return title;
	}
	
	public int getPaymentsPerYear() {
		return paymentsPerYear;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return title;
	}
}


