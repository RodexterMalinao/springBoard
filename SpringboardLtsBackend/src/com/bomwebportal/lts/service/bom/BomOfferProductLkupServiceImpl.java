package com.bomwebportal.lts.service.bom;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.OfferProductLkupDAO;

public class BomOfferProductLkupServiceImpl implements BomOfferProductLkupService {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	private OfferProductLkupDAO offerProductLkupDao;
	
	public OfferProductLkupDAO getOfferProductLkupDao() {
		return offerProductLkupDao;
	}

	public void setOfferProductLkupDao(OfferProductLkupDAO offerProductLkupDao) {
		this.offerProductLkupDao = offerProductLkupDao;
	}

	public List<String> getRenewalDeviceOfferIdList()  {
		
		try {
			return offerProductLkupDao.getRenewalDeviceOfferIdList();
		} catch (DAOException de) {
			logger.error("Fail in BomOfferProductLkupService.getRenewalDeviceOfferIdList", de);
			throw new AppRuntimeException(de);
		}
	}


}
