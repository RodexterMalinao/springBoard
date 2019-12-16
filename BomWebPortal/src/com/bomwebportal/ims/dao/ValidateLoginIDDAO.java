package com.bomwebportal.ims.dao;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ValidateLoginIDDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean validateLoginID(String loginName) throws DAOException{
		logger.info("validateLoginID"+"["+loginName+"]");
		
		long starttime = System.currentTimeMillis();
		logger.info("Start time - validateLoginID["+loginName+"]: " + starttime);
		
		boolean valid = false;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_OC_IMS_WebApp")
				.withProcedureName("LoginIDValidation2010");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_login_id", Types.VARCHAR),
					new SqlOutParameter("o_valid", Types.VARCHAR),
					new SqlOutParameter("o_msg", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_login_id", loginName);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = 0;
			String result = null;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((String) out.get("o_valid"))!= null ) {
				result = (String) out.get("o_valid");
			}
			
			String error_text = (String)out.get("gsErrText");			

			logger.info("OPS$BOM.pkg_OC_IMS_WebApp.LoginIDValidate2010() output error_code = " + error_code);
			logger.info("OPS$BOM.pkg_OC_IMS_WebApp.LoginIDValidate2010() output error_text = " + error_text);
			
			if (result.equals("Y")) {
				valid = true;
			}else valid = false;
			
			long endtime = System.currentTimeMillis();			
			long callTime = endtime - starttime;
			logger.info("End time - validateLoginID["+loginName+"]: " + endtime);
			logger.info("Total call time - validateLoginID["+loginName+"]: " + callTime + " ms");
			
			return valid;
		}catch (Exception e) {
				logger.error("Exception caught in validateLoginID()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
		
	public String validateLoginIDError(String loginName) throws DAOException{
		logger.info("validateLoginIDError"+"["+loginName+"]");
		
		long starttime = System.currentTimeMillis();
		logger.info("Start time - validateLoginIDError["+loginName+"]: " + starttime);
			
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_OC_IMS_WebApp")
				.withProcedureName("LoginIDValidation2010");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_login_id", Types.VARCHAR),
					new SqlOutParameter("o_valid", Types.VARCHAR),
					new SqlOutParameter("o_msg", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_login_id", loginName);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			String result = null;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((String) out.get("o_valid"))!= null ) {
				result = (String) out.get("o_valid");
			}
			
			String error_text = (String)out.get("gsErrText");			
			logger.info("OPS$BOM.pkg_OC_IMS_WebApp.LoginIDValidate2010() output error_code = " + error_code);
			logger.info("OPS$BOM.pkg_OC_IMS_WebApp.LoginIDValidate2010() output error_text = " + error_text);
			
			long endtime = System.currentTimeMillis();			
			long callTime = endtime - starttime;
			logger.info("End time - validateLoginIDError["+loginName+"]: " + endtime);
			logger.info("Total call time - validateLoginIDError["+loginName+"]: " + callTime + " ms");
			
			return error_text;
		}catch (Exception e) {
				logger.error("Exception caught in validateLoginIDError()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
}
