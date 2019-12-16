package com.bomltsportal.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ClearCacheDAO extends BaseDAO {

	private static final String SQL_GET_REFRESH_CACHE_PW = 
		"select description " +
		"from w_code_lkup " +
		"where grp_id = 'CLEAR_CACHE_PW' and code = 'PW'";
	
	
	public String getRefreshCachePassword() throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_REFRESH_CACHE_PW, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
}
