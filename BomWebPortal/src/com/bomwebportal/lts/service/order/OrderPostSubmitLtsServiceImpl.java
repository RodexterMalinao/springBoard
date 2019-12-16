package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsClubMembershipService;
import com.bomwebportal.lts.service.LtsEmailService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsRemarkService;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.bom.BomInvPreassgnService;
import com.bomwebportal.lts.service.bom.CustContactInfoService;
import com.bomwebportal.lts.service.bom.CustPdpoProfileService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.service.sms.LtsSmsService;
import com.bomwebportal.lts.service.workQueue.WqTransSubmitService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.spring.SpringApplicationContext;

public class OrderPostSubmitLtsServiceImpl extends OrderSignoffLtsServiceImpl implements OrderPostSubmitLtsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsSbOrderService imsSbOrderService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsEmailService ltsEmailService;
	private LtsSignatureService ltsSignatureService;
	private LtsRemarkService ltsRemarkService;
	private LtsSmsService ltsSmsService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	private WqTransSubmitService wqTransSubmitService;
	private BomInvPreassgnService bomInvPreassgnService;
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	private CustPdpoProfileService custPdpoProfileService;
	private LtsClubMembershipService ltsClubMembershipService;
	private CustContactInfoService custContactInfoService;
	
	@Transactional
	public String signOffOrder(SbOrderDTO pSbOrder, String pReportType, String pExportType, String pEditButton, String pUser, String pShopCd) {
		if (!verifySignoff(pSbOrder)) {
			return null;
		}
		
		try {
			this.initializeSignoff(pSbOrder, pUser);
			this.preSubmitSignoff(pSbOrder, pUser);
			this.determineWorkQueueNatureForSignoff(pSbOrder);
			this.setWqNatureRemark(pSbOrder);
			this.bomInvPreassgnService.updateInvPreassgnJunctionMsg(pSbOrder, pUser);
			this.submitToWorkQueue(pSbOrder, pUser, pShopCd);
			this.wqTransSubmitService.submitPendingWqTrans(pSbOrder, pUser, pShopCd);
			return this.postSubmitSignoff(pSbOrder, pUser, pReportType, pExportType, pEditButton);	
		}
		catch (Exception e) {
			logger.error("Fail to signOffOrder " + pSbOrder.getOrderId() + "\n" +ExceptionUtils.getFullStackTrace(e), e);
			throw new AppRuntimeException("Fail to signOffOrder " + pSbOrder.getOrderId(), e);
		}
	}
	
	@Transactional
	public void approvalOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd) {
		this.preSubmitApproval(pSbOrder);
		this.determineWorkQueueNatureForApproval(pSbOrder);
		this.setWqNatureRemark(pSbOrder);
		this.submitToApprovalWorkQueue(pSbOrder, pUser, pShopCd);
		this.postSubmitApproval(pSbOrder, pUser);
	}
	
	@Transactional
	public void suspendWithPendingOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd) {
		
//		this.initialize(pSbOrder, pUser, pShopCd);
		this.preSuspend(pSbOrder);
		this.determineWorkQueueNatureForSuspendWithPending(pSbOrder);
		this.submitToSuspendPendingWorkQueue(pSbOrder, pUser, pShopCd);
		this.postSuspend(pSbOrder, pUser);
	}
	
	private void preSubmitSignoff(SbOrderDTO sbOrder, String userId) {
		this.ltsRemarkService.generateOrderRemark(sbOrder, userId);
		this.ltsRemarkService.generateCustomerRemark(sbOrder, userId);
		this.clearFollowupWorkQueue(sbOrder, userId);
		this.getServiceActions(sbOrder, userId);
		this.updateSuspendBomOrder(sbOrder, userId);
	}
	
	private void preSubmitApproval(SbOrderDTO sbOrder) {
		this.getApprovalServiceActions(sbOrder);
	}
	
	private void preSuspend(SbOrderDTO sbOrder) {
		this.getSuspendServiceActions(sbOrder);
	}
	
	private void setDnAssignedFromDnPool(SbOrderDTO sbOrder) {
		for(int i = 0; i< sbOrder.getSrvDtls().length;i++){
			if (sbOrder.getSrvDtls()[i] instanceof ServiceDetailLtsDTO
					&& LtsConstant.DN_SOURCE_DN_POOL.equals(((ServiceDetailLtsDTO)sbOrder.getSrvDtls()[i]).getDnSource())){
				ltsAcqDnPoolService.assignDn(sbOrder.getSrvDtls()[i].getNewSrvNum() != null ? sbOrder.getSrvDtls()[i].getNewSrvNum() : sbOrder.getSrvDtls()[i].getSrvNum());								
			}
		}
	}
	
	private String postSubmitSignoff(SbOrderDTO sbOrder, String userId, String pReportType, String pExportType, String pEditButton) {		
		try {
			if (LtsSbOrderHelper.getImsService(sbOrder) != null) {
				this.imsSbOrderService.createImsEyeOrder(sbOrder, userId);
			}
		}
		catch (Exception e) {
			logger.error("Fail to create IMS Eye Order " + sbOrder.getOrderId() + "\n" +ExceptionUtils.getFullStackTrace(e), e);
			throw new AppRuntimeException("Fail to create IMS Eye Order " + sbOrder.getOrderId(), e);
		}
		
		//assign dn form dnpool --Modified By Max.R.MENG
		setDnAssignedFromDnPool(sbOrder);
		updateStatusAfterSignoff(sbOrder, userId);
		String signOffDate = this.orderDetailService.updateSignoffDate(sbOrder.getOrderId());
		sbOrder.setSignOffDate(signOffDate);
		
		StringBuilder message = new StringBuilder();
		String letterPrintReq = createLetterPrintReq(sbOrder, userId);
		if (StringUtils.isNotBlank(letterPrintReq) && !EmailReqResult.SUCCESS.toString().equals(letterPrintReq)) {
			message.append("\n" + letterPrintReq);	
		}


		// Logic for except Disconnect Order 
		if (!LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())) {
			ServiceDetailLtsDTO srvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
			
			// Register TheClub & MyHKT 
			String regResult = regMyHktTheClub(sbOrder, srvDtl, userId);
			if (StringUtils.isNotBlank(regResult)) {
				message.append("\n" + regResult);
			}
			
			// Generate and Save Application Form to File Server
			saveAfForm(sbOrder);
			saveAfToOrderDoc(sbOrder, userId);

			
			// Earn Clubpoints
			ltsClubMembershipService.earnClubPointsAndUpdateItems(sbOrder, userId);
		}
		
		String smsResult = sendSignOffSms(sbOrder, userId);
		if (StringUtils.isNotBlank(smsResult) && !EmailReqResult.SUCCESS.toString().equals(smsResult)) {
			message.append("\n" + smsResult);	
		}
		
		String emailResult = sendSignOffEmail(sbOrder, userId);
		if (StringUtils.isNotBlank(emailResult) && !EmailReqResult.SUCCESS.toString().equals(emailResult)) {
			message.append("\n" + emailResult);	
		}
		
//		removeOrderSignature(sbOrder);
		
		String updatePdpoResult = updateCustDataPrivayInfo(sbOrder, userId);
		if (StringUtils.isNotBlank(updatePdpoResult)) {
			message.append("\n" + updatePdpoResult);	
		}
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())
				|| LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			custContactInfoService.updateCustContactInfo(sbOrder.getOrderId(), sbOrder.getContact(), userId, sbOrder.getSalesChannel());
		}
		
		
		
		return message.toString();		
	}
	
	private String updateCustDataPrivayInfo(SbOrderDTO sbOrder, String userId) {
		ServiceDetailDTO coreService = LtsSbOrderHelper.getLtsService(sbOrder);
		CustOptOutInfoLtsDTO custOptOutInfo = coreService.getAccount().getCustomer().getCustOptOutInfo();
		if (custOptOutInfo == null) {
			return null;
		}

		String returnMsg = null;
		
		try {
			if (LtsConstant.PDPO_UPDATE_BOM_STATUS_PENDING.equals(custOptOutInfo.getUpdBomStatus())) {
				returnMsg = custPdpoProfileService.updateCustDataPrivayInfo(coreService.getAccount().getCustomer().getCustOptOutInfo(), 
						coreService.getAccount().getBillingAddress(), userId);
				if (StringUtils.isNotBlank(returnMsg)) {
					custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_FAIL);
					custOptOutInfo.setErrMsg(returnMsg);
					returnMsg = "Fail to update customer data privay in BOM: [" + returnMsg + "]";
				}
				else {
					custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_COMPLETED);
				}
				custOptOutInfo.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				ServiceActionBase custOptOutInfoLtsService = SpringApplicationContext.getBean("custOptOutInfoLtsService");
				custOptOutInfoLtsService.doSave(custOptOutInfo, userId, sbOrder.getOrderId(), custOptOutInfo.getCustNo());
			}	
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "Fail to update customer data privay in BOM:[" + e.getMessage() + "]";	
		}
		return returnMsg;
	}
	
	private String createLetterPrintReq(SbOrderDTO sbOrder, String userId) {
		if (LtsConstant.DISTRIBUTE_MODE_PAPER.equals(sbOrder.getDisMode())) {
			return ltsEmailService.createLetterPrintReq(sbOrder, userId);	
		}
		return null;
	}
	
	protected void saveAfForm(SbOrderDTO sbOrder) {
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(LtsConstant.RPT_KEY_SBORDER, sbOrder);
		inputMap.put(LtsConstant.RPT_KEY_LOB, LtsConstant.LOB_LTS);
		
		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob(LtsConstant.LOB_LTS);
		rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
		rptSet.setRptSet(ReportCreationLtsService.RPT_SET_SIGNOFF_AF);
		this.reportCreationLtsService.generateReport((ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey()), inputMap);
	}
	
	private void postSubmitApproval(SbOrderDTO sbOrder, String userId) {
		this.updateStatusAfterApproval(sbOrder, userId);
	}
	
	private void postSuspend(SbOrderDTO sbOrder, String userId) {
		this.updateStatusSuspendWithPending(sbOrder, userId);
	}
	
	public void submitToDsPrepaymentWorkQueue(SbOrderDTO pSbOrder, String userId) {
		this.getDsPrepaymentServiceActions(pSbOrder);
		this.determineWorkQueueNatureForApproval(pSbOrder);
		this.setWqNatureRemark(pSbOrder);
		ServiceDetailDTO[] wqSrvs = this.getRequiredWorkQueueServices(pSbOrder);
		
		if (wqSrvs.length > 0) {
			this.workQueueSubmissionService.submitToDsPrepaymentWorkQueue(pSbOrder, wqSrvs, userId);
		}
	}
	
	private void submitToApprovalWorkQueue(SbOrderDTO sbOrder, String userId, String shopCd) {
		
		ServiceDetailDTO[] wqSrvs = this.getRequiredWorkQueueServices(sbOrder);
		
		if (wqSrvs.length > 0) {
			this.workQueueSubmissionService.submitToApprovalWorkQueue(sbOrder, wqSrvs, userId, shopCd);
		}
	}
	
	private void submitToSuspendPendingWorkQueue(SbOrderDTO sbOrder, String userId, String shopCd) {
		
		ServiceDetailDTO[] wqSrvs = this.getRequiredWorkQueueServices(sbOrder);
		
		if (wqSrvs.length > 0) {
			this.workQueueSubmissionService.submitToPendingOrderWorkQueue(sbOrder, wqSrvs, userId, shopCd);
		}
	}
	
	private ServiceDetailDTO[] getRequiredWorkQueueServices(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		List<ServiceDetailDTO> srvDtlList = new ArrayList<ServiceDetailDTO>();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			if (StringUtils.isNotEmpty(srvDtls[i].getWorkQueueType())) {
				srvDtlList.add(srvDtls[i]);
			}
		}
		return srvDtlList.toArray(new ServiceDetailDTO[srvDtlList.size()]);
	}
	
	private void getDsPrepaymentServiceActions(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			srvDtls[i].setSrvActions(this.serviceActionTypeLookupService.getDirectSalesPrepaymentServiceActionType(sbOrder));	
		}
	}
	
	private void getApprovalServiceActions(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			srvDtls[i].setSrvActions(this.serviceActionTypeLookupService.getApprovalServiceActionType(sbOrder, srvDtls[i], sbOrder.getOrderType()));	
		}
	}
	
	private void getSuspendServiceActions(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			srvDtls[i].setSrvActions(this.serviceActionTypeLookupService.getSuspendWithPendingServiceActionType(srvDtls[i]));	
		}
	}
	
	private void updateStatusAfterSignoff(SbOrderDTO sbOrder, String userId) {
		this.updateLtsCoreServiceStatusAfterSubmission(sbOrder, userId);
		this.updateImsServiceStatusAfterSubmission(sbOrder, userId);
		this.updateOtherDelServiceStatusAfterSubmission(sbOrder, userId);
	}
	
	private void updateStatusAfterApproval(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			this.orderStatusService.setPendingApprovalStatus(sbOrder.getOrderId(), srvDtls[i].getDtlId(), userId, srvDtls[i].getWorkQueueType());
		}
	}
	
	private void updateStatusSuspendWithPending(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			if (StringUtils.isNotEmpty(srvDtls[i].getPendingOcid())) {
				this.orderStatusService.setSuspendStatus(sbOrder.getOrderId(), srvDtls[i].getDtlId(), "PENORD", userId, null);
			} else if ((srvDtls[i] instanceof ServiceDetailLtsDTO) && ((ServiceDetailLtsDTO)srvDtls[i]).isTos()) {
				this.orderStatusService.setSuspendStatus(sbOrder.getOrderId(), srvDtls[i].getDtlId(), "TOS_SRV", userId, null);
			} else {
				this.orderStatusService.setSuspendStatus(sbOrder.getOrderId(), srvDtls[i].getDtlId(), "OTHER", userId, null);
			}
		}
	}
	
	private void updateLtsCoreServiceStatusAfterSubmission(SbOrderDTO sbOrder, String userId) {
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		if (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, ltsCoreService.getWorkQueueType()) 
				|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, ltsCoreService.getWorkQueueType())){
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), ltsCoreService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
			return;
		} 
		
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		if (imsService != null) {
			if (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, imsService.getWorkQueueType()) 
					|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, imsService.getWorkQueueType())){
				this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), ltsCoreService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
				return;
			} 	
		}
		
		this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), ltsCoreService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
	}	
	
	private void updateImsServiceStatusAfterSubmission(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		if (imsService == null) {
			return;
		}
		
		if (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, imsService.getWorkQueueType()) 
				|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, imsService.getWorkQueueType())) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), imsService.getDtlId(), userId, imsService.getWorkQueueType());
			return;
		}
		if (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, ltsCoreService.getWorkQueueType()) 
				|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, ltsCoreService.getWorkQueueType())){
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), imsService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
			return;
		} 
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			if (!LtsSbOrderHelper.isImsServiceChangeOffer((ServiceDetailOtherLtsDTO)imsService)) {
				this.orderStatusService.setClosedStatus(sbOrder.getOrderId(), imsService.getDtlId(), userId);
				return;
			}
		}
		this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), imsService.getDtlId(), userId, imsService.getWorkQueueType());
	}
	
	
	private void updateOtherDelServiceStatusAfterSubmission(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)
					|| StringUtils.equals(serviceDetail.getDtlId(), ltsCoreService.getDtlId())) {
				continue;
			}
			
			if (StringUtils.isNotEmpty(((ServiceDetailLtsDTO)serviceDetail).getDuplexInd())) {
				this.alignDuplexServiceStatus(sbOrder, (ServiceDetailLtsDTO)serviceDetail, userId);
			} 
			else if (LtsConstant.WQ_TYPE_FULL.equals(serviceDetail.getWorkQueueType()) 
					|| LtsConstant.WQ_TYPE_FOLLOWUP.equals(serviceDetail.getWorkQueueType())) {
				this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), serviceDetail.getDtlId(), userId, serviceDetail.getWorkQueueType());
			} else if (LtsConstant.LTS_PRODUCT_TYPE_REMOVE.equals(serviceDetail.getToProd())) {
				this.alignRemoveServiceStatus(sbOrder, (ServiceDetailLtsDTO)serviceDetail, userId);
			} else {
				this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), serviceDetail.getDtlId(), userId, serviceDetail.getWorkQueueType());
			}
		}
	}
	
	private void alignRemoveServiceStatus(SbOrderDTO sbOrder, ServiceDetailLtsDTO pRemoveSrv, String userId) {
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		if (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, ltsCoreService.getWorkQueueType()) 
//				|| StringUtils.equals(LtsConstant.WQ_TYPE_FULL, imsService.getWorkQueueType())
//				|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, imsService.getWorkQueueType())
				|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, ltsCoreService.getWorkQueueType())) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), pRemoveSrv.getDtlId(), userId, pRemoveSrv.getWorkQueueType());
		} 
		else if (imsService != null 
				&& (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, imsService.getWorkQueueType())
						|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, imsService.getWorkQueueType()))) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), pRemoveSrv.getDtlId(), userId, pRemoveSrv.getWorkQueueType());
		} 
		else {
			this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), pRemoveSrv.getDtlId(), userId, pRemoveSrv.getWorkQueueType());
		}
	}
	
	protected void alignDuplexServiceStatus(SbOrderDTO sbOrder, ServiceDetailLtsDTO pDuplexService, String userId) {
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		
		if (LtsConstant.LTS_SRV_TYPE_2DEL.equals(pDuplexService.getToSrvType())
				&& StringUtils.equals(LtsConstant.WQ_TYPE_FULL, ltsCoreService.getWorkQueueType())) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), pDuplexService.getDtlId(), userId, pDuplexService.getWorkQueueType());
			return;
		}
		
		if (StringUtils.equals(LtsConstant.WQ_TYPE_FULL, ltsCoreService.getWorkQueueType()) 
				|| StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, ltsCoreService.getWorkQueueType())) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), pDuplexService.getDtlId(), userId, pDuplexService.getWorkQueueType());
			return;
		}
		else if (imsService != null 
				&& (LtsConstant.WQ_TYPE_FULL.equals(imsService.getWorkQueueType()) 
						|| LtsConstant.WQ_TYPE_FOLLOWUP.equals(imsService.getWorkQueueType()))) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), pDuplexService.getDtlId(), userId, pDuplexService.getWorkQueueType());
			return;
		}
		else {
			if ((StringUtils.equals(LtsConstant.WQ_TYPE_FULL, pDuplexService.getWorkQueueType()) || StringUtils.equals(LtsConstant.WQ_TYPE_FOLLOWUP, pDuplexService.getWorkQueueType())) 
					&& ((LtsConstant.LTS_SRV_TYPE_DNX.equals(pDuplexService.getFromSrvType()) || LtsConstant.LTS_SRV_TYPE_DNY.equals(pDuplexService.getFromSrvType()))
							&& LtsConstant.LTS_SRV_TYPE_2DEL.equals(pDuplexService.getToSrvType()))) {
				this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), pDuplexService.getDtlId(), userId, pDuplexService.getWorkQueueType());
				return;
			}
		}
		this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), pDuplexService.getDtlId(), userId, pDuplexService.getWorkQueueType());	
	}
	
	private void determineWorkQueueNatureForApproval(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			srvDtls[i].setWorkQueueType(this.determineWorkQueueNatureForApproval(srvDtls[i]));
		}
	}
	
	private String determineWorkQueueNatureForApproval(ServiceDetailDTO pSrvDtl) {
		
		ServiceActionTypeDTO[] srvActions = pSrvDtl.getSrvActions();
		String wqType = "";
		
		for (int i=0; srvActions!=null && i<srvActions.length; ++i) {
			if (srvActions[i] == null 
					|| StringUtils.isEmpty(srvActions[i].getWqSubtype())
					|| StringUtils.equalsIgnoreCase(LtsConstant.WQ_TYPE_FOLLOWUP, srvActions[i].getWqSubtype())) {
				continue;
			} else if (StringUtils.isEmpty(wqType)
					|| StringUtils.equalsIgnoreCase(LtsConstant.WQ_TYPE_APPROVAL, srvActions[i].getWqSubtype())) {
				wqType = srvActions[i].getWqSubtype();
			}
		}
		return wqType;
	}
	
	private void determineWorkQueueNatureForSuspendWithPending(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			srvDtls[i].setWorkQueueType(this.determineWorkQueueNatureForSuspendWithPending(srvDtls[i]));
		}
	}
	
	private String determineWorkQueueNatureForSuspendWithPending(ServiceDetailDTO pSrvDtl) {
		
		ServiceActionTypeDTO[] srvActions = pSrvDtl.getSrvActions();
		String wqType = "";
		
		for (int i=0; srvActions!=null && i<srvActions.length; ++i) {
			if (StringUtils.equalsIgnoreCase(LtsConstant.WQ_TYPE_FOLLOWUP, srvActions[i].getWqSubtype())) {				
				wqType = srvActions[i].getWqSubtype();
			}
		}
		return wqType;
	}
	
	protected String sendSignOffSms(SbOrderDTO sbOrder, String userId) {
		if (LtsConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode())
				&& StringUtils.isNotEmpty(sbOrder.getSmsNo())) {
			return ltsSmsService.sendSignOffSms(sbOrder, userId);
		}
		return null;
	}
	
	protected String sendSignOffEmail(SbOrderDTO sbOrder, String userId) {
		if (LtsConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(sbOrder.getDisMode()) && StringUtils.isNotEmpty(sbOrder.getEsigEmailAddr())) {
			return ltsEmailService.sendSignOffEmail(sbOrder, sbOrder.getEsigEmailAddr(), userId);	
		}
		return null;
	}
	
	protected void saveAfToOrderDoc(SbOrderDTO sbOrder, String userId) {
		List<AllOrdDocLtsDTO> generatedFormList = ltsOrderDocumentService.getGeneratedFormList(sbOrder);
		ltsOrderDocumentService.saveGeneratedForm(generatedFormList, userId);
	}
	
	protected void removeOrderSignature(SbOrderDTO sbOrder) {
		OrderStatusDTO[] orderStatuses = orderStatusService.getStatus(sbOrder.getOrderId());
		
		if(ArrayUtils.isEmpty(orderStatuses)) {
			return;
		}
		
		for (OrderStatusDTO orderStatus : orderStatuses) {
			if (LtsConstant.ORDER_STATUS_PENDING_SIGNOFF.equals(orderStatus.getSbStatus())) {
				return;
			}
		}
		ltsSignatureService.deleteOrderSignature(sbOrder.getOrderId(), null);	
	}

	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}
	
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}

	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}

	public LtsRemarkService getLtsRemarkService() {
		return ltsRemarkService;
	}

	public void setLtsRemarkService(LtsRemarkService ltsRemarkService) {
		this.ltsRemarkService = ltsRemarkService;
	}

	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
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

	public WqTransSubmitService getWqTransSubmitService() {
		return wqTransSubmitService;
	}

	public void setWqTransSubmitService(WqTransSubmitService wqTransSubmitService) {
		this.wqTransSubmitService = wqTransSubmitService;
	}

	public BomInvPreassgnService getBomInvPreassgnService() {
		return bomInvPreassgnService;
	}

	public void setBomInvPreassgnService(BomInvPreassgnService bomInvPreassgnService) {
		this.bomInvPreassgnService = bomInvPreassgnService;
	}

	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}

	public CustPdpoProfileService getCustPdpoProfileService() {
		return custPdpoProfileService;
	}

	public void setCustPdpoProfileService(
			CustPdpoProfileService custPdpoProfileService) {
		this.custPdpoProfileService = custPdpoProfileService;
	}

	public LtsClubMembershipService getLtsClubMembershipService() {
		return ltsClubMembershipService;
	}

	public void setLtsClubMembershipService(LtsClubMembershipService ltsClubMembershipService) {
		this.ltsClubMembershipService = ltsClubMembershipService;
	}

	public CustContactInfoService getCustContactInfoService() {
		return custContactInfoService;
	}

	public void setCustContactInfoService(
			CustContactInfoService custContactInfoService) {
		this.custContactInfoService = custContactInfoService;
	}
	
}
