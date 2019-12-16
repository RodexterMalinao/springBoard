package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemAttbInfoDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.PcdOrderCheckAttrDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO.ItemCriteriaBuilder;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.service.order.SbOrderInfoLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.pccw.springboard.svc.server.dto.CampaignDTO;

public class LtsAcqBasketVasDetailController extends SimpleFormController {

	private final String viewName = "lts/acq/ltsacqbasketvasdetail";
	private final String nextView = "ltsacqpremiumselection.html";
	private final String commandName = "ltsAcqBasketVasDetailCmd";

	protected LtsBasketService ltsBasketService;
	protected LtsOfferService ltsOfferService;
	protected CustomerProfileLtsService customerProfileLtsService;
	protected CodeLkupCacheService ltsIddCampaignLkupCacheService;
	protected OfferItemService offerItemService;
	protected OfferProfileLtsService offerProfileLtsService;
	protected SbOrderInfoLtsService sbOrderInfoLtsService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqBasketVasDetailController(){
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsAcqBasketVasDetailFormDTO.class);
	}

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
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		String locale = RequestContextUtils.getLocale(request).toString();
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		String selectedBasketId = order.getLtsAcqBasketSelectionForm().getSelectedBasketId();
		BasketDetailDTO selectedBasketDetail = ltsBasketService.getBasket(selectedBasketId, LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
		
		LtsAcqBasketVasDetailFormDTO form = order.getLtsAcqBasketVasDetailForm();
		
		if (form == null) {
			form = new LtsAcqBasketVasDetailFormDTO();
			initForm(form, order, selectedBasketId, selectedBasketDetail, locale, bomSalesUser);
			form.setControlBasketId(selectedBasketId);
		}
		else{
			if(StringUtils.isNotBlank(form.getControlBasketId()) && !form.getControlBasketId().equals(selectedBasketId)){
				initForm(form, order, selectedBasketId, selectedBasketDetail, locale, bomSalesUser);
				form.setControlBasketId(selectedBasketId);
			}
		}
		
		if(order.getLtsAcqBasketSelectionForm()!=null && order.getLtsAcqBasketSelectionForm().isDelFreeBundle())
		{
			form.setIddVasItemList(null);
			form.setE0060VasItemList(null);
		}
		
		removeExclusiveItem(request, order, form);
		filterBasketExclusiveItemByPsef(order, form);
		return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqBasketVasDetailFormDTO form = (LtsAcqBasketVasDetailFormDTO) command;

		if (order == null || form == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		};

		switch (form.getFormAction()) {
			case FILTER:
				String locale = RequestContextUtils.getLocale(request).toString();
				ModelAndView mav = new ModelAndView(viewName);
				reloadFfpVasItemList(form,order,order.getSelectedBasket(), locale);
				mav.addObject(commandName, form);
				return mav;			
		}
		
		ValidationResultDTO validationResult = validateExclusiveItem(request, order, form);
				
		if (Status.INVAILD == validationResult.getStatus()) {
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			mav.addObject("errorMsgList", validationResult.getMessageList());
			return mav;
		}	
		
		List<ItemDetailDTO> prewireList = form.getPrewireVasItemList();
		order.setContainPrewiringVAS(false);
		if(prewireList != null){
			for(ItemDetailDTO prewireItem : prewireList){
				if(prewireItem.isSelected()){
					order.setContainPrewiringVAS(true);
				}
			}
		}
		
		List<ItemDetailDTO> preinstallList = form.getPreinstallVasItemList();
		order.setContainPreInstallVAS(false);
		if(preinstallList != null){
			for(ItemDetailDTO preInstallItem : preinstallList){
				if(preInstallItem.isSelected()){
					order.setContainPreInstallVAS(true);
				}
			}
		}
		
		List<ItemDetailDTO> ffpHotList = form.getFfpVasHotItemList();
		List<ItemDetailDTO> ffpOtherList = form.getFfpVasOtherItemList();
		List<ItemDetailDTO> ffpStaffList = form.getFfpVasStaffItemList();
		order.setContainFfpVAS(false);
		if(ffpHotList != null){
			for(ItemDetailDTO hotItem : ffpHotList){
				if(hotItem.isSelected()){
					order.setContainFfpVAS(true);
					break;
				}
			}
		}
		if(ffpOtherList != null && !order.isContainFfpVAS()){
			for(ItemDetailDTO otherItem : ffpOtherList){
				if(otherItem.isSelected()){
					order.setContainFfpVAS(true);
					break;
				}
			}
		}
		if(ffpStaffList != null && !order.isContainFfpVAS()){
			for(ItemDetailDTO staffItem : ffpStaffList){
				if(staffItem.isSelected()){
					order.setContainFfpVAS(true);
					break;
				}
			}
		}
		setItemPassword(order, form);
		order.setLtsAcqBasketVasDetailForm(form);
		order.setCreate2ndDelByReserveDnInd(null);
		
		// fail in validateItemQuota but it can keep on going with warning
		if(Status.INVAILD != validationResult.getStatus()
				&& order.getLtsAcqBasketServiceForm() != null
				&& !order.getLtsAcqBasketServiceForm().isExcludeQuotaCheck()){
			validationResult = validateItemQuota(form);
		}
		
		if (Status.INVAILD == validationResult.getStatus()) {
			ModelAndView mav = new ModelAndView(new RedirectView(nextView));
			mav.addObject("errorMsgList", validationResult.getMessageList());
			return mav;
		}
		//
		
		return new ModelAndView(new RedirectView(nextView));
	}

	@SuppressWarnings("unchecked")
	private ValidationResultDTO validateItemQuota(LtsAcqBasketVasDetailFormDTO form){		
		List<String> outQuotaItemList = new ArrayList<String>();
		List<String> errorMsgList = new ArrayList<String>();
		if(form.getPrewireVasItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getPrewireVasItemList()));
		if(form.getPreinstallVasItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getPreinstallVasItemList()));
		if(form.getFfpVasHotItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getFfpVasHotItemList()));		
		if(form.getFfpVasOtherItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getFfpVasOtherItemList()));
		if(form.getFfpVasStaffItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getFfpVasStaffItemList()));
		
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
	
	private void setItemPassword(AcqOrderCaptureDTO orderCapture, LtsAcqBasketVasDetailFormDTO form) {
		String defaultPassword = null;
		ItemAttbDTO planPasswordAttb = null;
		ItemAttbDTO iddVasPasswordAttb = null;
		
		// Get Plan package password attribute (7FOM)
		List<ItemDetailDTO> planItemList = orderCapture.getLtsAcqBasketServiceForm().getPlanItemList();
		if (planItemList != null && !planItemList.isEmpty()) {
			for (ItemDetailDTO planItem : planItemList) {
				if (ArrayUtils.isNotEmpty(planItem.getItemAttbs())) {
					for (ItemAttbDTO itemAttb : planItem.getItemAttbs()) {
						if (StringUtils.containsIgnoreCase("Password", itemAttb.getAttbDesc())) {
							planPasswordAttb = itemAttb;
							break;
						}
					}
				}
			}
		}
		
		// Get IDD password attribute (7IDD)
		List<ItemDetailDTO> iddVasList = form.getIddVasItemList();
		if(iddVasList != null){
			for(ItemDetailDTO iddVasItem : iddVasList){
				if(iddVasItem.isSelected()){
					ItemAttbDTO[] attributes = iddVasItem.getItemAttbs();
					for(ItemAttbDTO attb : attributes){
						if(StringUtils.containsIgnoreCase("Password", attb.getAttbDesc())
								&& StringUtils.isBlank(attb.getAttbValue())){
							iddVasPasswordAttb = attb;
						}
					}
				}
			}
		}
		
		if (iddVasPasswordAttb != null && StringUtils.isBlank(iddVasPasswordAttb.getAttbValue())) {
			defaultPassword = LtsSbOrderHelper.generateDefaultPassword(iddVasPasswordAttb.getMaxLength());
			iddVasPasswordAttb.setAttbValue(defaultPassword);
		}
		
		// Copy password from 7IDD if 7FOM password is empty. 
		if (planPasswordAttb != null && StringUtils.isBlank(planPasswordAttb.getAttbValue())) {
			if (iddVasPasswordAttb != null) {
				planPasswordAttb.setAttbValue(iddVasPasswordAttb.getAttbValue());
			}
			else {
				defaultPassword = LtsSbOrderHelper.generateDefaultPassword(planPasswordAttb.getMaxLength());
				planPasswordAttb.setAttbValue(defaultPassword);
			}
		}
	}
	
	private void reloadFfpVasItemList(LtsAcqBasketVasDetailFormDTO form, AcqOrderCaptureDTO order, BasketDetailDTO selectedBasketDetail, String locale){
		String iddCampaignCd = getIddCampaignCode(order);
		String basketChannel = order.getBasketChannelId();
		String baseContractPeriod = selectedBasketDetail.getContractPeriod();
		boolean is12Mth = form.isMth12Ffp();
		boolean is18Mth = form.isMth18Ffp();
		boolean is24Mth = form.isMth24Ffp();
		
		if (StringUtils.isNotEmpty(selectedBasketDetail.getExtendContractPeriod())) {
			baseContractPeriod = String.valueOf(Integer.parseInt(baseContractPeriod) - Integer.parseInt(selectedBasketDetail.getExtendContractPeriod()));
		}
		List<ItemDetailDTO> ffpVasHotItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_FFP_HOT, locale, true, true, null, basketChannel, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, iddCampaignCd, null);
		List<ItemDetailDTO> ffpVasOtherItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_FFP_OTHER, locale, true, true, null, basketChannel, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, iddCampaignCd, null);
		
		if(order.getSelectedBasket() != null && "Y".equals(order.getSelectedBasket().getStaffPlan())){
			List<ItemDetailDTO> ffpVasStaffItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_FFP_STAFF, locale, true, true, baseContractPeriod, basketChannel, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, iddCampaignCd, null);
			List<ItemDetailDTO> filterList = new ArrayList<ItemDetailDTO>();
			for(ItemDetailDTO info : ffpVasStaffItemList){
				if(is12Mth && "12".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
				else if(is18Mth && "18".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
				else if(is24Mth && "24".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
				else if(StringUtils.isBlank(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
			}
			form.setFfpVasStaffItemList(filterList);
		}
		
		List<ItemDetailDTO> filterList = new ArrayList<ItemDetailDTO>();
		for(ItemDetailDTO info : ffpVasHotItemList){
			if(is12Mth && "12".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is18Mth && "18".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is24Mth && "24".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(StringUtils.isBlank(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
		}
		form.setFfpVasHotItemList(filterList);
		
		filterList = new ArrayList<ItemDetailDTO>();
		for(ItemDetailDTO info : ffpVasOtherItemList){
			if(is12Mth && "12".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is18Mth && "18".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is24Mth && "24".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(StringUtils.isBlank(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
		}	
		form.setFfpVasOtherItemList(filterList);

	}
	
	private void chkPreInstall(LtsAcqBasketVasDetailFormDTO form, AcqOrderCaptureDTO order, BasketDetailDTO selectedBasketDetail) {

			boolean isPcdPreInstall = false;
			List<PcdOrderCheckAttrDTO> pcdOrderCheckAttrList = new ArrayList<PcdOrderCheckAttrDTO>();
			
			if(StringUtils.isNotBlank(order.getLtsAcqBasketSelectionForm().getPcdSbid()) 
					&& "Y".equals(selectedBasketDetail.getPreinstallCheck())){
				
				pcdOrderCheckAttrList = sbOrderInfoLtsService.getPcdSbOrderDetails(order.getLtsAcqBasketSelectionForm().getPcdSbid());
				if(pcdOrderCheckAttrList != null){
					for(PcdOrderCheckAttrDTO attr : pcdOrderCheckAttrList){
						if("IS_PRE_INSTALL".equalsIgnoreCase(attr.getCheckAttribute())){
							if("Y".equalsIgnoreCase(attr.getResult())){
								isPcdPreInstall = true;
							}	
						}
					}
				}

				if(isPcdPreInstall){
					for (int i = 0; i<form.getPreinstallVasItemList().size(); i++){
						form.getPreinstallVasItemList().get(i).setSelected(true);
						form.getPreinstallVasItemList().get(i).setMdoInd("M");
				    }
				}
			}
		}
	
	private void initForm(LtsAcqBasketVasDetailFormDTO form,
			AcqOrderCaptureDTO order, String selectedBasketId,
			BasketDetailDTO selectedBasketDetail, String locale, BomSalesUserDTO bomSalesUser) {
		
		String basketChannel = order.getBasketChannelId();
		String baseContractPeriod = selectedBasketDetail.getContractPeriod();
		String deviceType = StringUtils.defaultIfEmpty(LtsConstant.ELIGIBLE_EYE_TYPE_MAPPING.get(order.getLtsAcqBasketSelectionForm().getSelectedType()), "");
		String osType = selectedBasketDetail.getOsType();
		
		if (StringUtils.isNotEmpty(selectedBasketDetail.getExtendContractPeriod())) {
			baseContractPeriod = String.valueOf(Integer.parseInt(baseContractPeriod) - Integer.parseInt(selectedBasketDetail.getExtendContractPeriod()));
		}
		ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getAcqItemCriteriaDefaultBuilder(basketChannel, locale, osType).setEligibleDevice(deviceType).build();
		ItemCriteriaDTO defaultBasketCriteria = LtsSbOrderHelper.getAcqItemCriteriaDefaultBuilder(basketChannel, locale, osType).setBasketId(selectedBasketId).build();
		
		List<ItemDetailDTO> existVasItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_EXIST_VAS, locale, true, basketChannel, null);
		
		if (existVasItemList == null || existVasItemList.isEmpty()) {
			existVasItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_EXIST_VAS, locale, true, basketChannel, null);
			existVasItemList = replaceItemAttbToRightLang(existVasItemList);
		}
		
		List<ItemDetailDTO> hotVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_HOT).setBaseContractPeriod(baseContractPeriod).build()));
		List<ItemDetailDTO> otherVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_OTHER).build()));
		List<ItemDetailDTO> iddVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_IDD).setEligibleDevice(null).build()));
		List<ItemDetailDTO> e0060VasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_0060E).setEligibleDevice(null).build()));
		List<ItemDetailDTO> prewireVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_PRE_WIRING).setEligibleDevice(null).build()));
		List<ItemDetailDTO> preinstallVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_PRE_INSTALL).setEligibleDevice(null).build()));
		String iddCampaignCd = getIddCampaignCode(order);
		List<ItemDetailDTO> ffpVasHotItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_HOT).setCampaignCd(iddCampaignCd).build()));   
		List<ItemDetailDTO> ffpVasOtherItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_OTHER).setCampaignCd(iddCampaignCd).build()));

		List<ItemDetailDTO> peFreeVasItemList = replaceItemAttbToRightLang(ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultBasketCriteria).setItemType(LtsConstant.ITEM_TYPE_PE_FREE).build()));
		List<ItemDetailDTO> peSocketVasItemList = replaceItemAttbToRightLang(ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultBasketCriteria).setItemType(LtsConstant.ITEM_TYPE_PE_SOCKET).build()));
		List<ItemDetailDTO> optAccessaryItemList = replaceItemAttbToRightLang(ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultBasketCriteria).setItemType(LtsConstant.ITEM_TYPE_OPT_ACC).build()));
		
		List<ItemDetailDTO> bvasItemList = replaceItemAttbToRightLang(order.getLtsAcqBasketServiceForm().getBvasItemList());
		
		List<ItemDetailDTO> otherVasCommitmentPeriodItemList = replaceItemAttbToRightLang(ltsOfferService.getVasOtherCommitmentPeriodItemList(LtsConstant.ITEM_TYPE_VAS_HOT, locale, true, true, baseContractPeriod, basketChannel, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, null, null, osType));

		ItemSetCriteriaDTO smartWrtyItemCriteria = LtsSbOrderHelper.getItemSetCriteria(order, bomSalesUser, LtsConstant.ITEM_SET_TYPE_SMART_WARRANTY, locale);
		smartWrtyItemCriteria.setOsType(osType);
		List<ItemSetDetailDTO> smartWrtyItemList = ltsOfferService.getBasketItemSetList(smartWrtyItemCriteria);
		ItemSetCriteriaDTO bundleVasItemCriteria = LtsSbOrderHelper.getItemSetCriteria(order, bomSalesUser, LtsConstant.ITEM_SET_TYPE_BUNDLE_VAS, locale);
		bundleVasItemCriteria.setOsType(osType);
		List<ItemSetDetailDTO> bundleVasItemList = ltsOfferService.getBasketItemSetList(bundleVasItemCriteria);
		
		form.setBundleVasSetList(bundleVasItemList);
		
		List<String> bundleVasItemIdList = ltsOfferService.getAllItemSetItemId(bundleVasItemList);		
		
		form.setSmartWrtySetList(smartWrtyItemList);
		form.setFfpVasHotItemList(ltsOfferService.filterOutItemById(ffpVasHotItemList, bundleVasItemIdList));
		form.setFfpVasOtherItemList(ltsOfferService.filterOutItemById(ffpVasOtherItemList, bundleVasItemIdList));
		form.setPrewireVasItemList(prewireVasItemList);
		form.setPreinstallVasItemList(preinstallVasItemList);
		chkPreInstall(form, order, selectedBasketDetail);
		
		if(order.getSelectedBasket() != null && "Y".equals(order.getSelectedBasket().getStaffPlan())){
			List<ItemDetailDTO> ffpVasStaffItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_FFP_STAFF, locale, true, true, baseContractPeriod, basketChannel, LtsBackendConstant.DISPLAY_TYPE_ITEM_SELECT, iddCampaignCd, null);
			form.setFfpVasStaffItemList(ltsOfferService.filterOutItemById(ffpVasStaffItemList, bundleVasItemIdList));
		}
		

		form.setExistVasItemList(existVasItemList);
		form.setHotVasItemList(ltsOfferService.filterOutItemById(hotVasItemList, bundleVasItemIdList));
		form.setOtherVasItemList(ltsOfferService.filterOutItemById(otherVasItemList, bundleVasItemIdList));
		
		form.setOtherVasComtPeriodItemList(otherVasCommitmentPeriodItemList);
		
		form.setPeFreeItemList(peFreeVasItemList);
		form.setPeSocketItemList(peSocketVasItemList);
		form.setIddVasItemList(iddVasItemList);
		form.setE0060VasItemList(e0060VasItemList);
		form.setOptAccessaryItemList(optAccessaryItemList);
		form.setBvasItemList(bvasItemList);
		form.setMth12Ffp(true);
		form.setMth18Ffp(true);
		form.setMth24Ffp(true);
	}
	
	private List<ItemDetailDTO> replaceItemAttbToRightLang(List<ItemDetailDTO> inItemDetailList){
		if (inItemDetailList!=null)
		{
			for (int i = 0; i<inItemDetailList.size(); i++)
			{
				ItemAttbDTO[] tempItemAttbList = inItemDetailList.get(i).getItemAttbs();
				if(tempItemAttbList != null)
				{
					for (int j = 0; j<tempItemAttbList.length; j++)
					{
						if(tempItemAttbList[j].getAttbDesc().equalsIgnoreCase("Phone Mail Language"))
						{
							tempItemAttbList[j].setAttbDesc(messageSource.getMessage("lts.acq.common.phoneMailLang", new Object[] {}, this.locale));
						}
						else if(tempItemAttbList[j].getAttbDesc().equalsIgnoreCase("Mobile Number (must register for IDD 0060 Service)"))
						{
							tempItemAttbList[j].setAttbDesc(messageSource.getMessage("lts.acq.common.mobileNumMustRegForIDD", new Object[] {}, this.locale));
						}
						
						if (tempItemAttbList[j].getInputMethod().equalsIgnoreCase("SELECT"))
						{
							List<ItemAttbInfoDTO> tempItemAttbInfoList = tempItemAttbList[j].getItemAttbInfoList();
							if(tempItemAttbInfoList != null)
							{
								for (int k = 0; k<tempItemAttbInfoList.size(); k++)
								{
									if(tempItemAttbInfoList.get(k).getAttbValue().equalsIgnoreCase("CHI"))
									{
										tempItemAttbInfoList.get(k).setAttbDesc(messageSource.getMessage("lts.acq.common.chinese", new Object[] {}, this.locale));
									}
									else if(tempItemAttbInfoList.get(k).getAttbValue().equalsIgnoreCase("ENG"))
									{
										tempItemAttbInfoList.get(k).setAttbDesc(messageSource.getMessage("lts.acq.common.english", new Object[] {}, this.locale));
									}
								}
							}
						}
					}
				}
			}
		}
		return inItemDetailList;
	}
	
	private String extractContractMonthFromRecurrentAmt(String pRecurrentAmtTxt){
		if(StringUtils.isBlank(pRecurrentAmtTxt)){
			return "";
		}
		
		String[] result = pRecurrentAmtTxt.split("x");
		
		if(result == null || result.length != 2){
			return "";
		}
		
		return result[1].replaceAll(" ", "").replace("month", "");
	}
	
	private void removeExclusiveItem (HttpServletRequest request, AcqOrderCaptureDTO order, LtsAcqBasketVasDetailFormDTO form) {
		
		//List<ItemDetailDTO> itemDetailListA = order.getLtsAcqBasketServiceForm().getBvasItemList();// getProfileOrBasketDefaultItemList(order, form);
		List<ItemDetailDTO> itemDetailListA = getProfileOrBasketDefaultItemList(order, form);
		List<ItemDetailDTO> itemDetailListB = getAllValidateExclusiveItem(order, form);

		List<ExclusiveItemDetailDTO> exclusiveItemDetailList = ltsOfferService
				.getExclusiveItemDetailList(itemDetailListA, itemDetailListB,
						RequestContextUtils.getLocale(request).toString(), false);
		
		if (exclusiveItemDetailList == null || exclusiveItemDetailList.isEmpty()) {
			return;
		}
		
		for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {
			
			form.setHotVasItemList(filterExclusiveItem(form.getHotVasItemList(), exclusiveItemDetail));
			form.setOtherVasItemList(filterExclusiveItem(form.getOtherVasItemList(), exclusiveItemDetail));
			form.setIddVasItemList(filterExclusiveItem(form.getIddVasItemList(), exclusiveItemDetail));
			form.setIddOutVasItemList(filterExclusiveItem(form.getIddOutVasItemList(), exclusiveItemDetail));
			form.setE0060VasItemList(filterExclusiveItem(form.getE0060VasItemList(), exclusiveItemDetail));
			form.setE0060OutVasItemList(filterExclusiveItem(form.getE0060OutVasItemList(), exclusiveItemDetail));
			form.setOtherVasComtPeriodItemList(filterExclusiveItem(form.getOtherVasComtPeriodItemList(),exclusiveItemDetail));
			//form.setProfileAutoChangeTpItemList(filterExclusiveProfileItem(form.getProfileAutoChangeTpItemList(), exclusiveItemDetail));
			

			if (form.getBundleVasSetList()!= null && !form.getBundleVasSetList().isEmpty()) {
				for(ItemSetDetailDTO bundleVasSet : form.getBundleVasSetList()) {
					if (ArrayUtils.isNotEmpty(bundleVasSet.getItemDetails())) {
						List<ItemDetailDTO> bundleVasList = Arrays.asList(bundleVasSet.getItemDetails());
						List<ItemDetailDTO> filteredList = filterExclusiveItem(bundleVasList, exclusiveItemDetail);
						bundleVasSet.setItemDetails(filteredList.toArray(new ItemDetailDTO[filteredList.size()]));
					}
				}
			}
		}
	}
	
	private List<ItemDetailDTO> getProfileOrBasketDefaultItemList(AcqOrderCaptureDTO orderCapture, LtsAcqBasketVasDetailFormDTO form) {
		List<ItemDetailDTO> resultList = new ArrayList<ItemDetailDTO>();
		
		if (orderCapture.getLtsAcqBasketServiceForm().getBvasItemList() != null && 
				!orderCapture.getLtsAcqBasketServiceForm().getBvasItemList().isEmpty()) {
			resultList.addAll(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList());
		}
		if (orderCapture.getLtsAcqBasketServiceForm() != null 
				&& orderCapture.getLtsAcqBasketServiceForm().getPlanItemList() != null
				&& !orderCapture.getLtsAcqBasketServiceForm().getPlanItemList().isEmpty()) {
			resultList.addAll(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList());
		}
/*		
  		addProfileItemToItemList(form.getProfileExistingTpItemList(), resultList);
		addProfileItemToItemList(form.getProfileExistingVasItemList(), resultList);
*/
		return resultList;
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

	private List<ItemDetailDTO> getAllValidateExclusiveItem(AcqOrderCaptureDTO order,
			LtsAcqBasketVasDetailFormDTO form) {
		
		List<ItemDetailDTO> validateItemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (form.getHotVasItemList() != null && !form.getHotVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getHotVasItemList());
		}
		if (form.getOtherVasItemList() != null && !form.getOtherVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getOtherVasItemList());
		}
		if (form.getE0060OutVasItemList() != null && !form.getE0060OutVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getE0060OutVasItemList());
		}
		if (form.getE0060VasItemList() != null && !form.getE0060VasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getE0060VasItemList());
		}
		if (form.getIddOutVasItemList() != null && !form.getIddOutVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getIddOutVasItemList());
		}
		if (form.getIddVasItemList() != null && !form.getIddVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getIddVasItemList());
		}
		if (form.getPeFreeItemList() != null && !form.getPeFreeItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getPeFreeItemList());
		}
		if (form.getPeSocketItemList() != null && !form.getPeSocketItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getPeSocketItemList());
		}
		if (form.getOtherVasComtPeriodItemList() != null && !form.getOtherVasComtPeriodItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getOtherVasComtPeriodItemList());
		}

		if (form.getBundleVasSetList() != null && !form.getBundleVasSetList().isEmpty()) {
			for (ItemSetDetailDTO bundleVasItemSet : form.getBundleVasSetList()) {
				if (ArrayUtils.isNotEmpty(bundleVasItemSet.getItemDetails())) {
					validateItemDetailList.addAll(Lists.newArrayList(bundleVasItemSet.getItemDetails()));	
				}
			}
		}
		//addProfileItemToItemList(form.getProfileAutoChangeTpItemList(), validateItemDetailList)	;
		
		return validateItemDetailList;
	}
	
	private ValidationResultDTO validateExclusiveItem(
			HttpServletRequest request, AcqOrderCaptureDTO order,
			LtsAcqBasketVasDetailFormDTO form) {
				
		List<ItemDetailDTO> validateExclusiveList = getAllValidateExclusiveItem(order, form); 
		 return ltsOfferService.validateExclusiveItem(validateExclusiveList, validateExclusiveList,
				RequestContextUtils.getLocale(request).toString());
	}
	
	private String getIddCampaignCode(AcqOrderCaptureDTO orderCapture) {
		
		CustomerDetailProfileLtsDTO profileCustomer = orderCapture.getCustomerDetailProfileLtsDTO();
		List<CampaignDTO> iddCampaignList = null;
		
		if(profileCustomer != null){
			iddCampaignList = customerProfileLtsService.getCampaign(LtsConstant.SYSTEM_ID_DRG, profileCustomer.getCustNum(), null, LtsConstant.CAMPAIGN_LOB_IDD);
		}
		
		if (iddCampaignList == null || iddCampaignList.isEmpty()) {
			return "Z";
		}
		return StringUtils.defaultIfEmpty((String)ltsIddCampaignLkupCacheService.get((iddCampaignList.get(0)).getValuePropId()), "Z");
	}
	
	private List<String> getItemPsefCd(List<ItemDetailDTO> itemList) {
		
		List<String> psefList = new ArrayList<String>();
		if (itemList == null || itemList.isEmpty()) {
			return psefList;
		}
		
		for (ItemDetailDTO item : itemList) {
			if (!item.isSelected()) {
				continue;
			}
			String[] offerIds = offerItemService.getOffersByItemId(item.getItemId());	
			if (ArrayUtils.isEmpty(offerIds)) {
				continue;
			}
			List<String> psefCdList = offerProfileLtsService.getPsefCdByOfferId(Lists.newArrayList(offerIds));
			if (psefCdList == null || psefCdList.isEmpty()) {
				continue; 
			}
			psefList.addAll(psefCdList);
		}
		return psefList;
	}
	
	private void filterBasketExclusiveItemByPsef(AcqOrderCaptureDTO orderCapture, LtsAcqBasketVasDetailFormDTO form) {
		List<String> basketOfferPsefList = new ArrayList<String>();
		if (orderCapture.getLtsAcqBasketServiceForm() != null && orderCapture.getLtsAcqBasketServiceForm().getPlanItemList() != null) {
			basketOfferPsefList.addAll(getItemPsefCd(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList()));	
		}
		if (orderCapture.getLtsAcqBasketServiceForm() != null && orderCapture.getLtsAcqBasketServiceForm().getPlanItemList() != null) {
			basketOfferPsefList.addAll(getItemPsefCd(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList()));
		}
		if (basketOfferPsefList.isEmpty()) {
			return;
		}
		
		form.setHotVasItemList(filterItemDetailListByPsef(form.getHotVasItemList(), basketOfferPsefList));
		form.setOtherVasItemList(filterItemDetailListByPsef(form.getOtherVasItemList(), basketOfferPsefList));
		filterItemSetDetailListByPsef(form.getBundleVasSetList(), basketOfferPsefList);
	}

	private void filterItemSetDetailListByPsef(List<ItemSetDetailDTO> itemSetDetailList, List<String> psefCdList) {
		if (itemSetDetailList != null && !itemSetDetailList.isEmpty()) {
			for(ItemSetDetailDTO teamVasSet : itemSetDetailList) {
				if (ArrayUtils.isNotEmpty(teamVasSet.getItemDetails())) {
					List<ItemDetailDTO> filteredItemDetailList = filterItemDetailListByPsef(Lists.newArrayList(teamVasSet.getItemDetails()), psefCdList);
					teamVasSet.setItemDetails(filteredItemDetailList.toArray(new ItemDetailDTO[filteredItemDetailList.size()]));
				}
			}
		}
	}
	
	private List<ItemDetailDTO> filterItemDetailListByPsef(List<ItemDetailDTO> itemDetailList, List<String> psefCdList) {
		
		if (itemDetailList == null || itemDetailList.isEmpty() 
				|| psefCdList == null || psefCdList.isEmpty()) {
			return itemDetailList;
		}
		
		List<ItemDetailDTO> filteredList = new ArrayList<ItemDetailDTO>();
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			filteredList.add(itemDetail);
			
			String[] offerIds = offerItemService.getOffersByItemId(itemDetail.getItemId());	
			if (ArrayUtils.isEmpty(offerIds)) {
				continue;
			}

			List<String> offerPsefCdList = offerProfileLtsService.getPsefCdByOfferId(Lists.newArrayList(offerIds));
			if (psefCdList == null || psefCdList.isEmpty()) {
				continue; 
			}
			
			for (String psefCd : offerPsefCdList) {
				if (psefCdList.contains(psefCd)) {
					filteredList.remove(itemDetail);
					break;
				}
			}
		}
		return filteredList;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
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

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}

	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

	public CodeLkupCacheService getLtsIddCampaignLkupCacheService() {
		return ltsIddCampaignLkupCacheService;
	}

	public void setLtsIddCampaignLkupCacheService(
			CodeLkupCacheService ltsIddCampaignLkupCacheService) {
		this.ltsIddCampaignLkupCacheService = ltsIddCampaignLkupCacheService;
	}
	
	
}
