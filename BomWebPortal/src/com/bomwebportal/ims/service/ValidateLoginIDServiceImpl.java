package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ValidateLoginIDDAO;

@Transactional(readOnly=true)
public class ValidateLoginIDServiceImpl implements ValidateLoginIDService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ValidateLoginIDDAO validateLoginIDDao;
	
	public ValidateLoginIDDAO getValidateLoginIDDao() {
		return validateLoginIDDao;
	}

	public void setValidateLoginIDDao(ValidateLoginIDDAO validateLoginIDDao) {
		this.validateLoginIDDao = validateLoginIDDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public boolean validateLoginID(String loginName){
		try{
			logger.info("validateLoginID"+"["+loginName+"]");
			return validateLoginIDDao.validateLoginID(loginName);
		}catch(DAOException de) {
			logger.error("Exception caught in validateLoginID()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String validateLoginIDError(String loginName){
		try{
			logger.info("validateLoginID"+"["+loginName+"]");
			return validateLoginIDDao.validateLoginIDError(loginName);
		}catch(DAOException de) {
			logger.error("Exception caught in validateLoginIDError()", de);
			throw new AppRuntimeException(de);
		}
	}
		
}
