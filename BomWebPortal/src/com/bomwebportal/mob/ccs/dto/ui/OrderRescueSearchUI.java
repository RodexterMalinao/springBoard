package com.bomwebportal.mob.ccs.dto.ui;

public class OrderRescueSearchUI {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getIncidentNo() {
		return incidentNo;
	}
	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	private String orderId;
	private String incidentNo;
	private String msisdn;
}
