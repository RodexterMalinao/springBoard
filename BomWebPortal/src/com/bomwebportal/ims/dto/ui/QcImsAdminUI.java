package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.dto.QcStaffDTO;

public class QcImsAdminUI extends QcStaffDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7827078335006944743L;

	private String ActionInd;
	private List<QcImsAdminUI> staffList;
	private String staffId;
	
	public void setStaffList(List<QcImsAdminUI> staffList) {
		this.staffList = staffList;
	}
	public List<QcImsAdminUI> getStaffList() {
		return staffList;
	}
	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}
	public String getActionInd() {
		return ActionInd;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffId() {
		return staffId;
	}
	
}