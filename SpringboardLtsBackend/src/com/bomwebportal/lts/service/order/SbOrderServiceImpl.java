package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.SbOrderLtsDAO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class SbOrderServiceImpl extends ServiceActionImplBase {

	public SbOrderServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		SbOrderDTO sbOrder = new SbOrderDTO();
		SbOrderLtsDAO sbOrderDao = (SbOrderLtsDAO)pDaoBase;
		sbOrder.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(sbOrderDao, sbOrder);
		sbOrder.setSignOffDate(DateFormatHelper.dateConvertFromDAO2DTO(sbOrderDao.getSignOffDate()));
		sbOrder.setAppDate(DateFormatHelper.dateConvertFromDAO2DTO(sbOrderDao.getAppDate()));
		sbOrder.setSalesProcessDate(DateFormatHelper.dateConvertFromDAO2DTO(sbOrderDao.getSalesProcessDate()));
		sbOrder.setSrvReqDate(DateFormatHelper.dateConvertFromDAO2DTO(sbOrderDao.getSrvReqDate()));
		return sbOrder;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		SbOrderDTO sbOrder = (SbOrderDTO)pBaseDTO;
		SbOrderLtsDAO sbOrderDao = (SbOrderLtsDAO)this.dao;
		this.DTO2DAO(sbOrder, sbOrderDao);
		sbOrderDao.setSignOffDate(DateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getSignOffDate()));
		sbOrderDao.setAppDate(DateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getAppDate()));
		sbOrderDao.setSalesProcessDate(DateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getSalesProcessDate()));
		sbOrderDao.setSrvReqDate(DateFormatHelper.dateConvertFromDTO2DAO(sbOrder.getSrvReqDate()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((SbOrderLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
