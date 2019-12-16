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


public class DecoderTypeDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String getDecoderType(
			String ParmStr, String Serbdyno) throws DAOException{
		
		logger.debug("getDecoderType() is called");
		logger.debug("ParmStr:"+ParmStr);
		logger.debug("Serbdyno:"+Serbdyno);
		
		try{
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$BOM")
			.withCatalogName("pkg_oc_ims_sb")
			.withProcedureName("get_decoder_type")
			.declareParameters(
					new SqlParameter("i_parm", Types.VARCHAR),
					new SqlParameter("i_service_boundary", Types.VARCHAR),
					new SqlOutParameter("o_decoder_type", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR)
					);
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_parm",  ParmStr);
			inMap.addValue("i_service_boundary",  Serbdyno);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			logger.debug("gnRetVal:"+out.get("gnRetVal"));
			logger.debug("gnErrCode:"+out.get("gnErrCode"));
			logger.debug("gsErrText:"+out.get("gsErrText"));
			
			String decoderType;
			
			if((Integer)out.get("gnRetVal")!=0){
				logger.info("get_decoder_type returns error");
				throw new Exception("get_decoder_type returns error");
			}else{
				decoderType = (String)out.get("o_decoder_type");
			}
			
			logger.info("decoderType:"+decoderType);
			logger.info("getDecoderType() is ended");
			
			return decoderType;
			
		}catch (Exception e) {
			logger.error("Exception caught in getDecoderType()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
			
}
