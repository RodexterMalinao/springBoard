package com.bomwebportal.dto;

import java.util.Date;

public class BasketMinVasLkupDTO {
	private String basketId;
	private double minVas;
	private Date effStartDate;
	private Date effEndDate;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public double getMinVas() {
		return minVas;
	}
	public void setMinVas(double minVas) {
		this.minVas = minVas;
	}
	public Date getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(Date effStartDate) {
		this.effStartDate = effStartDate;
	}
	public Date getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(Date effEndDate) {
		this.effEndDate = effEndDate;
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
