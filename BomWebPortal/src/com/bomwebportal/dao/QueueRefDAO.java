package com.bomwebportal.dao;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.exception.DAOException;

public class QueueRefDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String verifyWalkInByPassControlSQL = 
			"SELECT DECODE(COUNT(*),0,'N','Y') walkIn " +
					"FROM w_mob_hsrm_bypass_lkup " +
					"WHERE item_code = :itemCode " +
					"AND ref_id      = :refId " +
					"AND trunc(:appDate) BETWEEN trunc(eff_start_date) AND trunc(nvl(eff_end_date, sysdate))";
	
	public boolean verifyWalkInByPassControl(String itemCode,String refId, Date appDate) throws DAOException {
		
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemCode", itemCode);
			params.addValue("refId", refId);
			params.addValue("appDate", appDate);
			String verifyResult = (String) simpleJdbcTemplate.queryForObject(verifyWalkInByPassControlSQL,String.class,params);

			if (logger.isDebugEnabled()) {
				logger.debug("SQL: " + verifyWalkInByPassControlSQL);
				logger.debug("verifyResult: " + verifyResult);
			}
			
			if (StringUtils.isNotEmpty(verifyResult)){
				return ("Y".equals(verifyResult));

			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		}
		catch (Exception e) {
			logger.error("Exception caught in verifyWalkInByPassControl() @ QueueRefDAO", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return false;
	}
	
	private static final String isWalkInByPassProductSQL = 
			"SELECT DECODE(COUNT(*),0,'N','Y') walkIn " +
					"FROM w_mob_hsrm_bypass_lkup " +
					"WHERE item_code = :itemCode " +
					"AND trunc(:appDate) BETWEEN trunc(eff_start_date) AND trunc(nvl(eff_end_date, sysdate))";
	
	public boolean isWalkInByPassProduct(String itemCode, Date appDate) throws DAOException {
		
		try {
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("itemCode", itemCode);
			params.addValue("appDate", appDate);
			String verifyResult = (String) simpleJdbcTemplate.queryForObject(isWalkInByPassProductSQL,String.class,params);

			if (logger.isDebugEnabled()) {
				logger.debug("SQL: " + isWalkInByPassProductSQL);
				logger.debug("verifyResult: " + verifyResult);
			}
			
			if (StringUtils.isNotEmpty(verifyResult)){
				return ("Y".equals(verifyResult));

			}

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		}
		catch (Exception e) {
			logger.error("Exception caught in isWalkInByPassProduct() @ QueueRefDAO", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return false;
	}
}
