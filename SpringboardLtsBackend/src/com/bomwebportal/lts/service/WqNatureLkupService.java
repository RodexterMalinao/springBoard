package com.bomwebportal.lts.service;

import com.bomwebportal.exception.DAOException;

public interface WqNatureLkupService {

	String getNatureType(String wqNatureId) throws DAOException;
	
}
