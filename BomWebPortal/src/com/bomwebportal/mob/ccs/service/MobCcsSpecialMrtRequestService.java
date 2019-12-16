package com.bomwebportal.mob.ccs.service;

import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;

public interface MobCcsSpecialMrtRequestService {

	public SpecialMrtRequestDTO getSpecialMrtRequestDTO(String requestId);
	public int updateSpecialMrtRequestDTO(SpecialMrtRequestDTO dto);
	public String insertSpecialMrtRequest(SpecialMrtRequestDTO dto);
	public int updateSpecialMrtRequestStatus(SpecialMrtRequestDTO dto);
	
	
	public MrtInventoryDTO getMrtInventoryByRequestId(String requestId);
	public MrtInventoryDTO getMrtInventoryByMrt(String msisdn);
	public int updateMrtInventoryByMrt(MrtInventoryDTO dto);
	public int updateMrtInventoryStatusByMrt(String msisdnStatus, String staffId, String msisdn, String requestID);
	public int updateMrtInventoryByRequestId(MrtInventoryDTO dto);
	public String getSpecialMrtRequestByApprovalSerial(String approvalSerial);
	
}
