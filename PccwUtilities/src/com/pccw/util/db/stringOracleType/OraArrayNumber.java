package com.pccw.util.db.stringOracleType;

import java.util.ArrayList;

public class OraArrayNumber extends OraArray {

	/**
	 * 
	 */
	private static final long serialVersionUID = 792961763050628215L;
	private Object[] values;
	
	public OraArrayNumber(String pOracleType, String[] pValues) {
		super(pOracleType);
		this.setValues(pValues);
	}

	public OraArrayNumber(String pOracleType, int[] pValues) {
		super(pOracleType);
		this.setValues(pValues);
	}

	public OraArrayNumber(String pOracleType, float[] pValues) {
		super(pOracleType);
		this.setValues(pValues);
	}

	public OraArrayNumber(String pOracleType, Float[] pValues) {
		super(pOracleType);
		this.setValues(pValues);
	}

	public OraArrayNumber(String pOracleType, OraNumber[] pValues) {
		super(pOracleType);
		this.setValues(pValues);
	}
	
	@Override
	public Object[] getValue() {
		return this.values;
	}

	public void setValues(String[] pValues) {
		if (pValues == null) {
			this.values = null;
			return;
		}
		this.values = pValues;
	}
	
	public void setValues(OraNumber[] pValues) {
		if (pValues == null) {
			this.values = null;
			return;
		}
		ArrayList<String> valueList = new ArrayList<String>();
		for (OraNumber value : pValues) {
			valueList.add(value.getValue());
		}
		this.values = valueList.toArray(new String[0]);
	}
	
	public void setValues(int[] pValues) {
		if (pValues == null) {
			this.values = null;
			return;
		}
		ArrayList<String> valueList = new ArrayList<String>();
		for (int value : pValues) {
			valueList.add(String.valueOf(value));
		}
		this.values = valueList.toArray(new String[0]);
	}

	public void setValues(float[] pValues) {
		if (pValues == null) {
			this.values = null;
			return;
		}
		ArrayList<String> valueList = new ArrayList<String>();
		for (float value : pValues) {
			valueList.add(String.valueOf(value));
		}
		this.values = valueList.toArray(new String[0]);
	}

	public void setValues(Float[] pValues) {
		if (pValues == null) {
			this.values = null;
			return;
		}
		ArrayList<String> valueList = new ArrayList<String>();
		for (float value : pValues) {
			valueList.add(String.valueOf(value));
		}
		this.values = valueList.toArray(new String[0]);
	}
}