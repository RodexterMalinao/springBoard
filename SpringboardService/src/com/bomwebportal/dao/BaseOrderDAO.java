package com.bomwebportal.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.exception.DAOException;

public class BaseOrderDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public int updateDmsInd(String orderId, String dmsInd, String lastUpdateBy) throws DAOException {
		String sql = "update bomweb_order" +
				" set dms_ind = :dmsInd" +
				" , LAST_UPDATE_BY = :lastUpdateBy" +
				" , LAST_UPDATE_DATE = sysdate" +
				" where ORDER_ID = :orderId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("dmsInd", dmsInd);
		params.addValue("lastUpdateBy", lastUpdateBy);
		params.addValue("orderId", orderId);

		try {
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateDmsInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}

