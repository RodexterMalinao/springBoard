package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemAttbInfoDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO.DuplexAction;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.form.acq.LtsAcqOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.bom.AddressDetailLtsService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqOtherVoiceServiceController extends SimpleFormController {
	
	private LtsOfferService ltsOfferService;
	private LtsProfileService ltsProfileService;
	private OfferItemService offerItemService;
	private AddressDetailLtsService addressDetailLtsService;
	
	private final String viewName = "lts/acq/ltsacqothervoiceservice";
	private final String nextView = "ltsacqappointment.html";
	private final String commandName = "ltsAcqOtherVoiceServiceCmd";

	private LtsAcqDnPoolService ltsAcqDnPoolService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqOtherVoiceServiceController(){
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsAcqOtherVoiceServiceFormDTO.class);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		if (order == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		/*Temp fix for secDel for recall case*/
		if (order.getSbOrder() != null){
			LtsAcqOtherVoiceServiceFormDTO form = order.getLtsAcqOtherVoiceServiceForm();
			if(form == null){
				form = new LtsAcqOtherVoiceServiceFormDTO();
				form.setCreate2ndDel(false);
				order.setLtsAcqOtherVoiceServiceForm(form);
			}
			return new ModelAndView(new RedirectView(nextView));
		}
		setReferenceData(request, order);
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);		
		LtsAcqOtherVoiceServiceFormDTO form = orderCapture.getLtsAcqOtherVoiceServiceForm();
		
		if(form == null){
			form = new LtsAcqOtherVoiceServiceFormDTO();
		}
		
		if(orderCapture.isContainPrewiringVAS()){
			form.setCreate2ndDel(false);
			return form;
		}
		
		String locale = RequestContextUtils.getLocale(request).toString();
		List<ItemDetailDTO> secondDelHotVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_HOT, locale, true, true, orderCapture.getBasketChannelId(), null));
		List<ItemDetailDTO> secondDelOtherVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_OTHER, locale, true, true, orderCapture.getBasketChannelId(), null));
		List<ItemDetailDTO> secondDelStandaloneVasItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_STANDALONE, locale, true, true, orderCapture.getBasketChannelId(), null));
		List<ItemDetailDTO> secondDelIddItemList = replaceItemAttbToRightLang(ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_IDD, locale, true, true, orderCapture.getBasketChannelId(), null));
		String sessionId = orderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId();
				
		form.setNumSelectionList(getNumSelectionList(sessionId));
		form.setSecondDelHotVasItemList(secondDelHotVasItemList);
		form.setSecondDelOtherVasItemList(secondDelOtherVasItemList);
		form.setSecondDelStandaloneVasItemList(secondDelStandaloneVasItemList);
		form.setSecondDelIddItemList(secondDelIddItemList);
		
		handleNew2ndDelFreeVas(orderCapture, form, locale);
		addSecondDelCancelVasItemList(orderCapture.getSecondDelServiceProfile(), form);
		
		if (orderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()==Selection.USE_PIPB_DN
				&& orderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().isDuplexInd()
				&& orderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexAction()==DuplexAction.PORT_IN_TOGETHER) {
			form.setNew2ndDelDn(LtsSbHelper.getDisplaySrvNum(orderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexDn()));
			form.setNew2ndDelSrvStatus(orderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexDnStatus());
			form.setPortInDuplexDn(true);
			form.setCreate2ndDel(true);
			form.setSecondDelProfileValid(true);
			orderCapture.setCreate2ndDelByPipbDnInd(true);
			orderCapture.setSecondDelServiceProfile(null);			
			orderCapture.setLtsAcqOtherVoiceServiceForm(form);
		} else {
			if (form!=null && form.isPortInDuplexDn()) {
				form.setNew2ndDelDn(null);
				form.setNew2ndDelSrvStatus(null);
				form.setPortInDuplexDn(false);
				form.setCreate2ndDel(false);
				form.setSecondDelProfileValid(false);
				orderCapture.setCreate2ndDelByPipbDnInd(false);
				orderCapture.setSecondDelServiceProfile(null);
				orderCapture.setLtsAcqOtherVoiceServiceForm(form);
	        }
		}
		
		return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		String locale = RequestContextUtils.getLocale(request).toString();
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		LtsAcqOtherVoiceServiceFormDTO form = (LtsAcqOtherVoiceServiceFormDTO) command;
		List<LtsAcqNumberSelectionInfoDTO> dnList = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
		String sessionId = orderCapture.getLtsAcqNumSelectionAndPipbForm().getSessionId();
		List <String> sList = new ArrayList<String>();
		
		if (orderCapture == null || form.getFormAction() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		ModelAndView mav = new ModelAndView(viewName);
		switch (form.getFormAction()) {
		case SUBMIT:			
						
			if (form.getCreate2ndDel()) {
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
				
				if(orderCapture.getCustomerDetailProfileLtsDTO() == null
						&& orderCapture.getSecondDelServiceProfile() != null){
					orderCapture.setCustomerDetailProfileLtsDTO(orderCapture.getSecondDelServiceProfile().getPrimaryCust());
				}
		    	
			}			
			
			// update DN status to Normal(N) for DN not selected
	    	ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
						
			List<ItemDetailDTO> iddVasList = form.getSecondDelIddItemList();
			if(iddVasList != null){
				for(ItemDetailDTO iddVasItem : iddVasList){
					if(iddVasItem.isSelected()){
						ItemAttbDTO[] attributes = iddVasItem.getItemAttbs();
						for(ItemAttbDTO attb : attributes){
							if(LtsConstant.LOOKUP_IDD_PASSWORD_ATTRIBUTE_DESC.equals(attb.getAttbDesc())
									&& StringUtils.isBlank(attb.getAttbValue())){
								Calendar seed = Calendar.getInstance();
								Random generater = new Random(seed.getTimeInMillis());
								int random = generater.nextInt();
								String randomNum = String.valueOf(Math.abs(random%9999));
								while(randomNum.length() < 4){ // 4-character password
									randomNum = "0" + randomNum;
								}
								attb.setAttbValue(randomNum);
							}
						}
					}
				}
			}			
			
			if (orderCapture.getCreate2ndDelByPipbDnInd()!=null && orderCapture.getCreate2ndDelByPipbDnInd()) {
				form.setNew2ndDelDn(LtsSbHelper.getDisplaySrvNum(orderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexDn()));
				form.setNew2ndDelSrvStatus(orderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexDnStatus());
			} else {
				orderCapture.setCreate2ndDelByPipbDnInd(false);
			}
			
			orderCapture.setLtsAcqOtherVoiceServiceForm(form);
	    	
	    	break;
		case SEARCH_DN:
			
			clear2ndDel(orderCapture, form);
			mav.addObject(commandName, form);
			
			form.setNew2ndDelDn(StringUtils.isNotEmpty(form.getNew2ndDelDn()) ? form.getNew2ndDelDn().trim() : "");
			String searchSrvNum = StringUtils.leftPad(form.getNew2ndDelDn(), 12, '0') ;
		
			if (StringUtils.equals(form.getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_WORKING) ) {
				ServiceDetailProfileLtsDTO secondDelProfile = ltsProfileService.retrieve2ndDelServiceProfile(form.getNew2ndDelDn(), bomSalesUser.getUsername());
				validate2ndDel(orderCapture, secondDelProfile, form, locale);
			} else if (StringUtils.equals(form.getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_RESERVED) ) {
	
				String serviceInventSts = ltsProfileService.getServiceInventoryStatus(searchSrvNum, LtsConstant.SERVICE_TYPE_TEL);
				
				if (!StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, serviceInventSts) ) {
						form.setSecondDelMsg(messageSource.getMessage("lts.acq.othersVoiceService.plsReserveDN", new Object[] {}, this.locale)) ;
						form.setSecondDelProfileValid(false);
				}
				else {
					form.setSecondDelMsg(messageSource.getMessage("lts.acq.othersVoiceService.srvNumRetrieve", new Object[] {}, this.locale)) ;
					form.setSecondDelProfileValid(true);
					orderCapture.setCreate2ndDelByReserveDnInd(true);
				}
			}
			
			handleNew2ndDelFreeVas(orderCapture, form, locale);
			return mav;
		case CLEAR_DN:
			form.setNew2ndDelDn(null);
			form.setNew2ndDelSrvStatus(null);
			clear2ndDel(orderCapture, form);
			form.setNumSelectionList(null);
			form.setDnPoolSelectionMethod(false);
			ltsAcqDnPoolService.updDnStatusToNormal(LtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
			orderCapture.setCreate2ndDelByReserveDnInd(false);
			orderCapture.setCreate2ndDelByPipbDnInd(false);
			mav.addObject(commandName, form);
			return mav;
		case CUST_VERIFY:
			form.setSecondDelDocNum(StringUtils.isNotEmpty(form.getSecondDelDocNum()) ? form.getSecondDelDocNum().trim() : "");
			validate2ndDel(orderCapture, orderCapture.getSecondDelServiceProfile(), form, locale);
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
            		sList.add(LtsSbHelper.leftPadSrvNum(form.getNew2ndDelDn()));
            		ltsAcqDnPoolService.updDnStatusToLock(sList, sessionId);
        		}
        		form.setNew2ndDelDn(LtsSbHelper.getDisplaySrvNum(form.getNumSelectionStringList().get(0)));
        		ltsAcqDnPoolService.updDnStatusToConfirm(LtsSbHelper.leftPadSrvNum(form.getNew2ndDelDn()), sessionId);
				form.setSecondDelProfileValid(true);
				orderCapture.setCreate2ndDelByReserveDnInd(false);
				orderCapture.setCreate2ndDelByPipbDnInd(false);
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
//		return new ModelAndView(new RedirectView((orderCapture.isEyeOrder())?nextView:nextView2));
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
	
	private void optOutSecondDelIddItem(AcqOrderCaptureDTO orderCapture, LtsAcqOtherVoiceServiceFormDTO form, String locale) {
		if (!form.getCreate2ndDel() || !form.isSecondDelOptOutIdd()) {
			return;
		}
		
		List<ItemDetailDTO> secondDelOptOutIddItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_IDD_OUT, locale, true, orderCapture.getBasketChannelId(), null);
		
		for (ItemDetailDTO itemDetail : secondDelOptOutIddItemList) {
			itemDetail.setSelected(true);
		}
		form.setSecondDelOptOutIddItemList(secondDelOptOutIddItemList);
	}
	
	private ValidationResultDTO validateItemAttb(LtsAcqOtherVoiceServiceFormDTO form) {
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
		
		return ltsOfferService.validateItemAttb(itemDetailList);
		
	}
	
	private ValidationResultDTO validateExclusiveItem(HttpServletRequest request, LtsAcqOtherVoiceServiceFormDTO form) {
		
		List<ItemDetailDTO> validateItemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (form.getNew2ndDelAutoChangeTpList() != null && !form.getNew2ndDelAutoChangeTpList().isEmpty()) {
			validateItemDetailList.addAll(form.getNew2ndDelAutoChangeTpList());
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
	
	private void handleNew2ndDelFreeVas(AcqOrderCaptureDTO orderCapture, LtsAcqOtherVoiceServiceFormDTO form, String locale) {
		
		List<ItemDetailDTO> new2ndDelAutoOutItemList = new ArrayList<ItemDetailDTO>(); 
		if (orderCapture.getSecondDelServiceProfile() != null) {
			if (ArrayUtils.isEmpty(orderCapture.getSecondDelServiceProfile().getItemDetails())) {
				return;
			}
			String[] freeVasItemIds = offerItemService.generateFreeVasItem(orderCapture.getSecondDelServiceProfile().getItemDetails(), LtsConstant.DUMMY_SECOND_DEL_DEVICE_TYPE, null, orderCapture.getBasketChannelId(), LtsConstant.ORDER_TYPE_SB_ACQUISITION);
			
			
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
				ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_FREE, locale, true, true, orderCapture.getBasketChannelId(), null);
			form.setNew2ndDelAutoChangeTpList(new2ndDelFreeVasItemList);
		}
		else if (form.isDnPoolSelectionMethod()) {
			List<ItemDetailDTO> new2ndDelFreeVasItemList = 
				ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_FREE, locale, true, true, orderCapture.getBasketChannelId(), null);
			form.setNew2ndDelAutoChangeTpList(new2ndDelFreeVasItemList);
		}
		else if(orderCapture.getSecondDelServiceProfile() == null){
			List<ItemDetailDTO> new2ndDelFreeVasItemList = 
				ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_VAS_2DEL_FREE, locale, true, true, orderCapture.getBasketChannelId(), null);
			form.setNew2ndDelAutoChangeTpList(new2ndDelFreeVasItemList);
		}
	}
	
	private void addSecondDelCancelVasItemList(ServiceDetailProfileLtsDTO secondDelProfile , LtsAcqOtherVoiceServiceFormDTO form) {
		
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
	
	private void clear2ndDel(AcqOrderCaptureDTO orderCapture, LtsAcqOtherVoiceServiceFormDTO form) {
		resetSearch2ndDelForm(orderCapture, form);
		orderCapture.setSecondDelServiceProfile(null);
		orderCapture.setCreate2ndDelByReserveDnInd(null);
	}
	
	private void resetSearch2ndDelForm(AcqOrderCaptureDTO orderCapture, LtsAcqOtherVoiceServiceFormDTO form) {
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
		form.setSecondDelCancelVasItemList(null);
	}

	private void validate2ndDel(AcqOrderCaptureDTO orderCapture,
			ServiceDetailProfileLtsDTO secondDelProfile,
			LtsAcqOtherVoiceServiceFormDTO form, String locale) {

		if (secondDelProfile == null) {
			form.setSecondDelMsg(messageSource.getMessage("lts.acq.othersVoiceService.serviceNumberNotFound", new Object[] {}, this.locale));
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
		
		form.setSecondDelMsg(messageSource.getMessage("lts.acq.othersVoiceService.serviceProfileRetrieved", new Object[] {}, this.locale));
		form.setSecondDelProfileValid(true);
		orderCapture.setSecondDelServiceProfile(secondDelProfile);
	}
	
	private boolean verifyCustomer(AcqOrderCaptureDTO orderCapture, CustomerDetailProfileLtsDTO secondDelCust,
			LtsAcqOtherVoiceServiceFormDTO form) {
		
		String errorMsg = null;
		
		switch (form.getFormAction()) {
		case SEARCH_DN:
			if(orderCapture.getCustomerDetailProfileLtsDTO() != null){
				if(!isUnderSameCust(orderCapture.getCustomerDetailProfileLtsDTO(), secondDelCust)) {
					errorMsg = messageSource.getMessage("lts.acq.othersVoiceService.serviceNumberUnderDiffCust", new Object[] {}, this.locale);
				}
			}
			break;
		case CUST_VERIFY:
			 if (!isUnderSameCust(form, secondDelCust)) {
				errorMsg = messageSource.getMessage("lts.acq.othersVoiceService.documentTypeNNumberNotMatch", new Object[] {}, this.locale);
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
	
		form.setSecondDelDiffCust(false);
		return true;
	}
	
	private boolean isAllowConfirmSameIa(AcqOrderCaptureDTO orderCapture, AddressDetailProfileLtsDTO secondDelProfileAddress) {
		String targetDistrictCode = orderCapture.getLtsAddressRolloutForm().getDistrictCode();
		String targetAreaCode = orderCapture.getLtsAddressRolloutForm().getAreaCode();
		
		if (StringUtils.equalsIgnoreCase(targetDistrictCode,
				secondDelProfileAddress.getDistrictCd())
				&& StringUtils.equalsIgnoreCase(targetAreaCode,
						secondDelProfileAddress.getAreaCd())) {
			return true;
		}
		return false;
	}
	
	private boolean verifyProfileAddress(AcqOrderCaptureDTO orderCapture, 
			AddressDetailProfileLtsDTO secondDelProfileAddress, LtsAcqOtherVoiceServiceFormDTO form) {
		LtsAddressRolloutFormDTO targetAddressRollout = orderCapture.getLtsAddressRolloutForm();
		if (!isUnderSameAddress(targetAddressRollout, secondDelProfileAddress, orderCapture)) {			
			form.setSecondDelMsg(messageSource.getMessage("lts.acq.othersVoiceService.serviceNumUnderDiffInstallAddress", new Object[] {}, this.locale));
			form.setSecondDelDiffAddress(true);
			form.setAllowSecondDelConfirmSameAddress(isAllowConfirmSameIa(orderCapture, secondDelProfileAddress));			
			return false;
		}
		form.setSecondDelDiffAddress(false);
		return true;
	}
	
	private boolean validateServiceProfile(ServiceDetailProfileLtsDTO secondDelProfile, LtsAcqOtherVoiceServiceFormDTO form) {
		
		String errorMsg = null;
		
		if (!StringUtils.equals("DEL", secondDelProfile.getDatCd())) {
			errorMsg = messageSource.getMessage("lts.acq.othersVoiceService.serviceTypeNotTEL", new Object[] {}, this.locale);
		}
		else if (!StringUtils.equals(secondDelProfile.getInventStatus(), form.getNew2ndDelSrvStatus())) {
			errorMsg = messageSource.getMessage("lts.acq.othersVoiceService.invalidServiceLineStatus", new Object[] {}, this.locale);
		}
		else if (!StringUtils.equals(secondDelProfile.getTariff(), "R")) {
			errorMsg = messageSource.getMessage("lts.acq.othersVoiceService.tariffNotResidential", new Object[] {}, this.locale);
		}
		
		if (StringUtils.isNotEmpty(errorMsg)) {
			form.setSecondDelMsg(errorMsg);
			form.setSecondDelProfileValid(false);
			return false;
		}
		form.setSecondDelProfileValid(true);
		return true;
	}
	
	private boolean isUnderSameAddress(LtsAddressRolloutFormDTO targetIa, 
			AddressDetailProfileLtsDTO profileIa, AcqOrderCaptureDTO orderCapture) {		
		if (orderCapture.isChannelDirectSales()) { // Special checking for channel direct sales
			if (!StringUtils.equals(targetIa.getServiceBoundary(), profileIa.getSrvBdry())) {
				if (StringUtils.isBlank(targetIa.getLotNum())) { // IA is not village type, further check build_XY
					String targetIaBuildXy = addressDetailLtsService.getAddressBuildXy(targetIa.getServiceBoundary());
					String profileIaBuildXy = addressDetailLtsService.getAddressBuildXy(profileIa.getSrvBdry());					
					if (StringUtils.isBlank(targetIaBuildXy) || StringUtils.isBlank(profileIaBuildXy)
							|| !StringUtils.equals(targetIaBuildXy, profileIaBuildXy)) {
						return false;
					}
				} else { // Village type
					return false;
				}
			}			 
		} else {
			if (!StringUtils.equalsIgnoreCase(targetIa.getServiceBoundary(), profileIa.getSrvBdry())) {
				return false;
			}
			if (!StringUtils.equalsIgnoreCase(targetIa.getFloor(), profileIa.getFloorNum())) {
				return false;
			}
			if (!StringUtils.equalsIgnoreCase(targetIa.getFlat(), profileIa.getUnitNum())) {
				return false;
			}
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
	
	private boolean isUnderSameCust(LtsAcqOtherVoiceServiceFormDTO form,
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
    private List<LtsAcqNumberSelectionInfoDTO> searchDnFromDnPool(LtsAcqOtherVoiceServiceFormDTO form, String sessionId) {
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
				numberSelectionObj.setDisplaySrvNum(LtsSbHelper.getDisplaySrvNum(list.get(i)));
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
				numberSelectionObj.setDisplaySrvNum(LtsSbHelper.getDisplaySrvNum(list.get(i)));
				result.add(numberSelectionObj);						
			}
		}
		return result;
    }
    
    private void setReferenceData(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture) throws Exception {    
		request.setAttribute("isChannelDS", acqOrderCapture.isChannelDirectSales());
		request.setAttribute("isArpuAllow2Del", isArpuAllow2Del(acqOrderCapture));
    }
    
    private boolean isArpuAllow2Del(AcqOrderCaptureDTO acqOrderCapture) {
		if(acqOrderCapture.getSelectedBasket()!=null && StringUtils.isBlank(acqOrderCapture.getSelectedBasket().getEffectivePrice())){
			return true;	// Temporary fix allowing Basket without value of the effective price to have 2DEL option.
		}
		else{
			return acqOrderCapture.getSelectedBasket()!=null
			&& Double.parseDouble(acqOrderCapture.getSelectedBasket().getEffectivePrice()) 
			>= LtsConstant.MININUM_APRU_FOR_CREATE_2DEL;
		}
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

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}
	
	/**
	 * @return the ltsAcqDnPoolService
	 */
	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	/**
	 * @param ltsAcqDnPoolService the ltsAcqDnPoolService to set
	 */
	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}

	/**
	 * @return the addressDetailLtsService
	 */
	public AddressDetailLtsService getAddressDetailLtsService() {
		return addressDetailLtsService;
	}

	/**
	 * @param addressDetailLtsService the addressDetailLtsService to set
	 */
	public void setAddressDetailLtsService(
			AddressDetailLtsService addressDetailLtsService) {
		this.addressDetailLtsService = addressDetailLtsService;
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
