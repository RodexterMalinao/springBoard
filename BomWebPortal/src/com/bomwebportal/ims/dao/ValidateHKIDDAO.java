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

public class ValidateHKIDDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean validateHKID(String hkId) throws DAOException{
		logger.info("validateHKID is called");
		
		boolean valid = false;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_CC_CUSTOMER_PKG")
				.withProcedureName("validate_hkid");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_iddocnb", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_iddocnb", hkId);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");			

			logger.info("OPS$BOM.B_CC_CUSTOMER_PKG.validate_hkid() output error_code = " + error_code);
			logger.info("OPS$BOM.B_CC_CUSTOMER_PKG.validate_hkid() output error_text = " + error_text);
			
			if (error_code == 0) {
				valid = true;
			}else valid = false;
			
			return valid;
		}catch (Exception e) {
				logger.error("Exception caught in validateHKID()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
		
	public String validateHKIDError(String hkId) throws DAOException{
		logger.info("validateHKIDError is called");
			
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("B_CC_CUSTOMER_PKG")
				.withProcedureName("validate_hkid");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_iddocnb", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_iddocnb", hkId);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");			

			logger.info("OPS$BOM.B_CC_CUSTOMER_PKG.validate_hkid() output error_code = " + error_code);
			logger.info("OPS$BOM.B_CC_CUSTOMER_PKG.validate_hkid() output error_text = " + error_text);
			
			return error_text;
		}catch (Exception e) {
				logger.error("Exception caught in validateHKIDError()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
}
