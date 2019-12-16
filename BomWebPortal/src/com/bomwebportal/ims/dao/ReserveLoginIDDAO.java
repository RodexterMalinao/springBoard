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

public class ReserveLoginIDDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public int reserveLoginID(String loginName, String idNo, String idType) throws DAOException{
		logger.info("reserveLoginID is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$IMS")
				.withCatalogName("pkg_bossweb")
				.withProcedureName("reserveLoginID");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_domntype", Types.VARCHAR),
					new SqlParameter("i_netlogid", Types.VARCHAR),
					new SqlParameter("i_iddoctype", Types.VARCHAR),
					new SqlParameter("i_iddocnb", Types.VARCHAR),
					new SqlParameter("i_capWebLogin", Types.VARCHAR),
					new SqlParameter("i_reuseLogin", Types.VARCHAR),
					new SqlParameter("i_canDiffCust", Types.VARCHAR),
					new SqlParameter("i_serordnb", Types.INTEGER),
					new SqlOutParameter("o_iddoctyp", Types.VARCHAR),
					new SqlOutParameter("o_iddocnb", Types.VARCHAR),
					new SqlOutParameter("o_status", Types.VARCHAR),
					new SqlOutParameter("o_stsupdt", Types.DATE),
					new SqlOutParameter("o_webind", Types.VARCHAR),
					new SqlOutParameter("o_serordnb", Types.INTEGER),
					new SqlOutParameter("o_retVal", Types.INTEGER),
					new SqlOutParameter("o_errorCode", Types.INTEGER),
					new SqlOutParameter("o_errorText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_domntype", 'N');
			inMap.addValue("i_netlogid", loginName);
			inMap.addValue("i_iddoctype", idType);
			inMap.addValue("i_iddocnb", idNo);
			inMap.addValue("i_capWebLogin", 'X');
			inMap.addValue("i_reuseLogin", 'N');
			inMap.addValue("i_canDiffCust", 'N');
			inMap.addValue("i_serordnb", -99999999);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			
			if (((Integer) out.get("o_errorCode"))!= null ) {
				error_code = ((Integer) out.get("o_errorCode")).intValue();
			}
						
			String error_text = (String)out.get("o_errorText");			

			logger.info("OPS$IMS.pkg_bossweb.ReserveLoginID() output error_code = " + error_code);
			logger.info("OPS$IMS.pkg_bossweb.ReserveLoginID() output error_text = " + error_text);
			
			if(error_code < 0){
				return error_code;
			}
			
			return 0;
		}catch (Exception e) {
				logger.error("Exception caught in reserveloginName()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
}
