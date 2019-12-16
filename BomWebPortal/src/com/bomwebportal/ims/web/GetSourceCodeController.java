package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.GetSourceCodeService;

public class GetSourceCodeController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public GetSourceCodeService getGetSourceCodeService() {
		return getSourceCodeService;
	}


	public void setGetSourceCodeService(GetSourceCodeService getSourceCodeService) {
		this.getSourceCodeService = getSourceCodeService;
	}


	private GetSourceCodeService getSourceCodeService;


	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray = new JSONArray();
    	
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if( sessionOrder!=null ){	logger.info("ImsOrderID:"+sessionOrder.getOrderId());	};
		
		String AppMethod = (String) request.getParameter("AppMethod");
		logger.info("AppMethod:" + AppMethod);
		
		JSONObject jsonObject;
		
		
		
		/*if ( "CSS".equals(AppMethod) )
		{
			jsonObject = new JSONObject();
			jsonObject.put("code","A");
			jsonArray.element(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("code","B");
			jsonArray.element(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("code","C");
			jsonArray.element(jsonObject);
		}
		else if ( "CFO".equals(AppMethod) )
		{
			jsonObject = new JSONObject();
			jsonObject.put("code","1");
			jsonArray.element(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("code","2");
			jsonArray.element(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("code","3");
			jsonArray.element(jsonObject);
		}
		else if ( "HPM".equals(AppMethod) )
		{
			jsonObject = new JSONObject();
			jsonObject.put("code","I");
			jsonArray.element(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("code","II");
			jsonArray.element(jsonObject);
			
			jsonObject = new JSONObject();
			jsonObject.put("code","III");
			jsonArray.element(jsonObject);
		}*/
		
		//List<String> list = getSourceCodeService.getSourceCode(AppMethod);
		
		List<Map<String,String>> map = (List<Map<String,String>>)request.getSession().getAttribute("sourceCodeList");
		
		
		
		//for (String entry:list )
		for (Map<String, String> entry:map )
		{
			if (AppMethod.equals(entry.get("appMethodCode")))
			{
				jsonObject = new JSONObject();
				jsonObject.put("code", entry.get("sourceCodeValue"));
				jsonObject.put("label", entry.get("sourceCodeLabel"));
				jsonArray.element(jsonObject);
			}
		}
    	
		return new ModelAndView("ajax_getsourcecode", "jsonArray", jsonArray);
	}
}
