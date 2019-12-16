package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.dto.CustInfoDTO;
import com.bomwebportal.ims.service.CustInfoService;

public class CustInfoSearchController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustInfoService custservice;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String iddocno = request.getParameter("ID");
		
		List<CustInfoDTO> list = custservice.searchCustInfo(iddocno);
		
		JSONArray jsonArray = new JSONArray();
		
		for(int i=0; i<list.size(); i++){
			
			CustInfoDTO cust = list.get(i);
			
			jsonArray.add("{\"custnb\":\"" + cust.getCustNo()
					+ "\",\"title\":\""	+ cust.getTitle()
					+ "\",\"firstname\":\""	+ cust.getFirstName()
					+ "\",\"lastname\":\""	+ cust.getLastName() 
					+ "\"}");
		}			
		/*
		jsonArray.add("{\"custnb\":\"" + "abc123"
				+ "\",\"title\":\""	+ "Mr."
				+ "\",\"firstname\":\""	+ "Ho"
				+ "\",\"lastname\":\""	+ "Raymond" + "\"}");*/
		
		return new ModelAndView("ajax_custinfosearch", "jsonArray",	jsonArray);
	}

	public CustInfoService getCustservice() {
		return custservice;
	}

	public void setCustservice(CustInfoService custservice) {
		this.custservice = custservice;
	}
	
	
	
}
