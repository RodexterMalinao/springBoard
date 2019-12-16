package com.bomwebportal.mob.ccs.dto.ui;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;

public class BasketAssoJobListTeamAssoEditUI {
	public String getJobList() {
		return jobList;
	}
	public void setJobList(String jobList) {
		this.jobList = jobList;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public List<BasketAssoJobListTeamAssoDTO> getOverlapRecords() {
		return overlapRecords;
	}
	public void setOverlapRecords(List<BasketAssoJobListTeamAssoDTO> overlapRecords) {
		this.overlapRecords = overlapRecords;
	}
	private String jobList;
	private String channelCd;
	private String teamCd;
	private String centreCd;
	private String startDate;
	private String endDate;
	private String rowId;
	
	private List<BasketAssoJobListTeamAssoDTO> overlapRecords;
}
