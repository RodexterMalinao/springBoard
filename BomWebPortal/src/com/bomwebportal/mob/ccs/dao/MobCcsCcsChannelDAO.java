package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CcsChannelDTO;

public class MobCcsCcsChannelDAO extends BaseDAO {
	public List<CcsChannelDTO> getCcsChannelDTOALL() throws DAOException {
		logger.info("getCcsChannelDTOALL @ MobCcsCcsChannelDAO is called");
		List<CcsChannelDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" CHANNEL_CD" +
				", CENTRE_CD" +
				", TEAM_CD" +
				" FROM bomweb_ccs_channel" +
				" ORDER BY CHANNEL_CD, CENTRE_CD, TEAM_CD";
		try {
		    logger.info("getCcsChannelDTOALL() @ CcsChannelDTO: " + sql);
		    itemList = simpleJdbcTemplate.query(sql, this.getRowMapper());
		} catch (EmptyResultDataAccessException erdae) {
		    logger.info("EmptyResultDataAccessException in getCcsChannelDTOALL()");
		    itemList = null;
		} catch (Exception e) {
		    logger.info("Exception caught in getCcsChannelDTOALL():", e);
		    throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<String> getTeamCdALL() throws DAOException {
		logger.info("getTeamCdALL @ MobCcsCcsChannelDAO is called");
		List<String> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT(TEAM_CD) TEAM_CD" +
				" FROM bomweb_ccs_channel" +
				" WHERE TEAM_CD IS NOT NULL";
		try {
			logger.info("getTeamCdALL() @ MobCcsCcsChannelDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("TEAM_CD");
				}
			});
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getTeamCdALL()");
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getTeamCdALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getTeamCdALL(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<String> getCentreCdALL() throws DAOException {
		logger.info("getCentreCdALL @ MobCcsCcsChannelDAO is called");
		List<String> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT(CENTRE_CD) CENTRE_CD" +
				" FROM bomweb_ccs_channel" +
				" WHERE CENTRE_CD IS NOT NULL";
		try {
			logger.info("getCentreCdALL() @ MobCcsCcsChannelDAO: "
					+ sql);

			itemList = simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("CENTRE_CD");
				}
			});
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getCentreCdALL()");
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getCentreCdALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getCentreCdALL(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<String> getChannelCdALL() throws DAOException {
		logger.info("getChannelCdALL @ MobCcsCcsChannelDAO is called");
		List<String> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" DISTINCT(CHANNEL_CD) CHANNEL_CD" +
				" FROM bomweb_ccs_channel" +
				" WHERE CHANNEL_CD IS NOT NULL";
		try {
			logger.info("getChannelCdALL() @ MobCcsCcsChannelDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("CHANNEL_CD");
				}
			});
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getChannelCdALL()");
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getChannelCdALL()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getChannelCdALL(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	

	private ParameterizedRowMapper<CcsChannelDTO> getRowMapper() {
		return new ParameterizedRowMapper<CcsChannelDTO>() {
			public CcsChannelDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				CcsChannelDTO dto = new CcsChannelDTO();
				dto.setChannelCd(rs.getString("CHANNEL_CD"));
				dto.setCentreCd(rs.getString("CENTRE_CD"));
				dto.setTeamCd(rs.getString("TEAM_CD"));
				return dto;
			}
		};
	}
}
