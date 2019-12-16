package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ExclusiveChannelDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2873243913783071885L;
	
	private String channelAId;
	private String channelACd;
	private String channelADesc;
	
	private String channelBId;
	private String channelBCd;
	private String channelBDesc;
	public String getChannelAId() {
		return channelAId;
	}
	public void setChannelAId(String channelAId) {
		this.channelAId = channelAId;
	}
	public String getChannelACd() {
		return channelACd;
	}
	public void setChannelACd(String channelACd) {
		this.channelACd = channelACd;
	}
	public String getChannelADesc() {
		return channelADesc;
	}
	public void setChannelADesc(String channelADesc) {
		this.channelADesc = channelADesc;
	}
	public String getChannelBId() {
		return channelBId;
	}
	public void setChannelBId(String channelBId) {
		this.channelBId = channelBId;
	}
	public String getChannelBCd() {
		return channelBCd;
	}
	public void setChannelBCd(String channelBCd) {
		this.channelBCd = channelBCd;
	}
	public String getChannelBDesc() {
		return channelBDesc;
	}
	public void setChannelBDesc(String channelBDesc) {
		this.channelBDesc = channelBDesc;
	}
	
}
