package com.bomwebportal.lts.dao.bom;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.order.BomOrderAppntDTO;

public class BomOrderAppointmentDAO extends BaseDAO {

	
	private static final String SQL_GET_BOM_APPNT = 
		"SELECT OC_ID, DTL_ID, TO_FROM_SIDE, " +
		" APPNT_START_TIME, APPNT_END_TIME, APPNT_TYPE, " +
		" DELAY_REA_CD, BOM_DESC DELAY_REA_DESC, B_ORD_APPNT.LAST_UPD_DATE " +
		" FROM B_ORD_APPNT, B_LOOKUP  " +
		" WHERE OC_ID = ? " +
		" AND DTL_ID = ? "  +
	    " AND B_LOOKUP.BOM_GRP_ID(+) = 'DELAY_REASON' " + 
	    " AND B_LOOKUP.BOM_CODE(+)   = B_ORD_APPNT.DELAY_REA_CD";		
	
	public List<BomOrderAppntDTO> getBomAppointment(String ocId, String dtlId) throws DAOException {
		
		ParameterizedRowMapper<BomOrderAppntDTO> mapper = new ParameterizedRowMapper<BomOrderAppntDTO>() {
			public BomOrderAppntDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				BomOrderAppntDTO appnt = new BomOrderAppntDTO();
				appnt.setOcId(rs.getString("OC_ID"));
				appnt.setDtlId(rs.getString("DTL_ID"));
				appnt.setAppntStartTime(rs.getTimestamp("APPNT_START_TIME"));
				appnt.setAppntEndTime(rs.getTimestamp("APPNT_END_TIME"));
				appnt.setAppntType(rs.getString("APPNT_TYPE"));
				appnt.setToFromSide(rs.getString("TO_FROM_SIDE"));
				appnt.setDelayReaCd(rs.getString("DELAY_REA_CD"));
				appnt.setDelayReaDesc(rs.getString("DELAY_REA_DESC"));
				appnt.setLastUpdDate(rs.getTimestamp("LAST_UPD_DATE"));
				return appnt;
			}
		};

		try {
			return simpleJdbcTemplate.query(SQL_GET_BOM_APPNT, mapper, ocId, dtlId);
		} catch (Exception e) {
			logger.error("Error in getStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
}
