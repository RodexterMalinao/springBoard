package com.bomwebportal.ims.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import com.bomwebportal.ims.dto.ui.AppointmentUI;

public class ImsOLEnquiryErrorController extends SimpleFormController{
	
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		String orderId = (String) request.getParameter("orderId");
		request.getSession().setAttribute("imsOnlineOrderEnquiryId", orderId);
		AppointmentUI appointment = new AppointmentUI();
		return appointment;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
	throws Exception {
		return null;
		
	}

}
