package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class DoaItemDTO {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getDoaItemCode() {
		return doaItemCode;
	}
	public void setDoaItemCode(String doaItemCode) {
		this.doaItemCode = doaItemCode;
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
	public String getDoaItemSerial() {
		return doaItemSerial;
	}
	public void setDoaItemSerial(String doaItemSerial) {
		this.doaItemSerial = doaItemSerial;
	}
	private String orderId;
	private int seqNo;
	private String doaItemCode;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String doaItemSerial;
}
