package com.bomwebportal.mob.ds.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutRecordDTO;
import com.bomwebportal.mob.ds.service.MobDsFalloutRecordService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;

public class MobDsFalloutRecordController implements Controller{

	private MobDsFalloutRecordService mobDsFalloutRecordService;
	private OrderService orderService;
	private CustomerProfileService customerProfileService;
	
	public MobDsFalloutRecordService getMobDsFalloutRecordService() {
		return mobDsFalloutRecordService;
	}

	public void setMobDsFalloutRecordService(
			MobDsFalloutRecordService mobDsFalloutRecordService) {
		this.mobDsFalloutRecordService = mobDsFalloutRecordService;
	}


	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobdsfalloutrecord");
		

		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		modelAndView.addObject("user", user);
				
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);
		modelAndView.addObject("orderDTO", orderDTO);
		
		CustomerProfileDTO customerProfile = customerProfileService.getCustomerProfile(orderId);
		modelAndView.addObject("customerProfile", customerProfile);
		
		
		List<MobDsFalloutRecordDTO> falloutRecordList = this.mobDsFalloutRecordService.getFalloutRecordALLByOrderId(orderId);
		modelAndView.addObject("falloutRecordList", falloutRecordList);
		// TODO Auto-generated method stub
		return modelAndView;
	}

}
