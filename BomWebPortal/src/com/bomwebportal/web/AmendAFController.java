package com.bomwebportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.SupportDocAdminService;

public class AmendAFController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private SupportDocAdminService supportDocAdminService;
	private OrderService orderService;

	public SupportDocAdminService getSupportDocAdminService() {
		return supportDocAdminService;
	}
	public void setSupportDocAdminService(
			SupportDocAdminService supportDocAdminService) {
		this.supportDocAdminService = supportDocAdminService;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String orderId = request.getParameter("orderId");
		
		logger.debug("AmendAFController (orderId): " + orderId);
		
		OrdDocAssgnDTO temp = new OrdDocAssgnDTO();
		temp.setOrderId(orderId);
		temp.setDocType("M012");
		temp.setWaiveReason(null);
		temp.setCollectedInd("N");
		temp.setWaivedBy(null);
		temp.setLastUpdBy(user.getUsername());
		
		int result = 0;
		result = supportDocAdminService.insertOrdDocAssgn(temp);
		result += orderService.updateDmsInd(orderId, "N", user.getUsername());
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("{\"insertResult\":\"" + result + "\"}");

		return new ModelAndView("ajax_search", "jsonArray", jsonArray);
	}
}
