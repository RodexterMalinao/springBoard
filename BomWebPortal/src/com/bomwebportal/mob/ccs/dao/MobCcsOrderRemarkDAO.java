package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;

public class MobCcsOrderRemarkDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String getOrderRemarkDTOSQL = "SELECT" +
			" ORDER_ID" +
			" , REMARK" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" , rownum SEQ" +
			" , rowid ROW_ID" +
			" FROM bomweb_order_remark" +
			" WHERE row_id = :rowId";
	public OrderRemarkDTO getOrderRemarkDTO(String rowId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrderRemarkDTO is called");
		}
		List<OrderRemarkDTO> itemList = null;
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrderRemarkDTO() @ OrderRemarkDAO: " + getOrderRemarkDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("rowId", rowId);
			itemList = simpleJdbcTemplate.query(getOrderRemarkDTOSQL, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrderRemarkDTO()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getOrderRemarkDTO():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}
	
	private static final String getOrderRemarkDTOALLSQL = "SELECT" +
			" ORDER_ID" +
			" , REMARK" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" , rownum SEQ" +
			" , rowid ROW_ID" +
			" FROM bomweb_order_remark" +
			" WHERE ORDER_ID = :orderId AND nvl(type, 'U') != 'S'" +
			" ORDER BY SEQ";
	public List<OrderRemarkDTO> getOrderRemarkDTOALL(String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getOrderRemarkDTOALL is called");
		}
		List<OrderRemarkDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getOrderRemarkDTOALL() @ OrderRemarkDAO: " + getOrderRemarkDTOALLSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			itemList = simpleJdbcTemplate.query(getOrderRemarkDTOALLSQL, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getOrderRemarkDTOALL()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getOrderRemarkDTOALL():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private static final String getPTOrderRemarkDTOSQL = "SELECT" +
			" ORDER_ID" +
			" , REMARK" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" , rownum SEQ" +
			" , rowid ROW_ID" +
			" FROM bomweb_order_remark" +
			" WHERE ORDER_ID = :orderId" +
			" AND TYPE = 'M' " +
			" ORDER BY SEQ";
	public List<OrderRemarkDTO> getPTOrderRemarkDTO(String orderId) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getPTOrderRemarkDTO is called");
		}
		List<OrderRemarkDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getPTOrderRemarkDTO() @ OrderRemarkDAO: " + getPTOrderRemarkDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			itemList = simpleJdbcTemplate.query(getPTOrderRemarkDTOSQL, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getPTOrderRemarkDTO()");
			}
			itemList = Collections.emptyList();
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getPTOrderRemarkDTO():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<OrderRemarkDTO> getRowMapper() {
		return new ParameterizedRowMapper<OrderRemarkDTO>() {
			public OrderRemarkDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderRemarkDTO dto = new OrderRemarkDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setRemark(rs.getString("REMARK"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setSeq(rs.getLong("SEQ"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}
	
	private static final String insertOrderRemarkDTOSQL = "INSERT INTO bomweb_order_remark" +
			" (" +
			" ORDER_ID" +
			" , REMARK" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" , SEQ" +
			" , TYPE" +
			" ) VALUES (" +
			" :orderId" +
			" , :remark" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" , BOMWEB_ORDER_REMARK_SEQ.nextVal" +
			" , :type" +
			" )";
	public int insertOrderRemarkDTO(OrderRemarkDTO dto) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("insertOrderRemarkDTO is called");
		}
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("insertOrderRemarkDTO() @ OrderRemarkDAO: " + insertOrderRemarkDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("remark", dto.getRemark());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("type", dto.getType());
			return simpleJdbcTemplate.update(insertOrderRemarkDTOSQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertOrderRemarkDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
