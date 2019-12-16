package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class RelatedPcdOrderDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3390889986026965890L;
	
	private String orderId;
	private String docType;
	private String docNum;
	private String applicationDate;
	
	private String serviceType;
	private String bandwidth;

	private String preBookSerial;
	private String installDate;
	private String installContactName;
	private String installContactNum;
	private String installMobileSmsNum;
	private String installTimeSlot;

	private String addressId;
	private String serviceBoundary;
	private String flat;
	private String floor;
	
	private boolean custNotMatch;
	private boolean iaNotMatch;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
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
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getPreBookSerial() {
		return preBookSerial;
	}
	public void setPreBookSerial(String preBookSerial) {
		this.preBookSerial = preBookSerial;
	}
	public String getInstallDate() {
		return installDate;
	}
	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}
	public String getInstallContactName() {
		return installContactName;
	}
	public void setInstallContactName(String installContactName) {
		this.installContactName = installContactName;
	}
	public String getInstallContactNum() {
		return installContactNum;
	}
	public void setInstallContactNum(String installContactNum) {
		this.installContactNum = installContactNum;
	}
	public String getInstallMobileSmsNum() {
		return installMobileSmsNum;
	}
	public void setInstallMobileSmsNum(String installMobileSmsNum) {
		this.installMobileSmsNum = installMobileSmsNum;
	}
	public String getInstallTimeSlot() {
		return installTimeSlot;
	}
	public void setInstallTimeSlot(String installTimeSlot) {
		this.installTimeSlot = installTimeSlot;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getServiceBoundary() {
		return serviceBoundary;
	}
	public void setServiceBoundary(String serviceBoundary) {
		this.serviceBoundary = serviceBoundary;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public boolean isCustNotMatch() {
		return custNotMatch;
	}
	public void setCustNotMatch(boolean custNotMatch) {
		this.custNotMatch = custNotMatch;
	}
	public boolean isIaNotMatch() {
		return iaNotMatch;
	}
	public void setIaNotMatch(boolean iaNotMatch) {
		this.iaNotMatch = iaNotMatch;
	}
	
}
