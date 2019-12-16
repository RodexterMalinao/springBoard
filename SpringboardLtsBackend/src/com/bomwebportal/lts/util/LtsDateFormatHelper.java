package com.bomwebportal.lts.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.util.DateFormatHelper;

public class LtsDateFormatHelper extends DateFormatHelper {

	/*
	 * Input: format hh:mm-hh:mm
	 * Output: format hh:mm-hh:mm
	 * */
	public static String convertToPonTime(String pTimeSlot){
		if(LtsBackendConstant.APPOINTMENT_TIMESLOT_18_TO_20.equals(pTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_EVENING;
		}
		return pTimeSlot;
	}

	/*
	 * Input: format hh:mm-hh:mm
	 * Output: format hh:mm-hh:mm
	 * */
	public static String convertPonTime(String pPonTimeSlot){
		if(LtsBackendConstant.APPOINTMENT_TIMESLOT_EVENING.equals(pPonTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_18_TO_20;
		}else if(LtsBackendConstant.APPOINTMENT_TIMESLOT_10_TO_13.equals(pPonTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_09_TO_13;
		}else if(LtsBackendConstant.APPOINTMENT_TIMESLOT_10_TO_18.equals(pPonTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_09_TO_18;
		}
		return pPonTimeSlot;
	}
	
	public static String convertToSBTime(String pTimeSlot){
		if(LtsBackendConstant.APPOINTMENT_TIMESLOT_09_TO_13.equals(pTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_10_TO_13;
		}else if(LtsBackendConstant.APPOINTMENT_TIMESLOT_09_TO_18.equals(pTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_10_TO_18;
		}
		return pTimeSlot;
	}

	public static String revertToBomTime(String pTimeSlot){
		if(LtsBackendConstant.APPOINTMENT_TIMESLOT_10_TO_13.equals(pTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_09_TO_13;
		}else if(LtsBackendConstant.APPOINTMENT_TIMESLOT_10_TO_18.equals(pTimeSlot)){
			return LtsBackendConstant.APPOINTMENT_TIMESLOT_09_TO_18;
		}
		return pTimeSlot;
	}
	
	public static String reformatDate(String inputDateString, String inputFormat, String outputFormat){
		DateTime dateTime = DateTime.parse(inputDateString, DateTimeFormat.forPattern(inputFormat));
		return dateTime.toString(DateTimeFormat.forPattern(outputFormat));
	}
}
