package com.bomwebportal.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.exception.DAOException;

public class BomwebDnPoolDAO extends BaseDAO {

	public void updateDnStatus(String orderId, String dtlId, String status) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_DN_POOL SET ");
		sql.append(" STATUS = :status ,");
		sql.append(" LAST_UPD_DATE = SYSDATE");
		sql.append(" WHERE SRV_NUM IN ( ");
		sql.append(" SELECT SRV_NUM  ");
		sql.append(" FROM BOMWEB_ORDER_SERVICE ");
		sql.append(" WHERE ORDER_ID = :orderId ");
		sql.append(" AND DTL_ID = :dtlId ");

		try {
			paramSource.addValue("status", status);
			paramSource.addValue("orderId", orderId);
			paramSource.addValue("dtlId", dtlId);
			
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updateDnStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	
}
