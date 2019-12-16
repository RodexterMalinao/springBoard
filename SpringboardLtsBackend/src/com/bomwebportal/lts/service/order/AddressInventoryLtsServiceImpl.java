package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.AddressInventoryLtsDAO;
import com.bomwebportal.lts.dto.order.AddressInventoryDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class AddressInventoryLtsServiceImpl extends ServiceActionImplBase {
	
	public AddressInventoryLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		AddressInventoryDetailLtsDTO addrInventory = new AddressInventoryDetailLtsDTO();
		addrInventory.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, addrInventory);
		return addrInventory;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {

		AddressInventoryDetailLtsDTO addrInventory = (AddressInventoryDetailLtsDTO)pBaseDTO;
		AddressInventoryLtsDAO addrInventoryDao = (AddressInventoryLtsDAO)this.dao;
		this.DTO2DAO(addrInventory, addrInventoryDao);
		addrInventoryDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((AddressInventoryLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
