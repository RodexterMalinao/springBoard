package com.bomwebportal.lts.dao.order;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OrderModifyDAO extends BaseDAO {
	
	public int updateEdfRef(String orderId, String dtlId, String edfRef, String userId) throws DAOException {
		
		String sql = "UPDATE BOMWEB_ORDER_SERVICE_OTHER SET " +
				" EDF_REF = :edfRef , " +
				" LAST_UPD_DATE = SYSDATE, " +
				" LAST_UPD_BY = :userId " +
				" WHERE ORDER_ID = :orderId " +
				" AND DTL_ID = :dtlId ";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("edfRef", edfRef);
			params.addValue("userId", userId);
			params.addValue("orderId", orderId);
			params.addValue("dtlId", dtlId);
			return simpleJdbcTemplate.update(sql.toString(), params);
			
		} catch (Exception e) {
			logger.error("Exception caught in updateEdfRef(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

}
