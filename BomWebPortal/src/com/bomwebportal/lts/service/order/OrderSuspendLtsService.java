package com.bomwebportal.lts.service.order;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderSuspendLtsService {

	public abstract String suspendOrder(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser,
			String pReasonCd);

}