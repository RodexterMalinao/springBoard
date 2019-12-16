package com.bomwebportal.lts.service.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.service.dataAccess.ActivityStatusService;
import com.bomwebportal.lts.dto.activity.LtsActivityDTO;
import com.bomwebportal.lts.service.AmendLtsService;
import com.bomwebportal.lts.service.DsServiceLtsService;
import com.bomwebportal.lts.service.WqNatureLkupService;
import com.bomwebportal.lts.service.order.LtsDsOrderApprovalSubmitService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.PipbOrderResumeSubmitService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.lts.activity.WorkQueueStatusChangeService;

public class WorkQueueStatusChangeServiceImpl implements WorkQueueStatusChangeService {

	private RetrieveLtsActivityService retrieveLtsActivityService;
	private ActivityStatusService activityStatusService;
	private PipbOrderResumeSubmitService pipbOrderResumeSubmitService;
	private PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService;
	private LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService;
	private AmendLtsService amendLtsService;
	private DsServiceLtsService dsServiceLtsService;
	private WqNatureLkupService wqNatureLkupService;
	private OrderStatusService orderStatusService;
	
	private ActivityDTO activityDTO;
	private ActivityTaskDTO activityTaskDTO;	
	private String user;
	
	private final Log logger = LogFactory.getLog(getClass());

	
	public void statusChangedAction(String pWqWpAssgnId, String pSbId, String pSbDtlId, 
			String pSbActvId, String pWqStatus, String[] pWqNatureIds, String pRemarks, String pUserId) throws Exception {
		
		this.user = pUserId;
		this.activityDTO = this.retrieveLtsActivityService.retrieveActivity(pSbActvId, null);
		if(activityDTO != null){
			handleActvWqStatusChange(pWqWpAssgnId, pSbId, pSbActvId, pWqStatus);
		}
		handleWqStatusChange(pSbId, pSbDtlId, pWqStatus, pWqNatureIds);
	}
	
	private void handleWqStatusChange(String pSbId, String pSbDtlId, String pWqStatus, String[] pWqNatureIds) throws Exception {

		logger.info("WorkQueueStatusChangeService.handleWqStatusChange: SB_ID="+pSbId
				+ ", WqNature="+Arrays.toString(pWqNatureIds) + ", WQ_STATUS=" + pWqStatus);
		
		String wqNatureId = null;
		for(String natureId: pWqNatureIds){
			String natureType = wqNatureLkupService.getNatureType(natureId);
			if(!LtsBackendConstant.WQ_NATURE_TYPE_INDICATOR.equals(natureType)){
				wqNatureId = natureId;
				break;
			}
		}
		
		if(LtsBackendConstant.WQ_NATURE_ADD_IDD_FIX_FREE_PLAN.equals(wqNatureId)){
			if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_COMPLETE, pWqStatus)) {
				orderStatusService.setClosedStatus(pSbId, pSbDtlId, this.user);
			}
		}
		
		
	}
	
	private void handleActvWqStatusChange(String pWqWpAssgnId, String pSbId, String pSbActvId, String pWqStatus) throws Exception{
		
		this.activityTaskDTO = this.activityDTO.getTaskByWqWpAssgnId(pWqWpAssgnId);
		if (this.activityTaskDTO==null) {
			String[] list = this.retrieveLtsActivityService.getRelatedAssgnIdFromSearchSourceByAssgnId(pWqWpAssgnId, null);
			for (int i=0; ArrayUtils.isNotEmpty(list) && i<list.length; i++) {
				this.activityTaskDTO = this.activityDTO.getTaskByWqWpAssgnId(list[i]);
				if (this.activityTaskDTO!=null) {
					break;
				}
			}
		}
		
		if (this.activityTaskDTO==null) {
			throw new Exception(LtsActvBackendConstant.ACTV_TASK_NOT_FOUND_ERR + " " + pWqWpAssgnId);
		}

		logger.info("WorkQueueStatusChangeService.handleActvWqStatusChange: SB_ID="+pSbId
				+", ACTV_ID=" + pSbActvId + ", ACTV_TYPE=" + this.activityDTO.getActvType()
				+ ", TASK_TYPE=" + activityTaskDTO.getTaskType()
				+ ", WQ_STATUS=" + pWqStatus);
		
		if (StringUtils.equals(LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC, this.activityDTO.getActvType())
				|| StringUtils.equals(LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, this.activityDTO.getActvType())) {
		
			// CREATE DN
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_PIPB_DN_CREATE, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_COMPLETE, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_COMPLETED);			
				    // resume PIPB order sign off
					pipbOrderResumeSubmitService.resumeSignOffPipbOrder(pSbId, this.user);
				}
			}
			
			// CHANGE DN
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_PIPB_DN_CHANGE, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_ACKNOWLEDGE, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_ACKNOWLEDGE);
				}			
			}
			
			// PIPB REQUEST
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_ACKNOWLEDGE, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_ACKNOWLEDGE);			   		
				}
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_PIPB_REJECT, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_REJECTED);
					pipbActivityLtsSubmissionService.createPipbReject(pSbId, this.user);				
				}
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_PIPB_CANCEL, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_URGENT_CANCELLED);
					pipbActivityLtsSubmissionService.createCancelSalesAlert(pSbId, this.user);
					updateActvStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED);
				}
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_PIPB_RECALL_REJECT, pWqStatus)) {
					ActivityTaskDTO[] actvTask = getTasksByTypeFilterOutStatus(this.activityDTO, LtsActvBackendConstant.TASK_TYPE_PIPB_REJECT);
					if (actvTask!=null) {
						this.activityTaskDTO = this.activityDTO.getTaskBySeq(getMaxTaskSeqFromTaskList(actvTask));
						updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED);
					}	
					// Restore previous deferred SRD
					LtsActivityDTO actvDTO = this.retrieveLtsActivityService.retrievePendingLtsActivityByOrderIdActvType(
							pSbId, null, LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC);					
					if (StringUtils.isNotBlank(actvDTO.getActvId())) {
						ActivityTaskDTO[] srdTask = retrieveTaskByType(actvDTO, LtsActvBackendConstant.TASK_TYPE_SRD_CHANGE, true);
						if (srdTask!=null) {
							pipbActivityLtsSubmissionService.restoreDeferredSRD(pSbId, this.user);
						}
					}
				}
			}
			
			// PIPB REJECT
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_PIPB_REJECT, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_PIPB_RESUME, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_RESUME);
					ActivityTaskDTO[] actvTask = getTasksByTypeFilterOutStatus(this.activityDTO, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST); 
					if (actvTask!=null) {
						this.activityTaskDTO = this.activityDTO.getTaskBySeq(getMaxTaskSeqFromTaskList(actvTask));
						// cancel PIPB REQ
						updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED);
						updateActvStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED);
					}
					// create a new WQ for PIPB REQ
					pipbActivityLtsSubmissionService.createPipbRequest(pSbId, this.user);
				}
			}
			
			// PIPB CANCEL
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_PIPB_CANCEL, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_ACKNOWLEDGE, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_ACKNOWLEDGE);
					updateActvStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED);
				}
			}
			
			// SRD CHANGE
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_SRD_CHANGE, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_COMPLETE, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_COMPLETED);			   		
				}
			}
			
		}
		
		if (StringUtils.equals(LtsActvBackendConstant.ACTV_TYPE_AMEND_APPROVAL, this.activityDTO.getActvType())) {
			
			// AMEND APPROVAL
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_AMEND_APPROVAL, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_APPROVED, pWqStatus)) {					
					updateApprovalStatus(pSbId, LtsBackendConstant.AMEND_APPROVAL_APPROVED_STATUS);
				}
                if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_REJECT, pWqStatus)) {
                	updateApprovalStatus(pSbId, LtsBackendConstant.AMEND_APPROVAL_APPROVED_REJECT);
				}
			}
		}	
		
		if (StringUtils.equals(LtsActvBackendConstant.ACTV_TYPE_DS_AMEND_APPROVAL, this.activityDTO.getActvType())) {
			
			// DIRECT SALES AMENDMENT CANCELLATION APPROVAL
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_DS_AMEND_APPROVAL, activityTaskDTO.getTaskType())) {
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_APPROVED, pWqStatus)) {					
					updateDsAmendCancelApprovalStatus(pSbId, LtsBackendConstant.AMEND_APPROVAL_APPROVED_STATUS);
					
					// ############## Call Felix Lee API
					amendLtsService.completeAmendment(pSbId);
					
					// ##############
					
				}
                if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_REJECT, pWqStatus)) {
                	updateDsAmendCancelApprovalStatus(pSbId, LtsBackendConstant.AMEND_APPROVAL_APPROVED_REJECT);
				}
                
				updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_SETTLED);
			}
		}	

		if (StringUtils.equals(LtsActvBackendConstant.ACTV_TYPE_DS_CUST_NAME_APPROVAL, this.activityDTO.getActvType())) {
			logger.warn("WorkQueueStatusChangeService Name Not Match Approval processing...");
			// DIRECT SALES NAME NOT MATCH APPROVAL
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_DS_CUST_NAME_APPROVAL, activityTaskDTO.getTaskType())) {
				
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_APPROVED, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_SETTLED);
					dsServiceLtsService.updateDsNameNotMatchApprovalStatus(pSbId, this.user, LtsBackendConstant.NAME_MISMATCH_APR_CD_APPROVED);
					ltsDsOrderApprovalSubmitService.resumeSignOffDsOrder(pSbId, this.user);
					
				}
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_APPROVED_DIFF_CUST, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_SETTLED);
					dsServiceLtsService.updateDsNameNotMatchApprovalStatus(pSbId, this.user, LtsBackendConstant.NAME_MISMATCH_APR_CD_APPROVED_WITH_DIFF_CUST);
					ltsDsOrderApprovalSubmitService.resumeSignOffDsOrder(pSbId, this.user);
					
				}
                if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_REJECT, pWqStatus)) {
					updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_SETTLED);
					dsServiceLtsService.updateDsNameNotMatchApprovalStatus(pSbId, this.user, LtsBackendConstant.NAME_MISMATCH_APR_CD_REJECTED);
				}
			}
		}
			
		
		if (StringUtils.equals(LtsActvBackendConstant.ACTV_TYPE_WAIVE_QC, this.activityDTO.getActvType())) {
			logger.warn("WorkQueueStatusChangeService.WAIVE_QC processing...");
			// WAIVE QC
			if (StringUtils.equals(LtsActvBackendConstant.TASK_TYPE_WAIVE_QC, activityTaskDTO.getTaskType())) {
				logger.warn("WorkQueueStatusChangeService.WAIVE_QC: wq_return_status = " + pWqStatus);
				
				if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_APPROVED, pWqStatus)) {					
					updateWaiveQcApprovalStatus(pSbId, LtsBackendConstant.WAIVE_QC_STATUS_APPROVED);
				}
                if (StringUtils.equals(LtsActvBackendConstant.WQ_STATUS_APPROVAL_REJECT, pWqStatus)) {
                	updateWaiveQcApprovalStatus(pSbId, LtsBackendConstant.WAIVE_QC_STATUS_APPROVAL_REJECTED);
				}
			}
		}	
	}
	
	private void updateDsAmendCancelApprovalStatus(String orderId, String status) throws Exception {
		this.dsServiceLtsService.updateDsAmendCancelApprovalStatus(orderId, this.user, status);
	}
	
	private void updateWaiveQcApprovalStatus(String orderId, String status) throws Exception {
		this.dsServiceLtsService.updateWaiveQcApprovalStatus(orderId, this.user, status);
	}

	private void updateApprovalStatus(String orderId, String status) throws Exception {
		this.amendLtsService.updateApprovalStatus(orderId, this.user, status);
	}
	
	private void updateTaskStatus(String status) throws Exception {
		this.activityStatusService.updateStatus(this.activityDTO, this.activityTaskDTO, status, this.user);
	}

	private void updateActvStatus(String status) throws Exception {
		this.activityStatusService.updateStatus(this.activityDTO, status, this.user);
	}
	
	private ActivityTaskDTO[] retrieveTaskByType(ActivityDTO activityDTO, String taskType, boolean isPending) {
		ActivityTaskDTO[] activityTaskDTO = activityDTO.getTasksByType(taskType, isPending);
		if (activityTaskDTO!=null && activityTaskDTO.length>0) {
			return activityTaskDTO;
		}
		return null;
	}
	
	private ActivityTaskDTO[] getTasksByTypeFilterOutStatus(ActivityDTO activityDTO, String taskType) {
		String[] filterOutStatus = {LtsActvBackendConstant.ACTV_TASK_STATUS_COMPLETED, LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED};
		ActivityTaskDTO[] activityTaskDTO = activityDTO.getTasksByTypeFilterOutStatus(taskType, filterOutStatus); 
		if (activityTaskDTO!=null && activityTaskDTO.length>0) {
			return activityTaskDTO;
		}
		return null;
	}
	
	private String getMaxTaskSeqFromTaskList(ActivityTaskDTO[] taskList) {
		ArrayList<Integer> taskSeqList = new ArrayList<Integer>();
		for (int i=0; i<taskList.length; i++) {
			taskSeqList.add(Integer.parseInt(taskList[i].getTaskSeq()));
		}
		Integer maxTaskSeq = Collections.max(taskSeqList);
		return Integer.toString(maxTaskSeq);
	}
	
	/**
	 * @return the retrieveLtsActivityService
	 */
	public RetrieveLtsActivityService getRetrieveLtsActivityService() {
		return retrieveLtsActivityService;
	}

	/**
	 * @param retrieveLtsActivityService the retrieveLtsActivityService to set
	 */
	public void setRetrieveLtsActivityService(
			RetrieveLtsActivityService retrieveLtsActivityService) {
		this.retrieveLtsActivityService = retrieveLtsActivityService;
	}

	/**
	 * @return the activityStatusService
	 */
	public ActivityStatusService getActivityStatusService() {
		return activityStatusService;
	}

	/**
	 * @param activityStatusService the activityStatusService to set
	 */
	public void setActivityStatusService(ActivityStatusService activityStatusService) {
		this.activityStatusService = activityStatusService;
	}

	/**
	 * @return the pipbOrderResumeSubmitService
	 */
	public PipbOrderResumeSubmitService getPipbOrderResumeSubmitService() {
		return pipbOrderResumeSubmitService;
	}

	/**
	 * @param pipbOrderResumeSubmitService the pipbOrderResumeSubmitService to set
	 */
	public void setPipbOrderResumeSubmitService(
			PipbOrderResumeSubmitService pipbOrderResumeSubmitService) {
		this.pipbOrderResumeSubmitService = pipbOrderResumeSubmitService;
	}

	/**
	 * @return the pipbActivityLtsSubmissionService
	 */
	public PipbActivityLtsSubmissionService getPipbActivityLtsSubmissionService() {
		return pipbActivityLtsSubmissionService;
	}

	/**
	 * @param pipbActivityLtsSubmissionService the pipbActivityLtsSubmissionService to set
	 */
	public void setPipbActivityLtsSubmissionService(
			PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService) {
		this.pipbActivityLtsSubmissionService = pipbActivityLtsSubmissionService;
	}

	/**
	 * @return the amendLtsService
	 */
	public AmendLtsService getAmendLtsService() {
		return amendLtsService;
	}

	/**
	 * @param amendLtsService the amendLtsService to set
	 */
	public void setAmendLtsService(AmendLtsService amendLtsService) {
		this.amendLtsService = amendLtsService;
	}

	public DsServiceLtsService getDsServiceLtsService() {
		return dsServiceLtsService;
	}

	public void setDsServiceLtsService(DsServiceLtsService dsServiceLtsService) {
		this.dsServiceLtsService = dsServiceLtsService;
	}

	public LtsDsOrderApprovalSubmitService getLtsDsOrderApprovalSubmitService() {
		return ltsDsOrderApprovalSubmitService;
	}

	public void setLtsDsOrderApprovalSubmitService(
			LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService) {
		this.ltsDsOrderApprovalSubmitService = ltsDsOrderApprovalSubmitService;
	}

	public WqNatureLkupService getWqNatureLkupService() {
		return wqNatureLkupService;
	}

	public void setWqNatureLkupService(WqNatureLkupService wqNatureLkupService) {
		this.wqNatureLkupService = wqNatureLkupService;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}
	
}
