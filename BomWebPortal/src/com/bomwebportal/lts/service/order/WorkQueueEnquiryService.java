package com.bomwebportal.lts.service.order;

public interface WorkQueueEnquiryService {

	public abstract String getWorkQueueStatus(String pOrderId, String pBatchId,
			String pDtlId, String pNature);

}