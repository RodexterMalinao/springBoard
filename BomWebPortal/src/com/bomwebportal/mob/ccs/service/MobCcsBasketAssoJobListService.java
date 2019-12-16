package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;

public interface MobCcsBasketAssoJobListService {
	List<String> getExistJobListALL();
	List<String> getExistChannelCdALL();

	BasketAssoJobListDTO getBasketAssoJobListDTO(String rowId);
	BasketAssoJobListDTO getBasketAssoJobListDTOByJobList(String jobList);
	List<BasketAssoJobListDTO> getBasketAssoJobListDTOByJobListAndChannelCd(String jobList, String channelCd);

	void insertBasketAssoJobListDTO(BasketAssoJobListDTO dto);
	void deleteBasketAssoJobListDTO(String rowId);
	int updateBasketAssoJobListDTODesc(BasketAssoJobListDTO dto);
	
}
