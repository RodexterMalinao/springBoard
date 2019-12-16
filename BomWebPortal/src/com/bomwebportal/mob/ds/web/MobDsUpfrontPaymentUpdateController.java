package com.bomwebportal.mob.ds.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ds.dto.MobDsFalloutRecordDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.mob.ds.service.MobDsFalloutRecordService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.PaymentService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobDsUpfrontPaymentUpdateController extends SimpleFormController{

	private MobDsFalloutRecordService mobDsFalloutRecordService;
	private OrderService orderService;
	private CustomerProfileService customerProfileService;
	private PaymentService service;
	private VasDetailService vasService;
	
	
	public PaymentService getService() {
		return service;
	}

	public void setService(PaymentService service) {
		this.service = service;
	}

	public VasDetailService getVasService() {
		return vasService;
	}

	public void setVasService(VasDetailService vasService) {
		this.vasService = vasService;
	}

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

	public Object formBackingObject(HttpServletRequest request) throws Exception {
		
		String orderId = (String) request.getParameter("orderId");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		//get info from DB
		MobDsPaymentUpfrontDTO paymentUpfrontDto = orderService.getPaymentUpfront(orderId);

		request.getSession().setAttribute("paymentUpfrontDto", paymentUpfrontDto);
		
		return paymentUpfrontDto;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		String nextView = (String) request.getAttribute("nextView");
		MobDsPaymentUpfrontDTO paymentUpfrontDto = (MobDsPaymentUpfrontDTO) command;
		String orderId = (String) request.getParameter("orderId");

		orderService.updateDsCashPay(orderId, paymentUpfrontDto.getPaymentTransList());
		request.getSession().setAttribute("paymentUpfrontDto", paymentUpfrontDto);
		//paymentUpfrontDto = orderService.updateDsCashPay(paymentUpfrontDto);
		nextView = "mobdsupfrontpaymentupdate.html?orderId="+orderId;
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
		
		return modelAndView;
	}
}
