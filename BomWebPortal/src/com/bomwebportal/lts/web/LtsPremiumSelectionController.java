package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO.AdditionalTvType;
import com.bomwebportal.lts.dto.form.LtsBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.LtsPremiumSelectionFormDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.PremiumItemSetDetailService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.offer.ItemSetDetailService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.pccw.springboard.svc.server.dto.CampaignDTO;

public class LtsPremiumSelectionController extends SimpleFormController {
	
	private final String commandName = "ltsPremiumSelectionCmd";
	private final String currentView = "ltspremiumselection.html";
	private final String nextView = "ltsrenewmodemarrangement.html";
//	private final String nextView = "ltscustomeridentification.html";
	private final String viewName = "ltspremiumselection";
	
	
	protected CustomerProfileLtsService customerProfileLtsService;
	protected PremiumItemSetDetailService premiumItemSetDetailService;
	protected LtsOfferService ltsOfferService;
	protected CodeLkupCacheService ltsPremiumCampaignLkupCacheService;	
	protected ItemSetDetailService itemSetDetailService;
	
	public ItemSetDetailService getItemSetDetailService() {
		return itemSetDetailService;
	}

	public void setItemSetDetailService(ItemSetDetailService itemSetDetailService) {
		this.itemSetDetailService = itemSetDetailService;
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

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public CodeLkupCacheService getLtsPremiumCampaignLkupCacheService() {
		return ltsPremiumCampaignLkupCacheService;
	}

	public void setLtsPremiumCampaignLkupCacheService(
			CodeLkupCacheService ltsPremiumCampaignLkupCacheService) {
		this.ltsPremiumCampaignLkupCacheService = ltsPremiumCampaignLkupCacheService;
	}

	public LtsPremiumSelectionController() {
		setCommandClass(LtsPremiumSelectionFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (LtsSessionHelper.getOrderCapture(request) == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	private ValidationResultDTO[] validateSubmit(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsPremiumSelectionFormDTO form) {
		List<ValidationResultDTO> validationResultList = new ArrayList<ValidationResultDTO>();
		validationResultList.add(validateExclusiveItem(request, orderCapture, form));
		if(orderCapture.getLtsBasketServiceForm() != null
				&& !orderCapture.getLtsBasketServiceForm().isExcludeQuotaCheck()){
			validationResultList.add(validateItemQuota(form));
		}
		return validationResultList.toArray(new ValidationResultDTO[validationResultList.size()]);
	}
	
	private ValidationResultDTO validateItemQuota(LtsPremiumSelectionFormDTO form){
		List<String> errorMsgList = new ArrayList<String>();
		errorMsgList.addAll(ltsOfferService.validateQuotaItemSetList(form.getGiftSetList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemSetList(form.getPremiumSetList()).getMessageList());

		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}

		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	private ValidationResultDTO validateExclusiveItem(
			HttpServletRequest request, OrderCaptureDTO orderCapture,
			LtsPremiumSelectionFormDTO form) {
		List<ItemSetDetailDTO> allItemSetList = new ArrayList<ItemSetDetailDTO>();
		if (form.getPremiumSetList() != null && !form.getPremiumSetList().isEmpty()) {
			allItemSetList.addAll(form.getPremiumSetList());	
		}
		if (form.getGiftSetList() != null && !form.getGiftSetList().isEmpty()) {
			allItemSetList.addAll(form.getGiftSetList());	
		}
		 return ltsOfferService.validateExclusiveItemSet(allItemSetList, allItemSetList, 
				 LtsConstant.DISPLAY_TYPE_ITEM_SELECT, LtsConstant.LOCALE_ENG);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		LtsPremiumSelectionFormDTO form = (LtsPremiumSelectionFormDTO) command;
		orderCapture.setLtsPremiumSelectionForm(form);

		if(LtsConstant.ACTION_LTS_GIFT_CODE_SEARCH.equals(form.getAction())){			
			return new ModelAndView(new RedirectView(currentView));
		}
		
		
		ValidationResultDTO[] validationResults = validateSubmit(request, orderCapture, form);
		
		for (ValidationResultDTO validationResult : validationResults) {
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}	
		}
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		boolean isRenewPremiumBasket = LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType()) 
										&& LtsConstant.BASKET_CATEGORY_PREMIUM.equals(orderCapture.getSelectedBasket().getBasketCatg());
		
		List<ItemSetDetailDTO> premiumSetList = premiumItemSetDetailService
				.getAllPremiumSetList(getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_PREM_SET, locale, isRenewPremiumBasket));
		
		if (premiumSetList == null) {
			premiumSetList = new ArrayList<ItemSetDetailDTO>();
		}
		
		
		List<ItemSetDetailDTO> premiumSetForSelectedBasketList =  getPremiumSetFromSelectedBasketItem(orderCapture, bomSalesUser, locale);
		
		if (premiumSetForSelectedBasketList != null && !premiumSetForSelectedBasketList.isEmpty()) {
			premiumSetList.addAll(premiumSetForSelectedBasketList);	
		}
		
		if (orderCapture.getLtsMiscellaneousForm().isSubmitDisForm()) {
			
			List<ItemSetDetailDTO> dFormPremSetList = premiumItemSetDetailService.getPremiumSetList(getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_DFORM_PREM, locale, isRenewPremiumBasket));
			
			if (dFormPremSetList != null && !dFormPremSetList.isEmpty()) {
				premiumSetList.addAll(dFormPremSetList);	
			}
		}
		else {
			List<ItemSetDetailDTO> teamPremSetList = premiumItemSetDetailService.getPremiumSetList(getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_TEAM_PREM, locale, isRenewPremiumBasket));
			if (teamPremSetList != null && !teamPremSetList.isEmpty()) {
				premiumSetList.addAll(teamPremSetList);	
			}
		}
		String giftCode = null;
		// Gift Code Set
		
		
		LtsPremiumSelectionFormDTO form = new LtsPremiumSelectionFormDTO();
		
		if(orderCapture.getLtsPremiumSelectionForm() != null 
				&& StringUtils.isNotBlank(orderCapture.getLtsPremiumSelectionForm().getGiftCode())){
			giftCode = orderCapture.getLtsPremiumSelectionForm().getGiftCode();
			ItemSetDetailDTO giftSet = premiumItemSetDetailService.getGiftItemSet(giftCode, locale, orderCapture.getBasketChannelId(), null, orderCapture.getLtsBasketSelectionForm().getSelectedBasketId(), bomSalesUser.getShopCd(), orderCapture.getNewAddressRollout().getSrvBdary());
			if(giftSet != null && StringUtils.isNotBlank(giftSet.getItemSetId())){
				form.setGiftSetList(Lists.newArrayList(giftSet));
				form.setGiftCode(giftCode);
			}
		}
		
		if (!premiumSetList.isEmpty()) {
			ItemSetDetailDTO[] premiumSetDetails = premiumSetList.toArray(new ItemSetDetailDTO[premiumSetList.size()]);
			filterExclusivePremiums(request, orderCapture, premiumSetDetails);
			
			Arrays.sort(premiumSetDetails, new Comparator<ItemSetDetailDTO>() {
				public int compare(ItemSetDetailDTO o1, ItemSetDetailDTO o2) {
					return o1.getItemSetId().compareTo(o2.getItemSetId());
				}
			});
			
			form.setPremiumSetList(Arrays.asList(premiumSetDetails));
		}

		return form;
	}


	
	private void filterExclusivePremiums(HttpServletRequest request, OrderCaptureDTO orderCapture, ItemSetDetailDTO[] premiumSetDetails) {
		
		if (ArrayUtils.isEmpty(premiumSetDetails)) {
			return;
		}
		List<ItemDetailDTO> allItemDetailList = getValidatingItemList(orderCapture);
		
		if (allItemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemSetDetailDTO premiumSet : premiumSetDetails) {
			if (ArrayUtils.isEmpty(premiumSet.getItemDetails())) {
				continue;
			}
			
			List<ExclusiveItemDetailDTO> exclusiveItemDetailList = ltsOfferService
				.getExclusiveItemDetailList(allItemDetailList, Lists.newArrayList(premiumSet.getItemDetails()),
					RequestContextUtils.getLocale(request).toString(), false);
			
			if (exclusiveItemDetailList == null || exclusiveItemDetailList.isEmpty()) {
				continue;
			}
			
			for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {
				premiumSet.setItemDetails(filterExclusiveItem(premiumSet.getItemDetails(), exclusiveItemDetail));
			}
		}
	}

	private ItemDetailDTO[] filterExclusiveItem(ItemDetailDTO[] itemDetails, ExclusiveItemDetailDTO exclusiveItemDetail) {
		
		if (ArrayUtils.isEmpty(itemDetails)) {
			return null;
		}
		
		List<ItemDetailDTO> newItemDetailList = new ArrayList<ItemDetailDTO>();
		
		for (ItemDetailDTO itemDetail : itemDetails) {
			if (StringUtils.equals(exclusiveItemDetail.getItemAId(), itemDetail.getItemId()) ||
					StringUtils.equals(exclusiveItemDetail.getItemBId(), itemDetail.getItemId())) {
				continue;
			}
			newItemDetailList.add(itemDetail);
		}
		if (newItemDetailList.isEmpty()) {
			return null;
		}
		return newItemDetailList.toArray(new ItemDetailDTO[newItemDetailList.size()]);
	}
	
	private List<ItemDetailDTO> getValidatingItemList(OrderCaptureDTO orderCapture) {
		List<ItemDetailDTO> allItemDetailList = new ArrayList<ItemDetailDTO>();
		if (orderCapture.getLtsBasketServiceForm() != null) {
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsBasketServiceForm().getContentItemList()));	
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsBasketServiceForm().getBvasItemList()));
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsBasketServiceForm().getMoovItemList()));
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsBasketServiceForm().getPlanItemList()));
			if (orderCapture.getLtsBasketServiceForm().getContItemSetList() != null) {
				for (ItemSetDetailDTO contItemSet : orderCapture.getLtsBasketServiceForm().getContItemSetList()) {
					if (ArrayUtils.isNotEmpty(contItemSet.getItemDetails())) {
						allItemDetailList.addAll(getSelectedItemList(Lists.newArrayList(contItemSet.getItemDetails())));	
					}
				}
			}
		}
		return allItemDetailList;
	}
	
	private List<ItemDetailDTO> getSelectedItemList(List<ItemDetailDTO> itemDetailList) {
		List<ItemDetailDTO> selectedItemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return selectedItemDetailList;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (itemDetail.isSelected()) {
				selectedItemDetailList.add(itemDetail);
			}
		}
		return selectedItemDetailList;
	}
	
	
	private ItemSetCriteriaDTO getItemSetCriteria(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String itemSetType, String locale, boolean isRenewPremiumBasket) {
		double profileReduceArpu = LtsSbOrderHelper.getCancelProfileItemAmt(orderCapture.getLtsBasketVasDetailForm());
		double newArpu = LtsSbOrderHelper.getNewArpu(orderCapture, orderCapture.getLtsBasketServiceForm(), orderCapture.getLtsBasketVasDetailForm() ) - profileReduceArpu;
		double profileArpu = LtsSbOrderHelper.getProfileArpu(orderCapture);
		
		ItemSetCriteriaDTO itemSetCriteria = new ItemSetCriteriaDTO();
		itemSetCriteria.setBasketId(orderCapture.getSelectedBasket().getBasketId());
		itemSetCriteria.setChannelId(orderCapture.getBasketChannelId());
		itemSetCriteria.setCustCampaignCd(getCustCampaign(orderCapture));
		itemSetCriteria.setTpExpiredMonths(String.valueOf(LtsSbOrderHelper.getTpExpireMth(orderCapture.getLtsServiceProfile(), orderCapture.getOrderType())));
		itemSetCriteria.setApplnDate(orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		itemSetCriteria.setProfileArpu(profileArpu);
		itemSetCriteria.setAdditionalFee(LtsSbOrderHelper.getAdditionalFee(orderCapture, orderCapture.getLtsBasketServiceForm(), orderCapture.getLtsBasketVasDetailForm()));
		itemSetCriteria.setAreaCode(bomSalesUser.getAreaCd());
		itemSetCriteria.setTeamCode(bomSalesUser.getShopCd());
		itemSetCriteria.setStandaloneItemSetTypes(getStandaloneItemSetTypes(orderCapture));
		itemSetCriteria.setLocale(locale);
		itemSetCriteria.setSelectDefault(true);
		itemSetCriteria.setDisplayType(LtsConstant.DISPLAY_TYPE_ITEM_SELECT);
		itemSetCriteria.setAtLeastHave1Premium(isAtLeastHaveOnePremium(orderCapture, profileArpu, newArpu));
		itemSetCriteria.setItemSetType(itemSetType);
		itemSetCriteria.setEligibleItemTypeList(getEligibleItemTypeList(orderCapture));
		itemSetCriteria.setHousingType(orderCapture.getNewAddressRollout().getHousingType());
		itemSetCriteria.setLtsHousingType(orderCapture.getNewAddressRollout().getLtsHousingCatCd());
		itemSetCriteria.setProfilePsefCds(getProfilePsedCds(orderCapture.getLtsServiceProfile()));
		itemSetCriteria.setNewArpu(newArpu);
		itemSetCriteria.setSrvBoundary(orderCapture.getNewAddressRollout().getSrvBdary());
		itemSetCriteria.setSrvTenure(LtsSbOrderHelper.getTenure(orderCapture));
		itemSetCriteria.setEligibleDocType(LtsSbOrderHelper.getTargetCustomerDocType(orderCapture));
		
		if (isRenewPremiumBasket) {
			itemSetCriteria.setOrderType(orderCapture.getOrderType());
			itemSetCriteria.setToProd(orderCapture.getLtsServiceProfile().getExistEyeType());
			itemSetCriteria.setAssignedQty(getAssignedPremiumQty(orderCapture));
		}
		return itemSetCriteria;

	}
	
	
	private List<String> getEligibleItemTypeList(OrderCaptureDTO orderCapture) {
		List<String> eligibleItemTypeList = new ArrayList<String>();
		
		if (orderCapture.getLtsBasketServiceForm() != null) {
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsBasketServiceForm().getPlanItemList(),
					LtsConstant.ITEM_TYPE_PLAN)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_PLAN);
			}
			
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsBasketServiceForm().getMoovItemList(),
					LtsConstant.ITEM_TYPE_MOOV)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_MOOV);
			}
			
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsBasketServiceForm().getContentItemList(),
					LtsConstant.ITEM_TYPE_CONTENT)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_CONTENT);
			}
			
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsBasketVasDetailForm().getHotVasItemList(), LtsConstant.ITEM_TYPE_VAS_HOT) 
					|| ltsOfferService.isItemSelected(orderCapture
							.getLtsBasketVasDetailForm().getOtherVasItemList(), LtsConstant.ITEM_TYPE_VAS_OTHER)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_VAS);
			}
		}

		if (orderCapture.getLtsNowTvServiceForm() != null && !orderCapture.getLtsNowTvServiceForm().isSelectedBasketBundleTv()) {
			if (ltsOfferService.isItemSelected(orderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList(), LtsConstant.ITEM_TYPE_NOWTV_SPEC)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_NOWTV_SPEC);
			}
			if (ltsOfferService.isItemSelected(orderCapture.getLtsNowTvServiceForm().getNowTvPayItemList(), LtsConstant.ITEM_TYPE_NOWTV_PAY)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_NOWTV_PAY);
			}
			if (ltsOfferService.isItemSelected(orderCapture.getLtsNowTvServiceForm().getNowTvMirrorItemList(), LtsConstant.ITEM_TYPE_NOWTV_MIRR)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_NOWTV_MIRR);
			}
		}
		
		return eligibleItemTypeList;
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
			return null;
		}
		
		for (CampaignDTO campaign : campaignList) {
			if (StringUtils.isEmpty(campaign.getValuePropId())) {
				continue;
			}
			String campaignCd = (String)ltsPremiumCampaignLkupCacheService.get(campaign.getValuePropId());
			if (StringUtils.isNotEmpty(campaignCd)) {
				return campaignCd;
			}
		}
		return null;
	}
	
	
	private Integer getAssignedPremiumQty(OrderCaptureDTO orderCapture) {
		
		String toProd = StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getExistEyeType()) ? orderCapture.getLtsServiceProfile().getExistEyeType() : "DEL" ;
		double existTpArpu = LtsSbOrderHelper.getRenewalProfileTpArpu(orderCapture.getLtsServiceProfile());
		double newTpApru = LtsSbOrderHelper.getNewTpArpu(orderCapture);
		
		String premiumQty = premiumItemSetDetailService.getArpuChangePremiumQty(toProd, String.valueOf(existTpArpu), String.valueOf(newTpApru));
		return StringUtils.isEmpty(premiumQty) ? null : new Integer(premiumQty);
	}
	
	private boolean isAtLeastHaveOnePremium(OrderCaptureDTO orderCapture, double profileArpu, double newArpu) {
		
		if (!orderCapture.getLtsMiscellaneousForm().isSubmitDisForm()) {
			return false;
		}
		
		boolean arpuDecrease = newArpu < profileArpu;
		double newTpAmount = 0.0;
		
		if (orderCapture.getLtsBasketServiceForm().getPlanItemList() != null && !orderCapture.getLtsBasketServiceForm().getPlanItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : orderCapture.getLtsBasketServiceForm().getPlanItemList()) {
				if (itemDetail.isSelected() && StringUtils.isNotEmpty(itemDetail.getRecurrentAmt())) {
					newTpAmount = Double.parseDouble(itemDetail.getRecurrentAmt());
					break;
				}
			}
		}
		
		if (LtsConstant.EYE_TYPE_EYE1_5A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())
				|| LtsConstant.EYE_TYPE_EYE2.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())
				|| LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			if (arpuDecrease && newTpAmount > 88.00) {
				return true;
			}
		}
		
		return false;
	}
	
	
	private List<ItemSetDetailDTO> getPremiumSetFromSelectedBasketItem(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String locale) {
		
		List<ItemSetDetailDTO> premiumItemSetList = new ArrayList<ItemSetDetailDTO>();
		
		if (orderCapture.getLtsBasketServiceForm() != null) {
			premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser, orderCapture.getLtsBasketServiceForm().getMoovItemList(), locale));
			premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser, orderCapture.getLtsBasketServiceForm().getContentItemList(), locale));
		}
		
		if (orderCapture.getLtsNowTvServiceForm() != null) {
			
			if (orderCapture.getLtsNowTvServiceForm().getAdditionalTvType() == AdditionalTvType.SD_PAY_CHANNEL) {
				premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser, orderCapture.getLtsNowTvServiceForm().getNowTvPayItemList(), locale));
			}
			if (orderCapture.getLtsNowTvServiceForm().getAdditionalTvType() == AdditionalTvType.SD_SPECIAL_CHANNEL) {
				premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser, orderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList(), locale));
			}
			if (orderCapture.getLtsNowTvServiceForm().getAdditionalTvType() == AdditionalTvType.MIRROR) {
				premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser, orderCapture.getLtsNowTvServiceForm().getNowTvMirrorItemList(), locale));
			}
			
			if (!(orderCapture.getLtsNowTvServiceForm().getAdditionalTvType() == AdditionalTvType.NO_ADDITIONAL_TV || orderCapture
					.getLtsNowTvServiceForm().getAdditionalTvType() == AdditionalTvType.MIRROR)) {
				premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser,
						orderCapture.getLtsNowTvServiceForm()
								.getNowTvSportItemList(), locale));
			}
			
			
		}
		
		return premiumItemSetList;
	}
	
	private List<ItemSetDetailDTO> getItemSetDetail(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, List<ItemDetailDTO> itemDetailList, String locale) {
		
		List<ItemSetDetailDTO> itemSetDetailList = new ArrayList<ItemSetDetailDTO>();
		
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return itemSetDetailList;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (itemDetail.isSelected()) {
				ItemSetCriteriaDTO itemSetCriteria = LtsSbOrderHelper.getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_PREM_SET, locale);
				itemSetCriteria.setRelatedItemId(itemDetail.getItemId());
				List<ItemSetDetailDTO> premiumSetList = premiumItemSetDetailService.getBasketItemSet(itemSetCriteria);
				if (premiumSetList != null && !premiumSetList.isEmpty()) {
					itemSetDetailList.addAll(premiumSetList);
				}
			}
		}
		
		return itemSetDetailList;
	}
	
	
	private String[] getStandaloneItemSetTypes(OrderCaptureDTO orderCapture) {
		
		List<String> itemSetTypeList = new ArrayList<String>();
		
		if (orderCapture.getLtsAddressRolloutForm() != null) {
			if (orderCapture.getLtsAddressRolloutForm().isExternalRelocate()) {
				if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
					itemSetTypeList.add(LtsConstant.ITEM_SET_TYPE_ER_DEL_PREMIUM);	
				}
				else {
					itemSetTypeList.add(LtsConstant.ITEM_SET_TYPE_ER_EYE_PREMIUM);
				}
			}
		}
		
		if (itemSetTypeList.isEmpty()) {
			return null;
		}
		
		return itemSetTypeList.toArray(new String[itemSetTypeList.size()]);
	}
	
	public String[] getProfilePsedCds(ServiceDetailProfileLtsDTO serviceProfile) {
		List<String> eligiblePsefList = itemSetDetailService.getItemSetEligiblePsefList();
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
	
}
