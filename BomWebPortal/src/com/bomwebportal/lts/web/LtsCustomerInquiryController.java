package com.bomwebportal.lts.web;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.ServiceLineDTO;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsCustomerInquiryFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerInquiryFormDTO.Action;
import com.bomwebportal.lts.dto.profile.CustomerRemarkProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsAddressRolloutService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsRelatedPcdOrderService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsCustomerInquiryController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());

	private final String viewName = "ltscustomerinquiry";
	private final String nextView = "ltsmiscellaneous.html";
	private final String csNextView = "ltsaddressrollout.html";
	private final String commandName = "ltsCustomerInquiryCmd";
	
	private LtsProfileService ltsProfileService;
	private LtsOfferService ltsOfferService;
	protected CodeLkupCacheService specialHandleDocIdLkupCacheService;
	private CustomerProfileLtsService customerProfileLtsService;
	protected CodeLkupCacheService ltsFuncLkupCacheService;
	protected LtsRelatedPcdOrderService ltsRelatedPcdOrderService;
	protected LtsAddressRolloutService ltsAddressRolloutService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	private boolean bypassSso = false;

	public boolean isBypassSso() {
		return bypassSso;
	}

	public void setBypassSso(boolean bypassSso) {
		this.bypassSso = bypassSso;
	}

	public LtsRelatedPcdOrderService getLtsRelatedPcdOrderService() {
		return ltsRelatedPcdOrderService;
	}

	public void setLtsRelatedPcdOrderService(
			LtsRelatedPcdOrderService ltsRelatedPcdOrderService) {
		this.ltsRelatedPcdOrderService = ltsRelatedPcdOrderService;
	}

	public CodeLkupCacheService getLtsFuncLkupCacheService() {
		return ltsFuncLkupCacheService;
	}

	public void setLtsFuncLkupCacheService(
			CodeLkupCacheService ltsFuncLkupCacheService) {
		this.ltsFuncLkupCacheService = ltsFuncLkupCacheService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
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

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public LtsAddressRolloutService getLtsAddressRolloutService() {
		return ltsAddressRolloutService;
	}

	public void setLtsAddressRolloutService(
			LtsAddressRolloutService ltsAddressRolloutService) {
		this.ltsAddressRolloutService = ltsAddressRolloutService;
	}

	public LtsCustomerInquiryController() {
		setCommandClass(LtsCustomerInquiryFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		LtsCustomerInquiryFormDTO form = (LtsCustomerInquiryFormDTO) command;
		
		ModelAndView mav = new ModelAndView(viewName);
		OrderCaptureDTO orderCapture = null;
		switch (form.getFormAction()) {
		case CLEAR_PROFILE:
			LtsSessionHelper.clearAllSessionAttb(request);
			form = new LtsCustomerInquiryFormDTO();
			String searchProfileFunc = (String)ltsFuncLkupCacheService.get(LtsConstant.SB_LTS_FUNC_SEARCH_PROFILE);
			form.setAllowSearchProfile(LtsSessionHelper.isBackdoorMode(request) ? true : bypassSso ? true : StringUtils.equalsIgnoreCase("Y", searchProfileFunc));
			
			mav.addObject(commandName, form);
			return mav;
		case SEARCH_PROFILE:
			if(isValid(request, form, errors)){
				LtsSessionHelper.clearAllSessionAttb(request);
				mav.addObject(commandName, form);
				form.setDocNum(StringUtils.isNotEmpty(form.getDocNum()) ? form.getDocNum().trim() : "");
				form.setServiceNum(StringUtils.isNotEmpty(form.getServiceNum()) ? form.getServiceNum().trim() : "");
				searchServiceProfile(request, form);
				orderCapture = LtsSessionHelper.getOrderCapture(request);
				if(orderCapture != null && orderCapture.getLtsServiceProfile() != null){
					StringBuilder sb = new StringBuilder();
					sb.append(getSpecialHandleCustomerRemark(orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum()));
					for(CustomerRemarkProfileLtsDTO remark: customerProfileLtsService.getCustomerSpecialRemark(orderCapture.getLtsServiceProfile().getPrimaryCust())){
						sb.append(remark.getRemarks());
					}
					if(StringUtils.isNotEmpty(sb.toString())){
						mav.addObject("alertMsg", StringUtils.replace(sb.toString(), "\n", "\\n"));
					}
				}
				return mav; 
			}else{
				mav.addObject(commandName, form);
				mav.addAllObjects(errors.getModel());
				return mav; 
			}
		case SUBMIT_RET:
			orderCapture = LtsSessionHelper.getOrderCapture(request);
			
			if (!validateRenewalSubmit(request, orderCapture, form)) {
				mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				return mav;
			}
			break;
		case SUBMIT_UPG:
			orderCapture = LtsSessionHelper.getOrderCapture(request);
			
			if (!validateUpgradeSubmit(request, orderCapture, form)) {
				mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				return mav;
			}
			break;
		case SUBMIT_STANDALONE_VAS:
			orderCapture = LtsSessionHelper.getOrderCapture(request);
			
			if (!validateRenewalSubmit(request, orderCapture, form)) {
				mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				return mav;
			}
		default:
			break;
		}
		
		orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);	
		}
		
		orderCapture.setLtsCustomerInquiryForm(form);
		orderCapture.setOrderType(Action.SUBMIT_UPG == form.getFormAction() ? LtsConstant.ORDER_TYPE_SB_UPGRADE : LtsConstant.ORDER_TYPE_SB_RETENTION);
		orderCapture.setOrderSubType(Action.SUBMIT_STANDALONE_VAS == form.getFormAction() ? LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS : null);
		orderCapture.setBasketChannelId(LtsSbOrderHelper.getBasketChannelId(LtsSessionHelper.getBomSalesUser(request), orderCapture));
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	private boolean validateUpgradeSubmit(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsCustomerInquiryFormDTO form) {
//		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
//		if (LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())
//				&& !LtsSbOrderHelper.isSecondContract(orderCapture.getLtsServiceProfile())
//				&& !LtsConstant.CHANNEL_CD_CUSTOMER_SERVICE.equals(bomSalesUser.getChannelCd())) {
//			ItemDetailProfileLtsDTO profileTermPlan = LtsSbOrderHelper.getProfileServiceTermPlan(orderCapture.getLtsServiceProfile());
//			if (profileTermPlan != null && profileTermPlan.getTpExpiredMonths() < -3) {
//				request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, messageSource.getMessage("lts.ltsCustInq.eye2A", new Object[] {}, this.locale));
//				return false;
//			}
//		}
		
		if (LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, messageSource.getMessage("lts.ltsCustInq.eye3A", new Object[] {}, this.locale));
			return false;
		}	
		return true;
	}
	
	
	private boolean validateRenewalSubmit(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsCustomerInquiryFormDTO form) {

		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		AddressRolloutDTO addressRollout = orderCapture.getLtsServiceProfile().getAddress().getAddressRollout();
		if ( StringUtils.isNotBlank(addressRollout.getHktPremier())) {
			if (Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(bomSalesUser.getCategory())) {
				return true;
			}else {
				if (!orderCapture.isChannelPremier()) {
					request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, messageSource.getMessage("lts.ltsCustInq.hktPreCust", new Object[] {}, this.locale));
					return false;
				}else {
					List<String> teamCdList = ltsAddressRolloutService.getTeamByPremierAddr(addressRollout.getHktPremier(), "0");
					
					boolean allowCreatePremierOrder = false;
					if (teamCdList != null && !teamCdList.isEmpty()) {
						for (String teamCd : teamCdList) {
							if (StringUtils.equals(teamCd, bomSalesUser.getShopCd())) {
								allowCreatePremierOrder = true;
								break;
							}
						}
					}
					
					if (!allowCreatePremierOrder) {
						request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, messageSource.getMessage("lts.addressRollout.hktPreAddr", new Object[] {addressRollout.getHktPremier()}, this.locale));
						return false;
					}
				}
			}
		}else {
			if (orderCapture.isChannelPremier()) {
				request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, messageSource.getMessage("lts.ltsCustInq.massCust", new Object[] {}, this.locale));
				return false;
			}
		}
		
		
		
		
//		if (!orderCapture.isChannelPremier() 
//				&& orderCapture.getLtsServiceProfile().getAddress().getAddressRollout() != null
//				&& StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getAddress().getAddressRollout().getHktPremier())) {
//			request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, "HKT Premier Customer. Please contact HKT Premier Team.");
//			return false;
//			
//		}
//		
//		if (orderCapture.isChannelPremier() 
//				&& orderCapture.getLtsServiceProfile().getAddress().getAddressRollout() != null
//				&& StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getAddress().getAddressRollout().getHktPremier())) {
//			request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, "Mass Customer. Please contact Mass Team.");
//			return false;
//			
//		}
		
//		if (ArrayUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getChannelOfferActions())) {
//			for (OfferActionLtsDTO channelOfferAction : orderCapture.getLtsServiceProfile().getChannelOfferActions()) {
//				if(StringUtils.isNotEmpty(channelOfferAction.getDescription())) {
//					request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, "Bundle TV offer exist - not support renewal through Springboard.");
//					return false;
//				}
//			}
//		}
		
		
		return true;
	}
	
	private boolean isValid(HttpServletRequest request, LtsCustomerInquiryFormDTO form, BindException errors){
//			if(!LtsSessionHelper.isChannelCs(request)){
//				if (StringUtils.isBlank(form.getServiceNum()) || StringUtils.isBlank(form.getDocNum())) {
//					errors.rejectValue("docNum", "lts.idDocNumOrServiceNum.required");
//				}
//			}
		
			if(LtsSessionHelper.getBomSalesUser(request).getChannelId() == 1){
				if (form.getDummyDoc() == null) {
					errors.rejectValue("dummyDoc", "lts.dummyDoc.required");
				}
				if (form.getThirdPartyApplication() == null) {
					errors.rejectValue("thirdPartyApplication", "lts.thirdPartyApplication.required");
				}
			}else{
				form.setDummyDoc(false);
				form.setThirdPartyApplication(false);
			}
			if (form.isUpgradeToStaffPlan()){
				if(StringUtils.isEmpty(form.getStaffId())){
					errors.rejectValue("staffId", "lts.staffId.required");
				}else if(form.getStaffId().length() < 6){
					errors.rejectValue("staffId", "lts.invalid.staffId");
				}
			}
			
		return errors.getErrorCount()==0;
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		String paramSrvNum = (String)request.getParameter(LtsConstant.REQUEST_PARAM_SRV_NUM);
		setLocale(RequestContextUtils.getLocale(request));
//		String tp = (String)request.getParameter(LtsConstant.REQUEST_PARAM_TYPE_OF_SRV);
//		String datCd = (String)request.getParameter(LtsConstant.REQUEST_PARAM_DAT_CD);
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null || orderCapture.getLtsCustomerInquiryForm() == null) {
			
			LtsCustomerInquiryFormDTO form = new LtsCustomerInquiryFormDTO();
			String searchProfileFunc = (String)ltsFuncLkupCacheService.get(LtsConstant.SB_LTS_FUNC_SEARCH_PROFILE);
			form.setAllowSearchProfile(LtsSessionHelper.isBackdoorMode(request) ? true : bypassSso ? true : StringUtils.equalsIgnoreCase("Y", searchProfileFunc));
			
			if (StringUtils.isNotBlank(paramSrvNum)) {
				form.setServiceNum(StringUtils.leftPad(paramSrvNum.trim(), 12, "0"));
				searchServiceProfile(request, form);
				return form;
			}
			
			List<ServiceLineDTO> serviceLineList = LtsSessionHelper.getCustomerServiceInUse(request);
			CustomerInformationDTO customerInfoForm = LtsSessionHelper.getCustomerInformationForm(request);
			
			if (customerInfoForm != null && serviceLineList != null && !serviceLineList.isEmpty()) {
				form.setServiceNum(customerInfoForm.getSelectedNum());
			}
//			form.setUserChannelId(LtsSessionHelper.getBomSalesUser(request).getChannelId());
			return form;	
		}

		return orderCapture.getLtsCustomerInquiryForm();
	}
	
	private void searchServiceProfile(HttpServletRequest request, LtsCustomerInquiryFormDTO form) {
		
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		ValidationResultDTO result = ltsProfileService.retrieveAndValidateServiceProfile(form, bomSalesUser, LtsSessionHelper.isChannelCs(request));
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_SRV_PROFILE_MSG, result.getMessageList());

		// VALID Profile 
		OrderCaptureDTO orderCapture = new OrderCaptureDTO();
		
		String locale = RequestContextUtils.getLocale(request).toString();
			
		ServiceDetailProfileLtsDTO serviceProfile = (ServiceDetailProfileLtsDTO)result.getValidateObject();
		setProfileItemDetail(serviceProfile, locale, result, LtsSessionHelper.isChannelCs(request));
		setFsaProfileItemDetail(serviceProfile, locale);
		orderCapture.setLtsServiceProfile(serviceProfile);			
			
				
		UpgradeEyeInfoDTO upgradeEyeInfo = ltsProfileService
				.retrieveUpgradeEyeInfo(serviceProfile, locale, orderCapture, form.isUpgradeToStaffPlan());
		orderCapture.setUpgradeEyeInfo(upgradeEyeInfo);
		
		form.setAllowCreateOrder(true);
		orderCapture.setLtsCustomerInquiryForm(form);
		
		if (serviceProfile != null && StringUtils.isNotEmpty(serviceProfile.getRelatedEyeFsa())) {
			orderCapture.setImsPendingOrder(ltsRelatedPcdOrderService.getImsLatestPendingOrder(serviceProfile.getRelatedEyeFsa()));
		}
		
		LtsSessionHelper.setOrderCapture(request, orderCapture);
		
		if (result.getValidateObject() == null 
				|| result.getStatus() == null
				|| Status.INVAILD == result.getStatus()) {
			form.setAllowCreateOrder(false);
			return;
		}
		
		if(serviceProfile.getPrimaryCust() != null){
			if(StringUtils.equals(LtsConstant.DOC_TYPE_HKBR, serviceProfile.getPrimaryCust().getDocType())){
				form.setThirdPartyApplication(true);
				result.getMessageList().add(messageSource.getMessage("lts.ltsCustInq.hkbr", new Object[] {}, this.locale));
			}
		}
	}
	
	private void setFsaProfileItemDetail(ServiceDetailProfileLtsDTO serviceProfile, String locale) {
		if (serviceProfile == null
				|| serviceProfile.getEyeFsaProfile() == null
				|| ArrayUtils.isEmpty(serviceProfile.getEyeFsaProfile()
						.getItems())) {
			return;
		}
		for (ItemDetailProfileLtsDTO itemDetailProfile : serviceProfile.getEyeFsaProfile().getItems()) {
			if (StringUtils.isEmpty(itemDetailProfile.getItemId())) {
				continue;
			}
			itemDetailProfile.setItemDetail(ltsOfferService.getTpVasItemDetail(
					itemDetailProfile.getItemId(), locale));
		}
	}
	
	
	private void setProfileItemDetail(ServiceDetailProfileLtsDTO serviceProfile, String locale, ValidationResultDTO result, boolean isChannelCcPt) {
		
		if (serviceProfile == null || ArrayUtils.isEmpty(serviceProfile.getItemDetails())) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO itemDetailProfile : serviceProfile.getItemDetails()) {
			if (StringUtils.isEmpty(itemDetailProfile.getItemId())) {
				continue;
			}
			itemDetailProfile.setItemDetail(ltsOfferService.getTpVasItemDetail(
					itemDetailProfile.getItemId(), locale));
		}
		
		if(!isChannelCcPt && serviceProfile.isExistStaffPlan()){
			result.getMessageList().add(messageSource.getMessage("lts.ltsCustInq.exstStaffPlan", new Object[] {}, this.locale));
			result.setStatus(Status.INVAILD);
			result.setValidateObject(null);
		}
	}
	
	private String getSpecialHandleCustomerRemark(String docId){
		try {
			for(LookupItemDTO item: specialHandleDocIdLkupCacheService.getCodeLkupDAO().getCodeLkup()){
				if(docId.equals(item.getItemKey())){
					return (String) item.getItemValue();
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
