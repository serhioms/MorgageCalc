package com.prosperica.mc.impl;

import java.math.MathContext;

import com.prosperica.mc.MortgageType;


public class MortgageSettings {
	final static public String className = MortgageSettings.class.getName();
	final static public long serialVersionUID = className.hashCode();
	final static public int SCALE = 2;
	final static public int SCALE_PRC = 2;
	final static public MathContext MATH_CONTEXT = new MathContext(SCALE);
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