package com.bomwebportal.util;


public class BomWebPortalConstant {	 
	
	
	/*
	 * Spring Status 
	 */
	public static final String BWP_ORDER_INITIAL = "INITIAL";
	public static final String BWP_ORDER_SIGNOFF = "SIGNOFF";
	public static final String BWP_ORDER_PENDING = "PENDING";
	public static final String BWP_ORDER_PROCESS = "PROCESS";
	public static final String BWP_ORDER_SUCCESS = "SUCCESS";
	public static final String BWP_ORDER_FAILED = "FAILED";
	
	//For Multi Sim
	public static final String BWP_MULTISIM_ORDER_INITIAL = "000";
	public static final String BWP_MULTISIM_ORDER_PENDING = "400";
	public static final String BWP_MULTISIM_ORDER_PROCESS = "449";
	public static final String BWP_MULTISIM_ORDER_FAIL = "999";
	public static final String BWP_MULTISIM_ORDER_SUCCESS = "500";
	public static final String BWP_MULTISIM_ORDER_CANCELLED = "899";
	public static final String BWP_MULTISIM_ORDER_CANCELLING = "800";

	
	//For Direct Sales
	public static final String BWP_ORDER_REVIEWING = "REVIEWING";
	public static final String BWP_ORDER_REJECTED = "REJECTED";
	public static final String BWP_ORDER_CANCELLED = "CANCELLED";
	public static final String BWP_ORDER_CANCELLING = "CANCELLING";
	public static final String BWP_ORDER_FALLOUT = "FALLOUT";
	public static final String BWP_ORDER_QAPROCESS = "QAPROCESS";
	public static final String BWP_ORDER_VOID = "VOID";
	
	public static final String BWP_MOBCCS_ORDER_PENDING = "01";
	public static final String BWP_MOBCCS_ORDER_CANCELLING = "03";
	public static final String BWP_MOBCCS_ORDER_FALLOUT = "99";
	
	public static final String BWP_MOBCCS_CHECK_POINT_INITIAL = "000";
	public static final String BWP_MOBCCS_CHECK_POINT_PENDING = "400";
	public static final String BWP_MOBCCS_CHECK_POINT_PROCESS = "449";
	public static final String BWP_MOBCCS_CHECK_POINT_PARTIAL = "479";
	public static final String BWP_MOBCCS_CHECK_POINT_SUCCESS = "500";//"499";
	public static final String BWP_MOBCCS_CHECK_POINT_FALLOUT = "999";
	
	public static final String BWP_MOBCCS_HIST_CHECK_POINT_PENDING = "C400";
	public static final String BWP_MOBCCS_HIST_CHECK_POINT_PROCESS = "C449";
	public static final String BWP_MOBCCS_HIST_CHECK_POINT_SUCCESS = "C500";//"499";
	
	public static final String BWP_MOBCCS_BOM_CUST_PROFILE_FAIL = "D001";
	public static final String BWP_MOBCCS_BOM_ACCT_PROFILE_FAIL = "D002";
	public static final String BWP_MOBCCS_BOM_SIM_FAIL = "D003";
	public static final String BWP_MOBCCS_BOM_MRT_FAIL = "D004";
	public static final String BWP_MOBCCS_BOM_CREATE_ORDER_FAIL = "D005";
	public static final String BWP_MOBCCS_BOM_OTHERS_FAIL = "D999";
	
	public static final String BWP_MOBCCS_HIST_BOM_CUST_PROFILE_FAIL = "L001";
	public static final String BWP_MOBCCS_HIST_BOM_ACCT_PROFILE_FAIL = "L002";
	public static final String BWP_MOBCCS_HIST_BOM_SIM_FAIL = "L003";
	public static final String BWP_MOBCCS_HIST_BOM_MRT_FAIL = "L004";
	public static final String BWP_MOBCCS_HIST_BOM_CREATE_ORDER_FAIL = "L005";
	public static final String BWP_MOBCCS_HIST_BOM_OTHERS_FAIL = "L999";
		
	public static final int BWP_PROCESSOR_THREAD_SIZE = 5;
	/*
	 * BOM Error Code 
	 */
	
	public static final int ERRCODE_SUCCESS = 0;
	public static final int ERRCODE_FAIL = -1;
	public static final String ERRCODE_STR_SUCCESS = "0";
	
	/*
	 *Date Format 
	 */
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	/*
	 * CNM Web Service
	 */
	
	 public static final String CNM_OPERCODE = "WH";
	 public static final int CNM_DEFAULT_STATUS = -1;
	 public static final String CNM_TYPE = "PCCW3G";
	 public static final int CNM_STATUS_NORMAL = 2;
	 public static final int CNM_STATUS_RESERVE = 18;
	 public static final int CNM_STATUS_SOLD = 4;
	 public static final int CNM_STATUS_PORTOUT = 99;
	 
	 public static final String C_NUMBER_TYPE = "C";
	 public static final String H_NUMBER_TYPE = "H";
	 public static final String B_NUMBER_TYPE = "B";
	 
	 public static final String RESERVE_NUMBER = "A";
	 public static final String RELEASE_NUMBER = "D";
	 
	 
	 /*
	  * AD Store Procedure
	  */
	 public static final String DN_STR_ERR = "XX";
	 public static final String DN_STR_PCCW3G = "M3";
	 public static final String DN_STR_PCCW2G = "MP";
	 
	 /*
	  * HW INV Web Service 
	  */
	 public static final String HWINV_OPERCODE = "root";
	 public static final int HWINV_DEFAULT_STATUS = -1;
	 public static final int HWINV_VALID_STATUS = 2;
	 public static final int HWINV_SOLD_STATUS = 4;
	 
	 public static final String APP_ENV = "environment";
	 public static final String CEKS_TEST_URL = "ceks.testUrl";
	 public static final String CEKS_URL = "ceks.url";
	 public static final String CEKS_TEST_STRING = "ceks.testString";
	 public static final String CEKS_APPID_PREFIX = "ceks.appIdPrefix";
	 public static final String CEKS_APPID_PREFIX_INSTANT2 = "ceks.appIdPrefix.instant2";
	 public static final String CEKS_ENC_KEY = "0123456789ABCDEF";
	 public static final String CEKS_CCS_MODE_ID = "ceksCCSAppModeId";//added by Herbert 20120710
	 
	 public static final String CUSTSEARCH_SUCCESS_MSG = "SUCCESS";
	 public static final String CUSTSEARCH_FAIL_MSG = "No record found";
	 public static final String CUSTSEARCH_MORETHAN30_MSG = "No of service line over 30";
	 public static final String CUSTSEARCH_OBJECT_ACTION = "0";
	 
	 public static final String ENV_ID = "ssm_envid";
	 
	 public static final String CHANNEL_ID_SHOP = "1";
	 public static final String CHANNEL_ID_MOBCCS = "2";
	 public static final String CHANNEL_ID_MOBPREMIUM = "3";
	 public static final String CHANNEL_ID_MOBDS = "10";
	 
	 /*
	  * Customer Credit Check Service
	  */
	 public static final String Identifier = "IVR";
	 public static final String Channel = "EAI";	
	 public static final String REPORT_SERVER_PATH = "dataFilePath";
	 
	 public static final String SALES_MEMO_ITEM_CODE = "C000000";
	 
	 public static final String COMPANY_LOGO_FILE_CHI = "hkt_ch2_bw.png";		
	 public static final String COMPANY_LOGO_FILE_ENG = "hkt_en2_bw.png";
	 public static final String COMPANY_BOTTOM_LOGO_FILE_CHI = "pccwlogo_ch.png";
	 public static final String COMPANY_BOTTOM_LOGO_FILE_ENG= "pccwlogo_en.png";
	 public static final String COMPANY_LOGO_TOP_M2 = "CSL_300.jpg";
	 public static final String COMPANY_LOGO_TOP_1010 = "1010_logo.jpg";
	 public static final String COMPANY_LOGO_BOTTOM_RT_M2 = "o2f_300.jpg";
	 public static final String COMPANY_LOGO_BOTTOM_LT_CHI_M2 = "hkt_zh_300.jpg";
	 public static final String COMPANY_LOGO_BOTTOM_LT_ENG_M2= "hkt_en_300.jpg";
	 public static final String COMPANY_LOGO_BOTTOM_RT_ENG_HTML2PDF = "hkt_en_300_MIP.JPG";
	 public static final String COMPANY_LOGO_BOTTOM_RT_CHI_HTML2PDF = "hkt_zh_300_MIP.JPG";
	 
	 public static final String iphonetradeinform1 = "iphone_trade_in_form1.jpg";
	 public static final String iphonetradeinform2 = "iphone_trade_in_form2.jpg";
	 public static final String iphonetradeinform3 = "iphone_trade_in_form3.jpg";
	  
	 public static final String iphone8tradeinform1_csl_zh = "iphone8_trade_in_form1_csl_zh.jpg";
	 public static final String iphone8tradeinform2_csl_zh = "iphone8_trade_in_form2_csl_zh.jpg";
	 public static final String iphone8tradeinform3_csl_zh = "iphone8_trade_in_form3_csl_zh.jpg";
	 
	 public static final String iphone8tradeinform1_csl_en = "iphone8_trade_in_form1_csl_en.jpg";
	 public static final String iphone8tradeinform2_1_csl_en = "iphone8_trade_in_form2_csl_en.jpg";
	 public static final String iphone8tradeinform3_1_csl_en = "iphone8_trade_in_form3_1_csl_en.jpg";
	 public static final String iphone8tradeinform3_csl_en = "iphone8_trade_in_form3_csl_en.jpg";
	 
	 public static final String iphone8tradeinform1_1010_zh = "iphone8_trade_in_form1_1010_zh.jpg";
	 public static final String iphone8tradeinform2_1010_zh = "iphone8_trade_in_form2_1010_zh.jpg";
	 public static final String iphone8tradeinform3_1010_zh = "iphone8_trade_in_form3_1010_zh.jpg";
	 
	 public static final String iphone8tradeinform1_1010_en = "iphone8_trade_in_form1_1010_en.jpg";
	 public static final String iphone8tradeinform2_1_1010_en = "iphone8_trade_in_form2_1_1010_en.jpg";
	 public static final String iphone8tradeinform2_2_1010_en = "iphone8_trade_in_form2_2_1010_en.jpg";
	 public static final String iphone8tradeinform3_1_1010_en = "iphone8_trade_in_form3_1_1010_en.jpg";
	 public static final String iphone8tradeinform3_1010_en = "iphone8_trade_in_form3_1010_en.jpg";
	 
	 public static final String RPT_LANG_ZH_HK = "zh_HK";
	 public static final String RPT_LANG_EN = "en";
	 //   public static final String REPORT_LOCALE_EN = "en";
	 //   public static final String REPORT_LOCALE_ZH_HK = "zh_HK";
	 
	 public static final String JASPER_PATH_MOB = "mob/";
	 public static final String JASPER_PATH_CCS = "mob/ccs/";
	
	 public static final String JASPER_PATH_MOB_1010 = "mob/1010/";
	 public static final String JASPER_PATH_CCS_1010 = "mob/1010/ccs/";
	 
	 public static final String BRAND_1010 = "0";
	 public static final String BRAND_CSL = "1";
	 
	 public static final String M2ServiceGuide_csl_eng = "csl_acq_service_guide_eng.pdf";
	 public static final String M2ServiceGuide_csl_chi = "csl_acq_service_guide_chi.pdf";
	 public static final String M2ServiceGuide_1010_eng = "1O1O_acq_service_guide_eng.pdf";
	 public static final String M2ServiceGuide_1010_chi = "1O1O_acq_service_guide_chi.pdf";
	 
	 public static final String RetServiceGuide_csl_eng = "csl_ret_service_guide_eng.pdf";
	 public static final String RetServiceGuide_csl_chi = "csl_ret_service_guide_chi.pdf";
	 public static final String RetServiceGuide_1010_eng = "1O1O_ret_service_guide_eng.pdf";
	 public static final String RetServiceGuide_1010_chi = "1O1O_ret_service_guide_chi.pdf";
	 
	 public static final String UniServiceGuide_eng = "uni_service_guide_eng.pdf";
	 public static final String UniServiceGuide_chi = "uni_service_guide_chi.pdf";
	 
	 
	 public static final String SalesMemo_csl = "csl_sales_memo.pdf"; 
	 public static final String SalesMemo_1010 = "1O1O_sales_memo.pdf";
	 
	 //time interval to check SSO session if alive (in min)
	 public static int TIME_INTV_TO_CHECK_SSO_SESSION = 20;
 
	 public static String SSO_APP_ID = "SBACT_MOB";

	 public static final int RS_FURTHER_MNP_MAX_DATE = 400;
	 
	 
	 
	 
	 //20160613 MNP Policy
	 public static final int SM_APPROVAL_MNP_EXTEND_DAYS = 180;
	 
	 public static final int CCS_UPDATE_MNP_APP_EXTEND_DAYS = 365;
	 public static final int CCS_UPDATE_MNP_FALLOUT_EXTEND_DAYS = 30;
	 
	 public static final int CCS_MNP_APP_EXTEND_DAYS = 365;
	 public static final int RS_MNP_APP_EXTEND_DAYS = 365;
	 public static final int DS_MNP_APP_EXTEND_DAYS = 365;
	 
	 public static final int CCS_NEWMRTMNP_SRD_APP_EXTEND_DAYS = 45;
	 public static final int RS_NEWMRTMNP_SRD_APP_EXTEND_DAYS = 45;
	 public static final int DS_NEWMRTMNP_SRD_APP_EXTEND_DAYS = 45;
	 
	 public static final int CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS = 400;
	 public static final int RS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS = 400;
	 public static final int DS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS = 400;
	 
	/* public static final int CCS_MUP_CUTOVER_SRD_EXTEND_DAYS = 400;
	 public static final int RS_MUP_CUTOVER_SRD_EXTEND_DAYS = 400;
	 public static final int DS_MUP_CUTOVER_SRD_EXTEND_DAYS = 400;*/
	 
}