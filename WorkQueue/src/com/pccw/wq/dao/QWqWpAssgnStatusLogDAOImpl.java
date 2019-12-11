/*
 * Created on Nov 7, 2011
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

public class QWqWpAssgnStatusLogDAOImpl extends DaoBaseImpl implements QWqWpAssgnStatusLogDAO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3423744986724803922L;

	private OraNumber wqWpAssgnId; // Q_WQ_WP_ASSGN_STATUS_LOG.WQ_WP_ASSGN_ID

	private String latestStatusInd; // Q_WQ_WP_ASSGN_STATUS_LOG.LATEST_STAUS_IND

	private String statusCd; // Q_WQ_WP_ASSGN_STATUS_LOG.STATUS_CD

	private String reasonCd; // Q_WQ_WP_ASSGN_STATUS_LOG.REASON_CD

	private String assignee; // Q_WQ_WP_ASSGN_STATUS_LOG.ASSIGNEE

	private String createBy; // Q_WQ_WP_ASSGN_STATUS_LOG.CREATE_BY

	private OraDate createDate = new OraDateCreateDate(); // Q_WQ_WP_ASSGN_STATUS_LOG.CREATE_DATE

	private String lastUpdBy; // Q_WQ_WP_ASSGN_STATUS_LOG.LAST_UPD_BY

	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WQ_WP_ASSGN_STATUS_LOG.LAST_UPD_DATE

	public QWqWpAssgnStatusLogDAOImpl() {
		primaryKeyFields = new String[] {};
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getTableName()
	 */
	public String getTableName() {
		return "Q_WQ_WP_ASSGN_STATUS_LOG";
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getStatusCd()
	 */
	public String getStatusCd() {
		return this.statusCd;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getReasonCd()
	 */
	public String getReasonCd() {
		return this.reasonCd;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getAssignee()
	 */
	public String getAssignee() {
		return this.assignee;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getCreateBy()
	 */
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getLastUpdBy()
	 */
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getWqWpAssgnId()
	 */
	public String getWqWpAssgnId() {
		return this.wqWpAssgnId != null ? this.wqWpAssgnId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getCreateDate()
	 */
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getLastUpdDate()
	 */
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getCreateDateORACLE()
	 */
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#getLastUpdDateORACLE()
	 */
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setStatusCd(java.lang.String)
	 */
	public void setStatusCd(String pStatusCd) {
		this.statusCd = pStatusCd;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setReasonCd(java.lang.String)
	 */
	public void setReasonCd(String pReasonCd) {
		this.reasonCd = pReasonCd;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setAssignee(java.lang.String)
	 */
	public void setAssignee(String pAssignee) {
		this.assignee = pAssignee;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setCreateBy(java.lang.String)
	 */
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setLastUpdBy(java.lang.String)
	 */
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setWqWpAssgnId(java.lang.String)
	 */
	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = new OraNumber(pWqWpAssgnId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setCreateDate(java.lang.String)
	 */
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnStatusLogDAO#setLastUpdDate(java.lang.String)
	 */
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getLatestStatusInd() {
		return latestStatusInd;
	}

	public void setLatestStatusInd(String latestStausInd) {
		this.latestStatusInd = latestStausInd;
	}
}
