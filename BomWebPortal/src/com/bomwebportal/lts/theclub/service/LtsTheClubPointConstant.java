package com.bomwebportal.lts.theclub.service;

public interface LtsTheClubPointConstant {
	static final String TRANS_STATUS_PENDING = "PENDING";
	static final String TRANS_STATUS_SENDING = "SENDING";
	static final String TRANS_STATUS_SUCCESS = "SUCCESS";
	static final String TRANS_STATUS_FAILED = "FAILED";
	
	static final String AJAX_RESPONSE_STATUS_FAILED = "Failed";
	static final String AJAX_RESPONSE_STATUS_SUCCESS = "Success";
	
	static final String GET_MEMBER_BASIC_INFO_WITH_MASKED_ID = "getMemberBasicInfoWithMaskedID";
	static final String DO_INSTANT_EARN_POINT = "doInstantEarnPoint";
	static final String DO_INSTANT_EARN_REVERSE_POINT = "doInstantEarnReversePoint";
	
	static final String RTNMSG_SUCCEED = "Succeed";
	
	static final String RTNCODE_SUCCEED = "SUCC";
	static final String RTNCODE_FAILED = "ERR";
	
	static final String MEMBER_SEARCH_TYPE_MEMBERID="MEMBERID";
	static final String MEMBER_SEARCH_TYPE_LOGINID="LOGINID";
	static final String MEMBER_SEARCH_TYPE_DOCUMENT="DOCUMENT";
	
	static final String ACTION_EARN_POINT="EARN";
	static final String ACTION_REVERSE_POINT="REVERSE";
}
