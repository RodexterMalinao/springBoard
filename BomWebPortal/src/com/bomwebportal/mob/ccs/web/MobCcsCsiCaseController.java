package com.bomwebportal.mob.ccs.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.CsiCaseDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiCaseService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;

public class MobCcsCsiCaseController implements Controller {
	private MobCcsCsiCaseService mobCcsCsiCaseService;
	private CustomerProfileService customerProfileService;
    private OrderService orderService;
	private DeliveryService deliveryService;
	private CodeLkupService codeLkupService;
	
		public MobCcsCsiCaseService getMobCcsCsiCaseService() {
		return mobCcsCsiCaseService;
	}

	public void setMobCcsCsiCaseService(MobCcsCsiCaseService mobCcsCsiCaseService) {
		this.mobCcsCsiCaseService = mobCcsCsiCaseService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}
	
    public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
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

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccscsicase");
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		CustomerProfileDTO customerProfile = customerProfileService.getCustomerProfileAll(orderId);
		modelAndView.addObject("customerProfile", customerProfile);
		OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);
		modelAndView.addObject("orderDTO", orderDTO);
		List<CsiCaseDTO> resultList = this.mobCcsCsiCaseService.getCsiCaseALLByOrderId(orderId);
		modelAndView.addObject("resultList", resultList);
		List<ContactDTO> contact = this.deliveryService.findContactDTOList(orderId);
		modelAndView.addObject("contact", contact);

		/*Map<String, String> results = new LinkedHashMap<String, String>();
		results.put("Y", "Success");
		results.put("N", "Fail");
		modelAndView.addObject("results", results);*/
		List<CodeLkupDTO> callTypeCds = this.codeLkupService.getCodeLkupDTOALL("CALL_TYPE");
		modelAndView.addObject("callTypeCds", callTypeCds);
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");//add by wilson 20121218, for create button control
		modelAndView.addObject("user", salesUserDto);//add by wilson 20121218, for create button control
		
		return modelAndView;
	}

}
