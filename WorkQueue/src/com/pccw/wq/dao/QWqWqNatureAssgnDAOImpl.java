package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class QWqWqNatureAssgnDAOImpl extends DaoBaseImpl implements QWqWqNatureAssgnDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4774587602900975200L;
	private OraNumber wqId; // Q_WQ_WQ_NATURE_ASSGN.WQ_ID
	private OraNumber wqBatchId; // Q_WQ_WQ_NATURE_ASSGN.WQ_BATCH_ID
	private String wqType; // Q_WQ_WQ_NATURE_ASSGN.WQ_TYPE
	private String wqSubtype; // Q_WQ_WQ_NATURE_ASSGN.WQ_SUBTYPE
	private OraNumber wqNatureId; // Q_WQ_WQ_NATURE_ASSGN.WQ_NATURE_ID
	private String createBy; // Q_WQ_WQ_NATURE_ASSGN.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // Q_WQ_WQ_NATURE_ASSGN.CREATE_DATE
	private String lastUpdBy; // Q_WQ_WQ_NATURE_ASSGN.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WQ_WQ_NATURE_ASSGN.LAST_UPD_DATE

	public QWqWqNatureAssgnDAOImpl() {
		primaryKeyFields = new String[] { "wqId", "wqBatchId", "wqType",
				"wqSubtype", "wqNatureId" };
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getTableName()
	 */
	@Override
	public String getTableName() {
		return "Q_WQ_WQ_NATURE_ASSGN";
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getWqType()
	 */
	@Override
	public String getWqType() {
		return this.wqType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getWqSubtype()
	 */
	@Override
	public String getWqSubtype() {
		return this.wqSubtype;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getCreateBy()
	 */
	@Override
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getLastUpdBy()
	 */
	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getWqId()
	 */
	@Override
	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getWqBatchId()
	 */
	@Override
	public String getWqBatchId() {
		return this.wqBatchId != null ? this.wqBatchId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getWqNatureId()
	 */
	@Override
	public String getWqNatureId() {
		return this.wqNatureId != null ? this.wqNatureId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getCreateDate()
	 */
	@Override
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getLastUpdDate()
	 */
	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getCreateDateORACLE()
	 */
	@Override
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#getLastUpdDateORACLE()
	 */
	@Override
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setWqType(java.lang.String)
	 */
	@Override
	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setWqSubtype(java.lang.String)
	 */
	@Override
	public void setWqSubtype(String pWqSubtype) {
		this.wqSubtype = pWqSubtype;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setCreateBy(java.lang.String)
	 */
	@Override
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setLastUpdBy(java.lang.String)
	 */
	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setWqId(java.lang.String)
	 */
	@Override
	public void setWqId(String pWqId) {
		this.wqId = new OraNumber(pWqId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setWqBatchId(java.lang.String)
	 */
	@Override
	public void setWqBatchId(String pWqBatchId) {
		this.wqBatchId = new OraNumber(pWqBatchId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setWqNatureId(java.lang.String)
	 */
	@Override
	public void setWqNatureId(String pWqNatureId) {
		this.wqNatureId = new OraNumber(pWqNatureId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setCreateDate(java.lang.String)
	 */
	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWqNatureAssgnDAO#setLastUpdDate(java.lang.String)
	 */
	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
