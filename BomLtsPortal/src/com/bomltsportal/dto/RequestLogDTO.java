package com.bomltsportal.dto;

import java.io.Serializable;

public class RequestLogDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4245196081693276668L;
	/*
	 * (req_id, ip_address, session_id, create_date) values
	 */
	
	private String reqId;
	private String ipAddress;
	private String sessionId;
	private String useragent;
	private String lob;
	private String subType;
	private String deviceType;
	private String channel;
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUseragent() {
		return useragent;
	}
	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	

}
