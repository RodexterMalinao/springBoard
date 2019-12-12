package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public class QWqAttbDAOImpl extends DaoBaseImpl implements QWqAttbDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1349717559976987693L;
	/**
	 * 
	 */

	private OraNumber wqId; // Q_WQ_ATTB.WQ_ID
	private OraNumberInsertValueFromSelect attbSeq; // Q_WQ_ATTB.ATTB_SEQ
	private OraNumber wqBatchId; // Q_WQ_ATTB.WQ_BATCH_ID
	private String attbName; // Q_WQ_ATTB.ATTB_NAME
	private String attbValue; // Q_WQ_ATTB.ATTB_VALUE
	private String createBy; // Q_WQ_ATTB.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // Q_WQ_ATTB.CREATE_DATE
	private String lastUpdBy; // Q_WQ_ATTB.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WQ_ATTB.LAST_UPD_DATE

	public QWqAttbDAOImpl() {
		primaryKeyFields = new String[] { "wqId", "attbSeq" };
	}

	public String getTableName() {
		return "Q_WQ_ATTB";
	}

	@Override
	public String getCreateBy() {
		return this.createBy;
	}

	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	@Override
	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	@Override
	public String getWqBatchId() {
		return this.wqBatchId != null ? this.wqBatchId.toString() : null;
	}

	@Override
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	@Override
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	@Override
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	@Override
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	@Override
	public void setWqId(String pWqId) {
		this.wqId = new OraNumber(pWqId);
	}

	@Override
	public void setWqBatchId(String pWqBatchId) {
		this.wqBatchId = new OraNumber(pWqBatchId);
	}

	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	@Override
	public String getAttbName() {
		return this.attbName;
	}

	@Override
	public void setAttbName(String pAttbName) {
		this.attbName = pAttbName;
	}

	@Override
	public String getAttbValue() {
		return this.attbValue;
	}

	@Override
	public void setAttbValue(String pAttbValue) {
		this.attbValue = pAttbValue;
	}
	
	@Override
	public String getAttbSeq() {
		return this.attbSeq != null ? this.attbSeq.toString() : null;
	}

	@Override
	public OraNumberInsertValueFromSelect getAttbSeqORACLE() {
		return this.attbSeq;
	}

	@Override
	public void setAttbSeq(String pAttbSeq) {
		this.attbSeq.setValue(pAttbSeq);
	}

	@Override
	public void setAttbSeqORACLE(OraNumberInsertValueFromSelect pAttbSeq) {
		this.attbSeq = pAttbSeq;
	}	
}