package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class MobCcsMaintFuncDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -407323172907734541L;
	
	private int channelId;
	private String channelCd;
	private String category;
	private String maintId;
	private String funcName;
	private String funcHtml;
	
	/**
	 * @return the channelId
	 */
	public int getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	/**
	 * @return the channelCd
	 */
	public String getChannelCd() {
		return channelCd;
	}
	/**
	 * @param channelCd the channelCd to set
	 */
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the maintId
	 */
	public String getMaintId() {
		return maintId;
	}
	/**
	 * @param maintId the maintId to set
	 */
	public void setMaintId(String maintId) {
		this.maintId = maintId;
	}
	/**
	 * Retrieve MOBCCS admin maintenance function name (label)
	 * @return the funcName
	 */
	public String getFuncName() {
		return funcName;
	}
	/**
	 * Set MOBCCS admin maintenance function name (label)
	 * @param funcName the funcName to set
	 */
	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}
	/**
	 * Retrieve MOBCCS admin maintenance function link (href)
	 * @return the funcHtml
	 */
	public String getFuncHtml() {
		return funcHtml;
	}
	/**
	 * Set MOBCCS admin maintenance function link (href)
	 * @param funcHtml the funcHtml to set
	 */
	public void setFuncHtml(String funcHtml) {
		this.funcHtml = funcHtml;
	}

}
