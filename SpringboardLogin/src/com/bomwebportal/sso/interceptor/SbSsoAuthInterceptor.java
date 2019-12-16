package com.bomwebportal.sso.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bomwebportal.dto.AuthorizeDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.BomWebPortalException;
import com.bomwebportal.exception.UserTimeoutException;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.sso.SSOAuthContext;

public abstract class SbSsoAuthInterceptor extends BasicSSOAuthInterceptor {

	private final Log logger = LogFactory.getLog(getClass());
	
	private LoginService loginService;
	
	protected boolean successfulAuthentication(HttpServletRequest pRequest, HttpServletResponse pResponse, SSOAuthContext pAuthContext) throws Exception {
		logger.debug("Authentication success: " + pAuthContext.getUserId());
		setupBomUser(pRequest, pAuthContext);
		setupBomSession(pRequest);
		return super.successfulAuthentication(pRequest, pResponse, pAuthContext);
	}
	
	protected boolean unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		BomSalesUserDTO bomSalesUserDTO = null;
		if (session != null) {
			session.invalidate();
			bomSalesUserDTO = (BomSalesUserDTO)session.getAttribute("bomsalesuser");
		}
		throw new UserTimeoutException("Your session has expired or you are not authorized to access this page. UserID:[" + (bomSalesUserDTO != null ? bomSalesUserDTO.getUsername() : "") + "]");
	}
	
	public abstract boolean shouldBypassThisRequest(HttpServletRequest pRequest);
	
	private boolean setupBomUser(HttpServletRequest pRequest, SSOAuthContext pAuthContext) {

		BomSalesUserDTO bomSalesUserDTO = new BomSalesUserDTO();
		bomSalesUserDTO.setUsername(pAuthContext.getUserId());
		
		//update db session record
		String remoteAddress = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		if (logger.isInfoEnabled()) {
			logger.info("bomSalesUserDTO.getUsername():" + bomSalesUserDTO.getUsername());
			logger.info("remoteAddress:" + remoteAddress);
			logger.info("sessionId:" + sessionId);
		}
		if (loginService != null) {
			loginService.updateSessionId(bomSalesUserDTO.getUsername(),  sessionId,  remoteAddress);
		}

		BomSalesUserDTO loginedUser = loginService.getSalesAssign(bomSalesUserDTO.getUsername());
		pRequest.getSession().setAttribute("bomsalesuser", loginedUser);
		
		RequestContextHolder
				.currentRequestAttributes()
				.setAttribute("authorizationInfo", this.getAuthorizationInfo(loginedUser), RequestAttributes.SCOPE_GLOBAL_SESSION);
		
		return true;
	}
	
	public abstract Map<String, List<AuthorizeDTO>> getAuthorizationInfo(BomSalesUserDTO pLoginedUser);
	
	protected abstract void setupBomSession(HttpServletRequest pRequest) throws BomWebPortalException;

	public LoginService getLoginService() {
		return this.loginService;
	}

	public void setLoginService(LoginService pLoginService) {
		this.loginService = pLoginService;
	}
}