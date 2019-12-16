package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.service.OnlineSalesLogService;
import com.bomltsportal.service.OrderSubmitService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public class OrderSubmitController extends AbstractController {
	
	
	private static final String summaryView = "summary.html";
	
	private OrderSubmitService orderSubmitService;
	private OnlineSalesLogService onlineSalesLogService;
	
	public OrderSubmitService getOrderSubmitService() {
		return orderSubmitService;
	}

	public void setOrderSubmitService(OrderSubmitService orderSubmitService) {
		this.orderSubmitService = orderSubmitService;
	}

	public OnlineSalesLogService getOnlineSalesLogService() {
		return onlineSalesLogService;
	}

	public void setOnlineSalesLogService(OnlineSalesLogService onlineSalesLogService) {
		this.onlineSalesLogService = onlineSalesLogService;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		
		if(!validateOrderCapture(orderCapture)) {
			return SessionHelper.getSessionTimeOutRedirectView();
		}
		
		SbOrderDTO sbOrder = orderSubmitService.submitOrder(orderCapture);
		orderCapture.setSbOrder(sbOrder);

		onlineSalesLogService.logOnlineDetailTrack(
				SessionHelper.getRequestId(request), 
				SessionHelper.getCurrentPage(request), 
				BomLtsConstant.LOG_TRACK_ITEM_CD_ORDER_ID, 
				sbOrder.getOrderId(), 
				SessionHelper.getRequestSeq(request));
		
		return new ModelAndView(new RedirectView(summaryView));
	}

	private boolean validateOrderCapture(OrderCaptureDTO orderCapture) {
		if (orderCapture == null) {
			return false;
		}
		
		if (orderCapture.isOrderSignoffed()) {
			return false;
		}
		
		if (orderCapture.getAddressLookupForm() == null
				|| orderCapture.getAddressRolloutForm() == null
				|| orderCapture.getApplicantInfoForm() == null
				|| orderCapture.getBasketDetailForm() == null
				|| orderCapture.getBasketSelectForm() == null
				|| orderCapture.getVasDetailForm() == null) {
			return false;
		}
		
		return true;
	}
	
}
