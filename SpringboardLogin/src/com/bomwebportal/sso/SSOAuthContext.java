package com.bomwebportal.sso;

import java.io.Serializable;

public class SSOAuthContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5588305741153188329L;
	private String userId;
	private String hash;
	private String appId;
	private String ticket;
	private long lastUpdate;
	
	public SSOAuthContext(String userId, String hash, String appId) {
		this(userId, hash, appId, null);
	}
	public SSOAuthContext(String userId, String hash, String appId, String ticket) {
		this.userId = userId;
		this.hash = hash;
		this.appId = appId;
		this.ticket = ticket;
		this.lastUpdate = System.currentTimeMillis();
	}
	public String getUserId() {
		return userId;
	}
	public String getHash() {
		return hash;
	}
	public String getAppId() {
		return appId;
	}
	public String getTicket() {
		return ticket;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}

	public void touch() {
		this.lastUpdate = System.currentTimeMillis();
	}
	
}
