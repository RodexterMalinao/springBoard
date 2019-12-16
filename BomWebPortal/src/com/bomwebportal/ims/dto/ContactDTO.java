package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class ContactDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1804974812180513511L;
	
	
	private String OrderId;
	private String Title;
	private String ContactName;
	private String ContactPhone;
	private String deliveryPhone;
	private String EmailAddr;
	private String ActionInd;
	private String OtherPhone;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;
	private String ContactType; //eSignature Cr by Eric Ng @ 2012-10-29

	
	
	
	public String getContactType() {
		return ContactType;
	}
	public void setContactType(String contactType) {
		ContactType = contactType;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContactName() {
		return ContactName;
	}
	public void setContactName(String contactName) {
		ContactName = contactName;
	}
	public String getContactPhone() {
		return ContactPhone;
	}
	public void setContactPhone(String contactPhone) {
		ContactPhone = contactPhone;
	}
	public String getEmailAddr() {
		return EmailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		EmailAddr = emailAddr;
	}
	public String getActionInd() {
		return ActionInd;
	}
	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getOtherPhone() {
		return OtherPhone;
	}
	public void setOtherPhone(String otherPhone) {
		OtherPhone = otherPhone;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}


	
}
