package com.pccw.util.db.stringOracleType;

import java.sql.Date;
import java.util.ArrayList;

public class OraArrayDate extends OraArray {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6543943547308984678L;
	private Object[] values;
	
	public OraArrayDate(String pOracleType, OraDate[] pValues) throws Exception {
		super(pOracleType);
		this.setValues(pValues);
	}
	
	@Override
	public Object[] getValue() {
		return this.values;
	}

	public void setValues(OraDate[] pValues) throws Exception {
		if (pValues == null) {
			this.values = null;
			return;
		}
		ArrayList<Date> valueList = new ArrayList<Date>();
		for (OraDate value : pValues) {
			valueList.add(value.toSqlDate());
		}
		this.values = valueList.toArray(new Date[0]);
	}
}