package com.bomltsportal.web;

//import java.text.SimpleDateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.SrdDTO;
import com.bomltsportal.service.ApplicantInfoService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;


public class AjaxAppointmentController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private AppointmentDwfmService appointmentDwfmService;
	private ApplicantInfoService applicantInfoService;
//	private SimpleDateFormat df = new SimpleDateFormat();

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView("ajax_ajaxappointment");
		}
		String requestType = request.getParameter("type");
		boolean isImportNum =  "true".equals(request.getParameter("onp"));
		if("AMEND".equals(requestType)){
			String SrvactionCdType = orderCaptureDTO.getSbOrder().getSrvDtls()[0].getSrvTypeCdAction();
			isImportNum = StringUtils.equals(SrvactionCdType, BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_DEL)
							|| StringUtils.equals(SrvactionCdType, BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_EYE);
		}
		if("APPT".equals(requestType) || "AMEND".equals(requestType)){
			JSONArray jsonArray = new JSONArray();
			String dateString =  request.getParameter("date");
			dateString = LtsDateFormatHelper.reformatDate(dateString, "yyyy/MM/dd", "dd/MM/yyyy");
			ResourceDetailInputDTO resourceInput = new ResourceDetailInputDTO();
			resourceInput = applicantInfoService.getResourceDetailInput(orderCaptureDTO, dateString);
			
			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern("dd/MM/yyyy");
			GregorianCalendar date = new GregorianCalendar();
			date.setTime(df.parse(dateString));

			boolean isPON = applicantInfoService.isPon(orderCaptureDTO);
			ResourceDetailOutputDTO resource = applicantInfoService.getResourceDetailWithFilter(resourceInput, isPON);//appointmentDwfmService.getResourceDetail(resourceInput);
			
			if(resource.getResourceSlots() == null){
				logger.error(resource.getErrorMsg());
			}else{
				//TODO
				
				for(ResourceSlotDTO slot: resource.getResourceSlots()){
					if("Y".equals(slot.getAvailableInd()) && !"Y".equals(slot.getRestrictInd())){
						String timeSlot = LtsDateFormatHelper.convertToSBTimeSlot(slot.getApptTimeSlot());
						if(isPON){
							timeSlot = LtsDateFormatHelper.convertToPonTimeSlot(timeSlot);
						}
//						String temp = StringUtils.substring(timeSlot, 0, 2);
						int startHour = Integer.parseInt(StringUtils.substring(timeSlot, 0, 2));
						if(isImportNum && 
								((date.get(GregorianCalendar.DAY_OF_WEEK) == Calendar.SATURDAY && startHour > 12)
										||(timeSlot.equals(BomLtsConstant.APPOINTMENT_TIMESLOT_10_TO_18)))){
							continue;
						}else{
							jsonArray.add(generateTimeSlotJsonArray(
									timeSlot, 
									timeSlot + "||" + slot.getApptTimeSlotType(), 
									slot.getApptTimeSlotType()));
						}
					}
				}
			}
			if(jsonArray.size() == 0){
				jsonArray.add(generateTimeSlotJsonArray(
						"No timeslot available", 
						"", 
						""));
			}
			System.out.println(jsonArray);
			
			return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		}else if("ESRD".equals(requestType)){
			JSONObject jsonObject = new JSONObject();
			orderCaptureDTO.getApplicantInfoForm().setNewNum(!isImportNum);
			boolean nameNotMatch 	= orderCaptureDTO.getCustomerDetailProfile() != null 
										&& "false".equals(request.getParameter("nameMatch"));
			boolean isBlacklist 		= orderCaptureDTO.getCustomerDetailProfile() != null
										&& orderCaptureDTO.getCustomerDetailProfile().isBlacklistCustInd();
			boolean isBlacklistAddr 		= orderCaptureDTO.getAddressRollout().isLtsAddressBlacklist();
			SrdDTO earliestSrd = applicantInfoService.getEarliestSRD(isBlacklist, isBlacklistAddr, isImportNum, nameNotMatch, orderCaptureDTO.getAddressRollout().isIs2nBuilding(), orderCaptureDTO.getServiceTypeInd());
			orderCaptureDTO.getApplicantInfoForm().setEarliestSrd(earliestSrd);
			int maxSrd = applicantInfoService.getMaxSrd(orderCaptureDTO.getServiceTypeInd());
			if(earliestSrd.getLeadTime() >= maxSrd){
				jsonObject.put("lastSrd", "+"+(earliestSrd.getLeadTime()+maxSrd)+"D");
			}else{
				jsonObject.put("lastSrd", "+"+maxSrd+"D");
			}
			jsonObject.put("ealiestSrd", earliestSrd.getDateString("yyyy/MM/dd"));
			return new ModelAndView("ajax_ajaxappointment", jsonObject);
		}else if("AMENDSRD".equals(requestType)){
			
		}
		return new ModelAndView("ajax_ajaxappointment");
	}


	private String generateTimeSlotJsonArray(String timeSlotDisplay, String timeSlotValue, String type){
		String jsonArray = "{\"tsdisplay\":\"" + timeSlotDisplay
				+ "\",\"tsvalue\":\""	+ timeSlotValue
				+ "\",\"tstype\":\""	+ type			
				+ "\"}";
		return jsonArray;
	}
	
	public ApplicantInfoService getApplicantInfoService() {
		return applicantInfoService;
	}

	public void setApplicantInfoService(ApplicantInfoService applicantInfoService) {
		this.applicantInfoService = applicantInfoService;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	
}
