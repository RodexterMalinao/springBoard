package com.bomwebportal.mob.ccs.dto;

import java.math.BigDecimal;
import java.util.Date;

public class UrgentDeliveryReportDTO {


	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
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
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
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
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}
	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNum1() {
		return contactNum1;
	}
	public void setContactNum1(String contactNum1) {
		this.contactNum1 = contactNum1;
	}
	public String getContactNum2() {
		return contactNum2;
	}
	public void setContactNum2(String contactNum2) {
		this.contactNum2 = contactNum2;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public int getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(int batchNo) {
		this.batchNo = batchNo;
	}
	//SQL2
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemSerial() {
		return itemSerial;
	}
	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}
	public String getDoaitemSerial() {
		return doaitemSerial;
	}
	public void setDoaitemSerial(String doaitemSerial) {
		this.doaitemSerial = doaitemSerial;
	}
	public String getYahooCoupon() {
		return yahooCoupon;
	}
	public void setYahooCoupon(String yahooCoupon) {
		this.yahooCoupon = yahooCoupon;
	}
	private String courier;
	private Date processDate;
	private String orderId;
	private String ocid;
	private String msisdn;
	private String custName;
	private String staffId;
	private String deliveryType;
	private Date deliveryDate;
	private String deliveryTimeSlot;
	private String paymentMethod;
	private BigDecimal paymentAmt;
	private String contactName;
	private String contactNum1;
	private String contactNum2;
	private String deliveryAddress;
	private int batchNo;
	
	//SQL2
	private String itemType;
	private String itemCode;
	private String itemDesc;
	private String itemSerial;
	private String doaitemSerial;
	private String yahooCoupon;
	
	private String tngSim;
	private String tngCard;
	
	public String getTngSim() {
		return tngSim;
	}
	public void setTngSim(String tngSim) {
		this.tngSim = tngSim;
	}
	public String getTngCard() {
		return tngCard;
	}
	public void setTngCard(String tngCard) {
		this.tngCard = tngCard;
	}
	
	
	
	

}
