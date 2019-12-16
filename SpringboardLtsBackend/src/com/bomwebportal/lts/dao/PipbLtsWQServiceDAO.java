package com.bomwebportal.lts.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class PipbLtsWQServiceDAO extends BaseDAO {
	
	
    public int countBomwebWqTrans(String pOrderId, String pDtlId, String pAction, String pStatus) throws DAOException {
		
		String sql = "select count(*) from BOMWEB_WQ_TRANS where order_id = :orderId and dtl_id = :dtlId and action = :action and status = :status";
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", pOrderId);
			params.addValue("dtlId", pDtlId);
			params.addValue("action", pAction);
			params.addValue("status", pStatus);
			return simpleJdbcTemplate.queryForInt(sql, params);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in countBomwebWqTrans()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	
}
