package com.activity.service;

import java.util.Map;

import com.activity.dto.ActivityAttachDocDTO;
import com.activity.dto.ActivityAttributeDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.dto.DocumentDTO;
import com.bomwebportal.exception.AppRuntimeException;

public class ActivityTaskFactory {

	public static final String PARM_TYPE_ATTB = "ATTB";
	public static final String PARM_TYPE_DOC = "DOC";
	public static final String PARM_TYPE_ATTH_DOC = "ATTH_DOC";
	public static final String PARM_TYPE_ASSIGNEE = "ASSGN";
	
	
	public ActivityTaskDTO createTask(String pTaskType, Class<ActivityTaskDTO> pClass, Map<String, ?> pParmMap) {
		
		ActivityTaskDTO task = null;
		
		try {
			task = pClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new AppRuntimeException(e.getMessage(), e);
		}
		task.setTaskType(pTaskType);
		
		if (pParmMap != null && pParmMap.size() > 0) {
			task.setAssignee((String)pParmMap.get(PARM_TYPE_ASSIGNEE));
			task.setTaskAttbs((ActivityAttributeDTO[])pParmMap.get(PARM_TYPE_ATTB));
			task.setAttachDocs((ActivityAttachDocDTO[])pParmMap.get(PARM_TYPE_ATTH_DOC));
			task.setDocuments((DocumentDTO[])pParmMap.get(PARM_TYPE_DOC));	
		}
				
		return task;
	}
}
