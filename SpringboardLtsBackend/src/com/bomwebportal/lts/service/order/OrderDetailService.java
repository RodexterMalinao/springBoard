package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.OrderIdDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;

public interface OrderDetailService {

	
	public abstract String getOrderType(String pOrderId);
	
	public abstract OrderIdDTO generateOrderId(String pShopCd, String pUser, String pMode, String pSrvType);
	
	public abstract String updateSignoffDate(String pOrderId);
	
	public abstract String updatePaymentTermDate(String pOrderId); 

	public abstract int getNextDocSeq(String pOrderId, String pDocType);
	
	public abstract void clearCustNotMatchInd(String pOrderId);
	
	public abstract void updateServicePendingBomOrder(String pSbOrderId, ServiceDetailDTO pSrvDtl);
	
	public abstract void updateMustQCInd(String pOrderId, String mustQcInd, String pUserId);
	
	public abstract String getWaiveQcStatus(String pOrderId);
	
	public abstract void updateWaiveQcStatus(String pOrderId, String waiveQcStatus, String pUserId);
	
	public abstract String getWaiveQcCode(String pOrderId);
	
}