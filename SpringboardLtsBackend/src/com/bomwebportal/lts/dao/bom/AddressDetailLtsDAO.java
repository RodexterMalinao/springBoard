package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AddressDetailDTO;
import com.bomwebportal.lts.util.LtsProfileHelper;

public class AddressDetailLtsDAO extends BaseDAO {

	private static final String SQL_GET_BLACKLIST_ADDR_IND = 
			"select bl_ind " +
			"from b_addr_lkup " +
			"where (serbdyno = ? or serbdyno = ?)";
	
	
	private static final String SQL_GET_ADDRESS_DTL = 
			"select * from b_addr_lkup " +
			"where serbdyno = ? ";
	
	private static final String SQL_GET_ADDRESS_BUILD_XY = 
		"select build_xy " +
		"from B_SERBDYNO_BLDG_XY_SRC " + //b_slv_addr_sb " +
		"where sb_no = ? ";
	
	
	public boolean isBlacklistAddress(String pSrvBdy, String pFlat, String pFloor) throws DAOException {

		String[] srvBdies = LtsProfileHelper.reformatSrvBoundary(pSrvBdy);
		List<String> parameters = new ArrayList<String>();
		parameters.add(srvBdies[0]);
		parameters.add(srvBdies[1]);
		
		StringBuilder sb = new StringBuilder(SQL_GET_BLACKLIST_ADDR_IND);
		
		if (StringUtils.isEmpty(pFlat)) {
			sb.append(" and apfltun is null");
		} else {
			sb.append("and apfltun = ? ");
			parameters.add(pFlat);
		}
		if (StringUtils.isEmpty(pFloor)) {
			sb.append(" and flr_num is null");
		} else {
			sb.append(" and flr_num = ? ");
			parameters.add(pFloor);
		}
		
		
		try {
			List<String> result = this.simpleJdbcTemplate.query(sb.toString(), new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("bl_ind");
				}}, parameters.toArray(new Object[0]));
			
			if (result.size() == 0) {
				return false;
			}
			return StringUtils.equals("B", result.get(0));
		} catch (Exception e) {
			logger.error("Error in isBlacklistAddress() Unit: " + pFlat + " Floor: " + pFloor + " SB: " + pSrvBdy, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public AddressDetailDTO getAddressDetail(String sb) throws DAOException {
		
		ParameterizedRowMapper<AddressDetailDTO> mapper = new ParameterizedRowMapper<AddressDetailDTO>() {
			public AddressDetailDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				AddressDetailDTO addressDetail = new AddressDetailDTO();
				addressDetail.setArea(rs.getString("AREANAME"));
				addressDetail.setAreaCd(rs.getString("AREA_CD"));
				addressDetail.setBuildID(rs.getString("BUILD_ID"));
				addressDetail.setBuildName(rs.getString("BLDG_NAM"));
				addressDetail.setDistrict(rs.getString("DISTDESC"));
				addressDetail.setDistrictCd(rs.getString("DISTRNUM"));
				addressDetail.setFloorNum(rs.getString("FLR_NUM"));
//				addressDetail.setFullAddress(fullAddress);
				addressDetail.setGridID(rs.getString("GRID_ID"));
				addressDetail.setHlotNum(rs.getString("lotno"));
				addressDetail.setLdlotNum(rs.getString("LOTNO"));
				addressDetail.setLotHseInd(rs.getString("LOT_HSE_IND"));
				addressDetail.setSectCd(rs.getString("SECT_CD"));
				addressDetail.setSectDesc(rs.getString("SECTDESC"));
				addressDetail.setSrvBdry(rs.getString("SERBDYNO"));
				addressDetail.setStreetCat(rs.getString("STCATDESC"));
				addressDetail.setStreetCatCd(rs.getString("STCAT_CD"));
				addressDetail.setStreetName(rs.getString("ST_NAM"));
				addressDetail.setStreetNum(rs.getString("HSELTNUM"));
				return addressDetail;
			}
		};
		
		
		try {
			List<AddressDetailDTO> addressDetailList = this.simpleJdbcTemplate.query(SQL_GET_ADDRESS_DTL, mapper, sb);
			
			if (addressDetailList == null || addressDetailList.isEmpty()) {
				return null;
			}
			
			return addressDetailList.get(0);
		}
		catch (Exception e) {
			logger.error("Error in getAddressDetail [Service Boundary : " + sb + "]" , e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
    public String getAddressBuildXy(String pSrvBdy) throws DAOException {
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {				
				return rs.getString("BUILD_XY");
			}
		};
		try {
			List<String> itemList = this.simpleJdbcTemplate.query(SQL_GET_ADDRESS_BUILD_XY, mapper, pSrvBdy);
			return itemList.isEmpty() ? null : itemList.get(0);
		}
		catch (Exception e) {
			logger.error("Error in getAddressBuildXy [Service Boundary : " + pSrvBdy + "]" , e);
			throw new DAOException(e.getMessage(), e);
		}
	}	
	
}
