package com.bomwebportal.dto;

import java.util.Date;

public class ChannalBasketDTO/* extends BasketDTO*/ {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5524235176374548989L;
	String channelId;
	String channelBasketStatus; //Y= active, N, inactive, call function getChannelBasketStatus() to get this value
	String customerTier;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelBasketStatus() {
		return channelBasketStatus;
	}
	public void setChannelBasketStatus(String channelBasketStatus) {
		this.channelBasketStatus = channelBasketStatus;
	}
	public String getCustomerTier() {
		return customerTier;
	}
	public void setCustomerTier(String customerTier) {
		this.customerTier = customerTier;
	}
	
	

}
