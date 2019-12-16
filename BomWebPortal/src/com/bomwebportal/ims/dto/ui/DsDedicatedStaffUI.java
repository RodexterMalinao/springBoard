package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.ims.dto.DsDedicatedStaffDTO;

public class DsDedicatedStaffUI extends DsDedicatedStaffDTO {

	private static final long serialVersionUID = 4645607032974861943L;

	private String staffListInput;

	private List<DsDedicatedStaffUI> staffList;

	public String getStaffListInput() {
		return staffListInput;
	}

	public void setStaffListInput(String staffListInput) {
		this.staffListInput = staffListInput;
	}

	public void setStaffList(List<DsDedicatedStaffUI> staffList) {
		this.staffList = staffList;
	}

	public List<DsDedicatedStaffUI> getStaffList() {
		return staffList;
	}
}