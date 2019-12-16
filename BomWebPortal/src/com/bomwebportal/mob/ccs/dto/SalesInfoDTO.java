package com.bomwebportal.mob.ccs.dto;

public class SalesInfoDTO {
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTeamCd() {
		return teamCd;
	}
	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}
	public String getCentreCd() {
		return centreCd;
	}
	public void setCentreCd(String centreCd) {
		this.centreCd = centreCd;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	private String staffId;
	private String category;
	private String teamCd;
	private String centreCd;
	private String channelCd;
	private String channelId; // add by Joyce 20120827
}
