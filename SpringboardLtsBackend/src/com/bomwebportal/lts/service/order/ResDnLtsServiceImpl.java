package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ResDnLtsDAO;
import com.bomwebportal.lts.dto.order.ResDnLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ResDnLtsServiceImpl extends ServiceActionImplBase {

	public ResDnLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		ResDnLtsDTO resDn = new ResDnLtsDTO();
		resDn.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, resDn);
		return resDn;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ResDnLtsDTO resDn = (ResDnLtsDTO)pBaseDTO;
		ResDnLtsDAO resDnDao = (ResDnLtsDAO)this.dao;
		this.DTO2DAO(resDn, resDnDao);
		resDnDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ResDnLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
