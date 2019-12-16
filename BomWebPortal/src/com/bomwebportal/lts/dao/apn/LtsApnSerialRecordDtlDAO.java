package com.bomwebportal.lts.dao.apn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.apn.LtsApnResultDTO;
import com.bomwebportal.lts.dto.apn.LtsApnSerialOrderInfoDTO;

public class LtsApnSerialRecordDtlDAO extends BaseDAO{	
	final String ORDER_STATUS_COMPLETED = "L";
	final String ORDER_STATUS_CANCELLED = "C";
	final String ORDER_STATUS_SIGNOFF = "S";
	final String ORDER_STATUS_EXTRACTED = "E";
	
	final String ORDER_REMARK_STATUS_PENDING = "P";
	final String ORDER_REMARK_STATUS_COMPLETED = "C";
	
	final String SQL_GET_APN_SERIAL_RECORD = "SELECT bos.ORDER_ID, bos.DTL_ID, bos.SRV_NUM, TO_CHAR(ba.CUT_OVER_START_TIME, 'YYYYMMDDHH24MISS') CUT_OVER_START_TIME, "
			+ " TO_CHAR(ba.CUT_OVER_END_TIME, 'YYYYMMDDHH24MISS') CUT_OVER_END_TIME"
			+ " FROM BOMWEB_ORDER_SERVICE bos, BOMWEB_ORDER_STATUS_HIST bosh, BOMWEB_APPOINTMENT ba where bos.ORDER_ID = bosh.ORDER_ID AND bos.DTL_ID = bosh.DTL_ID " 
			+ " and bos.ORDER_ID = ba.ORDER_ID AND bos.DTL_ID = ba.DTL_ID "
			+ " and bosh.LAST_UPD_DATE = (SELECT MAX(LAST_UPD_DATE) from BOMWEB_ORDER_STATUS_HIST where ORDER_ID = bosh.ORDER_ID and DTL_ID = bosh.DTL_ID) ";
	
	final String SQL_GET_ORDER_INFO = "SELECT bos.ORDER_ID, bos.DTL_ID, bopl.IS_PORT_BACK, bosl.SRV_NN, bopl.DUPLEX_ACTION from BOMWEB_ORDER_SERVICE bos, BOMWEB_ORDER_PIPB_LTS bopl, BOMWEB_ORDER_LATEST_STATUS bols, BOMWEB_ORDER_SERVICE_LTS bosl " +
			" where bos.ORDER_ID = bopl.ORDER_ID and bos.ORDER_ID = bols.ORDER_ID and bos.DTL_ID = bols.DTL_ID and bosl.ORDER_ID = bos.ORDER_ID AND bosl.DTL_ID = bos.DTL_ID";	
	
	final String SQL_GET_APN_DN_ORDER_INFO = "SELECT count(*) " +
			" from BOMWEB_ORDER_PIPB_LTS";
	
	final String SQL_GET_APN_SERIAL_BY_SB_ID = "SELECT APN_SERIAL FROM BOMWEB_PIPB_APN_DTL WHERE ORDER_ID = ?";
	
	final String SQL_IS_DUPLEX_COMPLETED = "select SRV_NUM from BOMWEB_PIPB_APN_DTL where ORDER_ID = ? " +
			" and STATUS in ('" + ORDER_REMARK_STATUS_PENDING + "','" + ORDER_REMARK_STATUS_COMPLETED + "')" +
			" group by SRV_NUM";
	
	public boolean isFromDiffCust(String pOrderId){
		StringBuilder sql= new StringBuilder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		sql.append(SQL_GET_APN_DN_ORDER_INFO);		
		sql.append(" and ORDER_ID = :oid");
		paramSource.addValue("oid", pOrderId);
		
		sql.append(" and FROM_DIFF_CUST_IND = 'Y'");
		
		try {
			int count = this.simpleJdbcTemplate.queryForInt(sql.toString(), paramSource);
			if (logger.isDebugEnabled()) {
				logger.debug("count: " + count);
			}
			return count > 0;
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("Exception found", e);
			}
		}
		return false;
	}
	
	public List<LtsApnResultDTO> getApnDtlResultInfo(String pSrvNum, String pOrderId, String pOrderDtl) throws DAOException {
		ParameterizedRowMapper<LtsApnResultDTO> mapper = new ParameterizedRowMapper<LtsApnResultDTO>() {
			public LtsApnResultDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsApnResultDTO info = new LtsApnResultDTO();
				info.setOrderId(rs.getString("ORDER_ID"));
				info.setServiceNum(rs.getString("SRV_NUM"));
				info.setDtlId(rs.getString("DTL_ID"));
				info.setCutOverStartDate(rs.getString("CUT_OVER_START_TIME"));
				info.setCutOverEndDate(rs.getString("CUT_OVER_END_TIME"));
				//info.setSrdDate(rs.getString("SUGGEST_SRD"));
				return info;
			}
		};
		
		StringBuilder sql= new StringBuilder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		sql.append(SQL_GET_APN_SERIAL_RECORD);
		sql.append(" and bos.SRV_NUM = :num");
		paramSource.addValue("num", pSrvNum);
		
		sql.append(" and bos.ORDER_ID = :oid");
		paramSource.addValue("oid", pOrderId);
		
		sql.append(" and bos.DTL_ID = :did");
		paramSource.addValue("did", pOrderDtl);
		
		// ORDER STATUS -> PENDING,  ! L & ! C & ! S & !E
		sql.append(" and bosh.SB_STATUS not in ('" + ORDER_STATUS_COMPLETED + "','" + ORDER_STATUS_CANCELLED + "','" + ORDER_STATUS_SIGNOFF + "','" + ORDER_STATUS_EXTRACTED + "') ");
		
		try {
			return simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getApnSerialFileResults()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public LtsApnSerialOrderInfoDTO getApnDtlOrderInfo(String pSrvNum, String pSrvNn) throws DAOException {
		ParameterizedRowMapper<LtsApnSerialOrderInfoDTO> mapper = new ParameterizedRowMapper<LtsApnSerialOrderInfoDTO>() {
			public LtsApnSerialOrderInfoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsApnSerialOrderInfoDTO info = new LtsApnSerialOrderInfoDTO();
				info.setOrderId(rs.getString("ORDER_ID"));
				info.setDtlId(rs.getString("DTL_ID"));
				info.setIsPortBack(rs.getString("IS_PORT_BACK"));
				info.setSrvNn(rs.getString("SRV_NN"));
				info.setDuplexAction(rs.getString("DUPLEX_ACTION"));
				return info;
			}
		};
		StringBuilder sql= new StringBuilder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		sql.append(SQL_GET_ORDER_INFO);
		sql.append(" and bos.SRV_NUM = :srv"); 
		paramSource.addValue("srv", pSrvNum);
		sql.append(" and bols.SB_STATUS not in ('" + ORDER_STATUS_COMPLETED + "','" + ORDER_STATUS_CANCELLED + "','" + ORDER_STATUS_SIGNOFF + "','" + ORDER_STATUS_EXTRACTED + "') ");
		
		try {
			List<LtsApnSerialOrderInfoDTO> result = simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
			if(result != null && result.size() > 0){
				return result.get(0);
			}
			else{
				sql= new StringBuilder();
				paramSource = new MapSqlParameterSource();
				sql.append(SQL_GET_ORDER_INFO);
				sql.append(" and bosl.SRV_NN = :nn");
				paramSource.addValue("nn", pSrvNn);
				sql.append(" and bols.SB_STATUS not in ('" + ORDER_STATUS_COMPLETED + "','" + ORDER_STATUS_CANCELLED + "','" + ORDER_STATUS_SIGNOFF + "','" + ORDER_STATUS_EXTRACTED + "') ");
				result = simpleJdbcTemplate.query(sql.toString(), mapper, paramSource);
				if(result != null && result.size() > 0){
					return result.get(0);
				}
				else{
					return null;
				}
			}
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getApnDtlOrderInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public boolean isApnExist(String orderId){
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("APN_SERIAL");
			}
		};
		
		List<String> apnList = simpleJdbcTemplate.query(SQL_GET_APN_SERIAL_BY_SB_ID, mapper, orderId);
		
		if(apnList.size()> 0){
			return true;
		}
		
		return false;
	}
	
	public boolean isDuplexCompleted(String pOrderId){
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("SRV_NUM");
			}
		};
		
		List<String> result = simpleJdbcTemplate.query(SQL_IS_DUPLEX_COMPLETED, mapper, pOrderId);
		
		if(result.size()>1){
			return true;
		}
		
		return false;
	}
}
