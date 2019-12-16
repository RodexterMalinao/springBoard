package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class SbOrderAmendDTO {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderAmendType() {
		return orderAmendType;
	}
	public void setOrderAmendType(String orderAmendType) {
		this.orderAmendType = orderAmendType;
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
	public String getPrevSeqNo() {
		return prevSeqNo;
	}
	public void setPrevSeqNo(String prevSeqNo) {
		this.prevSeqNo = prevSeqNo;
	}
	public String getRptSent() {
		return rptSent;
	}
	public void setRptSent(String rptSent) {
		this.rptSent = rptSent;
	}
	public String getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}

	private String orderId;
	private String orderAmendType;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String prevSeqNo;
	private String rptSent;
	private String memberNum;
}
