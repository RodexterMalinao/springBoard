package com.bomwebportal.lts.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class AddressRolloutDAO extends BaseDAO {

	private static final String SQL_GET_TEAM_BY_PREMIER_ADDR =
		"SELECT TEAM_CD " +
		" FROM W_TEAM_HOUSING_TYPE_ASSGN " +
		" WHERE UPPER(HOUSING_TYPE) = UPPER(:housingType) " +
		" AND NVL(:activationFee, 0) BETWEEN LOWER_ACTIVATION_FEE AND UPPER_ACTIVATION_FEE ";
		
	
	@SuppressWarnings("unchecked")
	public List<String> getTeamByPremierAddr(String premierAddr, String activationFee) throws DAOException {
		
		try {
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("housingType", premierAddr);
			paramSource.addValue("activationFee", activationFee);
			
			return simpleJdbcTemplate.getNamedParameterJdbcOperations().queryForList(SQL_GET_TEAM_BY_PREMIER_ADDR, paramSource, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBasketList()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getEyeDeviceList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	
}
