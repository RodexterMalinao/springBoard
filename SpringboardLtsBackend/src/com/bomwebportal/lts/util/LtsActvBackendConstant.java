package com.bomwebportal.lts.util;

import com.activity.util.ActivityConstants;

public interface LtsActvBackendConstant extends ActivityConstants{

	public enum ActvAction {
		CREATE_DN,
		CHANGE_DN,
		PIPB_REQUEST,
		PIPB_REJECT,
		PIPB_REJECT_WITH_DEFER_SRD,
		PIPB_REJECT_WITH_INVALID_APPT,		
		PIPB_CANCEL,
		UPDATE_APN,
		CHANGE_SRD,
		CHANGE_SRD_BY_PIPB_REJ,
		RESTORE_DEFERRED_SRD,
		DN_FOLLOWUP,
		PIPB_CANCEL_SALES_ALERT,
		PIPB_CANCEL_WITH_ACKNOWLEDGE,
		AMEND_APPROVAL,
		WAIVE_QC,
		DS_AMEND_APPROVAL,
		CUST_NAME_NOT_MATCH,
		PREINSTALL_PIPB_REQUEST
	}	
	
	public static final String ACTV_TYPE_PIPB_PROC = "PIPB_PROC";
	public static final String ACTV_TYPE_PIPB_REQ = "PIPB_REQ";	
	public static final String ACTV_TYPE_AMEND_APPROVAL = "AMEND_APPR";
	public static final String ACTV_TYPE_WAIVE_QC = "WAIVE_QC";	
	public static final String ACTV_TYPE_DS_AMEND_APPROVAL = "DS_AMD_APR";
	public static final String ACTV_TYPE_DS_CUST_NAME_APPROVAL = "DS_NAM_APR";
	
	public static final String ACTV_TASK_STATUS_ACKNOWLEDGE = "K";   
	public static final String ACTV_TASK_STATUS_RESUME = "M";    
	public static final String ACTV_TASK_STATUS_SETTLED = "T";     
	public static final String ACTV_TASK_STATUS_ACCEPTANCE = "E";
	public static final String ACTV_TASK_STATUS_SUCCESS = "U";
	public static final String ACTV_TASK_STATUS_FAIL = "F";
	public static final String ACTV_TASK_STATUS_PENDING_ACTION = "A";
	public static final String ACTV_TASK_STATUS_URGENT_CANCELLED = "X";
	
	public static final String ACTV_STATUS_NO_OCID = "NO_OCID";
	public static final String ACTV_STATUS_SUCCESS = "SUCCESS";
	public static final String ACTV_STATUS_UPLOAD_SUCCESS = "UPLOAD_SUCCESS";
	public static final String ACTV_STATUS_SRD_EXPIRED = "SRD_EXPIRED";
	
	public static final String ACTV_SRD_DATE_FORMAT = "yyyyMMdd";
	
	public static final String ACTV_ACTION_APN_UPLOAD = "APN_UPLOAD";
	public static final String ACTV_ACTION_ORD_AMEND = "ORD_AMEND";
	public static final String ACTV_ACTION_ORD_CANCEL = "ORD_CANCEL";
	public static final String ACTV_ACTION_ORD_AMEND_WITH_SRD_CHG = "ORD_AMEND_WITH_SRD_CHG";
	
	public static final String TASK_TYPE_PIPB_REQUEST = "PIPB_REQ";
	public static final String TASK_TYPE_PIPB_REJECT = "PIPB_REJ";
	public static final String TASK_TYPE_PIPB_CANCEL = "PIPB_CXL";		
	public static final String TASK_TYPE_APN_UPDATE = "UPDATE_APN";	
	public static final String TASK_TYPE_SRD_CHANGE = "CHANGE_SRD";
	public static final String TASK_TYPE_PIPB_DN_CREATE = "CREATE_DN";
	public static final String TASK_TYPE_PIPB_DN_CHANGE = "CHANGE_DN";
	public static final String TASK_TYPE_PIPB_CANCEL_SALES_ALERT = "PIPB_CXLSA";
	public static final String TASK_TYPE_AMEND_APPROVAL = "AMEND_APPR";
	public static final String TASK_TYPE_WAIVE_QC = "WAIVE_QC";
	public static final String TASK_TYPE_DS_AMEND_APPROVAL = "DS_AMD_APR";
	public static final String TASK_TYPE_DS_CUST_NAME_APPROVAL = "DS_NAM_APR";
	
	public static final String WQ_STATUS_APPROVAL_APPROVED = "050";
	public static final String WQ_STATUS_APPROVAL_REJECT = "060";
	public static final String WQ_STATUS_COMPLETE = "090";
	public static final String WQ_STATUS_ACKNOWLEDGE = "330";
	public static final String WQ_STATUS_PIPB_CANCEL = "350";
	public static final String WQ_STATUS_PIPB_REJECT = "360";
	public static final String WQ_STATUS_PIPB_RESUME = "400";
	public static final String WQ_STATUS_PIPB_RECALL_REJECT = "410";
	public static final String WQ_STATUS_APPROVAL_APPROVED_DIFF_CUST = "051";
		
	public static final String ACTV_LTS_LOB = "LTS";
		
	public static final String ACTV_WQ_NATURE_CREATE_DN = "56";
	public static final String ACTV_WQ_NATURE_2DEL = "5";
	
	public static final String WQ_WATER_MARK_FOR_PIPB_CANCEL = "CANCEL";
		
	public static final String ACTV_REMAKRS_FOR_CREATE_DN = "Create Port-in DN request for PIPB.";
	public static final String ACTV_REMAKRS_FOR_CHANGE_DN = "Change DN request.";
	public static final String ACTV_REMAKRS_FOR_PIPB_REQ = "PIPB request.";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_PIPB_REQ = "PIPB request.Remarks: Pre-installation = Y.";
	public static final String ACTV_REMAKRS_FOR_PIPB_REJ = "PIPB request rejected.";
	public static final String ACTV_REMAKRS_FOR_PIPB_REJ_WITH_DEFER_SRD = "F&S defer 30 calendar days with SRD < " + LtsBackendConstant.MINIMUM_PIPB_DAY + " days.";
	public static final String ACTV_REMAKRS_FOR_PIPB_REJ_WITH_INVALID_APPT = "Invalid appointment time.";
	public static final String ACTV_REMAKRS_FOR_PIPB_CANCEL = "Cancel PIPB request.";
	public static final String ACTV_REMAKRS_FOR_UPD_APN = "Update APN.";
	public static final String ACTV_REMAKRS_FOR_CHANGE_SRD = "SRD < " + LtsBackendConstant.MINIMUM_PIPB_DAY + " days. Defer 30 calendar days in BOM.";	
	public static final String ACTV_REMAKRS_FOR_CHANGE_SRD_BY_PIPB_REJ = "PIPB Reject. Defer 30 calendar days in BOM.";
	public static final String ACTV_REMAKRS_FOR_DN_FOLLOWUP = "DN status is not able to be changed for BOM order creation.";
	public static final String ACTV_REMAKRS_FOR_CANCEL_SALES_ALERT = "Port-in DN request Cancelled.";
	public static final String ACTV_REMAKRS_FOR_FIXED_FEE_PLAN_ALERT = "Alert - FFP SUBSCRIBED, UPDATE EFFECTIVE DATE.";
	public static final String ACTV_REMAKRS_FOR_RESTORE_DEFERRED_SRD = "Carrier Recall PIPB Reject. Change back the SRD in BOM.";
	public static final String ACTV_REMAKRS_FOR_INSTALLATION_DATE = "Target Installation Date:";
	public static final String ACTV_REMAKRS_FOR_SWITCHING_DATE = "Target Telephone Switching Date:";
	
	public static final String ACTV_REMAKRS_FOR_WAIVE_QC = "Pending for the approval of the waive QC case";
	public static final String ACTV_REMAKRS_FOR_DS_AMEND_APPR = "Pending for the approval of the Direct Sales order amendment or cancellation";
	public static final String ACTV_REMAKRS_FOR_CUST_NAME_NOT_MATCH = "Pending for the approval of customer name not match case";
	
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_WQ_NATURE1 = "i) Order - Add Pre-installation Suspension VAS Code";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_WQ_NATURE2 = "ii) Order - Change Pre-install Dummy Code to Real Term Plan / VAS Code";
	
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ1 = "1st Order: Add Pre-installation Suspension VAS Code";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ2 = "New DN:";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ3 = "Add VAS Code: 7DS1";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ4 = "Application Date:";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ5 = "SRD: Order issue date";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ6 = "2nd Order:";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ7 = "IN Real Term Plan / VAS Code:";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ8 = "OUT Pre-installation Term Plan / VAS Code:";
	public static final String ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ9 = "OUT all existing PSEF code & waive penalty";
	
	public static final String ACTV_TASK_NOT_FOUND_ERR = "Activity task not found with WQ assgn id";
	
	public static final String ORDER_REMARK = "LTS Order Remarks";
	public static final String CUSTOMER_REMARK = "LTS Customer Remarks";
	
	
		
}
