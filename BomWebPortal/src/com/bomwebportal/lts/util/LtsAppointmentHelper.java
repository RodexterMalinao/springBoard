package com.bomwebportal.lts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class LtsAppointmentHelper {
	public static final String TIMESLOT_DELIMITER = "="; 
	
	public static final ImmutableMap<String, String> earliestSrdReason = new ImmutableMap.Builder<String, String>()
			.put("00", "Normal Lead Time ")
			.put("01", "Field Permit Required ")
			.put("02", "IMS Blacklist Address ")
			.put("03", "External Relocate and 2NBW ")
			.put("04", "2NBW and Voice Share PCD  ")
			.put("05", "2NBW and Not Voice Share PCD ")
			.put("06", "2N Port In ")
			.put("07", "External Relocate and Blacklist Customer ")
			.put("08", "LTS Blacklist Address ")
			.put("09", "External Relocate ")

			.put("11", "2N Blockwiring ")
			.put("12", "PIPB Normal Lead Time ")
			.put("13", "EYE Normal Lead Time ")
			.put("14", "FFP Normal Lead Time ")
			.put("15", "DEL Lead Time before cut-off time ")
			.put("16", "DEL Lead Time ")
			.put("17", "Premier Team Lead Time ")
			.put("18", "PIPB DN not exist in DRG ")
			.put("19", "Second Del Porting ")

			.put("20", "Blacklist Customer ")
			.put("21", "ONP ")
			.put("22", "Customer input name not matched with BOM record ")
			.put("23", "2N Building ")
			.put("24", "Blacklist Address ")

			.put("30", "EYE3A Normal Lead Time ")
			.put("31", "Call Center/Premier Team Lead Time ")
			.put("32", "Selected Specific Basket ")
			
			.put("40", "External Removal (PCD) ")
			
			.put("50", "Customer Name does not match with BOM ")
			.put("51", "Must QC ")
			
			.put("52", "Recontract case ")
			.put("53", "2NBW and Technology Upgrade ")
			.put("54", "2NBW and PE Basket selected for SIP ")
			.put("55", "Passport customer with Credit Card Prepayment ")
			
			.build();

	public static final ImmutableList<String> blacklistCustReasonCodeList = new ImmutableList.Builder<String>()
			.add("07")
			.add("20")
			.build();

	public static final ImmutableList<String> blacklistAddrReasonCodeList = new ImmutableList.Builder<String>()
			.add("02")
			.add("08")
			.add("24")
			.build();
	
	public static final ImmutableList<String> tentativeReasonCodeList = new ImmutableList.Builder<String>()
			.addAll(blacklistAddrReasonCodeList)
			.addAll(blacklistCustReasonCodeList)
			.build();

	
	public static void disableTimeSlotByType(ResourceDetailOutputDTO resource, String timeSlotType){
		if(resource.getResourceSlots() == null){
			return;
		}
		
		for(ResourceSlotDTO timeSlot: resource.getResourceSlots()){
			if(StringUtils.equals(timeSlot.getApptTimeSlotType(), timeSlotType)){
				timeSlot.setAvailableInd("N");
				timeSlot.setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE);
			}
		}
		return;
	}
	
	public static List<ResourceSlotDTO> setupResourceList(ResourceDetailOutputDTO resource, boolean changeEveningSlotInd){
		List<ResourceSlotDTO> timeSlotList = new ArrayList<ResourceSlotDTO>();
		
		if(resource.getResourceSlots() == null){
			return timeSlotList;
		}
		
		for(int i = 0; i < resource.getResourceSlots().length; i++){
			if(StringUtils.equals("Y", resource.getResourceSlots()[i].getRestrictInd())){
				resource.getResourceSlots()[i].setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT);
			}else if(!StringUtils.equals("Y", resource.getResourceSlots()[i].getAvailableInd())){
				resource.getResourceSlots()[i].setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE);
			}
			if(changeEveningSlotInd){
				resource.getResourceSlots()[i].setApptTimeSlot(
						LtsDateFormatHelper.convertToPonTime(resource.getResourceSlots()[i].getApptTimeSlot()));
			}
			resource.getResourceSlots()[i].setApptTimeSlot(
					LtsDateFormatHelper.convertToSBTime(resource.getResourceSlots()[i].getApptTimeSlot()));
			timeSlotList.add(resource.getResourceSlots()[i]);
		}
		return timeSlotList;
	}
	
	public static JSONArray formatResourceToJsonArray(ResourceDetailOutputDTO resource, boolean changeEveningSlotInd){
		JSONArray jsonArray = new JSONArray();
		
		if(resource.getResourceSlots() == null){
			return jsonArray;
		}
		
		ResourceSlotDTO[] resourceSlots = setupResourceList(resource, changeEveningSlotInd).toArray(new ResourceSlotDTO[0]);
		for(int i = 0; i < resourceSlots.length; i++){
			String currentTimeSlot = resourceSlots[i].getApptTimeSlot();
			jsonArray.add(getTimeSlotJsonArray(
					currentTimeSlot, 
					currentTimeSlot+TIMESLOT_DELIMITER+resourceSlots[i].getApptTimeSlotType(), 
					resourceSlots[i].getApptTimeSlotType()));
		}
		return jsonArray;
	}
	
	private static String getTimeSlotJsonArray(String timeSlotDisplay, String timeSlotValue, String type){
		String jsonArray = "{\"tsdisplay\":\"" + timeSlotDisplay
				+ "\",\"tsvalue\":\""	+ timeSlotValue
				+ "\",\"tstype\":\""	+ type			
				+ "\"}";
		return jsonArray;
	}
	
	public static JSONObject calculateCutOverDateTime(String dateString, String timeSlot, List<String> holidayList){
		JSONObject jsonObject = new JSONObject();
		
		int installStartHour = Integer.parseInt(timeSlot.split("-")[0].split(":")[0]);
		int installEndHour = Integer.parseInt(timeSlot.split("-")[1].split(":")[0]);
		if(installStartHour == 10){
			installStartHour = 9;
		}
		String cutOverTimeSlot = null;
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("dd/MM/yyyy");
		Calendar cutOverDate = new GregorianCalendar();
		try {
			cutOverDate.setTime(df.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
			return jsonObject;
		}
		df.applyPattern("yyyy-MM-dd");
		boolean recheckFlag = false;
		do{
			recheckFlag = false;
			String temp = df.format(cutOverDate.getTime());
			if(cutOverDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				recheckFlag = true;
			}else if(holidayList.contains(temp)){
				recheckFlag = true;
			}else{
				if(cutOverDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
						if(installStartHour == 9){
							cutOverTimeSlot = "09:00-11:00";
						}else{
							if(installEndHour >= 13){
								cutOverTimeSlot = "11:00-13:00";
								recheckFlag = false;
							}else if(installEndHour >= 11){
								cutOverTimeSlot = "09:00-11:00";
								recheckFlag = false;
							}else{
								recheckFlag = true;
							}
					}
				}else{
					if(installStartHour == 9){
						cutOverTimeSlot = "09:00-11:00";
					}else if(installStartHour == 14){
						cutOverTimeSlot = "13:00-15:00";
					}else if(installEndHour >= 17){
						cutOverTimeSlot = "15:00-17:00";
					}else if(installEndHour >= 15){
						cutOverTimeSlot = "13:00-15:00";
					}else if(installEndHour >= 13){
						cutOverTimeSlot = "11:00-13:00";
					}else if(installEndHour >= 11){
						cutOverTimeSlot = "09:00-11:00";
					}else{
						recheckFlag = true;
					}
				}
			}
			if(recheckFlag == true){
				installEndHour = 24;
				installStartHour = 24;
				cutOverDate.add(Calendar.DATE, -1);
			}
		}while(recheckFlag == true);

		df.applyPattern("dd/MM/yyyy");
		String cutOverDateString = df.format(cutOverDate.getTime());
		jsonObject.put("date", cutOverDateString);
		jsonObject.put("timeSlot", cutOverTimeSlot);
		
		return jsonObject;
	}
	
	public static String getToday(){
		return DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
	}
	
	public static String getAppDate(int pDay){
		return DateTime.now().plusDays(pDay).toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
	}
	
	public static String reformatToDwfmDate(String date){
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("dd/MM/yyyy");
		Date a = null;
		try {
			a = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		df.applyPattern("yyyyMMdd");
		return df.format(a);
	}

	public static String getDateFromDwfmFormat(String dwfmTime){
		SimpleDateFormat df = new SimpleDateFormat();
		try{
			df.applyPattern("yyyy-MM-dd HH:mm:ss");
			Date fullTime = df.parse(dwfmTime);
			df.applyPattern("dd/MM/yyyy");
			String dateString = df.format(fullTime);
			return dateString;
			
		}catch (ParseException pe) {
			pe.printStackTrace();
			return null;
		}
	}

	public static String getTimeFromDwfmFormat(String dwfmTime){
		SimpleDateFormat df = new SimpleDateFormat();
		try{
			df.applyPattern("yyyy-MM-dd HH:mm:ss");
			Date fullTime = df.parse(dwfmTime);
			df.applyPattern("HH:mm");
			String timeString = df.format(fullTime);
			return timeString;
			
		}catch (ParseException pe) {
			pe.printStackTrace();
		}
		return null;
	}
	
	public static void clearTimeField(Calendar date) {
		date.clear(Calendar.HOUR_OF_DAY);
		date.clear(Calendar.HOUR);
		date.clear(Calendar.MINUTE);
		date.clear(Calendar.SECOND);
		date.clear(Calendar.MILLISECOND);
		date.clear(Calendar.AM_PM);
	}
	
	/**
	 * create SRD description without reason
	 * @param srd
	 * @return String: dd/MM/yyyy (EEE)
	 */
	public static String getEarilestSrdOnly(LtsSRDDTO srd){
		//set description for earliest srd
		if(srd == null){
			return null;
		}
		return srd.getSRDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy (EEE)"));
	}
	
	/**
	 * create SRD description with reason
	 * @param srd
	 * @return String 
	 */
	public static String getEarilestSrdReason(LtsSRDDTO srd){
		if(srd == null){
			return null;
		}
		StringBuilder sb = new StringBuilder(getEarilestSrdOnly(srd));
		sb.append(" - Ref: ");
		sb.append(srd.getInfo());
		if(srd.getLeadTime() > 0) {
			sb.append(" (T+" + srd.getLeadTime() + ")");
		}
		if(srd.isExcludeSat() && srd.isExcludeSunPH()){
			sb.append(" excluded Sat/Sun/PH");
		}else if(srd.isExcludeSat()){
			sb.append(" excluded Sat");
		}else if(srd.isExcludeSunPH()){
			sb.append(" excluded Sun/PH");
		}
		return sb.toString();
	}
	
	/**
	 * create SRD description with reason
	 * @param srd
	 * @param locale - can be ignored
	 * @return String 
	 */
	public static String getEarilestSrdReason(LtsSRDDTO srd, Locale locale){
		//set description for earliest srd
		if(srd == null){
			return null;
		}
		
//		String dayOfWeek = srd.getDate().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale);
//		StringBuilder sb = new StringBuilder("");
//		sb.append(srd.getDateString());
//		sb.append(" (" + dayOfWeek + ") - Ref: ");
		
		StringBuilder sb = new StringBuilder(getEarilestSrdOnly(srd));
		sb.append(" - Ref: ");
		sb.append(srd.getInfo());
		if(srd.getLeadTime() > 0) {
			sb.append(" (T+" + srd.getLeadTime() + ")");
		}
		if(srd.isExcludeSat() && srd.isExcludeSunPH()){
			sb.append(" excluded Sat/Sun/PH");
		}else if(srd.isExcludeSat()){
			sb.append(" excluded Sat");
		}else if(srd.isExcludeSunPH()){
			sb.append(" excluded Sun/PH");
		}
		return sb.toString();
	}
	
	/*
	 * skip holidays/SAT/SUN
	 */
	public static void pushWorkingDays(LtsSRDDTO srd, int leadTime, boolean pushSatInd, List<String> holidayList){
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("yyyy-MM-dd");
		Calendar temp = (Calendar)srd.getDate().clone();
		temp.add(Calendar.DATE, 1);
		int skipped = 0;
		for(int i = 1; i <= leadTime; i++){
			int dayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
			while((dayOfWeek == Calendar.SATURDAY && pushSatInd)
					|| dayOfWeek == Calendar.SUNDAY 
					||  holidayList.contains(df.format(temp.getTime()))){
				skipped++;
				temp.add(Calendar.DATE, 1);
				dayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
			}
			temp.add(Calendar.DATE, 1);
		}
		srd.setLeadTime(leadTime);
		srd.addSkippedTime(skipped);
		srd.setExcludeSat(pushSatInd);
		srd.setExcludeSunPH(true);
	}
	
	public static DateTime pushWorkingDays(DateTime dateTime, int leadTime, boolean pushSatInd, List<String> holidayList){
		DateTime tempDate = new DateTime(dateTime);
		
		for(int i = 1; i <= leadTime; i++){
			int dayOfWeek = dateTime.getDayOfWeek();
			while((dayOfWeek == DateTimeConstants.SATURDAY && pushSatInd)
					|| dayOfWeek == DateTimeConstants.SUNDAY
					|| holidayList.contains(tempDate.toString(DateTimeFormat.forPattern("yyyy-MM-dd")))){ 
				tempDate = tempDate.plusDays(1);
				dayOfWeek = dateTime.getDayOfWeek();
			}
			tempDate = tempDate.plusDays(1);
		}
		
		return tempDate;
	}
	
	public static void addExtraDaysToSRD(StringBuilder srdReasonBuilder, LtsSRDDTO sRDInfo, int days, String reasonCd){
		sRDInfo.addDate(days);
		srdReasonBuilder.append(LtsAppointmentHelper.earliestSrdReason.get(reasonCd));
		srdReasonBuilder.append(" + " + days + " days ");
		srdReasonBuilder.append(" & ");
	}
	
	public static List<ResourceSlotDTO> getTimeSlotListWholeDay(){
		List<ResourceSlotDTO> timeSlot = new ArrayList<ResourceSlotDTO>();
		ResourceSlotDTO time = new ResourceSlotDTO();
		time.setApptTimeSlot(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
		time.setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
		timeSlot.add(time);
		return timeSlot;
	}
}
