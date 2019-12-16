package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.RemarkDTO;
import com.bomwebportal.ims.dto.ui.ImsPaymentUI;

public class GetSourceCodeDAO extends BaseDAO {
	protected final Log logger = LogFactory.getLog(getClass());
	

	public List<Map<String, String>> getSourceCode() throws DAOException
	{
		String SQL = 
			" select APP_MTHD.BOM_DESC APP_MTHD_DESC, SRC_DESC.BOM_DESC src_desc, APP_MTHD.BOM_CODE APP_MTHD_CODE ,SRC_DESC.BOM_CODE src_cd" +
			" from B_LOOKUP CC_APP_MTHD, B_LOOKUP APP_MTHD, B_APPLN_SRC APPLN_SRC, B_LOOKUP SRC_DESC" +
			" where CC_APP_MTHD.BOM_GRP_ID = 'SB_CC_APPL_MTH' and CC_APP_MTHD.BOM_STATUS='A' " +
			" and APP_MTHD.BOM_GRP_ID = 'APPLMTHD' and APP_MTHD.BOM_STATUS='A'" +
			" and SRC_DESC.BOM_GRP_ID = 'SOURCE' and SRC_DESC.BOM_STATUS='A'" +
			" and APPLN_SRC.applcode = APP_MTHD.BOM_CODE" +
			" and SRC_DESC.BOM_CODE = APPLN_SRC.SRCCODE " +
			" and APP_MTHD.BOM_CODE = CC_APP_MTHD.BOM_DESC" +
			" order by APP_MTHD_DESC,SRC_DESC";
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {
			
			public Map<String, String> mapRow(ResultSet rs, int rowNum)
			throws SQLException {
				Map<String, String> map = new HashMap<String, String>();				
				map.put("appMethod", rs.getString("APP_MTHD_DESC"));
				map.put("appMethodCode", rs.getString("APP_MTHD_CODE"));
				if ( rs.getString("SRC_DESC") != null 
						&& !"undefined".equalsIgnoreCase(rs.getString("SRC_DESC"))
						&& !"".equalsIgnoreCase(rs.getString("SRC_DESC"))
					)
					map.put("sourceCodeLabel", rs.getString("SRC_DESC"));
				else
					map.put("sourceCodeLabel", "---");
				
				map.put("sourceCodeValue", rs.getString("SRC_CD"));
				
		
				return map;
			}
		};
		
		
		try {
			logger.debug("getSourceCode: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate.query(SQL, mapper);
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getSourceCode()", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
	}
	
	public List<Map<String, String>> getSourceCode(String channel) throws DAOException
	{
//		String SQL = 
//			" select l.bom_desc, s.srccode from B_LOOKUP l, B_APPLN_SRC s " +
//			" where l.BOM_GRP_ID = 'APPLMTHD' " +
//			" and s.applcode = l.BOM_CODE " +
//			" and l.bom_desc in ('CSS','CFO','HPM')";
		
		String grp_id = "";
		
		if("CC".equalsIgnoreCase(channel)){
			grp_id = "SB_CC_APPL_MTH";
		}else if("CC_M".equalsIgnoreCase(channel)){
			grp_id = "SB_CC_APPL_MTH','SB_CC_M_APPL_MTH";
		}
		
		String SQL = 
			" select APP_MTHD.BOM_DESC APP_MTHD_DESC, SRC_DESC.BOM_DESC src_desc, APP_MTHD.BOM_CODE APP_MTHD_CODE ,SRC_DESC.BOM_CODE src_cd" +
			" from B_LOOKUP CC_APP_MTHD, B_LOOKUP APP_MTHD, B_APPLN_SRC APPLN_SRC, B_LOOKUP SRC_DESC" +
			" where CC_APP_MTHD.BOM_GRP_ID in ('"+grp_id+"') and CC_APP_MTHD.BOM_STATUS='A' " +
			" and APP_MTHD.BOM_GRP_ID = 'APPLMTHD' and APP_MTHD.BOM_STATUS='A'" +
			" and SRC_DESC.BOM_GRP_ID = 'SOURCE' and SRC_DESC.BOM_STATUS='A'" +
			" and APPLN_SRC.applcode = APP_MTHD.BOM_CODE" +
			" and SRC_DESC.BOM_CODE = APPLN_SRC.SRCCODE " +
			" and APP_MTHD.BOM_CODE = CC_APP_MTHD.BOM_DESC" +
			" order by APP_MTHD_DESC,SRC_DESC";
		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {
			
			public Map<String, String> mapRow(ResultSet rs, int rowNum)
			throws SQLException {
				Map<String, String> map = new HashMap<String, String>();
				
//				map.put("appMethod", rs.getString("bom_desc"));
//				map.put("sourceCode", rs.getString("srccode"));
				
				map.put("appMethod", rs.getString("APP_MTHD_DESC"));
				map.put("appMethodCode", rs.getString("APP_MTHD_CODE"));
				if ( rs.getString("SRC_DESC") != null 
						&& !"undefined".equalsIgnoreCase(rs.getString("SRC_DESC"))
						&& !"".equalsIgnoreCase(rs.getString("SRC_DESC"))
					)
					map.put("sourceCodeLabel", rs.getString("SRC_DESC"));
				else
					map.put("sourceCodeLabel", "---");
				
				map.put("sourceCodeValue", rs.getString("SRC_CD"));
				
		
				return map;
			}
		};
		
		
		try {
			logger.debug("getSourceCode: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate.query(SQL, mapper);
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getSourceCode()", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
	}
	
	
	public String getDeflaultAppMethod(String deflaultSourceCode) throws DAOException{
		logger.info("getDeflaultAppMethod is called");
		
		String deflaultAppMethod  = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			String SQL = "	select APP_MTHD.BOM_CODE APP_MTHD_CODE	             " +
						 "	     from B_LOOKUP CC_APP_MTHD,   " +
						 "       B_LOOKUP APP_MTHD, 	     " +
					     "	     B_APPLN_SRC APPLN_SRC,  " +
					     "		 B_LOOKUP SRC_DESC       " +
				         "       where CC_APP_MTHD.BOM_GRP_ID = 'SB_CC_APPL_MTH'  " +
					     "	     and CC_APP_MTHD.BOM_STATUS='A' 		 " +
					     "	 	 and APP_MTHD.BOM_GRP_ID = 'APPLMTHD' 			 " +
					     "	 	 and APP_MTHD.BOM_STATUS='A'		 " +
					     "	 	 and SRC_DESC.BOM_GRP_ID = 'SOURCE' 				 " +
					     "	 	and SRC_DESC.BOM_STATUS='A'	 " +
					     "	 	 and APPLN_SRC.applcode = APP_MTHD.BOM_CODE	 " +
					     "	 	and SRC_DESC.BOM_CODE = APPLN_SRC.SRCCODE  " +
					     "	 	and APP_MTHD.BOM_CODE = CC_APP_MTHD.BOM_DESC " +
					     "	 	and APPLN_SRC.OBSOLETE <> 'Y' " +
					     "		and APPLN_SRC.SRCCODE = ? ";
					
			
			
			deflaultAppMethod = simpleJdbcTemplate.queryForObject(SQL, String.class, deflaultSourceCode);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			deflaultAppMethod = null;
		}catch (IncorrectResultSizeDataAccessException erdae){
			logger.debug("IncorrectResultSizeDataAccessException");
			deflaultAppMethod = null;
		}catch (Exception e) {
				logger.error("Exception caught in getDeflaultSourceCode()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return deflaultAppMethod;
	}
	
	public String getDeflaultAppMethodRetry(String deflaultSourceCode) throws DAOException{
		logger.info("getDeflaultAppMethodRetry is called");
		
		String deflaultAppMethod  = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			String SQL = 	"	select CC_APP_MTHD.BOM_DESC 					" +
							"	from B_LOOKUP CC_APP_MTHD, B_LOOKUP CC_SRC 		" +
							"	where CC_SRC.BOM_GRP_ID = 'SB_IMS_CC_SRC' 		" +
							"	and CC_SRC.BOM_STATUS = 'A' 					" +
							"	and CC_APP_MTHD.BOM_GRP_ID = 'SB_CC_APPL_MTH' 	" +
							"	and CC_APP_MTHD.BOM_STATUS= 'A'              	" +
							"	and CC_APP_MTHD.BOM_CODE = CC_SRC.BOM_DESC		" +
							"	and CC_SRC.BOM_CODE = ?							" ;
					
				
			
			deflaultAppMethod = simpleJdbcTemplate.queryForObject(SQL, String.class, deflaultSourceCode);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			deflaultAppMethod = null;
		}catch (Exception e) {
				logger.error("Exception caught in getDeflaultSourceCode()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return deflaultAppMethod;
	}
	
	public List<RemarkDTO> getL2JobCode() throws DAOException {
		logger.info("getL2JobCode is called.");
		List<RemarkDTO> codeList = new ArrayList<RemarkDTO>();
		String SQL = "select BOM_CODE, BOM_DESC from B_LOOKUP where BOM_DESC like 'now TV cash deposit%'";
		ParameterizedRowMapper<RemarkDTO> mapper = new ParameterizedRowMapper<RemarkDTO>() {
			public RemarkDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				RemarkDTO dto = new RemarkDTO();
				dto.setDtlId(rs.getString("BOM_CODE"));
				dto.setRmkDtl(rs.getString("BOM_DESC"));
				return dto;
			}
		};
		try {
			logger.debug("getL2JobCode @ GetSourceCodeDAO: " + SQL);
			codeList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage(), e);
		}
		
		return codeList;
	}
	
	public void getDirectSalesAppMethod(ImsPaymentUI payment, String srcCd) throws DAOException {
		logger.info("getDirectSalesAppMethod is called");
		List<OrderImsDTO> oList = new ArrayList<OrderImsDTO>();
		String SQL = "select distinct app_mthd.bom_code appl_method_code, app_mthd.bom_desc appl_method_desc " +
						"from b_lookup cc_app_mthd, " +
						"b_lookup app_mthd, " +
						"b_appln_src appln_src, " +
						"b_lookup src_desc " +
						"where cc_app_mthd.bom_grp_id(+) = 'SB_CC_APPL_MTH' " +
						"and cc_app_mthd.bom_status(+)='A' " +
						"and app_mthd.bom_grp_id = 'APPLMTHD' " +
						"and app_mthd.bom_status='A' " +
						"and src_desc.bom_grp_id = 'SOURCE' " +
						"and src_desc.bom_status='A' " +
						"and appln_src.applcode = app_mthd.bom_code " +
						"and src_desc.bom_code = appln_src.srccode " +
						"and app_mthd.bom_code = cc_app_mthd.bom_desc(+) " +
						"and appln_src.obsolete <> 'Y' " +
						"and appln_src.srccode = ? and rownum =1";
		ParameterizedRowMapper<OrderImsDTO> mapper = new ParameterizedRowMapper<OrderImsDTO>() {
			public OrderImsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderImsDTO o = new OrderImsDTO();
				o.setAppMethod(rs.getString("appl_method_code"));
				o.setAppMethodDesc(rs.getString("appl_method_desc"));
				return o;
			}
		};
		try {
			logger.debug("getDirectSalesAppMethod @ GetSourceCodeDAO: " + SQL);
			oList = simpleJdbcTemplate.query(SQL, mapper, srcCd);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException(e.getMessage(), e);
		}
		if (oList != null && oList.size() > 0) {
			payment.setAppMethod(oList.get(0).getAppMethod());
			payment.setAppMethodDesc(oList.get(0).getAppMethodDesc());
		}
	}
	
	public String getRetailAppMethod(String deflaultSourceCode) throws DAOException{
		logger.info("getRetailAppMethod is called");
		
		String deflaultAppMethod  = null;
		
		try{
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate);
			
			String SQL = "	select APP_MTHD.BOM_CODE APP_MTHD_CODE	             " +
						 "	     from B_LOOKUP CC_APP_MTHD,   " +
						 "       B_LOOKUP APP_MTHD, 	     " +
					     "	     B_APPLN_SRC APPLN_SRC,  " +
					     "		 B_LOOKUP SRC_DESC       " +
				         "       where CC_APP_MTHD.BOM_GRP_ID = 'SB_RS_APPL_MTH'  " +
					     "	     and CC_APP_MTHD.BOM_STATUS='A' 		 " +
					     "	 	 and APP_MTHD.BOM_GRP_ID = 'APPLMTHD' 			 " +
					     "	 	 and APP_MTHD.BOM_STATUS='A'		 " +
					     "	 	 and SRC_DESC.BOM_GRP_ID = 'SOURCE' 				 " +
					     "	 	and SRC_DESC.BOM_STATUS='A'	 " +
					     "	 	 and APPLN_SRC.applcode = APP_MTHD.BOM_CODE	 " +
					     "	 	and SRC_DESC.BOM_CODE = APPLN_SRC.SRCCODE  " +
					     "	 	and APP_MTHD.BOM_CODE = CC_APP_MTHD.BOM_DESC " +
					     "	 	and APPLN_SRC.OBSOLETE <> 'Y' " +
					     "		and APPLN_SRC.SRCCODE = ? ";
					
			
			
			deflaultAppMethod = simpleJdbcTemplate.queryForObject(SQL, String.class, deflaultSourceCode);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			deflaultAppMethod = null;
		}catch (IncorrectResultSizeDataAccessException erdae){
			logger.debug("IncorrectResultSizeDataAccessException");
			deflaultAppMethod = null;
		}catch (Exception e) {
				logger.error("Exception caught in getRetailAppMethod()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return deflaultAppMethod;
	}
}
