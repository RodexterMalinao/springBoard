package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.OrderLtsAmendDAO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class OrderLtsAmendServiceImpl extends ServiceActionImplBase {

	public OrderLtsAmendServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}
	
	@Override
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		OrderLtsAmendDTO orderLtsAmendDTO = new OrderLtsAmendDTO();
		orderLtsAmendDTO.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, orderLtsAmendDTO);
		return orderLtsAmendDTO;
	}

	@Override
	protected void setDAO2DTODetails(Object... args) {
		((OrderLtsAmendDAO)this.dao).setOrderId((String)args[0]);
	}

	@Override
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {		
		OrderLtsAmendDTO orderLtsAmendDTO = (OrderLtsAmendDTO)pBaseDTO;
		OrderLtsAmendDAO orderLtsAmendDAO = (OrderLtsAmendDAO)this.dao;
		this.DTO2DAO(orderLtsAmendDTO, orderLtsAmendDAO);
		orderLtsAmendDAO.setOrderId((String)args[0]);
		orderLtsAmendDAO.setDtlId((String)args[1]);
		orderLtsAmendDAO.setBatchSeq((String)args[2]);
	}

}
