package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.service.bom.BomOrderStatusSynchService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;

public class AmendDetailEnquiryServiceImpl implements AmendDetailEnquiryService {

	private ServiceProfileLtsDrgService serviceProfileLtsDrgService = null;
	private BomOrderStatusSynchService bomOrderStatusSynchService = null;
	
	
	public boolean isAmendmentAllow(String pSbOrderId, String pSrvType, String pCcSrvId, String pSrvNum, String pSrvMemNum, StringBuilder pMsgSb) {
		
		OrderStatusSynchDTO[] status = this.bomOrderStatusSynchService.getBomOrderStatusByCcServiceId(pSbOrderId, pSrvType, pCcSrvId, pSrvNum, pSrvMemNum);
		
		if (ArrayUtils.isEmpty(status)) {
			pMsgSb.append("No Bom order exist related to SB order ");
			pMsgSb.append(pSbOrderId);
			pMsgSb.append(".\n");
			return true;
		}
		for (int i=0; status!=null && i<status.length; ++i) {
			if (!StringUtils.equals("04", status[i].getBomStatus()) && !StringUtils.equals("07", status[i].getBomStatus())) {
				return true;
			}
		}
		pMsgSb.append("Serivce is cancelled under OCID ");
		pMsgSb.append(status[0].getOcId());
		pMsgSb.append(".\n");
		return false;
	}

	public ServiceProfileLtsDrgService getServiceProfileLtsDrgService() {
		return serviceProfileLtsDrgService;
	}

	public void setServiceProfileLtsDrgService(
			ServiceProfileLtsDrgService serviceProfileLtsDrgService) {
		this.serviceProfileLtsDrgService = serviceProfileLtsDrgService;
	}

	public BomOrderStatusSynchService getBomOrderStatusSynchService() {
		return bomOrderStatusSynchService;
	}

	public void setBomOrderStatusSynchService(
			BomOrderStatusSynchService bomOrderStatusSynchService) {
		this.bomOrderStatusSynchService = bomOrderStatusSynchService;
	}
}
