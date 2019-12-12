package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface QWqWqNatureAssgnDAO extends DaoBase {

	public String getWqType();

	public String getWqSubtype();

	public String getCreateBy();

	public String getLastUpdBy();

	public String getWqId();

	public String getWqBatchId();

	public String getWqNatureId();

	public String getCreateDate();

	public String getLastUpdDate();

	public OraDate getCreateDateORACLE();

	public OraDate getLastUpdDateORACLE();

	public void setWqType(String pWqType);

	public void setWqSubtype(String pWqSubtype);

	public void setCreateBy(String pCreateBy);

	public void setLastUpdBy(String pLastUpdBy);

	public void setWqId(String pWqId);

	public void setWqBatchId(String pWqBatchId);

	public void setWqNatureId(String pWqNatureId);

	public void setCreateDate(String pCreateDate);

	public void setLastUpdDate(String pLastUpdDate);

}