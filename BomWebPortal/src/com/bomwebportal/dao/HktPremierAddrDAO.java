package com.bomwebportal.dao;

import java.sql.Types;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.exception.DAOException;

public class HktPremierAddrDAO extends BaseDAO {
	
	public String checkPremierAddr(String serbdyno) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("checkPremierAddr @ HktPremierAddrDAO is called");
			logger.debug("serbdyno: " + serbdyno);
		}

		try {
			SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
													.withSchemaName("ops$bom")
													.withCatalogName("pkg_OC_IMS_SB")
													.withProcedureName("check_premier_addr");
			// declare procedure parameters 
			simpleJdbcCall.declareParameters(
					new SqlParameter("i_service_boundary", Types.VARCHAR)
					, new SqlOutParameter("o_result", Types.VARCHAR)
					, new SqlOutParameter("gnRetVal", Types.NUMERIC)
					, new SqlOutParameter("gnErrCode", Types.NUMERIC)
					, new SqlOutParameter("gsErrText", Types.VARCHAR));

			// procedure parameters value
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("i_service_boundary", serbdyno);
			
			// execute
			Map<String, Object> out = simpleJdbcCall.execute(params);
			String oResult = null;
			Integer gnRetVal = null;
			Integer gnErrCode = null;
			String gsErrText = null;
			if (out.get("o_result") instanceof String) {
				oResult = (String) out.get("o_result");
			}
			if (out.get("gnRetVal") instanceof Integer) {
				gnRetVal = (Integer) out.get("gnRetVal");
			}
			if (out.get("gnErrCode") instanceof Integer) {
				gnErrCode = (Integer) out.get("gnErrCode");
			}
			if (out.get("gsErrText") instanceof String) {
				gsErrText = (String) out.get("gsErrText");
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("oResult: " + oResult);
				logger.debug("gnRetVal: " + gnRetVal);
				logger.debug("gnErrCode: " + gnErrCode);
				logger.debug("gsErrText: " + gsErrText);
			}
			
			return oResult;
		} catch (Exception e) {
			logger.error("Exception caught in checkPremierAddr()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
