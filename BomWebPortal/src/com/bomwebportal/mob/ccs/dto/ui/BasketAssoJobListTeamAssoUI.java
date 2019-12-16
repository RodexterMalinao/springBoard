package com.bomwebportal.mob.ccs.dto.ui;

public class BasketAssoJobListTeamAssoUI {
	public String getJobList() {
		return jobList;
	}
	public void setJobList(String jobList) {
		this.jobList = jobList;
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
	public boolean isShowEnded() {
		return showEnded;
	}
	public void setShowEnded(boolean showEnded) {
		this.showEnded = showEnded;
	}
	private String jobList;
	private String teamCd;
	private String centreCd;
	private String channelCd;
	private boolean showEnded;
}
