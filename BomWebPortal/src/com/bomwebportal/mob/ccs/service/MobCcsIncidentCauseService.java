package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MobCcsIncidentCauseDTO;

public interface MobCcsIncidentCauseService {
	
	List<MobCcsIncidentCauseDTO> getMobCcsIncidentCause(String incidentNo);
	void insertMobCcsIncidentCause(MobCcsIncidentCauseDTO dto);
	void deleteMobCcsIncidentCause(String incidentNo);
}
