package com.bomltsportal.service;

import java.util.Locale;

import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;

public interface AmendmentService {
	public boolean isAmendmentAllow(String pOrderId, OrderStatusSynchDTO pSrvStatus, OrderStatusSynchDTO pSrvImsStatus, String pSrvType, StringBuilder pMsgSb, Locale locale);

	public String[] getLastestAmendApptTime(ServiceDetailDTO pSrvDtl);

	public OrderStatusSynchDTO getValidBomOrderStatus(String pSbOrderId, String pTypeOfSrv, String pSrvNum, String pCcServiceId, String pCcServiceMemNum);

	public OrderStatusSynchDTO getValidFsaOrderStatus(String pSbOrderId, String pFsa, String pOcid, String pGrpId);

	public String[] getBomOrderAppointmentDateTime(String ocId, String dtlId);
}
