package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.PipbLtsDAO;
import com.bomwebportal.lts.dao.order.ResDnLtsDAO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.order.ResDnLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class PipbLtsServiceImpl extends ServiceActionImplBase {

	public PipbLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		PipbLtsDTO pipb = new PipbLtsDTO();
		pipb.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, pipb);
		return pipb;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		PipbLtsDTO pipb = (PipbLtsDTO)pBaseDTO;
		PipbLtsDAO pipbDao = (PipbLtsDAO)this.dao;
		this.DTO2DAO(pipb, pipbDao);
		pipbDao.setOrderId((String)args[0]);
		pipbDao.setDtlId((String)args[1]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((PipbLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
