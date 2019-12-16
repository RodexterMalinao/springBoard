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
import com.bomwebportal.util.Utility;

public class MobCcsCsiCaseDAO extends BaseDAO {
	
	private static String getCsiCaseALLByOrderIdSQL = "select "+
			 "CASE_NO, " +
			 "ORDER_ID, " +
			 "FOLLOW_UP_TYPE, " +
			 "CASE_STATUS, " +
			 "NEXT_CALL_SCH_DATE, " +
			 "ONSITE_VISIT_IND, " +
			 "SMS_COUNT, " +
			 "CONTACT_COUNT, " +
			 "REMARK, " +
			 "CREATE_BY, " +
			 "CREATE_DATE, " +
			 "LAST_UPD_BY, " +
			 "LAST_UPD_DATE, " +
			 "OVI_CREATE_DATE," +
			 "FALLOUT_REPORT_DATE, " +
			 "FALLOUT_REPORT_TSLOT," +
			 "ONSITE_VISIT_RESULT, " +
			 "NEXT_CALL_SCH_TIME " +
			 "from BOMWEB_CSI " +
			 "WHERE ORDER_ID = :orderId " +
			 "ORDER BY CREATE_DATE DESC";
	public List<CsiCaseDTO> getCsiCaseALLByOrderId(String orderId) throws DAOException {
		List<CsiCaseDTO> itemList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getCsiCaseALLByOrderId() @ MobCcsCsiCaseDAO: " + getCsiCaseALLByOrderIdSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getCsiCaseALLByOrderIdSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCsiCaseALLByOrderId()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCsiCaseALLByOrderId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private static String getCsiCaseALLByCaseNoSQL = "select "+
			 "CASE_NO, " +
			 "ORDER_ID, " +
			 "FOLLOW_UP_TYPE, " +
			 "CASE_STATUS, " +
			 "NEXT_CALL_SCH_DATE, " +
			 "ONSITE_VISIT_IND, " +
			 "SMS_COUNT, " +
			 "CONTACT_COUNT, " +
			 "REMARK, " +
			 "CREATE_BY, " +
			 "CREATE_DATE, " +
			 "LAST_UPD_BY, " +
			 "LAST_UPD_DATE, " +
			 "OVI_CREATE_DATE," +
			 "FALLOUT_REPORT_DATE, " +
			 "FALLOUT_REPORT_TSLOT," +
			 "ONSITE_VISIT_RESULT," +
			 "NEXT_CALL_SCH_TIME " +
			 "from BOMWEB_CSI " +
			 "WHERE CASE_NO = :caseNo " +
			 "ORDER BY CREATE_DATE DESC";
	public CsiCaseDTO getCsiCaseALLByCaseNo(String caseNo) throws DAOException {
		List<CsiCaseDTO> itemList = Collections.emptyList();
		CsiCaseDTO resultDto = new CsiCaseDTO();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", caseNo);
			if (logger.isInfoEnabled()) {
				logger.info("getCsiCaseALLByCaseNo() @ MobCcsCsiCaseDAO: " + getCsiCaseALLByCaseNoSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getCsiCaseALLByCaseNoSQL, this.getRowMapper(), params);
			resultDto = itemList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getCsiCaseALLByCaseNo()");
			}
			
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getCsiCaseALLByCaseNo():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return resultDto;
	}
	
	private ParameterizedRowMapper<CsiCaseDTO> getRowMapper() {
		return new ParameterizedRowMapper<CsiCaseDTO>() {
			public CsiCaseDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				CsiCaseDTO dto = new CsiCaseDTO();
				dto.setCaseNo(rs.getString("CASE_NO"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setFollowUpType(rs.getString("FOLLOW_UP_TYPE"));
				dto.setCaseStatus(rs.getString("CASE_STATUS"));
				dto.setNextCallSchDate(rs.getTimestamp("NEXT_CALL_SCH_DATE"));
				if (rs.getString("ONSITE_VISIT_IND") != null && rs.getString("ONSITE_VISIT_IND").equalsIgnoreCase("Y")){
					dto.setOnsiteVisitInd(true);
				} else {
					dto.setOnsiteVisitInd(false);
				}
				dto.setSmsCount(rs.getString("SMS_COUNT"));
				dto.setContactCount(rs.getString("CONTACT_COUNT"));
				dto.setRemark(rs.getString("REMARK"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setOVICreateDate(rs.getTimestamp("OVI_CREATE_DATE"));
				dto.setFalloutReportDate(rs.getTimestamp("FALLOUT_REPORT_DATE"));
				dto.setFalloutReportDateStr(Utility.date2String(rs.getTimestamp("FALLOUT_REPORT_DATE"), "dd/MM/yyyy"));
				dto.setFalloutReportTSlot(rs.getString("FALLOUT_REPORT_TSLOT"));
				dto.setOnsiteVisitResult(rs.getString("ONSITE_VISIT_RESULT"));
				dto.setNextCallSchTime(rs.getString("NEXT_CALL_SCH_TIME"));
				return dto;
			}
		};
	}

	private static String insertCsiCaseDTOSQL = "INSERT INTO" +
			" BOMWEB_CSI" +
			" (" +
			" CASE_NO, " +
			" ORDER_ID, " +
			" FOLLOW_UP_TYPE, " +
			" CASE_STATUS, " +
			" NEXT_CALL_SCH_DATE, " +
			" REMARK, " +
			" CREATE_BY, " +
			" CREATE_DATE, " +
			" LAST_UPD_BY, " +
			" LAST_UPD_DATE, " +
			" SMS_COUNT, " +
			" CONTACT_COUNT, " +
			" FALLOUT_REPORT_DATE, " +
			" FALLOUT_REPORT_TSLOT, " +
			" NEXT_CALL_SCH_TIME " +
			" ) VALUES (" +
			" BOMWEB_CSI_CASE_NO.nextVal " +
			" , :orderId" +
			" , :followUpType" +
			" , :caseStatus" +
			" , :nextCallSchDate" +
			" , :remark" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" , 0 " +
			" , 0 " +
			" , :falloutReportDate " +
			" , :falloutReportTSlot " +
			" , :nextCallSchTime" +
			" )";
	public int insertCsiCaseDTO(CsiCaseDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertCsiCaseDTOSQL @ MobCcsCsiCaseDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertCsiCaseDTOSQL() @ MobCcsCsiCaseDAO: " + insertCsiCaseDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", dto.getCaseNo());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("followUpType", dto.getFollowUpType());
			params.addValue("caseStatus", dto.getCaseStatus());
			params.addValue("nextCallSchDate", dto.getNextCallSchDate());
			params.addValue("remark", dto.getRemark());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("falloutReportDate", dto.getFalloutReportDate());
			params.addValue("falloutReportTSlot", dto.getFalloutReportTSlot());
			params.addValue("nextCallSchTime", dto.getNextCallSchTime());
			return simpleJdbcTemplate.update(insertCsiCaseDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertCsiCaseDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String updateCsiCaseDTOSQL = "update BOMWEB_CSI " +
			"set " +
			"FOLLOW_UP_TYPE = :followUpType, " +
			"CASE_STATUS = :caseStatus, " +
			"NEXT_CALL_SCH_DATE = :nextCallSchDate, " +
			"REMARK = :remark, " +
			"LAST_UPD_BY = :lastUpdBy, " +
			"LAST_UPD_DATE = sysdate," +
			"FALLOUT_REPORT_DATE = :falloutReportDate, " +
			"FALLOUT_REPORT_TSLOT = :falloutReportTSlot, " +
			"ONSITE_VISIT_IND = :onsiteVisitInd, " +
			"OVI_CREATE_DATE = :oviCreateDate," +
			"NEXT_CALL_SCH_TIME = :nextCallSchTime " +
			"where case_no = :caseNo ";
	public int updateCsiCaseDTO(CsiCaseDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateCsiCaseDTO @ MobCcsCsiCaseDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("updateCsiCaseDTO() @ MobCcsCsiCaseDAO: " + updateCsiCaseDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("caseNo", dto.getCaseNo());
			params.addValue("followUpType", dto.getFollowUpType());
			params.addValue("caseStatus", dto.getCaseStatus());
			params.addValue("nextCallSchDate", dto.getNextCallSchDate());
			params.addValue("remark", dto.getRemark());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("onsiteVisitInd", (dto.getOnsiteVisitInd()?"Y":""));
			params.addValue("falloutReportDate", dto.getFalloutReportDate());
			params.addValue("falloutReportTSlot", dto.getFalloutReportTSlot());
			params.addValue("oviCreateDate", dto.getOVICreateDate());
			params.addValue("nextCallSchTime", dto.getNextCallSchTime());
			
			return simpleJdbcTemplate.update(updateCsiCaseDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateCsiCaseDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String updateSmsCountSQL = "UPDATE BOMWEB_CSI " +
			"SET " +
			"SMS_COUNT = SMS_COUNT + 1 " +
			"WHERE CASE_NO = :caseNo ";
	public int updateSmsCount(String caseNo) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateSmsCount @ MobCcsCsiCaseDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("updateSmsCount() @ MobCcsCsiCaseDAO: " + updateSmsCountSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", caseNo);

			return simpleJdbcTemplate.update(updateSmsCountSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateSmsCount()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String updateContactCountSQL = "UPDATE BOMWEB_CSI " +
			"SET " +
			"CONTACT_COUNT = CONTACT_COUNT + 1 " +
			"WHERE CASE_NO = :caseNo ";
	public int updateContactCount(String caseNo) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateContactCount @ MobCcsCsiCaseDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("updateContactCount() @ MobCcsCsiCaseDAO: " + updateContactCountSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", caseNo);

			return simpleJdbcTemplate.update(updateContactCountSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateContactCount()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
}
