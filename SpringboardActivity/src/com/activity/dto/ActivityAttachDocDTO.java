package com.activity.dto;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ActivityAttachDocDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 4628607575228989136L;
	
	private String docActvId = null;
	private String docType = null;
	private String docSeqNum = null;

	
	public String getDocActvId() {
		return docActvId;
	}

	public void setDocActvId(String docActvId) {
		this.docActvId = docActvId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocSeqNum() {
		return docSeqNum;
	}

	public void setDocSeqNum(String docSeqNum) {
		this.docSeqNum = docSeqNum;
	}
}
