package com.bomwebportal.mob.ccs.service;

public interface MobCcsCancelService {
	void cancelMobCcsOrder(String orderId, String codeId, String remark, String username);
	void cancelWithRecreateMobCcsOrder(String orderId, String codeId, String remark, String username, String newOrderId);
}
