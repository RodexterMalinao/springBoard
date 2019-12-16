package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class OfferProductLkupDAO extends BaseDAO {

	private static final String SQL_GET_RENEWAL_NEW_DEVICE_OFFER = 
		" select offer_id from b_offer_a " +
			" where offer_cd in ( " +
			" select distinct condition_offer_cd from  SB_LTS_ACTION_LKUP_V " +
			" where action = 'CO' )";
	
	private static final String SQL_GET_PRODUCT_ID_BY_PARAM_VALUE = 
		"select boa.offer_id " +
		" from " +
		" 	b_offer_product_assgn_a bopa, " +
		" 	b_offer_assgn_a boa " +
		" where bopa.prod_id in ( " +
		"   select bp.prod_id " +
		"     from b_product_a bp, " + 
		"          b_product_attb_a bpa, " +
		"          b_attb_a ba " +
		"     where bp.prod_id = bpa.prod_id " +
		"     and bpa.prod_attb_id = ba.attb_id " +
		"     and ba.attb_desc = :parameter " +
		"     and ba.attb_value = :value ) " +
		" and (bopa.eff_end_date is null or bopa.eff_end_date > sysdate) " +
		" and bopa.offer_sub_id = boa.offer_sub_id ";
	
	
	public List<String> getRenewalDeviceOfferIdList() throws DAOException {
		try {
			
			return this.simpleJdbcTemplate.query(SQL_GET_RENEWAL_NEW_DEVICE_OFFER, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("offer_id");
				}
			});
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getRenewalDeviceOfferIdList()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String> getOfferIdListByProductParam(String pParameter, String pValue) throws DAOException {
		try {
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("parameter", pParameter);
			paramSource.addValue("value", pValue);
			
			return this.simpleJdbcTemplate.query(SQL_GET_PRODUCT_ID_BY_PARAM_VALUE, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("offer_id");
				}
			}, paramSource);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Error in getProductIdListByParamValue()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
}
