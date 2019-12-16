package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class LtsPendingSbOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5975724678167647821L;
	
	private String sbOrderId;
	private String bomOcId;
	private String appDate;
	private String sbStatusCd;
	private String sbStatusDesc;
	private String erInd;
	private String edfRefNum;
	private String appntStartTime;
	private String appntEndTime;
	private String fieldVisitInd;
	private String orderType;
	
	public String getSbOrderId() {
		return sbOrderId;
	}
	public void setSbOrderId(String sbOrderId) {
		this.sbOrderId = sbOrderId;
	}
	public String getBomOcId() {
		return bomOcId;
	}
	public void setBomOcId(String bomOcId) {
		this.bomOcId = bomOcId;
	}
	public String getSbStatusCd() {
		return sbStatusCd;
	}
	public void setSbStatusCd(String sbStatusCd) {
		this.sbStatusCd = sbStatusCd;
	}
	public String getSbStatusDesc() {
		return sbStatusDesc;
	}
	public void setSbStatusDesc(String sbStatusDesc) {
		this.sbStatusDesc = sbStatusDesc;
	}
	public String getErInd() {
		return erInd;
	}
	public void setErInd(String erInd) {
		this.erInd = erInd;
	}
	public String getEdfRefNum() {
		return edfRefNum;
	}
	public void setEdfRefNum(String edfRefNum) {
		this.edfRefNum = edfRefNum;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getAppntStartTime() {
		return appntStartTime;
	}
	public void setAppntStartTime(String appntStartTime) {
		this.appntStartTime = appntStartTime;
	}
	public String getAppntEndTime() {
		return appntEndTime;
	}
	public void setAppntEndTime(String appntEndTime) {
		this.appntEndTime = appntEndTime;
	}
	public String getFieldVisitInd() {
		return fieldVisitInd;
	}
	public void setFieldVisitInd(String fieldVisitInd) {
		this.fieldVisitInd = fieldVisitInd;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
