package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class LtsDsOrderInfoDTO extends ObjectActionBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -980884148378515831L;

	private String orderId = null;      
	private String peLink = null;
	private String afStatus = null;
	private String dsType = null;
	private String dsLocation = null;
	private String coolOff = null;
	private String waiveCloff = null;
	private String qcCallTime = null;
	private String mustQc = null;
	private String mustQcReasonCd = null;
	private String waiveQcCd = null;
	private String waiveQcStatus = null;
	private String nameMismatchStatus = null;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPeLink() {
		return peLink;
	}
	public void setPeLink(String peLink) {
		this.peLink = peLink;
	}
	public String getAfStatus() {
		return afStatus;
	}
	public void setAfStatus(String afStatus) {
		this.afStatus = afStatus;
	}
	public String getDsType() {
		return dsType;
	}
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}
	public String getDsLocation() {
		return dsLocation;
	}
	public void setDsLocation(String dsLocation) {
		this.dsLocation = dsLocation;
	}
	public String getCoolOff() {
		return coolOff;
	}
	public void setCoolOff(String coolOff) {
		this.coolOff = coolOff;
	}
	public String getWaiveCloff() {
		return waiveCloff;
	}
	public void setWaiveCloff(String waiveCloff) {
		this.waiveCloff = waiveCloff;
	}
	public String getQcCallTime() {
		return qcCallTime;
	}
	public void setQcCallTime(String qcCallTime) {
		this.qcCallTime = qcCallTime;
	}
	public String getMustQc() {
		return mustQc;
	}
	public void setMustQc(String mustQc) {
		this.mustQc = mustQc;
	}
	public String getMustQcReasonCd() {
		return mustQcReasonCd;
	}
	public void setMustQcReasonCd(String mustQcReasonCd) {
		this.mustQcReasonCd = mustQcReasonCd;
	}
	public String getWaiveQcCd() {
		return waiveQcCd;
	}
	public void setWaiveQcCd(String waiveQcCd) {
		this.waiveQcCd = waiveQcCd;
	}
	public String getWaiveQcStatus() {
		return waiveQcStatus;
	}
	public void setWaiveQcStatus(String waiveQcStatus) {
		this.waiveQcStatus = waiveQcStatus;
	}
	public String getNameMismatchStatus() {
		return nameMismatchStatus;
	}
	public void setNameMismatchStatus(String nameMismatchStatus) {
		this.nameMismatchStatus = nameMismatchStatus;
	}
}
