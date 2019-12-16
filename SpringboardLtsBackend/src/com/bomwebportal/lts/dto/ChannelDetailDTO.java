package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ChannelDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4057296065512441176L;
	
	private boolean selected;
	
	private String channelId;
	
	private String channelCd;
	
	private String credit;
	
	private String tvType;
	
	private String isAdultChannel;
	
	private String mdoInd;
	
	private String channelHtml;
	
	private ChannelAttbDTO[] channelAttbs;
	
	private ChannelIconDTO[] channelIcons;

	private ItemDetailDTO itemDetail;
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getTvType() {
		return tvType;
	}

	public void setTvType(String tvType) {
		this.tvType = tvType;
	}

	public String getIsAdultChannel() {
		return isAdultChannel;
	}

	public void setIsAdultChannel(String isAdultChannel) {
		this.isAdultChannel = isAdultChannel;
	}

	public String getMdoInd() {
		return mdoInd;
	}

	public void setMdoInd(String mdoInd) {
		this.mdoInd = mdoInd;
	}

	public String getChannelHtml() {
		return channelHtml;
	}

	public void setChannelHtml(String channelHtml) {
		this.channelHtml = channelHtml;
	}

	public ChannelAttbDTO[] getChannelAttbs() {
		return channelAttbs;
	}

	public void setChannelAttbs(ChannelAttbDTO[] channelAttbs) {
		this.channelAttbs = channelAttbs;
	}

	public ChannelIconDTO[] getChannelIcons() {
		return channelIcons;
	}

	public void setChannelIcons(ChannelIconDTO[] channelIcons) {
		this.channelIcons = channelIcons;
	}

	public ItemDetailDTO getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetailDTO itemDetail) {
		this.itemDetail = itemDetail;
	}	
	
	
}
