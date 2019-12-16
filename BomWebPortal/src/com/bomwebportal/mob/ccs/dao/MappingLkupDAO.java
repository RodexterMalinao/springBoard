package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MappingLkupDTO;

public class MappingLkupDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());
    
    private static String getMappingLkupDTOALLSQL = "SELECT" +
    		" MAP_TYPE" +
    		", MAP_FROM" +
    		", MAP_TO" +
    		", MAP_DESC" +
    		", CREATE_BY" +
    		", CREATE_DATE" +
    		", LAST_UPD_BY" +
    		", LAST_UPD_DATE" +
    		" FROM w_mapping_lkup" +
    		" WHERE MAP_TYPE = :mapType" +
    		" ORDER BY MAP_FROM";
    
	public List<MappingLkupDTO> getMappingLkupDTOALL(String mapType) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getMappingLkupDTOALL @ MappingLkupDAO is called");
		}
		List<MappingLkupDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getMappingLkupDTOALL() @ MappingLkupDAO: " + getMappingLkupDTOALLSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("mapType", mapType);
			itemList = simpleJdbcTemplate.query(getMappingLkupDTOALLSQL, getRowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			if (logger.isInfoEnabled()) {
				logger.info("BadSqlGrammarException in getMappingLkupDTOALL()");
			}
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getMappingLkupDTOALL()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getMappingLkupDTOALL(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
    private static String getMappingLkupDTOSQL = "SELECT" +
    		" MAP_TYPE" +
    		", MAP_FROM" +
    		", MAP_TO" +
    		", MAP_DESC" +
    		", CREATE_BY" +
    		", CREATE_DATE" +
    		", LAST_UPD_BY" +
    		", LAST_UPD_DATE" +
    		" FROM w_mapping_lkup" +
    		" WHERE MAP_TYPE = :mapType" +
    		" AND MAP_FROM = :mapFrom";
    
	public MappingLkupDTO getMappingLkupDTO(String mapType, String mapFrom) throws DAOException {
		if (logger.isDebugEnabled()) {
			logger.debug("getMappingLkupDTO @ MappingLkupDAO is called");
		}
		List<MappingLkupDTO> itemList = Collections.emptyList();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("getMappingLkupDTO() @ MappingLkupDAO: " + getMappingLkupDTOSQL);
			}
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("mapType", mapType);
			params.addValue("mapFrom", mapFrom);
			itemList = simpleJdbcTemplate.query(getMappingLkupDTOSQL, getRowMapper(), params);
		} catch (BadSqlGrammarException bsge) {
			if (logger.isInfoEnabled()) {
				logger.info("BadSqlGrammarException in getMappingLkupDTO()");
			}
			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			if (logger.isInfoEnabled()) {
				logger.info("EmptyResultDataAccessException in getMappingLkupDTO()");
			}
			itemList = null;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info("Exception caught in getMappingLkupDTO(): ", e);
			}
			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}
	
	private ParameterizedRowMapper<MappingLkupDTO> getRowMapper() {
		return new ParameterizedRowMapper<MappingLkupDTO>() {
			public MappingLkupDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				MappingLkupDTO dto = new MappingLkupDTO();
				dto.setMapType(rs.getString("MAP_TYPE"));
				dto.setMapFrom(rs.getString("MAP_FROM"));
				dto.setMapTo(rs.getString("MAP_TO"));
				dto.setMapDesc(rs.getString("MAP_DESC"));
				dto.setCreateBy(rs.getString("CREATE_BY"));
				dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				dto.setLastUpdBy(rs.getString("LAST_UPD_BY"));
				dto.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				return dto;
			}
		};
	}
}
