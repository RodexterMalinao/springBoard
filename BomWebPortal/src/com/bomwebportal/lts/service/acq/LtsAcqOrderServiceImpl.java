package com.bomwebportal.lts.service.acq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.order.OrderCancelLtsService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderSuspendLtsService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderCreateService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderModifyService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderPostSubmitLtsService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderQcSubmitLtsService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderRecallService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.util.LtsSbHelper;

public class LtsAcqOrderServiceImpl implements LtsAcqOrderService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsAcqOrderCreateService ltsAcqOrderCreateService;
	protected LtsAcqOrderRecallService ltsAcqOrderRecallService;
	protected LtsAcqOrderModifyService ltsAcqOrderModifyService;
	protected OrderCancelLtsService orderCancelLtsService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected OrderSuspendLtsService orderSuspendLtsService;
	protected LtsAcqOrderPostSubmitLtsService ltsAcqOrderPostSubmitLtsService;
	protected LtsAcqOrderQcSubmitLtsService ltsAcqOrderQcSubmitLtsService;
	protected OrderStatusService orderStatusService;
	
	/**
	 * @return the ltsAcqOrderPostSubmitLtsService
	 */
	public LtsAcqOrderPostSubmitLtsService getLtsAcqOrderPostSubmitLtsService() {
		return ltsAcqOrderPostSubmitLtsService;
	}

	/**
	 * @param ltsAcqOrderPostSubmitLtsService the ltsAcqOrderPostSubmitLtsService to set
	 */
	public void setLtsAcqOrderPostSubmitLtsService(
			LtsAcqOrderPostSubmitLtsService ltsAcqOrderPostSubmitLtsService) {
		this.ltsAcqOrderPostSubmitLtsService = ltsAcqOrderPostSubmitLtsService;
	}

	public OrderSuspendLtsService getOrderSuspendLtsService() {
		return orderSuspendLtsService;
	}


	public void setOrderSuspendLtsService(
			OrderSuspendLtsService orderSuspendLtsService) {
		this.orderSuspendLtsService = orderSuspendLtsService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}


	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}


	public OrderCancelLtsService getOrderCancelLtsService() {
		return orderCancelLtsService;
	}


	public void setOrderCancelLtsService(OrderCancelLtsService orderCancelLtsService) {
		this.orderCancelLtsService = orderCancelLtsService;
	}

	public LtsAcqOrderCreateService getLtsAcqOrderCreateService() {
		return ltsAcqOrderCreateService;
	}

	public void setLtsAcqOrderCreateService(LtsAcqOrderCreateService ltsAcqOrderCreateService) {
		this.ltsAcqOrderCreateService = ltsAcqOrderCreateService;
	}

	public LtsAcqOrderRecallService getLtsAcqOrderRecallService() {
		return ltsAcqOrderRecallService;
	}

	public void setLtsAcqOrderRecallService(LtsAcqOrderRecallService ltsAcqOrderRecallService) {
		this.ltsAcqOrderRecallService = ltsAcqOrderRecallService;
	}

	public LtsAcqOrderModifyService getLtsAcqOrderModifyService() {
		return ltsAcqOrderModifyService;
	}

	public void setLtsAcqOrderModifyService(LtsAcqOrderModifyService ltsAcqOrderModifyService) {
		this.ltsAcqOrderModifyService = ltsAcqOrderModifyService;
	}
	
	public AcqOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser) {
		return ltsAcqOrderRecallService.recallOrder(sbOrderId, pIsEquiry, bomSalesUser);
	}
	
	public void approvalOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		ltsAcqOrderPostSubmitLtsService.approvalOrder(sbOrder, bomSalesUser.getUsername(), bomSalesUser.getShopCd());
	}
	
	public String signOffOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		return ltsAcqOrderPostSubmitLtsService.signOffNewAcquisitionOrder(sbOrder, bomSalesUser);
	}
	
	public String signOffOrderQC(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		return ltsAcqOrderQcSubmitLtsService.signOffOrder(sbOrder, bomSalesUser);
	}
	
	public boolean cancelOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser, String reasonCd) {
		return orderCancelLtsService.cancelOrder(sbOrder, bomSalesUser.getUsername(), reasonCd);
	}
	
	public SbOrderDTO retrieveOrder(String sbOrderId) {
		return orderRetrieveLtsService.retrieveSbOrder(sbOrderId, false);
	}
	
	public String saveOrder(AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser, String reasonCd) {
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		if (sbOrder == null) {
			sbOrder = createSbOrder(acqOrderCapture, bomSalesUser);
		}
		else {
			sbOrder = ltsAcqOrderModifyService.modifySbOrder(acqOrderCapture, sbOrder, bomSalesUser);
		}
		return orderSuspendLtsService.suspendOrder(sbOrder, bomSalesUser, reasonCd);
	}

	public SbOrderDTO createSbOrder(AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		return ltsAcqOrderCreateService.createSbOrder(acqOrderCapture, bomSalesUser);
	}
	
	public void suspendOrderWithPending(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser) {
		ltsAcqOrderPostSubmitLtsService.suspendWithPendingOrder(pSbOrder, pBomSalesUser.getUsername(), pBomSalesUser.getShopCd());
	}

	public void suspendOrderForPrepayment(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser) {
		if(pSbOrder != null && LtsSbHelper.getLtsService(pSbOrder) != null){
			ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(pSbOrder);
			orderStatusService.setPendingPrepaymentStatus(pSbOrder.getOrderId(), ltsCoreService.getDtlId(), pBomSalesUser.getUsername(), ltsCoreService.getWorkQueueType());
		}
	}
	
	public void suspendOrderForQc(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser) {
		if(pSbOrder != null && LtsSbHelper.getLtsService(pSbOrder) != null){
			ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(pSbOrder);
			orderStatusService.setPendingQcStatus(pSbOrder.getOrderId(), ltsCoreService.getDtlId(), pBomSalesUser.getUsername(), ltsCoreService.getWorkQueueType());
		}
	}
	
	public void updateAppointmentForQc(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser) {
		if(pSbOrder != null && LtsSbHelper.getLtsService(pSbOrder) != null){
			ltsAcqOrderModifyService.modifyAppointment(acqOrderCapture, pSbOrder);
		}
	}	
	
	/**
	 * @return the ltsAcqOrderQcSubmitLtsService
	 */
	public LtsAcqOrderQcSubmitLtsService getLtsAcqOrderQcSubmitLtsService() {
		return ltsAcqOrderQcSubmitLtsService;
	}

	/**
	 * @param ltsAcqOrderQcSubmitLtsService the ltsAcqOrderQcSubmitLtsService to set
	 */
	public void setLtsAcqOrderQcSubmitLtsService(
			LtsAcqOrderQcSubmitLtsService ltsAcqOrderQcSubmitLtsService) {
		this.ltsAcqOrderQcSubmitLtsService = ltsAcqOrderQcSubmitLtsService;
	}
	
	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}	
}
