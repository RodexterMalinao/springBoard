package com.bomwebportal.lts.util;

import java.util.TreeMap;

public class LtsCsPortalBackendConstant {
	
	public static final String API_TYPE_VERF = "EMBO_VERF";
	public static final String API_TYPE_REGR = "EMBO_REGR";
	public static final String API_TYPE_REGR_PRO = "EMBO_REGR_PRO";
	
	public static final String API_TYPE_CSID_CHECK = "CSLD_IDCK";
	public static final String API_TYPE_CSID_REGISTER = "CSLD_REG";
	
	public static final String CSP_REPLY_NO_CUSTOMER = "RC_NO_CUSTOMER";
	public static final String CSP_REPLY_CUSTOMER_EXIST = "RC_CUST_EXIST";
	public static final String CSP_REPLY_CUST_ALREADY_REGISTER = "RC_CUS_ALDY_REG";
	public static final String CSP_REPLY_SUCCESS = "RC_SUCC";
	public static final String CSP_REPLY_DOC_NUM_6_DIGIT = "RC_ARQ_IVDOCNUM6DIG";
	public static final String[] CSP_REPLY_INVALID_DOC_NUM = {"RC_ARQ_IVDOCNUM6DIG", "RC_CUST_IVDOCNUM", "RC_CUST_IVDOCTY"};
	public static final String CSP_REPLY_INVALID_LOGIN_ID = "RC_RCUS_IVLOGINID";
	public static final String CSP_REPLY_LOGIN_ID_IN_USE = "RC_IN_USE";
	public static final String CSP_REPLY_OK = "OK";
	public static final String[] CSP_REPLY_EMAIL_FAIL = {"RC_LOGIN_ID_EXIST", "RC_CTMAIL_EXIST"};
	public static final String CSP_REPLY_GATE_BUSY = "RC_GATEBUSY";
	public static final String CSP_REPLY_BUSY = "RC_BUSY";
		
	public static final String SOURCE_SYSTEM_SPRINGBOARD = "SB";
	public static final String SOURCE_SYSTEM_SPRINGBOARD_ONLINE_SALES = "SBOS";
		
	public static final String REPLY_DESC_FOR_NO_CUSTOMER = "Document Id can not be found";
	public static final String REPLY_DESC_FOR_ID_ALREADY_REGISTERED = "Login Id is already registered";
	public static final String REPLY_DESC_FOR_CUSTOMER_EXISTS = "Customer already exists";
	public static final String REPLY_DESC_FOR_INVALID_DOC_NUM = "Invalid document type/id";
	public static final String REPLY_DESC_FOR_EMAIL_EXISTS = "Email already exists";
	public static final String REPLY_DESC_FOR_INVALID_LOGIN_ID = "Login Id is too large";
	public static final String REPLY_DESC_FOR_REGISTER_SUCCESS = "Register success";
	public static final String REPLY_DESC_FOR_SYSTEM_BUSY = "System busy from CS Portal";
	
	public static final TreeMap<String, String> CSP_REPLY_DESC_MAP = new TreeMap<String, String>();
	static{		
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_NO_CUSTOMER, REPLY_DESC_FOR_NO_CUSTOMER);
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_CUST_ALREADY_REGISTER, REPLY_DESC_FOR_ID_ALREADY_REGISTERED);
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_LOGIN_ID_IN_USE, REPLY_DESC_FOR_ID_ALREADY_REGISTERED);
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_CUSTOMER_EXIST, REPLY_DESC_FOR_CUSTOMER_EXISTS);
		for (int i=0; i<CSP_REPLY_INVALID_DOC_NUM.length; i++) {
			CSP_REPLY_DESC_MAP.put(CSP_REPLY_INVALID_DOC_NUM[i], REPLY_DESC_FOR_INVALID_DOC_NUM);
		}
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_INVALID_LOGIN_ID, REPLY_DESC_FOR_INVALID_LOGIN_ID);
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_OK, REPLY_DESC_FOR_REGISTER_SUCCESS);
		for (int i=0; i<CSP_REPLY_EMAIL_FAIL.length; i++) {
			CSP_REPLY_DESC_MAP.put(CSP_REPLY_EMAIL_FAIL[i], REPLY_DESC_FOR_EMAIL_EXISTS);
		}
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_GATE_BUSY, REPLY_DESC_FOR_SYSTEM_BUSY);
		CSP_REPLY_DESC_MAP.put(CSP_REPLY_BUSY, REPLY_DESC_FOR_SYSTEM_BUSY);
	};
	
	public static final String MY_HKT_REGISTER_ONLY = "MYHKT-REG";
	public static final String THE_CLUB_REGISTER_ONLY = "THECLUB-REG";
	public static final String MY_HKT_AND_THE_CLUB_REGISTER = "MYHKT-THECLUB-REG";
	public static final String EXIST_MY_HKT_AND_THE_CLUB = "EXIST-MYHKT-THECLUB";
	
	public static final String LTS_CSP_OPT_OUT_REASON_CD_OTHERS = "51";
	
	public static final String LTS_CSP_OPT_OUT_ALL = "OOA";
	public static final String LTS_CSP_OPT_IN_ALL = "OIA";
	
	public static final String CSP_DUMMY_NUM = "00000000";

}
