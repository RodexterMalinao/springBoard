package com.bomwebportal.mob.ds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;

import com.bomwebportal.exception.DAOException;

public class MobDsFalloutLogDAO extends BaseDAO {

	private static String getFalloutLogDTOALLByCaseNoSQL = "SELECT" +
			" CASE_NO" +
			" , ORDER_ID" +
			" , SEQ_NO" +
			" , CONTACT_NAME" +
			" , CONTACT_PHONE" +
			" , CONTACT_EMAIL" +
			" , CONTACT_BY" +
			" , REL_WT_CUST" +
			" , RESULT_CD" +
			" , REMARK" +
			" , CALL_TYPE_CD" +
			" , CREATE_DATE" +
			" FROM BOMWEB_CSI_LOG" +
			" WHERE CASE_NO = :caseNo AND ORDER_ID = :orderId" +
			" ORDER BY CREATE_DATE DESC ,SEQ_NO DESC";
	
	public List<MobDsFalloutLogDTO> getFalloutLogDTOALLByCaseNo(String caseNo, String orderId) throws DAOException {
		List<MobDsFalloutLogDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", caseNo);
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getFalloutLogDTOALLByCaseNo() @ MobDsFalloutLogDAO: " + getFalloutLogDTOALLByCaseNoSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getFalloutLogDTOALLByCaseNoSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getFalloutLogDTOALLByCaseNo()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getFalloutLogDTOALLByCaseNo():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<MobDsFalloutLogDTO> getRowMapper() {
		return new ParameterizedRowMapper<MobDsFalloutLogDTO>() {
			public MobDsFalloutLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobDsFalloutLogDTO dto = new MobDsFalloutLogDTO();
				dto.setCaseNo(rs.getString("CASE_NO"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSeqNo(rs.getString("SEQ_NO"));
				dto.setContactName(rs.getString("CONTACT_NAME"));
				dto.setContactPhone(rs.getString("CONTACT_PHONE"));
				dto.setContactEmail(rs.getString("CONTACT_EMAIL"));
				dto.setContactBy(rs.getString("CONTACT_BY"));
				dto.setNature(rs.getString("REL_WT_CUST"));
				dto.setResult(rs.getString("RESULT_CD"));
				dto.setRemark(rs.getString("REMARK"));
				dto.setCallTypeCd(rs.getString("CALL_TYPE_CD"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
	
				return dto;
			}
		};
	}
	
	private static String insertFalloutLogDTOSQL = "INSERT INTO" +
			" bomweb_csi_log" +
			" (" +
			" CASE_NO" +
			" , SEQ_NO" +
			" , ORDER_ID" +
			" , CONTACT_NAME" +
			" , CONTACT_PHONE" +
			" , CONTACT_EMAIL" +
			" , CONTACT_BY" +
			" , REL_WT_CUST" +
			" , RESULT_CD" +
			" , REMARK" +
			" , CREATE_DATE" +
			" , CALL_TYPE_CD" +
			" ) VALUES (" +
			" :caseNo" +
			" , (select nvl(max(seq_no),0)  from bomweb_csi_log where CASE_NO = :caseNo and ORDER_ID = :orderId ) +1" +
			" , :orderId" +
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
	
	public int insertFalloutLogDTO(MobDsFalloutLogDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertFalloutLogDTOSQL @ MobDsFalloutLogDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertFalloutLogDTOSQL() @ MobDsFalloutLogDAO: " + insertFalloutLogDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", dto.getCaseNo());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("contactName", dto.getContactName());
			params.addValue("contactPhone", dto.getContactPhone());
			params.addValue("contactEmail", dto.getContactEmail());
			params.addValue("contactBy", dto.getContactBy());
			params.addValue("nature", dto.getNature());
			params.addValue("result", dto.getResult());
			params.addValue("remark", dto.getRemark());
			params.addValue("callTypeCd", dto.getCallTypeCd());
			return simpleJdbcTemplate.update(insertFalloutLogDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertFalloutLogDTOSQL()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
}
