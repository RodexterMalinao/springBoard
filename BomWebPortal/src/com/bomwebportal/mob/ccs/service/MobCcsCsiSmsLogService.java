package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CsiSmsLogDTO;

public interface MobCcsCsiSmsLogService {
	List<CsiSmsLogDTO> getCsiSmsLogALLByCaseId (String caseNo);
	int insertCsiSmsLogDTO(CsiSmsLogDTO dto);
}
