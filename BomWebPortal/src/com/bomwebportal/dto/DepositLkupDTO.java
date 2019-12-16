package com.bomwebportal.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

// W_DEPOSIT_LKUP
public class DepositLkupDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 99649179100345478L;
	private String depositId;
	private String depositCd;
	private String depositDesc;
	private BigDecimal depositAmount;
	private String depositLevel;
	private String waivable;
	private String waiveOnAutopay;
	private String itemCd;
	private String itemDesc;
	private Date startDate;
	private Date endDate;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	public String getDepositId() {
		return depositId;
	}
	public void setDepositId(String depositId) {
		this.depositId = depositId;
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
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getDepositLevel() {
		return depositLevel;
	}
	public void setDepositLevel(String depositLevel) {
		this.depositLevel = depositLevel;
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
	public String getItemCd() {
		return itemCd;
	}
	public void setItemCd(String itemCd) {
		this.itemCd = itemCd;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
