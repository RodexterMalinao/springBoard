package com.bomwebportal.lts.web.disconnect;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateCustomerInquiryFormDTO;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.order.SbOrderInfoLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsTerminateCustomerInquiryController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String viewName = "lts/disconnect/ltsterminatecustomerinquiry";
	private final String nextView = "ltsterminateserviceselection.html";
	private final String commandName = "ltsTerminateCustomerInquiryCmd";
	
	private SbOrderInfoLtsService sbOrderInfoLtsService;
	private CustomerProfileLtsService customerProfileLtsService;
	private LtsProfileService ltsProfileService;
	private LtsOfferService ltsOfferService;
	protected CodeLkupCacheService specialHandleDocIdLkupCacheService;
	protected CodeLkupCacheService restrictPsefLkupCacheService;
	
	private boolean bypassSso = false;

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

	public boolean isBypassSso() {
		return bypassSso;
	}

	public void setBypassSso(boolean bypassSso) {
		this.bypassSso = bypassSso;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public CodeLkupCacheService getSpecialHandleDocIdLkupCacheService() {
		return specialHandleDocIdLkupCacheService;
	}

	public void setSpecialHandleDocIdLkupCacheService(
			CodeLkupCacheService specialHandleDocIdLkupCacheService) {
		this.specialHandleDocIdLkupCacheService = specialHandleDocIdLkupCacheService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}
	
	public CodeLkupCacheService getRestrictPsefLkupCacheService() {
		return restrictPsefLkupCacheService;
	}

	public void setRestrictPsefLkupCacheService(
			CodeLkupCacheService restrictPsefLkupCacheService) {
		this.restrictPsefLkupCacheService = restrictPsefLkupCacheService;
	}

	public LtsTerminateCustomerInquiryController() {
		setCommandClass(LtsTerminateCustomerInquiryFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// Get parameter from SVC
		String paramSrvNum = (String)request.getParameter(LtsConstant.REQUEST_PARAM_SRV_NUM);
		
		if (bypassSso || StringUtils.isEmpty(paramSrvNum) || LtsSessionHelper.isBackdoorMode(request)) {
			return super.handleRequestInternal(request, response);	
		}
		
		LtsSessionHelper.clearAllTerminateOrderSessionAttb(request);
		LtsTerminateCustomerInquiryFormDTO form = new LtsTerminateCustomerInquiryFormDTO();
		form.setServiceNum(StringUtils.leftPad(paramSrvNum, 12, "0"));
		return handleSubmit(request, form);
	}
	
	private ModelAndView handleSubmit(HttpServletRequest request, LtsTerminateCustomerInquiryFormDTO form) {
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		String locale = RequestContextUtils.getLocale(request).toString();
		if (StringUtils.isNotBlank(form.getServiceNum())) {
			form.setServiceNum(form.getServiceNum().trim());
		}
		ServiceDetailProfileLtsDTO serviceProfile = ltsProfileService.retrieveServiceProfile(form.getServiceNum(), bomSalesUser.getUsername(), locale);
		boolean isValidProfile = validateProfile(request, serviceProfile, bomSalesUser, form);

		if (!isValidProfile) {
			// Not allow create order
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			return mav;	
		}
		
		TerminateOrderCaptureDTO orderCapture = new TerminateOrderCaptureDTO();
		orderCapture.setLtsServiceProfile(serviceProfile);
		orderCapture.setImsPendingOrder(getImsPendingOrder(serviceProfile));
		orderCapture.setLtsTerminateCustomerInquiryForm(form);
		LtsSessionHelper.setTerminateOrderCapture(request, orderCapture);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		LtsTerminateCustomerInquiryFormDTO form = (LtsTerminateCustomerInquiryFormDTO) command;
		
		switch (form.getFormAction()){
		case CLEAR_PROFILE:
			LtsSessionHelper.clearTerminateOrderCapture(request);
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, new LtsTerminateCustomerInquiryFormDTO());
			return mav;
		}

		return handleSubmit(request, form);
	}
	
	
	@Override
	protected Object formBackingObject(HttpServletRequest pRequest) throws Exception {
		return new LtsTerminateCustomerInquiryFormDTO();
	}
	
	private boolean validateProfile(HttpServletRequest request, ServiceDetailProfileLtsDTO serviceProfile, 
			BomSalesUserDTO bomSalesUser, LtsTerminateCustomerInquiryFormDTO form) {
		
		List<String> messageList = new ArrayList<String>();
		
		if (serviceProfile == null) {
			messageList.add("Service profile not found.");
		}
		else {
			if (serviceProfile.getPrimaryCust() == null) {
				messageList.add("Customer profile not found.");
			}
			else if (serviceProfile.getAddress() == null) {
				messageList.add("Address profile not found.");
			}
			else if (ArrayUtils.isEmpty(serviceProfile.getAccounts())) {
				messageList.add("Account profile not found.");
			}
			else if (!StringUtils.equals(serviceProfile.getInventStatus(), "20")
					&& !StringUtils.equals(serviceProfile.getInventStatus(), "14")) {
				messageList.add("Service line status is not working.");
			}
			else if (StringUtils.isNotEmpty(serviceProfile.getPendingOcid()) 
					&& StringUtils.equals("D", serviceProfile.getPendingOrdType())){
				messageList.add("Existing pending D order: " + serviceProfile.getPendingOcid());
			}
			
			if(serviceProfile.getExistEyeType() != null && serviceProfile.getRelatedEyeFsa() == null){
				messageList.add("Service group profile corrupt.");
			}
			
			if (serviceProfile.getAddress().getAddressRollout() != null
					&& StringUtils.isNotEmpty(serviceProfile.getAddress().getAddressRollout().getHktPremier())) {
				if (!LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
					messageList.add("HKT Premier Customer. Please contact HKT Premier Team.");
				}
			}
			else if (serviceProfile.getAddress().getAddressRollout() != null
					&& StringUtils.isEmpty(serviceProfile.getAddress().getAddressRollout().getHktPremier())) {
				if (LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
					messageList.add("Mass Customer. Please contact Mass Team.");
				}
			}
			
			if (ArrayUtils.isNotEmpty(serviceProfile.getRestrictedOfferActions())) {
				for (OfferActionLtsDTO restrictedOfferAction : serviceProfile.getRestrictedOfferActions()) {
					if (StringUtils.equals("*", restrictedOfferAction.getAction())
							|| LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(restrictedOfferAction.getAction()))
						
						// W_CODE_LKUP GRP_ID = 'RESTRICT_PSEF'
						if (restrictPsefLkupCacheService.get(restrictedOfferAction.getCode()) != null) {
							if (LtsConstant.RESTRICTED_PSEF_4BBH.equals(restrictedOfferAction.getCode())
									&& ! LtsSbOrderHelper.allow4BBHProfile(serviceProfile)) {
								messageList.add("DOB missing or age below 60 or eye2A start date not on / before Dec 31, 2013");		
							}
						}
						else {
							messageList.add(restrictedOfferAction.getDescription());		
						}
				}
			}
			
			// 20160905
			if(serviceProfile.getSrvGrp() != null 
					&& serviceProfile.getSrvGrp().getGrpMems() != null
					&& serviceProfile.getSrvGrp().getGrpMems().length > 2){
				String srvNum = form.getServiceNum();
				ServiceGroupMemberProfileDTO grpMemNcr = serviceProfile.getSrvGrp().getMemberByType("NCR");
				if(grpMemNcr != null
						&& grpMemNcr.getSrvNum().equals(StringUtils.leftPad(srvNum, 12, '0'))){
					messageList.add("Existing eye SIP Service number, not allow to create order. Please input PE Service number.");
				}
				
			}
		}
		
		if (messageList.size() >= 1) {
			request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, messageList);
			return false;
		}
		return true;
	}
	
	private ImsPendingOrderDTO getImsPendingOrder(ServiceDetailProfileLtsDTO serviceProfile) {
		
		if (serviceProfile != null 
				&& StringUtils.isNotEmpty(serviceProfile.getRelatedEyeFsa())) {
			List<ImsPendingOrderDTO> imsPendingOrderList = sbOrderInfoLtsService.getSbImsLatestPendingOrderBySrvNum(serviceProfile.getRelatedEyeFsa());
			if (imsPendingOrderList != null && !imsPendingOrderList.isEmpty()) {
				return imsPendingOrderList.get(0);
			}
		}
		return null;
	}
	
}
