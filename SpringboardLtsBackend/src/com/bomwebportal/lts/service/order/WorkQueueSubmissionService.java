package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;

public interface WorkQueueSubmissionService {

	public abstract void submitToWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtl, String pUser, String pShopCd);

	public abstract void submitAmendmentToWorkQueue(AmendLtsDTO pAmend, String pUser, String pShopCd);
	
	public abstract void submitToApprovalWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser, String pShopCd);
	
	public abstract void submitToPendingOrderWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser, String pShopCd);
	
	public abstract void submitToDsPrepaymentWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser); 

	public abstract void submitDsWorkQueue(SbOrderDTO pSbOrder, ServiceDetailDTO[] pSrvDtls, String pUser);
	
	public abstract void clearWorkQueue(SbOrderDTO pSbOrder, String pAction, String pUser);
}