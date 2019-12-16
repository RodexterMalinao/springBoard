package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class CsiCaseLogDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4906493816993967822L;
	
	private String rowId;
	private String caseNo;
	private String orderId;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String callTypeCd;
	private String contactBy;
	private String resultCd;
	private String relWtCust;
	private String remark;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String seqNo;
	
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
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
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getCallTypeCd() {
		return callTypeCd;
	}
	public void setCallTypeCd(String callTypeCd) {
		this.callTypeCd = callTypeCd;
	}
	public String getContactBy() {
		return contactBy;
	}
	public void setContactBy(String contactBy) {
		this.contactBy = contactBy;
	}
	public String getResultCd() {
		return resultCd;
	}
	public void setResultCd(String resultCd) {
		this.resultCd = resultCd;
	}
	public String getRelWtCust() {
		return relWtCust;
	}
	public void setRelWtCust(String relWtCust) {
		this.relWtCust = relWtCust;
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
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	
}
