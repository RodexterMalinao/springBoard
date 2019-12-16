package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.ims.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.AppointmentPermitPropertyDtlDTO;
import com.bomwebportal.ims.dto.AppointmentReserveDtlDTO;
import com.bomwebportal.ims.dto.AppointmentSubmitDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.google.gson.Gson;

public class AppointmentDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();	
	public AppointmentReserveDtlDTO getReserveDtl(
			String ProdSubTypeCd, String AreaCd, String DistCd, String ApptDate, String srvBoundry) throws DAOException{
		logger.debug("getReserveDtl is called");
		
		try{
			try {
				logger.debug("testing DFM datasource");
				
				getConnectionPoolInfo();
				
				String testSql = "select user||'@'||to_char(sysdate, 'yyyymmdd hh24:mi:ss') from dual";
				
				String testStr = (String) simpleJdbcTemplate.queryForObject(testSql,String.class);
				logger.info("test string:"+testStr);
				
				logger.debug("testing DFM datasource - end");
			} catch (Exception e) {
				logger.info(e.toString());
			}
			
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$MVF")
			.withCatalogName("pkg_dfm_springboard_intf")
			.withProcedureName("get_rsrc_dtls_by_date")
			.declareParameters(
					new SqlParameter("iv_prod_subtype_cd", Types.VARCHAR),
					new SqlParameter("iv_prod_type", Types.VARCHAR),
					new SqlParameter("iv_srv_type", Types.VARCHAR),
					new SqlParameter("iv_area", Types.VARCHAR),
					new SqlParameter("iv_district", Types.VARCHAR),
					new SqlParameter("iv_bldg_exch_bldg", Types.VARCHAR),
					new SqlParameter("iv_bldg_grid_id", Types.VARCHAR),
					new SqlParameter("iv_appt_date", Types.VARCHAR),
					new SqlParameter("iv_source_sys_id", Types.VARCHAR),
					new SqlParameter("iv_source_user_id", Types.VARCHAR),
					new SqlParameter("iv_srv_boundry", Types.VARCHAR),
					new SqlParameter("iv_ord_type", Types.VARCHAR),
					new SqlParameter("iv_fr_prod_subtype_cd", Types.VARCHAR),
					new SqlParameter("iv_addr_chg_ind", Types.VARCHAR),
					new SqlOutParameter("ov_rsrc_info", OracleTypes.CURSOR, new TimeSlotDetailMapper()),
					new SqlOutParameter("ov_restricted_timeslots", Types.VARCHAR),
					new SqlOutParameter("on_return_value", Types.INTEGER),
					new SqlOutParameter("on_error_code", Types.INTEGER),
					new SqlOutParameter("ov_error_msg", Types.VARCHAR)
					);
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iv_prod_subtype_cd",  ProdSubTypeCd);
			inMap.addValue("iv_prod_type",  "I");
			inMap.addValue("iv_srv_type",  "SO");
			inMap.addValue("iv_area", AreaCd);
			inMap.addValue("iv_district",  DistCd);
			inMap.addValue("iv_bldg_exch_bldg",  "");
			inMap.addValue("iv_bldg_grid_id",  "");
			inMap.addValue("iv_appt_date", ApptDate);
			inMap.addValue("iv_source_sys_id", "SPB");
			inMap.addValue("iv_source_user_id",  "DFMSPB");	
			inMap.addValue("iv_srv_boundry",  srvBoundry);
			inMap.addValue("iv_ord_type",  "I");
			inMap.addValue("iv_fr_prod_subtype_cd",  "");
			inMap.addValue("iv_addr_chg_ind",  "N");
			SqlParameterSource in = inMap;

			logger.info("get_rsrc_dtls_by_date input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.info("get_rsrc_dtls_by_date output:"+gson.toJson(out));
			
			
			AppointmentReserveDtlDTO reserveDtl = new AppointmentReserveDtlDTO();
			
			if((Integer)out.get("on_return_value")!=0){
				logger.info("get_rsrc_dtls_by_date returns error");
				throw new Exception("get_rsrc_dtls_by_date returns error");
			}else{
				//timeslots = (List)out.get("ov_rsrc_info");
				reserveDtl.setTimeslots((List)out.get("ov_rsrc_info"));
				reserveDtl.setRestrictedTimeslots((String)out.get("ov_restricted_timeslots"));
				reserveDtl.setReturnValue((Integer)out.get("on_return_value"));
				reserveDtl.setErrorCode((Integer)out.get("on_error_code"));
				if(out.get("ov_error_msg") == null){
					reserveDtl.setErrorMsg("");
				}else{
					reserveDtl.setErrorMsg((String)out.get("ov_error_msg"));
				}
				logger.info(reserveDtl.getErrorMsg());
			}
			
			return reserveDtl;
			
		}catch (Exception e) {
			logger.error("Exception caught in getReserveDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public AppointmentSubmitDTO submitAppointment(String ProdSubTypeCd, String AreaCd, String DistCd,
			String ApptStartDate, String ApptEndDate, String ApptType) throws DAOException{
		logger.debug("submitAppointment");
		
		try{
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$MVF")
			.withCatalogName("pkg_dfm_springboard_intf")
			.withProcedureName("submit_appointment")
			.declareParameters(
					new SqlParameter("iv_prod_subtype_cd", Types.VARCHAR),
					new SqlParameter("iv_area", Types.VARCHAR),
					new SqlParameter("iv_district", Types.VARCHAR),
					new SqlParameter("iv_bldg_exch_bldg", Types.VARCHAR),
					new SqlParameter("iv_bldg_grid_id", Types.VARCHAR),
					new SqlParameter("iv_appt_start_date", Types.VARCHAR),
					new SqlParameter("iv_appt_end_date", Types.VARCHAR),
					new SqlParameter("iv_appt_type", Types.VARCHAR),
					new SqlParameter("iv_source_sys_id", Types.VARCHAR),
					new SqlParameter("iv_source_user_id", Types.VARCHAR),
					new SqlParameter("iv_commit_flag", Types.VARCHAR),
					new SqlOutParameter("ov_serial_num", Types.VARCHAR),
					new SqlOutParameter("on_return_value", Types.INTEGER),
					new SqlOutParameter("on_error_code", Types.INTEGER),
					new SqlOutParameter("ov_error_msg", Types.VARCHAR)
					);
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iv_prod_subtype_cd",  ProdSubTypeCd);
			inMap.addValue("iv_area", AreaCd);
			inMap.addValue("iv_district",  DistCd);
			inMap.addValue("iv_bldg_exch_bldg",  "");
			inMap.addValue("iv_bldg_grid_id",  "");
			inMap.addValue("iv_appt_start_date", ApptStartDate);
			inMap.addValue("iv_appt_end_date", ApptEndDate);
			inMap.addValue("iv_appt_type", ApptType);
			inMap.addValue("iv_source_sys_id", "SPB");
			inMap.addValue("iv_source_user_id",  "DFMSPB");
			inMap.addValue("iv_commit_flag",  "Y");
			SqlParameterSource in = inMap;

			logger.info("submitAppointment human input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.info("submitAppointment output:"+gson.toJson(out));
			
			AppointmentSubmitDTO appointmentSubmit = new AppointmentSubmitDTO();
			
			appointmentSubmit.setReturnValue((Integer)out.get("on_return_value"));
			appointmentSubmit.setErrorCode((Integer)out.get("on_error_code"));
			appointmentSubmit.setErrorMsg((String)out.get("ov_error_msg"));
			appointmentSubmit.setSerialNum((String)out.get("ov_serial_num"));
			
			return appointmentSubmit;
			
		}catch (Exception e){
			logger.error("Exception caught in submitAppointment()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	public void cancelPrebookSerial(String SerialNum) throws DAOException{
		logger.debug("cancelPrebookSerial");
		
		try{
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$MVF")
			.withCatalogName("pkg_dfm_springboard_intf")
			.withProcedureName("cancel_prebook_serial")
			.declareParameters(
					new SqlParameter("iv_login_id", Types.VARCHAR),
					new SqlParameter("iv_serial_num", Types.VARCHAR),
					new SqlParameter("iv_commit_flag", Types.VARCHAR),					
					new SqlOutParameter("on_return_value", Types.INTEGER),
					new SqlOutParameter("on_error_code", Types.INTEGER),
					new SqlOutParameter("ov_error_msg", Types.VARCHAR)
					);
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iv_login_id",  "DFMSPB");
			inMap.addValue("iv_serial_num", SerialNum);
			inMap.addValue("iv_commit_flag",  "Y");		
			SqlParameterSource in = inMap;

			logger.info("cancelPrebookSerial human input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.info("cancelPrebookSerial output:"+gson.toJson(out));
						
			
			if((Integer)out.get("on_return_value")!=0){
				logger.info("cancelPrebookSerial returns error");
				throw new Exception("cancelPrebookSerial returns error, " +
						"on_return_value:"+out.get("on_return_value")+", "+
						"on_error_code:"+out.get("on_error_code")+", "+
						"ov_error_msg:"+out.get("ov_error_msg"));
			}
			
		}catch (Exception e){
			logger.error("Exception caught in cancelPrebookSerial()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	public final class TimeSlotDetailMapper implements RowMapper {
	    
	    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	AppointmentTimeSlotDTO dto = new AppointmentTimeSlotDTO();
	    	dto.setApptDate(rs.getString("appt_date"));
	    	dto.setTimeSlot(rs.getString("appt_tslot"));
	    	dto.setSlotType(rs.getString("appt_tslot_type"));
	    	dto.setResourceInMinute(rs.getString("resc"));
	        return dto;
	    }
	    
	}
	
	public AppointmentPermitPropertyDtlDTO getPermitPropertyDtl(
			String srvBoundry, String ProdSubTypeCd, String applicationDate) throws DAOException{
		
		try{
			
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
			.withSchemaName("OPS$MVF")
			.withCatalogName("pkg_dfm_springboard_intf")
			.withProcedureName("GET_PERMIT_PROPERTY_DTL")
			.declareParameters(
					new SqlParameter("iv_srv_boundry", Types.VARCHAR),
					new SqlParameter("iv_to_prod_subtype_cd", Types.VARCHAR),
					new SqlParameter("iv_fr_prod_subtype_cd", Types.VARCHAR),
					new SqlParameter("iv_srv_type", Types.VARCHAR),
					new SqlParameter("iv_ord_type", Types.VARCHAR),
					new SqlParameter("iv_drgn_permit_days", Types.VARCHAR),
					new SqlParameter("iv_application_date", Types.VARCHAR),
					new SqlParameter("iv_addr_chg_ind", Types.VARCHAR),
					new SqlParameter("iv_prod_type", Types.VARCHAR),
					new SqlParameter("iv_delimiter", Types.VARCHAR),
					new SqlOutParameter("ov_prod_tubtype_cd", Types.VARCHAR),
					new SqlOutParameter("ov_permit_lead_days", Types.VARCHAR),
					new SqlOutParameter("ov_earliest_appt_date", Types.VARCHAR),
					new SqlOutParameter("ov_alert_msg", Types.VARCHAR),
					new SqlOutParameter("ov_bmo_remark", Types.VARCHAR),
					new SqlOutParameter("on_return_value", Types.INTEGER),
					new SqlOutParameter("on_error_code", Types.INTEGER),
					new SqlOutParameter("ov_error_msg", Types.VARCHAR)
					);
			
			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iv_srv_boundry",  srvBoundry);
			inMap.addValue("iv_to_prod_subtype_cd",  ProdSubTypeCd);
			inMap.addValue("iv_fr_prod_subtype_cd",  "");
			inMap.addValue("iv_srv_type",  "SO");
			inMap.addValue("iv_ord_type",  "I");
			inMap.addValue("iv_drgn_permit_days",  "0");
			inMap.addValue("iv_application_date", applicationDate);
			inMap.addValue("iv_addr_chg_ind",  "N");
			inMap.addValue("iv_prod_type",  "I");
			inMap.addValue("iv_delimiter",  "|");
			SqlParameterSource in = inMap;

			logger.info("getPermitPropertyDtl human input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.info("getPermitPropertyDtl output:"+gson.toJson(out));
			
			//String earliestApptDay = null;
			AppointmentPermitPropertyDtlDTO appointmentPermitPropertyDtl = new AppointmentPermitPropertyDtlDTO();

			if((Integer)out.get("on_return_value")!=0){
				logger.info("get_rsrc_dtls_by_date returns error");
				throw new Exception("get_rsrc_dtls_by_date returns error");
			}else{
				//timeslots = (List)out.get("ov_rsrc_info");				
				appointmentPermitPropertyDtl.setProdTubtypeCd((String)out.get("ov_prod_tubtype_cd"));
				appointmentPermitPropertyDtl.setPermitLeadDays((String)out.get("ov_permit_lead_days"));
				appointmentPermitPropertyDtl.setEarliestApptDate((String)out.get("ov_earliest_appt_date"));
				appointmentPermitPropertyDtl.setAlertMsg((String)out.get("ov_alert_msg"));
				appointmentPermitPropertyDtl.setBmoRemark((String)out.get("ov_bmo_remark"));
				appointmentPermitPropertyDtl.setReturnValue((Integer)out.get("on_return_value"));
				appointmentPermitPropertyDtl.setErrorCode((Integer)out.get("on_error_code"));
				appointmentPermitPropertyDtl.setErrorMsg((String)out.get("ov_error_msg"));
			}
			/*
			if("FTHI".equals(ProdSubTypeCd)){
				appointmentPermitPropertyDtl.setEarliestApptDate("20120213");
			}
			*/
			if(appointmentPermitPropertyDtl.getPermitLeadDays()!=null && 
					appointmentPermitPropertyDtl.getPermitLeadDays().length()>0){
				int _leadDay = Integer.parseInt(appointmentPermitPropertyDtl.getPermitLeadDays()) + 1;
				appointmentPermitPropertyDtl.setPermitLeadDays(String.valueOf(_leadDay));
			}
			
			return appointmentPermitPropertyDtl;
			
		}catch (Exception e) {
			logger.error("Exception caught in getPermitPropertyDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//// for ret/temp
	
	
	public AppointmentReserveDtlDTO getReserveDtl(String toProdSubTypeCd,
			String frProdSubTypeCd, String AreaCd, String DistCd,
			String ApptDate, String srvBoundry, String orderType,
			String changeAddr) throws DAOException {
		logger.info("getReserveDtl is called");
		logger.info("AreaCd:" + AreaCd+" \tDistCd:" + DistCd+" \tApptDate:" + ApptDate+" \tSrvBoundry:" + srvBoundry);

		logger.info("b4    toProdSubTypeCd:" + toProdSubTypeCd+" \tfrProdSubTypeCd:" + frProdSubTypeCd+" \torderType:" + orderType+" \tchangeAddr:" + changeAddr);

		if (orderType == null) {
			orderType = "C";
		}
		if (changeAddr == null) {
			changeAddr = "N";
		}
		if (frProdSubTypeCd == null) {
			frProdSubTypeCd = "PCDC";
		}
		if (toProdSubTypeCd == null) {
			toProdSubTypeCd = "PCDC";
		}

		logger.info("after toProdSubTypeCd:" + toProdSubTypeCd+" \tfrProdSubTypeCd:" + frProdSubTypeCd+" \torderType:" + orderType+" \tchangeAddr:" + changeAddr);

		try {
			try {
				logger.info("testing DFM datasource");

				getConnectionPoolInfo();

				String testSql = "select user||'@'||to_char(sysdate, 'yyyymmdd hh24:mi:ss') from dual";

				String testStr = (String) simpleJdbcTemplate.queryForObject(
						testSql, String.class);
				logger.info("test string:" + testStr);

				logger.info("testing DFM datasource - end");
			} catch (Exception e) {
				logger.info(e.toString());
			}


			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$MVF")
					.withCatalogName("pkg_dfm_springboard_intf")
					.withProcedureName("get_rsrc_dtls_by_date")
					.declareParameters(
							new SqlParameter("iv_prod_subtype_cd",Types.VARCHAR),
							new SqlParameter("iv_prod_type", Types.VARCHAR),
							new SqlParameter("iv_srv_type", Types.VARCHAR),
							new SqlParameter("iv_area", Types.VARCHAR),
							new SqlParameter("iv_district", Types.VARCHAR),
							new SqlParameter("iv_bldg_exch_bldg", Types.VARCHAR),
							new SqlParameter("iv_bldg_grid_id", Types.VARCHAR),
							new SqlParameter("iv_appt_date", Types.VARCHAR),
							new SqlParameter("iv_source_sys_id", Types.VARCHAR),
							new SqlParameter("iv_source_user_id", Types.VARCHAR),
							new SqlParameter("iv_srv_boundry", Types.VARCHAR),
							new SqlParameter("iv_ord_type", Types.VARCHAR),
							new SqlParameter("iv_fr_prod_subtype_cd",Types.VARCHAR),
							new SqlParameter("iv_addr_chg_ind", Types.VARCHAR),
							new SqlOutParameter("ov_rsrc_info",OracleTypes.CURSOR, new TimeSlotDetailMapper()),
							new SqlOutParameter("ov_restricted_timeslots",Types.VARCHAR),
							new SqlOutParameter("on_return_value",Types.INTEGER),
							new SqlOutParameter("on_error_code", Types.INTEGER),
							new SqlOutParameter("ov_error_msg", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iv_prod_subtype_cd", toProdSubTypeCd);
			inMap.addValue("iv_prod_type", "I");
			inMap.addValue("iv_srv_type", "SO");
			inMap.addValue("iv_area", AreaCd);
			inMap.addValue("iv_district", DistCd);
			inMap.addValue("iv_bldg_exch_bldg", "");
			inMap.addValue("iv_bldg_grid_id", "");
			inMap.addValue("iv_appt_date", ApptDate);
			inMap.addValue("iv_source_sys_id", "SPB");
			inMap.addValue("iv_source_user_id", "DFMSPB");
			inMap.addValue("iv_srv_boundry", srvBoundry);
			inMap.addValue("iv_ord_type", orderType);
			inMap.addValue("iv_fr_prod_subtype_cd", frProdSubTypeCd);
			inMap.addValue("iv_addr_chg_ind", changeAddr);
			SqlParameterSource in = inMap;

			logger.debug("getReserveDtl human input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.debug("getReserveDtl output:"+gson.toJson(out));

			AppointmentReserveDtlDTO reserveDtl = new AppointmentReserveDtlDTO();

			if ((Integer) out.get("on_return_value") != 0) {
				logger.info("get_rsrc_dtls_by_date returns error");
				throw new Exception("get_rsrc_dtls_by_date returns error");
			} else {
				// timeslots = (List)out.get("ov_rsrc_info");
				reserveDtl.setTimeslots((List) out.get("ov_rsrc_info"));
				reserveDtl.setRestrictedTimeslots((String) out.get("ov_restricted_timeslots"));
				reserveDtl.setReturnValue((Integer) out.get("on_return_value"));
				reserveDtl.setErrorCode((Integer) out.get("on_error_code"));
				if (out.get("ov_error_msg") == null) {
					reserveDtl.setErrorMsg("");
				} else {
					reserveDtl.setErrorMsg((String) out.get("ov_error_msg"));
				}
				logger.info(reserveDtl.getErrorMsg());
			}

			return reserveDtl;

		} catch (Exception e) {
			logger.error("Exception caught in getReserveDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	
	
	
	
	public AppointmentPermitPropertyDtlDTO getPermitPropertyDtl(
			String srvBoundry, String toProdSubTypeCd, String frProdSubTypeCd,
			String applicationDate, String orderType, String changeAddr)
			throws DAOException {
		logger.info("getPermitPropertyDtl is called SrvBoundry:" + srvBoundry+" \tapplicationDate:" + applicationDate+" \torderType:"+orderType);
		logger.info("b4    toProdSubTypeCd:" + toProdSubTypeCd+" \tfrProdSubTypeCd:" + frProdSubTypeCd+" \torderType:" + orderType+" \tchangeAddr:" + changeAddr);

		if (orderType == null) {
			orderType = "C";
		}
		if (changeAddr == null) {
			changeAddr = "N";
		}
		if (frProdSubTypeCd == null) {
			frProdSubTypeCd = "PCDC";
		}
		if (toProdSubTypeCd == null) {
			toProdSubTypeCd = "PCDC";
		}

		logger.info("after toProdSubTypeCd:" + toProdSubTypeCd+" \tfrProdSubTypeCd:" + frProdSubTypeCd+" \torderType:" + orderType+" \tchangeAddr:" + changeAddr);

		try {
//			logger.info("Start time - getPermitPropertyDtl: " + starttime);

			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					.withSchemaName("OPS$MVF")
					.withCatalogName("pkg_dfm_springboard_intf")
					.withProcedureName("GET_PERMIT_PROPERTY_DTL")
					.declareParameters(	
							new SqlParameter("iv_srv_boundry", Types.VARCHAR),
							new SqlParameter("iv_to_prod_subtype_cd",Types.VARCHAR),
							new SqlParameter("iv_fr_prod_subtype_cd",Types.VARCHAR),
							new SqlParameter("iv_srv_type", Types.VARCHAR),
							new SqlParameter("iv_ord_type", Types.VARCHAR),
							new SqlParameter("iv_drgn_permit_days",Types.VARCHAR),
							new SqlParameter("iv_application_date",Types.VARCHAR),
							new SqlParameter("iv_addr_chg_ind", Types.VARCHAR),
							new SqlParameter("iv_prod_type", Types.VARCHAR),
							new SqlParameter("iv_delimiter", Types.VARCHAR),
							new SqlOutParameter("ov_prod_tubtype_cd",Types.VARCHAR),
							new SqlOutParameter("ov_permit_lead_days",Types.VARCHAR),
							new SqlOutParameter("ov_earliest_appt_date",Types.VARCHAR),
							new SqlOutParameter("ov_alert_msg", Types.VARCHAR),
							new SqlOutParameter("ov_bmo_remark", Types.VARCHAR),
							new SqlOutParameter("on_return_value",Types.INTEGER),
							new SqlOutParameter("on_error_code", Types.INTEGER),
							new SqlOutParameter("ov_error_msg", Types.VARCHAR));

			MapSqlParameterSource inMap = new MapSqlParameterSource();
			inMap.addValue("iv_srv_boundry", srvBoundry);
			inMap.addValue("iv_to_prod_subtype_cd", toProdSubTypeCd);
			inMap.addValue("iv_fr_prod_subtype_cd", frProdSubTypeCd);
			inMap.addValue("iv_srv_type", "SO");
			inMap.addValue("iv_ord_type", orderType);
			inMap.addValue("iv_drgn_permit_days", "0");
			inMap.addValue("iv_application_date", applicationDate);
			inMap.addValue("iv_addr_chg_ind", changeAddr);
			inMap.addValue("iv_prod_type", "I");
			inMap.addValue("iv_delimiter", "|");
			SqlParameterSource in = inMap;

			logger.info("getPermitPropertyDtl human input:"+gson.toJson(in));
			Map out = jdbcCall.execute(in);
			logger.info("getPermitPropertyDtl output:"+gson.toJson(out));

			// String earliestApptDay = null;
			AppointmentPermitPropertyDtlDTO appointmentPermitPropertyDtl = new AppointmentPermitPropertyDtlDTO();

			if ((Integer) out.get("on_return_value") != 0) {
				logger.info("get_rsrc_dtls_by_date returns error");
				throw new Exception("get_rsrc_dtls_by_date returns error");
			} else {
				// timeslots = (List)out.get("ov_rsrc_info");
				appointmentPermitPropertyDtl.setProdTubtypeCd((String) out.get("ov_prod_tubtype_cd"));
				appointmentPermitPropertyDtl.setPermitLeadDays((String) out.get("ov_permit_lead_days"));
				appointmentPermitPropertyDtl.setEarliestApptDate((String) out.get("ov_earliest_appt_date"));
				appointmentPermitPropertyDtl.setAlertMsg((String) out.get("ov_alert_msg"));
				appointmentPermitPropertyDtl.setBmoRemark((String) out.get("ov_bmo_remark"));
				appointmentPermitPropertyDtl.setReturnValue((Integer) out.get("on_return_value"));
				appointmentPermitPropertyDtl.setErrorCode((Integer) out.get("on_error_code"));
				appointmentPermitPropertyDtl.setErrorMsg((String) out.get("ov_error_msg"));
			}
			if (appointmentPermitPropertyDtl.getPermitLeadDays() != null
					&& appointmentPermitPropertyDtl.getPermitLeadDays()
							.length() > 0) {
				int _leadDay = Integer.parseInt(appointmentPermitPropertyDtl
						.getPermitLeadDays()) + 1;
				appointmentPermitPropertyDtl.setPermitLeadDays(String
						.valueOf(_leadDay));
			}

			return appointmentPermitPropertyDtl;

		} catch (Exception e) {
			logger.error("Exception caught in getPermitPropertyDtl()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
}

