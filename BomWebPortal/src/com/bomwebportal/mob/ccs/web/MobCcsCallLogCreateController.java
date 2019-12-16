package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
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
import com.bomwebportal.mob.ccs.dto.CsiCaseLogDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiCaseLogService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiCaseService;
import com.bomwebportal.service.CustomerProfileService;

public class MobCcsCallLogCreateController extends SimpleFormController {
	private CustomerProfileService customerProfileService;
	private CodeLkupService codeLkupService;
	private MobCcsCsiCaseLogService mobCcsCsiCaseLogService;
	private MobCcsCsiCaseService mobCcsCsiCaseService;
	
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

	public MobCcsCsiCaseLogService getMobCcsCsiCaseLogService() {
		return mobCcsCsiCaseLogService;
	}

	public void setMobCcsCsiCaseLogService(
			MobCcsCsiCaseLogService mobCcsCsiCaseLogService) {
		this.mobCcsCsiCaseLogService = mobCcsCsiCaseLogService;
	}

	public MobCcsCsiCaseService getMobCcsCsiCaseService() {
		return mobCcsCsiCaseService;
	}

	public void setMobCcsCsiCaseService(MobCcsCsiCaseService mobCcsCsiCaseService) {
		this.mobCcsCsiCaseService = mobCcsCsiCaseService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		CsiCaseLogDTO form = new CsiCaseLogDTO();
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		if (StringUtils.isNotBlank(orderId)) {
			CustomerProfileDTO dto = this.customerProfileService.getCustomerProfileAll(orderId);
			if (dto != null) {
				form.setContactName(dto.getContactName());
				form.setContactPhone(dto.getContactPhone());
				form.setContactEmail(dto.getEmailAddr());
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
		logger.info("MobCcsCallLogCreateController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		referenceData.put("callTypeCds", this.codeLkupService.getCodeLkupDTOALL("CALL_TYPE"));
		referenceData.put("relWtCustLst", this.codeLkupService.getCodeLkupDTOALL("REL_WT_CUST"));
		referenceData.put("results", this.codeLkupService.getCodeLkupDTOALL("CALL_RESULT_TYPE"));
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		CsiCaseLogDTO form = (CsiCaseLogDTO) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccscalllog.html"));
		CustomerProfileDTO customerProfile = this.customerProfileService.getCustomerProfileAll(form.getOrderId());
		
		if (customerProfile != null) {
			Date now = new Date();
			CsiCaseLogDTO dto = new CsiCaseLogDTO();
			dto.setCaseNo(StringUtils.trim(form.getCaseNo()));
			dto.setOrderId(StringUtils.trim(form.getOrderId()));
			dto.setContactName(StringUtils.trim(form.getContactName()));
			dto.setContactPhone(StringUtils.trim(form.getContactPhone()));
			dto.setContactEmail(StringUtils.trim(form.getContactEmail()));
			dto.setContactBy(user.getUsername());
			dto.setResultCd(form.getResultCd());
			dto.setRelWtCust(form.getRelWtCust());
			dto.setRemark(StringUtils.trim(form.getRemark()));
			dto.setCreateDate(now);
			dto.setCallTypeCd(form.getCallTypeCd());
			dto.setCreateBy(user.getUsername());
			dto.setLastUpdBy(user.getUsername());
			mobCcsCsiCaseLogService.insertCsiCaseLogDTO(dto);
			mobCcsCsiCaseService.updateContactCount(StringUtils.trim(form.getCaseNo()));
			modelAndView.addObject("recordCreated", true);
		}
		
		modelAndView.addObject("orderId", form.getOrderId());
		
		if (StringUtils.isNotBlank(form.getCaseNo())){
			modelAndView.addObject("caseNo", form.getCaseNo());
			modelAndView.setView(new RedirectView("mobccscsicasecreate.html"));
		}
		return modelAndView;
	}
}
