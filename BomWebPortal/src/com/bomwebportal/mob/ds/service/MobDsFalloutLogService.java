package com.bomwebportal.mob.ds.service;

import java.util.List;

import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;

public interface MobDsFalloutLogService {
	List<MobDsFalloutLogDTO> getFalloutLogDTOALLByCaseNo(String caseNo, String orderId);
	int insertFalloutLogDTO(MobDsFalloutLogDTO dto);
}
