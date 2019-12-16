package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;

import com.activity.dao.dataAccess.ActivityAttributeDAO;
import com.activity.dto.ActivityAttributeDTO;
import com.bomwebportal.dto.AttributeDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ActivityAttributeServiceImpl extends ServiceActionImplBase {

	public ActivityAttributeServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq", "attbId"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ActivityAttributeDTO attb = (ActivityAttributeDTO)this.convertToDto(new ActivityAttributeDTO(), pDaoBase);
		attb.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return attb;
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ActivityAttributeDAO)this.dao).setActvId((String)pArgs[0]);
		((ActivityAttributeDAO)this.dao).setTaskSeq((String)pArgs[1]);
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ActivityAttributeDTO attb = (ActivityAttributeDTO)pBaseDTO;
		ActivityAttributeDAO attbDao = (ActivityAttributeDAO)this.dao;
		this.DTO2DAO(attb, attbDao);
		attbDao.setActvId((String)args[0]);
		attbDao.setTaskSeq((String)args[1]);
	}
}
