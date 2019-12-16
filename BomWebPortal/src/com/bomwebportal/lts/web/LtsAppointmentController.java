package com.bomwebportal.lts.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO.DuplexSrvType;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialInputDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsRelatedPcdOrderService;
import com.bomwebportal.lts.service.LtsUpgradeEyeOrderService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.service.order.OrderPostSubmitLtsService;
import com.bomwebportal.lts.service.order.ServiceActionTypeLookupService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAppointmentController extends SimpleFormController {
	
	
	private LtsRelatedPcdOrderService ltsRelatedPcdOrderService;
	private LtsAppointmentService ltsAppointmentService;
	private AppointmentDwfmService appointmentDwfmService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private CodeLkupCacheService ltsAppointmentDeviceBasicTLkupCacheService;
	private CodeLkupCacheService ltsAppointmentBasketBasicTLkupCacheService;
	private ServiceActionTypeLookupService serviceActionTypeLookupService;
	private LtsUpgradeEyeOrderService ltsUpgradeEyeOrderService;
	private OrderPostSubmitLtsService orderPostSubmitLtsService;
	private LtsOfferService ltsOfferService;
	private Locale locale;
	private MessageSource messageSource;
	
	private final int APPOINTMENT_BB_SHORTAGE_LEADTIME = 60;
	private final int APPOINTMENT_BLACKLIST_LEADTIME = 30;

	private final String viewName = "ltsappointment";
	private final String nextView = "ltssupportdoc.html";
	private final String commandName = "ltsappointment";
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}
	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}
	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}
	public LtsRelatedPcdOrderService getLtsRelatedPcdOrderService() {
		return ltsRelatedPcdOrderService;
	}
	public void setLtsRelatedPcdOrderService(
			LtsRelatedPcdOrderService ltsRelatedPcdOrderService) {
		this.ltsRelatedPcdOrderService = ltsRelatedPcdOrderService;
	}
	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}
	public void setAppointmentDwfmService(AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}
	
	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}
	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}
	
	public CodeLkupCacheService getLtsAppointmentDeviceBasicTLkupCacheService() {
		return ltsAppointmentDeviceBasicTLkupCacheService;
	}
	public void setLtsAppointmentDeviceBasicTLkupCacheService(
			CodeLkupCacheService ltsAppointmentDeviceBasicTLkupCacheService) {
		this.ltsAppointmentDeviceBasicTLkupCacheService = ltsAppointmentDeviceBasicTLkupCacheService;
	}
	public ServiceActionTypeLookupService getServiceActionTypeLookupService() {
		return serviceActionTypeLookupService;
	}
	public void setServiceActionTypeLookupService(
			ServiceActionTypeLookupService serviceActionTypeLookupService) {
		this.serviceActionTypeLookupService = serviceActionTypeLookupService;
	}
	public LtsUpgradeEyeOrderService getLtsUpgradeEyeOrderService() {
		return ltsUpgradeEyeOrderService;
	}
	public void setLtsUpgradeEyeOrderService(
			LtsUpgradeEyeOrderService ltsUpgradeEyeOrderService) {
		this.ltsUpgradeEyeOrderService = ltsUpgradeEyeOrderService;
	}
	public OrderPostSubmitLtsService getOrderPostSubmitLtsService() {
		return orderPostSubmitLtsService;
	}
	public void setOrderPostSubmitLtsService(
			OrderPostSubmitLtsService orderPostSubmitLtsService) {
		this.orderPostSubmitLtsService = orderPostSubmitLtsService;
	}
	
	public CodeLkupCacheService getLtsAppointmentBasketBasicTLkupCacheService() {
		return ltsAppointmentBasketBasicTLkupCacheService;
	}
	public void setLtsAppointmentBasketBasicTLkupCacheService(
			CodeLkupCacheService ltsAppointmentBasketBasicTLkupCacheService) {
		this.ltsAppointmentBasketBasicTLkupCacheService = ltsAppointmentBasketBasicTLkupCacheService;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		setLocale(RequestContextUtils.getLocale(request));
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		
		LtsAppointmentFormDTO ltsAppointmentForm = orderCaptureDTO.getLtsAppointmentForm();
		SimpleDateFormat df = new SimpleDateFormat();

			
		if(ltsAppointmentForm == null){
			ltsAppointmentForm = new LtsAppointmentFormDTO();
			orderCaptureDTO.setLtsAppointmentForm(ltsAppointmentForm);
			ltsAppointmentForm.setConfirmedInd(LtsConstant.FALSE_IND);
			ltsAppointmentForm.setSecDelInd(LtsConstant.FALSE_IND);
			ltsAppointmentForm.setSecDelCutOverInd(LtsConstant.FALSE_IND);
			ltsAppointmentForm.setCutOverInd(LtsConstant.FALSE_IND);
			ltsAppointmentForm.setBbShortageInd(LtsConstant.FALSE_IND);
			ltsAppointmentForm.setPcdOrderExistInd(LtsConstant.FALSE_IND);
			ltsAppointmentForm.setSubmitInd("SUBMIT");
			String upgradeDn = StringUtils.right(orderCaptureDTO.getLtsServiceProfile().getSrvNum(), 8);
			ltsAppointmentForm.setInstallationContactNum(upgradeDn);
			ltsAppointmentForm.setInstallationContactName(ltsAppointmentForm.getDefaultContactName());
			
			
		}else if(ltsAppointmentForm.getInstallationTime() != null){
			if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType())
					|| StringUtils.isNotEmpty(orderCaptureDTO.getLtsServiceProfile().getExistEyeType())) {
				if(ltsAppointmentService.getChangePonTimeSlotInd(orderCaptureDTO)){
					ltsAppointmentForm.setInstallationTime(
							LtsDateFormatHelper.convertToPonTime(ltsAppointmentForm.getInstallationTime()));
					ltsAppointmentForm.setSecDelInstallationTime(
							LtsDateFormatHelper.convertToPonTime(ltsAppointmentForm.getSecDelInstallationTime()));
				}	
			}
			
			ltsAppointmentForm.setInstallationTime(LtsDateFormatHelper.convertToSBTime(ltsAppointmentForm.getInstallationTime()));
			ltsAppointmentForm.setSecDelInstallationTime(LtsDateFormatHelper.convertToSBTime(ltsAppointmentForm.getSecDelInstallationTime()));
		}
		
		
		if (!LtsConstant.DOC_TYPE_HKBR.equals(orderCaptureDTO.getLtsServiceProfile().getPrimaryCust().getDocType())) {
			CustomerDetailProfileLtsDTO custProfile = orderCaptureDTO.getLtsServiceProfile().getPrimaryCust();
			if(LtsSbOrderHelper.isRecontractOrder(orderCaptureDTO)){
				custProfile = orderCaptureDTO.getLtsRecontractForm().getCustDetailProfile();
			}
			ltsAppointmentForm.setDefaultContactName(custProfile.getTitle() + " " + custProfile.getLastName() + " " + custProfile.getFirstName());
			ltsAppointmentForm.setDefaultContactNum(orderCaptureDTO.getLtsCustomerIdentificationForm().getCustomerContactMobileNum());
		}

		
		boolean fieldVisitRequired = ltsOfferService.isSelectedNewDeviceFieldService(orderCaptureDTO);
		boolean weeeRequired = LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType()) || ltsOfferService.isRenewOfferWithNewDevice(orderCaptureDTO);
		ltsAppointmentForm.setFieldVisitRequired(fieldVisitRequired);
		
		//BOM2018061
		if(weeeRequired){
			ltsAppointmentForm.setEpdItemList(
					ltsAppointmentService.getNewEpdItemList(
							orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate(), 
							RequestContextUtils.getLocale(request).toString(), ltsAppointmentForm.getEpdItemList()));
		}else{
			ltsAppointmentForm.setEpdItemList(null);
		}
		
		request.setAttribute("appDate", orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
		if (!fieldVisitRequired) {
			ltsAppointmentForm.setInstallationTime(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
			ltsAppointmentForm.setInstallationType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
		}

		ltsAppointmentForm.setTentativeInd(LtsConstant.FALSE_IND);
		request.setAttribute("showPcdOrderId", false);
		request.setAttribute("sharePCD", false);
		request.setAttribute("allowAppt", true);
		ltsAppointmentForm.setSharePCDInd(LtsConstant.FALSE_IND);
		
		//share PCD order
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType())) {
			if(ModemType.SHARE_BUNDLE.equals(orderCaptureDTO.getLtsModemArrangementForm().getModemType())
					|| ModemType.SHARE_PCD.equals(orderCaptureDTO.getLtsModemArrangementForm().getModemType())){
				
				request.setAttribute("sharePCD", true);
				ltsAppointmentForm.setSharePCDInd(LtsConstant.TRUE_IND);
				boolean pcdOrderExist = setupPcdOrderDetail(ltsAppointmentForm, orderCaptureDTO.getRelatedPcdOrder());
				boolean edfNumExist = StringUtils.isNotBlank(orderCaptureDTO.getLtsModemArrangementForm().getEdfRefNum());
				
				request.setAttribute("showPcdOrderId", (pcdOrderExist || (!pcdOrderExist && !edfNumExist)));
				request.setAttribute("allowAppt", edfNumExist);
				//request.setAttribute("showEdfNum", !edfNumExist && !pcdOrderExist);
				
			}	
		}
		
		LtsSRDDTO srd = null;
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCaptureDTO.getOrderType())) {
			if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCaptureDTO.getOrderSubType())){
				srd = ltsAppointmentService.getStandaloneVasEarliestSRD(orderCaptureDTO);
			}else{
				srd = ltsAppointmentService.getRetEarliestSRD(orderCaptureDTO, fieldVisitRequired);
				
				if (srd.isBackDateAppln()) {
					ltsAppointmentForm.setInstallationDate(srd.getDateString());
				}
			}
		}
		else {
			//generate earliest SRD
			srd = ltsAppointmentService.getEarliestSRD(orderCaptureDTO, LtsAppointmentHelper.getToday(), false);
			
			//check for min T+ day by device
			String deviceType = orderCaptureDTO.getLtsDeviceSelectionForm().getSelectedDeviceType();

			if(deviceType != null){
				LookupItemDTO[] basicTArray = ltsAppointmentDeviceBasicTLkupCacheService.getCodeLkupDAO().getCodeLkup();
				LtsSRDDTO newSRD = addAdditionSrd(basicTArray, deviceType, srd);
				if (newSRD != null) {
					srd = newSRD;
				}
			}
		}
			
		BasketDetailDTO selectedBasket = orderCaptureDTO.getSelectedBasket();
		if (selectedBasket != null) {
			LookupItemDTO[] basicTArray = ltsAppointmentBasketBasicTLkupCacheService.getCodeLkupDAO().getCodeLkup();
			LtsSRDDTO newSRD = addAdditionSrd(basicTArray, StringUtils.defaultIfEmpty(selectedBasket.getBaseBasketId(), selectedBasket.getBasketId()), srd);
			if (newSRD != null) {
				srd = newSRD;
			}
		}
		
		if (srd.getBmoPermit() != null) {
			ltsAppointmentForm.setBmoRemark(srd.getBmoPermit().getBmoRemark());
		}
		
		//set description for earliest srd
		df.applyPattern("dd/MM/yyyy");
		request.setAttribute("earliestSRD", df.format(srd.getDate().getTime()));
		Calendar today = new GregorianCalendar();
		
		int srdDiff = Days.daysBetween(new DateTime(srd.getDate()), DateTime.now()).getDays();
		request.setAttribute("srdDiffence", srdDiff);
		
		String earliestSrdReason = LtsAppointmentHelper.getEarilestSrdReason(srd, RequestContextUtils.getLocale(request));
				
		request.setAttribute("earliestSrdReason", earliestSrdReason);
		ltsAppointmentForm.setEarliestSrdReason(earliestSrdReason);
		
		
		ltsAppointmentForm.setEarliestSRD(srd);
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType())) {
			//check tentative
			if(ltsAppointmentService.isBBShortage(orderCaptureDTO)){
				ltsAppointmentForm.setBbShortageInd(LtsConstant.TRUE_IND);
				ltsAppointmentForm.setTentativeInd(LtsConstant.TRUE_IND);
			}else{
				ltsAppointmentForm.setBbShortageInd(LtsConstant.FALSE_IND);
				if("02".equals(srd.getReasonCd())
					|| "07".equals(srd.getReasonCd())
					|| "08".equals(srd.getReasonCd())){
					ltsAppointmentForm.setTentativeInd(LtsConstant.TRUE_IND);
				}else{
					ltsAppointmentForm.setTentativeInd(LtsConstant.FALSE_IND);
				}
			}
			
			//put tentative values
			if(LtsConstant.TRUE_IND.equals(ltsAppointmentForm.getTentativeInd())){
				if(LtsConstant.FALSE_IND.equals(ltsAppointmentForm.getSharePCDInd())){
					today = new GregorianCalendar();
					LtsAppointmentHelper.clearTimeField(today);
					if(LtsConstant.TRUE_IND.equals(ltsAppointmentForm.getBbShortageInd())){
						today.add(Calendar.DATE, APPOINTMENT_BB_SHORTAGE_LEADTIME);
					}else{
						today.add(Calendar.DATE, APPOINTMENT_BLACKLIST_LEADTIME);
					}
					df.applyPattern("dd/MM/yyyy");
					String dateString = df.format(today.getTime());
					ltsAppointmentForm.setInstallationDate(dateString);
					ltsAppointmentForm.setInstallationTime(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
					ltsAppointmentForm.setInstallationType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
				}
			}
			
			if(orderCaptureDTO.getLtsAddressRolloutForm().isExternalRelocate()){
				request.setAttribute("externalRelocate", true);
			}
		}
		
		boolean isCreate2ndDel = false;
		if(orderCaptureDTO.getLtsOtherVoiceServiceForm().getCreate2ndDel() != null){
			isCreate2ndDel = orderCaptureDTO.getLtsOtherVoiceServiceForm().getCreate2ndDel();
			ltsAppointmentForm.setSecDelInd(isCreate2ndDel ? LtsConstant.TRUE_IND : LtsConstant.FALSE_IND);
		}
		
		setupCutOverDtl(ltsAppointmentForm, orderCaptureDTO);
		setupSecDelDtl(ltsAppointmentForm, orderCaptureDTO);
		
		if(isCreate2ndDel){
			LtsSRDDTO secDelEarliestSRD = ltsAppointmentService.getSecDelEarliestSRD(orderCaptureDTO, LtsAppointmentHelper.getToday(), null);
			if(secDelEarliestSRD.getDate().after(srd.getDate()) || LtsConstant.TRUE_IND.equals(ltsAppointmentForm.getTentativeInd())){
				ltsAppointmentForm.setSecDelEarliestSRD(secDelEarliestSRD);
				request.setAttribute("secDelEarliestSRD", df.format(secDelEarliestSRD.getDate().getTime()));
				String secDelEarliestSrdReason = LtsAppointmentHelper.getEarilestSrdReason(secDelEarliestSRD, RequestContextUtils.getLocale(request));
				ltsAppointmentForm.setSecDelEarliestSrdReason(secDelEarliestSrdReason);
			}else{
				ltsAppointmentForm.setSecDelEarliestSRD(null);
				ltsAppointmentForm.setSecDelEarliestSrdReason(null);
			}
			if(LtsConstant.TRUE_IND.equals(ltsAppointmentForm.getTentativeInd())){
				ltsAppointmentForm.setSecDelInstallationDate(ltsAppointmentForm.getInstallationDate());
				ltsAppointmentForm.setSecDelInstallationTime(ltsAppointmentForm.getInstallationTime());
				ltsAppointmentForm.setSecDelInstallationType(ltsAppointmentForm.getInstallationType());
			}
		}
		
		if (!ltsAppointmentForm.isSecDelFieldVisitRequired()) {
			ltsAppointmentForm.setSecDelInstallationTime(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
			ltsAppointmentForm.setSecDelInstallationType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
//			request.removeAttribute("secDelEarliestSRD");
//			ltsAppointmentForm.setSecDelEarliestSRD(null);
//			ltsAppointmentForm.setSecDelEarliestSrdReason(null);
		}
		
		setAdjustmentCalInfo(orderCaptureDTO, ltsAppointmentForm);

		//BOM2018061
		ltsAppointmentForm.setEpdLeadDay(ltsAppointmentService.getEpdLeadTime());
		
		// 13 Oct add reminder alert if a renewal with new device with auto upgrade VDSL
		request.setAttribute("technologyChange", isTechnologyChange(orderCaptureDTO));
		
		return ltsAppointmentForm;
	}
	
	
	private boolean isTechnologyChange(OrderCaptureDTO orderCapture) {
		FsaDetailDTO selectedFsa = LtsSbOrderHelper.getSelectedFsa(orderCapture.getLtsModemArrangementForm());
		ModemTechnologyAissgnDTO modemTechnologyAissgn = orderCapture.getModemTechnologyAissgn();
		if (selectedFsa != null && modemTechnologyAissgn != null) {
			if (!StringUtils.equalsIgnoreCase(selectedFsa.getTechnology(), modemTechnologyAissgn.getTechnology())) {
				return true;
			}
		}
		return false;
	}
	
	
	private void setupCutOverDtl(LtsAppointmentFormDTO form, OrderCaptureDTO order){
		LtsOtherVoiceServiceFormDTO otherVoiceForm = order.getLtsOtherVoiceServiceForm();
		
		form.setCutOverInd(LtsConstant.FALSE_IND);
		
		boolean isTwoN = false;
		
		if(otherVoiceForm.isDuplexProfile()){ //is duplex
			if(otherVoiceForm.getDuplexXSrvType() == DuplexSrvType.UPGRADE){
				isTwoN = order.getLtsServiceProfile().isTwoNBuildInd();
			}else if(otherVoiceForm.getDuplexYSrvType() == DuplexSrvType.UPGRADE){
				isTwoN = order.getLtsServiceProfile().isDuplexTwoNBuildInd();
			}
		}else{
			//check 2n for non-duplex upgrade order only
			if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(order.getOrderType())){ 
				isTwoN = order.getLtsServiceProfile().isTwoNBuildInd();
			}
		}

		form.setCutOverInd(isTwoN? LtsConstant.TRUE_IND:LtsConstant.FALSE_IND);
		
	}

	private void setupSecDelDtl(LtsAppointmentFormDTO form, OrderCaptureDTO order){
		LtsOtherVoiceServiceFormDTO otherVoiceForm = order.getLtsOtherVoiceServiceForm();

		form.setSecDelInd(LtsConstant.FALSE_IND);
		form.setSecDelFieldVisitRequired(false);
		form.setSecDelCutOverInd(LtsConstant.FALSE_IND);

		if(otherVoiceForm != null){
			if(BooleanUtils.isTrue(otherVoiceForm.getCreate2ndDel())){
				boolean isSecDelTwoN = false;
				boolean isFieldVisitRequried = false;
				if(otherVoiceForm.isDuplexProfile()){
					if(otherVoiceForm.getDuplexXSrvType() == DuplexSrvType.NEW_2ND_DEL ){
						isSecDelTwoN = order.getLtsServiceProfile().isTwoNBuildInd();
						isFieldVisitRequried = true;
					}else if(otherVoiceForm.getDuplexYSrvType() == DuplexSrvType.NEW_2ND_DEL ){
						isSecDelTwoN = order.getLtsServiceProfile().isDuplexTwoNBuildInd();
						isFieldVisitRequried = true;
					}
				}
				
				if (LtsConstant.INVENT_STS_RESERVED.equals(otherVoiceForm.getNew2ndDelSrvStatus())
						|| LtsConstant.INVENT_STS_PRE_ASSIGN.equals(otherVoiceForm.getNew2ndDelSrvStatus())
						|| otherVoiceForm.isDnPoolSelectionMethod()
						|| (otherVoiceForm.getSecondDelEr() != null 
								&& otherVoiceForm.getSecondDelEr())) {
					isFieldVisitRequried = true;
				}
				form.setSecDelInd(LtsConstant.TRUE_IND);
				form.setSecDelFieldVisitRequired(isFieldVisitRequried);
				form.setSecDelCutOverInd(isSecDelTwoN?LtsConstant.TRUE_IND:LtsConstant.FALSE_IND);
			}
		}
		
	}

	private LtsSRDDTO addAdditionSrd(LookupItemDTO[] basicTArray, String targetValue, LtsSRDDTO srd) {
		
		if (ArrayUtils.isEmpty(basicTArray) || srd == null) {
			return null;
		}
		
		for(LookupItemDTO deviceMinT: basicTArray){
			if(StringUtils.equals(targetValue, deviceMinT.getItemKey())){
				String minTString = (String)deviceMinT.getItemValue();
				int minT = Integer.parseInt(minTString);
				if(srd.getLeadTime()+srd.getSkippedTime() < minT){
					LtsSRDDTO newSrd = new LtsSRDDTO();
					newSrd.setLeadTime(minT);
					newSrd.setReasonCd("32");
					newSrd.setInfo("Selected Specific Basket");
					return newSrd;
				}
			}
		}
		return null;
	}
	
	
	// Just use LtsSRDDTO to do date compare 
	private void setAdjustmentCalInfo(OrderCaptureDTO orderCapture, LtsAppointmentFormDTO form) {
		
		String contractEndDate = orderCapture.getSbOrder() != null ? LtsSbOrderHelper
				.getExpiredContractEndDate(orderCapture.getSbOrder()) : LtsSbOrderHelper
				.getExpiredContractEndDate(orderCapture.getLtsServiceProfile());
		
		String endedProfileContractEndDate = LtsSbOrderHelper.getEndedContractEndDate(orderCapture.getLtsServiceProfile());
				
		BasketDetailDTO selectedBasket = orderCapture.getSelectedBasket();
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				|| LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())
				|| !orderCapture.isChannelCs()
				|| orderCapture.getLtsMiscellaneousForm().isBackDateOrder()
				|| !(LtsConstant.BASKET_CATEGORY_REBATE.equals(selectedBasket.getBasketCatg())
						|| LtsConstant.BASKET_CATEGORY_REBATE_COUPON.equals(selectedBasket.getBasketCatg()))) {
			form.setAllowAdjustment(false);
			return;
		}
		
		String sysdate = LtsDateFormatHelper.getSysDate("dd/MM/yyyy");
		LtsSRDDTO startDate = null;
		
		int maxBackDateDays = -120;
		if (StringUtils.isNotEmpty(contractEndDate)) {
			int contractEndDateDiff = 
				LtsDateFormatHelper.getDateDiffInDays(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), contractEndDate, "dd/MM/yyyy") + 1;
			if (contractEndDateDiff  > maxBackDateDays) {
				maxBackDateDays = contractEndDateDiff;
			}
		}
		if (StringUtils.isNotEmpty(endedProfileContractEndDate)) {
			int contractEndDateDiff = 
				LtsDateFormatHelper.getDateDiffInDays(LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), endedProfileContractEndDate, "dd/MM/yyyy") + 1;
			if (contractEndDateDiff  > maxBackDateDays) {
				maxBackDateDays = contractEndDateDiff;
			}
		}
//		
//		if (StringUtils.isNotEmpty(contractEndDate)) {
//			
//			int dayDiff = LtsDateFormatHelper.getDateDiffInDays(contractEndDate, sysdate, "dd/MM/yyyy");
//			
//			if (dayDiff > 120) {
//				startDate = new LtsSRDDTO(sysdate);
//				startDate.addDate(-120);
//			}
//			else {
//				startDate = new LtsSRDDTO(contractEndDate);
//				startDate.addDate(1);
//			}
//			form.setAdjStartDate(startDate.getDateString());
//		}
//		else {
			startDate = new LtsSRDDTO(sysdate);
			startDate.addDate(maxBackDateDays);
//			if (maxBackDateDays != -120) {
//				startDate.addDate(1);
//			}
			form.setAdjStartDate(startDate.getDateString());
//		}

		LtsSRDDTO endDate = new LtsSRDDTO(form.getEarliestSRD().getDateString());
		endDate.addDate(-1);
		form.setAdjEndDate(endDate.getDateString());
		form.setAllowAdjustment(true);	
	}
	
	private void confirmRenewalAppointment(OrderCaptureDTO orderCapture, LtsAppointmentFormDTO form) {
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return;
		}
		
		// Get Prebook Serial for install 2nd DEL
		if (LtsConstant.TRUE_IND.equals(form.getSecDelInd())) {
			if (form.isSecDelFieldVisitRequired()) {
				String secDelInstallDate = form.getSecDelInstallationDate();
				String secDelInstallTime = form.getSecDelInstallationTime();
				String secDelInstallType = form.getSecDelInstallationType();
				prebookAppointment(orderCapture, form, secDelInstallDate, secDelInstallTime, secDelInstallType);
			}
		}
		else {
			form.setSecDelInstallationTime(null);
			form.setSecDelInstallationDate(null);
			form.setSecDelInstallationType(null);
		}
		
		form.setConfirmedInd(LtsConstant.TRUE_IND);
	}
	
	private void confirmUpgradeAppointment(OrderCaptureDTO orderCapture, LtsAppointmentFormDTO form) {
		if (!form.isFieldVisitRequired()) {
			return;
		}
		
		if (!StringUtils.equals(form.getTentativeInd(), LtsConstant.TRUE_IND)) {
			String installDate = form.getInstallationDate();
			String installTime = form.getInstallationTime();
			String installType = form.getInstallationType();
			prebookAppointment(orderCapture, form, installDate, installTime, installType);
		}
		
    	if(form.getSecDelInd().equals(LtsConstant.FALSE_IND)){
    		form.setSecDelInstallationTime(null);
    		form.setSecDelInstallationDate(null);
		}
	}
	private void prebookAppointment(OrderCaptureDTO orderCapture, LtsAppointmentFormDTO form, String installDate, String installTime, String installType) {
		
		if (StringUtils.isEmpty(installType) || StringUtils.isEmpty(installDate) || StringUtils.isEmpty(installTime)) {
			return;
		}
		
		PrebookAppointmentInputDTO input = ltsAppointmentService
				.getPrebookAppointmentInput(orderCapture, installDate,
						installTime, orderCapture.getLtsSalesInfoForm().getStaffId(), installType);
		
		PrebookAppointmentOutputDTO output = appointmentDwfmService.getPrebookAppointment(input);
		
		if(output == null || StringUtils.isEmpty(output.getSerialNum()) 
				|| !StringUtils.equals("0", output.getReturnValue())) {
			form.setPreBookSerialNum(null);
			form.setConfirmedInd(LtsConstant.FALSE_IND);
			form.setErrorMsg(StringUtils.defaultIfEmpty(output.getErrorMsg(), messageSource.getMessage("lts.appointmentImpl.apptFailed", new Object[] {}, this.locale)));	
		}
		
		form.setPreBookSerialNum(output.getSerialNum());
		form.setConfirmedInd(LtsConstant.TRUE_IND);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		LtsAppointmentFormDTO ltsAppointmentForm = (LtsAppointmentFormDTO)command;

		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
		
	    if(ltsAppointmentForm.getSubmitInd().compareTo("CONFIRM") == 0){
			
	    	if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType())
	    			|| ltsAppointmentForm.isFieldVisitRequired()) {
		    	confirmUpgradeAppointment(orderCaptureDTO, ltsAppointmentForm);
			}
	    	
			if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCaptureDTO.getOrderType())) {
		    	confirmRenewalAppointment(orderCaptureDTO, ltsAppointmentForm);
			}
			
			orderCaptureDTO.setLtsAppointmentForm(ltsAppointmentForm);
	    	return new ModelAndView(new RedirectView("ltsappointment.html"));
	    	
	    }else if(ltsAppointmentForm.getSubmitInd().compareTo("SUBMIT") == 0){
	    	
	    	//BOM2018061
	    	ValidationResultDTO validationResult = ltsOfferService.validateItemAttb(ltsAppointmentForm.getEpdItemList());
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, ltsAppointmentForm);
				mav.addObject("epdErrorMsgList", validationResult.getMessageList());
				return mav;
			}
	    	//BOM2018061 END
	    	
	    	ltsAppointmentForm.setEarliestSrdReason("");
			if("02".equals(ltsAppointmentForm.getEarliestSRD().getReasonCd())){
				ltsAppointmentForm.setEarliestSrdReason(messageSource.getMessage("lts.appointmentImpl.imsBlacklist", new Object[] {APPOINTMENT_BLACKLIST_LEADTIME}, this.locale));
			}else if("08".equals(ltsAppointmentForm.getEarliestSRD().getReasonCd())){
				ltsAppointmentForm.setEarliestSrdReason(messageSource.getMessage("lts.appointmentImpl.ltsBlacklist", new Object[] {APPOINTMENT_BLACKLIST_LEADTIME}, this.locale));
			}else if("07".equals(ltsAppointmentForm.getEarliestSRD().getReasonCd())){
				ltsAppointmentForm.setEarliestSrdReason(messageSource.getMessage("lts.appointmentImpl.blacklistCust", new Object[] {APPOINTMENT_BLACKLIST_LEADTIME}, this.locale));
				
			}
			if(LtsConstant.TRUE_IND.equals(ltsAppointmentForm.getBbShortageInd())){
				ltsAppointmentForm.setEarliestSrdReason(ltsAppointmentForm.getEarliestSrdReason().concat(messageSource.getMessage("lts.appointmentImpl.bbShortage", new Object[] {APPOINTMENT_BB_SHORTAGE_LEADTIME}, this.locale)));
				
			}
			orderCaptureDTO.setLtsAppointmentForm(ltsAppointmentForm);
			return new ModelAndView(new RedirectView(nextView));
			
		} else if(ltsAppointmentForm.getSubmitInd().compareTo("CANCEL") == 0){
			CancelPrebookSerialInputDTO input = new CancelPrebookSerialInputDTO ();
			input.setSerialNum(ltsAppointmentForm.getPreBookSerialNum());
			input.setLoginID(orderCaptureDTO.getLtsSalesInfoForm().getStaffId());
			CancelPrebookSerialOutputDTO cancel = appointmentDwfmService.cancelPrebookSerial(input);
			ltsAppointmentForm.setPreBookSerialNum(null);
			ltsAppointmentForm.setErrorMsg(messageSource.getMessage("lts.appointmentImpl.apptCancelled", new Object[] {}, this.locale));
			orderCaptureDTO.setLtsAppointmentForm(ltsAppointmentForm);
	    	return new ModelAndView(new RedirectView("ltsappointment.html"));
		} else if(ltsAppointmentForm.getSubmitInd().compareTo("CLEAR") == 0){
			ltsAppointmentForm.setErrorMsg(null);
			ltsAppointmentForm.setInstallationTime(null);
			ltsAppointmentForm.setInstallationDate(null);
			ltsAppointmentForm.setInstallationType(null);
			orderCaptureDTO.setLtsAppointmentForm(ltsAppointmentForm);
			return new ModelAndView(new RedirectView("ltsappointment.html"));
		} else if(ltsAppointmentForm.getSubmitInd().compareTo("EDF") == 0){
			orderCaptureDTO.setLtsAppointmentForm(ltsAppointmentForm);
			request.setAttribute("allowAppt", true);
			return new ModelAndView(new RedirectView("ltsappointment.html"));
		}else {
		    paramMap.put(LtsConstant.REQUEST_PARAM_FROM_VIEW, viewName);
		    paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, ltsAppointmentForm.getSuspendReason());
		}
		
		return new ModelAndView(new RedirectView(LtsConstant.UPGRADE_EYE_ORDER_VIEW), paramMap);
	}
	
	protected Map<String, List> referenceData(HttpServletRequest request) throws Exception {
		Map<String, List> referenceData = new HashMap<String, List>();
		referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		LtsAppointmentFormDTO ltsAppointmentForm = orderCaptureDTO.getLtsAppointmentForm();
		boolean eveningSlotInd = false;
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType())
				|| ltsAppointmentForm.isFieldVisitRequired()
				|| StringUtils.isNotEmpty(orderCaptureDTO.getLtsServiceProfile().getExistEyeType())) {
			eveningSlotInd = ltsAppointmentService.getChangePonTimeSlotInd(orderCaptureDTO);
		}
		
		if(ltsAppointmentForm != null){
			String installationDate = ltsAppointmentForm.getInstallationDate();
			String secDelInstallationDate = ltsAppointmentForm.getSecDelInstallationDate();
			String secDelInstallationTime = ltsAppointmentForm.getSecDelInstallationTime();
			
			if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCaptureDTO.getOrderType()) && !ltsAppointmentForm.isFieldVisitRequired()) {
				List<ResourceSlotDTO> timeSlot = new ArrayList<ResourceSlotDTO>();
				ResourceSlotDTO time = new ResourceSlotDTO();
				time.setApptTimeSlot(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				time.setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
				timeSlot.add(time);
				referenceData.put("installationTimeSlot", timeSlot);
				ltsAppointmentForm.setInstallationType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
				
				if(ltsAppointmentForm.getSecDelInd().equals(LtsConstant.TRUE_IND)
						&& !StringUtils.isBlank(secDelInstallationDate) && !StringUtils.isBlank(secDelInstallationTime)){
					setSecDelTimeSlotReference(orderCaptureDTO, ltsAppointmentForm, 
							secDelInstallationDate, installationDate, eveningSlotInd, request, referenceData);
				}
			}
			else if(LtsConstant.FALSE_IND.equals(ltsAppointmentForm.getTentativeInd())){
				if(!StringUtils.isBlank(installationDate)){
					List<ResourceSlotDTO> installationTimeSlot = new ArrayList<ResourceSlotDTO>();
					ResourceDetailInputDTO resourceInput = null;
					if(ltsAppointmentForm.getInstallationDate().equals(ltsAppointmentForm.getSecDelInstallationDate())){
						resourceInput = ltsAppointmentService.getResourceDetailInput(orderCaptureDTO, installationDate, LtsConstant.DWFM_INPUT_TYPE_ALL);
					}else{
						resourceInput = ltsAppointmentService.getResourceDetailInput(orderCaptureDTO, installationDate, LtsConstant.DWFM_INPUT_TYPE_UPGRADE);
					}
					
					try{
					ResourceDetailOutputDTO resource = appointmentDwfmService.getResourceDetail(resourceInput);
					if(resource != null && resource.getResourceSlots().length > 0){
						for(int i = 0; i < resource.getResourceSlots().length; i++){
							if(StringUtils.equals("Y", resource.getResourceSlots()[i].getRestrictInd())){
								resource.getResourceSlots()[i].setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT);
							}else if(!StringUtils.equals("Y", resource.getResourceSlots()[i].getAvailableInd())){
								resource.getResourceSlots()[i].setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE);
							}
							if(eveningSlotInd){
								resource.getResourceSlots()[i].setApptTimeSlot(
										LtsDateFormatHelper.convertToPonTime(resource.getResourceSlots()[i].getApptTimeSlot()));
							}
							resource.getResourceSlots()[i].setApptTimeSlot(
									LtsDateFormatHelper.convertToSBTime(resource.getResourceSlots()[i].getApptTimeSlot()));
							installationTimeSlot.add(resource.getResourceSlots()[i]);
						}
					}
					}catch(NullPointerException npe){
						npe.printStackTrace();
					}
					referenceData.put("installationTimeSlot", installationTimeSlot);
					
				}
				
				if(ltsAppointmentForm.getSecDelInd().equals(LtsConstant.TRUE_IND)
						&& !StringUtils.isBlank(secDelInstallationDate) && !StringUtils.isBlank(secDelInstallationTime)){
					setSecDelTimeSlotReference(orderCaptureDTO, ltsAppointmentForm, 
							secDelInstallationDate, installationDate, eveningSlotInd, request, referenceData);
				}
			}else{
				List<ResourceSlotDTO> timeSlot = new ArrayList<ResourceSlotDTO>();
				ResourceSlotDTO time = new ResourceSlotDTO();
				time.setApptTimeSlot(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				time.setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
				timeSlot.add(time);
				referenceData.put("installationTimeSlot", timeSlot);
				referenceData.put("secDelTimeSlot", timeSlot);
				ltsAppointmentForm.setInstallationType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
			}
		}
		
		return referenceData;
		
	}

	private void setSecDelTimeSlotReference(OrderCaptureDTO orderCaptureDTO, LtsAppointmentFormDTO ltsAppointmentForm
			, String secDelInstallationDate, String installationDate, boolean ponInd, HttpServletRequest request, Map referenceData){
		List<ResourceSlotDTO> timeSlot = new ArrayList<ResourceSlotDTO>();
		if(secDelInstallationDate.equals(installationDate) && LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType())){
			timeSlot = (List<ResourceSlotDTO>) referenceData.get("installationTimeSlot");
		}else{
			ResourceDetailInputDTO resourceInput = ltsAppointmentService.getResourceDetailInput(orderCaptureDTO, secDelInstallationDate, LtsConstant.DWFM_INPUT_TYPE_2NDDEL);
			try{
			ResourceDetailOutputDTO resource = appointmentDwfmService.getResourceDetail(resourceInput);
			if(resource != null && resource.getResourceSlots().length > 0){
				for(int i = 0; i < resource.getResourceSlots().length; i++){
					if(StringUtils.equals(resource.getResourceSlots()[i].getRestrictInd(), "Y")){
						resource.getResourceSlots()[i].setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_RESTRICT);
					}else if(!StringUtils.equals(resource.getResourceSlots()[i].getAvailableInd(), "Y")){
						resource.getResourceSlots()[i].setApptTimeSlotType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_UNAVAILABLE);
					}
					if(ponInd){
						resource.getResourceSlots()[i].setApptTimeSlot(
								LtsDateFormatHelper.convertToPonTime(resource.getResourceSlots()[i].getApptTimeSlot()));
					}
					resource.getResourceSlots()[i].setApptTimeSlot(
							LtsDateFormatHelper.convertToSBTime(resource.getResourceSlots()[i].getApptTimeSlot()));
					timeSlot.add(resource.getResourceSlots()[i]);
				}
			}
			}catch(NullPointerException npe){
				npe.printStackTrace();
			}
		}
		referenceData.put("secDelTimeSlot", timeSlot);
	}

	private boolean setupPcdOrderDetail(LtsAppointmentFormDTO ltsAppointmentForm, ImsSbOrderDTO pcdOrder){
		if(pcdOrder != null){
			ltsAppointmentForm.setPcdOrderExistInd(LtsConstant.TRUE_IND);
			ltsAppointmentForm.setPCDOrderId(pcdOrder.getOrderId());
			ltsAppointmentForm.setPreBookSerialNum(pcdOrder.getSerialNum());

			if(StringUtils.isNotBlank(pcdOrder.getAppntStartTime())
					&& StringUtils.isNotBlank(pcdOrder.getAppntEndTime())){
				String apptDate = LtsAppointmentHelper.getDateFromDwfmFormat(pcdOrder.getAppntStartTime());
				//apptdate == srd; normal case
				ltsAppointmentForm.setInstallationDate(apptDate);
				String slot = LtsAppointmentHelper.getTimeFromDwfmFormat(pcdOrder.getAppntStartTime());
				slot = slot.concat("-").concat(LtsAppointmentHelper.getTimeFromDwfmFormat(pcdOrder.getAppntEndTime()));
				ltsAppointmentForm.setInstallationTime(slot);
				ltsAppointmentForm.setInstallationType(pcdOrder.getAppntType());
				if("PON".equals(pcdOrder.getTechnology())){
					ltsAppointmentForm.setInstallationTime(LtsDateFormatHelper.convertToPonTime(slot));
				}
				ltsAppointmentForm.setInstallationTime(LtsDateFormatHelper.convertToSBTime(slot));
			}else{
				//apptdate == null; resource shortage
				ltsAppointmentForm.setInstallationDate(LtsAppointmentHelper.getDateFromDwfmFormat(pcdOrder.getSrvReqDate()));
				ltsAppointmentForm.setInstallationTime(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				ltsAppointmentForm.setInstallationType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
			}
			
			ltsAppointmentForm.setInstallationContactName(pcdOrder.getInstContactName());
			ltsAppointmentForm.setInstallationContactNum(pcdOrder.getInstContactNum());
			ltsAppointmentForm.setInstallationMobileSMSAlert(pcdOrder.getInstSmsNum());
			return true;
		}else{
			ltsAppointmentForm.setPcdOrderExistInd(LtsConstant.FALSE_IND);
			return false;
		}
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
