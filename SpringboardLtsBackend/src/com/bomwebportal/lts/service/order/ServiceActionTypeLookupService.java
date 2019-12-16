package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;

public interface ServiceActionTypeLookupService {

	public abstract ServiceActionTypeDTO[] getServiceActionType(ServiceDetailDTO pSrv, SbOrderDTO sbOrder);

	public abstract ServiceActionTypeDTO[] getApprovalServiceActionType(SbOrderDTO pSbOrder, ServiceDetailDTO pSrv, String pOrderType);
	
	public abstract ServiceActionTypeDTO[] getSuspendWithPendingServiceActionType(ServiceDetailDTO pSrv);
	
	public abstract ServiceActionTypeDTO[] getServiceActionType(ServiceDetailOtherLtsDTO pImsSrv, String pFromEyeProd, String pToEyeProd);
	
	public abstract ServiceActionTypeDTO[] getDirectSalesPrepaymentServiceActionType(SbOrderDTO pSbOrder);
	
	public abstract ServiceActionTypeDTO[] getDirectSalesServiceActionType(SbOrderDTO pSbOrder);
	
	public abstract ServiceActionTypeDTO[] getDsDrgdwntimeActionType(SbOrderDTO pSbOrder);
}