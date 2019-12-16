//By Jason
package com.bomltsportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class MessageDAO extends BaseDAO {
//Get promotion message
	public String getPromotionMsg(String pHousingType, String pOrderType, String pServiceType, String pLang) throws DAOException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("select promo_msg PROMO_MSG ");
		sql.append("from bomweb_sbo_enq_msg_lkup ");
		sql.append("where housing_type = :housingType ");
		sql.append("and order_type = :orderType ");
		sql.append("and service_type = :serviceType ");
		sql.append("and lang = :language ");

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("housingType", pHousingType);
			params.addValue("orderType", pOrderType);
			params.addValue("serviceType", pServiceType);
			params.addValue("language", pLang);

			List<String> resultMsgList = simpleJdbcTemplate.query(sql.toString(), new SbMapper(), params);
			if(resultMsgList != null && resultMsgList.size() >0){
				return resultMsgList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in getPromotionMsg():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private class SbMapper implements ParameterizedRowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("PROMO_MSG");
		}
	};//Get promotion message end

//Get contact message
public String getContactMsg(String pHousingType, String pOrderType, String pServiceType, String pLang) throws DAOException{
		
		StringBuilder sql = new StringBuilder();
		sql.append("select contact_msg CONTACT_MSG ");
		sql.append("from bomweb_sbo_enq_msg_lkup ");
		sql.append("where housing_type = :housingType ");
		sql.append("and order_type = :orderType ");
		sql.append("and service_type = :serviceType ");
		sql.append("and lang = :language ");

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("housingType", pHousingType);
			params.addValue("orderType", pOrderType);
			params.addValue("serviceType", pServiceType);
			params.addValue("language", pLang);

			List<String> resultMsgList = simpleJdbcTemplate.query(sql.toString(), new ContactMapper(), params);
			if(resultMsgList != null && resultMsgList.size() >0){
				return resultMsgList.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("Exception caught in getContactMsg():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private class ContactMapper implements ParameterizedRowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("CONTACT_MSG");
		}
	};//Get contact message end
}