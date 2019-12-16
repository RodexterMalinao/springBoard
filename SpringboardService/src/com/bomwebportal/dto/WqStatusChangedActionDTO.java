package com.bomwebportal.dto;

public class WqStatusChangedActionDTO {
	
	private String wqWpAssgnId;
	private String pSbId; 
	private String pSbDtlId; 
	private String pSbActvId;
	private String pWqStatus;
	private String[] pWqNatureIds;
	private String pRemarks;
	private String pUser;
	
	public String getWqWpAssgnId() {
		return wqWpAssgnId;
	}
	public void setWqWpAssgnId(String wqWpAssgnId) {
		this.wqWpAssgnId = wqWpAssgnId;
	}
	public String getpSbId() {
		return pSbId;
	}
	public void setpSbId(String pSbId) {
		this.pSbId = pSbId;
	}
	public String getpSbDtlId() {
		return pSbDtlId;
	}
	public void setpSbDtlId(String pSbDtlId) {
		this.pSbDtlId = pSbDtlId;
	}
	public String getpSbActvId() {
		return pSbActvId;
	}
	public void setpSbActvId(String pSbActvId) {
		this.pSbActvId = pSbActvId;
	}
	public String getpWqStatus() {
		return pWqStatus;
	}
	public void setpWqStatus(String pWqStatus) {
		this.pWqStatus = pWqStatus;
	}
	public String[] getpWqNatureIds() {
		return pWqNatureIds;
	}
	public void setpWqNatureIds(String[] pWqNatureIds) {
		this.pWqNatureIds = pWqNatureIds;
	}
	public String getpRemarks() {
		return pRemarks;
	}
	public void setpRemarks(String pRemarks) {
		this.pRemarks = pRemarks;
	}
	public String getpUser() {
		return pUser;
	}
	public void setpUser(String pUser) {
		this.pUser = pUser;
	}
}
