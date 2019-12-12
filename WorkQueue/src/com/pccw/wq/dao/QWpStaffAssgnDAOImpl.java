/*
 * Created on Nov 4, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class QWpStaffAssgnDAOImpl extends DaoBaseImpl implements QWpStaffAssgnDAO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516567722305362789L;

	private OraNumber wpId; // Q_WP_STAFF_ASSGN.WP_ID

	private String staffId; // Q_WP_STAFF_ASSGN.STAFF_ID

	private String createBy; // Q_WP_STAFF_ASSGN.CREATE_BY

	private OraDate createDate = new OraDateCreateDate(); // Q_WP_STAFF_ASSGN.CREATE_DATE

	private String lastUpdBy; // Q_WP_STAFF_ASSGN.LAST_UPD_BY

	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WP_STAFF_ASSGN.LAST_UPD_DATE

	public QWpStaffAssgnDAOImpl() {
		primaryKeyFields = new String[] { "wpId", "staffId" };
	}

	public String getTableName() {
		return "Q_WP_STAFF_ASSGN";
	}

	public String getStaffId() {
		return this.staffId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getWpId() {
		return this.wpId != null ? this.wpId.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setStaffId(String pStaffId) {
		this.staffId = pStaffId;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setWpId(String pWpId) {
		this.wpId = new OraNumber(pWpId);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
