package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CsiCaseLogDTO;

public interface MobCcsCsiCaseLogService {
	List<CsiCaseLogDTO> getCsiCaseLogALLByOrderId(String orderId);
	List<CsiCaseLogDTO> getCsiCaseLogALLByCaseNo(String caseNo);
	int insertCsiCaseLogDTO(CsiCaseLogDTO dto);
}
