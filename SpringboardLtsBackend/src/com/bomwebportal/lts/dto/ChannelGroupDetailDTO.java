package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ChannelGroupDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 122721132642731338L;

	private boolean selected;
	
	private String formId;
	
	private String channelGroupId;
	
	private String channelGroupCd;
	
	private String channelGroupDesc;
	
	private String channelGroupHtml;
	
	private String imagePath;
	
	private ChannelDetailDTO[] channelDetails;

	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ChannelDetailDTO[] getChannelDetails() {
		return channelDetails;
	}

	public void setChannelDetails(ChannelDetailDTO[] channelDetails) {
		this.channelDetails = channelDetails;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getChannelGroupId() {
		return channelGroupId;
	}

	public void setChannelGroupId(String channelGroupId) {
		this.channelGroupId = channelGroupId;
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

	public String getChannelGroupHtml() {
		return channelGroupHtml;
	}

	public void setChannelGroupHtml(String channelGroupHtml) {
		this.channelGroupHtml = channelGroupHtml;
	}
	
}
