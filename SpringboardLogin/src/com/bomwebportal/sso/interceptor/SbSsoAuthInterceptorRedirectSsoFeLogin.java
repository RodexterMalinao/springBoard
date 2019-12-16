package com.bomwebportal.sso.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.UserTimeoutException;

public abstract class SbSsoAuthInterceptorRedirectSsoFeLogin extends SbSsoAuthInterceptor {

	@Override
	protected boolean unsuccessfulAuthentication(HttpServletRequest pRequest,
			HttpServletResponse pResponse) throws Exception {
		
		HttpSession session = pRequest.getSession(false);
		BomSalesUserDTO bomSalesUserDTO = null;
		if (session != null) {
			bomSalesUserDTO = (BomSalesUserDTO)session.getAttribute("bomsalesuser");
			session.invalidate();
		}
		
		boolean isUrlAllowSsoRejoin = isUrlAllowSsoRejoin(pRequest.getRequestURL().toString(), pRequest);
		boolean isUrlAllowSsoFeLogin = isUrlAllowSsoFeLogin(pRequest.getRequestURL().toString(), pRequest);
		
		if (!(isUrlAllowSsoRejoin || isUrlAllowSsoFeLogin)) {
			throw new UserTimeoutException("Your session has expired or you are not authorized to access this page. UserID:[" + (bomSalesUserDTO != null ? bomSalesUserDTO.getUsername() : "") + "]");
		}
		
		sendToSSORejoin(pRequest, pResponse, isUrlAllowSsoFeLogin);
		return false;
	}

	public abstract boolean isUrlAllowSsoRejoin(String pUrl, HttpServletRequest pRequest);
	
	public abstract boolean isUrlAllowSsoFeLogin(String pUrl, HttpServletRequest pRequest);
	
}