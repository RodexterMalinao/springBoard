package com.bomwebportal.service;

import org.springframework.transaction.annotation.Transactional;

public interface SbLockService {

	public abstract void initializeLock(String pId, String pType, String pUserId);

	@Transactional
	public abstract boolean lockOrder(String pId, String pType,
			String pSessionId, String pUserId, StringBuilder pMsgSb);

	public abstract void releaseLockById(String pId, String pType,
			String pUserId);

	public abstract void releaseLockByLocker(String pLockBy, String pUserId);

	public abstract void releaseLockBySession(String pSessionId, String pUserId);
}