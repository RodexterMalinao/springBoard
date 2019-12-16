package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CallLogDTO;

public interface MobCcsCallLogService {
	List<CallLogDTO> getCallLogDTOALLByOrderId(String orderId);
	int insertCalLogDTO(CallLogDTO dto);
}
