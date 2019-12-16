package com.bomwebportal.lts.dao.acq;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class LtsAcqAccountDetailDAO extends BaseDAO {
	
	private static final String SQL_UPDATE_BOMWEB_ACCT_NO = 
		"UPDATE BOMWEB_ACCT SET ACCT_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_UPDATE_BOMWEB_PAYMENT_ACCT_NO = 
		"UPDATE BOMWEB_PAYMENT SET ACCT_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_UPDATE_BOMWEB_SERVICE_ACCT_ASSGN_ACCT_NO = 
		"UPDATE BOMWEB_SERVICE_ACCT_ASSGN SET ACCT_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_UPDATE_BOMWEB_BILLING_ADDR_ACCT_NO = 
		"UPDATE BOMWEB_BILLING_ADDR SET ACCT_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_GET_BOM_DUMMY_ACCT_NUM_SEQ = 
			"select bomweb_dummy_acct_num_seq.nextval acct_num " +
			"from dual ";

	public void updateDummyAcctNum(String sbOrderId, String acctNum, String userId) throws DAOException {
		try {
			updateAccountInfoAcctNum(sbOrderId, acctNum, userId);
			updatePaymentInfoAcctNum(sbOrderId, acctNum, userId);
			updateServiceAccountAssignAcctNum(sbOrderId, acctNum, userId);
			updateBillingAddressAcctNum(sbOrderId, acctNum, userId);
		} catch (Exception e) {
			logger.error("Exception caught in updateDummyAcctNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateAccountInfoAcctNum(String sbOrderId, String acctNum, String userId) throws DAOException {
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_ACCT_NO, acctNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateAccountInfoAcctNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updatePaymentInfoAcctNum(String sbOrderId, String acctNum, String userId) throws DAOException {
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_PAYMENT_ACCT_NO, acctNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updatePaymentInfoAcctNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateServiceAccountAssignAcctNum(String sbOrderId, String acctNum, String userId) throws DAOException {
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_SERVICE_ACCT_ASSGN_ACCT_NO, acctNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateServiceAccountAssignAcctNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateBillingAddressAcctNum(String sbOrderId, String acctNum, String userId) throws DAOException {
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_BILLING_ADDR_ACCT_NO, acctNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateBillingAddressAcctNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getBomDummyAcctNumSeq() throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {				
				return rs.getString("ACCT_NUM");
			}
		};
		try {
			List<String> itemList = this.simpleJdbcTemplate.query(SQL_GET_BOM_DUMMY_ACCT_NUM_SEQ, mapper);
			return itemList.isEmpty() ? "0" : itemList.get(0);
		}
		catch (Exception e) {
			logger.error("Error in getBomDummyAcctNumSeq - " , e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
