package com.pccw.util.db.stringOracleType;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.OracleSelectHelper;

public class OraNumberInsertValueFromSelectSeq extends OraNumberInsertValueFromSelect {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6855112569416919702L;
	
	private String sequenceName;

	public OraNumberInsertValueFromSelectSeq(String pValue, String pSequenceName) {
		super(pValue);
		this.sequenceName = pSequenceName;
	}
	
	@Override
	public String getInsertValue(DaoBaseImpl pDao) throws Exception {
		return OracleSelectHelper.getSqlFirstRowColumnString(
				pDao.getDataSource(), 
				(new StringBuilder("SELECT TO_CHAR(").append(this.sequenceName).append(".nextval) FROM DUAL")).toString());
	}
}