package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ItemLtsDAO;
import com.bomwebportal.lts.dao.order.ServiceCallPlanDetailLtsDAO;
import com.bomwebportal.lts.dto.order.Service0060DetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ServiceCallPlanDetailLtsServiceImpl extends ServiceActionImplBase {
	
	public ServiceCallPlanDetailLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "planCd", "ioInd", "effStartDate"));
	}
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
//		ServiceCallPlanDetailLtsDAO callPlanDtlDao = (ServiceCallPlanDetailLtsDAO)pDaoBase;
		ServiceCallPlanDetailLtsDTO srvCallPlanDtl = new ServiceCallPlanDetailLtsDTO();
		srvCallPlanDtl.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, srvCallPlanDtl);
//		srvCallPlanDtl.setEffStartDate(DateFormatHelper.dateConvertFromDAO2DTO(callPlanDtlDao.getEffStartDate()));
//		srvCallPlanDtl.setEffEndDate(DateFormatHelper.dateConvertFromDAO2DTO(callPlanDtlDao.getEffEndDate()));
		return srvCallPlanDtl;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ServiceCallPlanDetailLtsDTO srvCallPlanDtl = (ServiceCallPlanDetailLtsDTO)pBaseDTO;
		ServiceCallPlanDetailLtsDAO srvCallPlanDtlDao = (ServiceCallPlanDetailLtsDAO)this.dao;
		this.DTO2DAO(srvCallPlanDtl, srvCallPlanDtlDao);
		srvCallPlanDtlDao.setOrderId((String)args[0]);
		srvCallPlanDtlDao.setDtlId((String)args[1]);
		srvCallPlanDtlDao.setEffStartDate(DateFormatHelper.dateConvertFromDTO2DAO(srvCallPlanDtl.getEffStartDate()));
		srvCallPlanDtlDao.setEffEndDate(DateFormatHelper.dateConvertFromDTO2DAO(srvCallPlanDtl.getEffEndDate()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ServiceCallPlanDetailLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}

}
