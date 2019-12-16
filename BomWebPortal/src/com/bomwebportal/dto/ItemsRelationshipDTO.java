package com.bomwebportal.dto;

import java.sql.Date;

public class ItemsRelationshipDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4097375449054509975L;
	private String channelId;
	private String relationType;
	private String itemA;
	private String itemB;
	private Date effStartDate;
	private Date effEndDate;
	private String description;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Date getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(Date effStartDate) {
		this.effStartDate = effStartDate;
	}

	public Date getEffEndDate() {
		return effEndDate;
	}

	public void setEffEndDate(Date effEndDate) {
		this.effEndDate = effEndDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getItemA() {
		return itemA;
	}

	public void setItemA(String itemA) {
		this.itemA = itemA;
	}

	public String getItemB() {
		return itemB;
	}

	public void setItemB(String itemB) {
		this.itemB = itemB;
	}

}
