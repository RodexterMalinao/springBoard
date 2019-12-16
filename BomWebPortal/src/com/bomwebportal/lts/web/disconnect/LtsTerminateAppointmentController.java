package com.bomwebportal.lts.web.disconnect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.Idd0060ProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialInputDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsAddressRolloutService;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsRetrieveFsaService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.service.disconnect.LtsTerminateOrderService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.disconnect.LtsTerminateConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsTerminateAppointmentController extends SimpleFormController {
	private final String viewName = "lts/disconnect/ltsterminateappointment";
	private final String nextView = "ltsterminatesupportdoc.html";
	private final String commandName = "ltsterminateappointmentCmd";
	
	private LtsAddressRolloutService ltsAddressRolloutService;
	private LtsAppointmentService ltsAppointmentService;
	private LtsTerminateOrderService ltsTerminateOrderService;
	private AppointmentDwfmService appointmentDwfmService;
	private CodeLkupCacheService ltsAppointmentDeviceBasicTLkupCacheService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private LtsRetrieveFsaService ltsRetrieveFsaService;
	private ImsProfileService imsProfileService;
	
	public LtsTerminateAppointmentController() {
		setFormView(viewName);
		setCommandName(commandName);
		setCommandClass(LtsTerminateAppointmentFormDTO.class);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TerminateOrderCaptureDTO orderCaptureDTO = (TerminateOrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request,response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		TerminateOrderCaptureDTO orderCaptureDTO = (TerminateOrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
		
		LtsTerminateAppointmentFormDTO form = orderCaptureDTO.getLtsTerminateAppointmentForm();
		
		if(form == null){
			form = new LtsTerminateAppointmentFormDTO();
			orderCaptureDTO.setLtsTerminateAppointmentForm(form);
			form.setConfirmedInd(LtsConstant.FALSE_IND);
			String upgradeDn = StringUtils.right(orderCaptureDTO.getLtsServiceProfile().getSrvNum(), 8);
			form.setCustContactNum(upgradeDn);
			
			if (!LtsConstant.DOC_TYPE_HKBR.equals(orderCaptureDTO.getLtsServiceProfile().getPrimaryCust().getDocType())) {
				form.setCustContactName(orderCaptureDTO.getLtsServiceProfile().getPrimaryCust().getLastName() 
						+ " " 
						+ orderCaptureDTO.getLtsServiceProfile().getPrimaryCust().getFirstName());
			}
			
		}

		form.setFieldVisitRequired(isAppntRequired(orderCaptureDTO));
        form.setAllowBackDate(isAllowBackDate(orderCaptureDTO)); 
        form.setAllowProceed(isAllowProceed(orderCaptureDTO, form));
        
        if(StringUtils.isBlank(form.getAppntDate())){
    		form.setAppntDate(setupDefaultSrdDate(orderCaptureDTO, form));
        }
        
		if(StringUtils.isBlank(form.getApplicationDate())){
			form.setApplicationDate(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
		}
		
		if(orderCaptureDTO.getLtsTerminateServiceSelectionForm().isLostModem()){
			form.setForceAppntTime(true);
			form.setAppntTimeSlotAndType(LtsConstant.APPOINTMENT_TIMESLOT_WHOLE + LtsAppointmentHelper.TIMESLOT_DELIMITER + LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
		}
		
		return form;
	}
	
	private void getTermPrebookAppointmentInput(String date, String timeSlot, String timeSlotType, LtsTerminateAppointmentFormDTO form, TerminateOrderCaptureDTO orderCapture) {
			PrebookAppointmentInputDTO input = 
				ltsAppointmentService.getTermPrebookAppointmentInput(orderCapture, date, timeSlot, orderCapture.getLtsSalesInfoForm().getStaffId(),timeSlotType);
			PrebookAppointmentOutputDTO output = appointmentDwfmService.getPrebookAppointment(input);
			if(output != null){
				if(output.getSerialNum() != null){
					form.setPreBookSerialNum(output.getSerialNum());
					form.setConfirmedInd(LtsConstant.TRUE_IND);
				}else if(!"0".equals(output.getReturnValue())){
					form.setPreBookSerialNum(null);
					form.setConfirmedInd(LtsConstant.FALSE_IND);
					form.setAppntErrorMsg(output.getErrorMsg());
				}else{
					form.setPreBookSerialNum(null);
					form.setConfirmedInd(LtsConstant.FALSE_IND);
					form.setAppntErrorMsg("Appointment Failed");
				}
			}else {
				form.setConfirmedInd(LtsConstant.TRUE_IND);
			}
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse reqponse,Object command,BindException errors) throws Exception {
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		LtsTerminateAppointmentFormDTO form = (LtsTerminateAppointmentFormDTO)command;

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
		
		if(StringUtils.equals(form.getSubmitInd(), "CONFIRM")){
			if(form.isFieldVisitRequired()){
				String date = form.getAppntDate();
				String timeSlot = form.getAppntTimeSlot();
				String timeSlotType = form.getAppntTimeSlotType();
				if(date != null && timeSlot != null){
					getTermPrebookAppointmentInput(date, timeSlot, timeSlotType, form, orderCapture);
				}else {
					form.setConfirmedInd(LtsConstant.TRUE_IND);
				}
			}else{
				
				form.setConfirmedInd(LtsConstant.TRUE_IND);
			}
			
			orderCapture.setLtsTerminateAppointmentForm(form);
			return new ModelAndView(new RedirectView("ltsterminateappointment.html"));
			
		}else if(StringUtils.equals(form.getSubmitInd(), "SUBMIT")){
			if(!form.isFieldVisitRequired()){
				form.setAppntTimeSlotAndType(
						LtsConstant.APPOINTMENT_TIMESLOT_WHOLE
						+ LtsAppointmentHelper.TIMESLOT_DELIMITER
						+ LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
			}
			
			DateTime appntDate = DateTime.parse(form.getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
			DateTime applicationDate = DateTime.parse(orderCapture.getLtsTerminateServiceSelectionForm().getAppDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
			if(applicationDate.isBefore(appntDate) 
					&& Days.daysBetween(applicationDate, appntDate).getDays() >= 30){
				form.setCeaseRentalDate(form.getAppntDate());
//				DateTime ceaseRentalDate = new DateTime(appntDate);
//				ltsTerminateAppointmentForm.setCeaseRentalDate(ceaseRentalDate.minus(1).toString(DateTimeFormat.forPattern("dd/MM/yyyy")));				
			}else{
				form.setCeaseRentalDate(null);
			}
			form.setBackDate(appntDate.isBeforeNow());
			
			if(LtsSbOrderHelper.isDeceasedCase(orderCapture.getLtsTerminateServiceSelectionForm())){
				form.setFiveNa(false);
				form.setAllowFiveNa(false);
			}
			
			
			// 20160310 get pre-book serial for IMS is not WG.
			if (orderCapture.getLtsServiceProfile().getEyeFsaProfile() != null) {
				FsaServiceType imsSrvType = 
					ltsRetrieveFsaService.getExistSrvType(orderCapture.getLtsServiceProfile().getEyeFsaProfile());
				if (FsaServiceType.WG != imsSrvType) {
					getTermPrebookAppointmentInput(form.getAppntDate(), LtsConstant.APPOINTMENT_TIMESLOT_WHOLE, LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE, form, orderCapture);
				}
			}
			
			orderCapture.setLtsTerminateAppointmentForm(form);
			return new ModelAndView(new RedirectView(nextView));
			
		}else if(StringUtils.equals(form.getSubmitInd(), "CANCEL")){
			CancelPrebookSerialInputDTO input = new CancelPrebookSerialInputDTO ();
			input.setSerialNum(form.getPreBookSerialNum());
			input.setLoginID(orderCapture.getLtsSalesInfoForm().getStaffId());
			appointmentDwfmService.cancelPrebookSerial(input);
			form.setPreBookSerialNum(null);
			form.setAppntErrorMsg("Appointment Cancelled");
			orderCapture.setLtsTerminateAppointmentForm(form);
			return new ModelAndView(new RedirectView("ltsterminateappointment.html"));
		}else{
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, form.getSuspendReason());
		}
		return new ModelAndView(new RedirectView(LtsConstant.TERMINATION_ORDER_VIEW), paramMap);
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		TerminateOrderCaptureDTO order = (TerminateOrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_TERMINATE_ORDER_CAPTURE);
		boolean eveningSlotInd = ltsAppointmentService.getTermChangePonTimeSlotInd(order);
		LtsTerminateAppointmentFormDTO form = order.getLtsTerminateAppointmentForm();

		if(form != null && form.isFieldVisitRequired() && StringUtils.isNotBlank(form.getAppntDate())){
			ResourceDetailInputDTO resourceInput;
			if(order.getSbOrder()!= null){
				resourceInput = ltsAppointmentService.getTermResourceDetailInput(order.getSbOrder(), LtsConstant.DWFM_INPUT_TYPE_DISCONNECT, form.getAppntDate(), LtsSessionHelper.getBomSalesUser(request).getUsername());
			}else{
				resourceInput = ltsAppointmentService.getTermResourceDetailInput(order, form.getAppntDate());
			}
			ResourceDetailOutputDTO resource = appointmentDwfmService.getResourceDetail(resourceInput);
			referenceData.put("appntTimeSlot", LtsAppointmentHelper.setupResourceList(resource, eveningSlotInd));
		}
		

		referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("isDeathCase", LtsSbOrderHelper.isDeceasedCase(order.getLtsTerminateServiceSelectionForm()));
		referenceData.put("imsPendingOrderSrd", getImsPendingOrderSrd(order));
		referenceData.put("contractEndDate", getContractEndDate(order));
		referenceData.put("applicationDate", getApplicationDate(order));
		referenceData.put("isTerminateWithPCD", order.getLtsTerminateServiceSelectionForm().isRemoveNowtv()
				|| order.getLtsTerminateServiceSelectionForm().isRemoveBoardband());
		
		
		return referenceData;
		
	}
	
	private boolean isAllowBackDate(TerminateOrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getSbOrder() != null){
			ServiceDetailLtsDTO eyeService = LtsSbOrderHelper.getLtsEyeService(orderCaptureDTO.getSbOrder());
			if(eyeService != null){
				return false;
			}
		}
		
		if(StringUtils.isNotBlank(orderCaptureDTO.getLtsServiceProfile().getExistEyeType())){
			return false;
		}
		
		if(LtsSbOrderHelper.isBundle2GTp(orderCaptureDTO.getLtsServiceProfile())){
			return false;
		}
		
		ItemDetailProfileLtsDTO profileTp = LtsSbOrderHelper.getProfileServiceTermPlan(orderCaptureDTO.getLtsServiceProfile());
        if (profileTp != null && StringUtils.isNotBlank(profileTp.getIddFreeMin())
                && !StringUtils.equals("0", profileTp.getIddFreeMin())) {
        	return false; 
        }
        
        LtsTerminateServiceSelectionFormDTO ssForm = orderCaptureDTO.getLtsTerminateServiceSelectionForm();
        if(ssForm.getIdd0060ProfileList() != null){
            for(Idd0060ProfileLtsDTO item: ssForm.getIdd0060ProfileList()){
            	if(LtsConstant.IDD_ACTION_REMOVE.equals(item.getAction())){
            		return false;
            	}
            }
        }
        if(ssForm.getIddCallPlanProfileList() != null){
	        for(IddCallPlanProfileLtsDTO item : ssForm.getIddCallPlanProfileList()){
	        	if(LtsConstant.IDD_ACTION_REMOVE.equals(item.getAction())){
	        		return false;
	        	}
	        }
        }
        if(ssForm.isCallingCardInd() && LtsTerminateConstant.CALLING_CARD_HANDLING_DISCONNECT.equals(ssForm.getCallingCardDetailsHandling())){
        	return false;
        }
        
        
        
        return true;
	}
	
	private String getApplicationDate(TerminateOrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getSbOrder() != null){
			return LtsDateFormatHelper.getDateFromDTOFormat(orderCaptureDTO.getSbOrder().getAppDate());
		}else{
			return orderCaptureDTO.getLtsTerminateServiceSelectionForm().getAppDate();
		}
	}
	
	private String getContractEndDate(TerminateOrderCaptureDTO orderCaptureDTO){
		if(orderCaptureDTO.getSbOrder() != null){
			ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(orderCaptureDTO.getSbOrder());
			if(ltsService == null || ArrayUtils.isEmpty(ltsService.getItemDtls())){
				return null;
			}
			for(ItemDetailLtsDTO item : LtsSbOrderHelper.getLtsService(orderCaptureDTO.getSbOrder()).getItemDtls()){
				if(StringUtils.equals(item.getItemType(), LtsConstant.ITEM_LTS_TP) 
						&& StringUtils.equals(item.getProfileItemType(), LtsConstant.PROFILE_ITEM_TYPE_SERVICE)){
					return LtsDateFormatHelper.getDateFromDTOFormat(item.getExistEndDate());
				}
			}
		}else{
			return LtsSbOrderHelper.getProfileContractEndDate(orderCaptureDTO.getLtsServiceProfile());
		}
		
		return null;
	}
	
	private String setupDefaultSrdDate(TerminateOrderCaptureDTO order, LtsTerminateAppointmentFormDTO form){
		if(order.getImsPendingOrder() != null){
			String[] imsPendingOrderSrd = getImsPendingOrderSrd(order);
			if(imsPendingOrderSrd != null){
				String imsSrd = imsPendingOrderSrd[0];
				if(StringUtils.isBlank(form.getAppntTimeSlotAndType()) 
						&& StringUtils.isNotBlank(imsPendingOrderSrd[1])){
					if(!"00:00-00:00".equals(imsPendingOrderSrd[1])){
						form.setAppntTimeSlotAndType(imsPendingOrderSrd[1]);
					}else{
						form.setAppntTimeSlotAndType(
								LtsConstant.APPOINTMENT_TIMESLOT_WHOLE
								+ LtsAppointmentHelper.TIMESLOT_DELIMITER
								+ LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
					}
				}
				return imsSrd;
			}
		}
		if(LtsSbOrderHelper.isDeceasedCase(order.getLtsTerminateServiceSelectionForm())){
			return DateTime.now().plusDays(7).toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
		}else{
			String contractEndDate = getContractEndDate(order);
			DateTime tpSrd = StringUtils.isNotBlank(contractEndDate)? DateTime.parse(contractEndDate, DateTimeFormat.forPattern("dd/MM/yyyy")).plusDays(1) : null;//getTpSrd(order);
			String applicationDate = order.getLtsTerminateServiceSelectionForm().getAppDate();
			DateTime appDateSrd = DateTime.parse(applicationDate, DateTimeFormat.forPattern("dd/MM/yyyy")).plusDays(29);
			if(isValidCed(tpSrd)){
				if(tpSrd == null || appDateSrd.isAfter(tpSrd)){
					return appDateSrd.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
				}else{
					return tpSrd != null? tpSrd.toString(DateTimeFormat.forPattern("dd/MM/yyyy")): null;
				}
			}else{
				if(!form.isAllowBackDate() && appDateSrd.isBeforeNow()){
					if(order.getLtsServiceProfile().getExistEyeType() != null){
						return DateTime.now().plusDays(3).toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
					}else{
						return DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
					}
				}
				return appDateSrd.toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
			}
		}
	}
	
	/* return true if contract end date < current month + 3 month */
	private boolean isValidCed(DateTime ced){
		if(ced == null){
			return false;
		}
		DateTime now = DateTime.now();
		DateTime nowPlusThreeMonths = new DateTime(now.getYear(), now.getMonthOfYear(), 1, 0, 0).plusMonths(3);
		DateTime cedMonth = new DateTime(ced.getYear(), ced.getMonthOfYear(), 1, 0, 0);
		if(cedMonth.isAfter(nowPlusThreeMonths)){
			return false;
		}
		
		return true;
	}
	
	private String[] getImsPendingOrderSrd(TerminateOrderCaptureDTO order){
		if(order.getImsPendingOrder() != null
				&& StringUtils.isNotBlank(order.getImsPendingOrder().getSrdStart())
				&& StringUtils.isNotBlank(order.getImsPendingOrder().getSrdEnd())){
			
			DateTime srdStart = DateTime.parse(order.getImsPendingOrder().getSrdStart().substring(0, 10), DateTimeFormat.forPattern("yyyy-MM-dd"));
			String srdStartTime = null;
			String srdEndTime = null;
			try{
				srdStartTime = order.getImsPendingOrder().getSrdStart().substring(11, 16);
				srdEndTime = order.getImsPendingOrder().getSrdEnd().substring(11, 16);
			}catch(Exception e){
			}
			String[] srdStrings = {srdStart.toString(DateTimeFormat.forPattern("dd/MM/yyyy")), srdStartTime+"-"+srdEndTime};
			return srdStrings;
		}
		
		return null;
	}
	
	private boolean isAppntRequired(TerminateOrderCaptureDTO order) {
		
		if(order.getSbOrder() != null){
			ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(order.getSbOrder());
			if(StringUtils.isNotBlank(ltsService.getFchNum())){
				return false;
			}
			
			ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(order.getSbOrder());
			if(imsService != null){
				return LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN.equals(imsService.getFromProd());
			}
			return false;
		}
		
		if(StringUtils.isNotBlank(order.getLtsTerminateServiceSelectionForm().getFch())){
			return false;
		}
		
		ServiceDetailProfileLtsDTO pServiceProfile = order.getLtsServiceProfile();
		FsaServiceDetailOutputDTO fsa = pServiceProfile.getEyeFsaProfile();
		if(pServiceProfile.getRelatedEyeFsa() != null){
			FsaServiceType imsSrvType=ltsRetrieveFsaService.getExistSrvType(fsa);
			if(FsaServiceType.WG==imsSrvType){
				return true;
			}
		}
		return false;
	}
	
	private boolean isAllowProceed(TerminateOrderCaptureDTO order, LtsTerminateAppointmentFormDTO form){
		String[] imsPendingOrderSrd = getImsPendingOrderSrd(order);
		if(imsPendingOrderSrd == null){
			return true;
		}
		
		DateTime imsSrd = DateTime.parse(imsPendingOrderSrd[0], DateTimeFormat.forPattern("dd/MM/yyyy"));
		if(imsSrd.isBefore(DateTime.parse(LtsAppointmentHelper.getToday(), DateTimeFormat.forPattern("dd/MM/yyyy")).plusDays(2))){
			form.setErrorMsg("PCD Pending order SRD < T+2, not allow to proceed.");
			return false;
		}
		
		return true;
		
	}
	
	public LtsRetrieveFsaService getLtsRetrieveFsaService() {
		return this.ltsRetrieveFsaService;
	}
	public void setLtsRetrieveFsaService(
			LtsRetrieveFsaService pLtsRetrieveFsaService) {
		this.ltsRetrieveFsaService = pLtsRetrieveFsaService;
	}
	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return this.imsServiceProfileAccessService;
	}
	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService pImsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = pImsServiceProfileAccessService;
	}
	public CodeLkupCacheService getLtsAppointmentDeviceBasicTLkupCacheService() {
		return ltsAppointmentDeviceBasicTLkupCacheService;
	}
	public void setLtsAppointmentDeviceBasicTLkupCacheService(
			CodeLkupCacheService ltsAppointmentDeviceBasicTLkupCacheService) {
		this.ltsAppointmentDeviceBasicTLkupCacheService = ltsAppointmentDeviceBasicTLkupCacheService;
	}
	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}
	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
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

	public LtsTerminateOrderService getLtsTerminateOrderService() {
		return ltsTerminateOrderService;
	}

	public void setLtsTerminateOrderService(
			LtsTerminateOrderService ltsTerminateOrderService) {
		this.ltsTerminateOrderService = ltsTerminateOrderService;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(
			AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}
	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}
	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}
}
