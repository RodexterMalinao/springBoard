package com.bomwebportal.lts.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAddressDTO;


public class LtsAddressSearchDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());

	public List<ImsAddressDTO> getFlatFloorBySB(String[] sbList , boolean includeLtsOnly) 
		throws DAOException{
		
		logger.debug("getFlatFloorBySB is called");
		final String ADDPTYPE_INCLUDE_LTS_ONLY = " and addptype <> 'I' \n";
		final String ADDPTYPE_INCLUDE_NOT_LTS_ONLY = " and addptype = 'I' \n";

		List<ImsAddressDTO> addrList = new ArrayList<ImsAddressDTO>();
		
		String addpTypeSql;
		if(includeLtsOnly){
			addpTypeSql = ADDPTYPE_INCLUDE_LTS_ONLY;
		}else{
			addpTypeSql = ADDPTYPE_INCLUDE_NOT_LTS_ONLY;
		}
		
		// Only handle 20 Service Boundary input for address searching
		StringBuilder SQL= new StringBuilder();
		SQL.append(" select distinct apfltun flat, flr_num floor, serbdyno, 'LTS' lob, lot_hse_ind, \n");
		SQL.append("        hseltnum, lotno, bldg_nam, st_nam, stcatdesc, \n");
		SQL.append("        stcat_cd, sectdesc, sect_cd, distdesc, distrnum, \n");
		SQL.append("        b.area_desc areaname, a.area_cd area_cd \n");
		SQL.append(" from B_ADDR_LKUP a, B_AREA b \n");
		SQL.append(" where serbdyno in ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) \n");
		SQL.append(" and (bl_ind IS NULL OR (apfltun IS NULL AND flr_num IS NULL)) \n");
		SQL.append(addpTypeSql);
		SQL.append(" and a.area_cd = b.area_cd \n");
		
		SQL.append(" UNION \n");
		
		SQL.append(" select distinct apfltun flat, flr_num floor, serbdyno, 'LTS' lob, lot_hse_ind, \n");
		SQL.append("        hseltnum, lotno, bldg_nam, st_nam, stcatdesc, \n");
		SQL.append("        stcat_cd, sectdesc, sect_cd, distdesc, distrnum, \n");
		SQL.append("        b.area_desc areaname, a.area_cd area_cd \n");
		SQL.append(" from B_ADDR_LKUP a, B_AREA b \n");
		SQL.append(" WHERE (serbdyno, 1) IN \n");
		SQL.append("   (SELECT serbdyno, COUNT(*) FROM b_addr_lkup \n");
		SQL.append("    where serbdyno in ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) \n");
		SQL.append(addpTypeSql);
		SQL.append("    GROUP BY serbdyno HAVING COUNT(*) = 1) \n");
		SQL.append(" AND a.area_cd = b.area_cd \n");
		
		ParameterizedRowMapper<ImsAddressDTO> mapper = new ParameterizedRowMapper<ImsAddressDTO>() {
			public ImsAddressDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				ImsAddressDTO dto = new ImsAddressDTO();
				dto.setFlat(rs.getString("FLAT"));
				dto.setFloor(rs.getString("FLOOR"));
				dto.setServiceBoundaryNum(rs.getString("SERBDYNO"));
				dto.setLob(rs.getString("LOB"));
				dto.setLotHouseInd(rs.getString("LOT_HSE_IND"));
				dto.setHouseLotNum(rs.getString("HSELTNUM"));
				dto.setLotNum(rs.getString("LOTNO"));
				dto.setBuildingName(rs.getString("BLDG_NAM"));
				dto.setStreetName(rs.getString("ST_NAM"));
				dto.setStreetCatgDesc(rs.getString("STCATDESC"));
				dto.setStreetCatgCode(rs.getString("STCAT_CD"));
				dto.setSectionDesc(rs.getString("SECTDESC"));
				dto.setSectionCode(rs.getString("SECT_CD"));
				dto.setDistrictDesc(rs.getString("DISTDESC"));
				dto.setDistrictCode(rs.getString("DISTRNUM"));
				dto.setAreaDesc(rs.getString("AREANAME"));
				dto.setAreaCode(rs.getString("AREA_CD"));
//				dto.setAddress(rs.getString("AREANAME") + rs.getString("BLDG_NAM"));

				return dto;
			}
		};
		
		try {
			logger.debug("getFlatFloorBySB() @ AddressSearchDAO: " + SQL);
			logger.info("getFlatFloorBySB() @ AddressSearchDAO: sbList: " + ArrayUtils.toString(sbList.toString()));
			/*duplicate itself for second part of the SQL*/
			sbList = (String[])ArrayUtils.addAll(sbList, sbList);
			addrList = simpleJdbcTemplate.query(SQL.toString(), mapper, new Object[]{sbList} );

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getFlatFloorBySB()");

			addrList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getFlatFloorBySB():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		logger.debug("Count in DAO=" + addrList.size());
		return addrList;
		
	}

}
