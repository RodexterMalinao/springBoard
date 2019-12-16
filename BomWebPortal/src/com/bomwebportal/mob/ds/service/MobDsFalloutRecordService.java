package com.bomwebportal.mob.ds.service;

import java.util.List;

import com.bomwebportal.mob.ds.dto.MobDsFalloutRecordDTO;

public interface MobDsFalloutRecordService {
	List<MobDsFalloutRecordDTO> getFalloutRecordALLByOrderId(String orderId);
	MobDsFalloutRecordDTO getFalloutRecordALLByCaseNo(String caseNo);
	int insertFalloutRecordDTO(MobDsFalloutRecordDTO dto);
	int updateFalloutRecordDTO(MobDsFalloutRecordDTO dto);
	int getLatestCaseNoByOrderId(String orderId);
	boolean hasUnsettledFallout(String orderId);
	
}
