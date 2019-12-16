package com.bomwebportal.mob.ccs.dto.ui;

public class UrgentDeliveryReportUI {
	
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String[] getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(String[] orderIds) {
		this.orderIds = orderIds;
	}
	public boolean isExportCsv() {
		return exportCsv;
	}
	public void setExportCsv(boolean exportCsv) {
		this.exportCsv = exportCsv;
	}
	private String processDate;
	private String orderId;
	private String action;
	private String[] orderIds;
	private boolean exportCsv;
}
