package com.bomwebportal.mob.ccs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketDTO;

public class MobCcsBasketAssoWBasketDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());

	public BasketAssoWBasketDTO getWBasket(String basketId) throws DAOException {

		logger.info("getWBasket @ MobCcsBasketAssoWBasketDAO is called");
		List<BasketAssoWBasketDTO> itemList = new ArrayList<BasketAssoWBasketDTO>();
		String getWBasketSQL = "SELECT" +
				" b.ID, ba.DESCRIPTION" +
				" FROM W_BASKET b" +
				" LEFT JOIN W_BASKET_ATTRIBUTE_MV ba ON (b.id = ba.basket_id)" +
				" WHERE b.ID = ?" +
				" AND NVL(ba.NATURE, 'ACQ') = 'ACQ' ";
		try {
			logger.info("getWBasket() @ MobCcsBasketAssoWBasketDAO: "
					+ getWBasketSQL);

			itemList = simpleJdbcTemplate.query(getWBasketSQL, this.getRowMapper(), basketId);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasket()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasket()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasket(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList == null || itemList.isEmpty() ? null : itemList.get(0);
	}

	public List<BasketAssoWBasketDTO> getWBasketALL() throws DAOException {
		logger.info("getWBasketALL @ MobCcsBasketAssoWBasketDAO is called");
		List<BasketAssoWBasketDTO> itemList = new ArrayList<BasketAssoWBasketDTO>();
		String getWBasketALLSQL = "SELECT" +
				" b.ID, ba.DESCRIPTION" +
				" FROM W_BASKET b" +
				" LEFT JOIN W_BASKET_ATTRIBUTE_MV ba ON (b.id = ba.basket_id)" +
				" WHERE b.ID = ?" +
				" AND NVL(ba.NATURE, 'ACQ') = 'ACQ' ";
		try {
			logger.info("getWBasketALL() @ MobCcsBasketAssoWBasketDAO: "
					+ getWBasketALLSQL);

			itemList = simpleJdbcTemplate.query(getWBasketALLSQL, this.getRowMapper());
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getWBasketALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getWBasketALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getWBasketALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	public ParameterizedRowMapper<BasketAssoWBasketDTO> getRowMapper() {
		return new ParameterizedRowMapper<BasketAssoWBasketDTO>() {
			public BasketAssoWBasketDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasketAssoWBasketDTO dto = new BasketAssoWBasketDTO();
				dto.setBasketId(rs.getString("ID"));
				dto.setBasketDesc(rs.getString("DESCRIPTION"));
				return dto;
			}
		};
	}
	
}
