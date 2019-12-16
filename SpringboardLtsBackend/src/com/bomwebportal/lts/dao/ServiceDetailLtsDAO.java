package com.bomwebportal.lts.dao;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ServiceDetailLtsDAO extends BaseDAO {
	
	private static final String SQL_UPDATE_SERVICE_DETAILS_LTS_DN_STATUS = 
		"UPDATE BOMWEB_ORDER_SERVICE_LTS SET DN_STATUS = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? AND DTL_ID = ? ";
	
	private static final String SQL_UPDATE_SERVICE_DETAILS_LTS_GATEWAY_NUM = 
		"UPDATE BOMWEB_ORDER_SERVICE_LTS SET GATEWAY_NUM = ?, LAST_UPD_BY = ?, LAST_UPD_DATE = SYSDATE " + 
		"WHERE ORDER_ID = ? AND DTL_ID = ? ";	
	
	public void updateDnStatus(String serviceInventSts, String orderId, String dtlId, String user) throws DAOException {
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_SERVICE_DETAILS_LTS_DN_STATUS,	serviceInventSts, user, orderId, dtlId);
		} catch (Exception e) {
			logger.error("Exception caught in updateDnStatus():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void updateGatewayNum(String gatewayNum, String orderId, String dtlId, String user) throws DAOException {
		try {
			simpleJdbcTemplate.update(SQL_UPDATE_SERVICE_DETAILS_LTS_GATEWAY_NUM,	gatewayNum, user, orderId, dtlId);
		} catch (Exception e) {
			logger.error("Exception caught in updateGatewayNum():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
