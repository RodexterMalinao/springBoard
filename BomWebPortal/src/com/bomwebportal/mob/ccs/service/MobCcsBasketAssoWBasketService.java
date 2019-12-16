package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketDTO;

public interface MobCcsBasketAssoWBasketService {
	BasketAssoWBasketDTO getMobCcsBasketAssoWBasketDTO(String basketId);
	List<BasketAssoWBasketDTO> getMobCcsBasketAssoWBasketDTOALL();
}
