package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.PrepayLtsDAO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class PrepayLtsServiceImpl extends ServiceActionImplBase {

	public PrepayLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		PrepayLtsDTO prepay = new PrepayLtsDTO();
		prepay.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, prepay);
		return prepay;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		PrepayLtsDAO prepayDao = (PrepayLtsDAO)this.dao;
		this.DTO2DAO(pBaseDTO, prepayDao);
		prepayDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((PrepayLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
