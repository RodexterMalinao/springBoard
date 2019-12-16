package com.bomwebportal.lts.service;

import com.bomwebportal.exception.DAOException;


public interface DsServiceLtsService {

	public abstract void updateWaiveQcApprovalStatus(String orderId, String pUser, String status) throws DAOException;

	public abstract void updateDsAmendCancelApprovalStatus(String orderId, String pUser, String status) throws DAOException;
	
	public abstract void updateDsNameNotMatchApprovalStatus(String orderId, String pUser, String status) throws DAOException;
	
}
