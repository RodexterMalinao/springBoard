package com.bomwebportal.lts.service.workQueue;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface WqTransSubmitService {

	void submitPendingWqTrans(SbOrderDTO sbOrder, String userId, String shopCd);
	
}
