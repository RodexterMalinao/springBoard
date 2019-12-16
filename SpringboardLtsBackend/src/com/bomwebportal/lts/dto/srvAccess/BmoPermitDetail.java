package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class BmoPermitDetail implements Serializable {

	private static final long serialVersionUID = 5642733752389658649L;
	
	private String prodSubTypeCd = null;
	private String permitLeadDays = null;
	private String earliestApptDate = null;
	private String alertMsg = null;
	private String bmoRemark = null;

	
	public String getProdSubTypeCd() {
		return this.prodSubTypeCd;
	}

	public void setProdSubTypeCd(String pProdSubTypeCd) {
		this.prodSubTypeCd = pProdSubTypeCd;
	}

	public String getPermitLeadDays() {
		return this.permitLeadDays;
	}

	public void setPermitLeadDays(String pPermitLeadDays) {
		this.permitLeadDays = pPermitLeadDays;
	}

	public String getEarliestApptDate() {
		return this.earliestApptDate;
	}

	public void setEarliestApptDate(String pEarliestApptDate) {
		this.earliestApptDate = pEarliestApptDate;
	}

	public String getAlertMsg() {
		return this.alertMsg;
	}

	public void setAlertMsg(String pAlertMsg) {
		this.alertMsg = pAlertMsg;
	}

	public String getBmoRemark() {
		return this.bmoRemark;
	}

	public void setBmoRemark(String pBmoRemark) {
		this.bmoRemark = pBmoRemark;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" prodSubTypeCd = ");
		sb.append(this.prodSubTypeCd);
		sb.append(" permitLeadDays = ");
		sb.append(this.permitLeadDays);
		sb.append(" earliestApptDate = ");
		sb.append(this.earliestApptDate);
		sb.append(" alertMsg = ");
		sb.append(this.alertMsg);
		sb.append(" bmoRemark = ");
		sb.append(this.bmoRemark);
		return sb.toString();
	}
}
