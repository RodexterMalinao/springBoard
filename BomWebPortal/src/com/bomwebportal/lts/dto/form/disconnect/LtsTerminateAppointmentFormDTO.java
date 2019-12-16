package com.bomwebportal.lts.dto.form.disconnect;

import java.io.Serializable;

import org.apache.axis.utils.StringUtils;

import com.bomwebportal.lts.util.LtsAppointmentHelper;

public class LtsTerminateAppointmentFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 235610963908934492L;
	//Appointment Details
	private String applicationDate;
	private String earliestSrdReason;
	private String preBookSerialNum;
	private String appntDate;
	private String appntTimeSlotAndType;
	private String submitInd;
	private String appntErrorMsg;
	private String ceaseRentalDate;
	//Contact Details
	private String custContactName;
	private String custContactNum;
	private String custContactMobileNum;
	//External Address
	private boolean externalCollection;
	private String alternateAddress;
	private String alternateAddressDetails;
	private String baQuickSearch;
	private String newBillingAddress;
	//Field Add-on Remarks
	private String remarks;
	//Suspend
	private String suspendReason;
	//Indicators
	private String confirmedInd;
	private boolean fieldVisitRequired;
	private boolean forceAppntTime;
	private boolean fiveNa;
	private boolean allowBackDate;
	private boolean backDate;
	private boolean allowFiveNa = true;
	
	private String errorMsg;
	private boolean allowProceed;

	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getPreBookSerialNum() {
		return preBookSerialNum;
	}
	public void setPreBookSerialNum(String preBookSerialNum) {
		this.preBookSerialNum = preBookSerialNum;
	}
	public String getSubmitInd() {
		return submitInd;
	}
	public void setSubmitInd(String submitInd) {
		this.submitInd = submitInd;
	}
	public String getCeaseRentalDate() {
		return ceaseRentalDate;
	}
	public void setCeaseRentalDate(String ceaseRentalDate) {
		this.ceaseRentalDate = ceaseRentalDate;
	}
	public String getCustContactName() {
		return custContactName;
	}
	public void setCustContactName(String custContactName) {
		this.custContactName = custContactName;
	}
	public String getCustContactNum() {
		return custContactNum;
	}
	public void setCustContactNum(String custContactNum) {
		this.custContactNum = custContactNum;
	}
	public String getCustContactMobileNum() {
		return custContactMobileNum;
	}

	public void setCustContactMobileNum(String custContactMobileNum) {
		this.custContactMobileNum = custContactMobileNum;
	}

	public boolean isExternalCollection() {
		return externalCollection;
	}
	public void setExternalCollection(boolean externalCollection) {
		this.externalCollection = externalCollection;
	}
	public String getAlternateAddress() {
		return alternateAddress;
	}
	public void setAlternateAddress(String alternateAddress) {
		this.alternateAddress = alternateAddress;
	}
	public String getAlternateAddressDetails() {
		return alternateAddressDetails;
	}
	public void setAlternateAddressDetails(String alternateAddressDetails) {
		this.alternateAddressDetails = alternateAddressDetails;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSuspendReason() {
		return suspendReason;
	}
	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}
	public String getBaQuickSearch() {
		return baQuickSearch;
	}
	public void setBaQuickSearch(String baQuickSearch) {
		this.baQuickSearch = baQuickSearch;
	}
	public String getNewBillingAddress() {
		return newBillingAddress;
	}
	public void setNewBillingAddress(String newBillingAddress) {
		this.newBillingAddress = newBillingAddress;
	}
	public String getConfirmedInd() {
		return confirmedInd;
	}
	public void setConfirmedInd(String confirmedInd) {
		this.confirmedInd = confirmedInd;
	}
	public String getEarliestSrdReason() {
		return earliestSrdReason;
	}
	public void setEarliestSrdReason(String earliestSrdReason) {
		this.earliestSrdReason = earliestSrdReason;
	}

	public boolean isFiveNa() {
		return fiveNa;
	}

	public void setFiveNa(boolean fiveNa) {
		this.fiveNa = fiveNa;
	}

	public boolean isFieldVisitRequired() {
		return fieldVisitRequired;
	}

	public void setFieldVisitRequired(boolean fieldVisitRequired) {
		this.fieldVisitRequired = fieldVisitRequired;
	}
	
	public String getAppntTimeSlot() {
		if(StringUtils.isEmpty(this.appntTimeSlotAndType)){
			return null;
		}else{
			String[] timeSlotAndType = this.appntTimeSlotAndType.split(LtsAppointmentHelper.TIMESLOT_DELIMITER); //StringUtils.split(this.appntTimeSlotAndType, "|");
			return timeSlotAndType[0];
		}
	}

	public String getAppntTimeSlotType() {
		if(StringUtils.isEmpty(this.appntTimeSlotAndType)){
			return null;
		}else{
			String[] timeSlotAndType = this.appntTimeSlotAndType.split(LtsAppointmentHelper.TIMESLOT_DELIMITER);
			if(timeSlotAndType.length > 1){
				return timeSlotAndType[1];
			}else{
				return null;
			}
		}
	}
	
	public String getAppntTimeSlotAndType() {
		return appntTimeSlotAndType;
	}

	public void setAppntTimeSlotAndType(String appntTimeSlotAndType) {
		this.appntTimeSlotAndType = appntTimeSlotAndType;
	}

	public String getAppntDate() {
		return appntDate;
	}

	public void setAppntDate(String appntDate) {
		this.appntDate = appntDate;
	}
	public boolean isAllowBackDate() {
		return allowBackDate;
	}
	public void setAllowBackDate(boolean allowBackDate) {
		this.allowBackDate = allowBackDate;
	}
	public boolean isBackDate() {
		return backDate;
	}
	public void setBackDate(boolean backDate) {
		this.backDate = backDate;
	}
	public boolean isAllowProceed() {
		return allowProceed;
	}
	public void setAllowProceed(boolean allowProceed) {
		this.allowProceed = allowProceed;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String notAllowProceedMsg) {
		this.errorMsg = notAllowProceedMsg;
	}
	public String getAppntErrorMsg() {
		return appntErrorMsg;
	}
	public void setAppntErrorMsg(String appntErrorMsg) {
		this.appntErrorMsg = appntErrorMsg;
	}
	public boolean isAllowFiveNa() {
		return allowFiveNa;
	}
	public void setAllowFiveNa(boolean allowFiveNa) {
		this.allowFiveNa = allowFiveNa;
	}
	public boolean isForceAppntTime() {
		return forceAppntTime;
	}
	public void setForceAppntTime(boolean forceAppntTime) {
		this.forceAppntTime = forceAppntTime;
	}
	
}
