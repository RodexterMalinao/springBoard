package com.bomltsportal.util;

import com.bomwebportal.lts.util.LtsBackendConstant;
import java.util.HashMap;
import java.util.TreeMap;//POPD


public class BomLtsConstant extends LtsBackendConstant{
	public static final String SESSION_ORDER_CAPTURE = "sessionOnlineOrderCapture";
	public static final String SESSION_OSUID = "osuid";
	public static final String SESSION_OPTION_VT = "vt_mode";
	public static final String SESSION_LTS_ONLINE_REQUEST = "lts_online_request";
	public static final String SESSION_LTS_ONLINE_REQUEST_SEQ = "lts_online_request_seq";
	public static final String SESSION_CURRENT_PAGE = "lts_current_page";
	
	public static final String LOG_TRACK_ITEM_CD_SB_NO = "SB_NO";
	public static final String LOG_TRACK_ITEM_CD_BASKET_ID = "BASKET_ID";
	public static final String LOG_TRACK_ITEM_CD_ORDER_ID = "ORDER_ID";
	public static final String LOG_TRACK_ITEM_CD_USER_ID = "USER_ID";

	public static final String TOP_NAV_IND_CONFIRM_ADDRESS = "A";
	public static final String TOP_NAV_IND_SELECT_SERVICE = "S";
	public static final String TOP_NAV_IND_REGISTER = "R";
	
	public static final int IDEN_SRV_NUM_DISPLAY_AMOUNT = 30;

	public static final String DN_POOL_STATUS_INITIAL = "I";
	public static final String DN_POOL_STATUS_LOCKED = "L";
	public static final String DN_POOL_STATUS_ASSIGNED = "A";

	public static final String DUMMY_BASKET_ID_EYE = "301010000";
	public static final String DUMMY_BASKET_ID_DEL = "301010001";
	
	public static final String DN_POOL_STATUS_NORMAL = "N";
	public static final String DN_POOL_STATUS_SOLD = "S";
	public static final String DN_POOL_STATUS_PRE_ASSIGN = "P";
	
	/*IMS address search*/
	public static final String IDX_SEARCH_TYPE = "searchType";
	public static final String IDX_SEARCH_KEY = "searchKey";
	public static final String IDX_RESULT_KEY = "resultKey";
	public static final String IDX_RESULT_EN = "resultEn";
	public static final String IDX_RESULT_CH = "resultCh";
	public static final String IDX_DISPLAY_SEQ = "displaySeq";
	
	public static final String IDX_UNIQUE_KEY = "unique_key";
	
	public static final String IDX_LEVEL = "level";
	public static final String IDX_LAT = "lat";
	public static final String IDX_LNG = "lng";
	public static final String IDX_DESC_EN = "desc_en";
	public static final String IDX_DESC_CH = "desc_ch";
	public static final String IDX_RES_BB_IND = "res_bb";
	public static final String IDX_RES_TV_IND = "res_tv";
	public static final String IDX_BUS_BB_IND = "bus_bb";
	public static final String IDX_BUS_TV_IND = "bus_tv";
	public static final String IDX_RES_LAT = "res_lat";
	public static final String IDX_RES_LNG = "res_lng";
	public static final String IDX_BUS_LAT = "bus_lat";
	public static final String IDX_BUS_LNG = "bus_lng";
	
	public static final String IDX_KEY = "key";
	public static final String IDX_SEARCH_LAT = "idx_lat";
	public static final String IDX_SEARCH_LNG = "idx_lng";
		
	public static final String IDX_NAME_EN = "name_en";
	public static final String IDX_NAME_CH = "name_ch";
	public static final String IDX_STREET_NUM = "street_num";
	public static final String IDX_STREET_NAME_EN = "street_name_en";
	public static final String IDX_STREET_NAME_CH = "street_name_ch";
	public static final String IDX_SECTION_DESC_EN = "section_desc_en";
	public static final String IDX_SECTION_DESC_CH = "section_desc_ch";
	public static final String IDX_DISTRICT_DESC_EN = "district_desc_en";
	public static final String IDX_DISTRICT_DESC_CH = "district_desc_ch";
	public static final String IDX_AREA_DESC_EN = "area_desc_en";
	public static final String IDX_AREA_DESC_CH = "area_desc_ch";
	public static final String IDX_BUILD_XY = "build_xy";
	public static final String IDX_SITE_GROUP = "site_group";
	public static final String IDX_SF_BLDG_RES = "sf_bldg_res";
	public static final String IDX_SF_BLDG_BUS = "sf_bldg_bus";
	public static final String IDX_SECT_CD = "sect_cd";
	public static final String IDX_DISTR_CD = "distr_cd";
	public static final String IDX_AREA_CD = "area_cd";
	public static final String IDX_RES_BASIC_BW = "res_basic_bw";
	public static final String IDX_RES_FTTB_BW = "res_fttb_bw";
	public static final String IDX_RES_FTTH_BW = "res_ftth_bw";
	public static final String IDX_RES_TV_SD = "res_tv_sd";
	public static final String IDX_RES_TV_HD = "res_tv_hd";
	public static final String IDX_BUS_BASIC_BW = "bus_basic_bw";
	public static final String IDX_BUS_FTTB_BW = "bus_fttb_bw";
	public static final String IDX_BUS_FTTH_BW = "bus_ftth_bw";
	public static final String IDX_BUS_TV_SD = "bus_tv_sd";
	public static final String IDX_BUS_TV_HD = "bus_tv_hd";
	public static final String IDX_IS_RM = "is_rm";
	public static final String IDX_IS_PREMIER = "is_premier";
	public static final String IDX_IS_SINGLE_BLDG = "is_single_bldg";
	public static final String IDX_HOUSING_ADDR_EN = "housing_addr_en";
	public static final String IDX_HOUSING_ADDR_CH = "housing_addr_ch";
	public static final String IDX_IS_PH = "is_ph";
	public static final String IDX_IS_HOS = "is_hos";
	
	public static final String IDX_IS_CLEANED_VILLAGE = "is_cleaned_village";
	
	
	public static final String DUMMY_CUST_NO = "-1";
	public static final String DUMMY_ACCT_NO = "-1";
	
	public static final String USER_ID = "TLTSOLS";
	
	public static final String NOWTV_FORM_TYPE_LIST_A = "OLS_TV_A";
	public static final String NOWTV_FORM_TYPE_LIST_B = "OLS_TV_B";
	
	public static final String ONLINE_INDUSTRY_TYPE = "CODE_10";
	public static final String ONLINE_INDUSTRY_SUB_TYPE = "RES";
	
	public static final String RELATIONSHIP_CODE_SUB = "SU";
	
	public static final String AMEND_DELAY_REASON_CODE = "16";
	
	public static final String DOC_TYPE_NSD = "L009";
	public static final String DOC_NAME_NSD = "NSD Form";
	
	public static final String URL_VIEW_MSG = "message";
	public static final String URL_PARM_MSG = "msg";
	public static final String URL_PARM_MSG_CODE = "msgCode";
	
	public static final String URL_PARM_MSG_CODE_TIMEOUT = "msg.session.timeout";
	public static final String URL_PARM_MSG_CODE_AMEND_SUCCESS = "msg.amend.success";
	public static final String URL_PARM_MSG_CODE_EXCEPTION = "msg.exception";
	public static final String URL_PARM_MSG_CODE_MAINTEINANCE = "msg.maint";
	
	public static final String ONLINE_SALES_REQ_CD_LTS = "OLS_LTS";
	public static final String ONLINE_SALES_REQ_TYPE_UPGRADE_EYE = "UPGRADE_EYE";
	public static final String ONLINE_SALES_REQ_TYPE_PREMIER = "PREMIER";
	public static final String ONLINE_SALES_REQ_TYPE_NOT_CLEANED_VILLAGE = "NOT_CLEAN_VILLAGE";
	public static final String ONLINE_SALES_REQ_TYPE_SYSTEM_ERROR = "SYSTEM_ERROR";
	public static final String ONLINE_SALES_REQ_TYPE_PCD_SHORTAGE = "PCD_SHORTAGE";
	public static final String ONLINE_SALES_REQ_TYPE_PON_VILLA = "PON_VILLA";
	public static final String ONLINE_SALES_REQ_TYPE_PORTINGDN_EYE = "PORTINGDN_EYE";
	public static final String ONLINE_SALES_REQ_TYPE_PORTINGDN_DEL = "PORTINGDN_DEL";
	public static final String ONLINE_SALES_REQ_ITEM_ID_UPGRADE_EYE = "300001";
	public static final String ONLINE_SALES_REQ_ITEM_ID_PREMIER = "300002";
	public static final String ONLINE_SALES_REQ_ITEM_ID_NOT_CLEANED_VILLAGE = "300003";
	public static final String ONLINE_SALES_REQ_ITEM_ID_PCD_SHORTAGE = "300004";
	public static final String ONLINE_SALES_REQ_ITEM_ID_SYSTEM_ERROR = "300000";
	public static final String ONLINE_SALES_REQ_ITEM_ID_PON_VILLA = "300005";
	public static final String ONLINE_SALES_REQ_ITEM_ID_PCD_ROLLOUT_RETURN_ERROR = "300006";
	public static final String ONLINE_SALES_REQ_ITEM_ID_PORTINGDN_EYE = "300007";
	public static final String ONLINE_SALES_REQ_ITEM_ID_PORTINGDN_DEL = "300008";
	public static final String ONLINE_SALES_REQ_PROCESS_STATUS_INITIAL = "I";
	public static final String ONLINE_SALES_REQ_PROCESS_STATUS_PROCESSED = "P";
	public static final String ONLINE_SALES_REQ_PROCESS_STATUS_FAIL = "F";
	
	// Email Constants
	public static final String EMAIL_FILE_PATTERN = "_AF.pdf";
	public static final String EMAIL_PORT_IN_FORM_FILE_PATTERN = "_NSD.pdf";
	public static final String CODE_LKUP_EMAIL_SENDER_ADDRESS = "ADDRESS";
	public static final String CODE_LKUP_EMAIL_SENDER_NAME_ENG = "NAME_ENG";
	public static final String CODE_LKUP_EMAIL_SENDER_NAME_CHN = "NAME_CHN";
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String EMAIL_ESIG_LANG_ENG = "ENG";
	public static final String EMAIL_ESIG_LANG_CHN = "CHN";
	
	public static final String EMAIL_CONTENT_HEADER_EYE = "Thank you for your subscription of <span style=\"font-size:130%\">eye</span> service. Please find the attached Customer's Agreement for your record.";
	public static final String EMAIL_CONTENT_PRIVACY_STATEMENT_EYE = "In order to protect your privacy, the attached Customer's Agreement is protected by password. Please key in the first 6 characters of your identification document number set out in your online application in upper case format (e.g. A12345) to access your Customer's Agreement.";
	public static final String EMAIL_CONTENT_HEADER_DEL = "Thank you for your subscription of Residential Telephone Line service. Please find the attached Customer's Agreement for your record.";
	public static final String EMAIL_CONTENT_PRIVACY_STATEMENT_DEL = "In order to protect your privacy, the attached Customer's Agreement is protected by password. Please key in the first 6 characters of your identification document number set out in your online application in upper case format (e.g. A12345) to access your Customer's Agreement.";

	public static HashMap<String, String> EYE_SMS_STRING_REPLACEMENT_MAP_ENG = new HashMap<String, String>();
	static {
		EYE_SMS_STRING_REPLACEMENT_MAP_ENG.put("EYE", "eye Home Tablet Communication Package");
		EYE_SMS_STRING_REPLACEMENT_MAP_ENG.put("EYE3", "eye3 Smart Communications Service Plan");
	}
	
	public static HashMap<String, String> DEL_SMS_STRING_REPLACEMENT_MAP_ENG = new HashMap<String, String>();
	static {
		DEL_SMS_STRING_REPLACEMENT_MAP_ENG.put("DEL_TP", "Residential Telephone Line Fixed Term Service Plan");
		DEL_SMS_STRING_REPLACEMENT_MAP_ENG.put("DEL_NON_TP", "Residential Telephone Line Service Plan");
	}	
	
	public static HashMap<String, String> EYE_SMS_STRING_REPLACEMENT_MAP_CHI = new HashMap<String, String>();
	static {
		EYE_SMS_STRING_REPLACEMENT_MAP_CHI.put("EYE", "eye\u5bb6\u5c45\u5e73\u677f\u96fb\u8166\u901a\u8a0a\u7d44\u5408\u670d\u52d9\u8a08\u5283");
		EYE_SMS_STRING_REPLACEMENT_MAP_CHI.put("EYE3", "eye3\u667a\u80fd\u901a\u8a0a\u670d\u52d9\u8a08\u5283");
	}
	
	public static HashMap<String, String> DEL_SMS_STRING_REPLACEMENT_MAP_CHI = new HashMap<String, String>();
	static {
		DEL_SMS_STRING_REPLACEMENT_MAP_CHI.put("DEL_TP", "\u5bb6\u5c45\u96fb\u8a71\u7dda\u56fa\u5b9a\u671f\u670d\u52d9\u8a08\u5283");
		DEL_SMS_STRING_REPLACEMENT_MAP_CHI.put("DEL_NON_TP", "\u5bb6\u5c45\u96fb\u8a71\u7dda\u670d\u52d9\u8a08\u5283");
	}		
	
	public static HashMap<String, String> MONTH_MAPPING = new HashMap<String, String>();
	static {
		MONTH_MAPPING.put("01","January");
		MONTH_MAPPING.put("02","February");
		MONTH_MAPPING.put("03","March");
		MONTH_MAPPING.put("04","April");
		MONTH_MAPPING.put("05","May");
		MONTH_MAPPING.put("06","June");
		MONTH_MAPPING.put("07","July");
		MONTH_MAPPING.put("08","August");
		MONTH_MAPPING.put("09","September");
		MONTH_MAPPING.put("10","October");
		MONTH_MAPPING.put("11","November");
		MONTH_MAPPING.put("12","December");
	}			
	
	public static final String SMS_INQUIRIES = "1000";
	
	public static final String DEVICE_TYPE_1015 = "1015";
	public static final String DEVICE_TYPE_1020 = "1020";
	public static final String DEVICE_TYPE_1030 = "1030";	
	
	public static final String FAIL_TO_GET_SRVBDRY_IND = "000000000000";
	public static final String CODE_LKUP_ACQ_PREPAY_TENURE_BYPASS = "BYPASS";
	
	public static final String GOOGLE_MAP_API_KEY = "KEY";
	
	//==POPD==
	public static final TreeMap<String, String> DATA_PRIVACY_STATUS_MAP = new TreeMap<String, String>();
	static{
		DATA_PRIVACY_STATUS_MAP.put(DATA_PRIVACY_OPT_IND_OOA_CD, DATA_PRIVACY_OPT_IND_OOA);
		DATA_PRIVACY_STATUS_MAP.put(DATA_PRIVACY_OPT_IND_OIA_CD, DATA_PRIVACY_OPT_IND_OIA);
		DATA_PRIVACY_STATUS_MAP.put(DATA_PRIVACY_OPT_IND_OOP_CD, DATA_PRIVACY_OPT_IND_OOP);
	}
	//==POPD end==
}
