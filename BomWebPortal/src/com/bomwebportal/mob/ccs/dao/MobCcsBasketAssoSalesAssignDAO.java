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

public class MobCcsBasketAssoSalesAssignDAO extends BaseDAO {
    protected final Log logger = LogFactory.getLog(getClass());

	private static String getExistSalesAssignChannelCdALLSQL = "SELECT" +
			" DISTINCT(CHANNEL_CD) CHANNEL_CD" +
			" FROM BOMWEB_SALES_ASSIGNMENT" +
			" WHERE CHANNEL_CD IS NOT NULL";
	public List<String> getExistSalesAssignChannelCdALL() throws DAOException {

		logger.info("getExistSalesAssignChannelCdALL @ MobCcsBasketAssoSalesAssignDAO is called");
		List<String> itemList = new ArrayList<String>();

		try {
			logger.info("getExistSalesAssignChannelCdALL() @ MobCcsBasketAssoSalesAssignDAO: "
					+ getExistSalesAssignChannelCdALLSQL);

			itemList = simpleJdbcTemplate.query(getExistSalesAssignChannelCdALLSQL, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("CHANNEL_CD");
				}
			});
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getExistSalesAssignChannelCdALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getExistSalesAssignChannelCdALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getExistSalesAssignChannelCdALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
	private static String getExistSalesAssignTeamCdALLSQL = "SELECT" +
			" DISTINCT(TEAM_CD) TEAM_CD" +
			" FROM BOMWEB_SALES_ASSIGNMENT" +
			" WHERE CHANNEL_CD = ?" +
			" AND CENTRE_CD = ?" +
			" AND TEAM_CD IS NOT NULL";
	public List<String> getExistSalesAssignTeamCdALL(String channelCd, String centreCd) throws DAOException {

		logger.info("getExistSalesAssignTeamCdALL @ MobCcsBasketAssoSalesAssignDAO is called");
		List<String> itemList = new ArrayList<String>();

		try {
			logger.info("getExistSalesAssignTeamCdALL() @ MobCcsBasketAssoSalesAssignDAO: "
					+ getExistSalesAssignTeamCdALLSQL);

			itemList = simpleJdbcTemplate.query(getExistSalesAssignTeamCdALLSQL, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("TEAM_CD");
				}
			}, channelCd, centreCd);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getExistSalesAssignTeamCdALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getExistSalesAssignTeamCdALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getExistSalesAssignTeamCdALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}

	private static String getExistSalesAssignCentreCdALLSQL = "SELECT" +
			" DISTINCT(CENTRE_CD) CENTRE_CD" +
			" FROM BOMWEB_SALES_ASSIGNMENT" +
			" WHERE CHANNEL_CD = ?" +
			" AND CENTRE_CD IS NOT NULL";
	public List<String> getExistSalesAssignCentreCdALL(String channelCd) throws DAOException {

		logger.info("getExistSalesAssignCentreCdALL @ MobCcsBasketAssoSalesAssignDAO is called");
		List<String> itemList = new ArrayList<String>();

		try {
			logger.info("getExistSalesAssignCentreCdALL() @ MobCcsBasketAssoSalesAssignDAO: "
					+ getExistSalesAssignCentreCdALLSQL);

			itemList = simpleJdbcTemplate.query(getExistSalesAssignCentreCdALLSQL, new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("CENTRE_CD");
				}
			}, channelCd);
		} catch (BadSqlGrammarException bsge) {
			logger.info("BadSqlGrammarException in getExistSalesAssignCentreCdALL()");

			itemList = null;
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getExistSalesAssignCentreCdALL()");

			itemList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getExistSalesAssignCentreCdALL(): ", e);

			throw new DAOException(e.getMessage(), e);
		}
		return itemList;
	}
	
}
