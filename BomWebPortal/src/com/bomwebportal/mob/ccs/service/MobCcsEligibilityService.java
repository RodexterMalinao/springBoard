package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsValuePropAssgnDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO.IdDocType;

public interface MobCcsEligibilityService {
	List<MobCcsValuePropAssgnDTO> getMobCcsValuePropAssgnDTOALL();
	List<MobCcsEligibilityDTO> getMobCcsEligibilityDTOALL(IdDocType idDocType, String idDocNum);
	MobCcsEligibilityDTO getMobCcsEligibilityDTO(IdDocType idDocType, String idDocNum, String valuePropId);
	int insertMobCcsEligibilityDTO(MobCcsEligibilityDTO dto);
}
