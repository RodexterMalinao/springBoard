package com.bomltsportal.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.ApplicantInfoFormDTO;
import com.bomltsportal.service.ApplicantInfoService;
import com.bomltsportal.service.BasketDetailService;
import com.bomltsportal.service.DnPoolService;
import com.bomltsportal.service.ItemDetailService;
import com.bomltsportal.service.SummaryService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.service.OfferService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.EyeProfileCountService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;


public class ApplicantInfoController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String nextView = "ordersubmit.html";
	
	private ApplicantInfoService applicantInfoService;
	private AppointmentDwfmService appointmentDwfmService;
	private DnPoolService dnPoolService;
	private SummaryService summaryService;
	private ItemDetailService itemDetailService;
	private CodeLkupCacheService csPortalTcUrlCacheService;
	private BasketDetailService basketDetailService;
	private CustomerProfileLtsService customerProfileLtsService;
	private ImsProfileService imsProfileService;
	private EyeProfileCountService eyeProfileCountService;
	private OfferService offerService;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null || orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}		
		return super.handleRequestInternal(request, response);
	}
	
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		logger.info("ApplicantInfoController formBackingObject is called");
		ApplicantInfoFormDTO form;
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		form = orderCaptureDTO.getApplicantInfoForm();
		if(form == null){
			form = new ApplicantInfoFormDTO();
			form.setCsPortalConfirm(true);
			form.setBillMethod("E");
			form.setBillLang("en".equalsIgnoreCase(orderCaptureDTO.getLang())?"E":"C");
			orderCaptureDTO.setApplicantInfoForm(form);
		}

		form.setNewNum(true);
		List<ItemDetailDTO> epdOptions = itemDetailService.getItemList(orderCaptureDTO.getChannelId(), LtsBackendConstant.ITEM_TYPE_EPD_FORFEIT, "ITEM_SELECT", RequestContextUtils.getLocale(request).toString(), false, true); 
		epdOptions.addAll(itemDetailService.getItemList(orderCaptureDTO.getChannelId(), LtsBackendConstant.ITEM_TYPE_EPD_ACCEPT, "ITEM_SELECT", RequestContextUtils.getLocale(request).toString(), false, true));
		epdOptions.addAll(itemDetailService.getItemList(orderCaptureDTO.getChannelId(), LtsBackendConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION, "ITEM_SELECT", RequestContextUtils.getLocale(request).toString(), false, true));
		form.setEpdItemList(epdOptions);
		
		orderCaptureDTO.setTopNavInd(BomLtsConstant.TOP_NAV_IND_REGISTER);
		if(form.getDisplayNewNum() == null || form.getDisplayNewNum().isEmpty()){
			form.setDisplayNewNum(dnPoolService.getDnListFromPool(BomLtsConstant.IDEN_SRV_NUM_DISPLAY_AMOUNT, request.getSession().getId()));
			
		}
		
		orderCaptureDTO.getApplicantInfoForm().setEarliestSrd(applicantInfoService.getEarliestSRD(orderCaptureDTO));
		
		/*if(BomLtsConstant.DEVICE_TYPE_EYE3A.equals(basketDetailService.getSelectedDeviceType(orderCaptureDTO, orderCaptureDTO.getBasketSelectForm().getSelectedBasketId()))){
			List<ItemDetailDTO> prepayItemList = itemDetailService.getBasketItemList(orderCaptureDTO.getChannelId(),
					orderCaptureDTO.getBasketSelectForm().getSelectedBasketId(), BomLtsConstant.ITEM_TYPE_PREPAY,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, orderCaptureDTO.getLang(), true, false);
			form.setPrepayItemList(prepayItemList);
			if(prepayItemList != null && prepayItemList.size() > 0){
				form.setPayAmount(Integer.parseInt(prepayItemList.get(0).getOneTimeAmt()));
			}
		}else{
			List<ItemDetailDTO> allItemList = getSelectedItemList(orderCaptureDTO);
			int amount = 0;
			if(allItemList != null && allItemList.size() > 0){
				amount += summaryService.calculateItemPaymentAmount(allItemList);
			}
			for(OnlineBasketDTO basket :orderCaptureDTO.getBasketSelectForm().getOnlineBasketList()){
				if(basket.isSelected()){
					try{
						if(StringUtils.isEmpty(basket.getBasketDetail().getContractPeriod())
								|| "0".equals(basket.getBasketDetail().getContractPeriod())){
							amount += Integer.parseInt(basket.getBasketDetail().getMthToMthAmt());
						}else{
							amount += Integer.parseInt(basket.getBasketDetail().getRecurrentAmt());
						}
					}catch(NumberFormatException nfe){
						nfe.printStackTrace();
					}
				}
			}
			form.setPayAmount(amount);
		}*/
		
		request.setAttribute("earliestSrd",  form.getEarliestSrd().getDateString("yyyy/MM/dd"));
		request.setAttribute("leadTime", form.getEarliestSrd().getLeadTime());
		
		List<ItemDetailDTO> ebillItemList = itemDetailService.getItemList(
				orderCaptureDTO.getChannelId(), 
				BomLtsConstant.ITEM_TYPE_ONLINE_EBILL, 
				BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, 
				orderCaptureDTO.getLang(), true, false);
		form.setBillMethodItemList(ebillItemList);
		return form;
		
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException {
		logger.info("ApplicantInfoController onSubmit is called");
		
		logger.info("Next View: " + nextView);
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		ApplicantInfoFormDTO form = (ApplicantInfoFormDTO) command;
		
		PrebookAppointmentOutputDTO output = appointmentDwfmService.getPrebookAppointment(
				applicantInfoService.getPrebookAppointmentInput(
						orderCapture, LtsDateFormatHelper.reformatDate(form.getInstallDate(), "yyyy/MM/dd", "dd/MM/yyyy"), 
						form.getInstallTime(), BomLtsConstant.USER_ID, form.getInstallTimeType()));
		if(output != null){
			if(output.getSerialNum() != null){
				form.setPrebookSerialNum(output.getSerialNum());
			}
		}
//		if((!form.isCustNameMatch() && form.isCustExist())|| StringUtils.isNotEmpty(form.getImportNum())){
//			form.setPrepayItemList(null);
//		}
		form.setEpdItemList(selectWeeeOption(form.getEpdItemList(), form.getWeeeOptions()));
		orderCapture.setApplicantInfoForm(form);
		
//		setupPrepayment(orderCapture, form, RequestContextUtils.getLocale(request));
		setupPrepaymentItem(orderCapture, form, RequestContextUtils.getLocale(request));
		return new ModelAndView(new RedirectView(nextView));
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		logger.info("ApplicantInfoController referenceData is called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(BomLtsConstant.SESSION_ORDER_CAPTURE);
		
		ApplicantInfoFormDTO form = orderCaptureDTO.getApplicantInfoForm();
		
		if(StringUtils.isNotBlank(form.getInstallDate())){
			boolean pon = applicantInfoService.isPon(orderCaptureDTO);
			try{
				ResourceDetailOutputDTO resource = 
						applicantInfoService.getResourceDetailWithFilter(applicantInfoService.getResourceDetailInput(
								orderCaptureDTO, LtsDateFormatHelper.reformatDate(form.getInstallDate(), "yyyy/MM/dd", "dd/MM/yyyy")), pon);
				
				if(resource != null && resource.getResourceSlots() != null){
					ResourceSlotDTO[] slots = resource.getResourceSlots();
					for(ResourceSlotDTO slot: slots){
						if("Y".equals(slot.getAvailableInd()) && !"Y".equals(slot.getRestrictInd())){
							String appTimeSlot = LtsDateFormatHelper.convertToSBTimeSlot(slot.getApptTimeSlot());
							if(pon){
								appTimeSlot = LtsDateFormatHelper.convertToPonTimeSlot(appTimeSlot);
							}
							slot.setApptTimeSlot(appTimeSlot);
						}
					}
					referenceData.put("timeSlots", slots);
				}
			}catch(Exception e){
				logger.error(ExceptionUtils.getFullStackTrace(e));
			}
		}

		for(LookupItemDTO cutOffLkup : csPortalTcUrlCacheService.getCodeLkupDAO().getCodeLkup()){
			if(orderCaptureDTO.getLang().equals(cutOffLkup.getItemKey())){
				referenceData.put("csPortalTcUrl", (String)cutOffLkup.getItemValue());
				break;
			}
		}
		
		return referenceData;
	}

	private List<ItemDetailDTO> getSelectedItemList(OrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO != null){
			List<ItemDetailDTO> allItems = new ArrayList<ItemDetailDTO>();
			if(orderCaptureDTO.getBasketDetailForm() != null){
				List<ItemDetailDTO> contentItemList = new ArrayList<ItemDetailDTO>();
				List<ItemDetailDTO> premiumItemList = new ArrayList<ItemDetailDTO>();
				for(ItemSetDetailDTO itemSet : orderCaptureDTO.getBasketDetailForm().getContItemSetList()){
					for(ItemDetailDTO itemDetail : itemSet.getItemDetails()){
						if(itemDetail.isSelected()){
							contentItemList.add(itemDetail);
							allItems.add(itemDetail);
						}
					}
				}
				for(ItemSetDetailDTO itemSet : orderCaptureDTO.getBasketDetailForm().getPremiumItemSetList()){
					for(ItemDetailDTO itemDetail : itemSet.getItemDetails()){
						if(itemDetail.isSelected()){
							premiumItemList.add(itemDetail);
							allItems.add(itemDetail);
						}
					}
				}
				for(ItemDetailDTO itemDetail : orderCaptureDTO.getBasketDetailForm().getInstallFeeItemList()){
					if(itemDetail.isSelected()){
						allItems.add(itemDetail);
					}
				}
			}
			if(orderCaptureDTO.getVasDetailForm() != null){
				if(orderCaptureDTO.getVasDetailForm().getMoovItems() != null){
					for(ItemDetailDTO itemDetail : orderCaptureDTO.getVasDetailForm().getMoovItems()){
						if(itemDetail.isSelected()){
							allItems.add(itemDetail);
						}
					}
				}
				if(orderCaptureDTO.getVasDetailForm().getOtherItems() != null){
					for(ItemDetailDTO itemDetail : orderCaptureDTO.getVasDetailForm().getOtherItems()){
						if(itemDetail.isSelected()){
							allItems.add(itemDetail);
						}
					}
				}
				if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvPayChannelId() != null){
					for(ChannelGroupDetailDTO channelGrp : orderCaptureDTO.getVasDetailForm().getPayChannelGroupList()){
						for(ChannelDetailDTO channel : channelGrp.getChannelDetails()){
							if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvPayChannelId().equals(channel.getChannelId())){
								allItems.add(channel.getItemDetail());
							}
						}
					}
				}
				if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvSpecChannelId() != null){
					for(ChannelGroupDetailDTO channelGrp : orderCaptureDTO.getVasDetailForm().getSpecChannelGroupList()){
						for(ChannelDetailDTO channel : channelGrp.getChannelDetails()){
							if(orderCaptureDTO.getVasDetailForm().getSelectedNowTvSpecChannelId().equals(channel.getChannelId())){
								allItems.add(channel.getItemDetail());
							}
						}
					}
				}
			}
			return allItems;
		}
		return null;
	}
	
/*	private double setupTenure(OrderCaptureDTO orderCapture) {
		
		CustomerDetailProfileLtsDTO custDetailDTO = orderCapture.getCustomerDetailProfile();
		AddressRolloutDTO addressDTO = orderCapture.getAddressRollout();
		//LTS
		double ltsTenure= 0;
		if(custDetailDTO != null){
			ltsTenure = this.customerProfileLtsService.getMaxLtsTenure(custDetailDTO.getParentCustNum(), addressDTO.getFlat(), addressDTO.getFloor(), addressDTO.getSrvBdary());
		} 
		
		//IMS
		TenureDTO[] imsTenures = this.imsProfileService.getImsTenureByAddress(addressDTO.getFlat(), addressDTO.getFloor(), addressDTO.getSrvBdary());
		
		if (imsTenures == null && StringUtils.isNotEmpty(addressDTO.getFloor()) && StringUtils.isNotEmpty(addressDTO.getFlat())) {
			imsTenures = this.imsProfileService.getImsTenureByAddress(addressDTO.getFloor() + addressDTO.getFlat(), "", addressDTO.getSrvBdary());
		}
		
		double maxTenure = 0.0;
		
		if(custDetailDTO != null){
			if (!ArrayUtils.isEmpty(imsTenures)) {
				for (TenureDTO tenure : imsTenures) {
					//check if imsTenure is under same cust
					String[] custDocDtl = this.imsProfileService.getImsCustDocByParentCust(tenure.getCustNum());
					if(StringUtils.equals(custDocDtl[0], custDetailDTO.getDocType())
							&& StringUtils.equals(custDocDtl[1], custDetailDTO.getDocNum())
							&& tenure.getTenure() > maxTenure){
						maxTenure = tenure.getTenure();
					}
				}
			}
		}
		return ltsTenure > maxTenure ? ltsTenure : maxTenure;
	}*/
	
	private void setupPrepayment(OrderCaptureDTO order, ApplicantInfoFormDTO form, Locale locale){
		String tenureCode = applicantInfoService.getLtsTenureCode(setupTenure(order));

		form.setTenureCode(tenureCode);
		
		String selectedBasketId = order.getBasketSelectForm().getSelectedBasketId();
		
		List<ItemDetailDTO> basketPrepayItemList = applicantInfoService.getLtsBasketItemList(selectedBasketId, BomLtsConstant.ITEM_TYPE_PREPAY, locale.toString(), true, order.getChannelId(), null);
		
		String housingType = StringUtils.defaultIfEmpty(order.getAddressRollout().getHousingType(), BomLtsConstant.HOUSE_TYPE_PUBLIC_HSE);

		// set tenureCode = "ACQ" if BYPASS = Y
		if("Y".equalsIgnoreCase((String)csPortalTcUrlCacheService.get(BomLtsConstant.CODE_LKUP_ACQ_PREPAY_TENURE_BYPASS)))
		{
			tenureCode = "ACQ";
		}
		//
		
		if(order.getBasketSelectForm().getSelectedBasket() != null){
			/*List<String[]> prepayItemIdList = {ITEM ID, PAYMENT TYPE}*/
			List<String[]> prepayItemIdList = applicantInfoService.getLtsPrePayItem(tenureCode, housingType, order.getBasketSelectForm().getSelectedBasket().getType());
			
			List<ItemDetailDTO> prepayItemList = new ArrayList<ItemDetailDTO>();
			if(prepayItemIdList != null && prepayItemIdList.size() > 0){
				for(String[] prepayItemId : prepayItemIdList){
					if(basketPrepayItemList != null && basketPrepayItemList.size() > 0){
						for(ItemDetailDTO itemDetail : basketPrepayItemList){
							if(StringUtils.equals(prepayItemId[0],itemDetail.getItemId()) && StringUtils.equals(prepayItemId[1], "C")){
								prepayItemList.add(itemDetail);
								break ;
							}
						}
					}
				}
			}
			
			if(prepayItemList != null && prepayItemList.size() > 0){
				form.setPrepayInd(true);
			} else {
				form.setPrepayInd(false);
			} 
			form.setPrepayItemList(prepayItemList);
		}
//		form.setSalesMemoNumRequired(order.isChannelRetail());
	}
	
	private int setupTenure(OrderCaptureDTO order) {
		CustomerDetailProfileLtsDTO custDetailDTO = order.getCustomerDetailProfile();
		AddressRolloutDTO addressDTO = order.getAddressRollout();
		//LTS
		int ltsTenure = 0;
		if(custDetailDTO != null){
			ltsTenure= this.customerProfileLtsService.getMaxLtsTenure(custDetailDTO.getParentCustNum(), addressDTO.getFlat(), addressDTO.getFloor(), addressDTO.getSrvBdary());
		}
		
		List<String[]> addrPatternList = LtsSbHelper.getAddrCombinationPattern(addressDTO.getFlat(), addressDTO.getFloor());

		// Get IMS Tenure
		TenureDTO[] imsTenures = null;
		if (addrPatternList != null && !addrPatternList.isEmpty()) {
			for (String[] addrParrtern : addrPatternList) {
				imsTenures = this.imsProfileService.getImsTenureByAddress(addrParrtern[0], addrParrtern[1], addressDTO.getSrvBdary());
				if (ArrayUtils.isNotEmpty(imsTenures)) {
					break;
				}
			}
		}
		
		int maxTenure = 0;

		if(custDetailDTO != null){
			if (!ArrayUtils.isEmpty(imsTenures)) {
				for (TenureDTO tenure : imsTenures) {
					//check if imsTenure is under same cust
					String[] custDocDtl = this.imsProfileService.getImsCustDocByParentCust(tenure.getCustNum());
					if(StringUtils.equals(custDocDtl[0], custDetailDTO.getDocType())
							&& StringUtils.equals(custDocDtl[1], custDetailDTO.getDocNum())
							&& tenure.getTenure() > maxTenure){
						maxTenure = tenure.getTenure();
					}
				}
			}
		}
			
		return ltsTenure > maxTenure ? ltsTenure : maxTenure;
	}
	
	private void setupPrepaymentItem(OrderCaptureDTO order, ApplicantInfoFormDTO form, Locale locale){
		CustomerDetailProfileLtsDTO custDetailDTO = order.getCustomerDetailProfile();
		PrepaymentLkupCriteriaDTO criteria = new PrepaymentLkupCriteriaDTO();
		criteria.setChannel("O");
//		criteria.setDocType(order.getLtsCustomerIdentificationForm().getDocType());
		criteria.setOrderType("SBA");
		criteria.setTenure(setupTenure(order));
		String housingType = StringUtils.defaultIfEmpty(order.getAddressRollout().getHousingType(), BomLtsConstant.HOUSE_TYPE_PUBLIC_HSE);
		criteria.setHouseType(housingType);
		criteria.setAppDate(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")));
		
		int addrEyeCount = order.getAddressRollout().getNumOfEyeProfile();
		int custEyeCount = 0;
		if(custDetailDTO != null){
			eyeProfileCountService.getEyeProfileCountByCust(
				custDetailDTO.getDocType(),
				custDetailDTO.getDocNum());
		}
		criteria.setEyeProfileCount(addrEyeCount > custEyeCount? addrEyeCount : custEyeCount);
		criteria.setPaymentMethod(BomLtsConstant.PAYMENT_TYPE_CREDIT_CARD);
		
		/*check all psef code of plan item / content set item */
		List<String> psefCodeList = new ArrayList<String>();
		if(order.getBasketDetailForm() != null){
			if(order.getBasketDetailForm().getPlanItemList() != null){
				for(ItemDetailDTO itemDtl: order.getBasketDetailForm().getPlanItemList()){
					psefCodeList.addAll(Arrays.asList(offerService.getItemPsefCodes(itemDtl.getItemId())));
				}
			}
			if(order.getBasketDetailForm().getContItemSetList() != null){
				for (ItemSetDetailDTO itemSetDetail : order.getBasketDetailForm().getContItemSetList()) {
					for(ItemDetailDTO itemDtl: itemSetDetail.getItemDetails()){
						psefCodeList.addAll(Arrays.asList(offerService.getItemPsefCodes(itemDtl.getItemId())));
					}
				}
			}
		}

		if(CollectionUtils.containsAny(psefCodeList, applicantInfoService.getPrepayExcludePsefCodeList())){
			return;
		}

		List<PrepaymentLkupResultDTO> resultList = applicantInfoService.getPrepaymentItemIdByLkup(criteria);
		
		for(PrepaymentLkupResultDTO result: resultList){
			if(CollectionUtils.containsAny(result.getPsefCdSet(), psefCodeList)
					&& StringUtils.isNotBlank(result.getPrepayItemId())){
				List<ItemDetailDTO> itemList = itemDetailService.getItems(new String[]{result.getPrepayItemId()}, 
																		BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, 
																		locale.toString(), 
																		true, true);				
				if(itemList != null && itemList.size() > 0){
					form.setPrepayInd(true);
				} else {
					form.setPrepayInd(false);
				} 
				form.setPrepayItemList(itemList);
				
				break;
			}
		}
		
	}

	private List<ItemDetailDTO> selectWeeeOption(List<ItemDetailDTO> list, String option){		
		for(ItemDetailDTO item :list){	
			item.setSelected(option.equals(item.getItemDesc()));
		}	
		return list;	
	}		

	public ApplicantInfoService getApplicantInfoService() {
		return applicantInfoService;
	}

	public void setApplicantInfoService(ApplicantInfoService applicantInfoService) {
		this.applicantInfoService = applicantInfoService;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(
			AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public DnPoolService getDnPoolService() {
		return dnPoolService;
	}

	public void setDnPoolService(DnPoolService dnPoolService) {
		this.dnPoolService = dnPoolService;
	}

	public SummaryService getSummaryService() {
		return summaryService;
	}

	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}


	public CodeLkupCacheService getCsPortalTcUrlCacheService() {
		return csPortalTcUrlCacheService;
	}


	public void setCsPortalTcUrlCacheService(CodeLkupCacheService csPortalTcUrlCacheService) {
		this.csPortalTcUrlCacheService = csPortalTcUrlCacheService;
	}


	public BasketDetailService getBasketDetailService() {
		return basketDetailService;
	}


	public void setBasketDetailService(BasketDetailService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}


	public EyeProfileCountService getEyeProfileCountService() {
		return eyeProfileCountService;
	}


	public void setEyeProfileCountService(
			EyeProfileCountService eyeProfileCountService) {
		this.eyeProfileCountService = eyeProfileCountService;
	}


	public OfferService getOfferService() {
		return offerService;
	}


	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}
}
	
	