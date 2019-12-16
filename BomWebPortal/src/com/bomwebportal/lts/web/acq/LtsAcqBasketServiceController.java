package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO.ItemCriteriaBuilder;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketServiceFormDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsDeviceService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqBasketServiceController extends SimpleFormController {

	private final String viewName = "lts/acq/ltsacqbasketservice";
	private final String nextView = "ltsacqbasketvasdetail.html";
	private final String commandName = "ltsAcqBasketServiceCmd";
	
	protected LtsOfferService ltsOfferService;
	protected LtsDeviceService ltsDeviceService;
	protected CodeLkupCacheService installFeeArpuLkupCacheService;
	protected LtsBasketService ltsBasketService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqBasketServiceController(){
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsAcqBasketServiceFormDTO.class);
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
		String selectedBasketId = order.getLtsAcqBasketSelectionForm().getSelectedBasketId();
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		LtsAcqBasketServiceFormDTO form = new LtsAcqBasketServiceFormDTO();
		String basketChannel = order.getBasketChannelId();
		BasketDetailDTO selectedBasketDetail = ltsBasketService.getBasket(selectedBasketId, LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
		String osType = selectedBasketDetail.getOsType();
		ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getAcqItemCriteriaDefaultBuilder(basketChannel, locale, osType).setBasketId(selectedBasketId).build();
		
		List<ItemDetailDTO> planItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_PLAN).build());
		List<ItemDetailDTO> bvasItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_BVAS).build());
		List<ItemDetailDTO> moovItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_MOOV).build());
		List<ItemDetailDTO> contentItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_CONTENT).build());
		
		ItemSetCriteriaDTO contItemCriteria = LtsSbOrderHelper.getItemSetCriteria(order, bomSalesUser, LtsConstant.ITEM_SET_TYPE_CONT_SET, locale);
		contItemCriteria.setOsType(osType);
		List<ItemSetDetailDTO> contItemSetList = ltsOfferService.getBasketItemSetList(contItemCriteria);
		
		form.setPlanItemList(planItemList);
		form.setBvasItemList(bvasItemList);
		form.setMoovItemList(moovItemList);
		form.setContentItemList(contentItemList);
		form.setContItemSetList(contItemSetList);
		

//		addBbRentalItemList(orderCapture, form, locale);
		addInstallationFeeItemList(order, form, locale);
//		addUpgradeInfoItem(orderCapture, form, locale);
		return form;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String locale = RequestContextUtils.getLocale(request).toString();
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		LtsAcqBasketServiceFormDTO form = (LtsAcqBasketServiceFormDTO)command;
		
		//form.setExcludeQuotaCheck(ltsOfferService.isExcludeQuotaCheck(form.getPlanItemList(), form.getContItemSetList()));
		form.setExcludeQuotaCheck(false); // no passby
		
		ValidationResultDTO[] validationResults = validateSubmit(request, form);
		
		for (ValidationResultDTO validationResult : validationResults) {
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}	
		}

		addInstallationFeeItemList(orderCapture, form, locale);
		orderCapture.setLtsAcqBasketServiceForm(form);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private ValidationResultDTO[] validateSubmit(HttpServletRequest request, LtsAcqBasketServiceFormDTO form) {
		
		List<ValidationResultDTO> validationResultList = new ArrayList<ValidationResultDTO>();

		validationResultList.add(validateExclusiveItem(request, form));
		validationResultList.add(validateItemQuota(form));
		validationResultList.add(ltsOfferService.validateItemAttb(form.getPlanItemList()));
		
		return validationResultList.toArray(new ValidationResultDTO[validationResultList.size()]);
	}
	
	@SuppressWarnings("unchecked")
	private ValidationResultDTO validateItemQuota(LtsAcqBasketServiceFormDTO form){
		List<String> outQuotaItemList = new ArrayList<String>();
		List<String> errorMsgList = new ArrayList<String>();
		if(form.getContItemSetList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemSetDescList(form.getContItemSetList()));
		if(form.getMoovItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getMoovItemList()));
		if(form.getContentItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getContentItemList()));		
		if(form.getPlanItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getPlanItemList()));
		if(form.getBvasItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getBvasItemList()));		
		if(form.getInstallItemList()!=null)
			outQuotaItemList.addAll(ltsOfferService.getOutQuotaItemDescList(form.getInstallItemList()));
		
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
	
	private ValidationResultDTO validateExclusiveItem(HttpServletRequest request, LtsAcqBasketServiceFormDTO form) {
		
		List<ItemDetailDTO> validateItemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (form.getMoovItemList() != null && !form.getMoovItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getMoovItemList());
		}
		
		if (form.getContentItemList() != null && !form.getContentItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getContentItemList());
		}
		
		if (form.getContItemSetList() != null && !form.getContItemSetList().isEmpty()) {
			for (ItemSetDetailDTO itemSetDetail : form.getContItemSetList()) {
				if (ArrayUtils.isEmpty(itemSetDetail.getItemDetails())) {
					continue;
				}
				validateItemDetailList.addAll((List<ItemDetailDTO>)Arrays.asList(itemSetDetail.getItemDetails()));
			}
		}
		
		return ltsOfferService.validateExclusiveItem(validateItemDetailList, validateItemDetailList, RequestContextUtils.getLocale(request).toString());

	}
	
//	private boolean isProfileRebateTermPlan(AcqOrderCaptureDTO orderCapture) {
//		ItemDetailProfileLtsDTO[] profileItems = orderCapture.getLtsServiceProfile().getItemDetails();
//		
//		if (ArrayUtils.isNotEmpty(profileItems)) {
//			for (ItemDetailProfileLtsDTO profileItem : profileItems) {
//				if (StringUtils.equals(LtsConstant.PROFILE_ITEM_TYPE_SERVICE, profileItem.getItemType())
//						&& StringUtils.contains(LtsConstant.TP_CATG_REBATE, profileItem.getItemDetail().getTpCatg())) {
//					return true;
//				}
//			}
//		}
//		
//		return false;
//	}
		
	private void addInstallationFeeItemList(AcqOrderCaptureDTO order, LtsAcqBasketServiceFormDTO form, String locale) {
		
		String selectedBasketId = order.getLtsAcqBasketSelectionForm().getSelectedBasketId();
		List<ItemDetailDTO> installItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_INSTALL, locale, true, order.getBasketChannelId(), null);
		List<ItemDetailDTO> installWaiveItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_INSTALL_WAIVE, locale, true, order.getBasketChannelId(), null);
		
		if (installItemList == null || installItemList.isEmpty()) {
			form.setInstallItemList(installWaiveItemList);
			return;
		}
		
		boolean waiveInstallationFee = false;
//		if (isProfileRebateTermPlan(orderCapture)) {
//			waiveInstallationFee = true;
//		}
	          
		if (ltsOfferService.isItemSelected(form.getContentItemList(), LtsConstant.ITEM_TYPE_CONTENT)
				|| ltsOfferService.isItemSelected(form.getMoovItemList(), LtsConstant.ITEM_TYPE_MOOV)) {
			waiveInstallationFee = true;
		}
		
//		Object installFeeArpu = installFeeArpuLkupCacheService.get(LtsConstant.CODE_LKUP_INSTALL_FEE_ARPU);
//		if (installFeeArpu != null && StringUtils.isNotEmpty((String)installFeeArpu)) {
//			double profileTpArpu = LtsSbOrderHelper.getProfileTpArpu(orderCapture.getLtsServiceProfile());
//			if (profileTpArpu < Double.parseDouble((String)installFeeArpu)) {
//				waiveInstallationFee = true;
//			}
//		}
		
		form.setInstallItemList(waiveInstallationFee ? installWaiveItemList : installItemList);
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsDeviceService getLtsDeviceService() {
		return ltsDeviceService;
	}

	public void setLtsDeviceService(LtsDeviceService ltsDeviceService) {
		this.ltsDeviceService = ltsDeviceService;
	}

	public CodeLkupCacheService getInstallFeeArpuLkupCacheService() {
		return installFeeArpuLkupCacheService;
	}

	public void setInstallFeeArpuLkupCacheService(
			CodeLkupCacheService installFeeArpuLkupCacheService) {
		this.installFeeArpuLkupCacheService = installFeeArpuLkupCacheService;
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

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}
	
}
