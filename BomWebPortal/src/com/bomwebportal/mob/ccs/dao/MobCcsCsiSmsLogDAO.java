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
import com.bomwebportal.mob.ccs.dto.CsiCaseDTO;
import com.bomwebportal.mob.ccs.dto.CsiSmsLogDTO;

public class MobCcsCsiSmsLogDAO extends BaseDAO {
	
	private static String getCsiSmsLogALLByCaseIdSQL = "select "+
			 "CASE_NO, " +
			 "ORDER_ID, " +
			 "SMS_RECORD_DATE, " +
			 "SEQ_NO " +
			 "from BOMWEB_CSI_SMS_LOG " +
			 "WHERE CASE_NO = :caseNo " +
			 "ORDER BY SMS_RECORD_DATE DESC";
	public List<CsiSmsLogDTO> getCsiSmsLogALLByCaseId(String caseNo) throws DAOException {
		List<CsiSmsLogDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", caseNo);
			if (logger.isInfoEnabled()) {
				logger.info("getCsiSmsLogALLByCaseId() @ MobCcsCsiCaseDAO: " + getCsiSmsLogALLByCaseIdSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getCsiSmsLogALLByCaseIdSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCsiSmsLogALLByCaseId()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCsiSmsLogALLByCaseId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	
	private ParameterizedRowMapper<CsiSmsLogDTO> getRowMapper() {
		return new ParameterizedRowMapper<CsiSmsLogDTO>() {
			public CsiSmsLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CsiSmsLogDTO dto = new CsiSmsLogDTO();
				dto.setCaseNo(rs.getString("CASE_NO"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSmsRecordDate(rs.getTimestamp("SMS_RECORD_DATE"));
				System.out.print(dto.getSmsRecordDate());
				dto.setSeqNo(rs.getString("SEQ_NO"));
				return dto;
			}
		};
	}
	
	
	private static String insertCsiSmsLogDTOSQL = "INSERT INTO" +
			" BOMWEB_CSI_SMS_LOG" +
			" (" +
			" CASE_NO, " +
			" ORDER_ID, " +
			" SMS_RECORD_DATE, " +
			" SEQ_NO " +
			" ) VALUES (" +
			" :caseNo " +
			" , :orderId" +
			" , TO_DATE ( :smsRecordDate , 'DD/MM/YYYY hh24:mi') " +
			" , (select nvl(max(seq_no),0) from BOMWEB_CSI_SMS_LOG where case_no = :caseNo ) + 1 " +
			" )";
	public int insertCsiSmsLogDTO(CsiSmsLogDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertCsiSmsLogDTO @ MobCcsCsiCaseDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertCsiSmsLogDTO() @ MobCcsCsiCaseDAO: " + insertCsiSmsLogDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", dto.getCaseNo());
			params.addValue("orderId", dto.getOrderId());
			System.out.println(dto.getCaseNo()+":"+ dto.getOrderId());
			params.addValue("smsRecordDate", dto.getSmsRecordDateStr() + " " +dto.getSmsRecordTimeHour()+":"+dto.getSmsRecordTimeMin());
			System.out.println(dto.getSmsRecordDateStr() + " " +dto.getSmsRecordTimeHour()+":"+dto.getSmsRecordTimeMin());
			return simpleJdbcTemplate.update(insertCsiSmsLogDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertCsiSmsLogDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	

	
}
