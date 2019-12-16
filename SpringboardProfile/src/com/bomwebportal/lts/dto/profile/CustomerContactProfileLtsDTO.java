package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class CustomerContactProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = 1929460272653100722L;

	private String contactName = null;
	
	private String mediaType = null;
	
	private String mediaNum = null;
	
	private String mediaKey = null;
	
	private String lastUpdDate = null;

	
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaNum() {
		return mediaNum;
	}

	public void setMediaNum(String mediaNum) {
		this.mediaNum = mediaNum;
	}

	public String getMediaKey() {
		return mediaKey;
	}

	public void setMediaKey(String mediaKey) {
		this.mediaKey = mediaKey;
	}

	public String getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	
	
}
