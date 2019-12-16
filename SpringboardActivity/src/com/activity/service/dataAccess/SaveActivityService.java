package com.activity.service.dataAccess;

import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;


public interface SaveActivityService {

	@Transactional
	public abstract void saveActivity(ActivityDTO pActv, String pUser);

	@Transactional
	public abstract void saveActivityTask(String pActvId,
			ActivityTaskDTO pTask, String pUser);

}