package com.bomwebportal.mob.ds.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;


import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ds.dto.MobDsFalloutRecordDTO;
import com.bomwebportal.util.Utility;


public class MobDsFalloutRecordDAO extends BaseDAO{

	/*Show all fallout record from same orderID*/
	

	private static String getFalloutRecordALLByOrderIdSQL = "SELECT " +
			 "CASE_NO " +
			 ", ORDER_ID " +
			 ", STAFF_ID " +
			 ", FALLOUT_STATUS " +
			 ", FALLOUT_TYPE " +
			 ", FALLOUT_CAT_OPT " +
			 ", REMARK " +
			 ", CREATE_BY " +
			 ", CREATE_DATE " +
			 ", LAST_UPD_BY " +
			 ", LAST_UPD_DATE " +
			 "FROM bomweb_ds_fallout " +
			 "WHERE order_ID = :orderId " +
			 "ORDER BY CREATE_DATE DESC";
	
	//private static String getFalloutRecordALLByOrderIdSQL = "select CASE_NO,ORDER_ID,STAFF_ID, FALLOUT_STATUS, FALLOUT_CAT_OPT, REMARK, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE from BOMWEB_DS_FALLOUT where order_ID='DGKNM000015'";
	
	public List<MobDsFalloutRecordDTO> getFalloutRecordALLByOrderId(String orderId) throws DAOException {
		List<MobDsFalloutRecordDTO> FalloutRecordList = Collections.emptyList();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getFalloutRecordALLByOrderId() @ MobDsFalloutRecordDAO: " + getFalloutRecordALLByOrderIdSQL);
			}
			FalloutRecordList = this.simpleJdbcTemplate.query(getFalloutRecordALLByOrderIdSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getFalloutRecordALLByOrderId()");
			}
			FalloutRecordList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getFalloutRecordALLByOrderId():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return FalloutRecordList;
	}
	
	/*Further show the fallout record from case no. in same orderID*/
	private static String getFalloutRecordALLByCaseNoSQL = "select "+
			 "CASE_NO, " +
			 "ORDER_ID, " +
			 "STAFF_ID, " +
			 "FALLOUT_STATUS, " +
			 "FALLOUT_TYPE, " +
			 "FALLOUT_CAT_OPT, " +
			 "REMARK, " +
			 "CREATE_BY, " +
			 "CREATE_DATE, " +
			 "LAST_UPD_BY, " +
			 "LAST_UPD_DATE " +
			 "from BOMWEB_DS_FALLOUT " +
			 "WHERE CASE_NO = :caseNo " +
			 "ORDER BY CREATE_DATE DESC";

	public MobDsFalloutRecordDTO getFalloutRecordALLByCaseNo(String caseNo) throws DAOException {
		List<MobDsFalloutRecordDTO> itemList = Collections.emptyList();
		MobDsFalloutRecordDTO resultDto = new MobDsFalloutRecordDTO();
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("caseNo", caseNo);
			if (logger.isInfoEnabled()) {
				logger.info("getFalloutRecordALLByCaseNo() @ MobDsFalloutRecordDAO: " + getFalloutRecordALLByCaseNoSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getFalloutRecordALLByCaseNoSQL, this.getRowMapper(), params);
			resultDto = itemList.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getFalloutRecordALLByCaseNo()");
			}
			
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getFalloutRecordALLByCaseNo():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return resultDto;
	}
	
	
	private ParameterizedRowMapper<MobDsFalloutRecordDTO> getRowMapper() {
		return new ParameterizedRowMapper<MobDsFalloutRecordDTO>() {
			public MobDsFalloutRecordDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobDsFalloutRecordDTO dto = new MobDsFalloutRecordDTO();
				dto.setCaseNo(rs.getString("CASE_NO"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setStaffId(rs.getString("STAFF_ID"));
				dto.setFalloutStatus(rs.getString("FALLOUT_STATUS"));
				dto.setFalloutType(rs.getString("FALLOUT_TYPE"));
				dto.setFalloutCatOpt(rs.getString("FALLOUT_CAT_OPT"));
				dto.setRemark(rs.getString("REMARK"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE")); 
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				
				
				return dto;
			}
		};
	}
	
	
	private static String insertFalloutRecordDTOSQL = "INSERT INTO" +
			" BOMWEB_DS_FALLOUT " +
			" ( " +
			" CASE_NO, " +
			" ORDER_ID, " +
			" STAFF_ID, " +
			" FALLOUT_STATUS, " +
			" FALLOUT_TYPE, " +
			" FALLOUT_CAT_OPT, " +
			" REMARK, " +
			" CREATE_BY, " +
			" CREATE_DATE, " +
			" LAST_UPD_BY, " +
			" LAST_UPD_DATE " +
			" ) VALUES (" +
			" BOMWEB_FALLOUT_NO.nextVal " +
			" , :orderId" +
			" , :staffId" +
			" , :falloutStatus" +
			" , :falloutType" +
			" , :falloutCatOpt" +
			" , :remark" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" )";
	
	/*private static String insertFalloutRecordDTOSQL = "INSERT INTO" +
			" BOMWEB_DS_FALLOUT " +
			" ( " +
			" CASE_NO, " +
			" ORDER_ID, " +
			" STAFF_ID, " +
			" FALLOUT_STATUS, " +
			" FALLOUT_CAT_OPT, " +
			" REMARK, " +
			" CREATE_BY, " +
			" CREATE_DATE, " +
			" LAST_UPD_BY, " +
			" LAST_UPD_DATE " +
			" ) VALUES (" +
			" (select nvl(max(Case_NO),0) + 1 from bomweb_ds_fallout) " +
			" , :orderId" +
			" , :staffId" +
			" , :falloutStatus" +
			" , :falloutCatOpt" +
			" , :remark" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" )";*/
	public int insertFalloutRecordDTO(MobDsFalloutRecordDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertFalloutRecordDTOSQL @ MobDsFalloutRecordDAO is called");
		}
		
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertFalloutRecordDTOSQL() @ MobDsFalloutRecordDAO: " + insertFalloutRecordDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
		/*	System.out.print(dto.getFalloutStatus());
			System.out.print( dto.getOrderId());
			System.out.print(dto.getStaffId());
			System.out.print(dto.getFalloutCatOpt());
			
			System.out.print(dto.getRemark());
			System.out.print(dto.getCreateBy());
			System.out.print(dto.getLastUpdBy());*/
			
			//params.addValue("caseNo", dto.getCaseNo());
			params.addValue("orderId", dto.getOrderId());
			params.addValue("staffId", dto.getStaffId());
			params.addValue("falloutStatus", Integer.parseInt(dto.getFalloutStatus()));
			params.addValue("falloutType", dto.getFalloutType());
			params.addValue("falloutCatOpt", Integer.parseInt(dto.getFalloutCatOpt()));
			params.addValue("remark", dto.getRemark());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
	
			return simpleJdbcTemplate.update(insertFalloutRecordDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertFalloutRecordDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static String updateFalloutRecordDTOSQL = "update BOMWEB_DS_FALLOUT " +
			"set " +
			"FALLOUT_STATUS = :falloutStatus, " +
			"FALLOUT_TYPE = :falloutType, " +
			"FALLOUT_CAT_OPT = :falloutCatOpt, " +
			"REMARK = :remark, " +
			"LAST_UPD_BY = :lastUpdBy, " +
			"LAST_UPD_DATE = sysdate " +	
			"where case_no = :caseNo ";
	public int updateFalloutRecordDTO(MobDsFalloutRecordDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateFalloutRecordDTO @ MobDsFalloutRecordDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info("updateFalloutRecordDTO() @ MobDsFalloutRecordDAO: " + updateFalloutRecordDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("caseNo", dto.getCaseNo());
			params.addValue("falloutStatus", Integer.parseInt(dto.getFalloutStatus()));
			params.addValue("falloutType", dto.getFalloutType());
			params.addValue("falloutCatOpt", Integer.parseInt(dto.getFalloutCatOpt()));
			params.addValue("remark", dto.getRemark());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			
			return simpleJdbcTemplate.update(updateFalloutRecordDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateFalloutRecordDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String getLatestCaseNoByOrderIdSQL = "select nvl(max(to_number(case_no)),0) from bomweb_ds_fallout where order_id = :orderId ";

	public int getLatestCaseNoByOrderId(String orderId) throws DAOException {
		int latestCaseNo = 0;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("getLatestCaseNoByOrderId() @ MobDsFalloutRecordDAO: " + getLatestCaseNoByOrderIdSQL);
			}
			latestCaseNo = (int) simpleJdbcTemplate.queryForObject(
					getLatestCaseNoByOrderIdSQL, Integer.class, orderId);;
			
			return latestCaseNo ;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in getLatestCaseNoByOrderId()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private static String hasUnsettledFalloutSQL = "select count(case_no) from bomweb_ds_fallout where order_id = :orderId and fallout_status !=6";

	public boolean hasUnsettledFallout(String orderId) throws DAOException {
		int caseCount = 0;
		try {
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);
			if (logger.isInfoEnabled()) {
				logger.info("hasUnsettledFallout() @ MobDsFalloutRecordDAO: " + hasUnsettledFalloutSQL);
			}
			caseCount = (int) simpleJdbcTemplate.queryForObject(
					hasUnsettledFalloutSQL, Integer.class, orderId);;
			
			return (caseCount > 0);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in hasUnsettledFallout()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
}
