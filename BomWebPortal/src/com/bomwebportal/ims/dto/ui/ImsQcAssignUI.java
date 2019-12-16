package com.bomwebportal.ims.dto.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.QcStaffDTO;


public class ImsQcAssignUI extends QcStaffDTO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8128896840461783962L;
	
	
	
	//Assignment
	private String orderId;
	private String assignee;
	private String assignDate;
	private String sys_f;
	private String lob;
/*	private String createDate;
	private String createBy;
	private String lastUpDate;
	private String lastUpBy;*/
	
	
	public String getOrderIds() {
		return orderId;
	}
	public void setOrderIds(String orderId) {
		this.orderId = orderId;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public void setSys_f(String sys_f) {
		this.sys_f = sys_f;
	}
	public String getSys_f() {
		return sys_f;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getLob() {
		return lob;
	}
		
}