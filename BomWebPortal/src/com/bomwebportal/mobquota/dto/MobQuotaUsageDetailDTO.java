package com.bomwebportal.mobquota.dto;

import java.util.Date;

public class MobQuotaUsageDetailDTO {

	private String idDocType;
	private String idDocNum;
	private String quotaId;
	private int qty;
	private String orderType;
	private String orderId;
	private String remarks;
	private String markDelInd;
	
//	@JsonIgnore
	private String createBy;
	
	//@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="GMT+8")
//	@JsonIgnore
	private Date createDate;
	
	
	private String lastUpdBy;
	
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="GMT+8")
	private Date lastUpdDate;

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

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMarkDelInd() {
		return markDelInd;
	}

	public void setMarkDelInd(String markDelInd) {
		this.markDelInd = markDelInd;
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
// //FILE name:MobQuotaUsageDTO.java
