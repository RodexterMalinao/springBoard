package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;

public class ReleaseLockDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public OrderDTO getOrderLockInfo(String orderId) throws DAOException {
		logger.debug("getOrderLockInfo() is called");

		String SQL = "select O.ORDER_ID,\n" + "       O.ORDER_STATUS,\n"
				+ "       O.LAST_UPDATE_BY,\n" + "       O.LAST_UPDATE_DATE,\n"
				+ "       O.LOCK_IND\n" + "  from BOMWEB_ORDER O\n"
				+ " where O.ORDER_ID = ?";

		ParameterizedRowMapper<OrderDTO> mapper = new ParameterizedRowMapper<OrderDTO>() {
			public OrderDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				OrderDTO dto = new OrderDTO();

				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setOrderStatus(rs.getString("ORDER_STATUS"));
				dto.setLastUpdateBy(rs.getString("LAST_UPDATE_BY"));
				dto.setLastUpdateDate(rs.getDate("LAST_UPDATE_DATE"));
				dto.setLockInd(rs.getString("LOCK_IND"));
				return dto;
			}
		};

		try {
			logger.debug("getOrderLockInfo() @ OrderDAO: " + SQL);

			List<OrderDTO> orderList = simpleJdbcTemplate.query(SQL, mapper, orderId);
			
			if (orderList != null && !orderList.isEmpty()) {
				return orderList.get(0);
			}
			
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.info("Exception caught in getOrderLockInfo():", e);
			throw new DAOException(e.getMessage(), e);
		}

		return null;

	}

	public int releaseLockOrder(String orderId, String lastUpdateBy) throws DAOException {
		logger.debug("releaseLockOrder is called");
		logger.info("update orderID : " + orderId);
		
		String releaseSQL = "update BOMWEB_ORDER O\n"
				+ "   set O.LOCK_IND = null, O.LAST_UPDATE_BY = ?, O.LAST_UPDATE_DATE = sysdate\n"
				+ " where O.ORDER_ID = ?";

		try {
			return simpleJdbcTemplate.update(releaseSQL, lastUpdateBy, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in releaseLockOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	public int lockOrder(String orderId, String lastUpdateBy) throws DAOException {
		logger.debug("lockOrder is called");
		logger.info("update orderID : " + orderId);

		String lockSQL = "update BOMWEB_ORDER O\n"
				+ "   set O.LOCK_IND = 'Y', O.LAST_UPDATE_BY = ?, O.LAST_UPDATE_DATE = sysdate\n"
				+ " where O.ORDER_ID = ?";

		try {
			return simpleJdbcTemplate.update(lockSQL, lastUpdateBy, orderId);

		} catch (Exception e) {
			logger.error("Exception caught in lockOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
}
