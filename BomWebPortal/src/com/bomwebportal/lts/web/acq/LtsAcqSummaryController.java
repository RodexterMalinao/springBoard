package com.bomwebportal.lts.web.acq;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsQuotaDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO.SummaryPayMtdDtl;
import com.bomwebportal.lts.dto.form.LtsPremiumSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSummaryFormDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.acq.LtsDsOrderService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.acq.LtsAcqSummaryBaseService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.acq.LtsAcqSbOrderHelper;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.ConfigProperties;
import com.google.common.collect.Lists;

public class LtsAcqSummaryController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsAcqSummaryBaseService ltsAcqSummaryEyeService;
	private LtsAcqSummaryBaseService ltsAcqSummaryDelService;
	private LtsAcqSummaryBaseService ltsAcqSummary2ndDelService;
	private OrderStatusService orderStatusService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsProfileService ltsProfileService;
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	private LtsSignatureService ltsSignatureService;
	private LtsCommonService ltsCommonService;
	private CodeLkupCacheService awaitQcReasonLkupCacheService;
	private CodeLkupCacheService prepaymentCancelReasonLkupCacheService;
	private CodeLkupCacheService ltsDsSuspendReasonLkupCacheService;
	private LtsDsOrderService ltsDsOrderService;
	private LtsOfferService ltsOfferService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	private final String viewName = "/lts/acq/ltsacqsummary";
	private final String commandName = "serviceSummary";
	
	public LtsAcqSummaryController() {
		setCommandClass(LtsAcqSummaryFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	protected Map<String, List> referenceData(HttpServletRequest request) throws Exception {
		Map<String, List> referenceData = new HashMap<String, List>();
		referenceData.put("cancelReasonCodeList", Arrays.asList(prepaymentCancelReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		return referenceData;
	}
	
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		setReferenceData(request, orderCaptureDTO);
		return super.handleRequestInternal(request, response);
	} 
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER); 
		SbOrderDTO sbOrder = orderCaptureDTO.getSbOrder();
		
		if (orderCaptureDTO.getLtsAcqSummaryForm() != null) {
			this.setStatus(sbOrder, orderCaptureDTO.getLtsAcqSummaryForm(), orderCaptureDTO.isEyeOrder());
			return orderCaptureDTO.getLtsAcqSummaryForm();
		}
		String locale = RequestContextUtils.getLocale(request).toString();
		boolean isEquiry = StringUtils.equals("ENQUIRY", orderCaptureDTO.getOrderAction());
		boolean isDsAfterSubmit = StringUtils.equals(LtsConstant.ORDER_ACTION_AFTER_SUBMIT, orderCaptureDTO.getOrderAction());
		LtsAcqServiceSummaryDTO eyeSummary = this.ltsAcqSummaryEyeService.buildSummary(sbOrder, locale, isEquiry);
		LtsAcqServiceSummaryDTO delSymmary = this.ltsAcqSummaryDelService.buildSummary(sbOrder, locale, isEquiry);
		LtsAcqServiceSummaryDTO secDelSymmary = this.ltsAcqSummary2ndDelService.buildSummary(sbOrder, locale, isEquiry);
		List<LtsAcqServiceSummaryDTO> serviceSummaryList = new ArrayList<LtsAcqServiceSummaryDTO>();
		
		if (eyeSummary != null) {
			serviceSummaryList.add(eyeSummary);
		}
		if (delSymmary != null) {
			serviceSummaryList.add(delSymmary);
		}
		if (secDelSymmary != null) {
			serviceSummaryList.add(secDelSymmary);
		}

		boolean containsPrepaymentItem = false;
		String prepayAmount = "";

		if(sbOrder != null && sbOrder.getPrepay() != null){ 	// Recall order case
			if(sbOrder != null && sbOrder.getPrepay() != null){ 	
				for(PrepayLtsDTO prepay : sbOrder.getPrepay()){
					if(!"C".equals(prepay.getPaymentStatus())){
						containsPrepaymentItem = true;
						prepayAmount = prepay.getPrepayAmt();
						break;
					}
				}
			}
		}
		
		LtsAcqPaymentMethodFormDTO prepayForm = orderCaptureDTO.getLtsAcqPaymentMethodFormDTO();
		if(prepayForm != null){
			for(PaymentMethodDtl prepayDtl : prepayForm.getPaymentMethodDtlList()){
				if(prepayDtl.getAutopayPrePayItem() != null && prepayDtl.getAutopayPrePayItem().isSelected()){
					containsPrepaymentItem = true;
					prepayAmount = prepayDtl.getAutopayPrePayItem().getOneTimeAmtTxt();
					break;
				}
				if(prepayDtl.getCashPrePayItem() != null && prepayDtl.getCashPrePayItem().isSelected()){
					containsPrepaymentItem = true;
					prepayAmount = prepayDtl.getCashPrePayItem().getOneTimeAmtTxt();
					break;
				}
			}
		} 		
		
		String wqType = "";
		if(orderCaptureDTO.isEyeOrder()){
			ServiceDetailLtsDTO eyeLtsSrv = LtsSbOrderHelper.getLtsEyeService(sbOrder);
			wqType = eyeLtsSrv.getWorkQueueType();
		}
		else{
			ServiceDetailLtsDTO delLtsSrv = LtsSbOrderHelper.getDelServices(sbOrder);
			wqType = delLtsSrv.getWorkQueueType();
		}
		
		request.setAttribute("newAcqEyeServiceLabel", messageSource.getMessage("lts.acq.summary.newAcqOfEyeService", new Object[] {}, this.locale));
		request.setAttribute("newAcqFixedLineServiceLabel", messageSource.getMessage("lts.acq.summary.newAcqOfFixedLineService", new Object[] {}, this.locale));
		request.setAttribute("summaryFor2ndDELLabel", messageSource.getMessage("lts.acq.summary.summaryFor2ndDEL", new Object[] {}, this.locale));
		
		request.setAttribute("signoffBtnName", messageSource.getMessage("lts.acq.summary.signoff", new Object[] {}, this.locale));
		request.setAttribute("requestApprovalBtnName", messageSource.getMessage("lts.acq.summary.requestApproval", new Object[] {}, this.locale));
		request.setAttribute("suspendBtnName", messageSource.getMessage("lts.acq.summary.suspend", new Object[] {}, this.locale));
		request.setAttribute("submitBtnName", messageSource.getMessage("lts.acq.summary.submit", new Object[] {}, this.locale));			
		
		request.setAttribute("disModeDigital", messageSource.getMessage("lts.acq.summary.digital", new Object[] {}, this.locale));
		request.setAttribute("disModePaper", messageSource.getMessage("lts.acq.summary.paper", new Object[] {}, this.locale));	
		
		request.setAttribute("esigEmailLangEng", messageSource.getMessage("lts.acq.summary.english", new Object[] {}, this.locale));
		request.setAttribute("esigEmailLangChi", messageSource.getMessage("lts.acq.summary.traditionalChinese", new Object[] {}, this.locale));	

		return this.generateSummaryForm(sbOrder, serviceSummaryList, isEquiry, orderCaptureDTO.isEyeOrder(), containsPrepaymentItem, prepayAmount, bomSalesUser, wqType, orderCaptureDTO.isChannelDirectSales(), isDsAfterSubmit);		
	}

	private LtsAcqSummaryFormDTO generateSummaryForm(SbOrderDTO pSbOrder, List<LtsAcqServiceSummaryDTO> pSrvSummaryList, boolean pIsEquiry, boolean pIsEyeOrder, boolean pContainsPrepayment, String pPrepayAmount, BomSalesUserDTO pBomSalesUser, String pWorkQueueType, boolean isDsOrder, boolean isDsAfterSubmit) {		
		LtsAcqSummaryFormDTO summaryForm = new LtsAcqSummaryFormDTO();
		ServiceDetailDTO serviceDtl = LtsSbHelper.getLtsService(pSbOrder);
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
		summaryForm.setContainPrepayment(pContainsPrepayment);
		summaryForm.setOrderGeneratePenalty(LtsSbOrderHelper.isOrderGeneratePenalty(pSbOrder));
		summaryForm.setWorkQueueType(pWorkQueueType);
		summaryForm.setSignoffMode(pSbOrder.getSignoffMode());
		if(!isDsOrder){
			this.setStatus(pSbOrder, summaryForm, pIsEyeOrder);
		}
		
		summaryForm.setPayMtdTypeN(chkPayMtdTypeN(pSbOrder));
		
		if (pIsEquiry) {
			summaryForm.setRequireButton(LtsAcqSummaryFormDTO.BUTTON_DISABLE);
		} else {
			this.determineFormButton(summaryForm, pSbOrder, isDsOrder, isDsAfterSubmit);
		}
		
		if(pContainsPrepayment){
			summaryForm.setPrepayAccountNumber(pSbOrder.getPrepayAcctNum());
			summaryForm.setPrepayAmount(pPrepayAmount);
		}	
		
		
		if(pBomSalesUser != null){
			summaryForm.setSalesChannelId(String.valueOf(pBomSalesUser.getChannelId()));
		}
		
		if(pSbOrder != null && pSbOrder.getLtsDsOrderInfo() != null){
			summaryForm.setQcCallTimePeriod(pSbOrder.getLtsDsOrderInfo().getQcCallTime());
			summaryForm.setWaiveQc(pSbOrder.getLtsDsOrderInfo().getWaiveQcCd());
			summaryForm.setMustQc(pSbOrder.getLtsDsOrderInfo().getMustQc());

			if(LtsConstant.NAME_MISMATCH_APR_CD_PENDING_APPROVAL.equals(pSbOrder.getLtsDsOrderInfo().getNameMismatchStatus())){
				summaryForm.setAllowResendNameNotMatchWQ(true);
			}
		}
		
		if(pSbOrder != null && pSbOrder.getPrepay() != null){
			for(PrepayLtsDTO prepayInfo : pSbOrder.getPrepay()){
				if(prepayInfo.getPrepayType().equals("P")){  		// PREPAY ITEM
					summaryForm.setPrepayShopCode(prepayInfo.getShopCode());
					summaryForm.setPrepayTransactionCode(prepayInfo.getTranCode());									
					summaryForm.setPrepayDate(prepayInfo.getPrepayDate());
					//summaryForm.setPrepayCancelReasonCode();
					break;
				}
			}
			if(pSbOrder.getSrvDtls() != null){
				OrderStatusDTO[] status = orderStatusService.getStatus(pSbOrder.getOrderId());
		    	for (int i=0; status!=null && i<status.length; ++i) {
		 		   if ((StringUtils.equals(LtsBackendConstant.ORDER_STATUS_AWAIT_PREPAYMENT, status[i].getSbStatus())
		 				   && (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_REASON_AWAIT_PREPAY, status[i].getReasonCd())))
		 				  || StringUtils.equals(LtsBackendConstant.ORDER_STATUS_EXTRACTED, status[i].getSbStatus())
		 				  || StringUtils.equals(LtsBackendConstant.ORDER_STATUS_SUBMITTED, status[i].getSbStatus())) {
		 			  summaryForm.setDsOrderSubmit(true);
		 		   }
		        }
			}
		}
		if (serviceDtl.getRemarks() != null && ArrayUtils.isNotEmpty(serviceDtl.getRemarks())) {
			summaryForm.setSuspendRemarks(serviceDtl.remarkToString(LtsConstant.REMARK_TYPE_SUSPEND_REMARK));
		}
		
		// Added by Markball, 12-02-2016
		if(pSbOrder != null && pSbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = pSbOrder.getSrvDtls();
			String legacyId = "";
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];
					if(tempSrvLtsDtls.getLegacyOrdNum() == null)
					{
						legacyId += messageSource.getMessage("lts.acq.summary.noLegacyIDYet", new Object[] {}, this.locale);
					}
					else
					{
						//legacyId += tempSrvLtsDtls.getLegacyOrdNum()+" (for DN "+tempSrvDtls[i].getSrvNum()+"),";
						legacyId += messageSource.getMessage("lts.acq.summary.forDN", new Object[] {tempSrvLtsDtls.getLegacyOrdNum(),tempSrvDtls[i].getSrvNum()}, this.locale) ;
					}					
				}
			}
			
			if(!legacyId.equals(""))
			{
				legacyId = legacyId.substring(0, legacyId.length()-1);
				summaryForm.setLegacyId(legacyId);
			}
		}
		else
		{
			summaryForm.setLegacyId(messageSource.getMessage("lts.acq.summary.noLegacyIDYet", new Object[] {}, this.locale));
		}
		// 
		
		if(pSbOrder != null && pSbOrder.getCustIguardReg() != null)
		{
			if(StringUtils.isNotBlank(pSbOrder.getCustIguardReg().getCarecashInd()))
			{
				String tempCarecashInd = pSbOrder.getCustIguardReg().getCarecashInd();
				summaryForm.setCarecashInd(tempCarecashInd);
				if(tempCarecashInd.equalsIgnoreCase("I"))
				{
					summaryForm.setCarecashDmInd(pSbOrder.getCustIguardReg().getCarecashDmInd());
					summaryForm.setCarecashContactNum(pSbOrder.getCustIguardReg().getContactNum());
					summaryForm.setCarecashEmail(pSbOrder.getCustIguardReg().getEmailAddr());
				}
				
			}
		}
		
		return summaryForm;
	}
	
	private boolean chkPayMtdTypeN(SbOrderDTO pSbOrder){
		ServiceDetailDTO serviceDtl = LtsSbOrderHelper.getLtsService(pSbOrder);
		for(AccountServiceAssignLtsDTO accts : serviceDtl.getAccounts()){
				if(StringUtils.equals(LtsConstant.PAYMENT_TYPE_AWAIT_PAYMENT_METHOD, accts.getAccount().getFuturePayment().getPayMtdType())){
					return true;
				}
		}
		return false;
	}
	
	private boolean isAllowUpdateEdfRef(SbOrderDTO pSbOrder) {
		
		if (LtsSbOrderHelper.isOnlineAcquistionOrder(pSbOrder)) {
			return false;
		}
	
		if (LtsConstant.ORDER_MODE_PREMIER.equals(pSbOrder.getOrderMode())
				|| LtsConstant.ORDER_MODE_CALL_CENTER.equals(pSbOrder.getOrderMode())) {
			return true;
		}

		return false;
		
	}
	
	private void determineFormButton(LtsAcqSummaryFormDTO pSummaryForm, SbOrderDTO pSbOrder, boolean isDsOrder, boolean isDsAfterSubmit) {
		
		List<LtsAcqServiceSummaryDTO> srvSummaryList = pSummaryForm.getServiceSummaryList();
		int button = LtsAcqSummaryFormDTO.BUTTON_SIGNOFF;
		
		for (int i=0; srvSummaryList!=null && i<srvSummaryList.size(); ++i) {
			if (StringUtils.equals(LtsAcqServiceSummaryDTO.STATUS_STATE_APPROVAL_REJECTED, srvSummaryList.get(i).getStatusState())
					|| StringUtils.equals(ServiceSummaryDTO.STATUS_STATE_PROFILE_OUTDATED, srvSummaryList.get(i).getStatusState())) {
				button = LtsAcqSummaryFormDTO.BUTTON_DISABLE;
				pSummaryForm.setMessageList(srvSummaryList.get(i).getMessageList());
				break;
			} else if (StringUtils.equals(LtsAcqServiceSummaryDTO.STATUS_STATE_TOS, srvSummaryList.get(i).getStatusState())
					|| StringUtils.equals(LtsAcqServiceSummaryDTO.STATUS_STATE_PENDING_ORD, srvSummaryList.get(i).getStatusState())) {
				button = LtsAcqSummaryFormDTO.BUTTON_SUSPEND;
			} else if (StringUtils.equals(LtsAcqServiceSummaryDTO.STATUS_STATE_APPROVAL, srvSummaryList.get(i).getStatusState())) {
				button = LtsAcqSummaryFormDTO.BUTTON_APPROVE;
			}
			pSummaryForm.appendMessage(srvSummaryList.get(i).getMessageList());
			pSummaryForm.appendPromptAlertMessage(srvSummaryList.get(i).getPromptAlertMessage());
		}
		
		if(isDsOrder){
			OrderStatusDTO[] statusList = null;
			statusList = this.orderStatusService.getStatus(pSbOrder.getOrderId());
			
			for (int i=0; statusList!=null && i<statusList.length; ++i) {
				if (StringUtils.equals(LtsConstant.ORDER_STATUS_SUBMITTED, statusList[i].getSbStatus()) 
						|| (!( StringUtils.equals(LtsConstant.ORDER_STATUS_AWAIT_PREPAYMENT, statusList[i].getSbStatus()) 
								 && StringUtils.equals(LtsConstant.ORDER_STATUS_REASON_AWAIT_PREPAY, statusList[i].getReasonCd())
							  ) && isDsAfterSubmit)) {
					button = LtsAcqSummaryFormDTO.BUTTON_DISABLE;
				}
				if (StringUtils.equals(LtsConstant.ORDER_STATUS_AWAIT_QC, statusList[i].getSbStatus())
						&& isDsAfterSubmit) {
					button = LtsAcqSummaryFormDTO.BUTTON_DISABLE;
				}
			}
			
		}
		
		pSummaryForm.setRequireButton(button);
	}
	
	private String[] getDsStatusHistory(String pOrderId) {
		List<String> history = new ArrayList<String>();
		String signoffDate = this.orderStatusService.getSignoffDate(pOrderId);
		
		if(StringUtils.isNotBlank(signoffDate)){
			history.add(messageSource.getMessage("lts.acq.summary.orderSignoffOn", new Object[] {}, this.locale)+" " + signoffDate);
			String prepaymentSettleDate = this.orderStatusService.getPrepaymentSettleDate(pOrderId);
			if(StringUtils.isNotBlank(prepaymentSettleDate)){
				history.add(messageSource.getMessage("lts.acq.summary.prepaymentSettledOn", new Object[] {prepaymentSettleDate}, this.locale));
			}
		}

		if(history == null || history.size() == 0){
			return null;
		}
		else{
			return (String[]) history.toArray(new String[history.size()]);
		}
	}
	
	private void setStatus(SbOrderDTO pSbOrder, LtsAcqSummaryFormDTO pSummaryForm, boolean pIsEyeOrder) {
		
		ServiceDetailLtsDTO eyeLtsSrv = LtsSbOrderHelper.getLtsEyeService(pSbOrder);
		ServiceDetailLtsDTO delLtsSrv = LtsSbOrderHelper.getDelServices(pSbOrder);
		List<OrderStatusDTO> statusHistDisplayList = null;
		
		if (eyeLtsSrv == null && delLtsSrv == null) {
			return;
		}
		
		if (pIsEyeOrder){
		    statusHistDisplayList = setStatus(pSbOrder, this.orderStatusService.getStatusHistory(pSbOrder.getOrderId(), eyeLtsSrv.getDtlId()));
		}
		else{
		    statusHistDisplayList = setStatus(pSbOrder, this.orderStatusService.getStatusHistory(pSbOrder.getOrderId(), delLtsSrv.getDtlId()));
		}
		
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
				statusHists[i].setSbStatus(messageSource.getMessage("lts.acq.summary.statusHist.cancel", new Object[] {}, this.locale));
				statusHistDisplayList.add(statusHists[i]);
			} else if (StringUtils.equals(LtsConstant.ORDER_STATUS_SUBMITTED, statusHists[i].getSbStatus())
					|| (StringUtils.equals(LtsConstant.ORDER_STATUS_PENDING_BOM, statusHists[i].getSbStatus()) && StringUtils.equals(LtsBackendConstant.WQ_TYPE_FULL, statusHists[i].getWqType()))
					|| (StringUtils.equals(LtsConstant.ORDER_STATUS_PENDING_BOM, statusHists[i].getSbStatus()) && StringUtils.equals(LtsBackendConstant.WQ_TYPE_PARTIAL, statusHists[i].getWqType()))) {
				statusHists[i].setSbStatus(messageSource.getMessage("lts.acq.summary.statusHist.signoff", new Object[] {}, this.locale));
				statusHistDisplayList.add(statusHists[i]);
			} else if (StringUtils.equals(LtsConstant.ORDER_STATUS_AWAIT_QC, statusHists[i].getSbStatus())) {
				statusHists[i].setSbStatus(messageSource.getMessage("lts.acq.summary.statusHist.submit", new Object[] {}, this.locale));
				statusHistDisplayList.add(statusHists[i]);	
				statusHists[i].setReasonCd((String)this.awaitQcReasonLkupCacheService.get(statusHists[i].getReasonCd()));
			} else if (StringUtils.equals(LtsConstant.ORDER_STATUS_SUSPENDED, statusHists[i].getSbStatus()) && StringUtils.isNotEmpty(statusHists[i].getReasonCd())) {
				statusHists[i].setSbStatus(messageSource.getMessage("lts.acq.summary.statusHist.suspend", new Object[] {}, this.locale));
				statusHistDisplayList.add(statusHists[i]);
				if(LtsConstant.ORDER_PREFIX_DIRECT_SALES.equals(pSbOrder.getOrderId().substring(0, 1))
						||LtsConstant.ORDER_PREFIX_DIRECT_SALES_NOW_TV_QA.equals(pSbOrder.getOrderId().substring(0, 1))){
					statusHists[i].setReasonCd(getReasonDescInLang((String)this.ltsDsSuspendReasonLkupCacheService.get(statusHists[i].getReasonCd())));
				}else{
					statusHists[i].setReasonCd(getReasonDescInLang((String)this.suspendReasonLkupCacheService.get(statusHists[i].getReasonCd())));
				}
			}
		}
		return statusHistDisplayList;
	}
	
	private String getReasonDescInLang(String reasonStr)
	{
		
		if(reasonStr.equalsIgnoreCase("Await Appointment Making (cancel after 7 day)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.swaitAppointmentMaking", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("User Missing Submit Order (cancel after 2 days)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.userMissingSubmitOrder", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("Await Document (cancel after 7 day)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.awaitDocument", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("DRG Downtime"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.DRGDowntime", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("5NA Case (Cancel after 30 days)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.5NACase", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("Other (cancel after 7 day)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.Other", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("Pending Order Exist (cancel after SRD + 30 days)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.pendingOrderExist", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("Await Payment (cancel after 7 days)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.awaitPayment", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("Manual Cancellation"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.manualCancellation", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("TOS Service Number (cancel after 7 day)"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.TOSServiceNumber", new Object[] {}, this.locale);
		}
		else if (reasonStr.equalsIgnoreCase("Await Credit Card / Bank Auto Pay Payment"))
		{
			reasonStr = messageSource.getMessage("lts.acq.common.suspendReason.awaitCreditCardOrAutoPayPayment", new Object[] {}, this.locale);
		}
		
		return reasonStr;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		LtsAcqSummaryFormDTO summaryForm = (LtsAcqSummaryFormDTO)command;
		String action = LtsConstant.ORDER_ACTION_SIGNOFF;
		
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		boolean isAwaitPrepaymentSubmittedForDs = false;
		
		PrepayLtsDTO prepayUpdateInfo = new PrepayLtsDTO();
		PrepayLtsDTO reusePrepayInfo = new PrepayLtsDTO();
		String reusePrepayOrderId = null;
		boolean containPrepay = false;	
		
		 if(StringUtils.isNotBlank(summaryForm.getSuspendRemarks())){
			 orderCapture.setSuspendRemark(summaryForm.getSuspendRemarks());
		 }
		 else{
			 orderCapture.setSuspendRemark(null);
		 }
		
		String prePayAccNo = "";
		for( LtsAcqServiceSummaryDTO summary : summaryForm.getServiceSummaryList()){
			if(summary.getSummaryPayMtdDtlList() != null){
				for(SummaryPayMtdDtl payDtl : summary.getSummaryPayMtdDtlList()){
					if(!LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payDtl.getPaymentMethod())
							&& summary.getPrepaymentItemList() != null && summary.getPrepaymentItemList().size() > 0){
						containPrepay = true;
						prePayAccNo = summary.getRacctNum();
					}
				}
			}
		}	
		
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		if((orderCapture != null && orderCapture.isChannelDirectSales() && containPrepay) || summaryForm.isDsOrderSubmit()){
			isAwaitPrepaymentSubmittedForDs = true;
		}
		else{
			if (!validate(orderCapture, summaryForm, errors)) {
				if (errors.hasFieldErrors()) {
					summaryForm.setDsOrderSubmit(false);
					ModelAndView mav = new ModelAndView(viewName);
					mav.addAllObjects(errors.getModel());
					mav.addObject("serviceSummary", summaryForm);
					if(orderCapture.isChannelDirectSales()){
						mav.addObject("suspendReasonList", Arrays.asList(ltsDsSuspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
					}else{
						mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
					}
					mav.addObject("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
					return mav;
				}
			}
		}
		
		if (summaryForm.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_REFRESH) {
			orderCapture.setSbOrder(orderRetrieveLtsService.retrieveSbOrder(summaryForm.getOrderId(), false));
			return new ModelAndView(new RedirectView("ltsacqsummary.html"));
		}
		
		else if (summaryForm.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_SIGNOFF) {
			List<String> sList = selectedDnValidation(orderCapture.getSbOrder(), bomSalesUser.getUsername());
			if (sList.size()>0) {
				summaryForm.setMessageList(sList);
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, summaryForm);
				return mav;
			}
			
			sList = validateItemQuota(orderCapture);
			if (sList.size()>0) {
				summaryForm.setMessageList(sList);
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, summaryForm);
				return mav;
			}
			
			action = LtsConstant.ORDER_ACTION_SIGNOFF;	
			if(orderCapture.isChannelDirectSales()){
				action = LtsConstant.ORDER_ACTION_DS_SUBMIT;	
				if(!StringUtils.equals(bomSalesUser.getChannelCd(), LtsConstant.CHANNEL_CD_DIRECT_SALES_QC)){
					
					if(summaryForm!=null)
					{
						if(summaryForm.getServiceSummaryList()!=null)
						{
							logger.info("[1st typed DocNum]: " + summaryForm.getServiceSummaryList().get(0).getDocNum());	
						}				
						logger.info("[Retype DocNum]: " + summaryForm.getRetypeDocNum());
					}
					
					SbOrderDTO sbOrder = orderCapture.getSbOrder();
					logger.warn("[Contain PREPAYMENT]: " + containPrepay);
					
					if(isAwaitPrepaymentSubmittedForDs && StringUtils.isBlank(summaryForm.getPrepayDate())){
						if(StringUtils.isBlank(summaryForm.getPrepayDateYear()) || StringUtils.isBlank(summaryForm.getPrepayDateMonth()) || StringUtils.isBlank(summaryForm.getPrepayDateDay())
								|| StringUtils.isBlank(summaryForm.getPrepayTimeHour()) || StringUtils.isBlank(summaryForm.getPrepayTimeMin()) || StringUtils.isBlank(summaryForm.getPrepayTimeSec())){
							ModelAndView mav = new ModelAndView(viewName);
							mav.addAllObjects(errors.getModel());
							mav.addObject("serviceSummary", summaryForm);
							mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
							mav.addObject("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
							mav.addObject("alertMessage", messageSource.getMessage("lts.acq.summary.plsFillInPrepaymentSettleDate", new Object[] {}, this.locale));
							return mav;
						}
						
						String prepaymentSettleDate = summaryForm.getPrepayDateYear() +  summaryForm.getPrepayDateMonth() + summaryForm.getPrepayDateDay() 
								+ summaryForm.getPrepayTimeHour() + summaryForm.getPrepayTimeMin() + summaryForm.getPrepayTimeSec();
						
						if(!isValidPrepaymentSettlementDate(prepaymentSettleDate)){
							ModelAndView mav = new ModelAndView(viewName);
							mav.addAllObjects(errors.getModel());
							mav.addObject("serviceSummary", summaryForm);
							mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
							mav.addObject("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
							mav.addObject("alertMessage", messageSource.getMessage("lts.acq.summary.invalidPrepaymentSettleDate", new Object[] {}, this.locale) + prepaymentSettleDate);
							return mav;
						}
						
						List<String> duplicatedShopCodeTranCodeResult = ltsDsOrderService.isDuplicatedShopCodeTranCode(sbOrder.getOrderId(), summaryForm.getPrepayShopCode(), summaryForm.getPrepayTransactionCode(), prepaymentSettleDate);
						if(duplicatedShopCodeTranCodeResult!= null && duplicatedShopCodeTranCodeResult.size() > 0){
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							String tempCancelOrderDate = "";
							Date cancelOrderDate = new Date();
							Calendar tempCalendar = Calendar.getInstance();
							tempCalendar.setTime(cancelOrderDate);
							tempCalendar.add(Calendar.DATE,-4);
							tempCancelOrderDate = sdf.format(tempCalendar.getTime())+"000000";
							String tempOrderId = "";
							boolean canReuseTranCd = false;
							String tempResult = "";
							for(int i=0; i<duplicatedShopCodeTranCodeResult.size(); i++)
							{
								tempOrderId = duplicatedShopCodeTranCodeResult.get(i);
								tempResult = ltsDsOrderService.canReuseShopCodeTranCode(prePayAccNo, tempOrderId, tempCancelOrderDate);
								if(tempResult!=null)
								{									
									canReuseTranCd = true;
									break;
								}
							}
														
							if(canReuseTranCd)
							{		
								if(tempResult.equalsIgnoreCase("DIFF_ACC"))
								{
									ModelAndView mav = new ModelAndView(viewName);
									mav.addAllObjects(errors.getModel());
									mav.addObject("serviceSummary", summaryForm);
									mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
									mav.addObject("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
									mav.addObject("alertMessage", messageSource.getMessage("lts.acq.summary.accountNumberNotSameAsBefore", new Object[] {}, this.locale));
									return mav;
								}
								
								if(summaryForm.getPrepayStatus()!=null && summaryForm.getPrepayStatus().equalsIgnoreCase("R"))
								{
									reusePrepayOrderId = tempOrderId;
								}
								else
								{
									orderCapture.setOrderAction(summaryForm.getPreEnquiry());
									LtsSessionHelper.clearAcqOrderCapture(request);
									LtsSessionHelper.setAcqOrderCapture(request,orderCapture);
									
									ModelAndView mav = new ModelAndView(viewName);
									mav.addAllObjects(errors.getModel());
									mav.addObject("serviceSummary", summaryForm);
									mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
									mav.addObject("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
									//mav.addObject("confirmReuseMsg", "canReuseTranCd - OrderId:" + tempOrderId + ", CancelOrderDate:" + tempCancelOrderDate+ ", OrderAction:" + orderCapture.getOrderAction()+ ", preEnquiry:" + summaryForm.getPreEnquiry());
									mav.addObject("confirmReuseMsg", messageSource.getMessage("lts.acq.summary.samePaymentSlipInCancelOrderReuse", new Object[] {tempOrderId}, this.locale));
									
									return mav;
								}
							}
							else
							{
								ModelAndView mav = new ModelAndView(viewName);
								mav.addAllObjects(errors.getModel());
								mav.addObject("serviceSummary", summaryForm);
								mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
								mav.addObject("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
								mav.addObject("alertMessage", messageSource.getMessage("lts.acq.summary.duplicatedShopCodeNTransCode", new Object[] {}, this.locale));
								return mav;
							}
						}
	
						if(sbOrder != null && sbOrder.getPrepay() != null){
							PrepayLtsDTO[] prepayInfoList = sbOrder.getPrepay();
							for(PrepayLtsDTO info : prepayInfoList){
								if(StringUtils.equals(info.getPrepayType(), "P")){
									info.setBillType(LtsBackendConstant.LTS_BILL_TYPE);
									info.setMercahntCode(LtsBackendConstant.MERCHANT_CODE);
									info.setShopCode(summaryForm.getPrepayShopCode());
									info.setTranCode(summaryForm.getPrepayTransactionCode());
									info.setPaymentStatus("S");
									info.setPrepayDate(prepaymentSettleDate);
									prepayUpdateInfo = info;
									
									if(summaryForm.getPrepayStatus()!=null && summaryForm.getPrepayStatus().equalsIgnoreCase("R"))
									{
										reusePrepayInfo.setPrepayType(info.getPrepayType());
										reusePrepayInfo.setBillType(LtsBackendConstant.LTS_BILL_TYPE);
										reusePrepayInfo.setMercahntCode(LtsBackendConstant.MERCHANT_CODE);
										reusePrepayInfo.setShopCode(summaryForm.getPrepayShopCode());
										reusePrepayInfo.setTranCode(summaryForm.getPrepayTransactionCode());
										reusePrepayInfo.setPrepayDate(prepaymentSettleDate);
										reusePrepayInfo.setOrderId(reusePrepayOrderId);
										reusePrepayInfo.setPaymentStatus("R");
									}
									
								}
							}									
						}
						
						logger.warn("[START] updatePrepayInfo ");
						if(prepayUpdateInfo != null && ltsDsOrderService != null){
							logger.warn("[PROCESS] updatePrepayInfo... ");
							ltsDsOrderService.updatePrepayInfo(sbOrder.getOrderId(), prepayUpdateInfo, bomSalesUser.getUsername());
						}
						if(summaryForm.getPrepayStatus()!=null && summaryForm.getPrepayStatus().equalsIgnoreCase("R")&&reusePrepayInfo != null && ltsDsOrderService != null)
						{
							logger.warn("[PROCESS] updateReusePrepayInfo... ");
							ltsDsOrderService.updatePrepayInfo(reusePrepayOrderId, reusePrepayInfo, bomSalesUser.getUsername());
						}
						logger.warn("[COMPLETED] updatePrepayInfo ");
					}
					else{						
						logger.warn("[START] updateDsInfo ");
						if(ltsDsOrderService != null){
							logger.warn( sbOrder.getOrderId() + ": [PROCESS] updateDsInfo... QC: " + summaryForm.getQcCallTimePeriod() + ", Waive CD: " + summaryForm.getWaiveQc());
							if(sbOrder != null && sbOrder.getLtsDsOrderInfo() != null){
								LtsDsOrderInfoDTO dsInfo = sbOrder.getLtsDsOrderInfo();
								dsInfo.setQcCallTime(summaryForm.getQcCallTimePeriod());
								dsInfo.setWaiveQcCd(summaryForm.getWaiveQc());				
							}
							if(orderCapture != null && orderCapture.getLtsDsOrderInfo() != null){
								LtsDsOrderInfoDTO dsInfo2 = orderCapture.getLtsDsOrderInfo();
								dsInfo2.setQcCallTime(summaryForm.getQcCallTimePeriod());
								dsInfo2.setWaiveQcCd(summaryForm.getWaiveQc());		
							}
							ltsDsOrderService.updateDsInfo(sbOrder.getOrderId(), summaryForm.getQcCallTimePeriod(), summaryForm.getWaiveQc(), bomSalesUser.getUsername());
						}
						logger.warn("[COMPLETED] updateDsInfo ");
					}
				}
			}
		} else if (summaryForm.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_APPROVE) {
			action = LtsConstant.ORDER_ACTION_APPROVE;
		} else if (summaryForm.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_CANCEL) {
			action = LtsConstant.ORDER_ACTION_CANCEL;
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, "TEST");
		} else if (summaryForm.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_SUSPEND) {
			action = LtsConstant.ORDER_ACTION_SUSPEND_W_PEND;
		} else if (summaryForm.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_SIMPLE_SUSPEND) {
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, summaryForm.getSuspendReason());
			action = LtsConstant.ORDER_ACTION_SUSPEND;
			
			List<String> sList = validateItemQuota(orderCapture);
			if (sList.size()>0) {
				summaryForm.setMessageList(sList);
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, summaryForm);
				return mav;
			}
			
		}
        paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, action);
        return new ModelAndView(new RedirectView(LtsConstant.NEW_ACQ_ORDER_VIEW), paramMap);
	}
	
	private List<String> validateItemQuota(AcqOrderCaptureDTO orderCapture){
		
		Map<String,LtsQuotaDTO> quotaMap = new HashMap<String,LtsQuotaDTO>();
		List<String> outQuotaItemList = new ArrayList<String>();
		List<String> errorMsgList = new ArrayList<String>();
		boolean needCheckQuota = false;  // need to Check Quota if order is not recalled and recalled without suspend reason
		if(orderCapture!=null)
		{
			if(orderCapture.getSbOrder()!=null)
			{
				String sbOrderId = orderCapture.getSbOrder().getOrderId();
				if(!StringUtils.isBlank(sbOrderId))
				{
					needCheckQuota = orderStatusService.noSuspendReason(sbOrderId,null);
				}
			}
		}
		
		if(orderCapture!=null && needCheckQuota)
		{
			BasketDetailDTO basketDtl = orderCapture.getSelectedBasket();
			quotaMap = ltsOfferService.getQuotaMapByBasket(basketDtl,quotaMap);	
			
			/*
			 if(basketDtl != null)
			
			{
				logger.error("basketDtl BasketId: "+basketDtl.getBasketId());
			}
			else
			{
				if(orderCapture.getSbOrder().getSelectedBasket()!=null)
				{
					logger.error("SbOrder().getSelectedBasket BasketId: "+orderCapture.getSbOrder().getSelectedBasket().getBasketId());
					basketDtl = orderCapture.getSbOrder().getSelectedBasket();
				}
				else
				{
					logger.error("SbOrder().getSelectedBasket is null");
				}				
			}			
			 */
			
			//calculate the CurrentQuotaUsed for each ProgramCd
			//quotaMap = ltsOfferService.getQuotaMapByBasket(basketDtl,quotaMap);	
			SbOrderDTO tempSbOrder = orderCapture.getSbOrder();
			List<ItemDetailDTO> tempItemDtlList = new ArrayList<ItemDetailDTO>();
			List<String> itemIdList = new ArrayList<String>();
			if(tempSbOrder != null && tempSbOrder.getSrvDtls() != null)
			{
				ServiceDetailDTO[] tempSrvDtls = tempSbOrder.getSrvDtls();
				for (int i=0; i<tempSrvDtls.length; i++)
				{
					if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
					{
						ServiceDetailLtsDTO tempSrvLtsDtl = (ServiceDetailLtsDTO) tempSrvDtls[i];
						ItemDetailLtsDTO[] tempItemDtls = tempSrvLtsDtl.getItemDtls();
						for (int j=0; tempItemDtls != null &&  j<tempItemDtls.length; j++)
						{
							if(LtsConstant.IO_IND_INSTALL.equals(tempItemDtls[j].getIoInd())){
								itemIdList.add(tempItemDtls[j].getItemId());
							}
						}
									
					}
				}
			}
			String currentLocoleStr = this.locale.getDisplayName();
			if(!LtsConstant.LOCALE_ENG.equalsIgnoreCase(currentLocoleStr))
			{
				currentLocoleStr = LtsConstant.LOCALE_CHI;
			}
			if(itemIdList.size() >0)
			{
				tempItemDtlList = ltsOfferService.getItem(itemIdList.toArray(new String[itemIdList.size()]), LtsConstant.DISPLAY_TYPE_ITEM_SELECT, currentLocoleStr, true);
			}
			for(ItemDetailDTO item: tempItemDtlList){
				item.setSelected(true);
			}			
			if(tempItemDtlList!=null && tempItemDtlList.size()>0)
			{
				quotaMap = ltsOfferService.getQuotaMapByItemList(tempItemDtlList,quotaMap);
			}	
			//
			
			// get the all OutQuota basket desc and item desc
			String tempOutQuotaBasket = ltsOfferService.getOutQuotaByBasket(basketDtl,quotaMap);
			if(StringUtils.isNotBlank(tempOutQuotaBasket)){
				outQuotaItemList.add(tempOutQuotaBasket);
			}
			if(tempItemDtlList!=null && tempItemDtlList.size()>0)
			{
				outQuotaItemList.addAll(ltsOfferService.getOutQuotaByMap(quotaMap));
			}			
			//
		
			if (outQuotaItemList.isEmpty()) {
				ltsOfferService.addBasketQuotaUsed(basketDtl);
				ltsOfferService.addItemListQuotaUsed(tempItemDtlList);
			}
			else
			{
				for(int i = 0; i < outQuotaItemList.size(); i++){
					errorMsgList.add(messageSource.getMessage("lts.offerDtl.outQuotaMsg", new Object[] {outQuotaItemList.get(i)}, locale));
				}
			}
		
		}
		
		return errorMsgList;
	}

	private List<String> selectedDnValidation(SbOrderDTO sbOrder, String pUser) {
		List<String> messageList = new ArrayList<String>();
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);		
		ServiceDetailLtsDTO pipbService = LtsSbOrderHelper.getAcqPipbService(sbOrder.getSrvDtls());
		
		if (LtsAcqSbOrderHelper.isDummyCustomer(sbOrder.getCustomers()[0].getCustNo())) {
			messageList.add(messageSource.getMessage("lts.acq.summary.customerNotAssignedPlsSuspend", new Object[] {}, this.locale));
			return messageList;
		}
		
		if (LtsAcqSbOrderHelper.isDummyAccount(sbOrder.getAccounts()[0].getAcctNo())) {
			messageList.add(messageSource.getMessage("lts.acq.summary.accountNotAssignedPlsSuspend", new Object[] {}, this.locale));
			return messageList;
		}
		
		if(ltsCommonService.isNowDrgDownTime() && (pipbService!=null || (ltsServiceDetail != null
						&& StringUtils.equals(LtsConstant.DN_SOURCE_DN_RESERVED, ltsServiceDetail.getDnSource())))) {
			messageList.add(messageSource.getMessage("lts.acq.summary.DRGdowntimeNotAllowSignOff", new Object[] {}, this.locale));
			return messageList;
		}
				
	    if (ltsServiceDetail != null) {	    	
			if (ltsProfileService.retrieveServiceProfile(ltsServiceDetail.getSrvNum(), pUser) != null) {
				messageList.add(messageSource.getMessage("lts.acq.summary.DNServiceProfileExists", new Object[] {LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum())}, this.locale));
				return messageList;
			}
	    	if (LtsConstant.DN_SOURCE_DN_RESERVED.equals(ltsServiceDetail.getDnSource())) {
		        String serviceInventSts = ltsProfileService.getServiceInventoryStatus(
				ltsServiceDetail.getSrvNum(), LtsConstant.SERVICE_TYPE_TEL);
				if (!StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, serviceInventSts) ) {
					messageList.add(messageSource.getMessage("lts.acq.summary.DNNoLongerReserved", new Object[] {LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum())}, this.locale));						
			    }
		    } else if (LtsConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())) {
		    	if(ltsAcqDnPoolService.isDnAssigned(ltsServiceDetail.getSrvNum())) {
					messageList.add(messageSource.getMessage("lts.acq.summary.DNAlreadyAssigned", new Object[] {LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum())}, this.locale));
		    	}
		    } else if (LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())) {
		    	pipbDnValidation(ltsServiceDetail, messageList);
			}
		}
	    
	    ServiceDetailLtsDTO ltsPortLaterService = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
	    if (ltsPortLaterService!=null) {	    	
	    	pipbDnValidation(ltsPortLaterService, messageList);
	    }    
	    
		ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		if (lts2ndDelServiceDetail!=null) {
			if (!LtsConstant.DN_SOURCE_DN_WORKING.equals(lts2ndDelServiceDetail.getDnSource())
					&& ltsProfileService.retrieve2ndDelServiceProfile(lts2ndDelServiceDetail.getSrvNum(), pUser) != null) {
				messageList.add(messageSource.getMessage("lts.acq.summary.DNServiceProfileExists", new Object[] {LtsSbHelper.getDisplaySrvNum(lts2ndDelServiceDetail.getSrvNum())}, this.locale));
				return messageList;
			}
			if (LtsConstant.DN_SOURCE_DN_POOL.equals(lts2ndDelServiceDetail.getDnSource())) {
				if(ltsAcqDnPoolService.isDnAssigned(lts2ndDelServiceDetail.getSrvNum())) {
					messageList.add(messageSource.getMessage("lts.acq.summary.2DELAlreadyAssigned", new Object[] {LtsSbHelper.getDisplaySrvNum(lts2ndDelServiceDetail.getSrvNum())}, this.locale));
		    	} else if (LtsConstant.DN_SOURCE_DN_PIPB.equals(lts2ndDelServiceDetail.getDnSource())) {
		    		pipbDnValidation(lts2ndDelServiceDetail, messageList);
		    	}	
			}
		}
		
		return messageList;
	}
	
	private void pipbDnValidation(ServiceDetailLtsDTO ltsServiceDetail, List<String> messageList) {
		String serviceInventSts = ltsProfileService.getServiceInventoryStatus(
				ltsServiceDetail.getSrvNum(), LtsConstant.SERVICE_TYPE_TEL);
		boolean isDrgPortOutStatus = LtsSbHelper.isDrgPortOutStatus(serviceInventSts);					
		if (StringUtils.isNotBlank(serviceInventSts)
				&& !StringUtils.equals(LtsConstant.DRG_DN_SPARE_STATUS, serviceInventSts)
				&& !StringUtils.equals(LtsConstant.DRG_DN_RESERVED_STATUS, serviceInventSts)
				&& !isDrgPortOutStatus) {
			//messageList.add("The PIPB DN " + LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()) + " is " 
			//		+ LtsConstant.DRG_DN_STATUS_MAP.get(serviceInventSts) 
			//		+ ", please cancel the order.");
			messageList.add(messageSource.getMessage("lts.acq.summary.PIPBDNPleaseCancel", new Object[] {LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()),LtsConstant.DRG_DN_STATUS_MAP.get(serviceInventSts) }, this.locale)
);
		}
	}
	
	private boolean validate(AcqOrderCaptureDTO orderCapture, LtsAcqSummaryFormDTO form, BindException errors) {

		if (form.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_SIGNOFF) {
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, form.getDistributeMode())
					|| orderCapture.isChannelDirectSales()) { //For direct sales, have to check digital signature even if dis mode = paper
				if(!orderCapture.isChannelCs() && !(orderCapture.isChannelPremier() && LtsConstant.SIGNOFF_MODE_CALLCENTER.equals(form.getSignoffMode()) )
						&& !LtsConstant.DOC_TYPE_HKBR.equals(orderCapture.getCustomerDetailProfileLtsDTO().getDocType())){
					if (!ltsSignatureService.isAllAcqSignatureSigned(orderCapture)) {
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
	
	
	private void setReferenceData(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String orderId = "";
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		if(orderCaptureDTO != null && orderCaptureDTO.getSbOrder() != null){
			orderId = orderCaptureDTO.getSbOrder().getOrderId();
		}
		
		if(acqOrderCapture.isChannelDirectSales()){
			request.setAttribute("suspendReasonList", Arrays.asList(ltsDsSuspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}else{
			request.setAttribute("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}
		request.setAttribute("captureDocIpadAppUri", getCaptureDocIpadAppUri(request));
		
		request.setAttribute("salesUserDto", salesUserDto);//for app	    
		request.setAttribute("currentSessionId", sessionId);//for app
		request.setAttribute("orderId", orderId);//for app
		request.setAttribute("statusHistory", getDsStatusHistory(orderId));
	}
	
	private String getCaptureDocIpadAppUri(HttpServletRequest request) {
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
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
	
	private boolean isValidPrepaymentSettlementDate(String pSettleDate){		
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");  
			dateFormat.setLenient(false);
			Date inputDate = dateFormat.parse(pSettleDate);			
			if(inputDate == null){
				return false;
			}
		}
		catch(Exception e){
			logger.error("The input payment settle date is not a valid date (yyyyMMddHHmmss): " + pSettleDate);
			return false;
		}
		
		return true;
	}
	
	
	public LtsAcqSummaryBaseService getLtsAcqSummaryEyeService() {
		return this.ltsAcqSummaryEyeService;
	}

	public void setLtsAcqSummaryEyeService(
			LtsAcqSummaryBaseService pLtsAcqSummaryEyeService) {
		this.ltsAcqSummaryEyeService = pLtsAcqSummaryEyeService;
	}

	public LtsAcqSummaryBaseService getLtsAcqSummaryDelService() {
		return this.ltsAcqSummaryDelService;
	}

	public void setLtsAcqSummaryDelService(LtsAcqSummaryBaseService pLtsAcqSummaryDelService) {
		this.ltsAcqSummaryDelService = pLtsAcqSummaryDelService;
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

	public LtsAcqSummaryBaseService getLtsAcqSummary2ndDelService() {
		return ltsAcqSummary2ndDelService;
	}

	public void setLtsAcqSummary2ndDelService(
			LtsAcqSummaryBaseService ltsAcqSummary2ndDelService) {
		this.ltsAcqSummary2ndDelService = ltsAcqSummary2ndDelService;
	}

	/**
	 * @return the ltsProfileService
	 */
	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	/**
	 * @param ltsProfileService the ltsProfileService to set
	 */
	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
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

	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}

	/**
	 * @return the ltsCommonService
	 */
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	/**
	 * @param ltsCommonService the ltsCommonService to set
	 */
	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	/**
	 * @return the awaitQcReasonLkupCacheService
	 */
	public CodeLkupCacheService getAwaitQcReasonLkupCacheService() {
		return awaitQcReasonLkupCacheService;
	}

	/**
	 * @param awaitQcReasonLkupCacheService the awaitQcReasonLkupCacheService to set
	 */
	public void setAwaitQcReasonLkupCacheService(
			CodeLkupCacheService awaitQcReasonLkupCacheService) {
		this.awaitQcReasonLkupCacheService = awaitQcReasonLkupCacheService;
	}

	public CodeLkupCacheService getPrepaymentCancelReasonLkupCacheService() {
		return prepaymentCancelReasonLkupCacheService;
	}

	public void setPrepaymentCancelReasonLkupCacheService(
			CodeLkupCacheService prepaymentCancelReasonLkupCacheService) {
		this.prepaymentCancelReasonLkupCacheService = prepaymentCancelReasonLkupCacheService;
	}

	public LtsDsOrderService getLtsDsOrderService() {
		return ltsDsOrderService;
	}

	public void setLtsDsOrderService(LtsDsOrderService ltsDsOrderService) {
		this.ltsDsOrderService = ltsDsOrderService;
	}

	public CodeLkupCacheService getLtsDsSuspendReasonLkupCacheService() {
		return ltsDsSuspendReasonLkupCacheService;
	}

	public void setLtsDsSuspendReasonLkupCacheService(
			CodeLkupCacheService ltsDsSuspendReasonLkupCacheService) {
		this.ltsDsSuspendReasonLkupCacheService = ltsDsSuspendReasonLkupCacheService;
	}
		
    public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
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
	
}
