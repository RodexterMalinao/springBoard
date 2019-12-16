package com.activity.service.dataAccess;

import org.apache.commons.lang.StringUtils;

import com.activity.dao.dataAccess.ActivityStatusDAO;
import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityStatusDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.service.ActivityWorkQueueService;
import com.activity.util.ActivityConstants;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.pccw.util.spring.SpringApplicationContext;

public class ActivityStatusServiceImpl implements ActivityStatusService {

	private ActivityStatusDAO activityStatusSLVDao = null;
	
	@Override
	public void initializeStatus(String pActvId, ActivityTaskDTO pTask, String pUser) {
		
		try {
			this.activityStatusSLVDao.insertStatus(pActvId, pTask.getTaskSeq(), pUser);
			pTask.setStatusHistory(this.getStatusHistory(pActvId, pTask.getTaskSeq()));
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to initialize status task_id " + pTask.getTaskSeq(), ex);
		}
	}
	
	@Override
	public void initializeStatus(ActivityDTO pActv, String pUser) {
		
		try {
			this.activityStatusSLVDao.insertStatus(pActv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ, pUser);
			pActv.setStatusHistory(this.getStatusHistory(pActv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ));
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to initialize status " + pActv.getActvId(), ex);
		}
	}
	
	@Override
	public void updateStatus(ActivityDTO pActivity, ActivityTaskDTO pTask, String pStatus, String pUser) throws Exception {
		updateStatus(pActivity, pTask, pStatus, pUser, false);
	}
	
	@Override
	public void updateStatus(ActivityDTO pActivity, ActivityTaskDTO pTask, String pStatus, String pUser, boolean pIsSkipInvokeTrigger) throws Exception {
		updateStatus(pActivity, pTask, pStatus, pUser, pIsSkipInvokeTrigger, null);
	}
	
	@Override
	public void updateStatus(ActivityDTO pActivity, ActivityTaskDTO pTask,
			String pStatus, String pUser, 
			ActivityWorkQueueService pActivityWorkQueueService)
			throws Exception {
		updateStatus(pActivity, pTask, pStatus, pUser, false, pActivityWorkQueueService);
	}
	
	@Override
	public void updateStatus(ActivityDTO pActivity, ActivityTaskDTO pTask,
			String pStatus, String pUser, boolean pIsSkipInvokeTrigger,
			ActivityWorkQueueService pActivityWorkQueueService)
			throws Exception {
		
		try {
			ActivityStatusDTO actvStatus = this.activityStatusSLVDao.getLatestStatus(pActivity.getActvId(), pTask.getTaskSeq());
			if (actvStatus == null) {
				this.initializeStatus(pActivity.getActvId(), pTask, pUser);
			}
			if (!ActivityConstants.ACTV_TASK_STATUS_INITIAL.equals(pStatus)) {
				this.activityStatusSLVDao.updateActvStatus(pActivity.getActvId(), pTask.getTaskSeq(), pStatus, pUser);
			}
			pTask.setStatusHistory(this.getStatusHistory(pActivity.getActvId(), pTask.getTaskSeq()));
				if (	(actvStatus == null 
							|| (actvStatus != null && !StringUtils.equals(pStatus, actvStatus.getStatus()))) 
						&& !pIsSkipInvokeTrigger) {
				if (pActivityWorkQueueService == null) {
					pActivityWorkQueueService = SpringApplicationContext.getBean(ActivityWorkQueueService.class);
				}
				pActivityWorkQueueService.invokeWorkQueue(pActivity, pTask, pUser);
			}
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update pending status on task_id " + pTask.getTaskSeq(), ex);
		}
	}
	
	@Override
	public void updateStatus(ActivityDTO pActv, String pStatus, String pUser) throws Exception  {
		updateStatus(pActv, pStatus, pUser, null);
	}
	
	@Override
	public void updateStatus(ActivityDTO pActv, String pStatus, String pUser, ActivityWorkQueueService pActivityWorkQueueService) throws Exception  {
		try {
			ActivityStatusDTO actvStatus = this.activityStatusSLVDao.getLatestStatus(pActv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ);
			this.activityStatusSLVDao.updateActvStatus(pActv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ, pStatus, pUser);
			pActv.setStatusHistory(this.getStatusHistory(pActv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ));
			if (actvStatus == null 
					|| (actvStatus == null && !StringUtils.equals(pStatus, actvStatus.getStatus()))) {
				if (pActivityWorkQueueService == null) {
					pActivityWorkQueueService = SpringApplicationContext.getBean(ActivityWorkQueueService.class);
				}
				pActivityWorkQueueService.invokeWorkQueue(pActv, null, pUser);
			}
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update pending status on " + pActv.getActvId(), ex);
		}
	}

	public ActivityStatusDTO[] getLatestStatus(String pActvId){

		try {
			return this.activityStatusSLVDao.getLatestStatus(pActvId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to get status", ex);
		}
	}

	public ActivityStatusDTO[] getStatusHistory(String pActvId, String pTaskSeq) {

		try {
			return this.activityStatusSLVDao.getStatusHistory(pActvId, pTaskSeq);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to get status hist on task_id " + pTaskSeq, ex);
		}
	}

	public ActivityStatusDAO getActivityStatusSLVDao() {
		return activityStatusSLVDao;
	}

	public void setActivityStatusSLVDao(ActivityStatusDAO activityStatusSLVDao) {
		this.activityStatusSLVDao = activityStatusSLVDao;
	}
}
