package com.bomwebportal.lts.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.BasketCriteriaDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.BasketSelectionInfoDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketSelectionFormDTO.BasketTab;
import com.bomwebportal.lts.dto.form.LtsBasketServiceFormDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupProfileDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.ret.RenewBasketPolicyDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsDeviceService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.PremiumItemSetDetailService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.pccw.springboard.svc.server.dto.CampaignDTO;

public class LtsBasketSelectionController extends SimpleFormController {
	
	private final String viewName = "ltsbasketselection";
	private final String nextView = "ltsbasketservice.html";
	private final String standaloneVasNextView = "ltsbasketvasdetail.html";
	private final String commandName = "ltsBasketSelectionCmd";
	
	protected LtsBasketService ltsBasketService;
	protected LtsDeviceService ltsDeviceService;
	protected PremiumItemSetDetailService premiumItemSetDetailService;
	private LtsOfferService ltsOfferService;
	private CustomerProfileLtsService customerProfileLtsService;
	private CodeLkupCacheService ltsCustCampaignLkupCacheService;
	private CodeLkupCacheService ltsBasketCampaignLkupCacheService;
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private ImsProfileService imsProfileService;
	private CodeLkupCacheService ltsEffStartDateLkupCacheService;
	private Locale locale;
	private MessageSource messageSource;
	
	
	
	public CodeLkupCacheService getLtsEffStartDateLkupCacheService() {
		return ltsEffStartDateLkupCacheService;
	}

	public void setLtsEffStartDateLkupCacheService(
			CodeLkupCacheService ltsEffStartDateLkupCacheService) {
		this.ltsEffStartDateLkupCacheService = ltsEffStartDateLkupCacheService;
	}

	public CodeLkupCacheService getLtsBasketCampaignLkupCacheService() {
		return ltsBasketCampaignLkupCacheService;
	}

	public void setLtsBasketCampaignLkupCacheService(
			CodeLkupCacheService ltsBasketCampaignLkupCacheService) {
		this.ltsBasketCampaignLkupCacheService = ltsBasketCampaignLkupCacheService;
	}

	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}

	public CodeLkupCacheService getLtsCustCampaignLkupCacheService() {
		return ltsCustCampaignLkupCacheService;
	}

	public void setLtsCustCampaignLkupCacheService(
			CodeLkupCacheService ltsCustCampaignLkupCacheService) {
		this.ltsCustCampaignLkupCacheService = ltsCustCampaignLkupCacheService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public PremiumItemSetDetailService getPremiumItemSetDetailService() {
		return premiumItemSetDetailService;
	}

	public void setPremiumItemSetDetailService(
			PremiumItemSetDetailService premiumItemSetDetailService) {
		this.premiumItemSetDetailService = premiumItemSetDetailService;
	}

	public LtsDeviceService getLtsDeviceService() {
		return ltsDeviceService;
	}

	public void setLtsDeviceService(LtsDeviceService ltsDeviceService) {
		this.ltsDeviceService = ltsDeviceService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public LtsBasketSelectionController() {
		setCommandClass(LtsBasketSelectionFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		setLocale(RequestContextUtils.getLocale(request));
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if(LtsSbOrderHelper.isStandaloneVasOrder(orderCapture)){
			LtsBasketSelectionFormDTO form = new LtsBasketSelectionFormDTO();
			form.setSelectedBasketId(getStandaloneVasDummyBasket(orderCapture));
			orderCapture.setLtsBasketSelectionForm(form);
			
			BasketDetailDTO selectedBasket = ltsBasketService.getBasket(
					form.getSelectedBasketId(), LtsConstant.LOCALE_ENG,
					LtsConstant.DISPLAY_TYPE_RP_PROMOT);
			orderCapture.setSelectedBasket(selectedBasket);

			/* LtsBasketService can be skipped*/
			/* LtsNowTvService form can be left as null */
			LtsBasketServiceFormDTO basketServiceForm = new LtsBasketServiceFormDTO();

			String locale = RequestContextUtils.getLocale(request).toString();
			String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
			String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
			
			List<ItemDetailDTO> planItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_PLAN, locale, true, true, orderCapture.getBasketChannelId(), applicationDate);
			basketServiceForm.setPlanItemList(planItemList);
			orderCapture.setLtsBasketServiceForm(basketServiceForm);
			
			
			return new ModelAndView(new RedirectView(standaloneVasNextView));
		}
		
		return super.handleRequestInternal(request, response);
		
	}
	
	private String getStandaloneVasDummyBasket(OrderCaptureDTO orderCapture){		
		BasketCriteriaDTO basketCriteria = new BasketCriteriaDTO();
		basketCriteria.setApplicationDate(StringUtils.split(orderCapture.getLtsMiscellaneousForm().getApplicationDate(), " ")[0]);
		basketCriteria.setBasketChannelId(orderCapture.getBasketChannelId());
		basketCriteria.setDeviceType(getDeviceType(orderCapture));
		
		List<BasketDetailDTO> basketList = ltsBasketService.getStandaloneVasDummyBasketList(basketCriteria);
		orderCapture.setBasketCriteria(basketCriteria);
		
		if(basketList.size() > 0 && basketList.get(0) != null){
			return basketList.get(0).getBasketId();
		}
		
		return null;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		LtsBasketSelectionFormDTO form = (LtsBasketSelectionFormDTO) command;
		BasketDetailDTO selectedBasket = ltsBasketService.getBasket(
				form.getSelectedBasketId(), LtsConstant.LOCALE_ENG,
				LtsConstant.DISPLAY_TYPE_RP_PROMOT);
		ModelAndView mav = new ModelAndView(viewName, referenceData(request, command, errors));
		switch (form.getFormAction()) {
		case FILTER:
			setBasketSelectionInfo(request, form);
			mav.addAllObjects(errors.getModel());
			mav.addObject(commandName, form);
			return mav;
		case SUBMIT:
			ValidationResultDTO result = validateSubmit(request, orderCapture, form, selectedBasket);
			if (result.getStatus() == Status.INVAILD) {
				setBasketSelectionInfo(request, form);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", result.getMessageList());
				return mav;
			}
		default:
			break;
		}
		
		orderCapture.setSelectedBasket(selectedBasket);
		orderCapture.setLtsBasketSelectionForm(form);
		request.getSession().removeAttribute(LtsConstant.SESSION_BASKET_SELECTION_INFO);
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		
		LtsBasketSelectionFormDTO form = new LtsBasketSelectionFormDTO();
		form.setBasketTab(determineBasketTab(orderCapture, bomSalesUser));
		return form;
	}
	
	
	private BasketTab determineBasketTab(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		if (orderCapture == null) {
			return null;
		}
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
			return BasketTab.UPG_MASS;
		}
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			
			if (StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
				if (LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
					return BasketTab.RET_PT_EYE;	
				}
				return BasketTab.RET_MASS_EYE;
			}
			else {
				if (LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
					return BasketTab.RET_PT_DEL;
				}
				return BasketTab.RET_MASS_DEL; 
			}
		}
		return null;
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request, Object command,
			Errors errors) throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		
		Map<String, Object> referenceData = new HashMap<String, Object>(); 
		referenceData.put("filterProjectCdList", ltsBasketService.getProjectCdList(bomSalesUser.getChannelCd(), bomSalesUser.getShopCd(), orderCapture.getOrderType()));
		setBasketSelectionInfo(request, (LtsBasketSelectionFormDTO)command);
		
		return referenceData;
	}
	
	
	private void setBasketSelectionInfo(HttpServletRequest request, LtsBasketSelectionFormDTO form) throws Exception {
		
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		request.getSession().setAttribute(LtsConstant.SESSION_BASKET_SELECTION_INFO, getBasketSelectionInfo(bomSalesUser, orderCapture, form, locale));
		
	}
	
	private String getBasketType(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsDeviceSelectionForm() == null) {
			return null;	
		}
		return orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		
	}
	
	private String getIddFreeMin(OrderCaptureDTO orderCapture) {
		
		if (LtsSbOrderHelper.isFreeToGoService(orderCapture.getLtsServiceProfile())) {
			ItemDetailProfileLtsDTO expiredTpItem = LtsSbOrderHelper.getExpiredTermPlanWithin6Mths(orderCapture.getLtsServiceProfile());
			if (expiredTpItem != null 
					&& StringUtils.isNotBlank(expiredTpItem.getIddFreeMin())
					&& !StringUtils.equals("0", expiredTpItem.getIddFreeMin())) {
				return expiredTpItem.getIddFreeMin(); 
			}
			
			ItemDetailProfileLtsDTO endedTpItem = LtsSbOrderHelper.getEndedTermPlanWithin6Mths(orderCapture.getLtsServiceProfile());
			if (endedTpItem != null 
					&& StringUtils.isNotBlank(endedTpItem.getIddFreeMin())
					&& !StringUtils.equals("0", endedTpItem.getIddFreeMin())) {
				return endedTpItem.getIddFreeMin(); 
			}
		}
		
		ItemDetailProfileLtsDTO currentTpItem = LtsSbOrderHelper.getProfileServiceTermPlan(orderCapture.getLtsServiceProfile());
		if (currentTpItem != null 
				&& StringUtils.isNotBlank(currentTpItem.getIddFreeMin())
				&& !StringUtils.equals("0", currentTpItem.getIddFreeMin())) {
			return currentTpItem.getIddFreeMin(); 
		}
		
		return null;
	}
	
	private boolean isParallelExtension(OrderCaptureDTO orderCapture) {
		if (orderCapture.getNewAddressRollout() == null) {
			return false;
		}
		return orderCapture.getNewAddressRollout().isPeCoverage();
	}
	
	private String getDeviceType(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsServiceProfile() != null) {
			return orderCapture.getLtsServiceProfile().getEyeDeviceType();
		}
		return null;
	}
	
	private boolean isDuplexProfile(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsServiceProfile() != null) {
			return StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getDuplexNum()) ;
		}
		return false;
	}
	
	private String[] getBasketCampaigns(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsServiceProfile() == null) {
			return null;
		}
		CustomerDetailProfileLtsDTO profileCustomer = orderCapture.getLtsServiceProfile().getPrimaryCust();
		
		List<CampaignDTO> campaignList = customerProfileLtsService.getCampaign(
				LtsConstant.SYSTEM_ID_DRG, profileCustomer.getCustNum(),
				orderCapture.getLtsServiceProfile().getSrvNum(),
				LtsConstant.CAMPAIGN_LOB_LTS);
		
		if (campaignList == null || campaignList.isEmpty()) {
			return null;
		}
		
		List<String> basketCampaignList = new ArrayList<String>();
		
		for (CampaignDTO campaign : campaignList) {
			
			if (StringUtils.isEmpty(campaign.getValuePropId())) {
				continue;
			}
			
			String basketCampaignCode = (String)ltsBasketCampaignLkupCacheService.get(campaign.getValuePropId());
			
			if (StringUtils.isNotEmpty(basketCampaignCode)) {
				basketCampaignList.add(basketCampaignCode);
			}
		}
			
		return basketCampaignList.toArray(new String[basketCampaignList.size()]);
	}
	
	private String getCustCampaign(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsServiceProfile() == null) {
			return null;
		}
		CustomerDetailProfileLtsDTO profileCustomer = orderCapture.getLtsServiceProfile().getPrimaryCust();
		
		List<CampaignDTO> campaignList = customerProfileLtsService.getCampaign(
				LtsConstant.SYSTEM_ID_DRG, profileCustomer.getCustNum(),
				orderCapture.getLtsServiceProfile().getSrvNum(),
				LtsConstant.CAMPAIGN_LOB_LTS);
		
		if (campaignList == null || campaignList.isEmpty()) {
			return "C1";
		}
			
		for (CampaignDTO campaign : campaignList) {
			if (StringUtils.isEmpty(campaign.getValuePropId())) {
				continue;
			}
			String campaignCd = (String)ltsCustCampaignLkupCacheService.get(campaign.getValuePropId());
			if (StringUtils.isNotEmpty(campaignCd)) {
				return campaignCd;
			}
		}
		return "C1";
	}
	
	private BasketSelectionInfoDTO getBasketSelectionInfo(BomSalesUserDTO bomSalesUser, OrderCaptureDTO orderCapture, LtsBasketSelectionFormDTO form, String locale) {
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture
				.getOrderType())) {
			return getUpgradeBasket(bomSalesUser, orderCapture, form, locale);
		}

		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture
				.getOrderType())) {
			return getRetentionBasket(bomSalesUser, orderCapture, form, locale);
		}
		return null;
	}
	
	private BasketSelectionInfoDTO getRetentionBasket(BomSalesUserDTO bomSalesUser, OrderCaptureDTO orderCapture, LtsBasketSelectionFormDTO form, String locale) {
		
		
		double remainContractMth = LtsSbOrderHelper.getRemainTpContractMth(orderCapture.getLtsServiceProfile());
		String existTpCatg = LtsSbOrderHelper.getExistTpCatg(orderCapture.getLtsServiceProfile());
		
		BasketCriteriaDTO basketCriteria = new BasketCriteriaDTO();
		basketCriteria.setBasketType(getBasketType(orderCapture));
		basketCriteria.setIddFreeMin(getIddFreeMin(orderCapture));
		basketCriteria.setParalleExtension(LtsSbOrderHelper.isEyeProfileParalleExtension(orderCapture.getLtsServiceProfile()) ? "Y" : "N");
		basketCriteria.setBundle2G(LtsSbOrderHelper.isBundle2GTp(orderCapture.getLtsServiceProfile()) ? "Y" : null);
		basketCriteria.setDeviceType(getDeviceType(orderCapture));
		String custCampaignCd = StringUtils.substring(getCustCampaign(orderCapture), 0, 1);
		basketCriteria.setCustCampaignCd(custCampaignCd);
		basketCriteria.setAreaCode(bomSalesUser.getAreaCd());
		basketCriteria.setTeamCode(bomSalesUser.getShopCd());
		basketCriteria.setRole(bomSalesUser.getCategory()); //JT2017062
		basketCriteria.setDuplexProfile(isDuplexProfile(orderCapture));
		basketCriteria.setSubmitDisForm(orderCapture.getLtsMiscellaneousForm().isSubmitDisForm());
		basketCriteria.setNonNowTvCust(LtsSbOrderHelper.getNowTvServiceProfile(orderCapture) == null);
//		ItemDetailProfileLtsDTO profileTpItem = LtsSbOrderHelper.getProfileServiceTermPlan(orderCapture.getLtsServiceProfile());
//		double tpExpiredMonths = profileTpItem == null ? 0 : profileTpItem.getTpExpiredMonths();
		basketCriteria.setTpExpiredMonths(String.valueOf(LtsSbOrderHelper.getTpExpireMth(orderCapture.getLtsServiceProfile())));
		basketCriteria.setBasketCampaignCds(getBasketCampaigns(orderCapture));
		basketCriteria.setHktPremier(orderCapture.getNewAddressRollout().getHktPremier());
		basketCriteria.setDefaultProjectCds(getDefaultProjectCodes(orderCapture, bomSalesUser));
		basketCriteria.setFilterProjectCds(getFilterProjectCodes(form));
		basketCriteria.setProfilePsefCds( getProfilePsedCds(orderCapture.getLtsServiceProfile()));
		basketCriteria.setLocale(locale);
		basketCriteria.setBasketChannelId(orderCapture.getBasketChannelId());
		basketCriteria.setContractPeriodEq15(form.isFilterContractMthEq15());
		basketCriteria.setContractPeriodEq18(form.isFilterContractMthEq18());
		basketCriteria.setContractPeriodEq21(form.isFilterContractMthEq21());
		basketCriteria.setContractPeriodGt24(form.isFilterContractMthGe24());
		basketCriteria.setContractPeriodLt12(form.isFilterContractMthLe12());
		basketCriteria.setBundleTvCredit(orderCapture.getLtsServiceProfile().getBundleTvCredit());
		basketCriteria.setRolloutHousingType(orderCapture.getNewAddressRollout().getHousingType());
		basketCriteria.setProfileArpu(String.valueOf(LtsSbOrderHelper.getProfileArpu(orderCapture)));
		basketCriteria.setSrvBoundary(orderCapture.getNewAddressRollout().getSrvBdary());
		basketCriteria.setImsHousingType(orderCapture.getNewAddressRollout().getHousingType());
		basketCriteria.setLtsHousingType(orderCapture.getNewAddressRollout().getLtsHousingCatCd());
		
		String osType = null;
		if(form.isFilterOsTypeAndroid()){
			osType = LtsConstant.OS_TYPE_AND;
		}
		if(form.isFilterOsTypeiOS()){
			osType = LtsConstant.OS_TYPE_IOS;
		}
		if(form.isFilterOsTypeAndroid() && form.isFilterOsTypeiOS()){
			osType = null;
		}
		basketCriteria.setOsType(osType);
		
		String applicationDate = null;
		if(orderCapture.getLtsMiscellaneousForm() != null && orderCapture.getLtsMiscellaneousForm().isBackDateOrder()){		
			applicationDate  = orderCapture.getLtsMiscellaneousForm().getBackDate();
		}else{
			applicationDate = StringUtils.split(orderCapture.getLtsMiscellaneousForm().getApplicationDate(), " ")[0];
		}
		basketCriteria.setApplicationDate(applicationDate);
		
		orderCapture.setBasketCriteria(basketCriteria);
		BasketSelectionInfoDTO basketSelectionInfo = new BasketSelectionInfoDTO();	
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(getBasketType(orderCapture), locale);
		basketSelectionInfo.setSelectedDevice(selectedDevice);
		
		List<RenewBasketPolicyDTO> renewBasketPolicyList = ltsBasketService.getRenewBasketPolicyList(orderCapture.getBasketChannelId(), existTpCatg, String.valueOf(remainContractMth));
		if (renewBasketPolicyList == null || renewBasketPolicyList.isEmpty()) {
			return basketSelectionInfo;
		}
		
		orderCapture.setRenewBasketPolicyList(renewBasketPolicyList);
		basketSelectionInfo.setWarningMsg(getWarningMsg(renewBasketPolicyList));
		
		List<BasketDetailDTO> allBasketList = ltsBasketService.getRetBasketList(basketCriteria);
		
		if (allBasketList == null || allBasketList.isEmpty()) {
			return basketSelectionInfo;
		}
		
		List<BasketDetailDTO> hotBasketList = new ArrayList<BasketDetailDTO>();
		List<BasketDetailDTO> premiumBasketList = new ArrayList<BasketDetailDTO>();
		List<BasketDetailDTO> rebateBasketList = new ArrayList<BasketDetailDTO>();
		List<BasketDetailDTO> rebateCouponBasketList = new ArrayList<BasketDetailDTO>();
		List<BasketDetailDTO> otherBasketList = new ArrayList<BasketDetailDTO>();
		List<BasketDetailDTO> couponIddBasketList = new ArrayList<BasketDetailDTO>();
		List<BasketDetailDTO> iddBasketList = new ArrayList<BasketDetailDTO>();
		List<BasketDetailDTO> rebateIddBasketList = new ArrayList<BasketDetailDTO>();
		
		for (BasketDetailDTO basketDetail : allBasketList) {
			for (RenewBasketPolicyDTO renewBasketPolicy : renewBasketPolicyList) {
				if (!StringUtils.equals(basketDetail.getBasketCatg(), renewBasketPolicy.getNewBasketCatg())
						|| ! StringUtils.equals(
								StringUtils.defaultIfEmpty(basketDetail.getExtendContractPeriod(), "0"), 
								renewBasketPolicy.getExtendContractPeriod())) {
					continue;
				}
				
				// Ignore extend contract period if user selected special offer (project code)
				if (! StringUtils.equals(StringUtils.defaultIfEmpty(basketDetail.getExtendContractPeriod(), "0"), 
							renewBasketPolicy.getExtendContractPeriod()) 
						&& ArrayUtils.isEmpty(basketCriteria.getFilterProjectCds())) {
					continue;
				}
				
				if (StringUtils.equals("Y", basketDetail.getHotBasketInd())) {
					hotBasketList.add(basketDetail);
				}
				
				if (LtsConstant.BASKET_CATEGORY_PREMIUM.equals(renewBasketPolicy.getNewBasketCatg())) {
					premiumBasketList.add(basketDetail);
				}
					
				if (LtsConstant.BASKET_CATEGORY_REBATE.equals(renewBasketPolicy.getNewBasketCatg())) {
					rebateBasketList.add(basketDetail);
				}
					
				if (LtsConstant.BASKET_CATEGORY_REBATE_COUPON.equals(renewBasketPolicy.getNewBasketCatg())) {
					rebateCouponBasketList.add(basketDetail);
				}
				
				if (LtsConstant.BASKET_CATEGORY_OTHER.equals(renewBasketPolicy.getNewBasketCatg())) {
					otherBasketList.add(basketDetail);
				}
				
				if (LtsConstant.BASKET_CATEGORY_COUPON_IDD.equals(renewBasketPolicy.getNewBasketCatg())) {
					couponIddBasketList.add(basketDetail);
				}
				
				if (LtsConstant.BASKET_CATEGORY_REBATE_IDD.equals(renewBasketPolicy.getNewBasketCatg())) {
					rebateIddBasketList.add(basketDetail);
				}
				
				if (LtsConstant.BASKET_CATEGORY_IDD.equals(renewBasketPolicy.getNewBasketCatg())) {
					iddBasketList.add(basketDetail);
				}
			}
		}
		
		basketSelectionInfo.setHotBasketList(hotBasketList); 		
		basketSelectionInfo.setOtherBasketList(otherBasketList);
		basketSelectionInfo.setPremiumBasketList(premiumBasketList);
		basketSelectionInfo.setRebateBasketList(rebateBasketList);
		basketSelectionInfo.setRebateCouponBasketList(rebateCouponBasketList);
		basketSelectionInfo.setCouponIddBasketList(couponIddBasketList);
		basketSelectionInfo.setRebateIddBasketList(rebateIddBasketList);
		basketSelectionInfo.setIddBasketList(iddBasketList);
		return basketSelectionInfo;
		
	}
	
			
	private String getWarningMsg(List<RenewBasketPolicyDTO> renewBasketPolicyList) {
		for (RenewBasketPolicyDTO renewBasketPolicy : renewBasketPolicyList) {
			if (StringUtils.isEmpty(renewBasketPolicy.getNewBasketCatg()) && 
					StringUtils.isNotBlank(renewBasketPolicy.getWarningMsg())) {
					return renewBasketPolicy.getWarningMsg();
			}
		}
		return null;	
	}
			
	private BasketSelectionInfoDTO getUpgradeBasket(BomSalesUserDTO bomSalesUser, OrderCaptureDTO orderCapture, LtsBasketSelectionFormDTO form, String locale) {
		
		String basketType = getBasketType(orderCapture);
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(basketType, locale);
		
//		if (isReturnEmptyBasket(orderCapture, form)) {
//			BasketSelectionInfoDTO basketSelectionInfo = new BasketSelectionInfoDTO();
//			basketSelectionInfo.setSelectedDevice(selectedDevice);
//			basketSelectionInfo.setHotBasketList(null);
//			basketSelectionInfo.setRegularBasketList(null);
//			return basketSelectionInfo;
//		}
		
		boolean parallelExtension = isParallelExtension(orderCapture);
		String iddFreeMin = getIddFreeMin(orderCapture);
		String extendContractPeriod = "0";
		String modemArrangment = null;

		boolean isNonNowTvCust = LtsSbOrderHelper.getNowTvServiceProfile(orderCapture) == null
				&& !LtsSbOrderHelper.isNewNowTvService(orderCapture);
		
		String applicationDate = null;
		if(orderCapture.getLtsMiscellaneousForm() != null && orderCapture.getLtsMiscellaneousForm().isBackDateOrder()){		
			applicationDate  = orderCapture.getLtsMiscellaneousForm().getBackDate();
		}else{
			applicationDate = StringUtils.split(orderCapture.getLtsMiscellaneousForm().getApplicationDate(), " ")[0];
		}
		
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType()) && orderCapture.getUpgradeEyeInfo() != null && orderCapture.getLtsMiscellaneousForm() != null) {
			if (orderCapture.getLtsMiscellaneousForm().isRedeemPrevPremium()) {
				
				UpgradeEyeInfoDTO upgradeInfo = ltsOfferService.getUpgradeEyeInfo(orderCapture.getLtsServiceProfile(), basketType, orderCapture.getLtsCustomerInquiryForm().isUpgradeToStaffPlan());
				extendContractPeriod = upgradeInfo != null ? upgradeInfo.getExtendContractPeriod() : "0";
			}
		}

		// Get possible max. iddFreeMin
		if (StringUtils.isNotEmpty(iddFreeMin)){
			iddFreeMin = ltsBasketService.getMaxIddFreeMin(orderCapture.getBasketChannelId(), basketType, iddFreeMin, parallelExtension, locale, extendContractPeriod, isNonNowTvCust, applicationDate);
		}
		
		// Allow 2L2B nowTV customer select TV bundle basket
		if (orderCapture.getModemTechnologyAissgn()!= null){
			modemArrangment = orderCapture.getModemTechnologyAissgn().getModemArrangment();
		}
		
		if (ltsOfferService.isAllow2L2B(basketType) && StringUtils.equalsIgnoreCase(LtsConstant.MODEM_TYPE_2L2B,modemArrangment)){
			isNonNowTvCust = true;
		}
		
		Map<String, BasketDetailDTO> allBasketMap = new HashMap<String, BasketDetailDTO>();
		
		String fromProduct = (orderCapture.getLtsServiceProfile().getSrvType() != null && StringUtils
				.isNotBlank(orderCapture.getLtsServiceProfile()
						.getExistEyeType())) ? orderCapture
				.getLtsServiceProfile().getExistEyeType()
				: LtsConstant.LTS_PRODUCT_TYPE_DEL;

		// For basket selection, Samsung determine as eye1
		if (LtsConstant.EYE_TYPE_SAMSUNG.equalsIgnoreCase(fromProduct)) {
			fromProduct = LtsConstant.EYE_TYPE_EYE1;
		}
		
		BasketCriteriaDTO basketCriteria = new BasketCriteriaDTO();
		basketCriteria.setBasketType(basketType);
		basketCriteria.setIddFreeMin(iddFreeMin);
		basketCriteria.setParalleExtension(isParallelExtension(orderCapture) ? "Y" : "N");
		basketCriteria.setAreaCode(bomSalesUser.getAreaCd());
		basketCriteria.setTeamCode(bomSalesUser.getShopCd());
		basketCriteria.setRole(bomSalesUser.getCategory()); //JT2017062
		basketCriteria.setNonNowTvCust(LtsSbOrderHelper.getNowTvServiceProfile(orderCapture) == null);
		
		basketCriteria.setBasketCampaignCds(getBasketCampaigns(orderCapture));
		basketCriteria.setHktPremier(orderCapture.getNewAddressRollout().getHktPremier());
		basketCriteria.setDefaultProjectCds(getDefaultProjectCodes(orderCapture, bomSalesUser));
		basketCriteria.setFilterProjectCds(getFilterProjectCodes(form));
		basketCriteria.setLocale(locale);
		basketCriteria.setBasketChannelId(orderCapture.getBasketChannelId());
		basketCriteria.setFromProduct(fromProduct);
		basketCriteria.setExtendContractMth(StringUtils.isBlank(extendContractPeriod) ? "0" : extendContractPeriod);
		basketCriteria.setTpExpiredMonths(String.valueOf(LtsSbOrderHelper.getTpExpireMth(orderCapture.getLtsServiceProfile(), orderCapture.getOrderType())));
		basketCriteria.setProfileArpu(String.valueOf(LtsSbOrderHelper.getProfileArpu(orderCapture)));
		basketCriteria.setCustCampaignCd(getCustCampaign(orderCapture));
		basketCriteria.setProfilePsefCds( getProfilePsedCds(orderCapture.getLtsServiceProfile()));
		basketCriteria.setSrvBoundary(orderCapture.getNewAddressRollout().getSrvBdary());
		basketCriteria.setLtsHousingType(orderCapture.getNewAddressRollout().getLtsHousingCatCd());
		
		String osType = null;
		if(form.isFilterOsTypeAndroid()){
			osType = LtsConstant.OS_TYPE_AND;
		}
		if(form.isFilterOsTypeiOS()){
			osType = LtsConstant.OS_TYPE_IOS;
		}
		if(form.isFilterOsTypeAndroid() && form.isFilterOsTypeiOS()){
			osType = null;
		}
		basketCriteria.setOsType(osType);
		
		// Hot Basket
		basketCriteria.setBasketCategory(LtsConstant.BASKET_CATEGORY_HOT);
		basketCriteria.setHotBasketInd(true);

		basketCriteria.setRolloutHousingType(orderCapture.getNewAddressRollout().getHousingType());
		List<BasketDetailDTO> hotBasketList = ltsBasketService.getBasketList(basketCriteria);
		if (hotBasketList != null && !hotBasketList.isEmpty()) {
			for (BasketDetailDTO basketDetail : hotBasketList) {
				
				if (!allBasketMap.containsKey(basketDetail.getBasketId())) {
					allBasketMap.put(basketDetail.getBasketId(), basketDetail);
				}
				
			}	
		}

		// Regular Basket
		basketCriteria.setBasketCategory(LtsConstant.BASKET_CATEGORY_REGULAR);
		basketCriteria.setHotBasketInd(false);
		// Always not include PE  & IDD Basket
		List<BasketDetailDTO> regularBasketList = ltsBasketService.getBasketList(basketCriteria);

		List<BasketDetailDTO> filteredRegularBasketList = new ArrayList<BasketDetailDTO>();
		
		if (regularBasketList != null && !regularBasketList.isEmpty()) {
			for (BasketDetailDTO basketDetail : regularBasketList) {
				if (!allBasketMap.containsKey(basketDetail.getBasketId())) {
					allBasketMap.put(basketDetail.getBasketId(), basketDetail);
					filteredRegularBasketList.add(basketDetail);
				}
			}
		}
		orderCapture.setBasketCriteria(basketCriteria);
		BasketSelectionInfoDTO basketSelectionInfo = new BasketSelectionInfoDTO();
		basketSelectionInfo.setSelectedDevice(selectedDevice);
		basketSelectionInfo.setHotBasketList(hotBasketList);
		basketSelectionInfo.setRegularBasketList(filteredRegularBasketList);
		return basketSelectionInfo;
		
	}
	
	private boolean isEye3Free3Mth(OrderCaptureDTO orderCapture, String userId) {
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		
		if (!LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())
				|| StringUtils.isNotEmpty(serviceProfile.getExistEyeType())) {
			return false;
		}
		
		double profileArpu = LtsSbOrderHelper.getProfileTpArpu(serviceProfile,orderCapture.getOrderType());
		if (profileArpu >= 128.00) {
			return true;
		}
		
		switch (orderCapture.getLtsModemArrangementForm().getModemType()) {
		case SHARE_PCD:
		case SHARE_BUNDLE:	
		case SHARE_TV:
			return true;
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:
			FsaDetailDTO selectedFsaDetail = LtsSbOrderHelper.getSelectedFsa(orderCapture.getLtsModemArrangementForm());
			if (selectedFsaDetail.getNewService() != null && StringUtils.contains(selectedFsaDetail.getNewService().getDesc(), "PCD")) {
				return true;
			}
			if (StringUtils.contains(selectedFsaDetail.getExistingService().getDesc(), "PCD")) {
				return true;
			}
		}
		
		TenureDTO[] newImsTenures = this.imsProfileService.getImsTenureByAddress(orderCapture.getNewAddressRollout().getFlat(),
			orderCapture.getNewAddressRollout().getFloor(), orderCapture.getNewAddressRollout().getSrvBdary());
		
		if (ArrayUtils.isEmpty(newImsTenures)) {
			return false;
		}
		
		for (TenureDTO imsTenure : newImsTenures) {
			FsaServiceDetailOutputDTO fsaProfile = imsServiceProfileAccessService.getServiceDetailByFSA(imsTenure.getServiceId(), userId);
			
			if (fsaProfile == null) {
				continue;
			}
			
			if (StringUtils.containsIgnoreCase(fsaProfile.getSrvType(), "TV")
					|| StringUtils.containsIgnoreCase(fsaProfile.getSrvType(), "PCD")) {
				return true;
			}
		}

		return false;
	}
	
	
	private String[] getFilterProjectCodes(LtsBasketSelectionFormDTO form) {
		if (StringUtils.isNotEmpty(form.getFilterProjectCd())) {
			return new String[] {form.getFilterProjectCd()};
		}
		return null;
	}
	
	private String[] getDefaultProjectCodes(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		List<String> projectCodeList = new ArrayList<String>();
//		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType()) && isEye3Free3Mth(orderCapture, bomSalesUser.getUsername())) {
//			projectCodeList.add(LtsConstant.BASKET_PROJECT_CD_EYE3_FREE_3MTH);
//		}
//		
//		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())
//				&& LtsSbOrderHelper.getProfileArpu(orderCapture) <= 110.00) {
//			projectCodeList.add(LtsConstant.BASKET_PROJECT_CD_30_FREE_6);
//		}
//		
//		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())
//				&& LtsSbOrderHelper.getProfileArpu(orderCapture) > 110.00) {
//			projectCodeList.add(LtsConstant.BASKET_PROJECT_CD_30_FREE_6_ARPU_OVER_110);
//		}
//		
//		if (StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())
//				&& !LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
//			projectCodeList.add(LtsConstant.BASKET_PROJECT_CD_30_FREE_6);
//		}
		
		if (LtsSbOrderHelper.isSecondContract(orderCapture.getLtsServiceProfile())) {
			projectCodeList.add(LtsConstant.BASKET_PROJECT_CD_2ND_CONTRACT);
		}
		else {
			projectCodeList.add(LtsConstant.BASKET_PROJECT_CD_1ST_CONTRACT);
		}
		
		if (projectCodeList.isEmpty()) {
			return null;
		}
		
		return projectCodeList.toArray(new String[projectCodeList.size()] );
	}

	public String[] getProfilePsedCds(ServiceDetailProfileLtsDTO serviceProfile) {
		List<String> eligiblePsefList = ltsBasketService.getEligiblePsefList();
		List<String> psefCdList = new ArrayList<String>();
		
		if (eligiblePsefList == null || eligiblePsefList.isEmpty()) {
			return null;
		}
		
		ItemDetailProfileLtsDTO currentTermPlanItem = LtsSbOrderHelper.getProfileServiceTermPlan(serviceProfile);
		if (currentTermPlanItem != null && currentTermPlanItem.getItemDetail() != null) {
			for(OfferDetailProfileLtsDTO offerDetail : currentTermPlanItem.getOffers()) {
				if (offerDetail.getPsefSet() != null && !offerDetail.getPsefSet().isEmpty()) {
					for (String psef : offerDetail.getPsefSet()) {
						if (eligiblePsefList.contains(psef)) {
							psefCdList.add(psef);
						}
					}
				}
			}
		}
		
		boolean isFreeToGo = LtsSbOrderHelper.isFreeToGoService(serviceProfile);
		
		if (isFreeToGo) {
			ItemDetailProfileLtsDTO expiredTermPlanItem = LtsSbOrderHelper.getExpiredTermPlanWithin6Mths(serviceProfile);
			if (expiredTermPlanItem != null && expiredTermPlanItem.getItemDetail() != null) {
				for(OfferDetailProfileLtsDTO offerDetail : expiredTermPlanItem.getOffers()) {
					if (offerDetail.getPsefSet() != null && !offerDetail.getPsefSet().isEmpty()) {
						for (String psef : offerDetail.getPsefSet()) {
							if (eligiblePsefList.contains(psef)) {
								psefCdList.add(psef);
							}
						}
					}
				}
			}
			
			ItemDetailProfileLtsDTO endedTermPlanItem = LtsSbOrderHelper.getEndedTermPlanWithin6Mths(serviceProfile);
			if (endedTermPlanItem != null && endedTermPlanItem.getItemDetail() != null) {
				for(OfferDetailProfileLtsDTO offerDetail : endedTermPlanItem.getOffers()) {
					if (offerDetail.getPsefSet() != null && !offerDetail.getPsefSet().isEmpty()) {
						for (String psef : offerDetail.getPsefSet()) {
							if (eligiblePsefList.contains(psef)) {
								psefCdList.add(psef);
							}
						}
					}
				}
			}
		}
		
		if (psefCdList.isEmpty()) {
			return null;
		}
		
		return psefCdList.toArray(new String[psefCdList.size()]);
	}
	
	// Obsolete since 2017/02/23 
//	private boolean isReturnEmptyBasket(OrderCaptureDTO orderCapture, LtsBasketSelectionFormDTO form) {
//		if (LtsConstant.BASKET_PROJECT_CD_EYE2A_FAULT_SPECIAL.equals(form.getFilterProjectCd())) {
//			ItemDetailProfileLtsDTO profileTp = LtsSbOrderHelper.getProfileServiceTermPlan(orderCapture.getLtsServiceProfile());
//			if (!LtsSbOrderHelper.isSecondContract(orderCapture.getLtsServiceProfile()) 
//					&& profileTp.getTpExpiredMonths() < -12) {
//				return true;	
//			}
//		}
//		return false;
//	}
	
	
	private ValidationResultDTO validateSubmit(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsBasketSelectionFormDTO form, BasketDetailDTO selectedBasket) {

		if(StringUtils.isNotBlank(selectedBasket.getProgramCd())){
			String invalidProgramDesc = ltsOfferService.validateQuotaByProgramCdRtnStr(selectedBasket.getProgramCd(), 1);
			if(StringUtils.isNotBlank(invalidProgramDesc)){
				return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.offerDtl.outQuotaMsg", new Object[] {invalidProgramDesc}, this.locale)), null);
			}
		}
		
		if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())
				&& isTpStartDateWithin3Month(orderCapture)
				&& !isSelectedFaultCaseBasket(selectedBasket)){
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.basketSelect.errorMsg", new Object[] {}, this.locale)), null);
		}
				
		return new ValidationResultDTO(Status.VALID, null, null);
	}
	
	private boolean isSelectedFaultCaseBasket(BasketDetailDTO selectedBasket) {
		if (selectedBasket == null) {
			return false;
		}
		return LtsConstant.EYE_FAULT_CASE_PROJECT_CD_MAP.containsValue(selectedBasket.getProjectCd()); 
	}
	
	private boolean isTpStartDateWithin3Month(OrderCaptureDTO orderCapture) {
		ItemDetailProfileLtsDTO profileTermPlan = LtsSbOrderHelper.getProfileServiceTermPlan(orderCapture.getLtsServiceProfile());
        if(profileTermPlan != null){ 
        	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String effStartDate = profileTermPlan.getConditionEffStartDate();
			String dateToCompare = null;
			if(orderCapture.getLtsMiscellaneousForm().isBackDateOrder() && orderCapture.getLtsMiscellaneousForm().getBackDate() != null){
				dateToCompare = orderCapture.getLtsMiscellaneousForm().getBackDate();
			}else{
				dateToCompare = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
			}
			if(effStartDate != null && dateToCompare != null){
				try {
					Date date = formatter.parse(effStartDate);
					Date applicationDate = formatter.parse(dateToCompare);
					
					if(ltsEffStartDateLkupCacheService.getCodeLkupDAO().getCodeLkup()[0] != null){
						String ltsEffStartDate = ltsEffStartDateLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey();
    					int month = Integer.parseInt(StringUtils.defaultIfEmpty(ltsEffStartDate, "3"));
    					Date dateAfterBarSlot = DateUtils.addMonths(date, month);           					            				
    					if(dateAfterBarSlot.after(applicationDate)){
    						return true;
    					}
					}    				
				} catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
					throw new AppRuntimeException(e);
				}
			}		
        }
        return false;
	}
	
	
	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
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
