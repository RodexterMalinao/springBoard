package com.bomwebportal.mob.ccs.dao;

import java.sql.Types;
import java.util.Date;
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

public class MobCcsUtilDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
    public Date getNextNDeliveryDay(Date jobDate, int nDay) throws DAOException {
		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_UTIL")
            	.withFunctionName("GET_NEXT_N_DELIVERY_DAY");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlOutParameter("RESULT", Types.DATE)
					, new SqlParameter("i_job_date", Types.DATE)
					, new SqlParameter("i_n_day", Types.INTEGER));

			/*
FUNCTION get_next_n_delivery_day (
   i_job_date              IN DATE,
   i_n_day                 IN NUMBER
) RETURN DATE;
			 */
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("i_job_date", jobDate);
			params.addValue("i_n_day", nDay);
			
			Date nextNDeliveryDay = jdbcCall.executeFunction(Date.class, params);
			
			if (logger.isInfoEnabled()) {
				logger.info("PKG_SB_MOB_UTIL.GET_NEXT_N_DELIVERY_DAY() output nextNDeliveryDay = " + nextNDeliveryDay);
			}
			
			return nextNDeliveryDay;
			
		} catch (Exception e) {
			logger.error("Exception caught in getNextNDeliveryDate()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
    public String isWorkingDay(Date inDate) throws DAOException {

		try {			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$CNM")
            	.withCatalogName("PKG_SB_MOB_UTIL")
            	.withProcedureName("IS_WORKING_DAY");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_job_date", Types.DATE),
					new SqlOutParameter("result", Types.DATE));
		
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_job_date", inDate);
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
						
			String result = (String)out.get("result");
						
			logger.info("PKG_SB_MOB_UTIL.IS_WORKING_DAY() result = " + result);
			
			return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in isWorkingDay()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

}
