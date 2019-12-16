package com.bomwebportal.ims.dao;

import java.sql.Types;
import java.util.Date;
import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;


import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.google.gson.Gson;

public class CheckImsCustomerDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String checkImsCustomer(String idDocType, String idDocNum) throws DAOException{
		logger.debug("getImsCustomer is called");
		
		try{
			String result = "N";
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_oc_ims_sb")
				.withProcedureName("has_bb_dailup");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_id_doc_type", Types.VARCHAR),
					new SqlParameter("i_id_doc_num", Types.VARCHAR),
					new SqlOutParameter("o_result", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_id_doc_type", idDocType);
			inMap.addValue("i_id_doc_num", idDocNum);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			logger.info("has_bb_dailup result:" + new Gson().toJson(out));
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
						
			String error_text = (String)out.get("gsErrText");			

//			logger.info("OPS$BOM.pkg_oc_ims_sb.has_active_bb_dailup() output error_code = " + error_code);
//			logger.info("OPS$BOM.pkg_oc_ims_sb.has_active_bb_dailup() output error_text = " + error_text);
			
			
			result = out.get("o_result").toString();
			
			return result;
			
		}catch (Exception e) {
				logger.error("Exception caught in checkImsCustomer()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String checkImsCustomerNowTV(String idDocType, String idDocNum) throws DAOException{
		logger.info("getImsCustomerNowTV is called");
		
		try{
			String result = "N";
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_oc_ims_sb")
				.withProcedureName("has_nowtv");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_id_doc_type", Types.VARCHAR),
					new SqlParameter("i_id_doc_num", Types.VARCHAR),
					new SqlOutParameter("o_result", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_id_doc_type", idDocType);
			inMap.addValue("i_id_doc_num", idDocNum);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
						
			String error_text = (String)out.get("gsErrText");			

			logger.info("OPS$BOM.pkg_oc_ims_sb.has_nowtv() output error_code = " + error_code);
			logger.info("OPS$BOM.pkg_oc_ims_sb.has_nowtv() output error_text = " + error_text);
			
			
			System.out.println("OPS$BOM.pkg_oc_ims_sb.has_nowtv() output error_code = " + error_code);

			System.out.println("OPS$BOM.pkg_oc_ims_sb.has_nowtv() output error_text = " + error_text);
			
			
			result = out.get("o_result").toString();
			
			System.out.println("OPS$BOM.pkg_oc_ims_sb.has_nowtv() output output = " + result);
			
			return result;
			
		}catch (Exception e) {
				logger.error("Exception caught in checkImsCustomerNowTV()", e);
				throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public String checkImsDataPrivacy(String idDocType, String idDocNum, String bomLob) throws DAOException{
		logger.debug("checkImsDataPrivacy is called");
		
		try{
			String result = "N";
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("pkg_oc_ims_sb")
				.withProcedureName("is_data_privacy_empty");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_id_doc_type", Types.VARCHAR),
					new SqlParameter("i_id_doc_num", Types.VARCHAR),
					new SqlParameter("i_bom_lob", Types.VARCHAR),
					new SqlOutParameter("o_result", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_id_doc_type", idDocType);
			inMap.addValue("i_id_doc_num", idDocNum);
			inMap.addValue("i_bom_lob", bomLob);
			SqlParameterSource in = inMap;
		
			Map out = jdbcCall.execute(in);
			logger.info("checkImsDataPrivacy result:" + new Gson().toJson(out));
			int error_code = -1;
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
						
			String error_text = (String)out.get("gsErrText");			

//			logger.info("OPS$BOM.pkg_oc_ims_sb.has_active_bb_dailup() output error_code = " + error_code);
//			logger.info("OPS$BOM.pkg_oc_ims_sb.has_active_bb_dailup() output error_text = " + error_text);
			
			
			result = out.get("o_result").toString();
			
			return result;
			
		}catch (Exception e) {
				logger.error("Exception caught in checkImsDataPrivacy()", e);
				throw new DAOException(e.getMessage(), e);
		}
	}
	
}
