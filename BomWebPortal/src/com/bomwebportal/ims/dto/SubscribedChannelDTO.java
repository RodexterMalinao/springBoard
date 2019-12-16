package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class SubscribedChannelDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3348743205534147711L;
	
	
	private String OrderId;
	private String DtlId;
	private String ChannelId;	
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;
	
	//bom sync detail
	private String CampaignCd;
	private String PlanCd;
	private String ChannelCd;
	private String TvTypt;
	
	private String SbOfferType;
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getChannelId() {
		return ChannelId;
	}
	public void setChannelId(String channelId) {
		ChannelId = channelId;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getDtlId() {
		return DtlId;
	}
	public void setDtlId(String dtlId) {
		DtlId = dtlId;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public String getCampaignCd() {
		return CampaignCd;
	}
	public void setCampaignCd(String campaignCd) {
		CampaignCd = campaignCd;
	}
	public String getPlanCd() {
		return PlanCd;
	}
	public void setPlanCd(String planCd) {
		PlanCd = planCd;
	}
	public String getChannelCd() {
		return ChannelCd;
	}
	public void setChannelCd(String channelCd) {
		ChannelCd = channelCd;
	}
	public String getTvTypt() {
		return TvTypt;
	}
	public void setTvTypt(String tvTypt) {
		TvTypt = tvTypt;
	}
	public void setSbOfferType(String sbOfferType) {
		SbOfferType = sbOfferType;
	}
	public String getSbOfferType() {
		return SbOfferType;
	}
}