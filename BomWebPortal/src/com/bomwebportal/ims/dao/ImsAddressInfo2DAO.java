package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;


public class ImsAddressInfo2DAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());

	public List<String> getOsOrderSB(String sbNum, String floor, String unitNo) throws DAOException{
		
		logger.debug("getOsOrderSB is called");

		List<String> osOrderList = new ArrayList<String>();
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" select bo.order_id \n");
		SQL.append(" from BOMWEB_ORDER bo, BOMWEB_CUST_ADDR bua \n");
		SQL.append(" where bo.lob='IMS' \n");
		SQL.append(" and bua.order_id=bo.order_id \n");
		SQL.append(" and bua.addr_usage='IA' \n");
		SQL.append(" and bua.serbdyno=? \n");
		SQL.append(" and nvl(bua.floor_no,' ')=nvl(?,' ') \n");
		SQL.append(" and nvl(bua.unit_no,' ')=nvl(?,' ') \n");
		SQL.append(" and bo.order_status in \n");
		SQL.append("  (select wcl.code From W_CODE_LKUP wcl where wcl.grp_id='SB_IMS_ACQ_PENDING') \n");

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("ORDER_ID");

				return dto;
			}
		};
		
		try {
			logger.debug("getOsOrderSB() @ ImsAddressInfoDAO: " + SQL);
			osOrderList = simpleJdbcTemplate.query(SQL.toString(), mapper, sbNum, floor, unitNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOsOrderSB()");

			osOrderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOsOrderSB():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return osOrderList;
		
	}

	
	public boolean isShopCodeValid(String shopCode) throws DAOException{
		 
		logger.debug("isShopCodeValid is called");
		
		String count="";
		
		String sql = 
		"SELECT COUNT (shop_cd) cnt" +
		"  FROM bomweb_shop" +
		" WHERE pilot_status = 'A' AND shop_cd = ?";

		try {
			count = simpleJdbcTemplate.queryForObject(sql, String.class, shopCode);
		} catch (Exception e) {
			logger.info("Exception caught in isShopCodeValid():", e);

			throw new DAOException(e.getMessage(), e);
		}

		logger.info("isShopCodeValid count:"+count);
		return (count.equals("0")?false:true);
		
	}
	
	//IMS CPQ Roadshow List 20150907 
	public  Map<String, String>  getAddressDistrictList() throws SQLException{
		
		Map<String, String> rtnMap = new HashMap<String, String>();
		
		String SQL = "select location_cd, DESC_ENG from ds_location where (eff_end_date is null or trunc(sysdate)<=eff_end_date) and sysdate>eff_start_date  order by desc_eng ";

		ParameterizedRowMapper<Map<String, String>> mapper = new ParameterizedRowMapper<Map<String, String>>() { 

			public Map<String, String> mapRow(ResultSet rs, int rowNum)	throws SQLException {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("code", rs.getString("location_cd"));
				map.put("desc", rs.getString("DESC_ENG"));

				return map;
			}
		};

		try {
			logger.debug("getAddressDistrictList @ ImsAddressSearchDAO: " + SQL);
			List<Map<String, String>> map = simpleJdbcTemplate
					.query(SQL, mapper);
			
			for (Map<String, String> m:map) {
				rtnMap.put(m.get("code"), m.get("desc"));
			}
			
			
			return rtnMap;
		
		}catch (EmptyResultDataAccessException erdae) {
			return null;
		}catch (Exception e) {
			logger.error("Exception caught in getAddressDistrictList()", e);
			throw new SQLException(e.getMessage(), e);
		}		
	}
	public String cslMobileNumPCDCheck(String mrt,String orderId) throws DAOException{
		logger.info("cslMobileNumPCDCheck: " + "mrt:"+mrt);

		String hasNonCslPCDInd;
		try {

			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$CNM")
			.withCatalogName("PKG_IMS_ORDER")
			.withProcedureName("has_noncxl_pcdacq_cslord");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
			new SqlParameter("i_mrt", Types.VARCHAR),
			new SqlParameter("i_orderId", Types.VARCHAR),
			new SqlOutParameter("o_value", Types.VARCHAR),
			new SqlOutParameter("gnRetVal", Types.INTEGER),
			new SqlOutParameter("gnErrCode", Types.INTEGER),
			new SqlOutParameter("gsErrText", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_mrt", mrt);
			inMap.addValue("i_orderId", orderId);

			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);

			hasNonCslPCDInd = (String) out.get("o_value");
			
			logger.info("has_noncxl_pcdacq_cslord [hasNonCslPCDInd]= "+hasNonCslPCDInd );
			
			int errcode=0;
			if (((Integer) out.get("gnErrCode"))!= null ) {
				errcode = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			String errorText = (String) out.get("gsErrText");
			logger.info("has_noncxl_pcdacq_cslord output [errcode]= "+errcode +" [errorText]"+ errorText);
				
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		logger.info("has_noncxl_pcdacq_cslord: " + hasNonCslPCDInd);
		return hasNonCslPCDInd;
	}
	
	public String getCslOfferEnableInd(String user) throws DAOException{
		
		logger.debug("getCslOfferEnableInd is called");
		
		String count="";
		
		String sql = 
			 " select count(*) cnt from dual " +
			 "where  "+
			 "exists ( "+
			 "select * from bomweb_saleS_assignment bsa, bomweb_shop bs where bsa.team_cd = bs.SHOP_CD and bsa.channel_id = 1 and bs.brand  in (sELECT code FROM w_code_lkup WHERE grp_id='IMS_CSL_RETAIL') and bsa.staff_id = ?  AND BSA.END_DATE IS NULL "+
			 ")  "+
			 "or exists ( "+
			 "select * from bomweb_saleS_assignment bsa where bsa.channel_id in (2,3) and bsa.channel_cd in (sELECT code FROM w_code_lkup WHERE grp_id='IMS_CSL_CC') and bsa.staff_id = ?  AND BSA.END_DATE IS NULL "+
			 ") ";


		try {
			count = simpleJdbcTemplate.queryForObject(sql, String.class, user,user);
			
		} catch (Exception e) {
			logger.info("Exception caught in getCslOfferEnableInd():", e);

			throw new DAOException(e.getMessage(), e);
		}

		logger.info("getCslOfferEnableInd count:"+count);
		
		return (count.equals("0")?"N":"Y");
		
	}
	
	public String getCslShopCustInd(String user) throws DAOException{
		
		logger.debug("getCslShopCustInd is called");
		
		String count="";
		
		String sql = 
			 " select count(*) cnt from dual " +
			 "where  "+
			 "exists ( "+
			 "select * from bomweb_saleS_assignment bsa, bomweb_shop bs where bsa.team_cd = bs.SHOP_CD and bsa.channel_id = 1 and bs.brand  in (sELECT code FROM w_code_lkup WHERE grp_id='IMS_CSL_RETAIL' and description = 'CSL_SHOP') and bsa.staff_id = ?  AND BSA.END_DATE IS NULL "+
			 ")  "+
			 "or exists ( "+
			 "select * from bomweb_saleS_assignment bsa where bsa.channel_id in (2,3) and bsa.channel_cd in (sELECT code FROM w_code_lkup WHERE grp_id='IMS_CSL_CC') and bsa.staff_id = ?  AND BSA.END_DATE IS NULL "+
			 ") ";


		try {
			count = simpleJdbcTemplate.queryForObject(sql, String.class, user,user);
			
		} catch (Exception e) {
			logger.info("Exception caught in getCslShopCustInd():", e);

			throw new DAOException(e.getMessage(), e);
		}

		logger.info("getCslShopCustInd count:"+count);
		
		return (count.equals("0")?"N":"Y");
		
	}
	
	public String getBypassLtsCheckInd() throws DAOException {

		String sql = "select description from w_code_lkup where grp_id='BYPASS_LTS_SRV_CHECKING' and code='BYPASS_LTS_SRV_CHECKING'  ";
	
		List<String> resultList = new ArrayList<String>();
		String result ="";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {

			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				return rs.getString("DESCRIPTION");
			}
		};

		try {
			resultList = simpleJdbcTemplate.query(sql, mapper);
			if(resultList!=null&&resultList.size()>0)
				result=resultList.get(0);			
		} catch (EmptyResultDataAccessException erdae) {
			throw new AppRuntimeException(erdae);
		} catch (Exception e) {
			logger.error("Exception caught in getBypassLtsCheckInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
		return result;
		
	}
	
	
}
