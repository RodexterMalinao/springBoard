package com.bomwebportal.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.exception.DAOException;

public class BServiceDAO extends BaseDAO {
	public enum SystemId {
		MOB
		;
	}
	public enum ServiceType {
		TYPE_3G("3G")
		;
		ServiceType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		private String value;
	}
	
	public boolean isActivated(String srvNum, SystemId systemId, ServiceType serviceType) throws DAOException {
		final String sql = "select count(*)" +
				" from" +
				"   b_service" +
				" where" +
				"   srv_num = :srvNum" +
				"   and system_id = :systemId" +
				"   and service_type = :serviceType" +
				"   and eff_end_date is null";
		if (logger.isDebugEnabled()) {
			logger.debug("sql: " + sql);
			logger.debug("srvNum: " + srvNum + ", systemId: " + systemId + ", service_type: " + serviceType);
		}
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("srvNum", srvNum);
		params.addValue("systemId", systemId.toString());
		params.addValue("serviceType", serviceType.getValue());
		try {
			int count = this.simpleJdbcTemplate.queryForInt(sql, params);
			if (logger.isDebugEnabled()) {
				logger.debug("count: " + count);
			}
			return count > 0;
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("Exception found", e);
			}
			throw new DAOException(e);
		}
	}
}
