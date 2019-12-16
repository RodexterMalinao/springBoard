package com.bomwebportal.lts.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.util.DateFormatHelper;

public class LtsDateFormatHelper extends DateFormatHelper {

	// Only count Month Diff (ignore day)
	public static int getMonthDiff(String date1, String date2, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(StringUtils.defaultIfEmpty(dateFormat, "dd/MM/yyyy"));
		Date d1 = null;
		Date d2 = null;
	 
		try {
			d1 = format.parse(date1);
			d2 = format.parse(date2);
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(d1);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(d2);

			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
		} catch (Exception e) {
				throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		 }
	
	}
	
	// Count Month Diff with day (include day)	
	public static double getDiffMonth(String pDate){
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		double remainMth = 0;
		try{
			Date inDate = format.parse(pDate); 
			
			Calendar calendarInDate = Calendar.getInstance();
			Calendar calendarCurrent = Calendar.getInstance();	
			
			calendarInDate.set(inDate.getYear() + 1900, inDate.getMonth(), inDate.getDate());
		    int startDayOfMonth = calendarCurrent.get(Calendar.DAY_OF_MONTH);
		    int startMonth =  calendarCurrent.get(Calendar.MONTH);
		    int startYear = calendarCurrent.get(Calendar.YEAR);  
			
		
		    int endDayOfMonth = calendarInDate.get(Calendar.DAY_OF_MONTH);  
		    int endMonth =  calendarInDate.get(Calendar.MONTH);
		    int endYear = calendarInDate.get(Calendar.YEAR);
		    
		    int diffMonths = endMonth - startMonth;
		    int diffYears = endYear - startYear;
		    int diffDays = endDayOfMonth - startDayOfMonth;
		    
		    remainMth = (diffYears * 12) + diffMonths + diffDays/31.0;
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return remainMth; 
	}
	
	
	public static int getDateDiffInDays(String date1, String date2, String dateFormat) {
	 
		SimpleDateFormat format = new SimpleDateFormat(StringUtils.defaultIfEmpty(dateFormat, "dd/MM/yyyy"));
	 
		Date d1 = null;
		Date d2 = null;
	 
		try {
			d1 = format.parse(date1);
			d2 = format.parse(date2);
	 
			LocalDateTime dt1 = new LocalDateTime(d1);
			LocalDateTime dt2 = new LocalDateTime(d2);
	 
			return Days.daysBetween(dt1, dt2).getDays();
			
//			System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
//			System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
//			System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
//			System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");
	 
		 } catch (Exception e) {
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		 }
	}
	
	
	public static String getSysDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(StringUtils.defaultIfEmpty(format, "dd/MM/yyyy HH:mm"));
		return dateFormat.format(new Date());
	}
	
	/*
	 * Input: format hh:mm-hh:mm
	 * Output: format hh:mm-hh:mm
	 * */
	public static String convertToPonTime(String pTimeSlot){
		if(LtsConstant.APPOINTMENT_TIMESLOT_18_TO_20.equals(pTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_EVENING;
		}
		return pTimeSlot;
	}

	/*
	 * Input: format hh:mm-hh:mm
	 * Output: format hh:mm-hh:mm
	 * */
	public static String convertPonTime(String pPonTimeSlot){
		if(LtsConstant.APPOINTMENT_TIMESLOT_EVENING.equals(pPonTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_18_TO_20;
		}else if(LtsConstant.APPOINTMENT_TIMESLOT_10_TO_13.equals(pPonTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_09_TO_13;
		}else if(LtsConstant.APPOINTMENT_TIMESLOT_10_TO_18.equals(pPonTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_09_TO_18;
		}
		return pPonTimeSlot;
	}
	
	public static String convertToSBTime(String pTimeSlot){
		if(LtsConstant.APPOINTMENT_TIMESLOT_09_TO_13.equals(pTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_10_TO_13;
		}else if(LtsConstant.APPOINTMENT_TIMESLOT_09_TO_18.equals(pTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_10_TO_18;
		}
		return pTimeSlot;
	}

	public static String revertToBomTime(String pTimeSlot){
		if(LtsConstant.APPOINTMENT_TIMESLOT_10_TO_13.equals(pTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_09_TO_13;
		}else if(LtsConstant.APPOINTMENT_TIMESLOT_10_TO_18.equals(pTimeSlot)){
			return LtsConstant.APPOINTMENT_TIMESLOT_09_TO_18;
		}
		return pTimeSlot;
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
	
	public static String reformatDate(String inputDateString, String inputFormat, String outputFormat){
		LocalDateTime dateTime = LocalDateTime.parse(inputDateString, DateTimeFormat.forPattern(inputFormat));
		return dateTime.toString(DateTimeFormat.forPattern(outputFormat));
	}
	
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
	
}
