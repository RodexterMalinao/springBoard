package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public class QWqRemarksDAOImpl extends DaoBaseImpl implements QWqRemarksDAO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 463761803376892331L;
	private OraNumber wqId; // Q_WQ_REMARKS.WQ_ID
	private OraNumberInsertValueFromSelect remarkSeq; // Q_WQ_REMARKS.REMARK_SEQ
	private OraNumber wqWpAssgnId; // Q_WQ_REMARKS.WQ_WP_ASSIGN_ID
	private OraNumber wqBatchId; // Q_WQ_REMARKS.WQ_BATCH_ID
	private OraNumber wqNatureId; // Q_WQ_REMARKS.WQ_NATURE_ID
	private String remarks; // Q_WQ_REMARKS.REMARKS
	private String createBy; // Q_WQ_REMARKS.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // Q_WQ_REMARKS.CREATE_DATE
	private String lastUpdBy; // Q_WQ_REMARKS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WQ_REMARKS.LAST_UPD_DATE

	public QWqRemarksDAOImpl() {
		primaryKeyFields = new String[] { "wqId", "remarkSeq" };
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getTableName()
	 */
	@Override
	public String getTableName() {
		return "Q_WQ_REMARKS";
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getRemarks()
	 */
	@Override
	public String getRemarks() {
		return this.remarks;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getCreateBy()
	 */
	@Override
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getLastUpdBy()
	 */
	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getWqId()
	 */
	@Override
	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getRemarkSeq()
	 */
	@Override
	public String getRemarkSeq() {
		return this.remarkSeq != null ? this.remarkSeq.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getRemarkSeq()
	 */
	@Override
	public OraNumberInsertValueFromSelect getRemarkSeqORACLE() {
		return this.remarkSeq;
	}
	
	
	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getWqWpAssignId()
	 */
	@Override
	public String getWqWpAssgnId() {
		return this.wqWpAssgnId != null ? this.wqWpAssgnId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getWqBatchId()
	 */
	@Override
	public String getWqBatchId() {
		return this.wqBatchId != null ? this.wqBatchId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getWqNatureId()
	 */
	@Override
	public String getWqNatureId() {
		return this.wqNatureId != null ? this.wqNatureId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getCreateDate()
	 */
	@Override
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getLastUpdDate()
	 */
	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getCreateDateORACLE()
	 */
	@Override
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#getLastUpdDateORACLE()
	 */
	@Override
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setRemarks(java.lang.String)
	 */
	@Override
	public void setRemarks(String pRemarks) {
		this.remarks = pRemarks;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setCreateBy(java.lang.String)
	 */
	@Override
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setLastUpdBy(java.lang.String)
	 */
	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setWqId(java.lang.String)
	 */
	@Override
	public void setWqId(String pWqId) {
		this.wqId = new OraNumber(pWqId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setRemarkSeq(java.lang.String)
	 */
	@Override
	public void setRemarkSeq(String pRemarkSeq) {
		this.remarkSeq.setValue(pRemarkSeq);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setRemarkSeq(java.lang.String)
	 */
	@Override
	public void setRemarkSeqORACLE(OraNumberInsertValueFromSelect pRemarkSeq) {
		this.remarkSeq = pRemarkSeq;
	}	
	
	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setWqWpAssignId(java.lang.String)
	 */
	@Override
	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = new OraNumber(pWqWpAssgnId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setWqBatchId(java.lang.String)
	 */
	@Override
	public void setWqBatchId(String pWqBatchId) {
		this.wqBatchId = new OraNumber(pWqBatchId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setWqNatureId(java.lang.String)
	 */
	@Override
	public void setWqNatureId(String pWqNatureId) {
		this.wqNatureId = new OraNumber(pWqNatureId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setCreateDate(java.lang.String)
	 */
	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqRemarksDAO#setLastUpdDate(java.lang.String)
	 */
	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
