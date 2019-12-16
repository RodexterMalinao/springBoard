package com.bomwebportal.mob.ccs.service;

import com.bomwebportal.mob.ccs.dto.MobCcsOrderFalloutDTO;

public interface MobCcsOrderFalloutService {
	int insertOrderFalloutDTO(MobCcsOrderFalloutDTO dto);
	MobCcsOrderFalloutDTO getOrderFalloutByCat(String orderId, String falloutCat);
}
