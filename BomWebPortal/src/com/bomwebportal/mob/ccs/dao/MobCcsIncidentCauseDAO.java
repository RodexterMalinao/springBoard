package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsIncidentCauseDTO;

public class MobCcsIncidentCauseDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	private static String getMobCcsIncidentCauseDTOSQL = "SELECT" +
			" INCIDENT_NO" +
			" , CAUSE_CODE" +
			" , bic.rowid ROW_ID" +
			" , CODE_DESC CAUSE_CODE_DESC" +
			" FROM BOMWEB_INCIDENT_CAUSE bic" +
			" JOIN BOMWEB_CODE_LKUP bcl ON (bic.CAUSE_CODE = bcl.CODE_ID AND bcl.CODE_TYPE = 'DEL_FAIL')" +
			" WHERE INCIDENT_NO = :incidentNo";
	public List<MobCcsIncidentCauseDTO> getMobCcsIncidentCauseDTO(String incidentNo) throws DAOException {
		logger.info("getMobCcsIncidentCauseDTO() is called");
		
		List<MobCcsIncidentCauseDTO> itemList = null;
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			params.addValue("incidentNo", incidentNo);
			if (logger.isInfoEnabled()) {
				logger.info("getMobCcsIncidentCauseDTO() @ MobCcsOrderRescueDAO: " + getMobCcsIncidentCauseDTOSQL);
			}
			itemList = this.simpleJdbcTemplate.query(getMobCcsIncidentCauseDTOSQL, this.getRowMapper(), params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getMobCcsIncidentCauseDTO()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getMobCcsIncidentCauseDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<MobCcsIncidentCauseDTO> getRowMapper() {
		return new ParameterizedRowMapper<MobCcsIncidentCauseDTO>() {

			public MobCcsIncidentCauseDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				MobCcsIncidentCauseDTO dto = new MobCcsIncidentCauseDTO();
				dto.setIncidentNo(rs.getString("INCIDENT_NO"));
				dto.setCauseCode(rs.getString("CAUSE_CODE"));
				dto.setCauseCodeDesc(rs.getString("CAUSE_CODE_DESC"));
				dto.setRowId(rs.getString("ROW_ID"));
				return dto;
			}
			
		};
	}

	private static String insertMobCcsIncidentCauseDTOSQL = "INSERT INTO BOMWEB_INCIDENT_CAUSE" +
			" (" +
			" INCIDENT_NO" +
			" , CAUSE_CODE" +
			" )" +
			" VALUES" +
			" (" +
			" :incidentNo" +
			" , :causeCode" +
			" )";
	public void insertMobCcsIncidentCauseDTO(MobCcsIncidentCauseDTO dto) throws DAOException {
		logger.info("insertMobCcsIncidentCauseDTO() is called");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("incidentNo", dto.getIncidentNo());
			params.addValue("causeCode", dto.getCauseCode());
			if (logger.isInfoEnabled()) {
				logger.info("insertMobCcsIncidentCauseDTO() @ MobCcsOrderRescueDAO: " + insertMobCcsIncidentCauseDTOSQL);
			}
			this.simpleJdbcTemplate.update(insertMobCcsIncidentCauseDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in insertMobCcsIncidentCauseDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	private static String deleteMobCcsIncidentCauseDTOSQL = "DELETE FROM BOMWEB_INCIDENT_CAUSE" +
			" WHERE INCIDENT_NO = :incidentNo";
	public void deleteMobCcsIncidentCauseDTO(String incidentNo) throws DAOException {
		logger.info("deleteMobCcsIncidentCauseDTO() is called");
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("incidentNo", incidentNo);
			if (logger.isInfoEnabled()) {
				logger.info("deleteMobCcsIncidentCauseDTO() @ MobCcsOrderRescueDAO: " + deleteMobCcsIncidentCauseDTOSQL);
			}
			this.simpleJdbcTemplate.update(deleteMobCcsIncidentCauseDTOSQL, params);
		} catch (Exception e) {
			logger.info("Exception caught in deleteMobCcsIncidentCauseDTO():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
