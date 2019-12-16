package com.bomwebportal.mob.ccs.web;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.ui.SpecialMRTReserveUI;
import com.bomwebportal.mob.ccs.service.SpecialMRTReserveService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;


public class MobCcsSpecialMRTReserveController extends SimpleFormController {
	
	SpecialMRTReserveService specialMRTReserveService;
	
	
	
	
	public SpecialMRTReserveService getSpecialMRTReserveService() {
		return specialMRTReserveService;
	}




	public void setSpecialMRTReserveService(
			SpecialMRTReserveService specialMRTReserveService) {
		this.specialMRTReserveService = specialMRTReserveService;
	}




	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		SpecialMRTReserveUI sessionSpecialMRTReserve = (SpecialMRTReserveUI) MobCcsSessionUtil.getSession(request, "specialMRTRequest"); //get a session (cast an object to SpecialMrtRequestDTO)
		
		if (sessionSpecialMRTReserve == null) {
			
			logger.info("MobCcsSpecialMRTReserveController@formBackingObject called , sessionSpecialMRTReserve is null");
			
			sessionSpecialMRTReserve = new SpecialMRTReserveUI();
			
			List<SpecialMRTReserveUI> reserveSpecialMRTList = specialMRTReserveService.getSpecialReserveMRTList(user.getUsername());
			sessionSpecialMRTReserve.setReserveSpecialMRTList(reserveSpecialMRTList);
			request.setAttribute("reserveSpecialMRTList", reserveSpecialMRTList);  //for ...items="${reserveSpecialMRTList}"...
			
			Integer count = specialMRTReserveService.noOfRequests(user.getUsername());
			if (count >= 2) {
				
				sessionSpecialMRTReserve.setTwoRequestsAlready(true);
				request.setAttribute("twoRequestsAlready", true);
			
			} else {
				
				sessionSpecialMRTReserve.setTwoRequestsAlready(false);
				request.setAttribute("twoRequestsAlready", false);
				
			}
			
			
			//below is for rejected MRT Requests table
			/*
			 * List<SpecialMRTReserveUI> rejectedSpecialMRTList = specialMRTReserveService.getRejectedSpecialMRTRequest(user.getUsername());
			 * sessionSpecialMRTReserve.setRejectedSpecialMRTList(rejectedSpecialMRTList);
			 * request.setAttribute("rejectedSpecialMRTList", rejectedSpecialMRTList);
			 */
			
			
			MobCcsSessionUtil.setSession(request, "specialMRTReserve", sessionSpecialMRTReserve);
			
		}
		
		return sessionSpecialMRTReserve;
		
	}
	
	

}
