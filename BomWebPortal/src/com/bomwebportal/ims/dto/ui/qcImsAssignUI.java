package com.bomwebportal.ims.dto.ui;

import java.util.List;


public class qcImsAssignUI extends CustomerUI{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8128896840461783962L;
	
	private String qcStaffId;
	private String status;
	private String todayOsOrders;
	private String todayAssignedOrders;
	private String past7daysAssignedOrders;
	private String totalOrders;
	private List<String> skills;
	private String staffName;
	private String channelCD;
	private String channelID;
	
	
	public String getQcStaffId() {
		return qcStaffId;
	}
	public void setQcStaffId(String qcStaffId) {
		this.qcStaffId = qcStaffId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<String>  getSkills() {
		return skills;
	}
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setChannelCD(String channelCD) {
		this.channelCD = channelCD;
	}
	public String getChannelCD() {
		return channelCD;
	}
	public void setChannelID(String channelID) {
		this.channelID = channelID;
	}
	public String getChannelID() {
		return channelID;
	}
	
	
	
}