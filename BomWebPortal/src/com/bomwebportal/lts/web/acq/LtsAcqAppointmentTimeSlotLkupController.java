package com.bomwebportal.lts.web.acq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.acq.LtsAcqAppointmentService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqAppointmentTimeSlotLkupController implements Controller {
	private AppointmentDwfmService appointmentDwfmService;
	private LtsAcqAppointmentService ltsAcqAppointmentService;
	private LtsAppointmentService ltsAppointmentService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		if (order == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}

		String dateString = request.getParameter("date");
		String type = request.getParameter("type");

		if(type.equals("NEWACQ") || type.equals("PREWIRING")){
			JSONArray jsonArray = createTimeSlotJsonArray(order, dateString, 
					order.isEyeOrder() ? LtsConstant.DWFM_INPUT_TYPE_NEWACQ_EYE : LtsConstant.DWFM_INPUT_TYPE_NEWACQ_DEL);
			return new ModelAndView("ajax_view", "jsonArray", jsonArray);
			
		}else if(type.equals("SECDEL")){
			JSONArray jsonArray = createTimeSlotJsonArray(order, dateString, LtsConstant.DWFM_INPUT_TYPE_NEWACQ_DEL);
			return new ModelAndView("ajax_view", "jsonArray", jsonArray);
			
		}else if(type.equals("ALL")){
			JSONArray jsonArray = createTimeSlotJsonArray(order, dateString, 
					order.isEyeOrder() ? LtsConstant.DWFM_INPUT_TYPE_ALL: LtsConstant.DWFM_INPUT_TYPE_NEWACQ_DEL);
			return new ModelAndView("ajax_view", "jsonArray", jsonArray);
			
		}else{
			//else calcuate cutover time
			String timeSlot = request.getParameter("timeSlot");
			JSONObject jsonObject= LtsAppointmentHelper.calculateCutOverDateTime(dateString, timeSlot, ltsAppointmentService.getPublicHolidayList());
			return new ModelAndView("ajax_ltsappointmenttimeslotlookup", jsonObject);
		}
		
	}

	private JSONArray createTimeSlotJsonArray(AcqOrderCaptureDTO order, String dateString, String detailInputType){
		ResourceDetailInputDTO resourceInput = ltsAcqAppointmentService.getResourceDetailInput(order, dateString, detailInputType);
		ResourceDetailOutputDTO resource = appointmentDwfmService.getResourceDetail(resourceInput);
		if(StringUtils.equals(dateString, LtsAppointmentHelper.getToday())){
			LtsAppointmentHelper.disableTimeSlotByType(resource, LtsConstant.APPOINTMENT_TIMESLOT_TYPE_AM);
		}
		if(order.isChannelDirectSales()){
			LtsAppointmentHelper.disableTimeSlotByType(resource, LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
		}
		return LtsAppointmentHelper.formatResourceToJsonArray(resource, ltsAcqAppointmentService.getChangePonTimeSlotInd(order));
	}
	
	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(
			AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public LtsAcqAppointmentService getLtsAcqAppointmentService() {
		return ltsAcqAppointmentService;
	}

	public void setLtsAcqAppointmentService(
			LtsAcqAppointmentService ltsAcqAppointmentService) {
		this.ltsAcqAppointmentService = ltsAcqAppointmentService;
	}

	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

}
