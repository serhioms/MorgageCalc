package ca.mss.finance.mc.impl;

import java.math.MathContext;

import ca.mss.finance.mc.MortgageType;


public class MortgageSettings {
	final static public String className = MortgageSettings.class.getName();
	final static public long serialVersionUID = className.hashCode();
	final static public int PRECISION_CRY = 0;
	final static public int SCALE_CRY = 2;
	final static public int SCALE_PRC = 3;
	final static public MathContext MC_CRY = new MathContext(PRECISION_CRY);
	private MortgageType compoundType;

	/**
	 * Canadian mortgage by default
	 */
	public MortgageSettings() {
		this.compoundType = MortgageType.CANADIAN;
	}

	/**
	 * @return the compoundType
	 */
	public final MortgageType getCompoundType() {
		return compoundType;
	}

	/**
	 * @param compoundType the compoundType to set
	 */
	public final void setCompoundType(MortgageType compoundType) {
		this.compoundType = compoundType;
	}
}