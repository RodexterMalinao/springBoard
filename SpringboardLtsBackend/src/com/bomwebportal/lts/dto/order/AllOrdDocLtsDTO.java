package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AllOrdDocLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 5779681269447908499L;
	
	private String orderId = null;
	private String docType = null;
	private String seqNum = null;
	private String filePathName = null;
	private String processDate = null;
	private String outdatedInd = null;
	private String serial = null;

	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

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

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
