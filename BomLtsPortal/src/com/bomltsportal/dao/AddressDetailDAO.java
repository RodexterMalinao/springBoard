package com.bomltsportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

public class AddressDetailDAO extends BaseDAO {

	private static String SQL_GET_BL_ADDR_IND = 
			"select bl_ind " +
			"from b_addr_lkup " +
			"where serbdyno = :serbdyno";
	
	private static String SQL_FILTER_EYE_CONSUMER_SB = 
			"select distinct srvbdry_num as sb_no " + 
			"from b_lts_srvroll  " + 
			"where service_type in ('00', '02', '03', '04') " + 
			"and srvbdry_num in (:sbList)";
	
	
	public boolean isBlacklistListLtsAddress(String pFlat, String pFloor, String pServiceBoundary) throws DAOException {
		
		StringBuffer sqlSb = new StringBuffer(SQL_GET_BL_ADDR_IND);
		Map<String, String> parmMap = new HashMap<String, String>();
		parmMap.put("serbdyno", pServiceBoundary);
		
		if (StringUtils.isNotBlank(pFlat)) {
			sqlSb.append(" and apfltun = :apfltun");
			parmMap.put("apfltun", pFlat);
		}
		if (StringUtils.isNotBlank(pFloor)) {
			sqlSb.append(" and flr_num = :flr_num");
			parmMap.put("flr_num", pFloor);
		}
		try {
			return StringUtils.equalsIgnoreCase("B", simpleJdbcTemplate.queryForObject(sqlSb.toString(), String.class, parmMap));
		}  catch (EmptyResultDataAccessException erdae) {
			return false;
		} catch (Exception e) {
			logger.error("Error in isBlacklistListAddress() SB: " + pServiceBoundary + " Flat: " + pFlat + " Floor: " + pFloor, e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String[] getServiceBoundaryByBuildCoordinate(String pFlat, String pFloor, String pBuildxy, String pSrvType) throws DAOException {

		
		String system = StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, pSrvType) ? BomLtsConstant.SERVICE_TYPE_EYE
				: BomLtsConstant.SERVICE_TYPE_TEL;
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$BOM")
				.withCatalogName("PKG_OC_IMS_SB")
				.withProcedureName("get_sb_by_flat_floor_xy_by_sys")
				.declareParameters(
						new SqlParameter("i_flat", Types.VARCHAR),
						new SqlParameter("i_floor", Types.VARCHAR),
						new SqlParameter("i_build_xy", Types.VARCHAR),
						new SqlParameter("i_system", Types.VARCHAR),
						new SqlOutParameter("service_detail_cursor", OracleTypes.CURSOR, new SbMapper()),
						new SqlOutParameter("gnretval", Types.INTEGER),
						new SqlOutParameter("gnerrcode", Types.INTEGER),
						new SqlOutParameter("gserrtext", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_flat", pFlat);
		inMap.addValue("i_floor", pFloor);
		inMap.addValue("i_build_xy", pBuildxy);
		inMap.addValue("i_system", system);
		Map<String, Object> out = null;
		
		try {
			logger.debug("Calling get_sb_by_flat_floor_xy_by_sys [i_flat:"+pFlat+" i_floor:"+pFloor+" i_build_xy:"+pBuildxy+" i_system:"+ system +"]");
			
			out = jdbcCall.execute(inMap);

			if (out != null) {
				logger.debug("Finish get_sb_by_flat_floor_xy_by_sys [i_flat:"
						+ pFlat
						+ " i_floor:"
						+ pFloor
						+ " i_build_xy:"
						+ pBuildxy
						+ " i_system:"
						+ system
						+ "] "
						+ " [gnretval:"
						+ ((Integer) out.get("gnretval") + " gserrtext:"
								+ ((String) out.get("gserrtext")) + "]"));
			}
			
		} catch (Exception e) {
			if (out != null && (Integer)out.get("gnretval") != 0) {
				logger.error("Exception caught in getServiceBoundaryByBuildCoordinate[i_flat:"+pFlat+" i_floor:"+pFloor+" i_build_xy:"+pBuildxy+" i_system:"+ system +"] " + ((String)out.get("gserrtext")));
				logger.error(ExceptionUtils.getFullStackTrace(e));
				return new String[] {BomLtsConstant.FAIL_TO_GET_SRVBDRY_IND, ((String)out.get("gserrtext"))};
			}
			else {
				logger.error("Exception caught in getServiceBoundaryByBuildCoordinate" + "[i_flat:"+pFlat+" i_floor:"+pFloor+" i_build_xy:"+pBuildxy+" i_system:"+ system +"]", e);
				throw new DAOException(e.getMessage(), e);	
			}
		}
		
		if (out == null || (Integer)out.get("gnretval") != 0) {
			return new String[] {BomLtsConstant.FAIL_TO_GET_SRVBDRY_IND, ((String)out.get("gserrtext"))};
		}
		
		List<String> sbList = (List<String>)out.get("service_detail_cursor");

		return sbList.size() == 0 ? null : sbList.toArray(new String[sbList.size()]);
	}
	
	public String[] filterSrvBdryForEyeConsumer(String[] pSrvBdrys) throws DAOException  {

		List<String> sbList = Arrays.asList(pSrvBdrys);

		try {
			List<String> resultSbList = simpleJdbcTemplate.query(SQL_FILTER_EYE_CONSUMER_SB, new SbMapper(), Collections.singletonMap("sbList", sbList));
			return resultSbList.size() == 0 ? null : resultSbList.toArray(new String[resultSbList.size()]);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		} catch (Exception e) {
			logger.error("Error in filterSrvBdryForEyeConsumer()", e);
			throw new DAOException(SQL_FILTER_EYE_CONSUMER_SB + e.getMessage(), e);
		}
	}
	
	private class SbMapper implements ParameterizedRowMapper<String> {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("SB_NO");
		}
	};
}