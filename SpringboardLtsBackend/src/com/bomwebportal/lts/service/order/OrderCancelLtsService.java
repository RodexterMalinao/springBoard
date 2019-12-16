package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderCancelLtsService {

	public abstract boolean cancelOrder(SbOrderDTO pSbOrder, String pUser,
			String pReasonCd);

}