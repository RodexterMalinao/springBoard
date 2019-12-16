package com.bomwebportal.svc.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bomwebportal.exception.UserTimeoutException;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.sso.web.SSORedirController;
import com.bomwebportal.util.ConfigProperties;

@Controller
public class SVCOrderPreviewController {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/svcmoborderpreview.html")
	public String preview(String orderId,
			@RequestParam(required=false) String language,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("SVC Previewing order : " + orderId);
		return redirector(orderId, true, language, request, response);
	}
	
	
	private String redirector(String orderId,
			boolean isPreview,
			String language,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			boolean sso = false;
			
			Map<String,String> orderTypeInfo = orderService.getOrderType(orderId);
			if (orderTypeInfo == null) {
				throw new IllegalArgumentException("unable to get orderTypeInfo for order : " + orderId);
			}
			String channelId = orderTypeInfo.get("channel_id");
			String orderType = orderTypeInfo.get("order_type");
			
			if (StringUtils.isEmpty(language)) language = "en";
			
			String cosBase = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
			if (cosBase.endsWith("/")) {
				cosBase = cosBase.substring(0, cosBase.length()-1);
			}
			if (!cosBase.toLowerCase().startsWith("http")) {
				String fp = request.getRequestURL().toString();
				String domain = fp.replace(request.getRequestURI(), "");
				cosBase = domain + cosBase;
			}
			
			String url = null;
			if ("ACQ".equals(orderType)) {
				if ("1".equals(channelId)) {
					url = "/orderdetail.html?orderId={orderId}";
					if (isPreview) url +="&action=PREVIEW";
				} else if ("2".equals(channelId)) {
					url = "/mobccspreview.html?orderId={orderId}";
					if (isPreview) url += "&action=ENQUIRY";
				} else if ("3".equals(channelId)) {
					url = "/orderdetail.html?orderId={orderId}";
					if (isPreview) url += "&action=PREVIEW";
				} else if ("10".equals(channelId)) {
					url = "/orderdetail.html?orderId={orderId}";
					if (isPreview) url += "&action=PREVIEW";
				}
			} else if ("COS".equals(orderType)) {
				url = cosBase + "/enquiryorder.html?orderId={orderId}";
				if (isPreview) url += "&action=ENQUIRY";
				sso = true;
			} else if ("BRM".equals(orderType)) {
				url = cosBase + "/enquiryorder.html?orderId={orderId}";
				if (isPreview) url += "&action=ENQUIRY";
				sso = true;
			}  else if ("TOO1".equals(orderType)) {
				url = cosBase + "/enquiryorder.html?orderId={orderId}";
				if (isPreview) url += "&action=ENQUIRY";
				sso = true;
			} else if ("CSIM".equals(orderType)) {
				url = cosBase + "/csimsummary/enquiryorder.html?orderId={orderId}";
				if (isPreview) url += "&action=ENQUIRY";
				sso = true;
			} else if ("CPM".equals(orderType)) {
				url = cosBase + "/cpmsummary/enquiryorder.html?orderId={orderId}";
				if (isPreview) url += "&action=ENQUIRY";
				sso = true;
			}
			
			if (url == null) {

				logger.error("Cannot resolve preview url for orderId : " + orderId + ", type=" + orderType + ",channelId="+channelId);
				throw new IllegalArgumentException("Cannot resolve order url for (orderId,orderType,channelId) : ("
						+ orderId + "/" + orderType + "/" + channelId + ")");
			}
			
			url = url.replace("{orderId}", orderId);
			logger.debug("SVC Redirect to url : " + url);

			if (sso) {
				url = SSORedirController.rewrite (url);
				response.sendRedirect(url);
				return null;
			}
			//String tempUrl= "/ssoredir.html&__t=";
		//	System.out.println("final:"+"redirect:" +tempUrl+url);
		//	return "redirect:" +tempUrl+url;
			return "redirect:" + url;
		} catch (IllegalArgumentException e) {
			logger.error("Error while resolving order url for orderId : " + orderId, e);
			throw new UserTimeoutException("Cannot open order detail - " + e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("Error while resolving order url for orderId : " + orderId, e);
			throw new UserTimeoutException("Cannot open order detail - unexpected exception while resolving order url : " + e.getLocalizedMessage(), e);
		}
	}
	
	
}
