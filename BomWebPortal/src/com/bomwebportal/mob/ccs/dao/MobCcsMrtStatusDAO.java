package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MrtStatusDTO;

public class MobCcsMrtStatusDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	public List<MrtStatusDTO> getMrtStatusDTOByMsisdn(String msisdn) throws DAOException {
		return this.getMrtStatusDTOByMsisdn(msisdn, null);
	}
	
	public List<MrtStatusDTO> getMrtStatusDTOByMsisdn(String msisdn, Integer status) throws DAOException {
		String sql = "SELECT" +
				" STAFF_ID" +
				", ORDER_ID" +
				", SRV_NUM_TYPE" +
				", MSISDN" +
				", MSISDNLVL" +
				", STATUS" +
				", CITY_CD" +
				", APPLY_DATE" +
				", CREATED_BY" +
				", CREATED_DATE" +
				", LAST_UPD_BY" +
				", LAST_UPD_DATE" +
				", rowid ROW_ID" +
				" FROM BOMWEB_MRT_STATUS" +
				" WHERE MSISDN = :msisdn";
		if (status != null) {
			sql += " AND STATUS = :status";
		}
		List<MrtStatusDTO> itemList = Collections.emptyList();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getMrtStatusDTOByMsisdn() @ MobCcsMrtStatusDAO: " + sql);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("msisdn", msisdn);
			if (status != null) {
				params.addValue("status", status);
			}
			itemList = simpleJdbcTemplate.query(sql, getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getMrtStatusDTOByMsisdn()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getMrtStatusDTOByMsisdn():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<MrtStatusDTO> getRowMapper() {
		return new ParameterizedRowMapper<MrtStatusDTO>() {
			public MrtStatusDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MrtStatusDTO dto = new MrtStatusDTO();
				dto.setStaffId(rs.getString("STAFF_ID"));
				dto.setOrderId(rs.getString("ORDER_ID"));
				dto.setSrvNumType(rs.getString("SRV_NUM_TYPE"));
				dto.setMsisdn(rs.getString("MSISDN"));
				dto.setMsisdnLvl(rs.getString("MSISDNLVL"));
				dto.setStatus(rs.getInt("STATUS"));
				dto.setCityCd(rs.getString("CITY_CD"));
				dto.setApplyDate(rs.getTimestamp("APPLY_DATE"));
				dto.setCreatedBy(rs.getString("CREATED_BY"));
				dto.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
		};
	}
	
	public int deleteMrtStatusDTOByMsisdnAndStatus(String msisdn, int status) throws DAOException {
		final String sql = "delete from BOMWEB_MRT_STATUS where msisdn = :msisdn and status = :status";
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("sql: " + sql);
				logger.debug("msisdn: " + msisdn + ", status: " + status);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("msisdn", msisdn);
			params.addValue("status", status);
			return this.simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("Exception caught in deleteMrtStatusDTOByMsisdnAndStatus():", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
	}
}
