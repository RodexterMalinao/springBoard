package com.bomwebportal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.ReleaseLockDAO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;

public class ReleaseLockServiceImpl implements ReleaseLockService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	
	
	ReleaseLockDAO releaseLockDao;
	
	MobCcsOrderDAO mobCcsOrderDao;

	/**
	 * @return the mobCcsOrderDao
	 */
	public MobCcsOrderDAO getMobCcsOrderDao() {
		return mobCcsOrderDao;
	}

	/**
	 * @param mobCcsOrderDao the mobCcsOrderDao to set
	 */
	public void setMobCcsOrderDao(MobCcsOrderDAO mobCcsOrderDao) {
		this.mobCcsOrderDao = mobCcsOrderDao;
	}

	public ReleaseLockDAO getReleaseLockDao() {
		return releaseLockDao;
	}

	public void setReleaseLockDao(ReleaseLockDAO releaseLockDao) {
		this.releaseLockDao = releaseLockDao;
	}

	public OrderDTO getOrderLockInfo(String orderId) {
		
		try {
			return releaseLockDao.getOrderLockInfo(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderLockInfo(String orderId)", e);
		}
		return null;
	}

	public int updateOrderLockInd(String orderId, String lockInd, String lastUpdateBy) {
		
		if ("Y".equalsIgnoreCase(lockInd)) {
			try {
				return releaseLockDao.lockOrder(orderId, lastUpdateBy);
			} catch (DAOException e) {
				logger.error("Exception caught in lockOrder(String orderId, String lastUpdateBy)", e);
			}
		} else {
			try {
				return releaseLockDao.releaseLockOrder(orderId, lastUpdateBy);
			} catch (DAOException e) {
				logger.error("Exception caught in releaseLockOrder(String orderId, String lastUpdateBy)", e);
			}
		}
		
		return 0;
	}

	public String[] checkAuthority(String orderId, String staffId) {
		try {
			return mobCcsOrderDao.checkAuthority(orderId, staffId);
		} catch (DAOException e) {
			logger.error("Exception caught in checkAuthority (String orderId, String staffId)", e);
		}
		return null;
	}
	
	public LockResult getOrderLockInfo(String orderId, String staffId) {
		
		LockResult result=null;
		OrderDTO orderLockInfo = getOrderLockInfo(orderId);

		if ("Y".equals(orderLockInfo.getLockInd())){
			if (!staffId.equals(orderLockInfo.getLastUpdateBy())){
				result =LockResult.LOCKED_BY_OTHER_USER;
			}else{
				result =LockResult.LOCKED_BY_YOURSELF;
			}
		}else{
			result =LockResult.LOCK_FREE;
		}
		return result;
	}

}
