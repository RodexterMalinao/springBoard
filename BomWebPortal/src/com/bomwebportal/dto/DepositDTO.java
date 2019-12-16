package com.bomwebportal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

// BOMWEB_DEPOSIT
public class DepositDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7959102298419955373L;
	private String orderId;
	private String depositId;
	private BigDecimal depositAmount;
	private String itemCd;
	private String waiveInd;
	private String reasonCd;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	private String depositCd;
	private String depositDesc;
	private String reasonDesc;
	private String waivable;
	private String waiveOnAutopay;
	private String depositLevel;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
	}
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getWaiveInd() {
		return waiveInd;
	}
	public void setWaiveInd(String waiveInd) {
		this.waiveInd = waiveInd;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
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
	public String getDepositCd() {
		return depositCd;
	}
	public void setDepositCd(String depositCd) {
		this.depositCd = depositCd;
	}
	public String getDepositDesc() {
		return depositDesc;
	}
	public void setDepositDesc(String depositDesc) {
		this.depositDesc = depositDesc;
	}
	public String getReasonDesc() {
		return reasonDesc;
	}
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	public String getWaivable() {
		return waivable;
	}
	public void setWaivable(String waivable) {
		this.waivable = waivable;
	}
	public String getWaiveOnAutopay() {
		return waiveOnAutopay;
	}
	public void setWaiveOnAutopay(String waiveOnAutopay) {
		this.waiveOnAutopay = waiveOnAutopay;
	}
	public String getDepositLevel() {
		return depositLevel;
	}
	public void setDepositLevel(String depositLevel) {
		this.depositLevel = depositLevel;
	}
	
	
}
