package com.bomwebportal.lts.service.disconnect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.order.DisconnectOrderCreateService;
import com.bomwebportal.lts.service.order.DisconnectOrderModifyService;
import com.bomwebportal.lts.service.order.DisconnectOrderRecallService;
import com.bomwebportal.lts.service.order.OrderCancelLtsService;
import com.bomwebportal.lts.service.order.OrderPostSubmitLtsService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderSuspendLtsService;

public class LtsTerminateOrderServiceImpl implements LtsTerminateOrderService {

	protected final Log logger = LogFactory.getLog(getClass());

	protected OrderCancelLtsService orderCancelLtsService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected OrderSuspendLtsService orderSuspendLtsService;
	protected OrderPostSubmitLtsService orderPostSubmitLtsService;
	protected DisconnectOrderCreateService disconnectOrderCreateService;
	protected DisconnectOrderModifyService disconnectOrderModifyService;
	protected DisconnectOrderRecallService disconnectOrderRecallService;
	
	public DisconnectOrderModifyService getDisconnectOrderModifyService() {
		return disconnectOrderModifyService;
	}

	public void setDisconnectOrderModifyService(
			DisconnectOrderModifyService disconnectOrderModifyService) {
		this.disconnectOrderModifyService = disconnectOrderModifyService;
	}

	public DisconnectOrderRecallService getDisconnectOrderRecallService() {
		return disconnectOrderRecallService;
	}

	public void setDisconnectOrderRecallService(
			DisconnectOrderRecallService disconnectOrderRecallService) {
		this.disconnectOrderRecallService = disconnectOrderRecallService;
	}
	public DisconnectOrderCreateService getDisconnectOrderCreateService() {
		return disconnectOrderCreateService;
	}

	public void setDisconnectOrderCreateService(
			DisconnectOrderCreateService disconnectOrderCreateService) {
		this.disconnectOrderCreateService = disconnectOrderCreateService;
	}

	public OrderPostSubmitLtsService getOrderPostSubmitLtsService() {
		return orderPostSubmitLtsService;
	}

	public void setOrderPostSubmitLtsService(
			OrderPostSubmitLtsService orderPostSubmitLtsService) {
		this.orderPostSubmitLtsService = orderPostSubmitLtsService;
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
	
	public TerminateOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser) {
		return disconnectOrderRecallService.recallOrder(sbOrderId, pIsEquiry, bomSalesUser);
	}
	
	public void approvalOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		orderPostSubmitLtsService.approvalOrder(sbOrder, bomSalesUser.getUsername(), bomSalesUser.getShopCd());
	}
	
	public String signOffOrder(SbOrderDTO sbOrder, String pReportType, String pExportType, String pEditButton, BomSalesUserDTO bomSalesUser) {
		return orderPostSubmitLtsService.signOffOrder(sbOrder, pReportType, pExportType, pEditButton, bomSalesUser.getUsername(), bomSalesUser.getShopCd());
	}
	
	public boolean cancelOrder(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser, String reasonCd) {
		return orderCancelLtsService.cancelOrder(sbOrder, bomSalesUser.getUsername(), reasonCd);
	}
	
	public SbOrderDTO retrieveOrder(String sbOrderId) {
		return orderRetrieveLtsService.retrieveSbOrder(sbOrderId, false);
	}
	
	public String saveOrder(TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String reasonCd) {
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		if (sbOrder == null) {
			sbOrder = createSbOrder(orderCapture, bomSalesUser);
		}
		else {
			sbOrder = disconnectOrderModifyService.modifyTerminateSbOrder(orderCapture, sbOrder, bomSalesUser);
		}
		return orderSuspendLtsService.suspendOrder(sbOrder, bomSalesUser, reasonCd);
	}

	public SbOrderDTO createSbOrder(TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		return disconnectOrderCreateService.createTerminateSbOrder(orderCapture, bomSalesUser);
	}
	
	public void suspendOrderWithPending(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser) {
		orderPostSubmitLtsService.suspendWithPendingOrder(pSbOrder, pBomSalesUser.getUsername(), pBomSalesUser.getShopCd());
	}
}
