package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class ContactDTO implements java.io.Serializable {

	/**
	 * BOMWEB_CONTACT table DTO
	 */
	private static final long serialVersionUID = -6448901954025074075L;

	private String orderId;
	private String title;
	private String contactName;
	private String contactPhone;
	private String otherPhone;
	private String emailAddr;
	private String actionInd;
	private Date createDate;

	private String createBy;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String contactType;
	private String lockInd;
	
	private String idDocType;// table need to add 2011-11-10
	private String idDocNum;// table need to add 2011-11-10

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getActionInd() {
		return actionInd;
	}

	public void setActionInd(String actionInd) {
		this.actionInd = actionInd;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOtherPhone() {
		return otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
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

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getLockInd() {
		return lockInd;
	}

	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}

	

}
