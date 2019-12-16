package com.activity.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.dto.StatusDTO;

public class ActivityTaskDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 9163102969031917650L;
	
	private String taskId = null;
	private String taskSeq = null;
	private String taskType = null;
	private String assignee = null;
	private String wqWpAssgnId = null;
	private String oriWqWpAssgnId = null;
	private String relatedTaskSeq = null;
	private String completedBy = null; 
	private String completionCd = null; 
	private String subCompletionCd = null;
	private String completionDate = null;
	private String cancelledBy = null;
	private String cancellationCd = null;
	private String cancellationDate = null;
	protected String keyA = null;
	protected String keyB = null;
	protected String keyC = null;
	protected String keyD = null;
	protected String keyE = null;
	protected String keyF = null;
	protected String keyG = null;
	protected String keyH = null;
	protected String keyI = null;
	protected String keyJ = null;
	
	private ActivityAttachDocDTO[] attachDocs = null;
	private List<RemarkDTO> taskRemarkList = null;
	private List<DocumentDTO> documentList = null;
	private List<ActivityStatusDTO> statusList = null;
	private List<ActivityAttributeDTO> taskAttbList = null;
	

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskSeq() {
		return taskSeq;
	}

	public void setTaskSeq(String taskSeq) {
		this.taskSeq = taskSeq;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	public ActivityStatusDTO getTaskStatus() {
		return (this.statusList == null || this.statusList.size() == 0) ? null : this.statusList.get(0);
	}

	public StatusDTO[] getStatusHistory() {
		return (this.statusList == null || this.statusList.size() == 0) ? null : this.statusList.toArray(new StatusDTO[this.statusList.size()]);
	}

	public void setStatusHistory(ActivityStatusDTO[] statusHistory) {
		this.statusList = new ArrayList<ActivityStatusDTO>();
		this.statusList.addAll(Arrays.asList(statusHistory));
	}
	
	public void addLatestStatus(ActivityStatusDTO status) {
		
		if (this.statusList == null) {
			this.statusList = new ArrayList<ActivityStatusDTO>(1);
		}
		this.statusList.add(status);
	}
	
	public boolean isPendingTask() {
		
		ActivityStatusDTO status = this.getTaskStatus();
		
		return status == null || status.isPendingStatus();
	}

	public String getWqWpAssgnId() {
		return wqWpAssgnId;
	}

	public void setWqWpAssgnId(String wqWpAssgnId) {
		this.wqWpAssgnId = wqWpAssgnId;
	}

	public ActivityAttachDocDTO[] getAttachDocs() {
		return attachDocs;
	}

	public void setAttachDocs(ActivityAttachDocDTO[] attachDocs) {
		this.attachDocs = attachDocs;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public RemarkDTO[] getTaskRemarks() {
		
		if (this.taskRemarkList == null || this.taskRemarkList.size() == 0) {
			return null;
		}
		return this.taskRemarkList.toArray(new RemarkDTO[this.taskRemarkList.size()]);
	}
	
	public String getTaskRemarkContent() {
		
		if (this.taskRemarkList == null || this.taskRemarkList.size() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<this.taskRemarkList.size(); ++i) {
			sb.append(this.taskRemarkList.get(i).getRmkDtl());
		}
		return sb.toString();
	}

	public void setTaskRemarks(RemarkDTO[] pRemarks) {
		
		if (ArrayUtils.isEmpty(pRemarks)) {
			return;
		}
		this.taskRemarkList = new ArrayList<RemarkDTO>();
		this.taskRemarkList.addAll(Arrays.asList(pRemarks));
	}
	
	public void appendTaskRemark(RemarkDTO[] pRemarks) {
		
		if (ArrayUtils.isEmpty(pRemarks)) {
			return;
		}
		for (int i=0; i<pRemarks.length; ++i) {
			this.appendTaskRemark(pRemarks[i]);
		}
	}
	
	public void appendTaskRemark(RemarkDTO pRemark) {
		
		int lastIndex = 0;
		
		if (this.taskRemarkList == null) {
			this.taskRemarkList = new ArrayList<RemarkDTO>();
		} else {
			lastIndex = Integer.parseInt(this.taskRemarkList.get(this.taskRemarkList.size()-1).getRmkSeq());
		}
		pRemark.setRmkSeq(String.valueOf(lastIndex+1));
		this.taskRemarkList.add(pRemark);
	}
	
	public ActivityAttributeDTO[] getTaskAttbs() {
		
		if (this.taskAttbList == null || this.taskAttbList.size() == 0) {
			return null;
		}
		return this.taskAttbList.toArray(new ActivityAttributeDTO[this.taskAttbList.size()]);
	}

	public void setTaskAttbs(ActivityAttributeDTO[] pAttbs) {
		
		if (ArrayUtils.isEmpty(pAttbs)) {
			return;
		}
		this.taskAttbList = new ArrayList<ActivityAttributeDTO>();
		this.taskAttbList.addAll(Arrays.asList(pAttbs));
	}
	
	public void appendTaskAttb(ActivityAttributeDTO pAttb) {

		if (this.taskAttbList == null) {
			this.taskAttbList = new ArrayList<ActivityAttributeDTO>();
		}
		this.taskAttbList.add(pAttb);
	}
	
	public ActivityAttributeDTO[] getAttbByType(String pAttbType) {
		
		List<ActivityAttributeDTO> attbList = new ArrayList<ActivityAttributeDTO>();
		ActivityAttributeDTO attb = null;
		
		for (int i=0; this.taskAttbList!=null && i<this.taskAttbList.size(); ++i) {
			attb = this.taskAttbList.get(i);
			if (StringUtils.equals(pAttbType, attb.getAttbType())) {
				attbList.add(attb);
			}
		}
		return attbList.toArray(new ActivityAttributeDTO[attbList.size()]);
	}
	
	public DocumentDTO[] getDocuments() {
		
		if (this.documentList == null || this.documentList.size() == 0) {
			return null;
		}
		return this.documentList.toArray(new DocumentDTO[this.documentList.size()]);
	}

	public void setDocuments(DocumentDTO[] documents) {
		
		if (ArrayUtils.isEmpty(documents)) {
			return;
		}
		this.documentList = new ArrayList<DocumentDTO>();
		this.documentList.addAll(Arrays.asList(documents));
	}
	
	public void appendDocument(DocumentDTO pDocument) {

		if (this.documentList == null) {
			this.documentList = new ArrayList<DocumentDTO>();
		}
		this.documentList.add(pDocument);
	}
	
	public DocumentDTO getDocumentByType(String pDocType) {
		
		if (this.documentList == null) {
			return null;
		}
		for (int i=0; i<this.documentList.size(); ++i) {
			if (StringUtils.equals(this.documentList.get(i).getDocType(), pDocType)) {
				return this.documentList.get(i);
			}
		}
		return null;
	}
	
	public void removeDocument(DocumentDTO pDoc) {
		
		if (this.documentList == null) {
			return;
		}
		this.documentList.remove(pDoc);
	}

	public String getRelatedTaskSeq() {
		return relatedTaskSeq;
	}

	public void setRelatedTaskSeq(String relatedTaskSeq) {
		this.relatedTaskSeq = relatedTaskSeq;
	}
	
	// ++KC 20141203
	public void setRelatedTask(ActivityTaskDTO pRelatedTask){
		if (pRelatedTask == null){
			return;
		}
		this.relatedTaskSeq = pRelatedTask.getTaskSeq();
	}
	// --KC 20141203
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

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public String getOriWqWpAssgnId() {
		return this.oriWqWpAssgnId;
	}

	public void setOriWqWpAssgnId(String pOriWqWpAssgnId) {
		this.oriWqWpAssgnId = pOriWqWpAssgnId;
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

	public String getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(String pCancellationDate) {
		cancellationDate = pCancellationDate;
	}
	
}
