package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.mob.ccs.dto.ui.MobCcsFalloutUI;
import com.bomwebportal.service.OrderService;

public class MobCcsFalloutHistoryController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	OrderService orderService;
	
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

	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		MobCcsFalloutUI ui = new MobCcsFalloutUI();

		return ui;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		String orderId = (String) request.getParameter("orderId");
		
		List<MobCcsFalloutUI> falloutHist = orderService.getOrderFalloutHist(orderId);
		
		Map referenceData = new HashMap<String, List<String>>();
		referenceData.put("falloutHist", falloutHist);
		
		return referenceData;
	}
}
