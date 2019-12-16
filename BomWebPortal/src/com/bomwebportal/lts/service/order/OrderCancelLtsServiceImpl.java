package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialInputDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialOutputDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class OrderCancelLtsServiceImpl implements OrderCancelLtsService {
	
	private OrderStatusService orderStatusService;
	private AppointmentDwfmService appointmentDwfmService;
	private WorkQueueSubmissionService workQueueSubmissionService;
	
	public boolean cancelOrder(SbOrderDTO pSbOrder, String pUser, String pReasonCd) {
		
		preSubmit(pSbOrder, pReasonCd, pUser);
		if (!cancelOrder(pSbOrder, pUser)) {
			return false;
		}
		postSubmit(pSbOrder, pReasonCd, pUser);
		return true;
	}
	
	private boolean cancelOrder(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		CancelPrebookSerialInputDTO cancelIn = null;
		CancelPrebookSerialOutputDTO cancelOut = null;
		
		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
			if (srvDtls[i] instanceof ServiceDetailOtherLtsDTO 
					&& srvDtls[i].getAppointmentDtl() != null && StringUtils.isNotEmpty(srvDtls[i].getAppointmentDtl().getSerialNum())) {
				cancelIn = new CancelPrebookSerialInputDTO();
				cancelIn.setLoginID(userId);
				cancelIn.setSerialNum(srvDtls[i].getAppointmentDtl().getSerialNum());
				cancelOut = this.appointmentDwfmService.cancelPrebookSerial(cancelIn);
				
				if (!StringUtils.equals("0", cancelOut.getReturnValue())) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void preSubmit(SbOrderDTO sbOrder, String cancelReasonCd, String userId) {
		this.initializeStatus(sbOrder, userId);
	}
	
	private void postSubmit(SbOrderDTO sbOrder, String cancelReasonCd, String userId) {
		
		this.updateStatusAfterCancellation(sbOrder, cancelReasonCd, userId);
		
		try {
			this.workQueueSubmissionService.clearWorkQueue(sbOrder, "SB_CANCEL", userId);
		} catch (Exception e) {}
	}
	
	private void initializeStatus(SbOrderDTO sbOrder, String userId) {
		
		ServiceDetailDTO[] srvs = sbOrder.getSrvDtls();
		
		for (int i=0; srvs!=null && i<srvs.length; ++i) {
			if (StringUtils.isEmpty(srvs[i].getSbStatus())) {
				this.orderStatusService.initializeStatus(sbOrder.getOrderId(), srvs[i].getDtlId(), userId);
			}
		}
	}
	
	private void updateStatusAfterCancellation(SbOrderDTO sbOrder, String cancelReasonCd, String userId) {
		
		ServiceDetailDTO[] srvs = sbOrder.getSrvDtls();
		
		for (int i=0; srvs!=null && i<srvs.length; ++i) {
			if(!LtsConstant.ORDER_STATUS_CLOSED.equals(srvs[i].getSbStatus())){
				this.orderStatusService.setCancelStatus(sbOrder.getOrderId(), srvs[i].getDtlId(), cancelReasonCd, userId, srvs[i].getWorkQueueType());
			}
		}
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public AppointmentDwfmService getAppointmentDwfmService() {
		return appointmentDwfmService;
	}

	public void setAppointmentDwfmService(
			AppointmentDwfmService appointmentDwfmService) {
		this.appointmentDwfmService = appointmentDwfmService;
	}

	public WorkQueueSubmissionService getWorkQueueSubmissionService() {
		return workQueueSubmissionService;
	}

	public void setWorkQueueSubmissionService(
			WorkQueueSubmissionService workQueueSubmissionService) {
		this.workQueueSubmissionService = workQueueSubmissionService;
	}
}
