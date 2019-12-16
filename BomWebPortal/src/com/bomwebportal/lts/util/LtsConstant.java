package com.bomwebportal.lts.util;

import java.util.HashMap;
import java.util.TreeMap;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.pccw.util.spring.SpringApplicationContext;

public class LtsConstant extends LtsBackendConstant {

	
	 /* LTS SESSION KEY */
	public static final String SESSION_LTS_ORDER_CAPTURE = "sessionLtsOrderCapture";
	public static final String SESSION_LTS_ACQ_ORDER_CAPTURE = "sessionLtsAcqOrderCapture";
	public static final String SESSION_LTS_APN_SERIAL_FILE_CAPTURE = "sessionLtsApnSerialFileCapture";
	public static final String SESSION_LTS_TERMINATE_ORDER_CAPTURE = "sessionLtsTerminateOrderCapture";
	public static final String SESSION_BASKET_SELECTION_INFO = "sessionBasketSelectionInfo";
	public static final String SESSION_LTS_ORDER_INFO_MSG = "sessionLtsOrderInfoMsg";
	public static final String SESSION_LTS_SRV_PROFILE_MSG = "sessionLtsSrvProfileMsg";
	public static final String SESSION_LTS_ORDER_RETURN_URL = "sessionLtsOrderInfoReutrnUrl";
	public static final String SESSION_LTS_TEAM_FUNCTION = "sessionLtsTeamFunction";
	public static final String SESSION_LTS_ORDER_AMEND = "sessionLtsOrderAmend";
	public static final String SESSION_LTS_DUMMY_SB_ORDER = "sessionLtsDummySbOrder";
	public static final String SESSION_LTS_ORDER_MODE = "sessionLtsOrderMode";
	public static final String SESSION_LTS_DS_QC_PROCESS_DETAIL = "sessionLtsDsQcProcessDetail";
	
	/* COMMON LOB SESSION KEY */
	public static final String SESSION_CUST_SERACH_RESULT_LIST = "custSearchResultListSession";
	public static final String SESSION_CUST_SRV_IN_USE_LIST = "custServInUseListSession";
	public static final String SESSION_CUST_INFORMATION_FORM = "customerInformationDTOSession";
	public static final String SESSION_BOM_SALES_USER = "bomsalesuser";
	public static final String SESSION_SBUID = "sbuid";

	public static final String REQUEST_PARAM_ORDER_TYPE = "type";
	public static final String REQUEST_PARAM_ORDER_ACTION = "action";
	public static final String REQUEST_PARAM_REASON_CD = "reasonCd";
	public static final String REQUEST_PARAM_ORDER_ID = "orderId";
	public static final String REQUEST_PARAM_SRV_TYPE = "srvType";
	public static final String REQUEST_PARAM_DOC_TYPE = "docType";
	public static final String REQUEST_PARAM_DOC_ID = "docId";
	public static final String REQUEST_PARAM_SRV_NUM = "srvNum";	
	public static final String REQUEST_PARAM_TYPE_OF_SRV = "tp";
	public static final String REQUEST_PARAM_DAT_CD = "dat";
	public static final String REQUEST_PARAM_MODE = "mode";
	public static final String REQUEST_PARAM_FROM_VIEW = "fromView";
	
	public static final String REQUEST_PARAM_ORDER_ACTION_CREATE = "create";
	public static final String REQUEST_PARAM_ORDER_ACTION_ENQUIRY = "enquiry";
	public static final String REQUEST_PARAM_ORDER_ACTION_RECALL = "recall";
	public static final String REQUEST_PARAM_ORDER_ACTION_ENQUIRY_N_CANCEL = "enquiry_n_cancel";
	public static final String REQUEST_PARAM_ORDER_ACTION_RECALL_N_UPDATE_PREPAYMENT = "recall_n_update_prepayment";
	public static final String REQUEST_PARAM_ORDER_ACTION_RECALL_N_UPDATE_APPNT = "recall_n_update_appnt";
	public static final String REQUEST_PARAM_BOM_CUST_NUM = "bomCustNum";
	public static final String REQUEST_PARAM_LEGACY_CUST_NUM = "legacyCustNum";
	
	public static final String TP_CATG_REBATE = "Rebate";
	public static final String TP_CATG_PREMIUM = "Premium";
	public static final String TP_CATG_RETENTION = "RETENTION";
	
	public static final String BASKET_CATEGORY_HOT = "HOT";
	public static final String BASKET_CATEGORY_REGULAR = "REGULAR";
	public static final String BASKET_CATEGORY_PREMIUM = "PREMIUM";
	public static final String BASKET_CATEGORY_OTHER = "OTHER";
	public static final String BASKET_CATEGORY_REBATE = "REBATE";
	public static final String BASKET_CATEGORY_REBATE_COUPON = "REBATE_COUPON";
	public static final String BASKET_CATEGORY_REBATE_PREMIUM = "REBATE_PREMIUM";
    public static final String BASKET_CATEGORY_PCD_BUNDLE = "PCD_BUNDLE";
	public static final String BASKET_CATEGORY_PCD_BUNDLE_FREE = "PCD_BUNDLE_FREE";
	public static final String BASKET_CATEGORY_COUPON_IDD = "COUPON + IDD";
	public static final String BASKET_CATEGORY_REBATE_IDD = "REBATE + IDD";
	public static final String BASKET_CATEGORY_IDD = "IDD";
	
	
    public static final String EYE_DEVICE_TYPE_7_INCH = "ST3-7";
    public static final String EYE_DEVICE_TYPE_10_INCH = "ST3-10";
    
	
	public static final String INVENT_STS_WORKING = "20";
	public static final String INVENT_STS_RESERVED = "70";
	public static final String INVENT_STS_PRE_ASSIGN = "08";
	
	public static final String ERROR_VIEW = "ltserror";
	public static final String UPGRADE_EYE_ORDER_VIEW = "ltsupgradeeyeorder.html";
	public static final String TERMINATION_ORDER_VIEW = "ltsterminateorder.html";
	public static final String NEW_ACQ_ORDER_VIEW = "ltsacqorder.html";
	
	public static final String OFFER_TYPE_TERM_PLAN = "TP";
	public static final String OFFER_TYPE_PREMIUM = "PM";
	public static final String OFFER_TYPE_PROMOTION = "PR";
	public static final String OFFER_TYPE_CROSS_LOB = "CR";
	public static final String OFFER_TYPE_AUTO_RENEW = "AR";
	public static final String OFFER_TYPE_VAS = "VA";
	public static final String OFFER_TYPE_MIRROR_PLAN = "MP";
	public static final String OFFER_TYPE_EXPIRED_MIRROR_PLAN = "XMP";
	public static final String OFFER_TYPE_SPECIAL_MODE = "SM";
	public static final String OFFER_TYPE_WALL_GARDEN = "WG";
	
	public static final String CONTRACT_MONTH = "24";
	
	public static final String TRUE_IND = "true";
	public static final String FALSE_IND = "false";
	
	public static final String ORDER_ACTION_CREATE = "CREATE";
	public static final String ORDER_ACTION_RECALL = "RECALL";
	public static final String ORDER_ACTION_SIGNOFF = "SIGNOFF";
	public static final String ORDER_ACTION_APPROVE = "APPROVE";
	public static final String ORDER_ACTION_SUSPEND = "SUSPEND";
	public static final String ORDER_ACTION_CANCEL = "CANCEL";
	public static final String ORDER_ACTION_ENQUIRY = "ENQUIRY";
	public static final String ORDER_ACTION_AMEND = "AMEND";
	public static final String ORDER_ACTION_SUBMIT = "SUBMIT";
	public static final String ORDER_ACTION_AFTER_SUBMIT = "AFTER_SUBMIT";
	public static final String ORDER_ACTION_SUSPEND_W_PEND = "SUSPEND_W_PEND";
	public static final String ORDER_ACTION_ENQUIRY_N_CANCEL = "ENQUIRY_N_CANCEL";
	public static final String ORDER_ACTION_RECALL_N_UPDATE_PREPAYMENT = "RECALL_N_UPDATE_PREPAYMENT";
	public static final String ORDER_ACTION_RECALL_N_UPDATE_APPOINTMENT = "RECALL_N_UPDATE_APPOINTMENT";
	public static final String ORDER_ACTION_UPDATE_APPOINTMENT_FOR_QC = "UPDATE_APPOINTMENT_FOR_QC";
	public static final String ORDER_ACTION_UPDATE_APPOINTMENT_FOR_AWAIT_PREPAYMENT = "UPDATE_APPOINTMENT_FOR_AWAIT_PREPAYMENT";
	public static final String ORDER_ACTION_DS_SUBMIT = "DS_SUBMIT";
	
	public static final String DUPLEX_X_IND = "A";
	public static final String DUPLEX_Y_IND = "B";
	
	public static final String APPROVAL_CD_NO_REDEEM_PREMIUM = "NO-PREM";
	
	public static final String TV_TYPE_SDTV = "SDTV";
	public static final String TV_TYPE_HDTV = "HDTV";
	
	public static final String PROFILE_ITEM_TYPE_SERVICE = "S";
	public static final String PROFILE_ITEM_TYPE_VAS = "V";
	
	public static final String PREMIUM_ELIGIBLE_TYPE_PLAN = "PLAN";
	public static final String PREMIUM_ELIGIBLE_TYPE_VAS = "VAS";
	public static final String PREMIUM_ELIGIBLE_TYPE_MOOV = "MOOV";
	public static final String PREMIUM_ELIGIBLE_TYPE_CONTENT = "CONTENT";
	public static final String PREMIUM_ELIGIBLE_TYPE_NOWTV_SPEC = "NOWTV-SPEC";
	public static final String PREMIUM_ELIGIBLE_TYPE_NOWTV_PAY = "NOWTV-PAY";
	public static final String PREMIUM_ELIGIBLE_TYPE_NOWTV_MIRR = "NOWTV-MIRR";
	public static final String PREMIUM_ELIGIBLE_TYPE_2DEL_VAS = "2DEL_VAS";
	public static final String PREMIUM_ELIGIBLE_TYPE_BUNDLE = "BUNDLE";
	
	public static final String DRG_ORDER_STATUS_COMPLETED = "L";
	public static final String DRG_ORDER_STATUS_CANCELLED = "C";
	public static final String DRG_ORDER_STATUS_NUMBER_INVESTIGATE = "I";
	public static final String DRG_ORDER_STATUS_PAIR_INVESTIGATE = "P";
	public static final String DRG_ORDER_STATUS_ADDRESS_INVESTIGATE = "A";
	public static final String DRG_ORDER_STATUS_DISTRIBUTED = "D";
	public static final String DRG_ORDER_STATUS_SUSPENDED = "S";
	
	public static final String IMS_ORDER_STATUS_ISSUED = "01";
	public static final String IMS_ORDER_STATUS_COMPLETED = "03";
	public static final String IMS_ORDER_STATUS_L1_FALLOUT = "05";
	public static final String IMS_ORDER_STATUS_CANCELLED = "06";
	
	public static final String BOM_ORDER_STATUS_SUBMITTED = "01";	
	public static final String BOM_ORDER_STATUS_COMPLETED = "02";
	public static final String BOM_ORDER_STATUS_CANCELLED_W_ORDER = "04";
	public static final String BOM_ORDER_STATUS_CANCELLED_WO_ORDER = "07";
	public static final String BOM_ORDER_STATUS_SUSPWDORDER = "03";
	public static final String BOM_ORDER_STATUS_SUSPWOORDER = "06";
	public static final String BOM_ORDER_STATUS_UNDER_INVESTIGATE = "08";
	
	public static final String DWFM_INPUT_TYPE_ALL = "ALL";
	public static final String DWFM_INPUT_TYPE_UPGRADE = "UPGRADE";
	public static final String DWFM_INPUT_TYPE_2NDDEL = "2NDDEL";
	public static final String DWFM_INPUT_TYPE_NEWACQ_EYE = "NEWACQ_EYE";
	public static final String DWFM_INPUT_TYPE_NEWACQ_DEL = "NEWACQ_DEL";
	public static final String DWFM_INPUT_TYPE_PREWIRING = "PREWIRING";
	public static final String DWFM_INPUT_TYPE_DISCONNECT = "DISCONNECT";
	
	public static final String CHANNEL_ATTB_FORMAT_MOBILE_NUM = "MOBILE_NUM";
	public static final String CHANNEL_ATTB_FORMAT_EMAIL = "EMAIL_ADDR";
	public static final String CHANNEL_ATTB_FORMAT_CHECK = "CHECK";
	
	public static final String IDD_CALL_PLAN_CANCEL = "X";
	public static final String IDD_CALL_PLAN_CHANGED = "C";
	public static final String IDD_CALL_PLAN_UNCHANGED = "U";
	
	public static final String WQ_TRANS_ACTION_FAILED_OE="FAILED_IN_OE";
	public static final String WQ_TRANS_ACTION_FAILED_RESUME="FAILED_RESUME";	
	public static final String WQ_TRANS_ACTION_UPDATE_OCID=WQ_TYPE_PARTIAL;
	public static final String WQ_TRANS_ACTION_CHECK_FOR_BLACKLIST="CHECK_FOR_BLACKLIST";
	public static final String WQ_TRANS_ACTION_CHECK_FOR_IDD_FREE="CHECK_FOR_IDD_FREE";
	public static final String WQ_TRANS_ACTION_RELATED_ORDER="RELATED_ORDER";
	public static final String WQ_TRANS_ACTION_TO_INVSTGT="TO_INVESTIGATE";
	public static final String WQ_TRANS_ACTION_FROM_INVSTGT="FROM_INVESTIGATE";
	public static final String WQ_TRANS_ACTION_TO_FALLOUT="TO_FALLOUT";
	public static final String WQ_TRANS_ACTION_FROM_FALLOUT="FROM_FALLOUT";
	public static final String WQ_TRANS_ACTION_APPNT_DELAY="APPOINTMENT_DELAY";
	public static final String WQ_TRANS_ACTION_VIM_FREE="VIM_FREE_CHANNEL";
	public static final String WQ_TRANS_ACTION_BACK_ORDER="BACKDATED_ORDER";
	public static final String WQ_TRANS_ACTION_CANCEL_WQ="CANCEL_WQ";
	public static final String WQ_TRANS_ACTION_CHANGE_SRD="CHANGE_SRD";
	public static final String WQ_TRANS_ACTION_PIPB_TO_D="PIPB_TO_D";
	public static final String WQ_TRANS_ACTION_PIPB_TO_L="PIPB_TO_L";
	public static final String WQ_TRANS_ACTION_PIPB_TO_C="PIPB_TO_C";
	public static final String WQ_TRANS_ACTION_PIPB_APN_TO_C="PIPB_APN_TO_C";	
	public static final String WQ_TRANS_ACTION_PIPB_CHG_SRD="PIPB_CHANGE_SRD";
	public static final String WQ_TRANS_ACTION_PIPB_APN_RMK_W="PIPB_APN_RMK_W";
	public static final String WQ_TRANS_ACTION_PIPB_APN_RMK_F="PIPB_APN_RMK_F";

	
	public static final String WQ_TRANS_STATUS_PENDING="PENDING";
	public static final String WQ_TRANS_STATUS_READY="READY";
	public static final String WQ_TRANS_STATUS_COMPLETED="COMPLETED";
	public static final String WQ_TRANS_STATUS_CANCELLED="CANCELLED";
	public static final String WQ_TRANS_STATUS_NO_WQ_REQD="NO_WQ_REQD";
	public static final String WQ_TRANS_STATUS_ERROR="ERROR";
	
	// moved to LtsBackendConstant
//	public static final String DOC_TYPE_PASSPORT = "PASS";
//	public static final String DOC_TYPE_HKID = "HKID";
//	public static final String DOC_TYPE_HKBR = "BS";
//	public static final String DOC_TYPE_CERTIFICATE = "CERT";
	
	public static final String DUMMY_SECOND_DEL_DEVICE_TYPE = "2000";
	
	public static final String EXIST_MODEM_TYPE_NIL = "NIL";
	
	public static final String REPORT_TEMPLATE_SIGNATURE_TITLE = "SIGNATURE_TITLE";
	public static final String REPORT_TEMPLATE_SIGNATURE_NOTE = "SIGNATURE_NOTE";
	public static final String COLLECT_METHOD_DIGITAL = "D";
	
	public static final String SIGNOFF_MODE_RETAIL = "R";
	public static final String SIGNOFF_MODE_CALLCENTER = "C";
	
	public static final String CODE_LKUP_EMAIL_SENDER_ADDRESS = "ADDRESS";
	public static final String CODE_LKUP_EMAIL_SENDER_NAME_ENG = "NAME_ENG";
	public static final String CODE_LKUP_EMAIL_SENDER_NAME_CHN = "NAME_CHN";
	public static final String CODE_LKUP_EMAIL_SENDER_PT_NAME = "PT_NAME";
	public static final String CODE_LKUP_EMAIL_SENDER_CC_NAME = "CC_NAME";
	public static final String CODE_LKUP_EMAIL_SENDER_DS_NAME_ENG = "DS_NAME_ENG";
	public static final String CODE_LKUP_EMAIL_SENDER_DS_NAME_CHN = "DS_NAME_CHN";
	public static final String CODE_LKUP_EMAIL_SENDER_IGUARD_ADDRESS = "IGUARD_EMAIL";
	public static final String CODE_LKUP_EMAIL_SENDER_IGUARD_NAME_ENG = "IGUARD_ENAME";
	public static final String CODE_LKUP_EMAIL_SENDER_IGUARD_NAME_CHN = "IGUARD_CNAME";
	public static final String CODE_LKUP_EMAIL_SENDER_RS_NAME_ENG = "RS_NAME_ENG";
	public static final String CODE_LKUP_EMAIL_SENDER_RS_NAME_CHN = "RS_NAME_CHN";
	
	public static final String CODE_LKUP_IGUARD_CARECASH_EFFECTIVE = "EFFECTIVE";
	public static final String CODE_LKUP_ACQ_PREPAY_TENURE_BYPASS = "BYPASS";
	
	public static final String CHANNEL_ID_RETAIL = "1";
	public static final String CHANNEL_ID_CS = "2";
	public static final String CHANNEL_ID_PREMIER = "3";	
	public static final String[] CHANNEL_ID_ALL_CC = {CHANNEL_ID_CS, CHANNEL_ID_PREMIER};
	
	public static final String CHANNEL_ID_DIRECT_SALES_AGENT = "5";
	public static final String CHANNEL_ID_DIRECT_SALES = "12";
	public static final String CHANNEL_ID_DIRECT_SALES_NOW_TV_QA = "13";
	public static final String[] CHANNEL_ID_ALL_DIRECT_SALES = {
		CHANNEL_ID_DIRECT_SALES_AGENT, 
		CHANNEL_ID_DIRECT_SALES, 
		CHANNEL_ID_DIRECT_SALES_NOW_TV_QA};
	public static final String[] CHANNEL_ID_DIRECT_SALES_QCC = {CHANNEL_ID_DIRECT_SALES, CHANNEL_ID_DIRECT_SALES_NOW_TV_QA};
	
	public static final String CHANNEL_CD_DIRECT_SALES_AGENT_FRONTLINE = "DS5";
	public static final String CHANNEL_CD_DIRECT_SALES_QC = "QCC";
	public static final String CHANNEL_CD_DIRECT_SALES_ORDER_SUPPORT = "DS4";
	public static final String CHANNEL_CD_CUSTOMER_SERVICE = "CSS";
	
	public static final String CHANNEL_DIRECT_SALES_DEFAULT_BOC = "212";
	
	public static final String IMS_APPLICATION_METHOD_CALL_CENTER = "CSS";
	public static final String IMS_APPLICATION_METHOD_PREMIER = "HPM";
		
	public static final String EYE_TYPE_EYE3A = "eye3A";
	public static final String EYE_TYPE_EYE2A = "eye2A";
	public static final String EYE_TYPE_EYE1_5A = "eye1.5A";
	public static final String EYE_TYPE_EYE2 = "eye2";
	public static final String EYE_TYPE_EYE1 = "eye1";
	public static final String EYE_TYPE_SAMSUNG = "Samsung";
	public static final String EYE_TYPE_EYE3A_STAFF = "eye3A Staff";
	public static final String EYE_TYPE_EYE2A_STAFF = "eye2A Staff";
	
	public static final String ALERT_MSG_SRV_TYPE_TEL = "TEL";
	public static final String ALERT_MSG_SRV_TYPE_IMS = "IMS";
	
	public static final String PCD_ORDER_EXIST_NIL = "NIL";
	public static final String PCD_ORDER_EXIST_EDF = "EDF";
	public static final String PCD_ORDER_EXIST_SPRINGBOARD = "SB";

	public static final String SALES_ROLE_FRONTLINE = "FRONTLINE";
	public static final String SALES_ROLE_MANAGER = "MANAGER";
	public static final String SALES_ROLE_SALES_MANAGER = "SALES MANAGER";
	public static final String SALES_ROLE_SUPPORT = "SUPPORT";
	public static final String[] SALES_ROLES_ALL_MANAGERS = {SALES_ROLE_MANAGER, SALES_ROLE_SALES_MANAGER};
	
	public static final String MODEM_TYPE_SHARE_EX_FSA = "SHARE_EX_FSA";
	public static final String MODEM_TYPE_STANDALONE = "STANDALONE";
	public static final String MODEM_TYPE_SHARE_PCD = "SHARE_PCD";
	public static final String MODEM_TYPE_SHARE_TV = "SHARE_TV";
	public static final String MODEM_TYPE_SHARE_BUNDLE = "SHARE_BUNDLE";
	public static final String MODEM_TYPE_SHARE_OTHER_FSA = "SHARE_OTHER_FSA";
	
	public static final String SMS_MSG_KEY_CALL_CENTER = "lts.sms.callcenter";
	public static final String SMS_MSG_KEY_PREMIER = "lts.sms.premier";
	
	public static final String SEND_LETTER_PRINT_AF = "LP001";
	public static final String SEND_LETTER_PRINT_COVER_LETTER = "LP002";
	public static final String LETTER_PRINT_PAPER_MODE_AVAILABLE_DATE_LKUP = "paperModeAvailableDate";
	
	public static final String SMS_TEMPLATE_ID_EYE_SMS_CALL_CENTER = "EYESMS01";
	public static final String SMS_TEMPLATE_ID_EYE_SMS_PREMIER = "EYESMS02";
	public static final String SMS_TEMPLATE_ID_EYE_SMS_RENEWAL = "EYESMS03";
	public static final String SMS_TEMPLATE_ID_EYE_SMS_RENEWAL_PREMIER = "EYESMS04";
	public static final String SMS_TEMPLATE_ID_DEL_SMS_RENEWAL = "DELSMS01";
	public static final String SMS_TEMPLATE_ID_DEL_SMS_RENEWAL_PREMIER = "DELSMS02";
	public static final String SMS_TEMPLATE_ID_DEL_SMS_CALL_CENTER = "DELSMS03";
	public static final String SMS_TEMPLATE_ID_DEL_SMS_PREMIER = "DELSMS04";
		
	public static final String SMS_TEMPLATE_ID_EYE_SMS_DIRECT_SALES = "EYESMS05";
	public static final String SMS_TEMPLATE_ID_DEL_SMS_DIRECT_SALES = "DELSMS05";
	public static final String SMS_TEMPLATE_ID_EYE_SMS_DIRECT_SALES_WITH_PREPAYMENT = "EYESMS06";
	public static final String SMS_TEMPLATE_ID_DEL_SMS_DIRECT_SALES_WITH_PREPAYMENT = "DELSMS06";
	
	public static final String SMS_TEMPLATE_ID_EYE_SMS_TERMINATION = "EYESMS07";
	public static final String SMS_TEMPLATE_ID_DEL_SMS_TERMINATION = "DELSMS07";

	public static final String EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_MASS = "VASE01";
	public static final String EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_CALL_CENTER = "VASE01C";
	public static final String EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_RETAIL = "VASE01R";
	public static final String EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_PREMIER = "VASE01P";
	public static final String SMS_TEMPLATE_SIGNOFF_STANDALONE_VAS_MASS = "VASS01";	
	
	public static final String CAMPAIGN_LOB_LTS = "LTS";
	public static final String CAMPAIGN_LOB_IDD = "IDD";
	
	public static final String SB_LTS_FUNC_SEARCH_PROFILE = "SEARCH_PROFILE";
	
	public static final String PROD_SUB_TYPE_CD_V = "VSO-";
	public static final String SRV_TYPE_CD_SO = "SO";
	
	public static final String TEAM_AREA_CD_DEL_RETENTION = "DEL RETENTION";
	
	public static final String DISC_DECEASE_NORMAL ="N";
	public static final String DISC_DECEASE_SPECIAL ="S";
	public static final String DISC_DECEASE_INHERIT ="I";
	public static final String DISC_DECEASE_UNREACHED ="U";
	
	public static final String CUST_NAME_MATCHED = "M";
	public static final String CUST_NAME_NOT_MATCHED = "N";
	
	public static final String TAB_BASKET_CATEGORY_INITIAL = "0";
	public static final String TAB_BASKET_CATEGORY_EYE_HOT = "1";
	public static final String TAB_BASKET_CATEGORY_EYE_REGULAR = "2";
	public static final String TAB_BASKET_CATEGORY_EYE_OTHER = "3";
	public static final String TAB_BASKET_CATEGORY_DEL_PREMIUM = "4";
	public static final String TAB_BASKET_CATEGORY_DEL_REBATE = "5";
	public static final String TAB_BASKET_CATEGORY_DEL_REBATE_COUPON = "6";
	public static final String TAB_BASKET_CATEGORY_DEL_OTHER = "7";
	
	public static final String CANCEL_REASON_CD_AMEND = "AMEND";
	public static final String CANCEL_REASON_CD_CANCEL_BY_UESR = "USR_CANC";
	
	public static final TreeMap<String, String> BASKET_TAB_MAP = new TreeMap<String, String>();
	public static final String TAB_BASKET_TYPE_EYE = "EYE";
	public static final String TAB_BASKET_TYPE_DEL = "DEL";
	static {
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_INITIAL, TAB_BASKET_TYPE_EYE);
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_EYE_HOT, TAB_BASKET_TYPE_EYE);
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_EYE_REGULAR, TAB_BASKET_TYPE_EYE);
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_EYE_OTHER, TAB_BASKET_TYPE_EYE);
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_DEL_PREMIUM, TAB_BASKET_TYPE_DEL);
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_DEL_REBATE, TAB_BASKET_TYPE_DEL);
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_DEL_REBATE_COUPON, TAB_BASKET_TYPE_DEL);
		BASKET_TAB_MAP.put(TAB_BASKET_CATEGORY_DEL_OTHER, TAB_BASKET_TYPE_DEL);
	}
	
	public static final String BASKET_PROJECT_CD_EYE3_FREE_3MTH = "EYE3-FREE-3MTH";
	public static final String BASKET_PROJECT_CD_30_FREE_6 = "30Free6";
	public static final String BASKET_PROJECT_CD_30_FREE_6_ARPU_OVER_110 = "30Free6>110";
	public static final String BASKET_PROJECT_CD_2ND_CONTRACT = "2ND-CONTRACT";
	public static final String BASKET_PROJECT_CD_1ST_CONTRACT = "1ST-CONTRACT";
	public static final String BASKET_PROJECT_CD_EYE2A_FAULT = "EYE2A-FAULT";
	public static final String BASKET_PROJECT_CD_EYE2A_FAULT_SPECIAL = "EYE2A-FAULT-SPECIAL";
	public static final String BASKET_PROJECT_CD_EYE_FAULT = "EYE1/2/1.5A-FAULT";
	public static final String BASKET_PROJECT_CD_EYE_FAULT_SPECIAL = "EYE1/2/1.5A-SPECIAL";
	public static final String BASKET_PROJECT_CD_CSL = "CSL";
	
	
	public static final TreeMap<String, String> EYE_FAULT_CASE_PROJECT_CD_MAP = new TreeMap<String, String>();
	static{
		EYE_FAULT_CASE_PROJECT_CD_MAP.put(BASKET_PROJECT_CD_EYE2A_FAULT, BASKET_PROJECT_CD_EYE2A_FAULT);
		EYE_FAULT_CASE_PROJECT_CD_MAP.put(BASKET_PROJECT_CD_EYE2A_FAULT_SPECIAL, BASKET_PROJECT_CD_EYE2A_FAULT_SPECIAL);
		EYE_FAULT_CASE_PROJECT_CD_MAP.put(BASKET_PROJECT_CD_EYE_FAULT, BASKET_PROJECT_CD_EYE_FAULT);
		EYE_FAULT_CASE_PROJECT_CD_MAP.put(BASKET_PROJECT_CD_EYE_FAULT_SPECIAL, BASKET_PROJECT_CD_EYE_FAULT_SPECIAL);
	}
	
	public static final TreeMap<String, String> DATA_PRIVACY_STATUS_MAP = new TreeMap<String, String>();
	static{
		DATA_PRIVACY_STATUS_MAP.put(DATA_PRIVACY_OPT_IND_OOA_CD, DATA_PRIVACY_OPT_IND_OOA);
		DATA_PRIVACY_STATUS_MAP.put(DATA_PRIVACY_OPT_IND_OIA_CD, DATA_PRIVACY_OPT_IND_OIA);
		DATA_PRIVACY_STATUS_MAP.put(DATA_PRIVACY_OPT_IND_OOP_CD, DATA_PRIVACY_OPT_IND_OOP);
	}
	
	public static final String CUSTOMER_ACCOUNT_CHARGE_TYPE_RIAP = "RIAP";
	public static final String CUSTOMER_ACCOUNT_CHARGE_TYPE_RAP = "RAP";
	public static final String CUSTOMER_ACCOUNT_CHARGE_TYPE_I = "I";
	
	public static final TreeMap<String, String> CUSTOMER_ACCOUNT_CHARGE_TYPE_MAP = new TreeMap<String, String>();
	static{
		CUSTOMER_ACCOUNT_CHARGE_TYPE_MAP.put(CUSTOMER_ACCOUNT_CHARGE_TYPE_RIAP, "Rental & IDD");
		CUSTOMER_ACCOUNT_CHARGE_TYPE_MAP.put(CUSTOMER_ACCOUNT_CHARGE_TYPE_RAP, "Rental");
		CUSTOMER_ACCOUNT_CHARGE_TYPE_MAP.put(CUSTOMER_ACCOUNT_CHARGE_TYPE_I, "IDD");
	}
	
	public static final TreeMap<String, String> CUSTOMER_CHARGE_TYPE_CODE_CONVERSION_MAP = new TreeMap<String, String>();
	static{
		CUSTOMER_CHARGE_TYPE_CODE_CONVERSION_MAP.put("Rental & IDD", CUSTOMER_ACCOUNT_CHARGE_TYPE_RIAP);
		CUSTOMER_CHARGE_TYPE_CODE_CONVERSION_MAP.put("Rental", CUSTOMER_ACCOUNT_CHARGE_TYPE_RAP);
		CUSTOMER_CHARGE_TYPE_CODE_CONVERSION_MAP.put("IDD", CUSTOMER_ACCOUNT_CHARGE_TYPE_I);
	}
	
	public static final String LOOKUP_IDD_PASSWORD_ATTRIBUTE_DESC = "IDD password";
	
	public static final String SB_CLIENT_CERT_PWD = "SB_CLIENT_CERT_PWD";
	public static final String SB_CLIENT_CERT_LOCATION = "SB_CLIENT_CERT_LOCATION";
	
	public static final String getConstant(String pEnvironment, String pConstantName) throws Exception {
		return ((BomPropertyPlaceholderConfigurer) SpringApplicationContext.getBean("propertyConfigurer")).getMergedProperties().getProperty(
					pEnvironment + "." + pConstantName);
	}	
	
	public static final String LTS_DIRECT_SALES = "lts_direct_sales";
	public static final String LTS_DS_MOB_NUM ="directSalesMobileNumber";
	public static final int CHANNEL_ID_LTS_DS = 13;
	
	public static final String ACTION_LTS_GIFT_CODE_SEARCH = "GIFT_SEARCH";
	public static final String ACTION_LTS_PCD_SBID_SEARCH = "PCD_SBID_SEARCH";
	
	public static final TreeMap<String, String> DOC_TYPE = new TreeMap<String, String>();
	static{
		DOC_TYPE.put("Passport", DOC_TYPE_PASSPORT);
		DOC_TYPE.put("HKID", DOC_TYPE_HKID);
		DOC_TYPE.put("BR", DOC_TYPE_HKBR);
		DOC_TYPE.put("Certificate No", DOC_TYPE_CERTIFICATE);
	}	
	
	public static final String SB_OFFER_ACTION_TP_SWITCH = "RS";
	
//	public static final String AMEND_CATEGORY_ORDER_CANCELLATION_VALUE = "C";
//	public static final String AMEND_CATEGORY_APPOINTMENT_VALUE = "A";
//	public static final String AMEND_CATEGORY_CREDIT_CARD_VALUE = "R";
//	public static final String AMEND_CATEGORY_PIPB_INFO_VALUE = "P";
//	public static final String AMEND_CATEGORY_DOCUMENT_VALUE = "D";
//	public static final String AMEND_CATEGORY_FREE_INPUT_VALUE = "F";
//	
//	public static final String AMEND_ITEM_OF_ORDER_CANCEL_TYPE = "TYPE";
//	public static final String AMEND_ITEM_OF_ORDER_CANCEL_REASON = "REASON";
//	public static final String AMEND_ITEM_OF_ORDER_CANCEL_REMARK = "REMARK";
//	
//	public static final String AMEND_ITEM_OF_APPOINTMENT_START_TIME = "APPNT_START_TIME";
//	public static final String AMEND_ITEM_OF_APPOINTMENT_END_TIME = "APPNT_END_TIME";
//	public static final String AMEND_ITEM_OF_CUT_OVER_START_TIME = "CUT_OVER_START_TIME";
//	public static final String AMEND_ITEM_OF_CUT_OVER_END_TIME = "CUT_OVER_END_TIME";
//	public static final String AMEND_ITEM_OF_DELAY_REA_CD = "DEPLAY_REA_CD";
//	
//	public static final String AMEND_ITEM_OF_TITLE = "TITLE";
//	public static final String AMEND_ITEM_OF_FIRST_NAME = "FIRST_NAME";
//	public static final String AMEND_ITEM_OF_LAST_NAME = "LAST_NAME";
//	public static final String AMEND_ITEM_OF_FLAT = "FLAT";
//	public static final String AMEND_ITEM_OF_FLOOR = "FLOOR";
//	
//	public static final String AMEND_ITEM_OF_THIRTY_PARTY_INDICATOR = "THIRTY_PARTY_IND";
//	public static final String AMEND_ITEM_OF_FAX_SERIAL = "FAX_SERIAL";
//	public static final String AMEND_ITEM_OF_CREDIT_CARD_HOLDER_NAME = "CC_HOLD_NAME";
//	public static final String AMEND_ITEM_OF_CREDIT_CARD_NUMBER = "CC_NUM";
//	public static final String AMEND_ITEM_OF_CREDIT_CARD_TYPE = "CC_TYPE";
//	public static final String AMEND_ITEM_OF_CREDIT_CARD_EXPIRY_DATE = "CC_EXP_DATE";
//	
//	public static final String AMEND_ITEM_OF_VAS = "VAS";
//	public static final String AMEND_ITEM_OF_BILLING_ADDRESS = "BA";
//	public static final String AMEND_ITEM_OF_FSA = "FSA";
//	public static final String AMEND_ITEM_OF_CUST_NAME = "CUST_NAME";
//	public static final String AMEND_ITEM_OF_INSTALLATION_ADDRESS = "IA";
//	public static final String AMEND_ITEM_OF_CONTACT = "CONTACT";
//	public static final String AMEND_ITEM_OF_OFFER = "OFFER";
//	public static final String AMEND_ITEM_OF_PDPO = "PDPO";
//	public static final String AMEND_ITEM_OF_EQUIPMENT = "EQUIP";
//	
//	public static final String AMEND_ITEM_OF_VAS_NATURE_ID = "310";
//	public static final String AMEND_ITEM_OF_BILLING_ADDRESS_NATURE_ID = "301";
//	public static final String AMEND_ITEM_OF_FSA_NATURE_ID = "304";
//	public static final String AMEND_ITEM_OF_CUST_NAME_NATURE_ID = "305";
//	public static final String AMEND_ITEM_OF_INSTALLATION_ADDRESS_NATURE_ID = "306";
//	public static final String AMEND_ITEM_OF_CONTACT_NATURE_ID = "336";
//	public static final String AMEND_ITEM_OF_OFFER_NATURE_ID = "337";
//	public static final String AMEND_ITEM_OF_PDPO_NATURE_ID = "338";
//	public static final String AMEND_ITEM_OF_EQUIPMENT_NATURE_ID = "339";
//			
//	public static final TreeMap<String, String> AMEND_ITEM_OF_FREE_INPUT_MAP = new TreeMap<String, String>();
//	static{
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_VAS_NATURE_ID, AMEND_ITEM_OF_VAS);
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_BILLING_ADDRESS_NATURE_ID, AMEND_ITEM_OF_BILLING_ADDRESS);
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_FSA_NATURE_ID, AMEND_ITEM_OF_FSA);		
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_CUST_NAME_NATURE_ID, AMEND_ITEM_OF_CUST_NAME);
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_INSTALLATION_ADDRESS_NATURE_ID, AMEND_ITEM_OF_INSTALLATION_ADDRESS);
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_CONTACT_NATURE_ID, AMEND_ITEM_OF_CONTACT);		
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_OFFER_NATURE_ID, AMEND_ITEM_OF_OFFER);
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_PDPO_NATURE_ID, AMEND_ITEM_OF_PDPO);
//		AMEND_ITEM_OF_FREE_INPUT_MAP.put(AMEND_ITEM_OF_EQUIPMENT_NATURE_ID, AMEND_ITEM_OF_EQUIPMENT);
//	}
//	
//	public static final String[] AMEND_ITEM_ORDER_CANCEL = {
//		AMEND_ITEM_OF_ORDER_CANCEL_TYPE, 
//		AMEND_ITEM_OF_ORDER_CANCEL_REASON, 
//		AMEND_ITEM_OF_ORDER_CANCEL_REMARK};
//	
//	public static final String[] AMEND_ITEM_APPOINTMNET = {
//		AMEND_ITEM_OF_APPOINTMENT_START_TIME, 
//		AMEND_ITEM_OF_APPOINTMENT_END_TIME,
//		AMEND_ITEM_OF_CUT_OVER_START_TIME,
//		AMEND_ITEM_OF_CUT_OVER_END_TIME,
//		AMEND_ITEM_OF_DELAY_REA_CD};
//	
//	public static final String[] AMEND_ITEM_PIPB_INFO = {
//		AMEND_ITEM_OF_TITLE,
//		AMEND_ITEM_OF_FIRST_NAME,
//		AMEND_ITEM_OF_LAST_NAME,
//		AMEND_ITEM_OF_FLAT,
//		AMEND_ITEM_OF_FLOOR
//	};
//	
//	public static final String[] AMEND_ITEM_CREDIT_CARD = {
//		AMEND_ITEM_OF_THIRTY_PARTY_INDICATOR,
//		AMEND_ITEM_OF_FAX_SERIAL,
//		AMEND_ITEM_OF_CREDIT_CARD_HOLDER_NAME,
//		AMEND_ITEM_OF_CREDIT_CARD_NUMBER,
//		AMEND_ITEM_OF_CREDIT_CARD_TYPE,
//		AMEND_ITEM_OF_CREDIT_CARD_EXPIRY_DATE
//	};
	
	public static final String QC_STATUS_SPECIAL_WAIVED = "Q05";
	public static final String QC_CANNOT_QC_REASON_SPECIAL_WAIVED = "C0004";
	
	public static final int MININUM_APRU_FOR_CREATE_2DEL = 99;
	
	public static final String ITEM_SET_ATTB_LAST_SRD = "LAST_SRD";
	public static final String ITEM_SET_ATTB_SRD_PERIOD = "SRD_PERIOD";
	public static final String ITEM_SET_ATTB_CD_MEMBERSHIP = "MEMBERSHIP";
	public static final String ITEM_SET_ATTB_CD_NEW_PCD_BUNDLING = "NEW_PCD";
	public static final String ITEM_SET_ATTB_CD_CODE = "CODE";
	
	public static final TreeMap<String, String> SALES_TYPE_MAPPING = new TreeMap<String, String>();
	static{
		SALES_TYPE_MAPPING.put("Door Knocked", "D");
		SALES_TYPE_MAPPING.put("Survey Point", "S");
		SALES_TYPE_MAPPING.put("Appointment", "A");
		SALES_TYPE_MAPPING.put("Roadshow", "R");
		
		SALES_TYPE_MAPPING.put("D", "Door Knocked");
		SALES_TYPE_MAPPING.put("S", "Survey Point");
		SALES_TYPE_MAPPING.put("A", "Appointment");
		SALES_TYPE_MAPPING.put("R", "Roadshow");				
	}
	  
	public static final int LTS_DS_COOLING_OFF_LEADTIME = 7;
	public static final int LTS_DS_PE_LINK_LEADTIME = 30;
	public static final String LTS_DS_SALES_TYPE_CD_DOOR_KNOCKED = "D";
	
	public static final String LTS_DS_MUST_QC_REASON_CD_ELDERLY = "AGE65";
	public static final String LTS_DS_MUST_QC_REASON_CD_PELINK = "PE";
	public static final String LTS_DS_MUST_QC_REASON_CD_3RDPARTY = "3PARTY";
	
	
	public static final TreeMap<String, String> ELIGIBLE_EYE_TYPE_MAPPING = new TreeMap<String, String>();
	static{
		ELIGIBLE_EYE_TYPE_MAPPING.put(DEVICE_TYPE_DEL, "");
		ELIGIBLE_EYE_TYPE_MAPPING.put(DEVICE_TYPE_EYE1, "eye1");
		ELIGIBLE_EYE_TYPE_MAPPING.put(DEVICE_TYPE_EYE1_5_A, "eye1.5a");
		ELIGIBLE_EYE_TYPE_MAPPING.put(DEVICE_TYPE_EYE2, "eye2");
		ELIGIBLE_EYE_TYPE_MAPPING.put(DEVICE_TYPE_EYE2A, "eye2a");
		ELIGIBLE_EYE_TYPE_MAPPING.put(DEVICE_TYPE_EYE3A, "eye3a");
	}
	
	public static final int ACQ_NORMAL_MAX_LEADTIME = 60;
	public static final int ACQ_PIPB_MAX_LEADTIME = 365;
	
	public static final String LTS_LETTER_PRINT_XML_PARAM_NAMED_PARAMS = "named_params";
	public static final String LTS_LETTER_PRINT_XML_PARAM_EYE_TYPE_DESC = "eyeTypeDesc";
	public static final String LTS_LETTER_PRINT_XML_PARAM_LTS_M_CODE = "mCodes";
	public static final String LTS_LETTER_PRINT_IO_IND = "I";
	
	public static final String PAPER_BILL_CHARGE_WAIVE = "W";
	public static final String PAPER_BILL_CHARGE_GENERATE = "Y";
	public static final String PAPER_BILL_WAIVE_REASON_AGE_OVER_65 = "1";
	public static final String PAPER_BILL_WAIVE_REASON_OTHER = "9";

	public static final String PASSPORT_REGEX = "[a-zA-Z0-9()]{6,}";
	
	public static final String RESTRICTED_PSEF_4BBH = "4BBH";
	
	public static final String CLUB_POINT_EARN_ORD_SUB = "CLUB_PT_EARN_ORD_SUB";
	public static final String CLUB_POINT_EARN_ORD_SUB_PCODE = "CLUB_PT_EARN_ORD_SUB_PCODE";

	public static final String ROUTER_GROUP_PARM_NAME_1 = "ROUTER GROUP - GROUP1"; 
	public static final String ROUTER_GROUP_PARM_NAME_2 = "ROUTER GROUP - GROUP2"; 
	public static final String ROUTER_GROUP_PARM_NAME_3 = "ROUTER GROUP - GROUP3"; 
	public static final String ROUTER_GROUP_PARM_NAME_4 = "ROUTER GROUP - GROUP4"; 
	public static final String ROUTER_GROUP_PARM_NAME_5 = "ROUTER GROUP - GROUP5"; 
	public static final String ROUTER_GROUP_PARM_NAME_6 = "ROUTER GROUP - GROUP6"; 
	public static final String ROUTER_GROUP_PARM_NAME_7 = "ROUTER GROUP - GROUP7"; 
	public static final String ROUTER_GROUP_PARM_NAME_8 = "ROUTER GROUP - GROUP8"; 
	
	public static final String ROUTER_MESH_ROUTER_GROUP_CODE = "MESH_ROUTER_GROUP_CODE";
	public static final String ROUTER_MESH_ROUTER_GROUP_PARM_NAME = "MESH ROUTER GROUP - Y";
	
	public static final String[] ROUTER_RENTAL_ROUTER_GROUP_CODES = {"4", "5", "6", "7", "9"};
	
	public static final HashMap<String, String> ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP = new HashMap<String, String>();
	static{
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_1, "1");
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_2, "2");
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_3, "3");
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_4, "4");
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_5, "5");
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_6, "6");
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_7, "7");
		ROUTER_ALL_RENTAL_ROUTER_GROUP_MAP.put(ROUTER_GROUP_PARM_NAME_8, "8");
	}
	
	public static final String FSA_ATTB_NAME_COMPONENT_VAS_TYPE_WIRELESS_MODEM = "COMPONENT VAS TYPE (Wireless Modem)";
	public static final String FSA_ATTB_VALUE_BRM_WIFI = "BRM";
	
	public static final String[] RECONTRACT_ACCT_INVALID_STATUS = {"07", "08", "09"};
	
	public static final String DN_OPTION_ND = "ND";
	public static final String DN_OPTION_NP = "NP";
	public static final String DN_OPTION_PD = "PD";
	
	public static final String TNG_CARD_PREFIX = "5599";
	
	public static final String NETWORK_IND_NGN = "G";
	public static final String FS_PARM_ALLOW_SELF_PICKUP_WG_VAS = "ALLOW_SELF_PICKUP_WG_VAS";
	public static final String FS_PARM_FORCE_FIELD_SERVICE_WG_VAS = "FORCE_FIELD_SERVICE_WG_VAS";

	public enum LtsOrderFlag {
		CHECK_AGE_REDEMPTION_PAPER, CHECK_AGE_DISTRIBUTE_PAPER
	}
	
	public enum NewDeviceRedemTypeAttb{
		SELF_PICKUP(ITEM_TYPE_SELF_PICKUP), FIELD_SERVICE(ITEM_TYPE_FIELD_SERVICE), NONE(null) ;
		String itemType;
		NewDeviceRedemTypeAttb(String itemType){this.itemType = itemType;}
		public String getItemType(){return itemType;}
	}
	
	public enum UpgradeOrderAttb{
		ALLOW_SELF_PICKUP_DEVICE(100001)
		;
		String attbId;
		UpgradeOrderAttb(int attbId){this.attbId = String.valueOf(attbId);}
		UpgradeOrderAttb(String attbId){this.attbId = attbId;}
		public String getAttbId(){return attbId;}
	}
	public enum RenewOrderAttb{}
	public enum AcqOrderAttb{}
	
	public enum DisconnectOrderAttb{
		MODEM_COLLECT_REQUIRED(400001),
		MODEM_COLLECT_TYPE(400002),
		MODEM_COLLECT_CHARGE_TYPE(400003),
		MODEM_COLLECT_CHARGE_WAIVE_REASON(400004),
		DOB(400005)
		;
		String attbId;
		DisconnectOrderAttb(int attbId){this.attbId = String.valueOf(attbId);}
		DisconnectOrderAttb(String attbId){this.attbId = attbId;}
		public String getAttbId(){return attbId;}
	}
	
}
