package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ServiceDetailLtsDAO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ServiceDetailLtsServiceImpl extends ServiceActionImplBase {

	public ServiceDetailLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ServiceDetailLtsDTO srvLts = new ServiceDetailLtsDTO();
		srvLts.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(pDaoBase, srvLts);
		
		srvLts.setAdjustStartDate(DateFormatHelper.dateConvertFromDAO2DTO(((ServiceDetailLtsDAO)pDaoBase).getAdjustStartDate()));
		srvLts.setAdjustEndDate(DateFormatHelper.dateConvertFromDAO2DTO(((ServiceDetailLtsDAO)pDaoBase).getAdjustEndDate()));
		srvLts.setUpdateDnProfile(((ServiceDetailLtsDAO)pDaoBase).getUpdDnProfile());
		return srvLts;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ServiceDetailLtsDTO srvDtlLts = (ServiceDetailLtsDTO)pBaseDTO;
		ServiceDetailLtsDAO srvDtlLtsDao = (ServiceDetailLtsDAO)this.dao;
		this.DTO2DAO(srvDtlLts, srvDtlLtsDao);
		srvDtlLtsDao.setAdjustStartDate(DateFormatHelper.dateConvertFromDTO2DAO(srvDtlLts.getAdjustStartDate()));
		srvDtlLtsDao.setAdjustEndDate(DateFormatHelper.dateConvertFromDTO2DAO(srvDtlLts.getAdjustEndDate()));
		srvDtlLtsDao.setOrderId((String)args[0]);
		srvDtlLtsDao.setUpdDnProfile(srvDtlLts.getUpdateDnProfile());
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ServiceDetailLtsDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
