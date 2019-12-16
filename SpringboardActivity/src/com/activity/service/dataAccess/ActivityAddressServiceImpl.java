package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;

import com.activity.dao.dataAccess.ActivityAddressDAO;
import com.activity.dto.ActivityAddressDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ActivityAddressServiceImpl extends ServiceActionImplBase {

	public ActivityAddressServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "addrUsage"));
	}	
	
	@Override
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		ActivityAddressDTO doc = (ActivityAddressDTO)this.convertToDto(new ActivityAddressDTO(), pDaoBase);
		doc.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return doc;
	}	
	
	@Override
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO,
			Object... pArgs) {
		ActivityAddressDTO addr = (ActivityAddressDTO)pBaseDTO;
		ActivityAddressDAO addrDao = (ActivityAddressDAO)this.dao;
		this.DTO2DAO(addr, addrDao);
		addrDao.setActvId((String)pArgs[0]);

	}

	@Override
	protected void setDAO2DTODetails(Object... pArgs) {
		((ActivityAddressDAO)this.dao).setActvId((String)pArgs[0]);
	}
}
