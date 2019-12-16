package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.service.ImsSyncOrderService;

public class ImsSyncOrderToBomController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private static boolean isProcessing;
	
	private ImsSyncOrderService service;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("ImsSyncOrderToBom request recieved");
		
		JSONArray jsonArray = new JSONArray();		
		
		if(!isProcessing){			
			try{
				isProcessing = true;				
				service.syncOrderToBOM();
				logger.info("ImsSyncOrderToBom request completed");
				jsonArray.add(
						"{\"return\":\"" + "success" +				
						"\"}");
			}catch(Exception e){			
				throw new ServletException(e.getMessage());			
			}finally{				
				isProcessing = false;
			}			
		}else{
			jsonArray.add(
					"{\"return\":\"" + "previous request is processing" +				
					"\"}");
		}	
		
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		
	}

	public ImsSyncOrderService getService() {
		return service;
	}

	public void setService(ImsSyncOrderService service) {
		this.service = service;
	}

}
