package com.bomwebportal.lts.dao.acq;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class LtsAcqCustomerDetailDAO extends BaseDAO {
	
	private static final String SQL_UPDATE_BOMWEB_CUSTOMER_CUST_NO = 
		"UPDATE BOMWEB_CUSTOMER SET CUST_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_UPDATE_BOMWEB_CUST_OPTOUT_INFO_CUST_NO = 
		"UPDATE BOMWEB_CUST_OPTOUT_INFO SET CUST_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_UPDATE_BOMWEB_CONTACT_CUST_NO = 
		"UPDATE BOMWEB_CONTACT SET CUST_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_UPDATE_BOMWEB_ACCT_CUST_NO = 
		"UPDATE BOMWEB_ACCT SET CUST_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_UPDATE_BOMWEB_PAYMENT_CUST_NO = 
		"UPDATE BOMWEB_PAYMENT SET CUST_NO = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? ";
	
	private static final String SQL_GET_BOM_DUMMY_CUST_NUM_SEQ = 
			"select bomweb_dummy_cust_num_seq.nextval cust_num " +
			"from dual ";


	public void updateDummyCustNum(String sbOrderId, String custNum, String userId) throws DAOException {
		try {
			updateCustomerInfoCustNum(sbOrderId, custNum, userId);
			updateCustomerOptoutInfoCustNum(sbOrderId, custNum, userId);
			updateContactInfoCustNum(sbOrderId, custNum, userId);
			updateAccountInfoCustNum(sbOrderId, custNum, userId);
			updatePaymentInfoCustNum(sbOrderId, custNum, userId);
		} catch (Exception e) {
			logger.error("Exception caught in updateDummyCustNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateCustomerInfoCustNum(String sbOrderId, String custNum, String userId) throws DAOException {
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_CUSTOMER_CUST_NO, custNum, userId, sbOrderId);			
		} catch (Exception e) {
			logger.error("Exception caught in updateCustomerInfoCustNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateCustomerOptoutInfoCustNum(String sbOrderId, String custNum, String userId) throws DAOException {
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_CUST_OPTOUT_INFO_CUST_NO, custNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateCustomerOptoutInfoCustNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateContactInfoCustNum(String sbOrderId, String custNum, String userId) throws DAOException {
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_CONTACT_CUST_NO, custNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateContactInfoCustNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateAccountInfoCustNum(String sbOrderId, String custNum, String userId) throws DAOException {
		try {			
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_ACCT_CUST_NO, custNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updateAccountInfoCustNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updatePaymentInfoCustNum(String sbOrderId, String custNum, String userId) throws DAOException {
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_BOMWEB_PAYMENT_CUST_NO, custNum, userId, sbOrderId);
		} catch (Exception e) {
			logger.error("Exception caught in updatePaymentInfoCustNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getBomDummyCustNumSeq() throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {				
				return rs.getString("CUST_NUM");
			}
		};
		try {
			List<String> itemList = this.simpleJdbcTemplate.query(SQL_GET_BOM_DUMMY_CUST_NUM_SEQ, mapper);
			return itemList.isEmpty() ? "0" : itemList.get(0);
		}
		catch (Exception e) {
			logger.error("Error in getBomDummyCustNumSeq - " , e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
