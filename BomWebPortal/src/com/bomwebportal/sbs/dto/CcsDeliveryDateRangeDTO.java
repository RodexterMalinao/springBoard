package com.bomwebportal.sbs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CcsDeliveryDateRangeDTO implements Serializable {

	private static final long serialVersionUID = -4330781616948043705L;
	private Date startDate;
	private Date endDate;
	private String phDateString;
	private String startDateString;
	private String endDateString;
	private String timeSlot;
	private String errText;
	private Integer ruleNo;
	private Integer retValue;
	private Integer errCode;
	private List<String> normalDateList = new ArrayList<String>();
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getPhDateString() {
		return phDateString;
	}
	
	public void setPhDateString(String phDateString) {
		this.phDateString = phDateString;
	}
	
	public String getStartDateString() {
		return startDateString;
	}
	
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	
	public String getEndDateString() {
		return endDateString;
	}
	
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	
	public String getTimeSlot() {
		return timeSlot;
	}
	
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	public String getErrText() {
		return errText;
	}
	
	public void setErrText(String errText) {
		this.errText = errText;
	}

	public Integer getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(Integer ruleNo) {
		this.ruleNo = ruleNo;
	}

	public Integer getRetValue() {
		return retValue;
	}
	
	public void setRetValue(Integer retValue) {
		this.retValue = retValue;
	}
	
	public Integer getErrCode() {
		return errCode;
	}
	
	public void setErrCode(Integer errCode) {
		this.errCode = errCode;
	}
	
	public List<String> getNormalDateList() {
		return normalDateList;
	}
	
	public void setNormalDateList(List<String> normalDateList) {
		this.normalDateList = normalDateList;
	}

}