package com.bomwebportal.lts.service.order;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderRetrieveLtsService {

	@Transactional
	public abstract SbOrderDTO retrieveSbOrder(String pOrderId, boolean pIsEquiry);
	AllOrdDocLtsDTO[] retrieveAllOrdDocs(String pOrderId);
	AllOrdDocAssgnLtsDTO[] retrieveAllOrdDocAssgn(String orderId);
}