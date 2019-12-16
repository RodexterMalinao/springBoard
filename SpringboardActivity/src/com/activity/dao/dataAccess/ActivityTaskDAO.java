package com.activity.dao.dataAccess;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MI;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ActivityTaskDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -7920494981659055469L;
	
	private OraNumber actvId; // SLV_ACTV_TASK.ACTV_ID
	private OraNumber taskId; // SLV_ACTV_TASK.TASK_ID
	private OraNumber taskSeq; // SLV_ACTV_TASK.TASK_SEQ
	private String taskType; // SLV_ACTV_TASK.TASK_TYPE
	private String assignee; // SLV_ACTV_TASK.ASSIGNEE
	private OraNumber wqWpAssgnId; //SLV_ACTV_TASK.WQ_WP_ASSGN_ID
	private OraNumber oriWqWpAssgnId; //SLV_ACTV_TASK.ORI_WQ_WP_ASSGN_ID
	private OraNumber relatedTaskSeq; // SLV_ACTV_TASK.RELATED_TASK_SEQ
	private String createBy; // SLV_ACTV_TASK.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // SLV_ACTV_TASK.CREATE_DATE
	private String lastUpdBy; // SLV_ACTV_TASK.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // SLV_ACTV_TASK.LAST_UPD_DATE
	private String completedBy; // SB_ACTV_TASK.COMPLETED_BY
	private String completionCd; // SB_ACTV_TASK.COMPLETION_CD
	private String subCompletionCd; // SB_ACTV_TASK.SUB_COMPLETION_CD
	private OraDate completionDate = new OraDateYYYYMMDDHH24MISS(); //// SB_ACTV_TASK.COMPLETION_DATE
	private String cancelledBy; // SB_ACTV_TASK.CANCELLED_BY
	private String cancellationCd; // SB_ACTV_TASK.CANCELLATION_CD
	private OraDate cancellationDate = new OraDateYYYYMMDDHH24MISS(); //// SB_ACTV_TASK.CANCELLATION_DATE
	private String keyA; // SB_ACTV_TASK.KEY_A
	private String keyB; // SB_ACTV_TASK.KEY_B
	private String keyC; // SB_ACTV_TASK.KEY_C
	private String keyD; // SB_ACTV_TASK.KEY_D
	private String keyE; // SB_ACTV_TASK.KEY_E
	private String keyF; // SB_ACTV_TASK.KEY_F
	private String keyG; // SB_ACTV_TASK.KEY_G
	private String keyH; // SB_ACTV_TASK.KEY_H
	private String keyI; // SB_ACTV_TASK.KEY_I
	private String keyJ; // SB_ACTV_TASK.KEY_J	


	public ActivityTaskDAO() {
		primaryKeyFields = new String[] { "actvId", "actvTaskId" };
	}

	public String getTableName() {
		return "SB_ACTV_TASK";
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

	public String getTaskId() {
		return this.taskId != null ? this.taskId.toString() : null;
	}
	
	public String getTaskSeq() {
		return this.taskSeq != null ? this.taskSeq.toString() : null;
	}
	
	public String getRelatedTaskSeq() {
		return this.relatedTaskSeq != null ? this.relatedTaskSeq.toString() : null;
	}
	
	public String getWqWpAssgnId() {
		return this.wqWpAssgnId != null ? this.wqWpAssgnId.toString() : null;
	}
	
	public String getOriWqWpAssgnId() {
		return this.oriWqWpAssgnId != null ? this.oriWqWpAssgnId.toString() : null;
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

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setActvId(String pActvId) {
		this.actvId = new OraNumber(pActvId);
	}

	public void setTaskId(String pTaskId) {
		this.taskId = new OraNumber(pTaskId);
	}
	
	public void setTaskSeq(String pTaskSeq) {
		this.taskSeq = new OraNumber(pTaskSeq);
	}
	
	public void setRelatedTaskSeq(String pRelatedTaskSeq) {
		this.relatedTaskSeq = new OraNumber(pRelatedTaskSeq);
	}
	
	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = new OraNumber(pWqWpAssgnId);
	}
	
	public void setOriWqWpAssgnId(String pOriWqWpAssgnId) {
		this.oriWqWpAssgnId = new OraNumber(pOriWqWpAssgnId);
	}
	
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCompletedBy() {
		return completedBy;
	}

	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}

	public String getCompletionCd() {
		return completionCd;
	}

	public void setCompletionCd(String completionCd) {
		this.completionCd = completionCd;
	}

	public String getSubCompletionCd() {
		return subCompletionCd;
	}

	public void setSubCompletionCd(String subCompletionCd) {
		this.subCompletionCd = subCompletionCd;
	}

	public String getKeyA() {
		return keyA;
	}

	public void setKeyA(String keyA) {
		this.keyA = keyA;
	}

	public String getKeyB() {
		return keyB;
	}

	public void setKeyB(String keyB) {
		this.keyB = keyB;
	}

	public String getKeyC() {
		return keyC;
	}

	public void setKeyC(String keyC) {
		this.keyC = keyC;
	}

	public String getKeyD() {
		return keyD;
	}

	public void setKeyD(String keyD) {
		this.keyD = keyD;
	}

	public String getKeyE() {
		return keyE;
	}

	public void setKeyE(String keyE) {
		this.keyE = keyE;
	}

	public String getKeyF() {
		return keyF;
	}

	public void setKeyF(String keyF) {
		this.keyF = keyF;
	}

	public String getKeyG() {
		return keyG;
	}

	public void setKeyG(String keyG) {
		this.keyG = keyG;
	}

	public String getKeyH() {
		return keyH;
	}

	public void setKeyH(String keyH) {
		this.keyH = keyH;
	}

	public String getKeyI() {
		return keyI;
	}

	public void setKeyI(String keyI) {
		this.keyI = keyI;
	}

	public String getKeyJ() {
		return keyJ;
	}

	public void setKeyJ(String keyJ) {
		this.keyJ = keyJ;
	}

	public void setRelatedTaskSeq(OraNumber relatedTaskSeq) {
		this.relatedTaskSeq = relatedTaskSeq;
	}
	public String getCompletionDate() {
		return this.completionDate != null ? this.completionDate.toString() : null;
	}
	
	public OraDate getCompletionDateORACLE() {
		return this.completionDate;
	}	
	public void setCompletionDate(String pCompletionDate) {
		this.completionDate = new OraDateYYYYMMDDHH24MISS(pCompletionDate);
	}	
	
	public String getCancellationDate() {
		return this.cancellationDate != null ? this.cancellationDate.toString() : null;
	}
	
	public OraDate getCancellationDateORACLE() {
		return this.cancellationDate;
	}	
	public void setCancellationDate(String pCancellationDate) {
		this.cancellationDate = new OraDateYYYYMMDDHH24MISS(pCancellationDate);
	}

	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String pCancelledBy) {
		cancelledBy = pCancelledBy;
	}

	public String getCancellationCd() {
		return cancellationCd;
	}

	public void setCancellationCd(String pCancellationCd) {
		cancellationCd = pCancellationCd;
	}	
	
}
