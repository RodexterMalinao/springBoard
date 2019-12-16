package com.bomwebportal.lts.service.order.acq;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;

public interface LtsAcqOrderPostSubmitLtsService {
	
	@Transactional
	public abstract String signOffNewAcquisitionOrder(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser);
	
	@Transactional
	public abstract void submitDsAllWorkQueue(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser);
	
	@Transactional
	public abstract void approvalOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd);

	@Transactional
	public abstract void suspendWithPendingOrder(SbOrderDTO pSbOrder, String pUser, String pShopCd);
	
	public abstract String determineWorkQueueNatureForSignoff(ServiceDetailDTO pSrvDtl);

	public abstract void submitToDsPrepaymentWorkQueue(SbOrderDTO pSbOrder, String userId);
	
	public abstract void submitDrgDowntimeWorkQueue(SbOrderDTO pSbOrder, String userId);
	
	public abstract void setPostSubmitSignoffStatus(SbOrderDTO sbOrder, String userId);
}
