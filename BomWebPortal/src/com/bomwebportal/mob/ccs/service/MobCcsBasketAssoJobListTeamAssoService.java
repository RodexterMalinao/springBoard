package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;

public interface MobCcsBasketAssoJobListTeamAssoService {
	List<String> getExistJobListALL();
	//List<String> getExistSalesChannelCdALL();
	BasketAssoJobListTeamAssoDTO getBasketAssoJobListTeamAssoDTO(String rowId);
	List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOByJobList(String jobList);
	List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoInRunningDTOByJobList(String jobList);
	List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOBySearch(BasketAssoJobListTeamAssoDTO dto);
	List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOBySearch(BasketAssoJobListTeamAssoDTO dto, boolean showEnded);
	List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOByRange(String jobList, String channelCd, String centreCd, String teamCd, Date startDate, Date endDate);
	
	void insertBasketAssoJobListTeamAssoDTO(BasketAssoJobListTeamAssoDTO dto);
	void updateBasketAssoJobListTeamAssoDTOForEndAsso(BasketAssoJobListTeamAssoDTO dto);
}
