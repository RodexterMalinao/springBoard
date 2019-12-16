package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ServiceDetailDAO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class ServiceDetailServiceImpl extends ServiceActionImplBase {

	public ServiceDetailServiceImpl() {
		selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId"));
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		return null;
	}
	
	public ServiceDetailDTO convertToDto(ServiceDetailDTO pSrvDtl, DaoBase pDaoBase) {
		
		ServiceDetailDAO srvDtlDao = (ServiceDetailDAO)pDaoBase;
		this.DAO2DTO(pDaoBase, pSrvDtl);
		pSrvDtl.setSuggestSrd(DateFormatHelper.dateConvertFromDAO2DTO(srvDtlDao.getSuggestSrd()));
		pSrvDtl.setCeaseRentalDate(DateFormatHelper.dateConvertFromDAO2DTO(srvDtlDao.getCeaseRentalDate()));
		pSrvDtl.setBackDateApplnDate(DateFormatHelper.dateConvertFromDAO2DTO(srvDtlDao.getBackDateApplnDate()));
		pSrvDtl.setPendingOcid(null);
		pSrvDtl.setPendingOcidSrd(null);
		return pSrvDtl;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		ServiceDetailDTO srvDtl = (ServiceDetailDTO)pBaseDTO;
		ServiceDetailDAO srvDtlDao = (ServiceDetailDAO)this.dao;
		this.DTO2DAO(srvDtl, srvDtlDao);
		srvDtlDao.setOrderId((String)args[0]);
		srvDtlDao.setAcctNo((String)args[1]);
		srvDtlDao.setSuggestSrd(DateFormatHelper.dateConvertFromDTO2DAO(srvDtl.getSuggestSrd()));
		srvDtlDao.setPendingOcidSrd(DateFormatHelper.dateConvertFromDTO2DAO(srvDtl.getPendingOcidSrd()));
		srvDtlDao.setCeaseRentalDate(DateFormatHelper.dateConvertFromDTO2DAO(srvDtl.getCeaseRentalDate()));
		srvDtlDao.setBackDateApplnDate(DateFormatHelper.dateConvertFromDTO2DAO(srvDtl.getBackDateApplnDate()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ServiceDetailDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
