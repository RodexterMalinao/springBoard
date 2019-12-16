package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.OrderStatusDTO;


public interface OrderStatusService {

	public abstract void initializeStatus(String pOrderId, String pDtlId, String pUser);

	public abstract void setClosedStatus(String pOrderId, String pDtlId, String pUser);
	
	public abstract void setSubmissionStatus(String pOrderId, String pDtlId,String pUser, String pWqType);

	public abstract void setCancelStatus(String pOrderId, String pDtlId, String pReaCd, String pUser, String pWqType);

	public abstract void setSuspendStatus(String pOrderId, String pDtlId, String pReaCd, String pUser, String pWqType);

	public abstract void setPendingBomStatus(String pOrderId, String pDtlId, String pUser, String pWqType);
	
	public abstract void setPendingApprovalStatus(String pOrderId, String pDtlId, String pUser, String pWqType);
	
	public abstract void setPendingSignoffStatus(String pOrderId, String pDtlId, String pUser);
	
	public abstract OrderStatusDTO[] getStatus(String pOrderId);
	
	public abstract OrderStatusDTO[] getStatusHistory(String pOrderId, String pDtlId);
	
	public abstract void setAwaitPrepaymentStatus(String pOrderId, String pDtlId, String pUser);
	
	public abstract void setAwaitQCStatus(String pOrderId, String pDtlId, String pUser, String pReaCd);
	
	public abstract void setPendingPrepaymentStatus(String pOrderId, String pDtlId, String pUser, String pWqType);
	
	public abstract void setPendingNameNotMatchApprovalStatus(String pOrderId, String pDtlId, String pUser, String pWqType);
	
	public abstract void setPendingQcStatus(String pOrderId, String pDtlId, String pUser, String pWqType);	
	
	public abstract String getSignoffDate(String pOrderId);
		
	public abstract String getPrepaymentSettleDate(String pOrderId);
	
	public abstract boolean noSuspendReason(String pOrderId, String pDtlId);
}