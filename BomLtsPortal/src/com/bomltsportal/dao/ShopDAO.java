package com.bomltsportal.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class ShopDAO extends BaseDAO {
	
	private static final String SQL_GET_SALES_CHANNEL = 
		"select sales_channel from bomweb_shop " +
			" where shop_cd = ? ";

	public String getSalesChannel(String shopCd) throws DAOException {
		
		try {
			return simpleJdbcTemplate.queryForObject(SQL_GET_SALES_CHANNEL, String.class, shopCd);
		}
		catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException in getSalesChannel()");
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getSalesChannel():", e);
			throw new DAOException();
		}
		
	}
	
	
}
