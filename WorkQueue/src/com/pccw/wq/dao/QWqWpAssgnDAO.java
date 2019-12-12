/*
 * Created on Nov 4, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface QWqWpAssgnDAO extends DaoBase {

	public abstract String getWqType();

	public abstract String getWqSubtype();

	public abstract String getWqSerial();

	public abstract String getCreateBy();

	public abstract String getLastUpdBy();

	public abstract String getWqWpAssgnId();

	public abstract String getWqId();

	public abstract String getWqBatchId();

	public abstract String getWpId();

	public abstract String getReceiveDate();

	public abstract String getCreateDate();

	public abstract String getLastUpdDate();

	public abstract OraDate getReceiveDateORACLE();

	public abstract OraDate getCreateDateORACLE();

	public abstract OraDate getLastUpdDateORACLE();

	public abstract void setWqType(String pWqType);

	public abstract void setWqSubtype(String pWqSubtype);

	public abstract void setWqSerial(String pWqSerial);

	public abstract void setCreateBy(String pCreateBy);

	public abstract void setLastUpdBy(String pLastUpdBy);

	public abstract void setWqWpAssgnId(String pWqWpAssgnId);

	public abstract void setWqId(String pWqId);

	public abstract void setWqBatchId(String pWqBatchId);

	public abstract void setWpId(String pWpId);

	public abstract void setReceiveDate(String pReceiveDate);

	public abstract void setCreateDate(String pCreateDate);

	public abstract void setLastUpdDate(String pLastUpdDate);

}