package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AppointmentSubmitDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsAppointmentService;

public class ImsReserveAppointmentController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsAppointmentService appointmentService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {				
		String ApptDate = request.getParameter("date");
		String TimeSlot = request.getParameter("time");
		String TimeSlotType = request.getParameter("type");
		logger.info("ApptDate:"+ApptDate);
		logger.info("TimeSlot:"+TimeSlot);
		logger.info("TimeSlotType:"+TimeSlotType);
		request.getSession().setAttribute(ImsConstants.IMS_AMEND_TIMESLOTTYPE, TimeSlotType);
		
		AppointmentTimeSlotDTO timeSlot = new AppointmentTimeSlotDTO();
		timeSlot.setApptDate(ApptDate.replace("/", ""));
		timeSlot.setTimeSlot(TimeSlot);
		timeSlot.setSlotType(TimeSlotType);
		
		OrderImsUI order = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		if(request.getSession().getAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL)!=null){
			logger.info("found reserved serial before booking");
			appointmentService.cancelReservedTimeSlot(
					(String)request.getSession().getAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL));
		}
		
		//String Serialnum = appointmentService.reserveTimeSlot(order.getInstallAddress(), timeSlot);
		AppointmentSubmitDTO appointmentSubmit = appointmentService.reserveTimeSlot(order.getInstallAddress(), timeSlot, order.getSupremeFsInd());
		
		String Serialnum = appointmentSubmit.getSerialNum();
		int returnValue = appointmentSubmit.getReturnValue();
		String errorMsg = appointmentSubmit.getErrorMsg();
		
		order.getAppointment().setSerialNum(Serialnum);
		//order.getAppointment().setTargetInstallDate(ApptDate);
		//order.getAppointment().setTimeSlot(TimeSlot);
		
		request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, Serialnum);
		
		Calendar appntStart = new GregorianCalendar(
				Integer.parseInt(ApptDate.substring(0,4)),
				Integer.parseInt(ApptDate.substring(5,7))-1,
				Integer.parseInt(ApptDate.substring(8,10)),
				Integer.parseInt(TimeSlot.substring(0, 2)),
				Integer.parseInt(TimeSlot.substring(3, 5)));
		
		Calendar appntEnd = new GregorianCalendar(
				Integer.parseInt(ApptDate.substring(0,4)),
				Integer.parseInt(ApptDate.substring(5,7))-1,
				Integer.parseInt(ApptDate.substring(8,10)),
				Integer.parseInt(TimeSlot.substring(6, 8)),
				Integer.parseInt(TimeSlot.substring(9, 11)));
		
		order.getAppointment().setAppntStartDate(appntStart.getTime());
		order.getAppointment().setAppntEndDate(appntEnd.getTime());
		//order.getAppointment().setTidInd(TimeSlotType);
		order.getAppointment().setAppntType(TimeSlotType);
		
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"serial\":\"" + Serialnum +				
				"\",\"returnValue\":" + returnValue +
				",\"errorMsg\":\"" + errorMsg +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		
	}		

	public ImsAppointmentService getAppointmentService() {
		return appointmentService;
	}

	public void setAppointmentService(ImsAppointmentService appointmentService) {
		this.appointmentService = appointmentService;
	}

}
