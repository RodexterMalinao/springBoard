package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO.Action;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsOtherVoiceServiceController extends SimpleFormController {

	private final String nextView = "ltspremiumselection.html";
	private final String viewName = "ltsothervoiceservice";
	private final String commandName = "ltsOtherVoiceServiceCmd";
	
	protected LtsOfferService ltsOfferService;
	protected LtsProfileService ltsProfileService;
	protected OfferItemService offerItemService;
	protected ServiceProfileLtsService serviceProfileLtsService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	private LtsAcqDnPoolService ltsAcqDnPoolService;

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}
	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}
	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}

	public LtsOtherVoiceServiceController() {
		setCommandClass(LtsOtherVoiceServiceFormDTO.class);
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
			LtsOtherVoiceServiceFormDTO form = new LtsOtherVoiceServiceFormDTO();
			form.setCreate2ndDel(null);
			orderCapture.setLtsOtherVoiceServiceForm(form);
			return new ModelAndView(new RedirectView(nextView));
		}

		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		LtsOtherVoiceServiceFormDTO form = (LtsOtherVoiceServiceFormDTO)command;
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		List<LtsAcqNumberSelectionInfoDTO> dnList = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
		
		String sessionId = LtsSessionHelper.getSessionUid(request);
		List <String> sList = new ArrayList<String>();
		
		
		ModelAndView mav = new ModelAndView(viewName);
		
		switch (form.getFormAction()) {
		case SUBMIT:
			
			ValidationResultDTO validationResult = validateExclusiveItem(request, form);
			if (Status.INVAILD == validationResult.getStatus()) {
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}
			
			validationResult = validateItemAttb(form);
			if (Status.INVAILD == validationResult.getStatus()) {
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}
			optOutSecondDelIddItem(orderCapture, form, locale);
			// update DN status to Normal(N) for DN not selected
	    	ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
									
			orderCapture.setLtsOtherVoiceServiceForm(form);
			break;
		case SEARCH_DN:
			
			clear2ndDel(orderCapture, form);
			mav.addObject(commandName, form);
			
			form.setNew2ndDelDn(StringUtils.isNotEmpty(form.getNew2ndDelDn()) ? form.getNew2ndDelDn().trim() : "");
			String searchSrvNum = StringUtils.leftPad(form.getNew2ndDelDn(), 12, '0') ;
			
			if (StringUtils.equals(searchSrvNum, orderCapture
					.getLtsServiceProfile().getSrvNum())
					|| StringUtils.equals(searchSrvNum, orderCapture
							.getLtsServiceProfile().getDuplexNum())) {
				form.setSecondDelMsg(messageSource.getMessage("lts.ltsOtherVoiSrv.invalidSrvNum", new Object[] {}, this.locale));
				form.setSecondDelProfileValid(false);
				return mav;
			}
			if (StringUtils.equals(form.getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_WORKING) ) {
				ServiceDetailProfileLtsDTO secondDelProfile = ltsProfileService.retrieve2ndDelServiceProfile(form.getNew2ndDelDn(), bomSalesUser.getUsername());
				validate2ndDel(orderCapture, secondDelProfile, form, locale);
			} else if (StringUtils.equals(form.getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_RESERVED) ) {
	
				String serviceInventSts = ltsProfileService.getServiceInventoryStatus(searchSrvNum, LtsConstant.SERVICE_TYPE_TEL);
				
				if (!StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, serviceInventSts) ) {
						form.setSecondDelMsg(messageSource.getMessage("lts.ltsOtherVoiSrv.resDN", new Object[] {}, this.locale)) ;
						form.setSecondDelProfileValid(false);
				}
				else {
					if(serviceProfileLtsService.getPendingOrder(searchSrvNum, LtsConstant.SERVICE_TYPE_TEL) != null){
						form.setSecondDelMsg(messageSource.getMessage("lts.ltsOtherVoiSrv.selectedDN", new Object[] {}, this.locale)) ;
						form.setSecondDelProfileValid(false);
						return mav;
		    		}else{
						form.setSecondDelMsg(messageSource.getMessage("lts.ltsOtherVoiSrv.retreive", new Object[] {}, this.locale)) ;
						form.setSecondDelProfileValid(true);
						orderCapture.setCreate2ndDelByReserveDnInd(true);
		    		}
				}
			}
			filterIddExclusiveItem(request, orderCapture, form);
			handleNew2ndDelFreeVas(orderCapture, form, locale);
			return mav;
		case CLEAR_DN:
			form.setNew2ndDelDn(null);
			form.setNew2ndDelSrvStatus(null);
			clear2ndDel(orderCapture, form);
			form.setNumSelectionList(null);
			form.setDnPoolSelectionMethod(false);
			ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
			mav.addObject(commandName, form);
			return mav;
		case CUST_VERIFY:
			form.setSecondDelDocNum(StringUtils.isNotEmpty(form.getSecondDelDocNum()) ? form.getSecondDelDocNum().trim() : "");
			validate2ndDel(orderCapture, orderCapture.getSecondDelServiceProfile(), form, locale);
			filterIddExclusiveItem(request, orderCapture, form);
			handleNew2ndDelFreeVas(orderCapture, form, locale);
			mav.addObject(commandName, form);
			return mav;
		
		case SEARCH_BY_NO_CRITERIA:
        case SEARCH_BY_FIRST_8_DIGITS:
        case SEARCH_BY_LAST_3_DIGITS: 
        	
        	ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
        	dnList = searchDnFromDnPool(form, sessionId);
        	form.setNumSelectionList(dnList);
        	if (dnList.size() == 0) {
    			errors.rejectValue("searchErrMsg", "lts.acq.dnSearch.noDnFound");
    			mav.addAllObjects(errors.getModel());
			}
        	mav.addObject(commandName, form);        	
			return mav;	
			
        case LOCK_NUMBER:
        	clear2ndDel(orderCapture, form);
        	if (form.getNumSelectionStringList()!=null&&form.getNumSelectionStringList().size()==1) {
        		if (StringUtils.isNotEmpty(form.getNew2ndDelDn())) {            		
            		sList.add(StringUtils.leftPad(form.getNew2ndDelDn(), 12, "0"));
            		ltsAcqDnPoolService.updDnStatusToLock(sList, sessionId);
        		}
        		if(serviceProfileLtsService.getPendingOrder(form.getNumSelectionStringList().get(0).substring(4,12), LtsConstant.SERVICE_TYPE_TEL) != null){
					errors.rejectValue("searchErrMsg", "lts.acq.dnSearch.dnNotUsable");
	    			mav.addAllObjects(errors.getModel());
	    			mav.addObject(commandName, form);
	    			return mav;
				}else{
	        		form.setNew2ndDelDn(form.getNumSelectionStringList().get(0).substring(4,12));
	        		ltsAcqDnPoolService.updDnStatusToConfirm(
	        				StringUtils.leftPad(form.getNew2ndDelDn(), 12, "0"), sessionId);
					form.setSecondDelProfileValid(true);
				}
        	}
			form.setNumSelectionList(getNumSelectionList(sessionId));
			form.setDnPoolSelectionMethod(true);
			handleNew2ndDelFreeVas(orderCapture, form, locale);
        	mav.addObject(commandName, form);
			return mav;
		default:
			break;
		}

		return new ModelAndView(new RedirectView(nextView));
	}
	
	private void optOutSecondDelIddItem(OrderCaptureDTO orderCapture, LtsOtherVoiceServiceFormDTO form, String locale) {
		if (!form.getCreate2ndDel() || !form.isSecondDelOptOutIdd()) {
			return;
		}
		
		List<ItemDetailDTO> secondDelOptOutIddItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_IDD_OUT, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		for (ItemDetailDTO itemDetail : secondDelOptOutIddItemList) {
			itemDetail.setSelected(true);
		}
		
		form.setSecondDelOptOutIddItemList(secondDelOptOutIddItemList);
	}
	
	private void handleDuplex2ndDelFreeVas(OrderCaptureDTO orderCapture, LtsOtherVoiceServiceFormDTO form, String locale) {
		if (StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getDuplexNum())) {
			form.setDuplexProfile(true);
			String[] freeVasItemIds = offerItemService.generateFreeVasItem(orderCapture.getLtsServiceProfile().getItemDetails(), 
					LtsConstant.DUMMY_SECOND_DEL_DEVICE_TYPE, null, orderCapture.getBasketChannelId(), orderCapture.getOrderType());
			if (ArrayUtils.isNotEmpty(freeVasItemIds)) {
				List<ItemDetailDTO> secondDelFreeVasItemList = 
					ltsOfferService.getItem(freeVasItemIds, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, true);
				form.setDuplex2ndDelAutoInVasList(secondDelFreeVasItemList);
			}
		}
	}
	
	private void handleNew2ndDelFreeVas(OrderCaptureDTO orderCapture, LtsOtherVoiceServiceFormDTO form, String locale) {
		
		List<ItemDetailDTO> new2ndDelAutoOutItemList = new ArrayList<ItemDetailDTO>(); 
		if (orderCapture.getSecondDelServiceProfile() != null) {
			if (ArrayUtils.isEmpty(orderCapture.getSecondDelServiceProfile().getItemDetails())) {
				return;
			}
			String[] freeVasItemIds = offerItemService.generateFreeVasItem(
					orderCapture.getSecondDelServiceProfile().getItemDetails(),
					LtsConstant.DUMMY_SECOND_DEL_DEVICE_TYPE, null,
					orderCapture.getBasketChannelId(),
					orderCapture.getOrderType());
			
			
			if (ArrayUtils.isNotEmpty(freeVasItemIds)) {
				List<ItemDetailDTO> new2ndDelFreeVasItemList = 
					ltsOfferService.getItem(freeVasItemIds, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, true);
				form.setNew2ndDelAutoChangeTpList(new2ndDelFreeVasItemList);
			}
			

			for (ItemDetailProfileLtsDTO profileItemDetail : orderCapture.getSecondDelServiceProfile().getItemDetails()) {
				if (StringUtils.isEmpty(profileItemDetail.getItemId())
						|| !StringUtils.equals("Y", profileItemDetail.getTermExistCdInd())) {
					continue;
				}
				
				List<ItemDetailDTO> autoOutItemList = ltsOfferService.getItem(
						new String[] { profileItemDetail.getItemId() },
						LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, true);

				if (autoOutItemList != null && !autoOutItemList.isEmpty()) {
					new2ndDelAutoOutItemList.addAll(autoOutItemList);
				}
			}
		}
		else if (orderCapture.getCreate2ndDelByReserveDnInd() != null
				&& orderCapture.getCreate2ndDelByReserveDnInd().booleanValue()) {
			List<ItemDetailDTO> new2ndDelFreeVasItemList = 
					ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_FREE, locale, true, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
			form.setNew2ndDelAutoChangeTpList(new2ndDelFreeVasItemList);
		}
	}
	
	
	private ValidationResultDTO validateItemAttb(LtsOtherVoiceServiceFormDTO form) {
		List<ItemDetailDTO> itemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (form.getSecondDelHotVasItemList() != null && !form.getSecondDelHotVasItemList().isEmpty()) {
			itemDetailList.addAll(form.getSecondDelHotVasItemList());
		}
		
		if (form.getSecondDelOtherVasItemList() != null && !form.getSecondDelOtherVasItemList().isEmpty()) {
			itemDetailList.addAll(form.getSecondDelOtherVasItemList());
		}
		
		if (form.getNew2ndDelAutoChangeTpList() != null && !form.getNew2ndDelAutoChangeTpList().isEmpty()) {
			itemDetailList.addAll(form.getNew2ndDelAutoChangeTpList());
		}
		
		if (form.getDuplex2ndDelAutoInVasList() != null && !form.getDuplex2ndDelAutoInVasList().isEmpty()) {
			itemDetailList.addAll(form.getDuplex2ndDelAutoInVasList());
		}
		
		return ltsOfferService.validateItemAttb(itemDetailList);
		
	}
	
	private ValidationResultDTO validateExclusiveItem(HttpServletRequest request, LtsOtherVoiceServiceFormDTO form) {
		
		List<ItemDetailDTO> validateItemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (form.getDuplex2ndDelAutoInVasList() != null && !form.getDuplex2ndDelAutoInVasList().isEmpty()) {
			validateItemDetailList.addAll(form.getDuplex2ndDelAutoInVasList());
		}
		if (form.getNew2ndDelAutoChangeTpList() != null && !form.getNew2ndDelAutoChangeTpList().isEmpty()) {
			validateItemDetailList.addAll(form.getNew2ndDelAutoChangeTpList());
		}
		if (form.getNew2ndDelExistingTpList() != null && !form.getNew2ndDelExistingTpList().isEmpty()) {
			validateItemDetailList.addAll(form.getNew2ndDelExistingTpList());
		}
		if (form.getSecondDelHotVasItemList() != null && !form.getSecondDelHotVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getSecondDelHotVasItemList());
		}
		if (form.getSecondDelOtherVasItemList() != null && !form.getSecondDelOtherVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getSecondDelOtherVasItemList());
		}
		if (form.getSecondDelStandaloneVasItemList() != null && !form.getSecondDelStandaloneVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getSecondDelStandaloneVasItemList());
		}
		 return ltsOfferService.validateExclusiveItem(validateItemDetailList, validateItemDetailList,
				RequestContextUtils.getLocale(request).toString());
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		//List<ItemDetailDTO> secondDelHotVasItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_HOT, locale, true, true, orderCapture.getBasketChannelId());
		//List<ItemDetailDTO> secondDelOtherVasItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_OTHER, locale, true, true, orderCapture.getBasketChannelId());
		//List<ItemDetailDTO> secondDelStandaloneVasItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_STANDALONE, locale, true, true, orderCapture.getBasketChannelId());
		//List<ItemDetailDTO> secondDelIddItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_IDD, locale, true, orderCapture.getBasketChannelId());
		
		List<ItemDetailDTO> secondDelHotVasItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_HOT, locale, true, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		List<ItemDetailDTO> secondDelOtherVasItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_OTHER, locale, true, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		List<ItemDetailDTO> secondDelStandaloneVasItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_STANDALONE, locale, true, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		List<ItemDetailDTO> secondDelIddItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_IDD, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		LtsOtherVoiceServiceFormDTO form = new LtsOtherVoiceServiceFormDTO();
		
		String sessionId = LtsSessionHelper.getSessionUid(request);	

		form.setNumSelectionList(getNumSelectionList(sessionId));
		
		//##handle DN change when creating new second del
		form.setDnChange(false);
		if(orderCapture.getLtsMiscellaneousForm().isDnChange()){
			form.setDnChange(true);
			if(orderCapture.getLtsDnChangeForm() != null)
				if(orderCapture.getLtsDnChangeForm().getDnSource() == "D"){
				    form.setDnChangeNumX(orderCapture.getLtsDnChangeForm().getConfirmedDn());
			    }else if(orderCapture.getLtsDnChangeForm().getDnSource() == "R"){
			    	form.setDnChangeNumX(orderCapture.getLtsDnChangeForm().getReservedSrvNum());
			    }
			
			if(orderCapture.getLtsDnChangeDuplexForm() != null){
				if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "D"){
					form.setDnChangeNumY(orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn());
				}else if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "R"){
					form.setDnChangeNumY(orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum());
				}
			}		
		};
		form.setSecondDelHotVasItemList(secondDelHotVasItemList);
		form.setSecondDelOtherVasItemList(secondDelOtherVasItemList);
		form.setSecondDelStandaloneVasItemList(secondDelStandaloneVasItemList);
		form.setSecondDelIddItemList(secondDelIddItemList);
		handleRenewalDuplex(orderCapture, form);
		handleDuplex2ndDelFreeVas(orderCapture, form, locale);
		handleNew2ndDelFreeVas(orderCapture, form, locale);
		filterIddExclusiveItem(request, orderCapture, form);
		addSecondDelCancelVasItemList(orderCapture.getSecondDelServiceProfile(), form);
		
		if(!ltsOfferService.isSelectedNewDeviceFieldService(orderCapture)){
			form.setOnlyAllowWorking(true);
		}
		
		return form;
	}
	
	private void handleRenewalDuplex(OrderCaptureDTO orderCapture, LtsOtherVoiceServiceFormDTO form ) {
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return;
		}
		
		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getDuplexNum())) {
			return;
		}
		
		// Change from duplex package term plan to non duplex basket. 
		ItemDetailProfileLtsDTO[] profileItems = orderCapture.getLtsServiceProfile().getItemDetails();
		if (ArrayUtils.isNotEmpty(profileItems)) {
			for (ItemDetailProfileLtsDTO profileItem : profileItems) {
				if (!profileItem.isDuplexItem()) {
					continue;
				}
				if (LtsConstant.ITEM_TYPE_SERVICE.equals(profileItem.getItemType())) {
					BasketDetailDTO selectedBasket = orderCapture.getSelectedBasket();
					if (selectedBasket != null 
							&& StringUtils.equalsIgnoreCase("N", StringUtils.defaultIfEmpty(selectedBasket.getDuplexInd(), "N") )) {
						form.setRemoveRenewalDuplex(true);
						return;
					}
				}
			}
		}
		
		// Manual cancel profile duplex VAS 7DUR.
		List<ItemDetailProfileLtsDTO> manualCancelVasItemList = orderCapture.getLtsBasketVasDetailForm().getProfileExistingVasItemList();
		if (manualCancelVasItemList != null && !manualCancelVasItemList.isEmpty()) {
			for(ItemDetailProfileLtsDTO manualCancelVasItem : manualCancelVasItemList) {
				if (manualCancelVasItem.getItemDetail() != null 
						&& manualCancelVasItem.getItemDetail().isSelected() 
						&& manualCancelVasItem.isDuplexItem()) {
					form.setRemoveRenewalDuplex(true);
					return;
				}
			}
		}
		form.setRetainRenewalDuplex(true);
	}
	
	private void clear2ndDel(OrderCaptureDTO orderCapture, LtsOtherVoiceServiceFormDTO form) {
		resetSearch2ndDelForm(orderCapture, form);
		orderCapture.setSecondDelServiceProfile(null);
		orderCapture.setCreate2ndDelByReserveDnInd(null);
	}
	
	private void resetSearch2ndDelForm(OrderCaptureDTO orderCapture, LtsOtherVoiceServiceFormDTO form) {
		form.setSecondDelBaCaChange(null);
		form.setSecondDelDiffAddress(false);
		form.setSecondDelDiffCust(false);
		form.setSecondDelDocNum(null);
		form.setSecondDelDocType(null);
		form.setSecondDelDummyDoc(null);
		form.setSecondDelEr(null);
		form.setSecondDelMsg(null);
		form.setSecondDelThirdPartyAppl(null);
		form.setSecondDelProfileValid(false);
		form.setNew2ndDelAutoChangeTpList(null);
		form.setNew2ndDelAutoOutTpList(null);
		form.setNew2ndDelExistingTpList(null);
		form.setSecondDelRedeemPremium(null);
		form.setShowSecondDelRedeemPremium(false);
		form.setSecondDelCancelVasItemList(null);
	}
	
	private void validate2ndDel(OrderCaptureDTO orderCapture,
			ServiceDetailProfileLtsDTO secondDelProfile,
			LtsOtherVoiceServiceFormDTO form, String locale) {

		if (secondDelProfile == null) {
			form.setSecondDelMsg(messageSource.getMessage("lts.ltsOtherVoiSrv.srvNumNotFound", new Object[] {}, this.locale));
			form.setSecondDelProfileValid(false);
			return;
		}
		
		if (!verifyCustomer(orderCapture, secondDelProfile.getPrimaryCust(), form)) {
			orderCapture.setSecondDelServiceProfile(secondDelProfile);
			return;
		}
		
		if (!validateServiceProfile(secondDelProfile, form)) {
			return;
		}
		
		if (ArrayUtils.isNotEmpty(secondDelProfile.getItemDetails())) {
			for (ItemDetailProfileLtsDTO itemDetailProfileLts : secondDelProfile
					.getItemDetails()) {
				
				if (StringUtils.isNotEmpty(itemDetailProfileLts.getItemId())) {
					ItemDetailDTO profileItemDetail = ltsOfferService.getTpVasItemDetail(itemDetailProfileLts.getItemId(), locale);
					if (profileItemDetail != null ) {
						itemDetailProfileLts.setItemDetail(profileItemDetail);
						if (StringUtils.equals("Y", profileItemDetail.getIsPremiumTp()) && !LtsSbOrderHelper.isProfileItemExpired(itemDetailProfileLts)) {
							form.setShowSecondDelRedeemPremium(true);		
						}
					}
				}
			}
		}

		addSecondDelCancelVasItemList(secondDelProfile, form);
		
		if (!verifyProfileAddress(orderCapture, secondDelProfile.getAddress(), form)) {
			orderCapture.setSecondDelServiceProfile(secondDelProfile);
			return;
		}
		
		form.setSecondDelMsg(messageSource.getMessage("lts.ltsOtherVoiSrv.retreive", new Object[] {}, this.locale));
		form.setSecondDelProfileValid(true);
		orderCapture.setSecondDelServiceProfile(secondDelProfile);
	}
	
	private void addSecondDelCancelVasItemList(ServiceDetailProfileLtsDTO secondDelProfile , LtsOtherVoiceServiceFormDTO form) {
		
		if (secondDelProfile == null
				|| !StringUtils.equals(LtsConstant.INVENT_STS_WORKING, secondDelProfile.getInventStatus())
				|| ArrayUtils.isEmpty(secondDelProfile.getItemDetails())) {
			return;
		}
		
		List<ItemDetailProfileLtsDTO> secondDelCancelVasItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		
		for (ItemDetailProfileLtsDTO itemDetailProfile : secondDelProfile.getItemDetails()) {
			if (StringUtils.equals(itemDetailProfile.getItemType(), LtsConstant.ITEM_TYPE_VAS)
					&& StringUtils.isEmpty(itemDetailProfile.getChangeToItemId())
					&& !StringUtils.equals(itemDetailProfile.getTermExistCdInd(), "Y")) {
				try {
					secondDelCancelVasItemList.add((ItemDetailProfileLtsDTO)BeanUtils.cloneBean(itemDetailProfile));
				}
				catch (Exception e) {
					throw new AppRuntimeException(e);
				}
			}
		}

		if (!secondDelCancelVasItemList.isEmpty()) {
			form.setSecondDelCancelVasItemList(secondDelCancelVasItemList);	
		}
	}
	
	
	private boolean verifyCustomer(OrderCaptureDTO orderCapture, CustomerDetailProfileLtsDTO secondDelCust,
			LtsOtherVoiceServiceFormDTO form) {
		
		String errorMsg = null;
		
		switch (form.getFormAction()) {
		case SEARCH_DN:
			if(!isUnderSameCust(orderCapture.getLtsServiceProfile().getPrimaryCust(), secondDelCust)) {
				errorMsg = messageSource.getMessage("lts.ltsOtherVoiSrv.srvNumDiffCust", new Object[] {}, this.locale);
			}
			break;
		case CUST_VERIFY:
			 if (!isUnderSameCust(form, secondDelCust)) {
				errorMsg = messageSource.getMessage("lts.ltsOtherVoiSrv.docTypeNotMatch", new Object[] {}, this.locale);
			 }
			break;
		default:
			break;
		}

		if (StringUtils.isNotEmpty(errorMsg)) {
			form.setSecondDelMsg(errorMsg);
			form.setSecondDelDiffCust(true);
			return false;
		}
		
		if (Action.SEARCH_DN == form.getFormAction()) {
			form.setSecondDelDummyDoc(orderCapture.getLtsCustomerInquiryForm().getDummyDoc());
			form.setSecondDelThirdPartyAppl(orderCapture.getLtsCustomerInquiryForm().getThirdPartyApplication());
		}
		
		form.setSecondDelDiffCust(false);
		return true;
	}
	
	private boolean isAllowConfirmSameIa(OrderCaptureDTO orderCapture, AddressDetailProfileLtsDTO secondDelProfileAddress) {
		String targetDistrictCode = orderCapture.getLtsAddressRolloutForm().isExternalRelocate() ? orderCapture.getLtsAddressRolloutForm().getDistrictCode() :
			orderCapture.getLtsServiceProfile().getAddress().getDistrictCd();
		
		String targetAreaCode = orderCapture.getLtsAddressRolloutForm().isExternalRelocate() ? orderCapture.getLtsAddressRolloutForm().getAreaCode() :
			orderCapture.getLtsServiceProfile().getAddress().getAreaCd();
		
		if (StringUtils.equalsIgnoreCase(targetDistrictCode,
				secondDelProfileAddress.getDistrictCd())
				&& StringUtils.equalsIgnoreCase(targetAreaCode,
						secondDelProfileAddress.getAreaCd())) {
			return true;
		}
		return false;
	}
	
	private boolean verifyProfileAddress(OrderCaptureDTO orderCapture, AddressDetailProfileLtsDTO secondDelProfileAddress,
			LtsOtherVoiceServiceFormDTO form) {

		AddressRolloutDTO targetAddressRollout = orderCapture.getNewAddressRollout();
		if (!isUnderSameAddress(targetAddressRollout, secondDelProfileAddress)) {
			form.setSecondDelMsg(messageSource.getMessage("lts.ltsOtherVoiSrv.srvNumDiffIA", new Object[] {}, this.locale));
			form.setSecondDelDiffAddress(true);
			form.setAllowSecondDelConfirmSameAddress(isAllowConfirmSameIa(orderCapture, secondDelProfileAddress));
			return false;
		}
		form.setSecondDelDiffAddress(false);
		return true;
	}
	
	private boolean validateServiceProfile(ServiceDetailProfileLtsDTO secondDelProfile, LtsOtherVoiceServiceFormDTO form) {
		
		String errorMsg = null;
		
		if (!StringUtils.equals("DEL", secondDelProfile.getDatCd())) {
			errorMsg = messageSource.getMessage("lts.ltsOtherVoiSrv.notTelDel", new Object[] {}, this.locale);
		}
		else if (!StringUtils.equals(secondDelProfile.getInventStatus(), form.getNew2ndDelSrvStatus())) {
			errorMsg = messageSource.getMessage("lts.ltsOtherVoiSrv.invSrvLineStatus", new Object[] {}, this.locale);
		}
		else if (!StringUtils.equals(secondDelProfile.getTariff(), "R")) {
			errorMsg = messageSource.getMessage("lts.ltsOtherVoiSrv.tariffNotResidental", new Object[] {}, this.locale);
		}
		
		if (StringUtils.isNotEmpty(errorMsg)) {
			form.setSecondDelMsg(errorMsg);
			form.setSecondDelProfileValid(false);
			return false;
		}
		form.setSecondDelProfileValid(true);
		return true;
	}
	
	private boolean isUnderSameAddress(AddressRolloutDTO targetIa,
			AddressDetailProfileLtsDTO profileIa) {
		if (!StringUtils.equalsIgnoreCase(targetIa.getSrvBdary(), profileIa.getSrvBdry())) {
			return false;
		}
		if (!StringUtils.equalsIgnoreCase(targetIa.getFloor(), profileIa.getFloorNum())) {
			return false;
		}
		if (!StringUtils.equalsIgnoreCase(targetIa.getFlat(), profileIa.getUnitNum())) {
			return false;
		}
		return true;

	}
	
	private boolean isUnderSameCust(CustomerDetailProfileLtsDTO profileCust1,
			CustomerDetailProfileLtsDTO profileCust2) {

		if (!StringUtils.equals(profileCust1.getDocType(),
				profileCust2.getDocType())) {
			return false;
		}
		if (!StringUtils.equals(profileCust1.getDocNum(),
				profileCust2.getDocNum())) {
			return false;
		}
		return true;
	}
	
	private boolean isUnderSameCust(LtsOtherVoiceServiceFormDTO form,
			CustomerDetailProfileLtsDTO profileCust) {
		if (!StringUtils.equals(profileCust.getDocType(),
				form.getSecondDelDocType())) {
			return false;
		}
		if (!StringUtils.equals(profileCust.getDocNum(),
				form.getSecondDelDocNum())) {
			return false;
		}
		return true;
	}
	
	// Search the DN list from DN pool
    private List<LtsAcqNumberSelectionInfoDTO> searchDnFromDnPool(LtsOtherVoiceServiceFormDTO form, String sessionId) {
    	List<String> list = new ArrayList<String>();
    	List<LtsAcqNumberSelectionInfoDTO> result = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
    	LtsAcqNumberSelectionInfoDTO numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
    	switch (form.getFormAction()) {
			case SEARCH_BY_NO_CRITERIA:
		    	list = ltsAcqDnPoolService.searchDnListFromPoolByNoCriteria(sessionId);
		    	break;
			case SEARCH_BY_FIRST_8_DIGITS:
		    	list = ltsAcqDnPoolService.searchDnListFromPoolByFirst8Digits(sessionId, form.getFirst5To8Digits());
		    	break;
			case SEARCH_BY_LAST_3_DIGITS:
		    	list = ltsAcqDnPoolService.searchDnListFromPoolByLast3Digits(sessionId, form.getLast1To3Digits());
		    	break;
			default:
				break;
		}		
    	if (list != null) {
			for (int i=0; i<list.size(); i++) {
				numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
				numberSelectionObj.setSrvNum(list.get(i));
				numberSelectionObj.setDisplaySrvNum(list.get(i).substring(4,12));
				result.add(numberSelectionObj);						
			}
		}    	
		return result;
    }
	
    // Retrieve the DN list from DN pool where status = L(Locked)
    private List<LtsAcqNumberSelectionInfoDTO> getNumSelectionList(String sessionId) {
    	List<String> list = new ArrayList<String>();
    	List<LtsAcqNumberSelectionInfoDTO> result = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
    	LtsAcqNumberSelectionInfoDTO numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
    	list = ltsAcqDnPoolService.getLockedDnList(sessionId);
		if (list != null) {
			for (int i=0; i<list.size(); i++) {
				numberSelectionObj = new LtsAcqNumberSelectionInfoDTO();
				numberSelectionObj.setSrvNum(list.get(i));
				numberSelectionObj.setDisplaySrvNum(list.get(i).substring(4,12));
				result.add(numberSelectionObj);						
			}
		}
		return result;
    }
    
	private void filterIddExclusiveItem(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsOtherVoiceServiceFormDTO form) {
		List<ItemDetailDTO> itemDetailListA = new ArrayList<ItemDetailDTO>();
		List<ItemDetailDTO> itemDetailListB = new ArrayList<ItemDetailDTO>();
		
		if (orderCapture.getSecondDelServiceProfile() == null) {
			return;
		}
		
		if (ArrayUtils.isNotEmpty(orderCapture.getSecondDelServiceProfile().getItemDetails())) {
			for (ItemDetailProfileLtsDTO profileItem : orderCapture.getSecondDelServiceProfile().getItemDetails()) {
				if ((LtsConstant.ITEM_TYPE_SERVICE.equals(profileItem.getItemType())
						|| LtsConstant.ITEM_TYPE_VAS.equals(profileItem.getItemType()))
							&& profileItem.getItemDetail() != null) {
					itemDetailListA.add(profileItem.getItemDetail());
				}
			}
		}
		
		if (form.getSecondDelIddItemList() != null && !form.getSecondDelIddItemList().isEmpty()) {
			itemDetailListB.addAll(form.getSecondDelIddItemList());
		}
		if (form.getSecondDelOptOutIddItemList() != null && !form.getSecondDelOptOutIddItemList().isEmpty()) {
			itemDetailListB.addAll(form.getSecondDelOptOutIddItemList());
		}
		
		List<ExclusiveItemDetailDTO> exclusiveItemDetailList = ltsOfferService
		.getExclusiveItemDetailList(itemDetailListA, itemDetailListB,
				RequestContextUtils.getLocale(request).toString(), false);
		
		if (exclusiveItemDetailList == null || exclusiveItemDetailList.isEmpty()) {
			return;
		}
		
		for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {
			form.setSecondDelIddItemList(filterExclusiveItem(form.getSecondDelIddItemList(), exclusiveItemDetail));
			form.setSecondDelOptOutIddItemList(filterExclusiveItem(form.getSecondDelOptOutIddItemList(), exclusiveItemDetail));
		}
	}
	
	private List<ItemDetailDTO> filterExclusiveItem(List<ItemDetailDTO> itemDetailList, ExclusiveItemDetailDTO exclusiveItemDetail) {
		
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return null;
		}
		
		List<ItemDetailDTO> newItemDetailList = new ArrayList<ItemDetailDTO>();
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (StringUtils.equals(exclusiveItemDetail.getItemAId(), itemDetail.getItemId()) ||
					StringUtils.equals(exclusiveItemDetail.getItemBId(), itemDetail.getItemId())) {
				continue;
			}
			newItemDetailList.add(itemDetail);
		}
		return newItemDetailList;
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
