package com.bomwebportal.lts.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class DsServiceLtsDAO extends BaseDAO {
	private static final String SQL_UPDATE_WAIVE_QC_APPROVAL_STATUS = 
			"update BOMWEB_ORDER_LTS " +
			"set WAIVE_QC_STATUS = ?, " +
			"LAST_UPD_BY = ?, " + 
			"LAST_UPD_DATE = sysdate " +
			"where ORDER_ID = ? " +
		    "and WAIVE_QC_STATUS is NULL ";
		
	private static final String SQL_UPDATE_DS_AMEND_CANCEL_APPROVAL_STATUS = 
			"update BOMWEB_ORDER_LTS_AMEND " +
			"set APPROVAL_STATUS = ?, " +
			"LAST_UPD_BY = ?, " + 
			"LAST_UPD_DATE = sysdate " +
			"where ORDER_ID = ? " +
		    "and APPROVAL_STATUS is NULL ";
	
	private static final String SQL_UPDATE_DS_NAME_NOT_MATCH_APPROVAL_STATUS = 
			"update BOMWEB_ORDER_LTS " +
			"set NAME_MISMATCH_STATUS = ?, " +
			"LAST_UPD_BY = ?, " + 
			"LAST_UPD_DATE = sysdate " +
			"where ORDER_ID = ? ";

    public void updateWaiveQcApprovalStatus(String orderId, String pUser, String status) throws DAOException {		
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_WAIVE_QC_APPROVAL_STATUS, status, pUser, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in updateApprovalStatus - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    	
    public void updateDsAmendCancelApprovalStatus(String orderId, String pUser, String status) throws DAOException {		
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_DS_AMEND_CANCEL_APPROVAL_STATUS, status, pUser, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in updateApprovalStatus - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
    
    public void updateDsNameNotMatchApprovalStatus(String orderId, String pUser, String status) throws DAOException {		
    	try {			
			simpleJdbcTemplate.update(SQL_UPDATE_DS_NAME_NOT_MATCH_APPROVAL_STATUS, status, pUser, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in updateNameNotMatchApprovalStatus - ", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
}
