package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.LtsAppointmentDetail;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.OrderSrvStatusDetailDTO;
import com.bomwebportal.lts.dto.form.LtsOrderAmendmentFormDTO;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.order.AmendAppointmentLtsDTO;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AmendPaymentDTO;
import com.bomwebportal.lts.dto.order.AmendPipbInfoDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.BmoPermitDetail;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.service.AddressRolloutService;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsOrderSearchService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.PipbActvLtsService;
import com.bomwebportal.lts.service.activity.PipbActivityLtsSubmissionService;
import com.bomwebportal.lts.service.apn.LtsApnSerialFileService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.order.AmendDetailEnquiryService;
import com.bomwebportal.lts.service.order.AmendRetrieveService;
import com.bomwebportal.lts.service.order.AmendmentSubmitService;
import com.bomwebportal.lts.service.order.AppointmentDwfmService;
import com.bomwebportal.lts.service.order.ImsSbOrderService;
import com.bomwebportal.lts.service.order.OrderCancelLtsService;
import com.bomwebportal.lts.service.order.OrderRecallService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.service.order.SbOrderInfoLtsService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.wsClientLts.BomWsBackendClient;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrderService;
import com.pccw.util.db.OracleSelectHelper;

public class OrderAmendCreateController extends SimpleFormController {
	
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected OrderRecallService orderRecallService;
	protected OrderCancelLtsService orderCancelLtsService;
	private OrderService orderService;
	private AmendRetrieveService amendRetrieveService;
	private AmendDetailEnquiryService amendDetailEnquiryService;
	private LtsPaymentService ltsPaymentService;
	private LtsAppointmentService ltsAppointmentService;
	private AppointmentDwfmService appointmentDwfmService;
	private CodeLkupCacheService relationshipCodeLkupCacheService;
	private CodeLkupCacheService relationshipBrCodeLkupCacheService;
	private CodeLkupCacheService amendNatureCancelEyeNoPPCodeLkupCacheService;
	private CodeLkupCacheService amendNatureCancelEyePPCodeLkupCacheService;
	private CodeLkupCacheService amendNatureCancelDelNoPPCodeLkupCacheService;
	private CodeLkupCacheService amendNatureCancelDelPPCodeLkupCacheService;
	private CodeLkupCacheService amendCancelReasonCodeLkupCacheService;
	private CodeLkupCacheService amendNatureCategoryCodeLkupCacheService;
	private CodeLkupCacheService amendNatureCancelCodeLkupCacheService;
	private CodeLkupCacheService amendNatureCategoryLTSCodeLkupCacheService;
	private CodeLkupCacheService delayReasonCodeLkupCacheService;
	private CodeLkupCacheService creditCardTypeLkupCacheService;
	private CodeLkupCacheService autoAmendModeLkupCacheService;
	private AmendmentSubmitService amendmentSubmitService;
	private ImsSbOrderService imsSbOrderService;
	private CodeLkupCacheService amendNatureSrdCodeLkupCacheService;	
	private CodeLkupCacheService amendNaturePaymentCodeLkupCacheService;
	private CodeLkupCacheService titleLkupCacheService;
	private AddressRolloutService addressRolloutService;
	private PipbActvLtsService pipbActvLtsService;
	private SaveSbOrderLtsService saveSbOrderLtsService;
	private BomWsBackendClient bomWsBackendClient;
	private LtsApnSerialFileService ltsApnSerialFileService;
	private CustomerProfileLtsService customerProfileLtsService;
	private OrderStatusService orderStatusService;
	private PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService;
	private LtsOrderSearchService ltsOrderSearchService;
	private CodeLkupCacheService ltsAmendCategoryRemarkCodeLkupCacheService;
	private SbOrderInfoLtsService sbOrderInfoLtsService;
	private LtsBasketService ltsBasketService;

	private final String viewName = "orderamendcreate";
	private final String currentView = "orderamendcreate.html";
//	private final String parentView ="orderamend.html?orderId=";
	private final int REMARK_MAX_LENGTH = 4000;
//	private final String PIPB_AMEND_CUST_NAME_WQ_NAUTRE = "364";
//	private final String PIPB_AMEND_FLAT_FLOOR_WQ_NAUTRE = "365";
	private final String[] PIPB_REMOVE_NATURE = {"303", "357"};
	private final String[] PIPB_REMOVE_BEFORE_CUTOFF_DATE_NATURE = {"356"};
	private final String[] CHANNEL_DS_REMOVE_NATURE = {"337"};
	private final String[] PREINSTALL_ALLOW_NATURE = {"336"};
	
	private Locale locale;
	private MessageSource messageSource;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LtsOrderAmendmentFormDTO orderAmendDTO = LtsSessionHelper.getOrderAmendForm(request);
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		setLocale(RequestContextUtils.getLocale(request));
		
		if(orderAmendDTO != null && StringUtils.isNotBlank(orderId)){
			if(!StringUtils.equals(orderId, orderAmendDTO.getSbOrderNum())){
				orderAmendDTO = null;
				LtsSessionHelper.setOrderAmendForm(request, null);
			}
		}
		
		if (orderAmendDTO==null){
			SbOrderDTO dummySbOrder = orderRetrieveLtsService.retrieveSbOrder(orderId, true);
			if(dummySbOrder == null){
				return new ModelAndView(LtsConstant.ERROR_VIEW);
			}
			LtsSessionHelper.setDummySbOrder(request, dummySbOrder);
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		BomSalesUserDTO bomSalesUserDTO = LtsSessionHelper.getBomSalesUser(request);
		LtsOrderAmendmentFormDTO orderAmendDTO = LtsSessionHelper.getOrderAmendForm(request);
		SbOrderDTO dummySbOrder = LtsSessionHelper.getDummySbOrder(request);
		BasketDetailDTO basketDetail = getSelectedBasketDetail(dummySbOrder);
		String pcdActivationDate = null;
		if(LtsSbOrderHelper.isNewAcquistionOrder(dummySbOrder) && basketDetail!=null){
		  pcdActivationDate = sbOrderInfoLtsService.getPcdActivationDate(LtsSbOrderHelper.getPcdSbid(dummySbOrder), basketDetail.getPreinstallCheck(), basketDetail.getPcdBundleFreeType());
		}
		String reformatPcdActivationDate = null;
		
		if(orderAmendDTO == null){
			orderAmendDTO = new LtsOrderAmendmentFormDTO();
			initForm(orderAmendDTO, dummySbOrder, bomSalesUserDTO);
			setupAllowence(orderAmendDTO, dummySbOrder, bomSalesUserDTO);
			setupSalesInfo(orderAmendDTO, bomSalesUserDTO);
			setupAppointment(orderAmendDTO, dummySbOrder);
			setupPayment(orderAmendDTO, dummySbOrder);
			setupAcqPipbOrderAmendments(orderAmendDTO, dummySbOrder);
		}
		
		//CHECK IF INVESTIGATION CASE ---Modified by MAX.R.MENG LTS
		if(StringUtils.equals(autoAmendModeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey(), LtsBackendConstant.AUTO_AMEND_SWITCH_ON)){
			OrderStatusDTO[] latest_status = orderStatusService.getStatus(dummySbOrder.getOrderId());	
			orderAmendDTO.setInvestigationInd(false);
			for(OrderStatusDTO status : latest_status){
				String legacy_status = status.getLegacyStatus();
				if(StringUtils.equals(legacy_status, LtsConstant.DRG_ORDER_STATUS_ADDRESS_INVESTIGATE)
						|| StringUtils.equals(legacy_status, LtsConstant.DRG_ORDER_STATUS_PAIR_INVESTIGATE)
						|| StringUtils.equals(legacy_status, LtsConstant.DRG_ORDER_STATUS_NUMBER_INVESTIGATE)){
					orderAmendDTO.setInvestigationInd(true);
					break;
			    }
	        }
		}
	
		if(StringUtils.equals(autoAmendModeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey(), LtsBackendConstant.AUTO_AMEND_SWITCH_ON)){
			orderAmendDTO.setPenaltyWaivedCaseInd(false);
			if(LtsSbHelper.isPenaltyHandled(dummySbOrder)){
				orderAmendDTO.setPenaltyWaivedCaseInd(true);
			}
		}
		
		
		///////////////////////////////////////////////////////////////////////	
		
		if(StringUtils.equals(orderAmendDTO.getSubmitInd(), "SUBMIT") && 
				StringUtils.equals(orderAmendDTO.getAmendedInd(), LtsConstant.FALSE_IND)){
			orderAmendDTO.setSubmitInd("INITIATE");
		}
		
		if(StringUtils.isNotBlank(pcdActivationDate)){
			reformatPcdActivationDate = LtsDateFormatHelper.reformatDate(pcdActivationDate, "yyyyMMdd", "dd/MM/yyyy");
	    }
		
		orderAmendDTO.setPcdActivationDate(reformatPcdActivationDate);
		
		setupEarliestSrd(orderAmendDTO, dummySbOrder, request);

		LtsSessionHelper.setOrderAmendForm(request, orderAmendDTO);
		
		request.setAttribute("isPreInstall", LtsSbHelper.isPreInstall(dummySbOrder)?"Y":"N");
		
		return orderAmendDTO;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		SbOrderDTO sbOrder = LtsSessionHelper.getDummySbOrder(request);
		if (sbOrder == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		BomSalesUserDTO userInfo = LtsSessionHelper.getBomSalesUser(request);
			
		LtsOrderAmendmentFormDTO orderAmendDTO = (LtsOrderAmendmentFormDTO) command;

		ServiceDetailLtsDTO primarySrvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO secDelSrvDtl = LtsSbOrderHelper.get2ndDelService(sbOrder);
		ServiceDetailLtsDTO pipbSrvDtl = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		
		//CHECK DRAGON ORDER EXISTS -- Modified by Max.R.MENG LTS	
		String autoAmendMode = autoAmendModeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey();
		if(LtsConstant.AUTO_AMEND_SWITCH_ON.equals(autoAmendMode)){
			if(StringUtils.isEmpty(orderAmendDTO.getBomOcId()) 
					&& !StringUtils.equals("Y", primarySrvDtl.getFfpOnlyInd())){

				if(orderAmendDTO.isCancelOrderInd() && !StringUtils.equals(primarySrvDtl.getSbStatus(), LtsBackendConstant.ORDER_STATUS_FORCED_WQ)){
				  errors.rejectValue("cancelOrderInd", "lts.cancel.not.allow");
				}
				
				if(orderAmendDTO.isAmendAppointmentInd()){
	    		  errors.rejectValue("amendAppointmentInd", "lts.updateSRD.not.allow");	
				}
			}	
		}
		
		orderAmendDTO.setErrorMsg(null);
		if(StringUtils.equals(orderAmendDTO.getSubmitInd(), "APPOINTMENT_CONFIRM")){
			return this.confirmAppointment(orderAmendDTO, sbOrder);
		}else if(StringUtils.equals(orderAmendDTO.getSubmitInd(), "APPOINTMENT_CANCEL")){
			String serial = orderAmendDTO.getPreBookSerialNum();
			if(!StringUtils.isBlank(serial)){
				orderAmendDTO.setPreBookSerialNum(null);
				orderAmendDTO.setConfirmedInd(LtsConstant.FALSE_IND);
				orderAmendDTO.setErrorMsg(messageSource.getMessage("lts.ltsOrdAmendCreate.apptCancelled", new Object[] {}, this.locale));
			}
			return new ModelAndView(new RedirectView(currentView));
		}else{
			Map<String, String> requiredDocMap = new HashMap<String, String>();
			
			AmendLtsDTO amend = new AmendLtsDTO();
			amend.setOrderId(orderAmendDTO.getSbOrderNum());
			amend.setOcid(getOCID(orderAmendDTO.getBomOcId(), primarySrvDtl, pipbSrvDtl));
			amend.setSalesChannel(orderAmendDTO.getSalesChannel());
			amend.setSalesContactNum(orderAmendDTO.getSalesContactNum());
			amend.setShopCd(orderAmendDTO.getShopCode());
			amend.setStaffNum(orderAmendDTO.getStaffNum());
			amend.setSalesCd(orderAmendDTO.getSalesId());
			amend.setName(orderAmendDTO.getName());
			amend.setRelationshipCd(orderAmendDTO.getRelationship());
			amend.setContactNum(orderAmendDTO.getContactNum());
			
			List<AmendDetailLtsDTO> amendDtls = new ArrayList<AmendDetailLtsDTO>();
			
			if(pipbSrvDtl != null){
				amend.setPipbOrder(true);
			}
			
			AmendDetailLtsDTO ltsAmendDetail = null;
			if (StringUtils.equals(orderAmendDTO.getSerNum(),primarySrvDtl.getSrvNum())){ // Primary service selected
				ltsAmendDetail = this.createAmendDetail(orderAmendDTO, primarySrvDtl, sbOrder);
				if (ltsAmendDetail.getCategoryList()!=null && ltsAmendDetail.getCategoryList().size()> 0){
					amendDtls.add(ltsAmendDetail);
				}
			}
			
			if(orderAmendDTO.getSecDelAppntDtl() != null){
				boolean isAmendSecDelOnly = false;
				if (StringUtils.equals(orderAmendDTO.getSerNum(),secDelSrvDtl.getSrvNum())){ //Del service selected
					isAmendSecDelOnly = true;
				}
				if(pipbSrvDtl != null 
						&& isAmendSecDelOnly
						&& orderAmendDTO.isCancelOrderInd()
						&& LtsConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER.equals(pipbSrvDtl.getPipb().getDuplexAction())){
					errors.rejectValue("cancelOrderInd", "lts.amend.pipb.secDel.portInTogether.cancel");
				}else{
					AmendDetailLtsDTO secDel = this.create2ndDelAmendDetail(orderAmendDTO, ltsAmendDetail, sbOrder, isAmendSecDelOnly);
					if (secDel.getCategoryList()!=null && secDel.getCategoryList().size()> 0){
						amendDtls.add(secDel);
					}
				}
			}			
			
			if(amendDtls.size() > 0){
				amend.setAmendDtlList(amendDtls);
				orderAmendDTO.setAmendLtsDTO(amend);
			}
			
			if(orderAmendDTO.isAmendDocInd()){
				/*add document to upload*/
				if(orderAmendDTO.getPipbInfo() != null){
					requiredDocMap.put(LtsConstant.ORDER_DOC_TYPE_NSD, LtsConstant.ITEM_MDO_OPTIONAL);
					requiredDocMap.put(LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED, LtsConstant.ITEM_MDO_OPTIONAL);
				}
			}

			if(orderAmendDTO.isAmendAcqPipbInd()){
				requiredDocMap.put(LtsConstant.ORDER_DOC_TYPE_NSD, LtsConstant.ITEM_MDO_MANDATORY);
				requiredDocMap.put(LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED, LtsConstant.ITEM_MDO_OPTIONAL);
				if(!validatePipbAmendAddr(sbOrder, orderAmendDTO)){
					errors.rejectValue("pipbAmendFlatFloorInd", "lts.acq.amend.addrSB.invalid");
				}
				
			}
			
			if(errors.hasErrors()){
				ModelAndView mav = new ModelAndView(viewName, referenceData(request, command, errors));
				mav.addObject("orderamend", orderAmendDTO);
				mav.addAllObjects(errors.getModel());
				return mav;
			}
			
			
			orderAmendDTO.setRequiredDocMap(requiredDocMap);
			String url = null;
			if(requiredDocMap != null && requiredDocMap.size() > 0){
				String pageView = "ltsdoccapture";
				if(userInfo.getChannelId() == 2){
					pageView = "ltsacqcollectdoc";
				}
				StringBuilder sb = new StringBuilder(pageView+".html?orderId=" + orderAmendDTO.getSbOrderNum());
				sb.append("&isAmend=true");
//				for(String docType : requiredDocList){
//					sb.append("&docType=" + docType);
//				}
				url = sb.toString();
			}else{
				url = "orderamendsubmit.html";
			}
			
			ModelAndView nextView = new ModelAndView(new RedirectView(url));
			return nextView;
		}
	}
	
	private boolean validatePipbAmendAddr(SbOrderDTO sbOrder, LtsOrderAmendmentFormDTO orderAmendDTO){
		if(orderAmendDTO.isAmendAcqPipbInd() && orderAmendDTO.isPipbAmendFlatFloorInd()){
			String getSrvBdary = sbOrder.getAddress().getSerbdyno();
			AddressRolloutDTO addressRollout = addressRolloutService.getAddressRollout(getSrvBdary, orderAmendDTO.getPipbFlat(), orderAmendDTO.getPipbFloor());
			if(addressRollout == null || !StringUtils.equals(addressRollout.getSrvBdary(), getSrvBdary)){
				return false;
			}
		}
		
		return true;
	}
	
	public ModelAndView confirmAppointment(LtsOrderAmendmentFormDTO orderAmendDTO,SbOrderDTO sbOrder){
    	String date = orderAmendDTO.getCoreSrvAppntDtl().getAppntDate();
		String timeSlot = orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlot();
		String timeSlotType = orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlotType();
		LtsAppointmentDetail secDelAppntDtl = orderAmendDTO.getSecDelAppntDtl();
		LtsAppointmentDetail portLaterAppntDtl = orderAmendDTO.getPortLaterAppntDtl();
		LtsAppointmentDetail preWiringAppntDtl = orderAmendDTO.getPreWiringAppntDtl();
		
		if(date != null && timeSlot != null){
			boolean coreAppntAmendFlag = !StringUtils.equals(date, orderAmendDTO.getOriginalInstallationDate()) 
					|| !StringUtils.equals(timeSlot, orderAmendDTO.getOriginalInstallationTime());
			boolean secDelAppntAmendFlag = secDelAppntDtl != null? 
					!StringUtils.equals(secDelAppntDtl.getAppntDate(), orderAmendDTO.getOriginalSecDelInstallationDate())
					|| !StringUtils.equals(secDelAppntDtl.getAppntTimeSlot(), orderAmendDTO.getOriginalSecDelInstallationTime())
						: false;
			
			if(coreAppntAmendFlag || secDelAppntAmendFlag){
				ServiceDetailLtsDTO coreSrvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
					
				if("Y".equals(coreSrvDtl.getForceFieldVisitInd())
						&& !LtsConstant.ORDER_STATUS_CLOSED.equals(coreSrvDtl.getSbStatus())){
					//PrebookAppointmentInputDTO input = ltsAppointmentService.getPrebookAppointmentInput(sbOrder, date, timeSlot, orderAmendDTO.getStaffNum(), timeSlotType);
					// LTS_APPOINT_TIMESLOT fix
					PrebookAppointmentInputDTO input = ltsAppointmentService.getPrebookAppointmentInput(sbOrder, date, LtsDateFormatHelper.revertToBomTime(timeSlot), orderAmendDTO.getStaffNum(), timeSlotType);
					//
					PrebookAppointmentOutputDTO output = appointmentDwfmService.getPrebookAppointment(input);
					if(output != null){
						if(output.getSerialNum() != null){
							orderAmendDTO.setPreBookSerialNum(output.getSerialNum());
							orderAmendDTO.setConfirmedInd(LtsConstant.TRUE_IND);
						}else if(output.getErrorMsg() != null){
							orderAmendDTO.setPreBookSerialNum(null);
							orderAmendDTO.setConfirmedInd(LtsConstant.FALSE_IND);
							orderAmendDTO.setAmendedInd(LtsConstant.FALSE_IND);
							orderAmendDTO.setErrorMsg(output.getErrorMsg());
						}else{
							orderAmendDTO.setPreBookSerialNum(null);
							orderAmendDTO.setConfirmedInd(LtsConstant.FALSE_IND);
							orderAmendDTO.setAmendedInd(LtsConstant.FALSE_IND);
							orderAmendDTO.setErrorMsg(messageSource.getMessage("lts.ltsOrdAmendCreate.apptFailed", new Object[] {}, this.locale));
						}
					}
				}
			}else{
				boolean preWiringAppntAmendFlag = preWiringAppntDtl != null?
						!StringUtils.equals(preWiringAppntDtl.getAppntDate(), orderAmendDTO.getOriginalPreWiringDate())
						|| !StringUtils.equals(preWiringAppntDtl.getAppntTimeSlot(), orderAmendDTO.getOriginalPreWiringTime())
							: false;
						
				boolean portLaterAppntAmendFlag = portLaterAppntDtl != null?
						!StringUtils.equals(portLaterAppntDtl.getAppntDate(), orderAmendDTO.getOriginalPortLaterDate())
						|| !StringUtils.equals(portLaterAppntDtl.getAppntTimeSlot(), orderAmendDTO.getOriginalPortLaterTime())
							: false;
						
				if(portLaterAppntAmendFlag || preWiringAppntAmendFlag){
					orderAmendDTO.setConfirmedInd(LtsConstant.TRUE_IND);
				}else{
					orderAmendDTO.setConfirmedInd(LtsConstant.FALSE_IND);
					orderAmendDTO.setAmendedInd(LtsConstant.FALSE_IND);
					orderAmendDTO.setErrorMsg(messageSource.getMessage("lts.ltsOrdAmendCreate.apptNoChn", new Object[] {}, this.locale));
				}
			}
		}
		return new ModelAndView(new RedirectView(currentView));
	}
	
	private AmendPaymentDTO createPaymentAmendment(LtsOrderAmendmentFormDTO orderAmendDTO){
		AmendPaymentDTO paymentOrder = (AmendPaymentDTO)createNewAmendCategory(orderAmendDTO, AmendPaymentDTO.class, false);;
		paymentOrder.setCcExpDate(orderAmendDTO.getExpireMonth() + "/" + orderAmendDTO.getExpireYear());
		paymentOrder.setCcHoldName(orderAmendDTO.getCardHolderName());
		paymentOrder.setCcIdDocNo(orderAmendDTO.getCardHolderDocNum());
		paymentOrder.setCcIdDocType(orderAmendDTO.getCardHolderDocType());
		paymentOrder.setCcNum(orderAmendDTO.getCardNum());
		paymentOrder.setCcType(orderAmendDTO.getCardType());
		paymentOrder.setCcThirdPartyInd(orderAmendDTO.getThirdPartyCard()?"Y":"N");
		paymentOrder.setCcVerifiedInd(orderAmendDTO.isCardVerified()?"Y":"N");
		paymentOrder.setFaxSerialNum(orderAmendDTO.getFaxSerialNum());
		try{
			paymentOrder.setNature(amendNaturePaymentCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey());
		} catch (DAOException daoe) {
			daoe.printStackTrace();
		}
		
		return paymentOrder;
	}
	
	private AmendDetailLtsDTO createAmendDetail(LtsOrderAmendmentFormDTO orderAmendDTO, ServiceDetailLtsDTO ltsSrvDtl, SbOrderDTO sbOrder){
		
		AmendDetailLtsDTO amendDetail = new AmendDetailLtsDTO();
		ServiceDetailLtsDTO pipbPortLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		ServiceDetailLtsDTO secDelSrv = LtsSbOrderHelper.get2ndDelService(sbOrder);
		ServiceDetailOtherLtsDTO srvDtlIms = LtsSbOrderHelper.getImsEyeService(sbOrder.getSrvDtls());
	
		amendDetail.setSrvNum(ltsSrvDtl.getSrvNum());
		amendDetail.setSrvType(ltsSrvDtl.getTypeOfSrv());
		amendDetail.setDtlId(ltsSrvDtl.getDtlId());
		//Added by karen 20131211 start
		amendDetail.setFromProd(ltsSrvDtl.getFromProd());
		amendDetail.setToProd(ltsSrvDtl.getToProd());
		
		if (srvDtlIms != null) {	
			if (StringUtils.isEmpty(srvDtlIms.getRelatedSbOrderId())) {
				amendDetail.setRelatedSrvNum(srvDtlIms.getSrvNum());	
			} else {
				amendDetail.setRelatedSrvNum(this.imsSbOrderService.getFsaNumOnImsSbOrder(srvDtlIms.getRelatedSbOrderId()));	
			}
			amendDetail.setRelatedSrvType(srvDtlIms.getTypeOfSrv());
		}
		
		if(pipbPortLaterSrv != null){
			amendDetail.setRelatedSrvNum(pipbPortLaterSrv.getSrvNum());	
			amendDetail.setRelatedSrvType(pipbPortLaterSrv.getTypeOfSrv());
		}
		
		//Added by karen 20131211 end
		
		List<AmendCategoryLtsDTO> categoryList = new ArrayList<AmendCategoryLtsDTO>();
		
		if(orderAmendDTO.isCancelOrderInd()){
			String cancelRemark = orderAmendDTO.getCancelRemark();
			if(!StringUtils.isEmpty(orderAmendDTO.getCancelType())){
				AmendCategoryLtsDTO cancelOrder = createNewAmendCategory(orderAmendDTO, null, false);
				cancelOrder.setNature(orderAmendDTO.getCancelType());
				if(orderAmendDTO.isFirstMonthCcPrepaymentInd()){
					StringBuilder ccPrepayRemarks = new StringBuilder();
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.ccToken", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getCardNum());
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.ccName", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getCardHolderName());
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.ccExpDate", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getExpireMonth() + "/" + orderAmendDTO.getExpireYear());
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.dn", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getSerNum());
					if(orderAmendDTO.isShowPrepaymentRejectInd()){ //cancel date > 7 from signoff date
						if(orderAmendDTO.isPrepaymentRejectInd()){
							ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.prePayRej", new Object[] {}, this.locale));
						}else{
							ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.prePayNotRej", new Object[] {}, this.locale));
						}
					}else{
						ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.prePayNotRej", new Object[] {}, this.locale));
					}
					
					cancelRemark = cancelRemark+"\n"+ccPrepayRemarks;
				}

				List<String> cancelRemarks = new ArrayList<String>();
				String cancelReason =  messageSource.getMessage("lts.ltsOrdAmendCreate.canRea", new Object[] {orderAmendDTO.getCancelReason()}, this.locale) + "\n";
//				cancelOrder.appendRemarks(getRemarks(splitEqually(cancelReason + cancelRemark, REMARK_MAX_LENGTH)));

				cancelRemarks.addAll(Arrays.asList(splitEqually(cancelReason + cancelRemark, REMARK_MAX_LENGTH)));
				
				/*FFP*/
				if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())
						|| LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())){
					if(LtsSbOrderHelper.isInstallFfpItem(sbOrder)){
						//FFP CANCELATION 
						String latestSrd = StringUtils.substring(ltsSrvDtl.getAppointmentDtl().getAppntStartTime(), 0 , 10);
						if(LocalDate.now().isAfter(LocalDate.parse(latestSrd, DateTimeFormat.forPattern("dd/MM/yyyy")))){
							cancelRemarks.addAll(Arrays.asList(splitEqually(messageSource.getMessage("lts.ltsOrdAmendCreate.cancelFFPWithPen", new Object[] {}, this.locale), REMARK_MAX_LENGTH)));
						}else{
							cancelRemarks.addAll(Arrays.asList(splitEqually(messageSource.getMessage("lts.ltsOrdAmendCreate.cancelFFP", new Object[] {}, this.locale), REMARK_MAX_LENGTH)));
						}
					}

					if(LtsSbOrderHelper.isInstallFreeCallPlanItem(sbOrder)){
						cancelRemarks.addAll(Arrays.asList(splitEqually(messageSource.getMessage("lts.ltsOrdAmendCreate.iddFreeMins", new Object[] {}, this.locale), REMARK_MAX_LENGTH)));
					}
					
				}else if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) && ltsSrvDtl.getSrvCallPlanDtls() != null){
					for(ServiceCallPlanDetailLtsDTO dtl: ltsSrvDtl.getSrvCallPlanDtls()){
						if(LtsConstant.IO_IND_OUT.equals(dtl.getIoInd()) ){
							cancelRemarks.addAll(Arrays.asList(splitEqually(messageSource.getMessage("lts.ltsOrdAmendCreate.disOrderWithFFP", new Object[] {}, this.locale), REMARK_MAX_LENGTH)));
							break;
						}
					}
				}
				
				//Penalty waive remark --- Max.R.Meng
				if(LtsSbOrderHelper.isPenaltyWaivedPlan(sbOrder)){
					cancelRemarks.addAll(Arrays.asList(splitEqually(messageSource.getMessage("lts.ltsOrdAmendCreate.chnWaivePen", new Object[] {}, this.locale), REMARK_MAX_LENGTH)));
				}
				
				if(secDelSrv != null && orderAmendDTO.getPipbInfo() != null 
						&& orderAmendDTO.isCancelOrderInd() 
						&& StringUtils.equals(orderAmendDTO.getPipbInfo().getDuplexAction(), LtsConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER) ){
					cancelRemarks.addAll(Arrays.asList(splitEqually(messageSource.getMessage("lts.ltsOrdAmendCreate.cancel2nd", new Object[] {secDelSrv.getSrvNum()}, this.locale) , REMARK_MAX_LENGTH)));
				}
				
				if(LtsSbHelper.isPreInstall(sbOrder))
				{
					cancelRemarks.addAll(Arrays.asList(splitEqually(messageSource.getMessage("lts.ltsOrdAmendCreate.cancelPreInstall", new Object[] {}, this.locale), REMARK_MAX_LENGTH)));
				}
				
				cancelOrder.appendRemarks(getRemarks(cancelRemarks.toArray(new String[0])));
				categoryList.add(cancelOrder);
			}
		}else{
			if(orderAmendDTO.isAmendAppointmentInd()){
				AmendAppointmentLtsDTO apptOrder = (AmendAppointmentLtsDTO)createNewAmendCategory(orderAmendDTO, AmendAppointmentLtsDTO.class, false);
				
				apptOrder.setPrimarySrvStatus(ltsSrvDtl.getSbStatus());
				
				try {
					apptOrder.setNature(amendNatureSrdCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey());
				} catch (DAOException daoe) {
					daoe.printStackTrace();
				}
				String[] fromTime = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getOriginalInstallationDate(), 
						LtsDateFormatHelper.revertToBomTime(orderAmendDTO.getOriginalInstallationTime()));
				apptOrder.setFromAppntStartTime(fromTime[0]);
				apptOrder.setFromAppntEndTime(fromTime[1]);
				String[] time = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate(), 
						LtsDateFormatHelper.revertToBomTime(orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlot()));
				apptOrder.setAppntStartTime(time[0]);
				apptOrder.setAppntEndTime(time[1]);
				apptOrder.setAppntType(orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlotType());
				if(orderAmendDTO.getCoreSrvAppntDtl().isCutOverInd()){
					time = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getCoreSrvAppntDtl().getCutOverDate(), 
							orderAmendDTO.getCoreSrvAppntDtl().getCutOverTime());
					apptOrder.setCutOverStartTime(time[0]);
					apptOrder.setCutOverEndTime(time[1]);
				}
				if(orderAmendDTO.getPreWiringAppntDtl() != null){
					time = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getPreWiringAppntDtl().getAppntDate(), 
							orderAmendDTO.getPreWiringAppntDtl().getAppntTimeSlot());
					apptOrder.setPreWiringStartTime(time[0]);
					apptOrder.setPreWiringEndTime(time[1]);
					apptOrder.setPreWiringType(orderAmendDTO.getPreWiringAppntDtl().getAppntTimeSlotType());
				}
				apptOrder.setSerialNum(orderAmendDTO.getPreBookSerialNum());
				apptOrder.setDelayReasonCd(orderAmendDTO.getDelayReasonCode());
				
				//Set referenceOrdAppnt; Added by Karen
				AppointmentDetailLtsDTO referenceOrdAppnt = ltsSrvDtl.getAppointmentDtl();
				referenceOrdAppnt.setAppntStartTime(apptOrder.getAppntStartTime());
				referenceOrdAppnt.setAppntEndTime(apptOrder.getAppntEndTime());
				referenceOrdAppnt.setCutOverStartTime(apptOrder.getCutOverStartTime());
				referenceOrdAppnt.setCutOverEndTime(apptOrder.getCutOverEndTime());
				referenceOrdAppnt.setPreWiringStartTime(apptOrder.getPreWiringStartTime());
				referenceOrdAppnt.setPreWiringEndTime(apptOrder.getPreWiringEndTime());
				referenceOrdAppnt.setAppntType(apptOrder.getAppntType());
				referenceOrdAppnt.setPreWiringType(apptOrder.getPreWiringType());
				referenceOrdAppnt.setSerialNum(apptOrder.getSerialNum());
				
				//Added by karen 20131213 start set appnt datetime to order
				apptOrder.setReferenceOrdAppnt(referenceOrdAppnt);
				//Added by karen 20131213 end
				
				apptOrder.setSrd(apptOrder.getAppntStartTime());

				//ACQ PIPB
				if(orderAmendDTO.getPortLaterAppntDtl() != null && pipbPortLaterSrv != null && pipbPortLaterSrv.getAppointmentDtl() != null){
					apptOrder.setFromPipbAppntStartTime(pipbPortLaterSrv.getAppointmentDtl().getAppntStartTime());
					apptOrder.setFromPipbAppntEndTime(pipbPortLaterSrv.getAppointmentDtl().getAppntEndTime());
					time = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getPortLaterAppntDtl().getAppntDate(), 
							orderAmendDTO.getPortLaterAppntDtl().getAppntTimeSlot());
					apptOrder.setPipbAppntStartTime(time[0]);
					apptOrder.setPipbAppntEndTime(time[1]);
					if(orderAmendDTO.getPortLaterAppntDtl().isCutOverInd()){
						time = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getPortLaterAppntDtl().getCutOverDate(), 
								orderAmendDTO.getPortLaterAppntDtl().getCutOverTime());
						apptOrder.setPipbCutOverStartTime(time[0]);
						apptOrder.setPipbCutOverEndTime(time[1]);
					}
					apptOrder.setPipbAppntType(orderAmendDTO.getPortLaterAppntDtl().getAppntTimeSlotType());
					
					AppointmentDetailLtsDTO referenceOrdPipbAppnt = pipbPortLaterSrv.getAppointmentDtl();
					referenceOrdPipbAppnt.setAppntStartTime(apptOrder.getPipbAppntStartTime());
					referenceOrdPipbAppnt.setAppntEndTime(apptOrder.getPipbAppntEndTime());
					referenceOrdPipbAppnt.setCutOverStartTime(apptOrder.getPipbCutOverStartTime());
					referenceOrdPipbAppnt.setCutOverEndTime(apptOrder.getPipbCutOverEndTime());
					referenceOrdPipbAppnt.setAppntType(orderAmendDTO.getPortLaterAppntDtl().getAppntTimeSlotType());
					apptOrder.setReferenceOrdPipbAppnt(referenceOrdPipbAppnt);
					apptOrder.setPipbSrvDtlId(pipbPortLaterSrv.getDtlId());
					
					if(orderAmendDTO.isAcqOrderCompleted()){
						apptOrder.setSrd(apptOrder.getPipbAppntStartTime());
					}
				}
				
				boolean pipbDateChanged = false;
				
				if(orderAmendDTO.getPortLaterAppntDtl() != null)
				{
					String fromPipbDate = LtsDateFormatHelper.getDateFromDTOFormat(apptOrder.getFromPipbAppntStartTime());
					String fromPipbTimeslot = LtsDateFormatHelper.getFromToTimeFormat(apptOrder.getFromPipbAppntStartTime(), apptOrder.getFromPipbAppntEndTime());
					String targetPipbDate = LtsDateFormatHelper.getDateFromDTOFormat(apptOrder.getPipbAppntStartTime());
					String targetPipbTimeslot = LtsDateFormatHelper.getFromToTimeFormat(apptOrder.getPipbAppntStartTime(), apptOrder.getPipbAppntEndTime());
					
					if(!StringUtils.equals(fromPipbDate,targetPipbDate)
						|| !StringUtils.equals(LtsDateFormatHelper.convertToSBTime(fromPipbTimeslot), targetPipbTimeslot))
					{
						pipbDateChanged = true;
					}
					
				}
				
				/*FFP*/
				if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())
						|| LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())){
					if(LtsSbOrderHelper.isInstallFfpItem(sbOrder)){
						String remark = messageSource.getMessage("lts.ltsOrdAmendCreate.amendEffDateFfp", new Object[] {}, this.locale);
						if(LtsSbHelper.isPreInstall(sbOrder))
						{
							ServiceDetailLtsDTO portLaterSrv = LtsSbHelper.getAcqPortLaterService(sbOrder);
							remark = messageSource.getMessage("lts.ltsOrdAmendCreate.amendEffDateFfpPipb", new Object[] {}, this.locale)
									+ LtsDateFormatHelper.getDateFromDTOFormat(portLaterSrv.getAppointmentDtl().getAppntStartTime())
									+ LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(
											portLaterSrv.getAppointmentDtl().getAppntStartTime(),
											portLaterSrv.getAppointmentDtl().getAppntEndTime()));
							
							if(!pipbDateChanged)
							{
								remark = "";
							}
							
						}		
						if(!"".equals(remark))
						{
							apptOrder.appendRemarks(getRemarks(splitEqually(remark, REMARK_MAX_LENGTH)));
						}
					}
					
					if(LtsSbOrderHelper.isInstallFreeCallPlanItem(sbOrder)){
						String remark = messageSource.getMessage("lts.ltsOrdAmendCreate.amendEffDateIdd", new Object[] {}, this.locale);
						if(LtsSbHelper.isPreInstall(sbOrder))
						{
							ServiceDetailLtsDTO portLaterSrv = LtsSbHelper.getAcqPortLaterService(sbOrder);
							remark = messageSource.getMessage("lts.ltsOrdAmendCreate.amendEffDateIddPipb", new Object[] {}, this.locale)
									+ LtsDateFormatHelper.getDateFromDTOFormat(portLaterSrv.getAppointmentDtl().getAppntStartTime())
									+ LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(
											portLaterSrv.getAppointmentDtl().getAppntStartTime(),
											portLaterSrv.getAppointmentDtl().getAppntEndTime()));
						}
						if(!pipbDateChanged)
						{
							remark = "";
						}
						if(!"".equals(remark))
						{
							apptOrder.appendRemarks(getRemarks(splitEqually(remark, REMARK_MAX_LENGTH)));
						}
						
					}
				}else if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) && ltsSrvDtl.getSrvCallPlanDtls() != null){
					for(ServiceCallPlanDetailLtsDTO dtl: ltsSrvDtl.getSrvCallPlanDtls()){
						if(LtsConstant.IO_IND_OUT.equals(dtl.getIoInd()) ){
							String remark = "Alert - FFP REMOVED, UPDATE TERMINATION DATE";
							apptOrder.appendRemarks(getRemarks(splitEqually(remark, REMARK_MAX_LENGTH)));
							break;
						}
					}
				}
				
				//COMPARE DATETIME ---Modified by Max.R.MENG
				boolean appntChangedFlag = false;
				
				if(!StringUtils.equals(apptOrder.getFromAppntStartTime(),apptOrder.getAppntStartTime())
						|| !StringUtils.equals(apptOrder.getFromAppntEndTime(), apptOrder.getAppntEndTime())){
					appntChangedFlag = true;
				}
				
				if(orderAmendDTO.getPreWiringAppntDtl() != null
						&& (!StringUtils.equals(ltsSrvDtl.getAppointmentDtl().getPreWiringStartTime(),apptOrder.getPipbAppntStartTime())
								|| !StringUtils.equals(ltsSrvDtl.getAppointmentDtl().getPreWiringEndTime(), apptOrder.getPipbAppntEndTime()))){
					appntChangedFlag = true;
				}
				
				if(orderAmendDTO.getPortLaterAppntDtl() != null
						&& (!StringUtils.equals(apptOrder.getFromPipbAppntStartTime(),apptOrder.getPipbAppntStartTime())
								|| !StringUtils.equals(apptOrder.getFromPipbAppntEndTime(), apptOrder.getPipbAppntEndTime()))){
					appntChangedFlag = true;
				}
				
				if(appntChangedFlag){
					categoryList.add(apptOrder);
				}
				
			}
			if(orderAmendDTO.isAmendCreditCardInd()){
				categoryList.add(this.createPaymentAmendment(orderAmendDTO)); //Added by karen
			}
			//Added by Karen 20131211 start
	
			if (orderAmendDTO.isCategory1Ind()){
				AmendCategoryLtsDTO category1 = createNewAmendCategory(orderAmendDTO, null, false);
				category1.setNature(orderAmendDTO.getCategory1());
				category1.appendRemarks(getRemarks(splitEqually(orderAmendDTO.getContent1(), REMARK_MAX_LENGTH)));
				categoryList.add(category1);
			}
			if (orderAmendDTO.isCategory2Ind()){
				AmendCategoryLtsDTO category2 = createNewAmendCategory(orderAmendDTO, null, false);
				category2.setNature(orderAmendDTO.getCategory2());
				category2.appendRemarks(getRemarks(splitEqually(orderAmendDTO.getContent2(), REMARK_MAX_LENGTH)));
				categoryList.add(category2);	
			}
			if (orderAmendDTO.isCategory3Ind()){
				AmendCategoryLtsDTO category3 = createNewAmendCategory(orderAmendDTO, null, false);
				category3.setNature(orderAmendDTO.getCategory3());
				category3.appendRemarks(getRemarks(splitEqually(orderAmendDTO.getContent3(), REMARK_MAX_LENGTH)));
				categoryList.add(category3);
			}
			
			//Added by Karen 20131211 end
			
			if(orderAmendDTO.isAmendAcqPipbInd() || orderAmendDTO.isAmendDocInd()){
				AmendPipbInfoDTO amendPipb = new AmendPipbInfoDTO();
				if(orderAmendDTO.isPipbAmendCustNameInd()){
					amendPipb.setTitle(orderAmendDTO.getPipbTitle());
					amendPipb.setFirstName(orderAmendDTO.getPipbFirstName());
					amendPipb.setLastName(orderAmendDTO.getPipbLastName());
				}
				if(orderAmendDTO.isPipbAmendFlatFloorInd()){
					amendPipb.setFlat(orderAmendDTO.getPipbFlat());
					amendPipb.setFloor(orderAmendDTO.getPipbFloor());
				}
				if(orderAmendDTO.isAmendDocInd()){
					amendPipb.setUploadDocument(true);
				}
				categoryList.add(amendPipb);
			}
			
		}
		amendDetail.setCategoryList(categoryList);
		return amendDetail;
	}
	
	private AmendCategoryLtsDTO createNewAmendCategory(LtsOrderAmendmentFormDTO orderAmendDTO, Class<?> amendCategory, boolean isSecDel){
		AmendCategoryLtsDTO newAmendCat = new AmendCategoryLtsDTO();
		if(amendCategory != null){
			try {
				newAmendCat = (AmendCategoryLtsDTO) amendCategory.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		if(isSecDel && orderAmendDTO.getSecDelAppntDtl() != null){
			newAmendCat.setSrd(orderAmendDTO.getSecDelAppntDtl().getAppntDate());
		}else if(orderAmendDTO.getPortLaterAppntDtl() != null && orderAmendDTO.isAcqOrderCompleted()){
			newAmendCat.setSrd(orderAmendDTO.getPortLaterAppntDtl().getAppntDate());
		}else{
			newAmendCat.setSrd(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate());
		}
		
		return newAmendCat;
	}
	
	private AmendDetailLtsDTO create2ndDelAmendDetail(LtsOrderAmendmentFormDTO orderAmendDTO, AmendDetailLtsDTO ltsAmendDetail, 
			SbOrderDTO sbOrder, boolean isAmendSecDelOnly){
		ServiceDetailLtsDTO primarySrvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO secDelSrvDtl = LtsSbOrderHelper.get2ndDelService(sbOrder);
		
		List<AmendCategoryLtsDTO> secDelcategoryList = new ArrayList<AmendCategoryLtsDTO>();
		AmendDetailLtsDTO secDel = new AmendDetailLtsDTO();
		secDel.setSrvNum(secDelSrvDtl.getSrvNum());
		secDel.setSrvType(secDelSrvDtl.getTypeOfSrv());
		secDel.setDtlId(secDelSrvDtl.getDtlId());
		//Added by karen 20131211 start
		secDel.setFromProd(secDelSrvDtl.getFromProd());
		secDel.setToProd(secDelSrvDtl.getToProd());
		
		secDel.setRelatedSrvNum(primarySrvDtl.getSrvNum());
		secDel.setRelatedSrvType(primarySrvDtl.getTypeOfSrv());			
		//Added by karen 20131211 end 
		
		if(orderAmendDTO.isCancelOrderInd()){
			if(!StringUtils.isEmpty(orderAmendDTO.getCancelType())){
				String cancelRemark = orderAmendDTO.getCancelRemark();
				AmendCategoryLtsDTO cancelOrder = createNewAmendCategory(orderAmendDTO, null, true);
				cancelOrder.setNature(orderAmendDTO.getCancelType());
				if(orderAmendDTO.isFirstMonthCcPrepaymentInd()){
					StringBuilder ccPrepayRemarks = new StringBuilder();
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.ccToken", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getCardNum());
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.ccName", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getCardHolderName());
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.ccExpDate", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getExpireMonth() + "/" + orderAmendDTO.getExpireYear());
					ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.dn", new Object[] {}, this.locale));
					ccPrepayRemarks.append(orderAmendDTO.getSerNum());
					if(orderAmendDTO.isShowPrepaymentRejectInd()){ //cancel date > 7 from signoff date
						if(orderAmendDTO.isPrepaymentRejectInd()){
							ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.prePayRej", new Object[] {}, this.locale));
						}else{
							ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.prePayNotRej", new Object[] {}, this.locale));
						}
					}else{
						ccPrepayRemarks.append("\n" + messageSource.getMessage("lts.ltsOrdAmendCreate.awaitConPayRej", new Object[] {}, this.locale));
					}
					
					cancelRemark = cancelRemark +"\n"+ccPrepayRemarks;
				}
				String cancelReason =  messageSource.getMessage("lts.ltsOrdAmendCreate.canRea", new Object[] {orderAmendDTO.getCancelReason()}, this.locale);
				
				cancelOrder.appendRemarks(getRemarks(splitEqually(cancelReason + cancelRemark, REMARK_MAX_LENGTH)));
				secDelcategoryList.add(cancelOrder);
				
				secDel.setCategoryList(secDelcategoryList);
			}
		}else {
			if(orderAmendDTO.isAmendAppointmentInd()){
				AmendAppointmentLtsDTO secDelApptOrder = (AmendAppointmentLtsDTO) createNewAmendCategory(orderAmendDTO, AmendAppointmentLtsDTO.class, true);
				try {
					secDelApptOrder.setNature(amendNatureSrdCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey());
				} catch (DAOException daoe) {
					daoe.printStackTrace();
				}
				String[] fromTime = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getOriginalSecDelInstallationDate(), 
						orderAmendDTO.getOriginalSecDelInstallationTime());
				secDelApptOrder.setFromAppntStartTime(fromTime[0]);
				secDelApptOrder.setFromAppntEndTime(fromTime[1]);
				String[] time = ltsAppointmentService.reformatDateTimeSlot(orderAmendDTO.getSecDelAppntDtl().getAppntDate(), 
						orderAmendDTO.getSecDelAppntDtl().getAppntTimeSlot());
				secDelApptOrder.setAppntStartTime(time[0]);
				secDelApptOrder.setAppntEndTime(time[1]);
				secDelApptOrder.setDelayReasonCd(orderAmendDTO.getDelayReasonCode());
				secDelApptOrder.setSerialNum(orderAmendDTO.getPreBookSerialNum());
				secDelApptOrder.setAppntType(orderAmendDTO.getSecDelAppntDtl().getAppntTimeSlotType());
				
				//Set referenceOrdAppnt; Added by Karen
				AppointmentDetailLtsDTO referenceOrdAppnt = secDelSrvDtl.getAppointmentDtl();
				referenceOrdAppnt.setAppntStartTime(secDelApptOrder.getAppntStartTime());
				referenceOrdAppnt.setAppntEndTime(secDelApptOrder.getAppntEndTime());
				referenceOrdAppnt.setCutOverStartTime(secDelApptOrder.getCutOverStartTime());
				referenceOrdAppnt.setCutOverEndTime(secDelApptOrder.getCutOverEndTime());
				referenceOrdAppnt.setPreWiringStartTime(secDelApptOrder.getPreWiringStartTime());
				referenceOrdAppnt.setPreWiringEndTime(secDelApptOrder.getPreWiringEndTime());
				
				//Added by karen 20131213 start set appnt datetime to order
				referenceOrdAppnt.setAppntStartTime(time[0]);
				referenceOrdAppnt.setAppntEndTime(time[1]);
				secDelApptOrder.setReferenceOrdAppnt(referenceOrdAppnt);
				//Added by karen 20131213 end
				
				//COMPARE DATETIME ---Modified by Max.R.MENG
				if(!StringUtils.equals(secDelApptOrder.getFromAppntStartTime(),secDelApptOrder.getAppntStartTime())
						|| !StringUtils.equals(secDelApptOrder.getFromAppntEndTime(), secDelApptOrder.getAppntEndTime())){
					secDelcategoryList.add(secDelApptOrder);
				}				
				
			}
			if(isAmendSecDelOnly){
				if(orderAmendDTO.isAmendCreditCardInd()){
					secDelcategoryList.add(this.createPaymentAmendment(orderAmendDTO)); //Added by karen
				}
				
				if (orderAmendDTO.isCategory1Ind()){
					AmendCategoryLtsDTO category1 = createNewAmendCategory(orderAmendDTO, null, true);
					category1.setNature(orderAmendDTO.getCategory1());
					category1.appendRemarks(getRemarks(splitEqually(orderAmendDTO.getContent1(), REMARK_MAX_LENGTH)));
					secDelcategoryList.add(category1);
				}
				if (orderAmendDTO.isCategory2Ind()){
					AmendCategoryLtsDTO category2 = createNewAmendCategory(orderAmendDTO, null, true);
					category2.setNature(orderAmendDTO.getCategory2());
					category2.appendRemarks(getRemarks(splitEqually(orderAmendDTO.getContent2(), REMARK_MAX_LENGTH)));
					secDelcategoryList.add(category2);	
				}
				if (orderAmendDTO.isCategory3Ind()){
					AmendCategoryLtsDTO category3 = createNewAmendCategory(orderAmendDTO, null, true);
					category3.setNature(orderAmendDTO.getCategory3());
					category3.appendRemarks(getRemarks(splitEqually(orderAmendDTO.getContent3(), REMARK_MAX_LENGTH)));
					secDelcategoryList.add(category3);
				}
			}
			
			secDel.setCategoryList(secDelcategoryList);
		}


		return secDel;
	}
	
	public static String[] splitEqually(String text, int size) {
	    List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

	    for (int start = 0; start < text.length(); start += size) {
	        ret.add(text.substring(start, Math.min(text.length(), start + size)));
	    }
	    return ret.toArray(new String[0]);
	}
	
	public RemarkDetailLtsDTO[] getRemarks(String[] s){
		List<RemarkDetailLtsDTO> remarks = new ArrayList<RemarkDetailLtsDTO>();
		for(int i = 0; i < s.length; i++){
			RemarkDetailLtsDTO remark = new RemarkDetailLtsDTO();
			remark.setRmkSeq(Integer.toString(i));
			remark.setRmkDtl(s[i]);
			remarks.add(remark);
		}
		return remarks.toArray(new RemarkDetailLtsDTO[0]);
	}
	
	private List<ResourceSlotDTO> getResource(SbOrderDTO sbOrder, String type, String date, String username, boolean eveningSlotInd, boolean acqOrder){
		ResourceDetailInputDTO resourceInput = null;
		if(!acqOrder){
			resourceInput = ltsAppointmentService.getResourceDetailInput(sbOrder, type, date, username);
		}else{
			resourceInput = ltsAppointmentService.getResourceDetailInput(sbOrder, type, date, username);
		}
//		ResourceDetailInputDTO resourceInput = ltsAppointmentService.getResourceDetailInput(sbOrder, type, date, username); 
		ResourceDetailOutputDTO secDelResource = appointmentDwfmService.getResourceDetail(resourceInput);
		List<ResourceSlotDTO> resourceSlotList = LtsAppointmentHelper.setupResourceList(secDelResource, eveningSlotInd);

		return resourceSlotList;
	}
	
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		LtsOrderAmendmentFormDTO orderAmendDTO = LtsSessionHelper.getOrderAmendForm(request);
		SbOrderDTO sbOrder = LtsSessionHelper.getDummySbOrder(request);

		ServiceDetailLtsDTO coreService = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO secDelService = LtsSbOrderHelper.get2ndDelService(sbOrder);
		ServiceDetailLtsDTO pipbService = LtsSbOrderHelper.getAcqPipbService(sbOrder.getSrvDtls());
		
		boolean isPreInstall = LtsSbHelper.isPreInstall(sbOrder);
		boolean isEyeSrv = LtsSbOrderHelper.getLtsEyeService(sbOrder) != null;
		referenceData.put("isAcqPipbOrder", pipbService != null);
		referenceData.put("titleList", titleLkupCacheService.getCodeLkupDAO().getCodeLkup());
		
		/*** Timeslot reference data ***/
		if(!(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) && !"Y".equals(coreService.getForceFieldVisitInd()))){
			boolean eveningSlotInd = ltsAppointmentService.isInstallUpgradePon(sbOrder);
			String installationDate = orderAmendDTO.getCoreSrvAppntDtl().getAppntDate();
			String secDelInstallationDate = orderAmendDTO.getSecDelAppntDtl() != null? orderAmendDTO.getSecDelAppntDtl().getAppntDate(): null;
			String portLaterDate = orderAmendDTO.getPortLaterAppntDtl() != null? orderAmendDTO.getPortLaterAppntDtl().getAppntDate(): null;
			String preWiringDate = orderAmendDTO.getPreWiringAppntDtl() != null? orderAmendDTO.getPreWiringAppntDtl().getAppntDate(): null;
			
			if(StringUtils.isNotBlank(installationDate)){
				String type = null;
				String allType = null;
				String username = LtsSessionHelper.getBomSalesUser(request).getUsername();
				boolean secDelSameSrd = StringUtils.equals(installationDate, secDelInstallationDate);
				boolean acqOrder = LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType());
				
				type = LtsConstant.DWFM_INPUT_TYPE_UPGRADE;
				allType = LtsConstant.DWFM_INPUT_TYPE_ALL;
				
				List<ResourceSlotDTO> resourceSlotList = getResource(sbOrder, secDelSameSrd? allType : type, installationDate, username, eveningSlotInd, acqOrder);
				referenceData.put("installationTimeSlot", resourceSlotList);
				
				if(StringUtils.isNotBlank(secDelInstallationDate)){
					if(secDelSameSrd){
						referenceData.put("secDelTimeSlot", resourceSlotList);
					}else{
						List<ResourceSlotDTO> secDelRresourceSlotList = 
								getResource(sbOrder, LtsConstant.DWFM_INPUT_TYPE_2NDDEL, secDelInstallationDate, username, eveningSlotInd, acqOrder);
						referenceData.put("secDelTimeSlot", secDelRresourceSlotList);
					}
				}

				if(StringUtils.isNotBlank(preWiringDate)){
					List<ResourceSlotDTO> preWiringRresourceSlotList = getResource(sbOrder, type, preWiringDate, username, eveningSlotInd, acqOrder);
					referenceData.put("preWiringTimeSlot", preWiringRresourceSlotList);
				}
				
				if(StringUtils.isNotBlank(portLaterDate)){
					List<ResourceSlotDTO> portLaterRresourceSlotList = getResource(sbOrder, type, portLaterDate, username, eveningSlotInd, acqOrder);
					referenceData.put("portLaterTimeSlot", portLaterRresourceSlotList);
				}
			}
		}
		
		/*** Timeslot reference data END ***/
		
		// Updated by karen 20131212
		if (StringUtils.equals(coreService.getActualDocType(), LtsConstant.DOC_TYPE_HKBR)){
			referenceData.put("relationshipCodeList", Arrays.asList(relationshipBrCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}else {
			referenceData.put("relationshipCodeList", Arrays.asList(relationshipCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}
		
		
		List<LookupItemDTO> cancelCodeList = new ArrayList<LookupItemDTO>();
		
		boolean prepayItemExist = false;
		for(ItemDetailLtsDTO itemDtl : coreService.getItemDtls()){
			if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PREPAY)){
				prepayItemExist = true;
				break;
			}
		}
		
		if(prepayItemExist){
			if(isEyeSrv){
				cancelCodeList.addAll(Arrays.asList(amendNatureCancelEyePPCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
			}else{
				cancelCodeList.addAll(Arrays.asList(amendNatureCancelDelPPCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
			}
		}else{
			if(isEyeSrv){
				cancelCodeList.addAll(Arrays.asList(amendNatureCancelEyeNoPPCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
			}else{
				cancelCodeList.addAll(Arrays.asList(amendNatureCancelDelNoPPCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
			}
		}
		
		cancelCodeList.addAll(Arrays.asList(amendNatureCancelCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("cancelCodeList", cancelCodeList);
		
		List<LookupItemDTO> cancelReasonList = new ArrayList<LookupItemDTO>();
		cancelReasonList.addAll(Arrays.asList(amendCancelReasonCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("cancelReasonList", cancelReasonList);
		
		List<LookupItemDTO> categoryCodeList = new ArrayList<LookupItemDTO>();
		
		LookupItemDTO[] lookupItems = null;		
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		
		if(bomSalesUser.getChannelId() == Integer.parseInt(LtsConstant.CHANNEL_ID_RETAIL)){
			lookupItems = OracleSelectHelper.getSqlResultObjects("BomWebPortalDS", 
					"SELECT code \"itemKey\",description \"itemValue\" FROM W_CODE_LKUP WHERE GRP_ID = 'LTS_WQ_NATURE_RS'", 
					LookupItemDTO.class);
		}else{		
			categoryCodeList.addAll(Arrays.asList(amendNatureCategoryCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
			categoryCodeList.addAll(Arrays.asList(amendNatureCategoryLTSCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
			lookupItems = OracleSelectHelper.getSqlResultObjects("BomWebPortalDS", 
				"SELECT wq_nature_id \"itemKey\",wq_nature_desc \"itemValue\" FROM Q_WQ_NATURE WHERE wq_nature_type = ?", 
				new Object[] { "AMEND_CATEGORY_" +  bomSalesUser.getChannelCd() }, 
				LookupItemDTO.class);
		}
		if (ArrayUtils.isNotEmpty(lookupItems)) {
			categoryCodeList.addAll(Arrays.asList(lookupItems));
		}

		for(int i = 0; i < categoryCodeList.size(); i++){
			/*remove nature for PIPB Order*/
			if(pipbService != null){
				if(ArrayUtils.contains(PIPB_REMOVE_NATURE, categoryCodeList.get(i).getItemKey())){
					categoryCodeList.remove(i);
					i--;
				}else if((!orderAmendDTO.isPipbAmendAfterCutOver() || orderAmendDTO.isPipbWqRejectedOrNotExist())
						&& ArrayUtils.contains(PIPB_REMOVE_BEFORE_CUTOFF_DATE_NATURE, categoryCodeList.get(i).getItemKey())){
					categoryCodeList.remove(i);
					i--;
				}
			}

			/*remove nature for DS*/
			if(LtsSessionHelper.isChannelDirectSales(request)){
				if(ArrayUtils.contains(CHANNEL_DS_REMOVE_NATURE, categoryCodeList.get(i).getItemKey())){
					categoryCodeList.remove(i);
					i--;
				}
			}
			
			/*remove nature for PREINSTALL*/
			if(isPreInstall && !StringUtils.equals(coreService.getSbStatus(), LtsConstant.ORDER_STATUS_CLOSED))
			{
				if(!ArrayUtils.contains(PREINSTALL_ALLOW_NATURE, categoryCodeList.get(i).getItemKey())){
					categoryCodeList.remove(i);
					i--;
				}
			}
		}
		
		referenceData.put("categoryCodeList", categoryCodeList);
		referenceData.put("categoryRemarkList", Arrays.asList(ltsAmendCategoryRemarkCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
	
		List<LookupItemDTO> delayReasonCodeList = Arrays.asList(delayReasonCodeLkupCacheService.getCodeLkupDAO().getCodeLkup());
		for(LookupItemDTO item: delayReasonCodeList){
			item.setItemValue(item.getItemKey() + " " + item.getItemValue());
		}
		referenceData.put("delayReasonCodeList", delayReasonCodeList);
		referenceData.put("creditCardTypeList", Arrays.asList(creditCardTypeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("yearList", ltsPaymentService.getCcExpireYearList());
		
		List<LookupItemDTO> srvNumList = new ArrayList<LookupItemDTO>();
		
		LookupItemDTO templkup = null;
		templkup = new LookupItemDTO();
		templkup.setItemKey(coreService.getSrvNum());
		if (isEyeSrv){
			templkup.setItemValue(coreService.getSrvNum()+ " (EYE)");
		}else{
			templkup.setItemValue(coreService.getSrvNum()+ " (DEL)");
		}
		srvNumList.add(templkup);
		
		if (secDelService!=null){
			templkup = new LookupItemDTO();
			templkup.setItemKey(secDelService.getSrvNum());
			templkup.setItemValue(secDelService.getSrvNum()+ " (2DEL)");
			srvNumList.add(templkup);
		}
		referenceData.put("srvNumList",srvNumList);
		
		return referenceData;
		
	}
	
	
	private void initForm(LtsOrderAmendmentFormDTO orderAmendDTO, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUserDTO){
		
		ServiceDetailProfileLtsDTO ltsServiceProfile = orderRecallService.createDummyServiceDetailProfile(sbOrder);
		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		
		orderAmendDTO.setLtsServiceProfile(ltsServiceProfile);
		
		orderAmendDTO.setSharePCDInd(LtsConstant.FALSE_IND);
		orderAmendDTO.setConfirmedInd(LtsConstant.FALSE_IND);
		orderAmendDTO.setAmendedInd(LtsConstant.FALSE_IND);
		orderAmendDTO.setBbShortageInd(LtsConstant.FALSE_IND);
		orderAmendDTO.setPcdOrderExistInd(LtsConstant.FALSE_IND);
		orderAmendDTO.setSubmitInd("INITIATE");
		orderAmendDTO.setSbOrderNum(sbOrder.getOrderId());
		orderAmendDTO.setOrderType(sbOrder.getOrderType());
		orderAmendDTO.setSerNum(StringUtils.substring(ltsServiceProfile.getSrvNum(), 4));
		
		orderAmendDTO.setBrCust(StringUtils.equals(coreService.getActualDocType(), LtsConstant.DOC_TYPE_HKBR));
		
		orderAmendDTO.setBomOcId(sbOrder.getOcid());
		
		orderAmendDTO.setNoticeEmailAddr(sbOrder.getEsigEmailAddr());
		orderAmendDTO.setNoticeSmsNum(sbOrder.getSmsNo());
		
	}
	
	
	/*Check if the amendment items are allowed to amend*/
	private void setupAllowence(LtsOrderAmendmentFormDTO orderAmendDTO, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUserDTO){
		orderAmendDTO.setCancelAllowInd(true);
		orderAmendDTO.setAllowAcqPipbAmendInd(true);
		orderAmendDTO.setAllowAppointmentAmendInd(true);
		orderAmendDTO.setAllowCreditCardAmendInd(true);
		orderAmendDTO.setAllowDocAmendInd(true);
		orderAmendDTO.setAllowOtherCategoryAmendInd(true);
		
		StringBuilder msg = new StringBuilder();
		boolean cancelAllow = false;
		for(ServiceDetailDTO srvDtl: sbOrder.getSrvDtls()){
			if (LtsConstant.SERVICE_TYPE_TEL.equals(srvDtl.getTypeOfSrv())){
				if(amendDetailEnquiryService.isAmendmentAllow(orderAmendDTO.getSbOrderNum(), LtsConstant.SERVICE_TYPE_TEL, 
						srvDtl.getCcServiceId(), srvDtl.getSrvNum(), srvDtl.getCcServiceMemNum(), msg)){
					cancelAllow = true;
					break;
				}
			}
		}

		orderAmendDTO.setCancelAllowReason(StringUtils.replace(msg.toString(), "\n", "\\n"));
		orderAmendDTO.setCancelAllowInd(cancelAllow);

		/*PIPB Order Checking*/
		orderAmendDTO.setPipbSrdDaysEnough(true);
		orderAmendDTO.setPipbAmendAfterCutOver(false);
		orderAmendDTO.setPipbWqRejectedOrNotExist(true);
		ServiceDetailDTO pipbService = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());

//		ServiceDetailLtsDTO portLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		ServiceDetailLtsDTO coreService = LtsSbOrderHelper.getLtsService(sbOrder);
		
		if(pipbService != null){
			boolean isPipbWqTaskExist = false;
			boolean isPipbWqRejected = false;
			boolean isPipbSrdDaysEnough = false;
			boolean isPipbWqRejectedOrNotExist = false;
			boolean isPipbAfterCutOver = false;
			
			try {
				isPipbWqTaskExist = pipbActivityLtsSubmissionService.isTaskExistByType(
						sbOrder.getOrderId(), bomSalesUserDTO.getUsername(), 
						LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
				
				isPipbWqRejected = pipbActivityLtsSubmissionService.isStatusRejectByType(
						sbOrder.getOrderId(), bomSalesUserDTO.getUsername(), 
						LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
			} catch (Exception e) {
				logger.error(e);
			}
			if(pipbService.getPipb() != null){
				String startDate = StringUtils.isNotBlank(pipbService.getAppointmentDtl().getCutOverStartTime())
						? pipbService.getAppointmentDtl().getCutOverStartTime()
								: pipbService.getAppointmentDtl().getAppntStartTime();
				DateTime startDateTime = DateTime.parse(startDate, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm"));
				
				isPipbAfterCutOver = DateTime.now().isAfter(startDateTime);
				isPipbSrdDaysEnough = validatePipbAmendDate(startDate, ltsAppointmentService.getPublicHolidayList());
				isPipbWqRejectedOrNotExist = !isPipbWqTaskExist || (isPipbWqTaskExist && isPipbWqRejected);

				orderAmendDTO.setPipbAmendAfterCutOver(isPipbAfterCutOver);
				orderAmendDTO.setPipbSrdDaysEnough(isPipbSrdDaysEnough);
				orderAmendDTO.setPipbWqRejectedOrNotExist(isPipbWqRejectedOrNotExist);
				
				
				if (!isPipbSrdDaysEnough && !isPipbAfterCutOver && !isPipbWqRejectedOrNotExist) {
					orderAmendDTO.setCancelAllowInd(false);
					orderAmendDTO.setAllowAppointmentAmendInd(false);
					orderAmendDTO.setAllowCreditCardAmendInd(false);
					orderAmendDTO.setAllowOtherCategoryAmendInd(false);
				}
				
				if((!isPipbSrdDaysEnough || isPipbAfterCutOver) && !isPipbWqRejectedOrNotExist){
					orderAmendDTO.setAllowAcqPipbAmendInd(false);
					orderAmendDTO.setAllowDocAmendInd(false);
					if(!isPipbAfterCutOver && !isPipbSrdDaysEnough){
						orderAmendDTO.getAlertMsgList().add(messageSource.getMessage("lts.amend.alert.pipb.not.enough.day", new Object[] {}, this.locale));
					}
					if(isPipbAfterCutOver){
						orderAmendDTO.getAlertMsgList().add(messageSource.getMessage("lts.amend.alert.pipb.srd.passed", new Object[] {}, this.locale));
					}
				}
			}
	
			/*check if core srv is completed*/
			if(StringUtils.equals(coreService.getSbStatus(), LtsConstant.ORDER_STATUS_CLOSED)){
				orderAmendDTO.setAcqOrderCompleted(true);
			}
		}else{

			/*after 13:00 not allow amend order with SRD = T+1*/
			String startDateStr = StringUtils.isNotBlank(coreService.getAppointmentDtl().getCutOverStartTime())
					? coreService.getAppointmentDtl().getCutOverStartTime()
							: coreService.getAppointmentDtl().getAppntStartTime();
			LocalDate startDate = LocalDate.parse(startDateStr.substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
			
			LocalDateTime sysdate = LocalDateTime.now();
			if (sysdate.toLocalDate().isBefore(startDate)
					&& Days.daysBetween(sysdate.toLocalDate() ,startDate).getDays() <= 1 
					&& sysdate.getHourOfDay() >= 13){
				orderAmendDTO.setAllowAcqPipbAmendInd(false);
				orderAmendDTO.setAllowAppointmentAmendInd(false);
				orderAmendDTO.setAllowCreditCardAmendInd(false);
				orderAmendDTO.setAllowDocAmendInd(false);
				orderAmendDTO.setAllowOtherCategoryAmendInd(false);
				orderAmendDTO.getAlertMsgList().add(messageSource.getMessage("lts.amend.alert.not.enough.day", new Object[] {}, this.locale));
			}
		}
		
		/*if failed to create order only allow cancel*/
		if(StringUtils.equals(coreService.getSbStatus(), LtsConstant.ORDER_STATUS_FORCED_WQ)){
			orderAmendDTO.setAllowAcqPipbAmendInd(false);
			orderAmendDTO.setAllowAppointmentAmendInd(false);
			orderAmendDTO.setAllowCreditCardAmendInd(false);
			orderAmendDTO.setAllowDocAmendInd(false);
			orderAmendDTO.setAllowOtherCategoryAmendInd(false);
		}

	}
	
	private void setupSalesInfo(LtsOrderAmendmentFormDTO orderAmendDTO, BomSalesUserDTO bomSalesUserDTO){
		if(bomSalesUserDTO != null){
			orderAmendDTO.setStaffNum(bomSalesUserDTO.getUsername());
			orderAmendDTO.setSalesId(bomSalesUserDTO.getSalesCd());
			orderAmendDTO.setSalesChannel(bomSalesUserDTO.getChannelCd());
			orderAmendDTO.setSalesContactNum(bomSalesUserDTO.getShopContactPhone());
			orderAmendDTO.setShopCode(bomSalesUserDTO.getShopCd());
		}
	}

	private void setupAcqPipbOrderAmendments(LtsOrderAmendmentFormDTO orderAmendDTO, SbOrderDTO sbOrder){
		ServiceDetailLtsDTO pipbService = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		ServiceDetailLtsDTO coreSrv = LtsSbHelper.getLtsService(sbOrder);
		CustomerDetailLtsDTO custDtl = coreSrv.getAccount().getCustomer();
		orderAmendDTO.getLtsServiceProfile().getPrimaryCust();
		if(pipbService != null && pipbService.getPipb() != null){
			PipbLtsDTO pipbInfo = pipbService.getPipb();
			orderAmendDTO.setPipbInfo(pipbInfo);
			if(StringUtils.isNotBlank(pipbInfo.getTitle())
					&& StringUtils.isNotBlank(pipbInfo.getFirstName())
					&& StringUtils.isNotBlank(pipbInfo.getLastName())){
				orderAmendDTO.setPipbTitle(pipbInfo.getTitle());
				orderAmendDTO.setPipbFirstName(pipbInfo.getFirstName());
				orderAmendDTO.setPipbLastName(pipbInfo.getLastName());
			}else{
				orderAmendDTO.setPipbTitle(custDtl.getTitle());
				orderAmendDTO.setPipbFirstName(custDtl.getFirstName());
				orderAmendDTO.setPipbLastName(custDtl.getLastName());
			}

			if(StringUtils.isNotBlank(pipbInfo.getCompanyName())){
				orderAmendDTO.setPipbCompanyName(pipbInfo.getCompanyName());
			}else{
				orderAmendDTO.setPipbCompanyName(custDtl.getCompanyName());
			}

			orderAmendDTO.setPipbFlat(pipbInfo.getUnitNo());
			orderAmendDTO.setPipbFloor(pipbInfo.getFloorNo());
		}
	}
	
	private void setupAppointment(LtsOrderAmendmentFormDTO orderAmendDTO, SbOrderDTO sbOrder){//, ServiceDetailDTO coreService, ServiceDetailDTO secDelService){
		
		ServiceDetailLtsDTO portLaterSrv = LtsSbHelper.getAcqPortLaterService(sbOrder);
		ServiceDetailDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailDTO secDelService = LtsSbHelper.get2ndDelService(sbOrder);
		
		
		AppointmentDetailLtsDTO coreSrvAppnt = coreService.getAppointmentDtl();
		orderAmendDTO.setPreBookSerialNum(coreSrvAppnt.getSerialNum());
		
		String[] appntDateTime = LtsDateFormatHelper.convertStartEndDateTimeFromDTO2Display(coreSrvAppnt.getAppntStartTime(), coreSrvAppnt.getAppntEndTime()).split(" ");
		LtsAppointmentDetail coreSrvAppntDtl = new LtsAppointmentDetail();
		coreSrvAppntDtl.setAppntDate(appntDateTime[0]);
		if(appntDateTime.length >= 2){
			coreSrvAppntDtl.setAppntTimeSlotAndType(
					LtsDateFormatHelper.convertToSBTime(appntDateTime[1])
					+ LtsAppointmentHelper.TIMESLOT_DELIMITER
					+ coreSrvAppnt.getAppntType());
		}
		if(StringUtils.isNotEmpty(coreSrvAppnt.getCutOverStartTime())){
			String[] cutOverDateTime = LtsDateFormatHelper.convertStartEndDateTimeFromDTO2Display(coreSrvAppnt.getCutOverStartTime(), coreSrvAppnt.getCutOverEndTime()).split(" ");
			coreSrvAppntDtl.setCutOverDate(cutOverDateTime[0]);
			coreSrvAppntDtl.setCutOverTime(cutOverDateTime[1]);
			coreSrvAppntDtl.setCutOverInd(true);
		}
		if((LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) 
				|| LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType()))
				&& !"Y".equals(coreService.getForceFieldVisitInd())){
			coreSrvAppntDtl.setTimeSlotRequiredInd(false);
		}
		orderAmendDTO.setCoreSrvAppntDtl(coreSrvAppntDtl);
		
		if(StringUtils.isNotEmpty(coreSrvAppnt.getPreWiringStartTime())){
			LtsAppointmentDetail preWiringAppntDtl = new LtsAppointmentDetail();
			String[] preWiringDateTime = LtsDateFormatHelper.convertStartEndDateTimeFromDTO2Display(coreSrvAppnt.getPreWiringStartTime(), coreSrvAppnt.getPreWiringEndTime()).split(" ");
			preWiringAppntDtl.setAppntDate(preWiringDateTime[0]);
			if(preWiringDateTime.length >= 2){
				preWiringAppntDtl.setAppntTimeSlotAndType(
						LtsDateFormatHelper.convertToSBTime(preWiringDateTime[1])
						+ LtsAppointmentHelper.TIMESLOT_DELIMITER
						+ coreSrvAppnt.getPreWiringType());
			}
			orderAmendDTO.setPreWiringAppntDtl(preWiringAppntDtl);
		}
		
		/*If second del service exist*/
		if(secDelService != null){
			AppointmentDetailLtsDTO secDelSrvAppnt = secDelService.getAppointmentDtl();
			String[] secDelAppntDateTime = LtsDateFormatHelper.convertStartEndDateTimeFromDTO2Display(secDelSrvAppnt.getAppntStartTime(), secDelSrvAppnt.getAppntEndTime()).split(" ");

			LtsAppointmentDetail secDelAppntDtl = new LtsAppointmentDetail();
			secDelAppntDtl.setAppntDate(secDelAppntDateTime[0]);
			if(secDelAppntDateTime.length >= 2){
				secDelAppntDtl.setAppntTimeSlotAndType(
						LtsDateFormatHelper.convertToSBTime(secDelAppntDateTime[1])
								+ LtsAppointmentHelper.TIMESLOT_DELIMITER
								+ secDelSrvAppnt.getAppntType());
			}
			if(StringUtils.isNotEmpty(secDelSrvAppnt.getCutOverStartTime())){
				String[] secDelCutOverDateTime = LtsDateFormatHelper.convertStartEndDateTimeFromDTO2Display(secDelSrvAppnt.getCutOverStartTime(), secDelSrvAppnt.getCutOverEndTime()).split(" ");
				secDelAppntDtl.setCutOverDate(secDelCutOverDateTime[0]);
				secDelAppntDtl.setCutOverTime(secDelCutOverDateTime[1]);
				secDelAppntDtl.setCutOverInd(true);
			}
			if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())
					&& !"Y".equals(secDelService.getForceFieldVisitInd())){
				secDelAppntDtl.setTimeSlotRequiredInd(false);
			}
			orderAmendDTO.setSecDelAppntDtl(secDelAppntDtl);
		}
		
		/*If port later (PIPB) service exist*/
		if(portLaterSrv != null){
			AppointmentDetailLtsDTO portLaterSrvAppnt = portLaterSrv.getAppointmentDtl();
			String[] portLaterAppntDateTime = LtsDateFormatHelper.convertStartEndDateTimeFromDTO2Display(portLaterSrvAppnt.getAppntStartTime(), portLaterSrvAppnt.getAppntEndTime()).split(" ");

			LtsAppointmentDetail portLaterAppntDtl = new LtsAppointmentDetail();
			portLaterAppntDtl.setAppntDate(portLaterAppntDateTime[0]);
			if(portLaterAppntDateTime.length >= 2){
				portLaterAppntDtl.setAppntTimeSlotAndType(
						LtsDateFormatHelper.convertToSBTime(portLaterAppntDateTime[1])
								+ LtsAppointmentHelper.TIMESLOT_DELIMITER
								+ portLaterSrvAppnt.getAppntType());
			}

			if(StringUtils.isNotEmpty(portLaterSrvAppnt.getCutOverStartTime())){
				String[] secDelCutOverDateTime = LtsDateFormatHelper.convertStartEndDateTimeFromDTO2Display(portLaterSrvAppnt.getCutOverStartTime(), portLaterSrvAppnt.getCutOverEndTime()).split(" ");
				portLaterAppntDtl.setCutOverDate(secDelCutOverDateTime[0]);
				portLaterAppntDtl.setCutOverTime(secDelCutOverDateTime[1]);
				portLaterAppntDtl.setCutOverInd(true);
			}
			portLaterAppntDtl.setTimeSlotRequiredInd(true);
			boolean isPreInstall = LtsSbHelper.isPreInstall(sbOrder);
			if(isPreInstall)
			{
				portLaterAppntDtl.setEarliestSRDReason("Primary SRD +" + LtsConstant.PRE_INSTALL_MINDAY + " Days ");
			}
			else
			{
				portLaterAppntDtl.setEarliestSRDReason("Primary SRD +" + LtsConstant.MINIMUM_PIPB_DAY + " Days +2 Days ");
			}						
			orderAmendDTO.setPortLaterAppntDtl(portLaterAppntDtl);
		}
		
		
		/*original appointments to detect amendment*/
		orderAmendDTO.setOriginalInstallationDate(orderAmendDTO.getCoreSrvAppntDtl().getAppntDate());
		orderAmendDTO.setOriginalInstallationTime(orderAmendDTO.getCoreSrvAppntDtl().getAppntTimeSlot());
		if(orderAmendDTO.getSecDelAppntDtl() != null){
			orderAmendDTO.setOriginalSecDelInstallationDate(orderAmendDTO.getSecDelAppntDtl().getAppntDate());
			orderAmendDTO.setOriginalSecDelInstallationTime(orderAmendDTO.getSecDelAppntDtl().getAppntTimeSlot());
		}
		if(orderAmendDTO.getPortLaterAppntDtl() != null){
			orderAmendDTO.setOriginalPortLaterDate(orderAmendDTO.getPortLaterAppntDtl().getAppntDate());
			orderAmendDTO.setOriginalPortLaterTime(orderAmendDTO.getPortLaterAppntDtl().getAppntTimeSlot());
		}
		if(orderAmendDTO.getPreWiringAppntDtl() != null){
			orderAmendDTO.setOriginalPreWiringDate(orderAmendDTO.getPreWiringAppntDtl().getAppntDate());
			orderAmendDTO.setOriginalPreWiringTime(orderAmendDTO.getPreWiringAppntDtl().getAppntTimeSlot());
		}
	}
	
	private void setupPayment(LtsOrderAmendmentFormDTO orderAmendDTO, SbOrderDTO sbOrder){
		
		if(StringUtils.equals(sbOrder.getOrderType(), LtsConstant.ORDER_TYPE_DISCONNECT)){
			return;
		}
		
		LtsPaymentFormDTO ltsPaymentFormDTO = orderRecallService.createLtsPaymentForm(sbOrder);
		if(ltsPaymentFormDTO != null){
			orderAmendDTO.setCardHolderName(ltsPaymentFormDTO.getCardHolderName());
			orderAmendDTO.setCardHolderDocType(ltsPaymentFormDTO.getCardHolderDocType());
			orderAmendDTO.setCardHolderDocNum(ltsPaymentFormDTO.getCardHolderDocNum());
			orderAmendDTO.setCardNum(ltsPaymentFormDTO.getCardNum());
			orderAmendDTO.setCardType(ltsPaymentFormDTO.getCardType());
			orderAmendDTO.setCardVerified(ltsPaymentFormDTO.isCardVerified());
			orderAmendDTO.setExpireMonth(ltsPaymentFormDTO.getExpireMonth());
			orderAmendDTO.setExpireYear(ltsPaymentFormDTO.getExpireYear());
			orderAmendDTO.setThirdPartyCard(ltsPaymentFormDTO.getThirdPartyCard());
			orderAmendDTO.setFirstMonthCcPrepaymentInd(false);
			orderAmendDTO.setShowPrepaymentRejectInd(false);
			
		
			if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(ltsPaymentFormDTO.getNewPayMethodType())){
				try{
					orderAmendDTO.setCreditCard(true);
					
					List<String> cancelRemarkItemIdList = ltsPaymentService.getCancelRemarkItemIdList();
					for(String itemId: cancelRemarkItemIdList){
						if(ltsPaymentFormDTO.getPrePayItem() != null && itemId.equals(ltsPaymentFormDTO.getPrePayItem().getItemId())){
							orderAmendDTO.setFirstMonthCcPrepaymentInd(true);
							DateTime applicationDate = DateTime.parse(sbOrder.getAppDate().substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
							if(applicationDate.isBeforeNow()
									&& Days.daysBetween(applicationDate, DateTime.now()).getDays() > 7){
								orderAmendDTO.setShowPrepaymentRejectInd(true);
							}
							break;
						}
					}
				}catch(NullPointerException npe){
					logger.error(ExceptionUtils.getFullStackTrace(npe));
				}catch(AppRuntimeException are){
					logger.error(ExceptionUtils.getFullStackTrace(are));
				}
			}
		}
		
	}
	
	private LtsSRDDTO setupFieldPermitService(LtsOrderAmendmentFormDTO orderAmendDTO, SbOrderDTO sbOrder, String todayString, 
			ServiceDetailLtsDTO coreService, ServiceDetailLtsDTO secDelService, String userId){
		
		if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())){
			return null;
		}
		
		//fieldPermit, start date = today
		String fieldPermitString = sbOrder.getAddress().getAddrInventory().getFieldWorkPermitDay();
		int fieldPermit = 0;
		if(StringUtils.isNotBlank(fieldPermitString) && StringUtils.isNumeric(fieldPermitString)){
			fieldPermit = Integer.parseInt(fieldPermitString);
		}
		LtsSRDDTO fieldPermitSrd = null;
		if(fieldPermit != 0){
			fieldPermitSrd = new LtsSRDDTO(todayString, 0, LtsConstant.EARLIEST_SRD_REASON_CD_FIELD_PERMIT_REQUIRED);
			ResourceDetailInputDTO input = ltsAppointmentService.getResourceDetailInput(sbOrder, LtsConstant.DWFM_INPUT_TYPE_ALL, todayString, userId);
			
			/*get BmoPermitDetails*/
			BmoPermitDetail bmoPermit = ltsAppointmentService.getBmoPermitLeadDays(input, todayString);
			
			if(bmoPermit == null){
				fieldPermitSrd.setLeadTime(fieldPermit);
			}else{
				int permetLeadDays = Integer.parseInt(bmoPermit.getPermitLeadDays());
				if(permetLeadDays > fieldPermit){
					fieldPermitSrd.setLeadTime(permetLeadDays);
					orderAmendDTO.setBmoAlertMsg(bmoPermit.getAlertMsg());
					orderAmendDTO.setBmoRemark(bmoPermit.getBmoRemark());
				}else{
					fieldPermitSrd.setLeadTime(fieldPermit);
				}
			}
			return fieldPermitSrd;
		}

		return null;
	}
	
	private void setupEarliestSrd(LtsOrderAmendmentFormDTO orderAmendDTO, SbOrderDTO sbOrder, HttpServletRequest request){
		
		if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())){
			return;
		}
		
		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO secDelService = LtsSbOrderHelper.get2ndDelService(sbOrder);
		
		String orgEarliestSrd = coreService.getSuggestSrd();
		String orgEarliestSrdReasonCd = coreService.getSuggestSrdReasonCd();
		String orgEarliestSrdReasonDes = LtsAppointmentHelper.earliestSrdReason.get(orgEarliestSrdReasonCd);
		String todayString = DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy"));
		
		/*-----------Re-check Special Cases-----------*/
		boolean ignoreOrgEarliestSrd = false;
		
		/*Re-check blacklist customer*/
		if(LtsAppointmentHelper.tentativeReasonCodeList.contains(orgEarliestSrdReasonCd)){
			CustomerDetailProfileLtsDTO custProfile = customerProfileLtsService.getCustByCustNum(coreService.getAccount().getCustomer().getCustNo(), LtsConstant.SYSTEM_ID_DRG);
			/*If order distributed in BOM ignore Blacklist*/
			OrderStatusDTO[] statusHist = orderStatusService.getStatus(sbOrder.getOrderId());
			String bomStatus = null;
			if(statusHist != null){
				for(OrderStatusDTO status : statusHist){
					if(StringUtils.equals(coreService.getDtlId(), status.getDtlId())){
						bomStatus = status.getLegacyStatus();
					}
				}
			}			
			if(StringUtils.equals(bomStatus, LtsConstant.BOM_STATUS_DISTRIBUTED)
					|| (custProfile != null && !custProfile.isBlacklistCustInd()) ){
				ignoreOrgEarliestSrd = true;
			}
		}
		
		/*Re-check customer name not match case*/
		if(StringUtils.equals(orgEarliestSrdReasonCd, LtsConstant.EARLIEST_SRD_REASON_CD_DS_NAME_NOT_MATCH)){
			if(StringUtils.equals(LtsConstant.NAME_MISMATCH_APR_CD_APPROVED, sbOrder.getLtsDsOrderInfo().getNameMismatchStatus())
					|| StringUtils.equals(LtsConstant.NAME_MISMATCH_APR_CD_APPROVED_WITH_DIFF_CUST, sbOrder.getLtsDsOrderInfo().getNameMismatchStatus())){
				ignoreOrgEarliestSrd = true;
			}
		}
		
		if(ignoreOrgEarliestSrd){
			orgEarliestSrd = todayString;
			orgEarliestSrdReasonCd = LtsConstant.EARLIEST_SRD_REASON_CD_AMEND_NORMAL;
			orgEarliestSrdReasonDes = LtsConstant.EARLIEST_SRD_REASON_CD_AMEND_NORMAL_DESC;
		}
		/*-----------Re-check Special Cases END-----------*/
		
		
		/*normal lead time, start date = today*/
		LtsSRDDTO amendNormalLeadTime = new LtsSRDDTO(todayString);
		amendNormalLeadTime.setInfo(LtsConstant.EARLIEST_SRD_REASON_CD_AMEND_NORMAL_DESC);
		amendNormalLeadTime.setReasonCd(LtsConstant.EARLIEST_SRD_REASON_CD_AMEND_NORMAL);
		if(DateTime.now().getHourOfDay() > 13){
			amendNormalLeadTime.setLeadTime(2);
		}else{
			amendNormalLeadTime.setLeadTime(1);
		}

		ArrayList<LtsSRDDTO> sRDInfos = new ArrayList<LtsSRDDTO>();
		ArrayList<LtsSRDDTO> prewiringSRDInfos = new ArrayList<LtsSRDDTO>();
		
		/*calculate and get fieldPermit and BmoPermit, start date = today*/
		LtsSRDDTO fieldPermitSrd = setupFieldPermitService(orderAmendDTO, sbOrder, todayString, coreService, secDelService, orderAmendDTO.getStaffNum());
		
		if(!LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())){
			/*2n port in, start date = today*/
			LtsSRDDTO twoNPortInSRD = null;
			if("Y".equals(coreService.getTwoNInd())){
				twoNPortInSRD = new LtsSRDDTO(todayString, 0, LtsConstant.EARLIEST_SRD_REASON_CD_2N_LEAD_TIME);
				LtsAppointmentHelper.pushWorkingDays(twoNPortInSRD, 7, true, ltsAppointmentService.getPublicHolidayList());
			}

			if(twoNPortInSRD != null){
				sRDInfos.add(twoNPortInSRD);
				prewiringSRDInfos.add(twoNPortInSRD);
			}
		}
		
		ServiceDetailLtsDTO pipbSrv = LtsSbOrderHelper.getAcqPipbService(sbOrder.getSrvDtls());
		
		if(coreService.getPipb() != null && LtsConstant.DN_SOURCE_DN_PIPB.equals(coreService.getDnSource()) 
				&& !(orderAmendDTO.isPipbAmendAfterCutOver() && !orderAmendDTO.isPipbWqRejectedOrNotExist())){
			LtsSRDDTO pipbSrd = new LtsSRDDTO(todayString, LtsSbOrderHelper.getPipbMinDay(), LtsConstant.EARLIEST_SRD_REASON_CD_PIPB_NORMAL);
			sRDInfos.add(pipbSrd);
		}
		
		if(fieldPermitSrd != null){
			sRDInfos.add(fieldPermitSrd);
			prewiringSRDInfos.add(fieldPermitSrd);
		}
		sRDInfos.add(amendNormalLeadTime);
		prewiringSRDInfos.add(amendNormalLeadTime);

		//get max of those based on today
		LtsSRDDTO newEarliestSrd = Collections.max(sRDInfos);
		LtsSRDDTO prewiringEarliestSrd = Collections.max(prewiringSRDInfos);
		
		//compare new with original
		LtsSRDDTO srd;
		DateTime orgEarliestSrdDt = DateTime.parse(orgEarliestSrd.substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateTime newEarliestSrdDt = DateTime.parse(newEarliestSrd.getDateString(), DateTimeFormat.forPattern("dd/MM/yyyy"));
		if(orgEarliestSrdDt.isBefore(newEarliestSrdDt)){
			srd = newEarliestSrd;
		}else{
			srd = new LtsSRDDTO(orgEarliestSrdDt.toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
			srd.setReasonCd(orgEarliestSrdReasonCd);
			srd.setInfo(orgEarliestSrdReasonDes);
		}
		orderAmendDTO.getCoreSrvAppntDtl().setEarliestSRD(srd);
		orderAmendDTO.getCoreSrvAppntDtl().setEarliestSRDReason(LtsAppointmentHelper.getEarilestSrdReason(srd));
		
		/*Prewiring*/
		if(orderAmendDTO.getPreWiringAppntDtl() != null){
			orderAmendDTO.getPreWiringAppntDtl().setEarliestSRD(prewiringEarliestSrd);
			orderAmendDTO.getPreWiringAppntDtl().setEarliestSRDReason(LtsAppointmentHelper.getEarilestSrdReason(prewiringEarliestSrd));
		}
		
		
		/*For PIPB service*/
		if(pipbSrv != null){
			if(StringUtils.isNotBlank(sbOrder.getAddress().getAddrInventory().getN2BuildingInd())){
				LtsSRDDTO twoNBWSrd = new LtsSRDDTO(todayString, 0, LtsConstant.EARLIEST_SRD_REASON_CD_2N_BLOCK_WIRING);
				LtsAppointmentHelper.pushWorkingDays(twoNBWSrd, 14, false, ltsAppointmentService.getPublicHolidayList());

				//2NBW earliestSRD > earliestSRD, apply 2NBW if change PIPB flat/floor
				if(twoNBWSrd.compareTo(srd) > 0){
					orderAmendDTO.setPipbTwoNBWSrd(twoNBWSrd);
					orderAmendDTO.setPipbTwoNBWSrdReason(LtsAppointmentHelper.getEarilestSrdReason(twoNBWSrd));
				}
			}
			

			if(StringUtils.isBlank(pipbSrv.getDnStatus())){
				if(coreService.getPipb() != null){
					StringBuilder sb = new StringBuilder();
					LtsAppointmentHelper.addExtraDaysToSRD(sb, srd, 2, LtsConstant.EARLIEST_SRD_REASON_CD_PIPB_DN_NOT_EXIST_DRG);
					if(sb.length() > 0){
						sb.append(srd.getInfo());
						srd.setInfo(sb.toString());
					}
				}
				
			}

			if(orderAmendDTO.getPortLaterAppntDtl() != null){
				boolean portLaterOrderCreated = LtsConstant.ORDER_STATUS_CREATE_BOM.equals(pipbSrv.getSbStatus());
				boolean isPreInstall = LtsSbHelper.isPreInstall(sbOrder);
				StringBuilder sb = new StringBuilder();
				if(isPreInstall)
				{
					sb.append("Next day of Primary SRD +" + LtsConstant.PRE_INSTALL_MINDAY + " Days ");
					orderAmendDTO.setPipbPortLaterleadtime(LtsConstant.PRE_INSTALL_MINDAY+1);
				}
				else
				{
					sb.append("Primary SRD + "+LtsSbOrderHelper.getPipbMinDay()+" Days");
					if(!portLaterOrderCreated){
						sb.append(" +2 Days (");
						sb.append(LtsAppointmentHelper.earliestSrdReason.get(LtsConstant.EARLIEST_SRD_REASON_CD_PIPB_DN_NOT_EXIST_DRG));
						sb.append(")");
					}
					orderAmendDTO.getPortLaterAppntDtl().setEarliestSRDReason(sb.toString());
					orderAmendDTO.setPipbPortLaterleadtime(portLaterOrderCreated ? LtsSbOrderHelper.getPipbMinDay() : LtsSbOrderHelper.getPipbMinDay()+2);
				}
				orderAmendDTO.getPortLaterAppntDtl().setEarliestSRDReason(sb.toString());
			}
			
			orderAmendDTO.setPipbMaxLeadTime(null);
			if(pipbSrv != null){
				DateTime pipbSrDate = DateTime.parse(pipbSrv.getAppointmentDtl().getCutOverStartTime().substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
				if(Math.abs(Days.daysBetween(DateTime.now(), pipbSrDate).getDays()) > 60){
					orderAmendDTO.setPipbMaxLeadTime(Integer.toString(LtsConstant.ACQ_PIPB_MAX_LEADTIME));
				}else{
					orderAmendDTO.setPipbMaxLeadTime(Integer.toString(LtsConstant.ACQ_NORMAL_MAX_LEADTIME));
				}
			}
		}
		
		orderAmendDTO.setEarliestSRD(srd);
//		request.setAttribute("earliestSRDReason", LtsAppointmentHelper.getEarilestSrdReason(srd, RequestContextUtils.getLocale(request)));
//		request.setAttribute("earliestSRD", srd.getDateString());
//		request.setAttribute("leadtime", srd.getLeadTime());
	}


	private void addChristmasEve(List<String> publicHolidayList){
		String christmasEve;
		DateTime christmas = DateTime.parse(DateTime.now().getYear()+"/12/25", DateTimeFormat.forPattern("yyyy/MM/dd"));
		switch(christmas.getDayOfWeek()){
			case(DateTimeConstants.MONDAY):
				christmasEve = christmas.minusDays(3).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));break;
			case(DateTimeConstants.SUNDAY):
				christmasEve = christmas.minusDays(2).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));break;
			default:
				christmasEve = christmas.minusDays(1).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));break;
		}
		
		if(!publicHolidayList.contains(christmasEve)){
			publicHolidayList.add(christmasEve);
		}
	}

	/*
	 * Return true if the "1.5 days rule" is valid
	 * Input : srd = (String: dd/MM/yyyy)
	 * */
	private boolean validatePipbAmendDate(String srd, List<String> publicHolidayList){
		DateTime temp = getDefaultDate(DateTime.now());
		DateTime srDateTime = DateTime.parse(srd, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm"));
		addChristmasEve(publicHolidayList);
		for(int i = 0; i < 3 ; i++){
			temp = pushSrdTimeSlot(temp);
			/*is SAT or SUN */
			while((temp.getDayOfWeek() == DateTimeConstants.SATURDAY)
					|| (temp.getDayOfWeek() == DateTimeConstants.SUNDAY)
					|| publicHolidayList.contains(temp.toString("yyyy-MM-dd"))){ 
				temp = pushSrdTimeSlot(temp);
			}
		}
		
		if(srDateTime.isAfter(temp)){
			return true;
		}
		
		return false;
	}

	private DateTime getDefaultDate(DateTime date){
		DateTime temp = null;
		if(date.getHourOfDay() <= 10){//GROUPA
			temp = new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 9, 0, 0); 
		}else if(date.getHourOfDay() <= 14){//GROUPB
			temp = new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 13, 0, 0);
		}else{//GROUPC
			temp = new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 9, 0, 0);
			temp = temp.plusDays(1);
		}
		return temp;
	}
	
	private DateTime pushSrdTimeSlot(DateTime date){
		DateTime temp = null;
		if(date.getHourOfDay() == 9){
			temp = new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 13, 0, 0);
		}else{
			temp = new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 9, 0, 0);
			temp = temp.plusDays(1);
		}
		
		return temp;
	}
	
	/* Return ocid of primary/port-later(if exist) service depends on the status.
	 * Will return null if port-later order not yet created but primary srv already completed*/
	private String getOCID(String pOcid, ServiceDetailLtsDTO primarySrvDtl, ServiceDetailLtsDTO pipbService){
		List<OrderSrvStatusDetailDTO> ordSrvStatusDtlList = ltsOrderSearchService.getPendingOrderSrvStatusList(pOcid);
		
		for(OrderSrvStatusDetailDTO srvStatus: ordSrvStatusDtlList){
			/*If current ocid = primary srv*/
			String primarySrvNum = primarySrvDtl.getSrvNum();
			if(StringUtils.equals(srvStatus.getSrvId(), primarySrvNum)){
				/*If order not yet completed*/
				if(!StringUtils.equals(LtsConstant.DRG_ORDER_STATUS_COMPLETED, srvStatus.getLegacyStatus())
						&& !StringUtils.equals(LtsConstant.BOM_ORDER_STATUS_COMPLETED, srvStatus.getBomStatus())){
					return pOcid;
				}else{
					break;
				}
			}
		}
		
		/*check if current ocid = pipb srv*/
		if(pipbService != null && LtsConstant.LTS_PRODUCT_TYPE_PORT_LATER.equals(pipbService.getToProd())){
			String portingSrvNum = pipbService.getSrvNum();
			for(OrderSrvStatusDetailDTO srvStatus: ordSrvStatusDtlList){
				if(StringUtils.equals(srvStatus.getSrvId(), portingSrvNum)){
					return pOcid;
				}else{
					break;
				}
			}
			
		}
		
		
		return null;
	}
	
	private BasketDetailDTO getSelectedBasketDetail(SbOrderDTO sbOrder) {
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
		String displayType = LtsSbOrderHelper.getBasketDisplayType(sbOrder);
		String basketId = LtsSbOrderHelper.getBasketId(ltsServiceDetail);
		
        if (StringUtils.isNotBlank(basketId)) {
			return ltsBasketService.getBasket(basketId, LtsConstant.LOCALE_ENG, displayType);
		}
		return null;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public OrderRecallService getOrderRecallService() {
		return orderRecallService;
	}

	public void setOrderRecallService(OrderRecallService orderRecallService) {
		this.orderRecallService = orderRecallService;
	}
	
	public OrderCancelLtsService getOrderCancelLtsService() {
		return orderCancelLtsService;
	}

	public void setOrderCancelLtsService(OrderCancelLtsService orderCancelLtsService) {
		this.orderCancelLtsService = orderCancelLtsService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public AmendRetrieveService getAmendRetrieveService() {
		return amendRetrieveService;
	}

	public void setAmendRetrieveService(AmendRetrieveService amendRetrieveService) {
		this.amendRetrieveService = amendRetrieveService;
	}

	public AmendDetailEnquiryService getAmendDetailEnquiryService() {
		return amendDetailEnquiryService;
	}

	public void setAmendDetailEnquiryService(
			AmendDetailEnquiryService amendDetailEnquiryService) {
		this.amendDetailEnquiryService = amendDetailEnquiryService;
	}

	public LtsPaymentService getLtsPaymentService() {
		return ltsPaymentService;
	}

	public void setLtsPaymentService(LtsPaymentService ltsPaymentService) {
		this.ltsPaymentService = ltsPaymentService;
	}

	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(
			AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public CodeLkupCacheService getRelationshipBrCodeLkupCacheService() {
		return relationshipBrCodeLkupCacheService;
	}

	public void setRelationshipBrCodeLkupCacheService(
			CodeLkupCacheService relationshipBrCodeLkupCacheService) {
		this.relationshipBrCodeLkupCacheService = relationshipBrCodeLkupCacheService;
	}
	
	public CodeLkupCacheService getAmendCancelReasonCodeLkupCacheService() {
		return amendCancelReasonCodeLkupCacheService;
	}

	public void setAmendCancelReasonCodeLkupCacheService(
			CodeLkupCacheService amendCancelReasonCodeLkupCacheService) {
		this.amendCancelReasonCodeLkupCacheService = amendCancelReasonCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureCategoryCodeLkupCacheService() {
		return amendNatureCategoryCodeLkupCacheService;
	}

	public void setAmendNatureCategoryCodeLkupCacheService(
			CodeLkupCacheService amendNatureCategoryCodeLkupCacheService) {
		this.amendNatureCategoryCodeLkupCacheService = amendNatureCategoryCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureCancelCodeLkupCacheService() {
		return amendNatureCancelCodeLkupCacheService;
	}

	public void setAmendNatureCancelCodeLkupCacheService(
			CodeLkupCacheService amendNatureCancelCodeLkupCacheService) {
		this.amendNatureCancelCodeLkupCacheService = amendNatureCancelCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureCategoryLTSCodeLkupCacheService() {
		return amendNatureCategoryLTSCodeLkupCacheService;
	}

	public void setAmendNatureCategoryLTSCodeLkupCacheService(
			CodeLkupCacheService amendNatureCategoryLTSCodeLkupCacheService) {
		this.amendNatureCategoryLTSCodeLkupCacheService = amendNatureCategoryLTSCodeLkupCacheService;
	}

	public CodeLkupCacheService getDelayReasonCodeLkupCacheService() {
		return delayReasonCodeLkupCacheService;
	}

	public void setDelayReasonCodeLkupCacheService(
			CodeLkupCacheService delayReasonCodeLkupCacheService) {
		this.delayReasonCodeLkupCacheService = delayReasonCodeLkupCacheService;
	}

	public CodeLkupCacheService getCreditCardTypeLkupCacheService() {
		return creditCardTypeLkupCacheService;
	}

	public void setCreditCardTypeLkupCacheService(
			CodeLkupCacheService creditCardTypeLkupCacheService) {
		this.creditCardTypeLkupCacheService = creditCardTypeLkupCacheService;
	}

	public AmendmentSubmitService getAmendmentSubmitService() {
		return amendmentSubmitService;
	}

	public void setAmendmentSubmitService(
			AmendmentSubmitService amendmentSubmitService) {
		this.amendmentSubmitService = amendmentSubmitService;
	}

	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}

	public CodeLkupCacheService getAmendNatureSrdCodeLkupCacheService() {
		return amendNatureSrdCodeLkupCacheService;
	}

	public void setAmendNatureSrdCodeLkupCacheService(
			CodeLkupCacheService amendNatureSrdCodeLkupCacheService) {
		this.amendNatureSrdCodeLkupCacheService = amendNatureSrdCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNaturePaymentCodeLkupCacheService() {
		return amendNaturePaymentCodeLkupCacheService;
	}

	public void setAmendNaturePaymentCodeLkupCacheService(
			CodeLkupCacheService amendNaturePaymentCodeLkupCacheService) {
		this.amendNaturePaymentCodeLkupCacheService = amendNaturePaymentCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureCancelEyeNoPPCodeLkupCacheService() {
		return amendNatureCancelEyeNoPPCodeLkupCacheService;
	}

	public void setAmendNatureCancelEyeNoPPCodeLkupCacheService(
			CodeLkupCacheService amendNatureCancelEyeNoPPCodeLkupCacheService) {
		this.amendNatureCancelEyeNoPPCodeLkupCacheService = amendNatureCancelEyeNoPPCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureCancelEyePPCodeLkupCacheService() {
		return amendNatureCancelEyePPCodeLkupCacheService;
	}

	public void setAmendNatureCancelEyePPCodeLkupCacheService(
			CodeLkupCacheService amendNatureCancelEyePPCodeLkupCacheService) {
		this.amendNatureCancelEyePPCodeLkupCacheService = amendNatureCancelEyePPCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureCancelDelNoPPCodeLkupCacheService() {
		return amendNatureCancelDelNoPPCodeLkupCacheService;
	}

	public void setAmendNatureCancelDelNoPPCodeLkupCacheService(
			CodeLkupCacheService amendNatureCancelDelNoPPCodeLkupCacheService) {
		this.amendNatureCancelDelNoPPCodeLkupCacheService = amendNatureCancelDelNoPPCodeLkupCacheService;
	}

	public CodeLkupCacheService getAmendNatureCancelDelPPCodeLkupCacheService() {
		return amendNatureCancelDelPPCodeLkupCacheService;
	}

	public void setAmendNatureCancelDelPPCodeLkupCacheService(
			CodeLkupCacheService amendNatureCancelDelPPCodeLkupCacheService) {
		this.amendNatureCancelDelPPCodeLkupCacheService = amendNatureCancelDelPPCodeLkupCacheService;
	}

	public CodeLkupCacheService getTitleLkupCacheService() {
		return titleLkupCacheService;
	}

	public void setTitleLkupCacheService(CodeLkupCacheService titleLkupCacheService) {
		this.titleLkupCacheService = titleLkupCacheService;
	}

	public AddressRolloutService getAddressRolloutService() {
		return addressRolloutService;
	}

	public void setAddressRolloutService(AddressRolloutService addressRolloutService) {
		this.addressRolloutService = addressRolloutService;
	}

	public PipbActvLtsService getPipbActvLtsService() {
		return pipbActvLtsService;
	}

	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
		this.pipbActvLtsService = pipbActvLtsService;
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public BomWsBackendClient getBomWsBackendClient() {
		return bomWsBackendClient;
	}

	public void setBomWsBackendClient(BomWsBackendClient bomWsBackendClient) {
		this.bomWsBackendClient = bomWsBackendClient;
	}

	public LtsApnSerialFileService getLtsApnSerialFileService() {
		return ltsApnSerialFileService;
	}

	public void setLtsApnSerialFileService(LtsApnSerialFileService ltsApnSerialFileService) {
		this.ltsApnSerialFileService = ltsApnSerialFileService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public PipbActivityLtsSubmissionService getPipbActivityLtsSubmissionService() {
		return pipbActivityLtsSubmissionService;
	}

	public void setPipbActivityLtsSubmissionService(
			PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService) {
		this.pipbActivityLtsSubmissionService = pipbActivityLtsSubmissionService;
	}

	public LtsOrderSearchService getLtsOrderSearchService() {
		return ltsOrderSearchService;
	}

	public void setLtsOrderSearchService(LtsOrderSearchService ltsOrderSearchService) {
		this.ltsOrderSearchService = ltsOrderSearchService;
	}

	public CodeLkupCacheService getAutoAmendModeLkupCacheService() {
		return autoAmendModeLkupCacheService;
	}

	public void setAutoAmendModeLkupCacheService(
			CodeLkupCacheService autoAmendModeLkupCacheService) {
		this.autoAmendModeLkupCacheService = autoAmendModeLkupCacheService;
	}

	public CodeLkupCacheService getLtsAmendCategoryRemarkCodeLkupCacheService() {
		return ltsAmendCategoryRemarkCodeLkupCacheService;
	}

	public void setLtsAmendCategoryRemarkCodeLkupCacheService(
			CodeLkupCacheService ltsAmendCategoryRemarkCodeLkupCacheService) {
		this.ltsAmendCategoryRemarkCodeLkupCacheService = ltsAmendCategoryRemarkCodeLkupCacheService;
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

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}
	


}
