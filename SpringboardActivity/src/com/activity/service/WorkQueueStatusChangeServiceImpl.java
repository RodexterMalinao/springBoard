package com.activity.service;

import com.activity.dao.ActivityInquiryDAO;
import com.bomwebportal.service.lts.activity.WorkQueueStatusChangeService;
import com.pccw.util.spring.SpringApplicationContext;

public class WorkQueueStatusChangeServiceImpl implements WorkQueueStatusChangeService {

	private String user;
	
	public void statusChangedAction(String pWqWpAssgnId, String pSbId, String pSbDtlId, 
			String pSbActvId, String pWqStatus, String[] pWqNatureIds, String pRemarks, String pUserId) throws Exception {
	
		String lob = SpringApplicationContext.getBean(ActivityInquiryDAO.class).getActvLob(pWqWpAssgnId, pSbId, pSbActvId);
		WorkQueueStatusChangeService wqStatusChangeService = SpringApplicationContext.getBean("workQueueStatusChangeService"+lob);
		wqStatusChangeService.statusChangedAction(pWqWpAssgnId, pSbId, pSbDtlId, pSbActvId, pWqStatus, pWqNatureIds, pRemarks, pUserId);	
	}

}
