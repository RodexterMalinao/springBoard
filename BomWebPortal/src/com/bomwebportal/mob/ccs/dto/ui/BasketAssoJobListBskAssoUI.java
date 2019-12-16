package com.bomwebportal.mob.ccs.dto.ui;

public class BasketAssoJobListBskAssoUI {
	public String getJobList() {
		return jobList;
	}

	public void setJobList(String jobList) {
		this.jobList = jobList;
	}

	public boolean isShowEnded() {
		return showEnded;
	}

	public void setShowEnded(boolean showEnded) {
		this.showEnded = showEnded;
	}

	private String jobList;
	private boolean showEnded;
}
