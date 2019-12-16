package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.AccountServiceAssignLtsDAO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class AccountServiceAssignLtsServiceImpl extends ServiceActionImplBase {

	public AccountServiceAssignLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "acctNo", "action", "chrg_type"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		AccountServiceAssignLtsDTO acctSrv = new AccountServiceAssignLtsDTO();
		acctSrv.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, acctSrv);
		return acctSrv;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		AccountServiceAssignLtsDTO acctSrv = (AccountServiceAssignLtsDTO)pBaseDTO;
		AccountServiceAssignLtsDAO acctSrvDao = (AccountServiceAssignLtsDAO)this.dao;
		this.DTO2DAO(acctSrv, acctSrvDao);
		acctSrvDao.setOrderId((String)args[0]);
		acctSrvDao.setDtlId((String)args[1]);
		acctSrvDao.setAcctNo(acctSrv.getAccount().getAcctNo());
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((AccountServiceAssignLtsDAO)this.dao).setOrderId((String)pArgs[0]);
		((AccountServiceAssignLtsDAO)this.dao).setDtlId((String)pArgs[1]);
	}
}
