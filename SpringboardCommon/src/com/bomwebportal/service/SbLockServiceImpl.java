package com.bomwebportal.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.SbLockDAO;
import com.bomwebportal.dto.LockDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class SbLockServiceImpl implements SbLockService {

	private SbLockDAO sbLockDao = null;

	
	public void initializeLock(String pId, String pType, String pUserId) {
		
		try {
			this.sbLockDao.insertLock(pId, pType, pUserId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to initializeLock", ex);
		}
	}

	@Transactional
	public boolean lockOrder(String pId, String pType, String pSessionId, String pUserId, StringBuilder pMsgSb) {

		try {
			LockDTO lock = this.sbLockDao.getLockBy(pId, pType, pUserId);
			
			if (StringUtils.isNotBlank(lock.getLockBy()) && !StringUtils.equals(pUserId, lock.getLockBy())) {
				if (pMsgSb != null) {
					pMsgSb.append(pType);
					pMsgSb.append(" ");
					pMsgSb.append(pId);
					pMsgSb.append(" in use by ");
					pMsgSb.append(lock.getLockBy());
					pMsgSb.append(" ");
					pMsgSb.append(this.sbLockDao.getLockerInfo(lock.getLockBy()));
				}
				return false;
			}
			this.sbLockDao.updateLocker(pId, pType, pSessionId, pUserId, pUserId);
			
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public void releaseLockById(String pId, String pType, String pUserId) {
		
		try {
			this.sbLockDao.updateLocker(pId, pType, null, null, pUserId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to releaseLockById", ex);
		}
	}
	
	public void releaseLockByLocker(String pLockBy, String pUserId) {
		
		try {
			this.sbLockDao.releaseLockByLocker(pLockBy, pUserId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to releaseLockByLocker", ex);
		}
	}
	
	public void releaseLockBySession(String pSessionId, String pUserId) {
		
		try {
			this.sbLockDao.releaseLockBySession(pSessionId, pUserId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to releaseLockBySession", ex);
		}
	}

	public SbLockDAO getSbLockDao() {
		return sbLockDao;
	}

	public void setSbLockDao(SbLockDAO sbLockDao) {
		this.sbLockDao = sbLockDao;
	}
}
