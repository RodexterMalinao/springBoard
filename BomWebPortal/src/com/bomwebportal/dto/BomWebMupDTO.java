package com.bomwebportal.dto;

import java.util.Date;

public class BomWebMupDTO {
	private String orderId;
	private String mupGrpId;
	private String mupInd;
	private String newPriInd;
	private String ioInd;
	private String pOcId;
	private String pServiceId;
	private String parentOrderId;
	private String createBy;
	private Date createDate;
	private String lastUpdateBy;
	private Date lastUpdateDate;
	private String primary;
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMupGrpId() {
		return mupGrpId;
	}
	public void setMupGrpId(String mupGrpId) {
		this.mupGrpId = mupGrpId;
	}
	public String getMupInd() {
		return mupInd;
	}
	public void setMupInd(String mupInd) {
		this.mupInd = mupInd;
	}
	public String getNewPriInd() {
		return newPriInd;
	}
	public void setNewPriInd(String newPriInd) {
		this.newPriInd = newPriInd;
	}
	public String getIoInd() {
		return ioInd;
	}
	public void setIoInd(String ioInd) {
		this.ioInd = ioInd;
	}
	public String getpOcId() {
		return pOcId;
	}
	public void setpOcId(String pOcId) {
		this.pOcId = pOcId;
	}
	public String getpServiceId() {
		return pServiceId;
	}
	public void setpServiceId(String pServiceId) {
		this.pServiceId = pServiceId;
	}
	public String getParentOrderId() {
		return parentOrderId;
	}
	public void setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
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
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}