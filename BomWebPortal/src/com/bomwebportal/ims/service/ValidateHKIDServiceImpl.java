package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ValidateHKIDDAO;

@Transactional(readOnly=true)
public class ValidateHKIDServiceImpl implements ValidateHKIDService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ValidateHKIDDAO validateHKIDDao;
	
	public ValidateHKIDDAO getValidateHKIDDao() {
		return validateHKIDDao;
	}

	public void setValidateHKIDDao(ValidateHKIDDAO validateHKIDDao) {
		this.validateHKIDDao = validateHKIDDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public boolean validateHKID(String hkid){
		try{
			logger.info("validateHKID() is called in ValidateHKIDServiceImpl.java");
			return validateHKIDDao.validateHKID(hkid);
		}catch(DAOException de) {
			logger.error("Exception caught in validateHKID()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String validateHKIDError(String hkid){
		try{
			logger.info("validateHKIDError() is called in ValidateHKIDServiceImpl.java");
			return validateHKIDDao.validateHKIDError(hkid);
		}catch(DAOException de) {
			logger.error("Exception caught in validateHKIDError()", de);
			throw new AppRuntimeException(de);
		}
	}
		
}
