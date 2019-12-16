package com.bomwebportal.dto;

import java.io.Serializable;

public class AlertOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3419544965251334921L;

	private String orderId;
	private String shopCode;
	private String orderStatus;
	private String ocid;
	private String applnDate;
	private String errorMessage;
	private String orderSumLob;
	private String orderSumServiceNum;
	private String imsLoginId;
	private String reasonCd;
	private String checkPoint;
	private String orderSumCustName;
	private String bomCreationDate;
	private String disMode;
	private String collectMethod;
	private String dmsInd;
	private String wpDesc;
	private String wqNatureDesc;

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String pShopCode) {
		this.shopCode = pShopCode;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String pOrderStatus) {
		this.orderStatus = pOrderStatus;
	}

	public String getOcid() {
		return this.ocid;
	}

	public void setOcid(String pOcid) {
		this.ocid = pOcid;
	}

	public String getApplnDate() {
		return this.applnDate;
	}

	public void setApplnDate(String pApplnDate) {
		this.applnDate = pApplnDate;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String pErrorMessage) {
		this.errorMessage = pErrorMessage;
	}

	public String getOrderSumLob() {
		return this.orderSumLob;
	}

	public void setOrderSumLob(String pOrderSumLob) {
		this.orderSumLob = pOrderSumLob;
	}

	public String getOrderSumServiceNum() {
		return this.orderSumServiceNum;
	}

	public void setOrderSumServiceNum(String pOrderSumServiceNum) {
		this.orderSumServiceNum = pOrderSumServiceNum;
	}

	public String getImsLoginId() {
		return this.imsLoginId;
	}

	public void setImsLoginId(String pImsLoginId) {
		this.imsLoginId = pImsLoginId;
	}

	public String getReasonCd() {
		return this.reasonCd;
	}

	public void setReasonCd(String pReasonCd) {
		this.reasonCd = pReasonCd;
	}

	public String getCheckPoint() {
		return this.checkPoint;
	}

	public void setCheckPoint(String pCheckPoint) {
		this.checkPoint = pCheckPoint;
	}

	public String getOrderSumCustName() {
		return this.orderSumCustName;
	}

	public void setOrderSumCustName(String pOrderSumCustName) {
		this.orderSumCustName = pOrderSumCustName;
	}

	public String getBomCreationDate() {
		return this.bomCreationDate;
	}

	public void setBomCreationDate(String pBomCreationDate) {
		this.bomCreationDate = pBomCreationDate;
	}

	public String getDisMode() {
		return this.disMode;
	}

	public void setDisMode(String pDisMode) {
		this.disMode = pDisMode;
	}

	public String getCollectMethod() {
		return this.collectMethod;
	}

	public void setCollectMethod(String pCollectMethod) {
		this.collectMethod = pCollectMethod;
	}

	public String getDmsInd() {
		return this.dmsInd;
	}

	public void setDmsInd(String pDmsInd) {
		this.dmsInd = pDmsInd;
	}

	public String getWpDesc() {
		return this.wpDesc;
	}

	public void setWpDesc(String pWpDesc) {
		this.wpDesc = pWpDesc;
	}

	public String getWqNatureDesc() {
		return this.wqNatureDesc;
	}

	public void setWqNatureDesc(String pWqNatureDesc) {
		this.wqNatureDesc = pWqNatureDesc;
	}
}
