package com.bomwebportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.service.MnpService;

import bom.mob.schema.javabean.si.xsd.GetCurrentDNODTO;

public class MrtAJAXController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private MnpService mnpService;
	public MnpService getMnpService() {
		return mnpService;
	}
	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String msisdn = request.getParameter("msisdn");
		JSONArray jsonArray = new JSONArray();
		
		if (StringUtils.isNotEmpty(msisdn)){
			GetCurrentDNODTO result = mnpService.getCurrDNODTO(msisdn);
			if (result!=null){
				jsonArray.add(result.getDno());
			}else{
				jsonArray.add("ERROR");
			}
			
		}else{
			jsonArray.add("ERROR");
		}
		
		/*if (StringUtils.isNotEmpty(msisdn)) {
			String result = mnpService.getDno(msisdn);
			jsonArray.add(result);
			logger.info("jsonArray : " + jsonArray);
		}else{
			jsonArray.add("ERROR");
		}*/

		return new ModelAndView("ajax_csportal", "jsonArray",	jsonArray);
	}



	

}
