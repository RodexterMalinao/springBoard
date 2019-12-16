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
	 public static final String CEKS_ENC_KEY = "0123456789ABCDEF";
	 
	 public static final String CUSTSEARCH_SUCCESS_MSG = "SUCCESS";
	 public static final String CUSTSEARCH_FAIL_MSG = "No record found";
	 public static final String CUSTSEARCH_MORETHAN30_MSG = "No of service line over 30";
	 public static final String CUSTSEARCH_OBJECT_ACTION = "0";
	 
	 public static final String ENV_ID = "ssm_envid";
}