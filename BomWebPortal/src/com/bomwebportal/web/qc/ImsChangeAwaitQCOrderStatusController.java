package com.bomwebportal.web.qc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.service.qc.ImsDSQCService;

public class ImsChangeAwaitQCOrderStatusController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsDSQCService imsDSQCService; 
	
	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("ImsChangeAwaitQCOrderStatusController handle Request: (start) ");
		
		String orderId = request.getParameter("orderId");
		
		int returnCode = 0;
		String result;
		
		try {
			returnCode = imsDSQCService.changeAwaitQCOrderStatus(orderId);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(returnCode == 1)
			result = "S";
		else
			result = "F";
		
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"result\":\""	+ result +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		logger.info("ImsChangeAwaitQCOrderStatusController handle Request: (end)");

		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}
}
