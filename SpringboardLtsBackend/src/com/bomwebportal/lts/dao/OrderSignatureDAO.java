package com.bomwebportal.lts.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OrderSignatureDAO extends BaseDAO {
	
	public int deleteOrderSignature(String orderId, String signType) throws DAOException {
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM BOMWEB_ORDER_SIGNATURE");
		sql.append(" WHERE ORDER_ID = :orderId");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			
			if (StringUtils.isNotEmpty(signType)) {
				sql.append(" AND SIGN_TYPE = :signType");
				params.addValue("signType", signType);
			}
			
			return this.simpleJdbcTemplate.update(sql.toString(), params);
		}
		catch (Exception e) {
			logger.error("Exception caught in deleteOrderSignature(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
}
