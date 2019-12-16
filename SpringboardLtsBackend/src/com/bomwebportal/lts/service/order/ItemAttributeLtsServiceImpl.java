package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ItemAttributeLtsDAO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ItemAttributeLtsServiceImpl extends ServiceActionImplBase {
	
	public ItemAttributeLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "srvItemSeq", "attbCd"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ItemAttributeDetailLtsDTO itemAttb = new ItemAttributeDetailLtsDTO();
		itemAttb.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, itemAttb);
		return itemAttb;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ItemAttributeDetailLtsDTO itemAttb = (ItemAttributeDetailLtsDTO)pBaseDTO;
		ItemAttributeLtsDAO itemAttbDao = (ItemAttributeLtsDAO)this.dao;
		this.DTO2DAO(itemAttb, itemAttbDao);
		itemAttbDao.setOrderId((String)args[0]);
		itemAttbDao.setDtlId((String)args[1]);
		itemAttbDao.setSrvItemSeq((String)args[2]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ItemAttributeLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
