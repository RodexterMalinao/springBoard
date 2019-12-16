package com.bomwebportal.service.lts.activity;

public interface WorkQueueStatusChangeService {
	
	public void statusChangedAction(String pWqWpAssgnId, String pSbId, String pSbDtlId, 
			String pSbActvId, String pWqStatus, String[] pWqNatureIds, String pRemarks, String pUserId) throws Exception;

}
