package com.activity.dao.dataAccess;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public class ActivityRemarkDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 3194451413307788064L;

	private OraNumber actvId; // SL_ACTV_REMARK.ACTV_ID
	private OraNumber taskSeq; // SL_ACTV_REMARK.TASK_SEQ
	private OraNumber statusSeq; // SL_ACTV_REMARK.STATUS_SEQ
	private OraNumberInsertValueFromSelect rmkSeq; // SL_ACTV_REMARK.RMK_SEQ
	private String rmkDtl; // SL_ACTV_REMARK.RMK_DTL
	private String wqWpAssgnId; // SL_ACTV_REMARK.WQ_WP_ASSGN_ID
	private String createBy; // SL_ACTV_REMARK.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // SL_ACTV_REMARK.CREATE_DATE
	private String lastUpdBy; // SL_ACTV_REMARK.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // SL_ACTV_REMARK.LAST_UPD_DATE

	public ActivityRemarkDAO() {
		primaryKeyFields = new String[] {"actvId", "taskSeq", "statusSeq", "rmkSeq"};
	}

	public String getTableName() {
		return "SB_ACTV_REMARK";
	}

	public String getRmkDtl() {
		return this.rmkDtl;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getActvId() {
		return this.actvId != null ? this.actvId.toString() : null;
	}
	
	public String getTaskSeq() {
		return this.taskSeq != null ? this.taskSeq.toString() : null;
	}
	
	public String getStatusSeq() {
		return this.statusSeq != null ? this.statusSeq.toString() : null;
	}

	public String getRmkSeq() {
		return this.rmkSeq != null ? this.rmkSeq.toString() : null;
	}
	
	public OraNumberInsertValueFromSelect getRmkSeqORACLE() {
		return this.rmkSeq;
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

	public void setRmkDtl(String pRmkDtl) {
		this.rmkDtl = pRmkDtl;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setActvId(String pActvId) {
		this.actvId = new OraNumber(pActvId);
	}
	
	public void setTaskSeq(String pTaskSeq) {
		this.taskSeq = new OraNumber(pTaskSeq);
	}
	
	public void setStatusSeq(String pStatusSeq) {
		this.statusSeq = new OraNumber(pStatusSeq);
	}

	public void setRmkSeq(String pRemarkSeq) {
		this.rmkSeq.setValue(pRemarkSeq);
	}
	
	public void setRmkSeqORACLE(OraNumberInsertValueFromSelect pRemarkSeq) {
		this.rmkSeq = pRemarkSeq;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getWqWpAssgnId() {
		return wqWpAssgnId;
	}

	public void setWqWpAssgnId(String pWqWpAssgnId) {
		wqWpAssgnId = pWqWpAssgnId;
	}

}
