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

import com.bomwebportal.ims.service.ImsSupportDocService;;


public class ImsSupportDocCollectedIndRefreshController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	private ImsSupportDocService imsSupportDocService;
	
	public ImsSupportDocService getImsSupportDocService() {
		return imsSupportDocService;
	}

	public void setImsSupportDocService(ImsSupportDocService imsSupportDocService) {
		this.imsSupportDocService = imsSupportDocService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String orderId = request.getParameter("orderId");
		String docType = request.getParameter("docType");
		
		String isCollected = "N";
		
		isCollected = imsSupportDocService.isSupportDocCollected(orderId, docType);
	
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("{\"isCollected\":\"" + isCollected+ "\"}");
		
		return new ModelAndView("ajax_imssupportdoccollectedindrefresh", "jsonArray", jsonArray);

	}
}
