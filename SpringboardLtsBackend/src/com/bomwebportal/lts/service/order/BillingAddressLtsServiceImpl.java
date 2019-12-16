package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.BillingAddressLtsDAO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class BillingAddressLtsServiceImpl extends ServiceActionImplBase {

	public BillingAddressLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "acctNo"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		BillingAddressLtsDTO billingAddr = new BillingAddressLtsDTO();
		billingAddr.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, billingAddr);
		return billingAddr;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		BillingAddressLtsDAO billingAddrDao = (BillingAddressLtsDAO)this.dao;
		this.DTO2DAO(pBaseDTO, billingAddrDao);
		billingAddrDao.setOrderId((String)args[0]);
		billingAddrDao.setAcctNo((String)args[1]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((BillingAddressLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
