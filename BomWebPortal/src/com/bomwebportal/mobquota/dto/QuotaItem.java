package com.bomwebportal.mobquota.dto;

public class QuotaItem {

	private String quotaId;
	private int quantity;
	private String authBy;
	private String remarks;
	
	public String getQuotaId() {
		return quotaId;
	}
	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getAuthBy() {
		return authBy;
	}
	public void setAuthBy(String authBy) {
		this.authBy = authBy;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
