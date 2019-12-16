package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class ChannelDetailDTO implements Serializable{

	/**
	 * 
	 */
//	private static final long serialVersionUID = -8186657263196563679L;
	
	private String campaignCode;
	private String planCode;
	private String channelId;
	private String nameChi;
	private String nameEng;
	private String charge;
	private String registeredDate;
	
	private boolean isPayChannel = false;
	private boolean isEntChannel = false;
	
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getNameChi() {
		return nameChi;
	}
	public void setNameChi(String nameChi) {
		this.nameChi = nameChi;
	}
	public String getNameEng() {
		return nameEng;
	}
	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	public void setPayChannel(boolean isPayChannel) {
		this.isPayChannel = isPayChannel;
	}
	public boolean isPayChannel() {
		return isPayChannel;
	}
	public void setEntChannel(boolean isEntChannel) {
		this.isEntChannel = isEntChannel;
	}
	public boolean isEntChannel() {
		return isEntChannel;
	}
	
}
