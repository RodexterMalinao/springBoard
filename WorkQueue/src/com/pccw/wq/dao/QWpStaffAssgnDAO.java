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

public interface QWpStaffAssgnDAO extends DaoBase {
	public String getStaffId();

	public String getCreateBy();

	public String getLastUpdBy();

	public String getWpId();

	public String getCreateDate();

	public String getLastUpdDate();

	public OraDate getCreateDateORACLE();

	public OraDate getLastUpdDateORACLE();

	public void setStaffId(String pStaffId);

	public void setCreateBy(String pCreateBy);

	public void setLastUpdBy(String pLastUpdBy);

	public void setWpId(String pWpId);

	public void setCreateDate(String pCreateDate);

	public void setLastUpdDate(String pLastUpdDate);
}
