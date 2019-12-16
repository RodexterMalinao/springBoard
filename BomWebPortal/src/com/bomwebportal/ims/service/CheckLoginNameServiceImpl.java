package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.CheckLoginNameDAO;

@Transactional(readOnly=true)
public class CheckLoginNameServiceImpl implements CheckLoginNameService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CheckLoginNameDAO checkLoginNameDao;
	
	public CheckLoginNameDAO getCheckLoginNameDao() {
		return checkLoginNameDao;
	}

	public void setCheckLoginNameDao(CheckLoginNameDAO checkLoginNameDao) {
		this.checkLoginNameDao = checkLoginNameDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public boolean checkLoginName(String loginName){
		try{
//			logger.debug("checkLoginName"+"["+loginName+"]");
			return checkLoginNameDao.checkLoginName(loginName);
		}catch(DAOException de) {
			logger.error("Exception caught in checkLoginName()", de);
			throw new AppRuntimeException(de);
		}
	}
		
}
