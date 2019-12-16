package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;

public interface MobCcsSpecialMRTSummaryService {

	
	List<SpecialMrtRequestDTO> getSpecialMRTRequests(String requestStatus, String requestDateFrom, 
			String requestDateTo, String channel, String requestedBy, String mobNum);
	
	List<SpecialMrtRequestDTO> getSpecialMRTRequestsByManager(String requestStatus, String requestDateFrom, String requestDateTo, String channelCd, String teamCd);
	
	List<MaintParmLkupDTO> getResultStatusTypes(String channelCd);
	
	List<MaintParmLkupDTO> getChannelTypes();
	
	
}
