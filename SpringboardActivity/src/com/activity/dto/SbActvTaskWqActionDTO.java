package com.activity.dto;

import java.io.Serializable;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.pccw.wq.schema.dto.WorkQueueAttributeDTO;

public class SbActvTaskWqActionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059298041366487458L;
	
	public static final String WQ_ACTION_CREATE = "CREATE";
	
	public static final String WQ_ACTION_CREATE_WITH_DOC = "CREATE_WITH_DOC";
	
	public static final String WQ_ACTION_CHANGE_STATUS = "CHG_STS";
	
	public static final String ACTV_ACTION_CHANGE_TASK_STATUS = "CHG_TASK_STS";
	
	public static final String ACTV_ACTION_CHANGE_TASK_STATUS_BY_TYPE = "CHG_TASK_STS_BY_TYPE";

	public static final String ACTV_ACTION_CHANGE_RELATED_TASK_STATUS = "CHG_REL_TASK_STS";

	private String actvId;
	
	private String taskSeq;
	
	private String relatedTaskSeq;
	
	private String lob;
	
	private String actvChannelId;
	
	private String actvType;
	
	private String taskType;

	private String criteriaChannelId;
	
	private String wqWpAssgnId;
	
	private String actvAction;
	
	private String wqAction;
	
	private String wqType;
	
	private String wqSubType;
	
	private String wqNatureId;
	
	private String url;
	
	private WorkQueueAttributeDTO[] wqAttributes;
	private String wqAttributeString;

	public String getActvId() {
		return this.actvId;
	}

	public void setActvId(String pActvId) {
		this.actvId = pActvId;
	}

	public String getTaskSeq() {
		return this.taskSeq;
	}

	public void setTaskSeq(String pTaskSeq) {
		this.taskSeq = pTaskSeq;
	}

	public String getRelatedTaskSeq() {
		return this.relatedTaskSeq;
	}

	public void setRelatedTaskSeq(String pRelatedTaskSeq) {
		this.relatedTaskSeq = pRelatedTaskSeq;
	}

	public String getLob() {
		return this.lob;
	}

	public void setLob(String pLob) {
		this.lob = pLob;
	}

	public String getActvChannelId() {
		return this.actvChannelId;
	}

	public void setActvChannelId(String pActvChannelId) {
		this.actvChannelId = pActvChannelId;
	}

	public String getActvType() {
		return this.actvType;
	}

	public void setActvType(String pActvType) {
		this.actvType = pActvType;
	}

	public String getTaskType() {
		return this.taskType;
	}

	public void setTaskType(String pTaskType) {
		this.taskType = pTaskType;
	}

	public String getCriteriaChannelId() {
		return this.criteriaChannelId;
	}

	public void setCriteriaChannelId(String pCriteriaChannelId) {
		this.criteriaChannelId = pCriteriaChannelId;
	}

	public String getWqWpAssgnId() {
		return this.wqWpAssgnId;
	}

	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = pWqWpAssgnId;
	}

	public String getWqAction() {
		return this.wqAction;
	}

	public void setWqAction(String pWqAction) {
		this.wqAction = pWqAction;
	}

	public String getWqType() {
		return this.wqType;
	}

	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	public String getWqSubType() {
		return this.wqSubType;
	}

	public void setWqSubType(String pWqSubType) {
		this.wqSubType = pWqSubType;
	}

	public String getWqNatureId() {
		return this.wqNatureId;
	}

	public void setWqNatureId(String pWqNatureId) {
		this.wqNatureId = pWqNatureId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String pUrl) {
		this.url = pUrl;
	}
	
	public void setWqAttributeString(String pWqAttributeString) {
		if (StringUtils.isBlank(pWqAttributeString)) {
			return;
		}
		String[] attbNameValuePairs = pWqAttributeString.split("\n");
		String[] attbNameValuePair = null;
		TreeSet<WorkQueueAttributeDTO> wqAttributeList = new TreeSet<WorkQueueAttributeDTO>();
		WorkQueueAttributeDTO wqAttb = null;
		for (String attbNameValuePairStr : attbNameValuePairs) {
			if (StringUtils.isBlank(attbNameValuePairStr)) {
				continue;
			}
			if (!attbNameValuePairStr.contains("=")) {
				continue;
			}
			attbNameValuePair = attbNameValuePairStr.split("=");
			wqAttb = new WorkQueueAttributeDTO();
			wqAttb.setAttbName(attbNameValuePair[0]);
			if (attbNameValuePair.length > 1){
				wqAttb.setAttbValue(attbNameValuePair[1]);	
			}
			wqAttributeList.add(wqAttb);
		}
		this.wqAttributes = wqAttributeList.toArray(new WorkQueueAttributeDTO[0]);
	}

	public String getWqAttributeString() {
		return wqAttributeString;
	}

	public WorkQueueAttributeDTO[] getWqAttributes() {
		return this.wqAttributes;
	}

	public void setWqAttributes(WorkQueueAttributeDTO[] pWqAttributes) {
		this.wqAttributes = pWqAttributes;
	}
	
	public static String getCompareKey(SbActvTaskWqActionDTO pDto) {
		return pDto.getActvId() + "^" + pDto.getTaskSeq() + "^" + pDto.getWqAction() + "^" + pDto.getWqType() + "^" + pDto.getWqSubType() + "^" + pDto.getWqNatureId();
	}

	public String getActvAction() {
		return this.actvAction;
	}

	public void setActvAction(String pActvAction) {
		this.actvAction = pActvAction;
	}
}