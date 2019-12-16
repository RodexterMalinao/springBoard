package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
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
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListBskAssoDTO;

public class MobCcsBasketAssoJobListBskAssoDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());

	public List<String> getExistJobListALL() throws DAOException {
		logger.info("getExistJobListALL @ MobCcsBasketAssoJobListBskAssoDAO is called");
		List<String> itemList = new ArrayList<String>();
		String sql = "SELECT" +
				" DISTINCT(JOB_LIST) JOB_LIST" +
				" FROM bomweb_joblist_basket_assgn" +
				" WHERE JOB_LIST IS NOT NULL";
		try {
			logger.info("getExistJobListALL() @ MobCcsBasketAssoJobListBskAssoDAO: " + sql);

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
	
	public BasketAssoJobListBskAssoDTO getBasketAssoJobListBskAssoDTO(String rowId) throws DAOException {

		logger.info("getBasketAssoJobListBskAssoDTO @ MobCcsBasketAssoJobListBskAssoDAO is called");
		List<BasketAssoJobListBskAssoDTO> itemList = new ArrayList<BasketAssoJobListBskAssoDTO>();
		String sql = "SELECT" +
				" bjba.JOB_LIST" +
				" , bjba.CAMPAIGN_ID" +
				" , bjba.BASKET_ID" +
				" , wbam.DESCRIPTION BASKET_DESC" +
				" , bjba.START_DATE" +
				" , bjba.END_DATE" +
				" , bjba.CREATE_BY" +
				" , bjba.CREATE_DATE" +
				" , bjba.LAST_UPD_BY" +
				" , bjba.LAST_UPD_DATE" +
				" , bjba.rowid ROW_ID" +
				" FROM bomweb_joblist_basket_assgn bjba" +
				" LEFT JOIN w_basket_attribute_mv wbam ON (bjba.BASKET_ID = wbam.BASKET_ID)" +
				" WHERE bjba.rowid = ?" +
				" AND NVL(wbam.NATURE, 'ACQ') = 'ACQ' ";
		try {
			logger.info("getBasketAssoJobListBskAssoDTO() @ MobCcsBasketAssoJobListBskAssoDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListBskAssoDTORowMapper(), rowId);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListBskAssoDTO()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListBskAssoDTO()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListBskAssoDTO(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoDTOByJobList(String jobList) throws DAOException {

		logger.info("getBasketAssoJobListBskAssoDTOByJobList @ MobCcsBasketAssoJobListBskAssoDAO is called");
		List<BasketAssoJobListBskAssoDTO> itemList = new ArrayList<BasketAssoJobListBskAssoDTO>();
		String sql = "SELECT" +
				" bjba.JOB_LIST" +
				" , bjba.CAMPAIGN_ID" +
				" , bjba.BASKET_ID" +
				" , wbam.DESCRIPTION BASKET_DESC" +
				" , bjba.START_DATE" +
				" , bjba.END_DATE" +
				" , bjba.CREATE_BY" +
				" , bjba.CREATE_DATE" +
				" , bjba.LAST_UPD_BY" +
				" , bjba.LAST_UPD_DATE" +
				" , bjba.rowid ROW_ID" +
				" FROM bomweb_joblist_basket_assgn bjba" +
				" LEFT JOIN w_basket_attribute_mv wbam ON (bjba.BASKET_ID = wbam.BASKET_ID)" +
				" WHERE bjba.JOB_LIST = ?" +
				" AND NVL(wbam.NATURE, 'ACQ') = 'ACQ' " +
				" ORDER BY bjba.JOB_LIST, bjba.END_DATE DESC NULLS FIRST, bjba.START_DATE DESC, bjba.BASKET_ID";
		try {
			logger.info("getBasketAssoJobListBskAssoDTOByJobList() @ MobCcsBasketAssoJobListBskAssoDAO: " + sql);

			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListBskAssoDTORowMapper(), jobList);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListBskAssoDTOByJobList()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListBskAssoDTOByJobList()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListBskAssoDTOByJobList(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoInRunningDTOByJobList(String jobList) throws DAOException {
		logger.info("getBasketAssoJobListBskAssoInRunningDTOByJobList @ MobCcsBasketAssoJobListBskAssoDAO is called");
		List<BasketAssoJobListBskAssoDTO> itemList = new ArrayList<BasketAssoJobListBskAssoDTO>();
		String sql = "SELECT" +
				" bjba.JOB_LIST" +
				" , bjba.CAMPAIGN_ID" +
				" , bjba.BASKET_ID" +
				" , wbam.DESCRIPTION BASKET_DESC" +
				" , bjba.START_DATE" +
				" , bjba.END_DATE" +
				" , bjba.CREATE_BY" +
				" , bjba.CREATE_DATE" +
				" , bjba.LAST_UPD_BY" +
				" , bjba.LAST_UPD_DATE" +
				" , bjba.rowid ROW_ID" +
				" FROM bomweb_joblist_basket_assgn bjba" +
				" LEFT JOIN w_basket_attribute_mv wbam ON (bjba.BASKET_ID = wbam.BASKET_ID)" +
				" WHERE bjba.JOB_LIST = ?" +
				" AND NVL(wbam.NATURE, 'ACQ') = 'ACQ' " +
				" AND (bjba.END_DATE IS NULL OR bjba.END_DATE >= trunc(sysdate))" +
				" ORDER BY bjba.JOB_LIST, bjba.END_DATE DESC NULLS FIRST, bjba.START_DATE DESC, bjba.BASKET_ID";
		try {
			logger.info("getBasketAssoJobListBskAssoInRunningDTOByJobList() @ MobCcsBasketAssoJobListBskAssoDAO: " + sql);
			itemList = simpleJdbcTemplate.query(sql, this.getBasketAssoJobListBskAssoDTORowMapper(), jobList);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListBskAssoInRunningDTOByJobList()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListBskAssoInRunningDTOByJobList()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListBskAssoInRunningDTOByJobList(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	public List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoDTOBySearch(BasketAssoJobListBskAssoDTO dto) throws DAOException {
		logger.info("getBasketAssoJobListBskAssoDTOBySearch @ MobCcsBasketAssoJobListBskAssoDAO is called");
		List<BasketAssoJobListBskAssoDTO> itemList = new ArrayList<BasketAssoJobListBskAssoDTO>();
		String sql = "SELECT" +
				" bjba.JOB_LIST" +
				" , bjba.CAMPAIGN_ID" +
				" , bjba.BASKET_ID" +
				" , wbam.DESCRIPTION BASKET_DESC" +
				" , bjba.START_DATE" +
				" , bjba.END_DATE" +
				" , bjba.CREATE_BY" +
				" , bjba.CREATE_DATE" +
				" , bjba.LAST_UPD_BY" +
				" , bjba.LAST_UPD_DATE" +
				" , bjba.rowid ROW_ID" +
				" FROM bomweb_joblist_basket_assgn bjba" +
				" LEFT JOIN w_basket_attribute_mv wbam ON (bjba.BASKET_ID = wbam.BASKET_ID)" +
				" WHERE 1=1" +
				" AND NVL(wbam.NATURE, 'ACQ') = 'ACQ' ";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			StringBuilder searchSQL = new StringBuilder(sql);
			if (StringUtils.isNotEmpty(dto.getJobList())) {
				searchSQL.append(" AND bjba.JOB_LIST = :jobList");
				params.addValue("jobList", dto.getJobList());
			}
			if (StringUtils.isNotBlank(dto.getBasketId())) {
				searchSQL.append(" AND bjba.BASKET_ID = :basketId");
				params.addValue("basketId", dto.getBasketId());
			}
			if (dto.getStartDate() != null) {
				searchSQL.append(" AND bjba.START_DATE = trunc(:startDate)");
				params.addValue("startDate", dto.getStartDate(), Types.DATE);
			}
			searchSQL.append(" ORDER BY bjba.JOB_LIST, bjba.END_DATE DESC NULLS FIRST, bjba.START_DATE DESC, bjba.BASKET_ID");
			logger.info("getBasketAssoJobListBskAssoDTOBySearch() @ MobCcsBasketAssoJobListBskAssoDAO: " + searchSQL);
			itemList = simpleJdbcTemplate.query(searchSQL.toString(), this.getBasketAssoJobListBskAssoDTORowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getBasketAssoJobListBskAssoDTOBySearch()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketAssoJobListBskAssoDTOBySearch()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBasketAssoJobListBskAssoDTOBySearch(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private ParameterizedRowMapper<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoDTORowMapper() {
		return new ParameterizedRowMapper<BasketAssoJobListBskAssoDTO>() {
			public BasketAssoJobListBskAssoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoJobListBskAssoDTO obj = new BasketAssoJobListBskAssoDTO();
				obj.setJobList(rs.getString("JOB_LIST"));
				obj.setCampaignId(rs.getString("CAMPAIGN_ID"));
				obj.setBasketId(rs.getString("BASKET_ID"));
				obj.setBasketDesc(rs.getString("BASKET_DESC"));
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

	public void insertBasketAssoJobListBskAssoDTO(BasketAssoJobListBskAssoDTO dto) throws DAOException {
		logger.info("insertBasketAssoJobListBskAssoDTO @ MobCcsBasketAssoJobListBskAssoDAO is called");
		String sql = "INSERT INTO" +
				" bomweb_joblist_basket_assgn" +
				" (" +
				" JOB_LIST" +
				" , CAMPAIGN_ID" +
				" , BASKET_ID" +
				" , BASKET_DESC" +
				" , START_DATE" +
				" , END_DATE" +
				" , CREATE_BY" +
				" , CREATE_DATE" +
				" , LAST_UPD_BY" +
				" , LAST_UPD_DATE" +
				" ) VALUES (" +
				" :jobList" +
				" , :campaignId" +
				" , :basketId" +
				" , (SELECT description FROM w_basket_attribute_mv wbam WHERE basket_id = :basketId AND NVL(wbam.NATURE, 'ACQ') = 'ACQ' )" +
				" , trunc(:startDate)" +
				" , trunc(:endDate)" +
				" , :createBy" +
				" , :createDate" +
				" , :lastUpdBy" +
				" , :lastUpdDate" +
				" )";
		try {
			logger.info("insertBasketAssoJobListBskAssoDTO() @ MobCcsBasketAssoJobListBskAssoDAO: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("jobList", dto.getJobList());
			params.addValue("campaignId", dto.getCampaignId());
			params.addValue("basketId", dto.getBasketId());
			params.addValue("startDate", dto.getStartDate(), Types.DATE);
			params.addValue("endDate", dto.getEndDate(), Types.DATE);
			params.addValue("createBy", dto.getCreateBy());
			params.addValue("createDate", dto.getCreateDate(), Types.DATE);
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("lastUpdDate", dto.getLastUpdDate(), Types.DATE);

			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertBasketAssoJobListBskAssoDTO()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void updateBasketAssoJobListBskAssoDTOForEndAsso(BasketAssoJobListBskAssoDTO dto) throws DAOException {
		logger.info("updateBasketAssoJobListBskAssoDTOForEndAsso @ MobCcsBasketAssoJobListBskAssoDAO is called");
		String sql = "UPDATE" +
				" bomweb_joblist_basket_assgn" +
				" SET" +
				" END_DATE = trunc(:endDate)" +
				" , LAST_UPD_BY = :lastUpdBy" +
				" , LAST_UPD_DATE = :lastUpdDate" +
				" WHERE rowid = :rowId";
		try {
			logger.info("updateBasketAssoJobListBskAssoDTOForEndAsso() @ MobCcsBasketAssoJobListBskAssoDAO: " + sql);
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("endDate", dto.getEndDate(), Types.DATE);
			params.addValue("lastUpdBy", dto.getLastUpdBy());
			params.addValue("lastUpdDate", dto.getLastUpdDate());
			params.addValue("rowId", dto.getRowId());

			simpleJdbcTemplate.update(sql, params);
		} catch (Exception e) {
			logger.error("Exception caught in updateBasketAssoJobListBskAssoDTOForEndAsso()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
