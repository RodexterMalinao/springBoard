package com.bomwebportal.mob.ccs.dto.ui;

//import java.sql.Date;
import java.util.Date;

public class MobCcsFalloutUI {
	
	String falloutCode;
	String remarks;
	String staffId;
	String falloutDesc;
	Date createDate;
	Date occuranceDate;
	
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the falloutDesc
	 */
	public String getFalloutDesc() {
		return falloutDesc;
	}
	/**
	 * @param falloutDesc the falloutDesc to set
	 */
	public void setFalloutDesc(String falloutDesc) {
		this.falloutDesc = falloutDesc;
	}
	/**
	 * @return the falloutCode
	 */
	public String getFalloutCode() {
		return falloutCode;
	}
	/**
	 * @param falloutCode the falloutCode to set
	 */
	public void setFalloutCode(String falloutCode) {
		this.falloutCode = falloutCode;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the staffId
	 */
	public String getStaffId() {
		return staffId;
	}
	/**
	 * @param staffId the staffId to set
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	/**
	 * @return the occuranceDate
	 */
	public Date getOccuranceDate() {
		return occuranceDate;
	}
	/**
	 * @param occuranceDate the occuranceDate to set
	 */
	public void setOccuranceDate(Date occuranceDate) {
		this.occuranceDate = occuranceDate;
	}
	
}
