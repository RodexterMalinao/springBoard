package com.bomwebportal.sso.service;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bomwebportal.exception.BomWebPortalException;
import com.bomwebportal.sso.SSOAuthContext;
import com.pccw.paska.server.json.VerifyEtJson;

public class SSOAuthenticator {
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SSOService ssoService;
	
	private String ssoAppId;
	
	public SSOService getSsoService() {
		return ssoService;
	}

	public void setSsoService(SSOService ssoService) {
		this.ssoService = ssoService;
	}

	public String getSsoAppId() {
		return this.ssoAppId;
	}

	public void setSsoAppId(String pSsoAppId) {
		this.ssoAppId = pSsoAppId;
	}

	public SSOAuthContext authenticate(String ticket) throws BomWebPortalException {
		
		logger.debug("Authenticating with SSO Ticket:" + ticket);
		if(StringUtils.isEmpty(ticket) ){
			return null;
		} 
		

		SSOAuthContext authContext = null;
			
		VerifyEtJson vEtJson = ssoService.verifyEtCheck(this.ssoAppId, ticket);
		if(vEtJson != null && "RC_SUCC".equalsIgnoreCase(vEtJson.reply)){
			//if the entrance ticket is valid, set the returned usiHash to session
			
			logger.debug("set usihash to session[" + vEtJson.oUsiHash+"]");
			authContext = new SSOAuthContext(vEtJson.oUsrId, vEtJson.oUsiHash, vEtJson.iAppId, ticket);
			
			return authContext;
		}else{
			return null;
		}					
	}
	
	public boolean isAlive(SSOAuthContext ctx) {
		if (ctx == null) return false;
		
		boolean alive = ssoService.isAlive(this.ssoAppId, ctx.getHash());
		if (alive) ctx.touch();
		return alive;
	}
	
	
	// use the same as self appid ...
	public String createRejoinUrl(String target, String fail) throws UnsupportedEncodingException {
		return ssoService.createRejoinUrl(this.ssoAppId, target, fail);
	}
	
	// specify target appid
	public String createRejoinUrl(String targetAppId, String target, String fail) throws UnsupportedEncodingException {
		return ssoService.createRejoinUrl(targetAppId, target, fail);
	}
}
