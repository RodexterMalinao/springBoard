package com.bomwebportal.dao;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;

public class CeksDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());

	public String initialCeksAccess(String username, String appId) throws DAOException {
		String contextId = null;

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("BOMWEB_CEKS_PKG")
            	.withProcedureName("INIT_CEKS_ACCESS");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("I_APP_ID", Types.VARCHAR),
					new SqlParameter("I_USER_ID", Types.VARCHAR),
					new SqlOutParameter("O_CONTEXT_ID", Types.VARCHAR),
					new SqlOutParameter("RET_VAL", Types.INTEGER),
					new SqlOutParameter("ERROR_CODE", Types.INTEGER),
					new SqlOutParameter("ERROR_TEXT", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("I_APP_ID", appId);
			inMap.addValue("I_USER_ID", username);
			SqlParameterSource in = inMap;
			
			Map<String, Object> out = jdbcCall.execute(in);
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("ERROR_CODE"))!= null ) {
				error_code = ((Integer) out.get("ERROR_CODE")).intValue();
			}
			
			if (((Integer) out.get("RET_VAL"))!= null ) {
				ret_val = ((Integer) out.get("RET_VAL")).intValue();
			}
			
			String error_text = (String)out.get("ERROR_TEXT");
			contextId = (String)out.get("O_CONTEXT_ID");
			
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output ret_val = " + ret_val);
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output error_code = " + error_code);
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output error_text = " + error_text);
			logger.info("BOMWEB_CEKS_PKG.INIT_CEKS_ACCESS() output contextId = " + contextId);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				contextId = null;
			}
			
			logger.info("initialCeksAccess contextId = " + contextId);
			
			return contextId;
			
		} catch (Exception e) {
			logger.error("Exception caught in initialCeksAccess()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
