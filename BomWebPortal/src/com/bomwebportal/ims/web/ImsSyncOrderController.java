package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsSyncOrderService;

public class ImsSyncOrderController implements Controller{
	
	private ImsSyncOrderService syncOrderService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String orderid = request.getParameter("orderid");
		
		JSONArray jsonArray = new JSONArray();
		
		try{
			
			syncOrderService.syncOrderToBOM(orderid);
			
			jsonArray.add(
					"{\"return\":\"" + "success" +				
					"\"}");
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			jsonArray.add(
					"{\"return\":\"" + "fail" +				
					"\"}");
			
		}						
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
	}

	public ImsSyncOrderService getSyncOrderService() {
		return syncOrderService;
	}

	public void setSyncOrderService(ImsSyncOrderService syncOrderService) {
		this.syncOrderService = syncOrderService;
	}
	
	

}
