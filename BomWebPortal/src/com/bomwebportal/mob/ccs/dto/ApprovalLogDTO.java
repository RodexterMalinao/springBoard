package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class ApprovalLogDTO {
	public String getAuthorizedId() {
		return authorizedId;
	}
	public void setAuthorizedId(String authorizedId) {
		this.authorizedId = authorizedId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	public String getAuthorizedAction() {
		return authorizedAction;
	}
	public void setAuthorizedAction(String authorizedAction) {
		this.authorizedAction = authorizedAction;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	
	
	public String getReasonType() {
		return reasonType;
	}
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderNature() {
		return orderNature;
	}
	public void setOrderNature(String orderNature) {
		this.orderNature = orderNature;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getMinVas() {
		return minVas;
	}
	public void setMinVas(String minVas) {
		this.minVas = minVas;
	}


	public String getRpGrossPlanFee() {
		return rpGrossPlanFee;
	}
	public void setRpGrossPlanFee(String rpGrossPlanFee) {
		this.rpGrossPlanFee = rpGrossPlanFee;
	}


	private String authorizedId;
	private String orderId;
	private Date authorizedDate;
	private String authorizedBy;
	private String authorizedAction;
	private String shopCode;
	private String lastUpdBy;
	private Date createDate;
	private Date lastUpdDate;
	
	private String reasonType;
	private String reasonCd;
	private String remark;
	
	private String rpGrossPlanFee;
	private String orderNature;
	private String basketId;
	private String minVas;
}
