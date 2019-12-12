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
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class QWqWpAssgnDAOImpl extends DaoBaseImpl implements QWqWpAssgnDAO {		
	/**
	 * 
	 */
	private static final long serialVersionUID = 69199523539797840L;

	private OraNumber wqWpAssgnId; // Q_WQ_WP_ASSGN.WQ_WP_ASSGN_ID

	private String wqType; // Q_WQ_WP_ASSGN.WQ_TYPE

	private String wqSubtype; // Q_WQ_WP_ASSGN.WQ_SUBTYPE

	private String wqSerial; // Q_WQ_WP_ASSGN.WQ_SERIAL

	private OraNumber wqId; // Q_WQ_WP_ASSGN.WQ_ID

	private OraNumber wqBatchId; // Q_WQ_WP_ASSGN.WQ_BATCH_ID

	private OraNumber wpId; // Q_WQ_WP_ASSGN.WP_ID

	private OraDate receiveDate = new OraDateYYYYMMDDHH24MISS(); // Q_WQ_WP_ASSGN.RECEIVE_DATE

	private String createBy; // Q_WQ_WP_ASSGN.CREATE_BY

	private OraDate createDate = new OraDateCreateDate(); // Q_WQ_WP_ASSGN.CREATE_DATE

	private String lastUpdBy; // Q_WQ_WP_ASSGN.LAST_UPD_BY

	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WQ_WP_ASSGN.LAST_UPD_DATE

	public QWqWpAssgnDAOImpl() {
		primaryKeyFields = new String[] { "wqWpAssgnId" };
	}

	public String getTableName() {
		return "Q_WQ_WP_ASSGN";
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getWqType()
	 */
	public String getWqType() {
		return this.wqType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getWqSubtype()
	 */
	public String getWqSubtype() {
		return this.wqSubtype;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getWqSerial()
	 */
	public String getWqSerial() {
		return this.wqSerial;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getCreateBy()
	 */
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getLastUpdBy()
	 */
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getWqWpAssgnId()
	 */
	public String getWqWpAssgnId() {
		return this.wqWpAssgnId != null ? this.wqWpAssgnId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getWqId()
	 */
	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getWqBatchId()
	 */
	public String getWqBatchId() {
		return this.wqBatchId != null ? this.wqBatchId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getWpId()
	 */
	public String getWpId() {
		return this.wpId != null ? this.wpId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getReceiveDate()
	 */
	public String getReceiveDate() {
		return this.receiveDate != null ? this.receiveDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getCreateDate()
	 */
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getLastUpdDate()
	 */
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getReceiveDateORACLE()
	 */
	public OraDate getReceiveDateORACLE() {
		return this.receiveDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getCreateDateORACLE()
	 */
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#getLastUpdDateORACLE()
	 */
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setWqType(java.lang.String)
	 */
	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setWqSubtype(java.lang.String)
	 */
	public void setWqSubtype(String pWqSubtype) {
		this.wqSubtype = pWqSubtype;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setWqSerial(java.lang.String)
	 */
	public void setWqSerial(String pWqSerial) {
		this.wqSerial = pWqSerial;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setCreateBy(java.lang.String)
	 */
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setLastUpdBy(java.lang.String)
	 */
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setWqWpAssgnId(java.lang.String)
	 */
	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = new OraNumber(pWqWpAssgnId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setWqId(java.lang.String)
	 */
	public void setWqId(String pWqId) {
		this.wqId = new OraNumber(pWqId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setWqBatchId(java.lang.String)
	 */
	public void setWqBatchId(String pWqBatchId) {
		this.wqBatchId = new OraNumber(pWqBatchId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setWpId(java.lang.String)
	 */
	public void setWpId(String pWpId) {
		this.wpId = new OraNumber(pWpId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setReceiveDate(java.lang.String)
	 */
	public void setReceiveDate(String pReceiveDate) {
		this.receiveDate = new OraDateYYYYMMDDHH24MISS(pReceiveDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setCreateDate(java.lang.String)
	 */
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpAssgnDAO#setLastUpdDate(java.lang.String)
	 */
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
