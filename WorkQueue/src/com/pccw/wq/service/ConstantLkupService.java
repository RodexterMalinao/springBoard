package com.pccw.wq.service;

import java.util.List;

import com.pccw.wq.schema.dto.SelectionItemDTO;

public interface ConstantLkupService {
	
	public static final String WQ_STATUS_CODE_OUTSTANDING = "999";
	
	public static final String WQ_STATUS_CLOSED = "090";

	public static final String WQ_STATUS_CANCELLED = "100";
	
	public static final String WQ_STATUS_SYSTEM_CANCELLED = "110";
	
	public static final String[] WQ_STATUS_OUTSTANDING_STATUS_CODEs = {"010", "090", "100", "110"};

	
	List<SelectionItemDTO> getWQNature();
	
	List<SelectionItemDTO> getWQType();
	
	List<SelectionItemDTO> getWQSubType();
	
	List<SelectionItemDTO> getWQStatus();

	List<SelectionItemDTO> getReason();

//	<!--added by ims steven 20130228 start-->
	List<SelectionItemDTO> getCSPendingReason();

	List<SelectionItemDTO> getCSCancelReason();
//	<!--added by ims steven 20130228 end-->
	
	List<SelectionItemDTO> getWorkingParty();
}