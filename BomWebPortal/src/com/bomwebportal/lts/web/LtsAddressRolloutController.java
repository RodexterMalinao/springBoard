package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeDuplexFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO;
import com.bomwebportal.lts.service.LtsAddressRolloutService;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAddressRolloutController extends SimpleFormController{

	
	private final String viewName = "ltsaddressrollout";
	private final String nextView = "ltsmodemarrangement.html";
	private final String commandName = "ltsAddressRolloutCmd";
	
	protected LtsProfileService ltsProfileService;
	protected LtsAppointmentService ltsAppointmentService;
	protected LtsAddressRolloutService ltsAddressRolloutService;
	protected LtsOfferService ltsOfferService;
	private   MessageSource messageSource;
	private   Locale locale;
	
	public MessageSource getMessageSource() {
        return messageSource;
    }

	public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}

	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

	public LtsAddressRolloutService getLtsAddressRolloutService() {
		return ltsAddressRolloutService;
	}

	public void setLtsAddressRolloutService(
			LtsAddressRolloutService ltsAddressRolloutService) {
		this.ltsAddressRolloutService = ltsAddressRolloutService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsAddressRolloutController() {
		setCommandClass(LtsAddressRolloutFormDTO.class);
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
		
		if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			handleRetentionOrder(orderCapture);
			return new ModelAndView(new RedirectView(nextView));
		}
		
		
		return super.handleRequestInternal(request, response);
		
	}
	
	private void handleRetentionOrder(OrderCaptureDTO orderCapture) {
		LtsAddressRolloutFormDTO form = new LtsAddressRolloutFormDTO();
		form.setExternalRelocate(false);
		postSubmit(orderCapture, form);
	}
	
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		request.setAttribute("addressDistrictList", LookupTableBean.getInstance().getAddressDistrictList());
		
		if(orderCapture != null && orderCapture.getLtsServiceProfile().getAddress().getAddressRollout().isEyeCoverage()){
			LtsSRDDTO earliestSRD = ltsAppointmentService.getEarliestSRD(orderCapture);
			request.setAttribute("earliestSRD", earliestSRD.getDateString());
			request.setAttribute("earliestSRDReason", LtsAppointmentHelper.getEarilestSrdReason(earliestSRD, RequestContextUtils.getLocale(request)));
		}
		
		if (orderCapture == null || orderCapture.getLtsAddressRolloutForm() == null) {
			LtsAddressRolloutFormDTO form = new LtsAddressRolloutFormDTO();
			 if(orderCapture != null && orderCapture.getLtsMiscellaneousForm() != null){
				 form.setDnchange(orderCapture.getLtsMiscellaneousForm().isDnChange());
			 }
			return form;
		}
		
		
		return orderCapture.getLtsAddressRolloutForm();
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		LtsAddressRolloutFormDTO form = (LtsAddressRolloutFormDTO) command;
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		ValidationResultDTO result = validateAddressRollout(orderCapture, form, bomSalesUser);
		
		if (Status.INVAILD == result.getStatus()) {
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			mav.addObject("errorMsgList", result.getMessageList());
			mav.addObject("addressAreaList", LookupTableBean.getInstance().getAddressAreaList());
			return mav;
		}
		
		postSubmit(orderCapture, form);
		
		if(form.isExternalRelocate() && form.isDnchange()){
			String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
			String locale = RequestContextUtils.getLocale(request).toString();
			List<ItemDetailDTO> rebateItemList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_DN_CHANGE_WAIVE, locale, true, orderCapture.getBasketChannelId(), applicationDate);

			LtsDnChangeFormDTO dnChangeForm = orderCapture.getLtsDnChangeForm();
			if(dnChangeForm != null && dnChangeForm.getChargeItem() != null){
				ItemDetailDTO chargeItem = dnChangeForm.getChargeItem();
				if(StringUtils.equals(chargeItem.getPenaltyHandling() , LtsBackendConstant.OFFER_HANDLE_AUTO_GENERATE)
						&& rebateItemList != null){
						dnChangeForm.setRebateItem(rebateItemList.get(0));	
						dnChangeForm.getRebateItem().setSelected(true);
				}
				chargeItem.setPenaltyHandling(LtsBackendConstant.PENALTY_ACTION_AUTO_WAIVE);
			}
			
			LtsDnChangeDuplexFormDTO duplexForm = orderCapture.getLtsDnChangeDuplexForm();
			if(duplexForm != null && duplexForm.getChargeItem() != null){
				if(StringUtils.equals(duplexForm.getChargeItem().getPenaltyHandling() , LtsBackendConstant.OFFER_HANDLE_AUTO_GENERATE)
						&& rebateItemList != null){
					duplexForm.setRebateItem(rebateItemList.get(0));
					duplexForm.getRebateItem().setSelected(true);
				}
				duplexForm.getChargeItem().setPenaltyHandling(LtsBackendConstant.PENALTY_ACTION_AUTO_WAIVE);
			}
		}
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	private void postSubmit(OrderCaptureDTO orderCapture, LtsAddressRolloutFormDTO form) {
		if (form.isExternalRelocate()) {
			AddressRolloutDTO newAddressRollout =(AddressRolloutDTO)orderCapture.getNewAddressRollout();
			newAddressRollout.setFullAddress(getFullAddress(form));	
		}
		else if (form.isPonSbRequired()) {
			AddressRolloutDTO newAddressRollout =(AddressRolloutDTO)orderCapture.getNewAddressRollout();
			newAddressRollout.setFullAddress(orderCapture.getLtsServiceProfile().getAddress().getFullAddress());	
		}
		else {
			AddressRolloutDTO profileAddressRollout =(AddressRolloutDTO)orderCapture.getLtsServiceProfile().getAddress().getAddressRollout();
			if (profileAddressRollout != null) {
				try {
					orderCapture.setNewAddressRollout((AddressRolloutDTO)BeanUtils.cloneBean(profileAddressRollout));
				} catch (Exception e) {
					logger.error("LtsAddressRolloutController - Clone profile address rollout :" + e);
					throw new AppRuntimeException(e);
				} 
			}
		}
		
		ltsProfileService.setTenure(orderCapture.getLtsServiceProfile(),
				orderCapture.getNewAddressRollout().getFlat(), orderCapture.getNewAddressRollout().getFloor(), 
				orderCapture.getNewAddressRollout().getSrvBdary());
		
		clearSubmittedForm(form);
		orderCapture.setLtsAddressRolloutForm(form);
		orderCapture.setLtsModemArrangementForm(null);
	}
	
	
	
	
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("addressAreaList", LookupTableBean.getInstance().getAddressAreaList());
		referenceData.put("addressDistrictList", LookupTableBean.getInstance().getAddressDistrictList());
		referenceData.put("addressSectionList", LookupTableBean.getInstance().getAddressSectionList());
		
		return referenceData;
	}
	
	
	private void clearSubmittedForm(LtsAddressRolloutFormDTO form) {
		
		if (form.isExternalRelocate()) {
			form.setPonSbRequired(false);
			form.setPonSbNum(null);
		}
		else {
			form.setAreaCode(null);
			form.setAreaDesc(null);
//			form.setBaCaRemainUnchange(false);
			form.setBaCaAction(null);
			form.setBuildingName(null);
			form.setDistrictCode(null);
			form.setDistrictDesc(null);
			form.setFlat(null);
			form.setFloor(null);
			form.setLotHouseInd(null);
			form.setLotNum(null);
			form.setQuickSearch(null);
			form.setSectionCode(null);
			form.setSectionDesc(null);
			form.setServiceBoundary(null);
			form.setStreetCatgCode(null);
			form.setStreetCatgDesc(null);
			form.setStreetName(null);
			form.setStreetNum(null);
		}
		
	}
	
	
	private String getFullAddress(LtsAddressRolloutFormDTO form) {
		
		StringBuilder targetIa = new StringBuilder();
		if (StringUtils.isNotBlank(form.getFlat())) {
			targetIa.append("FLAT ").append(form.getFlat()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getFloor())) {
			targetIa.append(form.getFloor()).append("/FLOOR ").append(", ");	
		}
		if (StringUtils.isNotBlank(form.getBuildingName())) {
			targetIa.append(form.getBuildingName()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getSectionDesc())) {
			targetIa.append(form.getSectionDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getLotNum())) {
			targetIa.append("HSE ").append(form.getLotNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getStreetNum())) {
			targetIa.append(form.getStreetNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getStreetName())) {
			targetIa.append(form.getStreetName()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getStreetCatgDesc())) {
			targetIa.append(form.getStreetCatgDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getDistrictDesc())) {
			targetIa.append(form.getDistrictDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getAreaDesc())) {
			targetIa.append(form.getAreaDesc()).append(", ");	
		}
		return targetIa.substring(0, targetIa.length()-2).toString();
	}

	
	public ValidationResultDTO validateAddressRollout(OrderCaptureDTO orderCapture, LtsAddressRolloutFormDTO form, BomSalesUserDTO bomSalesUser) {
		
		List<String> messageList = new ArrayList<String>();
		AddressRolloutDTO addressRollout = orderCapture.getLtsServiceProfile().getAddress().getAddressRollout();
		String sb = orderCapture.getLtsServiceProfile().getAddress().getSrvBdry();

		
		if (form.isExternalRelocate() || form.isPonSbRequired()) {
			addressRollout = orderCapture.getNewAddressRollout();
			sb = form.getServiceBoundary();
		}
		
		if (addressRollout == null) {
			//messageList.add("Cannot found address rollout information [service boundary:" + sb + "]." + erMsg);
			messageList.add(messageSource.getMessage("lts.addressRollout.rolloutInfo", new Object[] {sb}, this.locale));
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}

		if ( StringUtils.isNotBlank(addressRollout.getHktPremier())) {
			//JT BOM2017062 starts
			if (Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(bomSalesUser.getCategory())) {
				return new ValidationResultDTO(Status.VALID, messageList, null);
			}
			//JT BOM2017062 ends
			
			else {
			if (!orderCapture.isChannelPremier()) {
				//messageList.add("HKT Premier Address (" + addressRollout.getHktPremier() + "). \nPlease contact HKT Premier Team.");
				messageList.add(messageSource.getMessage("lts.addressRollout.hktPreAddr", new Object[] {addressRollout.getHktPremier()}, this.locale));
				return new ValidationResultDTO(Status.INVAILD, messageList, null);	
			}
				else {
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
						//messageList.add("HKT Premier Address (" + addressRollout.getHktPremier() + "). \nPlease contact related HKT Premier Team.");
						messageList.add(messageSource.getMessage("lts.addressRollout.hktPreAddr", new Object[] {addressRollout.getHktPremier()}, this.locale));
						return new ValidationResultDTO(Status.INVAILD, messageList, null);	
					}
				}
			}
		}
		else {
			if (orderCapture.isChannelPremier()) {
				messageList.add(messageSource.getMessage("lts.addressRollout.massAddr", new Object[] {}, this.locale));
				return new ValidationResultDTO(Status.INVAILD, messageList, null);	
			}
		}
		
		
		if (!addressRollout.isEyeCoverage()) {
			messageList.add(messageSource.getMessage("lts.addressRollout.extRelocation", new Object[] {}, this.locale));
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}
		
		return new ValidationResultDTO(Status.VALID, messageList, null);
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
}
