package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ValidateAccountNumDAO;

@Transactional(readOnly=true)
public class ValidateAccountNumServiceImpl implements ValidateAccountNumService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ValidateAccountNumDAO validateAccountNumDao;

	public ValidateAccountNumDAO getValidateAccountNumDao() {
		return validateAccountNumDao;
	}

	public void setValidateAccountNumDao(ValidateAccountNumDAO validateAccountNumDao) {
		this.validateAccountNumDao = validateAccountNumDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public int validateAccountNum(long acctnb, String srccode){
		try{
			logger.info("validateAccountNum() is called in ValidateAccountNumServiceImpl.java");
			return validateAccountNumDao.validateAccountNum(acctnb, srccode);
		}catch(DAOException de) {
			logger.error("Exception caught in validateAccountNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String validateAccountNumError(long acctnb, String srccode){
		try{
			logger.info("validateAccountNumError() is called in ValidateAccountNumServiceImpl.java");
			return validateAccountNumDao.validateAccountNumError(acctnb, srccode);
		}catch(DAOException de) {
			logger.error("Exception caught in validateAccountNumError()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String validateEmailAvailable(String loginID) {
		try{
			logger.info("validateEmailAvailable() is called in ValidateAccountNumServiceImpl.java");
			return validateAccountNumDao.validateEmailAvailable(loginID);		
		}catch(DAOException de) {
			logger.error("Exception caught in validateEmailAvailable()", de);
			throw new AppRuntimeException(de);
		}
	}
		
}
