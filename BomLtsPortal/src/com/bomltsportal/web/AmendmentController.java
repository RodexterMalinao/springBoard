package com.bomltsportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.AmendmentFormDTO;
import com.bomltsportal.service.AmendmentService;
import com.bomltsportal.service.ApplicantInfoService;
import com.bomltsportal.service.OnlineSalesLogService;
import com.bomltsportal.service.SummaryService;
import com.bomltsportal.service.email.LtsEmailService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomltsportal.util.SessionConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomltsportal.util.uENC;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.AmendAppointmentLtsDTO;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.service.order.AmendmentSubmitService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class AmendmentController extends SimpleFormController{

	protected final Log logger = LogFactory.getLog(getClass());
	private final String nextView = "message?msgcode=msg.amend.success";
	
	private SummaryService summaryService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private ApplicantInfoService applicantInfoService;
	private AppointmentDwfmService appointmentDwfmService;
	private CodeLkupCacheService amendNatureSrdCodeLkupCacheService;
	private CodeLkupCacheService amendNatureSrdDelCodeLkupCacheService;
	private AmendmentSubmitService amendmentSubmitService;
	private AmendmentService amendmentService;
	private LtsEmailService ltsEmailService;
	private OnlineSalesLogService onlineSalesLogService;

	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String action = (String) request.getParameter("action");
		
		OrderCaptureDTO orderCapture = new OrderCaptureDTO();
		
		if(!"SUBMIT".equals(action)){
			// CLEAR ALL SESSION ATTBRIBUTES
			SessionHelper.clearOrderCapture(request);
			SessionHelper.setSessionUid(request);
//			OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
			AmendmentFormDTO amend = new AmendmentFormDTO(); 
			String orderId;
			if(StringUtils.isNotBlank(request.getParameter("_orderId_"))){
				orderId = request.getParameter("_orderId_");
			}else{
				String key = request.getParameter("key");
				orderId = uENC.decAES(BomLtsConstant.URL_PARM_ENC_KEY, key);
			}

			SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(orderId, false);//orderCaptureDTO.getSbOrder();
			
			orderCapture.setAmendmentForm(amend);
			orderCapture.setSbOrder(sbOrder);
			SessionHelper.setOrderCapture(request, orderCapture);
			SessionHelper.setLanguage(request, response, "ENG".equals(sbOrder.getLangPref())?BomLtsConstant.LOCALE_ENG:BomLtsConstant.LOCALE_CHI);
			RequestContextUtils.getLocaleResolver(request).setLocale(request, response, LocaleUtils.toLocale(orderCapture.getLang()));

		}
		
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{

		logger.info("AmendmentController formBackingObject is called");
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		AmendmentFormDTO amend = orderCapture.getAmendmentForm();
		String orderId = sbOrder.getOrderId();
		String srvType = request.getParameter(SessionConstant.PARAM_SRV_TYPE);
		String userId = request.getParameter(SessionConstant.PARAM_USER_ID);
		
		onlineSalesLogService.logOnlineDetailTrack(
				SessionHelper.getRequestId(request), 
				SessionHelper.getCurrentPage(request), 
				BomLtsConstant.LOG_TRACK_ITEM_CD_SB_NO, 
				sbOrder.getOrderId(), 
				SessionHelper.getRequestSeq(request));

		
		if(StringUtils.isNotBlank(userId)){
			amend.setUserId(userId);
			
			onlineSalesLogService.logOnlineDetailTrack(
					SessionHelper.getRequestId(request), 
					SessionHelper.getCurrentPage(request), 
					BomLtsConstant.LOG_TRACK_ITEM_CD_USER_ID, 
					userId, 
					SessionHelper.getRequestSeq(request));
		}
		ServiceDetailDTO srvDtl = LtsSbHelper.getLtsEyeService(sbOrder);
		ServiceDetailOtherLtsDTO srvImsDtl = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		OrderStatusSynchDTO srvStatus = null;
		OrderStatusSynchDTO srvImsStatus = null;
		
		if (srvDtl != null) {
			srvStatus = this.amendmentService.getValidBomOrderStatus(orderId, srvDtl.getTypeOfSrv(), srvDtl.getSrvNum(), srvDtl.getCcServiceId(), srvDtl.getCcServiceMemNum());
			
			if (srvStatus == null) {
				srvImsStatus = this.amendmentService.getValidFsaOrderStatus(orderId, srvImsDtl.getSrvNum(), null, null);	
			} else {
				srvImsStatus = this.amendmentService.getValidFsaOrderStatus(orderId, srvImsDtl.getSrvNum(), srvStatus.getOcId(), srvStatus.getGrpId());
			}
			srvType = BomLtsConstant.SERVICE_TYPE_EYE;
		} else {
			srvDtl = LtsSbHelper.getDelServices(sbOrder);
			srvStatus = this.amendmentService.getValidBomOrderStatus(orderId, srvDtl.getTypeOfSrv(), srvDtl.getSrvNum(), srvDtl.getCcServiceId(), srvDtl.getCcServiceMemNum());
			srvType = BomLtsConstant.SERVICE_TYPE_DEL;
		}
		SessionHelper.setOrderSrvType(request, srvType);
		StringBuilder msg = new StringBuilder();
		amend.setAllowAmend(amendmentService.isAmendmentAllow(orderId, srvStatus, srvImsStatus, srvType, msg, LocaleUtils.toLocale(orderCapture.getLang())));
		if(amend.isAllowAmend()){
			amend.setAddr(summaryService.getDisplayAddressWithoutFlatFoor(sbOrder.getAddress()));
			amend.setFamilyName(srvDtl.getAccount().getCustomer().getLastName());
			amend.setTitle(srvDtl.getAccount().getCustomer().getTitle());
			
			String[] dateTime = new String[3];
			String startTime = srvDtl.getAppointmentDtl().getAppntStartTime();
			String endTime = srvDtl.getAppointmentDtl().getAppntEndTime();
			String timeslot = LtsDateFormatHelper.convertToSBTimeSlot(startTime.split(" ")[1] + "-" + endTime.split(" ")[1]);
			String slotType = srvDtl.getAppointmentDtl().getAppntType();
			amend.setInstallDate(LtsDateFormatHelper.reformatDate(startTime.split(" ")[0], "dd/MM/yyyy", "yyyy/MM/dd"));
			amend.setInstallTimeAndType(timeslot + "||" + slotType );

			
			amend.setEarliestSrd(applicantInfoService.getEarliestSRD(sbOrder));
			amend.setOriginalInstallDate(amend.getInstallDate());
			amend.setOriginalInstallTime(amend.getInstallTime());
			
			orderCapture.setAmendmentForm(amend);
			orderCapture.setTopNavInd("AMEND");	
			request.setAttribute("earliestSrd",  amend.getEarliestSrd().getDateString("yyyy/MM/dd"));
			
			int maxSrd = applicantInfoService.getMaxSrd(srvType);
			if(StringUtils.isBlank(userId)){
				if(amend.getEarliestSrd().getLeadTime() >= maxSrd){
					request.setAttribute("maxDate",  "+" + (amend.getEarliestSrd().getLeadTime()+maxSrd) + "D");
				}else{
					request.setAttribute("maxDate",  "+" + (maxSrd) + "D");
				}
			}else{
				request.setAttribute("maxDate",  "");
			}
		}else{
			amend.setNotAllowMsg(msg.toString());
		}

		return amend;
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
			throws ServletException {
		logger.info("AmendmentController onSubmit is called");
		
		logger.info("Next View: " + " - ");
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		AmendmentFormDTO form = (AmendmentFormDTO) command;
		orderCapture.setAmendmentForm(form);
		
		PrebookAppointmentOutputDTO output = appointmentDwfmService.getPrebookAppointment(
				applicantInfoService.getPrebookAppointmentInput(
						orderCapture, LtsDateFormatHelper.reformatDate(form.getInstallDate(), "yyyy/MM/dd", "dd/MM/yyyy"), 
						form.getInstallTime(), BomLtsConstant.USER_ID, form.getInstallTimeType()));
		if(output != null){
			if(output.getSerialNum() != null){
				form.setPrebookSerialNum(output.getSerialNum());
			}
		}
		
		AmendLtsDTO amend = new AmendLtsDTO();
		amend.setOrderId(sbOrder.getOrderId());
		amend.setOcid(sbOrder.getOcid());
//		amend.setSalesChannel(orderAmendDTO.getSalesChannel());
//		amend.setSalesContactNum(orderAmendDTO.getSalesContactNum());
		amend.setShopCd(BomLtsConstant.OLS_SHOP_CD);
		amend.setStaffNum(BomLtsConstant.USER_ID);
//		amend.setSalesCd(orderAmendDTO.getSalesId());
		amend.setName(sbOrder.getCustomers()[0].getFirstName() + " " + sbOrder.getCustomers()[0].getLastName());
		amend.setRelationshipCd(BomLtsConstant.RELATIONSHIP_CODE_SUB);
		amend.setContactNum(sbOrder.getSrvDtls()[0].getAppointmentDtl().getInstContactNum());
		
		AmendDetailLtsDTO amendDtl = new AmendDetailLtsDTO();
		
		ServiceDetailLtsDTO srvDtl;
		boolean isDel = false;
		if(BomLtsConstant.SERVICE_TYPE_EYE.equals(orderCapture.getServiceTypeInd())){
			srvDtl = LtsSbHelper.getLtsEyeService(sbOrder);
		}else{
			srvDtl = LtsSbHelper.getDelServices(sbOrder);
			isDel = true;
		}
		amendDtl.setSrvNum(srvDtl.getSrvNum());
		amendDtl.setSrvType(srvDtl.getTypeOfSrv());
		amendDtl.setDtlId(srvDtl.getDtlId());
		amendDtl.setGrpType(srvDtl.getGrpType());
		
		List<AmendDetailLtsDTO> amendDtls = new ArrayList<AmendDetailLtsDTO>();
		List<AmendCategoryLtsDTO> categoryList = new ArrayList<AmendCategoryLtsDTO>();
		AmendAppointmentLtsDTO apptOrder = new AmendAppointmentLtsDTO();
		try {
			if(isDel){
				apptOrder.setNature(amendNatureSrdDelCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey());
			}else{
				apptOrder.setNature(amendNatureSrdCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey());
			}
		} catch (DAOException daoe) {
			daoe.printStackTrace();
		}
		String[] fromTime = LtsDateFormatHelper.reformatDateTimeSlot(LtsDateFormatHelper.reformatDate(form.getOriginalInstallDate(),"yyyy/MM/dd","dd/MM/yyyy"), form.getOriginalInstallTime());
		apptOrder.setFromAppntStartTime(fromTime[0]);
		apptOrder.setFromAppntEndTime(fromTime[1]);
		String[] time = LtsDateFormatHelper.reformatDateTimeSlot(LtsDateFormatHelper.reformatDate(form.getInstallDate(),"yyyy/MM/dd","dd/MM/yyyy"), form.getInstallTime());
		apptOrder.setAppntStartTime(time[0]);
		apptOrder.setAppntEndTime(time[1]);
		srvDtl.getAppointmentDtl().setAppntStartTime(time[0]);
		srvDtl.getAppointmentDtl().setAppntStartTime(time[1]);
		
//		for(ServiceDetailDTO srvDtlLts: sbOrder.getSrvDtls()){
//			srvDtlLts.getAppointmentDtl().setAppntStartTime(time[0]);
//			srvDtlLts.getAppointmentDtl().setAppntEndTime(time[1]);
//		}
		
		apptOrder.setSerialNum(form.getPrebookSerialNum());
		apptOrder.setDelayReasonCd(BomLtsConstant.AMEND_DELAY_REASON_CODE);
		categoryList.add(apptOrder);
		
		amendDtl.setCategoryList(categoryList);
		
		amendDtls.add(amendDtl);
		amend.setAmendDtlList(amendDtls);
		
		String userId = form.getUserId();
		if(StringUtils.isBlank(userId)){
			userId = BomLtsConstant.USER_ID;
		}
		
		// Added by karen 20131213 start (set appnt datetime to order)
		AppointmentDetailLtsDTO tempAppt = srvDtl.getAppointmentDtl();
		tempAppt.setAppntStartTime(time[0]);
		tempAppt.setAppntEndTime(time[1]);
		apptOrder.setReferenceOrdAppnt(tempAppt);
		// Added by karen 20131213 end		
		amendmentSubmitService.submitAmendment(amend, userId, BomLtsConstant.OLS_SHOP_CD);

		
		ltsEmailService.sendSignOffEmail(sbOrder, userId, null, null, null, true);
		
		return new ModelAndView("message", "msgCode", "msg.amend.success");
		
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		logger.info("AmendmentController referenceData is called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		
		AmendmentFormDTO form = orderCaptureDTO.getAmendmentForm();
		
		if(StringUtils.isNotBlank(form.getInstallDate())){
			//TODO
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
				e.printStackTrace();
			}
		}
		
		return referenceData;
	}
	
	private String[] getOriginalDateTime(SbOrderDTO sbOrder){
		
		return null;
	}
	
	public SummaryService getSummaryService() {
		return summaryService;
	}

	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
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

	public CodeLkupCacheService getAmendNatureSrdCodeLkupCacheService() {
		return amendNatureSrdCodeLkupCacheService;
	}

	public void setAmendNatureSrdCodeLkupCacheService(
			CodeLkupCacheService amendNatureSrdCodeLkupCacheService) {
		this.amendNatureSrdCodeLkupCacheService = amendNatureSrdCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureSrdDelCodeLkupCacheService() {
		return amendNatureSrdDelCodeLkupCacheService;
	}

	public void setAmendNatureSrdDelCodeLkupCacheService(
			CodeLkupCacheService amendNatureSrdDelCodeLkupCacheService) {
		this.amendNatureSrdDelCodeLkupCacheService = amendNatureSrdDelCodeLkupCacheService;
	}

	public AmendmentSubmitService getAmendmentSubmitService() {
		return amendmentSubmitService;
	}

	public void setAmendmentSubmitService(AmendmentSubmitService amendmentSubmitService) {
		this.amendmentSubmitService = amendmentSubmitService;
	}

	public AmendmentService getAmendmentService() {
		return amendmentService;
	}

	public void setAmendmentService(AmendmentService amendmentService) {
		this.amendmentService = amendmentService;
	}

	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}

	public OnlineSalesLogService getOnlineSalesLogService() {
		return onlineSalesLogService;
	}

	public void setOnlineSalesLogService(OnlineSalesLogService onlineSalesLogService) {
		this.onlineSalesLogService = onlineSalesLogService;
	}

	
}
