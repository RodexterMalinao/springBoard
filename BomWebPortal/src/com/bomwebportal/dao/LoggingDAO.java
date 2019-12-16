package com.bomwebportal.dao;

import com.bomwebportal.exception.DAOException;

public class LoggingDAO extends BaseDAO {

	private static final String SQL_INSERT_CDAL_LOG = 
		"insert into SVC_CDAL_LOG (DATE_KEY, ID, FUNCTION_ID, KEY_TYPE, KEY_VALUE, CREATED_BY, CREATION_DATE, LAST_UPDATED_BY, LAST_UPDATED_DATE) values " +
		"(1, SVC_CDAL_LOG_SEQ.nextval, ?, ?, ?, ?, sysdate, ?, sysdate)";
	
	
	public void logAction(String pUserId, String pFuncName, String pKeyValue, String pKeyType) throws DAOException {
		
		try {
			this.simpleJdbcTemplate.update(SQL_INSERT_CDAL_LOG, pFuncName, pKeyType, pKeyValue, pUserId, pUserId);
		} catch (Exception e) {
			logger.error("Error in logCdal()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
