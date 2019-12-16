package com.bomwebportal.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dto.WaiveLkupDTO;
import com.bomwebportal.exception.DAOException;

public class WaiveDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public WaiveLkupDTO getWaiveLkupDTO(String reasonType, String reasonCd) throws DAOException {
		logger.debug("getWaiveLkupDTO is called");
		
		String sql = " SELECT "
				+ " REASON_TYPE, REASON_CD, "
				+ " REASON_DESC, "
				+ " START_DATE, END_DATE, "
				+ " CREATE_BY, CREATE_DATE, "
				+ " LAST_UPD_BY, LAST_UPD_DATE "
				+ " FROM "
				+ " W_WAIVE_LKUP "
				+ " WHERE "
				+ " REASON_CD=:reasonCd "
				+ " AND REASON_TYPE=:reasonType "
				;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("reasonType", reasonType);
			params.addValue("reasonCd", reasonCd);
			List<WaiveLkupDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(WaiveLkupDTO.class), params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getWaiveLkupDTO()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getWaiveDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
	public List<WaiveLkupDTO> findWaiveLkupByReasonType(String reasonType, Date appDate) throws DAOException {
		logger.debug("findWaiveLkupByReasonType is called");

		String sql = " SELECT "
				+ " REASON_TYPE, REASON_CD, "
				+ " REASON_DESC, "
				+ " START_DATE, END_DATE, "
				+ " CREATE_BY, CREATE_DATE, "
				+ " LAST_UPD_BY, LAST_UPD_DATE "
				+ " FROM "
				+ " W_WAIVE_LKUP "
				+ " WHERE "
				+ " REASON_TYPE=:reasonType "
				+ " AND TRUNC(NVL(:appDate, sysdate)) BETWEEN TRUNC(NVL(START_DATE, sysdate)) AND TRUNC(NVL(END_DATE, sysdate)) "				
				;

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("reasonType", reasonType);
			params.addValue("appDate", appDate);
			List<WaiveLkupDTO> list = this.simpleJdbcTemplate.query(sql,
					ParameterizedBeanPropertyRowMapper.newInstance(WaiveLkupDTO.class), params);
			
			return list;
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in findWaiveLkupByReasonType()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in findWaiveLkupByReasonType():", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}	
	
}
