package com.bomwebportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dto.AddressAreaDTO;
import com.bomwebportal.dto.AddressCategoryDTO;
import com.bomwebportal.dto.AddressDistrictDTO;
import com.bomwebportal.dto.AddressSectionDTO;
import com.bomwebportal.dto.BankBranchDTO;
import com.bomwebportal.dto.CreditCardTypeDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.dto.report.MobAppFormDTO;

public class BomConstantLkupDAO extends BaseDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	public List<AddressAreaDTO> getImsAddressAreaList() throws DAOException {
		List<AddressAreaDTO> addressAreaList = new ArrayList<AddressAreaDTO>();
		
		String SQL = "SELECT area_cd, area_desc FROM B_AREA ORDER BY 1";

		ParameterizedRowMapper<AddressAreaDTO> mapper = new ParameterizedRowMapper<AddressAreaDTO>() {

			public AddressAreaDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressAreaDTO addressAreaDto = new AddressAreaDTO();
				addressAreaDto.setAreaCode(rs.getString("area_cd"));
				addressAreaDto.setAreaDescription(rs.getString("area_desc"));
				return addressAreaDto;
			}
		};

		try {
			addressAreaList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressAreaList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return addressAreaList;
	}

	public List<AddressDistrictDTO> getImsAddressDistrictList()
			throws DAOException {
		List<AddressDistrictDTO> addressDistrictList = new ArrayList<AddressDistrictDTO>();
		
		String SQL = "SELECT distrnum, area_cd, dist_desc FROM b_distlkup ORDER BY 3";

		ParameterizedRowMapper<AddressDistrictDTO> mapper = new ParameterizedRowMapper<AddressDistrictDTO>() {

			public AddressDistrictDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressDistrictDTO AddressDistrictDTO = new AddressDistrictDTO();
				AddressDistrictDTO.setDistrictCode(rs.getString("distrnum"));
				AddressDistrictDTO.setDistrictDescription(rs
						.getString("dist_desc"));
				AddressDistrictDTO.setAreaCode(rs.getString("area_cd"));
				return AddressDistrictDTO;
			}
		};

		try {
			addressDistrictList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressDistrictList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return addressDistrictList;
	}

	public List<AddressSectionDTO> getImsAddressSectionList() throws DAOException {
		List<AddressSectionDTO> addressSectionList = new ArrayList<AddressSectionDTO>();
		
		String SQL ="SELECT sect_cd, sect_desc, distrnum FROM B_SECTION ORDER BY 1";
		
		ParameterizedRowMapper<AddressSectionDTO> mapper = new ParameterizedRowMapper<AddressSectionDTO>() {

			public AddressSectionDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressSectionDTO AddressSectionDTO = new AddressSectionDTO();
				AddressSectionDTO.setSectionCode(rs.getString("sect_cd"));
				AddressSectionDTO.setSectionDescription(rs.getString("sect_desc"));
				AddressSectionDTO.setDistrictCode(rs.getString("distrnum"));
				return AddressSectionDTO;
			}
		};

		try {
			addressSectionList = simpleJdbcTemplate.query(SQL, mapper);
			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getAddressSectionList()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return addressSectionList;
	}
	
	public List<Map<String, String>> getImsLookUpCode(String BomGrpId) throws DAOException{
		logger.debug("getImsLookUpCode:"+BomGrpId);
		String SQL = "	SELECT   bom_grp_id, bom_code, bom_desc	"+
		"	    FROM b_lookup	"+
		"	   WHERE bom_grp_id = ?	"+
		"	ORDER BY bom_code ";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("bom_grp_id", rs.getString("bom_grp_id"));
				map.put("bom_code", rs.getString("bom_code"));
				map.put("bom_desc", rs.getString("bom_desc"));
				
				return map;
			}
		};
		
		try {
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, BomGrpId);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsLookUpCode()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}

}
