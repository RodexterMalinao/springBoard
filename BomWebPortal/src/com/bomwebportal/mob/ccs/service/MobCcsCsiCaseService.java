package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CsiCaseDTO;

public interface MobCcsCsiCaseService {
	List<CsiCaseDTO> getCsiCaseALLByOrderId(String orderId);
	CsiCaseDTO getCsiCaseALLByCaseNo(String caseNo);
	int insertCsiCaseDTO(CsiCaseDTO dto);
	int updateCsiCaseDTO(CsiCaseDTO dto);
	int updateSmsCount(String caseNo);
	int updateContactCount(String caseNo);
}
