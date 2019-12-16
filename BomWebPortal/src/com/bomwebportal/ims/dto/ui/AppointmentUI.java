package com.bomwebportal.ims.dto.ui;

import java.util.Date;
import java.util.Map;

import com.bomwebportal.ims.dto.AppointmentDTO;

public class AppointmentUI extends AppointmentDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7995832450328081643L;
	private String ActionInd;
	
	private String EstimatedSrd;
	private String EstSrdReason;
	private String TargetInstallDate;
	private String TargetCommDate;
	private String AdditionRemarks;
	private String TimeSlot;
	private String TimeSlotDisplay;
	private String BookNotAllowed;
	private String BlackListInd;
	private String ResourceInd;
	private String PreInstOrder;
	private Date AppntStartDateDisplay;
	private Date AppntEndDateDisplay;
	private String SuspendCd;
	private String CancelCd;
	private String SubmitTag;
	private Date SrvReqDate;
	private String HosOrPhInd;
	private String PrivateInd;
	private String errorMsg;
	private String BmoRmk;

	//for direct sales only
	private String waiveCoolingOffPeriod = "N";
	
	//gift require date
	private String giftCommDate;
	
	
	private String serviceEffectiveDateStr;

	private Date serviceEffectiveDate;
	
	private String nextBillDate;
	
	private String specialRequest;
	private String specialRequestCd;
	private String specialRequestRmk;
	private Map<String, String> specialRequestMap;
	private Map<String,  Map<String, String>> specialRequestMapAllLang;
	private String isNowRetAmendTvOnly;
	
	private String timeslotConfirmed;
	
	// BOM2019039
	private String srdAvailble;
	
	public String getBmoRmk() {
		return BmoRmk;
	}

	public void setBmoRmk(String bmoRmk) {
		BmoRmk = bmoRmk;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public String getAdditionRemarks() {
		return AdditionRemarks;
	}

	public void setAdditionRemarks(String additionRemarks) {
		AdditionRemarks = additionRemarks;
	}

	public String getEstimatedSrd() {
		return EstimatedSrd;
	}

	public void setEstimatedSrd(String estimatedSrd) {
		EstimatedSrd = estimatedSrd;
	}

	public String getTargetCommDate() {
		return TargetCommDate;
	}

	public void setTargetCommDate(String targetCommDate) {
		TargetCommDate = targetCommDate;
	}

	public String getTimeSlot() {
		return TimeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		TimeSlot = timeSlot;
	}

	public String getBookNotAllowed() {
		return BookNotAllowed;
	}

	public void setBookNotAllowed(String bookNotAllowed) {
		BookNotAllowed = bookNotAllowed;
	}

	public String getPreInstOrder() {
		return PreInstOrder;
	}

	public void setPreInstOrder(String preInstOrder) {
		PreInstOrder = preInstOrder;
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

	public Date getAppntEndDateDisplay() {
		return AppntEndDateDisplay;
	}

	public void setAppntEndDateDisplay(Date appntEndDateDisplay) {
		AppntEndDateDisplay = appntEndDateDisplay;
	}

	public String getSuspendCd() {
		return SuspendCd;
	}

	public void setSuspendCd(String suspendCd) {
		SuspendCd = suspendCd;
	}

	public String getCancelCd() {
		return CancelCd;
	}

	public void setCancelCd(String cancelCd) {
		CancelCd = cancelCd;
	}

	public String getSubmitTag() {
		return SubmitTag;
	}

	public void setSubmitTag(String submitTag) {
		SubmitTag = submitTag;
	}

	public String getBlackListInd() {
		return BlackListInd;
	}

	public void setBlackListInd(String blackListInd) {
		BlackListInd = blackListInd;
	}

	public String getResourceInd() {
		return ResourceInd;
	}

	public void setResourceInd(String resourceInd) {
		ResourceInd = resourceInd;
	}

	public Date getSrvReqDate() {
		return SrvReqDate;
	}

	public void setSrvReqDate(Date srvReqDate) {
		SrvReqDate = srvReqDate;
	}

	public String getEstSrdReason() {
		return EstSrdReason;
	}

	public void setEstSrdReason(String estSrdReason) {
		EstSrdReason = estSrdReason;
	}

	public String getHosOrPhInd() {
		return HosOrPhInd;
	}

	public void setHosOrPhInd(String hosOrPhInd) {
		HosOrPhInd = hosOrPhInd;
	}

	public String getPrivateInd() {
		return PrivateInd;
	}

	public void setPrivateInd(String privateInd) {
		PrivateInd = privateInd;
	}

	public void setWaiveCoolingOffPeriod(String waiveCoolingOffPeriod) {
		this.waiveCoolingOffPeriod = waiveCoolingOffPeriod;
	}

	public String getWaiveCoolingOffPeriod() {
		return waiveCoolingOffPeriod;
	}

	public void setGiftCommDate(String giftCommDate) {
		this.giftCommDate = giftCommDate;
	}

	public String getGiftCommDate() {
		return giftCommDate;
	}

	public String getServiceEffectiveDateStr() {
		return serviceEffectiveDateStr;
	}

	public void setServiceEffectiveDateStr(String serviceEffectiveDateStr) {
		this.serviceEffectiveDateStr = serviceEffectiveDateStr;
	}

	public Date getServiceEffectiveDate() {
		return serviceEffectiveDate;
	}

	public void setServiceEffectiveDate(Date serviceEffectiveDate) {
		this.serviceEffectiveDate = serviceEffectiveDate;
	}

	public String getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}

	public String getSpecialRequestCd() {
		return specialRequestCd;
	}

	public void setSpecialRequestCd(String specialRequestCd) {
		this.specialRequestCd = specialRequestCd;
	}

	public String getSpecialRequestRmk() {
		return specialRequestRmk;
	}

	public void setSpecialRequestRmk(String specialRequestRmk) {
		this.specialRequestRmk = specialRequestRmk;
	}

	public Map<String, String> getSpecialRequestMap() {
		return specialRequestMap;
	}

	public void setSpecialRequestMap(Map<String, String> specialRequestMap) {
		this.specialRequestMap = specialRequestMap;
	}

	public Map<String, Map<String, String>> getSpecialRequestMapAllLang() {
		return specialRequestMapAllLang;
	}

	public void setSpecialRequestMapAllLang(
			Map<String, Map<String, String>> specialRequestMapAllLang) {
		this.specialRequestMapAllLang = specialRequestMapAllLang;
	}

	public String getNextBillDate() {
		return nextBillDate;
	}

	public void setNextBillDate(String nextBillDate) {
		this.nextBillDate = nextBillDate;
	}

	public String getIsNowRetAmendTvOnly() {
		return isNowRetAmendTvOnly;
	}

	public void setIsNowRetAmendTvOnly(String isNowRetAmendTvOnly) {
		this.isNowRetAmendTvOnly = isNowRetAmendTvOnly;
	}

	public void setTimeslotConfirmed(String timeslotConfirmed) {
		this.timeslotConfirmed = timeslotConfirmed;
	}

	public String getTimeslotConfirmed() {
		return timeslotConfirmed;
	}

	public String getSrdAvailble() {
		return srdAvailble;
	}

	public void setSrdAvailble(String srdAvailble) {
		this.srdAvailble = srdAvailble;
	}

}
