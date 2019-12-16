package com.bomwebportal.ims.dto;

import java.io.Serializable;


public class ImsRptChannelDTO implements Serializable{
	
	private static final long serialVersionUID = 629210834742496125L;
	
	private String channelGroupCd;
	private String channelGroupDesc;
	private String channelId;
	private String channelCd;
	private String tvType;
	private String credit;
	private String mdoInd;
	private String channelDesc;
	private String parentChannelId;
	private String planCd;
	private String campaignCd;
	private String offerCd;
	
	public String getPlanCd() {
		return planCd;
	}

	public void setPlanCd(String planCd) {
		this.planCd = planCd;
	}

	public ImsRptChannelDTO(){
		
	}

	public String getChannelGroupCd() {
		return channelGroupCd;
	}

	public void setChannelGroupCd(String channelGroupCd) {
		this.channelGroupCd = channelGroupCd;
	}

	public String getChannelGroupDesc() {
		return channelGroupDesc;
	}

	public void setChannelGroupDesc(String channelGroupDesc) {
		this.channelGroupDesc = channelGroupDesc;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getTvType() {
		return tvType;
	}

	public void setTvType(String tvType) {
		this.tvType = tvType;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getMdoInd() {
		return mdoInd;
	}

	public void setMdoInd(String mdoInd) {
		this.mdoInd = mdoInd;
	}

	public String getChannelDesc() {
		return channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public String getParentChannelId() {
		return parentChannelId;
	}

	public void setParentChannelId(String parentChannelId) {
		this.parentChannelId = parentChannelId;
	}

	@Override
	public String toString() {
		return "ImsRptChannelDTO [channelGroupCd=" + channelGroupCd
				+ ", channelGroupDesc=" + channelGroupDesc + ", channelId="
				+ channelId + ", channelCd=" + channelCd + ", tvType=" + tvType
				+ ", credit=" + credit + ", mdoInd=" + mdoInd
				+ ", channelDesc=" + channelDesc + "]";
	}

	public void setCampaignCd(String campaignCd) {
		this.campaignCd = campaignCd;
	}

	public String getCampaignCd() {
		return campaignCd;
	}

	public void setOfferCd(String offerCd) {
		this.offerCd = offerCd;
	}

	public String getOfferCd() {
		return offerCd;
	}

}
