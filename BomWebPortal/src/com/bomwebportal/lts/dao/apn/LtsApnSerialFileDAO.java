package com.bomwebportal.lts.dao.apn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;

public class LtsApnSerialFileDAO extends BaseDAO{
	final String MESSAGE_STATUS_COMPLETED = "Order remark update completed";
	final String MESSAGE_STATUS_OUTSTANDING = "Outstanding order remark";
	final String MESSAGE_STATUS_PENDING_TO_UPDATE = "Pending for order remark update";
	final String STATUS_COMPLETED = "C";
	final String STATUS_OUTSTANDING = "O";
	final String STATUS_PENDING_TO_UPDATE = "P";
	
	final String SQL_GET_APN_SERIAL_FILE_INFO = "select BATCH_SEQ, FILE_NAME, FILE_STATUS, CREATE_DATE, FAILED_REASON " +
			" from BOMWEB_PIPB_APN_FILE ";

	final String SQL_GET_APN_DTL_STATUS_INFO = "select BATCH_SEQ, SRV_NUM, SRV_NN, IS_DN_MATCH, IS_NN_MATCH, IS_DATE_TIME_MATCH, STATUS, " +
			" TYPE_OF_DOC, TO_CHAR(APP_DATE, 'DD-MON-YYYY HH24:MI:SS') APP_DATE, TO_CHAR(BATCH_DATE, 'DD-MON-YYYY HH24:MI:SS') BATCH_DATE, " +
			" TO_CHAR(CHGOVER_START_TIME, 'DD-MON-YYYY HH24:MI:SS') CHGOVER_START_TIME, TO_CHAR(CHGOVER_END_TIME, 'DD-MON-YYYY HH24:MI:SS') CHGOVER_END_TIME, " +
			" DTL_SEQ, ORDER_ID, DTL_ID, STATUS_MESSAGE, APN_SERIAL " +
			" from BOMWEB_PIPB_APN_DTL ";
	
	public List<LtsApnDnDTO> getApnDtlStatusInfo(String pBatchSeq, String pSrvNum) throws DAOException {
		ParameterizedRowMapper<LtsApnDnDTO> mapper = new ParameterizedRowMapper<LtsApnDnDTO>() {
			public LtsApnDnDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsApnDnDTO info = new LtsApnDnDTO();
				info.setBatchSeq(rs.getString("BATCH_SEQ"));
				info.setDtlSeq(rs.getString("DTL_SEQ"));
				info.setApnSerial(rs.getString("APN_SERIAL"));
				info.setSrvNum(rs.getString("SRV_NUM"));
				info.setSrvNn(rs.getString("SRV_NN"));
				info.setIsDnMatch(rs.getString("IS_DN_MATCH"));
				info.setIsNnMatch(rs.getString("IS_NN_MATCH"));
				info.setIsDateTimeMatch(rs.getString("IS_DATE_TIME_MATCH"));		
				info.setTypeOfDoc(rs.getString("TYPE_OF_DOC"));
				info.setAppDate(rs.getString("APP_DATE"));
				info.setBatchDate(rs.getString("BATCH_DATE"));
				info.setChgoverStartTime(rs.getString("CHGOVER_START_TIME"));
				info.setChgoverEndTime(rs.getString("CHGOVER_END_TIME"));
				info.setOrderId(rs.getString("ORDER_ID"));
				info.setDtlId(rs.getString("DTL_ID"));
				
				String status = rs.getString("STATUS");
				info.setStatus(status);		
				if(STATUS_PENDING_TO_UPDATE.equals(status)){
					info.setStatusMessage(MESSAGE_STATUS_PENDING_TO_UPDATE);
				}
				else if(STATUS_COMPLETED.equals(status)){
					info.setStatusMessage(MESSAGE_STATUS_COMPLETED);
				}
				else if(STATUS_OUTSTANDING.equals(status)){
					info.setStatusMessage(MESSAGE_STATUS_OUTSTANDING);
				}
				else{
					info.setStatusMessage(rs.getString("STATUS_MESSAGE"));
				}
				return info;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		sql.append(SQL_GET_APN_DTL_STATUS_INFO);
		sql.append(" where BATCH_SEQ = :batch");
		paramSource.addValue("batch", pBatchSeq);
		
		// Consider only APN DN information
		sql.append(" and TYPE_OF_DOC = 'APN' ");
		
		// If particular service DN is required, search only for the number 
		// otherwise search all dn under the same batch seq
		if(StringUtils.isNotBlank(pSrvNum)){		
			sql.append(" and SRV_NUM = :srv");
			paramSource.addValue("srv", pSrvNum);
		}
		
		try {
			return simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getApnSerialFileResults()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public LtsApnFileDTO getApnSerialFileInfo(String pBatchSeq) throws DAOException {
		ParameterizedRowMapper<LtsApnFileDTO> mapper = new ParameterizedRowMapper<LtsApnFileDTO>() {
			public LtsApnFileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsApnFileDTO info = new LtsApnFileDTO();
				info.setBatchSeq(rs.getString("BATCH_SEQ"));
				info.setFileName(rs.getString("FILE_NAME"));
				info.setStatus(rs.getString("FILE_STATUS"));
				info.setUploadDate(rs.getString("CREATE_DATE"));
				info.setFailedReason(rs.getString("FAILED_REASON"));				
				return info;
			}
		};
		StringBuilder sql= new StringBuilder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		sql.append(SQL_GET_APN_SERIAL_FILE_INFO);
		sql.append(" where BATCH_SEQ = :batch ");
		paramSource.addValue("batch", pBatchSeq);
		
		try {
			List<LtsApnFileDTO> result = simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
			if(result != null){
				return result.get(0);
			}
			else{
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getApnSerialFileResults()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}		
	
	public List<LtsApnFileDTO> getApnSerialFileResults(String pFrom, String pTo, String pStatus) throws DAOException {
		ParameterizedRowMapper<LtsApnFileDTO> mapper = new ParameterizedRowMapper<LtsApnFileDTO>() {
			public LtsApnFileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsApnFileDTO info = new LtsApnFileDTO();
				info.setBatchSeq(rs.getString("BATCH_SEQ"));
				info.setFileName(rs.getString("FILE_NAME"));
				info.setStatus(rs.getString("FILE_STATUS"));
				info.setUploadDate(rs.getString("CREATE_DATE"));
				info.setFailedReason(rs.getString("FAILED_REASON"));				
				return info;
			}
		};
		StringBuilder sql= new StringBuilder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		sql.append(SQL_GET_APN_SERIAL_FILE_INFO);
		sql.append(" where ");
		
		if(StringUtils.isBlank(pStatus)){
			sql.append(" FILE_STATUS like '%' ");
		}
		else{
			sql.append(" FILE_STATUS = :state ");
			paramSource.addValue("state", pStatus);
		}
		
		if(StringUtils.isNotBlank(pFrom)){
			sql.append(" AND CREATE_DATE >= TO_DATE( :uploadFrom, 'yyyy-mm-dd HH24:MI:SS') ");
			paramSource.addValue("uploadFrom", pFrom);
		}
		
		if(StringUtils.isNotBlank(pTo)){
			sql.append(" AND CREATE_DATE <= TO_DATE( :uploadTo, 'yyyy-mm-dd HH24:MI:SS') ");
			paramSource.addValue("uploadTo", pTo);
		}
		
		sql.append(" order by CREATE_DATE desc ");
		
		try {
			return simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getApnSerialFileResults()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}		
}
