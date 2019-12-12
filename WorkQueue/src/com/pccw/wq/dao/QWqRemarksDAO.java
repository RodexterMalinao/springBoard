package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public interface QWqRemarksDAO extends DaoBase {

	public String getTableName();

	public String getRemarks();

	public String getCreateBy();

	public String getLastUpdBy();

	public String getWqId();

	public String getRemarkSeq();
	
	public OraNumberInsertValueFromSelect getRemarkSeqORACLE();

	public String getWqWpAssgnId();

	public String getWqBatchId();

	public String getWqNatureId();

	public String getCreateDate();

	public String getLastUpdDate();

	public OraDate getCreateDateORACLE();

	public OraDate getLastUpdDateORACLE();

	public void setRemarks(String pRemarks);

	public void setCreateBy(String pCreateBy);

	public void setLastUpdBy(String pLastUpdBy);

	public void setWqId(String pWqId);

	public void setRemarkSeq(String pRemarkSeq);

	public void setRemarkSeqORACLE(OraNumberInsertValueFromSelect pRemarkSeq);
		
	public void setWqWpAssgnId(String pWqWpAssignId);

	public void setWqBatchId(String pWqBatchId);

	public void setWqNatureId(String pWqNatureId);

	public void setCreateDate(String pCreateDate);

	public void setLastUpdDate(String pLastUpdDate);

}