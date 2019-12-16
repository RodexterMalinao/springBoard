package com.bomwebportal.mob.ccs.dto.ui;

public class BulkPrintUI {
	public String getProcessDateStr() {
		return processDateStr;
	}
	public void setProcessDateStr(String processDateStr) {
		this.processDateStr = processDateStr;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}


	private String processDateStr;
	private String checkPoint;
	private String location;
}
