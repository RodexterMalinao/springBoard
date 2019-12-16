package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.google.gson.Gson;
//steven created this java file, plz find steven if u find anything wrong
public class COrderDAO extends BaseDAO {
    private Gson gson = new Gson();

	public GetCOrderDTO getCOrder(String sub_pcd, String sub_nowtv, String tech, String custNum, String sb, String unit, String floor)
	throws DAOException {
		logger.debug("getCOrder(stor prod)@COrderDAO is called");

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$BOM").withCatalogName("pkg_oc_ims_sb")
			.withProcedureName("chk_onl_create_c_ord");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_subscribe_pcd",Types.VARCHAR), 
					new SqlParameter("i_subscribe_nowtv", Types.VARCHAR),
					new SqlParameter("i_technology", Types.VARCHAR),
					new SqlParameter("i_cust_num", Types.VARCHAR),
					new SqlParameter("i_service_boundary", Types.VARCHAR),
					new SqlParameter("i_unit", Types.VARCHAR),
					new SqlParameter("i_floor", Types.VARCHAR),
					new SqlOutParameter("o_create_c_order", Types.VARCHAR),
					new SqlOutParameter("o_reason", Types.VARCHAR),
					new SqlOutParameter("o_related_fsa", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_subscribe_pcd", sub_pcd);
			inMap.addValue("i_subscribe_nowtv", sub_nowtv);
			inMap.addValue("i_technology", tech);
			inMap.addValue("i_cust_num", custNum);
			inMap.addValue("i_service_boundary", sb);
			inMap.addValue("i_unit", unit);
			inMap.addValue("i_floor", floor);
			SqlParameterSource in = inMap;

			logger.debug("getCOrder input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.debug("getCOrder output:"+gson.toJson(out));

			String o_create_c_order = (String) out.get("o_create_c_order");
			String o_reason = (String) out.get("o_reason");
			String o_related_fsa = (String) out.get("o_related_fsa");

			GetCOrderDTO result = new GetCOrderDTO();
			result.setO_create_c_order(o_create_c_order);
			result.setO_reason(o_reason);
			result.setO_related_fsa(o_related_fsa);
			return result;

		} catch (Exception e) {
			logger.error("Exception caught in getCOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	


	public GetCOrderDTO getCOrderNowTVTechBW(String custNum, String sb, String unit, String floor)
	throws DAOException {
		logger.debug("getCOrderNowTVTechBW(stor prod)@COrderDAO is called");
		logger.info("custNum:"+custNum);
		logger.info("sb:"+sb);
		logger.info("unit:"+unit);
		logger.info("floor:"+floor);

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$BOM").withCatalogName("pkg_oc_ims_sb")
			.withProcedureName("is_c_order_nowTV_tech_bw");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_service_boundary", Types.VARCHAR),
					new SqlParameter("i_unit", Types.VARCHAR),
					new SqlParameter("i_floor", Types.VARCHAR),
					new SqlParameter("i_cust_num", Types.VARCHAR),
					new SqlOutParameter("o_is_create_c_order", Types.VARCHAR),
					new SqlOutParameter("o_related_fsa", Types.VARCHAR),
					new SqlOutParameter("o_tech", Types.VARCHAR),
					new SqlOutParameter("o_bw", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_cust_num", custNum);
			inMap.addValue("i_service_boundary", sb);
			inMap.addValue("i_unit", unit);
			inMap.addValue("i_floor", floor);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = -1;

			if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}

			String error_text = (String) out.get("gsErrText");
			int gnRetVal =  (Integer) out.get("gnRetVal");
			logger.info("OPS$getCOrderNowTV()  gnRetVal = "+ gnRetVal);
			logger.info("OPS$getCOrderNowTV()  error_code = "+ error_code);
			logger.info("OPS$getCOrderNowTV()  error_text = "+ error_text);
			//int temp =  (Integer) out.get("o_reason");
			String o_create_c_order = (String) out.get("o_is_create_c_order");
			String o_related_fsa = (String) out.get("o_related_fsa");
			String o_tech = (String) out.get("o_tech");
			String o_bw = (String) out.get("o_bw");

			logger.info("o_create_c_order:" + o_create_c_order);
			logger.info("o_related_fsa:" + o_related_fsa);
			logger.info("o_tech:" + o_tech);

			GetCOrderDTO result = new GetCOrderDTO();
			result.setO_create_c_order(o_create_c_order);
			result.setO_related_fsa(o_related_fsa);
			result.setO_tech(o_tech);
			if(o_bw!=null){
				result.setO_bw(Integer.valueOf(o_bw));
			}else{
				result.setO_bw(0);
			}
			return result;

		} catch (Exception e) {
			logger.error("Exception caught in getCOrderNowTVTechBW()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public String getHasNowtv(String docType, String docNum)
	throws DAOException {
		logger.debug("getHasNowtv(stor prod)@COrderDAO is called");
		logger.info("docType:"+docType);
		logger.info("docNum:"+docNum);

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$BOM").withCatalogName("pkg_oc_ims_sb")
			.withProcedureName("has_nowtv");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_id_doc_type", Types.VARCHAR),
					new SqlParameter("i_id_doc_num", Types.VARCHAR),
					new SqlOutParameter("o_result", Types.VARCHAR),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_id_doc_type", docType);
			inMap.addValue("i_id_doc_num", docNum);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = -1;

			if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}

			String error_text = (String) out.get("gsErrText");
			int gnRetVal =  (Integer) out.get("gnRetVal");
			logger.info("OPS$getCOrderNowTV()  gnRetVal = "+ gnRetVal);
			logger.info("OPS$getCOrderNowTV()  error_code = "+ error_code);
			logger.info("OPS$getCOrderNowTV()  error_text = "+ error_text);
			String o_is_existing_cust = (String) out.get("o_result");
			logger.info("o_is_existing_cust:" + o_is_existing_cust);
			return o_is_existing_cust;

		} catch (Exception e) {
			logger.error("Exception caught in getHasNowtv()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	//martin
	public List<GetCOrderDTO> getAvailFSAforNowTV(String sb, String unit, String floor, String custNum)
	throws DAOException {
		logger.debug("getAvailFSAforNowTV(stor prod)@COrderDAO is called");
		logger.info("sb:"+sb);
		logger.info("unit:"+unit);
		logger.info("floor:"+floor);
		logger.info("custNum:"+custNum);
		List<GetCOrderDTO> cList = new ArrayList<GetCOrderDTO>();
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$BOM").withCatalogName("pkg_oc_ims_sb")
			.withProcedureName("get_avail_fsa_for_nowTV");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_service_boundary", Types.VARCHAR),
					new SqlParameter("i_unit", Types.VARCHAR),
					new SqlParameter("i_floor", Types.VARCHAR),
					new SqlParameter("i_cust_num", Types.VARCHAR),
					new SqlOutParameter("fsa_cursor", OracleTypes.CURSOR, new CursorMapper()),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_service_boundary", sb);
			inMap.addValue("i_unit", unit);
			inMap.addValue("i_floor", floor);
			inMap.addValue("i_cust_num", custNum);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			int error_code = -1;

			if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}

			String error_text = (String) out.get("gsErrText");
			int gnRetVal =  (Integer) out.get("gnRetVal");
			logger.info("OPS$getAvailFSAforNowTV()  gnRetVal = "+ gnRetVal);
			logger.info("OPS$getAvailFSAforNowTV()  error_code = "+ error_code);
			logger.info("OPS$getAvailFSAforNowTV()  error_text = "+ error_text);
			
			List<Map<String, String>> result = (List<Map<String, String>>) out.get("fsa_cursor");
			
			for (Map<String, String> c: result) {
				GetCOrderDTO cOrder = null;
				String has_netvigator = c.get("HAS_NETVIGATOR");
				String has_eye = c.get("HAS_EYE");
				String technology = c.get("LINE_TYPE");
				
				if (!StringUtils.isEmpty(has_eye) && !StringUtils.isEmpty(has_netvigator)) {
					if ("N".equals(has_eye) && "N".equals(has_netvigator)) {
						break;
					}
					cOrder = new GetCOrderDTO();
					if ("Y".equals(has_eye) && "N".equals(has_netvigator)) {
						cOrder.setDesc(!"PON".equals(technology)?"EYE":technology+" EYE");
						cOrder.setO_reason("EYE");
					} else if ("N".equals(has_eye) && "Y".equals(has_netvigator)) {
						cOrder.setDesc(!"PON".equals(technology)?"PCD":technology+" PCD");
						cOrder.setO_reason("PCD");
					} else if ("Y".equals(has_eye) && "Y".equals(has_netvigator)) {
						cOrder.setDesc(!"PON".equals(technology)?"PCD + EYE":technology+" PCD + EYE");
						cOrder.setO_reason("BOTH");
					}
				} else {
					break;
				}
				
				try {
					cOrder.setO_bw(Integer.parseInt(c.get("DL_BW")));
				} catch (NumberFormatException ne) {
					logger.info("NumberFormatException @ getAvailFSAforNowTV");
					ne.printStackTrace();
				}
				cOrder.setO_create_c_order("N");
				cOrder.setO_related_fsa(c.get("SERVICE_ID"));
				cOrder.setO_tech(technology);
				
				cList.add(cOrder);
			}
		} catch (Exception e) {
			logger.info("Exception caught in getAvailFSAforNowTV()");
			e.printStackTrace();
		}
		
		return cList;
	}

	public String checkMultipleFSAUnderAcct(String acctNum)
	throws DAOException {
		logger.info("checkMultipleFSAUnderAcct@COrderDAO is called");

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$BOM").withCatalogName("pkg_ims_tap_and_go")
			.withProcedureName("isMultipleFSAUnderAcct");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
					new SqlParameter("i_acctNum",Types.VARCHAR), 
					new SqlOutParameter("o_hasMultipleFSA", Types.VARCHAR),
					new SqlOutParameter("o_errorCode", Types.INTEGER),
					new SqlOutParameter("o_errorDesc", Types.VARCHAR));			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_acctNum", acctNum);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);
			logger.debug("checkMultipleFSAUnderAcct input:"+gson.toJson(in));
			logger.debug("checkMultipleFSAUnderAcct output:"+gson.toJson(out));
			int error_code = -1;

			if (((Integer) out.get("o_errorCode")) != null) {
				error_code = ((Integer) out.get("o_errorCode")).intValue();
			}

			String error_text = (String) out.get("o_errorDesc");
			logger.info("OPS$isMultipleFSAUnderAcct() output error_code = "+ error_code);
			logger.info("OPS$isMultipleFSAUnderAcct() output error_text = "+ error_text);

			String result =(String) out.get("o_hasMultipleFSA");
			logger.info("o_hasMultipleFSA:" + result);
			
			return result;

		} catch (Exception e) {
			logger.error("Exception caught in checkMultipleFSAUnderAcct()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public String getNAcctByFSA (String fsa)
	throws DAOException {
		List<String> tempList = new ArrayList<String>();
				
		StringBuffer SQL =  new StringBuffer( 
				"  select b.acct_num " +
				"  from b_account b, b_account_service_assgn a " +
				"  where a.service_id = '"+fsa+"' " +
				"  and a.system_id = 'IMS' " +
				"  and a.eff_start_date is not null " +
				"  and a.eff_end_date is null " +
				"  and b.acct_num = a.acct_num " +
				"  and b.system_id = a.system_id " +
				"  and b.acct_type = 'N'");
				
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("acct_num");
				return tempResult;
			}
		};

	try {
		logger.debug("getNAcctByFSA : " + SQL);
		tempList = simpleJdbcTemplate.query(SQL.toString(), mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		tempList = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getNAcctByFSA():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return tempList.size()>0?tempList.get(0):null;
	}
	
	public final class CursorMapper implements RowMapper {
	    
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Map<String, String> map = new HashMap<String, String>();
	    	for(int i =1;i<=rs.getMetaData().getColumnCount();i++){
	    		map.put(rs.getMetaData().getColumnName(i), rs.getString(i));
	    	}
	        return map;
	    }
}
	
}
