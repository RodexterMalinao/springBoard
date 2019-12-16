package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class CsiCaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4906493816993967822L;
	
	public String caseNo;	
	public String orderId;
	public String followUpType;
	public String caseStatus;
	public Date nextCallSchDate;
	public String nextCallSchDateStr;
	public boolean onsiteVisitInd = false;
	public String smsCount;
	public String contactCount;
	public String remark;
	public String createBy;
	public Date createDate;
	public String lastUpdBy;
	public Date lastUpdDate;
	
	public Date OVICreateDate; //onsiteVisitInd
	public String onsiteVisitResult;
	public String falloutReportDateStr;
	public Date falloutReportDate;
	public String falloutReportTSlot;
	
	public String nextCallSchTime;
	
	public boolean followUpTypeisLock = true;
	
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
	public String getFollowUpType() {
		return followUpType;
	}
	public void setFollowUpType(String followUpType) {
		this.followUpType = followUpType;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public Date getNextCallSchDate() {
		return nextCallSchDate;
	}
	public void setNextCallSchDate(Date nextCallSchDate) {
		this.nextCallSchDate = nextCallSchDate;
	}
	public boolean getOnsiteVisitInd() {
		return onsiteVisitInd;
	}
	public void setOnsiteVisitInd(boolean onsiteVisitInd) {
		this.onsiteVisitInd = onsiteVisitInd;
	}
	public String getSmsCount() {
		return smsCount;
	}
	public void setSmsCount(String smsCount) {
		this.smsCount = smsCount;
	}
	public String getContactCount() {
		return contactCount;
	}
	public void setContactCount(String contactCount) {
		this.contactCount = contactCount;
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
	public String getNextCallSchDateStr() {
		return nextCallSchDateStr;
	}
	public void setNextCallSchDateStr(String nextCallSchDateStr) {
		this.nextCallSchDateStr = nextCallSchDateStr;
	}
	public Date getOVICreateDate() {
		return OVICreateDate;
	}
	public void setOVICreateDate(Date oVICreateDate) {
		OVICreateDate = oVICreateDate;
	}
	public String getOnsiteVisitResult() {
		return onsiteVisitResult;
	}
	public void setOnsiteVisitResult(String onsiteVisitResult) {
		this.onsiteVisitResult = onsiteVisitResult;
	}
	public String getFalloutReportDateStr() {
		return falloutReportDateStr;
	}
	public void setFalloutReportDateStr(String falloutReportDateStr) {
		this.falloutReportDateStr = falloutReportDateStr;
	}
	public Date getFalloutReportDate() {
		return falloutReportDate;
	}
	public void setFalloutReportDate(Date falloutReportDate) {
		this.falloutReportDate = falloutReportDate;
	}
	public String getFalloutReportTSlot() {
		return falloutReportTSlot;
	}
	public void setFalloutReportTSlot(String falloutReportTSlot) {
		this.falloutReportTSlot = falloutReportTSlot;
	}
	public String getNextCallSchTime() {
		return nextCallSchTime;
	}
	public void setNextCallSchTime(String nextCallSchTime) {
		this.nextCallSchTime = nextCallSchTime;
	}
	public boolean isFollowUpTypeisLock() {
		return followUpTypeisLock;
	}
	public void setFollowUpTypeisLock(boolean followUpTypeisLock) {
		this.followUpTypeisLock = followUpTypeisLock;
	}


	
}
