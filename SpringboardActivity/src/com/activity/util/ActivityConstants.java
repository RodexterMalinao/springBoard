package com.activity.util;

public interface ActivityConstants {

	public static final String ACTV_LEVEL_SEQ = "0";
	public static final String TASK_LEVEL_SEQ = "0";
	
	public static final String ACTV_TASK_STATUS_INITIAL = "I";
	public static final String ACTV_TASK_STATUS_PENDING = "P";
	public static final String ACTV_TASK_STATUS_INTERIM = "M";
	public static final String ACTV_TASK_STATUS_UNDER_APPROVAL = "U";
	public static final String ACTV_TASK_STATUS_APPROVED = "A";
	public static final String ACTV_TASK_STATUS_APPROVAL_REJECT = "N";
	public static final String ACTV_TASK_STATUS_DOCUMENT_AWAIT = "W";
	public static final String ACTV_TASK_STATUS_DOCUMENT_CAPTURED = "T";	
	public static final String ACTV_TASK_STATUS_REJECTED = "R";
	public static final String ACTV_TASK_STATUS_COMPLETED = "L";
	public static final String ACTV_TASK_STATUS_CANCELLED = "C";
	public static final String ACTV_TASK_STATUS_REPLY = "Y";
	public static final String ACTV_TASK_STATUS_REPLY_CANCELLED = "Z";
	public static final String ACTV_TASK_STATUS_ACKNOWLEDGE = "K";
	public static final String ACTV_TASK_STATUS_CONFIRM = "G";
	public static final String ACTV_TASK_STATUS_FALLOUT = "F";
	

	public static final String[] ACTV_END_STATUS = {ACTV_TASK_STATUS_APPROVED, 
													ACTV_TASK_STATUS_REJECTED, 
													ACTV_TASK_STATUS_COMPLETED, 
													ACTV_TASK_STATUS_CANCELLED};
	
	public static final String TASK_ASSIGNEE_SYSTEM = "SYSTEM";
	
	public static final int ACTV_RMK_LENGTH = 4000;
	
	public static final String TASK_TYPE_ATTACH_DOC = "ATTACH_DOC";
	
	public static final String DOC_TYPE_SIGNATURE = "SIGN";
	
	public static final String TASK_TYPE_DISOUNT_APPROVAL = "DIS_APPR";
}
