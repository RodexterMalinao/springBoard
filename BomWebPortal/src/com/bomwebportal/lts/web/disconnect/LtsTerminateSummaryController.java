package com.bomwebportal.lts.web.disconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.disconnect.DisconnectServiceSummaryDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSummaryFormDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SummaryBaseService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsTerminateSummaryController extends SimpleFormController {
	protected final Log logger=LogFactory.getLog(getClass());
	
	private final String viewName="lts/disconnect/ltsterminatesummary";
	private final String commandName="serviceSummary";
	
	private OrderStatusService orderStatusService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private SummaryBaseService summaryDisconnectService;
	private ServiceProfileLtsDrgService serviceProfileLtsDrgService;

	
	public LtsTerminateSummaryController() {
		setFormView(viewName);
		setCommandClass(LtsTerminateSummaryFormDTO.class);
		setCommandName(commandName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request,response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) {
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		
		if (orderCapture.getLtsTerminateSummaryForm() != null) {
			this.setStatus(sbOrder, orderCapture.getLtsTerminateSummaryForm());
			return orderCapture.getLtsTerminateSummaryForm();
		}
		
		String locale = RequestContextUtils.getLocale(request).toString();
		boolean isEquiry = LtsConstant.ORDER_ACTION_ENQUIRY.equals(orderCapture.getOrderAction());
		
		DisconnectServiceSummaryDTO disconnectSummary = (DisconnectServiceSummaryDTO)this.summaryDisconnectService.buildSummary(sbOrder, locale, isEquiry);
		getImsPendingOrder(orderCapture, disconnectSummary, sbOrder);
		
		request.setAttribute("today", LtsDateFormatHelper.getSysDate("yyyyMMdd"));
		
		return this.generateSummaryForm(sbOrder, disconnectSummary, isEquiry);
		
	}
	
	private void getImsPendingOrder(TerminateOrderCaptureDTO pOrderCapture, DisconnectServiceSummaryDTO pSrvSummary, SbOrderDTO pSbOrder){
		
		if(pOrderCapture.getImsPendingOrder() != null){
			for(ServiceDetailDTO temp:pSbOrder.getSrvDtls()){
				if(temp instanceof ServiceDetailOtherLtsDTO){
				pSrvSummary.setFsaNumber(((ServiceDetailOtherLtsDTO ) temp).getSrvNum());
				pSrvSummary.setFsaPendingSbOrdId((pOrderCapture.getImsPendingOrder().getOrderId()));
				pSrvSummary.setFsaPendingOCID(pOrderCapture.getImsPendingOrder().getOcId());
				pSrvSummary.setFsaSrd(pOrderCapture.getImsPendingOrder().getSrdStart() + "  -  " + pOrderCapture.getImsPendingOrder().getSrdEnd());
				}
			}
		}
	}
	
	private LtsTerminateSummaryFormDTO generateSummaryForm(SbOrderDTO pSbOrder, ServiceSummaryDTO pSrvSummaryDTO, boolean pIsEquiry) {
		
		LtsTerminateSummaryFormDTO summaryForm = new LtsTerminateSummaryFormDTO();
		summaryForm.setOrderId(pSbOrder.getOrderId());
		summaryForm.setDisconnectServiceSummaryList((DisconnectServiceSummaryDTO)pSrvSummaryDTO);
		summaryForm.setSalesCd(pSbOrder.getSalesCd());
		summaryForm.setSalesChannel(pSbOrder.getSalesChannel());
		summaryForm.setSalesContactNum(pSbOrder.getSalesContactNum());
		summaryForm.setSalesTeam(pSbOrder.getSalesTeam());
		summaryForm.setStaffNum(pSbOrder.getStaffNum());
		summaryForm.setSalesJob(pSbOrder.getSalesJob());
		summaryForm.setSalesPosition(pSbOrder.getSalesPosition());
		summaryForm.setSalesProcessDate(pSbOrder.getSalesProcessDate());
		summaryForm.setSmsNo(pSbOrder.getSmsNo());
		this.setStatus(pSbOrder, summaryForm);
		
		if (pIsEquiry) {
			summaryForm.setRequireButton(LtsTerminateSummaryFormDTO.BUTTON_DISABLE);
		} else {
			this.determineFormButton(summaryForm);
		}
		return summaryForm;
	}
	
	private void determineFormButton(LtsTerminateSummaryFormDTO pSummaryForm) {
		
		List<ServiceSummaryDTO> srvSummaryList = new ArrayList<ServiceSummaryDTO>();
		srvSummaryList.add(pSummaryForm.getDisconnectServiceSummaryList());
		
		int button = LtsTerminateSummaryFormDTO.BUTTON_SIGNOFF;
		
		for (int i=0; srvSummaryList!=null && i<srvSummaryList.size(); ++i) {
			if (StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_APPROVAL_REJECTED, srvSummaryList.get(i).getStatusState())) {
				button = LtsTerminateSummaryFormDTO.BUTTON_DISABLE;
				pSummaryForm.setMessageList(srvSummaryList.get(i).getMessageList());
				break;
			} else if (StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_TOS, srvSummaryList.get(i).getStatusState())
					|| StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_PENDING_ORD, srvSummaryList.get(i).getStatusState())
					|| StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_DISC_5NA, srvSummaryList.get(i).getStatusState())) {
				button = LtsTerminateSummaryFormDTO.BUTTON_SUSPEND;
			} else if (StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_APPROVAL, srvSummaryList.get(i).getStatusState())) {
				button = LtsTerminateSummaryFormDTO.BUTTON_APPROVE;
			}
			pSummaryForm.appendMessage(srvSummaryList.get(i).getMessageList());
			pSummaryForm.appendPromptAlertMessage(srvSummaryList.get(i).getPromptAlertMessage());
		}
		pSummaryForm.setRequireButton(button);
	}
	
	private void setStatus(SbOrderDTO pSbOrder, LtsTerminateSummaryFormDTO pSummaryForm) {
		ServiceDetailDTO srvDtl = LtsSbOrderHelper.getLtsService(pSbOrder);
		
		List<OrderStatusDTO> statusHistDisplayList = setStatus(pSbOrder, this.orderStatusService.getStatusHistory(pSbOrder.getOrderId(), srvDtl.getDtlId()));
		
//		if (statusHistDisplayList.size() == 0) {
//			ServiceDetailOtherLtsDTO eyeImsSrv = LtsSbOrderHelper.getImsEyeService(pSbOrder.getSrvDtls());
//			
//			if (eyeImsSrv == null) {
//				return;
//			}
//			statusHistDisplayList = setStatus(pSbOrder, this.orderStatusService.getStatusHistory(pSbOrder.getOrderId(), eyeImsSrv.getDtlId()));
//		}
		pSummaryForm.setStatusHistList(statusHistDisplayList);
	}
	
	private List<OrderStatusDTO> setStatus(SbOrderDTO pSbOrder, OrderStatusDTO[] statusHists) {
		
		List<OrderStatusDTO> statusHistDisplayList = new ArrayList<OrderStatusDTO>();
		
		for (int i=0; statusHists!=null && i<statusHists.length; ++i) {
			if (StringUtils.equals(LtsConstant.ORDER_STATUS_CANCELLED, statusHists[i].getSbStatus())) {
				statusHists[i].setSbStatus("cancel");
				statusHistDisplayList.add(statusHists[i]);
				if (StringUtils.isNotEmpty(statusHists[i].getReasonCd())){
					statusHists[i].setReasonCd((String)this.suspendReasonLkupCacheService.get(statusHists[i].getReasonCd()));
					
					if(StringUtils.isNotEmpty(statusHists[i].getLastUpdBy()) 
						&& !StringUtils.isBlank((String)this.suspendReasonLkupCacheService.get(statusHists[i].getLastUpdBy())))
					{
							statusHists[i].setReasonCd((String)this.suspendReasonLkupCacheService.get(statusHists[i].getLastUpdBy()));
					}
			   }
			} else if (StringUtils.equals(LtsConstant.ORDER_STATUS_SUBMITTED, statusHists[i].getSbStatus())
					|| (StringUtils.equals(LtsConstant.ORDER_STATUS_PENDING_BOM, statusHists[i].getSbStatus()) && StringUtils.equals("FULL_WQ", statusHists[i].getWqType()))) {
				statusHists[i].setSbStatus("signoff");
				statusHistDisplayList.add(statusHists[i]);
			} else if (StringUtils.equals(LtsConstant.ORDER_STATUS_SUSPENDED, statusHists[i].getSbStatus()) && StringUtils.isNotEmpty(statusHists[i].getReasonCd())) {
				statusHists[i].setSbStatus("suspend");
				statusHistDisplayList.add(statusHists[i]);
				statusHists[i].setReasonCd((String)this.suspendReasonLkupCacheService.get(statusHists[i].getReasonCd()));
			} else if (StringUtils.equals(LtsConstant.ORDER_STATUS_PENDING_APPROVAL, statusHists[i].getSbStatus())) {
				statusHists[i].setSbStatus("request approval");
				statusHistDisplayList.add(statusHists[i]);
			}
		}
		return statusHistDisplayList;
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		LtsTerminateSummaryFormDTO summaryForm = (LtsTerminateSummaryFormDTO)command;
		String action = LtsConstant.ORDER_ACTION_SIGNOFF;
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		
		if (summaryForm.getButtonPressed() == LtsTerminateSummaryFormDTO.BUTTON_REFRESH) {
			orderCapture.setSbOrder(orderRetrieveLtsService.retrieveSbOrder(summaryForm.getOrderId(), false));
			return new ModelAndView(new RedirectView("ltsterminatesummary.html"));
		} else if (summaryForm.getButtonPressed() == LtsTerminateSummaryFormDTO.BUTTON_SIGNOFF) {
			action = LtsConstant.ORDER_ACTION_SIGNOFF;
		} else if (summaryForm.getButtonPressed() == LtsTerminateSummaryFormDTO.BUTTON_APPROVE) {
			action = LtsConstant.ORDER_ACTION_APPROVE;
		} else if (summaryForm.getButtonPressed() == LtsTerminateSummaryFormDTO.BUTTON_CANCEL) {
			action = LtsConstant.ORDER_ACTION_CANCEL;
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, "TEST");
		} else if (summaryForm.getButtonPressed() == LtsTerminateSummaryFormDTO.BUTTON_SUSPEND) {
			action = LtsConstant.ORDER_ACTION_SUSPEND_W_PEND;
		} else if (summaryForm.getButtonPressed() == LtsTerminateSummaryFormDTO.BUTTON_SIMPLE_SUSPEND) {
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, summaryForm.getSuspendReason());
			action = LtsConstant.ORDER_ACTION_SUSPEND;
		}
        
		if(LtsConstant.ORDER_ACTION_APPROVE.equals(action)){
			validateApprovalRemark(orderCapture, errors);
		}
		
		if(LtsConstant.ORDER_ACTION_APPROVE.equals(action)
				|| LtsConstant.ORDER_ACTION_SIGNOFF.equals(action)){
			validate(orderCapture, summaryForm, errors, bomSalesUser.getUsername());
		}
		
		if (errors.hasFieldErrors()) {
			ModelAndView mav = new ModelAndView(viewName);
			mav.addAllObjects(errors.getModel());
			mav.addObject("serviceSummary", summaryForm);
			mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
			return mav;
		}
		
		paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, action);
        return new ModelAndView(new RedirectView(LtsConstant.TERMINATION_ORDER_VIEW), paramMap);
	}
	
	private boolean validate(TerminateOrderCaptureDTO orderCapture, LtsTerminateSummaryFormDTO form, BindException errors, String userId) {
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		ServiceDetailDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailProfileLtsDTO serviceDetailProfile = serviceProfileLtsDrgService
				.getServiceProfile(ltsService.getSrvNum(),
						LtsConstant.SERVICE_TYPE_TEL, userId);
		
		if (serviceDetailProfile == null) {
			errors.rejectValue("buttonPressed", "lts.signoff.invalid.profile");
			return false;
		}

		if (!isAllSupportDocCollected(orderCapture.getSbOrder())) {
			errors.rejectValue("buttonPressed", "lts.signoff.supportDoc.required");
			return false;
		}
		return true;
	}
	
	private boolean isAllSupportDocCollected(SbOrderDTO sbOrder) {
		AllOrdDocAssgnLtsDTO[] allOrdDocAssgnLtsDTOs = orderRetrieveLtsService
				.retrieveAllOrdDocAssgn(sbOrder.getOrderId());
		
		if (ArrayUtils.isEmpty(allOrdDocAssgnLtsDTOs)) {
			return true;
		}
		
		for (AllOrdDocAssgnLtsDTO allOrdDocAssgnLts : allOrdDocAssgnLtsDTOs) {
			if (!StringUtils.equals("Y", allOrdDocAssgnLts.getCollectedInd())
					&& StringUtils.isEmpty(allOrdDocAssgnLts.getWaiveReason())) {
				OrderDocDTO orderDoc = ltsOrderDocumentService.getOrderDoc(allOrdDocAssgnLts.getDocType());
				if (orderDoc != null
						&& StringUtils.equals(LtsConstant.ITEM_MDO_MANDATORY, orderDoc.getMdoInd())) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean validateApprovalRemark(OrderCaptureDTO orderCapture, BindException errors){
		if(orderCapture.getLtsWqRemarkForm() != null){
			if(StringUtils.isNotBlank(orderCapture.getLtsWqRemarkForm().getWqRemark())){
				return true;
			}
		}else if(orderCapture.getSbOrder() != null){
			ServiceDetailLtsDTO coreSrvDtl = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			if(coreSrvDtl != null && coreSrvDtl.getRemarks() != null){
				for(RemarkDetailLtsDTO rmkDtl: coreSrvDtl.getRemarks()){
					if(StringUtils.equals(rmkDtl.getRmkType(), LtsConstant.REMARK_TYPE_APPROVL_REMARK)){
						return true;
					}
				}
			}
		}
		errors.rejectValue("buttonPressed", "lts.approvalremark.required");
		return false;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {

		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		return referenceData;
	}
	
	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}

	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public SummaryBaseService getSummaryDisconnectService() {
		return summaryDisconnectService;
	}

	public void setSummaryDisconnectService(
			SummaryBaseService summaryDisconnectService) {
		this.summaryDisconnectService = summaryDisconnectService;
	}

	public ServiceProfileLtsDrgService getServiceProfileLtsDrgService() {
		return serviceProfileLtsDrgService;
	}

	public void setServiceProfileLtsDrgService(
			ServiceProfileLtsDrgService serviceProfileLtsDrgService) {
		this.serviceProfileLtsDrgService = serviceProfileLtsDrgService;
	}



	
}
