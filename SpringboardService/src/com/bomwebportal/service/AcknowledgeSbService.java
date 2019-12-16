package com.bomwebportal.service;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.ImsWqDTO;

public interface AcknowledgeSbService {

	@Transactional(rollbackFor = { Exception.class })
	public void imsVerDocApproved(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsVerDocFailed(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void sbOrderApprovalWqApproved(String pSbId, String pSbDtlId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void sbOrderApprovalWqRejected(String pSbId, String pSbDtlId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void sbOrderFullWqCompleted(String pSbId, String pSbDtlId,
			String pOcId) throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void onlineSalesDiffNameSignOff(String pSbId, String pSbDtlId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void onlineSalesDiffNameCancel(String pSbId, String pSbDtlId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void onlineSalesCOrderClosed(String pSbId, String pSbDtlId, String pOcId)
			throws Exception;

	
	@Transactional(rollbackFor = { Exception.class })
	public void onlineSalesLtsDiffNameSignOff (String pSbId)
			throws Exception;
	
	
	@Transactional(rollbackFor = { Exception.class })
	public void onlineSalesLtsDiffNameCancel (String pSbId, String pSbDtlId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void updateCOrderIgnored(String pSbId, String pSbDtlId, String pOcId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSWaiveQCAlertSettled(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSMisMatchSignoff(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSMisMatchCancel(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSBlackListAddressSettled(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSFSPendingSettled(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSFSPendingCancelled(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSWaiveQCAlertCancelled(String pSbId)
			throws Exception;

	@Transactional(rollbackFor = { Exception.class })
	public void imsDSCashPrepaySettled(String pSbId)
			throws Exception;
	
	@Transactional(rollbackFor = { Exception.class })
	public void wqStatusChanged(String pWqWpAssgnId, String pSbId,
			String pSbDtlId, String pSbActvId, String pWqStatus,
			String[] pWqNatureIds, String pRemarks,
			String pUserId) throws Exception;
	
//	@Transactional(rollbackFor = { Exception.class })
//	public ImsWqDTO getImsCOrderInfo(String orderId)
//			throws Exception;

}