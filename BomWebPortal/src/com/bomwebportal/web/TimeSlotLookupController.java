package com.bomwebportal.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;

import net.sf.json.JSONArray;

public class TimeSlotLookupController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private DeliveryService deliveryService;
	private OrderService orderService;
	
	/**
	 * @return the deliveryService
	 */
	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	/**
	 * @param deliveryService the deliveryService to set
	 */
	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}
	
	/**
	 * @return the orderService
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * @param orderService the orderService to set
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String districtCd = request.getParameter("district"); 
		String orderType = request.getParameter("orderType");
		String appDate = request.getParameter("appDate");
		
		Date date = new Date();
		if (appDate!=null){		
			date = Utility.string2Date(appDate);
			if (date==null){
				date = new Date();
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		
		String slotType = "SCH";
		/*if ("SBS".equalsIgnoreCase(orderType)){
			slotType = "OSF";
		}*/
		
		if (districtCd.equalsIgnoreCase("URGENT")) {
			
			List<String[]> timeSlotArray = deliveryService.findUrgentTimeSlot();
			
			for (int i = 0; i < timeSlotArray.size(); i++) {
				jsonArray.add("{\"timeSlot\":\""	+ (timeSlotArray.get(i))[0] 
						+ "\",\"time\":\""	+ (timeSlotArray.get(i))[1] + "-" + (timeSlotArray.get(i))[2] + "\"}");
			}
			
			logger.info("jsonArray : " + jsonArray);
			
		} else {
			
			
			List<String[]> timeSlotArray = deliveryService.findTimeSlot(districtCd, slotType, date);
			
			for (int i = 0; i < timeSlotArray.size(); i++) {
				jsonArray.add("{\"timeSlot\":\""	+ (timeSlotArray.get(i))[0] 
						+ "\",\"time\":\""	+ (timeSlotArray.get(i))[1] + "-" + (timeSlotArray.get(i))[2] + "\"}");
			}
			
			
			logger.info("jsonArray : " + jsonArray);
		}
		
		System.out.println(jsonArray);
		
		return new ModelAndView("ajax_TimeSlotLookup", "jsonArray",	jsonArray);
	}

}
