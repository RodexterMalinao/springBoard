package com.activity.service.dataAccess;

import java.util.ArrayList;
import java.util.Arrays;

import com.activity.dao.dataAccess.ActivityCustomerDtlDAO;
import com.activity.dto.ActivityAddressDTO;
import com.activity.dto.ActivityCustomerDtlDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ActivityCustomerDtlServiceImpl extends ServiceActionImplBase {

	public ActivityCustomerDtlServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("actvId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("actvId", "custNo"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		ActivityCustomerDtlDTO dto = (ActivityCustomerDtlDTO)this.convertToDto(new ActivityCustomerDtlDTO(), pDaoBase);
		dto.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);	
		return dto;
	}
		
	protected void setDAO2DTODetails(Object... pArgs) {
		((ActivityCustomerDtlDAO)this.dao).setActvId((String)pArgs[0]);
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ActivityCustomerDtlDAO customerDtlDao = (ActivityCustomerDtlDAO)this.dao;
		this.DTO2DAO(pBaseDTO, customerDtlDao);
		customerDtlDao.setActvId((String)args[0]);
	}

}
