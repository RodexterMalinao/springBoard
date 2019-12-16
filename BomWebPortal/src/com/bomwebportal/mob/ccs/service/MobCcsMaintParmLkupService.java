package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;

public interface MobCcsMaintParmLkupService {
	List<MaintParmLkupDTO> getMaintParmLkupDTO(String channelCd, String lob, String functionCd, String parmType);
}
