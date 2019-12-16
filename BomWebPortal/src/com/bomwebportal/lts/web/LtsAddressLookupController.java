package com.bomwebportal.lts.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.address.AddressHelper;
import com.bomwebportal.dto.AddressDTO;
import com.bomwebportal.ims.dto.ImsAddressDTO;
import com.bomwebportal.lts.service.LtsAddressSearchService;

public class LtsAddressLookupController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());

	public static final int MAX_INPUT = 20;

    private AddressHelper addressHelper;
    
	public AddressHelper getAddressHelper() {
		return addressHelper;
	}
	public void setAddressHelper(AddressHelper addressHelper) {
		this.addressHelper = addressHelper;
	}

    private LtsAddressSearchService service;

	public LtsAddressSearchService getService() {
		return service;
	}
	public void setService(LtsAddressSearchService service) {
		this.service = service;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {

//		 String action = request.getParameter("action");

		 String keyword = URLDecoder.decode(request.getParameter("keyword"),
			"UTF-8");
		 String type = request.getParameter("type");
//		 if (type.equalsIgnoreCase("dist")&&!keyword.equalsIgnoreCase("null")){
//			 type= "address";
//			 keyword = keyword.substring(keyword.indexOf(" "));
//		 }
		 
		 AddressDTO[] addrSBArray = addressHelper.ltsSearchAddress(keyword);
		// AddressDTO[] addrSBArray = addressHelper.searchAddress(keyword);
		 String[] sbList = new String[MAX_INPUT];
		 logger.debug("sb cnt=" + addrSBArray.length);
		 String testSBList = "";
		 List<ImsAddressDTO> addrArray;
		 int sbCnt = ((addrSBArray.length > MAX_INPUT)?MAX_INPUT:addrSBArray.length);
		
		 if (addrSBArray.length > 0) {
			 for (int i=0; i < sbCnt; i++) {
				 sbList[i] = addrSBArray[i].getServiceBoundaryNum();
				 if (i == (sbCnt-1)) {
					 testSBList = testSBList + "'" + addrSBArray[i].getServiceBoundaryNum() + "'";
				 } else {
					 testSBList = testSBList + "'" + addrSBArray[i].getServiceBoundaryNum() + "',";
				 }
			 }
			 for (int i=sbCnt; i < MAX_INPUT; i++) {
				 sbList[i] = "";
			 }
			 
			 logger.debug("SB list string =" + testSBList);
			 addrArray = service.getAddressBySB(sbList, StringUtils.equals(type, "includeLtsOnly"));
		 } else {
			 addrArray = null;
		 }

		 logger.debug("SB List = " + testSBList);
		 JSONArray jsonArray = JSONArray.fromObject(addrArray);
		 logger.info("Json address : "+jsonArray.size());
		 
		 return new ModelAndView("ajax_addrLookup", "jsonArray", jsonArray);
	 }
}
