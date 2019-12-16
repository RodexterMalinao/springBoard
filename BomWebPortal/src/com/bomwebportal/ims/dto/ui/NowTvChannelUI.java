package com.bomwebportal.ims.dto.ui;

public class NowTvChannelUI {
	private String parentPackId;
	private String channelId;
	private String channelDisplayDetail;
	private boolean isAdult;
	private String tvType;
	private int readRight=0;
	
	public int getReadRight() {
		if("HD".equalsIgnoreCase(tvType)){
			readRight = 4;
		}
		else if("HDR".equalsIgnoreCase(tvType)){
			readRight = 3;
		}
		else if("SD".equalsIgnoreCase(tvType)){
			readRight = 0;
		}
		return readRight;
	}

	public void setReadRight(int readRight) {
		this.readRight = readRight;
	}

	
	public String getParentPackId() {
		return parentPackId;
	}
	public void setParentPackId(String parentPackId) {
		this.parentPackId = parentPackId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public boolean isAdult() {
		return isAdult;
	}
	public void setAdult(boolean isAdult) {
		this.isAdult = isAdult;
	}
	public String getTvType() {
		return tvType;
	}
	public void setTvType(String tvType) {
		this.tvType = tvType;
	}
	public String getChannelDisplayDetail() {
		return channelDisplayDetail;
	}
	public void setChannelDisplayDetail(String channelDisplayDetail) {
		this.channelDisplayDetail = channelDisplayDetail;
	}
	
	
}
