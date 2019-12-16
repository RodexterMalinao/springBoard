package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;


import com.activity.dao.dataAccess.ActivityTaskDAO;
import com.activity.dto.ActivityTaskDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ActivityTaskServiceImpl extends ServiceActionImplBase {

	public ActivityTaskServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ActivityTaskDTO task = (ActivityTaskDTO)this.convertToDto(new ActivityTaskDTO(), pDaoBase);
		task.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return task;
	}
	
	public ObjectActionBaseDTO convertToDto(ObjectActionBaseDTO pObject, DaoBase pDaoBase) {
		super.convertToDto(pObject, pDaoBase);
		pObject.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return pObject;
	}
	
	protected void setDAO2DTODetails(Object... pArgs) {
		((ActivityTaskDAO)this.dao).setActvId((String)pArgs[0]);
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ActivityTaskDTO task = (ActivityTaskDTO)pBaseDTO;
		ActivityTaskDAO taskDao = (ActivityTaskDAO)this.dao;
		this.DTO2DAO(task, taskDao);
		taskDao.setActvId((String)args[0]);
		taskDao.setCompletionDate(DateFormatHelper.dateConvertFromDTO2DAO(task.getCompletionDate()));
		taskDao.setCancellationDate(DateFormatHelper.dateConvertFromDTO2DAO(task.getCancellationDate()));
	}
}
