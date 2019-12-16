package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.GetImsCustomerDAO;
import com.bomwebportal.ims.dao.CheckImsCustomerDAO;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;

@Transactional(readOnly=true)
public class GetImsCustomerServiceImpl implements GetImsCustomerService{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private GetImsCustomerDAO getImsCustomerDao;
	private CheckImsCustomerDAO checkImsCustomerDao;

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsInstallationUI> getImsCustomer(String idDocType, String idDocNum){
		logger.debug("getImsCustomer is called");
		
		try{			
			return getImsCustomerDao.getImsCustomer(idDocType, idDocNum);
		}catch (DAOException de){
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String checkImsCustomer(String idDocType, String idDocNum){
		logger.debug("checkImsCustomer is called");
		
		try{
			return checkImsCustomerDao.checkImsCustomer(idDocType, idDocNum);
		}catch (DAOException de){
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String checkImsCustomerNowTV(String idDocType, String idDocNum){
		logger.debug("checkImsCustomerNowTV is called");
		
		try{
			return checkImsCustomerDao.checkImsCustomerNowTV(idDocType, idDocNum);
		}catch (DAOException de){
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String checkImsDataPrivacy(String idDocType, String idDocNum, String bomLob){
		logger.debug("checkImsDataPrivacy is called");
		
		try{
			return checkImsCustomerDao.checkImsDataPrivacy(idDocType, idDocNum, bomLob);
		}catch (DAOException de){
			throw new AppRuntimeException(de);
		}
	}

	public GetImsCustomerDAO getGetImsCustomerDao() {
		return getImsCustomerDao;
	}

	public void setGetImsCustomerDao(GetImsCustomerDAO getImsCustomerDao) {
		this.getImsCustomerDao = getImsCustomerDao;
	}

	public CheckImsCustomerDAO getCheckImsCustomerDao() {
		return checkImsCustomerDao;
	}

	public void setCheckImsCustomerDao(CheckImsCustomerDAO checkImsCustomerDao) {
		this.checkImsCustomerDao = checkImsCustomerDao;
	}
	
}
