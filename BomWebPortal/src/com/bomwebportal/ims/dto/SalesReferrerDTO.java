package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class SalesReferrerDTO implements Serializable{
	
	
	private static final long serialVersionUID = 2979291345367175289L;
	
	private String OrderId;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;

	//referral for Call center
	private String referrerAppMethod;
	private String referrerAppMethodDesc;
	private String referrerSourcecode;
	private String referrerStaffNo;
	private String referrerStaffName;
	
	public String getReferrerAppMethod() {
		return referrerAppMethod;
	}

	public void setReferrerAppMethod(String referrerAppMethod) {
		this.referrerAppMethod = referrerAppMethod;
	}

	public String getReferrerAppMethodDesc() {
		return referrerAppMethodDesc;
	}

	public void setReferrerAppMethodDesc(String referrerAppMethodDesc) {
		this.referrerAppMethodDesc = referrerAppMethodDesc;
	}

	public String getReferrerSourcecode() {
		return referrerSourcecode;
	}

	public void setReferrerSourcecode(String referrerSourcecode) {
		this.referrerSourcecode = referrerSourcecode;
	}

	public String getReferrerStaffNo() {
		return referrerStaffNo;
	}

	public void setReferrerStaffNo(String referrerStaffNo) {
		this.referrerStaffNo = referrerStaffNo;
	}

	public String getReferrerStaffName() {
		return referrerStaffName;
	}

	public void setReferrerStaffName(String referrerStaffName) {
		this.referrerStaffName = referrerStaffName;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
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

}
