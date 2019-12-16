package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class CeksTxnDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3274904455384151604L;
	
	
	private String AppId;
	private String UserId;
	private String ContextId;
	private Date CreationDate;
	
	
	public String getAppId() {
		return AppId;
	}
	public void setAppId(String appId) {
		AppId = appId;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getContextId() {
		return ContextId;
	}
	public void setContextId(String contextId) {
		ContextId = contextId;
	}
	public Date getCreationDate() {
		return CreationDate;
	}
	public void setCreationDate(Date creationDate) {
		CreationDate = creationDate;
	}

	
	

}
