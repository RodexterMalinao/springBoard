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
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;

public class SuggestLoginIDDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public ImsInstallationUI suggestLoginID(String loginName) throws DAOException{
		logger.info("suggestLoginID"+"["+loginName+"]");
		
		long starttime = System.currentTimeMillis();
		logger.info("Start time - suggestLoginID["+loginName+"]: " + starttime);
		
		ImsInstallationUI result = new ImsInstallationUI();
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_oc_ims_sb")
				.withProcedureName("suggest_login_id");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("iLoginID", Types.VARCHAR),
					new SqlParameter("iDomainType", Types.VARCHAR),
					new SqlOutParameter("oHint1", Types.VARCHAR),
					new SqlOutParameter("oHint2", Types.VARCHAR),
					new SqlOutParameter("oHint3", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iLoginID", loginName);
			inMap.addValue("iDomainType", 'N');
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			result.setHintOne((String)out.get("oHint1"));
			result.setHintSecond((String)out.get("oHint2"));
			result.setHintThird((String)out.get("oHint3"));
			
			long endtime = System.currentTimeMillis();			
			long callTime = endtime - starttime;
			logger.info("End time - suggestLoginID["+loginName+"]: " + endtime);
			logger.info("Total call time - suggestLoginID["+loginName+"]: " + callTime + " ms");
			
			return result;
			
		}catch (Exception e) {
				logger.error("Exception caught in suggestLoginID()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
}
