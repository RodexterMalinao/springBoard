package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.ItemFuncAssgnMobDTO;

public interface ItemFuncMobService {
	ItemFuncAssgnMobDTO getItemFuncAssgnMobDTO(String itemId, String funcId);
	List<ItemFuncAssgnMobDTO> findItemFuncAssgnMobDTO(String itemId, String appDate);
	
}
