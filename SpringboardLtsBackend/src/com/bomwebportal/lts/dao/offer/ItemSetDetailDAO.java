package com.bomwebportal.lts.dao.offer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ItemSetDetailDAO extends BaseDAO {
	
	public List<String> getItemSetEligiblePsefList() throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	
					throws SQLException {
				return rs.getString("eligible_subc_psef");
			}
		};
		
		final String SQL_GET_ITEM_SET_ELIGIBLE_PSEF = 
			"select distinct eligible_subc_psef from w_item_set " +
				" where eligible_subc_psef is not null ";
		
		try {
			return simpleJdbcTemplate.query(SQL_GET_ITEM_SET_ELIGIBLE_PSEF, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getEligiblePsefList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
}
