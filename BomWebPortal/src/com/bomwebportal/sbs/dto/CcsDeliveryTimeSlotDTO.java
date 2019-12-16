package com.bomwebportal.sbs.dto;

public class CcsDeliveryTimeSlotDTO {
	
	private String timeslot;
	private String timeRange;
	private String quotaRemain;
	
	public CcsDeliveryTimeSlotDTO (String timeslot,String timeRange,String quotaRemain) {
		this.timeslot = timeslot;
		this.timeRange = timeRange;
		this.quotaRemain = quotaRemain;
	}

	public String getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(String timeslot) {
		this.timeslot = timeslot;
	}

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public String getQuotaRemain() {
		return quotaRemain;
	}

	public void setQuotaRemain(String quotaRemain) {
		this.quotaRemain = quotaRemain;
	}
	
}
