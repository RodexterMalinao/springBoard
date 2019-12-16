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
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;


public class ImsReport1DAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	public String getQosMeasureInd (String prodIdStr)
			throws DAOException {
		prodIdStr = prodIdStr.replace(",","','");
		
		int cnt = 0;
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT COUNT(*)  															\n");
		SQL.append(" FROM   IMS_VAS iv 															\n");
		SQL.append(" WHERE  iv.PRD_ID in ('" + prodIdStr + "') 									\n");
		SQL.append(" AND    iv.VAS_TYPE IN (SELECT BOM_DESC FROM B_LOOKUP WHERE BOM_GRP_ID='SBIMS_NE_QOS') 		\n");

		try {
			logger.debug("Prod ID:" + prodIdStr);
			logger.debug("getQosMeasureInd() @ ImsReport1DAO: " + SQL);
			cnt = simpleJdbcTemplate.queryForInt(SQL.toString());

		} catch (Exception e) {
			logger.info("Exception caught in getQosMeasureInd():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return (cnt>0?"Y":"N");
	}

	public String getAppMethod (String code)
	throws DAOException {
		
		List<String> result = new ArrayList<String>();
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT BOM_DESC                             " +
				" FROM B_LOOKUP APP_MTHD" +
				" WHERE APP_MTHD.BOM_GRP_ID = 'APPLMTHD' " +
				" AND APP_MTHD.BOM_STATUS='A' " +
				" AND BOM_CODE = '"+code+"'     " +
						" and rownum =1 ");

		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	String basketItem = "";
	        	basketItem = (rs.getString("BOM_DESC"));
	            return basketItem;
	        }
	    };
		
		try {
			logger.debug("getAppMethod: " + SQL);
			result = simpleJdbcTemplate.query(SQL.toString(),mapper);
		
		} catch (Exception e) {
			logger.info("Exception caught in getAppMethod():", e);
		
			throw new DAOException(e.getMessage(), e);
		}
		
		return result.size()>0?result.get(0):null;
		}
		
	public List<Map<String, String>> getImsLookUpCode(String GrpId) throws DAOException{
		logger.debug("getImsLookUpCode");
		String SQL = "	SELECT   bom_grp_id, bom_code, bom_desc	"+
		"	    FROM b_lookup	"+
		"	   WHERE bom_grp_id = ?	"+
		"	ORDER BY bom_code ";

		
		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() {

			public Map<String, String> mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("grp_id", rs.getString("bom_grp_id"));
				map.put("code", rs.getString("bom_code"));
				map.put("description", rs.getString("bom_desc"));
				
				return map;
			}
		};
		
		try {
			logger.debug("getImsLookUpCode @ ImsReport1DAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper, GrpId);
			
			return map;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getImsLookUpCode()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	public String getCOP_Service_Plan_Remark(String offerCd , String locale) throws DAOException{
		
	List<String> result = new ArrayList<String>();
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(  
				  "select decode('"+locale+"','en',srv_plan_rmk_eng,srv_plan_rmk_chi) srv_plan_rmk "+
				  "from b_offer_a a, b_offer_rmk_assgn_a  b	"+
				  "where a.offer_id = b.offer_id and a.offer_cd = '"+offerCd+"'");

		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
	        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	String basketItem = "";
	        	basketItem = (rs.getString("srv_plan_rmk"));
	            return basketItem;
	        }
	    };
		
		try {
			logger.debug("getAppMethod: " + SQL);
			result = simpleJdbcTemplate.query(SQL.toString(),mapper);
		
		} catch (Exception e) {
			logger.info("Exception caught in getAppMethod():", e);
		
			throw new DAOException(e.getMessage(), e);
		}
		
		return result.size()>0?result.get(0):null;
		}
}

