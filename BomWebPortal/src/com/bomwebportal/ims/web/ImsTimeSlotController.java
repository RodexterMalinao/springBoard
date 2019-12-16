package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.google.gson.Gson;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsAppointmentService;

public class ImsTimeSlotController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	
	Gson gson = new Gson();
	private ImsAppointmentService appointmentService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String ApptDate = request.getParameter("date");
		logger.info("ApptDate:"+ApptDate);
		
		OrderImsUI order = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		String toProdSubTypeCd=null; 
		String fromProdSubTypeCd="";
		String orderType="D"; 
		String changeAddr="N";
		if(ImsConstants.Termination.equals(order.getImsOrderType())){
			if(order!=null&&order.getInstallAddress()!=null&&order.getInstallAddress().getAddrInventory()!=null&&
					"PON".equals(order.getInstallAddress().getAddrInventory().getTechnology())){
				toProdSubTypeCd = ImsConstants.DWFM_PON_DISCONNECT;
			}else{
				toProdSubTypeCd = ImsConstants.DWFM_NON_PON_DISCONNECT;
			}
			fromProdSubTypeCd="";// from is always null for termination
		}else if(ImsConstants.Retention.equals(order.getImsOrderType())){
			if("PON".equals(order.getServiceIms().getLineType())){
				fromProdSubTypeCd = ImsConstants.DWFM_PON_CHANGE;
			}else{
				fromProdSubTypeCd = ImsConstants.DWFM_NON_PON_CHANGE;
			}
			if(order!=null&&order.getInstallAddress()!=null&&order.getInstallAddress().getAddrInventory()!=null&&
					"PON".equals(order.getInstallAddress().getAddrInventory().getTechnology())){
				toProdSubTypeCd = ImsConstants.DWFM_PON_CHANGE;
			}else{
				toProdSubTypeCd = ImsConstants.DWFM_NON_PON_CHANGE;
			}
			orderType="C";
		}
		
		GregorianCalendar _apptDate = new GregorianCalendar(
				Integer.parseInt(ApptDate.substring(0, 4)),
				Integer.parseInt(ApptDate.substring(5, 7))-1,
				Integer.parseInt(ApptDate.substring(8, 10)));
		
		AppointmentTimeSlotDTO[] timeSlots = null;
		if(ImsConstants.Termination.equals(order.getImsOrderType())||ImsConstants.Retention.equals(order.getImsOrderType())){
			 timeSlots = appointmentService.getTimeSlot(order.getInstallAddress(), _apptDate.getTime(), 
						toProdSubTypeCd, fromProdSubTypeCd, orderType, changeAddr, null);
		}else{
			 timeSlots = appointmentService.getTimeSlot(order.getInstallAddress(), _apptDate.getTime(), order.getSupremeFsInd());
		}
						
		JSONArray jsonArray = new JSONArray();
		
		for(int i=0; i<timeSlots.length; i++){
			if(timeSlots[i].getErrorMsg() == null){
				timeSlots[i].setErrorMsg("");
			}
			jsonArray.add("{\"tsdisplay\":\"" + timeSlots[i].getTimeSlotDisplay()
					+ "\",\"tsvalue\":\""	+ timeSlots[i].getTimeSlot()
					+ "\",\"tstype\":\""	+ timeSlots[i].getSlotType()
					+ "\",\"i\":\""	+ i
					+ "\",\"errorMsg\":\""	+ timeSlots[i].getErrorMsg()
					+ "\"}");
		}			
		
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
