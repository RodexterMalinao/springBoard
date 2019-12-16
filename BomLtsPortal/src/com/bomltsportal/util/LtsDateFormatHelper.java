package com.bomltsportal.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

//import com.bomwebportal.util.DateFormatHelper;

public class LtsDateFormatHelper /*extends DateFormatHelper*/ {

	/*
	 * Input: format hh:mm-hh:mm
	 * Output: format hh:mm-hh:mm
	 * */
	public static String convertToPonTimeSlot(String pTimeSlot){
		if(BomLtsConstant.APPOINTMENT_TIMESLOT_18_TO_20.equals(pTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_EVENING;
		}
		return pTimeSlot;
	}

	/*
	 * Input: format hh:mm-hh:mm
	 * Output: format hh:mm-hh:mm
	 * */
	public static String convertPonTimeSlot(String pPonTimeSlot){
		if(BomLtsConstant.APPOINTMENT_TIMESLOT_EVENING.equals(pPonTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_18_TO_20;
		}else if(BomLtsConstant.APPOINTMENT_TIMESLOT_10_TO_13.equals(pPonTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_09_TO_13;
		}else if(BomLtsConstant.APPOINTMENT_TIMESLOT_10_TO_18.equals(pPonTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_09_TO_18;
		}
		return pPonTimeSlot;
	}
	
	public static String convertToSBTimeSlot(String pTimeSlot){
		if(BomLtsConstant.APPOINTMENT_TIMESLOT_09_TO_13.equals(pTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_10_TO_13;
		}else if(BomLtsConstant.APPOINTMENT_TIMESLOT_09_TO_18.equals(pTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_10_TO_18;
		}
		return pTimeSlot;
	}

	public static String revertToBomTimeSlot(String pTimeSlot){
		if(BomLtsConstant.APPOINTMENT_TIMESLOT_10_TO_13.equals(pTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_09_TO_13;
		}else if(BomLtsConstant.APPOINTMENT_TIMESLOT_10_TO_18.equals(pTimeSlot)){
			return BomLtsConstant.APPOINTMENT_TIMESLOT_09_TO_18;
		}
		return pTimeSlot;
	}
	
	public static String reformatDate(String originalDateString, String inputFormat, String outputFormat){
		SimpleDateFormat df = new SimpleDateFormat();
		try {
			df.applyPattern(inputFormat);
			Date date = df.parse(originalDateString);
			df.applyPattern(outputFormat);
			return df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Input: format dd/MM/yyyy, format hh:mm-hh:mm
	 * Output: Array format dd/MM/yyyy hh:mm
	 * */
	public static String[] reformatDateTimeSlot(String date, String timeSlot){
		String[] time = StringUtils.split(timeSlot, '-');
		if(time.length > 1){
			String[] s = {date.concat(" ").concat(time[0]),
					date.concat(" ").concat(time[1])};
			return s;
		}else{
			String[] s = {date, date};
			return s;
		}
	}
	
	/*
	 * Input: format String dd/MM/yyyy hh:mm
	 * Output: String dd/MM/yyyy
	 * */
	public static String getDateFromDateTimeSlot(String dateTimeSlot){
		String[] data = StringUtils.split(dateTimeSlot, ' ');
		if(data == null || data.length != 2){
			return null;
		}
		return data[0];
	}	
	
	/*
	 * Input: format String dd/MM/yyyy hh:mm
	 * Output: String hh:mm
	 * */
	public static String getTimeFromDateTimeSlot(String dateTimeSlot){
		String[] data = StringUtils.split(dateTimeSlot, ' ');
		if(data == null || data.length != 2){
			return null;
		}
		return data[1];
	}		
	
	/*
	 * Input: format String dd/MM/yyyy hh:mm
	 * Output: String dd (without "0" left-padded)
	 * */	
	public static String getDayFromDateTimeSlot(String dateTimeSlot){
		String[] data = StringUtils.split(dateTimeSlot, '/');
		if(data == null){
			return null;
		}
		int value = Integer.parseInt(data[0]);
		return String.valueOf(value);
	}
	
	/*
	 * Input: format String dd/MM/yyyy hh:mm
	 * Output: String MM (without "0" left-padded) for zh_HK, return "January" - "December" for en
	 * */	
	public static String getMonthFromDateTimeSlot(String dateTimeSlot, String locale){
		if(BomLtsConstant.LOCALE_ENG.equals(locale)){
			String[] data = StringUtils.split(dateTimeSlot, '/');
			if(data == null){
				return null;
			}
			return BomLtsConstant.MONTH_MAPPING.get(data[1]);
		}
		else{
			String[] data = StringUtils.split(dateTimeSlot, '/');
			if(data == null){
				return null;
			}
			int value = Integer.parseInt(data[1]);
			return String.valueOf(value);
		}
	}	
	
	public static String getSysDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		return dateFormat.format(new Date());
	}
	
	public static String getFromToTimeFormat(String pFromDate, String pToDate) {
		
		if (StringUtils.isEmpty(pFromDate) || StringUtils.isEmpty(pToDate)) {
			return "";
		}
		return getTimeFromDTOFormat(pFromDate) + "-" + getTimeFromDTOFormat(pToDate);
	}
	
	public static String getTimeFromDTOFormat(String pDate) {
		if (StringUtils.isEmpty(pDate)) {
			return "";
		}
		return pDate.substring(11);
	}
	
	public static String date2String(Date date, String format) {
		return formatDate(date, format, Locale.US);
	}

	public static String formatDate(Date date, String format,
			Locale currentLocale) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat df = new SimpleDateFormat(format, currentLocale);
		String dateString = df.format(date);
		return dateString;
	}	
}
