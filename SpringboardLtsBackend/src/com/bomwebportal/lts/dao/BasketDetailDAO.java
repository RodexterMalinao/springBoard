package com.bomwebportal.lts.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class BasketDetailDAO extends BaseDAO {

	private static final String SQL_GET_BASKET_CONTRACT_PERIOD = 
		"select contract_period " +
		"from w_basket_dtl_lts " +
		"where id = ?";
	
	public String getBasketContractPeriod(String pBasketId) throws DAOException {
		
	    try{
	    	return simpleJdbcTemplate.queryForObject(SQL_GET_BASKET_CONTRACT_PERIOD, String.class, pBasketId);
	    } catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in getBasketContractPeriod - ", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
