package com.bomwebportal.lts.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;

public class UpdNnDAO extends BaseDAO {
	
	public void updateSrvNn(ServiceDetailLtsDTO srvDtl, String orderId, String srvNn) throws DAOException {
		
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE BOMWEB_ORDER_SERVICE_LTS ");
		sql.append("SET SRV_NN = :srvNn ");
		sql.append("WHERE ORDER_ID = :orderId ");
		sql.append("AND DTL_ID = :dtlId ");


		try {
			paramSource.addValue("orderId", orderId);
			paramSource.addValue("srvNn", srvNn);
			paramSource.addValue("dtlId", srvDtl.getDtlId());
			simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql.toString(), paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in updateSrvNn():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}	

	
}
