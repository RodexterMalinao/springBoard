package com.bomwebportal.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.exception.DAOException;

public class SalesDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public int updateShopCd(String shopcd, String username, String areacd) throws DAOException {
		String SQL = "update bomweb_sales_assignment b"
			+ " set team_cd=:shopcd "
			+ " , centre_cd=:areacd "
			+ " where staff_id=:username and channel_id= 1 "
			+ " and sysdate > trunc(b.start_date) "
			+ " and ( trunc(sysdate) < b.end_date "
			+ " or b.end_date is null) ";

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("shopcd", shopcd);
			params.addValue("username", username);
			params.addValue("areacd", areacd);
			return simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in updateShopCd(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}

}
