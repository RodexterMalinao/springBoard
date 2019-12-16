package com.bomwebportal.sbo.dto;

import java.util.Date;

public class SboMobOrderDTO {
	private String lob;
	private String orderId;
	private String ocid;
	private String idDocType;
	private String idDocNum;
	private String custName;
	//private String lastName;
	//private String firstName;
	private String msisdn;
	private Date appDate;
	private String orderStatus;
	private String esigEmailAddr;
	private Date deliveryDate;
	private String deliveryTimeSlot;
	private Date srvReqDate;
	private String bomOrderStatus;
	private Date actSrvReqDate;
	private String allowCancelInd;
	
	private String busTxnType;
	private String reasonCd;
	private String checkPoint;
	
	
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOcid() {
		return ocid;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
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
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}
	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryTimeSlot() {
		return deliveryTimeSlot;
	}
	public void setDeliveryTimeSlot(String deliveryTimeSlot) {
		this.deliveryTimeSlot = deliveryTimeSlot;
	}
	public Date getSrvReqDate() {
		return srvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}
	public String getBomOrderStatus() {
		return bomOrderStatus;
	}
	public void setBomOrderStatus(String bomOrderStatus) {
		this.bomOrderStatus = bomOrderStatus;
	}
	public Date getActSrvReqDate() {
		return actSrvReqDate;
	}
	public void setActSrvReqDate(Date actSrvReqDate) {
		this.actSrvReqDate = actSrvReqDate;
	}
	public String getAllowCancelInd() {
		return allowCancelInd;
	}
	public void setAllowCancelInd(String allowCancelInd) {
		this.allowCancelInd = allowCancelInd;
	}
	public String getBusTxnType() {
		return busTxnType;
	}
	public void setBusTxnType(String busTxnType) {
		this.busTxnType = busTxnType;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	
	
	
	
}
