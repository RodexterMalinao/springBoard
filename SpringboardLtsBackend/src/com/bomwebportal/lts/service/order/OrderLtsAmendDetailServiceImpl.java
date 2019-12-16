package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.OrderLtsAmendDetailDAO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDetailDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class OrderLtsAmendDetailServiceImpl extends ServiceActionImplBase {

	public OrderLtsAmendDetailServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "batchSeq"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "batchSeq"));
	}
	
	@Override
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		OrderLtsAmendDetailDTO orderLtsAmendDetailDTO = new OrderLtsAmendDetailDTO();
		orderLtsAmendDetailDTO.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, orderLtsAmendDetailDTO);
		return orderLtsAmendDetailDTO;
	}

	@Override
	protected void setDAO2DTODetails(Object... args) {
		((OrderLtsAmendDetailDAO)this.dao).setOrderId((String)args[0]);
		((OrderLtsAmendDetailDAO)this.dao).setDtlId((String)args[1]);
		((OrderLtsAmendDetailDAO)this.dao).setBatchSeq((String)args[2]);
	}

	@Override
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {		
		OrderLtsAmendDetailDTO orderLtsAmendDetailDTO = (OrderLtsAmendDetailDTO)pBaseDTO;
		OrderLtsAmendDetailDAO orderLtsAmendDetailDAO = (OrderLtsAmendDetailDAO)this.dao;
		this.DTO2DAO(orderLtsAmendDetailDTO, orderLtsAmendDetailDAO);
		orderLtsAmendDetailDAO.setOrderId((String)args[0]);
		orderLtsAmendDetailDAO.setDtlId((String)args[1]);
		orderLtsAmendDetailDAO.setBatchSeq((String)args[2]);
		orderLtsAmendDetailDAO.setItemSeq((String)args[3]);
		orderLtsAmendDetailDAO.setItemSubSeq((String)args[4]);
	}

}
