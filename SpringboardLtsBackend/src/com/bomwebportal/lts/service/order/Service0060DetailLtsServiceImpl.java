package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.Service0060DetailLtsDAO;
import com.bomwebportal.lts.dto.order.Service0060DetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class Service0060DetailLtsServiceImpl extends ServiceActionImplBase {
	
	public Service0060DetailLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "typeOfSrv", "datCd", "srvNum"));
	}
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		Service0060DetailLtsDTO srv0060Dtl = new Service0060DetailLtsDTO();
		srv0060Dtl.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, srv0060Dtl);
		return srv0060Dtl;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		Service0060DetailLtsDTO srv0060Dtl = (Service0060DetailLtsDTO)pBaseDTO;
		Service0060DetailLtsDAO srv0060DtlDao = (Service0060DetailLtsDAO)this.dao;
		this.DTO2DAO(srv0060Dtl, srv0060DtlDao);
		srv0060DtlDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((Service0060DetailLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}

}
