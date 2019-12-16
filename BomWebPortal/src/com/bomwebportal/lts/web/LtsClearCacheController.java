package com.bomwebportal.lts.web;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pccw.util.cache.ClearCacheService;

public class LtsClearCacheController extends SimpleFormController {

	private static String HTTP_PARM_BEAN_ID = "id";
	private ClearCacheService clearCacheService; 
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest pRequest,
			HttpServletResponse pResponse) throws Exception {
		
		int clearCacheCount = 0;
 	    ModelAndView modelAndView = new ModelAndView("ltsclearcache");
		try {
			String [] beanIds = pRequest.getParameterValues(HTTP_PARM_BEAN_ID);
			if (beanIds != null)  {
			    clearCacheCount = clearCacheService.clearCache(beanIds);					
			} else {
			    clearCacheCount = clearCacheService.clearCache();
			}
  		    modelAndView.addObject("message","Total no. of cache reset: " + clearCacheCount);
		} catch (Exception e) {
	  	   modelAndView.addObject("message", e.getMessage());			
		}
		
		return modelAndView;
	}

	public ClearCacheService getClearCacheService() {
		return clearCacheService;
	}

	public void setClearCacheService(ClearCacheService clearCacheService) {
		this.clearCacheService = clearCacheService;
	}




}
