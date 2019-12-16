package com.activity.service;

import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;


public interface ActivitySubmissionService {

	@Transactional
	public abstract boolean submitActivity(ActivityDTO pActv, String pAction, String pUser, StringBuffer pMsgSb) throws Exception;

	@Transactional
	public abstract void createTask(ActivityDTO pActv, ActivityTaskDTO pTask, String pUser, StringBuffer pMsgSb) throws Exception;

	@Transactional
	public abstract boolean completeActivity(ActivityDTO pActv, String pUser, String pRemark, StringBuffer pMsgSb) throws Exception;
//
//	@Transactional
//	public abstract void cancelActivity(String pActvId, String pUser);

	@Transactional
	public void requestApproval(ActivityDTO pActv, ActivityTaskDTO pApprovalTask, String pApprovalTaskSystemRemark, String pUser, StringBuffer pMsgSb) throws Exception;

	@Transactional
	public void submitApproval(ActivityDTO pActv, String pApprovalTaskType, boolean pIsApprove, String pRemark,	String pUser, StringBuffer pSb) throws Exception;

	@Transactional
	public void addActvTaskRemarks(String pRemarkContent, String pUser, ActivityDTO pActvDTO, String pTaskSeq);

}