package com.bomwebportal.sbs.dto.form;

import com.bomwebportal.sbs.dto.SbsContactInfoDTO;
import com.bomwebportal.sbs.dto.SbsDeliveryInfoDTO;


public class SbsContactDeliveryForm {
	private String orderId;
	
	private SbsContactInfoDTO contactInfo;
	private SbsDeliveryInfoDTO deliveryInfo;
	
	private String orderSubType;
	
	public SbsContactDeliveryForm() {
		contactInfo = new SbsContactInfoDTO();
		deliveryInfo = new SbsDeliveryInfoDTO();
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public SbsContactInfoDTO getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(SbsContactInfoDTO contactInfo) {
		this.contactInfo = contactInfo;
	}
	public SbsDeliveryInfoDTO getDeliveryInfo() {
		return deliveryInfo;
	}
	public void setDeliveryInfo(SbsDeliveryInfoDTO deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	
	public void setLastUpdBy(String lastUpdBy) {
		if (deliveryInfo != null) {
			deliveryInfo.setLastUpdBy(lastUpdBy);
		}
		if (contactInfo != null) {
			contactInfo.setLastUpdBy(lastUpdBy);
		}
	}
	public String getOrderSubType() {
		return orderSubType;
	}
	public void setOrderSubType(String orderSubType) {
		this.orderSubType = orderSubType;
	}
	

}
