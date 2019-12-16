package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;

public class ImsDSDedicatedStaffDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public Boolean isStaffExist(String staffId) throws DAOException {
		logger.debug("isStaffExist: " + staffId);

		String SQL = " select count(1) count from bomweb_sales_lkup_v "
				+ " where staff_id = :staffId or org_staff_id = :orgStaffId";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);
		params.addValue("orgStaffId", staffId);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String temp = new String();
				temp = (rs.getString("count"));
				return temp;
			}
		};

		List<String> resultList = new ArrayList<String>();

		try {
			resultList = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isStaffExist:", e);
			throw new DAOException(e.getMessage(), e);
		}
		return (resultList.size() > 0 ? !"0".equals(resultList.get(0)) : false);
	}

	public List<String> getStaffListName() throws DAOException {

		logger.debug("getStaffListName called");

		List<String> result = new ArrayList<String>();

		String SQL = "SELECT DISTINCT code_id FROM  BOMWEB_CODE_LKUP WHERE code_type='IMS_ACQ_STAFFLIST' ORDER BY 1";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("CODE_ID");
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getStaffListName():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
	}

	public List<String> getStaffList(String staffListName) throws DAOException {
		List<String> result = new ArrayList<String>();

		logger.debug("getStaffList called : " + staffListName);

		String SQL = " SELECT CODE_DESC FROM BOMWEB_CODE_LKUP WHERE code_type = 'IMS_ACQ_STAFFLIST' "
				+ " AND code_id = :staffListName ORDER BY 1";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffListName", staffListName);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("CODE_DESC");
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getStaffList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
	}

	public List<String> getOfferDescList(String staffListName) throws DAOException {

		logger.debug("getOfferDescList called : " + staffListName);

		List<String> result = new ArrayList<String>();

		String SQL = " SELECT ioa.OFFER_DESC "
				+ " FROM w_item_offer_assgn ioa, w_item i, w_dic_basket_item_assgn bia, w_basket_type bt "
				+ " WHERE bt.TYPE='CORE_DE_STAFF' AND bt.type_desc=:staffListName "
				+ " AND bia.basket_id= bt.basket_id " + " AND i.ID=bia.item_id " + " AND i.TYPE='PROG' "
				+ " AND ioa.item_Id=i.ID "
				+ " AND EXISTS (SELECT 1 FROM w_item_pricing WHERE ID=i.ID AND NVL(eff_end_date,trunc(SYSDATE))>=trunc(SYSDATE))";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffListName", staffListName);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("OFFER_DESC");
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getOfferDescList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
	}

	public List<String> getVasDescList(String staffListName) throws DAOException {

		logger.debug("getVasDescList called : " + staffListName);

		List<String> result = new ArrayList<String>();

		String SQL = " SELECT ioa.OFFER_DESC " + " FROM w_item_offer_assgn ioa, w_item_type it "
				+ " WHERE it.TYPE='VAS_DE_STAFF' AND it.type_desc=:staffListName " + " AND ioa.item_Id=it.item_id "
				+ " AND EXISTS (SELECT 1 FROM w_item_pricing WHERE ID=it.item_id AND NVL(eff_end_date,trunc(SYSDATE))>=trunc(SYSDATE))";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffListName", staffListName);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("OFFER_DESC");
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getVasDescList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
	}

	public List<String> getGiftDescList(String staffListName) throws DAOException {

		logger.debug("getGiftDescList called : " + staffListName);

		List<String> result = new ArrayList<String>();

		String SQL = " SELECT ioa.OFFER_DESC " + " FROM w_item_offer_assgn ioa, w_item_type it "
				+ " WHERE it.TYPE='GIFT_DE_STAFF' AND it.type_desc=:staffListName " + " AND ioa.item_Id=it.item_id "
				+ " AND EXISTS (SELECT 1 FROM w_item_pricing WHERE ID=it.item_id AND NVL(eff_end_date,trunc(SYSDATE))>=trunc(SYSDATE))";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffListName", staffListName);

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("OFFER_DESC");
				return dto;
			}
		};

		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getVasDescList():", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
	}

	public void deleteDsDelicatedStaff(String staffListName) throws DAOException {

		logger.debug("deleteDsDelicatedStaff called : " + staffListName);

		MapSqlParameterSource params = new MapSqlParameterSource();

		String SQL = "delete from BOMWEB_CODE_LKUP where code_id = :staffListName";

		params.addValue("staffListName", staffListName);

		try {
			simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in deleteDsDelicatedStaff()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public void insertDsDelicatedStaff(String staffListName, String staffId, String createBy) throws DAOException {

		logger.debug("insertDsDelicatedStaff called : " + staffListName + " " + staffId + " " + createBy);

		MapSqlParameterSource params = new MapSqlParameterSource();

		String SQL = " insert into BOMWEB_CODE_LKUP "
				+ " (code_type, code_id, code_desc, create_by, create_date, last_upd_by, last_upd_date) " + " values "
				+ " ('IMS_ACQ_STAFFLIST', :staffListName, :staffId, :createBy, sysdate, :lastUpdBy, sysdate)";

		params.addValue("staffListName", staffListName);
		params.addValue("staffId", staffId);
		params.addValue("createBy", createBy);
		params.addValue("lastUpdBy", createBy);

		try {
			simpleJdbcTemplate.update(SQL, params);
		} catch (Exception e) {
			logger.error("Exception caught in insertDsDelicatedStaff()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
