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

import com.bomwebportal.lts.service.LtsAlertMessageService;
import com.bomwebportal.lts.util.LtsConstant;

public class ImsAlertCountAjaxController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	private LtsAlertMessageService ltsAlertMessageService;
	
	public LtsAlertMessageService getLtsAlertMessageService() {
		return this.ltsAlertMessageService;
	}
	public void setLtsAlertMessageService(
			LtsAlertMessageService pLtsAlertMessageService) {
		this.ltsAlertMessageService = pLtsAlertMessageService;
	}
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		logger.info("ImsAlertCountAjaxController handle Request: ");
	
		String alertCounter ="0";
		String username = request.getParameter("username");
		
		logger.info("username : " + username);
		
		try {
			alertCounter = this.ltsAlertMessageService.getAlertCount(LtsConstant.ALERT_MSG_SRV_TYPE_IMS, username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"Result\":\""	+ alertCounter +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		logger.info("ImsDsAjaxController handle Request: (end)");
		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}
}
