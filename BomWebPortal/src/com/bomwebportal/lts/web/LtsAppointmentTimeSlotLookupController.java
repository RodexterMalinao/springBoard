package com.bomwebportal.lts.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAppointmentTimeSlotLookupController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private AppointmentDwfmService appointmentDwfmService;
	private LtsAppointmentService ltsAppointmentService;
	private LtsOfferService ltsOfferService;
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}
	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}
	public void setAppointmentDwfmService(AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}
	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}
	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SbOrderDTO sbOrder = LtsSessionHelper.getDummySbOrder(request);
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		TerminateOrderCaptureDTO termOrderCaptureDTO = (TerminateOrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
		String type = request.getParameter("type");
		String isAmendment = request.getParameter("amend");
		
		if(!StringUtils.equals("true", isAmendment)){
			if("D".equals(type) && termOrderCaptureDTO == null) {
				return new ModelAndView(LtsConstant.ERROR_VIEW);
			}
			if(!("D".equals(type)) && orderCaptureDTO == null) {
				return new ModelAndView(LtsConstant.ERROR_VIEW);
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String dateString = request.getParameter("date");
		

		ResourceDetailInputDTO resourceInput = null;
		if(type.equals("I") || type.equals("D") || type.equals("IA") || type.equals("P") || type.equals("S") || type.equals("SA")){
			String userId = LtsSessionHelper.getBomSalesUser(request).getUsername();
			if(StringUtils.equals("true", isAmendment)){
				if(type.contains("A")){
					resourceInput = ltsAppointmentService.getResourceDetailInput(sbOrder, LtsConstant.DWFM_INPUT_TYPE_ALL, dateString, userId);
				}else if((type.equals("P") || type.equals("I"))){
					resourceInput = ltsAppointmentService.getResourceDetailInput(sbOrder, LtsConstant.DWFM_INPUT_TYPE_UPGRADE, dateString, userId);
				}else if(type.equals("S")){
					resourceInput = ltsAppointmentService.getResourceDetailInput(sbOrder, LtsConstant.DWFM_INPUT_TYPE_2NDDEL, dateString, userId);
				}else if(type.equals("D")){
					resourceInput = ltsAppointmentService.getTermResourceDetailInput(sbOrder, LtsConstant.DWFM_INPUT_TYPE_DISCONNECT, dateString, userId);
				}
			}else{
				if("D".equals(type) && termOrderCaptureDTO != null) {
					if(termOrderCaptureDTO.getSbOrder() != null){
						resourceInput = ltsAppointmentService.getTermResourceDetailInput(termOrderCaptureDTO.getSbOrder(), LtsConstant.DWFM_INPUT_TYPE_DISCONNECT, dateString, userId);
					}else{
						resourceInput = ltsAppointmentService.getTermResourceDetailInput(termOrderCaptureDTO, dateString);
					}
				}
				else if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCaptureDTO.getOrderType())
							&& !ltsOfferService.isRenewalWithNewDevice(orderCaptureDTO)) {
					if(type.contains("A") || "S".equals(type)) {
						resourceInput = ltsAppointmentService.getResourceDetailInput(orderCaptureDTO, dateString, LtsConstant.DWFM_INPUT_TYPE_2NDDEL);
					}
				}
				else {
					if(type.contains("A")){
						resourceInput = ltsAppointmentService.getResourceDetailInput(orderCaptureDTO, dateString, LtsConstant.DWFM_INPUT_TYPE_ALL);
					}else if((type.equals("P") || type.equals("I"))){
						resourceInput = ltsAppointmentService.getResourceDetailInput(orderCaptureDTO, dateString, LtsConstant.DWFM_INPUT_TYPE_UPGRADE);
					}else if(type.equals("S")){
						resourceInput = ltsAppointmentService.getResourceDetailInput(orderCaptureDTO, dateString, LtsConstant.DWFM_INPUT_TYPE_2NDDEL);
					}else if(type.equals("D")){
						resourceInput = ltsAppointmentService.getTermResourceDetailInput(termOrderCaptureDTO, dateString);
					}
				}
			}
			
			if (resourceInput == null) {
				return new ModelAndView("ajax_ltsappointmenttimeslotlookup", jsonObject);
			}
			
			ResourceDetailOutputDTO resource = appointmentDwfmService.getResourceDetail(resourceInput);
			
			if(resource.getResourceSlots() == null){
				logger.error(resource.getErrorMsg());
			}else{
				if(type.equals("D")){
					boolean changeEveningSlotInd = ltsAppointmentService.getTermChangePonTimeSlotInd(termOrderCaptureDTO);
					jsonArray = LtsAppointmentHelper.formatResourceToJsonArray(resource, changeEveningSlotInd);
				}else{
					boolean changeEveningSlotInd = false;
					if(StringUtils.equals("true", isAmendment)){
						changeEveningSlotInd = ltsAppointmentService.isInstallUpgradePon(sbOrder);
					}else{
						changeEveningSlotInd = ltsAppointmentService.getChangePonTimeSlotInd(orderCaptureDTO);
					}
					
					if(StringUtils.equals("true", isAmendment)){
						jsonArray = LtsAppointmentHelper.formatResourceToJsonArray(resource, changeEveningSlotInd);
					}else{
						for(int i = 0; i < resource.getResourceSlots().length; i++){
							String convertedSlot = LtsDateFormatHelper.convertToSBTime(resource.getResourceSlots()[i].getApptTimeSlot());
							if(StringUtils.equals(resource.getResourceSlots()[i].getRestrictInd(), "Y")){
								jsonArray.add(getTimeSlotJsonArray(
										convertedSlot, 
										convertedSlot, 
										LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT));
							}else{
								if(StringUtils.equals(resource.getResourceSlots()[i].getAvailableInd(), "Y")){
									if(changeEveningSlotInd){
										jsonArray.add(getTimeSlotJsonArray(
												LtsDateFormatHelper.convertToPonTime(convertedSlot), 
												LtsDateFormatHelper.convertToPonTime(convertedSlot), 
												resource.getResourceSlots()[i].getApptTimeSlotType()));
									}else{
										jsonArray.add(getTimeSlotJsonArray(
												convertedSlot, 
												convertedSlot, 
												resource.getResourceSlots()[i].getApptTimeSlotType()));
									}
								}else{
									if(changeEveningSlotInd){
										jsonArray.add(getTimeSlotJsonArray(
												LtsDateFormatHelper.convertToPonTime(convertedSlot), 
												LtsDateFormatHelper.convertToPonTime(convertedSlot), 
												LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE));
									}else{
										jsonArray.add(getTimeSlotJsonArray(
												convertedSlot, 
												convertedSlot, 
												LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE));
									}
								}
							}
						}
					}
					
				}
			}
			return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		}else if(type.equals("C") || type.equals("SC")){
			String timeSlot = request.getParameter("timeSlot");
			jsonObject= LtsAppointmentHelper.calculateCutOverDateTime(dateString, timeSlot, ltsAppointmentService.getPublicHolidayList());
		}

		return new ModelAndView("ajax_ltsappointmenttimeslotlookup", jsonObject);
	}
	
	private String getTimeSlotJsonArray(String timeSlotDisplay, String timeSlotValue, String type){
		String jsonArray = "{\"tsdisplay\":\"" + timeSlotDisplay
				+ "\",\"tsvalue\":\""	+ timeSlotValue
				+ "\",\"tstype\":\""	+ type			
				+ "\"}";
		return jsonArray;
	}
}
