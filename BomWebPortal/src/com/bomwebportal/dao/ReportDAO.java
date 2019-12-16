package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class ReportDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());

	private static String getM2BrandingDateSQL = 
			"SELECT to_date(code_id, 'yyyymmdd') report_date "
			+ "FROM bomweb_code_lkup " + "WHERE code_type = 'M2_BRANDING'";

	public Date getM2BrandingDate() throws DAOException {
		logger.debug("getM2BrandingDate @ ReportDAO is called");

		ParameterizedRowMapper<Date> mapper = new ParameterizedRowMapper<Date>() {
			public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getDate("report_date");
			}
		};

		try {
			logger.debug("getM2BrandingDate() @ ReportDAO: " + getM2BrandingDateSQL);
			List<Date> result = simpleJdbcTemplate.query(getM2BrandingDateSQL, mapper);
			return result.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getM2BrandingDate()");
			return null;
		} catch (Exception e) {
			logger.info("Exception caught in getM2BrandingDate():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String getReportContentHtml(String formId, String locale, String variableId) throws DAOException {
		logger.debug("getContentHtml() is called");
		
		String SQL= "select html from bomweb_mob_appform_content "
				+ "where form_id = :formId "
				+ "and locale = nvl(:locale, 'en') "
				+ "and variable_id = :variableId ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("html");
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("formId", formId);
			params.addValue("locale", locale);
			params.addValue("variableId", variableId);
			
			List<String> result = simpleJdbcTemplate.query(SQL, mapper,params);
			
			if (CollectionUtils.isEmpty(result)) {
				return null;
			} else {
				return result.get(0);
			}
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getContentHtml()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public Boolean getCSubInd(String orderId) throws DAOException {
		logger.debug("getCSubInd() is called");
		
		String SQL= "select csub_ind from bomweb_order_mob "
				+ "where order_id = :orderId ";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("csub_ind");
			}
		};

		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("orderId", orderId);

			List<String> result = simpleJdbcTemplate.query(SQL, mapper,params);
			
			if (CollectionUtils.isEmpty(result)) {
				return false;
			} else {
				return "Y".equals(result.get(0));
			}
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getCSubInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
