package com.bomwebportal.web.ext;

import java.util.Locale;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AjaxViewResolver extends AbstractCachingViewResolver{

	protected final Log logger = LogFactory.getLog(getClass());
	
    private String ajaxPrefix;

    private View ajaxView;
    
	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {

	        logger.debug("viewName==>"+viewName);
	        View view = null;
	        if(viewName.startsWith(this.ajaxPrefix)){
	        	view = ajaxView;
	        }
	        return view;
	    }
	
	public String getAjaxPrefix() {
        return ajaxPrefix;
    }

    public void setAjaxPrefix(String ajaxPrefix) {
        this.ajaxPrefix = ajaxPrefix;
    }

    public View getAjaxView() {
        return ajaxView;
    }

    public void setAjaxView(View ajaxView) {
        this.ajaxView = ajaxView;
    }
}
