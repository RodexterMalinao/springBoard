package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;


public class SpecialMrtRequestDAO extends BaseDAO {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static String getNextRequestIdSQL = "select CONCAT('R',LPAD(BOMWEB_SPECIAL_MRT_REQUEST_NO.nextVal, 7, '0')) from dual";
	public String getNextRequestId() throws DAOException {
		String nextRequestId = "";
		try {

			if (logger.isInfoEnabled()) {
				logger.info("getNextRequestId() @ SpecialMrtRequestDAO: " + getNextRequestIdSQL);
			}
			nextRequestId = (String) simpleJdbcTemplate.queryForObject(
					getNextRequestIdSQL, String.class);;
			return nextRequestId ;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in getNextRequestId()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static String insertSpecialMrtRequestSQL = "INSERT INTO" +
			" BOMWEB_SPECIAL_MRT_REQUEST " +
			" ( " +
			" REQUEST_ID, " +
			" REQUEST_DATE, " +
			" REQUEST_BY, " +
			" TITLE, " +
			" FIRST_NAME, " +
			" LAST_NAME, " +
			" CONTRACT_PERIOD, " +
			" RECURRENT_AMT, " +
			" MSISDN_PATTERN, " +
			" REMARK, " +
			" OFFER_VIOLATE_IND, " +
			" CHANNEL, " +
			" REQUESTOR, " +
			" APPROVAL_RESULT, " +
			" CREATE_BY, " +
			" CREATE_DATE, " +
			" LAST_UPD_BY, " +
			" LAST_UPD_DATE " +
			" ) VALUES (" +
			//" (CONCAT('R',LPAD(BOMWEB_SPECIAL_MRT_REQUEST_NO.nextVal, 7, '0'))) " +
			"  :requestId" +
			" , sysdate" +
			" , :requestBy" +
			" , :title" +
			" , :firstName" +
			" , :lastName" +
			" , :contractPeriod" +
			" , :recurrentAmt" +
			" , :msisdnPattern" +
			" , :remark" +
			" , :offerViolateInd" +
			" , :channel" +
			" , :requestor" +
			" , :approvalResult" +
			" , :createBy" +
			" , sysdate" +
			" , :lastUpdBy" +
			" , sysdate" +
			" )";
	
	public String insertSpecialMrtRequest(SpecialMrtRequestDTO dto) throws DAOException {
			
		if (logger.isInfoEnabled()) {
			logger.info("insertSpecialMrtRequest @ SpecialMrtRequestDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info(insertSpecialMrtRequestSQL);
			}
			String nextRequestId = getNextRequestId();
	
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("requestId", nextRequestId);
			params.addValue("requestBy", dto.getRequestBy());
			params.addValue("title", dto.getTitle());
			params.addValue("firstName", dto.getFirstName());
			params.addValue("lastName", dto.getLastName());
			params.addValue("contractPeriod", Integer.parseInt(dto.getContractPeriod().substring(0, dto.getContractPeriod().indexOf("month")-1)));
			params.addValue("recurrentAmt", Integer.parseInt(dto.getRecurrentAmt().substring(1)));
			params.addValue("msisdnPattern", dto.getMsisdnPattern());
			params.addValue("remark", dto.getRemark());
			params.addValue("offerViolateInd",dto.getOfferViolateInd()? "Y":"N");
			params.addValue("channel", dto.getChannel());
			params.addValue("requestor", dto.getRequestor());
			params.addValue("approvalResult", dto.getApprovalResult());
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			simpleJdbcTemplate.update(insertSpecialMrtRequestSQL, params);
			return nextRequestId;
			
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in insertSpecialMrtRequest()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	

	
	private static String getSpecialMrtRequestDTOSQL = "SELECT" +
			" REQUEST_ID" +
			" , REQUEST_DATE" +
			" , TITLE" +
			" , FIRST_NAME" +
			" , LAST_NAME" +
			" , CONTRACT_PERIOD||' months' CONTRACT_PERIOD" +
			" , '$'||RECURRENT_AMT RECURRENT_AMT" +
			" , MSISDN_PATTERN" +
			" , REMARK" +
			" , APPROVAL_RESULT" +
			" , MSISDN" +
			" , MSISDNLVL" +
			" , TO_CHAR(VALID_DATE,'DD/MM/YYYY') VALID_DATE" +
			" , RESERVE_ID" +
			" , RES_OPER_ID" +
			" , APPROVAL_SERIAL" +
			" , ADMIN_REMARK" +
			" , CHANNEL" +
			" , REQUESTOR" +
			" , OFFER_VIOLATE_IND" +
			" , CREATE_BY" +
			" , CREATE_DATE" +
			" , LAST_UPD_BY" +
			" , LAST_UPD_DATE" +
			" FROM BOMWEB_SPECIAL_MRT_REQUEST" +
			" WHERE REQUEST_ID = :requestId";
	
	public SpecialMrtRequestDTO getSpecialMrtRequestDTO(String requestId) throws DAOException {
		logger.info("getSpecialMrtRequestDTO is called");
		
		List<SpecialMrtRequestDTO> specialMrtRequestDTO = new ArrayList<SpecialMrtRequestDTO>();
		
		if (logger.isDebugEnabled()) {
			logger.debug(getSpecialMrtRequestDTOSQL);
			logger.debug("requestId: " + requestId);
		}
		
		ParameterizedRowMapper<SpecialMrtRequestDTO> mapper = new ParameterizedRowMapper<SpecialMrtRequestDTO>() {
			public SpecialMrtRequestDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				SpecialMrtRequestDTO specialMrtRequestDTO = new SpecialMrtRequestDTO();
				specialMrtRequestDTO.setRequestId(rs.getString("REQUEST_ID"));
				specialMrtRequestDTO.setRequestDate(rs.getTimestamp("REQUEST_DATE"));
				specialMrtRequestDTO.setTitle(rs.getString("TITLE"));
				specialMrtRequestDTO.setFirstName(rs.getString("FIRST_NAME"));
				specialMrtRequestDTO.setLastName(rs.getString("LAST_NAME"));
				specialMrtRequestDTO.setContractPeriod(rs.getString("CONTRACT_PERIOD"));
				specialMrtRequestDTO.setRecurrentAmt(rs.getString("RECURRENT_AMT"));		
				specialMrtRequestDTO.setMsisdnPattern(rs.getString("MSISDN_PATTERN"));
				specialMrtRequestDTO.setRemark(rs.getString("REMARK"));
				specialMrtRequestDTO.setChannel(rs.getString("CHANNEL"));
				specialMrtRequestDTO.setRequestor(rs.getString("REQUESTOR"));
				specialMrtRequestDTO.setApprovalResult(rs.getString("APPROVAL_RESULT"));
				specialMrtRequestDTO.setMsisdn(rs.getString("MSISDN"));
				specialMrtRequestDTO.setMsisdnlvl(rs.getString("MSISDNLVL"));
				specialMrtRequestDTO.setValidDate(rs.getString("VALID_DATE"));
				specialMrtRequestDTO.setReserveId(rs.getString("RESERVE_ID"));			
				specialMrtRequestDTO.setResOperId(rs.getString("RES_OPER_ID"));
				specialMrtRequestDTO.setApprovalSerial(rs.getString("APPROVAL_SERIAL"));
				specialMrtRequestDTO.setAdminRemark(rs.getString("ADMIN_REMARK"));
				specialMrtRequestDTO.setChannel(rs.getString("CHANNEL"));
				specialMrtRequestDTO.setRequestor(rs.getString("REQUESTOR"));
				specialMrtRequestDTO.setOfferViolateInd("Y".equalsIgnoreCase(rs.getString("OFFER_VIOLATE_IND")));		
				specialMrtRequestDTO.setCreateBy(rs.getString("CREATE_BY"));
				specialMrtRequestDTO.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				specialMrtRequestDTO.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				specialMrtRequestDTO.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				
				return specialMrtRequestDTO;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("requestId", requestId);
			specialMrtRequestDTO = simpleJdbcTemplate.query(getSpecialMrtRequestDTOSQL, mapper, params);
			
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getSpecialMrtRequestDTO()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getSpecialMrtRequestDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (specialMrtRequestDTO == null || specialMrtRequestDTO.isEmpty()) {
    		return null;
    	}
		return specialMrtRequestDTO.get(0);
		
	}
	
	
	private static String updateSpecialMrtRequestDTOSQL = "update BOMWEB_SPECIAL_MRT_REQUEST " +
			"set " +
			"TITLE = :title, " +
			"FIRST_NAME = :firstName, " +
			"LAST_NAME = :lastName, " +
			"CONTRACT_PERIOD = :contractPeriod, " +
			"RECURRENT_AMT = :recurrentAmt, " +	
			"MSISDN_PATTERN = :msisdnPattern, " +	
			"REMARK = :remark, " +	
			"OFFER_VIOLATE_IND = :offerViolateInd, " +				
			"MSISDN = :msisdn, " +	
			"MSISDNLVL = :msisdnlvl, " +
			"RESERVE_ID = :reserveId, " +
			"RES_OPER_ID = :resOperId, " +
			"VALID_DATE = to_date(:validDate,'DD/MM/YYYY'), " +	
			"APPROVAL_SERIAL = :approvalSerial, " +	
			"ADMIN_REMARK = :adminRemark, " +				
			"LAST_UPD_BY = :lastUpdBy, " +
			"LAST_UPD_DATE = sysdate " +
			"where REQUEST_ID = :requestId ";
	
	private static String updateRejectSpecialMrtRequestDTOSQL = "update BOMWEB_SPECIAL_MRT_REQUEST " +
			"set " +			
			"ADMIN_REMARK = :adminRemark, " +				
			"LAST_UPD_BY = :lastUpdBy, " +
			"LAST_UPD_DATE = sysdate " +
			"where REQUEST_ID = :requestId ";
	
	public int updateSpecialMrtRequestDTO(SpecialMrtRequestDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateSpecialMrtRequestDTO @ SpecialMrtRequestDAO is called");
		}
		try {
			if ("REJECTED".equalsIgnoreCase(dto.getApprovalResult())){
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("requestId", dto.getRequestId());	
				params.addValue("adminRemark", dto.getAdminRemark());				
				params.addValue("lastUpdBy", dto.getLastUpdBy());
				return simpleJdbcTemplate.update(updateRejectSpecialMrtRequestDTOSQL, params);
			}else{
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("requestId", dto.getRequestId());		
				params.addValue("title", dto.getTitle());
				params.addValue("firstName", dto.getFirstName());
				params.addValue("lastName", dto.getLastName());		
				params.addValue("contractPeriod", Integer.parseInt(dto.getContractPeriod().substring(0, dto.getContractPeriod().indexOf("month")-1)));
				params.addValue("recurrentAmt", Integer.parseInt(dto.getRecurrentAmt().substring(1)));
				params.addValue("msisdnPattern", dto.getMsisdnPattern());
				params.addValue("remark", dto.getRemark());
				params.addValue("offerViolateInd", (dto.getOfferViolateInd())? "Y":"N");			
				//params.addValue("approvalResult", dto.getApprovalResult());
				params.addValue("msisdn", dto.getMsisdn());
				params.addValue("msisdnlvl", dto.getMsisdnlvl());
				params.addValue("reserveId", dto.getReserveId());
				params.addValue("resOperId", dto.getResOperId());
				params.addValue("validDate", dto.getValidDate());
				params.addValue("approvalSerial", dto.getApprovalSerial());
				params.addValue("adminRemark", dto.getAdminRemark());
				
				params.addValue("lastUpdBy", dto.getLastUpdBy());
				
				return simpleJdbcTemplate.update(updateSpecialMrtRequestDTOSQL, params);
			}
			
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateSpecialMrtRequestDTO()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static String updateSpecialMrtRequestStatusSQL = "update BOMWEB_SPECIAL_MRT_REQUEST " +
			"set " +
			"APPROVAL_RESULT = :approvalResult, " +			
			"LAST_UPD_BY = :lastUpdBy, " +
			"LAST_UPD_DATE = sysdate " +
			"where REQUEST_ID = :requestId ";
	
	
	public int updateSpecialMrtRequestStatus(SpecialMrtRequestDTO dto) throws DAOException {
		if (logger.isInfoEnabled()) {
			logger.info("updateSpecialMrtRequestStatus @ SpecialMrtRequestDAO is called");
		}
		try {
			if (logger.isInfoEnabled()) {
				logger.info(updateSpecialMrtRequestStatusSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("requestId", dto.getRequestId());
			params.addValue("approvalResult", dto.getApprovalResult());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			
			return simpleJdbcTemplate.update(updateSpecialMrtRequestStatusSQL, params);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Exception caught in updateSpecialMrtRequestStatus()", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
		private static String getSpecialMrtRequestByApprovalSerialSQL = "SELECT" +
				" REQUEST_ID" +
				" FROM BOMWEB_SPECIAL_MRT_REQUEST" +
				" WHERE APPROVAL_SERIAL = :approvalSerial";
		
		
	public String getSpecialMrtRequestByApprovalSerial(String approvalSerial) throws DAOException {
		logger.info("getSpecialMrtRequestByApprovalSerial is called");
			
		List<String> requestIdList = null;
			
		if (logger.isDebugEnabled()) {
			logger.debug(getSpecialMrtRequestByApprovalSerialSQL);
			logger.debug("approvalSerial: " + approvalSerial);
		}
			
			
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("approvalSerial", approvalSerial);
			requestIdList = this.simpleJdbcTemplate.query(getSpecialMrtRequestByApprovalSerialSQL, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("REQUEST_ID");
				}
			}, params);
				
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isDebugEnabled()) {
				logger.debug("EmptyResultDataAccessException in getSpecialMrtRequestByApprovalSerial()");
			}
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getSpecialMrtRequestByApprovalSerial():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if (requestIdList == null || requestIdList.isEmpty()) {
	    	return null;
	    }
		
		return requestIdList.get(0);
	}	
	
}
