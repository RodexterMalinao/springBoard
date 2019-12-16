package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class IsImsBlacklistCustDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean isImsBlacklistCust(String idDocType, String idDocNum) throws DAOException{
		logger.debug("isImsBlacklistCust is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			int blacklist;
			String SQL = "select count(1) from  blacklst a, b_customer b" +
                      " where a.CUSTNB =b.CUST_NUM" +
                      " and b.SYSTEM_ID='IMS'" +
                      " and reldate is null " +
                      " and id_doc_type= ? and id_doc_num = ?";

			blacklist = Integer.parseInt(simpleJdbcTemplate.queryForObject(SQL, String.class, idDocType, idDocNum));
			
			logger.info("blacklist: " + blacklist);
			
			return (blacklist==0?false:true);
		}catch (Exception e) {
			logger.error("Exception caught in checkloginName()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public List<String> getImsOsBalanceAcct(String idDocType, String idDocNum) throws DAOException{
		logger.debug("getImsOsBalanceAcct is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			List<String> acctNum;
			String SQL = "select acct_num from b_account where cust_num =" +
						 " (select distinct cust_num from  blacklst a, b_customer b" +
						 " where a.CUSTNB =b.CUST_NUM" +
						 " and b.SYSTEM_ID='IMS'" +
						 " and reldate is null" +
						 " and id_doc_type= ? and id_doc_num = ?)";
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					String acctnb=null;
								
					acctnb = rs.getString("acct_num");
					
					return acctnb;
				}
			};

			return simpleJdbcTemplate.query(SQL, mapper, idDocType, idDocNum);
			
			//return acctNum;
		}catch (Exception e) {
			logger.error("Exception caught in getImsOsBalanceAcct()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getImsOsBalance(String acctNum) throws DAOException{
		logger.debug("getImsOsBalance is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			String osAmt;
			String SQL = "select decode(acctstat, 'SUN', twritamt, 'BDT', twritamt, 'CLD', twritamt," +
						 " preosamt+curosamt) os_amt from custblac where acctnb = ?";

			osAmt = simpleJdbcTemplate.queryForObject(SQL, String.class, acctNum);

			
			return osAmt;
		}catch (Exception e) {
			logger.error("Exception caught in getImsOsBalance()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}


}