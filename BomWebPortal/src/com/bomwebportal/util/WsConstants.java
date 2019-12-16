package com.bomwebportal.util;

public class WsConstants {
	
	public static final String wsSWDL = "http://10.1.139.57:7001/BOM_MOB_SALESTOOL_Web/ws/CreateBulk3GNewAct.jws?WSDL"; 

	public static final String ERR_STATUS_NO_ERROR = "0";
	public static final String ERR_STATUS_INVALID_INPUT = "-1";
	public static final String ERR_STATUS_HW_INV_FAILURE = "-2";
	public static final String ERR_STATUS_CNM_FAILURE = "-3";
	public static final String ERR_STATUS_CNM_FAILURE_CN = "-4";
	public static final String ERR_STATUS_OTHERS_ERROR = "-99";

	public static final String ERR_MSG_INVALID_INPUT = "Input objects invalid.";
	public static final String ERR_MSG_OTHERS_ERROR = "Unable to call bulk new activate webservice, please contact support to check.";
	public static final String ERR_MSG_HW_INV_FAILURE = "Fail to update HW Inv status.";
	public static final String ERR_MSG_CNM_FAILURE = "Fail to update CNM status.";
	public static final String ERR_MSG_CNM_FAILURE_CN = "Fail to update 3GCN CNM status.";
	public static final String ERR_MSG_CUST_PROFILE_FAIL = "Unable to create customer profile in BOM.";
	public static final String ERR_MSG_HW_ICCID_NOT_FOUND = "Cannot find ICCID in HW Inv.";
	
	public static final String ERR_STRING_FAILED = "OF";
	public static final String ERR_STRING_SUCCESS = "OS";
	public static final String OPER_CODE = "root";
	public static final String CNM_TYPE = "PCCW3G";
	public static final String CNM_TYPE_CN = "UNICOM1C2N"; //add by herbert 20120210
	
	public static final int HW_INV_STATUS_NORMAL = 2;
	public static final int HW_INV_STATUS_SOLD = 4;
	
	public static final int CNM_STATUS_NORMAL = 2;
	public static final int CNM_STATUS_SOLD = 19;
	
	public static final String INV_MOB_NO_TYP_CD_3G = "PCCW3G";
	public static final String INV_MOB_NO_TYP_CD_3G_CN = "UNICOM1C2N";
}
