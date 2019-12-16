package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.CustomerIguardRegDAO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class CustomerIguardRegServiceImpl extends ServiceActionImplBase {

	public CustomerIguardRegServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		CustomerIguardRegDTO custIguard = new CustomerIguardRegDTO();
		custIguard.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, custIguard);
		return custIguard;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		CustomerIguardRegDTO custIguard = (CustomerIguardRegDTO)pBaseDTO;
		CustomerIguardRegDAO custIguardDao = (CustomerIguardRegDAO)this.dao;
		this.DTO2DAO(custIguard, custIguardDao);
		custIguardDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((CustomerIguardRegDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
