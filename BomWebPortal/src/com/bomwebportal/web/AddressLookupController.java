package com.bomwebportal.web;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.*;
import com.bomwebportal.address.*;

import net.sf.json.JSONArray;

public class AddressLookupController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());

    private AddressHelper addrHelper;
    
	public AddressHelper getAddrHelper() {
		return addrHelper;
	}

	public void setAddrHelper(AddressHelper addrHelper) {
		this.addrHelper = addrHelper;
	}



	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
		 
		 String keyword = request.getParameter("keyword");
		 //AddressDTO[] addrArray = AddressHelper.searchAddress(keyword);
		 AddressDTO[] addrArray = addrHelper.searchAddress(keyword);
		 logger.info("keyword:" + keyword);	
		 List<AddressDTO> addrList = Arrays.asList(addrArray);	
		 //logger.info("List of address : "+addrList.size());
		 JSONArray jsonArray = JSONArray.fromObject(addrList);
		
		 return new ModelAndView("ajax_addrLookup", "jsonArray", jsonArray);
	 }
}
