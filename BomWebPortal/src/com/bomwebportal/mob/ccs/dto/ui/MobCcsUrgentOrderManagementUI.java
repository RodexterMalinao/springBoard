package com.bomwebportal.mob.ccs.dto.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;

public class MobCcsUrgentOrderManagementUI {
	

	private String orderId;
	private String orderStatus;
	private String chackPoint;
	private String reasonCd;
	/**
	 * Set the monthly verify indicator.
	 * False - Payment have to be verified. UI verified check box unchecked 
	 * True - Payment have been verified. UI verified check box checked 
	 */
	private Boolean paymantMonthlyVerifiedInd;
	/**
	 * Set the upfront verify indicator.
	 * False - Payment have to be verified. UI verified check box unchecked 
	 * True - Payment have been verified. UI verified check box checked 
	 */
	private Boolean paymantUpfrontVerifiedInd;
	private Boolean supportDocVerifiedInd;
	private List <MobCcsPaymentTransDTO> paymentTransList;
	private String falloutReasonCd;
	private String actionType; 
	private String createBy;
	private String lastUpdBy;
	private Date createDate;
	private Date lastUpdDate;
	/**
	 * Set system payment upfront indicator
	 * False - Verify check box is checked by user
	 * True - Verify check box is checked by formBackingObject
	 */
	private Boolean sysPayUpFrontInd;
	private String submitErrorPrint;	
	private Boolean orderLockInd;
	
	public Boolean getOrderLockInd() {
		return orderLockInd;
	}
	public void setOrderLockInd(Boolean orderLockInd) {
		this.orderLockInd = orderLockInd;
	}
	public String getSubmitErrorPrint() {
		return submitErrorPrint;
	}
	public void setSubmitErrorPrint(String submitErrorPrint) {
		this.submitErrorPrint = submitErrorPrint;
	}
	public Boolean getSysPayUpFrontInd() {
		return sysPayUpFrontInd;
	}
	public void setSysPayUpFrontInd(Boolean sysPayUpFrontInd) {
		this.sysPayUpFrontInd = sysPayUpFrontInd;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getChackPoint() {
		return chackPoint;
	}
	public void setChackPoint(String chackPoint) {
		this.chackPoint = chackPoint;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	
	/**
	 * @return the paymantMonthlyVerifiedInd
	 */
	public Boolean getPaymantMonthlyVerifiedInd() {
		return paymantMonthlyVerifiedInd;
	}
	/**
	 * @param paymantMonthlyVerifiedInd the paymantMonthlyVerifiedInd to set
	 */
	public void setPaymantMonthlyVerifiedInd(Boolean paymantMonthlyVerifiedInd) {
		this.paymantMonthlyVerifiedInd = paymantMonthlyVerifiedInd;
	}
	/**
	 * @return the paymantUpfrontVerifiedInd
	 */
	public Boolean getPaymantUpfrontVerifiedInd() {
		return paymantUpfrontVerifiedInd;
	}
	/**
	 * @param paymantUpfrontVerifiedInd the paymantUpfrontVerifiedInd to set
	 */
	public void setPaymantUpfrontVerifiedInd(Boolean paymantUpfrontVerifiedInd) {
		this.paymantUpfrontVerifiedInd = paymantUpfrontVerifiedInd;
	}
	
	/**
	 * @return the supportDocVerifiedInd
	 */
	public Boolean getSupportDocVerifiedInd() {
		return supportDocVerifiedInd;
	}
	/**
	 * @param supportDocVerifiedInd the supportDocVerifiedInd to set
	 */
	public void setSupportDocVerifiedInd(Boolean supportDocVerifiedInd) {
		this.supportDocVerifiedInd = supportDocVerifiedInd;
	}
	public List<MobCcsPaymentTransDTO> getPaymentTransList() {
		return paymentTransList;
	}
	public void setPaymentTransList(MobCcsPaymentTransDTO mobCcsPaymentTransDTO) {
		
		if (this.paymentTransList == null) {
			this.paymentTransList = new ArrayList<MobCcsPaymentTransDTO>();
		}
		
		if (mobCcsPaymentTransDTO != null) {
			this.paymentTransList.add(mobCcsPaymentTransDTO);
		}
	}
	public String getFalloutReasonCd() {
		return falloutReasonCd;
	}
	public void setFalloutReasonCd(String falloutReasonCd) {
		this.falloutReasonCd = falloutReasonCd;
	}
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}	
}
