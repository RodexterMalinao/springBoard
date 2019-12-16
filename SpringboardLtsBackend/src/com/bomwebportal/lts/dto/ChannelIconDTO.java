package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ChannelIconDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -793775062729908720L;
	
	private String channelId;
	private String type;
	private String imageDesc;
	private String imagePath;
	private String imageHtml;
	private String displaySeq;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImageDesc() {
		return imageDesc;
	}
	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImageHtml() {
		return imageHtml;
	}
	public void setImageHtml(String imageHtml) {
		this.imageHtml = imageHtml;
	}
	public String getDisplaySeq() {
		return displaySeq;
	}
	public void setDisplaySeq(String displaySeq) {
		this.displaySeq = displaySeq;
	}
	
	

}
