package com.bomwebportal.lts.service.activity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.service.ActivitySubmissionServiceImpl;
import com.activity.service.ActivityWorkQueueService;
import com.activity.service.dataAccess.ActivityRemarkService;
import com.activity.util.ActivityConstants;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.pccw.util.spring.SpringApplicationContext;

public class LtsActivitySubmissionServiceImpl extends ActivitySubmissionServiceImpl {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private ActivityRemarkService activityRemarkService;
	
	@Transactional
	public boolean submitActivity(ActivityDTO pActv, String pAction, OrdDocDTO[] pDocList, String pUser, StringBuffer pMsgSb) throws Exception {
		logger.info("submitActivity - submitActivity actv Id: " + pActv.getActvId() + " Action: " + pAction);
		return super.submitActivity(pActv, pAction, pDocList, pUser, pMsgSb);
	}
	
	public void addActvTaskRemarks(String pRemarkContent, String pUser, ActivityDTO pActvDTO, String pTaskSeq){
		RemarkDTO[] remarks;
		ActivityTaskDTO task;
		activityRemarkService = SpringApplicationContext.getBean(ActivityRemarkService.class);
		this.activityRemarkService.saveCurrentActvStatusRemark(pRemarkContent, pUser, pActvDTO.getActvId(), pTaskSeq);
		
		if (! LtsActvBackendConstant.ACTV_LEVEL_SEQ.equals(pTaskSeq)){
			remarks = activityRemarkService.getRemarksByTaskSeq(pActvDTO.getActvId(), pTaskSeq);
			task = pActvDTO.getTaskBySeq(pTaskSeq);
			task.setTaskRemarks(remarks);
		}
	}
	
	@Override
	protected boolean determineTasks() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean preSubmit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean postSubmit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ActivityWorkQueueService getActivityWorkQueueService() {
		if (this.activityWorkQueueService == null) {
			return SpringApplicationContext.getBean(LtsActivityWorkQueueService.class);
		}
		return this.activityWorkQueueService;
	}	
	

}
