package com.bomwebportal.ims.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcCall;


import com.bomwebportal.dao.BaseDAO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

public class ImsEmailParamHelperDAO extends BaseDAO{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String grp_id ;
	private String lkup_address_code ;
	private String sender_name_chn  ;
	private String sender_name_eng ;
	private String sender_lkup_table;
	private String netvigator_sender_name_chn;
	private String netvigator_sender_name_eng;
	private String ntv_sender_name_chn;
	private String ntv_sender_name_eng;
	private String cc_sender_name_chn;
	private String cc_sender_name_eng;
	private String cc_pt_sender_name_chn;
	private String cc_pt_sender_name_eng;
	private String ds_pcd_sender_name_chn;
	private String ds_pcd_sender_name_eng;
	private String ds_ntv_sender_name_chn;
	private String ds_ntv_sender_name_eng;
	
	public String getDs_pcd_sender_name_chn() {
		return ds_pcd_sender_name_chn;
	}

	public void setDs_pcd_sender_name_chn(String ds_pcd_sender_name_chn) {
		this.ds_pcd_sender_name_chn = ds_pcd_sender_name_chn;
	}

	public String getDs_pcd_sender_name_eng() {
		return ds_pcd_sender_name_eng;
	}

	public void setDs_pcd_sender_name_eng(String ds_pcd_sender_name_eng) {
		this.ds_pcd_sender_name_eng = ds_pcd_sender_name_eng;
	}
	
	public String getDs_ntv_sender_name_chn() {
		return ds_ntv_sender_name_chn;
	}

	public void setDs_ntv_sender_name_chn(String ds_ntv_sender_name_chn) {
		this.ds_ntv_sender_name_chn = ds_ntv_sender_name_chn;
	}

	public String getDs_ntv_sender_name_eng() {
		return ds_ntv_sender_name_eng;
	}

	public void setDs_ntv_sender_name_eng(String ds_ntv_sender_name_eng) {
		this.ds_ntv_sender_name_eng = ds_ntv_sender_name_eng;
	}
	
	public String getCc_pt_sender_name_chn() {
		return cc_pt_sender_name_chn;
	}

	public void setCc_pt_sender_name_chn(String cc_pt_sender_name_chn) {
		this.cc_pt_sender_name_chn = cc_pt_sender_name_chn;
	}

	public String getCc_pt_sender_name_eng() {
		return cc_pt_sender_name_eng;
	}

	public void setCc_pt_sender_name_eng(String cc_pt_sender_name_eng) {
		this.cc_pt_sender_name_eng = cc_pt_sender_name_eng;
	}


	private enum Application{
		BomWebPortal,
		OnlineSales,
		StandaloneNowTV,
		CallCenter,
		CallCenterPremier,
		DirectSalesPCD,
		DirectSalesNTV,
		DirectSalesNTVRE,
		CallCenterNTVA
	}
	
	
	public String getNtv_sender_name_chn() {
		return ntv_sender_name_chn;
	}

	public void setNtv_sender_name_chn(String ntv_sender_name_chn) {
		this.ntv_sender_name_chn = ntv_sender_name_chn;
	}

	public String getNtv_sender_name_eng() {
		return ntv_sender_name_eng;
	}

	public void setNtv_sender_name_eng(String ntv_sender_name_eng) {
		this.ntv_sender_name_eng = ntv_sender_name_eng;
	}

	public String getNetvigator_sender_name_chn() {
		return netvigator_sender_name_chn;
	}

	public void setNetvigator_sender_name_chn(String netvigator_sender_name_chn) {
		this.netvigator_sender_name_chn = netvigator_sender_name_chn;
	}

	public String getNetvigator_sender_name_eng() {
		return netvigator_sender_name_eng;
	}

	public void setNetvigator_sender_name_eng(String netvigator_sender_name_eng) {
		this.netvigator_sender_name_eng = netvigator_sender_name_eng;
	}

	public String getSender_lkup_table() {
		return sender_lkup_table;
	}

	public void setSender_lkup_table(String sender_lkup_table) {
		this.sender_lkup_table = sender_lkup_table;
	}
	
	public String getGrp_id() {
		return grp_id;
	}

	public void setGrp_id(String grp_id) {
		this.grp_id = grp_id;
	}

	public String getLkup_address_code() {
		return lkup_address_code;
	}

	public void setLkup_address_code(String lkup_address_code) {
		this.lkup_address_code = lkup_address_code;
	}

	public String getSender_name_chn() {
		return sender_name_chn;
	}

	public void setSender_name_chn(String sender_name_chn) {
		this.sender_name_chn = sender_name_chn;
	}

	public String getSender_name_eng() {
		return sender_name_eng;
	}

	public void setSender_name_eng(String sender_name_eng) {
		this.sender_name_eng = sender_name_eng;
	}

	public String getLoginId(String order_id) throws DAOException{
		logger.info("getLoginId is called");
		
		String login_id = null;
			String SQL = 	" Select login_id	"+
							" from bomweb_order_ims "+ 
							" where order_id = ?";			
		try{
			
			login_id = simpleJdbcTemplate.queryForObject(SQL, String.class, order_id);
	
		}catch (Exception e) {
				logger.error("Exception caught in getLoginId()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return login_id;
	}
	
	public String getSenderEmailAddress() throws DAOException{
		
		logger.info("getSenderEmailAddress is called");
		
		String senderAddress = null;
			String SQL = 	" Select DESCRIPTION	"+
							" from W_CODE_LKUP "+ 
							" where GRP_ID = ? "+ 
							" and CODE = ? ";			
		try{
			
			senderAddress = simpleJdbcTemplate.queryForObject(SQL, String.class, grp_id, lkup_address_code);
	
		}catch (Exception e) {
				logger.error("Exception caught in getSenderEmailAddress()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return senderAddress;
			
	}
	
	public String getSenderEmailName(EsigEmailLang locale, String templateId, String orderType) throws DAOException {
		logger.info("getSenderEmailName locale:"+locale+" templateId:"+templateId+" orderType:"+orderType);
		
		String senderName = null;
		String code = "";
		
		switch(IdentifyApplicationByTemplateID(templateId)){
			case DirectSalesPCD:
				code = locale.equals(EsigEmailLang.CHN) ? ds_pcd_sender_name_chn : ds_pcd_sender_name_eng;
				break;
			case DirectSalesNTV:
			case CallCenterNTVA:
				code = locale.equals(EsigEmailLang.CHN) ? ds_ntv_sender_name_chn : ds_ntv_sender_name_eng;
				break;
			case DirectSalesNTVRE:
				code = locale.equals(EsigEmailLang.CHN) ? "DS_NTV_RE_NAME_CHN" : "DS_NTV_RE_NAME_ENG";
				break;
			case StandaloneNowTV:
				code = locale.equals(EsigEmailLang.CHN) ? ntv_sender_name_chn : ntv_sender_name_eng;
				break;
			case OnlineSales:
				code = locale.equals(EsigEmailLang.CHN) ? netvigator_sender_name_chn : netvigator_sender_name_eng;
				break;
			case CallCenter:
				code = locale.equals(EsigEmailLang.CHN) ? cc_sender_name_chn : cc_sender_name_eng;
				break;
			case CallCenterPremier:
				code = locale.equals(EsigEmailLang.CHN) ? cc_pt_sender_name_chn : cc_pt_sender_name_eng;
				break;
			case BomWebPortal:
				if ("NTV-A".equals(orderType)) {
					code = locale.equals(EsigEmailLang.CHN) ? ds_ntv_sender_name_chn : ds_ntv_sender_name_eng;
				} else {
					code = locale.equals(EsigEmailLang.CHN) ? sender_name_chn : sender_name_eng;
				}
				break;
				
		}
		
		
		String SQL = 	" Select DESCRIPTION	"+
						" from  "+ sender_lkup_table + 
						" where GRP_ID = ? "+ 
						" and CODE = ? ";			
			
		try{
			
			senderName = simpleJdbcTemplate.queryForObject(SQL, String.class, grp_id, code);
	
		}catch (Exception e) {
				logger.error("Exception caught in getSenderEmailAddress()", e);
				throw new DAOException(e.getMessage(), e);
		}
		return senderName;
		
		
	}
	
	private Application IdentifyApplicationByTemplateID(String templateId) throws DAOException {
//		if (templateId.equals("RT108") || templateId.equals("RT109") || templateId.equals("RT110"))
//		{
//			return Application.StandaloneNowTV;
//		}
//		else if(templateId.equals("RT104") || templateId.equals("RT105") || templateId.equals("RT106"))
//		{
//			return Application.OnlineSales;
//		}
//		else if(templateId.equals("RT112") || templateId.equals("RT113") || templateId.equals("RT114")
//				|| templateId.equals("RT012") || templateId.equals("RT013") || templateId.equals("RT014")
//			)
//		{
//			return Application.CallCenter;
//		}
//		else
//			return Application.BomWebPortal;
		
		logger.debug("IdentifyApplicationByTemplateID  templateId:"+templateId);
		String ProgramString = "";
		
		String SQL = "		Select DESCRIPTION from W_CODE_LKUP	" +
		"		 				where GRP_ID = 'IMS_EMAIL_GRP'	" +
		"		 				and code = ?            		";
		
		try{
			
			ProgramString = simpleJdbcTemplate.queryForObject(SQL, String.class, templateId);
		
		}catch (Exception e) {
				logger.error("Exception caught in IdentifyApplicationByTemplateID()"+templateId, e);
				//throw new DAOException(e.getMessage(), e);
		}
		
		if ( "CC".equalsIgnoreCase(ProgramString))
		{
			return Application.CallCenter;
		}
		else if ( "ONLINE".equalsIgnoreCase(ProgramString))
		{
			return Application.OnlineSales;
		}
		else if ( "NTV".equalsIgnoreCase(ProgramString))
		{
			return Application.StandaloneNowTV;
		}
		else if ( "CCPT".equalsIgnoreCase(ProgramString))
		{
			return Application.CallCenterPremier;
		}
		else if ("DSPCD".equalsIgnoreCase(ProgramString))
		{
			return Application.DirectSalesPCD;
		}
		else if ("DSNTV".equalsIgnoreCase(ProgramString))
		{
			return Application.DirectSalesNTV;
		}
		else if ("NTVRE".equalsIgnoreCase(ProgramString))
		{
			return Application.DirectSalesNTVRE;
		}
		else if ("CCNTVA".equalsIgnoreCase(ProgramString))
		{
			return Application.CallCenterNTVA;
		}
		else 
		{
			return Application.BomWebPortal;
		}
	}
	
	
	
	public String getCustomerIDNum(String orderId) throws DAOException{
		logger.debug("getCustomerIDNum is called");
		
		String id = "";
			String SQL = 	"Select bc.ID_DOC_NUM "	
							+ " from BOMWEB_CUSTOMER bc, bomweb_order bo "
							+ " where bc.order_id = bo.order_id "
							+ " and bo.lob = 'IMS' "
							+ " and bc.order_id = ? " ;
		try{
			
			id = simpleJdbcTemplate.queryForObject(SQL, String.class,orderId);
		
		}catch (Exception e) {
				logger.error("Exception caught in getSenderEmailAddress()", e);
				throw new DAOException(e.getMessage(), e);
		}
		
		logger.info("id got: " + id);
		
		return id;
	}
	
	public InstallationInfo getInsatllationInfo(String orderId) throws DAOException{
		logger.info("getInsatllationInfo is called. Order_id: " + orderId);
	
	
		List<InstallationInfo> installInfos;
		String SQL = 	" Select " +
						" APPNT_START_TIME, " +
						" APPNT_END_TIME " +
						" from bomweb_appointment " +
						" where " +
						" order_id = ?  " ;
				
		ParameterizedRowMapper<InstallationInfo> mapper = new ParameterizedRowMapper<InstallationInfo>() {

		public InstallationInfo mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				InstallationInfo installInfo = new InstallationInfo(
													rs.getTimestamp("APPNT_END_TIME"),
													rs.getTimestamp("APPNT_START_TIME"));
	
				return installInfo;
			}
		};
		
		try{
			
			installInfos =  simpleJdbcTemplate.query(SQL, mapper, orderId);
			
		}catch (Exception e) {
			logger.error("Exception caught in getInsatllationInfo()", e);
			throw new DAOException(e.getMessage(), e);
		}
		
		if (installInfos.size() != 1){
			throw new DAOException(installInfos.size() + " installation time info are selected! Order_id: " + orderId);
		}else
			return installInfos.get(0);
		
	}
	
	
	public void setCc_sender_name_chn(String cc_sender_name_chn) {
		this.cc_sender_name_chn = cc_sender_name_chn;
	}

	public String getCc_sender_name_chn() {
		return cc_sender_name_chn;
	}


	public void setCc_sender_name_eng(String cc_sender_name_eng) {
		this.cc_sender_name_eng = cc_sender_name_eng;
	}

	public String getCc_sender_name_eng() {
		return cc_sender_name_eng;
	}


	public class InstallationInfo{
		private String installationDate;
		private String installationStartTime;
		private String installationEndTime;
		
		public InstallationInfo(Date APPNT_END_TIME, Date APPNT_START_TIME){
			
			logger.info("APPNT_END_TIME: " + APPNT_END_TIME);
			logger.info("APPNT_START_TIME: " + APPNT_START_TIME);
			
			
			installationDate = Utility.date2String(APPNT_END_TIME, "dd/MM/yyyy");												
			installationStartTime = Utility.date2String(APPNT_START_TIME, "hh:mma");
			installationEndTime = Utility.date2String(APPNT_END_TIME, "hh:mma");
			
			if(Integer.parseInt(Utility.date2String(APPNT_START_TIME, "H"))==9 &&
					Integer.parseInt(Utility.date2String(APPNT_END_TIME, "H"))==13 ){
				installationStartTime="10:00AM";
				installationEndTime="01:00PM";								
			}else if(Integer.parseInt(Utility.date2String(APPNT_START_TIME, "H"))==9 &&
					Integer.parseInt(Utility.date2String(APPNT_END_TIME, "H"))==18 ){
				installationStartTime="10:00AM";
				installationEndTime="06:00PM";					
			}
			
			logger.info("installationStartTime: " + installationStartTime);
			logger.info("installationEndTime: " + installationEndTime);
				
				
		}
		
		public String getInstallationDate() {
			return installationDate;
		}
	
		public String getInstallationStartTime() {
			return installationStartTime;
		}
		
		public String getInstallationEndTime() {
			return installationEndTime;
		}
		
		
	}
	public Boolean isDSPCD(String templateId) throws DAOException{
		return IdentifyApplicationByTemplateID(templateId).equals(Application.DirectSalesPCD);
	}//channel 12 DS 
	public Boolean isDSNTV(String templateId) throws DAOException{
		Application app = IdentifyApplicationByTemplateID(templateId);
		return ( app.equals(Application.DirectSalesNTV)||app.equals(Application.DirectSalesNTVRE) );
		// martin, 20190211, this logic will change DisMode->E, Call Center is not needed 
	}//channel 13 DS 
	
	
	public String getDSTemplateId(String channelId, String orderType,String disMode,String param){
		logger.debug("getDSTemplateId is called");
		String channelIdMap;
		String orderTypeMap;
		
		if ("NTV-A".equals(orderType)) {
			String padString = "00";
			if (param != null && !"".equals(param) && param.startsWith("CC")) {
				channelIdMap = padString.substring(0,padString.length()-channelId.length()) + channelId;
			} else if(!"12".equalsIgnoreCase(channelId)&&!"13".equalsIgnoreCase(channelId)){
				channelIdMap = "RS";
			} else {
				channelIdMap = channelId;
			}
		} else {
			if(!"12".equalsIgnoreCase(channelId)&&!"13".equalsIgnoreCase(channelId)){
				channelIdMap = "RS";
			} else {
				channelIdMap = channelId;
			}
		}
		
		if(orderType!= null && (orderType.equalsIgnoreCase("NTV-A")
				||orderType.equalsIgnoreCase("NTVAO")||orderType.equalsIgnoreCase("NTVUS")||
				orderType.equalsIgnoreCase("NTVRE")))
			orderTypeMap = "NTV";
		else
			orderTypeMap = "PCD";
		
		String mapStr = channelIdMap+orderTypeMap+disMode+param;
		
		String ProgramString = "";
		
		String SQL = "		Select code from W_CODE_LKUP	" +
		"		 				where GRP_ID = 'IMS_EMAIL_DSTMPID'	" +
		"		 				and DESCRIPTION = ?            		";
		
		try{
			
			ProgramString = simpleJdbcTemplate.queryForObject(SQL, String.class, mapStr);
		
		}catch (Exception e) {
				logger.error("Exception caught in getDSTemplateId()", e);
				//throw new DAOException(e.getMessage(), e);
		}
		logger.info("getDSTemplateId(): "+ProgramString + " "+ SQL);
		return ProgramString;
	}
	
	public String getDSTemplateMap(String smsTmpId){
		String mapStr=null;
		String SQL = "		Select DESCRIPTION from W_CODE_LKUP	" +
		"		 				where GRP_ID = 'IMS_EMAIL_DSTMPID'	" +
		"		 				and CODE = ?            		";
		
		try{			
			mapStr = simpleJdbcTemplate.queryForObject(SQL, String.class,smsTmpId);		
		}catch (Exception e) {
				logger.error("Exception caught in getDSTemplateMap()", e);
				//throw new DAOException(e.getMessage(), e);
		}
		logger.info("getDSTemplateMap(): "+mapStr + " "+ SQL);
		return mapStr;
	}
	
	public String isNTV(String templateId) {
		String desc = "";
		String SQL = "SELECT DECODE (COUNT (*), 0, 'N', 'Y') COUNT" +
				"  FROM w_code_lkup" +
				" WHERE grp_id = 'IMS_NTV_SMS_GRP' AND code = ? ";
		try {
			desc = simpleJdbcTemplate.queryForObject(SQL, String.class, templateId);
		} catch (Exception e) {
			logger.error("Exception caught in isNTV()", e);
			return null;
		}
		if ("N".equals(desc)) {
			return null;
		}
		return desc;
	}
	
	public String getNowRetSmsMsg(String templateId, String order_id, String locale) {
		logger.info("getNowRetSmsMsg templateId:"+templateId+" order_id:"+order_id+" locale:"+locale);
		if(!"CHN".equals(locale)){
			locale="ENG";
		}
		String result = "";
		String SQL = "SELECT   REPLACE (" +
				"            REPLACE (template_content," +
				"                     '${requestDate}'," +
				"                     (SELECT   requestDate" +
				"                        FROM   (  SELECT   TRUNC (create_date) requestDate" +
				"                                    FROM   bomweb_amend_category" +
				"                                   WHERE   order_id = ?" +
				"                                ORDER BY   amend_seq DESC)" +
				"                       WHERE   ROWNUM = 1))," +
				"            '${contactPhone}'," +
				"            (SELECT   SALES_CONTACT_NUM" +
				"               FROM   bomweb_order" +
				"              WHERE   order_id = ?)" +
				"         )" +
				"            msg" +
				"  FROM   bomweb_email_template" +
				"			WHERE   template_id = ? AND locale = ? ";
		
		try {
			result = simpleJdbcTemplate.queryForObject(SQL, String.class, order_id, order_id, templateId, locale);
		} catch (Exception e) {
			logger.error("Exception caught in getNowRetSmsMsg()", e);
			return "Error";
		}
		logger.info("getNowRetSmsMsg desc:"+result);
		return result;
	}
	
}
