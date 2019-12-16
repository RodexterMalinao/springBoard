package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.AddressLtsDAO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class AddressLtsServiceImpl extends ServiceActionImplBase {
	
	public AddressLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		AddressDetailLtsDTO address = new AddressDetailLtsDTO();
		address.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, address);
		return address;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		AddressDetailLtsDTO address = (AddressDetailLtsDTO)pBaseDTO;
		AddressLtsDAO addrDao = (AddressLtsDAO)this.dao;
		this.DTO2DAO(address, addrDao);
		addrDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((AddressLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
