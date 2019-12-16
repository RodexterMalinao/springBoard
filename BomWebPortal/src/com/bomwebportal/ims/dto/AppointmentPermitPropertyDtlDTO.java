package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class AppointmentPermitPropertyDtlDTO implements Serializable{

	/**
	 * 
	 */
	
	private String prodTubtypeCd;
	private String permitLeadDays;
	private String earliestApptDate;
	private String alertMsg;
	private String bmoRemark;
	private int returnValue;
	private int errorCode;
	private String errorMsg;
	
	public String getProdTubtypeCd() {
		return prodTubtypeCd;
	}

	public void setProdTubtypeCd(String prodTubtypeCd) {
		this.prodTubtypeCd = prodTubtypeCd;
	}

	public String getPermitLeadDays() {
		return permitLeadDays;
	}

	public void setPermitLeadDays(String permitLeadDays) {
		this.permitLeadDays = permitLeadDays;
	}

	public String getEarliestApptDate() {
		return earliestApptDate;
	}

	public void setEarliestApptDate(String earliestApptDate) {
		this.earliestApptDate = earliestApptDate;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public String getBmoRemark() {
		return bmoRemark;
	}

	public void setBmoRemark(String bmoRemark) {
		this.bmoRemark = bmoRemark;
	}

	public int getReturnValue() {
		return returnValue;
	}
	
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
