package com.bomwebportal.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SignoffDTO;

public class SignoffController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("SignoffController called formBackingObject()");
		
		
		SignoffDTO signDto = (SignoffDTO) request.getSession().getAttribute("signoffSession");
		//String orderId = (String) request.getParameter("orderId");
		//String orderStatus = (String) request.getParameter("status");
		
		// get info from session
		//BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		// get info from DB
		//OrderDTO orderDto = orderService.getOrder(orderId);
		
		logger.info("#### - user-agent ************" + request.getHeader("user-agent"));
		
		if (signDto == null) {
			signDto = new SignoffDTO();
		}

		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		logger.info("SignoffController called onSubmit()");
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		SignoffDTO signoffDto = (SignoffDTO) command;
		
		OrderDTO orderDto =  (OrderDTO)request.getSession().getAttribute("orderdetailOrderDto");
		String orderId = orderDto.getOrderId();
		signoffDto.setOrderId(orderId);
		request.getSession().setAttribute("signoffDtoSession", signoffDto);

		return new ModelAndView(new RedirectView("summary.html"));
	}

	protected Map<String, List<String>> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");
		Map<String, List<String>> referenceData = new HashMap<String, List<String>>();

		return referenceData;
	}
}
