package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.mob.ccs.dto.ui.SpecialMRTReserveUI;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;


public class SpecialMRTReserveDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<SpecialMRTReserveUI> getSpecialReserveMRTList(String staffId) throws DAOException {
		
		String sql = "select request_id, request_date, first_name, last_name, msisdn_pattern, approval_result, valid_date valid_date_till, msisdn, msisdnlvl" + 
				 	 " from bomweb_special_mrt_request" +
				 	 " where request_by = :staffId and (approval_result='REQUEST' or approval_result='APPROVED' or approval_result='PROCEED' or approval_result='PRETACHED')" +
				 	 " union" +
				 	 " select request_id, request_date, first_name, last_name, msisdn_pattern, approval_result, valid_date valid_date_till, msisdn, msisdnlvl" +
				 	 " from bomweb_special_mrt_request" +
				 	 " where request_by = :staffId and (approval_result='REJECTED' or approval_result='EXPIRED') and last_upd_date between (sysdate-4) and sysdate" +
				 	 " order by request_id desc";
		
		List<SpecialMRTReserveUI> list = null;
		
		try {
			logger.info("sql: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			list = simpleJdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(SpecialMRTReserveUI.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("empty result", erdae);
		}
		
		return list;
	}
	
	private static String insertSpecialMrtReserveSQL = "INSERT INTO" +
			" BOMWEB_MRT_STATUS " +
			" ( " +
			" STAFF_ID, " +
			" SRV_NUM_TYPE, " +
			" MSISDN, " +
			" MSISDNLVL, " +
			" STATUS, " +
			" APPLY_DATE, " +
			" RESERVE_TYPE, " +
			" RESERVE_ID, " +
			" RES_OPER_ID, " +
			" APPROVAL_SERIAL, " +
			" REQUEST_ID, " +
			" RES_CUST_NAME, " +
			" CREATED_BY, " +
			" CREATED_DATE, " +
			" LAST_UPD_BY, " +
			" LAST_UPD_DATE " +
			" ) VALUES (" +
			"   :staffId" +
			" , :srvNumType" +
			" , :msisdn" +
			" , :msisdnlvl" +
			" , '18'" +
			" , sysdate" +
			" , 'SPECIAL'" +
			" , :reserveId" +
			" , :operId" +
			" , :approvalSerial" +
			" , :requestId" +
			" , (select bsmr.TITLE || ' ' || bsmr.LAST_NAME || ' ' || bsmr.FIRST_NAME CONTACT_NAME from bomweb_special_mrt_request bsmr where request_id = :requestId)" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" )";
	
	public int insertSpecialMrtReserve(SpecialMrtRequestDTO dto, String staffId, String srvNumType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("insertSpecialMrtReserve @ SpecialMrtReserveDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info(insertSpecialMrtReserveSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", dto.getRequestor());
			params.addValue("srvNumType", srvNumType);
			params.addValue("msisdn", dto.getMsisdn());
			params.addValue("msisdnlvl", dto.getMsisdnlvl());
			params.addValue("reserveId",dto.getReserveId());
			params.addValue("operId", dto.getResOperId());
			params.addValue("approvalSerial", dto.getApprovalSerial());
			params.addValue("requestId", dto.getRequestId());
			params.addValue("createBy", staffId);
			params.addValue("lastUpdBy", staffId);
			return simpleJdbcTemplate.update(insertSpecialMrtReserveSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertSpecialMrtReserve()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
	
	private static String updateSpecialMrtReserveDTOSQL = "update BOMWEB_MRT_STATUS " +
			"set " +
			"STAFF_ID = :staffId, " +
			"SRV_NUM_TYPE = :srvNumType, " +
			"MSISDN = :msisdn, " +
			"MSISDNLVL = :msisdnlvl, " +
			"STATUS = '18', " +	
			"RESERVE_TYPE = 'SPECIAL', " +
			"RESERVE_ID = :reserveId, " +	
			"RES_OPER_ID = :operId, " +				
			"APPROVAL_SERIAL = :approvalSerial, " +	
			"REQUEST_ID = :requestId, " +			
			"LAST_UPD_BY = :lastUpdBy, " +
			"LAST_UPD_DATE = sysdate " +
			"where REQUEST_ID = :requestId ";
	
	public int updateSpecialMrtReserve(SpecialMrtRequestDTO dto, String staffId, String srvNumType) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateSpecialMrtReserve @ SpecialMrtReserveDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info(updateSpecialMrtReserveDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", dto.getRequestor());
			params.addValue("srvNumType", srvNumType);
			params.addValue("msisdn", dto.getMsisdn());
			params.addValue("msisdnlvl", dto.getMsisdnlvl());
			params.addValue("reserveId", dto.getReserveId());
			params.addValue("operId", dto.getResOperId());
			params.addValue("approvalSerial", dto.getApprovalSerial());		
			params.addValue("requestId", dto.getRequestId());		
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			
			return simpleJdbcTemplate.update(updateSpecialMrtReserveDTOSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateSpecialMrtReserveDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	private static String getSpecialMrtReserveByRequestIdSQL = "SELECT" +
			" COUNT(*)" +
			" FROM BOMWEB_MRT_STATUS" +
			" WHERE REQUEST_ID = :requestId";
	
	
	public boolean getSpecialMrtReserveByRequestId(String requestId) throws DAOException {
		
			logger.info("getSpecialMrtReserveByRequestId is called");
		
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("requestId", requestId);
			try {
				int count = this.simpleJdbcTemplate.queryForInt(getSpecialMrtReserveByRequestIdSQL, params);
				if (logger.isDebugEnabled()) {
					logger.debug("SQL: " + getSpecialMrtReserveByRequestIdSQL);
					logger.debug("count: " + count);
				}
				return count > 0;

			} catch (EmptyResultDataAccessException erdae) {
				logger.info("EmptyResultDataAccessException");
				return false;
			} catch (Exception e) {
				logger.error("Exception caught in getSpecialMrtReserveByRequestId()", e);
				throw new DAOException(e.getMessage(), e);
			}
		
	
		
	}
	
	private static String getMrtReserveByMrtSQL = "SELECT" +
			"  request_id , status , msisdn " +
			" FROM BOMWEB_MRT_STATUS" +
			" WHERE msisdn = :msisdn";
	
	
	public List<MrtReserveDTO> getMrtReserveByMrt(String msisdn) throws DAOException {
		
		logger.info("getMrtReserveByMrt is called");
		List<MrtReserveDTO> list = new ArrayList<MrtReserveDTO>();


		ParameterizedRowMapper<MrtReserveDTO> mapper = new ParameterizedRowMapper<MrtReserveDTO>() {
			public MrtReserveDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MrtReserveDTO dto = new MrtReserveDTO();
				dto.setRequestId(rs.getString("request_id"));
				dto.setMsisdn(rs.getString("msisdn"));
				dto.setStatus(rs.getString("status"));
							
				return dto;
			}
		};
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("msisdn", msisdn);
			
			list = simpleJdbcTemplate.query(getMrtReserveByMrtSQL, mapper, params);
	
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException");

		} catch (Exception e) {
			logger.error("Exception caught in getMrtReserveByMrt()", e);
			throw new DAOException(e.getMessage(), e);
		}
	
        return CollectionUtils.isEmpty(list) ? null : list;

	
}
	
	

	
	public Integer noOfRequests(String staffId) throws DAOException {
		
		String sql="select count(*) from bomweb_special_mrt_request where request_by= :staffId" +
				" and (approval_result='REQUEST' or approval_result='APPROVED' or approval_result='PROCEED' or approval_result='PRETACHED')";
		Integer count = 0;
		
		try {
			logger.info("sql: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			count = simpleJdbcTemplate.queryForInt(sql, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("empty result", erdae);
		}
		
		
		return count;
		
	}
	
	
	
/*	public List<SpecialMRTReserveUI> getRejectedSpecialMRTRequest(String staffId)throws DAOException {
		
		String sql = "select * from bomweb_special_mrt_request where request_by=:staffId and approval_result='REJECTED'";
		List<SpecialMRTReserveUI> list = null;
		
		
		try {
			logger.info("sql: "+sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			list=simpleJdbcTemplate.query(sql,ParameterizedBeanPropertyRowMapper.newInstance(SpecialMRTReserveUI.class), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("empty result", erdae);
		}
		
		return list;
		
	}*/
	
	
	
	
	
	
	
}
