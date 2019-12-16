package com.activity.dao.dataAccess;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ActivityAttachDocDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 713019589888354596L;

	private OraNumber actvId; // SLV_ACTV_ATTACH_DOC.ACTV_ID
	private OraNumber taskSeq; // SLV_ACTV_ATTACH_DOC.TASK_SEQ
	private String docActvId; // SLV_ACTV_ATTACH_DOC.DOC_ACTV_ID
	private String docType; // SLV_ACTV_ATTACH_DOC.DOC_TYPE
	private OraNumber docSeqNum; // SLV_ACTV_ATTACH_DOC.DOC_SEQ_NUM
	private String createBy; // SLV_ACTV_ATTACH_DOC.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // SLV_ACTV_ATTACH_DOC.CREATE_DATE
	private String lastUpdBy; // SLV_ACTV_ATTACH_DOC.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // SLV_ACTV_ATTACH_DOC.LAST_UPD_DATE

	public ActivityAttachDocDAO() {
		primaryKeyFields = new String[] {"actvId", "taskSeq", "docActvId", "docType", "docSeqNum"};
	}

	public String getTableName() {
		return "SB_ACTV_ATTACH_DOC";
	}

	public String getDocActvId() {
		return this.docActvId;
	}

	public String getDocType() {
		return this.docType;
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

	public String getDocSeqNum() {
		return this.docSeqNum != null ? this.docSeqNum.toString() : null;
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

	public void setDocActvId(String pDocActvId) {
		this.docActvId = pDocActvId;
	}

	public void setDocType(String pDocType) {
		this.docType = pDocType;
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

	public void setDocSeqNum(String pDocSeqNum) {
		this.docSeqNum = new OraNumber(pDocSeqNum);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
