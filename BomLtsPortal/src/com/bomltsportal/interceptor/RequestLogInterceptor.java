package com.bomltsportal.interceptor;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.service.OnlineSalesLogService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionConstant;
import com.bomltsportal.util.SessionHelper;
import com.google.common.collect.ImmutableMap;

public class RequestLogInterceptor extends HandlerInterceptorAdapter {

	protected final Log logger = LogFactory.getLog(getClass());
	OnlineSalesLogService onlineSalesLogService;

	private final String[] NEW_LOG = {"registration", "amendment"};
	private final String[] SKIP_TO_LOG = {"message", "getimage"}; 
	private final String[] NOT_A_PAGE = {"captcha", "getimage", "shoppingcart", "ordersubmit"};
	private final Map<String, String> REPORT_RENAME_MAP = ImmutableMap.of(
	        "acknowledgement", "ack_report",
	        "summary", "sum_report"
	);
	
	public boolean preHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler) {
		
		HttpSession session = request.getSession();
		
		String reqId = SessionHelper.getRequestId(request);
		String reqSeq = SessionHelper.getRequestSeq(request);
		String currentPage = (String) session.getAttribute(BomLtsConstant.SESSION_CURRENT_PAGE);
		OrderCaptureDTO order = SessionHelper.getOrderCapture(request);
		String nextPage = request.getServletPath().replace(".html", "").replace("/", "");
		
		if(Arrays.asList(SKIP_TO_LOG).contains(nextPage)){
			return true;
		}
		
		if(Arrays.asList(NEW_LOG).contains(nextPage)){
			reqId = null;
			reqSeq = null;
		}
		
		if(reqId==null){
			logger.info("new lts online request");
			
			String channel = null;
			String subType = request.getParameter(SessionConstant.PARAM_SRV_TYPE);
			
			reqId = onlineSalesLogService.getNewRequest(request.getRemoteAddr(), session.getId(), request.getHeader("User-Agent"), channel, BomLtsConstant.LOB_LTS, subType);								
			reqSeq = "0"; 
			
			logger.info("new request id assigend:"+reqId);
			
			SessionHelper.setRequestId(request, reqId);
						
		}else{			
			logger.info("request id:"+reqId);			
		}
		
		if(reqSeq == null){
			reqSeq = "0"; 
		}
		
		if(request.getHeader("X-Requested-With")==null //exclude ajax call
				&& request.getMethod().equals("GET") //save after post submit
		){	
			SessionHelper.setRequestId(request, reqId);
			
			if("report".equals(nextPage)){
				String renamePage = REPORT_RENAME_MAP.get(currentPage);
				nextPage = renamePage != null? renamePage: nextPage;
			}
			
			if(!Arrays.asList(NOT_A_PAGE).contains(nextPage)){
				session.setAttribute(BomLtsConstant.SESSION_CURRENT_PAGE, nextPage);
			}
			
			if(reqSeq != null){
				onlineSalesLogService.logPageTrackDetail(order, reqId, currentPage, nextPage, reqSeq);
				session.setAttribute(BomLtsConstant.SESSION_LTS_ONLINE_REQUEST_SEQ, String.valueOf((Integer.valueOf(reqSeq))+1));
			}
			
		}
		System.out.println("reqId: " + reqId + ", reqSeq: " + reqSeq +  ", currentPage: " + currentPage + ", nextPage:" + nextPage);
		return true;
	}

	public OnlineSalesLogService getOnlineSalesLogService() {
		return onlineSalesLogService;
	}

	public void setOnlineSalesLogService(OnlineSalesLogService onlineSalesLogService) {
		this.onlineSalesLogService = onlineSalesLogService;
	}
	
	
}
