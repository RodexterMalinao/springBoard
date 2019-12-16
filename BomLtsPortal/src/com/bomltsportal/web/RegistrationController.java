package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionConstant;
import com.bomltsportal.util.SessionHelper;

public class RegistrationController extends AbstractController{

	protected final Log logger = LogFactory.getLog(getClass());
	private static final String addressLookupView = "addresslookup.html";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// CLEAR ALL SESSION ATTBRIBUTES
		SessionHelper.clearOrderCapture(request);
		
		String srvType = request.getParameter(SessionConstant.PARAM_SRV_TYPE);
		String lang = request.getParameter(SessionConstant.PARAM_LANGUAGE);
		Boolean isVtMode = Boolean.parseBoolean(request.getParameter(SessionConstant.PARAM_VT_MODE));
		OrderCaptureDTO orderCapture = new OrderCaptureDTO();

		SessionHelper.setSessionOption(request, BomLtsConstant.SESSION_OPTION_VT, isVtMode);
		SessionHelper.setOrderCapture(request, orderCapture);
		SessionHelper.setOrderSrvType(request, srvType);
		SessionHelper.setLanguage(request, response, lang);
		SessionHelper.setSessionUid(request);
		
		
		logger.info("\n Session UID: " + SessionHelper.getSessionUid(request) +
				"\n Service Type: " + srvType +
				"\n Language: " + lang +
				"\n isVtMode: " + isVtMode);
		
		return new ModelAndView(new RedirectView(addressLookupView));
	}

}
