package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsDsOrderApprovalSubmitService {
	public boolean resumeSignOffDsOrder(SbOrderDTO sbOrder, String username);
	public boolean resumeSignOffDsOrder(String orderId, String username);
	public boolean checkCustNameNotMatchCase(SbOrderDTO sbOrder);
	public boolean checkAndSubmitMustQcOrder(SbOrderDTO sbOrder, String username);
	public boolean submitOrderForCustNameNotMatch(SbOrderDTO sbOrder, String username);
	
}
