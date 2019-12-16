package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAddressDTO;


public class ImsAddressSearchDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());

	public List<ImsAddressDTO> getFlatFloorBySB(String[] sbList ) 
		throws DAOException{
		
		logger.debug("getFlatFloorBySB is called");

		List<ImsAddressDTO> addrList = new ArrayList<ImsAddressDTO>();
		
		
		// Only handle 20 Service Boundary input for address searching
		StringBuilder SQL= new StringBuilder();
		SQL.append(" select distinct apfltun flat, flr_num floor, serbdyno, 'IMS' lob, lot_hse_ind, \n");
		SQL.append("        hseltnum, lotno, bldg_nam, st_nam, stcatdesc, \n");
		SQL.append("        stcat_cd, sectdesc, sect_cd, distdesc, distrnum, \n");
		SQL.append("        b.area_desc areaname, a.area_cd area_cd, \n");
		SQL.append(" (SELECT DECODE (COUNT (*), 0, 'N', 'Y')									\n");
		SQL.append("    FROM b_service_rollout sr, b_lookup l                 \n");
		SQL.append("   WHERE sr.serbdyno = a.serbdyno                         \n");
		SQL.append("     AND NVL (sr.obsolete, 'N') = 'N'                     \n");
		SQL.append("     AND l.bom_grp_id = 'SB_IMS_LTS'                      \n");
		SQL.append("     AND l.bom_code = 'SRV_CD_EYEX_' || sr.service_code   \n");
		SQL.append("     AND l.bom_desc = 'Y') cover_eyex,                    \n");
		SQL.append(" (SELECT DECODE (COUNT (*), 0, 'N', 'Y')                  \n");
		SQL.append("    FROM b_service_rollout sr, b_lookup l                 \n");
		SQL.append("   WHERE sr.serbdyno = a.serbdyno                         \n");
		SQL.append("     AND NVL (sr.obsolete, 'N') = 'N'                     \n");
		SQL.append("     AND l.bom_grp_id = 'SB_IMS_LTS'                      \n");
		SQL.append("     AND l.bom_code = 'SRV_CD_PE_' || sr.service_code     \n");
		SQL.append("     AND l.bom_desc = 'Y') cover_pe,                      \n");
		SQL.append(" (SELECT DECODE(COUNT(*),0,'N','Y')  					  \n");
		SQL.append("    FROM b_srvcode_lkup sl, b_service_rollout sr 		  \n");
		SQL.append("   WHERE sr.serbdyno = a.serbdyno 						  \n");
		SQL.append("     AND NVL(sr.obsolete,'N') = 'N'						  \n");			
		SQL.append("     AND sr.service_code = sl.service_code				  \n");
		SQL.append("     AND sl.domain_type = 'P' 							  \n");
		SQL.append("     AND sl.pon='Y') has_pon							  \n");
		SQL.append(" from B_ADDR_LKUP a, B_AREA b \n");
		SQL.append(" where serbdyno in ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) \n");
		SQL.append(" and bl_ind is null \n");
		SQL.append(" and addptype = 'I' \n");
		SQL.append(" and a.area_cd = b.area_cd \n");

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
				dto.setCoverEyeX(rs.getString("cover_eyex"));
				dto.setCoverPe(rs.getString("cover_pe"));
//				dto.setAddress(rs.getString("AREANAME") + rs.getString("BLDG_NAM"));
				dto.setHasPon(rs.getString("has_pon"));

				return dto;
			}
		};
		
		try {
			logger.debug("getFlatFloorBySB() @ AddressSearchDAO: " + SQL);
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
