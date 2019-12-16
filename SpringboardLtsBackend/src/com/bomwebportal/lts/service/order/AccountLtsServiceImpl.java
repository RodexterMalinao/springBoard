package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.AccountLtsDAO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class AccountLtsServiceImpl extends ServiceActionImplBase {
	
	public AccountLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "acctNo"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		AccountDetailLtsDTO account = new AccountDetailLtsDTO();
		account.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, account);
		return account;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		AccountDetailLtsDTO account = (AccountDetailLtsDTO)pBaseDTO;
		AccountLtsDAO accountDao = (AccountLtsDAO)this.dao;
		this.DTO2DAO(account, accountDao);
		accountDao.setOrderId((String)args[0]);
		accountDao.setCustNo((String)args[1]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((AccountLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
