package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.WqNatureLkupDAO;

public class WqNatureLkupServiceImpl implements WqNatureLkupService {

	private WqNatureLkupDAO wqNatureLkupDao;
	private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public String getNatureType(String wqNatureId) throws DAOException {
		try{
			return wqNatureLkupDao.getNatureType(wqNatureId);
		}catch (DAOException de) {
			logger.error("Fail in WqNatureLkupService.getNatureType()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public WqNatureLkupDAO getWqNatureLkupDao() {
		return wqNatureLkupDao;
	}

	public void setWqNatureLkupDao(WqNatureLkupDAO wqNatureLkupDao) {
		this.wqNatureLkupDao = wqNatureLkupDao;
	}

}
