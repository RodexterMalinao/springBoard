package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.order.OrderCancelLtsService;
import com.bomwebportal.lts.service.order.OrderCreateService;
import com.bomwebportal.lts.service.order.OrderModifyService;
import com.bomwebportal.lts.service.order.OrderPostSubmitLtsService;
import com.bomwebportal.lts.service.order.OrderRecallService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderSuspendLtsService;

public class LtsUpgradeEyeOrderServiceImpl implements LtsUpgradeEyeOrderService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected OrderCreateService orderCreateService;
	protected OrderRecallService orderRecallService;
	protected OrderModifyService orderModifyService;
	protected OrderCancelLtsService orderCancelLtsService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected OrderSuspendLtsService orderSuspendLtsService;
	protected OrderPostSubmitLtsService orderPostSubmitLtsService;
	
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

	public OrderCreateService getOrderCreateService() {
		return orderCreateService;
	}

	public void setOrderCreateService(OrderCreateService orderCreateService) {
		this.orderCreateService = orderCreateService;
	}

	public OrderRecallService getOrderRecallService() {
		return orderRecallService;
	}

	public void setOrderRecallService(OrderRecallService orderRecallService) {
		this.orderRecallService = orderRecallService;
	}

	public OrderModifyService getOrderModifyService() {
		return orderModifyService;
	}

	public void setOrderModifyService(OrderModifyService orderModifyService) {
		this.orderModifyService = orderModifyService;
	}
	
	public OrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser) {
		return orderRecallService.recallOrder(sbOrderId, pIsEquiry, bomSalesUser);
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
	
	public String saveOrder(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser, String reasonCd) {
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		if (sbOrder == null) {
			sbOrder = createSbOrder(orderCapture, bomSalesUser);
		}
		else {
			sbOrder = orderModifyService.modifySbOrder(orderCapture, sbOrder, bomSalesUser);
		}
		return orderSuspendLtsService.suspendOrder(sbOrder, bomSalesUser, reasonCd);
	}

	public SbOrderDTO createSbOrder(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		return orderCreateService.createSbOrder(orderCapture, bomSalesUser);
	}
	
	public void suspendOrderWithPending(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser) {
		orderPostSubmitLtsService.suspendWithPendingOrder(pSbOrder, pBomSalesUser.getUsername(), pBomSalesUser.getShopCd());
	}
}
