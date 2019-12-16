package com.activity.service.dataAccess;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityStatusDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.service.ActivityWorkQueueService;

public interface ActivityStatusService {

	public abstract void initializeStatus(String pActvId, ActivityTaskDTO pTask, String pUser);
	
	public abstract void initializeStatus(ActivityDTO pActv, String pUser);

	public abstract void updateStatus(ActivityDTO pActivity, ActivityTaskDTO pTask, String pStatus, String pUser) throws Exception ;

	public abstract void updateStatus(ActivityDTO pActivity, ActivityTaskDTO pTask, String pStatus, String pUser, boolean pIsSkipInvokeTrigger) throws Exception ;
	
	public abstract void updateStatus(ActivityDTO pActivity,
			ActivityTaskDTO pTask, String pStatus, String pUser,
			ActivityWorkQueueService pActivityWorkQueueService)
			throws Exception;
	
	public abstract void updateStatus(ActivityDTO pActivity, ActivityTaskDTO pTask,
			String pStatus, String pUser, boolean pIsSkipInvokeTrigger,
			ActivityWorkQueueService pActivityWorkQueueService)
			throws Exception;
	
	public abstract void updateStatus(ActivityDTO pActv, String pStatus, String pUser, ActivityWorkQueueService pActivityWorkQueueService) throws Exception;
	
	public abstract void updateStatus(ActivityDTO pActv, String pStatus, String pUser) throws Exception;

	public abstract ActivityStatusDTO[] getLatestStatus(String pActvId);

	public abstract ActivityStatusDTO[] getStatusHistory(String pActvId, String pTaskSeq);

}