package com.bomwebportal.ims.dto;

import java.util.List;

import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;

public class ChannelDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5248514987697312232L;

	private String ChannelID;
	private String ChannelCD;
	private String ChannelDesc;
	  
	
	public ChannelDTO(){
		
	}

	public String getChannelID() {
		return ChannelID;
	}

	public void setChannelID(String ChannelID) {
		this.ChannelID = ChannelID;
	}
	
	public String getChannelCD() {
		return ChannelCD;
	}

	public void setChannelCD(String ChannelCD) {
		this.ChannelCD = ChannelCD;
	}
	
	public String getChannelDesc() {
		return ChannelDesc;
	}

	public void setChannelDesc(String ChannelDesc) {
		this.ChannelDesc = ChannelDesc;
	}

	
}
