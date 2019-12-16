package com.bomwebportal.ims.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.ims.dto.AmendmentModeDTO;
import com.bomwebportal.ims.dto.AppointmentDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsAutoSyncWQDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.exception.DAOException;
import com.google.gson.Gson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

public class ImsOrderAmendBomDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();
    
	public String getEqtReturnMethod(String ocid, String fsa) {
		String result="";
		String sql =
		" select decode(FIELD_VISIT_WITH_CHARGE||FIELD_VISIT_NO_CHARGE,'NN','self','YN','charge','NY','noCharge') eqt_return" +
		" from b_ord_srv a, b_ord_srv_ims_ext b " +
		" where a.dtl_id = b.dtl_id " +
		" and a.oc_id=b.oc_id" +
		" and a.oc_id = ? " +
		" and srv_id = ? ";
		try {
			result = simpleJdbcTemplate.queryForObject(sql, String.class, ocid, fsa);
		} catch (Exception e) {
			logger.info("Exception caught in isPonfilePonAndNot1G1G():", e);
			return "error";
		}
		logger.info("getEqtReturnMethod ocid:"+ocid+ " fsa:"+fsa+ " result:"+result);
		return result;
	}
    
    

	public Boolean isPonTo1G1GProfilePonAndNot1G1G(String orderId) {
		String result="";
		String sql =
		" select decode(count(*),0,'N','Y') " +
		" from bomweb_order_service_ims_tmp c, cservgrp a, l1product b " +
		" where a.csgrpid = fsa" +
		" and b.prd_cd = a.l1prod" +
		" and app_line_type = 'PON'" +
		" and to_char(l1dn_bw/1000) <> '2000' " +
		" and c.order_id = ? ";
		try {
			result = simpleJdbcTemplate.queryForObject(sql, String.class, orderId);
		} catch (Exception e) {
			logger.info("Exception caught in isPonfilePonAndNot1G1G():", e);
			return false;
		}
		logger.info("isPonfilePonAndNot1G1G orderId:"+orderId+ " result:"+result);
		if(result==null||"".equals(result)){
			return false;
		}
		return "Y".equals(result);
	}
	
	
	public Boolean isPonTo1G1GSpecialServiceCode(String orderId) {
		String result="";
		String sql =
		"  select decode(count(*),0,'N','Y') from bomweb_cust_addr_tmp e, b_service_rollout f, b_lookup g " +
		" where NVL(obsolete,'N')='N' " +
		" and bom_grp_id = 'RET_PON2_2X1000G'  " +
		" and f.service_code = g.ims_grp_id" +
		" and e.serbdyno = f.serbdyno" +
		" and order_id = ?" ;
		try {
			result = simpleJdbcTemplate.queryForObject(sql, String.class, orderId);
		} catch (Exception e) {
			logger.info("Exception caught in isPonTo1G1GSpecialServiceCode():", e);
			return false;
		}
		logger.info("isPonTo1G1GSpecialServiceCode orderId:"+orderId+ " result:"+result);
		if(result==null||"".equals(result)){
			return false;
		}
		return "Y".equals(result);
	}
	
	
	public Boolean isPonTo1G1GNewBuyBasket1G1G(String orderId) {
		String result="";
		String sql =
		" SELECT decode(count(*),0,'N','Y') " +
		" FROM ims_parm a, ims_prdassoc b , b_product_a c, BOMWEB_SUBSCRIBED_ITEM_tmp d" +
		" where parm_val in (select bom_code from b_lookup where bom_grp_id = 'SBIMS_1G1G')" +
		" and B.REL_PRD_ID = lpad(d.product_id,7,'0')" +
		" and a.prd_id = b.prd_id" +
		" and lpad(c.prod_id,7,'0') = B.REL_PRD_ID" +
		" and parm_typ_upr_desc ='L1 PRODUCT REQUIRED'" +
		" and order_id = ? ";
		try {
			result = simpleJdbcTemplate.queryForObject(sql, String.class, orderId);
		} catch (Exception e) {
			logger.info("Exception caught in isNewBuyBasket1G1G():", e);
			return false;
		}
		logger.info("isNewBuyBasket1G1G orderId:"+orderId+ " result:"+result);
		if(result==null||"".equals(result)){
			return false;
		}
		return "Y".equals(result);
	}
    
		
	public AppointmentDTO getSrdInfo(String orderId, String amendType) throws DAOException {
		logger.debug("ImsOrderAmendBomDAO getSrdInfo is called");

		logger.debug("getSrdInfo orderId = " + orderId);
		logger.debug("getSrdInfo amendType = " + amendType);
		
		
		AppointmentDTO result = new AppointmentDTO();

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$BOM")
					.withCatalogName("PKG_OC_IMS_SB")
					.withProcedureName("get_sb_oc_amend_detail")
					.declareParameters(
							new SqlParameter("i_order_id", Types.VARCHAR),
							new SqlParameter("i_amend_type", Types.VARCHAR),
							new SqlOutParameter("o_oc_id", Types.VARCHAR),
							new SqlOutParameter("o_fsa", Types.VARCHAR),
							new SqlOutParameter("o_cust_num", Types.VARCHAR),
							new SqlOutParameter("o_appnt_start_date",Types.TIMESTAMP),
							new SqlOutParameter("o_appnt_end_date",Types.TIMESTAMP),
							new SqlOutParameter("gnRetVal", Types.INTEGER),
							new SqlOutParameter("gnErrCode", Types.INTEGER),
							new SqlOutParameter("gsErrText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			inMap.addValue("i_amend_type", amendType);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);

			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}

			if (((Integer) out.get("gnRetVal")) != null) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}

			String error_text = (String) out.get("gsErrText");

			logger.debug("getSrdInfo() output ret_val = " + ret_val);
			logger.debug("getSrdInfo() output error_code = " + error_code);
			logger.debug("getSrdInfo() output error_text = " + error_text);
			
			result.setStorProErrorCode(error_code);
			result.setStorProErrorText(error_text);
			result.setStorProReturnValue(ret_val);

			result.setOrderId(orderId);
			result.setAppntStartDate((Timestamp) out.get("o_appnt_start_date"));
			result.setAppntEndDate((Timestamp) out.get("o_appnt_end_date"));
			Gson gson = new Gson();
			logger.info("getSrdInfo:"+gson.toJson(result));

			return result;

		} catch (Exception e) {
			logger.error("Exception caught in BomSRDApptDAO getSrdInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
	public String getBomLastestTechBySbId(String orderId, String sbType) throws DAOException {
		logger.debug("getBomLastestTechBySbId orderId:" + orderId+" sbType:" + sbType);
		String result = "";
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$BOM")
					.withCatalogName("pkg_CC_IMS_SB")
					.withProcedureName("getBomLastestTechBySbId")
					.declareParameters(
							new SqlParameter("i_sb_id", Types.VARCHAR),
							new SqlParameter("i_sb_type", Types.VARCHAR),
							new SqlOutParameter("o_latestTech", Types.VARCHAR),
							new SqlOutParameter("o_allow", Types.VARCHAR),
							new SqlOutParameter("o_errmsg", Types.VARCHAR),
							new SqlOutParameter("gnRetVal", Types.INTEGER),
							new SqlOutParameter("gnErrCode", Types.INTEGER),
							new SqlOutParameter("gsErrText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_sb_id", orderId);
			inMap.addValue("i_sb_type", sbType);
			SqlParameterSource in = inMap;
			logger.debug("getBomLastestTechBySbId human input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.debug("getBomLastestTechBySbId output:"+gson.toJson(out));
			if(!"0".equals((String) out.get("o_allow"))){
				logger.debug("not 0.equals((String) out.get(o_allow)");
				return "";
			}
			result = (String) out.get("o_latestTech");
			return result;
		} catch (Exception e) {
			logger.error("Exception caught in getBomLastestTechBySbId()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public List<HousingTypeDTO>  getHousingTypeByOrderID (String orderId)
	throws DAOException {
		logger.debug("getHousingTypeByOrderID orderId: " + orderId);
		
		String SQL = 	" select housing_type " +
				" from b_housing_type " +
				" where serbdyno in " +
				" (select serbdyno from bomweb_cust_addr_tmp where order_id='" + orderId +"')";
		
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  wq = new String ();
		    	wq = (rs.getString("housing_type"));
		        return wq;
		    }
		};
		
		List<HousingTypeDTO> housingTypeList = new ArrayList<HousingTypeDTO>();
		
    	List<String > housingTypeStringList = new ArrayList<String >();
		
		try {
			logger.debug("getHousingTypeByOrderID: " + SQL);
			housingTypeStringList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			housingTypeStringList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getHousingTypeByOrderID():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		for(int i = 0; i<housingTypeStringList.size();i++){
			HousingTypeDTO test = new HousingTypeDTO();
			test.setHousingType(housingTypeStringList.get(i));
			housingTypeList.add(test);
			logger.debug("housingType("+i+"):"+housingTypeStringList.get(i));
		}
		return housingTypeList;
	}
	
	public ImsAutoSyncWQDTO getPendingFromBOM (ImsAutoSyncWQDTO dto)
	throws DAOException {
		List<ImsAutoSyncWQDTO> orderIdList = new ArrayList<ImsAutoSyncWQDTO>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	select order_id, amend_seq, nature  from bomweb_amend_tmp " +
				" where status = 'Y' " +
				" and nature = :natureId " +
				" and order_ID in :SbIds  	" +
				" and amend_seq = :amendSeq " );
		
		params.addValue("SbIds", dto.getSbid());
		params.addValue("natureId", dto.getWqNatureID());
		params.addValue("amendSeq", dto.getAmendSeq());
		
		
		ParameterizedRowMapper<ImsAutoSyncWQDTO> mapper = new ParameterizedRowMapper<ImsAutoSyncWQDTO>() {
			public ImsAutoSyncWQDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsAutoSyncWQDTO tempResult = new ImsAutoSyncWQDTO();
				tempResult.setAmendSeq((String) rs.getString("amend_seq"));
				tempResult.setSbid((String) rs.getString("order_id"));
				tempResult.setWqNatureID((String) rs.getString("nature"));
				return tempResult;
			}
		};

	try {
//		logger.debug("getPendingCCAutoWQBOM : " + SQL);
		orderIdList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		orderIdList = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getPendingCCAutoWQBOM():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return orderIdList.size()>0?orderIdList.get(0):null;
	}
	
	
	public String lockBy (String orderId)
	throws DAOException {
		List<String> tempList = new ArrayList<String>();
				
		StringBuffer SQL =  new StringBuffer( 
				"	  select bso.in_user " +
				" from b_ord_request bor, bomweb_order_tmp bot, b_srv_ord bso" +
				" where bot.lob='IMS'" +
				" and bot.order_Id = '"+orderId+"'" +
				" and bor.req_id = bot.req_id" +
				" and bor.related_oc_id is not null and bor.status = '02'" +
				" and bor.related_oc_id=bso.oc_Id" +
				" and bso.in_user is not null ") ;
				
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("in_user");
				return tempResult;
			}
		};

	try {
		logger.debug("lockBy : " + SQL);
		tempList = simpleJdbcTemplate.query(SQL.toString(), mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		tempList = null;
	} catch (Exception e) {
		logger.debug("Exception caught in lockBy():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return tempList.size()>0?tempList.get(0):null;
	}
	
	public Boolean isL1Distributed (String orderId)
	throws DAOException {
		List<String> resultList = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	SELECT DECODE (COUNT (*), 0, 'N', 'Y') DISTRIBUTED " +
				"  FROM b_legacy_ord_dtl lod " +
				"  WHERE (   lod.oc_id = ( " +
				"              SELECT a.related_oc_id" +
				"                FROM b_ord_request a, bomweb_order_tmp b " +
				"               WHERE b.order_id = :orderid " +
				"                 AND a.req_id = b.req_id " +
				"                 AND a.status = '02') " +
				"        OR lod.oc_id = (SELECT ocid " +
				"                           FROM bomweb_order_tmp " +
				"                          WHERE order_id = :orderid) " +
				"       )" +
				"   AND lod.dtl_id = 1 " +
				"   AND lod.l1_ord_status = 'D' " );
		
		params.addValue("orderid", orderId);
		
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = new String();
				tempResult=(String) rs.getString("DISTRIBUTED");
				return tempResult;
			}
		};

	try {
		logger.debug("isL1Distributed key search: " + params.getValues());
		logger.debug("isL1Distributed : " + SQL);
		resultList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		resultList = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getPendingCCAutoWQBOM():", e);
		throw new DAOException(e.getMessage(), e);
	}	

	logger.debug("isL1Distributed : " + (resultList.size()>0?resultList.get(0):"resultList is null"));
		return resultList.size()>0?"Y".equals(resultList.get(0)):false;
	}
	
	public Boolean isEyeGroupAttach(String orderId) throws DAOException {
		logger.debug("checkIfNeedAppointment orderId: " + orderId);
		
		String SQL = "SELECT count(*) cnt FROM B_ORD_SRV " +
				" WHERE SRV_ID_TYPE <> 'F' " +
				" AND OC_ID IN " +
				" (select OCID from bomweb_order_tmp where order_id like '" + orderId +"')";
		
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  temp = new String ();
		    	temp = (rs.getString("cnt"));
		        return temp;
		    }
		};
		
    	List<String > tempList = new ArrayList<String >();
		
		try {
			logger.debug("isEyeGroupAttach SQL: " + SQL);
			tempList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList=null;
		} catch (Exception e) {
			logger.debug("Exception caught in isEyeGroupAttach():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if("0".equalsIgnoreCase(tempList.get(0))){
			logger.debug("orderId: " + orderId+" isEyeGroupAttach: false");
			return false;
		}else{
			logger.debug("orderId: " + orderId+" isEyeGroupAttach: true");
			return true;
		}
	}
	
	//Celia 20141011
	public List<String> getQCHousingType() throws DAOException {
		List<String> result = new ArrayList<String>();
		String SQL = " select bom_desc from b_lookup where bom_grp_id = 'SB_HOU_TYP_MAP'and bom_code not like '%PT%' ";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("bom_desc");
				return dto;
			}
		};
		try {
			logger.debug("getQCHousingType SQL: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getQCHousingType():", e);
			throw new DAOException(e.getMessage(), e);
		}	
			return result;
	}
	

	public boolean isBomDRC55(String orderId) throws DAOException{
		logger.debug("isBomDRC55 is called");
		String count="";
		String sql = 
		"SELECT COUNT (*) cnt" +
		"  FROM b_ord_request a, bomweb_order_tmp b, b_ord_appnt c" +
		" WHERE a.req_id = b.req_id" +
		"   AND b.LOB = 'IMS'" +
		"   AND c.delay_rea_cd = '55'" +
		"   AND b.order_id = ?" +
		"   AND c.oc_id(+) = a.related_oc_id";
		try {
			count = simpleJdbcTemplate.queryForObject(sql, String.class, orderId);
		} catch (Exception e) {
			logger.info("Exception caught in isBomDRC55():", e);

			throw new DAOException(e.getMessage(), e);
		}
		logger.info("isBomDRC55 count:"+count);
		return (count.equals("0")?false:true);
	}
	

	public String getBomSB(String orderId) throws DAOException{
		logger.info("getBomSB is called orderId:"+orderId);
		String resultSB="";
		String ocid=this.getOcidBySbid(orderId);

		if(ocid == null || "".equals(ocid)){
			logger.info("ocid not found");
			return null;
		}

		String findSBSQL =
		"SELECT addr.serbdyno" +
		"  FROM address addr, b_ord_srv_addr osa, b_ord_dtl od" +
		" WHERE od.oc_id = ?" +
		"   AND od.dtl_id = 1" +
		"   AND od.oc_id = osa.oc_id" +
		"   AND od.dtl_id = osa.dtl_id" +
		"   AND osa.io_ind NOT IN ('O')" +
		"   AND NVL (osa.mark_del_ind, 'N') = 'N'" +
		"   AND TO_NUMBER (osa.addr_id) = addr.addrid";
		try {
			resultSB = simpleJdbcTemplate.queryForObject(findSBSQL, String.class, ocid);
		} catch (Exception e) {
			logger.info("Exception caught in getBomSB():", e);
			return null;
		}
		logger.info("getBomSB:"+resultSB);
		if(resultSB==null||"".equals(resultSB)){
			return null;
		}
		return resultSB;
	}
	

	public String getOcidBySbid(String orderId) throws DAOException{
		logger.info("getOcidBySbid is called orderId:"+orderId);
		String ocid=null;
		String findOCIDsql1 = 
		"SELECT a.related_oc_id" +
		"  FROM b_ord_request a, bomweb_order_tmp b" +
		" WHERE b.order_id = ? AND a.req_id = b.req_id AND a.status = '02'";

		String findOCIDsql2 =
		"SELECT ocid" +
		"  FROM bomweb_order_tmp" +
		" WHERE order_id = ?";

		try {
			ocid = simpleJdbcTemplate.queryForObject(findOCIDsql1, String.class, orderId);
		} catch (Exception e) {
			logger.info("Exception caught in getOcidBySbid() findOCIDsql1:", e);
			ocid = null;
		}
		
		if(ocid == null || "".equals(ocid)){
			try {
				ocid = simpleJdbcTemplate.queryForObject(findOCIDsql2, String.class, orderId);
			} catch (Exception e) {
				logger.info("Exception caught in getOcidBySbid() findOCIDsql2:", e);
				ocid = null;
			}
		}
		if(ocid == null || "".equals(ocid)){
			logger.info("ocid not found");
			return null;
		}
		return ocid;
	}
	

	public Boolean isBomOrderSuspended(String orderId) throws DAOException{
		logger.info("isBomOrderSuspended is called orderId:"+orderId);
		String ocid=this.getOcidBySbid(orderId);
		if(ocid == null || "".equals(ocid)){
			logger.info("ocid not found");
			return false;
		}
		String cnt="";
		String findisBomOrderSuspendedSQL =
		"select count(*)  cnt    " +
		" from b_ord_latest_status bols  " +
		" where bols.oc_id=?    " +
		" and bols.dtl_id=1  " +
		" and nvl(bols.status,'00') <> '04' and nvl(bols.bom_status,'00') <> '06' " ;
		
		try {
			cnt = simpleJdbcTemplate.queryForObject(findisBomOrderSuspendedSQL, String.class, ocid);
		} catch (Exception e) {
			logger.info("Exception caught in isBomOrderSuspended():", e);
			return false;
		}
		logger.info("cnt:"+cnt);
		if("0".equals(cnt)){
			logger.info("Bom Order Suspended");
			return true;
		}
		logger.info("Bom Order NOT Suspended");
		return false;
	}
	
	public String auto_term_camp_order_amend(String orderId) throws DAOException{

		logger.info("auto_term_camp_order_amend [orderId]= "+orderId );
		String result ="";
		try {
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$BOM")
			.withCatalogName("pkg_CC_IMS_SB")
			.withProcedureName("auto_term_camp_order_amend");
			jdbcCall.setAccessCallParameterMetaData(false);
			jdbcCall.declareParameters(
			new SqlParameter("i_order_id", Types.VARCHAR),
			new SqlOutParameter("result", Types.VARCHAR),
			new SqlOutParameter("gnretval", Types.INTEGER),
			new SqlOutParameter("gnerrcode", Types.INTEGER),
			new SqlOutParameter("gserrtext", Types.VARCHAR));
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			logger.info("auto_term_camp_order_amend in:"+gson.toJson(in));	
			Map out = jdbcCall.execute(in); 
			logger.info("auto_term_camp_order_amend out:"+gson.toJson(out));				

			result = (String) out.get("result");
				
		} catch (Exception e) {
			throw new DAOException(e.getMessage(), e);
		}
		return result;
	}
		
	public AmendmentModeDTO getPreInstallAmendMode(String orderId) throws DAOException {
		logger.debug("ImsOrderAmendBomDAO getPreInstallAmendMode is called");

		logger.debug("getPreInstallAmendMode orderId = " + orderId);
		
		
		AmendmentModeDTO result = new AmendmentModeDTO();

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$BOM")
					.withCatalogName("PKG_OC_IMS_SB")
					.withProcedureName("get_preinstall_amend_mode")
					.declareParameters(
							new SqlParameter("i_order_id", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_opt_cancel", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_opt_appt", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_opt_update_cc", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_opt_login_id", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_opt_email", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_opt_FSamend", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_appt_SRD", Types.VARCHAR),
							new SqlOutParameter("o_is_amend_appt_TCOMM_DATE", Types.VARCHAR),
							new SqlOutParameter("gnRetVal", Types.INTEGER),
							new SqlOutParameter("gnErrCode", Types.INTEGER),
							new SqlOutParameter("gsErrText", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_order_id", orderId);
			SqlParameterSource in = inMap;

			Map out = jdbcCall.execute(in);

			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL;

			if (((Integer) out.get("gnErrCode")) != null) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}

			if (((Integer) out.get("gnRetVal")) != null) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}

			String error_text = (String) out.get("gsErrText");

			logger.debug("getPreInstallAmendMode() output ret_val = " + ret_val);
			logger.debug("getPreInstallAmendMode() output error_code = " + error_code);
			logger.debug("getPreInstallAmendMode() output error_text = " + error_text);
			
			result.setOrderId(orderId);
			result.setCancelOrder((String) out.get("o_is_amend_opt_cancel"));
			result.setAmendAppointment((String) out.get("o_is_amend_opt_appt"));
			result.setUpdateCreditCard((String) out.get("o_is_amend_opt_update_cc"));
			result.setChangeLoginId((String) out.get("o_is_amend_opt_login_id"));
			result.setUpdateContactEmail((String) out.get("o_is_amend_opt_email"));
			result.setFsAmendment((String) out.get("o_is_amend_opt_FSamend"));
			result.setChangeSRD((String) out.get("o_is_amend_appt_SRD"));
			result.setChangeCommDate((String) out.get("o_is_amend_appt_TCOMM_DATE"));
			
			Gson gson = new Gson();
			logger.info("getPreInstallAmendMode:"+gson.toJson(result));

			return result;

		} catch (Exception e) {
			logger.error("Exception caught in BomSRDApptDAO getPreInstallAmendMode()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public Boolean isRemakeAppointmentNeeded(String orderId) throws DAOException{
		logger.info("isRemakeAppointmentNeeded is called orderId:"+orderId);
		
		String cnt = "";

		String SQL =
		" select count(*) FROM B_SB_ALERT_REQ where alert_type = 'RA' and parm_2 = ? " ;
		
		
		try {
			cnt = simpleJdbcTemplate.queryForObject(SQL, String.class, orderId);
		} catch (Exception e) {
			logger.error("Exception caught in isBomOrderSuspended():", e);
			return false;
		}
		logger.info("alert_type RA count: " + cnt);
		if("0".equals(cnt)){
			logger.info("No Need Remake Appointment");
			return false;
		}
		logger.info("Need Remake Appointment");
		return true;
	}
}

