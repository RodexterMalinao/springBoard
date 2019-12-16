package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class ResourceSlotDTO implements Serializable {

	private static final long serialVersionUID = 6379801974143236992L;
	
	private String apptTimeSlot = null;
	private String apptTimeSlotType = null;
	private String availableInd = null;
	private String restrictInd = null;
	

	public String getApptTimeSlot() {
		return apptTimeSlot;
	}

	public void setApptTimeSlot(String apptTimeSlot) {
		this.apptTimeSlot = apptTimeSlot;
	}

	public String getApptTimeSlotType() {
		return apptTimeSlotType;
	}

	public void setApptTimeSlotType(String apptTimeSlotType) {
		this.apptTimeSlotType = apptTimeSlotType;
	}

	public String getAvailableInd() {
		return availableInd;
	}

	public void setAvailableInd(String availableInd) {
		this.availableInd = availableInd;
	}

	public String getRestrictInd() {
		return restrictInd;
	}

	public void setRestrictInd(String restrictInd) {
		this.restrictInd = restrictInd;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" apptTimeSlot = ");
		sb.append(this.apptTimeSlot);
		sb.append(" apptTimeSlotType = ");
		sb.append(this.apptTimeSlotType);
		sb.append(" availableInd = ");
		sb.append(this.availableInd);
		sb.append(" restrictInd = ");
		sb.append(this.restrictInd);
		return sb.toString();
	}
}
