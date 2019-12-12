/*
 * Created on Nov 10, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.dao;

import com.pccw.util.db.OracleSelectHelper;

public class SequencerDAOImpl implements SequencerDAO { 
	private String sequenceName;
		
	public SequencerDAOImpl(String pSequenceName) {
		super();
		sequenceName = pSequenceName;
	}

	@Override
	public String getSequenceName() {
		return sequenceName;
	}
	
	@Override	
	public String getNextVal() throws Exception {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("SELECT TO_CHAR(").append(this.getSequenceName()).append(
				".nextval) FROM dual");
		return execGetVal(sqlSb.toString());
	}
	
	@Override	
	public String getCurrVal() throws Exception {
	    StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("SELECT TO_CHAR(").append(this.getSequenceName()).append(
				".currval) FROM dual");	    
		return execGetVal(sqlSb.toString());
	}
	
	private String execGetVal(String pSql) throws Exception {
		return OracleSelectHelper.getSqlFirstRowColumnString("WorkQueueDS", 
				pSql,null);
   	
	}

	
}
