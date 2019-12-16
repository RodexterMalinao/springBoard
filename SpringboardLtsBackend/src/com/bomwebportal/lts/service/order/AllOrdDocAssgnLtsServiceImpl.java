package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.AllOrdDocAssgnLtsDAO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class AllOrdDocAssgnLtsServiceImpl extends ServiceActionImplBase {

	public AllOrdDocAssgnLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "docType"));
	}
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		AllOrdDocAssgnLtsDTO allOrdDocAssgn = new AllOrdDocAssgnLtsDTO();
		allOrdDocAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, allOrdDocAssgn);
		return allOrdDocAssgn;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		AllOrdDocAssgnLtsDTO allOrdDocAssgn = (AllOrdDocAssgnLtsDTO)pBaseDTO;
		AllOrdDocAssgnLtsDAO allOrdDocAssgnDao = (AllOrdDocAssgnLtsDAO)this.dao;
		this.DTO2DAO(allOrdDocAssgn, allOrdDocAssgnDao);
		allOrdDocAssgnDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((AllOrdDocAssgnLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
