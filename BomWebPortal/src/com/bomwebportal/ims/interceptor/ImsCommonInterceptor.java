package com.bomwebportal.ims.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.ImsMultiTabException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class ImsCommonInterceptor extends HandlerInterceptorAdapter{
	
	protected final Log logger = LogFactory.getLog(getClass());
	public static final String IMS_UID = "IMS_UID";
	
	private String appEnv;
	
	private static final Map<String, Double> webFlowMap = new HashMap<String, Double>(){
        {
            put("imsaddressinfo", 1.0);
            put("imsdsaddressinfo", 1.0);
            put("imsbasketselect", 2.0);
            put("imsbasketdetails", 3.0);
            put("imsnowtv", 3.5);
            put("imsinstallation", 4.0);
            put("imspayment", 5.0);
            put("imsappointment", 6.0);
            put("imssummary", 7.0);
            put("imsdone", 8.0);            
        }
    };
    
    public static final String IMS_ORDER_TYPE_DS = "DS";
    
    public static final List<Integer> directSalesRoles = new ArrayList<Integer>(){{
        add(12);
        add(13);
    }};
	
    public static final List<String> clearSessionCurrentViews = new ArrayList<String>(){{
        add("imsorderdetail");
    }};
    
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{								
		//steven added, requested by raymond - DS mobile number in header 20141113 start
		if(!"dev".equals(appEnv)){
//	    	logger.info("ImsCommonInterceptor preHandle is called, url:"+request.getRequestURL());
			if(request.getScheme().equals("http") && !request.isSecure() &&
					request.getRequestURL().indexOf("localhost")==-1 &&
					request.getRequestURL().toString().contains("welcome.html")){//not https and not local
		    	logger.debug("not https, not local, and welcome page");
		    	String httpsRedirect = request.getParameter("isRedirect");
				String header = request.getHeader("X-FH-MSISDN");
		    	logger.info("request.getHeader(X-FH-MSISDN), mob num:"+header);
				request.getSession().setAttribute(ImsConstants.IMS_DS_MOB_NUM , header);
		    	logger.info("check session mob num:"+request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM));
		    	if (StringUtils.isEmpty(httpsRedirect)) {
					//redirect to https
			    	logger.info("Redirect to https:"+request.getRequestURL().toString().replace("http://", "https://"));
				    response.sendRedirect(request.getRequestURL().toString().replace("http://", "https://"));
				    return false;
		    	} else {
		    		return true;
		    	}
			}
		}
		//steven added, requested by raymond - DS mobile number in header 20141113 end
		
		String sUID = (String)request.getSession().getAttribute(IMS_UID);
		String rUID = (String)request.getParameter(IMS_UID);
		
		logger.debug("preHandle UID (SESSION):"+sUID);		
		logger.debug("preHandle UID (REQUEST):"+rUID);
		
		if(rUID!=null && !rUID.equals(sUID)){
			throw new ImsMultiTabException();
		}
		
		//////////////// jacky development ///////////////////////
		if(request.getSession().getAttribute(ImsConstants.IMS_ORDER) != null){
			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
			request.getSession().setAttribute(ImsConstants.IMS_DIRECT_SALES , IMS_ORDER_TYPE_DS.equals(order.getImsOrderType())?true:false);
		}
		else if((BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser") != null) {
			request.getSession().setAttribute(ImsConstants.IMS_DIRECT_SALES , directSalesRoles.contains(((BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser")).getChannelId())?true:false);
		}
		else request.getSession().setAttribute(ImsConstants.IMS_DIRECT_SALES , false);
		
		
		
		if(request.getServletPath().length()>3 && 
				request.getServletPath().substring(1, 4).equalsIgnoreCase("ims")){
			
			logger.debug("****************************jacky development session obj*********************************");
			HttpSession session = request.getSession();
			Enumeration<String> e = session.getAttributeNames();
			//steven clear the log
			HashMap<String, Object> map= new  HashMap<String, Object>();
	    	while(e.hasMoreElements()){  
	    		String key = (String)e.nextElement();
	    	    map.put(key , session.getAttribute(key));
//	    		logger.debug(key + " : " + new Gson().toJson(session.getAttribute(key)));
	    	}
    		logger.debug(new Gson().toJson(map));
	    	logger.debug("****************************jacky development session obj(end)*********************************");
			//////////////// jacky development(end) ///////////////////////
			
			
			logger.debug("ims url detected url "+request.getServletPath());
						
			String nextView = request.getServletPath();
			nextView = nextView.replace("/", "").replace(".html", "");												
			if(clearSessionCurrentViews.contains(nextView)) {	//jacky 20141216
				request.getSession().setAttribute(ImsConstants.IMS_CURRENT_VIEW, null);
				request.getSession().setAttribute(ImsConstants.IMS_CURRENT_URL, null);
			}
			
			if(webFlowMap.containsKey(nextView)){				
				
				OrderImsUI order = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
				String currentView = (String)request.getSession().getAttribute(ImsConstants.IMS_CURRENT_VIEW);	
				
				if(!checkFlow(currentView, nextView, order.getOrderActionInd())){
					logger.info("invalid ims flow nextview-"+nextView+" currentview-"+currentView+" mode-"+order.getOrderActionInd());
					String redirectUrl = "welcome.html";					
					
					if(request.getSession().getAttribute(ImsConstants.IMS_CURRENT_URL)!=null){
						redirectUrl = (String)request.getSession().getAttribute(ImsConstants.IMS_CURRENT_URL);
					}
					logger.info("redirect to:"+redirectUrl);
					response.sendRedirect(redirectUrl);
					
					return false;
					
				}else{															
				
					request.getSession().setAttribute(ImsConstants.IMS_CURRENT_VIEW, nextView);
					request.getSession().setAttribute(ImsConstants.IMS_CURRENT_URL, nextView+".html"+
							((request.getQueryString()!=null)?"?"+request.getQueryString():"")
							);
					
					return true;
					
				}
				
			}else{
				return true;
			}
																	
		}else{
			return true;
		}
		
	}
	
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		String sUID = (String)request.getSession().getAttribute(IMS_UID);
		
		logger.debug("postHandle UID (SESSION):"+sUID);
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){
			sUID = sessionOrder.getUID();
			request.getSession().setAttribute(IMS_UID, sUID);
		}
		
		request.setAttribute(IMS_UID, sUID);
		
	}
	
	private boolean checkFlow(String currentView, String nextView, String mode){
		
		logger.debug("###debug currentView : " + currentView + "nextView : " + nextView); 
		
		Double current = 0.0;
		if(currentView!=null){
			current = webFlowMap.get(currentView);
		}
		
		Double next = webFlowMap.get(nextView);
		
		if(mode==null || mode.equals("")){
			if(next-current>1){
				return false;
			}
		}else if(mode.equals("W")){
			if(next-current>1 && next!=5.0){
				return false;
			}
		}else if(mode.equals("R")){
			if(next!=8.0){
				return false;
			}
		}else if(mode.equals("C")){
			if(next!=8.0){
				return false;
			}
		}
		
		return true;
		
	}

	public void setAppEnv(String appEnv) {
		this.appEnv = appEnv;
	}

	public String getAppEnv() {
		return appEnv;
	}
		
}
