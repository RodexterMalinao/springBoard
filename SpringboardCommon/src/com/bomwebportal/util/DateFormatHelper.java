package com.bomwebportal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.bomwebportal.exception.AppRuntimeException;

public class DateFormatHelper {

	private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
	
	public static boolean isDateOverdue(String date, String dateFormat) {
		
		if (StringUtils.isEmpty(dateFormat)) {
			dateFormat = "dd/MM/yyyy";
		}
		
		try {
			Date srDate = DateUtils.parseDate(date, new String[]{dateFormat});						
			Date currentDate = DateUtils.parseDate(getSysDate(dateFormat), new String[]{dateFormat});
			
			if (srDate.before(currentDate)) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			throw new AppRuntimeException(e);
		}
		
	}

	/*
	 * Date Format = DD/MM/YYYY hh:mm:ss
	 */
	public static String getSysDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		return dateFormat.format(new Date());
	}
	
	public static String getSysDate(String pFormat) {
		DateFormat dateFormat = new SimpleDateFormat(pFormat);  
		return dateFormat.format(new Date());
	}
	
	public static String dateConvertFromDTO2DAONoTime(String pDate) {
		
		if (StringUtils.isEmpty(pDate)) {
			return null;
		}
		String[] date = StringUtils.split(pDate.substring(0, 10), '/');
		return date[2] + date[1] + date[0];
	}
	
	public static String dateConvertFromDAO2DTO(String pDate) {
		
		if (StringUtils.isEmpty(pDate)) {
			return null;
		}
		if (pDate.length() == 8) {
			return dateConvertFromDAO2DTOWithoutTime(pDate);
		} else if (pDate.length() == 14) {
			return dateConvertFromDAO2DTOWithTime(pDate);
		}
		return null;
	}
	
	/*
	 * Input pDate:  yyyymmddhhmm
	 * Output: format dd/mm/yyyy
	 */
	public static String dateConvertFromDAO2DTOWithoutTime(String pDate) {
		
		if (StringUtils.isEmpty(pDate)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		
		sb.append(pDate.substring(6, 8));
		sb.append('/');
		sb.append(pDate.substring(4, 6));
		sb.append('/');
		sb.append(pDate.substring(0, 4));
		return sb.toString();
	}
	
	/*
	 * Input pDate:  yyyymmddhhmm
	 * Output: format dd/mm/yyyy hh:mm
	 */
	private static String dateConvertFromDAO2DTOWithTime(String pDate) {
		
		if (StringUtils.isEmpty(pDate)) {
			return null;
		}
		StringBuilder sb = new StringBuilder(dateConvertFromDAO2DTOWithoutTime(pDate));
		sb.append(' ');
		sb.append(pDate.substring(8, 10));
		sb.append(':');
		sb.append(pDate.substring(10, 12));
		return sb.toString();
	}
	
	public static String dateConvertFromDTO2DAO(String pDate) {
		
		if (StringUtils.isEmpty(pDate)) {
			return null;
		}
		if (pDate.length() == 10) {
			return dateConvertFromDTO2DAOWithoutTime(pDate);
		} else if (pDate.length() == 16) {
			return dateConvertFromDTO2DAOWithTime(pDate);
		} else if (pDate.length() == 8) {
			return dateConvertFromDTO2DAOWithoutDelimiter(pDate);
		}
		return null;
	}
	
	/*
	 * Input pDate:  format dd/mm/yyyy
	 * Output: yyyymmdd
	 */
	private static String dateConvertFromDTO2DAOWithoutTime(String pDate) {
		
		String[] date = StringUtils.split(pDate, '/');
		return date[2].trim() + date[1] + date[0] + "000000";
	}
	
	/*
	 * Input pDate:  format dd/mm/yyyy hh:mm
	 * Output: yyyymmddhhmm
	 */
	private static String dateConvertFromDTO2DAOWithTime(String pDate) {
		
		String[] date = StringUtils.split(pDate.substring(0, 11), '/');
		String[] time = StringUtils.split(pDate.substring(11), ':');
		return date[2].trim() + date[1] + date[0] + time[0] + time[1] + "00";
	}
	
	private static String dateConvertFromDTO2DAOWithoutDelimiter(String pDate){
		
		//convert string yyyymmdd into yyyymmddhhmm
		 return pDate + "000000" ;
		
	}
	
	public static String getDateFromDTOFormat(String pDate) {
		if (StringUtils.isEmpty(pDate)) {
			return "";
		}
		return pDate.substring(0, 10);
	}
	
	public static String getTimeFromDTOFormat(String pDate) {
		if (StringUtils.isEmpty(pDate)) {
			return "";
		}
		return pDate.substring(11);
	}
	
	public static String getFromToTimeFormat(String pFromDate, String pToDate) {
		
		if (StringUtils.isEmpty(pFromDate) || StringUtils.isEmpty(pToDate)) {
			return "";
		}
		return getTimeFromDTOFormat(pFromDate) + "-" + getTimeFromDTOFormat(pToDate);
	}

	/*
	 * Input pDTOStartTime: format dd/mm/yyyy hh:mm
	 * Input pDTOEndTime: format dd/mm/yyyy hh:mm
	 * Output: dd/mm/yyyy hh:mm-hh:mm
	 */
	public static String convertStartEndDateTimeFromDTO2Display(String pDTOStartTime, String pDTOEndTime){

		if (StringUtils.isEmpty(pDTOStartTime)
				|| StringUtils.isEmpty(pDTOEndTime)) {
			return "";
		}
		String[] startDateTime = StringUtils.split(pDTOStartTime, ' ');
		String[] endDateTime = StringUtils.split(pDTOEndTime, ' ');
		if(StringUtils.equals(startDateTime[0], endDateTime[0])
				&& startDateTime.length >= 2 
				&& endDateTime.length >= 2){
			StringBuilder sb = new StringBuilder(startDateTime[0]);
			sb.append(' ');
			sb.append(startDateTime[1]);
			sb.append('-');
			sb.append(endDateTime[1]);
			return sb.toString();
		}
		return "";
	}

	public static int getDaysBetween(Date pFromDate, Date pToDate) {
		return (int)((pToDate.getTime() - pFromDate.getTime())/DAY_IN_MILLIS );
	}
	
	/* Input pDate:  format dd/mm/yyyy hh:mm */
	public static Calendar getCalenderDateFromDTOFormat(String pDate) {		
		if (StringUtils.isEmpty(pDate) || pDate.length()<16) {
			return null;
		}
		Calendar cDate = Calendar.getInstance();
		int year = Integer.parseInt(pDate.substring(6,10));
		int month = Integer.parseInt(pDate.substring(3,5));
		int date = Integer.parseInt(pDate.substring(0,2));
		int hour = Integer.parseInt(pDate.substring(11,13));
		int mins = Integer.parseInt(pDate.substring(14,16));		
		cDate.set(year, month-1, date, hour, mins);
		
		return cDate;
	}
	
}
