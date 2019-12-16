package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.CodeLkupDAOImpl;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;

public class WqNatureLkupDAO extends CodeLkupDAOImpl {
	
	final String sql = "SELECT wq_nature_id AS CODE, wq_nature_desc AS DESCRIPTION FROM Q_WQ_NATURE ";
	
	
	public LookupItemDTO[] getCodeLkup() throws DAOException {
		ParameterizedRowMapper<LookupItemDTO> mapper = new ParameterizedRowMapper<LookupItemDTO>() {
			public LookupItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            	LookupItemDTO lookupItemDTO = new LookupItemDTO();
            	lookupItemDTO.setItemKey(rs.getString("CODE"));
            	lookupItemDTO.setItemValue(rs.getString("DESCRIPTION"));
                return lookupItemDTO;
			}
		};
		
		try {
			return (LookupItemDTO [])simpleJdbcTemplate.query(sql, mapper).toArray(new LookupItemDTO[0]);
		} catch (Exception e) {
			logger.error("Error in getCodeLkup()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getNatureType(String wqNatureId) throws DAOException{
		final String getNatureTypeSql = "SELECT WQ_NATURE_TYPE FROM Q_WQ_NATURE WHERE WQ_NATURE_ID = ?";
		
		try {
			return simpleJdbcTemplate.queryForObject(getNatureTypeSql, String.class, wqNatureId);
		} catch (Exception e) {
			logger.error("Error in getNatureType()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
