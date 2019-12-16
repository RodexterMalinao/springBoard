package com.bomwebportal.dto;

public class EligibilityDTO implements java.io.Serializable{
	private String idDocNum;
	private String idDocType;
	private String customerTierId;
	private String customerTierDesc;
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getCustomerTierId() {
		return customerTierId;
	}
	public void setCustomerTierId(String customerTierId) {
		this.customerTierId = customerTierId;
	}
	public String getCustomerTierDesc() {
		return customerTierDesc;
	}
	public void setCustomerTierDesc(String customerTierDesc) {
		this.customerTierDesc = customerTierDesc;
	}
}
