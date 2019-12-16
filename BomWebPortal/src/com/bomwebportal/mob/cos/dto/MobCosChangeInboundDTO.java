package com.bomwebportal.mob.cos.dto;

import java.util.List;

public class MobCosChangeInboundDTO {
	private List<MobCosStaffListDTO> staffList;
	private String selectedChannelCd;
	private String selectedCenterId;
	private String selectedTeamId;
	private String selectedInBoundindSt4;
	private String selectedInBoundindOw;
	private String selectedStaffid;
	private String salesCd;
	private String actionType;
	private boolean allowAccess;
	
	
	public List<MobCosStaffListDTO> getStaffList() {
		return staffList;
	}
	public void setStaffList(List<MobCosStaffListDTO> staffList) {
		this.staffList = staffList;
	}
	public String getSelectedChannelCd() {
		return selectedChannelCd;
	}
	public void setSelectedChannelCd(String selectedChannelCd) {
		this.selectedChannelCd = selectedChannelCd;
	}
	public String getSelectedCenterId() {
		return selectedCenterId;
	}
	public void setSelectedCenterId(String selectedCenterId) {
		this.selectedCenterId = selectedCenterId;
	}
	public String getSelectedTeamId() {
		return selectedTeamId;
	}
	public void setSelectedTeamId(String selectedTeamId) {
		this.selectedTeamId = selectedTeamId;
	}
	public String getSelectedInBoundindSt4() {
		return selectedInBoundindSt4;
	}
	public void setSelectedInBoundindSt4(String selectedInBoundindSt4) {
		this.selectedInBoundindSt4 = selectedInBoundindSt4;
	}
	public String getSelectedInBoundindOw() {
		return selectedInBoundindOw;
	}
	public void setSelectedInBoundindOw(String selectedInBoundindOw) {
		this.selectedInBoundindOw = selectedInBoundindOw;
	}
	public String getSelectedStaffid() {
		return selectedStaffid;
	}
	public void setSelectedStaffid(String selectedStaffid) {
		this.selectedStaffid = selectedStaffid;
	}
	public String getSalesCd() {
		return salesCd;
	}
	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public boolean isAllowAccess() {
		return allowAccess;
	}
	public void setAllowAccess(boolean allowAccess) {
		this.allowAccess = allowAccess;
	}

}
