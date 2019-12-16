package com.bomwebportal.dto;

import java.util.Date;

public class MobItemQuotaDTO {

	private String itemId;
	private String lvlInd;
	private String lvlId;
	private String quotaId;
	private String quotaEngDesc;
	private String quotaChiDesc;
	private int ceilCnt;
	private Date effStartDate;
	private Date effEndDate;
	private String lvlCd;
	private String lvlDesc;
	
	private Date quotaAssignEffStartDate;
	private Date quotaAssignEffEndDate;

	public Date getQuotaAssignEffStartDate() {
		return quotaAssignEffStartDate;
	}

	public void setQuotaAssignEffStartDate(Date quotaAssignEffStartDate) {
		this.quotaAssignEffStartDate = quotaAssignEffStartDate;
	}

	public Date getQuotaAssignEffEndDate() {
		return quotaAssignEffEndDate;
	}

	public void setQuotaAssignEffEndDate(Date quotaAssignEffEndDate) {
		this.quotaAssignEffEndDate = quotaAssignEffEndDate;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getLvlInd() {
		return lvlInd;
	}

	public void setLvlInd(String lvlInd) {
		this.lvlInd = lvlInd;
	}

	public String getLvlId() {
		return lvlId;
	}

	public void setLvlId(String lvlId) {
		this.lvlId = lvlId;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public String getQuotaEngDesc() {
		return quotaEngDesc;
	}

	public void setQuotaEngDesc(String quotaEngDesc) {
		this.quotaEngDesc = quotaEngDesc;
	}

	public String getQuotaChiDesc() {
		return quotaChiDesc;
	}

	public void setQuotaChiDesc(String quotaChiDesc) {
		this.quotaChiDesc = quotaChiDesc;
	}

	public int getCeilCnt() {
		return ceilCnt;
	}

	public void setCeilCnt(int ceilCnt) {
		this.ceilCnt = ceilCnt;
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

	public String getLvlCd() {
		return lvlCd;
	}

	public void setLvlCd(String lvlCd) {
		this.lvlCd = lvlCd;
	}

	public String getLvlDesc() {
		return lvlDesc;
	}

	public void setLvlDesc(String lvlDesc) {
		this.lvlDesc = lvlDesc;
	}

}
