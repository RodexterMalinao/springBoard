package com.bomwebportal.lts.web.acq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetAttbDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsPremiumSelectionFormDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.PremiumItemSetDetailService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.google.common.collect.Lists;

public class LtsAcqPremiumSelectionController extends SimpleFormController {

	private final String viewName = "lts/acq/ltsacqpremiumselection";
	private final String currentView = "ltsacqpremiumselection.html";
	private final String nextView = "ltsacqsalesinfo.html";
	private final String commandName = "ltsAcqPremiumSelectionCmd";

	protected PremiumItemSetDetailService premiumItemSetDetailService;
	protected LtsOfferService ltsOfferService;
	protected LtsBasketService ltsBasketService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqPremiumSelectionController(){
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsPremiumSelectionFormDTO.class);
	}
	
	private String pcdSbidErrMsg;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		if (order == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		LtsPremiumSelectionFormDTO form = (LtsPremiumSelectionFormDTO) command;
		orderCapture.setLtsPremiumSelectionForm(form);
		List<ItemSetDetailDTO> premiumItems = form.getPremiumSetList();
		
		if(premiumItems != null && premiumItems.size() > 0){
			for(ItemSetDetailDTO itemSet : premiumItems){
				if(StringUtils.isNotBlank(itemSet.getSelectedItemId())){
					ItemSetAttbDTO[] attbList = itemSet.getItemSetAttbs();
					if(attbList != null){
						for(ItemSetAttbDTO attb : attbList){
							if(LtsConstant.ITEM_SET_ATTB_LAST_SRD.equals(attb.getAttbCode())){
								if(StringUtils.isBlank(orderCapture.getPremiumItemSrdBeforeDateLimit())){
									orderCapture.setPremiumItemSrdBeforeDateLimit(attb.getAttbValue());
								}
								else if(StringUtils.isNotBlank(attb.getAttbValue())){
									DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									Date existingLimitDate = sdf.parse(orderCapture.getPremiumItemSrdBeforeDateLimit());
									Date newLimitDate = sdf.parse(attb.getAttbValue());

									if(newLimitDate.before(existingLimitDate)){
										orderCapture.setPremiumItemSrdBeforeDateLimit(attb.getAttbValue());
									}
								}
							}
							else if(LtsConstant.ITEM_SET_ATTB_SRD_PERIOD.equals(attb.getAttbCode())){
								if(StringUtils.isBlank(orderCapture.getPremiumItemSrdDayLimit())){
									orderCapture.setPremiumItemSrdDayLimit(attb.getAttbValue());
								}
								else if(StringUtils.isNotBlank(attb.getAttbValue())){
									if(Integer.parseInt(attb.getAttbValue()) < Integer.parseInt(orderCapture.getPremiumItemSrdDayLimit())){
										orderCapture.setPremiumItemSrdDayLimit(attb.getAttbValue());
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		if(StringUtils.equals(form.getAction(), LtsConstant.ACTION_LTS_GIFT_CODE_SEARCH) || StringUtils.equals(form.getAction(), LtsConstant.ACTION_LTS_PCD_SBID_SEARCH)){			
			return new ModelAndView(new RedirectView(currentView));
		}

		// Submit validation
		ValidationResultDTO[] validationResults = validateSubmit(request, orderCapture, form);
		
		for (ValidationResultDTO validationResult : validationResults) {
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}	
		}
		
		boolean isPcdBundleBasket = false;
		boolean isPcdBundlePremium = false;
		boolean isPcdBundle = false;
		boolean preIsPcdBundle = false;
		
		if(orderCapture.getLtsAcqBasketSelectionForm() != null && StringUtils.isNotBlank(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid()))
		{
			isPcdBundleBasket = true;
		}
		
		if(form != null && StringUtils.isNotBlank(form.getPcdSbid()))
		{
			List<ItemSetDetailDTO> premiumSetList = null;
			if(form.getPremiumSetList()!=null)
			{
				premiumSetList = form.getPremiumSetList();
			}
			
			ItemDetailDTO[] itemDetails = null;
			int selectedPcdItemCount = 0;
			for (int i=0; premiumSetList != null && i < premiumSetList.size(); i++  ) {
				int selectedItemCount = 0;
				
				if(premiumSetList.get(i).getItemSetType().equalsIgnoreCase("PREM-PCD"))
				{				
					itemDetails = premiumSetList.get(i).getItemDetails();
					if (!ArrayUtils.isEmpty(itemDetails)) {
						for (ItemDetailDTO itemDetail : itemDetails) {
							if (itemDetail.isSelected()) {
								selectedItemCount++;
							}
						}
					}
				}
				
				selectedPcdItemCount += selectedItemCount;
			}
			
			if(selectedPcdItemCount>0)
			{
				isPcdBundlePremium = true;
			}
		}
		
		if(isPcdBundleBasket && isPcdBundlePremium)
		{
			if(!form.getPcdSbid().equals(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid()))
			{
				return new ModelAndView(new RedirectView(currentView));
			}
		}
		
		if(orderCapture.getLtsModemArrangementForm()!=null)
		{
			
			if(isPcdBundleBasket || isPcdBundlePremium)
			{
				isPcdBundle = true;
			}
			
			if(orderCapture.getLtsModemArrangementForm().isPcdBundleBasket() || orderCapture.getLtsModemArrangementForm().isPcdBundlePremium())
			{
				preIsPcdBundle = true;
			}
			
			if( isPcdBundle != preIsPcdBundle)
			{
				orderCapture.setLtsModemArrangementForm(null);
			}
			
		}
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private ValidationResultDTO[] validateSubmit(HttpServletRequest request, AcqOrderCaptureDTO orderCapture, LtsPremiumSelectionFormDTO form) {
		List<ValidationResultDTO> validationResultList = new ArrayList<ValidationResultDTO>();
		validationResultList.add(validateExclusiveItem(request, form));
		if(orderCapture.getLtsAcqBasketServiceForm() != null
				&& !orderCapture.getLtsAcqBasketServiceForm().isExcludeQuotaCheck()){
			validationResultList.add(validateItemQuota(form));
		}
		return validationResultList.toArray(new ValidationResultDTO[validationResultList.size()]);
	}
	
	private ValidationResultDTO validateItemQuota(LtsPremiumSelectionFormDTO form){
		List<String> outQuotaItemList = new ArrayList<String>();
		List<String> errorMsgList = new ArrayList<String>();
		if(form.getGiftSetList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemSetDescList(form.getGiftSetList()));
		if(form.getPremiumSetList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemSetDescList(form.getPremiumSetList()));
		
		if (outQuotaItemList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		else
		{
			for(int i = 0; i < outQuotaItemList.size(); i++){
				errorMsgList.add(messageSource.getMessage("lts.offerDtl.outQuotaMsg", new Object[] {outQuotaItemList.get(i)}, locale));
			}
		}
		
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	private ValidationResultDTO validateExclusiveItem(
			HttpServletRequest request, LtsPremiumSelectionFormDTO form) {
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
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		String locale = RequestContextUtils.getLocale(request).toString();
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		List<String> eligibleItemTypeList = new ArrayList<String>();
		
		if (orderCapture.getLtsAcqBasketServiceForm() != null) {
			
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsAcqBasketServiceForm().getPlanItemList(),
					LtsConstant.ITEM_TYPE_PLAN)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_PLAN);
			}
			
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsAcqBasketServiceForm().getMoovItemList(),
					LtsConstant.ITEM_TYPE_MOOV)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_MOOV);
			}
			
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsAcqBasketServiceForm().getContentItemList(),
					LtsConstant.ITEM_TYPE_CONTENT)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_CONTENT);
			}
			
			if (ltsOfferService.isItemSelected(orderCapture
					.getLtsAcqBasketVasDetailForm().getHotVasItemList(), LtsConstant.ITEM_TYPE_VAS_HOT) 
					|| ltsOfferService.isItemSelected(orderCapture
							.getLtsAcqBasketVasDetailForm().getOtherVasItemList(), LtsConstant.ITEM_TYPE_VAS_OTHER)) {
				eligibleItemTypeList.add(LtsConstant.PREMIUM_ELIGIBLE_TYPE_VAS);
			}
		}
		
		double additionalFee = LtsSbOrderHelper.getAdditionalFee(orderCapture.getLtsAcqBasketServiceForm());
		
		ItemSetCriteriaDTO itemSetCriteria = LtsSbOrderHelper.getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_PREM_SET, locale);
		itemSetCriteria.setAdditionalFee(additionalFee);
		itemSetCriteria.setEligibleItemTypeList(eligibleItemTypeList);
		itemSetCriteria.setLtsHousingType(orderCapture.getAddressRollout().getLtsHousingCatCd());
		itemSetCriteria.setOrderType(LtsConstant.ORDER_TYPE_SB_ACQUISITION);
		
		List<ItemSetDetailDTO> premiumSetList = premiumItemSetDetailService.getAllPremiumSetList(itemSetCriteria);
		
		if(premiumSetList == null){
			premiumSetList = new ArrayList<ItemSetDetailDTO>();
		}
		
		premiumSetList.addAll(getPremiumSetFromSelectedBasketItem(orderCapture, bomSalesUser, locale));
		
		LtsPremiumSelectionFormDTO form = new LtsPremiumSelectionFormDTO();
		
		if(orderCapture.getLtsPremiumSelectionForm() != null && StringUtils.isNotBlank(orderCapture.getLtsPremiumSelectionForm().getGiftCode())){
			form.setGiftCode(orderCapture.getLtsPremiumSelectionForm().getGiftCode());
			ItemSetDetailDTO gift = getGiftItemSetDetail(orderCapture, orderCapture.getLtsPremiumSelectionForm().getGiftCode(), locale, itemSetCriteria.getTeamCode(), itemSetCriteria.getSrvBoundary());
			if(gift != null && StringUtils.isNotBlank(gift.getItemSetId())){
				premiumSetList.add(gift);
			}
		}
		
		// for pcd sbid search	
		if(orderCapture.getLtsPremiumSelectionForm() == null || orderCapture.getLtsPremiumSelectionForm() != null && !StringUtils.isNotBlank(orderCapture.getLtsPremiumSelectionForm().getPcdSbid())){
			if(orderCapture.getLtsAcqBasketSelectionForm() != null && StringUtils.isNotBlank(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid()))
			{
				form.setPcdSbid(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid());
			}
//			ItemSetDetailDTO gift = getPcdBundleGiftItemSetDetail(orderCapture, form.getPcdSbid(), locale);
//			if(gift != null && StringUtils.isNotBlank(gift.getItemSetId())){
//				premiumSetList.add(gift);
//			}
			List<ItemSetDetailDTO> gifts = getPcdBundleGiftItemSetDetail(orderCapture, form.getPcdSbid(), locale, bomSalesUser.getShopCd(), orderCapture.getAddressRollout().getSrvBdary());
			if(gifts != null){
				for (int i=0; i<gifts.size(); i++)
				{
					if(gifts.get(i) != null && StringUtils.isNotBlank(gifts.get(i).getItemSetId())){
						premiumSetList.add(gifts.get(i));
					}
				}
				
			}
			
		}
		
		form.setPcdSbidErrMsg("");
		pcdSbidErrMsg = "";
		
		if(orderCapture.getLtsPremiumSelectionForm() != null && StringUtils.isNotBlank(orderCapture.getLtsPremiumSelectionForm().getPcdSbid())){
			
			form.setPcdSbid(orderCapture.getLtsPremiumSelectionForm().getPcdSbid());
			boolean isPcdSbidValid = false;			

			if(!form.getPcdSbid().equals(""))
			{
				if(orderCapture.getLtsAcqBasketSelectionForm() != null && StringUtils.isNotBlank(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid()))
				{
					if(!orderCapture.getLtsPremiumSelectionForm().getPcdSbid().equals(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid()))
					{
						form.setPcdSbidErrMsg(messageSource.getMessage("lts.acq.premiumSelection.PCDOrderDifferent", new Object[] {}, this.locale));
						pcdSbidErrMsg = messageSource.getMessage("lts.acq.premiumSelection.PCDOrderDifferent", new Object[] {}, this.locale);						
					}
				}				
				
				if(pcdSbidErrMsg == null || pcdSbidErrMsg.equals(""))
				{
					
					isPcdSbidValid = ltsBasketService.isPcdSbidValid(form.getPcdSbid());
					
					if(!isPcdSbidValid)
					{
						form.setPcdSbidErrMsg(messageSource.getMessage("lts.acq.premiumSelection.noPendingPCDOrder", new Object[] {}, this.locale));
						pcdSbidErrMsg = messageSource.getMessage("lts.acq.premiumSelection.noPendingPCDOrder", new Object[] {}, this.locale);
					}
					else
					{
						form.setPcdSbidErrMsg("");
						pcdSbidErrMsg = "";
//						ItemSetDetailDTO gift = getPcdBundleGiftItemSetDetail(orderCapture, form.getPcdSbid(), locale);
//						if(gift != null && StringUtils.isNotBlank(gift.getItemSetId())){
//							premiumSetList.add(gift);
//						}
						List<ItemSetDetailDTO> gifts = getPcdBundleGiftItemSetDetail(orderCapture, form.getPcdSbid(), locale, bomSalesUser.getShopCd(), orderCapture.getAddressRollout().getSrvBdary());
						
						if(gifts == null){
							form.setPcdSbidErrMsg(messageSource.getMessage("lts.acq.premiumSelection.noPCDBundleAvailable", new Object[] {}, this.locale));
							pcdSbidErrMsg = messageSource.getMessage("lts.acq.premiumSelection.noPCDBundleAvailable", new Object[] {}, this.locale);
						}else{
							if(gifts.size()==0){
								form.setPcdSbidErrMsg(messageSource.getMessage("lts.acq.premiumSelection.noPCDBundleAvailable", new Object[] {}, this.locale));
								pcdSbidErrMsg = messageSource.getMessage("lts.acq.premiumSelection.noPCDBundleAvailable", new Object[] {}, this.locale);
							}
							else{
								for (int i=0; i<gifts.size(); i++)
								{
									if(gifts.get(i) != null && StringUtils.isNotBlank(gifts.get(i).getItemSetId())){
										premiumSetList.add(gifts.get(i));
									}
								}
							}
						}
					}
					
				}				
			}
		}
		//
		
		if (!premiumSetList.isEmpty()) {
			List<ItemSetDetailDTO> premiumSets = new ArrayList<ItemSetDetailDTO>();
			ItemSetDetailDTO[] premiumSetDetails = premiumSetList.toArray(new ItemSetDetailDTO[premiumSetList.size()]);
			filterExclusivePremiums(request, orderCapture, premiumSetDetails);
			Arrays.sort(premiumSetDetails, new Comparator<ItemSetDetailDTO>() {
				public int compare(ItemSetDetailDTO o1, ItemSetDetailDTO o2) {
					return o1.getItemSetId().compareTo(o2.getItemSetId());
				}
			});
			
			premiumSets.addAll(Arrays.asList(premiumSetDetails));
			form.setPremiumSetList(premiumSets);
		}

		return form;
	}
	

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String locale = RequestContextUtils.getLocale(request).toString();
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsPremiumSelectionFormDTO form = order.getLtsPremiumSelectionForm();
		
		referenceData.put("pcdSbidErrMsg", pcdSbidErrMsg);
				
		return referenceData; //super.referenceData(request);
	}
	
	private void filterExclusivePremiums(HttpServletRequest request, AcqOrderCaptureDTO orderCapture, ItemSetDetailDTO[] premiumSetDetails) {
		
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
	
	private List<ItemDetailDTO> getValidatingItemList(AcqOrderCaptureDTO orderCapture) {
		List<ItemDetailDTO> allItemDetailList = new ArrayList<ItemDetailDTO>();
		if (orderCapture.getLtsAcqBasketServiceForm() != null) {
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsAcqBasketServiceForm().getContentItemList()));	
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList()));
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList()));
			allItemDetailList.addAll(getSelectedItemList(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList()));
			if (orderCapture.getLtsAcqBasketServiceForm().getContItemSetList() != null) {
				for (ItemSetDetailDTO contItemSet : orderCapture.getLtsAcqBasketServiceForm().getContItemSetList()) {
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
	
	
	private List<ItemSetDetailDTO> getPremiumSetFromSelectedBasketItem(AcqOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String locale) {
		
		List<ItemSetDetailDTO> premiumItemSetList = new ArrayList<ItemSetDetailDTO>();
		
		if (orderCapture.getLtsAcqBasketServiceForm() != null) {
			premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser, orderCapture.getLtsAcqBasketServiceForm().getMoovItemList(), locale));
			premiumItemSetList.addAll(getItemSetDetail(orderCapture, bomSalesUser, orderCapture.getLtsAcqBasketServiceForm().getContentItemList(), locale));
		}
		
		return premiumItemSetList;
	}
	
	private ItemSetDetailDTO getGiftItemSetDetail(AcqOrderCaptureDTO orderCapture, String pGiftCode, String locale, String teamCd, String srvBdry) {
		if (pGiftCode == null || pGiftCode.isEmpty()) {
			return null;
		}

		return premiumItemSetDetailService.getGiftItemSet(pGiftCode, locale, orderCapture.getBasketChannelId(), null, orderCapture.getLtsAcqBasketSelectionForm().getSelectedBasketId(), teamCd, srvBdry);
	}
	
	private List<ItemSetDetailDTO> getPcdBundleGiftItemSetDetail(AcqOrderCaptureDTO orderCapture, String pPcdSbid, String locale,  String teamCd, String srvBdry) {
		if (pPcdSbid == null || pPcdSbid.isEmpty()) {
			return null;
		}
		
		if(orderCapture.getLtsAcqBasketSelectionForm() == null) {
			return null;
		}

		return premiumItemSetDetailService.getPcdBundleGiftItemSet("PCD SBID", locale, orderCapture.getBasketChannelId(),
				null, orderCapture.getLtsAcqBasketSelectionForm().getSelectedBasketId(), teamCd, srvBdry);
	}	
	
	private List<ItemSetDetailDTO> getItemSetDetail(AcqOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, List<ItemDetailDTO> itemDetailList, String locale) {
		
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
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}
	
	public PremiumItemSetDetailService getPremiumItemSetDetailService() {
		return premiumItemSetDetailService;
	}

	public void setPremiumItemSetDetailService(
			PremiumItemSetDetailService premiumItemSetDetailService) {
		this.premiumItemSetDetailService = premiumItemSetDetailService;
	}
	
    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
}
