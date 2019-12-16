package com.bomwebportal.mob.cos.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignHdrDTO;
import com.bomwebportal.mob.cos.service.MobCosCampaignService;

public class MobCosCampaignAJAXController extends SimpleFormController{
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosCampaignService mobCosCampaignService;
	
	public MobCosCampaignService getMobCosCampaignService() {
		return mobCosCampaignService;
	}
	public void setMobCosCampaignService(MobCosCampaignService mobCosCampaignService) {
		this.mobCosCampaignService = mobCosCampaignService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MobCosCampaignAJAXController formBackingObject called");
		}
		
		String type = this.getStringValue(request, "type");
		JSONArray jsonArray = new JSONArray();
		logger.info("type:"+type);
		
		List<MobCosCampaignHdrDTO> resultList = new ArrayList<MobCosCampaignHdrDTO>();
		if("SearchCampaignList".equals(type)) {
			String campTitle = this.getStringValue(request, "campTitle").trim();
			String campName = this.getStringValue(request, "campName").trim();
			String handsetDesc = this.getStringValue(request, "handsetDesc").trim();
			
			if (campTitle == null) campTitle = "";
			if (campName == null) campName = "";
			if (handsetDesc == null) handsetDesc = "";
			
			if (campTitle.length() > 0 || campName.length() > 0 || handsetDesc.length() > 0) {
				resultList = mobCosCampaignService.getCampaignTitle(campTitle, campName, handsetDesc);
			}
		} else if ("GetResultOption".equals(type)) {
			String campId = this.getStringValue(request, "campId");
			resultList = mobCosCampaignService.getResultOption(campId);
		}
		jsonArray = JSONArray.fromObject(resultList);
		
		return new ModelAndView("ajax_mobCcsCommonLookup", "jsonArray",	jsonArray);	    
	}
	
	private String getStringValue(HttpServletRequest request, String name) {
		String value = "";
		try {
		    if(!"".equals((String)request.getParameter(name)) && (String)request.getParameter(name) != null){
		    	value = new String(request.getParameter(name));
		    }	    
		} catch (NumberFormatException nfe) {}
		
		return value;
	}
}
