package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class OfferChangeDAO extends BaseDAO {
	
	private static final String SQL_GET_OFFER_CHANGE = 
		" SELECT to_offer, action, item_type, item_id " +
		"   FROM W_OFFER_CHANGE_LKUP " +
		"	WHERE UPPER(FROM_LTS_PROD) IN ('*', UPPER(:ltsFromProd)) " + 
		"	AND UPPER(TO_LTS_PROD) IN ('*', UPPER(:ltsToProd)) " +
		"	AND UPPER(FROM_IMS_PROD) IN ('*', UPPER(:imsFromProd)) " +
		"	AND UPPER(TO_IMS_PROD) IN ('*', UPPER(:imsToProd)) " +
		"	AND MIRROR IN ('*', :mirror) " +
		"	AND RENTAL_ROUTER IN ('*', :rentalRouter) " 
		;

		
	public List<String[]> getOfferChangeList(String ltsFromProd,
			String ltsToProd, String imsFromProd, String imsToProd, String mirror, String[] fromOffers, String rentalRouter) throws DAOException{
		
		
		ParameterizedRowMapper<String[]> mapper = getMapper();

		MapSqlParameterSource params = new MapSqlParameterSource();
		StringBuilder sql = new StringBuilder(SQL_GET_OFFER_CHANGE);
		try {
			
			params.addValue("ltsFromProd", StringUtils.defaultIfEmpty(ltsFromProd, "*"));
			params.addValue("ltsToProd", StringUtils.defaultIfEmpty(ltsToProd, "*"));
			params.addValue("imsFromProd", StringUtils.defaultIfEmpty(imsFromProd, "*"));
			params.addValue("imsToProd", StringUtils.defaultIfEmpty(imsToProd, "*"));
			params.addValue("mirror", StringUtils.defaultIfEmpty(mirror, "N"));
			params.addValue("rentalRouter", StringUtils.defaultIfEmpty(rentalRouter, "N"));
			
			
			if (ArrayUtils.isNotEmpty(fromOffers)) {
				sql.append(" and from_offer IN ('*', :fromOffer)");
				params.addValue("fromOffer", Arrays.asList(fromOffers));
			}
			
			return simpleJdbcTemplate.query(sql.toString(), mapper, params);

		} catch (EmptyResultDataAccessException erdae) {
			logger.error("searchLtsOnlineOrder EmptyResultDataAccessException");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in searchLtsOnlineOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
	
	private ParameterizedRowMapper<String[]> getMapper(){
		return new ParameterizedRowMapper<String[]>() {

			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] offerChanges = new String[4];
				offerChanges[0] = rs.getString("to_offer");
				offerChanges[1] = rs.getString("action");
				offerChanges[2] = rs.getString("item_type");
				offerChanges[3] = rs.getString("item_id");
				return offerChanges;
			}
		};
	}
}
