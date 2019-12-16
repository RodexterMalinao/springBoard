package com.bomwebportal.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.LoginDAO;
import com.bomwebportal.dao.ShopDAO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LoginLogDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

@Transactional(readOnly=true)
public class LoginServiceImpl implements LoginService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginDAO loginDao;
	
	public LoginDAO getPaymentDao() {
		return loginDao;
	}

	public void setLoginDao(LoginDAO loginDao) {
		this.loginDao = loginDao;
	}
	
	private ShopDAO shopDao;
	public void setShopDao(ShopDAO shopDao) {
		this.shopDao = shopDao;
	}

	public ShopDAO getShopDao() {
		return shopDao;
	}


	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public boolean validateLogin (BomSalesUserDTO bomSalesUserDTO){
		try{
			logger.debug("validateLogin() is called in LoginServiceImpl.java");
			return loginDao.validateLogin(bomSalesUserDTO);
		}catch (DAOException de) {
			logger.error("Exception caught in validateLogin()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<String> getShopList(){

		try{
		
			logger.debug("getShopList() is called");
			return  shopDao.getShopList();
		}catch (DAOException de) {
			logger.error("Exception caught in getShopList()", de);
			throw new AppRuntimeException(de);
		}
		
	}

	// add by Joyce 20111026
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public BomSalesUserDTO getSalesAssign(String username) {
		try{
			logger.debug("getSalesAssign() is called");
			BomSalesUserDTO salesUser = shopDao.getSalesAssign(username);
			return  salesUser;
		}catch (Exception e) {
			logger.error("Exception caught in getSalesAssign()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	// add by Joyce 20111108
	// modified by Joyce 20111215, add pilot status to distinguish active shop(s) for IMS
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public BomSalesUserDTO getCentreCdFromTeamCd(String teamCd) {
		try{
			logger.debug("getCentreCdFromTeamCd() is called");
			return  shopDao.getCentreCdFromTeamCd(teamCd);
		}catch (Exception e) {
			logger.error("Exception caught in getCentreCdFromTeamCd()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public void updateSessionId(String staffId, String sessionId, String ipAddress) {
		try{
			logger.debug("updateSessionId() is called");
			shopDao.updateSessionId(staffId, sessionId);
			shopDao.insertLoginLog(staffId, sessionId, ipAddress);
		}catch (Exception e) {
			logger.error("Exception caught in updateSessionId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public String getDbRecordSessionId(String staffId) {
		String result="";
		try{
			logger.debug("getDbRecordSessionId() is called");
			result= shopDao.getDbRecordSessionId(staffId);
			
		}catch (Exception e) {
			logger.error("Exception caught in getDbRecordSessionId()", e);
			throw new AppRuntimeException(e);
		}
		return result;
	}

	@Override
	public LoginLogDTO getLoginLogDTO(String staffId, String sessionId) {
		try {
			if (logger.isInfoEnabled()) {
				logger.debug("getLoginLogDTO() is called");
			}
			return this.shopDao.getLoginLogDTO(staffId, sessionId);
		} catch (Exception e) {
			logger.error("Exception caught in getLoginLogDTO()", e);
			throw new AppRuntimeException(e);
		}
	}
	
}
