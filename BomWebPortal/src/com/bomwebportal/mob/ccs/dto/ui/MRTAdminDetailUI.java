package com.bomwebportal.mob.ccs.dto.ui;

public class MRTAdminDetailUI {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	private String rowId;
	private String orderId;
	private String msisdnlvl;
	private String msisdn;
	private String reserveId;
}
