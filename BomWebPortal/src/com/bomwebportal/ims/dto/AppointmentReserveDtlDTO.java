package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;

public class AppointmentReserveDtlDTO implements Serializable{

	/**
	 * 
	 */
	
	private List<AppointmentTimeSlotDTO> timeslots;
	private String restrictedTimeslots;
	private int returnValue;
	private int errorCode;
	private String errorMsg;
	
	public List<AppointmentTimeSlotDTO> getTimeslots() {
		return timeslots;
	}
	public void setTimeslots(List<AppointmentTimeSlotDTO> timeslots) {
		this.timeslots = timeslots;
	}
	public String getRestrictedTimeslots() {
		return restrictedTimeslots;
	}
	public void setRestrictedTimeslots(String restrictedTimeslots) {
		this.restrictedTimeslots = restrictedTimeslots;
	}
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
	
}
