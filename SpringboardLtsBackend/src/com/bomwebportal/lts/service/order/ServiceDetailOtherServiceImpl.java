package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ServiceDetailOtherDAO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ServiceDetailOtherServiceImpl extends ServiceActionImplBase {

	public ServiceDetailOtherServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ServiceDetailOtherLtsDTO srvDtlOther = new ServiceDetailOtherLtsDTO();
		srvDtlOther.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, srvDtlOther);
		return srvDtlOther;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ServiceDetailOtherLtsDTO srvDtlOther = (ServiceDetailOtherLtsDTO)pBaseDTO;
		ServiceDetailOtherDAO srvDtlOtherDao = (ServiceDetailOtherDAO)this.dao;
		this.DTO2DAO(srvDtlOther, srvDtlOtherDao);
		srvDtlOtherDao.setOrderId((String)args[0]);
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ServiceDetailOtherDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
