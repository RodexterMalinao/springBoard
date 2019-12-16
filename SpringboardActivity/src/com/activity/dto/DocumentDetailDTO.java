package com.activity.dto;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class DocumentDetailDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 1105930045722413296L;

	private String docType = null;
	private String seqNum = null;
	private String filePathName = null;
	private String processDate = null;
	private String outdatedInd = null;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public String getFilePathName() {
		return filePathName;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getOutdatedInd() {
		return outdatedInd;
	}

	public void setOutdatedInd(String outdatedInd) {
		this.outdatedInd = outdatedInd;
	}
}
