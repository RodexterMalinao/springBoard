package com.bomwebportal.mob.ccs.service;

import com.bomwebportal.mob.ccs.dto.ApprovalLogDTO;

public interface MobCcsApprovalLogService {
	String getNextAuthorizedId();
	int insertApprovalLogDTO(ApprovalLogDTO dto);
	public int updateApprovalLog(String orderId, String authorizedId, String lastUpdBy);
	public boolean isApproval(String orderId, String actionType);
	public boolean isApprovedBasket(String orderId, String basketId, String authorizedAction);
}
