package com.bomwebportal.lts.web.acq;

//import java.io.PrintWriter;
//import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.PcdOrderCheckAttrDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO.DuplexAction;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO.LtsAppointmentDetail;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialInputDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.acq.LtsAcqAppointmentService;
import com.bomwebportal.lts.service.acq.LtsAcqRelatedPcdOrderService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.lts.service.order.SbOrderInfoLtsService;

public class LtsAcqAppointmentController extends SimpleFormController {

	private final String viewName = "lts/acq/ltsacqappointment";
	private final String nextView = "ltsacqsupportdoc.html";
	private final String commandName = "ltsAcqAppointmentCmd";
	private final String currentView = "ltsacqappointment.html";
	
	private LtsAcqAppointmentService ltsAcqAppointmentService;
	private AppointmentDwfmService appointmentDwfmService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService;
	private CodeLkupCacheService ltsDsSuspendReasonLkupCacheService;
	private SbOrderInfoLtsService sbOrderInfoLtsService;
	private LtsOfferService ltsOfferService;
	private LtsAppointmentService ltsAppointmentService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	private final int APPOINTMENT_BB_SHORTAGE_LEADTIME = 60;
	private final int APPOINTMENT_BLACKLIST_LEADTIME = 30;
	
	private final String APPOINTMENT_BB_SHORTAGE_MSG = 
			"Warning: BB Shortage - SRD default to T+" + APPOINTMENT_BB_SHORTAGE_LEADTIME + ". ";
	private final String APPOINTMENT_BLACKLIST_CUST_MSG = 
			"Warning: External Relocate and Blacklist Customer - SRD default to T+" + APPOINTMENT_BLACKLIST_LEADTIME + ". ";
	private final String APPOINTMENT_IMS_BLACKLIST_ADDR_MSG = 
			"Warning: IMS Blacklist Address - SRD default to T+" + APPOINTMENT_BLACKLIST_LEADTIME + ". ";
	private final String APPOINTMENT_LTS_BLACKLIST_ADDR_MSG = 
			"Warning: LTS Blacklist Address - SRD default to T+" + APPOINTMENT_BLACKLIST_LEADTIME + ". ";
		
	public LtsAcqAppointmentController(){
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsAcqAppointmentFormDTO.class);
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
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqAppointmentFormDTO form = order.getLtsAcqAppointmentForm();
		BomSalesUserDTO salesInfo = LtsSessionHelper.getBomSalesUser(request);
		String pcdActivationDate = null;
		String reformatPcdActivationDate = null;
		
		if(form == null){
			form = new LtsAcqAppointmentFormDTO();
			form.setWaiveCoolingOffInd(false);
		}
		
		form.setEpdItemList(ltsAppointmentService.getNewEpdItemList(order.getBasketChannelId(), 
				LtsDateFormatHelper.getSysDate("dd/MM/yyyy HH:mm"), locale.toString(), form.getEpdItemList()));
		
		order.setLtsAcqAppointmentForm(form);

		if(isContainPrewiringVAS(order)){
			form.setPreWiring(true);
		}
		
		setupInstallation(order, form);
		setupSecDelInstallation(order, form);
		
		boolean haveNoInstallAppnt = false;
		if(form.getInstallAppntDtl()==null)
		{
			haveNoInstallAppnt = true;
		}
		else if(form.getInstallAppntDtl().getAppntDate()==null)
		{
			haveNoInstallAppnt = true;
		}
		if(!form.isConfirmedInd() && haveNoInstallAppnt){
			setupPcdOrderDetail(order, form);
		}
		
		setupTentativeDetail(order, form);
		setupPrewiring(order, form);
		setupPortingAppointment(order, form);
		
		setupCutOverForAppntDtl(form.getInstallAppntDtl());
		setupCutOverForAppntDtl(form.getSecDelInstallAppntDtl());
		setupCutOverForAppntDtl(form.getPortLaterAppntDtl());
		
		setMaxDate(order, form);
		
		pcdActivationDate = sbOrderInfoLtsService.getPcdActivationDate(order.getLtsAcqBasketSelectionForm().getPcdSbid(), order.getSelectedBasket().getPreinstallCheck(), order.getSelectedBasket().getPcdBundleFreeType());
		form.setPcdActivationDate(pcdActivationDate);
		form.setChkPcdActivationDate(StringUtils.isNotBlank(pcdActivationDate));
		
		if(order.isChannelDirectSales()){
			setDsMinDate(order, form);
			setupDsDtl(order, form, salesInfo);
		}
		if(StringUtils.isNotBlank(order.getSuspendRemark())){
	    	form.setSuspendRemarks(order.getSuspendRemark());
	    }else{
		    form.setSuspendRemarks(null);
	    }
		
		if(StringUtils.isNotBlank(pcdActivationDate)){
			reformatPcdActivationDate = LtsDateFormatHelper.reformatDate(pcdActivationDate, "yyyyMMdd", "dd/MM/yyyy");
	    }
		
		//BOM2018061		
		form.setEpdLeadDay(ltsAppointmentService.getEpdLeadTime());
		
		if(order.getLtsAcqPersonalInfoForm()!=null){
			form.setDefaultContactName(order.getLtsAcqPersonalInfoForm().getFamilyName()+" "+order.getLtsAcqPersonalInfoForm().getGivenName());
			form.setDefaultContactNum(order.getLtsAcqPersonalInfoForm().getMobileNo());
		}
		//BOM2018061
		
		request.setAttribute("bbShortageLeadtime", APPOINTMENT_BB_SHORTAGE_LEADTIME);
		request.setAttribute("blacklistLeadtime", APPOINTMENT_BLACKLIST_LEADTIME);
		
		request.setAttribute("technologyChange", isTechnologyChange(order));
		request.setAttribute("isPreInstall", order.isContainPreInstallVAS());
		
		request.setAttribute("waiveVal", messageSource.getMessage("lts.acq.appointment.waive", new Object[] {}, this.locale));
		request.setAttribute("notWaiveVal", messageSource.getMessage("lts.acq.appointment.notWaive", new Object[] {}, this.locale));
		
		request.setAttribute("yesVal", messageSource.getMessage("lts.acq.common.yes", new Object[] {}, this.locale));
		request.setAttribute("noVal", messageSource.getMessage("lts.acq.common.no", new Object[] {}, this.locale));
		
		request.setAttribute("pcdActivationDate", reformatPcdActivationDate);
		
		return form;
		
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqAppointmentFormDTO form = (LtsAcqAppointmentFormDTO) command;
		BomSalesUserDTO salesInfo = LtsSessionHelper.getBomSalesUser(request);
		
		if(StringUtils.isNotBlank(form.getSuspendRemarks())){
			order.setSuspendRemark(form.getSuspendRemarks());
		}else{
			order.setSuspendRemark(null);
		}
		
		if("CONFIRM".equals(form.getSubmitInd())){
	    	order.setLtsAcqAppointmentForm(form);

	    	String date = null;
			String timeSlot = null;
			String timeSlotType = null;
			boolean tentativeInd = form.isTentativeInd();
			boolean pcdOrderExistInd = form.isPcdOrderExistInd();
			
			LtsAppointmentDetail appntDtlForBook = null;
			appntDtlForBook = form.getPreWiringAppntDtl();
	    	if(appntDtlForBook == null){
	    		appntDtlForBook = form.getInstallAppntDtl();
	    	}
	    	
    		date = appntDtlForBook.getAppntDate();
    		timeSlot = appntDtlForBook.getAppntTimeSlot();
    		timeSlotType = appntDtlForBook.getAppntTimeSlotType();
    		if(tentativeInd || pcdOrderExistInd){
    			form.setConfirmedInd(true);
				form.setErrorMsg(null);
    		}else{
	    		if(date != null && timeSlot != null){
	    			//If srd is valid then try prebooking
	    			if(validatePremiumItemSRD(order, form)){
	    				//PrebookAppointmentInputDTO input = ltsAcqAppointmentService.getPrebookAppointmentInput(order, date, timeSlot, order.getLtsAcqSalesInfoForm().getStaffId(), timeSlotType);
	    				// LTS_APPOINT_TIMESLOT fix
	    				PrebookAppointmentInputDTO input = ltsAcqAppointmentService.getPrebookAppointmentInput(order, date, LtsDateFormatHelper.revertToBomTime(timeSlot), order.getLtsAcqSalesInfoForm().getStaffId(), timeSlotType);
	    				//
						PrebookAppointmentOutputDTO output = appointmentDwfmService.getPrebookAppointment(input);
						if(output != null){
							if(output.getSerialNum() != null){
								form.setPreBookSerialNum(output.getSerialNum());
								form.setConfirmedInd(true);
								form.setErrorMsg(null);
							}else if(!"0".equals(output.getReturnValue())){
								form.setConfirmedInd(false);
								form.setErrorMsg(output.getErrorMsg());
							}else{
								form.setPreBookSerialNum(null);
								form.setConfirmedInd(false);
								form.setErrorMsg(messageSource.getMessage("lts.acq.appointment.appointmentFailed", new Object[] {}, this.locale));
							}
						}
	    			}
    			}
    		}
    		return new ModelAndView(new RedirectView(currentView));
    		
		} else if("CANCEL".equals(form.getSubmitInd())){
			CancelPrebookSerialInputDTO input = new CancelPrebookSerialInputDTO ();
			input.setSerialNum(form.getPreBookSerialNum());
			input.setLoginID("");
			CancelPrebookSerialOutputDTO cancel = null;
			for(int count = 0; count < 3 ; count++) {
				if(cancel == null || StringUtils.isNotBlank(cancel.getErrorCd())){
					cancel = appointmentDwfmService.cancelPrebookSerial(input);
				}else{
					break;
				}
			}
			form.setPreBookSerialNum(null);
			form.setErrorMsg(messageSource.getMessage("lts.acq.appointment.appointmentCancel", new Object[] {}, this.locale));
			form.setConfirmedInd(false);
			order.setLtsAcqAppointmentForm(form);
	    	return new ModelAndView(new RedirectView(currentView));
		} else if ("SUSPEND".equals(form.getSubmitInd())) {
    		order.setLtsAcqAppointmentForm(null);
    		Map<String, Object> paramMap = new HashMap<String, Object>();
        	paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
    		paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, form.getSuspendReason());
			paramMap.put(LtsConstant.REQUEST_PARAM_FROM_VIEW, "ltsacqappointment");
			return new ModelAndView(new RedirectView(LtsConstant.NEW_ACQ_ORDER_VIEW), paramMap);
		}
		/*else if("SEARCHPCD".equals(form.getSubmitInd())){
	    	return searchPcdOrder(order, form);
		} else if("CLEARPCD".equals(form.getSubmitInd())){
	    	clearPcdOrder(order, form);
    		return new ModelAndView(new RedirectView("ltsacqappointment.html"));
		} */
		else if("SUBMIT".equals(form.getSubmitInd())){
			//BOM2018061
	    	ValidationResultDTO validationResult = ltsOfferService.validateItemAttb(form.getEpdItemList());
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}
	    	//BOM2018061 END
			if("02".equals(form.getInstallAppntDtl().getEarliestSRD().getReasonCd())){
				form.getInstallAppntDtl().setEarliestSRDReason(APPOINTMENT_IMS_BLACKLIST_ADDR_MSG);
			}else if("08".equals(form.getInstallAppntDtl().getEarliestSRD().getReasonCd())){
				form.getInstallAppntDtl().setEarliestSRDReason(APPOINTMENT_LTS_BLACKLIST_ADDR_MSG);
			}else if("20".equals(form.getInstallAppntDtl().getEarliestSRD().getReasonCd())){
				form.getInstallAppntDtl().setEarliestSRDReason(APPOINTMENT_BLACKLIST_CUST_MSG);
			}
			if(form.isBbShortageInd()){
				form.getInstallAppntDtl().setEarliestSRDReason(form.getInstallAppntDtl().getEarliestSRDReason().concat(APPOINTMENT_BB_SHORTAGE_MSG));
			}
			
			if(order.isChannelDirectSales()){
				if(order.getLtsDsOrderInfo() != null){
					order.getLtsDsOrderInfo().setMustQc(ltsAcqAppointmentService.isMustQc(order, form)? "Y" : "N");
				}
				
				form.setMustQc(ltsAcqAppointmentService.isMustQc(order, form));
				form.setMustQcReasonCd(ltsAcqAppointmentService.getMustQcReasonCd(order, form));
			}

			order.setLtsAcqAppointmentForm(form);
			
			if(StringUtils.equals(salesInfo.getChannelCd(), LtsConstant.CHANNEL_CD_DIRECT_SALES_QC)){
				Map<String, Object> paramMap = new HashMap<String, Object>();
			    paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_UPDATE_APPOINTMENT_FOR_QC);
				return new ModelAndView(new RedirectView(LtsConstant.NEW_ACQ_ORDER_VIEW), paramMap);
			}
			
			if(order.getSbOrder() != null){
				ServiceDetailLtsDTO srv = LtsSbOrderHelper.getLtsService(order.getSbOrder());
				if(LtsConstant.ORDER_STATUS_AWAIT_PREPAYMENT.equals(srv.getSbStatus())
						&& LtsConstant.ORDER_STATUS_REASON_AWAIT_PREPAY.equals(srv.getSbReasonCd())){
					Map<String, Object> paramMap = new HashMap<String, Object>();
				    paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_UPDATE_APPOINTMENT_FOR_AWAIT_PREPAYMENT);
					return new ModelAndView(new RedirectView(LtsConstant.NEW_ACQ_ORDER_VIEW), paramMap);
				}
			}
			
		}
		
		return new ModelAndView(new RedirectView(nextView));
		
	}
	
	private boolean isTechnologyChange(AcqOrderCaptureDTO orderCapture) {
		FsaDetailDTO selectedFsa = LtsSbOrderHelper.getSelectedFsa(orderCapture.getLtsModemArrangementForm());
		ModemTechnologyAissgnDTO modemTechnologyAissgn = orderCapture.getModemTechnologyAssign();
		if (selectedFsa != null && modemTechnologyAissgn != null) {
			if (!StringUtils.equalsIgnoreCase(selectedFsa.getTechnology(), modemTechnologyAissgn.getTechnology())) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean validatePremiumItemSRD(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		String srdString = form.getInstallAppntDtl().getAppntDate();
		
		LocalDate srdDate = LocalDate.parse(srdString, DateTimeFormat.forPattern("dd/MM/yyyy"));
		LocalDate srdLimit = null;
		
		if(StringUtils.isNotBlank(order.getPremiumItemSrdBeforeDateLimit())){
			//value = dateString, ie. "20151231"
			LocalDate itemSrdLimit = LocalDate.parse(order.getPremiumItemSrdBeforeDateLimit(), DateTimeFormat.forPattern("yyyyMMdd"));
			if(srdLimit == null || itemSrdLimit.isBefore(srdLimit)){
				srdLimit = itemSrdLimit;
			}
		}else if(StringUtils.isNotBlank(order.getPremiumItemSrdDayLimit())){
			//value = numberString, ie. "30"
			LocalDate itemSrdLimit = LocalDate.now().plusDays(NumberUtils.toInt(order.getPremiumItemSrdDayLimit()));
			if(srdLimit == null || itemSrdLimit.isBefore(srdLimit)){
				srdLimit = itemSrdLimit;
			}
		}
		
		if(srdLimit != null){
			if(srdLimit.isBefore(srdDate)){
				form.setPreBookSerialNum(null);
				form.setConfirmedInd(false);
				//form.setErrorMsg("Premium with SRD Limit (" + srdLimit.toString("dd/MM/yyyy") + ") is selected. Please select a SRD before the limit.");
				form.setErrorMsg(messageSource.getMessage("lts.acq.appointment.premiumWithSRDLimit", new Object[] {srdLimit.toString("dd/MM/yyyy")}, this.locale));
				return false;
			}
		}
		
		return true;
	}
	
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();

		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqAppointmentFormDTO  form = order.getLtsAcqAppointmentForm();
		if(order.isChannelDirectSales()){
			referenceData.put("suspendReasonList", Arrays.asList(ltsDsSuspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}else{
			referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}
		
		if(order.isContainPreInstallVAS())
		{
			referenceData.put("acqPortLaterleadtime", LtsConstant.PRE_INSTALL_MINDAY);
		}
		else
		{
			referenceData.put("acqPortLaterleadtime", LtsSbOrderHelper.getPipbMinDay()+2);
		}
		
		
		if(form != null && !form.isConfirmedInd()){
			
			String type = order.isEyeOrder()?LtsConstant.DWFM_INPUT_TYPE_NEWACQ_EYE:LtsConstant.DWFM_INPUT_TYPE_NEWACQ_DEL;
			String allType = order.isEyeOrder()? LtsConstant.DWFM_INPUT_TYPE_ALL: LtsConstant.DWFM_INPUT_TYPE_NEWACQ_DEL;

			boolean setPreWiringResource = form.getPreWiringAppntDtl() != null;
			boolean setSecDelResource = form.getSecDelInstallAppntDtl() != null;
			boolean setPortLaterResource = form.getPortLaterAppntDtl() != null;
			
			if(form.getInstallAppntDtl() != null && StringUtils.isNotBlank(form.getInstallAppntDtl().getAppntDate())){
				if(form.getSecDelInstallAppntDtl() != null
					&& StringUtils.equals(form.getInstallAppntDtl().getAppntDate(), form.getSecDelInstallAppntDtl().getAppntDate())){
					List<ResourceSlotDTO> resourceList = getResourceList(order, form.getInstallAppntDtl().getAppntDate(), allType);
					referenceData.put("installationTimeSlot", resourceList);
					referenceData.put("secDelInstallationTimeSlot", resourceList);
					setSecDelResource = false; 
				}else{
					if(StringUtils.isNotBlank(form.getInstallAppntDtl().getAppntDate())){
						referenceData.put("installationTimeSlot", 
								getResourceList(order, form.getInstallAppntDtl().getAppntDate(), type));
					}
				}
			}
			
			if(setSecDelResource){
				if(form.getSecDelInstallAppntDtl() != null && StringUtils.isNotBlank(form.getSecDelInstallAppntDtl().getAppntDate())){
					referenceData.put("secDelInstallationTimeSlot", 
							getResourceList(order, form.getSecDelInstallAppntDtl().getAppntDate(), LtsConstant.DWFM_INPUT_TYPE_NEWACQ_DEL));
				}
			}

			if(setPreWiringResource){
				if(form.getPreWiringAppntDtl() != null && StringUtils.isNotBlank(form.getPreWiringAppntDtl().getAppntDate())){
					referenceData.put("preWiringTimeSlot", 
							getResourceList(order, form.getPreWiringAppntDtl().getAppntDate(), type));
				}
			}
			
			if(setPortLaterResource){
				if(form.getPortLaterAppntDtl() != null && StringUtils.isNotBlank(form.getPortLaterAppntDtl().getAppntDate())){
					referenceData.put("portLaterTimeSlot", 
							getResourceList(order, form.getPortLaterAppntDtl().getAppntDate(), type));
				}
				
			}
			
		}
		referenceData.put("isPcdResourceShortage", form.isPcdResourceShortage());
		referenceData.put("isEyeOrder", order.isEyeOrder());
		referenceData.put("isDelFree", form.isDelFree());
		referenceData.put("isPcdBundleBasket", form.isPcdBundleBasket());
		referenceData.put("isPcdBundlePremium", form.isPcdBundlePremium());
		referenceData.put("isPreWiring", form.isPreWiring());
		return referenceData;
	}
	
	private void setupPrewiring(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		if(!isContainPrewiringVAS(order)){
			form.setPreWiringAppntDtl(null);
			return;
		}
		
		boolean isPrewrirDate = false;
		LtsAppointmentDetail appntDtl = form.getPreWiringAppntDtl();
		if(appntDtl == null){
			appntDtl = form.new LtsAppointmentDetail();
		}
//		else
//		{
//			isPrewrirDate = true;
//		}
		
		appntDtl.setEarliestSRD(ltsAcqAppointmentService.getEarliestSRD(order, LtsAppointmentHelper.getToday(), true));
		appntDtl.setEarliestSRDReason(LtsAppointmentHelper.getEarilestSrdReason(appntDtl.getEarliestSRD()));
//		if((isDelFree || isPcdBundleBasket || isPcdBundlePremium)&&!isPrewrirDate)
//		{
//			form.setPreWiringAppntDtl(form.getInstallAppntDtl());
//			form.setInstallAppntDtl(appntDtl);
//		}
//		else
//		{
			form.setPreWiringAppntDtl(appntDtl);	
//		}
	}

	private void setupInstallation(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		//Re-calculate Earliest SRD every time in case user go back and made changes
		LtsAppointmentDetail appntDtl = form.getInstallAppntDtl();
		if(appntDtl == null){
			appntDtl = form.new LtsAppointmentDetail();
		}
		LtsSRDDTO earliestSRD = null;
		if(isUsePipbDn(order)){	
			appntDtl.setCutOverInd(true);
			earliestSRD = ltsAcqAppointmentService.getPipbEarlisetSRD(order, LtsAppointmentHelper.getToday());
		}else{
			appntDtl.setCutOverInd(false);
			earliestSRD = ltsAcqAppointmentService.getEarliestSRD(order, LtsAppointmentHelper.getToday());
		}
		appntDtl.setEarliestSRD(earliestSRD);
		appntDtl.setEarliestSRDReason(LtsAppointmentHelper.getEarilestSrdReason(appntDtl.getEarliestSRD()));
		form.setInstallAppntDtl(appntDtl);
		if (earliestSRD.getBmoPermit() != null) {
			form.setBmoRemark(earliestSRD.getBmoPermit().getBmoRemark());
		}
		
	}

	private void setupSecDelInstallation(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		//Re-calculate Earliest SRD every time in case user go back and made changes
		if(order.getLtsAcqOtherVoiceServiceForm() != null 
				&& order.getLtsAcqOtherVoiceServiceForm().getCreate2ndDel() == true){
			LtsAppointmentDetail appntDtl = form.getSecDelInstallAppntDtl();
			if(appntDtl == null){
				appntDtl = form.new LtsAppointmentDetail();
			}
			if(order.getLtsAcqNumSelectionAndPipbForm().getPipbInfo() != null
					&& order.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().isDuplexInd()
					&& order.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexAction() == DuplexAction.PORT_IN_TOGETHER){
				appntDtl.setCutOverInd(true);
				form.setSecDelMustSameDateInd(true);
			}else{
				appntDtl.setCutOverInd(isSecDelTwoN(order));
				form.setSecDelMustSameDateInd(false);
			}
			appntDtl.setEarliestSRD(ltsAcqAppointmentService.getSecDelEarliestSRD(order, LtsAppointmentHelper.getToday(), order.getSbOrder() != null? order.getSbOrder().getOcid(): null, form.getInstallAppntDtl().getEarliestSRD()));
			appntDtl.setEarliestSRDReason(LtsAppointmentHelper.getEarilestSrdReason(appntDtl.getEarliestSRD()));
			form.setSecDelInstallAppntDtl(appntDtl);
		}else if(order.getLtsAcqNumSelectionAndPipbForm().getPipbInfo() != null
				&& order.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().isDuplexInd()
				&& order.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDuplexAction() == DuplexAction.PORT_IN_TOGETHER){
			LtsAppointmentDetail appntDtl = form.getSecDelInstallAppntDtl();
			if(appntDtl == null){
				appntDtl = form.new LtsAppointmentDetail();
			}
			appntDtl.setCutOverInd(true);
			form.setSecDelMustSameDateInd(true);
			appntDtl.setEarliestSRD(ltsAcqAppointmentService.getSecDelEarliestSRD(order, LtsAppointmentHelper.getToday(), order.getSbOrder() != null? order.getSbOrder().getOcid(): null, form.getInstallAppntDtl().getEarliestSRD()));
			appntDtl.setEarliestSRDReason(LtsAppointmentHelper.getEarilestSrdReason(appntDtl.getEarliestSRD()));
			form.setSecDelInstallAppntDtl(appntDtl);
		}else{
			//remove SecDel Appointment if not SecDel case
			form.setSecDelInstallAppntDtl(null);
		}
	}

	private void setupPortingAppointment(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		if(isFirstNewPortLater(order)){
			LtsAppointmentDetail appntDtl = form.getPortLaterAppntDtl();
			if(appntDtl == null){
				appntDtl = form.new LtsAppointmentDetail();
			}
			appntDtl.setCutOverInd(true);
			
			if(order.isContainPreInstallVAS())
			{
				//appntDtl.setEarliestSRDReason("Next day of Primary SRD +"+LtsConstant.PRE_INSTALL_MINDAY+" Days");
				appntDtl.setEarliestSRDReason(messageSource.getMessage("lts.acq.appointment.nextDayOfPrimarySRDXDays", new Object[] {LtsConstant.PRE_INSTALL_MINDAY}, this.locale));
			}
			else
			{
				//appntDtl.setEarliestSRDReason("Next day of Primary SRD +"+LtsSbOrderHelper.getPipbMinDay()+" Days +2 Days");
				appntDtl.setEarliestSRDReason(messageSource.getMessage("lts.acq.appointment.nextDayOfPrimarySRDXDays2Days", new Object[] {LtsSbOrderHelper.getPipbMinDay()}, this.locale));
			}			
			
			form.setPortLaterAppntDtl(appntDtl);
		}else{
			//remove Porting Appointment if not "First new port later" case
			form.setPortLaterAppntDtl(null);
		}
	}
	
	private List<ResourceSlotDTO> getResourceList(AcqOrderCaptureDTO order, String appntDate, String type){
		List<ResourceSlotDTO> installationTimeSlot = new ArrayList<ResourceSlotDTO>();
		ResourceDetailInputDTO resourceInput = ltsAcqAppointmentService.getResourceDetailInput(order, appntDate, type);
		ResourceDetailOutputDTO resourceOutput = appointmentDwfmService.getResourceDetail(resourceInput);
		
		if(StringUtils.equals(appntDate, LtsAppointmentHelper.getToday())){
			LtsAppointmentHelper.disableTimeSlotByType(resourceOutput, LtsConstant.APPOINTMENT_TIMESLOT_TYPE_AM);
		}
		if(order.isChannelDirectSales()){
			LtsAppointmentHelper.disableTimeSlotByType(resourceOutput, LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
		}
		
		installationTimeSlot = LtsAppointmentHelper.setupResourceList(resourceOutput, ltsAcqAppointmentService.getChangePonTimeSlotInd(order));
		return installationTimeSlot;
	}
	
	private void setupPcdOrderDetail(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form) throws DAOException{

//		if(order.getLtsModemArrangementForm() == null){
//			return;
//		}
		
		form.setSharePcdInd(true);
		
		form.setPcdOrderExistInd(false);
		
		boolean isSharePcd = false;
		String pcdSbid = "";
		
		if(order.getLtsModemArrangementForm()!=null)
		{
			if(ModemType.SHARE_BUNDLE.equals(order.getLtsModemArrangementForm().getModemType()) || ModemType.SHARE_PCD.equals(order.getLtsModemArrangementForm().getModemType()))
			{
				isSharePcd = true;
				pcdSbid = order.getLtsModemArrangementForm().getInputPcdSbOrderId();
			}
		}
		
		if(order.getLtsAcqBasketSelectionForm() != null && StringUtils.isNotBlank(order.getLtsAcqBasketSelectionForm().getPcdSbid()))
		{
			form.setPcdBundleBasket(true);
			pcdSbid = order.getLtsAcqBasketSelectionForm().getPcdSbid();
			form.setDelFree(order.getLtsAcqBasketSelectionForm().isDelFreeBundle());
		}
		
		if(order.getLtsPremiumSelectionForm() != null && StringUtils.isNotBlank(order.getLtsPremiumSelectionForm().getPcdSbid()))
		{
			List<ItemSetDetailDTO> premiumSetList = null;
			if(order.getLtsPremiumSelectionForm().getPremiumSetList()!=null)
			{
				premiumSetList = order.getLtsPremiumSelectionForm().getPremiumSetList();
			}
			
			ItemDetailDTO[] itemDetails = null;
			int selectedPcdItemCount = 0;
			for (int i=0; premiumSetList != null && i < premiumSetList.size(); i++  ) {
				int selectedItemCount = 0;
				
				if(premiumSetList.get(i).getItemSetType().equalsIgnoreCase("PREM-PCD"))
				{				
					itemDetails = premiumSetList.get(i).getItemDetails();
					if (!ArrayUtils.isEmpty(itemDetails)) {
						for (ItemDetailDTO itemDetail : itemDetails) {
							if (itemDetail.isSelected()) {
								selectedItemCount++;
							}
						}
					}
				}
				
				selectedPcdItemCount += selectedItemCount;
			}
			
			if(selectedPcdItemCount>0)
			{
				form.setPcdBundlePremium(true);
				pcdSbid = order.getLtsPremiumSelectionForm().getPcdSbid();
			}
		}
		
		if(form.isPcdBundleBasket() || form.isPcdBundlePremium())
		{
			isSharePcd = true;
			ImsSbOrderDTO pcdSbOrder = null;
			try {
				// TODO: Call IMS API to Retrieve PCD Order
				pcdSbOrder = ltsAcqRelatedPcdOrderService.retrievePcdSbOrder(pcdSbid, order, true);
				if (pcdSbOrder == null) {
					//mav.addObject("retrievePcdSbOrderError", "Springboard order not found.");
				}
			}
			catch (Exception e) {
				//mav.addObject("retrievePcdSbOrderError", "Fail to retrieve springboard order : " + ExceptionUtils.getMessage(e));
				
				//StringWriter errors = new StringWriter();
			    //e.printStackTrace(new PrintWriter(errors));
				//System.out.println("Fail to retrieve springboard order : " + errors.toString());
			}
			finally {
				order.setRelatedPcdOrder(pcdSbOrder);
			}
			form.setPcdOrderId(pcdSbid);
			form.setSharePcdInd(true);
		}
				
		if(!isSharePcd){
			form.setPcdOrderId(null);
			form.setSharePcdInd(false);
			return;
		}
		
		if(pcdSbid!=null && !pcdSbid.equalsIgnoreCase(""))
		{
			form.setPcdResourceShortage(ltsAcqAppointmentService.isResourceShortage(pcdSbid));
		}
		
		
		ImsSbOrderDTO pcdOrder = order.getRelatedPcdOrder();
		if(pcdOrder != null){
			form.setPcdOrderExistInd(true);
			form.setPcdOrderId(pcdOrder.getOrderId());
			
			form.setInstallationContactName(pcdOrder.getInstContactName());
			form.setInstallationContactNum(pcdOrder.getInstContactNum());
			form.setInstallationMobileSMSAlert(pcdOrder.getInstSmsNum());
			
			if(form.isPcdResourceShortage())
			{
				
				return;
			}
			
			form.setPreBookSerialNum(pcdOrder.getSerialNum());
			
			final String IN_FORMAT = "yyyy-MM-dd HH:mm:ss.0";
			final String OUT_FORMAT_DATE = "dd/MM/yyyy";
			final String OUT_FORMAT_TIME = "HH:mm";
			

			if(StringUtils.isNotBlank(pcdOrder.getAppntStartTime())
					&& StringUtils.isNotBlank(pcdOrder.getAppntEndTime())){
				String apptDate = LtsDateFormatHelper.reformatDate(pcdOrder.getAppntStartTime(), IN_FORMAT, OUT_FORMAT_DATE);
//				String srd = LtsDateFormatHelper.reformatDate(pcdOrder.getSrvReqDate(), IN_FORMAT, OUT_FORMAT_DATE);
//				if(StringUtils.equals(apptDate, srd)){
					//apptdate == srd; normal case
					
					String slot = LtsDateFormatHelper.reformatDate(pcdOrder.getAppntStartTime(), IN_FORMAT, OUT_FORMAT_TIME);
					slot = slot.concat("-").concat(LtsDateFormatHelper.reformatDate(pcdOrder.getAppntEndTime(), IN_FORMAT, OUT_FORMAT_TIME));
					if("PON".equals(pcdOrder.getTechnology())){
						LtsDateFormatHelper.convertToPonTime(slot);
					}
					String pcdAppntType = pcdOrder.getAppntType();
					if(StringUtils.isBlank(pcdAppntType)){	
						pcdAppntType = "W";
					}
					if(form.isPreWiring())
					{
						LtsAppointmentDetail appntDtl = form.getPreWiringAppntDtl();
						if(appntDtl == null){
							appntDtl = form.new LtsAppointmentDetail();
						}
						appntDtl.setAppntTimeSlotAndType(
								LtsDateFormatHelper.convertToSBTime(slot) + 
								LtsAppointmentHelper.TIMESLOT_DELIMITER + 
								pcdAppntType);
						
						appntDtl.setAppntDate(apptDate);
						
						form.setPreWiringAppntDtl(appntDtl);
					}
					else
					{
						form.getInstallAppntDtl().setAppntDate(apptDate);
						form.getInstallAppntDtl().setAppntTimeSlotAndType(
								LtsDateFormatHelper.convertToSBTime(slot) + 
								LtsAppointmentHelper.TIMESLOT_DELIMITER + 
								pcdAppntType);
					}
					
			}else{
				//apptdate == null; resource shortage
				form.getInstallAppntDtl().setAppntDate(LtsDateFormatHelper.reformatDate(pcdOrder.getSrvReqDate(), IN_FORMAT, OUT_FORMAT_DATE));
				form.getInstallAppntDtl().setAppntTimeSlotAndType(
						LtsConstant.APPOINTMENT_TIMESLOT_WHOLE + 
						LtsAppointmentHelper.TIMESLOT_DELIMITER +
						LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
			}
		}else{
			form.setPcdOrderExistInd(false);
		}
		
	}
	
	private void setupTentativeDetail(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		
		//check tentative
		if(ltsAcqAppointmentService.isBBShortage(order) || form.isPcdResourceShortage() && order.isEyeOrder() || form.isPcdResourceShortage() && form.isDelFree()){
			form.setBbShortageInd(true);
			form.setTentativeInd(true);
		}else{
			form.setBbShortageInd(false);
			LtsSRDDTO srd = form.getInstallAppntDtl().getEarliestSRD();
			if(LtsAppointmentHelper.tentativeReasonCodeList.contains(srd.getReasonCd())){
				form.setTentativeInd(true);
			}else{
				form.setTentativeInd(false);
			}
		}
		
		//put tentative values
		if(form.isTentativeInd()){
			DateTime tentativeSrd = new DateTime();
			if(form.isBbShortageInd()){
				tentativeSrd = tentativeSrd.plusDays(APPOINTMENT_BB_SHORTAGE_LEADTIME);
			}else{
				tentativeSrd = tentativeSrd.plusDays(APPOINTMENT_BLACKLIST_LEADTIME);
			}
			LtsAppointmentDetail appntDtl = form.getInstallAppntDtl();
			appntDtl.setAppntDate(tentativeSrd.toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
			appntDtl.setAppntTimeSlotAndType(
					LtsConstant.APPOINTMENT_TIMESLOT_10_TO_18 + 
					LtsAppointmentHelper.TIMESLOT_DELIMITER +
					LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
		}
		
		if(form.isBbShortageInd() || form.isTentativeInd()){
			form.setAllowAppntInd(false);
		}else{
			form.setAllowAppntInd(true);
		}
	}
	
	private void setupCutOverForAppntDtl(LtsAppointmentDetail appntDtl){
		if(appntDtl == null){
			return;
		}
		
		if(appntDtl.isCutOverInd()
				&& StringUtils.isNotBlank(appntDtl.getAppntDate())
				&& StringUtils.isNotBlank(appntDtl.getAppntTimeSlot()) ){
			JSONObject temp = LtsAppointmentHelper.calculateCutOverDateTime(appntDtl.getAppntDate(), appntDtl.getAppntTimeSlot(), ltsAcqAppointmentService.getPublicHolidayList());
			appntDtl.setCutOverDate((String)temp.get("date"));
			appntDtl.setCutOverTime((String)temp.get("timeSlot"));
		}
	}
	
	private boolean isUsePipbDn(AcqOrderCaptureDTO order){
		LtsAcqNumSelectionAndPipbFormDTO numSelectForm = order.getLtsAcqNumSelectionAndPipbForm();
		if(numSelectForm != null && (numSelectForm.getNumSelection() == Selection.USE_PIPB_DN)){
			return true;
		}

		return false;
	}
	
	private boolean isFirstNewPortLater(AcqOrderCaptureDTO order){
		LtsAcqNumSelectionAndPipbFormDTO numSelectForm = order.getLtsAcqNumSelectionAndPipbForm();
		if(numSelectForm != null && (numSelectForm.getNumSelection() == Selection.USE_NEW_DN_AND_PIPB_DN)){
			return true;
		}

		return false;
	}
	
	private boolean isSecDelTwoN(AcqOrderCaptureDTO order){
		String secDelStatus = order.getLtsAcqOtherVoiceServiceForm().getNew2ndDelSrvStatus();
		if(secDelStatus.compareTo(LtsConstant.INVENT_STS_WORKING) == 0){
			String new2ndDelDn = order.getLtsAcqOtherVoiceServiceForm().getNew2ndDelDn();
			if(new2ndDelDn != null){
				new2ndDelDn = StringUtils.leftPad(new2ndDelDn, 12, "0");
				if(StringUtils.equals(new2ndDelDn, order.getSecondDelServiceProfile().getSrvNum())
						&& order.getSecondDelServiceProfile().isTwoNBuildInd()){
					return true;
				}else if(order.getSecondDelServiceProfile().getDuplexNum() != null
						&& new2ndDelDn.compareTo(order.getSecondDelServiceProfile().getDuplexNum()) == 0
						&& order.getSecondDelServiceProfile().isDuplexTwoNBuildInd()){
					return true;
				}
			}
		}
		return false;
	}

	private boolean isContainPrewiringVAS(AcqOrderCaptureDTO order){
		if(order.getSbOrder() != null){
			return LtsSbOrderHelper.isPrewiringVasItemSelected(order.getSbOrder());
		}
		
		return order.isContainPrewiringVAS();
		
	}

	private void setMaxDate(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		form.setMaxDate(null);
		if(isFirstNewPortLater(order) || isUsePipbDn(order)){
			form.setMaxDate(DateTime.now().plusDays(LtsConstant.ACQ_PIPB_MAX_LEADTIME).toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
		}else{
			form.setMaxDate(DateTime.now().plusDays(LtsConstant.ACQ_NORMAL_MAX_LEADTIME).toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
		}
	}
	
	private void setDsMinDate(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form){
		if(form.getInstallAppntDtl() == null || form.getSecDelInstallAppntDtl() == null){
			return;
		}
		
		form.setDsMinDate(null);
		if(order.isChannelDirectSales()){
			DateTime installDate = form.getInstallAppntDtl().getEarliestSRD().getSRDate();
			DateTime secDelInstallDate = form.getSecDelInstallAppntDtl().getEarliestSRD().getSRDate();
			DateTime laterDate = installDate.isAfter(secDelInstallDate)? installDate : secDelInstallDate;
			
			form.setDsMinDate(laterDate.toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
		}
	}
	
	private void setupDsDtl(AcqOrderCaptureDTO order, LtsAcqAppointmentFormDTO form, BomSalesUserDTO salesInfo){
		if(LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_APPOINTMENT.equals(order.getOrderAction())
				&& LtsConstant.CHANNEL_CD_DIRECT_SALES_QC.equals(salesInfo.getChannelCd())){
			/*QC passed, ignore must QC checking*/
			form.setQcPassedInd(true);
			form.setPeLinkMandatory(false);
			
		}else{
			/*Check sales type = door knocked*/
			String salesType = salesInfo.getSalesType();
			if(StringUtils.isEmpty(salesType)){
				return;
			}
			
			if(order.getLtsDsOrderInfo() != null && StringUtils.isNotBlank(order.getLtsDsOrderInfo().getDsType())){
				salesType = order.getLtsDsOrderInfo().getDsType();
			}
			boolean isDoorKnocked = StringUtils.equals(salesType, LtsConstant.LTS_DS_SALES_TYPE_CD_DOOR_KNOCKED)
					|| StringUtils.equals(LtsConstant.SALES_TYPE_MAPPING.get(salesType), LtsConstant.LTS_DS_SALES_TYPE_CD_DOOR_KNOCKED);
			form.setDsDoorKnockedInd(isDoorKnocked);
			
			/*If cust is over 65 years old user must choose PE Link (Yes/No)*/
			if(order.getLtsAcqPersonalInfoForm() != null){
				//form.setPeLinkMandatory(ltsAcqAppointmentService.isAgeOver(order.getLtsAcqPersonalInfoForm().getDateOfBirth()));
				form.setPeLinkMandatory(LtsSbOrderHelper.isAgeOver(order.getLtsAcqPersonalInfoForm().getDateOfBirth()));
			}
			
			/*Calculate min SRD for cooling off period and PE link case*/
			form.setEarliestCoolOffSRD(LocalDate.now().plusDays(LtsConstant.LTS_DS_COOLING_OFF_LEADTIME)
					.toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
			form.setEarliestPeLinkSRD(LocalDate.now().plusDays(LtsConstant.LTS_DS_PE_LINK_LEADTIME)
					.toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
		}
		
		
	}
	
	private String getPcdActivationDate(AcqOrderCaptureDTO order) {

		String pcdActivationDate = null;
		List<PcdOrderCheckAttrDTO> pcdOrderCheckAttrList = new ArrayList<PcdOrderCheckAttrDTO>();
		
		if(StringUtils.isNotBlank(order.getLtsAcqBasketSelectionForm().getPcdSbid()) 
				&& "Y".equals(order.getSelectedBasket().getPreinstallCheck())
				&& StringUtils.isNotBlank(order.getSelectedBasket().getPcdBundleFreeType())){
			
			pcdOrderCheckAttrList = sbOrderInfoLtsService.getPcdSbOrderDetails(order.getLtsAcqBasketSelectionForm().getPcdSbid());
			if(pcdOrderCheckAttrList != null){
				for(PcdOrderCheckAttrDTO attr : pcdOrderCheckAttrList){
					if("ACTIVATION_DATE".equalsIgnoreCase(attr.getCheckAttribute())){
						return pcdActivationDate;
					}
				}
			}
		}
		return null;
	}
	
	
	public LtsAcqAppointmentService getLtsAcqAppointmentService() {
		return ltsAcqAppointmentService;
	}

	public void setLtsAcqAppointmentService(LtsAcqAppointmentService ltsAcqAppointmentService) {
		this.ltsAcqAppointmentService = ltsAcqAppointmentService;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(
			AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}

	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}

	public LtsAcqRelatedPcdOrderService getLtsAcqRelatedPcdOrderService() {
		return ltsAcqRelatedPcdOrderService;
	}

	public void setLtsAcqRelatedPcdOrderService(
			LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService) {
		this.ltsAcqRelatedPcdOrderService = ltsAcqRelatedPcdOrderService;
	}

	public CodeLkupCacheService getLtsDsSuspendReasonLkupCacheService() {
		return ltsDsSuspendReasonLkupCacheService;
	}

	public void setLtsDsSuspendReasonLkupCacheService(
			CodeLkupCacheService ltsDsSuspendReasonLkupCacheService) {
		this.ltsDsSuspendReasonLkupCacheService = ltsDsSuspendReasonLkupCacheService;
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

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

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
	
}
