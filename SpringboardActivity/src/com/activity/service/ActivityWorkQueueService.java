package com.activity.service;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;

public interface ActivityWorkQueueService {

	public void invokeWorkQueue(ActivityDTO pActivity, ActivityTaskDTO pTask,
			String pUser) throws Exception;

	public void preSubmitWorkQueue(WorkQueueDTO pWorkQueueDTO,
			ActivityDTO pActivityDTO, ActivityTaskDTO pTask);

	public void postSubmitWorkQueue(WorkQueueDTO pWorkQueueDTO,
			ActivityDTO pActivityDTO, ActivityTaskDTO pTask);

}