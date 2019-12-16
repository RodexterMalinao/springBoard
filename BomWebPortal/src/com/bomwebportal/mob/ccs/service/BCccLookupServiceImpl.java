package com.bomwebportal.mob.ccs.service;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CccLkupDTO;
import com.bomwebportal.mob.dao.bom.BCccLookupDAO;


@Transactional(readOnly=true)
public class BCccLookupServiceImpl implements BCccLookupService {

	private BCccLookupDAO bCccLookupDAO;
	
	
	public BCccLookupDAO getbCccLookupDAO() {
		return bCccLookupDAO;
	}


	public void setbCccLookupDAO(BCccLookupDAO bCccLookupDAO) {
		this.bCccLookupDAO = bCccLookupDAO;
	}


	public CccLkupDTO getCccLkup(String ccc) {
		try {
			return bCccLookupDAO.getCccLkup(ccc);
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
}
