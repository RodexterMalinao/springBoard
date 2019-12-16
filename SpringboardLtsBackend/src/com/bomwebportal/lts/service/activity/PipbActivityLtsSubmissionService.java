package com.bomwebportal.lts.service.activity;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.util.LtsActvBackendConstant.ActvAction;

public interface PipbActivityLtsSubmissionService {
	
	public abstract void submitPipbActivity(SbOrderDTO sbOrderDTO, ServiceDetailDTO srvDtls, String pUser, ActvAction actvAction) throws Exception;
		
	public abstract void createPipbRequest(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createPipbReject(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createPipbCancel(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createPipbAmendment(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createAPNUpdate(String sbOrderId, String pUser, String statusCd) throws Exception;
	
	public abstract void restoreDeferredSRD(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createDnFollowup(String sbOrderId, String pUser, String remark, ActvAction actvAction) throws Exception;
		
	public abstract void createPipbRejectWithDeferSRD(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createPipbRejectWithInvalidAppointment(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createCancelSalesAlert(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createPipbCancelWithAcknowledge(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createAmendmentApproval(String sbOrderId, String pUser) throws Exception;
	
	public abstract boolean isTaskExistByType(String sbOrderId, String pUser, String actvType, String taskType) throws Exception;
	
	public abstract boolean isStatusRejectByType(String sbOrderId, String pUser, String actvType, String taskType) throws Exception;
		
	public abstract void updateWorkQueueWithSRD(SbOrderDTO sbOrderDTO, String pUser) throws Exception;
	
	public abstract void updateWorkQueueWithSRDByType(SbOrderDTO sbOrderDTO, String pUser, String actvType) throws Exception;
	
	public abstract void createDsWaiveQc(String sbOrderId, String pUser) throws Exception;
	
	public abstract void createDsAmendment(String sbOrderId, String pUser, String remarks) throws Exception;
	
	public abstract void createCustNameNotMatchApproval(String sbOrderId, String pUser, String remarks) throws Exception;
}
