package com.smartliving.dto;

import java.io.Serializable;

public class SlvOrderSummaryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3684378068625284968L;

	private String orderId;
	
	private String orderType;
	
	private String applicationDate;
	
	private String signOffDate;
	
	private String service;
	
	private String profileId;

	private String endUserName;
	
	private String contractorName;

	private String orderCreateBy;
	
	private String orderCreateDate;
	
	private String orderStatus;
	
	private String salesStaffId;
	
	private String tcStaffId;
	
	private String refererStaffId;
	
	private String pendingVisitInd;
	
	private String pendingPaymentInd;
	
	private String discardDate;
	
	private String discardBy;
	
	private String slCubeId;
	
	private String cancelBy;
	
	private String cancelDate;
	
	private String relatedOrderId;
	
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public String getApplicationDate() {
		return this.applicationDate;
	}

	public void setApplicationDate(String pApplicationDate) {
		this.applicationDate = pApplicationDate;
	}

	public String getSignOffDate() {
		return this.signOffDate;
	}

	public void setSignOffDate(String pSignOffDate) {
		this.signOffDate = pSignOffDate;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String pService) {
		this.service = pService;
	}

	public String getProfileId() {
		return this.profileId;
	}

	public void setProfileId(String pProfileId) {
		this.profileId = pProfileId;
	}

	public String getEndUserName() {
		return this.endUserName;
	}

	public void setEndUserName(String pEndUserName) {
		this.endUserName = pEndUserName;
	}

	public String getContractorName() {
		return this.contractorName;
	}

	public void setContractorName(String pContractorName) {
		this.contractorName = pContractorName;
	}

	public String getOrderCreateBy() {
		return this.orderCreateBy;
	}

	public void setOrderCreateBy(String pOrderCreateBy) {
		this.orderCreateBy = pOrderCreateBy;
	}

	public String getOrderCreateDate() {
		return this.orderCreateDate;
	}

	public void setOrderCreateDate(String pOrderCreateDate) {
		this.orderCreateDate = pOrderCreateDate;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String pOrderStatus) {
		this.orderStatus = pOrderStatus;
	}

	public String getSalesStaffId() {
		return this.salesStaffId;
	}

	public void setSalesStaffId(String pSalesStaffId) {
		this.salesStaffId = pSalesStaffId;
	}

	public String getTcStaffId() {
		return this.tcStaffId;
	}

	public void setTcStaffId(String pTcStaffId) {
		this.tcStaffId = pTcStaffId;
	}

	public String getRefererStaffId() {
		return this.refererStaffId;
	}

	public void setRefererStaffId(String pRefererStaffId) {
		this.refererStaffId = pRefererStaffId;
	}

	public String getPendingVisitInd() {
		return this.pendingVisitInd;
	}

	public void setPendingVisitInd(String pPendingVisitInd) {
		this.pendingVisitInd = pPendingVisitInd;
	}

	public String getPendingPaymentInd() {
		return this.pendingPaymentInd;
	}

	public void setPendingPaymentInd(String pPendingPaymentInd) {
		this.pendingPaymentInd = pPendingPaymentInd;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String pOrderType) {
		orderType = pOrderType;
	}

	public String getDiscardDate() {
		return this.discardDate;
	}

	public void setDiscardDate(String pDiscardDate) {
		this.discardDate = pDiscardDate;
	}

	public String getDiscardBy() {
		return this.discardBy;
	}

	public void setDiscardBy(String pDiscardBy) {
		this.discardBy = pDiscardBy;
	}

	public String getSlCubeId() {
		return this.slCubeId;
	}

	public void setSlCubeId(String pSlCubeId) {
		this.slCubeId = pSlCubeId;
	}

	public String getCancelBy() {
		return this.cancelBy;
	}

	public void setCancelBy(String pCancelBy) {
		this.cancelBy = pCancelBy;
	}

	public String getCancelDate() {
		return this.cancelDate;
	}

	public void setCancelDate(String pCancelDate) {
		this.cancelDate = pCancelDate;
	}

	public String getRelatedOrderId() {
		return this.relatedOrderId;
	}

	public void setRelatedOrderId(String pRelatedOrderId) {
		this.relatedOrderId = pRelatedOrderId;
	}

}

