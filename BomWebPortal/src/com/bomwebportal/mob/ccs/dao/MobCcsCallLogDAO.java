package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CallLogDTO;

public class MobCcsCallLogDAO extends BaseDAO {
	private static String getCallLogDTOALLByOrderIdSQL = "SELECT" +
			" ORDER_ID" +
			" , CONTACT_NAME" +
			" , CONTACT_PHONE" +
			" , CONTACT_EMAIL" +
			" , CONTACT_BY" +
			" , NATURE" +
			" , RESULT" +
			" , REMARK" +
			" , CREATE_DATE" +
			" , CALL_TYPE_CD" +
			" , rowid ROW_ID" +
			" FROM bomweb_call_log" +
			" WHERE ORDER_ID = :orderId" +
			" ORDER BY CREATE_DATE DESC";
	public List<CallLogDTO> getCallLogDTOALLByOrderId(String orderId) throws DAOException {
		List<CallLogDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getCallLogDTOALLByOrderId() @ MobCcsCallLogDAO: " + getCallLogDTOALLByOrderIdSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getCallLogDTOALLByOrderIdSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCallLogDTOALLByOrderId()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCallLogDTOALLByOrderId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<CallLogDTO> getRowMapper() {
		return new ParameterizedRowMapper<CallLogDTO>() {
			public CallLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CallLogDTO dto = new CallLogDTO();
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setContactName(rs.getString("CONTACT_NAME"));
				dto.setContactPhone(rs.getString("CONTACT_PHONE"));
				dto.setContactEmail(rs.getString("CONTACT_EMAIL"));
				dto.setContactBy(rs.getString("CONTACT_BY"));
				dto.setNature(rs.getString("NATURE"));
				dto.setResult(rs.getString("RESULT"));
				dto.setRemark(rs.getString("REMARK"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setCallTypeCd(rs.getString("CALL_TYPE_CD"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}
	
	private static String insertCalLogDTOSQL = "INSERT INTO" +
			" bomweb_call_log" +
			" (" +
			" ORDER_ID" +
			" , CONTACT_NAME" +
			" , CONTACT_PHONE" +
			" , CONTACT_EMAIL" +
			" , CONTACT_BY" +
			" , NATURE" +
			" , RESULT" +
			" , REMARK" +
			" , CREATE_DATE" +
			" , CALL_TYPE_CD" +
			" ) VALUES (" +
			" :orderId" +
			" , :contactName" +
			" , :contactPhone" +
			" , :contactEmail" +
			" , :contactBy" +
			" , :nature" +
			" , :result" +
			" , :remark" +
			" , sysdate" +
			" , :callTypeCd" +
			" )";
	public int insertCallLogDTO(CallLogDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertCallLogDTOSQL @ CallLogDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertCallLogDTOSQL() @ CallLogDAO: " + insertCalLogDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", dto.getOrderId());
			params.addValue("contactName", dto.getContactName());
			params.addValue("contactPhone", dto.getContactPhone());
			params.addValue("contactEmail", dto.getContactEmail());
			params.addValue("contactBy", dto.getContactBy());
			params.addValue("nature", dto.getNature());
			params.addValue("result", dto.getResult());
			params.addValue("remark", dto.getRemark());
			params.addValue("callTypeCd", dto.getCallTypeCd());
			return simpleJdbcTemplate.update(insertCalLogDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertCallLogDTOSQL()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
