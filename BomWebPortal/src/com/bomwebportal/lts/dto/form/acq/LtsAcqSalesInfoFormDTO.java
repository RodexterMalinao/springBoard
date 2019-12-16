package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;

public class LtsAcqSalesInfoFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -26624160700449803L;
	/**
	 * 
	 */
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
	private String refStaffId;
	private String refStaffName;
	private String refSalesTeam;
	private String refSalesCenter;
	private String dnis;
	private String boc;
	private boolean isCallCenter;
	private boolean isPremier;
	private boolean displayReferee;
	private boolean isDirectSales;
	private boolean modifySalesInfo;
	private boolean vaildateMobileNo;
	
	//add SOURCE_CODE ---Modified by Max.R.Meng
	private String sourceCode;
	
	public String getDnis() {
		return dnis;
	}
	public void setDnis(String dnis) {
		this.dnis = dnis;
	}
	public String getRefStaffId() {
		return refStaffId;
	}
	public void setRefStaffId(String refStaffId) {
		this.refStaffId = refStaffId;
	}
	public String getRefStaffName() {
		return refStaffName;
	}
	public void setRefStaffName(String refStaffName) {
		this.refStaffName = refStaffName;
	}
	public String getRefSalesTeam() {
		return refSalesTeam;
	}
	public void setRefSalesTeam(String refSalesTeam) {
		this.refSalesTeam = refSalesTeam;
	}
	public String getRefSalesCenter() {
		return refSalesCenter;
	}
	public void setRefSalesCenter(String refSalesCenter) {
		this.refSalesCenter = refSalesCenter;
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
	public boolean isDisplayReferee() {
		return displayReferee;
	}
	public void setDisplayReferee(boolean displayReferee) {
		this.displayReferee = displayReferee;
	}
	public boolean isDirectSales() {
		return isDirectSales;
	}
	public void setDirectSales(boolean isDirectSales) {
		this.isDirectSales = isDirectSales;
	}
	public boolean isModifySalesInfo() {
		return modifySalesInfo;
	}
	public void setModifySalesInfo(boolean modifySalesInfo) {
		this.modifySalesInfo = modifySalesInfo;
	}
	public boolean isVaildateMobileNo() {
		return vaildateMobileNo;
	}
	public void setVaildateMobileNo(boolean vaildateMobileNo) {
		this.vaildateMobileNo = vaildateMobileNo;
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
