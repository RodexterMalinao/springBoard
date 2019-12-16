package com.bomwebportal.mob.ccs.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.OnlinePaymentTxn;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentAdminService;

public class MobCcsPaymentTransHistoryController implements Controller {
	private MobCcsPaymentAdminService mobCcsPaymentAdminService;
	
	public MobCcsPaymentAdminService getMobCcsPaymentAdminService() {
		return mobCcsPaymentAdminService;
	}
	
	public void setMobCcsPaymentAdminService(
			MobCcsPaymentAdminService mobCcsPaymentAdminService) {
		this.mobCcsPaymentAdminService = mobCcsPaymentAdminService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccspaymenttranshistory");
		String orderId = ServletRequestUtils.getStringParameter(request, "orderId");
		if (StringUtils.isNotBlank(orderId)) {
			List<MobCcsPaymentTransDTO> resultList = this.mobCcsPaymentAdminService.getMobCcsPaymentTransDTOByOrderId(orderId);
			modelAndView.addObject("resultList", resultList);
			
			List<OnlinePaymentTxn> paymentTxnResultList =this.mobCcsPaymentAdminService.getOnlinePaymentTransDTOByOrderId( orderId);
			modelAndView.addObject("paymentTxnResultList", paymentTxnResultList);
		}
		return modelAndView;
	}

}
