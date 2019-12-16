package com.bomwebportal.ims.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ImsIsCurrentTabController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
	HttpServletResponse arg1) throws Exception {

		String ImsIsProcessing = (String) request.getSession().getAttribute(
				"ImsIsProcessing");
		
		String referer = request.getParameter("referer");
		String action = request.getParameter("action");
		
		// for release tab lock
		if("release".equals(action)){
			logger.debug("release ims flow lock");
			String header_referer = request.getHeader("referer");
			logger.debug("header_referer:"+header_referer);
			if(header_referer!=null && header_referer.indexOf("BomWebPortal/ims") > 0){
				request.getSession().setAttribute("ImsIsProcessing", null);
				logger.debug("ims flow lock released");
			}			
			return null;
		}
		
		// for create tab lock		
		JSONArray jsonArray = new JSONArray();
		if (StringUtils.equals(ImsIsProcessing, "true")) {

			logger.info("Ongoing process detected");

			//String referrer = request.getHeader("referer");

			logger.info("referer: " + referer);
			
			if(referer!=null){
				
				if (referer.indexOf("BomWebPortal/ims") > 0) {

					jsonArray.add(
					"{\"valid\":\"" + "Y" + "\"}");

				} else {

					jsonArray.add(
					"{\"valid\":\"" + "N" + "\"}");

				}
				
			}else {

				jsonArray.add(
				"{\"valid\":\"" + "N" + "\"}");

			}			
		} else {

			logger.info("New tab page with new process detected");

			request.getSession().setAttribute("ImsIsProcessing", "true");

			jsonArray.add(
			"{\"return\":\"" + "Y" + "\"}");

		}		
		return new ModelAndView("ajax_addrLookup", "jsonArray", jsonArray);

	}

}
