package com.bomwebportal.service;

import java.util.Date;

import com.bomwebportal.dto.ResultDTO;
import com.hkcsl.hsrm.service.SQPCOrderDetailResponse;

public interface QueueRefService {
	
	public boolean validateVipNumberPattern(String attbValue);
	public boolean validateWalkInNumberPattern(String attbValue);
	public ResultDTO validatePreRegStatusFromHSRM(SQPCOrderDetailResponse sqpcOrder, String attbValue, String docId, String basketBrand);
	public boolean verifyWalkInByPassControl(String itemCode,String refId, Date appDate);
	public boolean isWalkInByPassProduct(String itemCode, Date appDate);
	public int getCouponAccountInfo(String msisdn, String idDocType, String idDocNum);

}
