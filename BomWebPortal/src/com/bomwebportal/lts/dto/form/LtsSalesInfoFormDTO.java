package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

public class LtsSalesInfoFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7454719687213343176L;

	private String staffName;
	private String staffId;
	private String salesCode;
	private String salesChannel;
	private String salesTeam; 
	private String salesContact;
	private String salesCenter;
	private String position;
	private String jobName;
	private String date;
	private String time;
	private String imsApplicationMethod;
	private String imsSource;
	
	private String refereeSalesId;
	private String refereeSalesName;
	private String refereeSalesCenter;
	private String refereeSalesTeam;
	
	private String boc;
	
	private boolean isCallCenter;
	private boolean isPremier;
	
	//add SOURCE_CODE ---Modified by Max.R.Meng
	private String sourceCode;
	
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
	public String getSalesCode() {
		return salesCode;
	}
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
	public String getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	public String getSalesTeam() {
		return salesTeam;
	}
	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}
	public String getSalesContact() {
		return salesContact;
	}
	public void setSalesContact(String salesContact) {
		this.salesContact = salesContact;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImsApplicationMethod() {
		return imsApplicationMethod;
	}
	public void setImsApplicationMethod(String imsApplicationMethod) {
		this.imsApplicationMethod = imsApplicationMethod;
	}
	public String getImsSource() {
		return imsSource;
	}
	public void setImsSource(String imsSource) {
		this.imsSource = imsSource;
	}
	public String getBoc() {
		return boc;
	}
	public void setBoc(String boc) {
		this.boc = boc;
	}
	public boolean isCallCenter() {
		return isCallCenter;
	}
	public void setCallCenter(boolean isCallCenter) {
		this.isCallCenter = isCallCenter;
	}
	public boolean isPremier() {
		return isPremier;
	}
	public void setPremier(boolean isPremier) {
		this.isPremier = isPremier;
	}
	public String getRefereeSalesId() {
		return refereeSalesId;
	}
	public void setRefereeSalesId(String refereeSalesId) {
		this.refereeSalesId = refereeSalesId;
	}
	public String getRefereeSalesName() {
		return refereeSalesName;
	}
	public void setRefereeSalesName(String refereeSalesName) {
		this.refereeSalesName = refereeSalesName;
	}
	public String getRefereeSalesCenter() {
		return refereeSalesCenter;
	}
	public void setRefereeSalesCenter(String refereeSalesCenter) {
		this.refereeSalesCenter = refereeSalesCenter;
	}
	public String getRefereeSalesTeam() {
		return refereeSalesTeam;
	}
	public void setRefereeSalesTeam(String refereeSalesTeam) {
		this.refereeSalesTeam = refereeSalesTeam;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getSalesCenter() {
		return salesCenter;
	}
	public void setSalesCenter(String salesCenter) {
		this.salesCenter = salesCenter;
	}
	
}
