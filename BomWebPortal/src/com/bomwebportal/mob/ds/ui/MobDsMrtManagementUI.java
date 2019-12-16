package com.bomwebportal.mob.ds.ui;

import java.io.Serializable;

public class MobDsMrtManagementUI  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6320133548532843836L;
	private String requestNum;
	private String msisdnlvl;
	private String staffId;
	private String storeCode;
	private String teamCode;
	private String searchStoreCode;
	private String searchTeamCode;
	
	private String actionType;

	public String getRequestNum() {
		return requestNum;
	}
	public void setRequestNum(String requestNum) {
		this.requestNum = requestNum;
	}
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getSearchStoreCode() {
		return searchStoreCode;
	}
	public void setSearchStoreCode(String searchStoreCode) {
		this.searchStoreCode = searchStoreCode;
	}
	public String getSearchTeamCode() {
		return searchTeamCode;
	}
	public void setSearchTeamCode(String searchTeamCode) {
		this.searchTeamCode = searchTeamCode;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}
