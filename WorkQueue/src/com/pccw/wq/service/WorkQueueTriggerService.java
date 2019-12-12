package com.pccw.wq.service;

import com.pccw.wq.schema.dto.WorkQueueTriggerDTO;

public interface WorkQueueTriggerService {

	public static final String HNDL_METHOD_SEND_EMAIL = "SEND_EMAIL";
	public static final String HNDL_METHOD_ROUTE_WQ = "ROUTE_WQ";
	public static final String HNDL_METHOD_CHANGE_STATUS = "CHANGE_STATUS";
	public static final String HNDL_METHOD_ACK_SPRINGBOARD = "ACK_SPRINGBOARD";
	public static final String HNDL_METHOD_HOLD_N_TRANSFER = "HOLD_N_TRANSFER";
	public static final String HNDL_METHOD_RESUME_ON_HOLD = "RESUME_ON_HOLD";
	public static final String HNDL_METHOD_CHANGE_ON_HOLD_WQ_NATURE = "CHANGE_ON_HOLD_WQ_NATURE";
	public static final String HNDL_METHOD_FOLLOWUP_REPLIED = "FOLLOWUP_REPLIED";

	public void handleTrigger(WorkQueueTriggerDTO pWorkQueueTrigger, String pUserId) throws Exception;

	public void triggerHoldAndTransfer(WorkQueueTriggerDTO[] pWorkQueueTriggers, String pUser) throws Exception;
}