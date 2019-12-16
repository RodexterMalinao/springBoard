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
import com.bomwebportal.mob.cos.service.MobCosCampaignService;

public class AddCampaignBasketAJAXController extends SimpleFormController{
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
			logger.debug("AddCampaignBasketAJAXController formBackingObject called");
		}
		
		String type = this.getStringValue(request, "type");
		JSONArray jsonArray = new JSONArray();
		logger.info("type:"+type);
		
		List<CodeLkupDTO> resultList = new ArrayList<CodeLkupDTO>();
		if ("getBundle".equals(type)) {
			String ratePlan = this.getStringValue(request, "ratePlan");
			if (ratePlan == null) ratePlan = "";
			resultList = mobCosCampaignService.getBundleList(ratePlan);
		} else if ("getContract".equals(type)) {
			String ratePlan = this.getStringValue(request, "ratePlan");
			String bundle = this.getStringValue(request, "bundle");
			if (ratePlan == null) ratePlan = "";
			if (bundle == null) bundle = "";
			resultList = mobCosCampaignService.getContractList(ratePlan, bundle);
		} else if ("getHandset".equals(type)) {
			String ratePlan = this.getStringValue(request, "ratePlan");
			String bundle = this.getStringValue(request, "bundle");
			String contract = this.getStringValue(request, "contract");
			if (ratePlan == null) ratePlan = "";
			if (bundle == null) bundle = "";
			if (contract == null) contract = "";
			resultList = mobCosCampaignService.getHandsetList(ratePlan, bundle, contract);
		} else if("getBasket".equals(type)) {
			String ratePlan = this.getStringValue(request, "ratePlan");
			String bundle = this.getStringValue(request, "bundle");
			String contract = this.getStringValue(request, "contract");
			if (ratePlan == null) ratePlan = "";
			if (bundle == null) bundle = "";
			if (contract == null) contract = "";
			resultList = mobCosCampaignService.getBasketList(ratePlan, bundle, contract);
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
