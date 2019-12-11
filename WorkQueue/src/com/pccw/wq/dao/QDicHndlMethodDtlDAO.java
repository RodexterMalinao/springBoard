package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface QDicHndlMethodDtlDAO extends DaoBase {

	public String getAttbName();

	public String getAttbValue();

	public String getCreateBy();

	public String getLastUpdBy();

	public String getHandleMethodId();

	public String getCreateDate();

	public String getLastUpdDate();

	public OraDate getCreateDateORACLE();

	public OraDate getLastUpdDateORACLE();

	public void setAttbName(String pAttbName);

	public void setAttbValue(String pAttbValue);

	public void setCreateBy(String pCreateBy);

	public void setLastUpdBy(String pLastUpdBy);

	public void setHandleMethodId(String pHandleMethodId);

	public void setCreateDate(String pCreateDate);

	public void setLastUpdDate(String pLastUpdDate);

}