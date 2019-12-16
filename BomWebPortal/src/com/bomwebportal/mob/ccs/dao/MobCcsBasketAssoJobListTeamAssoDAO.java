package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;

public class MobCcsBasketAssoJobListTeamAssoDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());

	public List<String> getExistJobListALL() throws DAOException {
		logger.info("getExistJobListALL @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<String> itemList = new ArrayList<String>();
		String sql = "SELECT" +
				" DISTINCT(JOB_LIST) JOB_LIST" +
				" FROM bomweb_joblist_team_assgn" +
				" WHERE JOB_LIST IS NOT NULL";
		try {
			logger.info("getExistJobListALL() @ MobCcsBasketAssoJobListTeamAssoDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("JOB_LIST");
				}
			});
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getExistJobListALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getExistJobListALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getExistJobListALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOBySearch(BasketAssoJobListTeamAssoDTO dto, boolean showEnded) throws DAOException {
		logger.info("getBasketAssoJobListTeamAssoDTOBySearch @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<BasketAssoJobListTeamAssoDTO> itemList = new ArrayList<BasketAssoJobListTeamAssoDTO>();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , CHANNEL_CD" +
				" , CENTRE_CD" +
				" , TEAM_CD" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist_team_assgn" +
				" WHERE 1=1";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(sql);
			if (StringUtils.isNotBlank(dto.getJobList())) {
				searchSQL.append(" AND JOB_LIST = :jobList");
				params.addValue("jobList", dto.getJobList());
			}
			if (StringUtils.isNotBlank(dto.getTeamCd())) {
				searchSQL.append(" AND TEAM_CD = :teamCd");
				params.addValue("teamCd", dto.getTeamCd());
			}
			if (StringUtils.isNotBlank(dto.getCentreCd())) {
				searchSQL.append(" AND CENTRE_CD = :centreCd");
				params.addValue("centreCd", dto.getCentreCd());
			}
			if (StringUtils.isNotBlank(dto.getChannelCd())) {
				searchSQL.append(" AND CHANNEL_CD = :channelCd");
				params.addValue("channelCd", dto.getChannelCd());
			}
			if (dto.getStartDate() != null) {
				searchSQL.append(" AND START_DATE = trunc(:startDate)");
				params.addValue("startDate", dto.getStartDate(), Types.DATE);
			}
			if (dto.getEndDate() != null) {
				searchSQL.append(" AND END_DATE = trunc(:endDate)");
				params.addValue("endDate", dto.getEndDate(), Types.DATE);
			}
			if (!showEnded) {
				searchSQL.append(" AND (END_DATE IS NULL OR END_DATE >= trunc(sysdate))");
			}
			searchSQL.append(" ORDER BY JOB_LIST, END_DATE DESC NULLS FIRST, START_DATE DESC, TEAM_CD");
			logger.info("getBasketAssoJobListTeamAssoDTOBySearch() @ MobCcsBasketAssoJobListTeamAssoDAO: " + searchSQL);
			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getBasketAssoJobListTeamAssoDTORowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListTeamAssoDTOBySearch()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListTeamAssoDTOBySearch()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListTeamAssoDTOBySearch(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOByRange(String jobList, String channelCd
			, String centreCd, String teamCd
			, Date startDate, Date endDate) throws DAOException {
		logger.info("getBasketAssoJobListTeamAssoDTOByRange @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<BasketAssoJobListTeamAssoDTO> itemList = new ArrayList<BasketAssoJobListTeamAssoDTO>();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , CHANNEL_CD" +
				" , CENTRE_CD" +
				" , TEAM_CD" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist_team_assgn" +
				" WHERE 1=1";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(sql);
			if (StringUtils.isNotBlank(jobList)) {
				searchSQL.append(" AND JOB_LIST = :jobList");
				params.addValue("jobList", jobList);
			}
			if (StringUtils.isNotBlank(channelCd)) {
				searchSQL.append(" AND CHANNEL_CD = :channelCd");
				params.addValue("channelCd", channelCd);
			}
			if (!"ALL".equals(centreCd)) {
				if (StringUtils.isNotBlank(centreCd)) {
					searchSQL.append(" AND DECODE(CENTRE_CD, 'ALL', :centreCd, CENTRE_CD)  = :centreCd");
					params.addValue("centreCd", centreCd);
				}
			}
			if (!"ALL".equals(teamCd)) {
				if (StringUtils.isNotBlank(teamCd)) {
					searchSQL.append(" AND DECODE(TEAM_CD, 'ALL', :teamCd, TEAM_CD)  = :teamCd");
					params.addValue("teamCd", teamCd);
				}
			}
			if (startDate != null) {
				searchSQL.append(" AND (trunc(:startDate) >= START_DATE AND (trunc(:startDate) <= END_DATE OR END_DATE IS NULL))");
				params.addValue("startDate", startDate, Types.DATE);
			}
			if (endDate != null) {
				searchSQL.append(" AND (trunc(:endDate) >= START_DATE AND (trunc(:endDate) <= END_DATE OR END_DATE IS NULL))");
				params.addValue("endDate", endDate, Types.DATE);
			}
			searchSQL.append(" ORDER BY JOB_LIST, END_DATE DESC NULLS FIRST, START_DATE DESC, TEAM_CD");
			logger.info("getBasketAssoJobListTeamAssoDTOByRange() @ MobCcsBasketAssoJobListTeamAssoDAO: " + searchSQL);
			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getBasketAssoJobListTeamAssoDTORowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListTeamAssoDTOByRange()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListTeamAssoDTOByRange()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListTeamAssoDTOByRange(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public BasketAssoJobListTeamAssoDTO getBasketAssoJobListTeamAssoDTO(String rowId) throws DAOException {
		logger.info("getBasketAssoJobListTeamAssoDTO @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<BasketAssoJobListTeamAssoDTO> itemList = new ArrayList<BasketAssoJobListTeamAssoDTO>();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , CHANNEL_CD" +
				" , CENTRE_CD" +
				" , TEAM_CD" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist_team_assgn" +
				" WHERE rowid = ?";
		try {
			logger.info("getBasketAssoJobListTeamAssoDTO() @ MobCcsBasketAssoJobListTeamAssoDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListTeamAssoDTORowMapper(), rowId);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListTeamAssoDTO()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListTeamAssoDTO()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListTeamAssoDTO(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOByJobList(String jobList) throws DAOException {
		logger.info("getBasketAssoJobListTeamAssoDTOByJobList @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<BasketAssoJobListTeamAssoDTO> itemList = new ArrayList<BasketAssoJobListTeamAssoDTO>();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , CHANNEL_CD" +
				" , CENTRE_CD" +
				" , TEAM_CD" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist_team_assgn" +
				" WHERE JOB_LIST = ?" +
				" ORDER BY JOB_LIST, END_DATE DESC NULLS FIRST, START_DATE DESC, TEAM_CD";
		try {
			logger.info("getBasketAssoJobListTeamAssoDTOByJobList() @ MobCcsBasketAssoJobListTeamAssoDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListTeamAssoDTORowMapper(), jobList);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListTeamAssoDTOByJobList()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListTeamAssoDTOByJobList()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListTeamAssoDTOByJobList(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoInRunningDTOByJobList(String jobList) throws DAOException {
		logger.info("getBasketAssoJobListTeamAssoInRunningDTOByJobList @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<BasketAssoJobListTeamAssoDTO> itemList = new ArrayList<BasketAssoJobListTeamAssoDTO>();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , CHANNEL_CD" +
				" , CENTRE_CD" +
				" , TEAM_CD" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist_team_assgn" +
				" WHERE JOB_LIST = ?" +
				" AND (END_DATE IS NULL OR END_DATE >= trunc(sysdate))" +
				" ORDER BY JOB_LIST, END_DATE DESC NULLS FIRST, START_DATE DESC, TEAM_CD";
		try {
			logger.info("getBasketAssoJobListTeamAssoInRunningDTOByJobList() @ MobCcsBasketAssoJobListTeamAssoDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListTeamAssoDTORowMapper(), jobList);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListTeamAssoInRunningDTOByJobList()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListTeamAssoInRunningDTOByJobList()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListTeamAssoInRunningDTOByJobList(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoJobListTeamAssoDTO>() {
			public BasketAssoJobListTeamAssoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoJobListTeamAssoDTO obj = new BasketAssoJobListTeamAssoDTO();
				obj.setJobList(rs.getString("JOB_LIST"));
				obj.setChannelCd(rs.getString("CHANNEL_CD"));
				obj.setCentreCd(rs.getString("CENTRE_CD"));
				obj.setTeamCd(rs.getString("TEAM_CD"));
				obj.setStartDate(rs.getTimestamp("START_DATE"));
				obj.setEndDate(rs.getTimestamp("END_DATE"));
				obj.setCreateBy(rs.getString("CREATE_BY"));
				obj.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				obj.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				obj.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				obj.setRowId(rs.getString("ROW_ID"));
				return obj;
			}
		};
	}

	public void insertBasketAssoJobListTeamAssoDTO(BasketAssoJobListTeamAssoDTO dto) throws DAOException {
		logger.info("insertBasketAssoJobListTeamAssoDTO @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		String sql = "INSERT INTO" +
				" bomweb_joblist_team_assgn" +
				" (" +
				" JOB_LIST" +
				" , CHANNEL_CD" +
				" , CENTRE_CD" +
				" , TEAM_CD" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" ) VALUES (" +
				" :jobList" +
				" , :channelCd" +
				" , :centreCd" +
				" , :teamCd" +
				" , trunc(:startDate)" +
				" , trunc(:endDate)" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" )";
		try {
			logger.info("insertBasketAssoJobListTeamAssoDTO() @ MobCcsBasketAssoJobListTeamAssoDAO: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("jobList", dto.getJobList());
			params.addValue("channelCd", dto.getChannelCd());
			params.addValue("centreCd", dto.getCentreCd());
			params.addValue("teamCd", dto.getTeamCd());
			params.addValue("startDate", dto.getStartDate(), Types.DATE);
			params.addValue("endDate", dto.getEndDate(), Types.DATE);
			params.addValue("createBy", dto.getCreateBy());
			//params.addValue("createDate", dto.getCreateDate());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			//params.addValue("lastUpdDate", dto.getLastUpdDate());

			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBasketAssoJobListTeamAssoDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void updateBasketAssoJobListTeamAssoDTOForEndAsso(BasketAssoJobListTeamAssoDTO dto) throws DAOException {
		logger.info("updateBasketAssoJobListTeamAssoDTOForEndAsso @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		String sql = "UPDATE" +
				" bomweb_joblist_team_assgn" +
				" SET" +
				" END_DATE = trunc(:endDate)" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE rowid = :rowId";
		try {
			logger.info("updateBasketAssoJobListTeamAssoDTOForEndAsso() @ MobCcsBasketAssoJobListTeamAssoDAO: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("endDate", dto.getEndDate(), Types.DATE);
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			//params.addValue("lastUpdDate", dto.getLastUpdDate());
			params.addValue("rowId", dto.getRowId());

			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBasketAssoJobListTeamAssoDTOForEndAsso()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
