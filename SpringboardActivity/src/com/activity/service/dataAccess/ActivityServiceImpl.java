package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;

import com.activity.dao.dataAccess.ActivityDAO;
import com.activity.dto.ActivityDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ActivityServiceImpl extends ServiceActionImplBase {

	public ActivityServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("actvId"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ActivityDTO actv = (ActivityDTO)this.convertToDto(new ActivityDTO(""), pDaoBase);
		actv.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);		
		return actv;
	}
	
	public ObjectActionBaseDTO convertToDto(ObjectActionBaseDTO pObject, DaoBase pDaoBase) {
		super.convertToDto(pObject, pDaoBase);
		pObject.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return pObject;
	}
	
	protected void setDAO2DTODetails(Object... pArgs) {
		((ActivityDAO)this.dao).setActvId((String)pArgs[0]);
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		this.DTO2DAO(pBaseDTO, this.dao);
	}
}
