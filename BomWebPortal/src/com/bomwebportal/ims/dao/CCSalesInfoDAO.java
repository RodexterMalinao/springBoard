package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.util.BomWebPortalConstant;

public class CCSalesInfoDAO extends BaseDAO {
	public List<String> getCCManagerTeamCds(String staffId) throws DAOException
	{
		List<String> list;
		try{
			String SQL = 
				" select distinct TEAM_CD from bomweb_sales_assignment " +
				" where channel_cd in " +
				" 	( select distinct channel_cd " +
				"		from bomweb_sales_assignment bsa " +
				"		where exists (select 1 from bomweb_sales_profile bsp where bsp.staff_id = bsa.staff_id and bsp.category = 'SALES MANAGER' ) " +
				"		and bsa.staff_id =  :staffId)";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("staffId", staffId);
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("TEAM_CD");
				}
			};
			list = simpleJdbcTemplate.query(SQL, mapper, params);
		}
		catch (EmptyResultDataAccessException erdae)
		{
			logger.debug("EmptyResultDataAccessException");
			list = null;
		}
		catch(Exception e) 
		{
			logger.error("Exception caught in getSalesNum()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return list;
	}
	
	public ImsServiceSrdDTO getPTServiceByStaffId (String staffId, String housingType, String PONOTAmt, String PONOTChrgType) throws DAOException
	{
		logger.info("getPTServiceByStaffId is called");
		logger.info("staffId:" + staffId);
		logger.info("housingType:" + housingType);
		logger.info("PONOTAmt:" + PONOTAmt);
		logger.info("PONOTChrgType:" + PONOTChrgType);
		
		ImsServiceSrdDTO result = new ImsServiceSrdDTO();
		
		try {			
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$CNM")
	        	.withCatalogName("PKG_SB_IMS_LTS")
	        	.withProcedureName("get_PT_service_by_staff") 
	        	.declareParameters(
	        			new SqlParameter("i_team", Types.VARCHAR),
						new SqlParameter("i_housing_type", Types.VARCHAR),
						new SqlParameter("i_ot_amt", Types.INTEGER),
						new SqlParameter("i_ot_chrg_type", Types.VARCHAR),
					
						new SqlOutParameter("o_is_allowed", Types.VARCHAR),
						new SqlOutParameter("o_reject_desc", Types.VARCHAR),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR)
	        					);
	        
	        MapSqlParameterSource inMap = new MapSqlParameterSource();
	        inMap.addValue("i_staff", staffId);
	        inMap.addValue("i_housing_type", housingType);
	        inMap.addValue("i_ot_amt", PONOTAmt);
	        inMap.addValue("i_ot_chrg_type", PONOTChrgType);
	        SqlParameterSource in = inMap;
	        
	        Map out = jdbcCall.execute(in);
	        
	        int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			logger.debug("getPTServiceByStaffId() output ret_val = " + ret_val);
			logger.debug("getPTServiceByStaffId() output error_code = " + error_code);
			logger.debug("getPTServiceByStaffId() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				result = null;
			} else {
				result.setIsAllowed((String)out.get("o_is_allowed"));
				result.setRejectDesc((String)out.get("o_reject_desc"));
				
				logger.info("getPTServiceByStaffId() output record = " + result.toString());
			}
			
			return result;
		}catch (Exception e) {
			logger.error("Exception caught in getPTServiceByStaffId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getAllowMassprojByStaff (String staffId, String housingType) throws DAOException
	{
		logger.info("getAllowMassprojByStaff is called");
		logger.info("staffId:" + staffId);
		logger.info("housingType:" + housingType);
		
		String result = null;
		
		try {			
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$CNM")
	        	.withCatalogName("PKG_SB_IMS_LTS")
	        	.withProcedureName("allow_massproj_by_staff") 
	        	.declareParameters(
	        			new SqlParameter("i_staff", Types.VARCHAR),
						new SqlParameter("i_housing_type", Types.VARCHAR),
					
						new SqlOutParameter("o_is_allowed", Types.VARCHAR),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR)
	        					);
	        
	        MapSqlParameterSource inMap = new MapSqlParameterSource();
	        inMap.addValue("i_staff", staffId);
	        inMap.addValue("i_housing_type", housingType);
	        SqlParameterSource in = inMap;
	        
	        Map out = jdbcCall.execute(in);
	        
	        int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			logger.debug("getAllowMassprojByStaff() output ret_val = " + ret_val);
			logger.debug("getAllowMassprojByStaff() output error_code = " + error_code);
			logger.debug("getAllowMassprojByStaff() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				result = null;
			} else {
				result = (String)out.get("o_is_allowed");
				
				logger.info("getAllowMassprojByStaff() output record = " + result);
			}
			
			return result;
		}catch (Exception e) {
			logger.error("Exception caught in getPTServiceByStaffId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
