package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;

public class StaffInfoUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5487013504191427363L;
	
	private String orderId; // add by Joyce 20120105
	
	private String salesId;
	private String salesName;
	private String salesType; // add by Joyce 20120105
	
	private String contactPhone; // add by Joyce 20120313
	private String position;
	private String callDateStr;
	private String callTimeStr;
	private String callList; // call list Id
	private String callListDesc; // add by Joyce 20120206
	
	private String lockInd; // add by Joyce 20120105
	
	private String refSalesId;
	private String refSalesName;
	private String refSalesType; // add by Joyce 20120105
	
	private String lastUpdBy;
	private String createBy;
	
	private String actionType;
	private String errormsg;
	
	private String workStatus;
	
	private String callTimeHour;
	private String callTimeMin;
	
	// add by Joyce 20120626
	private String loginChannelCd; 
	private String appDate;
	
	// add by Joyce 20120827
	private String salesCentre;
	private String refSalesCentre;
	private String salesTeam;
	private String refSalesTeam;
	
	private String hotLine;
	
	private boolean manualInputBool;
	
	/**
	 * @return the callTimeHour
	 */
	public String getCallTimeHour() {
		return callTimeHour;
	}
	/**
	 * @param callTimeHour the callTimeHour to set
	 */
	public void setCallTimeHour(String callTimeHour) {
		this.callTimeHour = callTimeHour;
	}
	/**
	 * @return the callTimeMin
	 */
	public String getCallTimeMin() {
		return callTimeMin;
	}
	/**
	 * @param callTimeMin the callTimeMin to set
	 */
	
	public void setCallTimeMin(String callTimeMin) {
		this.callTimeMin = callTimeMin;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCallList() {
		return callList;
	}
	public void setCallList(String callList) {
		this.callList = callList;
	}
	public String getRefSalesName() {
		return refSalesName;
	}
	public void setRefSalesName(String refSalesName) {
		this.refSalesName = refSalesName;
	}
	public void setCallDateStr(String callDateStr) {
		this.callDateStr = callDateStr;
	}
	public String getCallDateStr() {
		return callDateStr;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setCallTimeStr(String callTimeStr) {
		this.callTimeStr = callTimeStr;
	}
	public String getCallTimeStr() {
		return callTimeStr;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setRefSalesId(String refSalesId) {
		this.refSalesId = refSalesId;
	}
	public String getRefSalesId() {
		return refSalesId;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}
	public String getLockInd() {
		return lockInd;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setRefSalesType(String refSalesType) {
		this.refSalesType = refSalesType;
	}
	public String getRefSalesType() {
		return refSalesType;
	}
	public void setCallListDesc(String callListDesc) {
		this.callListDesc = callListDesc;
	}
	public String getCallListDesc() {
		return callListDesc;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	public String getWorkStatus() {
		return workStatus;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	/**
	 * The channel code of login sales belongs to
	 * @return the loginChannelCd
	 */
	public String getLoginChannelCd() {
		return loginChannelCd;
	}
	/**
	 * @param loginChannelCd the loginChannelCd to set
	 */
	public void setLoginChannelCd(String loginChannelCd) {
		this.loginChannelCd = loginChannelCd;
	}
	/**
	 * The application date of the order
	 * @return the appDate
	 */
	public String getAppDate() {
		return appDate;
	}
	/**
	 * @param appDate the appDate to set
	 */
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getSalesCentre() {
		return salesCentre;
	}
	public void setSalesCentre(String salesCentre) {
		this.salesCentre = salesCentre;
	}
	public String getRefSalesCentre() {
		return refSalesCentre;
	}
	public void setRefSalesCentre(String refSalesCentre) {
		this.refSalesCentre = refSalesCentre;
	}
	public String getSalesTeam() {
		return salesTeam;
	}
	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}
	public String getRefSalesTeam() {
		return refSalesTeam;
	}
	public void setRefSalesTeam(String refSalesTeam) {
		this.refSalesTeam = refSalesTeam;
	}
	public String getHotLine() {
		return hotLine;
	}
	public void setHotLine(String hotLine) {
		this.hotLine = hotLine;
	}
	public boolean isManualInputBool() {
		return manualInputBool;
	}
	public void setManualInputBool(boolean manualInputBool) {
		this.manualInputBool = manualInputBool;
	}

	
	
}
