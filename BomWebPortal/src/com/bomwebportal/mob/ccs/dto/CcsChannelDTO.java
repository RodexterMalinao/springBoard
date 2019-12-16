package com.bomwebportal.mob.ccs.dto;

public class CcsChannelDTO {
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getCentreCd() {
		return centreCd;
	}
	public void setCentreCd(String centreCd) {
		this.centreCd = centreCd;
	}
	public String getTeamCd() {
		return teamCd;
	}
	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}
	private String channelCd;
	private String centreCd;
	private String teamCd;
}
