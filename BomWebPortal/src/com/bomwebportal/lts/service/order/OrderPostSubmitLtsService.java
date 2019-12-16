package com.bomwebportal.lts.service.order;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;

public interface OrderPostSubmitLtsService {

	@Transactional
	public abstract String signOffOrder(SbOrderDTO pSbOrder, String pReportType, String pExportType, String pEditButton, String pUser, String pShopCd);

	@Transactional
	public abstract void approvalOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd);

	@Transactional
	public abstract void suspendWithPendingOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd);
	
	public abstract String determineWorkQueueNatureForSignoff(ServiceDetailDTO pSrvDtl);
	
	public abstract void submitToDsPrepaymentWorkQueue(SbOrderDTO sbOrder, String userId);
}