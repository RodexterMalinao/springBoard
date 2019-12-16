package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.ServiceDetailLtsService;
import com.bomwebportal.lts.service.activity.PipbActivityLtsSubmissionService;
import com.bomwebportal.lts.util.LtsActvBackendConstant.ActvAction;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.wsClientLts.BomWsBackendClient;
import com.pccw.custProfileGateway.serviceProf.DNAssignOutput;
import com.pccw.custProfileGateway.serviceProf.DNAssignmentResultDTO;
import com.pccw.custProfileGateway.serviceProf.DnAssignReqResponse;
import com.pccw.custProfileGateway.serviceProf.GetDrgSvcDetailResponse;
import com.pccw.util.spring.SpringApplicationContext;

public class PipbOrderResumeSubmitServiceImpl extends OrderSignoffLtsServiceImpl implements PipbOrderResumeSubmitService {

	protected final Log logger = LogFactory.getLog(getClass());
		
	private ImsSbOrderService imsSbOrderService;
	private BomWsBackendClient bomWsBackendClient;
	private ServiceDetailLtsService serviceDetailLtsService;
	private LtsSignatureService ltsSignatureService;
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	
	public boolean resumeSignOffPipbOrder(String sbOrderId, String pUserId) throws Exception {		
		SbOrderDTO sbOrder = this.orderRetrieveLtsService.retrieveSbOrder(sbOrderId, true);	
		return resumeSignOffPipbOrder(sbOrder, pUserId);
	}
	
	public boolean resumeSignOffPipbOrder(SbOrderDTO sbOrder, String pUserId) throws Exception {	
//		this.initialize(sbOrder, pUserId, sbOrder.getShopCd());
		if (LtsSbHelper.isLtsOrderCancelled(sbOrder.getSrvDtls())) {
			return false;
		}
		if (!validatePipbDnExists(sbOrder, pUserId)) {
			createDnFollowup(ActvAction.CREATE_DN, null, sbOrder.getOrderId(), pUserId);
			ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
			if(!StringUtils.equals(ltsCoreService.getSbStatus(), LtsBackendConstant.ORDER_STATUS_PENDING_BOM)){
				this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), ltsCoreService.getDtlId(), pUserId, LtsBackendConstant.WQ_TYPE_PARTIAL);
			}
			
			return false;
		}		
		List<String> errList = changeDnToPreAssigned(sbOrder, pUserId);
		if (errList!=null && errList.size()>0) {
			createDnFollowup(ActvAction.DN_FOLLOWUP, errList, sbOrder.getOrderId(), pUserId);
			return false;
		}
		preSubmitSignoff(sbOrder, pUserId);
		this.determineWorkQueueNatureForSignoff(sbOrder);
		setWqNatureRemark(sbOrder);
		submitToWorkQueue(sbOrder, pUserId, sbOrder.getShopCd());
		updateInvPreassgnJunctionMsg(sbOrder, pUserId);
		postSubmitSignoff(sbOrder, pUserId);
		return true;
	}

	private void preSubmitSignoff(SbOrderDTO sbOrder, String userId) {
		this.clearFollowupWorkQueue(sbOrder, userId);
		this.getServiceActions(sbOrder, userId);
		this.updateSuspendBomOrder(sbOrder, userId);
	}
	
	private void postSubmitSignoff(SbOrderDTO sbOrder, String userId) {
		createImsEyeOrder(sbOrder, userId); // create IMS order if needed
		updateServiceStatusAfterSignoff(sbOrder, userId); // update table BOMWEB_ORDER_LATEST_STATUS
		setDnAssignedFromDnPool(sbOrder);
		//removeOrderSignature();
	}
	
	public void createImsEyeOrder(SbOrderDTO sbOrder, String userId) {
		this.imsSbOrderService.createImsEyeOrder(sbOrder, userId); // create IMS order if needed
	}
		
	public void createDnFollowup(ActvAction actvAction, List<String> errList, String orderId, String userId) throws Exception {
		PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService = SpringApplicationContext.getBean("pipbActivityLtsSubmissionService");
		pipbActivityLtsSubmissionService.createDnFollowup(orderId, userId, 
				errList!=null && errList.size()>0? errList.get(0) : null, actvAction);
	}
	
	public boolean validatePipbDnExists(SbOrderDTO sbOrder, String userId) {
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);		
		if (ltsServiceDetail!=null && LtsBackendConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())) {
			String serviceInventSts = getSrvDnStatus(ltsServiceDetail.getSrvNum(), LtsBackendConstant.SERVICE_TYPE_TEL);		
			if (StringUtils.isBlank(serviceInventSts)) {
				return false;
			} else {
				updatePipbDnStatus(serviceInventSts, sbOrder.getOrderId(), ltsServiceDetail.getDtlId(), userId);
			}
		}
		return true;
	}
	
	public String getSrvDnStatus(String srvNum, String srvType) {		
		GetDrgSvcDetailResponse response  = bomWsBackendClient.getDrgSvcDetail(srvNum, srvType);
		if (response!=null && response.getGetDrgSvcDetailResult()!=null) {
			return response.getGetDrgSvcDetailResult().getStsCd();
		}
		return null;
	}
	
	private void updatePipbDnStatus(String serviceInventSts, String orderId, String dtlId, String user) {
		serviceDetailLtsService.updateDnStatus(serviceInventSts, orderId, dtlId, user);
	}
	
	private void updateGatewayNum(String gatewayNum, String orderId, String dtlId, String user) {
		serviceDetailLtsService.updateGatewayNum(gatewayNum, orderId, dtlId, user);
	}
		
	public void updateServiceStatusAfterSignoff(SbOrderDTO sbOrder, String userId) {
		this.updateLtsCoreServiceStatusAfterSubmission(sbOrder, userId);
		this.updateImsServiceStatusAfterSubmission(sbOrder, userId);
		this.updateOtherDelServiceStatusAfterSubmission(sbOrder, userId);
	}
	
	private void updateLtsCoreServiceStatusAfterSubmission(SbOrderDTO sbOrder, String userId) {
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_FULL, ltsCoreService.getWorkQueueType()) 
				|| StringUtils.equals(LtsBackendConstant.WQ_TYPE_FOLLOWUP, ltsCoreService.getWorkQueueType())){
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), ltsCoreService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
			return;
		}
		
		ServiceDetailDTO imsService = getImsService(sbOrder);
		
		if (imsService != null) {
			if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_FULL, imsService.getWorkQueueType()) 
					|| StringUtils.equals(LtsBackendConstant.WQ_TYPE_FOLLOWUP, imsService.getWorkQueueType())){
				this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), ltsCoreService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
				return;
			} 	
		}
		
		this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), ltsCoreService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
	}	
	
	private void updateImsServiceStatusAfterSubmission(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailDTO imsService = getImsService(sbOrder);
		
		if (imsService == null) {
			return;
		}
		
		if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_FULL, imsService.getWorkQueueType()) 
				|| StringUtils.equals(LtsBackendConstant.WQ_TYPE_FOLLOWUP, imsService.getWorkQueueType())) {
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), imsService.getDtlId(), userId, imsService.getWorkQueueType());
			return;
		}
		if (StringUtils.equals(LtsBackendConstant.WQ_TYPE_FULL, ltsCoreService.getWorkQueueType()) 
				|| StringUtils.equals(LtsBackendConstant.WQ_TYPE_FOLLOWUP, ltsCoreService.getWorkQueueType())){
			this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), imsService.getDtlId(), userId, ltsCoreService.getWorkQueueType());
			return;
		}
		
		this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), imsService.getDtlId(), userId, imsService.getWorkQueueType());
	}
	
    private void updateOtherDelServiceStatusAfterSubmission(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(sbOrder);
		
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)
					|| StringUtils.equals(serviceDetail.getDtlId(), ltsCoreService.getDtlId())) {
				continue;
			} else if (LtsBackendConstant.WQ_TYPE_FULL.equals(serviceDetail.getWorkQueueType()) 
					|| LtsBackendConstant.WQ_TYPE_FOLLOWUP.equals(serviceDetail.getWorkQueueType())
					|| LtsSbHelper.isAcqPortLaterService(serviceDetail)) {
				this.orderStatusService.setPendingBomStatus(sbOrder.getOrderId(), serviceDetail.getDtlId(), userId, serviceDetail.getWorkQueueType());			
			} else {
				this.orderStatusService.setSubmissionStatus(sbOrder.getOrderId(), serviceDetail.getDtlId(), userId, serviceDetail.getWorkQueueType());
			}
		}
	}
    
    public List<String> changeDnToPreAssigned(SbOrderDTO sbOrder, String userId) {
    	List<String> list = new ArrayList<String>();
    	// primary del
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
		if (ltsServiceDetail!=null && (LtsBackendConstant.DN_SOURCE_DN_RESERVED.equals(ltsServiceDetail.getDnSource())
				|| (LtsBackendConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())))) {
			String stsCd = getSrvDnStatus(ltsServiceDetail.getSrvNum(), LtsBackendConstant.SERVICE_TYPE_TEL);
			String accountCd = "";
			String boc = "";
			String projectCd = "";
			String specialSrvGrp = "";
			String specialSrvName = "";
			if (LtsBackendConstant.DRG_DN_RESERVED_STATUS.equals(stsCd)) {
				accountCd = ltsServiceDetail.getNrpAccountCd()==null?LtsBackendConstant.DEFAULT_NRP_ACCOUNT_CD:ltsServiceDetail.getNrpAccountCd();
				boc = ltsServiceDetail.getNrpBoc()==null?LtsBackendConstant.DEFAULT_NRP_BOC:ltsServiceDetail.getNrpBoc();
				projectCd = ltsServiceDetail.getNrpProjectCd()==null?LtsBackendConstant.DEFAULT_NRP_PROJECT_CD:ltsServiceDetail.getNrpProjectCd();
			}
			DNAssignmentResultDTO dnAssgnDto = getDNAssignment(ltsServiceDetail.getSrvNum(), accountCd, boc, projectCd, specialSrvGrp, specialSrvName);
			if (dnAssgnDto!=null && !StringUtils.equals(LtsBackendConstant.WS_RESPONSE_SUCCESS_CODE, dnAssgnDto.getErrSeverity())
					&& StringUtils.equals(LtsBackendConstant.WS_ERROR_MESSAGE_DN_DEDICATED, dnAssgnDto.getErrDesc())) {
				specialSrvGrp = LtsBackendConstant.DEFAULT_SPECIAL_SERVICE_GROUP;
				specialSrvName = LtsBackendConstant.DEFAULT_SPECIAL_SERVICE_NAME;
				dnAssgnDto = getDNAssignment(ltsServiceDetail.getSrvNum(), accountCd, boc, projectCd, specialSrvGrp, specialSrvName);
			}
			if (dnAssgnDto!=null) {
				if (StringUtils.equals(LtsBackendConstant.WS_RESPONSE_SUCCESS_CODE, dnAssgnDto.getErrSeverity())) {
					if (LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls())==null
							|| StringUtils.equals(LtsBackendConstant.DN_SOURCE_DN_RESERVED, ltsServiceDetail.getDnSource())
							|| LtsSbHelper.isDrgPortOutStatus(stsCd)
							|| StringUtils.equals(LtsBackendConstant.EXCHANGE_CATEGORY_CODE_2N_DN,
									dnAssgnDto.getAvailableDNs().getDnDTO().get(0).getExchangeCategory())) {
						DNAssignOutput dnAssignOutput = dnAssignReq(ltsServiceDetail.getSrvNum(), null, LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_PRE_ASSIGN, null);
						if (dnAssignOutput==null) {
							list.add(LtsBackendConstant.WS_ERROR_MESSAGE_NO_RESPONSE);
						} else {
							if(StringUtils.equals(LtsBackendConstant.WS_RESPONSE_SUCCESS_CODE, dnAssignOutput.getDrgRtnSts().getErrorSeverity())) {
								if (dnAssignOutput.getDnAssignOutArray().getDNAssignOutDetail()!=null 
										&& dnAssignOutput.getDnAssignOutArray().getDNAssignOutDetail().size()>0) {
									updateGatewayNum(dnAssignOutput.getDnAssignOutArray().getDNAssignOutDetail().get(0).getGatewayNum(), 
											sbOrder.getOrderId(), ltsServiceDetail.getDtlId(), userId);
								}
							} else {
								list.add(LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()) + " " + dnAssignOutput.getDrgRtnSts().getErrorDesc());
							}
						}						
					} else {
						DNAssignOutput dnAssignOutput = dnAssignReq(ltsServiceDetail.getSrvNum(), stsCd, 
								LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_RELEASE, 
								LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_GATEWAY_NUM);
						if (dnAssignOutput==null) {
							list.add(LtsBackendConstant.WS_ERROR_MESSAGE_NO_RESPONSE);
						} else {
							if(StringUtils.equals(LtsBackendConstant.WS_RESPONSE_SUCCESS_CODE, dnAssignOutput.getDrgRtnSts().getErrorSeverity())) {
								list.add(LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()) + " " + LtsBackendConstant.WS_ERROR_MESSAGE_NON_2N_DN);
							} else {
								list.add(LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()) + " " + dnAssignOutput.getDrgRtnSts().getErrorDesc());
							}
						}
					}					
			    } else {
					list.add(LtsSbHelper.getDisplaySrvNum(ltsServiceDetail.getSrvNum()) + " " + dnAssgnDto.getErrDesc());
			    }
			} else {
				list.add(LtsBackendConstant.WS_ERROR_MESSAGE_NO_RESPONSE);
			}
		}
		return list;
	}
    
    public DNAssignmentResultDTO getDNAssignment(String srvNum, String accountCd, String boc, String projectCd, String specialSrvGrp, String specialSrvName) {
    	List<DNAssignmentResultDTO> list = bomWsBackendClient.getDNAssignment(srvNum, accountCd, boc, projectCd, specialSrvGrp, specialSrvName);
    	if (list!=null && list.size()>0) {
    		return (DNAssignmentResultDTO)list.get(0);    		
    	}
    	return null;
    }
        
    public DNAssignOutput dnAssignReq(String srvNum, String inventStsCd, String action, String gatewayNum) {
    	DnAssignReqResponse response = bomWsBackendClient.dnAssignReq(srvNum, inventStsCd, action, gatewayNum);
    	if (response!=null && response.getDnAssignReqResult()!=null) {
    		return response.getDnAssignReqResult();
    	}
    	return null;
    }
    
    public void updateInvPreassgnJunctionMsg(SbOrderDTO sbOrder, String userId) {
		try {
			// primary del
			ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
			if (ltsServiceDetail != null
					&& LtsBackendConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())) {
				bomWsBackendClient.updateInvPreassgnJunctionMsg(
						ltsServiceDetail.getSrvNum(), sbOrder.getOrderId(), userId);
			}
			// second del
			ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbHelper.get2ndDelServices(sbOrder.getSrvDtls());
			if (lts2ndDelServiceDetail != null
					&& LtsBackendConstant.DN_SOURCE_DN_POOL.equals(lts2ndDelServiceDetail.getDnSource())) {
				bomWsBackendClient.updateInvPreassgnJunctionMsg(
						lts2ndDelServiceDetail.getSrvNum(), sbOrder.getOrderId(), userId);
			}
		} catch (Exception e) {
			logger.warn("Exception in call remote service", e);
		}
	}
    
    private boolean setDnAssignedFromDnPool(SbOrderDTO sbOrder) {
		// primary del
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
		if (ltsServiceDetail != null
				&& LtsBackendConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())) {
			ltsAcqDnPoolService.assignDn(ltsServiceDetail.getSrvNum());
		}
		// second del
		ServiceDetailLtsDTO lts2ndDelServiceDetail = (ServiceDetailLtsDTO) LtsSbHelper.get2ndDelService(sbOrder);
		if (lts2ndDelServiceDetail != null
				&& LtsBackendConstant.DN_SOURCE_DN_POOL.equals(lts2ndDelServiceDetail.getDnSource())) {
			ltsAcqDnPoolService.assignDn(lts2ndDelServiceDetail.getSrvNum());
		}
		return true;
	}
    
    public void removeOrderSignature(SbOrderDTO sbOrder) {
		ltsSignatureService.deleteOrderSignature(sbOrder.getOrderId(), null); 
	}
    
    private ServiceDetailDTO getImsService(SbOrderDTO sbOrder) {
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (isImsService(serviceDetail)) {
				return serviceDetail;
			}
		}
		return null;
	}
	
    private boolean isImsService(ServiceDetailDTO serviceDetail) {
		if (!(serviceDetail instanceof ServiceDetailOtherLtsDTO)) {
			return false;
		}
		return true;
	}
       	
	/**
	 * @return the imsSbOrderService
	 */
	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	/**
	 * @param imsSbOrderService the imsSbOrderService to set
	 */
	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}

	/**
	 * @return the bomWsBackendClient
	 */
	public BomWsBackendClient getBomWsBackendClient() {
		return bomWsBackendClient;
	}

	/**
	 * @param bomWsBackendClient the bomWsBackendClient to set
	 */
	public void setBomWsBackendClient(BomWsBackendClient bomWsBackendClient) {
		this.bomWsBackendClient = bomWsBackendClient;
	}

	/**
	 * @return the serviceDetailLtsService
	 */
	public ServiceDetailLtsService getServiceDetailLtsService() {
		return serviceDetailLtsService;
	}

	/**
	 * @param serviceDetailLtsService the serviceDetailLtsService to set
	 */
	public void setServiceDetailLtsService(
			ServiceDetailLtsService serviceDetailLtsService) {
		this.serviceDetailLtsService = serviceDetailLtsService;
	}

	/**
	 * @return the ltsSignatureService
	 */
	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	/**
	 * @param ltsSignatureService the ltsSignatureService to set
	 */
	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}

	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}
	
}
