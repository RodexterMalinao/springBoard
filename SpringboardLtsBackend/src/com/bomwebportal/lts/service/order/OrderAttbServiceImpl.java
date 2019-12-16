package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.OrderAttbDAO;
import com.bomwebportal.lts.dto.order.OrderAttbDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class OrderAttbServiceImpl extends ServiceActionImplBase {

	public OrderAttbServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "attbId"));
	}
	
	@Override
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO,
			Object... args) {
		OrderAttbDTO orderAttbDto = (OrderAttbDTO)pBaseDTO;
		OrderAttbDAO orderAttbDao = (OrderAttbDAO)this.dao;
		this.DTO2DAO(orderAttbDto, orderAttbDao);
		orderAttbDao.setOrderId((String)args[0]);
		orderAttbDao.setDtlId((String)args[1]);
	}

	@Override
	protected void setDAO2DTODetails(Object... args) {
		((OrderAttbDAO)this.dao).setOrderId((String)args[0]);
	}

	@Override
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		OrderAttbDTO orderAttb = new OrderAttbDTO();
		orderAttb.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, orderAttb);
		return orderAttb;
	}

}
