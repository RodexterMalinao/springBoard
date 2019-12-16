package com.bomwebportal.dto;

import java.util.Date;

public class OrderMobDTO {

	private String orderId;
	private String shopCd;
	private String iguardSn;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String nfcInteger;
	
	private String staffId;
	private String ccc;
	private String sponsorLevel;
	
	private String orderSubType;
	
	private String manualAfNo;
	
	private String careStatus;
	private String careOptInd;
	private String careDmSupInd;
	
	private String locationCd;
	
	private String csubInd = "N"; 
	
	private String hkbnInd;
	
	private Date stockAssgnDate;
	
	// MBU2019003 -- Add CAMPAIGN_ID
	private String campaignId;
	
	public String getManualAfNo() {
		return manualAfNo;
	}

	public void setManualAfNo(String manualAfNo) {
		this.manualAfNo = manualAfNo;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getIguardSn() {
		return iguardSn;
	}
	public void setIguardSn(String iguardSn) {
		this.iguardSn = iguardSn;
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

	public String getNfcInteger() {
		return nfcInteger;
	}
	public void setNfcInteger(String nfcInteger) {
		this.nfcInteger = nfcInteger;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getCcc() {
		return ccc;
	}
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}
	public String getSponsorLevel() {
		return sponsorLevel;
	}
	public void setSponsorLevel(String sponsorLevel) {
		this.sponsorLevel = sponsorLevel;
	}
	public String getOrderSubType() {
		return orderSubType;
	}
	public void setOrderSubType(String orderSubType) {
		this.orderSubType = orderSubType;
	}

	public String getCareStatus() {
		return careStatus;
	}

	public void setCareStatus(String careStatus) {
		this.careStatus = careStatus;
	}

	public String getCareOptInd() {
		return careOptInd;
	}

	public void setCareOptInd(String careOptInd) {
		this.careOptInd = careOptInd;
	}

	public String getCareDmSupInd() {
		return careDmSupInd;
	}

	public void setCareDmSupInd(String careDmSupInd) {
		this.careDmSupInd = careDmSupInd;
	}

	public String getLocationCd() {
		return locationCd;
	}

	public void setLocationCd(String locationCd) {
		this.locationCd = locationCd;
	}

	public String getCsubInd() {
		return csubInd;
	}

	public void setCsubInd(String csubInd) {
		this.csubInd = csubInd;
	}

	public String getHkbnInd() {
		return hkbnInd;
	}

	public void setHkbnInd(String hkbnInd) {
		this.hkbnInd = hkbnInd;
	}

	public Date getStockAssgnDate() {
		return stockAssgnDate;
	}

	public void setStockAssgnDate(Date stockAssgnDate) {
		this.stockAssgnDate = stockAssgnDate;
	}
	
	// MBU2019003 -- Add CAMPAIGN_ID -- Start 
	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	// MBU2019003 -- Add CAMPAIGN_ID -- End 

}
