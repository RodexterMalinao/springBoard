package com.bomwebportal.mob.cos.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;






import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.cos.dto.MobCosChangeInboundDTO;
import com.bomwebportal.mob.cos.dto.MobCosStaffListDTO;
import com.bomwebportal.mob.cos.service.MobCosChangeInboundService;


public class MobCosChangeInboundAJAXController extends SimpleFormController {


	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosChangeInboundService mobCosChangeInboundService;
	String channelCd = "";

	public MobCosChangeInboundService getMobCosChangeInboundService() {
		return mobCosChangeInboundService;
	}

	public void setMobCosChangeInboundService(
			MobCosChangeInboundService mobCosChangeInboundService) {
		this.mobCosChangeInboundService = mobCosChangeInboundService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MobCosChangeInboundAJAXController formBackingObject called");
		}
		String selectedCenterId = this.getStringValue(request, "selectedCenterId");
		JSONArray jsonArray = new JSONArray();
		List<String> team = new ArrayList<String>();
        BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
        channelCd = bomsalesuser.getChannelCd();
		team=mobCosChangeInboundService.getTeamListByCenterId(selectedCenterId,channelCd);
			jsonArray = JSONArray.fromObject(team);
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
