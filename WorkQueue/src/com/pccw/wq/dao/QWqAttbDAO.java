package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public interface QWqAttbDAO extends DaoBase {

	public abstract String getCreateBy();

	public abstract String getLastUpdBy();

	public abstract String getWqId();

	public abstract String getWqBatchId();

	public abstract String getCreateDate();

	public abstract String getLastUpdDate();

	public abstract OraDate getCreateDateORACLE();

	public abstract OraDate getLastUpdDateORACLE();

	public abstract void setCreateBy(String pCreateBy);

	public abstract void setLastUpdBy(String pLastUpdBy);

	public abstract void setWqId(String pWqId);

	public abstract void setWqBatchId(String pWqBatchId);

	public abstract void setCreateDate(String pCreateDate);

	public abstract void setLastUpdDate(String pLastUpdDate);

	public abstract String getAttbName();

	public abstract void setAttbName(String pAttbName);

	public abstract String getAttbValue();

	public abstract void setAttbValue(String pAttbValue);

	public abstract String getAttbSeq();

	public abstract OraNumberInsertValueFromSelect getAttbSeqORACLE();

	public abstract void setAttbSeq(String pAttbSeq);

	public abstract void setAttbSeqORACLE(OraNumberInsertValueFromSelect pAttbSeq);
}