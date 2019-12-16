package com.bomwebportal.lts.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsRecontractController extends SimpleFormController {

	private final String viewName = "ltsrecontract";
	private final String nextView = "ltsaddressrollout.html";
	private final String commandName = "ltsRecontractCmd";
	private final String dnchangeView = "ltsdnchange.html";
	
	private CodeLkupCacheService relationshipCodeLkupCacheService;
	private CodeLkupCacheService relationshipBrCodeLkupCacheService;
	private CodeLkupCacheService recontractSrvHandleCacheService;
	private CodeLkupCacheService recontractModeCacheService;
	private CodeLkupCacheService titleLkupCacheService;

	public LtsRecontractController() {
		setCommandClass(LtsRecontractFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null || orderCapture.getLtsServiceProfile() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		LtsRecontractFormDTO form = orderCapture.getLtsRecontractForm();
		if(form == null){
			form = new LtsRecontractFormDTO ();
			AccountDetailProfileLtsDTO profileAccount = LtsSbOrderHelper.getPrimaryAccount(orderCapture.getLtsServiceProfile());
			if (profileAccount != null) {
				form.setExistingBillingName(profileAccount.getAcctName());	
			}
//			form.setNewBillingAddress(orderCapture.getLtsServiceProfile().getAddress().getFullAddress() + "\n" + "REFER L001");
			form.setSplitAccount(LtsSbOrderHelper.isSplitAccount(orderCapture.getLtsServiceProfile()));
			
			form.setVerifyRequried(orderCapture.isChannelRetail());
			
			orderCapture.setLtsRecontractForm(form);
		}
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		LtsRecontractFormDTO form = (LtsRecontractFormDTO) command;
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if (StringUtils.equalsIgnoreCase("M", form.getRecontractMode()) || StringUtils.equalsIgnoreCase("N", form.getRecontractMode())) {
			form.setUpdateDnProfile(form.getRecontractMode());
		}
		
		appendDeceaseCaseHandlingOnBa(form);
		orderCapture.setLtsRecontractForm(form);

		if(orderCapture.getLtsMiscellaneousForm().isDnChange()){
			return new ModelAndView(new RedirectView(dnchangeView)); 
		}
		
		return new ModelAndView(new RedirectView(nextView));
	}

	protected Map<String, List<LookupItemDTO>> referenceData(HttpServletRequest request) throws Exception {
		Map<String, List<LookupItemDTO>> referenceData = new HashMap<String, List<LookupItemDTO>>();
		referenceData.put("relationshipCodeList", Arrays.asList(relationshipCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("relationshipBrCodeList", Arrays.asList(relationshipBrCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		
		referenceData.put("srvHandleCodeList", Arrays.asList(recontractSrvHandleCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("recontractModeCodeList", Arrays.asList(recontractModeCacheService.getCodeLkupDAO().getCodeLkup()));

		referenceData.put("titleList", Arrays.asList(titleLkupCacheService.getCodeLkupDAO().getCodeLkup()));
				
		return referenceData;
	}
	
	private void appendDeceaseCaseHandlingOnBa (LtsRecontractFormDTO form) {
		if (StringUtils.isNotBlank(form.getNewBillingAddress())) {
			String[] newBillingAddrs = StringUtils.split(form.getNewBillingAddress(), "\n");
			if (newBillingAddrs.length >= 6) {
				form.setNewBillingAddress(form.getNewBillingAddress() + " " + "REFER L001");	
			}
			else {
				form.setNewBillingAddress(form.getNewBillingAddress() + "\n" + "REFER L001");
			}
		}
	}
	
	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public CodeLkupCacheService getRelationshipBrCodeLkupCacheService() {
		return relationshipBrCodeLkupCacheService;
	}

	public void setRelationshipBrCodeLkupCacheService(
			CodeLkupCacheService relationshipBrCodeLkupCacheService) {
		this.relationshipBrCodeLkupCacheService = relationshipBrCodeLkupCacheService;
	}

	public CodeLkupCacheService getRecontractSrvHandleCacheService() {
		return recontractSrvHandleCacheService;
	}

	public void setRecontractSrvHandleCacheService(
			CodeLkupCacheService recontractSrvHandleCacheService) {
		this.recontractSrvHandleCacheService = recontractSrvHandleCacheService;
	}

	public CodeLkupCacheService getRecontractModeCacheService() {
		return recontractModeCacheService;
	}

	public void setRecontractModeCacheService(CodeLkupCacheService recontractModeCacheService) {
		this.recontractModeCacheService = recontractModeCacheService;
	}

	public CodeLkupCacheService getTitleLkupCacheService() {
		return titleLkupCacheService;
	}

	public void setTitleLkupCacheService(CodeLkupCacheService titleLkupCacheService) {
		this.titleLkupCacheService = titleLkupCacheService;
	}
	
}
