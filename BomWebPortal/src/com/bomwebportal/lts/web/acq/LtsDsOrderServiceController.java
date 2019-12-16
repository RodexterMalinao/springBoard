package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ItemAttbBaseDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.acq.LtsAcqOrderService;
import com.bomwebportal.lts.service.acq.LtsDsOrderService;
import com.bomwebportal.lts.service.order.LtsDsOrderApprovalSubmitService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderInitSignoffService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderPostSubmitLtsService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.acq.LtsAcqSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsDsOrderServiceController implements Controller {
	
	OrderStatusService orderStatusService;
	LtsDsOrderService ltsDsOrderService;
	LtsAcqOrderPostSubmitLtsService ltsAcqOrderPostSubmitLtsService;
	
	private LtsSignatureService ltsSignatureService;
	private SaveSbOrderLtsService saveSbOrderLtsService;
	protected LtsAcqOrderService ltsAcqOrderService;
	private CsPortalService csPortalService;
	
	private LtsCommonService ltsCommonService;
	private LtsProfileService ltsProfileService;
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	
	private CodeLkupCacheService awaitQcReasonLkupCacheService;
	private CodeLkupCacheService ltsDsSuspendReasonLkupCacheService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService;
	private LtsAcqOrderInitSignoffService ltsAcqOrderInitSignoffService;
	
	private ReportCreationLtsService reportCreationLtsService = null;
	private CodeLkupCacheService reportSetLkupCacheService = null;
	
	private Locale locale;
	private MessageSource messageSource;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
   	public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
   		setLocale(RequestContextUtils.getLocale(request));
   		
		String orderId = request.getParameter("orderId");
		String userId = request.getParameter("userId");
		String reqType = request.getParameter("reqType");
		String wqType = request.getParameter("wqType");
		String prepayment = request.getParameter("prepay");
		String mustQc = request.getParameter("mustQc");
		String qcCallTimePeriod = request.getParameter("qcCallTimePeriod");
		String waiveQc = request.getParameter("waiveQc");
		String sbuid = request.getParameter("sbuid");
		JSONObject jsonObject = new JSONObject();
		
		logger.warn("[LtsDsOrderServiceController] Order ID: " + orderId + " Request Type: " +reqType +" User ID: " + userId + " PREPAY: " + prepayment 
				+ " Must QC: " + mustQc + " WqType: " + wqType);
		
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);	
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		String sessionUid = LtsSessionHelper.getSessionUid(request);
		
		if(sbOrder == null || !StringUtils.equals(sbOrder.getOrderId(), orderId)){// || !StringUtils.equals(sessionUid, sbuid)){
			jsonObject.put("result", "Session Timeout.");
			jsonObject.put("status", "");
		}
		
		if("SUBMIT_FOR_PREPAY".equals(reqType)){
			if(StringUtils.isNotBlank(orderId) && StringUtils.isNotBlank(userId)){			
				if(orderStatusService != null){	
					if(StringUtils.isNotBlank(prepayment) && StringUtils.equals("Y", prepayment)){
						logger.warn("[LtsDsOrderServiceController] orderStatusService.setPendingPrepaymentStatus [STARTS]");
						ServiceDetailDTO[] serviceDetails = acqOrderCapture.getSbOrder().getSrvDtls();
						BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
						
						
						List<String> sList = selectedDnValidation(sbOrder, bomSalesUser.getUsername());
						if (sList.size()>0) {
							String message = "";
							for(String msg : sList){
								message += msg + "\n";
							}							
							jsonObject.put("result", message);
							jsonObject.put("status", "");
							return new ModelAndView("ajax_ltsdsorderservice", jsonObject);
						}
						
						if(!isAllSupportDocCollected(sbOrder)){
							logger.warn("[LtsDsOrderServiceController] missing supporting document(s)");
							String errMsg = messageSource.getMessage("lts.acq.summary.plsSubmitAllSupportDoc", new Object[] {}, this.locale);
							jsonObject.put("result", errMsg);
							jsonObject.put("status", "");
							return new ModelAndView("ajax_ltsdsorderservice", jsonObject);
						}
						
						sbOrder.setSignatures(ltsSignatureService.getOrderSignatures(sbOrder.getOrderId()));
						if(sbOrder != null && sbOrder.getLtsDsOrderInfo() != null){
							LtsDsOrderInfoDTO info = sbOrder.getLtsDsOrderInfo();
							info.setWaiveQcCd(waiveQc);
							info.setQcCallTime(qcCallTimePeriod);							
						}

/*						String emailResult = isAwaitQcStatus(sbOrder)? ltsAcqOrderService.signOffOrderQC(sbOrder, bomSalesUser)
								: ltsAcqOrderService.signOffOrder(sbOrder, bomSalesUser);*/

						// ======================================
						// GENERATE PAYMENT SLIP on Tomcat Server
						// ======================================
						generatePaymentSlip(acqOrderCapture);
						// ======================================						
						ltsAcqOrderInitSignoffService.setSignOffDate(sbOrder);
						String emailResult = ltsAcqOrderInitSignoffService.saveFormsAndEmailAndSms(sbOrder, userId);//ltsAcqOrderService.signOffOrder(sbOrder, bomSalesUser);
						ltsAcqOrderInitSignoffService.checkAndRegCspForSbOrder(sbOrder, userId);
						ltsAcqOrderInitSignoffService.regIguardCarecashForSbOrder(sbOrder, userId);
						
						request.getSession().setAttribute("emailResult", emailResult);
						sbOrder = ltsAcqOrderService.retrieveOrder(acqOrderCapture.getSbOrder().getOrderId());
						acqOrderCapture.setSbOrder(sbOrder);
						acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
						
						for (int i=0; serviceDetails!=null && i<serviceDetails.length; ++i) {
							// update order status = W
							orderStatusService.setPendingPrepaymentStatus(acqOrderCapture.getSbOrder().getOrderId(), serviceDetails[i].getDtlId(), bomSalesUser.getUsername(), wqType);
						}	
						logger.warn("[LtsDsOrderServiceController] orderStatusService.setPendingPrepaymentStatus [COMPLETED]");
												
						logger.warn("[LtsDsOrderServiceController] ltsDsOrderService.updateDsInfo [STARTS]");
						if(ltsDsOrderService != null){
							logger.warn("[LtsDsOrderServiceController] ltsDsOrderService.updateDsInfo [Processing...]");
							ltsDsOrderService.updateDsInfo(orderId, qcCallTimePeriod, waiveQc, bomSalesUser.getUsername());
						}
						logger.warn("[LtsDsOrderServiceController] ltsDsOrderService.updateDsInfo [COMPLETED]");
						
						createAndSubmitAwaitPrepaymentWorkQueue(acqOrderCapture.getSbOrder(), userId);
						
						OrderStatusDTO info = getAwaitPrepaymentSignoffInfo(sbOrder);
						
						jsonObject.put("result", messageSource.getMessage("lts.acq.summary.submitPendPrepay", new Object[] {}, this.locale));
						jsonObject.put("statusReasonCd", info.getReasonCd());
						jsonObject.put("statusDate", info.getStatusDate());
						jsonObject.put("statusHistory", generateStatusHistoryInHtmlFormat(acqOrderCapture));
						jsonObject.put("status", "W");

					}
				}
				else{
					jsonObject.put("result", messageSource.getMessage("lts.acq.summary.orderStatusSrvNotAvail", new Object[] {}, this.locale));
					jsonObject.put("status", "");
				}
			}
			else{
				jsonObject.put("result", messageSource.getMessage("lts.acq.summary.orderInfoEmpty", new Object[] {}, this.locale));
				jsonObject.put("status", "");
			}
		}else if("RESEND_DS_NAME_APR_WQ".equals(reqType)){
			String nameNotMatchStatus = sbOrder.getLtsDsOrderInfo().getNameMismatchStatus();
			if(sbOrder.getLtsDsOrderInfo() != null){
				if(LtsConstant.NAME_MISMATCH_APR_CD_PENDING_APPROVAL.equals(nameNotMatchStatus)
					&& !Arrays.asList(LtsConstant.NAME_MISMATCH_AFTER_APPROVAL).contains(nameNotMatchStatus)){
					boolean resubmitedInd = ltsDsOrderApprovalSubmitService.submitOrderForCustNameNotMatch(sbOrder, userId);
					if(resubmitedInd){
						jsonObject.put("result", messageSource.getMessage("lts.acq.summary.approvalWQResubmit", new Object[] {}, this.locale));
					}else{
						jsonObject.put("result", messageSource.getMessage("lts.acq.summary.WQResubmitFailTryAgain", new Object[] {}, this.locale));
					}
					jsonObject.put("status", "");
				}else{
					jsonObject.put("result", messageSource.getMessage("lts.acq.summary.WQResubmitFailStatus", new Object[] {}, this.locale) + nameNotMatchStatus + ".");
					jsonObject.put("status", "");
				}
			}else{
				jsonObject.put("result", messageSource.getMessage("lts.acq.summary.noServiceAvailable", new Object[] {}, this.locale));
				jsonObject.put("status", "");
			}
		}
		else{
			jsonObject.put("result", messageSource.getMessage("lts.acq.summary.noServiceAvailable", new Object[] {}, this.locale));
			jsonObject.put("status", "");
		}
		
		return new ModelAndView("ajax_ltsdsorderservice", jsonObject);
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
   	
   	private String generateStatusHistoryInHtmlFormat(AcqOrderCaptureDTO acqOrderCapture){
   		if(acqOrderCapture != null && acqOrderCapture.getSbOrder() != null){
   			SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
	   		ServiceDetailLtsDTO eyeLtsSrv = LtsSbOrderHelper.getLtsEyeService(sbOrder);
			ServiceDetailLtsDTO delLtsSrv = LtsSbOrderHelper.getDelServices(sbOrder);
			OrderStatusDTO statusPrepaymentHistDisplay = null;
			if (acqOrderCapture.isEyeOrder()){
				statusPrepaymentHistDisplay = setStatus(sbOrder, this.orderStatusService.getStatusHistory(sbOrder.getOrderId(), eyeLtsSrv.getDtlId()));
			}
			else{
				statusPrepaymentHistDisplay = setStatus(sbOrder, this.orderStatusService.getStatusHistory(sbOrder.getOrderId(), delLtsSrv.getDtlId()));
			}
			
			// Order ${statusHist.sbStatus} on ${statusHist.statusDate} with reason ${statusHist.reasonCd}<BR>
			if(statusPrepaymentHistDisplay != null){
				return "<span class=\"title_a\">" + messageSource.getMessage("lts.acq.summary.orderSignoffOn", new Object[] {}, this.locale) + statusPrepaymentHistDisplay.getStatusDate() + "</span>";
			}
   		}
		return null;
   	}
   	
   	private OrderStatusDTO setStatus(SbOrderDTO pSbOrder, OrderStatusDTO[] statusHists) {
		for (int i=0; statusHists!=null && i<statusHists.length; ++i) {
			if (StringUtils.equals(LtsConstant.ORDER_STATUS_AWAIT_PREPAYMENT, statusHists[i].getSbStatus())
					&& StringUtils.equals(LtsConstant.ORDER_STATUS_REASON_AWAIT_PREPAY, statusHists[i].getReasonCd())) {
				statusHists[i].setSbStatus("submit");
				return statusHists[i];
			}
		}
		return null;
	}
   	
   	private void generatePaymentSlip(AcqOrderCaptureDTO pAcqOrderCapture){
   		if(pAcqOrderCapture == null || pAcqOrderCapture.getSbOrder() == null){
   			return;
   		}
   		
   		SbOrderDTO sbOrder = pAcqOrderCapture.getSbOrder();
   		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob(LtsBackendConstant.LOB_LTS);
		rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
		rptSet.setRptSet("PRINT_PS");
		rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
		
		Map<String,Object> inputMap = new HashMap<String,Object>();
		inputMap.put(LtsConstant.RPT_KEY_SBORDER, sbOrder);
		inputMap.put(LtsConstant.RPT_KEY_LOB, LtsConstant.LOB_LTS);
		
		LtsAcqPaymentMethodFormDTO prepayForm = pAcqOrderCapture.getLtsAcqPaymentMethodFormDTO();
		if(prepayForm != null){
			for(PaymentMethodDtl prepayDtl : prepayForm.getPaymentMethodDtlList()){
				if(prepayDtl.getAutopayPrePayItem() != null){
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ACCT, prepayDtl.getAcctNum());
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ITEM, prepayDtl.getAutopayPrePayItem());
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_SERVICE, pAcqOrderCapture.getSelectedBasket());
					break;
				}
				if(prepayDtl.getCashPrePayItem() != null){
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ACCT, prepayDtl.getAcctNum());
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ITEM, prepayDtl.getCashPrePayItem());
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_SERVICE, pAcqOrderCapture.getSelectedBasket());
					break;
				}
				if(prepayDtl.getCreditCardPrePayItem() != null){
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ACCT, prepayDtl.getAcctNum());
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ITEM, prepayDtl.getCreditCardPrePayItem());
					inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_SERVICE, pAcqOrderCapture.getSelectedBasket());
					break;
				}
			}
		}
		
		this.reportCreationLtsService.generateReport(rptSet, inputMap);
   	}
   	
   	private String getAttbValueByAttbType(ItemAttbBaseDTO[] attbArray, String attbType){
		for(ItemAttbBaseDTO attb: attbArray){
			if(attbType.equals(attb.getInputFormat())){
				return attb.getAttbValue();
			}
		}		
		return null;
	}
   	
   	private String getAttbValueByAttbTypeAndKey(ItemAttbBaseDTO[] attbArray, String attbType, String attbKey){
		for(ItemAttbBaseDTO attb: attbArray){
			if(attbType.equals(attb.getInputFormat()) && attbKey.equals(attb.getAttbInfoKey())){
				return attb.getAttbValue();
			}
		}		
		return null;
	}

   	private void createAndSubmitAwaitPrepaymentWorkQueue(SbOrderDTO pSbOrder, String pUserId){
   		logger.warn("[LtsDsOrderServiceController] createAndSubmitAwaitPrepaymentWorkQueue [STARTS]");
   		if(ltsAcqOrderPostSubmitLtsService != null){
   			this.ltsAcqOrderPostSubmitLtsService.submitToDsPrepaymentWorkQueue(pSbOrder, pUserId);
   		}
   		else{
   			logger.warn("[LtsDsOrderServiceController] ltsAcqOrderPostSubmitLtsService is null");
   		}
   		logger.warn("[LtsDsOrderServiceController] createAndSubmitAwaitPrepaymentWorkQueue [END]");
   	}
   	
   	private boolean isAwaitQcStatus(SbOrderDTO sbOrder) {
    	OrderStatusDTO[] status = orderStatusService.getStatus(sbOrder.getOrderId());
    	for (int i=0; status!=null && i<status.length; ++i) {
 		   if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_AWAIT_QC, status[i].getSbStatus())) {
 			   return true;
 		   }
        }
 	    return false;
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
				messageList.add(messageSource.getMessage("lts.acq.summary.DNServiceProfileExists", new Object[] { LtsSbHelper.getDisplaySrvNum(lts2ndDelServiceDetail.getSrvNum())}, this.locale));
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
			messageList.add(messageSource.getMessage("lts.acq.summary.PIPBDNPleaseCancel", new Object[] {LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()), LtsConstant.DRG_DN_STATUS_MAP.get(serviceInventSts)}, this.locale));
		}
	}
   	
   	private OrderStatusDTO getAwaitPrepaymentSignoffInfo(SbOrderDTO pSbOrder) {		
		OrderStatusDTO[] statusInfo = this.orderStatusService.getStatus(pSbOrder.getOrderId());
		for (int i=0; statusInfo!=null && i<statusInfo.length; ++i) {
			if (StringUtils.equals(LtsConstant.ORDER_STATUS_AWAIT_PREPAYMENT, statusInfo[i].getSbStatus())
					&& StringUtils.equals(LtsConstant.ORDER_STATUS_REASON_AWAIT_PREPAY, statusInfo[i].getReasonCd())) {				
				return statusInfo[i];
			} 
		}
		return null;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public LtsDsOrderService getLtsDsOrderService() {
		return ltsDsOrderService;
	}

	public void setLtsDsOrderService(LtsDsOrderService ltsDsOrderService) {
		this.ltsDsOrderService = ltsDsOrderService;
	}

	public LtsAcqOrderPostSubmitLtsService getLtsAcqOrderPostSubmitLtsService() {
		return ltsAcqOrderPostSubmitLtsService;
	}

	public void setLtsAcqOrderPostSubmitLtsService(
			LtsAcqOrderPostSubmitLtsService ltsAcqOrderPostSubmitLtsService) {
		this.ltsAcqOrderPostSubmitLtsService = ltsAcqOrderPostSubmitLtsService;
	}

	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public LtsAcqOrderService getLtsAcqOrderService() {
		return ltsAcqOrderService;
	}

	public void setLtsAcqOrderService(LtsAcqOrderService ltsAcqOrderService) {
		this.ltsAcqOrderService = ltsAcqOrderService;
	}

	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}

	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}

	public CodeLkupCacheService getAwaitQcReasonLkupCacheService() {
		return awaitQcReasonLkupCacheService;
	}

	public void setAwaitQcReasonLkupCacheService(
			CodeLkupCacheService awaitQcReasonLkupCacheService) {
		this.awaitQcReasonLkupCacheService = awaitQcReasonLkupCacheService;
	}

	public CodeLkupCacheService getLtsDsSuspendReasonLkupCacheService() {
		return ltsDsSuspendReasonLkupCacheService;
	}

	public void setLtsDsSuspendReasonLkupCacheService(
			CodeLkupCacheService ltsDsSuspendReasonLkupCacheService) {
		this.ltsDsSuspendReasonLkupCacheService = ltsDsSuspendReasonLkupCacheService;
	}

	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}

	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}

	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
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

	public LtsDsOrderApprovalSubmitService getLtsDsOrderApprovalSubmitService() {
		return ltsDsOrderApprovalSubmitService;
	}

	public void setLtsDsOrderApprovalSubmitService(
			LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService) {
		this.ltsDsOrderApprovalSubmitService = ltsDsOrderApprovalSubmitService;
	}

	public LtsAcqOrderInitSignoffService getLtsAcqOrderInitSignoffService() {
		return ltsAcqOrderInitSignoffService;
	}

	public void setLtsAcqOrderInitSignoffService(
			LtsAcqOrderInitSignoffService ltsAcqOrderInitSignoffService) {
		this.ltsAcqOrderInitSignoffService = ltsAcqOrderInitSignoffService;
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
