package com.bomwebportal.lts.service.activity;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.service.ActivityWorkQueueServiceImpl;
import com.bomwebportal.lts.dto.activity.LtsActivityDTO;
import com.bomwebportal.lts.dto.activity.LtsActivityTaskDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;

public class LtsActivityWorkQueueServiceImpl extends ActivityWorkQueueServiceImpl implements LtsActivityWorkQueueService {

	
	public void preSubmitWorkQueue(WorkQueueDTO pWorkQueueDTO, ActivityDTO pActivityDTO, ActivityTaskDTO pTask) {
		if (pWorkQueueDTO == null || pTask == null){
			return;
		}
		if (pActivityDTO instanceof LtsActivityDTO) {
			pWorkQueueDTO.setTypeOfService(((LtsActivityDTO)pActivityDTO).getSrvType());
			pWorkQueueDTO.setServiceId(((LtsActivityDTO)pActivityDTO).getSrvNum());
		}
		if (pTask instanceof LtsActivityTaskDTO) {
			pWorkQueueDTO.setTypeOfService(((LtsActivityTaskDTO)pTask).getSrvType());
			pWorkQueueDTO.setServiceId(((LtsActivityTaskDTO)pTask).getSrvNum());
			pWorkQueueDTO.setRelatedSrvType(((LtsActivityTaskDTO)pTask).getRelatedSrvType());
			pWorkQueueDTO.setRelatedSrvNum(((LtsActivityTaskDTO)pTask).getRelatedSrvNum());
			pWorkQueueDTO.setSrd(((LtsActivityTaskDTO)pTask).getSrd());
		}
	}
}