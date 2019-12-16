package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.LtsTeamFunctionDTO;

public class LtsTeamFunctionDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String SQL_GET_TEAM_FUNCTION = 
		"select * from w_team_function_lkup " +
		" where channel_cd in ('*', ?) " +
		" and team_cd in ('*', ?)" +
		" order by channel_cd desc, team_cd desc ";
	
	public LtsTeamFunctionDTO getTeamFunction(String channelCd, String teamCd) throws DAOException {
		try {
			List<LtsTeamFunctionDTO> teamFunctionList = this.simpleJdbcTemplate.query(SQL_GET_TEAM_FUNCTION, getRowMapper(), channelCd, teamCd);
			if (teamFunctionList != null) {
				return teamFunctionList.get(0);
			}
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new DAOException(e.getMessage());
		}
		
		return null;
	}
	
	private ParameterizedRowMapper<LtsTeamFunctionDTO> getRowMapper() {
		return new ParameterizedRowMapper<LtsTeamFunctionDTO>() {
			public LtsTeamFunctionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				LtsTeamFunctionDTO dto = new LtsTeamFunctionDTO();
				dto.setEnquiryOnly(rs.getString("ENQUIRY_ONLY"));
				dto.setDisplayStaffPlan(rs.getString("DISPLAY_STAFF_PLAN"));
				dto.setForceDocId(rs.getString("FORCE_DOC_ID"));
				dto.setRenewalOrder(rs.getString("RENEWAL_ORDER"));
				dto.setUpgradeOrder(rs.getString("UPGRADE_ORDER"));
				dto.setStandaloneVasOrder(rs.getString("STANDALONE_VAS_ORDER"));
				return dto;
			}
			
		};
	}
	
	
}
