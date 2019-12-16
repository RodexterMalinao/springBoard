package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class DeviceDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2248736870986672720L;
	
	private String type;
	private String locale;
	private String desc;
	private String html;
	private String imagePath;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
	
}
