package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.LtsDsOrderInfoDAO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class LtsDsOrderInfoServiceImpl extends ServiceActionImplBase {

	public LtsDsOrderInfoServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}
	
	@Override
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		LtsDsOrderInfoDTO info = new LtsDsOrderInfoDTO();
		info.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, info);
		return info;
	}

	@Override
	protected void setDAO2DTODetails(Object... args) {
		((LtsDsOrderInfoDAO)this.dao).setOrderId((String)args[0]);
	}

	@Override
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		LtsDsOrderInfoDTO info = (LtsDsOrderInfoDTO)pBaseDTO;
		LtsDsOrderInfoDAO infoDao = (LtsDsOrderInfoDAO)this.dao;
		this.DTO2DAO(info, infoDao);
		infoDao.setOrderId((String)args[0]);
	}

}
