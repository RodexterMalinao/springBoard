package com.activity.dao.dataAccess;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ActivityAttributeDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -7259390433799275248L;
	
	private OraNumber actvId; // SB_ACTV_ATTB.ACTV_ID
	private OraNumber taskSeq; // SB_ACTV_ATTB.TASK_SEQ
	private OraNumber statusSeq; // SB_ACTV_ATTB.STATUS_SEQ
	private String attbType; // SB_ACTV_ATTB.ATTB_TYPE
	private OraNumber attbId; // SB_ACTV_ATTB.ATTB_ID	
	private String attbValue; // SB_ACTV_ATTB.ATTB_VALUE
	private String createBy; // SB_ACTV_ATTB.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // SB_ACTV_ATTB.CREATE_DATE
	private String lastUpdBy; // SB_ACTV_ATTB.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // SB_ACTV_ATTB.LAST_UPD_DATE

	public ActivityAttributeDAO() {
		primaryKeyFields = new String[] {"actvId", "taskSeq", "statusSeq"};
	}

	public String getTableName() {
		return "SB_ACTV_ATTB";
	}

	public String getAttbType() {
		return this.attbType;
	}

	public String getAttbValue() {
		return this.attbValue;
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
	
	public String getAttbId() {
		return this.attbId != null ? this.attbId.toString() : null;
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

	public void setAttbType(String pAttbType) {
		this.attbType = pAttbType;
	}

	public void setAttbValue(String pAttbValue) {
		this.attbValue = pAttbValue;
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
	
	public void setAttbId(String pAttbId) {
		this.attbId = new OraNumber(pAttbId);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
