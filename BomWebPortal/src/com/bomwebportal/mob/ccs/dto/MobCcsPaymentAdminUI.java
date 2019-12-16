package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MobCcsPaymentAdminUI implements Serializable {
	private String startDateStr;
	private Date startDate;
	private String endDateStr;
	private Date endDate;
	private List<CodeLkupDTO> payMethodList;
	private String payMethod;
	private String mrt;
	private List<MobCcsPaymentTransDTO> mobCcsPaymentTransDTOList;
	private String actionType;
	private boolean validated;
	private String mobCcSPaymentAdminError;
	public String getStartDateStr() {
		return startDateStr;
	}
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<CodeLkupDTO> getPayMethodList() {
		return payMethodList;
	}
	public void setPayMethodList(List<CodeLkupDTO> payMethodList) {
		this.payMethodList = payMethodList;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}	
	public String getMrt() {
		return mrt;
	}
	public void setMrt(String mrt) {
		this.mrt = mrt;
	}	
	public List<MobCcsPaymentTransDTO> getMobCcsPaymentTransDTOList() {
		return mobCcsPaymentTransDTOList;
	}
	public void setMobCcsPaymentTransDTOList(
			List<MobCcsPaymentTransDTO> mobCcsPaymentTransDTOList) {
		this.mobCcsPaymentTransDTOList = mobCcsPaymentTransDTOList;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}	
	public boolean isValidated() {
		return validated;
	}
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
	public String getMobCcSPaymentAdminError() {
		return mobCcSPaymentAdminError;
	}
	public void setMobCcSPaymentAdminError(String mobCcSPaymentAdminError) {
		this.mobCcSPaymentAdminError = mobCcSPaymentAdminError;
	}

	
}
