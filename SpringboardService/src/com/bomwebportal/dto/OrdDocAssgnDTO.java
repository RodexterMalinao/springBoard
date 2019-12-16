package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;

public class OrdDocAssgnDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String orderId;
	private String docType;
	private String waiveReason;
	private String waivedBy;
	private String waiveDesc;
	private String collectedInd;

	private String docName;
	private String docNameChi;
	
	private String lastUpdBy;
	private Date lastUpdDate;
	
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
	public String getWaiveReason() {
		return waiveReason;
	}
	public void setWaiveReason(String waiveReason) {
		this.waiveReason = waiveReason;
	}
	public String getWaivedBy() {
		return waivedBy;
	}
	public void setWaivedBy(String waivedBy) {
		this.waivedBy = waivedBy;
	}
	public String getWaiveDesc() {
		return waiveDesc;
	}
	public void setWaiveDesc(String waiveDesc) {
		this.waiveDesc = waiveDesc;
	}
	public String getCollectedInd() {
		return collectedInd;
	}
	public void setCollectedInd(String collectedInd) {
		this.collectedInd = collectedInd;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocNameChi() {
		return docNameChi;
	}
	public void setDocNameChi(String docNameChi) {
		this.docNameChi = docNameChi;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
}
