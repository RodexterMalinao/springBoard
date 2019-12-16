package com.bomwebportal.mob.ccs.dto;

public class BasketAssoWBasketAttrCustomerTierDTO {
	public String getCustomerTierId() {
		return customerTierId;
	}
	public void setCustomerTierId(String customerTierId) {
		this.customerTierId = customerTierId;
	}
	public String getCustomerTier() {
		return customerTier;
	}
	public void setCustomerTier(String customerTier) {
		this.customerTier = customerTier;
	}
	private String customerTierId;
	private String customerTier;
}
