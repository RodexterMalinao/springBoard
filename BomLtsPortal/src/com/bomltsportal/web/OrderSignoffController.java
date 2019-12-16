package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.CreditPaymentFormDTO;
import com.bomltsportal.dto.form.CreditPaymentFormDTO.PaymentStatus;
import com.bomltsportal.service.OrderSignoffService;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public class OrderSignoffController  extends AbstractController {
	
	
	private static final String nextView = "acknowledgement.html";
	
	private OrderSignoffService orderSignoffService;
	
	public OrderSignoffService getOrderSignoffService() {
		return orderSignoffService;
	}

	public void setOrderSignoffService(OrderSignoffService orderSignoffService) {
		this.orderSignoffService = orderSignoffService;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		
		if (orderCapture == null || orderCapture.isOrderSignoffed() || !isOrderPaymentSuccess(orderCapture)) {
			return SessionHelper.getSessionTimeOutRedirectView();
		}
		
		SbOrderDTO sbOrder = orderSignoffService.signoffOrder(orderCapture, request.getSession().getId());
		String regMsg = orderSignoffService.regMyHktTheClub(sbOrder, RequestContextUtils.getLocale(request).toString());
		if (StringUtils.isNotBlank(regMsg)) {
			SessionHelper.setSessionOption(request, "regMsg", regMsg);
		}
		orderCapture.setSbOrder(sbOrder);
		orderCapture.setOrderSignoffed(true);
		return new ModelAndView(new RedirectView(nextView));
	}

	
	private boolean isOrderPaymentSuccess(OrderCaptureDTO orderCapture) {
		if (orderCapture == null || orderCapture.getCreditPaymentForm() == null) {
			return false;
		}
		
		CreditPaymentFormDTO paymentForm = orderCapture.getCreditPaymentForm();
		if (PaymentStatus.FINISH == paymentForm.getPayStatus()
				&& StringUtils.equals("000000", paymentForm.getResponseCode())) {
			return true;
		}
		return false;
	}

}
