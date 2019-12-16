package com.bomwebportal.lts.service.order.acq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsClubMembershipService;
import com.bomwebportal.lts.service.LtsEmailService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsRemarkService;
import com.bomwebportal.lts.service.PipbActvLtsService;
import com.bomwebportal.lts.service.acq.LtsAcq0060DetailService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.service.order.PipbOrderSignoffLtsServiceImpl;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.service.sms.LtsSmsService;
import com.bomwebportal.lts.util.LtsActvBackendConstant.ActvAction;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;

public class LtsAcqOrderPostSubmitLtsServiceImpl extends PipbOrderSignoffLtsServiceImpl implements LtsAcqOrderPostSubmitLtsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
		
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	private LtsAcq0060DetailService ltsAcq0060DetailService;
	private ServiceProfileLtsService serviceProfileLtsService;
	private LtsProfileService ltsProfileService;
	private LtsRemarkService ltsRemarkService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	private LtsSmsService ltsSmsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsEmailService ltsEmailService;
    private LtsPaymentService ltsPaymentService;	
    private PipbActvLtsService pipbActvLtsService;
    private LtsClubMembershipService ltsClubMembershipService;
	
	@Transactional
	public String signOffNewAcquisitionOrder(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser) {
		try {			
			if (!verifySignoff(pSbOrder) && !isAwaitPrePaymentStatus(pSbOrder) && !isMustQcStatus(pSbOrder)) {
				return null; // check order status = LtsBackendConstant.ORDER_STATUS_SUSPENDED
				             // or order status = LtsBackendConstant.ORDER_STATUS_AWAIT_PREPAYMENT
			}			
			
			String pUser = bomSalesUser.getUsername();
			String pShopCd = bomSalesUser.getShopCd();
			
			if(isAwaitPrePaymentStatus(pSbOrder) || isMustQcStatus(pSbOrder)){
				this.cleanupExistingDsWorkQueueInTheService(pSbOrder);
				this.clearFollowupWorkQueue(pSbOrder, bomSalesUser.getUsername());
				this.getServiceActions(pSbOrder, bomSalesUser.getUsername());
				submitDsAllWorkQueue(pSbOrder, bomSalesUser);
				
				if (!validatePipbDnExists(pSbOrder, pUser)) {
					createDnFollowup(ActvAction.CREATE_DN, null, pSbOrder.getOrderId(), pUser);
					return pauseOrder(pSbOrder, pUser);
				}
					
				setDnAssignedFromDnPool(pSbOrder);
				List<String> errList = changeDnToPreAssigned(pSbOrder, pUser);
				if (errList!=null && errList.size()>0) {
					if (errList.get(0).contains(LtsConstant.WS_ERROR_MESSAGE_NON_2N_DN)) {
						return errList.get(0);
					} else {
						createDnFollowup(ActvAction.DN_FOLLOWUP, errList, pSbOrder.getOrderId(), pUser);
						return pauseOrder(pSbOrder, pUser);
					}
				}
				
				updateServiceStatusAfterSignoff(pSbOrder, bomSalesUser.getUsername()); // update table BOMWEB_ORDER_LATEST_STATUS
				return null;
			}		
			
			boolean containsPrepaymentItem = false;
			if(pSbOrder != null && pSbOrder.getPrepay() != null){ 
				for(PrepayLtsDTO info : pSbOrder.getPrepay()){
					if(!"C".equals(info.getPaymentStatus())){	//Exempt Credit Card case 
						containsPrepaymentItem = true;
					}
				}
			}
			
			if (LtsSbOrderHelper.isOrderDS(pSbOrder)) {
				 String reaCd = LtsSbOrderHelper.checkMustQC(pSbOrder);
				 if(reaCd!=null && !containsPrepaymentItem) { // must QC cases
					 return mustQCCases(pSbOrder, pUser, reaCd);
				 }
				 
				 if(!containsPrepaymentItem){
					 if (!validatePipbDnExists(pSbOrder, pUser)) {
						createDnFollowup(ActvAction.CREATE_DN, null, pSbOrder.getOrderId(), pUser);
						return pauseOrder(pSbOrder, pUser);
					}
						
					setDnAssignedFromDnPool(pSbOrder);
					List<String> errList = changeDnToPreAssigned(pSbOrder, pUser);
					if (errList!=null && errList.size()>0) {
						if (errList.get(0).contains(LtsConstant.WS_ERROR_MESSAGE_NON_2N_DN)) {
							return errList.get(0);
						} else {
							createDnFollowup(ActvAction.DN_FOLLOWUP, errList, pSbOrder.getOrderId(), pUser);
							return pauseOrder(pSbOrder, pUser);
						}
					}
				 }
			}
			else {
				if (!validatePipbDnExists(pSbOrder, pUser)) {
					createDnFollowup(ActvAction.CREATE_DN, null, pSbOrder.getOrderId(), pUser);
					return pauseOrder(pSbOrder, pUser);
				}
				
				setDnAssignedFromDnPool(pSbOrder);
				List<String> errList = changeDnToPreAssigned(pSbOrder, pUser);
				if (errList!=null && errList.size()>0) {
					if (errList.get(0).contains(LtsConstant.WS_ERROR_MESSAGE_NON_2N_DN)) {
						return errList.get(0);
					} else {
						createDnFollowup(ActvAction.DN_FOLLOWUP, errList, pSbOrder.getOrderId(), pUser);
						return pauseOrder(pSbOrder, pUser);
					}
				}
			}
			
			
			initializeSignoff(pSbOrder, pUser); // setPendingSignoffStatus - LtsBackendConstant.ORDER_STATUS_PENDING_SIGNOFF
			preSubmitSignoff(pSbOrder, pUser); // generate remarks, get service action			
			
			pSbOrder.setPrepaymentSlipInd(containsPrepaymentItem?"Y":"N");
			
			boolean mustQc = false;
			boolean updateSignoffStatus = false; 
			if(pSbOrder != null && pSbOrder.getLtsDsOrderInfo() != null && "Y".equals(pSbOrder.getLtsDsOrderInfo().getMustQc())){
				mustQc = true;
			}
			
			if(!LtsSbOrderHelper.isOrderDS(pSbOrder) || (!containsPrepaymentItem && !mustQc)){
				updateSignoffStatus = true;
				determineWorkQueueNatureForSignoff(pSbOrder); // determine work queue and submit
				setWqNatureRemark(pSbOrder);
				submitToWorkQueue(pSbOrder, pUser, pShopCd);
				//submitDsWorkQueue(pSbOrder, bomSalesUser.getUsername());
			}
			
			updateInvPreassgnJunctionMsg(pSbOrder, pUser);
			checkAndTerminateExistingMobTel(pSbOrder, pUser); // check any MOB/TEL with same DN as the primary DN, terminate if any
			return postSubmitSignoff(pSbOrder, pUser, updateSignoffStatus);
		} catch (Exception e) {
			logger.error("Exception caught in Signoff Order " + pSbOrder.getOrderId() + " - " + e.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "Exception caught in Signoff Order";
		}
	}
	
	private void cleanupExistingDsWorkQueueInTheService(SbOrderDTO pSbOrder){		
		if(pSbOrder != null && pSbOrder.getSrvDtls() != null){
			ServiceDetailDTO[] srvDtls = pSbOrder.getSrvDtls();			
			for (int i = 0; srvDtls != null && i < srvDtls.length; ++i) {
				srvDtls[i].setSrvActions(new ServiceActionTypeDTO[]{});
			}
		}
	}
	
	@Transactional
	public void submitDsAllWorkQueue(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser) {
		determineWorkQueueNatureForSignoff(pSbOrder); // determine work queue and submit
		setWqNatureRemark(pSbOrder);
		submitToWorkQueue(pSbOrder, bomSalesUser.getUsername(), bomSalesUser.getShopCd());
		//submitDsWorkQueue(pSbOrder, bomSalesUser.getUsername());
	}
	
	private void preSubmitSignoff(SbOrderDTO sbOrder, String userId) throws Exception {		
		generateRemarks(sbOrder, userId);
		clearFollowupWorkQueue(sbOrder, userId);
		getServiceActions(sbOrder, userId);		
		
		logger.warn("preSubmitSignoff: createDsWaiveQcActivity [START]");
		if(sbOrder.getLtsDsOrderInfo() != null){
			LtsDsOrderInfoDTO dsInfo = sbOrder.getLtsDsOrderInfo();
			if(dsInfo == null){
				logger.warn("preSubmitSignoff: LtsDsOrderInfoDTO is null...");
			}
			
			if(dsInfo != null && StringUtils.isNotBlank(dsInfo.getWaiveQcCd())){
				logger.warn("preSubmitSignoff: createDsWaiveQcActivity processing...");
				createDsWaiveQcActivity(sbOrder.getOrderId(), userId);
			}	
			
			if(StringUtils.isBlank(dsInfo.getWaiveQcCd())){
				logger.warn("preSubmitSignoff: No Waive QC Code found");
			}
			
		}
		logger.warn("preSubmitSignoff: createDsWaiveQcActivity [END]");
		
		updateSuspendBomOrder(sbOrder, userId);
	}
	
	private String postSubmitSignoff(SbOrderDTO sbOrder, String userId, boolean pUpdateSignoffStatus) {
		createImsEyeOrder(sbOrder, userId); // create IMS order if needed
		
		if(pUpdateSignoffStatus){
			updateServiceStatusAfterSignoff(sbOrder, userId); // update table BOMWEB_ORDER_LATEST_STATUS
		}
		
		setSignOffDate(sbOrder);
		String result = saveFormsAndEmailAndSms(sbOrder, userId);
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		if(ltsServiceDetail != null && 
				(LtsConstant.DN_SOURCE_DN_RESERVED.equals(ltsServiceDetail.getDnSource()) || LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource()))){
			pipbActvLtsService.insertDummyRecordToBInvPreassgn(sbOrder);
		}
		
		ltsClubMembershipService.earnClubPointsAndUpdateItems(sbOrder, userId);
		
		//removeOrderSignature();
		if(EmailReqResult.SUCCESS.toString().equals(result) || result == null){
			return null;
		}
		
		return "Email result: " + result;
	}
	
	public void setPostSubmitSignoffStatus(SbOrderDTO sbOrder, String userId) {
		updateServiceStatusAfterSignoff(sbOrder, userId); // update table BOMWEB_ORDER_LATEST_STATUS
	}
	
	private String pauseOrder(SbOrderDTO sbOrder, String userId) throws Exception {
		// forced to WQ, put sign-off on hold, update order status = Pending(P)
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		initializeSignoff(sbOrder, userId);
		orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), ltsServiceDetail.getDtlId(), userId, LtsConstant.WQ_TYPE_PARTIAL);
		generateRemarks(sbOrder, userId);
		checkAndTerminateExistingMobTel(sbOrder, userId);
		setSignOffDate(sbOrder);
		String result = saveFormsAndEmailAndSms(sbOrder, userId);		
		return result;
	}
	
	private String mustQCCases(SbOrderDTO sbOrder, String userId, String reaCd) throws Exception {
		// must QC cases
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		initializeSignoff(sbOrder, userId);
		// update order status = Await QC(Q) and the reason code
		//orderStatusService.setAwaitQCStatus(sbOrder.getOrderId(), ltsServiceDetail.getDtlId(), userId, reaCd);
		updateServiceStatusForMustQcCase(sbOrder, userId);
		// update must QC indicator = Y
		orderDetailService.updateMustQCInd(sbOrder.getOrderId(), "Y", userId);
		generateRemarks(sbOrder, userId);
		checkAndTerminateExistingMobTel(sbOrder, userId);
		setSignOffDate(sbOrder);
		String result = saveFormsAndEmailAndSms(sbOrder, userId);
		return result;
	}	
	
	private boolean setDnAssignedFromDnPool(SbOrderDTO sbOrder) {
		// primary del
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		if (ltsServiceDetail != null
				&& LtsConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())) {
			ltsAcqDnPoolService.assignDn(ltsServiceDetail.getSrvNum());
		}
		// second del
		ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		if (lts2ndDelServiceDetail != null
				&& LtsConstant.DN_SOURCE_DN_POOL.equals(lts2ndDelServiceDetail.getDnSource())) {
			ltsAcqDnPoolService.assignDn(lts2ndDelServiceDetail.getSrvNum());
		}
		return true;
	}
	
	private void checkAndTerminateExistingMobTel(SbOrderDTO sbOrder, String userId) {
		// primary del
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		if (ltsServiceDetail != null) {
			ServiceDetailProfileLtsDTO serviceProfile = serviceProfileLtsService.getServiceProfileBySrvNum(
						ltsServiceDetail.getSrvNum(), LtsConstant.SERVICE_TYPE_MOB);
			if (serviceProfile != null && LtsConstant.DAT_CD_TEL.equals(serviceProfile.getDatCd()) 
					&& StringUtils.isEmpty(serviceProfile.getEffEndDate())) {
				ltsAcq0060DetailService.terminate0060DetailService(sbOrder, 
						serviceProfile.getSrvNum(), serviceProfile.getSrvType(), serviceProfile.getDatCd(), userId);
			}
		}
	}
	
	protected void saveForms(SbOrderDTO sbOrder, String action) {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(LtsConstant.RPT_KEY_SBORDER, sbOrder);
		inputMap.put(LtsConstant.RPT_KEY_LOB, LtsConstant.LOB_LTS);		
		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob(LtsConstant.LOB_LTS);
		rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
		rptSet.setRptSet(action);
		reportCreationLtsService.generateReport((ReportSetDTO)reportSetLkupCacheService.get(rptSet.getLkupKey()), inputMap);
	}
	
	protected void generateRemarks(SbOrderDTO sbOrder, String userId) {
		ltsRemarkService.generateOrderRemark(sbOrder, userId);
		ltsRemarkService.generateCustomerRemark(sbOrder, userId);
	}
		
	protected String saveFormsAndEmailAndSms(SbOrderDTO sbOrder, String userId) {
		saveAfForm(sbOrder);
		sendSignOffSms(sbOrder, userId);
		createLetterPrintReq(sbOrder, userId);
		return saveAndSendApplicationForm(sbOrder, userId);
	}
	
	protected void saveAfForm(SbOrderDTO sbOrder) {		
        try {
        	saveForms(sbOrder, ReportCreationLtsService.RPT_SET_SIGNOFF_AF);
	    } catch (Exception e) {
	    	logger.error("Failed to save AF when sign off order");
	    }
	}
	
	private String createLetterPrintReq(SbOrderDTO sbOrder, String userId) {
		if (LtsConstant.DISTRIBUTE_MODE_PAPER.equals(sbOrder.getDisMode())) {
			return ltsEmailService.createLetterPrintReq(sbOrder, userId);	
		}
		return null;
	}
	
    protected void sendSignOffSms(SbOrderDTO sbOrder, String userId) {
		if (LtsConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode())
				&& StringUtils.isNotBlank(sbOrder.getSmsNo())) {
			ltsSmsService.sendSignOffSms(sbOrder, userId);
		}
	}
    
    protected String saveAndSendApplicationForm(SbOrderDTO sbOrder, String userId) {
		List<AllOrdDocLtsDTO> generatedFormList = ltsOrderDocumentService.getGeneratedFormList(sbOrder);
		ltsOrderDocumentService.saveGeneratedForm(generatedFormList, userId);
		return sendSignOffEmail(sbOrder, userId);
	}
	
    protected String sendSignOffEmail(SbOrderDTO sbOrder, String userId) {		
		if (LtsConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode()) 
				&& StringUtils.isNotBlank(sbOrder.getEsigEmailAddr())) {
			return ltsEmailService.sendSignOffEmail(sbOrder, sbOrder.getEsigEmailAddr(), userId);	
		}
    	return null;
	}
        
    protected void setSignOffDate(SbOrderDTO sbOrder) {
    	sbOrder.setSignOffDate(orderDetailService.updateSignoffDate(sbOrder.getOrderId())); // update bomweb_order.sign_off_date 
    	String termDate = this.orderDetailService.updatePaymentTermDate(sbOrder.getOrderId());
    	this.setPaymentTermDate(sbOrder, termDate);
	}
    
    protected boolean isAwaitPrePaymentStatus(SbOrderDTO sbOrder) {
    	OrderStatusDTO[] status = orderStatusService.getStatus(sbOrder.getOrderId());
    	for (int i=0; status!=null && i<status.length; ++i) {
 		   if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_AWAIT_PREPAYMENT, status[i].getSbStatus())
 				   && StringUtils.equals(LtsBackendConstant.ORDER_STATUS_REASON_AWAIT_PREPAY, status[i].getReasonCd())) {
 			   return true;
 		   }
        }
 	    return false;
    }
    
    protected boolean isMustQcStatus(SbOrderDTO sbOrder) {
    	OrderStatusDTO[] status = orderStatusService.getStatus(sbOrder.getOrderId());
    	for (int i=0; status!=null && i<status.length; ++i) {
 		   if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_AWAIT_QC, status[i].getSbStatus())) {
 			   return true;
 		   }
        }
 	    return false;
    }
    
    private ServiceDetailDTO[] getDirectSalesServiceActions(SbOrderDTO sbOrder) {		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();		
		List<ServiceDetailDTO> srvDtlList = new ArrayList<ServiceDetailDTO>();		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			List<ServiceActionTypeDTO> srvAction = new ArrayList<ServiceActionTypeDTO>();	
			if(srvDtls[i].getSrvActions() != null){
				for(ServiceActionTypeDTO act : srvDtls[i].getSrvActions()){
					logger.warn("(getDirectSalesServiceActions) Existing service Actions (Nature ID): " + act.getWqNatureId());
					srvAction.add(act);
				}
			}
			logger.warn("(getDirectSalesServiceActions) Service Number: " + srvDtls[i].getSrvNum());
			ServiceActionTypeDTO[] dsActions = this.serviceActionTypeLookupService.getDirectSalesServiceActionType(sbOrder);
			for(ServiceActionTypeDTO act : dsActions){
				logger.warn("(getDirectSalesServiceActions) Action to be added (Nature Id): " + act.getWqNatureId());
				srvAction.add(act);
			}
			
			srvDtls[i].setSrvActions(srvAction.toArray(new ServiceActionTypeDTO[srvAction.size()]));
			srvDtlList.add(srvDtls[i]);
		}
		return srvDtlList.toArray(new ServiceDetailDTO[srvDtlList.size()]);
	}
    
    public void submitToDsPrepaymentWorkQueue(SbOrderDTO pSbOrder, String userId) {
		ServiceDetailDTO[] wqSrvs = this.getDsPrepaymentServiceActions(pSbOrder);
		this.setWqNatureRemark(pSbOrder);
		
		if (wqSrvs.length > 0) {
			logger.warn("(submitToDsPrepaymentWorkQueue) " + wqSrvs.length + " WQ service(s) being submitted! ");
			this.workQueueSubmissionService.submitToDsPrepaymentWorkQueue(pSbOrder, wqSrvs, userId);
		}
		else{
			logger.warn("(submitToDsPrepaymentWorkQueue) No available WQ service being found! ");
		}
	}
    
    public void submitDsWorkQueue(SbOrderDTO pSbOrder, String userId) {
    	ServiceDetailDTO[] wqSrvs = this.getDsServiceActions(pSbOrder);
		this.setWqNatureRemark(pSbOrder);
		
		if (wqSrvs.length > 0) {
			logger.warn("(submitDsWorkQueue) " + wqSrvs.length + " WQ service(s) being submitted! ");
			this.workQueueSubmissionService.submitDsWorkQueue(pSbOrder, wqSrvs, userId);
		}
		else{
			logger.warn("(submitDsWorkQueue) No available WQ service being found! ");
		}
	}
    
	public void submitDrgDowntimeWorkQueue(SbOrderDTO pSbOrder, String userId) {
    	ServiceDetailDTO[] wqSrvs = this.getDsDrgDowntimeServiceActions(pSbOrder);
		this.setWqNatureRemark(pSbOrder);
		
		if (wqSrvs.length > 0) {
			logger.warn("(submitDrgDowntimeWorkQueue) " + wqSrvs.length + " WQ service(s) being submitted! ");
			this.workQueueSubmissionService.submitDsWorkQueue(pSbOrder, wqSrvs, userId);
		}
		else{
			logger.warn("(submitDrgDowntimeWorkQueue) No available WQ service being found! ");
		}
	}
    
	private ServiceDetailDTO[] getDsServiceActions(SbOrderDTO sbOrder) {		
		List<ServiceDetailDTO> srvDtlList = new ArrayList<ServiceDetailDTO>();
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		ltsCoreService.setSrvActions(this.serviceActionTypeLookupService.getDirectSalesServiceActionType(sbOrder));
		srvDtlList.add(ltsCoreService);		
		return srvDtlList.toArray(new ServiceDetailDTO[srvDtlList.size()]);
	}
	
    private ServiceDetailDTO[] getDsDrgDowntimeServiceActions(SbOrderDTO sbOrder) {		
		List<ServiceDetailDTO> srvDtlList = new ArrayList<ServiceDetailDTO>();
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		ltsCoreService.setSrvActions(this.serviceActionTypeLookupService.getDsDrgdwntimeActionType(sbOrder));
		srvDtlList.add(ltsCoreService);		
		return srvDtlList.toArray(new ServiceDetailDTO[srvDtlList.size()]);
	}
    
    private ServiceDetailDTO[] getDsPrepaymentServiceActions(SbOrderDTO sbOrder) {		
		//ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();		// Only 1 Prepayment alert work queue is required
		List<ServiceDetailDTO> srvDtlList = new ArrayList<ServiceDetailDTO>();
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
/*		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			logger.warn("(getDsPrepaymentServiceActions) Service Number: " + srvDtls[i].getSrvNum());
			srvDtls[i].setSrvActions(this.serviceActionTypeLookupService.getDirectSalesPrepaymentServiceActionType(sbOrder));
			srvDtlList.add(srvDtls[i]);
		}*/
		ltsCoreService.setSrvActions(this.serviceActionTypeLookupService.getDirectSalesPrepaymentServiceActionType(sbOrder));
		srvDtlList.add(ltsCoreService);
		
		return srvDtlList.toArray(new ServiceDetailDTO[srvDtlList.size()]);
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

	/**
	 * @return the ltsAcq0060DetailService
	 */
	public LtsAcq0060DetailService getLtsAcq0060DetailService() {
		return ltsAcq0060DetailService;
	}

	/**
	 * @param ltsAcq0060DetailService the ltsAcq0060DetailService to set
	 */
	public void setLtsAcq0060DetailService(
			LtsAcq0060DetailService ltsAcq0060DetailService) {
		this.ltsAcq0060DetailService = ltsAcq0060DetailService;
	}

	/**
	 * @return the serviceProfileLtsService
	 */
	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	/**
	 * @param serviceProfileLtsService the serviceProfileLtsService to set
	 */
	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
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
	 * @return the ltsRemarkService
	 */
	public LtsRemarkService getLtsRemarkService() {
		return ltsRemarkService;
	}

	/**
	 * @param ltsRemarkService the ltsRemarkService to set
	 */
	public void setLtsRemarkService(LtsRemarkService ltsRemarkService) {
		this.ltsRemarkService = ltsRemarkService;
	}

	/**
	 * @return the reportCreationLtsService
	 */
	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	/**
	 * @param reportCreationLtsService the reportCreationLtsService to set
	 */
	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	/**
	 * @return the reportSetLkupCacheService
	 */
	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	/**
	 * @param reportSetLkupCacheService the reportSetLkupCacheService to set
	 */
	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
	}

	/**
	 * @return the ltsSmsService
	 */
	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	/**
	 * @param ltsSmsService the ltsSmsService to set
	 */
	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
	}

	/**
	 * @return the ltsOrderDocumentService
	 */
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	/**
	 * @param ltsOrderDocumentService the ltsOrderDocumentService to set
	 */
	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	/**
	 * @return the ltsEmailService
	 */
	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	/**
	 * @param ltsEmailService the ltsEmailService to set
	 */
	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}
	
	public void approvalOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd) {}

	public void suspendWithPendingOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd) {}

	/**
	 * @return the ltsPaymentService
	 */
	public LtsPaymentService getLtsPaymentService() {
		return ltsPaymentService;
	}

	/**
	 * @param ltsPaymentService the ltsPaymentService to set
	 */
	public void setLtsPaymentService(LtsPaymentService ltsPaymentService) {
		this.ltsPaymentService = ltsPaymentService;
	}

	public PipbActvLtsService getPipbActvLtsService() {
		return pipbActvLtsService;
	}

	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
		this.pipbActvLtsService = pipbActvLtsService;
	}
	
	public LtsClubMembershipService getLtsClubMembershipService() {
		return ltsClubMembershipService;
	}

	public void setLtsClubMembershipService(
			LtsClubMembershipService ltsClubMembershipService) {
		this.ltsClubMembershipService = ltsClubMembershipService;
	}
	
	
}
