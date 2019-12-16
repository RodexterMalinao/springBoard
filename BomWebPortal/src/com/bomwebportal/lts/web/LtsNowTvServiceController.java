package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.MessageSource;

import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveChannelDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.NowTvServiceDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO.AdditionalTvType;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsNowTvServiceController extends SimpleFormController {
	
	private final String nextView = "ltsbasketvasdetail.html";
	private final String viewName = "ltsnowtvservice";
	private final String commandName = "ltsNowTvServiceCmd";
	
	protected LtsBasketService ltsBasketService;
	protected LtsOfferService ltsOfferService;
	protected LtsCommonService ltsCommonService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsNowTvServiceController() {
		setCommandClass(LtsNowTvServiceFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		if (orderCapture == null
				|| orderCapture.getLtsBasketServiceForm() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if (isSkipNowTvPage(orderCapture)) {
//			LtsNowTvServiceFormDTO form = new LtsNowTvServiceFormDTO();
			orderCapture.setLtsNowTvServiceForm(null);
			return new ModelAndView(new RedirectView(nextView));
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	private boolean isSkipNowTvPage(OrderCaptureDTO orderCapture) {
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return true;
		}
		
		String selectedDeviceType = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		if (StringUtils.equals(LtsConstant.DEVICE_TYPE_EYE3A, selectedDeviceType)) {
			return true;
		}
		
		// Selected FSA was already subscribed mirror plan
		FsaDetailDTO selectedFsaDetail = LtsSbOrderHelper.getSelectedFsa(orderCapture.getLtsModemArrangementForm());
		if (selectedFsaDetail != null && selectedFsaDetail.getFsaProfile() != null) {
			if (StringUtils.equals("Y", selectedFsaDetail.getFsaProfile().getExistMirrorInd())) {
				return true;
			}
		}
		
		ServiceDetailProfileLtsDTO profileService = orderCapture.getLtsServiceProfile();
		
		if (StringUtils.equals("Y", LtsSbOrderHelper.getBundleTvInd(orderCapture, profileService))) {
			return true;
		}
		
		return false;
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		LtsNowTvServiceFormDTO form = (LtsNowTvServiceFormDTO) command;
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		ValidationResultDTO result = validationNowTvService(request, form);
		
		if (Status.INVAILD == result.getStatus()) {
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			mav.addObject("errorMsgList", result.getMessageList());
			return mav;
		}
		
		waiveItem(orderCapture, form, locale);
		orderCapture.setLtsNowTvServiceForm(form);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private void waiveItem(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form, String locale) {
		if (orderCapture.getLtsBasketServiceForm() == null) {
			return;
		}
		
		boolean waiveBBRental = false;
		boolean waiveInstallationFee = false;
		
		if (AdditionalTvType.SD_SPECIAL_CHANNEL == form.getAdditionalTvType() &&
				form.getNowTvSpecItemList() != null && !form.getNowTvSpecItemList().isEmpty()) {
			if (ltsOfferService.isItemSelected(form.getNowTvSpecItemList(), LtsConstant.ITEM_TYPE_NOWTV_SPEC)
					&& !form.isSelectedBasketBundleTv()) {
				waiveBBRental = true;
				waiveInstallationFee = true;
			}
		}
		
		if (AdditionalTvType.SD_PAY_CHANNEL == form.getAdditionalTvType() &&
				form.getNowTvPayItemList() != null && !form.getNowTvPayItemList().isEmpty()) {
			if (ltsOfferService.isItemSelected(form.getNowTvPayItemList(), LtsConstant.ITEM_TYPE_NOWTV_PAY)
					&& !form.isSelectedBasketBundleTv()) {
				waiveBBRental = true;
				waiveInstallationFee = true;
			}
		}
		
		if (AdditionalTvType.MIRROR == form.getAdditionalTvType() &&
				form.getNowTvMirrorItemList() != null && !form.getNowTvMirrorItemList().isEmpty()) {
			if (ltsOfferService.isItemSelected(form.getNowTvMirrorItemList(), LtsConstant.ITEM_TYPE_NOWTV_MIRR)) {
				waiveInstallationFee = true;
			}
		}
		
		if (waiveBBRental && 
				orderCapture.getLtsBasketServiceForm().getBbRentalItemList() != null 
						&& !orderCapture.getLtsBasketServiceForm().getBbRentalItemList().isEmpty()) {
			for (ItemDetailDTO bbRentalItem : orderCapture.getLtsBasketServiceForm().getBbRentalItemList()) {
				bbRentalItem.setSelected(false);
			}
		}
		
		if (waiveInstallationFee &&
				ltsOfferService.isItemSelected(orderCapture.getLtsBasketServiceForm().getInstallItemList(), LtsConstant.ITEM_TYPE_INSTALL)) {
			String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
			List<ItemDetailDTO> installItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_INSTALL_WAIVE, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
			orderCapture.getLtsBasketServiceForm().setInstallItemList(installItemList);
		}
		
	}
	
	private ValidationResultDTO validationNowTvService(HttpServletRequest request, LtsNowTvServiceFormDTO form) {
		
		List<String> errorMsgList = new ArrayList<String>();
		List<ChannelGroupDetailDTO> selectedChannelGroupList = null;
		ChannelDetailDTO[] channelDetail = null;
		ChannelAttbDTO[] channelAttb = null;
		
		if (AdditionalTvType.SD_PAY_CHANNEL == form.getAdditionalTvType()) {
			selectedChannelGroupList = form.getPayChannelGroupList();
		}
		else if (AdditionalTvType.SD_SPECIAL_CHANNEL == form.getAdditionalTvType()) {
			selectedChannelGroupList = form.getSpecChannelGroupList();
		}
		
		if (selectedChannelGroupList != null) {
			List<ExclusiveChannelDetailDTO> exclusiveChannelList = ltsOfferService
			.getExclusiveChannelList(selectedChannelGroupList,
					RequestContextUtils.getLocale(request).toString());
			
			if (exclusiveChannelList != null && !exclusiveChannelList.isEmpty()) {
				for (ExclusiveChannelDetailDTO exclusiveChannel : exclusiveChannelList) {
					errorMsgList.add(messageSource.getMessage("lts.ltsNowTVSrv.cantSelect", new Object[] {exclusiveChannel.getChannelACd(),exclusiveChannel.getChannelADesc(),exclusiveChannel.getChannelBCd(),exclusiveChannel.getChannelBDesc()}, this.locale));
				}
			}
			
			// Channel attributes validation
			for (int i = 0; selectedChannelGroupList != null && i < selectedChannelGroupList.size(); i++) {
				channelDetail = selectedChannelGroupList.get(i).getChannelDetails();
				for (int j = 0; channelDetail != null && j < channelDetail.length; j++){
					if (channelDetail[j].isSelected()){
						channelAttb = channelDetail[j].getChannelAttbs();
						if (channelDetail[j].isSelected() && channelAttb != null){
							for (int k = 0; channelAttb != null && k < channelAttb.length; k++){
								this.validateAttbValue(channelAttb[k], errorMsgList);
							}
						}
					}
				}	
			}
		}
		
		if (form.getNowTvEmailItemList() != null && !form.getNowTvEmailItemList().isEmpty()
				&& !ltsOfferService.isItemSelected(form.getNowTvEmailItemList(), LtsConstant.ITEM_TYPE_TV_EMAIL)
			&& form.getNowTvDeviceItemList() != null && !form.getNowTvDeviceItemList().isEmpty()
				&& !ltsOfferService.isItemSelected(form.getNowTvDeviceItemList(), LtsConstant.ITEM_TYPE_TV_DEVICE)
			&& form.getNowTvPrintItemList() != null && !form.getNowTvPrintItemList().isEmpty()
				&& !ltsOfferService.isItemSelected(form.getNowTvPrintItemList(), LtsConstant.ITEM_TYPE_TV_PRINT)) {
			errorMsgList.add(messageSource.getMessage("lts.ltsNowTVSrv.oneBillingOpt", new Object[] {}, this.locale));
		}

		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	private void validateAttbValue(ChannelAttbDTO channelAttb, List<String> errorMsgList){
		StringBuffer rejMsg = new StringBuffer();
		if  (StringUtils.equalsIgnoreCase(LtsConstant.CHANNEL_ATTB_FORMAT_CHECK, channelAttb.getInputMethod())){
			rejMsg.append("'" + channelAttb.getAttbDesc());
			if (!channelAttb.isSelected()){
				rejMsg.append(messageSource.getMessage("lts.ltsNowTVSrv.required", new Object[] {}, this.locale));
				errorMsgList.add(rejMsg.toString());
			}
		}else{
			ltsCommonService.validateAttbValue(channelAttb, errorMsgList);
		}
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		LtsNowTvServiceFormDTO form = new LtsNowTvServiceFormDTO();
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
		
		List<AdditionalTvType> additionalTvTypeSelectionList = new ArrayList<AdditionalTvType>();
		form.setAdditionalTvTypeSelectionList(additionalTvTypeSelectionList);
		
		setNowTvFree(orderCapture, form, selectedBasketId, locale);
		setBasketBundleTv(orderCapture, form, locale);
		setNowTvBillingOption(orderCapture, form, locale);
		
		if (!form.isSelectedBasketBundleTv()) {
			additionalTvTypeSelectionList.add(AdditionalTvType.NO_ADDITIONAL_TV);
			boolean isMirrorNowTv = setMirrorTv(orderCapture, form, locale);
			
			if (!isMirrorNowTv || form.isAllow2L2B()) {
				setAdditionalNowTv(orderCapture, form, selectedBasketId, locale);	
			}
		}
		
		return form;
	}

	private void setNowTvFree(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form,
			String selectedBasketId, String locale) {
		List<ItemDetailDTO> nowTvFreeItemList = ltsOfferService
				.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_NOWTV_FREE, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		form.setNowTvFreeItemList(nowTvFreeItemList);
	}
	
	
	private void setAllow2L2BMirror(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form) {
		String deviceType = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		String modemArrangment = orderCapture.getModemTechnologyAissgn().getModemArrangment();

		// Only for specify device in lookup
		if (!ltsOfferService.isAllow2L2B(deviceType)) {
			form.setAllow2L2B(false);
			return;
		}
		
		if (StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, modemArrangment)){
			form.setAllow2L2B(true);
		}
		// Special case 1L1B PCD -> PCD + TV & EYE
		else if (StringUtils.equals(LtsConstant.MODEM_TYPE_1L1B, modemArrangment)
				&& (StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_PCD_SDTV,orderCapture.getModemTechnologyAissgn().getNewImsService())
						|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_PCD_HDTV,orderCapture.getModemTechnologyAissgn().getNewImsService()))){
			form.setAllow2L2B(true);
		}
		
	}
	
	private void setAllowPcdMirror (OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form) {
		
		List<FsaDetailDTO> selectedFsaList = null;
		
		if (ModemType.SHARE_EX_FSA == orderCapture.getLtsModemArrangementForm().getModemType()) {
			selectedFsaList = orderCapture.getLtsModemArrangementForm().getExistingFsaList();
		}
		else if (ModemType.SHARE_OTHER_FSA == orderCapture.getLtsModemArrangementForm().getModemType()){
			selectedFsaList = orderCapture.getLtsModemArrangementForm().getOtherFsaList();
		}
		
		if (selectedFsaList == null) {
			return;
		}
		
		
		FsaDetailDTO selectedFsa = null;
		
		for (FsaDetailDTO fsaDetail : selectedFsaList) {
			if (fsaDetail.isSelected()) {
				selectedFsa = fsaDetail;
				break;
			}
		}
		
		if (FsaServiceType.PCD != selectedFsa.getExistingService()) {
			return;
		}

		List<FsaDetailDTO> mirrorTvFsaList = new ArrayList<FsaDetailDTO>();
		// PCD + Same address + contain tv -> allow PCD Mirror fsa		
		for (FsaDetailDTO fsaDetail : selectedFsaList) {
			if (!fsaDetail.isSelected()
					&& isContainTV(fsaDetail.getExistingService())
					&& isSameIA(selectedFsa.getAddressDetail(), fsaDetail.getAddressDetail())) {
				mirrorTvFsaList.add(fsaDetail);
			}
		}

		if (!mirrorTvFsaList.isEmpty()){
			form.setMirrorTvFsaList(mirrorTvFsaList);
			form.setAllowPcdMirror(true);
		}
	}
	
	private boolean setMirrorTv(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form, String locale) {

		NowTvServiceDetailDTO nowTvServiceProfile = LtsSbOrderHelper.getNowTvServiceProfile(orderCapture);
		boolean isExistingNowTvCust = nowTvServiceProfile != null;
		form.setExistingNowTvCust(isExistingNowTvCust);

		// Check if allow NOWTV Mirror by modem type and selected new now TV service 
		boolean isNewNowTvCust = LtsSbOrderHelper.isNewNowTvService(orderCapture); 
		
		List<ItemDetailDTO> nowTvMirrorItemList = null;
		List<ItemDetailDTO> defaultMirrorItemList = null;
		
		// allow 2L2B nowTV customer subscrible 1/2/3 TV & Additonal channel
		setAllow2L2BMirror(orderCapture, form);
		setAllowPcdMirror(orderCapture, form);
		
		if (isExistingNowTvCust || isNewNowTvCust || form.isAllow2L2B() || form.isAllowPcdMirror()) {
			
			String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();

			nowTvMirrorItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_NOWTV_MIRR, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
			
			if (isExistingNowTvCust) {
				
				if (!StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, orderCapture.getModemTechnologyAissgn().getModemArrangment())) {
					form.setShowDateOfBirth(false);
				}
				if (!form.isAllow2L2B()) {
					form.setConfirmAvAdultChannel(nowTvServiceProfile.isReceiveAdultContent());
				}
				//Get NOWTV-MIRR item list by arpu in profile
				defaultMirrorItemList = ltsOfferService.getNowTvMirrItemList(LtsConstant.ITEM_TYPE_NOWTV_MIRR, 
						locale, true, String.valueOf(nowTvServiceProfile.getArpu()), orderCapture.getBasketChannelId());
			}
			
			if (nowTvMirrorItemList != null && !nowTvMirrorItemList.isEmpty()) {
				if (defaultMirrorItemList != null && !defaultMirrorItemList.isEmpty()) {
					for (ItemDetailDTO itemDetail : nowTvMirrorItemList) {
						for (ItemDetailDTO defaultMirrorItem : defaultMirrorItemList) {
							if (StringUtils.equals(defaultMirrorItem.getItemId(), itemDetail.getItemId())) {
								itemDetail.setSelected(true);
							}	
							
						}
					}
				}
				form.getAdditionalTvTypeSelectionList().add(AdditionalTvType.MIRROR);
				form.setNowTvMirrorItemList(nowTvMirrorItemList);
				return true;
			}
		}
		return false;
	}
	
	private void setBasketBundleTv(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form, String locale) {
		List<ItemSetDetailDTO> contItemSetList = orderCapture.getLtsBasketServiceForm().getContItemSetList();

		List<ItemDetailDTO> nowTvSpecItemList = new ArrayList<ItemDetailDTO>();
		List<ItemDetailDTO> nowTvPayItemList = new ArrayList<ItemDetailDTO>();
		
		boolean isBasketBundleTvSelected = false;
		
		for (int i=0; contItemSetList != null && i < contItemSetList.size(); i++) {
			ItemDetailDTO[] itemDetails = contItemSetList.get(i).getItemDetails();
			if (ArrayUtils.isEmpty(itemDetails)) {
				continue;
			}
			
			for (ItemDetailDTO itemDetail : itemDetails) {
				if (itemDetail.isSelected()) {
					if (StringUtils.equals(LtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
						itemDetail.setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
						nowTvSpecItemList.add(itemDetail);
						isBasketBundleTvSelected = true;
						form.setAdditionalTvType(AdditionalTvType.SD_SPECIAL_CHANNEL);
					}
					if (StringUtils.equals(LtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType())) {
						itemDetail.setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
						nowTvPayItemList.add(itemDetail);
						isBasketBundleTvSelected = true;
						form.setAdditionalTvType(AdditionalTvType.SD_PAY_CHANNEL);
					}
				}
			}
		}

		form.setSelectedBasketBundleTv(isBasketBundleTvSelected);

		if (!nowTvPayItemList.isEmpty()) {
			form.getAdditionalTvTypeSelectionList().add(AdditionalTvType.SD_PAY_CHANNEL);
			form.setNowTvPayItemList(nowTvPayItemList);
			setTvChannelGroup(orderCapture, form, LtsConstant.ITEM_TYPE_NOWTV_PAY, locale);
		}
		if (!nowTvSpecItemList.isEmpty()) {
			form.getAdditionalTvTypeSelectionList().add(AdditionalTvType.SD_SPECIAL_CHANNEL);
			form.setNowTvSpecItemList(nowTvSpecItemList);
			setTvChannelGroup(orderCapture, form, LtsConstant.ITEM_TYPE_NOWTV_SPEC, locale);
		}
		
	}
	
	private void setAdditionalNowTv(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form, String basketId, String locale) {
		
		List<AdditionalTvType> additionalTvTypeSelectionList = form.getAdditionalTvTypeSelectionList();
		List<ItemDetailDTO> nowTvCeleItemList  = ltsOfferService.getBasketNowTvItemList(basketId, LtsConstant.ITEM_TYPE_NOWTV_CELE, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		List<ItemDetailDTO> nowTvSportItemList = ltsOfferService.getBasketNowTvItemList(basketId, LtsConstant.ITEM_TYPE_NOWTV_SPT, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		List<ItemDetailDTO> nowTvSpecItemList = ltsOfferService.getBasketNowTvItemList(basketId, LtsConstant.ITEM_TYPE_NOWTV_SPEC, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		List<ItemDetailDTO> nowTvPayItemList = ltsOfferService.getBasketNowTvItemList(basketId, LtsConstant.ITEM_TYPE_NOWTV_PAY, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());

		if (nowTvSpecItemList != null && !nowTvSpecItemList.isEmpty()) {
			form.setNowTvSpecItemList(nowTvSpecItemList);
			setTvChannelGroup(orderCapture, form, LtsConstant.ITEM_TYPE_NOWTV_SPEC, locale);
			additionalTvTypeSelectionList.add(AdditionalTvType.SD_SPECIAL_CHANNEL);
		}
		
		if (nowTvPayItemList != null && !nowTvPayItemList.isEmpty()) {
			form.setNowTvPayItemList(nowTvPayItemList);
			setTvChannelGroup(orderCapture, form, LtsConstant.ITEM_TYPE_NOWTV_PAY, locale);
			additionalTvTypeSelectionList.add(AdditionalTvType.SD_PAY_CHANNEL);
		}
		
		if (nowTvSportItemList != null && !nowTvSportItemList.isEmpty()) {
			form.setNowTvSportItemList(nowTvSportItemList);
			additionalTvTypeSelectionList.add(AdditionalTvType.ADDITIONAL_CHANNELS);	
		}
		
		if (nowTvCeleItemList != null && !nowTvCeleItemList.isEmpty()) {
			form.setNowTvCeleItemList(nowTvCeleItemList);
		}
	}
	
	
	private void setTvChannelGroup(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form, String itemType, String locale) {
		
		String selectedDeviceType = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		
		List<ChannelGroupDetailDTO> channelGroupList = ltsOfferService.getChannelGroupList(itemType, selectedDeviceType, locale, true);
		
		if (StringUtils.equals(itemType, LtsConstant.ITEM_TYPE_NOWTV_SPEC)) {
			form.setSpecChannelGroupList(channelGroupList);
		}
		else if (StringUtils.equals(itemType, LtsConstant.ITEM_TYPE_NOWTV_PAY)) {
			form.setPayChannelGroupList(channelGroupList);
		}
		
	}
	
	private void setNowTvBillingOption(OrderCaptureDTO orderCapture, LtsNowTvServiceFormDTO form, String locale) {
		
		if (!(StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, orderCapture.getModemTechnologyAissgn().getModemArrangment())
				|| StringUtils.equals(LtsConstant.MODEM_TYPE_1L1B, orderCapture.getModemTechnologyAissgn().getModemArrangment()))) {
			return;
		}
		
		// Split NOWTV-VIEW into TV-EMAIL, TV-PRINT, TV-DEVICE
		List<ItemDetailDTO> nowTvEmailItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_TV_EMAIL, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		
		for (int i=0; nowTvEmailItemList != null && i < nowTvEmailItemList.size(); i++) {
			ltsOfferService.getItemAttb(nowTvEmailItemList.get(i));
		}
		form.setNowTvEmailItemList(nowTvEmailItemList);

		List<ItemDetailDTO> nowTvPrintItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_TV_PRINT, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		
		for (int i=0; nowTvPrintItemList != null && i < nowTvPrintItemList.size(); i++) {
			ltsOfferService.getItemAttb(nowTvPrintItemList.get(i));
		}
		form.setNowTvPrintItemList(nowTvPrintItemList);
		
		List<ItemDetailDTO> nowTvDeviceItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_TV_DEVICE, locale, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
		
		for (int i=0; nowTvDeviceItemList != null && i < nowTvDeviceItemList.size(); i++) {
			ltsOfferService.getItemAttb(nowTvDeviceItemList.get(i));
		}
		form.setNowTvDeviceItemList(nowTvDeviceItemList);
	}
	
	public boolean isSameIA(AddressDetailProfileLtsDTO addrA, AddressDetailProfileLtsDTO addrB){
		if (StringUtils.equalsIgnoreCase(addrA.getUnitNum(), addrB.getUnitNum())
				&& StringUtils.equalsIgnoreCase(addrA.getFloorNum(), addrB.getFloorNum())
				&& StringUtils.equalsIgnoreCase(addrA.getSrvBdry(), addrB.getSrvBdry())){
			return true;
		}
		return false;
	}
	
	public boolean isContainTV(FsaServiceType srvType){ 
		if  (FsaServiceType.PCD_HDTV == srvType
				|| FsaServiceType.HDTV == srvType
				|| FsaServiceType.SDTV == srvType
				|| FsaServiceType.PCD_SDTV == srvType){
			return true;
		}
		return false;
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