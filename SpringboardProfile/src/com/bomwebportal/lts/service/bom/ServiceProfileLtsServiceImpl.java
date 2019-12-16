package com.bomwebportal.lts.service.bom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.ServiceProfileLtsDAO;
import com.bomwebportal.lts.dto.profile.AccountServiceLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupProfileDTO;
import com.bomwebportal.service.CodeLkupCacheService;

public class ServiceProfileLtsServiceImpl implements ServiceProfileLtsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ServiceProfileLtsDAO serviceProfileLtsDao;
	protected CodeLkupCacheService inventoryStatusCodeLkupCacheService;
		

	public PendingOrdStatusDetailDTO getPendingOrder(String pSrvNum, String pSrvType) {
		
		try {
			return this.serviceProfileLtsDao.getPendingOrder(pSrvNum, pSrvType);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getPendingOrderBySrvNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ServiceDetailProfileLtsDTO getSimpleServiceProfile(String pCcSrvId, String pSystemId) {
		
		try {
			ServiceDetailProfileLtsDTO srv = this.serviceProfileLtsDao.getSimpleServiceProfile(pCcSrvId, pSystemId);
			
			if (srv == null) {
				return null;
			}
			srv.setSrvGrp(this.serviceProfileLtsDao.getEyeGrp(pCcSrvId));
			srv.setInventStsDesc((String)this.inventoryStatusCodeLkupCacheService.get(srv.getInventStatus()));
			return srv;
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getSimpleServiceProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AccountServiceLtsDTO[] getServiceAccoutAssgn(String pCcSrvId, String pSystemId) {
		
		try {
			return this.serviceProfileLtsDao.getServiceAccoutAssgn(pCcSrvId, pSystemId);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getServiceAccoutAssgn()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ServiceDetailProfileLtsDTO getTerminatedServiceProfile(String pCcSrvId, String pSystemId) {
		
		try {
			ServiceDetailProfileLtsDTO srv = this.serviceProfileLtsDao.getTerminatedServiceProfile(pCcSrvId, pSystemId);
			
			if (srv == null) {
				return null;
			}
			srv.setSrvGrp(this.serviceProfileLtsDao.getTerminatedSrvEyeGrp(pCcSrvId));
			return srv;
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getTerminatedServiceProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ServiceGroupProfileDTO getEyeGrp(String pCcSrvId) {
		
		try {
			return this.serviceProfileLtsDao.getEyeGrp(pCcSrvId);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getEyeGrp()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ServiceDetailProfileLtsDTO[] getServiceProfileByCustomer(String pCustNum, String pSrvType) {
		
		try {
			return this.serviceProfileLtsDao.getServiceProfileByCustomer(pCustNum, pSrvType);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getServiceProfileByCustomer()", de);
			throw new AppRuntimeException(de);
		}
	}
	
    public ServiceDetailProfileLtsDTO getServiceProfileBySrvNum(String pSrvNum, String pSrvType) {
		
		try {
			return this.serviceProfileLtsDao.getServiceProfile(pSrvNum, pSrvType);
		} catch (DAOException de) {
			logger.error("Fail in ServiceProfileLtsService.getServiceProfileBySrvNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ServiceProfileLtsDAO getServiceProfileLtsDao() {
		return serviceProfileLtsDao;
	}

	public void setServiceProfileLtsDao(ServiceProfileLtsDAO serviceProfileLtsDao) {
		this.serviceProfileLtsDao = serviceProfileLtsDao;
	}	
	
	public CodeLkupCacheService getInventoryStatusCodeLkupCacheService() {
		return inventoryStatusCodeLkupCacheService;
	}

	public void setInventoryStatusCodeLkupCacheService(
			CodeLkupCacheService inventoryStatusCodeLkupCacheService) {
		this.inventoryStatusCodeLkupCacheService = inventoryStatusCodeLkupCacheService;
	}
}
