package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;


import com.activity.dao.dataAccess.ActivityAttachDocDAO;
import com.activity.dto.ActivityAttachDocDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ActivityAttachDocServiceImpl extends ServiceActionImplBase {

	public ActivityAttachDocServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "taskSeq", "docActvId", "docType", "docSeqNum"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ActivityAttachDocDTO doc = (ActivityAttachDocDTO)this.convertToDto(new ActivityAttachDocDTO(), pDaoBase);
		doc.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return doc;
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ActivityAttachDocDAO)this.dao).setActvId((String)pArgs[0]);
		((ActivityAttachDocDAO)this.dao).setTaskSeq((String)pArgs[1]);
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ActivityAttachDocDTO doc = (ActivityAttachDocDTO)pBaseDTO;
		ActivityAttachDocDAO docDao = (ActivityAttachDocDAO)this.dao;
		this.DTO2DAO(doc, docDao);
		docDao.setActvId((String)args[0]);
		docDao.setTaskSeq((String)args[1]);
	}
}
