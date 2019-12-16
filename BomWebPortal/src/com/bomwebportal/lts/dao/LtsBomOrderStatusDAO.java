package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.OrderSrvStatusDetailDTO;

public class LtsBomOrderStatusDAO extends BaseDAO {

	private static final String SQL_GET_ORDER_SRV_STATUS_BY_OCID = 
//			"SELECT * FROM b_ord_latest_status ols, b_ord_dtl od " +
//					"WHERE ols.oc_id = od.oc_id " +
//					"AND ols.dtl_id = od.dtl_id " +
//					"AND ols.oc_id = ? ";
			"SELECT ols.OC_ID, ols.BOM_STATUS, ols.DTL_ID, ols.STATUS, " +
					"od.ORD_TYPE, od.SRV_REQ_DATE, od.TYPE_OF_SRV, os.srv_id " +
					"FROM b_ord_latest_status ols, b_ord_dtl od, b_ord_srv os " +
					"WHERE ols.oc_id = od.oc_id " +
					"AND ols.oc_id = os.oc_id " +
					"AND ols.dtl_id = od.dtl_id " +
					"AND ols.dtl_id = os.dtl_id " +
					"AND ols.oc_id = ?";
	
	public List<OrderSrvStatusDetailDTO> getPendingOrderSrvStatusList(String pOcid) throws DAOException {
		List<OrderSrvStatusDetailDTO> statusList = null;
		
		try {
			statusList = this.simpleJdbcTemplate.query(SQL_GET_ORDER_SRV_STATUS_BY_OCID, this.getStatusMapper(), pOcid);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getPendingOrderSrvStatusList() OCID: " + pOcid, e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return statusList;
	}
	
	
	private ParameterizedRowMapper<OrderSrvStatusDetailDTO> getStatusMapper() {
		return new ParameterizedRowMapper<OrderSrvStatusDetailDTO>() {
			public OrderSrvStatusDetailDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderSrvStatusDetailDTO status = new OrderSrvStatusDetailDTO();
				status.setBomStatus(rs.getString("BOM_STATUS"));
				status.setDtlId(rs.getString("DTL_ID"));
				status.setLegacyStatus(rs.getString("STATUS"));
				status.setOcid(rs.getString("OC_ID"));
				status.setOrderType(rs.getString("ORD_TYPE"));
				status.setSrd(rs.getString("SRV_REQ_DATE"));
				status.setTypeOfSrv(rs.getString("TYPE_OF_SRV"));
				status.setSrvId(rs.getString("SRV_ID"));
				return status;
			}
		};
	}
}
