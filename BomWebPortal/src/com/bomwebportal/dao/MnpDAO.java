package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.BomWebPortalConstant;

public class MnpDAO extends BaseDAO{

	protected final Log logger = LogFactory.getLog(getClass());
	
	public MnpDTO checkAdMsisdn (MnpDTO mnpDTO)throws DAOException {
		logger.info("checkAdMsisdn is called");
		
		String msisdn = mnpDTO.getMsisdn(); 
		
		logger.debug("checkAdMsisdn Msisdn = " + msisdn);

		MnpDTO result = new MnpDTO();
				
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("MNP")
            	.withProcedureName("SELECT_DNO_SP");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("DN", Types.VARCHAR),
					new SqlInOutParameter("DNO_STR", Types.VARCHAR),
					new SqlOutParameter("RETURNREASON", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("DN", msisdn);
			inMap.addValue("DNO_STR", null);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			String dno_str = (String)out.get("DNO_STR");
			
			logger.info("MNP.SELECT_DNO_SP() output DNO_STR = " + dno_str);
			logger.debug("MNP.SELECT_DNO_SP() output RETURNREASON = " + (String)out.get("RETURNREASON"));
			
			if (dno_str != null && !"".equals(dno_str.trim())) {
				result.setMsisdn(msisdn);
				result.setDno(dno_str);
			}
			
			logger.info("checkAdMsisdn result = " + result);
			
			return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in checkAdMsisdn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public MnpDTO checkAdMsisdn (String msisdn)throws DAOException {
		logger.info("checkAdMsisdn is called");	
		logger.debug("checkAdMsisdn Msisdn = " + msisdn);

		MnpDTO result = new MnpDTO();
				
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("MNP")
            	.withProcedureName("SELECT_DNO_SP");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("DN", Types.VARCHAR),
					new SqlInOutParameter("DNO_STR", Types.VARCHAR),
					new SqlOutParameter("RETURNREASON", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("DN", msisdn);
			inMap.addValue("DNO_STR", null);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
			
			String dno_str = (String)out.get("DNO_STR");
			
			logger.info("MNP.SELECT_DNO_SP() output DNO_STR = " + dno_str);
			logger.debug("MNP.SELECT_DNO_SP() output RETURNREASON = " + (String)out.get("RETURNREASON"));
			
			if (dno_str != null && !"".equals(dno_str.trim())) {
				result.setMsisdn(msisdn);
				result.setDno(dno_str);
			}
			
			logger.info("checkAdMsisdn result = " + result);
			
			return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in checkAdMsisdn()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
