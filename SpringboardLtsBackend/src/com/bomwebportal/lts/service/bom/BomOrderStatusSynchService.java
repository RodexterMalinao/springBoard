package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;

public interface BomOrderStatusSynchService {

	OrderStatusSynchDTO[] getBomOrderStatus(String pOrderId, String pTypeOfSrv,
			String pSrvNum, String pCCServiceId, String pCCServiceMemNum);

	OrderStatusSynchDTO[] getBomOrderStatus(String pOrderId, String pTypeOfSrv,
			String pSrvNum, String pCCServiceId, String pCCServiceMemNum,
			String pOcId, String pGrpId, String pToProd);

	OrderStatusSynchDTO[] getBomOrderStatusByCcServiceId(String pSbOrderId,
			String pSrvType, String pCcSrvId, String pSrvNum, String pSrvMemNum);
}
