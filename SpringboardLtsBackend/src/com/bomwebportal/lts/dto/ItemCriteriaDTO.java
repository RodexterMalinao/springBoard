package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ItemCriteriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5079667656350456365L;
	
	private String itemType;
	private String locale;
	private boolean selectDefault;
	private boolean getAttrInd;
	private String baseContractPeriod;
	private String channelId;
	private String displayType;
	private String campaignCd;
	private String eligibleDevice;
	private String applnDate;
	private String eligibleDocType;
	private String osType;
	private String basketId;

	public static class ItemCriteriaBuilder {
		private String builderItemType;
		private String builderLocale;
		private boolean builderSelectDefault;
		private boolean builderGetAttrInd;
		private String builderBaseContractPeriod;
		private String builderChannelId;
		private String builderDisplayType;
		private String builderCampaignCd;
		private String builderEligibleDevice;
		private String builderApplnDate;
		private String builderEligibleDocType;
		private String builderOsType;
		private String builderBasketId;

		public ItemCriteriaBuilder(){
		}

		public ItemCriteriaBuilder(ItemCriteriaDTO anotherCriteria){
			this.builderItemType             = anotherCriteria.getItemType();
			this.builderLocale               = anotherCriteria.getLocale();
			this.builderSelectDefault        = anotherCriteria.isSelectDefault();
			this.builderGetAttrInd           = anotherCriteria.isGetAttrInd();
			this.builderBaseContractPeriod   = anotherCriteria.getBaseContractPeriod();
			this.builderChannelId            = anotherCriteria.getChannelId();
			this.builderDisplayType          = anotherCriteria.getDisplayType();
			this.builderCampaignCd           = anotherCriteria.getCampaignCd();
			this.builderEligibleDevice       = anotherCriteria.getEligibleDevice();
			this.builderApplnDate            = anotherCriteria.getApplnDate();
			this.builderEligibleDocType      = anotherCriteria.getEligibleDocType();
			this.builderOsType               = anotherCriteria.getOsType();
			this.builderBasketId             = anotherCriteria.getBasketId();
		}
		
		public ItemCriteriaDTO build(){
			ItemCriteriaDTO criteria = new ItemCriteriaDTO();
			criteria.setApplnDate(this.builderApplnDate);
			criteria.setBaseContractPeriod(this.builderBaseContractPeriod);
			criteria.setCampaignCd(this.builderCampaignCd);
			criteria.setChannelId(this.builderChannelId);
			criteria.setDisplayType(this.builderDisplayType);
			criteria.setEligibleDevice(this.builderEligibleDevice);
			criteria.setEligibleDocType(this.builderEligibleDocType);
			criteria.setGetAttrInd(this.builderGetAttrInd);
			criteria.setItemType(this.builderItemType);
			criteria.setLocale(this.builderLocale);
			criteria.setSelectDefault(this.builderSelectDefault);
			criteria.setOsType(this.builderOsType);
			criteria.setBasketId(this.builderBasketId);
			return criteria;
		}
		
		public ItemCriteriaBuilder setItemType(String itemType) {
			this.builderItemType = itemType;
			return this;
		}
		
		public ItemCriteriaBuilder setLocale(String locale) {
			this.builderLocale = locale;
			return this;
		}
		
		public ItemCriteriaBuilder setSelectDefault(boolean selectDefault) {
			this.builderSelectDefault = selectDefault;
			return this;
		}
		
		public ItemCriteriaBuilder setGetAttrInd(boolean getAttrInd) {
			this.builderGetAttrInd = getAttrInd;
			return this;
		}
		
		public ItemCriteriaBuilder setBaseContractPeriod(String baseContractPeriod) {
			this.builderBaseContractPeriod = baseContractPeriod;
			return this;
		}
		
		public ItemCriteriaBuilder setChannelId(String channelId) {
			this.builderChannelId = channelId;
			return this;
		}
		
		public ItemCriteriaBuilder setDisplayType(String displayType) {
			this.builderDisplayType = displayType;
			return this;
		}
		
		public ItemCriteriaBuilder setCampaignCd(String campaignCd) {
			this.builderCampaignCd = campaignCd;
			return this;
		}
		
		public ItemCriteriaBuilder setEligibleDevice(String eligibleDevice) {
			this.builderEligibleDevice = eligibleDevice;
			return this;
		}
		
		public ItemCriteriaBuilder setApplnDate(String applnDate) {
			this.builderApplnDate = applnDate;
			return this;
		}
		
		public ItemCriteriaBuilder setEligibleDocType(String eligibleDocType) {
			this.builderEligibleDocType = eligibleDocType;
			return this;
		}
		
		public ItemCriteriaBuilder setOsType(String osType) {
			this.builderOsType = osType;
			return this;
		}
		
		public ItemCriteriaBuilder setBasketId(String basketId) {
			this.builderBasketId = basketId;
			return this;
		}
		
	}
	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public boolean isSelectDefault() {
		return selectDefault;
	}
	public void setSelectDefault(boolean selectDefault) {
		this.selectDefault = selectDefault;
	}
	public boolean isGetAttrInd() {
		return getAttrInd;
	}
	public void setGetAttrInd(boolean getAttrInd) {
		this.getAttrInd = getAttrInd;
	}
	public String getBaseContractPeriod() {
		return baseContractPeriod;
	}
	public void setBaseContractPeriod(String baseContractPeriod) {
		this.baseContractPeriod = baseContractPeriod;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public String getCampaignCd() {
		return campaignCd;
	}
	public void setCampaignCd(String campaignCd) {
		this.campaignCd = campaignCd;
	}
	public String getEligibleDevice() {
		return eligibleDevice;
	}
	public void setEligibleDevice(String eligibleDevice) {
		this.eligibleDevice = eligibleDevice;
	}
	public String getApplnDate() {
		return applnDate;
	}
	public void setApplnDate(String applnDate) {
		this.applnDate = applnDate;
	}
	public String getEligibleDocType() {
		return eligibleDocType;
	}
	public void setEligibleDocType(String eligibleDocType) {
		this.eligibleDocType = eligibleDocType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	
	
}
