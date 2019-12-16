package com.bomwebportal.lts.dto.apn;

import java.io.Serializable;

public class LtsApnDnDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7986057238117016409L;

	private String batchSeq; //BOMWEB_PIPB_APN_DTL.BATCH_SEQ
	private String dtlSeq; //BOMWEB_PIPB_APN_DTL.DTL_SEQ
	private String typeOfDoc; //BOMWEB_PIPB_APN_DTL.TYPE_OF_DOC
	private String appDate; //BOMWEB_PIPB_APN_DTL.APP_DATE
	private String batchDate; //BOMWEB_PIPB_APN_DTL.BATCH_DATE
	private String apnSerial; //BOMWEB_PIPB_APN_DTL.APN_SERIAL
	private String srvNum; //BOMWEB_PIPB_APN_DTL.SRV_NUM
	private String srvNn; //BOMWEB_PIPB_APN_DTL.SRV_NN
	private String chgoverStartTime; //BOMWEB_PIPB_APN_DTL.CHGOVER_START_TIME
	private String chgoverEndTime; //BOMWEB_PIPB_APN_DTL.CHGOVER_END_TIME
	private String orderId; //BOMWEB_PIPB_APN_DTL.ORDER_ID
	private String dtlId; //BOMWEB_PIPB_APN_DTL.DTL_ID
	private String isDnMatch; //BOMWEB_PIPB_APN_DTL.IS_DN_MATCH
	private String isNnMatch; //BOMWEB_PIPB_APN_DTL.IS_NN_MATCH
	private String isDateTimeMatch; //BOMWEB_PIPB_APN_DTL.IS_DATE_TIME_MATCH
	private String status; //BOMWEB_PIPB_APN_DTL.STATUS
	private String statusMessage; //BOMWEB_PIPB_APN_DTL.STATUS_MESSAGE
	private String wqFailInd; //BOMWEB_PIPB_APN_DTL.WQ_FAIL_IND
	private String duplexAction; //BOMWEB_PIPB_APN_DTL.DUPLEX_ACTION
	private String wqStatus; //BOMWEB_PIPB_APN_DTL.WQ_STATUS
	
	public String getBatchSeq() {
		return batchSeq;
	}
	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}
	public String getDtlSeq() {
		return dtlSeq;
	}
	public void setDtlSeq(String dtlSeq) {
		this.dtlSeq = dtlSeq;
	}
	public String getTypeOfDoc() {
		return typeOfDoc;
	}
	public void setTypeOfDoc(String typeOfDoc) {
		this.typeOfDoc = typeOfDoc;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	public String getApnSerial() {
		return apnSerial;
	}
	public void setApnSerial(String apnSerial) {
		this.apnSerial = apnSerial;
	}
	public String getSrvNum() {
		return srvNum;
	}
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	public String getSrvNn() {
		return srvNn;
	}
	public void setSrvNn(String srvNn) {
		this.srvNn = srvNn;
	}
	public String getChgoverStartTime() {
		return chgoverStartTime;
	}
	public void setChgoverStartTime(String chgoverStartTime) {
		this.chgoverStartTime = chgoverStartTime;
	}
	public String getChgoverEndTime() {
		return chgoverEndTime;
	}
	public void setChgoverEndTime(String chgoverEndTime) {
		this.chgoverEndTime = chgoverEndTime;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDtlId() {
		return dtlId;
	}
	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}
	public String getIsDnMatch() {
		return isDnMatch;
	}
	public void setIsDnMatch(String isDnMatch) {
		this.isDnMatch = isDnMatch;
	}
	public String getIsNnMatch() {
		return isNnMatch;
	}
	public void setIsNnMatch(String isNnMatch) {
		this.isNnMatch = isNnMatch;
	}
	public String getIsDateTimeMatch() {
		return isDateTimeMatch;
	}
	public void setIsDateTimeMatch(String isDateTimeMatch) {
		this.isDateTimeMatch = isDateTimeMatch;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getWqFailInd() {
		return wqFailInd;
	}
	public void setWqFailInd(String wqFailInd) {
		this.wqFailInd = wqFailInd;
	}
	public String getDuplexAction() {
		return duplexAction;
	}
	public void setDuplexAction(String duplexAction) {
		this.duplexAction = duplexAction;
	}
	public String getWqStatus() {
		return wqStatus;
	}
	public void setWqStatus(String wqStatus) {
		this.wqStatus = wqStatus;
	}
}
