package com.bomwebportal.lts.service.acq;

import java.util.List;

import com.bomwebportal.lts.dto.acq.BomwebDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;

public interface LtsDsOrderService {
	
	public BomwebDsOrderInfoDTO getOrderInfo(String pOrderId);
	
	public void updateDsInfo(String pOrderId, String qcCallTimePeriod, String waiveCd, String pUserId);
	
	public void updatePrepayInfo(String pOrderId, PrepayLtsDTO pPrepayInfo, String pUserId);
	
	public List<String> isDuplicatedShopCodeTranCode(String pOrderId, String pShopCode, String pTranCode, String pPrepaySettleDate);
	
	public String canReuseShopCodeTranCode(String pAccNo, String pOrderId, String pCancelOrderDate) ;
}
