package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class LtsSalesInfoDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
//	private static final String SQL_GET_STAFF_INFO = 
//			"SELECT NVL(staff_name, '-') AS STAFF_NAME, " +
//			"NVL(org_staff_id, '-') AS ORG_STAFF_ID, " +
//			"NVL(sales_code, '-') AS SALES_CODE " +
//			"FROM BOMWEB_SALES_PROFILE " +
//			"WHERE (end_date IS NULL " +
//			"OR end_date >= SYSDATE) ";
	
	private static final String SQL_GET_STAFF_INFO = 
			"SELECT NVL(p.staff_name, '-') AS STAFF_NAME, " +
			"NVL(p.org_staff_id, '-') AS ORG_STAFF_ID, " +
			"NVL(p.sales_code, '-') AS SALES_CODE, " +
			"NVL(a.channel_cd, '-') AS CHANNEL_CODE, " + 
			"NVL(a.team_cd, '-') AS TEAM_CODE, " +
			"NVL(a.centre_cd, '-') AS CENTRE_CD, " +
			"a.source_code AS SOURCE_CD, " +
			"a.channel_id AS CHANNEL_ID, " +
			"a.boc AS BOC, " +
			"p.category AS CATEGORY "+
			"FROM BOMWEB_SALES_PROFILE p,  BOMWEB_SALES_ASSIGNMENT a " + 
			"WHERE (p.end_date IS NULL OR p.end_date >= SYSDATE) " +
			"AND (a.end_date IS NULL OR a.end_date >= SYSDATE) " +
			"AND p.STAFF_ID = a.STAFF_ID ";
	
	public String getStaffCategory(String staffId) throws DAOException{
		String SQL = SQL_GET_STAFF_INFO;
		SQL = SQL.concat("AND (p.STAFF_ID = ? OR p.ORG_STAFF_ID = ? )");
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("CATEGORY");
			}
		};
		
		try {
			List<String> list = simpleJdbcTemplate.query(SQL, mapper, staffId, staffId);
			return list.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getStaffInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	public LtsSalesInfoFormDTO getStaffInfo(String orgStaffId, String staffId)
			throws DAOException{
		List<LtsSalesInfoFormDTO> list = new ArrayList<LtsSalesInfoFormDTO>();
		String SQL = SQL_GET_STAFF_INFO;
		SQL = SQL.concat("AND p.STAFF_ID = ? AND p.ORG_STAFF_ID = ? ");
		
		ParameterizedRowMapper<LtsSalesInfoFormDTO> mapper = new ParameterizedRowMapper<LtsSalesInfoFormDTO>() {
			public LtsSalesInfoFormDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LtsSalesInfoFormDTO dto = new LtsSalesInfoFormDTO();
				dto.setStaffName(rs.getString("STAFF_NAME"));
				dto.setStaffId(rs.getString("ORG_STAFF_ID"));
				dto.setSalesCode(rs.getString("SALES_CODE"));
				dto.setSalesChannel(rs.getString("CHANNEL_CODE"));
				dto.setSalesTeam(rs.getString("TEAM_CODE"));
				dto.setSourceCode(rs.getString("SOURCE_CD"));
				dto.setBoc(rs.getString("BOC"));
				dto.setSalesCenter(rs.getString("CENTRE_CD"));
				dto.setPremier(StringUtils.equals(rs.getString("CHANNEL_ID"), LtsConstant.CHANNEL_ID_PREMIER));
				dto.setCallCenter(StringUtils.equals(rs.getString("CHANNEL_ID"), LtsConstant.CHANNEL_ID_CS));
				return dto;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper, staffId, orgStaffId);
			if(list != null && list.size() > 0){
				return list.get(0);
			}else{
				return null;
			}
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getStaffInfo() - orgStaffId: "+ orgStaffId + ", staffId: " + staffId , e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public LtsAcqSalesInfoFormDTO getAcqStaffInfo(String orgStaffId, String staffId)
			throws DAOException{
		List<LtsAcqSalesInfoFormDTO> list = new ArrayList<LtsAcqSalesInfoFormDTO>();
		String SQL = SQL_GET_STAFF_INFO;
		SQL = SQL.concat("AND p.STAFF_ID = ? AND p.ORG_STAFF_ID = ? ");
		
		ParameterizedRowMapper<LtsAcqSalesInfoFormDTO> mapper = new ParameterizedRowMapper<LtsAcqSalesInfoFormDTO>() {
			public LtsAcqSalesInfoFormDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LtsAcqSalesInfoFormDTO dto = new LtsAcqSalesInfoFormDTO();
				dto.setStaffName(rs.getString("STAFF_NAME"));
				dto.setStaffId(rs.getString("ORG_STAFF_ID"));
				dto.setSalesCode(rs.getString("SALES_CODE"));
				dto.setSalesChannel(rs.getString("CHANNEL_CODE"));
				dto.setSalesTeam(rs.getString("TEAM_CODE"));
				dto.setBoc(rs.getString("BOC"));
				dto.setSalesCenter(rs.getString("CENTRE_CD"));
				return dto;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper, staffId, orgStaffId);
			return list.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getStaffInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String[] getShopContactAndChannel(String pShopCode)
			throws DAOException{
		List<String[]> list = new ArrayList<String[]>();
		String SQL = "SELECT CONTACT_PHONE, SALES_CHANNEL FROM BOMWEB_SHOP WHERE shop_cd = ? ";
		
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String channel = rs.getString("SALES_CHANNEL");
				String contact = rs.getString("CONTACT_PHONE");
				String[] array = {channel, contact};
				return array;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper, pShopCode);
			if(list.size() > 0){
				return list.get(0);
			}else{
				return new String[2];
			}
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getShopContactAndChannel()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getSalesContact(String pStaffId)
			throws DAOException{
		List<String> list = new ArrayList<String>();
		String SQL = "SELECT CONTACT_NUMBER FROM BOMWEB_SALES_PROFILE " +
				"WHERE org_staff_id = '" + pStaffId + "' " +
				"and end_date is null " +
				"and contact_number is not null";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String contact = rs.getString("CONTACT_NUMBER");
				return contact;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
			if(list.size() > 0){
				return list.get(0);
			}else{
				return null;
			}

		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getSalesContact()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<String[]> getOrgStaffId(String staffId) throws DAOException{
		List<String[]> list = new ArrayList<String[]>();
		String SQL = "SELECT * FROM BOMWEB_SALES_PROFILE WHERE STAFF_ID = ? AND (end_date IS NULL OR end_date >= SYSDATE)";
		ParameterizedRowMapper<String[]> mapper = new ParameterizedRowMapper<String[]>() {
			public String[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String[] orgStaffId = {rs.getString("ORG_STAFF_ID"), rs.getString("STAFF_NAME")};
				return orgStaffId;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper, staffId);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getOrgStaffId()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return list;
	}
	
	public List<String> getTeamCdListByChannelCd(String channelCd) throws DAOException{
		List<String> list = new ArrayList<String>();
		String SQL = " SELECT DISTINCT TEAM_CD FROM BOMWEB_SALES_ASSIGNMENT " +
				" WHERE channel_cd = ? " +
				" AND team_cd IS NOT NULL " +
				" ORDER BY TEAM_CD " ;
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String teamCd = rs.getString("TEAM_CD");
				return teamCd;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper, channelCd);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getOrgStaffId()", e);
			throw new DAOException(e.getMessage(), e);
		}

		return list;
	}
}

