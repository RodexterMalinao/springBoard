package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.RecontractLtsDAO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class RecontractLtsServiceImpl extends ServiceActionImplBase {
	
	public RecontractLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		RecontractLtsDTO recontractLts = new RecontractLtsDTO();
		recontractLts.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, recontractLts);
		return recontractLts;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		RecontractLtsDTO recontractLts = (RecontractLtsDTO)pBaseDTO;
		RecontractLtsDAO recontractLtsDao = (RecontractLtsDAO)this.dao;
		this.DTO2DAO(recontractLts, recontractLtsDao);
		recontractLtsDao.setOrderId((String)args[0]);
		recontractLtsDao.setDtlId((String)args[1]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((RecontractLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
