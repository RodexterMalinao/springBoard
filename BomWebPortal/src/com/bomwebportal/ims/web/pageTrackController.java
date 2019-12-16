package com.bomwebportal.ims.web;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.service.PageTrackService;
import com.google.gson.Gson;





public class pageTrackController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());

	Gson gson = new Gson();
	
	private PageTrackService pageTrackService;
	
	public PageTrackService getPageTrackService() {
		return pageTrackService;
	}
	public void setPageTrackService(PageTrackService pageTrackService) {
		this.pageTrackService = pageTrackService;
	}


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String ip = "";
		InetAddress inet = InetAddress.getLocalHost();
		ip = inet.getHostAddress();
		
		String page = request.getParameter("page");
		String func = request.getParameter("func");
		if("rolloutSearch".equals(func)){
			func = "Rollout search";
		}
		String staffId = request.getParameter("staffId");
		String address = request.getParameter("address");
		
		logger.info("ip : " + ip);
		logger.info("page : " + page);
		logger.info("func : " + func);
		logger.info("staffId : " + staffId);
		logger.info("address: " + address);
		
		int result = 0 ;		
		
		try {
			if(address==null){
				result = pageTrackService.insertPageTrack(ip, staffId, page, func);
			}else{
				pageTrackService.doPageTrackRolloutSearch(ip, staffId, page, func, address);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject obj = new JSONObject(); 
		obj.put("result", result);
			
		logger.info("JSONObject : " + obj);
		return new ModelAndView("ajax_view", "JSONObject", obj);
		
	}
}
