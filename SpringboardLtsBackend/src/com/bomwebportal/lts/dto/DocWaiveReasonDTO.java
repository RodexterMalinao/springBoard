package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class DocWaiveReasonDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8050775206568574689L;
	
	private String docType;
	private String waiveReasonCd;
	private String waiveReasonDesc;
	private String defaultWaiveInd;
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getWaiveReasonCd() {
		return waiveReasonCd;
	}
	public void setWaiveReasonCd(String waiveReasonCd) {
		this.waiveReasonCd = waiveReasonCd;
	}
	public String getWaiveReasonDesc() {
		return waiveReasonDesc;
	}
	public void setWaiveReasonDesc(String waiveReasonDesc) {
		this.waiveReasonDesc = waiveReasonDesc;
	}
	public String getDefaultWaiveInd() {
		return defaultWaiveInd;
	}
	public void setDefaultWaiveInd(String defaultWaiveInd) {
		this.defaultWaiveInd = defaultWaiveInd;
	}
}
