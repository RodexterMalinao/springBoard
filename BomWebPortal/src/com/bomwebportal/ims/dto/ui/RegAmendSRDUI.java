package com.bomwebportal.ims.dto.ui;

import java.util.Date;

import com.bomwebportal.ims.dto.AppointmentSRDDTO;


public class RegAmendSRDUI extends AppointmentSRDDTO {

	private static final long serialVersionUID = -2273951756511687910L;


	private String ErrorCode;
	private String ApptDate;
	private String ApptTime;
	private String CustomerFullAddr;
	private String Title;
	private String LastName;
	
	private String EstimatedSrd;
	private String ActionInd;
	private String TargetInstallDate;

	private String SerialNum;
	private String AppntType;

	private Date AppntEndDateDisplay;
	private String TimeSlot;
	private String TimeSlotDisplay;
	private Date AppntStartDateDisplay;
	
	private String BlackListInd;
	private Date SrvReqDate;
	
	private Date AppntStartDate;
	private Date AppntEndDate;

	private Date ApplicationDate;
	
	private String OrigEarliestSrd;
	
	
	
	private String AmendSrdPendingInd;
	
	public String getAmendSrdPendingInd() {
		return AmendSrdPendingInd;
	}
	public void setAmendSrdPendingInd(String amendSrdPendingInd) {
		AmendSrdPendingInd = amendSrdPendingInd;
	}
	
	
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getApptDate() {
		return ApptDate;
	}
	public void setApptDate(String apptDate) {
		ApptDate = apptDate;
	}
	public String getApptTime() {
		return ApptTime;
	}
	public void setApptTime(String apptTime) {
		ApptTime = apptTime;
	}
	public String getCustomerFullAddr() {
		return CustomerFullAddr;
	}
	public void setCustomerFullAddr(String customerFullAddr) {
		CustomerFullAddr = customerFullAddr;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEstimatedSrd() {
		return EstimatedSrd;
	}
	public void setEstimatedSrd(String estimatedSrd) {
		EstimatedSrd = estimatedSrd;
	}
	public String getActionInd() {
		return ActionInd;
	}
	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}
	public String getTargetInstallDate() {
		return TargetInstallDate;
	}
	public void setTargetInstallDate(String targetInstallDate) {
		TargetInstallDate = targetInstallDate;
	}
	public String getSerialNum() {
		return SerialNum;
	}
	public void setSerialNum(String serialNum) {
		SerialNum = serialNum;
	}
	public String getAppntType() {
		return AppntType;
	}
	public void setAppntType(String appntType) {
		AppntType = appntType;
	}
	public Date getAppntEndDateDisplay() {
		return AppntEndDateDisplay;
	}
	public void setAppntEndDateDisplay(Date appntEndDateDisplay) {
		AppntEndDateDisplay = appntEndDateDisplay;
	}
	public String getTimeSlot() {
		return TimeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		TimeSlot = timeSlot;
	}
	public String getTimeSlotDisplay() {
		return TimeSlotDisplay;
	}
	public void setTimeSlotDisplay(String timeSlotDisplay) {
		TimeSlotDisplay = timeSlotDisplay;
	}
	public Date getAppntStartDateDisplay() {
		return AppntStartDateDisplay;
	}
	public void setAppntStartDateDisplay(Date appntStartDateDisplay) {
		AppntStartDateDisplay = appntStartDateDisplay;
	}
	public String getBlackListInd() {
		return BlackListInd;
	}
	public void setBlackListInd(String blackListInd) {
		BlackListInd = blackListInd;
	}
	public Date getSrvReqDate() {
		return SrvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		SrvReqDate = srvReqDate;
	}
	public Date getAppntStartDate() {
		return AppntStartDate;
	}
	public void setAppntStartDate(Date appntStartDate) {
		AppntStartDate = appntStartDate;
	}
	public Date getAppntEndDate() {
		return AppntEndDate;
	}
	public void setAppntEndDate(Date appntEndDate) {
		AppntEndDate = appntEndDate;
	}
	public Date getApplicationDate() {
		return ApplicationDate;
	}
	public void setApplicationDate(Date applicationDate) {
		ApplicationDate = applicationDate;
	}
	public String getOrigEarliestSrd() {
		return OrigEarliestSrd;
	}
	public void setOrigEarliestSrd(String origEarliestSrd) {
		OrigEarliestSrd = origEarliestSrd;
	}


}
