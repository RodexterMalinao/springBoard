package com.bomwebportal.dto;

import java.io.Serializable;

public class AuthorizeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 560513764695272124L;
	
	private String channelId;
	private String users;
	private String html;
	private String attribute;
	private String lob;
	
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public String toString() {
		return "{users:"+users+", html:"+html + ", attribute:" + attribute+"}";
	}

}
