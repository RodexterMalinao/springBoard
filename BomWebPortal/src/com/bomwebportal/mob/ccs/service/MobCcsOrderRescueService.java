package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MobCcsOrderRescueDTO;

public interface MobCcsOrderRescueService {
	MobCcsOrderRescueDTO getMobCcsOrderRescueDTO(String rowId);
	MobCcsOrderRescueDTO getMobCcsOrderRescueDTOByIncidentNo(String incidentNo);
	List<MobCcsOrderRescueDTO> getMobCcsOrderRescueDTOByOrderId(String orderId);
	//MobCcsOrderRescueDTO getMobCcsOrderRescueDTOByIncidentNo(String incidentNo);
	List<MobCcsOrderRescueDTO> getMobCcsOrderRescueDTOBySearch(String orderId, String incidentNo, String msisdn);
	//String getNextIncidentNo();
	void insertMobCcsOrderRescueDTO(MobCcsOrderRescueDTO dto);
	void updateMobCcsOrderRescueDTO(MobCcsOrderRescueDTO dto);
	
}
