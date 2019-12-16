package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class AppointmentTimeSlotDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6373340224055349177L;
	
	private String ApptDate;
	private String TimeSlot;
	private String TimeSlotDisplay;
	private String SlotType;
	private String resourceInMinute;
	private int returnValue;
	private int errorCode;
	private String errorMsg;
	
	public int getReturnValue() {
		return returnValue;
	}
	
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getApptDate() {
		return ApptDate;
	}
	public void setApptDate(String apptDate) {
		ApptDate = apptDate;
	}
	public String getTimeSlot() {
		return TimeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		TimeSlot = timeSlot;
	}
	public String getSlotType() {
		return SlotType;
	}
	public void setSlotType(String slotType) {
		SlotType = slotType;
	}
	public String getResourceInMinute() {
		return resourceInMinute;
	}
	public void setResourceInMinute(String resourceInMinute) {
		this.resourceInMinute = resourceInMinute;
	}
	public String getTimeSlotDisplay() {
		return TimeSlotDisplay;
	}
	public void setTimeSlotDisplay(String timeSlotDisplay) {
		TimeSlotDisplay = timeSlotDisplay;
	}
	
	
	
	
	
}
