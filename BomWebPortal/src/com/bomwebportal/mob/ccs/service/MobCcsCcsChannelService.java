package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.CcsChannelDTO;

public interface MobCcsCcsChannelService {
	List<String> getChannelCdALL();
	List<String> getCentreCdALL();
	List<String> getTeamCdALL();
	
	List<CcsChannelDTO> getCcsChannelDTOALL();
}
