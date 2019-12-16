package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;

public class LtsAcqBasketSelectionFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5714138082719293813L;

	private String selectedBasketId;
	private String selectedBasketChannel;
	private String selectedDeviceType;
	private String selectedType;
	private String searchingStaffPlan;
	
	private String[] filterBridgingMonth;
	private boolean filterCommitPeriodGt24mth;
	private boolean filterCommitPeriod18mth;
	private boolean filterCommitPeriodLt12mth;
	private String filterDeviceType;
	private String filterProjectCd;
	private String peInd;
	
	private String pcdSbid;
	private String pcdSbidErrMsg;
	
	private boolean staffPlan;
	private String staffNumber;
	
	private String currentTab;
	
	private boolean isDelFreeBundle;
	
	private String osType;
	
	public String getSelectedBasketId() {
		return selectedBasketId;
	}
	public void setSelectedBasketId(String selectedBasketId) {
		this.selectedBasketId = selectedBasketId;
	}
	public String getSelectedBasketChannel() {
		return selectedBasketChannel;
	}
	public void setSelectedBasketChannel(String selectedBasketChannel) {
		this.selectedBasketChannel = selectedBasketChannel;
	}
	public String getSelectedDeviceType() {
		return selectedDeviceType;
	}
	public void setSelectedDeviceType(String selectedDeviceType) {
		this.selectedDeviceType = selectedDeviceType;
	}
	public String getSelectedType() {
		return selectedType;
	}
	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}
	public String[] getFilterBridgingMonth() {
		return filterBridgingMonth;
	}
	public void setFilterBridgingMonth(String[] filterBridgingMonth) {
		this.filterBridgingMonth = filterBridgingMonth;
	}
	public boolean isFilterCommitPeriodGt24mth() {
		return filterCommitPeriodGt24mth;
	}
	public void setFilterCommitPeriodGt24mth(boolean filterCommitPeriodGt24mth) {
		this.filterCommitPeriodGt24mth = filterCommitPeriodGt24mth;
	}
	public boolean isFilterCommitPeriod18mth() {
		return filterCommitPeriod18mth;
	}
	public void setFilterCommitPeriod18mth(boolean filterCommitPeriod18mth) {
		this.filterCommitPeriod18mth = filterCommitPeriod18mth;
	}
	public boolean isFilterCommitPeriodLt12mth() {
		return filterCommitPeriodLt12mth;
	}
	public void setFilterCommitPeriodLt12mth(boolean filterCommitPeriodLt12mth) {
		this.filterCommitPeriodLt12mth = filterCommitPeriodLt12mth;
	}
	public String getFilterDeviceType() {
		return filterDeviceType;
	}
	public void setFilterDeviceType(String filterDeviceType) {
		this.filterDeviceType = filterDeviceType;
	}
	public boolean isStaffPlan() {
		return staffPlan;
	}
	public void setStaffPlan(boolean staffPlan) {
		this.staffPlan = staffPlan;
	}
	public String getCurrentTab() {
		return currentTab;
	}
	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}
	public String getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}
	public String getSearchingStaffPlan() {
		return searchingStaffPlan;
	}
	public void setSearchingStaffPlan(String searchingStaffPlan) {
		this.searchingStaffPlan = searchingStaffPlan;
	}
	public String getFilterProjectCd() {
		return filterProjectCd;
	}
	public void setFilterProjectCd(String filterProjectCd) {
		this.filterProjectCd = filterProjectCd;
	}
	public String getPeInd() {
		return peInd;
	}
	public void setPeInd(String peInd) {
		this.peInd = peInd;
	}
	public String getPcdSbid() {
		return pcdSbid;
	}
	public void setPcdSbid(String pcdSbid) {
		this.pcdSbid = pcdSbid;
	}
	public String getPcdSbidErrMsg() {
		return pcdSbidErrMsg;
	}
	public void setPcdSbidErrMsg(String pcdSbidErrMsg) {
		this.pcdSbidErrMsg = pcdSbidErrMsg;
	}
	public boolean isDelFreeBundle() {
		return isDelFreeBundle;
	}
	public void setDelFreeBundle(boolean isDelFreeBundle) {
		this.isDelFreeBundle = isDelFreeBundle;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	
}
