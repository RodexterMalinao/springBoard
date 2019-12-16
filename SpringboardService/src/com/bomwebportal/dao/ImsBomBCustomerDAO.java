package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.ImsCustomerDTO;
import com.bomwebportal.exception.DAOException;

public class ImsBomBCustomerDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String getCustNumByCustInfo (ImsCustomerDTO cust) throws DAOException {
		logger.debug("getCustNumByCustInfo");
		//logger.debug("getFirstName: "+cust.getFirstName());
		//logger.debug("getLastName: "+cust.getLastName());
		logger.debug("getID_DOC_NUM: "+cust.getID_DOC_NUM());
		logger.debug("getID_DOC_TYPE: "+cust.getID_DOC_TYPE());
	
		String SQL=	"select cust_num from b_customer " +
				" where system_Id='IMS' " +
				" and id_doc_type='" + cust.getID_DOC_TYPE() +"' " +
				" and id_doc_num='" + cust.getID_DOC_NUM() +"'" ;
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String Cust_num = rs.getString("cust_num");
		        return Cust_num;
		    }
		};

		List<String> CustNum = new ArrayList<String>();
		
		try {
			logger.debug("getCustNumByCustInfo @ ImsBomBCustomerDAO: " + SQL);
			CustNum = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			CustNum = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getCustNumByCustInfo @ ImsBomBCustomerDAO():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (CustNum.size()>0 ? CustNum.get(0):null);
	}

	
	public String imsDSBlackListAddressSettled (String pSbId) throws DAOException {
		logger.debug("imsDSBlackListAddressSettled, pSbId:"+pSbId);
	
		String SQL=	"select cust_num from b_customer " +
				" where system_Id='IMS' " +
				" and id_doc_type='" + pSbId +"' " ;
	
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String Cust_num = rs.getString("cust_num");
		        return Cust_num;
		    }
		};

		List<String> CustNum = new ArrayList<String>();
		
		try {
			logger.debug("getCustNumByCustInfo @ ImsBomBCustomerDAO: " + SQL);
			CustNum = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			CustNum = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getCustNumByCustInfo @ ImsBomBCustomerDAO():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (CustNum.size()>0 ? CustNum.get(0):null);
	}
	
//	public String getCustNumBy_count_star_ID (ImsCustomerDTO cust) throws DAOException {
//		logger.debug("getCustNumBy_count_star_ID");
//		logger.debug("getID_DOC_NUM: "+cust.getID_DOC_NUM());
//	
//		String SQL=	"SELECT CUST_NUM  FROM b_customer " +
//				"where ID_DOC_NUM = '" + cust.getID_DOC_NUM() +"'" ;
//	
//		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
//		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
//		    	String Cust_num = rs.getString("cust_num");
//		        return Cust_num;
//		    }
//		};
//
//		List<String> CustNum = new ArrayList<String>();
//		
//		try {
//			logger.debug("getCustNumBy_count_star_ID @ ImsBomBCustomerDAO: " + SQL);
//			CustNum = simpleJdbcTemplate.query(SQL, mapper);
//		} catch (EmptyResultDataAccessException erdae) {
//			logger.debug("EmptyResultDataAccessException");
//			CustNum = null;
//		} catch (Exception e) {
//			logger.debug("Exception caught in getCustNumBy_count_star_ID @ ImsBomBCustomerDAO():", e);
//			throw new DAOException(e.getMessage(), e);
//		}	
//		return (CustNum.size()>0 ? CustNum.get(0):null);
//	}
}
