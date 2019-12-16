package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class SpecialMrtRequestDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5196445354790405051L;
	
	private String requestId;
	private Date requestDate;
	private String requestBy;
	private String title;
	private String firstName;
	private String lastName;
	private String contractPeriod;
	private String recurrentAmt;
	private String msisdnPattern;
	private String remark;
	private String approvalResult;
	private String msisdn;
	private String msisdnlvl;
	private String validDate;
	private String reserveId;
	private String resOperId;
	private String approvalSerial;
	private String adminRemark;
	private String channel;
	private String requestor;
	private Boolean offerViolateInd = false;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String actionType;
	private String originalMsisdn;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestBy() {
		return requestBy;
	}
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getRecurrentAmt() {
		return recurrentAmt;
	}
	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}
	public String getMsisdnPattern() {
		return msisdnPattern;
	}
	public void setMsisdnPattern(String msisdnPattern) {
		this.msisdnPattern = msisdnPattern;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getApprovalResult() {
		return approvalResult;
	}
	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getResOperId() {
		return resOperId;
	}
	public void setResOperId(String resOperId) {
		this.resOperId = resOperId;
	}
	public String getApprovalSerial() {
		return approvalSerial;
	}
	public void setApprovalSerial(String approvalSerial) {
		this.approvalSerial = approvalSerial;
	}
	public String getAdminRemark() {
		return adminRemark;
	}
	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public Boolean getOfferViolateInd() {
		return offerViolateInd;
	}
	public void setOfferViolateInd(Boolean offerViolateInd) {
		this.offerViolateInd = offerViolateInd;
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
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getOriginalMsisdn() {
		return originalMsisdn;
	}
	public void setOriginalMsisdn(String originalMsisdn) {
		this.originalMsisdn = originalMsisdn;
	}
	
	
	
	
	
}
