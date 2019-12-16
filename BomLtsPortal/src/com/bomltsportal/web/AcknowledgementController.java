package com.bomltsportal.web;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.AcknowledgementFormDTO;
import com.bomltsportal.dto.form.CreditPaymentFormDTO;
import com.bomltsportal.dto.form.CreditPaymentFormDTO.PaymentStatus;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomltsportal.util.SessionHelper;

public class AcknowledgementController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request); 
		if( orderCapture == null || !orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}
		
		CreditPaymentFormDTO paymentForm = orderCapture.getCreditPaymentForm();
		if (PaymentStatus.FINISH != paymentForm.getPayStatus()
				|| !StringUtils.equals("000000", paymentForm.getResponseCode())) {
			return SessionHelper.getSessionTimeOutView();
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{

		Locale locale = RequestContextUtils.getLocale(request);
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		AcknowledgementFormDTO acknowledgementForm = new AcknowledgementFormDTO();
		acknowledgementForm.setInstallAddress(orderCaptureDTO.getSummaryForm().getAddress());
		acknowledgementForm.setInstallDate(LtsDateFormatHelper.convertToSBTimeSlot(orderCaptureDTO.getSummaryForm().getInstallDate()));
		acknowledgementForm.setInstallTime(orderCaptureDTO.getSummaryForm().getInstallTime());
		return acknowledgementForm;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException {

		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		String srv = orderCaptureDTO.getServiceTypeInd();
		String lang = orderCaptureDTO.getLang();
		
		return new ModelAndView(new RedirectView("registration.html?srv=" + srv + "&lang=" + lang));
	}
}
	
	