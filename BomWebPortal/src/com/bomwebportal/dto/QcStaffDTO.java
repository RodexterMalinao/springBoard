package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.List;

public class QcStaffDTO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2995849881651340329L;
	
	//Enquiry
	private String qcStaffId;
	private String status;
	private String todayOsOrders;
	private String todayAssignedOrders;
	private String past7daysAssignedOrders;
	private String totalOrders;
	private String skills;
	private String staffName;
	private String staffId;
	private List<String> skillSet;
	
	private String createDate;
	private String createBy;
	private String lastUpDate;
	private String lastUpBy;
	

	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpDate() {
		return lastUpDate;
	}
	public void setLastUpDate(String lastUpDate) {
		this.lastUpDate = lastUpDate;
	}
	public String getLastUpBy() {
		return lastUpBy;
	}
	public void setLastUpBy(String lastUpBy) {
		this.lastUpBy = lastUpBy;
	}
	public String getQcStaffId() {
		return qcStaffId;
	}
	public void setQcStaffId(String qcStaffId) {
		this.qcStaffId = qcStaffId;
	}
	public String getTodayOsOrders() {
		return todayOsOrders;
	}
	public void setTodayOsOrders(String todayOsOrders) {
		this.todayOsOrders = todayOsOrders;
	}
	public String getTodayAssignedOrders() {
		return todayAssignedOrders;
	}
	public void setTodayAssignedOrders(String todayAssignedOrders) {
		this.todayAssignedOrders = todayAssignedOrders;
	}
	public String getPast7daysAssignedOrders() {
		return past7daysAssignedOrders;
	}
	public void setPast7daysAssignedOrders(String past7daysAssignedOrders) {
		this.past7daysAssignedOrders = past7daysAssignedOrders;
	}
	public String getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(String totalOrders) {
		this.totalOrders = totalOrders;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
		
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public List<String> getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(List<String> skillSet) {
		this.skillSet = skillSet;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
}
