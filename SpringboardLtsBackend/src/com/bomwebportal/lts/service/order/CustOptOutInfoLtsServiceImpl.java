package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.CustOptOutInfoLtsDAO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class CustOptOutInfoLtsServiceImpl extends ServiceActionImplBase {

	public CustOptOutInfoLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		CustOptOutInfoLtsDTO custOptOutInfo = new CustOptOutInfoLtsDTO();
		custOptOutInfo.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, custOptOutInfo);
		return custOptOutInfo;
	}

	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		CustOptOutInfoLtsDTO custOptOutInfo =(CustOptOutInfoLtsDTO)pBaseDTO;
		CustOptOutInfoLtsDAO custOptOutInfoDao = (CustOptOutInfoLtsDAO)this.dao;
		this.DTO2DAO(custOptOutInfo, custOptOutInfoDao);
		custOptOutInfoDao.setOrderId((String)args[0]);
		custOptOutInfoDao.setCustNo((String)args[1]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((CustOptOutInfoLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}

}
