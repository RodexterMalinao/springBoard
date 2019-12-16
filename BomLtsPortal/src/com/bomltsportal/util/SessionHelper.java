package com.bomltsportal.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OrderCaptureDTO;

public class SessionHelper {
	public static void clearOrderCapture(HttpServletRequest request) {
		request.getSession().removeAttribute(BomLtsConstant.SESSION_ORDER_CAPTURE);
	}
	
	public static OrderCaptureDTO getOrderCapture(HttpServletRequest request) {
		return (OrderCaptureDTO)request.getSession().getAttribute(BomLtsConstant.SESSION_ORDER_CAPTURE);
	}
	
	public static void setOrderCapture(HttpServletRequest request, OrderCaptureDTO orderCapture) {
		request.getSession().setAttribute(BomLtsConstant.SESSION_ORDER_CAPTURE, orderCapture);
		//setSessionUid(request);
	}

	public static void setOrderSrvType(HttpServletRequest request, String srvType) {
		OrderCaptureDTO orderCapture = getOrderCapture(request);
		if (orderCapture == null) {
			return;
		}
		
		if (StringUtils.equals(srvType, BomLtsConstant.SERVICE_TYPE_DEL)) {
			orderCapture.setChannelId(BomLtsConstant.CHANNEL_ID_ONLINE_DEL);
		}
		if (StringUtils.equals(srvType, BomLtsConstant.SERVICE_TYPE_EYE)) {
			orderCapture.setChannelId(BomLtsConstant.CHANNEL_ID_ONLINE_EYE);
		}		
		
		orderCapture.setServiceTypeInd(srvType);
	}
	
	public static void setLanguage(HttpServletRequest request, HttpServletResponse response, String lang) {
		OrderCaptureDTO orderCapture = getOrderCapture(request);
		if (orderCapture == null) {
			return;
		}
		if (StringUtils.isBlank(lang)) {
			orderCapture.setLang(BomLtsConstant.LOCALE_ENG);
			RequestContextUtils.getLocaleResolver(request).setLocale(request, response, LocaleUtils.toLocale(BomLtsConstant.LOCALE_ENG));
		}
		else {
			orderCapture.setLang(lang);
			RequestContextUtils.getLocaleResolver(request).setLocale(request, response, LocaleUtils.toLocale(lang));
		}
	}
	
	public static void setupSession(HttpServletRequest request){
		OrderCaptureDTO order = getOrderCapture(request);
		if(order == null){
			order = new OrderCaptureDTO();
			order.setServiceTypeInd(BomLtsConstant.SERVICE_TYPE_EYE);
			setOrderCapture(request, order);
		}
	}
	
	public static ModelAndView getSessionTimeOutView(){
		return new ModelAndView(BomLtsConstant.URL_VIEW_MSG, BomLtsConstant.URL_PARM_MSG_CODE, BomLtsConstant.URL_PARM_MSG_CODE_TIMEOUT);
	}

	public static ModelAndView getSessionTimeOutRedirectView(){
		return new ModelAndView(new RedirectView(BomLtsConstant.URL_VIEW_MSG + ".html?" + BomLtsConstant.URL_PARM_MSG_CODE + "=" + BomLtsConstant.URL_PARM_MSG_CODE_TIMEOUT));
	}
	
	public static ModelAndView getExceptionView(){
		return new ModelAndView(BomLtsConstant.URL_VIEW_MSG, BomLtsConstant.URL_PARM_MSG_CODE, BomLtsConstant.URL_PARM_MSG_CODE_EXCEPTION);
	}
	
	public static void setSessionUid(HttpServletRequest request) {
		request.getSession().setAttribute(BomLtsConstant.SESSION_OSUID, UUID.randomUUID().toString());
	}
	
	public static String getSessionUid(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(BomLtsConstant.SESSION_OSUID);
	}

	public static void setRequestId(HttpServletRequest request, String reqId) {
		request.getSession().setAttribute(BomLtsConstant.SESSION_LTS_ONLINE_REQUEST, reqId);
	}

	public static String getRequestId(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(BomLtsConstant.SESSION_LTS_ONLINE_REQUEST);
	}

	public static void setRequestSeq(HttpServletRequest request, String reqSeq) {
		request.getSession().setAttribute(BomLtsConstant.SESSION_LTS_ONLINE_REQUEST_SEQ, reqSeq);
	}
	
	public static String getRequestSeq(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(BomLtsConstant.SESSION_LTS_ONLINE_REQUEST_SEQ);
	}
	
	public static void setSessionOption(HttpServletRequest request, String optionName, Object option) {
		request.getSession().setAttribute(optionName, option);
	}

	public static String getCurrentPage(HttpServletRequest request) {
		return (String)request.getSession().getAttribute(BomLtsConstant.SESSION_CURRENT_PAGE);
	}
	
	public static void setCurrentPage(HttpServletRequest request, String currPage) {
		request.getSession().setAttribute(BomLtsConstant.SESSION_CURRENT_PAGE, currPage);
	}
	
	public static Object getSessionOption(HttpServletRequest request, String optionName) {
		return request.getSession().getAttribute(optionName);
	}
	
}
