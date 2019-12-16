package com.bomltsportal.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomltsportal.service.OnlineSalesLogService;


public class LivechatlogController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private OnlineSalesLogService onlineSalesLogService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String channel = null;
		String ip = null;
		String action = null;
		String model = null;
		//logger.debug("livechatlogController handleRequest is called");
		if (request.getParameter("channel") != null) {
			channel = new String(request.getParameter("channel").getBytes(
					"ISO8859-1"), "UTF-8");
		} else {
			channel = "undefined";
		}
		if (request.getParameter("action") != null) {
			action = new String(request.getParameter("action").getBytes(
					"ISO8859-1"), "UTF-8");
		} else {
			action = "unknown";
		}
		if (request.getRemoteAddr() != null) {
			ip = new String(request.getRemoteAddr().getBytes("ISO8859-1"),
					"UTF-8");
		} else {
			ip = "unknown";
		}
		try {
			onlineSalesLogService.serviceLog(channel, action, ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model = SessionHelper.getOrderCapture(request).getServiceTypeInd();
		
		if ("EYE".equals(model))
			return new ModelAndView("livechatlogeye");
		else return new ModelAndView("livechatlogdel");
		
		
	}

	public OnlineSalesLogService getOnlineSalesLogService() {
		return onlineSalesLogService;
	}

	public void setOnlineSalesLogService(
			OnlineSalesLogService onlineSalesLogService) {
		this.onlineSalesLogService = onlineSalesLogService;
	}
}