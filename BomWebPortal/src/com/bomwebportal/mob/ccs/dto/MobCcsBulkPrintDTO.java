package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class MobCcsBulkPrintDTO {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOcId() {
		return ocId;
	}
	public void setOcId(String ocId) {
		this.ocId = ocId;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getCustFirstName() {
		return custFirstName;
	}
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	public String getCustTitle() {
		return custTitle;
	}
	public void setCustTitle(String custTitle) {
		this.custTitle = custTitle;
	}
	public String getCustBillLang() {
		return custBillLang;
	}
	public void setCustBillLang(String custBillLang) {
		this.custBillLang = custBillLang;
	}
	public String getCustSmsLang() {
		return custSmsLang;
	}
	public void setCustSmsLang(String custSmsLang) {
		this.custSmsLang = custSmsLang;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getSrvReqDate() {
		return srvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public String getUrgentInd() {
		return urgentInd;
	}
	public void setUrgentInd(String urgentInd) {
		this.urgentInd = urgentInd;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public String getCheckPointDesc() {
		return checkPointDesc;
	}
	public void setCheckPointDesc(String checkPointDesc) {
		this.checkPointDesc = checkPointDesc;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public boolean isSalesFalloutFlag() {
		return salesFalloutFlag;
	}
	public void setSalesFalloutFlag(boolean salesFalloutFlag) {
		this.salesFalloutFlag = salesFalloutFlag;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	private String orderId;
	private String ocId;
	private String lob;
	private String custFirstName;
	private String custLastName;
	private String custTitle;
	private String custBillLang;
	private String custSmsLang;
	private String msisdn;
	private Date srvReqDate;
	private Date deliveryDate;
	private Date processDate;
	private String urgentInd;
	private String orderStatus;
	private String orderStatusDesc;
	private String orderType;
	private String shopCd;
	
	private String checkPoint;
	private String checkPointDesc;
	
	private String reasonCd;
	private boolean salesFalloutFlag;
	private String location;
}
