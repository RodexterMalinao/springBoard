package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.ForceCancelDTO;

public interface MobCcsForceCancelService {
	List<ForceCancelDTO> forceCancelFalloutOrder();
	List<ForceCancelDTO> forceCancelDraftOrder();
	List<ForceCancelDTO> forceCancelPreorderOrder();
}
