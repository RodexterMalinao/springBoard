package com.bomltsportal.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomltsportal.dao.ClearCacheDAO;
import com.pccw.util.cache.ClearCacheService;

public class ClearCacheController extends SimpleFormController {

	private static String HTTP_PARM_BEAN_ID = "id";
	private static String HTTP_PARM_PASSWORD = "p";
	
	private ClearCacheService clearCacheService;
	private ClearCacheDAO clearCacheDao;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest pRequest, HttpServletResponse pResponse) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView("clearcache");
				
		if (!StringUtils.equals(pRequest.getParameter(HTTP_PARM_PASSWORD), this.clearCacheDao.getRefreshCachePassword())) {
			modelAndView.addObject("message", "Invalid password");
		} else {
			int clearCacheCount = 0;
 	    
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
		}
		return modelAndView;
	}

	public ClearCacheService getClearCacheService() {
		return clearCacheService;
	}

	public void setClearCacheService(ClearCacheService clearCacheService) {
		this.clearCacheService = clearCacheService;
	}

	public ClearCacheDAO getClearCacheDao() {
		return clearCacheDao;
	}

	public void setClearCacheDao(ClearCacheDAO clearCacheDao) {
		this.clearCacheDao = clearCacheDao;
	}
}
