package com.bomwebportal.lts.dao.order;

import java.sql.Types;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.srvAccess.BmoDetailOutput;
import com.bomwebportal.lts.dto.srvAccess.BmoPermitDetail;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialInputDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceSlotDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceTypeDTO;

public class AppointmentDwfmDAO extends BaseDAO {

	private final Log logger = LogFactory.getLog(getClass());
	
	private static final String PRODUCT_DELIMITER = ";";
	
	
	public ResourceDetailOutputDTO getResourceDetail(ResourceDetailInputDTO pResourceInput) throws DAOException {

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$MVF")
				.withCatalogName("pkg_dfm_springboard_intf")
				.withProcedureName("get_multi_rsrc_dtls_by_date")
				.declareParameters(
						new SqlParameter("iv_prod_subtype_cd", Types.VARCHAR),
						new SqlParameter("iv_prod_type", Types.VARCHAR),
						new SqlParameter("iv_srv_type", Types.VARCHAR),
						new SqlParameter("iv_ord_type", Types.VARCHAR),
						new SqlParameter("iv_area", Types.VARCHAR),
						new SqlParameter("iv_district", Types.VARCHAR),
						new SqlParameter("iv_bldg_exch_bldg", Types.VARCHAR),
						new SqlParameter("iv_bldg_grid_id", Types.VARCHAR),
						new SqlParameter("iv_appt_date", Types.VARCHAR),
						new SqlParameter("iv_source_sys_id", Types.VARCHAR),
						new SqlParameter("iv_source_user_id", Types.VARCHAR),
						new SqlParameter("iv_fr_prod_subtype_cd", Types.VARCHAR),
						new SqlParameter("iv_to_srv_boundry", Types.VARCHAR),
						new SqlParameter("iv_addr_chg_ind", Types.VARCHAR),
						new SqlParameter("iv_delimiter", Types.VARCHAR),
						new SqlOutParameter("ov_appt_date", Types.VARCHAR),
						new SqlOutParameter("ov_appt_tslot", Types.VARCHAR),
						new SqlOutParameter("ov_appt_tslot_type", Types.VARCHAR),
						new SqlOutParameter("ov_rsrc_avail_ind", Types.VARCHAR),
						new SqlOutParameter("ov_restricted_timeslots", Types.VARCHAR),
						new SqlOutParameter("on_return_value", Types.INTEGER),
						new SqlOutParameter("on_error_code", Types.INTEGER),
						new SqlOutParameter("ov_error_msg", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		logger.debug("AppointmentDwfmDAO.getResourceDetail Input: " + pResourceInput);
		
		try {
			ResourceDetailOutputDTO rscOutput = this.generateResourceDetailOutput(jdbcCall.execute(this.generateResourceDetailInput(pResourceInput)));
			if(StringUtils.isNotBlank(rscOutput.getErrorMsg())){
				logger.debug("AppointmentDwfmDAO.getResourceDetail Output ERROR CD: " + rscOutput.getErrorCd());
				logger.debug("AppointmentDwfmDAO.getResourceDetail Output ERROR MSG: " + rscOutput.getErrorMsg());
			}
			if(rscOutput.getResourceSlots() != null){
				logger.debug("AppointmentDwfmDAO.getResourceDetail Output: " + rscOutput);
			}
			return rscOutput;
		} catch (Exception e) {
			logger.error("Exception caught in getResourceDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	private MapSqlParameterSource generateResourceDetailInput(ResourceDetailInputDTO pResourceInput) {
		
		ResourceTypeDTO[] rscType = pResourceInput.getRscTypes();
		StringBuilder subTypeSb = new StringBuilder();
		StringBuilder prodTypeSb = new StringBuilder();
		StringBuilder srvTypeSb = new StringBuilder();
		StringBuilder fromSubTypeSb = new StringBuilder();
		StringBuilder orderTypeSb = new StringBuilder();
		
		for (int i=0; rscType!=null && i<rscType.length; ++i) {
			subTypeSb.append(rscType[i].getProdSubTypeCd());
			subTypeSb.append(PRODUCT_DELIMITER);
			prodTypeSb.append(rscType[i].getProdType());
			prodTypeSb.append(PRODUCT_DELIMITER);
			srvTypeSb.append(rscType[i].getSrvType());
			srvTypeSb.append(PRODUCT_DELIMITER);
			fromSubTypeSb.append(rscType[i].getFromProdSubTypeCd());
			fromSubTypeSb.append(PRODUCT_DELIMITER);
			orderTypeSb.append(rscType[i].getOrderType());
			orderTypeSb.append(PRODUCT_DELIMITER);
		}
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("iv_prod_subtype_cd", subTypeSb.toString());
		inMap.addValue("iv_prod_type", prodTypeSb.toString());               
		inMap.addValue("iv_srv_type", srvTypeSb.toString());
		inMap.addValue("iv_ord_type", orderTypeSb.toString());
		inMap.addValue("iv_area", pResourceInput.getArea());
		inMap.addValue("iv_district", pResourceInput.getDistrict());
		inMap.addValue("iv_bldg_exch_bldg", pResourceInput.getExchBldg());
		inMap.addValue("iv_bldg_grid_id", pResourceInput.getGridId());
		inMap.addValue("iv_appt_date", pResourceInput.getApptDate());
		inMap.addValue("iv_source_sys_id", pResourceInput.getSystemId());
		inMap.addValue("iv_source_user_id", pResourceInput.getUser());
		inMap.addValue("iv_fr_prod_subtype_cd", fromSubTypeSb.toString());
		inMap.addValue("iv_to_srv_boundry", pResourceInput.getSrvBoundary());
		inMap.addValue("iv_addr_chg_ind", pResourceInput.getAddrChangeInd());
		inMap.addValue("iv_delimiter", PRODUCT_DELIMITER);
		
		logger.debug("AppointmentDwfmDAO.generateResourceDetailInput Input: " + inMap.getValues().toString());
		
		return inMap;
	}
	
	private ResourceDetailOutputDTO generateResourceDetailOutput(Map<String, Object> pResourceResultMap) {
		
		if ((Integer)pResourceResultMap.get("on_return_value") != 0) {
			ResourceDetailOutputDTO resourceOut = new ResourceDetailOutputDTO();
			resourceOut.setReturnValue(((Integer)pResourceResultMap.get("on_return_value")).toString());
			resourceOut.setErrorCd(((Integer)pResourceResultMap.get("on_error_code")).toString());
			resourceOut.setErrorMsg((String)pResourceResultMap.get("ov_error_msg"));
			return resourceOut;
		}
		String[] apptSlots = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_appt_tslot"), PRODUCT_DELIMITER);
		String[] apptSlotTypes = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_appt_tslot_type"), PRODUCT_DELIMITER);
		String[] availableInds = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_rsrc_avail_ind"), PRODUCT_DELIMITER);
		String[] restrictedSlots = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_restricted_timeslots"), PRODUCT_DELIMITER);
		ResourceDetailOutputDTO resourceReturn = new ResourceDetailOutputDTO();
		resourceReturn.setApptDate((String)pResourceResultMap.get("ov_appt_date"));
		ResourceSlotDTO[] rscSlots = new ResourceSlotDTO[apptSlots.length-1];
		
		for (int i=0; i<rscSlots.length; ++i) {
			rscSlots[i] = new ResourceSlotDTO();
			rscSlots[i].setApptTimeSlot(apptSlots[i]);
			rscSlots[i].setApptTimeSlotType(apptSlotTypes[i]);
			rscSlots[i].setAvailableInd(availableInds[i]);
			
			for (int j=0; restrictedSlots!=null && j<restrictedSlots.length; ++j) {
				if (StringUtils.equals(restrictedSlots[j], apptSlots[i])) {
					rscSlots[i].setRestrictInd("Y");
				}
			}
		}
		resourceReturn.setResourceSlots(rscSlots);
		return resourceReturn;
	}

    public PrebookAppointmentOutputDTO getPrebookAppointment(PrebookAppointmentInputDTO pPrebookInput) throws DAOException {	

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
						new SqlOutParameter("ov_error_msg", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		logger.debug("AppointmentDwfmDAO.getPrebookAppointment Input: " + pPrebookInput);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("iv_prod_subtype_cd", pPrebookInput.getProdSubTypeCd());
		inMap.addValue("iv_area", pPrebookInput.getArea());
		inMap.addValue("iv_district", pPrebookInput.getDistrict());
		inMap.addValue("iv_bldg_exch_bldg", pPrebookInput.getExchBldg());
		inMap.addValue("iv_bldg_grid_id", pPrebookInput.getGridId());
		inMap.addValue("iv_appt_start_date", pPrebookInput.getApptDateStartDate());
		inMap.addValue("iv_appt_end_date", pPrebookInput.getApptDateEndDate());
		inMap.addValue("iv_appt_type", pPrebookInput.getApptType());
		inMap.addValue("iv_source_sys_id", pPrebookInput.getSystemId());
		inMap.addValue("iv_source_user_id", pPrebookInput.getUser());	
		inMap.addValue("iv_commit_flag", "Y");
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getResourceDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
		PrebookAppointmentOutputDTO prebookAppointmentOutputDTO = new PrebookAppointmentOutputDTO();
		prebookAppointmentOutputDTO.setSerialNum((String)out.get("ov_serial_num"));
		prebookAppointmentOutputDTO.setReturnValue(out.get("on_return_value").toString());
		prebookAppointmentOutputDTO.setErrorCd(out.get("on_error_code").toString());
		prebookAppointmentOutputDTO.setErrorMsg((String)out.get("ov_error_msg"));
		logger.debug("AppointmentDwfmDAO.getPrebookAppointment Output: " + prebookAppointmentOutputDTO);
		return prebookAppointmentOutputDTO;
    }
	
    public CancelPrebookSerialOutputDTO cancelPrebookSerial(CancelPrebookSerialInputDTO pCancelPrebookInput) throws DAOException {
	
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
						new SqlOutParameter("ov_error_msg", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		logger.debug("AppointmentDwfmDAO.cancelPrebookSerial Input: " + pCancelPrebookInput);
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("iv_login_id", pCancelPrebookInput.getLoginID());
		inMap.addValue("iv_serial_num", pCancelPrebookInput.getSerialNum());
		inMap.addValue("iv_commit_flag", "Y");
		Map<String, Object> out = null;
		
		try {
			out = jdbcCall.execute(inMap);
		} catch (Exception e) {
			logger.error("Exception caught in getResourceDetail()", e);
			throw new DAOException(e.getMessage(), e);
		}
		CancelPrebookSerialOutputDTO cancelPrebookSerialOutputDTO = new CancelPrebookSerialOutputDTO();
		cancelPrebookSerialOutputDTO.setReturnValue(out.get("on_return_value").toString());
		cancelPrebookSerialOutputDTO.setErrorCd(out.get("on_error_code").toString());
		cancelPrebookSerialOutputDTO.setErrorMsg((String)out.get("ov_error_msg"));
		logger.debug("AppointmentDwfmDAO.cancelPrebookSerial Output: " + cancelPrebookSerialOutputDTO);
		return cancelPrebookSerialOutputDTO;
    }
    
    public BmoDetailOutput getBmoPermits(ResourceDetailInputDTO pResourceInput) throws DAOException {
    	
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
				.withSchemaName("OPS$MVF")
				.withCatalogName("pkg_dfm_springboard_intf")
				.withProcedureName("get_permit_property_dtl")
				.declareParameters(
						new SqlParameter("iv_srv_boundry", Types.VARCHAR),
						new SqlParameter("iv_prod_subtype_cd", Types.VARCHAR),
						new SqlParameter("iv_fr_prod_subtype_cd", Types.VARCHAR),
						new SqlParameter("iv_srv_type", Types.VARCHAR),
						new SqlParameter("iv_ord_type", Types.VARCHAR),
						new SqlParameter("iv_drgn_permit_days", Types.VARCHAR),
						new SqlParameter("iv_application_date", Types.VARCHAR),
						new SqlParameter("iv_addr_chg_ind", Types.VARCHAR),
						new SqlParameter("iv_prod_type", Types.VARCHAR),
						new SqlParameter("iv_delimiter", Types.VARCHAR),
						new SqlOutParameter("ov_prod_subtype_cd", Types.VARCHAR),
						new SqlOutParameter("ov_permit_lead_days", Types.VARCHAR),
						new SqlOutParameter("ov_earliest_appt_date", Types.VARCHAR),
						new SqlOutParameter("ov_alert_msg", Types.VARCHAR),
						new SqlOutParameter("ov_bmo_remark", Types.VARCHAR),
						new SqlOutParameter("on_return_value", Types.INTEGER),
						new SqlOutParameter("on_error_code", Types.INTEGER),
						new SqlOutParameter("ov_error_msg", Types.VARCHAR));
		jdbcCall.setAccessCallParameterMetaData(false);
		logger.debug("AppointmentDwfmDAO.getBmoPermits Input: " + pResourceInput);
		
		try {
			BmoDetailOutput bmoOutput =  this.generateBmoDetailOutput(jdbcCall.execute(this.generateResourceDetailInputBmo(pResourceInput)));
			logger.debug("AppointmentDwfmDAO.getBmoPermits Output: " + bmoOutput);
			return bmoOutput;
		} catch (Exception e) {
			logger.error("Exception caught in getBmoPermits()", e);
			throw new DAOException(e.getMessage(), e);
		}
    }
    
	private MapSqlParameterSource generateResourceDetailInputBmo(ResourceDetailInputDTO pResourceInput) {
		
		ResourceTypeDTO[] rscType = pResourceInput.getRscTypes();
		StringBuilder subTypeSb = new StringBuilder();
		StringBuilder srvTypeSb = new StringBuilder();
		StringBuilder fromSubTypeSb = new StringBuilder();
		StringBuilder orderTypeSb = new StringBuilder();
		StringBuilder srvBoundarySb = new StringBuilder();
		StringBuilder drgPermitDaysSb = new StringBuilder();
		StringBuilder addrChangeIndSb = new StringBuilder();
		StringBuilder prodTypeSb = new StringBuilder();
		
		for (int i=0; rscType!=null && i<rscType.length; ++i) {
			subTypeSb.append(rscType[i].getProdSubTypeCd());
			subTypeSb.append(PRODUCT_DELIMITER);
			srvTypeSb.append(rscType[i].getSrvType());
			srvTypeSb.append(PRODUCT_DELIMITER);
			fromSubTypeSb.append(rscType[i].getFromProdSubTypeCd());
			fromSubTypeSb.append(PRODUCT_DELIMITER);
			orderTypeSb.append(rscType[i].getOrderType());
			orderTypeSb.append(PRODUCT_DELIMITER);
			srvBoundarySb.append(pResourceInput.getSrvBoundary());
			srvBoundarySb.append(PRODUCT_DELIMITER);
			drgPermitDaysSb.append(pResourceInput.getDrgPermitDays());
			drgPermitDaysSb.append(PRODUCT_DELIMITER);
			addrChangeIndSb.append(pResourceInput.getAddrChangeInd());
			addrChangeIndSb.append(PRODUCT_DELIMITER);
			prodTypeSb.append(rscType[i].getProdType());
			prodTypeSb.append(PRODUCT_DELIMITER);
		}
		MapSqlParameterSource inMap = new MapSqlParameterSource();
		inMap.addValue("iv_prod_subtype_cd", subTypeSb.toString());               
		inMap.addValue("iv_srv_type", srvTypeSb.toString());
		inMap.addValue("iv_ord_type", orderTypeSb.toString());
		inMap.addValue("iv_application_date", pResourceInput.getApptDate());
		inMap.addValue("iv_fr_prod_subtype_cd", fromSubTypeSb.toString());
		inMap.addValue("iv_srv_boundry", srvBoundarySb.toString());
		inMap.addValue("iv_addr_chg_ind", addrChangeIndSb.toString());
		inMap.addValue("iv_drgn_permit_days", drgPermitDaysSb.toString());
		inMap.addValue("iv_prod_type", prodTypeSb.toString());
		inMap.addValue("iv_delimiter", PRODUCT_DELIMITER);
		return inMap;
	}
	
	private BmoDetailOutput generateBmoDetailOutput(Map<String, Object> pResourceResultMap) {
		
		if ((Integer)pResourceResultMap.get("on_return_value") != 0) {
			BmoDetailOutput bmoOut = new BmoDetailOutput();
			bmoOut.setReturnValue(((Integer)pResourceResultMap.get("on_return_value")).toString());
			bmoOut.setErrorCd(((Integer)pResourceResultMap.get("on_error_code")).toString());
			bmoOut.setErrorMsg((String)pResourceResultMap.get("ov_error_msg"));
			return bmoOut;
		}
		String[] prodSubTypeCds = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_prod_subtype_cd"), PRODUCT_DELIMITER);
		String[] permitLeadDays = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_permit_lead_days"), PRODUCT_DELIMITER);
		String[] earliestApptDates = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_earliest_appt_date"), PRODUCT_DELIMITER);
		String[] alertMsgs = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_alert_msg"), PRODUCT_DELIMITER);
		String[] bmoRemarks = StringUtils.splitPreserveAllTokens((String)pResourceResultMap.get("ov_bmo_remark"), PRODUCT_DELIMITER);
		BmoDetailOutput bmoOut = new BmoDetailOutput();
		BmoPermitDetail[] bmoPermitDtls = new BmoPermitDetail[prodSubTypeCds.length-1];
		
		for (int i=0; i<bmoPermitDtls.length; ++i) {
			bmoPermitDtls[i] = new BmoPermitDetail();
			bmoPermitDtls[i].setProdSubTypeCd(prodSubTypeCds[i]);
			bmoPermitDtls[i].setPermitLeadDays(permitLeadDays[i]);
			bmoPermitDtls[i].setEarliestApptDate(earliestApptDates[i]);
			bmoPermitDtls[i].setAlertMsg(alertMsgs[i]);
			bmoPermitDtls[i].setBmoRemark(bmoRemarks[i]);
		}
		bmoOut.setBmoDtls(bmoPermitDtls);
		return bmoOut;
	}
}
