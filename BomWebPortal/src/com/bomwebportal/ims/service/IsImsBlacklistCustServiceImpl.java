package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.IsImsBlacklistCustDAO;

@Transactional(readOnly=true)
public class IsImsBlacklistCustServiceImpl implements IsImsBlacklistCustService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private IsImsBlacklistCustDAO isImsBlacklistCustDao;
	
	public IsImsBlacklistCustDAO getIsImsBlacklistCustDao() {
		return isImsBlacklistCustDao;
	}

	public void setIsImsBlacklistCustDao(IsImsBlacklistCustDAO isImsBlacklistCustDao) {
		this.isImsBlacklistCustDao = isImsBlacklistCustDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public boolean isImsBlacklistCust(String idDocType, String idDocNum){
		try{
			logger.info("isImsBlacklistCust() is called in IsImsBlacklistCustServiceImpl.java");
			return isImsBlacklistCustDao.isImsBlacklistCust(idDocType, idDocNum);
		}catch(DAOException de) {
			logger.error("Exception caught in isImsBlacklistCust()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<String> getImsOsBalanceAcct(String idDocType, String idDocNum){
		try{
			logger.info("getImsOsBalanceAcct() is called in IsImsBlacklistCustServiceImpl.java");
			return isImsBlacklistCustDao.getImsOsBalanceAcct(idDocType, idDocNum);
		}catch(DAOException de) {
			logger.error("Exception caught in getImsOsBalanceAcct()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String getImsOsBalance(String acctNum){
		try{
			logger.info("getImsOsBalance() is called in IsImsBlacklistCustServiceImpl.java");
			return isImsBlacklistCustDao.getImsOsBalance(acctNum);
		}catch(DAOException de) {
			logger.error("Exception caught in getImsOsBalance()", de);
			throw new AppRuntimeException(de);
		}
	}
}