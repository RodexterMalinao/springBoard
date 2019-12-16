package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class AddrLkupDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public String getStreetCatgDescFromStCatgCd(String stCatgCd) throws DAOException{
		List<String> streetCatgDesc = null;

		String SQL ="select stcatdsc from w_addrcategory where stcatgcd = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("stcatdsc");
			}
		};

		try {
			streetCatgDesc = simpleJdbcTemplate.query(SQL, mapper, stCatgCd);
		} catch (EmptyResultDataAccessException erdae) {
			
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAutopayBankBranchList()", e);
			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (streetCatgDesc.size() == 0) {
			streetCatgDesc.add("");
		}
		return streetCatgDesc.get(0);
	}
	
	public String getSectDescFromSectCd(String sectCd) throws DAOException{
		List<String> sectDesc = null;

		String SQL ="select sect_desc from w_addrlkup_section where code = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("sect_desc");
			}
		};

		try {
			sectDesc = simpleJdbcTemplate.query(SQL, mapper, sectCd);
		} catch (EmptyResultDataAccessException erdae) {
			
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getSectDescFromSectCd()", e);
			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (sectDesc.size() == 0) {
			sectDesc.add("");
		}
		
		return sectDesc.get(0);
	}
	
	public String getDistDscFromDistrNum(String distrNum) throws DAOException{
		List<String> distDsc = null;

		String SQL ="select distdsc from w_addrlkup_district where code = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("distdsc");
			}
		};

		try {
			distDsc = simpleJdbcTemplate.query(SQL, mapper, distrNum);
		} catch (EmptyResultDataAccessException erdae) {
			
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getDistDscFromDistrNum()", e);
			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if (distDsc.size() == 0) {
			distDsc.add("");
		}
		return distDsc.get(0);
	}
	
	public String getAreaDescFromDistrNum(String distrNum) throws DAOException{
		List<String> areaDesc = null;

		String SQL ="select description from w_addrlkup_area " +
				"where code = (select areacd from w_addrlkup_district where code = ?)";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("description");
			}
		};

		try {
			areaDesc = simpleJdbcTemplate.query(SQL, mapper, distrNum);
		} catch (EmptyResultDataAccessException erdae) {
			areaDesc.add("");
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAreaDescFromDistrNum()", e);
			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (areaDesc.size() == 0) {
			areaDesc.add("");
		}
		return areaDesc.get(0);
	}
	
	public String getAreaCdFromDistrNum(String distrNum) throws DAOException{
		List<String> areaCd = null;

		String SQL ="select areacd from w_addrlkup_district where code = ?";

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("areacd");
			}
		};

		try {
			areaCd = simpleJdbcTemplate.query(SQL, mapper, distrNum);
		} catch (EmptyResultDataAccessException erdae) {
			areaCd.add("");
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAreaCdFromDistrNum()", e);
			try {
				throw new DAOException(e.getMessage(), e);
			} catch (DAOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (areaCd.size() == 0) {
			areaCd.add("");
		}
		return areaCd.get(0);
	}
	
}
