package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;

public class MobCcsBasketAssoJobListDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
   
	public List<String> getExistJobListALL() throws DAOException {

		logger.info("getExistJobListALL @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<String> itemList = new ArrayList<String>();
		String sql = "SELECT" +
				" DISTINCT(JOB_LIST) JOB_LIST" +
				" FROM bomweb_joblist" +
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

	public List<String> getExistChannelCdALL() throws DAOException {

		logger.info("getExistChannelCdALL @ MobCcsBasketAssoJobListTeamAssoDAO is called");
		List<String> itemList = new ArrayList<String>();
		String sql = "SELECT" +
				" DISTINCT(CHANNEL_CD) CHANNEL_CD" +
				" FROM bomweb_joblist" +
				" WHERE CHANNEL_CD IS NOT NULL";
		try {
			logger.info("getExistChannelCdALL() @ MobCcsBasketAssoJobListTeamAssoDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("CHANNEL_CD");
				}
			});
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getExistChannelCdALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getExistChannelCdALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getExistChannelCdALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public BasketAssoJobListDTO getBasketAssoJobListDTO(String rowId) throws DAOException {

		logger.info("getBasketAssoJobListDTOSQL @ MobCcsBasketAssoDAO is called");
		List<BasketAssoJobListDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , JOB_LIST_DESC" +
				" , CHANNEL_CD" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist" +
				" WHERE rowid = ?";
		try {
			logger.info("getBasketAssoJobListDTOSQL() @ MobCcsBasketAssoDAO: " + sql);
			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListDTORowMapper(), rowId);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListDTOSQL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListDTOSQL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListDTOSQL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public BasketAssoJobListDTO getBasketAssoJobListDTOByJobList(String jobList) throws DAOException {

		logger.info("getBasketAssoJobListDTOByJobListSQL @ MobCcsBasketAssoDAO is called");
		List<BasketAssoJobListDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , JOB_LIST_DESC" +
				" , CHANNEL_CD" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist" +
				" WHERE JOB_LIST = ?";
		try {
			logger.info("getBasketAssoJobListDTOByJobListSQL() @ MobCcsBasketAssoDAO: "
					+ sql);
			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListDTORowMapper(), jobList);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListDTOByJobListSQL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListDTOByJobListSQL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListDTOByJobListSQL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return this.isEmpty(itemList) ? null : itemList.get(0);
	}

	public List<BasketAssoJobListDTO> getBasketAssoJobListDTOByJobListAndChannelCd(String jobList, String channelCd) throws DAOException {
		logger.info("getBasketAssoJobListDTOByJobListAndChannelCd @ MobCcsBasketAssoDAO is called");
		List<BasketAssoJobListDTO> itemList = Collections.emptyList();
		String sql = "SELECT" +
				" JOB_LIST" +
				" , JOB_LIST_DESC" +
				" , CHANNEL_CD" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" , rowid ROW_ID" +
				" FROM bomweb_joblist" +
				" WHERE 1=1";
		try {
			StringBuilder searchSQL = new StringBuilder(sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			if (StringUtils.isNotBlank(jobList)) {
				searchSQL.append(" AND JOB_LIST = :jobList");
				params.addValue("jobList", jobList);
			}
			if (StringUtils.isNotBlank(channelCd)) {
				searchSQL.append(" AND CHANNEL_CD = :channelCd");
				params.addValue("channelCd", channelCd);
			}
			searchSQL.append(" ORDER BY JOB_LIST, CHANNEL_CD");
			logger.info("getBasketAssoJobListDTOByJobListAndChannelCd() @ MobCcsBasketAssoDAO: " + searchSQL);
			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getBasketAssoJobListDTORowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListDTOByJobListAndChannelCd()");
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListDTOByJobListAndChannelCd()");
			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListDTOByJobListAndChannelCd(): ", e);
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<BasketAssoJobListDTO> getBasketAssoJobListDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoJobListDTO>() {
			public BasketAssoJobListDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoJobListDTO obj = new BasketAssoJobListDTO();
				obj.setJobList(rs.getString("JOB_LIST"));
				obj.setJobListDesc(rs.getString("JOB_LIST_DESC"));
				obj.setChannelCd(rs.getString("CHANNEL_CD"));
				obj.setCreateBy(rs.getString("CREATE_BY"));
				obj.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				obj.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				obj.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				obj.setRowId(rs.getString("ROW_ID"));
				return obj;
			}
		};
	}

	public void insertBasketAssoJobListDTO(BasketAssoJobListDTO dto) throws DAOException {
		logger.info("insertBasketAssoJobListDTO @ MobCcsBasketAssoDAO is called");
		String sql = "INSERT INTO" +
				" bomweb_joblist" +
				" (" +
				" JOB_LIST" +
				" , JOB_LIST_DESC" +
				" , CHANNEL_CD" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" ) VALUES (" +
				" :jobList" +
				" , :jobListDesc" +
				" , :channelCd" +
				" , :createBy" +
				" , sysdate" +
				" , :lastUpdBy" +
				" , sysdate" +
				" )";
		try {
			logger.info("insertBasketAssoJobListDTO() @ MobCcsBasketAssoDAO: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("jobList", dto.getJobList());
			params.addValue("jobListDesc", dto.getJobListDesc());
			params.addValue("channelCd", dto.getChannelCd());
			params.addValue("createBy", dto.getCreateBy());
			//params.addValue("createDate", dto.getCreateDate());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			//params.addValue("lastUpdDate", dto.getLastUpdDate());
			
			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBasketAssoJobListDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void deleteBasketAssoJobListDTO(String rowId) throws DAOException {
		logger.info("deleteBasketAssoJobListDTO @ MobCcsBasketAssoDAO is called");
		String sql = "DELETE FROM " +
				" bomweb_joblist" +
				" WHERE rowid = ?";
		try {
			logger.info("deleteBasketAssoJobListDTO() @ MobCcsBasketAssoDAO: "
					+ sql);
			
			simpleJdbcTemplate.update(sql, rowId);
		} catch (Exception e) {
			logger.error("Exception caught in deleteBasketAssoJobListDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public int updateBasketAssoJobListDTODesc(BasketAssoJobListDTO dto) throws DAOException {
		logger.info("updateBasketAssoJobListDTODesc @ MobCcsBasketAssoDAO is called");
		String sql = "UPDATE bomweb_joblist SET " +
				" JOB_LIST_DESC = :jobListDesc" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = sysdate" +
				" WHERE rowid = :rowId";
		try {
			logger.info("updateBasketAssoJobListDTODesc() @ MobCcsBasketAssoDAO: " + sql);
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("jobListDesc", dto.getJobListDesc());
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			//params.addValue("lastUpdDate", dto.getLastUpdDate());
			params.addValue("rowId", dto.getRowId());
			return simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBasketAssoJobListDTODesc()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
