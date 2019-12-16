package com.bomltsportal.dto.form;

import java.io.Serializable;

import com.bomltsportal.dto.BuildingMarkerDTO;

public class SalesLeadFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3647066472815800283L;
	private String installAddress;
	private String service;
	private String title;
	private String name;
	private String contactNum;
	private String emailAddr;
	private String serviceDN;
	private String reason;
	private String premier;
	private String wip;
	private BuildingMarkerDTO markerDTO;
	private String s_flat;
	private String s_floor;
	private String marker_idx;
	private boolean submitted;
	//Promotion and contact message
	private String promotionMsg;
	private String contactMsg;
	private String housingType;
	//Promotion and contact message end
	
	public String getInstallAddress() {
		return installAddress;
	}
	public void setInstallAddress(String installAddress) {
		this.installAddress = installAddress;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getServiceDN() {	
		return serviceDN;
	}	
	public void setServiceDN(String serviceDN) {	
		this.serviceDN = serviceDN;
	}	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPremier() {
		return premier;
	}
	public void setPremier(String premier) {
		this.premier = premier;
	}
	public String getWip() {
		return wip;
	}
	public void setWip(String wip) {
		this.wip = wip;
	}
	public BuildingMarkerDTO getMarkerDTO() {
		return markerDTO;
	}
	public void setMarkerDTO(BuildingMarkerDTO markerDTO) {
		this.markerDTO = markerDTO;
	}
	
	public boolean isSubmitted() {
		return submitted;
	}
	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}
	public String getS_flat() {
		return s_flat;
	}
	public void setS_flat(String s_flat) {
		this.s_flat = s_flat;
	}
	public String getS_floor() {
		return s_floor;
	}
	public void setS_floor(String s_floor) {
		this.s_floor = s_floor;
	}
	public String getMarker_idx() {
		return marker_idx;
	}
	public void setMarker_idx(String marker_idx) {
		this.marker_idx = marker_idx;
	}

	//Promotion and contact message
	public String getPromotionMsg() {
		return promotionMsg;
	}
	public void setPromotionMsg(String promotionMsg) {
		this.promotionMsg = promotionMsg;
	}
	
	public String getContactMsg() {
		return contactMsg;
	}
	public void setContactMsg(String contactMsg) {
		this.contactMsg = contactMsg;
	}
	
	public String getHousingType() {
		return housingType;
	}
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	//promotion and contact message(End)
	
	
}
