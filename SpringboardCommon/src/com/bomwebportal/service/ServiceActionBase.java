package com.bomwebportal.service;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.pccw.util.db.DaoBase;

public interface ServiceActionBase {

	public abstract DaoBase[] doRetrieve(Object... args);

	public abstract ObjectActionBaseDTO convertToDto(DaoBase pDaoBase);
	
	public abstract ObjectActionBaseDTO convertToDto(ObjectActionBaseDTO pObject, DaoBase pDaoBase);

	public abstract void doSave(ObjectActionBaseDTO pBaseDTO, String pUser, Object... arg);

}