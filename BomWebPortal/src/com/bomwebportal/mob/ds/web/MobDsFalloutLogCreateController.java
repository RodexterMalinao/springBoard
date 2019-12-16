package com.bomwebportal.mob.ds.web;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;
import com.bomwebportal.mob.ds.service.MobDsFalloutLogService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;

public class MobDsFalloutLogCreateController extends SimpleFormController{
	private CustomerProfileService customerProfileService;
	private CodeLkupService codeLkupService;
	private MobDsFalloutLogService mobDsFalloutLogService;
	private OrderService orderService;
	
	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}
	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public MobDsFalloutLogService getMobDsFalloutLogService() {
		return mobDsFalloutLogService;
	}
	public void setMobDsFalloutLogService(
			MobDsFalloutLogService mobDsFalloutLogService) {
		this.mobDsFalloutLogService = mobDsFalloutLogService;
	}

	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		MobDsFalloutLogDTO form = new MobDsFalloutLogDTO();
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		if (StringUtils.isNotBlank(orderId)) {
			CustomerProfileDTO customerProfile = this.customerProfileService.getCustomerProfile(orderId);
			if (customerProfile != null) {
				form.setContactName(customerProfile.getContactName());
				form.setContactPhone(customerProfile.getContactPhone());
				form.setContactEmail(customerProfile.getEmailAddr());
				form.setOrderId(orderId);
			}
		}
		String caseNo = ServletRequestUtils.getRequiredStringParameter(request, "caseNo");
		if (StringUtils.isNotBlank(caseNo)) {
				form.setCaseNo(caseNo);
		}
		return form;
	}
	
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobDsFalloutLogCreateController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		CustomerProfileDTO customerProfile = this.customerProfileService.getCustomerProfile(orderId);
		referenceData.put("customerProfile", customerProfile);
		
		OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);
		referenceData.put("orderDTO", orderDTO);		
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		referenceData.put("user", user);

		referenceData.put("callTypeCds", this.codeLkupService.getCodeLkupDTOALL("CALL_TYPE"));
		referenceData.put("relWtCustLst", this.codeLkupService.getCodeLkupDTOALL("REL_WT_CUST"));
		referenceData.put("results", this.codeLkupService.getCodeLkupDTOALL("CALL_RESULT_TYPE"));
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		MobDsFalloutLogDTO form = (MobDsFalloutLogDTO) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobdsfalloutrecordcreate.html"));
		CustomerProfileDTO customerProfile = this.customerProfileService.getCustomerProfile(form.getOrderId());
		
		if (customerProfile != null) {
			Date now = new Date();
			MobDsFalloutLogDTO dto = new MobDsFalloutLogDTO();

			dto.setCaseNo(StringUtils.trim(form.getCaseNo()));
			dto.setOrderId(StringUtils.trim(form.getOrderId()));
			dto.setContactName(StringUtils.trim(form.getContactName()));
			dto.setContactPhone(StringUtils.trim(form.getContactPhone()));
			dto.setContactEmail(StringUtils.trim(form.getContactEmail()));
			dto.setContactBy(user.getUsername());
			dto.setResult(form.getResult());
			dto.setNature(form.getNature());
			dto.setRemark(StringUtils.trim(form.getRemark()));
			dto.setCreateDate(now);
			dto.setCallTypeCd(form.getCallTypeCd());
			
			mobDsFalloutLogService.insertFalloutLogDTO(dto);
			//modelAndView.addObject("recordCreated", true);
		}
		
		modelAndView.addObject("orderId", form.getOrderId());
		modelAndView.addObject("caseNo", form.getCaseNo());
		/*if (StringUtils.isNotBlank(form.getCaseNo())){
				modelAndView.addObject("caseNo", form.getCaseNo());
				modelAndView.setView(new RedirectView("mobdsfalloutrecordcreate.html"));
		}*/
		
		return modelAndView;
	}
	
}
