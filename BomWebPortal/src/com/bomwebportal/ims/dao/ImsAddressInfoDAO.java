package com.bomwebportal.ims.dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;

import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.ims.dto.ImsBlacklistCustInfoDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.PaymentDTO;
import com.bomwebportal.ims.dto.SbRemarksDTO;
import com.bomwebportal.ims.dto.UimBlockageDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.dto.OnlineSalesRequestDTO;

import com.bomwebportal.util.BomWebPortalConstant;
import com.google.gson.Gson;

import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;


public class ImsAddressInfoDAO extends BaseDAO{
	
	protected final Log logger = LogFactory.getLog(getClass());

    private Gson gson = new Gson();
	public Date getResrcShortAppntDt(String sbno, String tech, String isPccwLn) throws DAOException{
		logger.debug("getResrcShortAppntDt");
		try{
			
			Date appt;
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_SB")
	        	.withProcedureName("GET_RESRC_SHORT_APPNT_DT")
	        	.declareParameters(
	        			new SqlParameter("i_service_boundary", Types.VARCHAR),
	        			new SqlParameter("i_technology", Types.VARCHAR),
	        			new SqlParameter("i_is_pccw_fixed_line", Types.VARCHAR),
	        			new SqlOutParameter("o_leadtime", Types.INTEGER),
	        			new SqlOutParameter("o_appnt_date", Types.DATE),
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
			 
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_service_boundary", sbno);
			inMap.addValue("i_technology", tech);
			inMap.addValue("i_is_pccw_fixed_line", isPccwLn);			
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
			
			
			logger.debug("getServiceSrd() output ret_val = " + ret_val);
			logger.debug("getServiceSrd() output error_code = " + error_code);
			logger.debug("getServiceSrd() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				appt = null;
			} else {
				logger.debug("getServiceSrd() output o_appnt_date = " + (Date)out.get("o_appnt_date"));
				logger.debug("getServiceSrd() output o_leadtime = " + (Integer)out.get("o_leadtime"));
				
				appt = (Date)out.get("o_appnt_date");							
				
			}
	        
			return appt;
			
		} catch (Exception e) {
			logger.error("Exception caught in getResrcShortAppntDt()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	//public ImsServiceSrdDTO getServiceSrd(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno)throws DAOException {
	public ImsServiceSrdDTO getServiceSrd(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno, String flat, String floor, String systemString, 
			String i_has_bb_srv, String i_has_nowtv_srv, String i_profile_mismatch, String i_fs_prepay, String i_fs_ind, 
			String i_must_qc_ind, String i_ride_on_fsa, String i_fthi_leadday, String i_fthi_appnt_date, String i_pcdi_leadday, String i_pcdi_appnt_date, OrderImsUI order)throws DAOException {
		logger.debug("getServiceSrd is called");
		
		ImsServiceSrdDTO result = new ImsServiceSrdDTO();
				
		try {			
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_SB")
	        	.withProcedureName("get_service_srd_detail")  
	        	.declareParameters(
	        			new SqlParameter("i_flat", Types.VARCHAR),
						new SqlParameter("i_floor", Types.VARCHAR),
						new SqlParameter("i_is_blacklist_addr", Types.VARCHAR),
						new SqlParameter("i_is_blacklist_cust", Types.VARCHAR),
						new SqlParameter("i_is_pccw_fixed_line", Types.VARCHAR),
						new SqlParameter("i_appnt_date_time", Types.DATE),
						new SqlParameter("i_service_boundary", Types.VARCHAR),
						new SqlParameter("i_system", Types.VARCHAR),
						new SqlParameter("i_has_bb_srv", Types.VARCHAR),
						new SqlParameter("i_has_nowtv_srv", Types.VARCHAR),
						new SqlParameter("i_profile_mismatch", Types.VARCHAR),
						new SqlParameter("i_fs_prepay", Types.VARCHAR),
						new SqlParameter("i_fs_ind", Types.VARCHAR),
						new SqlParameter("i_fsa", Types.VARCHAR),
						new SqlParameter("i_ride_on_fsa", Types.VARCHAR),
						new SqlParameter("i_must_qc", Types.VARCHAR),
						new SqlParameter("i_fthi_leadday", Types.VARCHAR),
						new SqlParameter("i_fthi_appnt_date", Types.VARCHAR),
						new SqlParameter("i_pcdi_leadday", Types.VARCHAR),
						new SqlParameter("i_pcdi_appnt_date", Types.VARCHAR),
						new SqlOutParameter("o_is_allowed", Types.VARCHAR),
						new SqlOutParameter("o_is_2n_building", Types.VARCHAR),
						new SqlOutParameter("o_is_pon_2n_building", Types.VARCHAR),
						new SqlOutParameter("o_rfs_status", Types.VARCHAR),
						new SqlOutParameter("o_rfs_date", Types.DATE),
						new SqlOutParameter("o_rfs_is_future", Types.VARCHAR),
						new SqlOutParameter("o_rfs_is_not_available", Types.VARCHAR),
						new SqlOutParameter("o_reject_cd", Types.VARCHAR),
						new SqlOutParameter("o_reject_desc", Types.VARCHAR),
						new SqlOutParameter("service_detail_cursor", OracleTypes.CURSOR, new ServiceDetailMapper()),
						new SqlOutParameter("housing_type_cursor", OracleTypes.CURSOR, new HousingTypeMapper()),
						new SqlOutParameter("bandwidth_cursor", OracleTypes.CURSOR, new BandwidthMapper()),
						new SqlOutParameter("o_field_permit_day", Types.INTEGER),
						new SqlOutParameter("housing_type_desc_cursor", OracleTypes.CURSOR, new HousingTypeDescMapper()),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
	       if("RETAIL_RET-TV".equals(systemString)){
	    	   systemString="RET_RETAIL-TV";
	       }
	       if("DS_RET-TV".equals(systemString)){
	    	   systemString="DS-TV"; 
	       }
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_flat", flat);
			inMap.addValue("i_floor", floor);
			inMap.addValue("i_is_blacklist_addr", isBlAddr);
			inMap.addValue("i_is_blacklist_cust", isBlCust);
			inMap.addValue("i_is_pccw_fixed_line", isPccwLn);
			inMap.addValue("i_appnt_date_time", appntDateTime);
			inMap.addValue("i_service_boundary", sbno);
			inMap.addValue("i_system", systemString);
			inMap.addValue("i_has_bb_srv", i_has_bb_srv);
			inMap.addValue("i_has_nowtv_srv", i_has_nowtv_srv);
			inMap.addValue("i_profile_mismatch", i_profile_mismatch);
			inMap.addValue("i_fs_prepay", i_fs_prepay);
			inMap.addValue("i_fs_ind", i_fs_ind);
			inMap.addValue("i_fsa", null);
			if(order!=null){
				try{
					if ("NTVAO".equals(order.getOrderType())||"NTVUS".equals(order.getOrderType())||"NTVRE".equals(order.getOrderType())) {
						inMap.addValue("i_fsa", order.getServiceIms().getFsa());
					}
				} catch (Exception e) {
					logger.error("Exception caught in getAddressInfo()", e);
				}
			}
			inMap.addValue("i_ride_on_fsa", i_ride_on_fsa);
			inMap.addValue("i_must_qc", i_must_qc_ind);
			inMap.addValue("i_fthi_leadday", i_fthi_leadday);
			inMap.addValue("i_fthi_appnt_date", i_fthi_appnt_date);
			inMap.addValue("i_pcdi_leadday", i_pcdi_leadday);
			inMap.addValue("i_pcdi_appnt_date", i_pcdi_appnt_date);

//			logger.debug("order:"+gson.toJson(order));	
			 

			SqlParameterSource in = inMap;
			logger.debug("get_service_srd_detail human input:"+gson.toJson(in));			
			Map out = jdbcCall.execute(in);
			logger.debug("get_service_srd_detail human output:"+gson.toJson(out));
	        
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			
//			logger.debug("getServiceSrd() output ret_val = " + ret_val);
//			logger.debug("getServiceSrd() output error_code = " + error_code);
//			logger.debug("getServiceSrd() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				result = null;
			} else {
				result.setIsBlacklistAddr(isBlAddr);
				result.setIsBlacklistCust(isBlCust);
				result.setIsPccwFixedLine(isPccwLn);
				result.setAppntDateTime(appntDateTime);
				result.setServiceBoundary(sbno);
				result.setIsAllowed((String)out.get("o_is_allowed"));
				result.setIs2NBuilding((String)out.get("o_is_2n_building"));
				result.setRfsStatus((String)out.get("o_rfs_status"));
				result.setRfsDate((Date)out.get("o_rfs_date"));
				result.setRfsIsFuture((String)out.get("o_rfs_is_future"));
				result.setRfsIsNotAvailable((String)out.get("o_rfs_is_not_available"));
				result.setRejectCd((String)out.get("o_reject_cd"));
				result.setRejectDesc((String)out.get("o_reject_desc"));
				result.setServiceDetailList((List) out.get("service_detail_cursor"));
				result.setHousingTypeList((List) out.get("housing_type_cursor"));
				result.setBandwidthList((List) out.get("bandwidth_cursor"));
				result.setFieldPermitDay((Integer) out.get("o_field_permit_day"));
				result.setHousingTypeDescList((List) out.get("housing_type_desc_cursor"));
				//result.setOtChrgType("I");
			
				logger.debug("getServiceSrd() output record = " + result.toString());
			}
			
	    	return result;
			
		} catch (Exception e) {
			logger.error("i_flat="+flat+";i_floor="+floor+";i_service_boundary="+sbno+";i_system="+systemString);
			logger.error("Exception caught in getAddressInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	// commanded by steven, nanon asked, 20131114
	
	
	public boolean isCreateCOrder(String serviceBoundary, String unit, String floor, String custNum)throws DAOException {
		logger.debug("isCreateCOrder");
		
		try{
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        	.withSchemaName("OPS$BOM")
        	.withCatalogName("PKG_OC_IMS_SB")
        	.withProcedureName("IS_CREATE_C_ORDER")
        	.declareParameters(
					new SqlInOutParameter("i_service_boundary", Types.VARCHAR),
					new SqlInOutParameter("i_unit", Types.VARCHAR),
					new SqlInOutParameter("i_floor", Types.VARCHAR),
					new SqlInOutParameter("i_cust_num", Types.VARCHAR),					
					new SqlOutParameter("o_is_create_c_order", Types.VARCHAR),
					new SqlOutParameter("o_reason", Types.VARCHAR),
					new SqlOutParameter("o_related_fsa", Types.VARCHAR),					
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_service_boundary", serviceBoundary);
			inMap.addValue("i_unit", unit);
			inMap.addValue("i_floor", floor);
			inMap.addValue("i_cust_num", custNum);			
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
			
			
			logger.debug("getServiceSrd() output ret_val = " + ret_val);
			logger.debug("getServiceSrd() output error_code = " + error_code);
			logger.debug("getServiceSrd() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				throw new Exception("stored proc return error "+
						"ret_val:"+ret_val+" "+
						"error_code:"+error_code+" "+
						"error_text:"+error_text);
			} else {
				logger.debug("o_is_create_c_order:"+out.get("o_is_create_c_order"));
				logger.debug("o_reason:"+out.get("o_reason"));
				logger.debug("o_related_fsa:"+out.get("o_related_fsa"));								
				
				if("Y".equals(out.get("o_is_create_c_order"))){
					return true;
				}else{
					return false;
				}
				
			}
			
		} catch (Exception e) {
			logger.error("Exception caught in isCreateCOrder()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	

	public List<UimBlockageDTO> getFiberBlockage(String sbNum, String floor, String unitNo) throws DAOException{
		
		logger.debug("getFiberBlockage is called");

		List<UimBlockageDTO> blockageList = new ArrayList<UimBlockageDTO>();
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT BLOCKAGE_CODE, BLOCKAGE_DESC, BLOCKAGE_DATE  ");
		SQL.append(" FROM B_UIM_BLOCKAGE  ");
		SQL.append(" WHERE SERBDYNO = ?  ");
		SQL.append(" AND NVL(FLOORNB, ' ') = NVL(?, ' ')  ");
		SQL.append(" AND NVL(UNITNB, ' ') = NVL(?, ' ')  ");

		ParameterizedRowMapper<UimBlockageDTO> mapper = new ParameterizedRowMapper<UimBlockageDTO>() {
			public UimBlockageDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UimBlockageDTO dto = new UimBlockageDTO();
				dto.setBlockageCode(rs.getString("BLOCKAGE_CODE"));
				dto.setBlockageDesc(rs.getString("BLOCKAGE_DESC"));
				dto.setBlockageDate(rs.getDate("BLOCKAGE_DATE"));

				return dto;
			}
		};

		try {
			logger.debug("getFiberBlockage() @ ImsAddressInfoDAO: " + SQL);
			blockageList = simpleJdbcTemplate.query(SQL.toString(), mapper, sbNum, floor, unitNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getFiberBlockage()");

			blockageList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getFiberBlockage():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return blockageList;
		
	}

	public List<UimBlockageDTO> getFiberBlockageByFloor(String sbNum, String floor) throws DAOException{
		
		logger.debug("getFiberBlockageByFloor is called");

		List<UimBlockageDTO> blockageList = new ArrayList<UimBlockageDTO>();
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT NVL(UNITNB,' ') UNITNB, BLOCKAGE_CODE, BLOCKAGE_DESC, BLOCKAGE_DATE  ");
		SQL.append(" FROM B_UIM_BLOCKAGE  ");
		SQL.append(" WHERE SERBDYNO = ?  ");
		SQL.append(" AND NVL(FLOORNB, ' ') = NVL(?, ' ')  ");

		ParameterizedRowMapper<UimBlockageDTO> mapper = new ParameterizedRowMapper<UimBlockageDTO>() {
			public UimBlockageDTO mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				UimBlockageDTO dto = new UimBlockageDTO();
				dto.setUnitnb(rs.getString("UNITNB"));
				dto.setBlockageCode(rs.getString("BLOCKAGE_CODE"));
				dto.setBlockageDesc(rs.getString("BLOCKAGE_DESC"));
				dto.setBlockageDate(rs.getDate("BLOCKAGE_DATE"));

				return dto;
			}
		};

		try {
			logger.debug("getFiberBlockageByFloor() @ ImsAddressInfoDAO: " + SQL);
			blockageList = simpleJdbcTemplate.query(SQL.toString(), mapper, sbNum, floor);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getFiberBlockageByFloor()");

			blockageList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getFiberBlockageByFloor():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return blockageList;
		
	}

	public String getBlacklistAddr(String sbNum, String floor) throws DAOException{
		
		logger.debug("getBlacklistAddr is called");

		int cnt = 0;
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT COUNT(*)  ");
		SQL.append(" FROM B_BLACKLIST_ADDR  ");
		SQL.append(" WHERE SERBDYNO = ?  ");
		SQL.append(" AND NVL(FLOORNB, ' ') = NVL(?, ' ')  ");

		try {
			logger.debug("getBlacklistAddr() @ ImsAddressInfoDAO: " + SQL);
			cnt = simpleJdbcTemplate.queryForInt(SQL.toString(), new Object[]{sbNum, floor});

		} catch (Exception e) {
			logger.info("Exception caught in getBlacklistAddr():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return (cnt==0?"N":"Y");
		
	}
	
	public String getBlacklistAddrForPH(String sbNum, String floor, String unitNo) throws DAOException{
		
		logger.debug("getBlacklistAddr is called");

		int cnt = 0;
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT COUNT(*)  ");
		SQL.append(" FROM B_BLACKLIST_ADDR  ");
		SQL.append(" WHERE SERBDYNO = ?  ");
		SQL.append(" AND NVL(FLOORNB, ' ') = NVL(?, ' ')  ");
		SQL.append(" AND NVL(UNITNB, ' ') = NVL(?, ' ')  ");

		try {
			logger.debug("getBlacklistAddr() @ ImsAddressInfoDAO: " + SQL);
			cnt = simpleJdbcTemplate.queryForInt(SQL.toString(), new Object[]{sbNum, floor, unitNo});

		} catch (Exception e) {
			logger.info("Exception caught in getBlacklistAddr():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return (cnt==0?"N":"Y");
		
	}
	
	public List<String> getOsOrder(String sbNum, String floor, String unitNo) throws DAOException{
		
		logger.debug("getOsOrder is called");

		List<String> osOrderList = new ArrayList<String>();
		
		StringBuilder SQL= new StringBuilder();
		/*
		SQL.append(" select oc_id from b_ord_latest_status where oc_id in (   ");
		SQL.append("  select oc_id from b_ord_srv_addr where addr_id in (  ");
		SQL.append("   select addrid || '' from address where serbdyno = ? and nvl(floornb,' ') = nvl(?,' ') and nvl(unitnb,' ') = nvl(?,' '))  ");
		SQL.append(" ) and nvl(status,'00') <> '03' and nvl(status,'00') <> '06' and nvl(bom_status,'00') <> '07'  ");
		*/
		SQL.append("	SELECT oc_id	 ");
		SQL.append("	  FROM b_ord_latest_status	 ");
		SQL.append("	 WHERE (oc_id, dtl_id) IN (	 ");
		SQL.append("	          SELECT oc_id, dtl_id	 ");
		SQL.append("	            FROM b_ord_srv_addr	 ");
		SQL.append("	           WHERE addr_id IN (	 ");
		SQL.append("	                    SELECT addrid || ''	 ");
		SQL.append("	                      FROM address	 ");
		SQL.append("	                     WHERE serbdyno = ?	 ");
		SQL.append("	                       AND NVL (floornb, ' ') = NVL (?, ' ')	 ");
		SQL.append("	                       AND NVL (unitnb, ' ') = NVL (?, ' ')))	 ");
		SQL.append("	   AND NVL (status, '00') <> '03'	 ");
		SQL.append("	   AND NVL (status, '00') <> '06'	 ");
		SQL.append("	   AND NVL (bom_status, '00') <> '07'	 ");

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("OC_ID");

				return dto;
			}
		};
		
		try {
			logger.debug("getOsOrder() @ ImsAddressInfoDAO: " + SQL);
			osOrderList = simpleJdbcTemplate.query(SQL.toString(), mapper, sbNum, floor, unitNo);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOsOrder()");

			osOrderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOsOrder():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return osOrderList;
		
	}

	public String isBlacklistCust(String idDocType, String idDocNum) throws DAOException{
		
		logger.debug("isBlacklistCust is called");

		int cnt = 0;
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" SELECT COUNT(*)  ");
		SQL.append(" from  blacklst a, b_customer b  ");
		SQL.append(" where a.CUSTNB =b.CUST_NUM  ");
		SQL.append(" and b.SYSTEM_ID='IMS'  ");
		SQL.append(" and reldate is null  ");
		SQL.append(" and ID_doc_type= ? and ID_DOC_num = ?  ");

		try {
			logger.debug("isBlacklistCust() @ ImsAddressInfoDAO: " + SQL);
			cnt = simpleJdbcTemplate.queryForInt(SQL.toString(), new Object[]{idDocType, idDocNum});

		} catch (Exception e) {
			logger.info("Exception caught in isBlacklistCust():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return (cnt==0?"N":"Y");
		
	}
	
	public List<String> getFilteredTimeSlotList(String[] AppntType, String sbno, String distno, String sectcd) throws DAOException{
		logger.debug("getFilteredTimeSlotList");
		
		List<String> outList = null;
		
		String SQL = "	SELECT timeslot	"+
		"	  FROM b_appnt_timeslot_filter	"+
		"	 WHERE serbdyno = ? OR distrnb = ? OR sect_cd = ? ";		

		if(AppntType!=null && AppntType.length>0){
			SQL = SQL + " OR appnt_type IN (";
			
			for(int i=0; i<AppntType.length; i++){
				SQL = SQL + "'"+AppntType[i] + "'";
				if(i<AppntType.length-1){
					SQL = SQL + ",";
				}
			}
			
			SQL = SQL + ") ";
		}
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("timeslot");

				return dto;
			}
		};
		
		try {
			logger.debug("getFilteredTimeSlotList(): " + SQL);
			outList = simpleJdbcTemplate.query(SQL.toString(), mapper, sbno, distno, sectcd);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("No filtered time slots return");			
		} catch (Exception e) {
			logger.info("Exception caught in getFilteredTimeSlotList():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return outList;
		
	}
	
	public List<String> getBlCustAcctList(String idDocType, String idDocNum) throws DAOException{
		
		logger.debug("getBlacklistCustAcctList is called");

		List<String> acctList = new ArrayList<String>();
		
		StringBuilder SQL= new StringBuilder();
		SQL.append(" select acctnb from  blacklst a, b_customer b  ");
		SQL.append(" where a.CUSTNB =b.CUST_NUM  ");
		SQL.append(" and b.SYSTEM_ID='IMS'  ");
		SQL.append(" and reldate is null  ");
		SQL.append(" and ID_doc_type= ? and ID_DOC_num = ?  ");

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("ACCTNB");

				return dto;
			}
		};
		
		try {
			logger.debug("getBlacklistCustAcctList() @ ImsAddressInfoDAO: " + SQL);
			acctList = simpleJdbcTemplate.query(SQL.toString(), mapper, idDocType, idDocNum);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBlacklistCustAcctList()");

			acctList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBlacklistCustAcctList():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return acctList;
		
	}

	public ImsBlacklistCustInfoDTO getBlCustOsAmt(String acctNo)throws DAOException {
		logger.debug("getBlacklistCustOsAmt is called");
		
		ImsBlacklistCustInfoDTO result = new ImsBlacklistCustInfoDTO();
				
		try {			
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("b_ccr2_account_pkg")
	        	.withProcedureName("get_os_amount")
	        	.declareParameters(
						new SqlParameter("i_acctnb", Types.VARCHAR),
						new SqlParameter("i_fch_ready", Types.VARCHAR),
						new SqlOutParameter("o_curosamt", Types.FLOAT),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("o_error_code", Types.INTEGER),
						new SqlOutParameter("o_error_text", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_acctnb", acctNo);
			inMap.addValue("i_fch_ready", "N");
			SqlParameterSource in = inMap;
			
			Map out = jdbcCall.execute(in);
	        
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("o_error_code"))!= null ) {
				error_code = ((Integer) out.get("o_error_code")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("o_error_text");
			
			
			logger.debug("getServiceSrd() output ret_val = " + ret_val);
			logger.debug("getServiceSrd() output error_code = " + error_code);
			logger.debug("getServiceSrd() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				result = null;
			} else {
				result.setAcctNo(acctNo);
				result.setFchReady("N");
				result.setCurOsAmt((Double) out.get("o_curosamt"));
			
				logger.debug("getBlacklistCustOsAmt() output record = " + result.toString());
			}
			
	    	return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in getAddressInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
		
	public boolean checkSpecialSBFilterVas(String SB) throws DAOException{
		 
		logger.debug("checkSpecialSBFilterVas is called");
		
		int cnt = 0;
		
		String sql = 
		"select count(*) 		    											"+
		"from b_service_rollout sr                                                                        "+
		"where sr.serbdyno= ?                                                                       "+
		"and sr.service_code in (select l.bom_code from b_lookup l where l.bom_grp_id='SB_FILTERBY_VAS') "+
		"and nvl(sr.obsolete,'N')='N'                                                                    ";

		try {

			cnt = simpleJdbcTemplate.queryForInt(sql, new Object[]{SB});
		} catch (Exception e) {
			logger.info("Exception caught in getBlacklistAddr():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return (cnt==0?false:true);
		
	}
	
///for ret/temp
	
	public ImsServiceSrdDTO getRetentionServiceSrd(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno, String flat, String floor, String systemString)throws DAOException {
		logger.debug("getServiceSrd is called");
		
		ImsServiceSrdDTO result = new ImsServiceSrdDTO();
				
		try {			
	        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_SB")
	        	.withProcedureName("get_service_srd_detail")
	        	.declareParameters(
	        			new SqlParameter("i_flat", Types.VARCHAR),
						new SqlParameter("i_floor", Types.VARCHAR),
						new SqlParameter("i_is_blacklist_addr", Types.VARCHAR),
						new SqlParameter("i_is_blacklist_cust", Types.VARCHAR),
						new SqlParameter("i_is_pccw_fixed_line", Types.VARCHAR),
						new SqlParameter("i_appnt_date_time", Types.DATE),
						new SqlParameter("i_service_boundary", Types.VARCHAR),
						new SqlParameter("i_system", Types.VARCHAR),
						new SqlParameter("i_has_bb_srv", Types.VARCHAR),
						new SqlParameter("i_has_nowtv_srv", Types.VARCHAR),
						new SqlParameter("i_profile_mismatch", Types.VARCHAR),
						new SqlParameter("i_fs_prepay", Types.VARCHAR),
						new SqlParameter("i_fs_ind", Types.VARCHAR),
						new SqlParameter("i_fsa", Types.VARCHAR),
						new SqlParameter("i_ride_on_fsa", Types.VARCHAR),
						new SqlOutParameter("o_is_allowed", Types.VARCHAR),
						new SqlOutParameter("o_is_2n_building", Types.VARCHAR),
						new SqlOutParameter("o_is_pon_2n_building", Types.VARCHAR),
						new SqlOutParameter("o_rfs_status", Types.VARCHAR),
						new SqlOutParameter("o_rfs_date", Types.DATE),
						new SqlOutParameter("o_rfs_is_future", Types.VARCHAR),
						new SqlOutParameter("o_rfs_is_not_available", Types.VARCHAR),
						new SqlOutParameter("o_reject_cd", Types.VARCHAR),
						new SqlOutParameter("o_reject_desc", Types.VARCHAR),
						new SqlOutParameter("service_detail_cursor", OracleTypes.CURSOR, new ServiceDetailMapper()),
						new SqlOutParameter("housing_type_cursor", OracleTypes.CURSOR, new HousingTypeMapper()),
						new SqlOutParameter("bandwidth_cursor", OracleTypes.CURSOR, new BandwidthMapper()),
						new SqlOutParameter("o_field_permit_day", Types.INTEGER),
						new SqlOutParameter("housing_type_desc_cursor", OracleTypes.CURSOR, new HousingTypeDescMapper()),
						new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_flat", flat);
			inMap.addValue("i_floor", floor);
			inMap.addValue("i_is_blacklist_addr", isBlAddr);
			inMap.addValue("i_is_blacklist_cust", isBlCust);
			inMap.addValue("i_is_pccw_fixed_line", isPccwLn);
			inMap.addValue("i_appnt_date_time", appntDateTime);
			inMap.addValue("i_service_boundary", sbno);
			inMap.addValue("i_system", systemString);
			inMap.addValue("i_has_bb_srv", "Y");
			inMap.addValue("i_has_nowtv_srv", "N");
			inMap.addValue("i_profile_mismatch", "N");
			inMap.addValue("i_fs_prepay", "N");
			inMap.addValue("i_fs_ind", "N");
			inMap.addValue("i_fsa", null);
			inMap.addValue("i_ride_on_fsa", null);
			SqlParameterSource in = inMap;

			logger.debug("input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.debug("output:"+gson.toJson(out));
	        
			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
			
			if (((Integer) out.get("gnErrCode"))!= null ) {
				error_code = ((Integer) out.get("gnErrCode")).intValue();
			}
			
			if (((Integer) out.get("gnRetVal"))!= null ) {
				ret_val = ((Integer) out.get("gnRetVal")).intValue();
			}
			
			String error_text = (String)out.get("gsErrText");
			
			
			logger.debug("getServiceSrd() output ret_val = " + ret_val);
			logger.debug("getServiceSrd() output error_code = " + error_code);
			logger.debug("getServiceSrd() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				result = null;
			} else {
				result.setIsBlacklistAddr(isBlAddr);
				result.setIsBlacklistCust(isBlCust);
				result.setIsPccwFixedLine(isPccwLn);
				result.setAppntDateTime(appntDateTime);
				result.setServiceBoundary(sbno);
				result.setIsAllowed((String)out.get("o_is_allowed"));
				result.setIs2NBuilding((String)out.get("o_is_2n_building"));
				result.setRfsStatus((String)out.get("o_rfs_status"));
				result.setRfsDate((Date)out.get("o_rfs_date"));
				result.setRfsIsFuture((String)out.get("o_rfs_is_future"));
				result.setRfsIsNotAvailable((String)out.get("o_rfs_is_not_available"));
				result.setRejectCd((String)out.get("o_reject_cd"));
				result.setRejectDesc((String)out.get("o_reject_desc"));
				result.setServiceDetailList((List) out.get("service_detail_cursor"));
				result.setHousingTypeList((List) out.get("housing_type_cursor"));
				result.setBandwidthList((List) out.get("bandwidth_cursor"));
				result.setFieldPermitDay((Integer) out.get("o_field_permit_day"));
				result.setHousingTypeDescList((List) out.get("housing_type_desc_cursor"));
				//result.setOtChrgType("I");
			
				logger.debug("getServiceSrd() output record:"+gson.toJson(result));
			}
			
	    	return result;
			
		} catch (Exception e) {
			logger.error("Exception caught in getAddressInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public String getCorrectSBbyFlatFloor(String sb, String floor, String flat) throws DAOException{
		logger.debug("getCorrectSBbyFlatFloor is called");
		List<String> resultSB = new ArrayList<String>();
		if("".equals(sb)){
			sb = null;
		}
		if("".equals(floor)){
			floor = null;
		}
		
		String SQL= "SELECT serbdyno" +
				"  FROM (SELECT   *" +
				"            FROM (SELECT b.serbdyno, '1' section" + // both flat floor
				"                    FROM b_addr_lkup b, b_addr_lkup a" +
				"                   WHERE a.serbdyno = :sbdy" +
				"                     AND a.addptype = 'I'" +
				"                     AND NVL (b.lotno, ' ') = NVL (a.lotno, ' ')" +
				"                     AND NVL (b.bldg_nam, ' ') = NVL (a.bldg_nam, ' ')" +
				"                     AND NVL (b.hseltnum, ' ') = NVL (a.hseltnum, ' ')" +
				"                     AND NVL (b.st_nam, ' ') = NVL (a.st_nam, ' ')" +
				"                     AND NVL (b.stcat_cd, ' ') = NVL (a.stcat_cd, ' ')" +
				"                     AND b.sect_cd = a.sect_cd" +
				"                     AND b.distrnum = a.distrnum" +
				"                     AND b.addptype = 'I'" +
				"                     AND NVL (b.apfltun, ' ') = NVL (:flat, ' ')" +
				"                     AND NVL (b.flr_num, ' ') = NVL (:floor, ' ')" +
				"                  UNION ALL" +
				"                  SELECT b.serbdyno, '2' section" + // only floor
				"				FROM b_addr_lkup b, b_addr_lkup a" +
				"                   WHERE a.serbdyno = :sbdy" +
				"                     AND a.addptype = 'I'" +
				"                     AND NVL (b.lotno, ' ') = NVL (a.lotno, ' ')" +
				"                     AND NVL (b.bldg_nam, ' ') = NVL (a.bldg_nam, ' ')" +
				"                     AND NVL (b.hseltnum, ' ') = NVL (a.hseltnum, ' ')" +
				"                     AND NVL (b.st_nam, ' ') = NVL (a.st_nam, ' ')" +
				"                     AND NVL (b.stcat_cd, ' ') = NVL (a.stcat_cd, ' ')" +
				"                     AND b.sect_cd = a.sect_cd" +
				"                     AND b.distrnum = a.distrnum" +
				"                     AND b.addptype = 'I'" +
				"                     AND NVL (b.flr_num, ' ') = NVL (:floor, ' ')" +
				"                     AND b.apfltun is null" +
				"                  UNION ALL" +
				"                  SELECT b.serbdyno, '3' section" + // only flat
				"                    FROM b_addr_lkup b, b_addr_lkup a" +
				"                   WHERE a.serbdyno = :sbdy" +
				"                     AND a.addptype = 'I'" +
				"                     AND NVL (b.lotno, ' ') = NVL (a.lotno, ' ')" +
				"                     AND NVL (b.bldg_nam, ' ') = NVL (a.bldg_nam, ' ')" +
				"                     AND NVL (b.hseltnum, ' ') = NVL (a.hseltnum, ' ')" +
				"                     AND NVL (b.st_nam, ' ') = NVL (a.st_nam, ' ')" +
				"                     AND NVL (b.stcat_cd, ' ') = NVL (a.stcat_cd, ' ')" +
				"                     AND b.sect_cd = a.sect_cd" +
				"                     AND b.distrnum = a.distrnum" +
				"                     AND b.addptype = 'I'" +
				"                     AND NVL (b.apfltun, ' ') = NVL (:flat, ' ')" +
				"                     AND b.flr_num is null" +
				"                  UNION ALL" +
				"                  SELECT b.serbdyno, '4' section" + // both null string
				"                    FROM b_addr_lkup b, b_addr_lkup a" +
				"                   WHERE a.serbdyno = :sbdy" +
				"                     AND a.addptype = 'I'" +
				"                     AND NVL (b.lotno, ' ') = NVL (a.lotno, ' ')" +
				"                     AND NVL (b.bldg_nam, ' ') = NVL (a.bldg_nam, ' ')" +
				"                     AND NVL (b.hseltnum, ' ') = NVL (a.hseltnum, ' ')" +
				"                     AND NVL (b.st_nam, ' ') = NVL (a.st_nam, ' ')" +
				"                     AND NVL (b.stcat_cd, ' ') = NVL (a.stcat_cd, ' ')" +
				"                     AND b.sect_cd = a.sect_cd" +
				"                     AND b.distrnum = a.distrnum" +
				"                     AND b.addptype = 'I'" +
				"                     AND NVL (b.apfltun, ' ') = NVL ('', ' ')" +
				"                     AND NVL (b.flr_num, ' ') = NVL ('', ' ')" +
				"                                                             )" +
				"        ORDER BY section)" +
				" WHERE ROWNUM = 1 ";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)	throws SQLException {
				String dto = (rs.getString("serbdyno"));
				return dto;
			}
		};
		
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("sbdy", sb);
			params.addValue("flat", flat);
			params.addValue("floor", floor);
			logger.info("getCorrectSBbyFlatFloor params: " + gson.toJson(params));
			
			logger.debug("getCorrectSBbyFlatFloor() @ ImsAddressInfoDAO: " + SQL);
			resultSB = simpleJdbcTemplate.query(SQL, mapper, params);

		} catch (Exception e) {
			logger.info("Exception caught in getCorrectSBbyFlatFloor():", e);

			throw new DAOException(e.getMessage(), e);
		}
		if(resultSB.size()>0){
			logger.info("getCorrectSBbyFlatFloor():"+resultSB.get(0));
			return resultSB.get(0);
		}else{
			logger.info("getCorrectSBbyFlatFloor() cannot find another sb which has diff flat floor");
			return sb;
		}
	}

	
	public List<String> getRelatedSBList(String currentSB) throws DAOException{
		
		logger.debug("getRelatedSBList is called");

		List<String> sbList = new ArrayList<String>();
		
		String sql = "select primary_sb from b_sb_related_sb where related_sb = ? and obsolete = 'N'			"+									
		"union                                                                               "+													
		"select related_sb from b_sb_related_sb where primary_sb = ? and obsolete = 'N'      ";  

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("primary_sb");

				return dto;
			}
		};
		
		try {
			sbList = simpleJdbcTemplate.query(sql, mapper, currentSB, currentSB);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getBlacklistCustAcctList()");

			sbList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getBlacklistCustAcctList():", e);

			throw new DAOException(e.getMessage(), e);
		}
		
		return sbList;
		
	};	
	
public boolean checkifAddressExactMatchwService(String serbdyNo, String floor, String unitNo, String sbOrderID) throws DAOException{
		
		logger.debug("checkifAddressExactMatchwService is called");
		boolean check=false;
		List<String> osOrderList = new ArrayList<String>();
		
		StringBuilder SQL= new StringBuilder();
		/*
		SQL.append("	SELECT oc_id	 ");
		SQL.append("	  FROM b_ord_latest_status	 ");
		SQL.append("	 WHERE (oc_id, dtl_id) IN (	 ");
		SQL.append("	          SELECT oc_id, dtl_id	 ");
		SQL.append("	            FROM b_ord_srv_addr	 ");
		SQL.append("	           WHERE addr_id IN (	 ");
		SQL.append("	                    SELECT addrid || ''	 ");
		SQL.append("	                      FROM address	 ");
		SQL.append("	                     WHERE serbdyno = ?	 ");
		SQL.append("	                       AND NVL (floornb, ' ') = NVL (?, ' ')	 ");
		SQL.append("	                       AND NVL (unitnb, ' ') = NVL (?, ' ')))	 ");
		SQL.append("	   AND NVL (status, '00') <> '06'	 ");
		SQL.append("	   AND NVL (bom_status, '00') <> '04'	 ");
		SQL.append("	   AND NVL (bom_status, '00') <> '07'	 ");
		*/
		
		SQL.append(" select A.OC_ID                                                         "); 
		SQL.append(" FROM b_ord_latest_status a, b_ord_dtl b,b_ord_srv_addr c,address d     ");
		SQL.append(" where A.OC_ID=b.oc_id                                                  ");
		SQL.append(" and A.DTL_ID=B.DTL_ID                                                  ");
		SQL.append(" and A.OC_ID = C.OC_ID                                                  ");
		SQL.append(" and a.DTL_ID = c.DTL_ID                                                ");
		SQL.append(" and C.ADDR_ID = d.addrid                                               ");
		SQL.append(" and B.TYPE_OF_SRV ='IMS'                                               ");
		SQL.append(" AND NVL (a.status, '00') <> '06'     									");
	    SQL.append(" AND NVL (a.bom_status, '00') <> '04' 									");    
	    SQL.append(" AND NVL (a.bom_status, '00') <> '07'  									");
		SQL.append(" and  serbdyno = NVL (?, ' ')                                                       ");
		SQL.append(" AND NVL (floornb, ' ') = NVL (?, ' ')                                  ");
		SQL.append(" AND NVL (unitnb, ' ') = NVL (?, ' ')                                   ");
		SQL.append(" AND B.AGREEMENT_NUM <> NVL (?, ' ')                                              ");

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("OC_ID");

				return dto;
			}
		};
		
		try {
			logger.debug("getOsOrder() @ ImsAddressInfoDAO: " + SQL);
			osOrderList = simpleJdbcTemplate.query(SQL.toString(), mapper, serbdyNo, floor, unitNo,sbOrderID);

		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getOsOrder()");

			osOrderList = null;
		} catch (Exception e) {
			logger.info("Exception caught in getOsOrder():", e);

			throw new DAOException(e.getMessage(), e);
		}
		if (osOrderList.size() > 0)
			check = true;
		else check =false;
		return check;
		
	}

	public String getH264Ind(String sbno) throws DAOException{
		logger.debug("getH264Ind");
		try{
			
			String h264Ind;
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_SB")
	        	.withFunctionName("get_h264_ind")
	        	.declareParameters(
	        			new SqlParameter("i_serbdyno", Types.VARCHAR),
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
			 
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_serbdyno", sbno);		
			SqlParameterSource in = inMap;
			
			//Map out = jdbcCall.execute(in);
			

			
			h264Ind = jdbcCall.executeFunction(String.class, inMap);
	        
//			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
//			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
//			
//			if (((Integer) out.get("gnErrCode"))!= null ) {
//				error_code = ((Integer) out.get("gnErrCode")).intValue();
//			}
//			
//			if (((Integer) out.get("gnRetVal"))!= null ) {
//				ret_val = ((Integer) out.get("gnRetVal")).intValue();
//			}
//			
//			String error_text = (String)out.get("gsErrText");
//			
//			
//			logger.debug("getH264Ind() output ret_val = " + ret_val);
//			logger.debug("getH264Ind() output error_code = " + error_code);
//			logger.debug("getH264Ind() output error_text = " + error_text);
//			
//			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
//				h264Ind = null;
//			} else {
//				logger.debug("getH264Ind() output h264Ind = " + (String)out.get("o_h264_ind"));
//				
//				h264Ind = (String)out.get("o_h264_ind");							
//				
//			}
	        
			return h264Ind;
			
		} catch (Exception e) {
			logger.error("Exception caught in getH264Ind()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}

	public String getAdsl18mInd(String sbno) throws DAOException{
		logger.debug("getAdsl18mInd");
		try{
			
			String adsl18mInd;
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("PKG_OC_IMS_SB")
	        	.withFunctionName("get_adsl_18m_ind")
	        	.declareParameters(
	        			new SqlParameter("i_serbdyno", Types.VARCHAR),
	        			new SqlParameter("i_sys_id", Types.VARCHAR),
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
			 
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_serbdyno", sbno);		
			inMap.addValue("i_sys_id", "RETAIL");		
			SqlParameterSource in = inMap;
			

			adsl18mInd = jdbcCall.executeFunction(String.class, inMap);
			
			//Map out = jdbcCall.execute(in);
	        
//			int error_code = BomWebPortalConstant.ERRCODE_FAIL;
//			int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
//			
//			if (((Integer) out.get("gnErrCode"))!= null ) {
//				error_code = ((Integer) out.get("gnErrCode")).intValue();
//			}
//			
//			if (((Integer) out.get("gnRetVal"))!= null ) {
//				ret_val = ((Integer) out.get("gnRetVal")).intValue();
//			}
//			
//			String error_text = (String)out.get("gsErrText");
//			
//			
//			logger.debug("getAdsl18mInd() output ret_val = " + ret_val);
//			logger.debug("getAdsl18mInd() output error_code = " + error_code);
//			logger.debug("getAdsl18mInd() output error_text = " + error_text);
//			
//			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
//				adsl18mInd = null;
//			} else {
//				logger.debug("getAdsl18mInd() output h264Ind = " + (String)out.get("o_adsl_18m_ind"));
//				
//				adsl18mInd = (String)out.get("o_adsl_18m_ind");							
//				
//			}
	        
			return adsl18mInd;
			
		} catch (Exception e) {
			logger.error("Exception caught in getAdsl18mInd()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
public List<String> getSbPopUp(String sbNum) throws DAOException{
		logger.debug("getSbPopUp is called");

		List<String> sbPopUpMsgs = new ArrayList<String>();
		String sbPopUp = "";
		
		String SQL= "select remark_1, remark_2 from (" +
				"select * from b_target_sb_lkup " +
				"where serbdyno = ? " +
				"and remark_type in ('A', 'N') " +
				"and eff_start_date <= sysdate " +
				"and nvl(eff_end_date, sysdate + 1) > sysdate " +
				"order by eff_start_date desc) ";
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("remark_1") + "\n\n" + rs.getString("remark_2");
			}
		};

		try {
			logger.debug("getSbPopUp() @ ImsAddressInfoDAO: " + SQL);
			sbPopUpMsgs = simpleJdbcTemplate.query(SQL.toString(), mapper, sbNum);
			if (sbPopUpMsgs != null && sbPopUpMsgs.size() > 0) sbPopUp = sbPopUpMsgs.get(0);
		} catch (EmptyResultDataAccessException erdae) {
			logger.info("EmptyResultDataAccessException in getSbPopUp()");
			sbPopUpMsgs = null;
		} catch (Exception e) {
			logger.info("Exception caught in getSbPopUp():", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		return sbPopUpMsgs;
		
	}

public String hasPcdBeforeCheckCsl(String sbno, String unit, String floor) throws DAOException{
	logger.debug("hasPcdBeforeCheckCsl");
	try{
		
		String hasPcdBeforeInd;
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        	.withSchemaName("OPS$BOM")
        	.withCatalogName("PKG_OC_IMS_SB")
        	.withProcedureName("HAS_PCD_BEFORE")
        	.declareParameters(
        			new SqlParameter("i_service_boundary", Types.VARCHAR),
        			new SqlParameter("i_unit", Types.VARCHAR),
        			new SqlParameter("i_floor", Types.VARCHAR),

        			new SqlOutParameter("o_has_pcd_before", Types.VARCHAR),
        			new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
		 
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_service_boundary", sbno);
		inMap.addValue("i_unit", unit);
		inMap.addValue("i_floor", floor);

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
		
		
		logger.debug("hasPcdBeforeCheckCsl() output ret_val = " + ret_val);
		logger.debug("hasPcdBeforeCheckCsl() output error_code = " + error_code);
		logger.debug("hasPcdBeforeCheckCsl() output error_text = " + error_text);
		
		if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
			hasPcdBeforeInd = null;
		} else {
			logger.debug("hasPcdBeforeCheckCsl() output o_has_pcd_before = " + (String)out.get("o_has_pcd_before"));
		
			
			hasPcdBeforeInd = (String)out.get("o_has_pcd_before");							
			
		}
        
		return hasPcdBeforeInd;
		
	} catch (Exception e) {
		logger.error("Exception caught in hasPcdBeforeCheckCsl()", e);
		throw new DAOException(e.getMessage(), e);
	}
}

public String getCslPcdMonthCheck() throws DAOException{
	logger.debug("getCslPcdMonthCheck is called");

	List<String> cslPcdMonthList = new ArrayList<String>();
	String cslPcdMonth = "";
	
	String SQL= "SELECT bom_desc FROM b_lookup WHERE bom_grp_Id='SB_IMS_CSL_CHK' AND bom_code='MTH'";
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			return rs.getString("bom_desc");
		}
	};

	try {
		logger.debug("getCslPcdMonthCheck() @ ImsAddressInfoDAO: " + SQL);
		cslPcdMonthList = simpleJdbcTemplate.query(SQL.toString(), mapper);
		if (cslPcdMonthList != null && cslPcdMonthList.size() > 0) cslPcdMonth = cslPcdMonthList.get(0);
	} catch (EmptyResultDataAccessException erdae) {
		logger.info("EmptyResultDataAccessException in getCslPcdMonthCheck()");

	} catch (Exception e) {
		logger.info("Exception caught in getCslPcdMonthCheck():", e);
		throw new DAOException(e.getMessage(), e);
	}
	
	return cslPcdMonth;
	
}


public List<SbRemarksDTO> getSbRemarks(String sbno)throws DAOException {
	logger.debug("getSbRemarks is called");
	
	List<SbRemarksDTO> result = new ArrayList<SbRemarksDTO>();
			
	try {			
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
        	.withSchemaName("OPS$BOM")
        	.withCatalogName("PKG_OC_IMS_SB")
        	.withProcedureName("get_sb_remarks")  
        	.declareParameters(
					new SqlParameter("i_service_boundary", Types.VARCHAR),
					new SqlOutParameter("remark_cursor", OracleTypes.CURSOR, new SbRemarksMapper()),
					new SqlOutParameter("gnRetVal", Types.INTEGER),
					new SqlOutParameter("gnErrCode", Types.INTEGER),
					new SqlOutParameter("gsErrText", Types.VARCHAR));
       
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("i_service_boundary", sbno);
		
		 
//		logger.info("***********getSbRemarks***********************");
//		logger.info("i_service_boundary : " + sbno);
//		logger.info("***********getSbRemarks(end)***********************");

		SqlParameterSource in = inMap;
		logger.debug("get_sb_remarks human input:"+gson.toJson(in));			
		Map out = jdbcCall.execute(in);
		logger.debug("get_sb_remarks human output:"+gson.toJson(out));
        
		int error_code = BomWebPortalConstant.ERRCODE_FAIL;
		int ret_val = BomWebPortalConstant.ERRCODE_FAIL; 
		
		if (((Integer) out.get("gnErrCode"))!= null ) {
			error_code = ((Integer) out.get("gnErrCode")).intValue();
		}
		
		if (((Integer) out.get("gnRetVal"))!= null ) {
			ret_val = ((Integer) out.get("gnRetVal")).intValue();
		}
		
		String error_text = (String)out.get("gsErrText");
		
		
//		logger.debug("getServiceSrd() output ret_val = " + ret_val);
//		logger.debug("getServiceSrd() output error_code = " + error_code);
//		logger.debug("getServiceSrd() output error_text = " + error_text);
		
		if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
			result = null;
		} else {
			result = (List) out.get("remark_cursor");
		}
		
    	return result;
		
	} catch (Exception e) {
		logger.error("Exception caught in getSbRemarks()", e);
		throw new DAOException(e.getMessage(), e);
	}
}

public String withDelCheckAddr(String sbno, String unit, String floor) throws DAOException{
	logger.debug("withDelCheckAddr");
	try{
		
		String ltsServiceInd;
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("lts_svc_check_pkg")
	        	.withProcedureName("chkLtsSvcbyIA")  
	        	.declareParameters(
						new SqlParameter("in_srvbdry_num", Types.VARCHAR),
						new SqlParameter("in_floor_num", Types.VARCHAR),
						new SqlParameter("in_unit_num", Types.VARCHAR),
						new SqlOutParameter("lts_srv_ind", Types.VARCHAR),
						new SqlOutParameter("rtnCode", Types.VARCHAR),
						new SqlOutParameter("rtnMsg", Types.VARCHAR));
	       
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("in_srvbdry_num", sbno);
			inMap.addValue("in_floor_num", floor);
			inMap.addValue("in_unit_num", unit);

		SqlParameterSource in = inMap;
		
		Map out = jdbcCall.execute(in);
        
		String error_code = null;
		String rtnMsg = null; 
		
		if (((String) out.get("rtnCode"))!= null ) {
			error_code = (String)out.get("rtnCode");							
			
		}
		
		if (((String) out.get("rtnMsg"))!= null ) {
			rtnMsg = (String)out.get("rtnMsg");	
		}
		logger.info("withDelCheckAddr() input sbno =" +sbno);
		logger.info("withDelCheckAddr() input unit =" +unit);
		logger.info("withDelCheckAddr() input floor =" +floor);
		logger.debug("withDelCheckAddr() output error_code = " + error_code);
		logger.debug("withDelCheckAddr() output error_text = " + rtnMsg);
		
		if ( !"0".equals(error_code)) {
			ltsServiceInd = null;
			
		} else {
			logger.debug("withDelCheckAddr() output withDelCheckAddr = " + (String)out.get("lts_srv_ind"));
		
			
			ltsServiceInd = (String)out.get("lts_srv_ind");							
			
		}
        
		return ltsServiceInd;
		
	} catch (Exception e) {
		logger.error("Exception caught in withDelCheckAddr()", e);
//		throw new DAOException(e.getMessage(), e);
		return "N";
	}
}

public BomwebSubscribedOfferImsDTO getHomeNetworkRequired(String sbno, String unit, String floor, String technology) throws DAOException{
	BomwebSubscribedOfferImsDTO result = new BomwebSubscribedOfferImsDTO();
	try{
		
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
	        	.withSchemaName("OPS$BOM")
	        	.withCatalogName("Pkg_Oc_Ims_Mesh_Router")
	        	.withProcedureName("is_Home_Network_Required_SBO")  
	        	.declareParameters(
						new SqlParameter("i_flat", Types.VARCHAR),
						new SqlParameter("i_floor", Types.VARCHAR),
						new SqlParameter("i_serbdyno", Types.VARCHAR),
						new SqlParameter("i_line_type", Types.VARCHAR),
	        			new SqlOutParameter("o_HN_Group_Req", Types.VARCHAR),
	        			new SqlOutParameter("o_prod_cd", Types.VARCHAR),
	        			new SqlOutParameter("gnRetVal", Types.INTEGER),
						new SqlOutParameter("gnErrCode", Types.INTEGER),
						new SqlOutParameter("gsErrText", Types.VARCHAR));
	       
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("i_flat", unit);
			inMap.addValue("i_floor", floor);
			inMap.addValue("i_serbdyno", sbno);
			inMap.addValue("i_line_type", technology);
			
			SqlParameterSource in = inMap;
			
			logger.info("getHomeNetworkRequired() input sbno =" +sbno);
			logger.info("getHomeNetworkRequired() input unit =" +unit);
			logger.info("getHomeNetworkRequired() input floor =" +floor);
			logger.info("getHomeNetworkRequired() input lineType =" +technology);
			
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
			
			
			logger.debug("getHomeNetworkRequired() output ret_val = " + ret_val);
			logger.debug("getHomeNetworkRequired() output error_code = " + error_code);
			logger.debug("getHomeNetworkRequired() output error_text = " + error_text);
			
			if ( (error_code != BomWebPortalConstant.ERRCODE_SUCCESS)) {
				result = null;
			} else {
				
				result.setIoInd("Y".equalsIgnoreCase((String)out.get("o_HN_Group_Req"))?"I":"N");
				result.setOfferCd((String)out.get("o_prod_cd"));
			
				logger.debug("getHomeNetworkRequired() output record = " + gson.toJson(result));
			}
			
	    	return result;
		
	} catch (Exception e) {
		logger.error("Exception caught in isHomeNetworkRequired()", e);
//		throw new DAOException(e.getMessage(), e);
		return null;
	}
}

public String isSupremeHousingType(String housingType) throws DAOException{
	 
	logger.debug("isSupremeHousingType is called");
	
	String rs="";
	
	String sql = 
	"select decode(count(*),0,'N','A') ind    											" +
	"from b_lookup						"+
	"where bom_grp_id = 'BOM2016143'					"+
	"and bom_desc = 'HOUSING_TYPE'						"+
	"and bom_code='"+housingType+"'                                                                       "+
	"and exists (select 1 from b_lookup WHERE BOM_GRP_ID = 'SBIMS_SUP_LKUP' AND BOM_DESC ='Y')    ";

	try {
		rs = simpleJdbcTemplate.queryForObject(sql, String.class);
	} catch (Exception e) {
		logger.info("Exception caught in getBlacklistAddr():", e);

		throw new DAOException(e.getMessage(), e);
	}
	
	return rs;
	
}
public  List<String>  getServiceCodeList(String serbdyno) throws DAOException{

	List<String> rtnList = new ArrayList<String>();
	String SQL = " SELECT sr.service_code FROM  "+
	"b_srvcode_lkup sl, b_service_rollout sr  "+
	"WHERE sr.serbdyno=? AND NVL(sr.obsolete,'N')='N' "+
	"AND sl.service_code=sr.service_code "+
	"AND sl.domain_type='P' ";


	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum)
		throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("service_code");

			return dto;
		}
	};

	try {
		rtnList = simpleJdbcTemplate.query(SQL, mapper,serbdyno);
		logger.debug(" getServiceCodeList " +SQL);
	} catch (EmptyResultDataAccessException erdae) {
		logger.info("EmptyResultDataAccessException in getServiceCodeList()");

		rtnList = null;
	} catch (Exception e) {
		logger.info("Exception caught in getServiceCodeList():", e);

		throw new DAOException(e.getMessage(), e);
	}

	return rtnList;

}

public List<String> getPonSBList(String sbNum) throws DAOException{
	
	logger.debug("getPonSBList is called");

	List<String> sbList = new ArrayList<String>();
	
	String sql = "SELECT primary_sb " +
				 "FROM B_SB_RELATED_SB " +
				 "WHERE related_sb = ?  " +
				 "AND TYPE = 'PON_VDSL'  " +
				 "AND NVL(obsolete,'N') = 'N' " +
				 "ORDER BY 1 "; 

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("primary_sb");

			return dto;
		}
	};
	
	try {
		sbList = simpleJdbcTemplate.query(sql, mapper, sbNum);

	} catch (EmptyResultDataAccessException erdae) {
		logger.error("EmptyResultDataAccessException in getPonSBList()");

		sbList = null;
	} catch (Exception e) {
		logger.error("Exception caught in getPonSBList():", e);

		throw new DAOException(e.getMessage(), e);
	}
	
	return sbList;
	
};

public OnlineSalesRequestDTO getAddressDtl(String sb_no) throws DAOException{
	 
	logger.debug("getAddressDtl is called");
	
	String sql = 
	"	select bldg_nam housing_addr_en,bldg_nam housing_addr_ch," +
	"   sectdesc section_desc_en, sectdesc section_desc_ch, " +
	"   distdesc district_desc_en, distdesc district_desc_ch ,  " +
	"   areaname area_desc_en,  areaname area_desc_ch"+
	"	from b_addr_lkup where serbdyno='"+sb_no+"' AND addptype = 'I'";


	ParameterizedRowMapper<OnlineSalesRequestDTO> mapper = new ParameterizedRowMapper<OnlineSalesRequestDTO>() {

		public OnlineSalesRequestDTO mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			OnlineSalesRequestDTO dto = new OnlineSalesRequestDTO();
				
//			dto.setBuild_xy(rs.getString("build_xy"));
			dto.setHousing_addr_en(rs.getString("housing_addr_en"));
			dto.setHousing_addr_ch(rs.getString("housing_addr_ch"));
			dto.setSection_desc_en(rs.getString("section_desc_en"));
			dto.setSection_desc_ch(rs.getString("section_desc_ch"));
			dto.setDistrict_desc_en(rs.getString("district_desc_en"));
			dto.setDistrict_desc_ch(rs.getString("district_desc_ch"));
			dto.setArea_desc_en(rs.getString("area_desc_en"));
			dto.setArea_desc_ch(rs.getString("area_desc_ch"));
//			dto.setIs_premier(rs.getString("is_premier"));
//			dto.setPhHosInd(rs.getString("phhosind"));

			return dto;
		}
	};
	
	try {
		logger.debug("getAddressDtl @ ImsAddressInfoDAO: " + sql);
		List<OnlineSalesRequestDTO> preReg = simpleJdbcTemplate.query(sql, mapper);
		
		if(preReg!=null)
		
			return preReg.get(0);
		
		else 
			
			return null;
	
	} catch (Exception e) {
		logger.error("Exception caught in getAddressDtl()", e);
		throw new DAOException(e.getMessage(), e);
	}
	
}

}


