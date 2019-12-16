package com.bomwebportal.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.UserTimeoutException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;

public class CommonInterceptor extends HandlerInterceptorAdapter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private BomPropertyPlaceholderConfigurer propertyConfigurer;

	private LoginService service;

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}
	
    private BomWebPortalApplicationFlow appFlow;
    
	public BomWebPortalApplicationFlow getAppFlow() {
		return appFlow;
	}
	
	public void setAppFlow(BomWebPortalApplicationFlow appFlow) {
		this.appFlow = appFlow;
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UserTimeoutException, Exception {
		
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
		
		if (request.getServletPath().indexOf("ltsdebug") >= 0) {
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
				|| request.getServletPath().indexOf("/pd/processRequest") >= 0
				) {
			return true;
		}
		/*
		 * Raymond Ho
		 */		
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
			logger.info("IMS batch request url:"+request.getRequestURL());
			return true;
		}

		if(!StringUtils.isEmpty((String)request.getSession().getAttribute("IMS_SSO_BYPASS"))){
			return true;
		}
		
		/* test uid*/
		String parameterUid="";
		String sessionUid ="";
		
		parameterUid=(String)request.getParameter("sbuid");
		sessionUid =(String)request.getSession().getAttribute("sbuid");
		boolean checkUid = false;
		
		List<CodeLkupDTO> checkUidCdLkupList
			= LookupTableBean.getInstance().getCodeLkupList().get("CHECK_UID");
		
		if (checkUidCdLkupList != null
				&& !checkUidCdLkupList.isEmpty()) {
			checkUid = "Y".equalsIgnoreCase(checkUidCdLkupList.get(0).getCodeDesc()) ?
						true : false;
		} else {
			checkUid = false;
		}
		
		if (checkUid) {
			if(request.getServletPath().indexOf("welcome") >= 0 || request.getServletPath().indexOf("ceks") >= 0 ){
				logger.info("skip uid check page:"+request.getRequestURL());
			}else{
				
				if(StringUtils.isNotBlank(sessionUid) ){
					
					if(StringUtils.isNotBlank(parameterUid)){
						if(!sessionUid.equalsIgnoreCase(parameterUid)){
							
							BomSalesUserDTO user = new BomSalesUserDTO();				
							request.setAttribute("bomsalesuser", user);
							request.getSession().invalidate();
							throw new UserTimeoutException("Invalid operation found (e.g. multiple order sessions), please login again to proceed.");

						}
					}
			
				}
				
			}
		}
		
		/* test uid*/
		
		
		String uri = request.getRequestURI();
		String appMode = (String)request.getSession().getAttribute("appMode");
		if(appMode==null){
			appMode = request.getParameter("appMode");
		}
		if(appMode==null){
			appMode = appFlow.getDefaultAppMode();
		}
		//System.out.println(appMode);
//		logger.info("Application Mode: " + appMode);
		String currentView = request.getParameter("currentView");
		String nextView = appFlow.getNextView(appMode, currentView);
		//logger.info("Current View: " + currentView);
		request.setAttribute("nextView", nextView+".html");
		//logger.info("Next View: "+ nextView);
		
		/*********************************************/
		//String sessionId = request.getSession().getId();
		//logger.info("Session is new? : " + request.getSession().isNew());
		//logger.info("Session ID: " + sessionId);
		/*********************************************/
		
		//if(appFlow.getDefaultAppMode().equals(appMode)&&currentView!=null&&!"login".equals(currentView)){
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		/*********************************************/
//		logger.info("Application Mode: " + appMode);
//		logger.info("bomSalesUser object in Session : " + ((bomSalesUser==null)?"NULL":bomSalesUser.getUsername()));
//		logger.info("uri: " + uri);
		/*********************************************/
		
		//Multiple Logins not allowed. add by wilson, 20120718
		String appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
//		logger.info("Environment: " + appEnv);
		String singleLoginInd = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+"singleLogin");
//		logger.info("singleLoginInd: " + singleLoginInd);
		logger.debug("Application Mode: " + appMode+
				"bomSalesUser object in Session : " + ((bomSalesUser==null)?"NULL":bomSalesUser.getUsername())+
				"uri: " + uri+
				"Environment: " + appEnv+
				"singleLoginInd: " + singleLoginInd);

		if("Y".equalsIgnoreCase(singleLoginInd) && bomSalesUser!=null&&!uri.contains("login.html")&&!uri.contains("imskeepalive.html")
				&&!("P".equalsIgnoreCase(bomSalesUser.getUsername().substring(0, 1))&&("DS6".equalsIgnoreCase(bomSalesUser.getChannelCd())))){
			String username = bomSalesUser.getUsername();
			logger.debug("validating sessionId for user " + username);
			if (!validateSessionId(request, username)) {
				logger.info("Invalid sessionId for user " + username);
				BomSalesUserDTO user = new BomSalesUserDTO();				
				request.setAttribute("bomsalesuser", user);
				List<String> shopCdList = new ArrayList<String>();
				shopCdList= service.getShopList();
				request.setAttribute("shopCdList", shopCdList);
				request.getSession().invalidate();
				throw new UserTimeoutException("Multiple Logins not allowed.");
			}
			
			
		}
		if (bomSalesUser == null && !uri.contains("login.html")) {

			BomSalesUserDTO user = new BomSalesUserDTO();
			request.setAttribute("bomsalesuser", user);

			// List<String> shopCdList = new ArrayList<String>();
			// shopCdList= service.getShopList();
			// request.setAttribute("shopCdList", shopCdList);
			throw new UserTimeoutException("Session timeout.Please login again.");

		}

		return true;
	}

	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}
	
	private boolean validateSessionId(HttpServletRequest request, String username) {
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		String dbSessionId= service.getDbRecordSessionId(username);
		
		return sessionId.equals(dbSessionId);
	}

	

}