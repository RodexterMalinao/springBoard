package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ValidateFixedLineDAO;

@Transactional(readOnly=true)
public class ValidateFixedLineServiceImpl implements ValidateFixedLineService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ValidateFixedLineDAO validateFixedLineDao;

	public ValidateFixedLineDAO getValidateFixedLineDao() {
		return validateFixedLineDao;
	}

	public void setValidateFixedLineDao(ValidateFixedLineDAO validateFixedLineDao) {
		this.validateFixedLineDao = validateFixedLineDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String validateFixedLine(String srvNum, String serbdyno, String unit, String floor){
		try{
			logger.info("validateFixedLine() is called in validateFixedLineServiceImpl.java");
			return validateFixedLineDao.validateFixedLine(srvNum, serbdyno, unit, floor);
		}catch(DAOException de) {
			logger.error("Exception caught in validateFixedLine()", de);
			throw new AppRuntimeException(de);
		}
	}		
}