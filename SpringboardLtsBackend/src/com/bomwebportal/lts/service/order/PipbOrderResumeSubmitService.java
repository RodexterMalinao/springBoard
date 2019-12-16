package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface PipbOrderResumeSubmitService {
	public boolean resumeSignOffPipbOrder(String sbOrderId, String pUserId) throws Exception;
	public boolean resumeSignOffPipbOrder(SbOrderDTO sbOrder, String pUserId) throws Exception;
	
}
