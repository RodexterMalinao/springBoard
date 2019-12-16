package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class LtsPrePaymentDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2080191475537158038L;
	
	private String orderId;
	private String prepayType;
	private String acctNo;
	private double prepayAmt;
	private String merchantCode;
	private String billType;
	private String shopCode;
	private String tranCode;
	private String paymentStatus;
	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the prepayType
	 */
	public String getPrepayType() {
		return prepayType;
	}
	/**
	 * @param prepayType the prepayType to set
	 */
	public void setPrepayType(String prepayType) {
		this.prepayType = prepayType;
	}
	/**
	 * @return the acctNo
	 */
	public String getAcctNo() {
		return acctNo;
	}
	/**
	 * @param acctNo the acctNo to set
	 */
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	/**
	 * @return the prepayAmt
	 */
	public double getPrepayAmt() {
		return prepayAmt;
	}
	/**
	 * @param prepayAmt the prepayAmt to set
	 */
	public void setPrepayAmt(double prepayAmt) {
		this.prepayAmt = prepayAmt;
	}
	/**
	 * @return the merchantCode
	 */
	public String getMerchantCode() {
		return merchantCode;
	}
	/**
	 * @param merchantCode the merchantCode to set
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}
	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}
	/**
	 * @return the shopCode
	 */
	public String getShopCode() {
		return shopCode;
	}
	/**
	 * @param shopCode the shopCode to set
	 */
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	/**
	 * @return the tranCode
	 */
	public String getTranCode() {
		return tranCode;
	}
	/**
	 * @param tranCode the tranCode to set
	 */
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}
	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
