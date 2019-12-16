package com.bomwebportal.lts.dto.form;

import java.io.Serializable;


public class LtsCcOrderSearchFormDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1511719631235030717L;
	
	private String idDocType;
	private String idDocNum;
	
	private String serviceNumber;
	
	private String contactEmail;
	private String orderId;

	private String teamCode;
	private String staffNum;
	
	private String action;
	
	private String startDate;
	private String endDate;

	private String srdStartDate;
	private String srdEndDate;
	
	private String sbOrdStatus;

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNum() {
		return idDocNum;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getStaffNum() {
		return staffNum;
	}

	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSrdStartDate() {
		return srdStartDate;
	}

	public void setSrdStartDate(String srdStartDate) {
		this.srdStartDate = srdStartDate;
	}

	public String getSrdEndDate() {
		return srdEndDate;
	}

	public void setSrdEndDate(String srdEndDate) {
		this.srdEndDate = srdEndDate;
	}

	public String getSbOrdStatus() {
		return sbOrdStatus;
	}

	public void setSbOrdStatus(String sbOrdStatus) {
		this.sbOrdStatus = sbOrdStatus;
	}

}
