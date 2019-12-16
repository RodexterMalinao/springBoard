package com.bomwebportal.mob.ccs.dto;

public class MobCcsIncidentCauseDTO {
	public String getIncidentNo() {
		return incidentNo;
	}
	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}
	public String getCauseCode() {
		return causeCode;
	}
	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}
	public String getCauseCodeDesc() {
		return causeCodeDesc;
	}
	public void setCauseCodeDesc(String causeCodeDesc) {
		this.causeCodeDesc = causeCodeDesc;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	private String incidentNo;
	private String causeCode;
	private String causeCodeDesc;
	private String rowId;
}
