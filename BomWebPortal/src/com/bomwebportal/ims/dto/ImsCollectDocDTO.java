package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class ImsCollectDocDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1935746317574409627L;

	String docType = null;
	String docTypeDisplay = null;
	String markDelInd = null;
	String waiveReason = null;
	String waiveReasonDisplay = null;
	String collectedInd = null;
	String faxSerial = null;
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocTypeDisplay() {
		return docTypeDisplay;
	}
	public void setDocTypeDisplay(String docTypeDisplay) {
		this.docTypeDisplay = docTypeDisplay;
	}
	public String getMarkDelInd() {
		return markDelInd;
	}
	public void setMarkDelInd(String markDelInd) {
		this.markDelInd = markDelInd;
	}
	public String getWaiveReason() {
		return waiveReason;
	}
	public void setWaiveReason(String waiveReason) {
		this.waiveReason = waiveReason;
	}
	public String getWaiveReasonDisplay() {
		return waiveReasonDisplay;
	}
	public void setWaiveReasonDisplay(String waiveReasonDisplay) {
		this.waiveReasonDisplay = waiveReasonDisplay;
	}
	public String getCollectedInd() {
		return collectedInd;
	}
	public void setCollectedInd(String collectedInd) {
		this.collectedInd = collectedInd;
	}
	public String getFaxSerial() {
		return faxSerial;
	}
	public void setFaxSerial(String faxSerial) {
		this.faxSerial = faxSerial;
	}
	
	
	
}