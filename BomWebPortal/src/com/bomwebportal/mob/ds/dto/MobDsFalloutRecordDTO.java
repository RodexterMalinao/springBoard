package com.bomwebportal.mob.ds.dto;

import java.util.Date;
import java.io.Serializable;

public class MobDsFalloutRecordDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7396764795906983272L;
	private String caseNo;
	private String orderId;
	private String staffId;
	private String falloutStatus;
	private String falloutType;
	private String falloutCatOpt;
	private String remark;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getFalloutStatus() {
		return falloutStatus;
	}
	public void setFalloutStatus(String falloutStatus) {
		this.falloutStatus = falloutStatus;
	}
	public String getFalloutType() {
		return falloutType;
	}
	public void setFalloutType(String falloutType) {
		this.falloutType = falloutType;
	}
	public String getFalloutCatOpt() {
		return falloutCatOpt;
	}
	public void setFalloutCatOpt(String falloutCatOpt) {
		this.falloutCatOpt = falloutCatOpt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	
	
	
}
