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
import com.bomwebportal.mob.ccs.dto.CsiCaseLogDTO;

public class MobCcsCsiCaseLogDAO extends BaseDAO {
	
	private static String getCsiCaseLogALLByOrderIdSQL = "select "+
			 "CASE_NO, " +
			 "ORDER_ID, " +
			 "CONTACT_NAME, " +
			 "CONTACT_PHONE, " +
			 "CONTACT_EMAIL, " +
			 "CALL_TYPE_CD, " +
			 "CONTACT_BY, " +
			 "RESULT_CD, " +
			 "REL_WT_CUST, " +
			 "REMARK, " +
			 "CREATE_BY, " +
			 "CREATE_DATE, " +
			 "LAST_UPD_BY, " +
			 "LAST_UPD_DATE " +
			 "from BOMWEB_CSI_LOG " +
			 "WHERE ORDER_ID = :orderId " +
			 "AND CASE_NO is null " +
			 "ORDER BY CREATE_DATE DESC";
	public List<CsiCaseLogDTO> getCsiCaseLogALLByOrderId(String orderId) throws DAOException {
		List<CsiCaseLogDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getCsiCaseLogALLByOrderId() @ MobCcsCsiCaseLogDAO: " + getCsiCaseLogALLByOrderIdSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getCsiCaseLogALLByOrderIdSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCsiCaseLogALLByOrderId()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCsiCaseLogALLByOrderId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private static String getCsiCaseLogALLByCaseNoSQL = "select "+
			 "CASE_NO, " +
			 "ORDER_ID, " +
			 "CONTACT_NAME, " +
			 "CONTACT_PHONE, " +
			 "CONTACT_EMAIL, " +
			 "CALL_TYPE_CD, " +
			 "CONTACT_BY, " +
			 "RESULT_CD, " +
			 "REL_WT_CUST, " +
			 "REMARK, " +
			 "CREATE_BY, " +
			 "CREATE_DATE, " +
			 "LAST_UPD_BY, " +
			 "LAST_UPD_DATE " +
			 "from BOMWEB_CSI_LOG " +
			 "WHERE CASE_NO = :caseNo " +
			 "ORDER BY CREATE_DATE DESC";
	public List<CsiCaseLogDTO> getCsiCaseLogALLByCaseNo(String caseNo) throws DAOException {
		List<CsiCaseLogDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", caseNo);
			if (logger.isInfoEnabled()) {
				logger.info("getCsiCaseLogALLByCaseNo() @ MobCcsCsiCaseLogDAO: " + getCsiCaseLogALLByCaseNoSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getCsiCaseLogALLByCaseNoSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCsiCaseLogALLByCaseNo()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCsiCaseLogALLByCaseNo():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<CsiCaseLogDTO> getRowMapper() {
		return new ParameterizedRowMapper<CsiCaseLogDTO>() {
			public CsiCaseLogDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CsiCaseLogDTO dto = new CsiCaseLogDTO();
				dto.setCaseNo(rs.getString("CASE_NO"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setContactName(rs.getString("CONTACT_NAME"));
				dto.setContactPhone(rs.getString("CONTACT_PHONE"));
				dto.setContactEmail(rs.getString("CONTACT_EMAIL"));
				dto.setCallTypeCd(rs.getString("CALL_TYPE_CD"));
				dto.setResultCd(rs.getString("RESULT_CD"));
				dto.setContactBy(rs.getString("CONTACT_BY"));
				dto.setRelWtCust(rs.getString("REL_WT_CUST"));
				dto.setRemark(rs.getString("REMARK"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				return dto;
			}
		};
	}
	
	
	private static String insertCsiCaseLogDTOSQL = "INSERT INTO" +
			" BOMWEB_CSI_LOG" +
			" (" +
			" CASE_NO," +
			" ORDER_ID," +
			" CONTACT_NAME," +
			" CONTACT_PHONE," +
			" CONTACT_EMAIL," +
			" CALL_TYPE_CD," +
			" CONTACT_BY," +
			" RESULT_CD," +
			" REL_WT_CUST," +
			" REMARK," +
			" CREATE_BY," +
			" CREATE_DATE," +
			" LAST_UPD_BY," +
			" LAST_UPD_DATE, " +
			" seq_no " +
			" ) VALUES (" +
			" :caseNo " +
			" , :orderId" +
			" , :contactName" +
			" , :contactPhone" +
			" , :contactEmail" +
			" , :callTypeCd" +
			" , :contactBy" +
			" , :resultCd" +
			" , :relWtCust" +
			" , :remark" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" , (select nvl(max(seq_no),0) from BOMWEB_CSI_LOG where case_no = :caseNo ) + 1 " +
			" )";
	public int insertCsiCaseLogDTO(CsiCaseLogDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertCsiCaseLogDTO @ MobCcsCsiCaseLogDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertCsiCaseDTOSQL() @ MobCcsCsiCaseDAO: " + insertCsiCaseLogDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", dto.getCaseNo());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("contactName", dto.getContactName());
			params.addValue("contactPhone", dto.getContactPhone());
			params.addValue("contactEmail", dto.getContactEmail());
			params.addValue("callTypeCd", dto.getCallTypeCd());
			params.addValue("contactBy", dto.getContactBy());
			params.addValue("resultCd", dto.getResultCd());
			params.addValue("relWtCust", dto.getRelWtCust());
			params.addValue("remark", dto.getRemark());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			return simpleJdbcTemplate.update(insertCsiCaseLogDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertCsiCaseLogDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
