package com.bomwebportal.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface BomwebOrderLatestStatusDAO extends DaoBase {

	public abstract String getOrderId();

	public abstract String getSbStatus();

	public abstract String getBomStatus();

	public abstract String getReaCd();

	public abstract String getLastUpdBy();

	public abstract String getLegacyStatus();

	public abstract String getWqType();

	public abstract String getDtlId();

	public abstract String getLastHistDate();

	public abstract String getBomIssueDate();

	public abstract String getLastUpdDate();

	public abstract OraDate getLastHistDateORACLE();

	public abstract OraDate getBomIssueDateORACLE();

	public abstract OraDate getLastUpdDateORACLE();

	public abstract void setOrderId(String pOrderId);

	public abstract void setSbStatus(String pSbStatus);

	public abstract void setBomStatus(String pBomStatus);

	public abstract void setReaCd(String pReaCd);

	public abstract void setLastUpdBy(String pLastUpdBy);

	public abstract void setLegacyStatus(String pLegacyStatus);

	public abstract void setWqType(String pWqType);

	public abstract void setDtlId(String pDtlId);

	public abstract void setLastHistDate(String pLastHistDate);

	public abstract void setBomIssueDate(String pBomIssueDate);

	public abstract void setLastUpdDate(String pLastUpdDate);

}