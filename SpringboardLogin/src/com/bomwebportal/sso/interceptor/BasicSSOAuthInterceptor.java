package com.bomwebportal.sso.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomwebportal.exception.BomWebPortalException;
import com.bomwebportal.sso.SSOAuthContext;
import com.bomwebportal.sso.event.SSOAuthenticationSuccessEvent;
import com.bomwebportal.sso.service.SSOAuthenticator;

public class BasicSSOAuthInterceptor extends HandlerInterceptorAdapter implements ApplicationEventPublisherAware {

	private final Log logger = LogFactory.getLog(getClass());
	
	public final static String AUTO_LOGIN_PARAM = "_al";
	public final static String SSO_ET_PARAM = "SSO_ET";
	public final static String SSO_HASH_AUTH_HEADER = "X-SSO-Authorization";
	public final static String SSO_AUTH_CONTEXT = "ssoAuthContext";
	
	private SSOAuthenticator ssoAuthenticator;

	private ApplicationEventPublisher applicationEventPublisher;

//	@Value("${sso.authFailureUrl}")
	private String failureUrl ;
	
	private boolean bypass = false;
	
	private boolean forceClearSessionOnTicket = true;
	
	private boolean forceClearSessionOnAutoLogin = false;
	
	private int aliveInterval = 10000;  // ms
	
	private boolean removeSsoParamsAfterAuth = true;

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}


	public boolean isBypass() {
		return bypass;
	}

	public void setBypass(boolean bypass) {
		this.bypass = bypass;
	}
	
	

	public boolean isForceClearSessionOnTicket() {
		return forceClearSessionOnTicket;
	}

	public void setForceClearSessionOnTicket(
			boolean forceClearSessionOnTicket) {
		this.forceClearSessionOnTicket = forceClearSessionOnTicket;
	}

	public boolean isForceClearSessionOnAutoLogin() {
		return forceClearSessionOnAutoLogin;
	}

	public void setForceClearSessionOnAutoLogin(boolean forceClearSessionOnAutoLogin) {
		this.forceClearSessionOnAutoLogin = forceClearSessionOnAutoLogin;
	}
	
	public int getAliveInterval() {
		return aliveInterval;
	}

	public void setAliveInterval(int pAliveInterval) {
		this.aliveInterval = pAliveInterval;
	}

	public boolean isRemoveSsoParamsAfterAuth() {
		return removeSsoParamsAfterAuth;
	}

	public void setRemoveSsoParamsAfterAuth(boolean removeSsoParamsAfterAuth) {
		this.removeSsoParamsAfterAuth = removeSsoParamsAfterAuth;
	}

	public boolean preHandle(HttpServletRequest pRequest,
			HttpServletResponse pResponse, Object pHandler) throws Exception {
		
		if (bypass) return true;
		
		if (shouldBypassThisRequest(pRequest)) return true;
		
		String authHeader = pRequest.getHeader(SSO_HASH_AUTH_HEADER);
		if (StringUtils.isNotEmpty(authHeader)) {
			if (checkAuthByHashHeader(authHeader) != null) {
				return true;
			}
		}

		HttpSession session = pRequest.getSession();

		String al = pRequest.getParameter(AUTO_LOGIN_PARAM);
		boolean isAutoLoginReq = StringUtils.isNotEmpty(al);
		
		String ticket = pRequest.getParameter(SSO_ET_PARAM);
		SSOAuthContext authContext = (SSOAuthContext)session.getAttribute(SSO_AUTH_CONTEXT);
		
		
		
		if (StringUtils.isNotEmpty(ticket)) {
			if (authContext != null 
					&& ! ticket.equals(authContext.getTicket()) 
					&& isForceClearSessionOnTicket()) {
				authContext = null;
				clearSession(pRequest);
			}
		} else if (isAutoLoginReq) {
			// is autologin req but no ticket param
			// or session is cleared when _al=new
			if ( this.isForceClearSessionOnAutoLogin() || "new".equals(al)) {
				authContext = null;
				clearSession(pRequest);
			}
		}
		
		// has previous auth session ...
		if (authContext != null) {
			if (System.currentTimeMillis() - authContext.getLastUpdate() > getAliveInterval()) {
				if(!ssoAuthenticator.isAlive(authContext)){

					pRequest.getSession().removeAttribute(SSO_AUTH_CONTEXT);
					unsuccessfulAuthentication(pRequest, pResponse);
					return false;
				} else {
					return true;
				}
			}
			return true;
			
		}
		
		
		authContext = null;
		// no prev. auth session ..

		if (StringUtils.isNotEmpty(ticket)) {
			authContext = null;
			authContext = authByTicket(ticket, pRequest);
			if (authContext != null) {
				return successfulAuthentication(pRequest, pResponse, authContext);
				//return true;
			}
			
			unsuccessfulAuthentication(pRequest, pResponse);
			return false;
		} else if (isAutoLoginReq) {
			// al parameter to trigger autologin if auth session not exists...
			sendToSSORejoin(pRequest, pResponse);
			return false;

		}
		unsuccessfulAuthentication(pRequest, pResponse);
		return false;
	}
	
	private SSOAuthContext authByTicket(String pTicket, HttpServletRequest pRequest) throws BomWebPortalException {
		if (StringUtils.isEmpty(pTicket)) return null;
		return ssoAuthenticator.authenticate(pTicket);
	}
	
	private SSOAuthContext checkAuthByHashHeader(String pEncoded) {
		byte decoded[] = Base64.decodeBase64(pEncoded.getBytes());
		String header = new String(decoded);
		logger.debug("decoded header="+header);
		if (StringUtils.isEmpty(header)) return null;
		
		
		if (header.indexOf(':') >= 0) {
			String f[] = header.split(":",2);
			if (f.length > 1) {
				String userId = f[0];
				String hash = f[1];
				SSOAuthContext ctx = new SSOAuthContext(userId, hash, null);
				if (ssoAuthenticator.isAlive(ctx)) {
					return ctx;
				}
			}
		}
		return null;
		
	}
	
	protected void sendToSSORejoin(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
		sendToSSORejoin(pRequest, pResponse, false);
	}
	
	protected void sendToSSORejoin(HttpServletRequest pRequest, HttpServletResponse pResponse, boolean pIsAllowSsoFeLogin) throws IOException {
		String fp = pRequest.getRequestURL().toString();
		//String sp = request.getRequestURI().replaceFirst(request.getContextPath(), "");
		String absoluteContext = fp.replace(pRequest.getRequestURI(), "") + pRequest.getContextPath();
		
		String qs = pRequest.getQueryString();
		qs = this.stripSsoParams(qs);
		
		String prefix = StringUtils.isEmpty(qs) ? "" : "&";
		qs = qs + prefix + SSO_ET_PARAM + "={ET}";  // SSO_ET={ET}

		StringBuffer redirectUrl = pRequest.getRequestURL();
		redirectUrl.append("?").append(qs);
		
		String target = redirectUrl.toString();
		logger.debug("target url = " + target);

		String fail = failureUrl;
		if (!fail.startsWith("http")) {
			if (!fail.startsWith("/")) {
				fail = "/" + fail;
			}
			fail = absoluteContext + fail;
		}
		String ssourl = ssoAuthenticator.createRejoinUrl(target, fail); //rejoinUrl+"?a=SBACT_MOB&go="+URLEncoder.encode(target)+"&back="+URLEncoder.encode(fail);
		if (pIsAllowSsoFeLogin) {
			ssourl = ssourl + "&ns=";
		}
		logger.debug("redirect to ssourl = " + ssourl);

		sendRedirect(pResponse, ssourl);

	}
	
	protected boolean shouldBypassThisRequest(HttpServletRequest pRequest) {
		return false;
	}
	
	protected boolean successfulAuthentication(HttpServletRequest pRequest, HttpServletResponse pResponse, SSOAuthContext pAuthContext) throws Exception {
		logger.debug("Authentication success: " + pAuthContext.getUserId());
		pRequest.getSession().setAttribute(SSO_AUTH_CONTEXT, pAuthContext);
		if (applicationEventPublisher != null)
			applicationEventPublisher.publishEvent(new SSOAuthenticationSuccessEvent(pAuthContext));
		
		
		// Try to remove the sso parameter and redirect after successful authentication, for GET request.
		// may enable/disble with properties removeSsoParamsAfterAuth : default true 
		if (isRemoveSsoParamsAfterAuth() && "GET".equalsIgnoreCase(pRequest.getMethod())) {
			
			StringBuffer url = pRequest.getRequestURL();
			String qs = pRequest.getQueryString();
			qs = stripSsoParams(qs);
			
			if (StringUtils.isNotEmpty(qs)) {
				url.append("?").append(qs);
			}
			logger.info("Authenticated : Redirect to - " + url.toString());
			sendRedirect(pResponse, url.toString());
			return false;
		}
		
		return true;
	}
	
	protected boolean unsuccessfulAuthentication(HttpServletRequest pRequest, HttpServletResponse pResponse) throws Exception {
		logger.debug("Authentication failed: " + pRequest.getRequestURL());
		String fp = pRequest.getRequestURL().toString();
		String absoluteContext = fp.replace(pRequest.getRequestURI(), "") + pRequest.getContextPath();
		String fail = failureUrl;
		if (!fail.startsWith("http")) {
			if (!fail.startsWith("/")) {
				fail = "/" + fail;
			}
			fail = absoluteContext + fail;
		}
		sendRedirect(pResponse, fail);
		
		return false;
	}

	@SuppressWarnings("unchecked")
	private void clearSession(HttpServletRequest pRequest) {
		HttpSession session = pRequest.getSession(false);
		if (session != null) {
			logger.debug("Clearing the current session ...");
			Enumeration<String> names = session.getAttributeNames();
			while(names.hasMoreElements()) {
				session.removeAttribute(names.nextElement());
			}
		}
	}

	public SSOAuthenticator getSsoAuthenticator() {
		return this.ssoAuthenticator;
	}

	public void setSsoAuthenticator(SSOAuthenticator pSsoAuthenticator) {
		this.ssoAuthenticator = pSsoAuthenticator;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
		
	}
	
	
	private List<NVPair> splitQuery(String query) throws UnsupportedEncodingException {
	    List<NVPair> queryPairs = new ArrayList<NVPair>();
	    if (StringUtils.isBlank(query)) {
	    	return queryPairs;
	    }

	    StringTokenizer pairs = new StringTokenizer(query, "&");
	    while(pairs.hasMoreTokens()) {
	    	String pair = pairs.nextToken();
	    	String nv[] = pair.split("=", 2);
	    	String k = URLDecoder.decode(nv[0], "UTF-8");
	    	String v = "";
	    	if (nv.length > 1) {
	    		v = URLDecoder.decode(nv[1], "UTF-8");
	    	}
	        queryPairs.add(new NVPair(k,v));
	    }
	    return queryPairs;
	}
	
	private String stripSsoParams(String queryString) throws UnsupportedEncodingException  {

		List<NVPair> nvPairs = splitQuery(queryString);
		if (nvPairs.isEmpty()) {
			return "";
		}
			
		StringBuffer qs = new StringBuffer();
		String prefix = "";
		for (NVPair nv : nvPairs) {
			String name = nv.getName();
			String val = nv.getValue();
			if (! SSO_ET_PARAM.equals(name) && ! AUTO_LOGIN_PARAM.equals(name) ) {
				qs.append(prefix)
					.append(URLEncoder.encode(name, "UTF-8"))
					.append("=")
					.append(URLEncoder.encode(val, "UTF-8"));
				prefix = "&";
			}
		}

		return qs.toString();
	}
	
	private void sendRedirect(HttpServletResponse pResponse, String url) throws IOException {
		pResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		pResponse.setHeader("Pragma", "no-cache");
		pResponse.setDateHeader("Expires", 0);
		pResponse.sendRedirect(url);
	}
	
	class NVPair {
		String name;
		String value;
		public NVPair() {}
		public NVPair(String name, String value) {
			this.name = name;
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
}
