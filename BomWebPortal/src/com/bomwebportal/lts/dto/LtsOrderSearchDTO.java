package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Date;

public class LtsOrderSearchDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7258355500316756487L;

	
	private String orderId;
	private String lob;
	private String ocId;
	private String custName;
	private String srvNum;
	private Date applicationDate;
	private String status;
	private String statusReaCd;
	private String errorMsg;
	private String contactEmail; 
	private String idDocType;
	private String idDocNum;
	private String emailTemplateId;
	private String amendSrdUrl;
	private String staffNum;
	private String createChannel;
	private String shopCd;
	private String centerCd;
	private String createBy;
	private boolean allowAmendOrder;
	private boolean allowRecallOrder;
	private boolean allowCancelOrder;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getOcId() {
		return ocId;
	}
	public void setOcId(String ocId) {
		this.ocId = ocId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getSrvNum() {
		return srvNum;
	}
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	public Date getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
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
	public String getEmailTemplateId() {
		return emailTemplateId;
	}
	public void setEmailTemplateId(String emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}
	public String getAmendSrdUrl() {
		return amendSrdUrl;
	}
	public void setAmendSrdUrl(String amendSrdUrl) {
		this.amendSrdUrl = amendSrdUrl;
	}
	public boolean isAllowAmendOrder() {
		return allowAmendOrder;
	}
	public void setAllowAmendOrder(boolean allowAmendOrder) {
		this.allowAmendOrder = allowAmendOrder;
	}
	public String getStaffNum() {
		return staffNum;
	}
	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}
	public String getCreateChannel() {
		return createChannel;
	}
	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public boolean isAllowRecallOrder() {
		return allowRecallOrder;
	}
	public void setAllowRecallOrder(boolean allowRecallOrder) {
		this.allowRecallOrder = allowRecallOrder;
	}
	public boolean isAllowCancelOrder() {
		return allowCancelOrder;
	}
	public void setAllowCancelOrder(boolean allowCancelOrder) {
		this.allowCancelOrder = allowCancelOrder;
	}
	public String getCenterCd() {
		return centerCd;
	}
	public void setCenterCd(String centerCd) {
		this.centerCd = centerCd;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getStatusReaCd() {
		return statusReaCd;
	}
	public void setStatusReaCd(String statusReaCd) {
		this.statusReaCd = statusReaCd;
	}
	
}
