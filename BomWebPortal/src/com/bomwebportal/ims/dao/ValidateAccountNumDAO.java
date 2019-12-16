package com.bomwebportal.ims.dao;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ValidateAccountNumDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public int validateAccountNum(long acctnb, String srccode) throws DAOException{
		logger.info("validateAccountNum is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$IMS")
				.withCatalogName("netsop_misc_pkg")
				.withProcedureName("validate_pregen_acctnb");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_acctnb", Types.INTEGER),
					new SqlParameter("i_srccode", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_acctnb", acctnb);
			inMap.addValue("i_srccode", srccode);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
						
			String error_text = (String)out.get("gsErrText");			

			logger.info("OPS$IMS.NETSOP_MISC_PKG.Validate_Pregen_Acctnb() output error_code = " + error_code);
			logger.info("OPS$IMS.NETSOP_MISC_PKG.Validate_Pregen_Acctnb() output error_text = " + error_text);
			
			if(error_code < 0){
				return error_code;
			}
			
			return 0;
		}catch (Exception e) {
				logger.error("Exception caught in validate_pregen_acctnb()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String validateAccountNumError(long acctnb, String srccode) throws DAOException{
		logger.info("validateAccountNumError is called");
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$IMS")
				.withCatalogName("netsop_misc_pkg")
				.withProcedureName("validate_pregen_acctnb");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_acctnb", Types.INTEGER),
					new SqlParameter("i_srccode", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_acctnb", acctnb);
			inMap.addValue("i_srccode", srccode);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
						
			String error_text = (String)out.get("gsErrText");			

			logger.info("OPS$IMS.NETSOP_MISC_PKG.Validate_Pregen_Acctnb() output error_code = " + error_code);
			logger.info("OPS$IMS.NETSOP_MISC_PKG.Validate_Pregen_Acctnb() output error_text = " + error_text);
			
			return error_text;
		}catch (Exception e) {
				logger.error("Exception caught in validate_pregen_acctnb()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String validateEmailAvailable(String loginID) throws DAOException{
		String emailAddrStatus = null;
		/*String SQL = " SELECT status " +
		"     FROM nelgninv            " +
		"     WHERE netlogid = '"+ loginID + "'" +
		"     AND status = 'A' " +
		"     AND domntype = 'N' ";*/
		
		String SQL = " SELECT n.status         " +
		"     FROM nelgninv n, csgmemb c     " +
		"     WHERE n.netlogid = c.netlogid    " +
		"     AND n.domntype = c.domntype      " +
		"     AND n.netlogid = '"+ loginID + "'" +
		"     AND n.status = 'A' " +
		"     AND n.domntype = 'N' " +
		"     AND c.MEMBSTAT = 'ACT' ";


		try {
			emailAddrStatus = (String) simpleJdbcTemplate.queryForObject(SQL,
					String.class);
		}catch (EmptyResultDataAccessException erdae) {
			emailAddrStatus = null;
		}catch (Exception e) {
			logger.error("Exception caught in getAutoProcessOrderId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return emailAddrStatus;
		
	}
}
