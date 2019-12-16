package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListBskAssoDTO;

public interface MobCcsBasketAssoJobListBskAssoService {
	List<String> getExistJobListALL();
	//List<String> getExistSalesChannelCdALL();
	BasketAssoJobListBskAssoDTO getBasketAssoJobListBskAssoDTO(String rowId);
	List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoDTOByJobList(String jobList);
	List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoInRunningDTOByJobList(String jobList);
	List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoDTOBySearch(BasketAssoJobListBskAssoDTO dto);
	
	void insertBasketAssoJobListBskAssoDTO(BasketAssoJobListBskAssoDTO dto);
	void updateBasketAssoJobListBskAssoDTOForEndAsso(BasketAssoJobListBskAssoDTO dto);
}
