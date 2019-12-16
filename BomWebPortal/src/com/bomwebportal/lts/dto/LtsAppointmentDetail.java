package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.axis.utils.StringUtils;

import com.bomwebportal.lts.util.LtsAppointmentHelper;

public class LtsAppointmentDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 446858728857197742L;

	private LtsSRDDTO earliestSRD;
	private String earliestSRDReason;
	private String appntDate;
	private boolean timeSlotRequiredInd = true;
	private String appntTimeSlotAndType;
	private boolean cutOverInd;
	private String cutOverDate;
	private String cutOverTime;
	
	public LtsSRDDTO getEarliestSRD() {
		return earliestSRD;
	}

	public void setEarliestSRD(LtsSRDDTO earliestSRD) {
		this.earliestSRD = earliestSRD;
	}

	public String getEarliestSRDReason() {
		return earliestSRDReason;
	}

	public void setEarliestSRDReason(String earliestSRDReason) {
		this.earliestSRDReason = earliestSRDReason;
	}

	public String getAppntDate() {
		return appntDate;
	}

	public void setAppntDate(String appntDate) {
		this.appntDate = appntDate;
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

	public String getCutOverDate() {
		return cutOverDate;
	}

	public void setCutOverDate(String cutOverDate) {
		this.cutOverDate = cutOverDate;
	}

	public String getCutOverTime() {
		return cutOverTime;
	}

	public void setCutOverTime(String cutOverTime) {
		this.cutOverTime = cutOverTime;
	}

	public boolean isCutOverInd() {
		return cutOverInd;
	}

	public void setCutOverInd(boolean cutOverInd) {
		this.cutOverInd = cutOverInd;
	}

	public boolean isTimeSlotRequiredInd() {
		return timeSlotRequiredInd;
	}

	public void setTimeSlotRequiredInd(boolean timeSlotRequiredInd) {
		this.timeSlotRequiredInd = timeSlotRequiredInd;
	}
	
	
}
