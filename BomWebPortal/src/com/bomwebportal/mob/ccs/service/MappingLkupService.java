package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MappingLkupDTO;

public interface MappingLkupService {
	List<MappingLkupDTO> getMappingLkupDTOALL(String mapType);
	MappingLkupDTO getMappingLkupDTO(String mapType, String mapFrom);
}
