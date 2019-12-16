package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class PrepayLtsDTO extends ObjectActionBaseDTO {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4398367883683741891L;
	
	
	private String orderId = null; 
	private String prepayType = null;
	private String acctNo = null;
	private String prepayAmt = null; 
	private String mercahntCode = null; 
	private String billType = null; 
	private String shopCode = null; 
	private String tranCode = null; 
	private String paymentStatus = null;
	private String prepayDate = null;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPrepayType() {
		return prepayType;
	}
	public void setPrepayType(String prepayType) {
		this.prepayType = prepayType;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getPrepayAmt() {
		return prepayAmt;
	}
	public void setPrepayAmt(String prepayAmt) {
		this.prepayAmt = prepayAmt;
	}
	public String getMercahntCode() {
		return mercahntCode;
	}
	public void setMercahntCode(String mercahntCode) {
		this.mercahntCode = mercahntCode;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPrepayDate() {
		return prepayDate;
	}
	public void setPrepayDate(String prepayDate) {
		this.prepayDate = prepayDate;
	} 
	
	
}
