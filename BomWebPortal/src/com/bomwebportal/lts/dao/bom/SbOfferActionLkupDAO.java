package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class SbOfferActionLkupDAO extends BaseDAO{

	
	public List<String> getActionOfferCdList(List<String> conditionOfferCdList, String action) throws DAOException{
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String actionOfferCd = rs.getString("offer_cd");
				return actionOfferCd;
			}
		};
		
		String sql =  "SELECT soal.action_offer_cd offer_cd" +
						" FROM  sb_offer_action_lkup soal " + 
						" WHERE soal.condition_offer_cd IN (:conditionOfferCds) " +
						" AND   soal.action = :action " +
					 " UNION " +
					 " SELECT soal.condition_offer_cd offer_cd " +
						" FROM  sb_offer_action_lkup soal " + 
						" WHERE soal.action_offer_cd IN (:conditionOfferCds) " +
						" AND   soal.action = :action ";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("conditionOfferCds", conditionOfferCdList);
		paramSource.addValue("action", action);
		
		try{
			return simpleJdbcTemplate.query(sql, mapper, paramSource);
		} catch (Exception e) {
			logger.error("Exception caught in getActionOfferCdList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
}
