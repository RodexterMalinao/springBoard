/*
 * Created on Mar 5, 2012
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public interface BomwebWqTransDAO  extends DaoBase {

	public abstract String getTableName();

	public abstract String getOrderId();

	public abstract String getWqSubtype();

	public abstract String getWqType();

	public abstract String getWqRemarks();

	public abstract String getLkupKey();

	public abstract String getLkupCache();

	public abstract String getStatus();

	public abstract String getCreateBy();

	public abstract String getLastUpdBy();

	public abstract String getDtlId();

	public abstract String getWqNatureId();

	public abstract String getCreateDate();

	public abstract String getLastUpdDate();

	public abstract OraDate getCreateDateORACLE();

	public abstract OraDateLastUpdDate getLastUpdDateORACLE();

	public abstract void setOrderId(String pOrderId);

	public abstract void setWqSubtype(String pWqSubtype);

	public abstract void setWqType(String pWqType);

	public abstract void setWqRemarks(String pWqRemarks);

	public abstract void setLkupKey(String pLkupKey);

	public abstract void setLkupCache(String pLkupCache);

	public abstract void setStatus(String pStatus);

	public abstract void setCreateBy(String pCreateBy);

	public abstract void setLastUpdBy(String pLastUpdBy);

	public abstract void setDtlId(String pDtlId);

	public abstract void setWqNatureId(String pWqNatureId);

	public abstract void setCreateDate(String pCreateDate);

	public abstract void setLastUpdDate(String pLastUpdDate);

	public abstract String getAction();
	
	public abstract void setAction(String action);
	
	public abstract String getShopCd();

	public abstract void setShopCd(String shopCd);

	public abstract String getUserId();

	public abstract void setUserId(String userId);
	
	public abstract String getStandardRemarks();

	public abstract void setStandardRemarks(String standardRemarks);
	
	public abstract String getHostIp();

	public abstract void setHostIp(String hostIp);	
	
	public abstract String getMsgLog();

	public abstract void setMsgLog(String msgLog);		
	
}