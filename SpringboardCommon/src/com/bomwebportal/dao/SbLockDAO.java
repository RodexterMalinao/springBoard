package com.bomwebportal.dao;

import com.bomwebportal.dto.LockDTO;
import com.bomwebportal.exception.DAOException;

public interface SbLockDAO {

	// NOT TO CALL DIRECTLY
	public abstract void insertLock(String pId, String pType, String pUserId)
			throws DAOException;

	// NOT TO CALL DIRECTLY
	public abstract LockDTO getLockBy(String pId, String pType, String pUserId) throws DAOException;

	// NOT TO CALL DIRECTLY
	public abstract void updateLocker(String pId, String pType,
			String pSessionId, String pLockBy, String pUserId)
			throws DAOException;

	// NOT TO CALL DIRECTLY
	public abstract void releaseLockByLocker(String pLockBy, String pUserId)
			throws DAOException;

	// NOT TO CALL DIRECTLY
	public abstract void releaseLockBySession(String pSessionId, String pUserId) throws DAOException;
	
	public abstract String getLockerInfo(String pLocker) throws Exception;
}