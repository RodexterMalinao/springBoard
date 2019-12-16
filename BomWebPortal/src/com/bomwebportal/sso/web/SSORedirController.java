package com.bomwebportal.sso.web;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.sso.SSOAuthContext;
import com.bomwebportal.sso.service.SSOService;

@Controller
public class SSORedirController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public final static String PARM_TARGET_URL = "__t";
	public final static String PARM_TARGET_APPID = "__a";
	
	@Autowired
	private SSOService ssoService;
	
	@RequestMapping("/ssoredir.html")
	public void redir(@RequestParam(PARM_TARGET_URL)String target, @RequestParam(value=PARM_TARGET_APPID, required=false)String targetAppId,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		
		URIBuilder ub;
		try {
			ub = new  URIBuilder(target);
			
			attachRemainingQueryParams(ub, request);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error("Unexpected error handling sso redirect url", e);
			throw new AppRuntimeException("Invalid target", e);
		}
		
		
		SSOAuthContext authContext = (SSOAuthContext)session.getAttribute("ssoAuthContext");
		
		
		if (authContext == null) {
			// should not happen normally
			String redirurl = ub.toString();
			logger.debug("Invalid sso session, just redirect to target");
			response.sendRedirect(redirurl);
			return;
		}
		
		
		//////////////////////////////////////
		
		String userId = authContext.getUserId();
		String appId = authContext.getAppId();
		String hash = authContext.getHash();
		if (StringUtils.isBlank(targetAppId)) targetAppId=appId;
		
		logger.debug("Handling sso redirect for userId:" + userId + ", targetAppId:"+targetAppId+", url:" + target);
		
		String ticket = ssoService.allocEt(appId, hash, targetAppId);
		
		if (StringUtils.isEmpty(ticket)) {
			logger.error("Failed to get ticket for userId:" + userId + ", targetAppId:" + targetAppId);
		}
		logger.debug("Received ticket for userId:" + userId + ", targetAppId:" + targetAppId + ", ticket:" + ticket);
		
		ub.addParameter("SSO_ET", ticket);
		
		String redirUrl = ub.toString();
		logger.debug("SSO Redirect : userId=" + userId + ", target=" + redirUrl);
		response.sendRedirect(redirUrl);
		return;
		
	}

	public static String rewrite(String url) {
		return rewrite(url, null);
	}
	
	public static String rewrite(String url, String appId) {
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (sra == null) return url;
		HttpServletRequest req = sra.getRequest();
		return rewrite(req, url, appId);
	}
	
	public static String rewrite(HttpServletRequest request, String url, String appId) {
		
		if (request == null) return url;
		
		String contextPath = request.getContextPath();
    	String target = url;
        
      	try {
      		  String encoded = URLEncoder.encode(url, "UTF-8");
      		  target = contextPath + "/ssoredir.html?" + PARM_TARGET_URL + "=" + encoded;
      		  if (appId != null) {
      			  target = target + "&" + PARM_TARGET_APPID + "=" + appId;
      		  }
      	} catch (Exception e) {
      		
      	}
      	return target;
	}
	
	
	
	
	

	private void attachRemainingQueryParams(URIBuilder builder, HttpServletRequest request) {
		String qs = request.getQueryString();
		List<NameValuePair> qslist = URLEncodedUtils.parse(qs, Consts.UTF_8);

		for (NameValuePair nv : qslist) {
			if (!PARM_TARGET_APPID.equals(nv.getName()) && !PARM_TARGET_URL.equals(nv.getName())) {
				builder.addParameter(nv.getName(), nv.getValue());
			}
		}
	}
	

}
