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

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AmendWqDTO;
import com.bomwebportal.ims.dto.AppointmentDTO;
import com.bomwebportal.ims.dto.CustAddrDTO;
import com.bomwebportal.ims.dto.DelayReasonDTO;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ImsAutoSyncWQDTO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;
import com.bomwebportal.ims.dto.OrderDTO;
import com.bomwebportal.ims.dto.RemarkDTO;
import com.bomwebportal.ims.dto.report.RptServiceInfoDTO;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.dto.ui.CcLtsImsOrderEnquiryUI;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.exception.DAOException;
import com.google.gson.Gson;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImsOrderAmendDAO extends BaseDAO{	
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();	
	
	public String getIsRetPreRenew (String orderId)	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		String SQL = 
				/*" SELECT   DECODE (COUNT ( * ), 0, 'N', 'Y') is_ret_pre_renew" +
				"	  FROM   BOMWEB_SUBSCRIBED_ITEM a, w_basket_item_assgn b, W_ITEM_DTL_IMS c" +
				"	 WHERE       a.ORDER_ID = :orderId " +
				"	         AND a.basket_id = b.basket_id" +
				"	         AND b.item_id = c.item_id" +
				"	         AND IS_PRE_RENEW = 'Y'  " ;;*/
			" select DECODE (COUNT ( * ), 0, 'N', 'Y') is_ret_pre_renew " +
			" from bomweb_order_ims where oRDER_ID =:orderId and pre_renew_ind = 'Y' ";
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", orderId);

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("is_ret_pre_renew");
			return dto;
		}
	};

	try {
		logger.debug("getIsRetPreRenew: " + params.getValues());
		result = simpleJdbcTemplate.query(SQL, mapper,params);		
		logger.debug("getIsRetPreRenew result:"+gson.toJson(result));

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.error("Exception caught in getIsRetPreRenew():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		if(result==null||result.size()<1){
			return "N";
		}else{
			return result.get(0);
		}
	}	
	       
	
	public List<DelayReasonDTO> getCancelReason ()
	throws DAOException {
		List<DelayReasonDTO> result = new ArrayList<DelayReasonDTO>();
		
		String SQL = 
			" select DESCRIPTION, code  from w_code_lkup " +
			" where grp_id = 'SB_IMS_AMEND_CAN_CD'  " ;

	ParameterizedRowMapper<DelayReasonDTO> mapper = new ParameterizedRowMapper<DelayReasonDTO>() {
		public DelayReasonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			DelayReasonDTO dto = new DelayReasonDTO();
			dto.setDelayReason((String) rs.getString("DESCRIPTION"));
			dto.setDelayReasonCode((String) rs.getString("code"));
			return dto;
		}
	};

	try {
		logger.debug("getCancelReason SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getCancelReason():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	
	public List<String> getOrderStatusList ()
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		String SQL = 
			" SELECT distinct description" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'IMS_SB_ORDER_STATUS'  " ;

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("DESCRIPTION");
			return dto;
		}
	};

	try {
		logger.debug("getOrderStatusList SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderStatusList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
	
	public String getOrgStaffID (String staffId)	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		String SQL = " select org_staff_id from " +
				" bomweb_sales_profile " +
				" where staff_id = :staffId " +
				" and rownum = 1 " +
				" order by end_date desc nulls first   " ;
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("staffId", staffId);

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String dto = new String();
			dto = (String) rs.getString("org_staff_id");
			return dto;
		}
	};

	try {
		logger.debug("getOrgStaffID SQL: " + SQL);
		logger.debug("key search: " + params.getValues());
		result = simpleJdbcTemplate.query(SQL, mapper,params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderStatusList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		if(result==null||result.size()<1){
			return staffId;
		}else{
			return result.get(0);
		}
	}	
	
	public List<AmendWqDTO> getAmendNature (String system)
	throws DAOException {
		List<AmendWqDTO> result = new ArrayList<AmendWqDTO>();

		String SQL = 
			" SELECT   WQ_NATURE_DESC, wq_nature_id " +
			"    FROM q_wq_nature" +
			"   WHERE wq_nature_type IN ('AMEND_CATEGORY_IMS', 'AMEND_CATEGORY')" +
			"   and wq_nature_id>0" +
			"   and HIDDEN_IND is null" +
		    "	AND WQ_NATURE_ID NOT IN (SELECT CODE FROM w_code_lkup WHERE grp_id = 'IMS_FS_AMEND_UNUSED_WQ')  " ;
		if(system.contains("ACQ")){
			SQL+="	AND WQ_NATURE_ID NOT IN (SELECT CODE FROM w_code_lkup WHERE grp_id = 'IMS_ACQ_UNUSED_WQ')  " ;
		}
		SQL+="	ORDER BY wq_nature_id  " ;
		
	ParameterizedRowMapper<AmendWqDTO> mapper = new ParameterizedRowMapper<AmendWqDTO>() {
		public AmendWqDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AmendWqDTO dto = new AmendWqDTO();
			dto.setWqNatureDesc((String) rs.getString("WQ_NATURE_DESC"));
			dto.setWqNatureId((String) rs.getString("wq_nature_id"));
			return dto;
		}
	};

	try {
		logger.debug("getAmendNature SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL, mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getAmendNature():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}	
		

	public void insertBomwebAmendCategory(AmendOrderImsUI amend, String rmk, String isNowRetAmendTvOnly) throws DAOException{
		logger.info("insertBomwebAmendCategory no WQdto amend: " + gson.toJson(amend));
		
		String SQL = "	Insert into BOMWEB_AMEND_CATEGORY" +
				"   (ORDER_ID, DTL_ID, AMEND_SEQ, " +
				"   NATURE, CC_TYPE, CC_NUM, CC_HOLD_NAME, CC_EXP_DATE, FAX_SERIAL, CC_THIRD_PARTY_IND, " +
				"   APPNT_START_TIME, APPNT_END_TIME, SERIAL_NUM, DELAY_REASON_CD, " +
//				"   CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, SEND, RMK, WQ_BATCH_ID, EMAIL_ADDR, LOGIN_ID )" +
				"   CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE, SEND, RMK, WQ_BATCH_ID, EMAIL_ADDR, LOGIN_ID, TARGET_COMM_DATE, MOBILE_NUM)" +
				" Values" +
				"   (:orderId, '1', (select nvl(max(amend_seq),0)+1 from BOMWEB_AMEND_CATEGORY where order_id = :orderId ), " +
				"   :NATURE, :CC_TYPE, :CC_NUM, :CC_HOLD_NAME, :CC_EXP_DATE, :FAX_SERIAL, :CC_THIRD_PARTY_IND,  " +
				"   :APPNT_START_TIME, :APPNT_END_TIME, :SERIAL_NUM, :DELAY_REASON_CD, " +
//				"   :CREATE_BY, sysdate, :LAST_UPD_BY, sysdate, :send, :RMK, :WQ_BATCH_ID, :EMAIL_ADDR, :LOGIN_ID) ";
				"   :CREATE_BY, sysdate, :LAST_UPD_BY, sysdate, 'N', :RMK, :WQ_BATCH_ID, :EMAIL_ADDR, :LOGIN_ID, :TARGET_COMM_DATE, :MOBILE_NUM) ";
		
		Date appStartTime = new Date();
		Date appEndTime = new Date();
		Date targetCommDate = new Date();
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
			if(!"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())&&!"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd())
					||(("Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())||"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd()))
							&&"Y".equalsIgnoreCase(amend.getIsPreInstApptEnabled()))){
			logger.debug("OrderImsUI getTargetInstallDate:"+amend.getOrderImsUI().getAppointment().getTargetInstallDate());
			logger.debug("amend getTimeSlot:"+amend.getTimeSlot());
			SimpleDateFormat formatter =null;
			try{
				if("Y".equals(amend.getCanAmendAppointment())){
					formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm");
					appStartTime = formatter.parse(amend.getOrderImsUI().getAppointment().getTargetInstallDate()+" "+amend.getTimeSlot().substring(0, 5));
					appEndTime = formatter.parse(amend.getOrderImsUI().getAppointment().getTargetInstallDate()+" "+amend.getTimeSlot().substring(6, 11));
				}else{
					formatter = new SimpleDateFormat("yyyy/MM/dd");
					appStartTime = formatter.parse(amend.getOrderImsUI().getAppointment().getTargetInstallDate());
					appEndTime = formatter.parse(amend.getOrderImsUI().getAppointment().getTargetInstallDate());
				}
				logger.debug("appStartTime:"+appStartTime);
				logger.debug("appEndTime:"+appEndTime);
			  }catch(ParseException e){
				  logger.debug("unparseable using" + formatter);
			 }
			}else{
				appStartTime = null;
				appEndTime = null;
				amend.setDelayReasonCode(null);
			}
			
			if(("Y".equalsIgnoreCase(amend.getOrderImsUI().getPreInstallInd())||"Y".equalsIgnoreCase(amend.getOrderImsUI().getPreUseInd()))
					&&"Y".equalsIgnoreCase(amend.getIsPreInstCommDateEnabled())){
				SimpleDateFormat formatter =null;
				try{
					
					formatter = new SimpleDateFormat("yyyy/MM/dd");
					targetCommDate = formatter.parse(amend.getOrderImsUI().getAppointment().getTargetCommDate());
					
				}catch(ParseException e){
					  logger.debug("unparseable using" + formatter);
				}
			}else{
				targetCommDate = null;
			}
		}else{
			targetCommDate = null;
		}


		int insertTimes = 1;
		if(amend.getAutoWQCount()>0){
			insertTimes = amend.getAutoWQCount();
		}
		logger.info("amend.getWqNatureIDList(): " + gson.toJson(amend.getWqNatureIDList()));
		for(int i = 0;i<insertTimes;i++){
			MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("orderId", amend.getOrderImsUI().getOrderId());
				params.addValue("CREATE_BY", amend.getCreatedBy());
				params.addValue("LAST_UPD_BY", amend.getCreatedBy());
				if(ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE.equals(amend.getWqNatureIDList().get(i))){
					if("Y".equals(isNowRetAmendTvOnly)){
						params.addValue("send", "P");
					}else{
						params.addValue("send", null);						
					}
				}else{
					params.addValue("send", "N");
				}
				if(i==0){
					params.addValue("RMK", rmk);
				}else{
					params.addValue("RMK", "");
				}
	
				params.addValue("CC_TYPE", amend.getCcType());
				params.addValue("CC_NUM", amend.getCcNum());
				params.addValue("CC_HOLD_NAME", amend.getCcHolderName());
				params.addValue("CC_EXP_DATE", amend.getCcExpDate());
				params.addValue("CC_THIRD_PARTY_IND", amend.getThirdPartyInd());
				params.addValue("FAX_SERIAL", amend.getFaxSerialNum());
				
				params.addValue("APPNT_START_TIME", appStartTime);
				params.addValue("APPNT_END_TIME", appEndTime);
				params.addValue("SERIAL_NUM", amend.getPreBkSerialNum());
				params.addValue("DELAY_REASON_CD", amend.getDelayReasonCode());

				params.addValue("EMAIL_ADDR", amend.getContactEmail());
				params.addValue("LOGIN_ID", amend.getLoginId());

				params.addValue("TARGET_COMM_DATE", targetCommDate);
				params.addValue("MOBILE_NUM", amend.getContactMobile());
				
				 if(amend.getWqNatureIDList()!=null 
						 && amend.getWqNatureIDList().size()==1 
						 && amend.getWqNatureIDList().get(0)==ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD){
						params.addValue("CC_TYPE", amend.getTimeSlotType());// time slot type put to CC TYPE if Auto 
				 }
	
				if("Y".equalsIgnoreCase(amend.getIsCancelOrderEnabled())){
					params.addValue("SERIAL_NUM", amend.getCancelReasonCode());
				}
				
				if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD.equals(amend.getWqNatureIDList().get(i))){
					params.addValue("NATURE", amend.getWqNatureIDList().get(i));
					params.addValue("CC_TYPE", amend.getTimeSlotType());// time slot type put to CC TYPE if Auto 
					params.addValue("CC_NUM", null);
					params.addValue("CC_HOLD_NAME", null);
					params.addValue("CC_EXP_DATE", null);
					params.addValue("CC_THIRD_PARTY_IND", null);
					params.addValue("FAX_SERIAL", null);
					params.addValue("EMAIL_ADDR", null);
					params.addValue("MOBILE_NUM", null);
					params.addValue("LOGIN_ID", null);
//					params.addValue("WQ_BATCH_ID", getLatestAmendWqBatchId(amend.getOrderImsUI().getOrderId(), amend.getWqNatureIDList().get(i)));
					params.addValue("WQ_BATCH_ID", null);
				}else if(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC.equals(amend.getWqNatureIDList().get(i))){
					params.addValue("NATURE", amend.getWqNatureIDList().get(i));
					params.addValue("APPNT_START_TIME", null);
					params.addValue("APPNT_END_TIME", null);
					params.addValue("SERIAL_NUM", null);
					params.addValue("DELAY_REASON_CD", null);
					params.addValue("EMAIL_ADDR", null);
					params.addValue("MOBILE_NUM", null);
					params.addValue("LOGIN_ID", null);
//					params.addValue("WQ_BATCH_ID", getLatestAmendWqBatchId(amend.getOrderImsUI().getOrderId(), amend.getWqNatureIDList().get(i)));
					params.addValue("WQ_BATCH_ID", null);
				}else if(ImsConstants.IMS_AMEND_WQ_NATURE_ChangeLoginId.equals(amend.getWqNatureIDList().get(i))){
					params.addValue("NATURE", amend.getWqNatureIDList().get(i));
					params.addValue("CC_TYPE", null);
					params.addValue("CC_NUM", null);
					params.addValue("CC_HOLD_NAME", null);
					params.addValue("CC_EXP_DATE", null);
					params.addValue("CC_THIRD_PARTY_IND", null);
					params.addValue("FAX_SERIAL", null);
					params.addValue("EMAIL_ADDR", null);
					params.addValue("MOBILE_NUM", null);
					params.addValue("APPNT_START_TIME", null);
					params.addValue("APPNT_END_TIME", null);
					params.addValue("SERIAL_NUM", null);
					params.addValue("DELAY_REASON_CD", null);
//					params.addValue("WQ_BATCH_ID", getLatestAmendWqBatchId(amend.getOrderImsUI().getOrderId(), amend.getWqNatureIDList().get(i)));
					params.addValue("WQ_BATCH_ID", null);
				}else if(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateContEmail.equals(amend.getWqNatureIDList().get(i))){
					params.addValue("NATURE", amend.getWqNatureIDList().get(i));
					params.addValue("CC_TYPE", null);
					params.addValue("CC_NUM", null);
					params.addValue("CC_HOLD_NAME", null);
					params.addValue("CC_EXP_DATE", null);
					params.addValue("CC_THIRD_PARTY_IND", null);
					params.addValue("FAX_SERIAL", null);
					params.addValue("LOGIN_ID", null);
					params.addValue("MOBILE_NUM", null);
					params.addValue("APPNT_START_TIME", null);
					params.addValue("APPNT_END_TIME", null);
					params.addValue("SERIAL_NUM", null);
					params.addValue("DELAY_REASON_CD", null);
//					params.addValue("WQ_BATCH_ID", getLatestAmendWqBatchId(amend.getOrderImsUI().getOrderId(), amend.getWqNatureIDList().get(i)));
					params.addValue("WQ_BATCH_ID", null);
				}else if(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateContMobile.equals(amend.getWqNatureIDList().get(i))){
					params.addValue("NATURE", amend.getWqNatureIDList().get(i));
					params.addValue("CC_TYPE", null);
					params.addValue("CC_NUM", null);
					params.addValue("CC_HOLD_NAME", null);
					params.addValue("CC_EXP_DATE", null);
					params.addValue("CC_THIRD_PARTY_IND", null);
					params.addValue("FAX_SERIAL", null);
					params.addValue("LOGIN_ID", null);
					params.addValue("APPNT_START_TIME", null);
					params.addValue("APPNT_END_TIME", null);
					params.addValue("SERIAL_NUM", null);
					params.addValue("DELAY_REASON_CD", null);
					params.addValue("EMAIL_ADDR", null);
//					params.addValue("WQ_BATCH_ID", getLatestAmendWqBatchId(amend.getOrderImsUI().getOrderId(), amend.getWqNatureIDList().get(i)));
					params.addValue("WQ_BATCH_ID", null);
				}else{
					params.addValue("NATURE", getPriorityWqNatureIDIfItExist(amend));
//					params.addValue("WQ_BATCH_ID", getLatestAmendWqBatchId(amend.getOrderImsUI().getOrderId(), getPriorityWqNatureIDIfItExist(amend)));
					params.addValue("WQ_BATCH_ID", null);
				}
				
	
			try{
				logger.debug("key search: " + params.getValues());
				logger.debug("insertBomwebAmendCategoryNew: " + SQL);
				simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in insertBomwebAmendCategoryNew()", e);
				throw new DAOException(e.getMessage(), e);
			}
		}
	}

	private String getPriorityWqNatureIDIfItExist(WorkQueueDTO dto){
		String result="1";
		if(dto!=null && dto.getWorkQueueNatures()!=null){
			for(com.pccw.wq.schema.dto.WorkQueueNatureDTO wqNatureDto:dto.getWorkQueueNatures()){
				if(ImsConstants.IMS_AMEND_WQ_NATURE_DS_offer_Plan_VAS_Premium.equals(wqNatureDto.getWorkQueueNatureId())){
					return ImsConstants.IMS_AMEND_WQ_NATURE_DS_offer_Plan_VAS_Premium;
				}				
				if(ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE.equals(wqNatureDto.getWorkQueueNatureId())){
					return ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE;
				}
			}
			return dto.getWorkQueueNatures()[0].getWorkQueueNatureId();
		}
		return result;
	}
	
	private String getPriorityWqNatureIDIfItExist(AmendOrderImsUI amend){
		String result="1";
		if(amend.getWqNatureIDList()!=null){
			for(String temp:amend.getWqNatureIDList()){
				if(ImsConstants.IMS_AMEND_WQ_NATURE_DS_offer_Plan_VAS_Premium.equals(temp)){
					return ImsConstants.IMS_AMEND_WQ_NATURE_DS_offer_Plan_VAS_Premium;
				}				
				if(ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE.equals(temp)){
					return ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE;
				}
			}
			return amend.getWqNatureIDList().get(0);
		}
		return result;
	}



    public Boolean isNowRetNeedAppointment(String orderId) throws DAOException{
  		List<String> result  = new ArrayList<String>();	  		
  		String SQL = "select decode(count(*),1,'Y','N') NeedAppointment from" +
		"      bomweb_appointment" +
		"      where order_id = ? " +
		"      and SERIAL_NUM is not null" +
		"      and APPNT_START_TIME is not null" +
		"      and APPNT_END_TIME is not null ";	
  		

		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String dto = new String();
				dto = (String) rs.getString("NeedAppointment");
				return dto;
			}
		};
		
  		try {
            result = simpleJdbcTemplate.query(SQL,mapper,orderId);
  		} catch (EmptyResultDataAccessException erdae) {
  			logger.debug("No isNoAppointment");			
  		} catch (Exception e) {
  			logger.debug("Exception caught in getServiceEffectiveDate():", e);
  			throw new DAOException(e.getMessage(), e);
  		}
  		if(result.size()>0&&"Y".equals(result.get(0))){
  			logger.info("isNowRetNeedAppointment true:"+orderId);			
  			return true;
  		}
		logger.info("isNowRetNeedAppointment false:"+orderId);	
  		return false;	  		
  	}
    
	private String getWQDtoRemark(WorkQueueDTO dto){
		String result = "";
		logger.info("getWQDtoRemark dto: " + gson.toJson(dto));
		if(dto!=null){
			try{
				if(dto.getRemarks()!=null && dto.getRemarks().length>0){
					for(com.pccw.wq.schema.dto.RemarkDTO rmkDto:dto.getRemarks()){
						if(rmkDto!=null){
							result+=rmkDto.getRemarkContent();
							logger.info("RemarkDTO result:"+result);
						}
					}
				}
			}catch (Exception e) {
				logger.error("Exception caught in getWQDtoRemark(), 1st part", e);
				logger.info("result:"+result);
				return result;
			}		
			try{		
				if(dto.getWorkQueueNatures()!=null && dto.getWorkQueueNatures().length>0){
					for (WorkQueueNatureDTO wqNatureDto :dto.getWorkQueueNatures()){
						if(wqNatureDto!=null && wqNatureDto.getRemarks()!=null && wqNatureDto.getRemarks().length>0){
							for(com.pccw.wq.schema.dto.RemarkDTO rmkDto2:wqNatureDto.getRemarks()){
								if(rmkDto2!=null){
									result+=rmkDto2.getRemarkContent();
									logger.info("wqNatureDto result:"+result);
								}
							}
						}
					}
				}
			}catch (Exception e) {
				logger.error("Exception caught in getWQDtoRemark(), 2nd part", e);
				logger.info("result:"+result);
				return result;
			}
		}
		logger.info("final result:"+result);
		return result;
	}
	
	
	public void updateWqSrd(AmendOrderImsUI amend) throws DAOException{
		logger.info("updateWqSrd:"+amend.getOrderImsUI().getOrderId());
		if(amend.getOrderImsUI().getAppointment().getTargetInstallDate()!=null){
			MapSqlParameterSource params = new MapSqlParameterSource();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date = null;
			try {
				date = sdf.parse(amend.getOrderImsUI().getAppointment().getTargetInstallDate());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String SQL = "	update Q_WORK_QUEUE set srd = :newSRD " +
					"  where sb_id = :orderId	";

			params.addValue("newSRD", date);
			params.addValue("orderId", amend.getOrderImsUI().getOrderId());
			
			try{
					logger.debug("updateWqSrdSQL: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in updateWqSrd()", e);
				throw new DAOException(e.getMessage(), e);
			}		
		}
	}
	
	
	public void updateFsAmendToDummy(AmendOrderImsUI amend) throws DAOException{
		logger.info("updateFsAmendToDummy:"+amend.getOrderImsUI().getOrderId());
	
		String SQL = "	UPDATE  q_wq_wp_assgn a SET WP_ID = '15023'" +
				"   WHERE wq_type in ('SB-PCD','SB-NOW')" +
				"     AND wq_subtype = 'ORDER_AMEND'" +
				"     AND a.wq_id = (SELECT b.wq_id" +
				"                      FROM q_work_queue b" +
				"                     WHERE sb_id = :orderId)" +
				"     AND a.wq_wp_assgn_id IN" +
				"            (SELECT c.wq_wp_assgn_id" +
				"               FROM q_wq_wp_assgn_status_log c" +
				"              WHERE c.latest_status_ind = 'Y'" +
				"                AND wq_wp_assgn_id IN (" +
				"                                SELECT wq_wp_assgn_id" +
				"                                  FROM q_wq_wp_assgn" +
				"                                 WHERE wq_id IN (" +
				"                                                 SELECT wq_id" +
				"                                                 FROM q_work_queue" +
				"                                                  WHERE sb_id = :orderId ))" +
				"                AND c.status_cd NOT IN (SELECT code" +
				"                                          FROM q_dic_code_lkup" +
				"                                         WHERE grp_id IN 'WQ_ENDING_STATUS'))	";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", amend.getOrderImsUI().getOrderId());
		
		try{
				logger.debug("updateFsAmendToDummy: " + SQL);
				simpleJdbcTemplate.update(SQL,params);
		}catch (Exception e) {
			logger.error("Exception caught in updateFsAmendToDummy()", e);
			throw new DAOException(e.getMessage(), e);
		}		
	}
	
	public void setWqSrdNull(String orderId) throws DAOException{
			MapSqlParameterSource params = new MapSqlParameterSource();
			
			String SQL = "	update Q_WORK_QUEUE set srd = :newSRD " +
					"  where sb_id = :orderId	";

			params.addValue("newSRD", null);
			params.addValue("orderId", orderId);
			
			try{
					logger.debug("setWqSrdNull: " + SQL);
					simpleJdbcTemplate.update(SQL,params);
				
			}catch (Exception e) {
				logger.error("Exception caught in setWqSrdNull()", e);
				throw new DAOException(e.getMessage(), e);
			}	
	}
	
	public List<WorkQueueNatureDTO> getTypeSubTypeByWqNatureID (List<String> wqNatureIdList)
	throws DAOException {
		
		List<WorkQueueNatureDTO> result = new ArrayList<WorkQueueNatureDTO>();
		
		for(String WQ_NATURE_ID:wqNatureIdList){
			
			String SQL=	"select WQ_TYPE, WQ_SUBTYPE, WQ_NATURE_ID  " +
					"    from Q_DIC_WP_WQ_NATURE_ASSGN " +
					"    where WQ_NATURE_ID = :wqNatureId ";
			logger.debug("getTypeSubTypeByWqNatureID @ WQ_NATURE_ID: " + WQ_NATURE_ID +"  SQL"+ SQL);

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("wqNatureId", WQ_NATURE_ID);
			
			ParameterizedRowMapper<WorkQueueNatureDTO> mapper = new ParameterizedRowMapper<WorkQueueNatureDTO>() {
			    public WorkQueueNatureDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			    	WorkQueueNatureDTO wq = new WorkQueueNatureDTO();
			    	wq.setWorkQueueType(rs.getString("WQ_TYPE"));
			    	wq.setWorkQueueSubType(rs.getString("WQ_SUBTYPE"));
			    	wq.setWorkQueueNatureId(rs.getString("WQ_NATURE_ID"));
			        return wq;
			    }
			};
			
	    	List<WorkQueueNatureDTO> wqNatureDTO = new ArrayList<WorkQueueNatureDTO>();
			
			try {
				wqNatureDTO = simpleJdbcTemplate.query(SQL, mapper, params);
			} catch (EmptyResultDataAccessException erdae) {
				logger.debug("EmptyResultDataAccessException");
				wqNatureDTO = null;
			} catch (Exception e) {
				logger.debug("Exception caught in getTypeSubTypeByWqNatureID():", e);
				throw new DAOException(e.getMessage(), e);
			}	
			if(wqNatureDTO.size()>0){
				logger.info("getWorkQueueSubType:"+wqNatureDTO.get(0).getWorkQueueSubType());
				logger.info("getWorkQueueType:"+wqNatureDTO.get(0).getWorkQueueType());
			}
			result.add(wqNatureDTO.get(0));
		}
		
		return (result.size()>0 ?result:null);
	}
	
	public String getNatureDesc (String wqNatureId) throws DAOException {
		List<String> result = new ArrayList<String>();
		String SQL = "SELECT wq_nature_desc" +
				"  FROM q_wq_nature" +
				" WHERE wq_nature_id = :WQ_NATURE_ID";
		logger.debug("getNatureDesc , WQ_NATURE_ID: " + wqNatureId + " SQL:"+SQL);
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("WQ_NATURE_ID", wqNatureId);
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return rs.getString("wq_nature_desc");
		    }
		};
		try {
			result = simpleJdbcTemplate.query(SQL, mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
		} catch (Exception e) {
			logger.debug("Exception caught in getNatureDesc():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (result.size()>0 ?result.get(0):"error");
	}

	public List<DelayReasonDTO> getDelayReason(String what) throws DAOException {
			List<DelayReasonDTO> result = new ArrayList<DelayReasonDTO>();
			
			String SQL = 
					" select DESCRIPTION, code  from w_code_lkup " +
					" where grp_id = 'IMS_AMEND_DEL_REA' " ;

		ParameterizedRowMapper<DelayReasonDTO> mapper = new ParameterizedRowMapper<DelayReasonDTO>() {
			public DelayReasonDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				DelayReasonDTO dto = new DelayReasonDTO();
				dto.setDelayReason((String) rs.getString("DESCRIPTION")) ;
				dto.setDelayReasonCode((String) rs.getString("code")) ;
				return dto;
			}
		};

		try {
			logger.debug("getDelayReason SQL: " + SQL);
			result = simpleJdbcTemplate.query(SQL, mapper);

		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getDelayReason():", e);
			throw new DAOException(e.getMessage(), e);
		}	
			return result;
	}


	public String getLatestAmendWqBatchId(String orderId, String wqNature_id) throws DAOException{
		logger.info("getLatestAmendWqBatchId is called:"+orderId+", wqNature_id:"+wqNature_id);
		
		String wqBatchId  = null;
		
		try{	
			String SQL = "SELECT MAX (wq_batch_id)" +
					"  FROM q_work_queue a, q_wq_wq_nature_assgn b" +
					" WHERE sb_id = ?" +
					"   AND a.wq_id = b.wq_id" +
					"   AND b.wq_nature_id IN (" +
					"          SELECT wq_nature_id" +
					"            FROM q_wq_nature" +
					"           WHERE wq_nature_type IN" +
					"                    ('AMEND_CATEGORY_IMS'," +
					"                     'AMEND_CATEGORY'," +
					"                     'AMEND_CATEGORY_IMS_A'" +
					"                    ))" +
					"   AND b.wq_nature_id = ?";
			
			wqBatchId = simpleJdbcTemplate.queryForObject(SQL, String.class, orderId,wqNature_id);
		}catch (EmptyResultDataAccessException erdae){
			logger.debug("EmptyResultDataAccessException");
			wqBatchId = "1";
		}catch (Exception e) {
				logger.error("Exception caught in getLatestAmendWqBatchId()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return wqBatchId;
	}
	
	public Boolean  isPendingExist (String orderId)
	throws DAOException {
		logger.debug("isPendingexist orderId: " + orderId);
		
		String 	SQL=" select count(*) pending from Q_WORK_QUEUE a, Q_WQ_WP_ASSGN_STATUS_LOG b , Q_WQ_WP_ASSGN c" +
				" where a.WQ_ID = c.WQ_ID" +
				" and b.WQ_WP_ASSGN_ID = c.WQ_WP_ASSGN_ID" +
				" and b.LATEST_STATUS_IND = 'Y'" +
				" and b.STATUS_CD not in (select code from Q_DIC_CODE_LKUP where grp_id ='WQ_ENDING_STATUS')" +
				" and a.SB_ID = '" + orderId +"'";
		
		SQL = "SELECT COUNT (*) pending" +
				"  FROM q_work_queue a," +
				"       q_wq_wp_assgn_status_log b," +
				"       q_wq_wp_assgn c," +
				"       q_wq_wq_nature_assgn d" +
				" WHERE a.wq_id = c.wq_id" +
				"   AND b.wq_wp_assgn_id = c.wq_wp_assgn_id" +
				"   AND b.wq_wp_assgn_id = d.dispatch_wq_wp_assgn_id" +
				"   AND b.latest_status_ind = 'Y'" +
				"   AND b.status_cd NOT IN (SELECT code" +
				"                             FROM q_dic_code_lkup" +
				"                            WHERE grp_id = 'WQ_ENDING_STATUS')" +
				"   AND (   d.wq_nature_id IN (" +
				"              SELECT wq_nature_id" +
				"                FROM q_wq_nature" +
				"               WHERE wq_nature_type IN" +
				"                        ('AMEND_CATEGORY_IMS'," +
				"                         'AMEND_CATEGORY'," +
				"                         'AMEND_CATEGORY_IMS_A'" +
				"                        )" +
				"                 AND wq_nature_id > 0)" +
				"        OR d.wq_nature_id IN (227)" +
				"       )" +
				"   AND a.sb_id = ?";
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  count = new String ();
		    	count = (rs.getString("pending"));
		        return count;
		    }
		};
		
    	List<String > countPending = new ArrayList<String >();
		
		try {
			logger.debug("isPendingexist SQL: " + SQL);
			countPending = simpleJdbcTemplate.query(SQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			countPending = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isPendingexist():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(countPending.get(0).equalsIgnoreCase("0")){
			logger.debug("is Pending exist: false");
			return false;
		}else{
			logger.debug("is Pending exist: true");
			return true;
		}
	}
	
	public Boolean  isPaymentMethodIsCC (String orderId)
	throws DAOException {
		logger.debug("isPaymentMethodIsCC orderId: " + orderId);
		
		String SQL = "select PAY_MTD_TYPE from bomweb_payment where order_id = '" + orderId +"' ";
		
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  temp = new String ();
		    	temp = (rs.getString("PAY_MTD_TYPE"));
		        return temp;
		    }
		};
		
    	List<String > tempList = new ArrayList<String >();
		
		try {
			logger.debug("isPaymentMethodIsCC SQL: " + SQL);
			tempList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isPaymentMethodIsCC():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(tempList.get(0).equalsIgnoreCase("C")){
			logger.debug("is Payment Method Is CC: true");
			return true;
		}else{
			logger.debug("is Payment Method Is CC: false");
			return false;
		}
	}

	
	public Boolean  isOCIDexist (String orderId)
	throws DAOException {
		logger.debug("isOCIDexist orderId: " + orderId);
		
		String SQL = 	" 	   select nvl(ocid,0) ocid from " +
				" bomweb_order where order_id ='" + orderId +"' ";
		
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  ocid = new String ();
		    	ocid = (rs.getString("ocid"));
		        return ocid;
		    }
		};
		
    	List<String > ocidList = new ArrayList<String >();
		
		try {
			logger.debug("isOCIDexist SQL: " + SQL);
			ocidList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			ocidList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isOCIDexist():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(ocidList.size()>0 && !ocidList.get(0).equalsIgnoreCase("0")){
			logger.debug("is OCID exist: true");
			return true;
		}else{
			logger.debug("is OCID exist: false");
			return false;
		}
	}

	
	public Boolean  isOpenRetailAmend ()	throws DAOException {
		String SQL = 	" SELECT CODE FROM w_code_lkup WHERE grp_id = 'IS_OPEN_RETAIL_AMEND' ";
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  result = new String ();
		    	result = (rs.getString("CODE"));
		        return result;
		    }
		};
		
    	List<String > resultList = new ArrayList<String >();
		
		try {
			logger.debug("isOpenRetailAmend SQL: " + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isOpenRetailAmend():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(resultList.size()>0 && resultList.get(0).equalsIgnoreCase("Y")){
			logger.debug("isOpenRetailAmend: true");
			return true;
		}else{
			logger.debug("isOpenRetailAmend: false");
			return false;
		}
	}
	
	public String  getOrderStatusDesc (String orderId)	throws DAOException {
		String SQL = 	" SELECT description" +
				"  FROM w_code_lkup" +
				" WHERE grp_id = 'IMS_SB_ORDER_STATUS'" +
				"   AND code = (SELECT order_status" +
				"                 FROM bomweb_order" +
				"                WHERE order_id = ?) ";
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  result = new String ();
		    	result = (rs.getString("description"));
		        return result;
		    }
		};
		
    	List<String > resultList = new ArrayList<String >();
		
		try {
			logger.debug("order_id:"+orderId+" getOrderStatusDesc SQL: " + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getOrderStatusDesc():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(resultList.size()>0){
			logger.debug("get Order Status Desc:"+resultList.get(0));
			return resultList.get(0);
		}else{
			logger.debug("get Order Status Desc:no record found");
			return "no record found";
		}
	}
	public Boolean  isOrderStatusOkayForAmend (String orderId)	throws DAOException {
		logger.debug("isOrderStatusOkayForAmend orderId: " + orderId);
		String SQL = 	" SELECT COUNT (*) cnt" +
				"  FROM bomweb_order" +
				" WHERE order_id = ? " +
				" AND order_status IN ('02', '04', '92', '93') ";
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  result = new String ();
		    	result = (rs.getString("cnt"));
		        return result;
		    }
		};
		
    	List<String > resultList = new ArrayList<String >();
		
		try {
			logger.debug("isOrderStatusOkayForAmend SQL: " + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper, orderId);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isOrderStatusOkayForAmend():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(resultList.size()>0 && resultList.get(0).equalsIgnoreCase("0")){
			logger.debug("isOrderStatusOkayForAmend: false");
			return false;
		}else{
			logger.debug("isOrderStatusOkayForAmend: true");
			return true;
		}
	}
	
	public List<ImsAlertMsgDTO> getImsAlertMsgList (List<String> sbid)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();		
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		

		
		StringBuffer sql =  new StringBuffer( 
				" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , b.FIRST_NAME||' '||b.LAST_NAME  as cust_Name," +
				" b.SERVICE_NUM, c.LOGIN_ID, to_char(a.APP_DATE,'DD/MM/YYYY HH:MI:SS AM') APP_DATE, a.order_Status , '' err, '' recall, " +
				" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY,  c.IMS_ORDER_TYPE  " +
				" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c " +
				" where a.order_id = b.order_id" +
				" and a.order_id = c.order_id" +
				" and a.LOB = 'IMS'" +
				" and a.order_id in (:SBID)" +
				" order by a.create_date desc" );
		
		params.addValue("SBID", sbid);
		
		ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
			public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
				dto.setAppDate((String) rs.getString("APP_DATE"));
				dto.setAlertStatus((String) rs.getString("alert_Status"));
				dto.setCustName((String) rs.getString("cust_Name"));
				dto.setError((String) rs.getString("err"));
				dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
				dto.setLoginId((String) rs.getString("LOGIN_ID"));
				dto.setOcid((String) rs.getString("OCID"));
				dto.setOrderId((String) rs.getString("order_id"));
				dto.setOrderStatus((String) rs.getString("order_Status"));
				dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
				dto.setSalesCd((String) rs.getString("SALES_CD"));
				dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
				dto.setCreateBy((String) rs.getString("CREATE_BY"));
				dto.setImsOrderType((String) rs.getString("IMS_ORDER_TYPE"));
				return dto;
			}
		};

	try {
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getImsAlertMsgList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}
	
	public List<ImsAlertMsgDTO> getLtsImsOrderEnquiryListInfo (CcLtsImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();

		logger.info("enquiry: " + gson.toJson(enquiry));
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer(  
			" select * from (" +
			" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , " +
			" decode(b.ID_DOC_TYPE, 'BS', b.company_name, b.last_name || ' ' || b.first_name)  AS cust_name ," +
			" b.SERVICE_NUM, c.LOGIN_ID, " + 
			" decode(substr(a.order_id, 6,1),'T',to_char(a.APP_DATE,'YYYY/MM/DD'), to_char(a.APP_DATE,'YYYY/MM/DD HH24:MI:SS')) APP_DATE, " +
			" a.order_Status, '' err, '' recall, " +
			" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY, a.reason_cd,  c.IMS_ORDER_TYPE  " +
			", a.LAST_UPDATE_DATE, "+"decode(b.ID_DOC_TYPE, 'BS', '********', b.last_name || ' ' || '****')  AS mask_name , a.order_type, "+//ims celia ds ntv 20150805
			" a.create_date " + // ims martin 20160425
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" );
		

		sql.append(" and a.order_id in (:orderIds) ");
		List<String> tempOrderIds =  this.getOrderIdsForEnquiry(enquiry, userDto);
		logger.info("tempOrderIds: "+gson.toJson(tempOrderIds));
		params.addValue("orderIds", tempOrderIds);
		
		
		sql.append(" order by a.create_date desc)" +
			" where rownum< (select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'ORD_ENQ_MAX') ") ;

	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setAlertStatus((String) rs.getString("alert_Status"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setError((String) rs.getString("err"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setLoginId((String) rs.getString("LOGIN_ID"));
			dto.setOcid((String) rs.getString("OCID"));
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
			dto.setSalesCd((String) rs.getString("SALES_CD"));
			dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			dto.setReasonCD((String) rs.getString("reason_cd"));
			dto.setImsOrderType((String) rs.getString("IMS_ORDER_TYPE"));
			dto.setLastUpdDate(rs.getDate("LAST_UPDATE_DATE"));
			dto.setMaskName((String) rs.getString("mask_name"));
			dto.setOrderType((String) rs.getString("order_type"));
			dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
			return dto;
		}
	};

	try {
		logger.debug("final search Sql:" + sql);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.error("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.error("Exception caught in getLtsImsOrderEnquiryList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}
	
	
	
	public List<String> getChannelCodeListByChannelID (int channelId)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	select distinct channel_cd from bomweb_sales_lkup_v where channel_id = :channelid  	" );
		
		params.addValue("channelid", channelId);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("channel_cd");
			return tempResult;
		}
	};

	try {
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.error("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.error("Exception caught in getChannelCodeByChannelID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		logger.info(channelId+" getChannelCodeListByChannelID result:"+gson.toJson(result));
		return result;
	}
	//ims direct sales Celia 20150318
	public List<String> getTeamCodeListOfCentreCd(String username)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
	"	select distinct team_cd from bomweb_sales_lkup_v where centre_cd IN (SELECT CENTRE_CD FROM BOMWEB_SALES_assignment WHERE STAFF_ID = :username	AND (sysdate < end_date or end_date is null) and START_DATE < sysdate	)");
		
		params.addValue("username", username);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("team_cd");
			return tempResult;
		}
	};

	try {
		logger.debug("username @ getTeamCodeListOfCentreCd: " + username);
		logger.debug("getTeamCodeListOfCentreCd : " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getTeamCodeListOfCentreCd():", e);
		throw new DAOException(e.getMessage(), e);
	}	
//	System.out.print("TeamCodeListOfCentreCd:	"+result);
	
		return result;
	}
	
	public String getLtsImsOrderEnquirySQL (CcLtsImsOrderEnquiryUI enquiry, String role)
	throws DAOException {		
		
		StringBuffer sql =  new StringBuffer( 
			" select a.order_id, a.app_date " +
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c, BOMWEB_SALES_ASSIGNMENT d, bomweb_sales_profile e " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" +
			" and d.staff_id = :user " +
			" and d.STAFF_ID = e.staff_id " +
			" and (a.shop_cd not in 	(Select code_id from BOMWEB_CODE_LKUP sbof 	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD') " +
			" or a.shop_cd  is null) " +
			" and trunc(e.start_date) <= trunc(sysdate) " +
			" and (trunc(sysdate) < trunc(e.end_date) or e.end_date is null) " +
			" and trunc(d.start_date) <= trunc(sysdate) " +
			" and (trunc(sysdate) < trunc(d.end_date) or d.end_date is null) " );

		//ims direct sales Celia 20150317
		if(role.equalsIgnoreCase("ALL_ORDERS")){	
								
		}else if(role.equalsIgnoreCase("CENTRE_CD")){	
			sql.append(" and a.centre_cd IN (SELECT CENTRE_CD FROM BOMWEB_SALES_assignment WHERE STAFF_ID = :user AND (trunc(sysdate) <= trunc(end_date) or end_date is null) and trunc(START_DATE) <= trunc(sysdate)) ");					
		}else if(role.equalsIgnoreCase("CHANNEL_ID")){
			sql.append(" and nvl(substr(a.order_id,2,3), (select channel_cd from bomweb_sales_lkup_v where staff_id=a.create_by and rownum=1) )" +
					"    in (select distinct channel_cd from bomweb_sales_lkup_v where channel_id = :channelid) ");
		}else if(role.equalsIgnoreCase("SALES_CD")){
			sql.append(" and e.ORG_STAFF_ID = a.SALES_CD");
		}else if(role.equalsIgnoreCase("TEAM_CD")){
			sql.append(" and d.team_cd = NVL (a.sales_team, " +
					"                               (SELECT team_cd" +
					"                                  FROM bomweb_sales_lkup_v" +
					"                                 WHERE staff_id = a.create_by AND ROWNUM = 1)" +
					"                              )");
			sql.append(" and a.centre_cd IN (SELECT CENTRE_CD FROM BOMWEB_SALES_assignment WHERE STAFF_ID = :user AND (trunc(sysdate) <= trunc(end_date) or end_date is null) and trunc(START_DATE) <= trunc(sysdate)) ");
		}else if(role.equalsIgnoreCase("CHANNEL_CD")){
			sql.append(" and d.channel_cd = nvl(substr(a.order_id,2,3), (select channel_cd from bomweb_sales_lkup_v where staff_id=a.create_by and rownum=1) ) ");
		}else if (role.equalsIgnoreCase("FandS")){
			sql.append(" and a.order_ID not like 'V%' ");
		}else if (role.equalsIgnoreCase("CFM66")){
			sql.append(" and a.order_ID not like 'V%' ");
			sql.append(" and a.sales_channel in (select code from w_code_lkup where grp_id = 'CFM66_channel_cd') ");
		}else{
			sql.append(" and d.STAFF_ID = a.create_by");
		}
		
			sql.append(" and a.LOB in (:LOB)");
		
		if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
			if(enquiry.getDateType().equalsIgnoreCase("S")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.srv_req_date >= TO_DATE(:SStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.srv_req_date < TO_DATE(:SEndDate, 'dd/mm/yyyy')+1 " );
				}
			}else if(enquiry.getDateType().equalsIgnoreCase("A")){
				if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
					sql.append(" and a.APP_DATE >= TO_DATE(:AStartDate, 'dd/mm/yyyy')  ");
				}
				if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
					sql.append(" and a.APP_DATE < TO_DATE(:AEndDate, 'dd/mm/yyyy')+1  ");
				}
			}
		}
		if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
			sql.append(" and a.order_id = :OrderID ");
		}
		if(enquiry.getDocType()!=null&&!enquiry.getDocType().equals("") &&
		   enquiry.getDocNum() !=null&&!enquiry.getDocNum() .equals("")	){
			sql.append(" and b.id_doc_type = :DocType ");
			sql.append(" and b.id_doc_num = :DocNum ");
		}
		if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
			sql.append(" and b.service_num = :SerNum ");
		}
		if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
			sql.append(" and c.login_id = :LoginId ");
		}		
		// martin 20140905
		if(enquiry.getOrderType()!=null&&!enquiry.getOrderType().equals("")){
			if(enquiry.getOrderType().equals("PCD-A"))
				sql.append(" AND (C.ims_order_type IS NULL or C.ims_order_type = 'DS')");
		
			if(enquiry.getOrderType().equals("P_R_T")||enquiry.getOrderType().equals("PCD_V")||enquiry.getOrderType().equals("RET")||enquiry.getOrderType().equals("TEMP"))
				sql.append(" AND C.ims_order_type = :ims_order_type");
		
		// martin 20140905 end
		//steven relocated SQL 20170725 start
			if(enquiry.getOrderType().equalsIgnoreCase("PCD-A")){
				sql.append(" and a.ORDER_TYPE is null ");
			}else if(enquiry.getOrderType().equalsIgnoreCase("NTV-A")){
				sql.append(" and a.ORDER_TYPE = 'NTV-A' ");
			}else if(enquiry.getOrderType().equalsIgnoreCase("NTVAO")){
				sql.append(" and a.ORDER_TYPE = 'NTVAO' ");
			}else if(enquiry.getOrderType().equalsIgnoreCase("NTVUS")){
				sql.append(" and a.ORDER_TYPE = 'NTVUS' ");
			}else if(enquiry.getOrderType().equalsIgnoreCase("NTVRE")){
				sql.append(" and a.ORDER_TYPE = 'NTVRE' ");
			}
		}
		//steven relocated SQL 20170725 end		
		if(enquiry.getOrderStatus()!=null && !enquiry.getOrderStatus().equals("")){
			sql.append(" and a.ORDER_STATUS in ( SELECT code" +
			"  FROM w_code_lkup" +
			" WHERE grp_id = 'IMS_SB_ORDER_STATUS'" +
			" and description = :description ) " );
		}
		if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
			sql.append(" and UPPER(a.SALES_CD) in UPPER(:salesNum) ");
		}
		if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
			sql.append(" and UPPER(a.create_by) in UPPER(:CreateStaff) ");
		}
		if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
			sql.append(" and UPPER(a.SALES_TEAM) in UPPER(:TeamSearch) ");
		}
		
		return sql.toString();
	}
	
	public Boolean checkIfSalesManager (String userId)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	SELECT CATEGORY FROM BOMWEB_SALES_PROFILE where staff_id = :userId  	" );
		
		params.addValue("userId", userId);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("CATEGORY");
			return tempResult;
		}
	};

	try {
		logger.debug("userId @ checkIfSalesManager: " + userId);
		logger.debug("checkIfSalesManager : " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in checkIfSalesManager():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		if(result.size()==0||!result.get(0).equalsIgnoreCase("SALES MANAGER")){
			return false;
		}else{
			return true;
		}
	}
	

	public List<String> getRoleCodeByUserIDLkupCode (String userId, String lkupCode, String lkupFuncCode)
	throws DAOException {
		List<String> result = new ArrayList<String>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	select CODE_DESC from bomweb_code_lkup 	"+
				"	where code_type like '"+lkupFuncCode+"'  and	"+
				"	code_id in(	"+
				"	select code_id from (	"+
				"	select code_id, '1' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\'||CHANNEL_iD||'\\'||CATEGORY||'\\'||CHANNEL_CD||'\\'|| TEAM_CD 	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	" +
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	union all	"+
				"	select code_id, '2' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\'||CHANNEL_iD||'\\'||CATEGORY||'\\'||CHANNEL_CD||'\\*'	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	"+
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	union all	"+
				"	select code_id, '3' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\'||CHANNEL_iD||'\\'||CATEGORY||'\\*\\*'	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	"+
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	union all	"+
				"	select code_id, '4' seq from bomweb_code_lkup c, BOMWEB_SALES_PROFILE a , BOMWEB_SALES_ASSIGNMENT b	"+
				"	where code_type = '"+lkupCode+"'	"+
				"	and code_desc = '\\*\\*\\*\\*'	"+
				"	and a.STAFF_ID=b.STAFF_ID	"+
				"	and a.staff_id = :userId	"+
				"	and a.START_DATE < sysdate" +
				"	and (sysdate < a.end_date or a.end_date is null) " +
				"	and b.START_DATE < sysdate" +
				"	and (sysdate < b.end_date or b.end_date is null) "+
				"	order by seq )	"+
				"	where rownum = 1	"+
				"	)	"+
				"	union all	"+
				"	select 'CREATEBY' from dual  	" );
		
		params.addValue("userId", userId);
		
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("CODE_DESC");
			return tempResult;
		}
	};

	try {
		logger.debug("get Role Code By UserID SQL: " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.error("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.error("Exception caught in getRoleCodeByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		logger.info(
				"get Role Code By UserID result:"+gson.toJson(result)+
				" userId: " + userId +" lkupCode:"+lkupCode+" lkupFuncCode:"+lkupFuncCode);
		return result;
	}
	
	public List<String> getOrderIdsForEnquiry (CcLtsImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<String> result = null;
		

		logger.info("getOrderIdByUserID userId:"+userDto.getUsername());
		List<String> roleCodeList = this.getRoleCodeByUserIDLkupCode(userDto.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_READ, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION) ;
		
		Boolean needChannelCD = false;
		Boolean needSales = false;
		Boolean needTeam = false;
		Boolean needChannelID = false;
		Boolean needCentreCD = false;
		Boolean needAllOrders = false;

		logger.info("getRoleCodeByUserID:"+gson.toJson(roleCodeList));
		for(String code : roleCodeList){
//			logger.info("getRoleCodeByUserID:"+code);
			if(code.equalsIgnoreCase("SALES_CD")){
				needSales=true;
			}
			if(code.equalsIgnoreCase("TEAM_CD")){
				needTeam=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_CD")){
				needChannelCD=true;
			}
			if(code.equalsIgnoreCase("CHANNEL_ID")){
				needChannelID=true;
			}
			if(code.equalsIgnoreCase("CENTRE_CD")){
				needCentreCD=true;
			}
			if(code.equalsIgnoreCase("ALL_ORDERS")){
				needAllOrders=true;
			}
		}

		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer(" SELECT order_id" +
		"  FROM (select distinct order_id from (") ;
		
		if(ImsConstants.CFM66_channelID == userDto.getChannelId()){
			logger.info("66 userId:"+userDto.getUsername());
			 SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CFM66"));			
		}else if(ImsConstants.IMS_FandS_channelID == userDto.getChannelId()
				|| this.isThisStaffCslFAndS(userDto.getUsername())){	//for F&S order inquiry
				 SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "FandS"));
				logger.info("76 userId:"+userDto.getUsername());
		}else {
			SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "create_by"));
			if(needSales){
				SQL.append( " union all ");
				SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "SALES_CD"));
			}
			if(needTeam){
				SQL.append( " union all ");
				SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "TEAM_CD"));
			}
			if(needChannelCD){
				SQL.append( " union all ");
				SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_CD"));
			}
			if(needChannelID){
				SQL.append( " union all ");
				SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CHANNEL_ID"));
				params.addValue("channelid", Integer.valueOf(userDto.getChannelId()));
			}
			if(needCentreCD){
				SQL.append( " union all ");
				SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "CENTRE_CD"));
					//ims direct sales Celia 20150317
			}
			if(needAllOrders){
				SQL.append( " union all ");
				SQL.append(this.getLtsImsOrderEnquirySQL(enquiry, "ALL_ORDERS"));
			}
	}			
		SQL.append("	order by app_date desc)) where rownum<1001	" );
		
	
		params.addValue("LOB", "IMS");
		

		// martin 20140905
		if(enquiry.getOrderType()!=null&&!enquiry.getOrderType().equals("")
				&&(enquiry.getOrderType().equals("P_R_T")||enquiry.getOrderType().equals("PCD_V")||enquiry.getOrderType().equals("RET")||enquiry.getOrderType().equals("TEMP"))){
			if (enquiry.getOrderType().equals("RET")) {
				params.addValue("ims_order_type", "PCD_R");
			} else if (enquiry.getOrderType().equals("P_R_T")) {
				params.addValue("ims_order_type", "P_R_T");
			} else if (enquiry.getOrderType().equals("PCD_V")) {
				params.addValue("ims_order_type", "PCD_V");
			} else {
				params.addValue("ims_order_type", "PCD_T");
			}
		}
		// martin 20140905 end

	if(enquiry.getDateType()!=null&&!enquiry.getDateType().equals("")){
		if(enquiry.getDateType().equalsIgnoreCase("S")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("SStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("SEndDate", enquiry.getEndDate());
			}
		}else if(enquiry.getDateType().equalsIgnoreCase("A")){
			if(enquiry.getStartDate()!=null&&!enquiry.getStartDate().equals("")){
				params.addValue("AStartDate", enquiry.getStartDate());
			}
			if(enquiry.getEndDate()!=null&&!enquiry.getEndDate().equals("")){
				params.addValue("AEndDate", enquiry.getEndDate());
			}
		}
	}
	if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
		params.addValue("OrderID", enquiry.getOrderId());
	}
	if(enquiry.getDocType()!=null&&!enquiry.getDocType().equals("")&&
	   enquiry.getDocNum() !=null&&!enquiry.getDocNum() .equals("")	){
		params.addValue("DocType", enquiry.getDocType());
		params.addValue("DocNum", enquiry.getDocNum());
	}
	if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
		params.addValue("SerNum", enquiry.getSerNum());
	}
	if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
		params.addValue("LoginId", enquiry.getLoginId());
	}		
	if(enquiry.getOrderStatus()!=null && !enquiry.getOrderStatus().equals("")){
		params.addValue("description", enquiry.getOrderStatus());
	}
	if(enquiry.getSalesNum()!=null&&!enquiry.getSalesNum().equals("")){
		params.addValue("salesNum", enquiry.getSalesNum());
	}
	if(enquiry.getCreateStaff()!=null&&!enquiry.getCreateStaff().equals("")){
		params.addValue("CreateStaff", enquiry.getCreateStaff());
	}
	if(enquiry.getTeamSearch()!=null&&!enquiry.getTeamSearch().equals("")){
		params.addValue("TeamSearch", enquiry.getTeamSearch());
	}
	
	params.addValue("user", userDto.getUsername());

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("order_id");
			return tempResult;
		}
	};
	try {
		logger.info("key search: " + params.getValues()+" userId @ getOrderIdByUserID: " + userDto.getUsername());
		logger.debug("final getOrderIdByUserID: " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = new ArrayList<String>();
		result.add("''");
	} catch (Exception e) {
		logger.debug("Exception caught in getOrderIdByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	if(result.size()==0){
		result.add("''");
	}
		return result;
	}
	
	
	
	public Boolean isThisStaffCslFAndS (String userName) throws DAOException {
		List<String> tempList = new ArrayList<String>();
				
		StringBuffer SQL =  new StringBuffer( 
				" select decode(count(*),0,'N','Y') isCslFandS" +
				" from bomweb_sales_lkup_v a , W_IMS_SB_LKUP b " +
				" where GRP_ID = 'CSL_FS' " +
				" and (a.channel_id = b.channel_id or  b.channel_id = '*')" +
				" and (a.channel_cd = b.channel_cd or  b.channel_cd = '*')" +
				" and (a.centre_cd = b.centre_cd or  b.centre_cd = '*')" +
				" and (a.team_cd = b.team_cd or  b.team_cd = '*')" +
				" and (staff_id = :salesCd or org_staff_id = :salesCd)"	
				) ;
				
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("salesCd", userName);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("isCslFandS");
				return tempResult;
			}
		};

		try {
			logger.debug("isThisStaffCslFAndS parms: " + params.getValues());
			tempList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
			logger.debug("isThisStaffCslFAndS result: " + gson.toJson(tempList));
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isThisStaffCslFAndS():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if(tempList!=null && tempList.size()>0){
			logger.debug("isThisStaffCslFAndS : " + tempList.get(0));
			return "Y".equals(tempList.get(0));
		}else{
			return false;
		}
	}	

	public List<ImsAlertMsgDTO> getImsAlertMsgDTOListByOrderIdList (List<String> orderIdList)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();
				
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer( 
			" select * from (" +
			" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , b.FIRST_NAME||' '||b.LAST_NAME  as cust_Name," +
			" b.SERVICE_NUM, c.LOGIN_ID, to_char(a.APP_DATE,'DD/MM/YYYY HH:MI:SS AM') APP_DATE, a.order_Status, '' err, '' recall, " +
			" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY,  c.IMS_ORDER_TYPE " +
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" );
		
		sql.append(" and a.order_id in (:orderIds) ");
//		System.out.println("num of tempOrderIds:"+orderIdList.size());
//		System.out.println(gson.toJson(orderIdList));
		params.addValue("orderIds", orderIdList);
		
		sql.append(" order by a.create_date desc)" +
			" ") ;

	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setAlertStatus((String) rs.getString("alert_Status"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setError((String) rs.getString("err"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setLoginId((String) rs.getString("LOGIN_ID"));
			dto.setOcid((String) rs.getString("OCID"));
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
			dto.setSalesCd((String) rs.getString("SALES_CD"));
			dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			dto.setImsOrderType((String) rs.getString("IMS_ORDER_TYPE"));
			return dto;
		}
	};

	try {
		logger.debug("getImsOrderListByOrderIdList :" + sql);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getImsOrderListByOrderIdList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}
	
	
	public List<String> getWqNatureWhichNeedSyncBackFromBom ()	throws DAOException {
		List<String> result = new ArrayList<String>();		
		StringBuffer SQL =  new StringBuffer("select code from w_code_lkup WHERE GRP_ID = 'IMS_AMEND_FROM_BOM'") ;			
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return (String) rs.getString("code");
			}
		};	
		try {
			result = simpleJdbcTemplate.query(SQL.toString(), mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.error("EmptyResultDataAccessException");
			result = null;
		} catch (Exception e) {
			logger.error("Exception caught in getWqNatureWhichNeedSyncBackFromBom():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		logger.info("getWqNatureWhichNeedSyncBackFromBom result:"+ gson.toJson(result));
		return result;
	}

	

	public List<ImsAutoSyncWQDTO> getPendingFromSpringBoard ()
	throws DAOException {
		List<ImsAutoSyncWQDTO> orderIdList = new ArrayList<ImsAutoSyncWQDTO>();
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer SQL =  new StringBuffer( 
				"	 select b.WQ_WP_ASSGN_ID, a.sb_id, amend_seq, nature, d.WQ_BATCH_ID from " +
				" Q_WORK_QUEUE a, Q_WQ_WP_ASSGN b, Q_WQ_WQ_NATURE_ASSGN c , BOMWEB_AMEND_CATEGORY d " +
				" where a.WQ_ID = b.WQ_ID " +
				" and a.WQ_ID=c.WQ_ID " +
				" and b.WQ_BATCH_ID=c.WQ_BATCH_ID " +
				" and b.WQ_BATCH_ID=d.WQ_BATCH_ID"+
				" and c.WQ_NATURE_ID = d.nature " +
				" and a.SB_ID=d.ORDER_ID " +
				" and DECODE(d.send ,'Y',d.send ,'N',d.send ,NULL) = 'Y'  " +
				" union all " +
				" select -1, order_id, amend_seq, nature, e.WQ_BATCH_ID from BOMWEB_AMEND_CATEGORY e" +
				" where nature = '10001' " +
				" and DECODE(e.send ,'Y',e.send ,'N',e.send ,NULL) = 'Y' ") ;
		
		
	ParameterizedRowMapper<ImsAutoSyncWQDTO> mapper = new ParameterizedRowMapper<ImsAutoSyncWQDTO>() {
		public ImsAutoSyncWQDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAutoSyncWQDTO tempResult = new ImsAutoSyncWQDTO();
			tempResult.setAmendSeq((String) rs.getString("amend_seq"));
			tempResult.setSbid((String) rs.getString("sb_id"));
			tempResult.setWqNatureID((String) rs.getString("nature"));
			tempResult.setWqWpAssgnId((String) rs.getString("WQ_WP_ASSGN_ID"));
			tempResult.setWqBatchId((String) rs.getString("WQ_BATCH_ID"));
			return tempResult;
		}
	};

	try {
		logger.debug("getPendingCCAutoWQ : " + SQL);
		orderIdList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		orderIdList = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getPendingCCAutoWQ():", e);
		throw new DAOException(e.getMessage(), e);
	}	
//	System.out.print("wq orderIdList:");
//	for(ImsAutoSyncWQDTO dto:orderIdList){
//		System.out.print("\t"+dto.getSbid());
//	}
//	System.out.println("");
		return orderIdList;
	}
	
	
	public void insertNewWQStatus(String wqWpAssgnId) throws DAOException{
		MapSqlParameterSource params = new MapSqlParameterSource();
		String SQL="Insert into Q_WQ_WP_ASSGN_STATUS_LOG" +
				"   (WQ_WP_ASSGN_ID, LATEST_STATUS_IND, STATUS_CD, CREATE_BY, CREATE_DATE, LAST_UPD_BY, LAST_UPD_DATE)" +
				" Values" +
				"   (:WqWpAssgnId, 'Y', '090', 'ImsAutoAmend', sysdate, 'ImsAutoAmend', sysdate)";

		params.addValue("WqWpAssgnId", wqWpAssgnId);
		
		try{
//			logger.info("insertNewWQStatus:"+SQL);
			simpleJdbcTemplate.update(SQL,params);

		}catch (Exception e) {
			logger.error("Exception caught in insertNewWQStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}

	}
	
	
	public void setWQStatusOutdated(String wqWpAssgnId) throws DAOException{		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String SQL = "	update Q_WQ_WP_ASSGN_STATUS_LOG " +
				"	set LATEST_STATUS_IND = 'N' " +
				"	where LATEST_STATUS_IND ='Y' " +
				"	and WQ_WP_ASSGN_ID = :WqWpAssgnId	" ;
		
		params.addValue("WqWpAssgnId", wqWpAssgnId);

		try {
//			logger.info("updateCCAutoWQStatus:"+SQL);
			simpleJdbcTemplate.update(SQL,params);

		} catch (Exception e) {
			logger.error("Exception caught in updateCCAutoWQStatus()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}	
	
	
	public void updateBomwebAmendCategory(String sbid, String wqNatureId) throws DAOException{		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String SQL = "	update BOMWEB_AMEND_CATEGORY " +
				"	set SEND = 'D' " +
				"	where SEND ='Y' " +
				"	and order_id = :order_id	" +
				"	and NATURE = :wqNatureId	" ;
		
		params.addValue("order_id", sbid);
		params.addValue("wqNatureId", wqNatureId);

		try {
//			logger.info("updateBomwebAmendCategory:"+SQL);
			simpleJdbcTemplate.update(SQL,params);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebAmendCategory()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}
	
	

	public void updateBomwebAmendCategoryFS(String sbid) throws DAOException{		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		String SQL = " update BOMWEB_AMEND_CATEGORY d set d.send = 'D' where d.ORDER_ID in (" +
				" select a.sb_id from  Q_WORK_QUEUE a, Q_WQ_WP_ASSGN b, Q_WQ_WP_ASSGN_STATUS_LOG c" +
				" where a.sb_id = :order_id" +
				" and a.SB_ID = d.ORDER_ID" +
				" and a.WQ_ID = b.WQ_ID" +
				" and b.WQ_WP_ASSGN_ID = c.WQ_WP_ASSGN_ID" +
				" and b.WQ_BATCH_ID = d.WQ_BATCH_ID" +
				" and DECODE(d.send ,'Y',d.send ,'N',d.send ,NULL) = 'Y' " +
				" and c.LATEST_STATUS_IND = 'Y'" +
				" and c.STATUS_CD in (select code from Q_DIC_CODE_LKUP where grp_id in ('WQ_ENDING_STATUS')))";
				
		params.addValue("order_id", sbid);

		try {
//			logger.info("updateBomwebAmendCategoryFS:"+SQL);
			simpleJdbcTemplate.update(SQL,params);

		} catch (Exception e) {
			logger.error("Exception caught in updateBomwebAmendCategoryFS()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
	}

	public String getWQChannelCD(String wqNatureID, String wqType)	throws DAOException {
		logger.debug("getWQChannelCD: " + wqNatureID + "\twqType:"+wqType);
		

		String SQL = 	" select wp_channel_cd " +
				" from Q_WP_WQ_NATURE_assgn " +
				" where WQ_NATURE_ID ='" + wqNatureID +"'" +
				" and WQ_TYPE = '" + wqType +"'" ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String channel = new String();
		    	channel=(rs.getString("wp_channel_cd"));
		        return channel;
		    }
		};
		
    	List<String> channelList = new ArrayList<String>();
		
		try {
			logger.debug("getWQChannelCD SQL:" + SQL);
			channelList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			channelList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getWQChannelCD:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		if(channelList.size()>0){
			logger.info("WQ Channel CD:"+channelList.get(0));
		}
		return (channelList.size()>0 ? channelList.get(0):null);
	}
	
	public String getRealStaffID(String salesCD)	throws DAOException {
		logger.debug("getRealStaffID: " + salesCD);
		
		String SQL=" select staff_id from bomweb_sales_lkup_v         " +
				" where staff_id <> org_staff_id   " +
				" and org_staff_id ='"+salesCD+"'              " +
				" and channel_id in " +
				" (SELECT code_desc FROM bomweb_code_lkup WHERE code_type='SBIMS_CHANNEL_SUPPORTED' AND code_id='CHANNEL_ID')      " +
				"   and rownum = 1  ";
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("staff_id"));
		        return temp;
		    }
		};
		
    	List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("getRealStaffID SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (Exception e) {
			logger.error("Exception caught in getRealStaffID:", e);
			try {
				SQL = 	" select staff_id from bomweb_sales_profile		" +
				" where staff_id <> org_staff_id						" +
				" and start_date<=trunc(sysdate)						" +
				" and (trunc(sysdate) < end_date or end_date is null)	" +
				" and org_staff_id ='"+salesCD+"' 						" +
				" and rownum = 1 										" ;
				resultList = simpleJdbcTemplate.query(SQL, mapper);
			} catch (Exception e2) {
				logger.error("Exception caught in getRealStaffID:", e2);
				throw new DAOException(e.getMessage(), e);
			}
		}	
		if(resultList.size()>0){
			logger.info("getRealStaffID:"+resultList.get(0));
		}
		return (resultList.size()>0 ? resultList.get(0):null);
	}
	
	public Boolean isStaffExist(String salesCD)	throws DAOException {
		logger.debug("isStaffExist: " + salesCD);
		
		String SQL = 	" select count(*) cnt from " +
				" bomweb_sales_lkup_v " +
				" where staff_id like '"+salesCD+"'  " +
				" or org_staff_id = '"+salesCD+"' " ;
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String temp = new String();
		    	temp=(rs.getString("cnt"));
		        return temp;
		    }
		};
		
    	List<String> resultList = new ArrayList<String>();
		
		try {
			logger.debug("isStaffExist SQL:" + SQL);
			resultList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			resultList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in isStaffExist:", e);
			throw new DAOException(e.getMessage(), e);
		}	
		return (resultList.size()>0 ? !"0".equals(resultList.get(0)):false);
	}
	

	public boolean isShortage(String orderId)
	throws DAOException {
			String SQL=	" select resource_shortage_ind from bomweb_addr_inventory " +
					" where  order_id =  '" + orderId +"'";
			
			ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			    	String temp = new String();
			    	temp=(rs.getString("resource_shortage_ind"));
			        return temp;
			    }
			};
			
	    	List<String> resultList = new ArrayList<String>();
			
			try {
				logger.debug("isShortage: " + SQL);
				resultList = simpleJdbcTemplate.query(SQL, mapper);
			} catch (EmptyResultDataAccessException erdae) {
				logger.debug("EmptyResultDataAccessException");
				resultList = null;
			} catch (Exception e) {
				logger.debug("Exception caught in getTypeSubTypeByWqNatureID():", e);
				throw new DAOException(e.getMessage(), e);
			}	
			
			if(resultList!=null && resultList.size()>0){
				if("Y".equalsIgnoreCase(resultList.get(0))){
					logger.info(orderId+" is Shortage");
					return true;
				}else{
					logger.info(orderId+" is not Shortage");
					return false;
				}
			}else{
				logger.error(orderId+" not found in bomweb_addr_inventory");
				return false;
			}
		
	}

	
	
	
	public String getWqSrd (String orderId)
	throws DAOException {
		List<String> tempList = new ArrayList<String>();
				
		StringBuffer SQL =  new StringBuffer( 
				"	  select to_char(SRD,'yyyyMMdd') SRD from Q_WORK_QUEUE where sb_id = '"+orderId+"'" ) ;
				
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("SRD");
				return tempResult;
			}
		};

	try {
		logger.debug("getWqSrd : " + SQL);
		tempList = simpleJdbcTemplate.query(SQL.toString(), mapper);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		tempList = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getWqSrd():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		if(tempList!=null && tempList.size()>0){
			logger.debug("getWqSrd : " + tempList.get(0));
			return tempList.get(0);
		}else{
			return null;
		}
	}

	public Boolean checkIfNeedAppointment(String orderId) throws DAOException {
		logger.debug("checkIfNeedAppointment orderId: " + orderId);
		
		String SQL = "select nvl(field_ind,'N') fieldVisit " +
				" from bomweb_order_ims " +
				" where order_id = '" + orderId +"' " +
				" and ims_order_type in ('PCD_T','PCD_R') ";
		
		
		ParameterizedRowMapper<String > mapper = new ParameterizedRowMapper<String >() {
		    public String  mapRow(ResultSet rs, int rowNum) throws SQLException {
		    	String  temp = new String ();
		    	temp = (rs.getString("fieldVisit"));
		        return temp;
		    }
		};
		
    	List<String > tempList = new ArrayList<String >();
		
		try {
			logger.debug("checkIfNeedAppointment SQL: " + SQL);
			tempList = simpleJdbcTemplate.query(SQL, mapper);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList=null;
		} catch (Exception e) {
			logger.debug("Exception caught in checkIfNeedAppointment():", e);
			throw new DAOException(e.getMessage(), e);
		}
		if(tempList.size()>0 && "N".equalsIgnoreCase(tempList.get(0))){
			logger.debug("orderId: " + orderId+" checkIfNeedAppointment: false");
			return false;
		}else{
			logger.debug("orderId: " + orderId+" checkIfNeedAppointment: true");
			return true;
		}
	}
	public List<String> getSBEndingStatus()  throws DAOException{
		List<String> result = new ArrayList<String>();
		StringBuffer SQL =  new StringBuffer("	select code from w_code_lkup where grp_id = 'IMS_SB_ENDING_STATUS'	" );
	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("code");
			return tempResult;
		}
	};
	try {
		logger.debug("getSBEndingStatus : " + SQL);
		result = simpleJdbcTemplate.query(SQL.toString(), mapper);
	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.debug("Exception caught in getSBEndingStatus():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}

	public void updateBomwebAmendCategory(AmendOrderImsUI amend) throws DAOException{
		for(String wqNatureID:amend.getWqNatureIDList()){
			MapSqlParameterSource params = new MapSqlParameterSource();			
			String SQL = "	update bomweb_amend_category set wq_batch_id = :WQ_BATCH_ID " +
					"  where order_Id = :order_Id and nature = :wq_nature_id	" +
					" and WQ_BATCH_ID is null ";

			params.addValue("order_Id", amend.getOrderImsUI().getOrderId());
			params.addValue("WQ_BATCH_ID", getLatestAmendWqBatchId(amend.getOrderImsUI().getOrderId(), wqNatureID));
			params.addValue("wq_nature_id", wqNatureID);
			
			try{
				logger.debug("key search updateBomwebAmendCategory: " + params.getValues());
				simpleJdbcTemplate.update(SQL,params);				
			}catch (Exception e) {
				logger.error("Exception caught in updateBomwebAmendCategory()", e);
				throw new DAOException(e.getMessage(), e);
			}	
		}
	}

	public void updateBomwebAmendCategoryMarkX(AmendOrderImsUI amend) throws DAOException{
		for(String wqNatureID:amend.getWqNatureIDList()){
			MapSqlParameterSource params = new MapSqlParameterSource();			
			String SQL = "	update bomweb_amend_category set send = 'X' " +
					"  where order_Id = :order_Id and nature = :wq_nature_id	" +
					" and send = 'N' ";

			params.addValue("order_Id", amend.getOrderImsUI().getOrderId());
			params.addValue("wq_nature_id", wqNatureID);
			
			try{
				logger.debug("key search updateBomwebAmendCategoryMarkX: " + params.getValues());
				simpleJdbcTemplate.update(SQL,params);				
			}catch (Exception e) {
				logger.error("Exception caught in updateBomwebAmendCategoryMarkX()", e);
				throw new DAOException(e.getMessage(), e);
			}	
		}
	}

	public void updateNowRetAmendTvOnlyToAuto(AmendOrderImsUI amend) throws DAOException{
		logger.info("updateNowRetAmendTvOnlyToAuto:"+amend.getOrderImsUI().getOrderId());
	
		String SQL = "	UPDATE  q_wq_wp_assgn a SET WP_ID = '20'" +
				"   WHERE wq_type in ('SB-PCD','SB-NOW')" +
				"     AND wq_subtype = 'ORDER_AMEND'" +
				"     AND a.wq_id = (SELECT b.wq_id" +
				"                      FROM q_work_queue b" +
				"                     WHERE sb_id = :orderId)" +
				"     AND a.wq_wp_assgn_id IN" +
				"            (SELECT c.wq_wp_assgn_id" +
				"               FROM q_wq_wp_assgn_status_log c" +
				"              WHERE c.latest_status_ind = 'Y'" +
				"                AND wq_wp_assgn_id IN (" +
				"                                SELECT wq_wp_assgn_id" +
				"                                  FROM q_wq_wp_assgn" +
				"                                 WHERE wq_id IN (" +
				"                                                 SELECT wq_id" +
				"                                                 FROM q_work_queue" +
				"                                                  WHERE sb_id = :orderId ))" +
				"                AND c.status_cd NOT IN (SELECT code" +
				"                                          FROM q_dic_code_lkup" +
				"                                         WHERE grp_id IN 'WQ_ENDING_STATUS'))	";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("orderId", amend.getOrderImsUI().getOrderId());
		
		try{
				logger.debug("updateNowRetAmendTvOnlyToAuto: " + SQL);
				simpleJdbcTemplate.update(SQL,params);
		}catch (Exception e) {
			logger.error("Exception caught in updateNowRetAmendTvOnlyToAuto()", e);
			throw new DAOException(e.getMessage(), e);
		}			
	}
	
	
	public String getPriorityWpId (String salesCd, String createdBy, String grpId) throws DAOException {
		List<String> tempList = new ArrayList<String>();
				
		StringBuffer SQL =  new StringBuffer( 
				" select distinct (attb_value) from ( " +
				" select attb_value "+
				" from W_IMS_WQ_LKUP "+
				" where grp_id = :grpId "+ 
				" and (channel_cd in (select channel_cd from bomweb_sales_lkup_v where staff_id = :salesCd or org_staff_id = :salesCd)  or channel_cd = '*')  "+
				" and (centre_cd in (select centre_cd from bomweb_sales_lkup_v where staff_id = :salesCd or org_staff_id = :salesCd)  or centre_cd = '*')  "+
				" and (team_cd in (select team_cd from bomweb_sales_lkup_v where staff_id = :salesCd or org_staff_id = :salesCd) or team_cd = '*') "+
				" and attb_name = 'PRIORITY_WP_ID' "+
				" UNION "+ 
				" select attb_value "+ 
				" from W_IMS_WQ_LKUP "+
				" where grp_id = :grpId "+ 
				" and (channel_cd in (select channel_cd from bomweb_sales_lkup_v where staff_id = :createdBy or org_staff_id = :createdBy)  or channel_cd = '*')  "+
				" and (centre_cd in (select centre_cd from bomweb_sales_lkup_v where staff_id = :createdBy or org_staff_id = :createdBy)  or centre_cd = '*')  "+
				" and (team_cd in (select team_cd from bomweb_sales_lkup_v where staff_id = :createdBy or org_staff_id = :createdBy) or team_cd = '*')  "+
				" and attb_name = 'PRIORITY_WP_ID' "+
				" ) where rownum = 1 "	
				) ;
				
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("salesCd", salesCd);
		params.addValue("createdBy", createdBy);
		params.addValue("grpId", grpId);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("attb_value");
				return tempResult;
			}
		};

		try {
			logger.debug("getPriorityWpId parms: " + params.getValues());
			tempList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
			logger.debug("getPriorityWpId result: " + gson.toJson(tempList));
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getPriorityWpId():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if(tempList!=null && tempList.size()>0){
			logger.debug("getPriorityWpId : " + tempList.get(0));
			return tempList.get(0);
		}else{
			return null;
		}
	}	
	
	public String getPriorityWpId (String salesCd, String createdBy) throws DAOException {
		List<String> tempList = new ArrayList<String>();
				
		StringBuffer SQL =  new StringBuffer( 
				"select distinct (attb_value) from ( " +
				"select attb_value "+
				"from W_IMS_WQ_LKUP "+
				"where grp_id = 'BOM2017038' "+ 
				"and channel_cd in (select channel_cd from bomweb_sales_lkup_v where staff_id = :salesCd or org_staff_id = :salesCd) "+
				"and centre_cd in (select centre_cd from bomweb_sales_lkup_v where staff_id = :salesCd or org_staff_id = :salesCd) "+
				"and attb_name = 'PRIORITY_WP_ID' "+
				"UNION "+ 
				"select attb_value "+ 
				"from W_IMS_WQ_LKUP "+
				"where grp_id = 'BOM2017038' "+ 
				"and channel_cd in (select channel_cd from bomweb_sales_lkup_v where staff_id = :createdBy or org_staff_id = :createdBy) "+
				"and centre_cd in (select centre_cd from bomweb_sales_lkup_v where staff_id = :createdBy or org_staff_id = :createdBy) "+
				"and attb_name = 'PRIORITY_WP_ID' "+
				") where rownum = 1 "	
				) ;
				
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("salesCd", salesCd);
		params.addValue("createdBy", createdBy);
		
		ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				String tempResult = "";
				tempResult=(String) rs.getString("attb_value");
				return tempResult;
			}
		};

		try {
			logger.debug("getPriorityWpId : " + SQL);
			tempList = simpleJdbcTemplate.query(SQL.toString(), mapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			logger.debug("EmptyResultDataAccessException");
			tempList = null;
		} catch (Exception e) {
			logger.debug("Exception caught in getPriorityWpId():", e);
			throw new DAOException(e.getMessage(), e);
		}	
		
		if(tempList!=null && tempList.size()>0){
			logger.debug("getPriorityWpId : " + tempList.get(0));
			return tempList.get(0);
		}else{
			return null;
		}
	}	
	public List<ImsAlertMsgDTO> getImsOrderEnquiryListInfo (CcLtsImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<ImsAlertMsgDTO> result = new ArrayList<ImsAlertMsgDTO>();

		logger.info("enquiry: " + gson.toJson(enquiry));
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer(  
			" select * from (" +
			" select a.order_id, a.SALES_TEAM, '' as alert_Status, a.OCID , " +
			" decode(b.ID_DOC_TYPE, 'BS', b.company_name, b.last_name || ' ' || b.first_name)  AS cust_name ," +
			" b.SERVICE_NUM, c.LOGIN_ID, " + 
			" decode(substr(a.order_id, 6,1),'T',to_char(a.APP_DATE,'YYYY/MM/DD'), to_char(a.APP_DATE,'YYYY/MM/DD HH24:MI:SS')) APP_DATE, " +
			" a.order_Status, '' err, '' recall, " +
			" a.SALES_CD, a.SALES_CHANNEL,  a.CREATE_BY, a.reason_cd,  c.IMS_ORDER_TYPE  " +
			", a.LAST_UPDATE_DATE, "+"decode(b.ID_DOC_TYPE, 'BS', '********', b.last_name || ' ' || '****')  AS mask_name , a.order_type, "+//ims celia ds ntv 20150805
			" a.create_date " + // ims martin 20160425
			" from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c " +
			" where a.order_id = b.order_id" +
			" and a.order_id = c.order_id" );
		

		sql.append(" and a.order_id in (:orderIds) ");
		List<String> tempOrderIds =  this.getImsOrderIdsForEnquiry(enquiry, userDto);
		logger.info("tempOrderIds: "+gson.toJson(tempOrderIds));
		params.addValue("orderIds", tempOrderIds);
		
		
		sql.append(" order by a.create_date desc)" +
			" where rownum< (select to_number(description) from w_code_lkup where grp_id =  'IMS_SB_PARM' and CODE = 'ORD_ENQ_MAX') ") ;

	ParameterizedRowMapper<ImsAlertMsgDTO> mapper = new ParameterizedRowMapper<ImsAlertMsgDTO>() {
		public ImsAlertMsgDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ImsAlertMsgDTO dto = new ImsAlertMsgDTO();
			dto.setAppDate((String) rs.getString("APP_DATE"));
			dto.setAlertStatus((String) rs.getString("alert_Status"));
			dto.setCustName((String) rs.getString("cust_Name"));
			dto.setError((String) rs.getString("err"));
			dto.setSalesTeam((String) rs.getString("SALES_TEAM"));
			dto.setLoginId((String) rs.getString("LOGIN_ID"));
			dto.setOcid((String) rs.getString("OCID"));
			dto.setOrderId((String) rs.getString("order_id"));
			dto.setOrderStatus((String) rs.getString("order_Status"));
			dto.setServiceNum((String) rs.getString("SERVICE_NUM"));
			dto.setSalesCd((String) rs.getString("SALES_CD"));
			dto.setSalesChannel((String) rs.getString("SALES_CHANNEL"));
			dto.setCreateBy((String) rs.getString("CREATE_BY"));
			dto.setReasonCD((String) rs.getString("reason_cd"));
			dto.setImsOrderType((String) rs.getString("IMS_ORDER_TYPE"));
			dto.setLastUpdDate(rs.getDate("LAST_UPDATE_DATE"));
			dto.setMaskName((String) rs.getString("mask_name"));
			dto.setOrderType((String) rs.getString("order_type"));
			dto.setCreateDate(rs.getTimestamp("CREATE_DATE"));
			return dto;
		}
	};

	try {
		logger.debug("final search Sql:" + sql);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);
		
	} catch (EmptyResultDataAccessException erdae) {
		logger.error("EmptyResultDataAccessException");
		result = null;
	} catch (Exception e) {
		logger.error("Exception caught in getImsOrderEnquiryList():", e);
		throw new DAOException(e.getMessage(), e);
	}	
		return result;
	}
	
	public List<String> getImsOrderIdsForEnquiry (CcLtsImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto)
	throws DAOException {
		List<String> result = null;


		MapSqlParameterSource params = new MapSqlParameterSource();
		
		StringBuffer sql =  new StringBuffer( " SELECT order_id FROM (select distinct order_id from ( "
			+ "  select a.order_id, a.app_date  "
      + " from bomweb_order a, bomweb_customer b, BOMWEB_ORDER_IMS c "
      + " where a.order_id = b.order_id "
      + " and a.order_id = c.order_id "
      + " and (a.shop_cd not in 	(Select code_id from BOMWEB_CODE_LKUP sbof 	where sbof.code_type = 'IMS_NON_RETAIL_SHOP_CD')  "
      + " or a.shop_cd  is null)  " 			);
		
			if(enquiry.getOrderId()!=null&&!enquiry.getOrderId().equals("")){
			sql.append(" and a.order_id = :OrderID ");
			params.addValue("OrderID", enquiry.getOrderId());
		}
		if(enquiry.getDocType()!=null&&!enquiry.getDocType().equals("") &&
		   enquiry.getDocNum() !=null&&!enquiry.getDocNum() .equals("")	){
			sql.append(" and b.id_doc_type = :DocType ");
			sql.append(" and b.id_doc_num = :DocNum ");
			params.addValue("DocType", enquiry.getDocType());
		params.addValue("DocNum", enquiry.getDocNum());
		}
		if(enquiry.getSerNum()!=null&&!enquiry.getSerNum().equals("")){
			sql.append(" and b.service_num = :SerNum ");
				params.addValue("SerNum", enquiry.getSerNum());
		}
		if(enquiry.getLoginId()!=null&&!enquiry.getLoginId().equals("")){
			sql.append(" and c.login_id = :LoginId ");
				params.addValue("LoginId", enquiry.getLoginId());
		}		
		sql.append(" and a.LOB in (:LOB) ");
		sql.append(" and a.order_id like 'C%' ");
		
		sql.append("	order by app_date desc)) where rownum<1001	" );
		
	
		params.addValue("LOB", "IMS");
	

	ParameterizedRowMapper<String> mapper = new ParameterizedRowMapper<String>() {
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String tempResult = "";
			tempResult=(String) rs.getString("order_id");
			return tempResult;
		}
	};
	try {
		logger.info("key search: " + params.getValues()+" userId @ getImsOrderIdByUserID: " + userDto.getUsername());
		logger.debug("final getImsOrderIdByUserID: " + sql);
		result = simpleJdbcTemplate.query(sql.toString(), mapper, params);

	} catch (EmptyResultDataAccessException erdae) {
		logger.debug("EmptyResultDataAccessException");
		result = new ArrayList<String>();
		result.add("''");
	} catch (Exception e) {
		logger.debug("Exception caught in getImsOrderIdByUserID():", e);
		throw new DAOException(e.getMessage(), e);
	}	
	if(result.size()==0){
		result.add("''");
	}
		return result;
	}
	
	
}



