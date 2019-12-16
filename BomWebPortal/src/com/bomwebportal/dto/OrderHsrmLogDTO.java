package com.bomwebportal.dto;

import java.util.Date;

public class OrderHsrmLogDTO {
	private String orderId;
	private String refId;
	private String itemCode;
	private String salesCd;
	private String actionInd;
	private String status;
	private String errCd;
	private String errMsg;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String queueBrand;
	private String queueStatus;
	private String basketBrand;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getSalesCd() {
		return salesCd;
	}
	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	public String getActionInd() {
		return actionInd;
	}
	public void setActionInd(String actionInd) {
		this.actionInd = actionInd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrCd() {
		return errCd;
	}
	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
	public String getQueueBrand() {
		return queueBrand;
	}
	public void setQueueBrand(String queueBrand) {
		this.queueBrand = queueBrand;
	}
	public String getQueueStatus() {
		return queueStatus;
	}
	public void setQueueStatus(String queueStatus) {
		this.queueStatus = queueStatus;
	}
	public String getBasketBrand() {
		return basketBrand;
	}
	public void setBasketBrand(String basketBrand) {
		this.basketBrand = basketBrand;
	}
	
}
