package ca.mss.finance.mc;

public enum AmortizationType {

	BY_PAYMENT("By Payment"),
	BY_MONTH("Monthly"),
	BY_QUOTER("Quoterly"),
	BY_YEAR("Annually"),
	BY_TERM("By Term"),
	BY_AMORTIZATION("Full Amortization");

	
	final public String title;
	
	private AmortizationType(String title){
		this.title = title;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return title;
	}
	
	public String toString(int year, int term) {
		return title + (year==0?"":" [year="+year+"]") + (term==0?"":" [term="+term+"]");
	}
	
}


