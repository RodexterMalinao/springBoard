package com.bomwebportal.sso.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bomwebportal.dto.AuthorizeDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.BomWebPortalException;
import com.bomwebportal.exception.DuplicateOrderException;
import com.bomwebportal.mob.ccs.dto.MappingLkupDTO;
import com.bomwebportal.mob.ccs.service.MappingLkupService;
import com.bomwebportal.service.AuthorizeService;

public class SSOAuthInterceptor extends SbSsoAuthInterceptorRedirectSsoFeLogin {

	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private AuthorizeService authorizeService;
	@Autowired
	private MappingLkupService mappingLkupService;	

	
	@Override
	public boolean shouldBypassThisRequest(HttpServletRequest request) {
		
		// simply copy from current common interceptor .......
		if (request.getServletPath().indexOf("pdfreport") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("mobcosreport") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("mobccsreport") >= 0) {
			return true;
		}

		if (request.getServletPath().indexOf("ltsreport") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("login.html") >= 0) {
			return true;
		}

		if (request.getServletPath().indexOf("signcapture") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("reportdownload") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("healthcheck") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("offercodereq.html") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("ltssysfassignment") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("ltsclearcache") >= 0) {
			return true;
		}

		if (request.getServletPath().indexOf("ltssendamendnote") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("ltsredirectsms") >= 0) {
			return true;
		}
		
		if (request.getServletPath().indexOf("ltsnotification") >= 0) {
			return true;
		}
		
		
		if (request.getServletPath().indexOf("getMemberBasicInfoWithMaskedID") >= 0 
				|| request.getServletPath().indexOf("/pd/doInstantEarnPoint") >= 0 
				|| request.getServletPath().indexOf("/pd/doInstantEarnReversePoint") >= 0 
				|| request.getServletPath().indexOf("/pd/processRequest") >= 0) {
			return true;
		}
				
		if(request.getServletPath().indexOf("imssyncorder") >= 0 ||
				request.getServletPath().indexOf("imssyncordertobom") >= 0 ||
				request.getServletPath().indexOf("imssyncorderfrombom") >= 0 ||
				request.getServletPath().indexOf("imscancelorder") >= 0 ||
			    request.getServletPath().indexOf("imsgetpendingorder") >= 0 ||
			    request.getServletPath().indexOf("imsorderamendment") >= 0 ||
			    request.getServletPath().indexOf("imssendamendnote") >= 0 ||
			    request.getServletPath().indexOf("imssysfassignment") >= 0 ||
			    request.getServletPath().indexOf("imssysfnowassign") >= 0 ||
				(request.getServletPath().indexOf("imsaddresslookup") >= 0 && request.getQueryString().indexOf("action=CloseAddressIndexFile") >= 0) ||
				(request.getServletPath().indexOf("imsaddresslookup") >= 0 && request.getQueryString().indexOf("action=CreateAddressIndexFile") >= 0)){
			logger.debug("IMS batch request url:"+request.getRequestURL());
			return true;
		}

		if(!StringUtils.isEmpty((String)request.getSession().getAttribute("IMS_SSO_BYPASS"))){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isUrlAllowSsoRejoin(String pUrl, HttpServletRequest pRequest) {
		return (pUrl.indexOf("orderamend") >= 0);
	}

	@Override
	public boolean isUrlAllowSsoFeLogin(String pUrl, HttpServletRequest pRequest) {
		return (pUrl.indexOf("orderamend") >= 0);
	}
	
	@Override
	public Map<String, List<AuthorizeDTO>> getAuthorizationInfo(
			BomSalesUserDTO pUser) {
		return authorizeService.getAuthorizeList(pUser.getUsername(), pUser.getCategory(), String.format("%02d", pUser.getChannelId()));
	}

	@Override
	protected void setupBomSession(HttpServletRequest pRequest) throws BomWebPortalException {
		BomSalesUserDTO user = (BomSalesUserDTO) pRequest.getSession().getAttribute("bomsalesuser");
		//channelCd ==> appMode
		String channelCd = user.getChannelCd();
		String appMode = "shop";
		
		MappingLkupDTO mappingLkupDTO = this.mappingLkupService.getMappingLkupDTO("MOB_APP_MODE", user.getChannelId() + "");
		if (mappingLkupDTO == null) {
			throw new DuplicateOrderException(
					"channelId appMode mapping error(w_mapping_lkup)" +
							" :Username=" + user.getUsername() +
							" :channelId=" + user.getChannelId() +
							"<br> Please contact Springboard support!!");
		}
		appMode = mappingLkupDTO.getMapTo();
		
		pRequest.getSession().setAttribute("appMode", appMode);
		pRequest.getSession().setAttribute("channelCd", channelCd);
		logger.debug("save appMode session: " + appMode);
	}

	public AuthorizeService getAuthorizeService() {
		return authorizeService;
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	public MappingLkupService getMappingLkupService() {
		return mappingLkupService;
	}

	public void setMappingLkupService(MappingLkupService mappingLkupService) {
		this.mappingLkupService = mappingLkupService;
	}


}