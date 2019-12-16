package com.bomwebportal.mobquota.dto;

import java.util.List;

public class QuotaConsumeRequest {

	private String docType;
	private String docNum;
	private String orderType;
	private String orderId;
	
	
	
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
	private String applnDate;
	
	private List<QuotaItem> items;

	private String updatedBy;
	
	private String authBy;
	
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<QuotaItem> getItems() {
		return items;
	}

	public void setItems(List<QuotaItem> items) {
		this.items = items;
	}

	public String getApplnDate() {
		return applnDate;
	}

	public void setApplnDate(String applnDate) {
		this.applnDate = applnDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getAuthBy() {
		return authBy;
	}

	public void setAuthBy(String authBy) {
		this.authBy = authBy;
	}
	
}
