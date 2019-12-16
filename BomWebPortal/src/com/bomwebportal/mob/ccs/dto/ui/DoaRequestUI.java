package com.bomwebportal.mob.ccs.dto.ui;

import java.util.Date;

public class DoaRequestUI {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getDoaType() {
		return doaType;
	}
	public void setDoaType(String doaType) {
		this.doaType = doaType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMktSerialId() {
		return mktSerialId;
	}
	public void setMktSerialId(String mktSerialId) {
		this.mktSerialId = mktSerialId;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String[] getReasons() {
		return reasons;
	}
	public void setReasons(String[] reasons) {
		this.reasons = reasons;
	}
	public String[] getStocks() {
		return stocks;
	}
	public void setStocks(String[] stocks) {
		this.stocks = stocks;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public boolean isApproveByManager() {
		return approveByManager;
	}
	public void setApproveByManager(boolean approveByManager) {
		this.approveByManager = approveByManager;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	private String orderId;
	private int seqNo;
	private String doaType;
	private String status;
	private String contactName;
	private String msisdn;
	private String mktSerialId;
	private Date deliveryDate;
	private Date requestDate;
	private String remarks;
	private String[] reasons;
	private String[] stocks;
	private String rowId;
	
	private String orderStatus;
	private String checkPoint;
	private String reasonCd;
	private boolean approveByManager;
	private boolean approved;
}
