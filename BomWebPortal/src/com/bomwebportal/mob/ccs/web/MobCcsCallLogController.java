package com.bomwebportal.mob.ccs.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.CsiCaseLogDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiCaseLogService;
import com.bomwebportal.service.CustomerProfileService;

public class MobCcsCallLogController implements Controller {
	private CustomerProfileService customerProfileService;
	private DeliveryService deliveryService;
	private CodeLkupService codeLkupService;
	private MobCcsCsiCaseLogService mobCcsCsiCaseLogService;

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	public MobCcsCsiCaseLogService getMobCcsCsiCaseLogService() {
		return mobCcsCsiCaseLogService;
	}

	public void setMobCcsCsiCaseLogService(
			MobCcsCsiCaseLogService mobCcsCsiCaseLogService) {
		this.mobCcsCsiCaseLogService = mobCcsCsiCaseLogService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccscalllog");
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		CustomerProfileDTO customerProfile = customerProfileService.getCustomerProfileAll(orderId);
		modelAndView.addObject("customerProfile", customerProfile);
		List<CsiCaseLogDTO> resultList = this.mobCcsCsiCaseLogService.getCsiCaseLogALLByOrderId(orderId);
		modelAndView.addObject("resultList", resultList);
		List<ContactDTO> contact = this.deliveryService.findContactDTOList(orderId);
		modelAndView.addObject("contact", contact);

		Map<String, String> results = new LinkedHashMap<String, String>();
		results.put("Y", "Success");
		results.put("N", "Fail");
		modelAndView.addObject("results", results);
		List<CodeLkupDTO> callTypeCds = this.codeLkupService.getCodeLkupDTOALL("CALL_TYPE");
		modelAndView.addObject("callTypeCds", callTypeCds);
		
		return modelAndView;
	}

}
