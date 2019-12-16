package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.LtsSummaryFormDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SummaryBaseService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.ConfigProperties;

public class LtsSummaryController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private SummaryBaseService summaryUpgradeEyeService;
	private SummaryBaseService summaryDelService;
	private SummaryBaseService summary2ndDelService;
	private OrderStatusService orderStatusService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsSignatureService ltsSignatureService;
	
	private final String viewName = "ltssummary";
	
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		SbOrderDTO sbOrder = orderCaptureDTO.getSbOrder();
		
		if (orderCaptureDTO.getLtsSummaryForm() != null) {
			this.setStatus(sbOrder, orderCaptureDTO.getLtsSummaryForm());
			return orderCaptureDTO.getLtsSummaryForm();
		}
		String locale = RequestContextUtils.getLocale(request).toString();
		boolean isEquiry = StringUtils.equals("ENQUIRY", orderCaptureDTO.getOrderAction());
		List<ServiceSummaryDTO> serviceSummaryList = new ArrayList<ServiceSummaryDTO>();
		
		if (LtsSbHelper.getLtsEyeService(sbOrder) != null) {
			ServiceSummaryDTO eyeSummary = this.summaryUpgradeEyeService.buildSummary(sbOrder, locale, isEquiry);
			if (eyeSummary != null) {
				serviceSummaryList.add(eyeSummary);
			}
		}
		ServiceSummaryDTO delSymmary = this.summaryDelService.buildSummary(sbOrder, locale, isEquiry);
		
		if (delSymmary != null) {
			serviceSummaryList.add(delSymmary);
		}
		ServiceSummaryDTO del2ndSymmary = this.summary2ndDelService.buildSummary(sbOrder, locale, isEquiry);
		
		if (del2ndSymmary != null) {
			serviceSummaryList.add(del2ndSymmary);
		}

		boolean containsPrepaymentItem = false;
		
		LtsPaymentFormDTO prepayForm = orderCaptureDTO.getLtsPaymentForm();
		if(prepayForm != null){
			if(prepayForm.getPrePayItem() != null && prepayForm.getPrePayItem().isSelected()){
				containsPrepaymentItem = true;
			}
		} 
		
		if(sbOrder != null && sbOrder.getPrepayItem() != null){
			containsPrepaymentItem = true;
		}
		
		return this.generateSummaryForm(sbOrder, serviceSummaryList, isEquiry, containsPrepaymentItem);		
	}

	private LtsSummaryFormDTO generateSummaryForm(SbOrderDTO pSbOrder, List<ServiceSummaryDTO> pSrvSummaryList, boolean pIsEquiry, boolean pContainsPrepayment) {
		
		LtsSummaryFormDTO summaryForm = new LtsSummaryFormDTO();
		summaryForm.setOrderId(pSbOrder.getOrderId());
		summaryForm.setServiceSummaryList(pSrvSummaryList);
		summaryForm.setSalesCd(pSbOrder.getSalesCd());
		summaryForm.setSalesChannel(pSbOrder.getSalesChannel());
		summaryForm.setSalesContactNum(pSbOrder.getSalesContactNum());
		summaryForm.setSalesTeam(pSbOrder.getSalesTeam());
		summaryForm.setStaffNum(pSbOrder.getStaffNum());
		summaryForm.setDistributeMode(pSbOrder.getDisMode());
		summaryForm.setOnlineAccqOrder(LtsSbOrderHelper.isOnlineAcquistionOrder(pSbOrder));
		summaryForm.setSalesJob(pSbOrder.getSalesJob());
		summaryForm.setSalesPosition(pSbOrder.getSalesPosition());
		summaryForm.setSalesProcessDate(pSbOrder.getSalesProcessDate());
		summaryForm.setAllowUpdateEdfRef(isAllowUpdateEdfRef(pSbOrder));
		summaryForm.setSmsNo(pSbOrder.getSmsNo());
		summaryForm.setCreateBy(pSbOrder.getCreateBy());
		summaryForm.setOrderGeneratePenalty(LtsSbOrderHelper.isOrderGeneratePenalty(pSbOrder));
		summaryForm.setContainPrepayment(pContainsPrepayment);
		summaryForm.setSignoffMode(pSbOrder.getSignoffMode());
		this.setStatus(pSbOrder, summaryForm);
		
		if (pIsEquiry) {
			summaryForm.setRequireButton(LtsSummaryFormDTO.BUTTON_DISABLE);
		} else {
			this.determineFormButton(summaryForm);
		}
		return summaryForm;
	}
	
	private boolean isAllowUpdateEdfRef(SbOrderDTO pSbOrder) {
		
		if (LtsSbOrderHelper.isOnlineAcquistionOrder(pSbOrder)) {
			return false;
		}
	
		if (LtsConstant.ORDER_MODE_PREMIER.equals(pSbOrder.getOrderMode())
				|| LtsConstant.ORDER_MODE_CALL_CENTER.equals(pSbOrder.getOrderMode())) {
			ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(pSbOrder);
			if (imsService != null 
					&& LtsConstant.ORDER_TYPE_INSTALL.equals(imsService.getOrderType())) {
				return false;
			}
			return true;
		}

		return false;
		
	}
	
	private void determineFormButton(LtsSummaryFormDTO pSummaryForm) {
		
		List<ServiceSummaryDTO> srvSummaryList = pSummaryForm.getServiceSummaryList();
		int button = LtsSummaryFormDTO.BUTTON_SIGNOFF;
		
		for (int i=0; srvSummaryList!=null && i<srvSummaryList.size(); ++i) {
			if (StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_APPROVAL_REJECTED, srvSummaryList.get(i).getStatusState())
					|| StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_BACKDATE_OVER_120, srvSummaryList.get(i).getStatusState())
					|| StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_PROFILE_OUTDATED, srvSummaryList.get(i).getStatusState())) {
				button = LtsSummaryFormDTO.BUTTON_DISABLE;
				pSummaryForm.setMessageList(srvSummaryList.get(i).getMessageList());
				break;
			} else if (StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_TOS, srvSummaryList.get(i).getStatusState())
					|| StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_PENDING_ORD, srvSummaryList.get(i).getStatusState())) {
				button = LtsSummaryFormDTO.BUTTON_SUSPEND;
			} else if (StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_APPROVAL, srvSummaryList.get(i).getStatusState())) {
				button = LtsSummaryFormDTO.BUTTON_APPROVE;
			}
			pSummaryForm.appendMessage(srvSummaryList.get(i).getMessageList());
			pSummaryForm.appendPromptAlertMessage(srvSummaryList.get(i).getPromptAlertMessage());
		}
		pSummaryForm.setRequireButton(button);
	}
	
	private void setStatus(SbOrderDTO pSbOrder, LtsSummaryFormDTO pSummaryForm) {
		
		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(pSbOrder);
		
		if (ltsService == null) {
			return;
		}
		List<OrderStatusDTO> statusHistDisplayList = setStatus(pSbOrder, this.orderStatusService.getStatusHistory(pSbOrder.getOrderId(), ltsService.getDtlId()));
		
		if (statusHistDisplayList.size() == 0) {
			ServiceDetailOtherLtsDTO eyeImsSrv = LtsSbOrderHelper.getImsEyeService(pSbOrder.getSrvDtls());
			
			if (eyeImsSrv == null) {
				return;
			}
			statusHistDisplayList = setStatus(pSbOrder, this.orderStatusService.getStatusHistory(pSbOrder.getOrderId(), eyeImsSrv.getDtlId()));
		}
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
			}
		}
		return statusHistDisplayList;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		LtsSummaryFormDTO summaryForm = (LtsSummaryFormDTO)command;
		String action = LtsConstant.ORDER_ACTION_SIGNOFF;
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		if (!validate(orderCapture, summaryForm, errors)) {
			if (errors.hasFieldErrors()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject("serviceSummary", summaryForm);
				mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
				mav.addObject("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
				return mav;
			}
		}
		
		if (summaryForm.getButtonPressed() == LtsSummaryFormDTO.BUTTON_REFRESH) {
			orderCapture.setSbOrder(orderRetrieveLtsService.retrieveSbOrder(summaryForm.getOrderId(), false));
			return new ModelAndView(new RedirectView("ltssummary.html"));
		}
		
		else if (summaryForm.getButtonPressed() == LtsSummaryFormDTO.BUTTON_SIGNOFF) {
			action = LtsConstant.ORDER_ACTION_SIGNOFF;
		} else if (summaryForm.getButtonPressed() == LtsSummaryFormDTO.BUTTON_APPROVE) {
			action = LtsConstant.ORDER_ACTION_APPROVE;
		} else if (summaryForm.getButtonPressed() == LtsSummaryFormDTO.BUTTON_CANCEL) {
			action = LtsConstant.ORDER_ACTION_CANCEL;
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, "TEST");
		} else if (summaryForm.getButtonPressed() == LtsSummaryFormDTO.BUTTON_SUSPEND) {
			action = LtsConstant.ORDER_ACTION_SUSPEND_W_PEND;
		} else if (summaryForm.getButtonPressed() == LtsSummaryFormDTO.BUTTON_SIMPLE_SUSPEND) {
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, summaryForm.getSuspendReason());
			action = LtsConstant.ORDER_ACTION_SUSPEND;
		}
        paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, action);
        return new ModelAndView(new RedirectView(LtsConstant.UPGRADE_EYE_ORDER_VIEW), paramMap);
	}

	
	private boolean validate(OrderCaptureDTO orderCapture, LtsSummaryFormDTO form, BindException errors) {

		if (form.getButtonPressed() == LtsSummaryFormDTO.BUTTON_SIGNOFF) {
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, form.getDistributeMode())) {
				if(!orderCapture.isChannelCs() && !(orderCapture.isChannelPremier() && LtsConstant.SIGNOFF_MODE_CALLCENTER.equals(form.getSignoffMode()) )
						&& !LtsConstant.DOC_TYPE_HKBR.equals(orderCapture.getLtsCustomerIdentificationForm().getDocType())){
					if (!ltsSignatureService.isAllSignatureSigned(orderCapture)) {
						errors.rejectValue("buttonPressed", "lts.signoff.esignature.required");
						return false;
					} 
				}
			}
			
			if (LtsSbOrderHelper.isEdfRefRequired(orderCapture.getSbOrder())) {
				ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(orderCapture.getSbOrder());
				if (StringUtils.isBlank(((ServiceDetailOtherLtsDTO)imsService).getEdfRef())) {
					errors.rejectValue("buttonPressed", "lts.signoff.edfRef.required");
					return false;
				}
			}
			
			
			if (!isAllSupportDocCollected(orderCapture.getSbOrder())) {
				errors.rejectValue("buttonPressed", "lts.signoff.supportDoc.required");
				return false;
			}
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
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
		return referenceData;
	}

	private String getCaptureDocIpadAppUri(HttpServletRequest request) {
		
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		if (orderCaptureDTO == null) {
			return null;
		}
		
		SbOrderDTO sbOrder = orderCaptureDTO.getSbOrder();
		
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		
		String scheme = ConfigProperties.getPropertyByEnvironment("orddoc.ipad.uri.scheme");
		Map<String, List<CodeLkupDTO>> codeLkupList = LookupTableBean.getInstance().getCodeLkupList();
		List<CodeLkupDTO> ipadVersionList = codeLkupList.get(LtsConstant.CODE_LKUP_IPAD_VERSION);
		String ipadVersion = "";
		if (ipadVersionList != null && !ipadVersionList.isEmpty()) {
			ipadVersion = ipadVersionList.get(0).getCodeId();
		}
		
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		StringBuilder uri = new StringBuilder();
		uri.append(scheme).append("://").append(sbOrder.getOrderId())
				.append("/").append(bomSalesUser.getUsername()).append("/")
				.append(sessionId).append("/").append(ipadVersion);
		return uri.toString();
	}
	
	
	public SummaryBaseService getSummaryUpgradeEyeService() {
		return this.summaryUpgradeEyeService;
	}

	public void setSummaryUpgradeEyeService(
			SummaryBaseService pSummaryUpgradeEyeService) {
		this.summaryUpgradeEyeService = pSummaryUpgradeEyeService;
	}

	public SummaryBaseService getSummaryDelService() {
		return this.summaryDelService;
	}

	public void setSummaryDelService(SummaryBaseService pSummaryDelService) {
		this.summaryDelService = pSummaryDelService;
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

	public SummaryBaseService getSummary2ndDelService() {
		return summary2ndDelService;
	}

	public void setSummary2ndDelService(SummaryBaseService summary2ndDelService) {
		this.summary2ndDelService = summary2ndDelService;
	}

	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}
	
}
