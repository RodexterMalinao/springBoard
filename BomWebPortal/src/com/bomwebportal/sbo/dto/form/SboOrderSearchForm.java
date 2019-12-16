package com.bomwebportal.sbo.dto.form;

import org.apache.commons.lang.StringUtils;


public class SboOrderSearchForm {
	private String idDocType;
	private String idDocNum;
	
	private String serviceNumberType;
	private String serviceNumber;
	
	private String loginIdPrefix;
	private String loginIdSuffix;
	
	private String contactEmail;
	private String orderId;
	
	private String contactNum;
	
	private String action;
	
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getServiceNumberType() {
		return serviceNumberType;
	}
	public void setServiceNumberType(String serviceNumberType) {
		this.serviceNumberType = serviceNumberType;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getLoginIdPrefix() {
		return loginIdPrefix;
	}
	public void setLoginIdPrefix(String loginIdPrefix) {
		this.loginIdPrefix = loginIdPrefix;
	}
	public String getLoginIdSuffix() {
		return loginIdSuffix;
	}
	public void setLoginIdSuffix(String loginIdSuffix) {
		this.loginIdSuffix = loginIdSuffix;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
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
	
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	
	/////////////////////////////////////////////////////////////////
	
	


	public boolean isMobSearch() {
		if (StringUtils.isEmpty(idDocNum)
				&& StringUtils.isEmpty(contactEmail)
				&& StringUtils.isEmpty(serviceNumber)
				&& StringUtils.isEmpty(orderId)
				&& StringUtils.isEmpty(contactNum)) {
			return false;
		}
		if (StringUtils.isNotEmpty(serviceNumberType) && !"MRT".equals(serviceNumberType)) return false;	
		if (StringUtils.isNotEmpty(orderId) && !(orderId.startsWith("CSBOM") || orderId.startsWith("CSBSM"))) return false;
		return true;
	}
}
