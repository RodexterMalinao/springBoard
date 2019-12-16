package com.bomwebportal.mob.cos.dto;

import java.util.Date;
import java.util.List;

public class MobCosCampaignDtlDTO implements java.io.Serializable {
	private static final long serialVersionUID = -8802398272919105259L;
	
	private String campaignId;
	private String basketId;
	private String basketDesc;
	private String sourceBasketId;
	private String campaignBasketSeq;
	private String tier;
	private String effStartDate;
	private String effEndDate;
	private String activeInd;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	private String ratePlan;
	private String bundle;
	private String contract;
	private String handset;
	
	private List<MobCosCampaignVasDTO> campaignVasList;
	
	public String getCampaignId() {
		return campaignId;
	}
	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getBasketDesc() {
		return basketDesc;
	}
	public void setBasketDesc(String basketDesc) {
		this.basketDesc = basketDesc;
	}
	public String getSourceBasketId() {
		return sourceBasketId;
	}
	public void setSourceBasketId(String sourceBasketId) {
		this.sourceBasketId = sourceBasketId;
	}
	public String getCampaignBasketSeq() {
		return campaignBasketSeq;
	}
	public void setCampaignBasketSeq(String campaignBasketSeq) {
		this.campaignBasketSeq = campaignBasketSeq;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public String getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}
	public String getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}
	public String getActiveInd() {
		return activeInd;
	}
	public void setActiveInd(String activeInd) {
		this.activeInd = activeInd;
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
	public String getRatePlan() {
		return ratePlan;
	}
	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}
	public String getBundle() {
		return bundle;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getHandset() {
		return handset;
	}
	public void setHandset(String handset) {
		this.handset = handset;
	}
	public List<MobCosCampaignVasDTO> getCampaignVasList() {
		return campaignVasList;
	}
	public void setCampaignVasList(List<MobCosCampaignVasDTO> campaignVasList) {
		this.campaignVasList = campaignVasList;
	}
}
