/*
 * Created on Nov 7, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface QWqWpAssgnStatusLogDAO extends DaoBase {

	public abstract String getTableName();

	public abstract String getStatusCd();

	public abstract String getReasonCd();

	public abstract String getAssignee();

	public abstract String getCreateBy();

	public abstract String getLastUpdBy();

	public abstract String getWqWpAssgnId();

	public abstract String getCreateDate();

	public abstract String getLastUpdDate();

	public abstract OraDate getCreateDateORACLE();

	public abstract OraDate getLastUpdDateORACLE();

	public abstract void setStatusCd(String pStatusCd);

	public abstract void setReasonCd(String pReasonCd);

	public abstract void setAssignee(String pAssignee);

	public abstract void setCreateBy(String pCreateBy);

	public abstract void setLastUpdBy(String pLastUpdBy);

	public abstract void setWqWpAssgnId(String pWqWpAssgnId);

	public abstract void setCreateDate(String pCreateDate);

	public abstract void setLastUpdDate(String pLastUpdDate);
	
	public String getLatestStatusInd();

	public void setLatestStatusInd(String latestStausInd);

}