package com.bomwebportal.mob.dao.bom;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CccLkupDTO;

public class BCccLookupDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	
	private static String GetCccLkup = "SELECT "
			+ " CCC, "
			+ " CCC_DESC "
			+ " FROM "
			+ " B_CCC_LOOKUP "			
			+ " WHERE "
			+ " CCC=:ccc "
			;
	
	public CccLkupDTO getCccLkup(String ccc) throws DAOException {
		logger.info("getCccLkup is called");

		if (logger.isDebugEnabled()) {
			logger.debug(GetCccLkup);
			logger.debug("ccc: " + ccc);
		}
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("ccc", ccc);
			List<CccLkupDTO> list = this.simpleJdbcTemplate.query(GetCccLkup,
					ParameterizedBeanPropertyRowMapper.newInstance(CccLkupDTO.class), params);
			
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getCccLkup()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getCccLkup():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
}
