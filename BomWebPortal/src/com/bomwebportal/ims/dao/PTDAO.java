package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.BomwebOTDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.google.gson.Gson;

public class PTDAO extends BaseDAO {
	
	private Gson gson = new Gson();
	
	
	public int getSalesNum(String code)throws DAOException
	{
		int salesNum = 0;
		try{
			String SQL = 
				" select DESCRIPTION from w_code_lkup " +
				" where GRP_ID = 'SB_IMS_SALES_CONTACT' " +
				" and CODE = :code" +
				"";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("code", code);
			salesNum = simpleJdbcTemplate.queryForInt(SQL, params);
		}
		catch (EmptyResultDataAccessException erdae)
		{
			logger.debug("EmptyResultDataAccessException");
			salesNum = 0;
		}
		catch(Exception e) 
		{
			logger.error("Exception caught in getSalesNum()", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		return salesNum;
		
	}
	
	public List<Map<String, String>> getNowTVPTList() throws DAOException
	{
		List<Map<String, String>> list = null;
		
		String SQL = 
			"	select code, description" +
			"		from w_code_lkup" +
			"		where grp_id = 'SBIMS_PT_TV_LIST'";
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {
			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				Map<String, String> item = new HashMap<String, String>();
				item.put("code", rs.getString("code"));
				item.put("description", rs.getString("description"));

				return item;
			}
		};
		
		try {
			list = simpleJdbcTemplate.query(SQL, mapper);
			logger.info(gson.toJson(list));
			return list;
			
		} catch (Exception e) {
			logger.debug("Exception caught in getNowTVPTList():", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public List<BomwebOTDTO> getOverrideOTC(String staffId, String application, String prodType, String housingType, String sbno, String orderType, String fsaMarker)throws DAOException {
		logger.debug("getOverrideISFASF is called");
		
		List<BomwebOTDTO> result = new ArrayList<BomwebOTDTO>();
				
		try {			
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("ops$cnm")
	        	.withCatalogName("pkg_SB_IMS_LTS")
	        	.withProcedureName("get_override_install_ot")  
	        	.declareParameters(
	        			new SqlParameter("i_staff_id", Types.VARCHAR),
						new SqlParameter("i_application", Types.VARCHAR),
						new SqlParameter("i_prod_type", Types.VARCHAR),
						new SqlParameter("i_housing_type", Types.VARCHAR),
						new SqlParameter("i_serbdyno", Types.VARCHAR),
						new SqlParameter("i_ord_type", Types.VARCHAR),
						new SqlParameter("i_fsa_marker", Types.VARCHAR),
						new SqlOutParameter("o_install_detail_cur", OracleTypes.CURSOR, new BomwebOTMapper()),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
	       
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_staff_id", staffId);
			inMap.addValue("i_application", application);
			inMap.addValue("i_prod_type", prodType);
			inMap.addValue("i_housing_type", housingType);
			inMap.addValue("i_serbdyno", sbno);
			inMap.addValue("i_ord_type", orderType);
			inMap.addValue("i_fsa_marker", fsaMarker);

			SqlParameterSource in = inMap;
			logger.debug("getOverrideISFASF human input:"+gson.toJson(in));			
			Map out = jdbcCall.execute(in);
			logger.debug("getOverrideISFASF human output:"+gson.toJson(out));
	        
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				result = null;
			} else {
				result = ((List) out.get("o_install_detail_cur"));
			
				logger.debug("getOverrideISFASF() output record = " + gson.toJson(result));
			}
			
	    	return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in getAddressInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}
