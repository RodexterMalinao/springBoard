package com.bomwebportal.ims.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.dto.ImsAddressDTO;

import com.bomwebportal.address.AddressHelper;
import com.bomwebportal.dto.AddressDTO;
import com.bomwebportal.ims.service.AddressIndex2Service;
import com.bomwebportal.ims.service.ImsAddressSearchService;

import com.bomwebportal.ims.address.CreateAddressIndex;

import net.sf.json.JSONArray;

public class ImsAddressLookupController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());

	public static final int MAX_INPUT = 20;

    private AddressHelper addressHelper;
    
	public AddressHelper getAddressHelper() {
		return addressHelper;
	}
	public void setAddressHelper(AddressHelper addressHelper) {
		this.addressHelper = addressHelper;
	}

    private CreateAddressIndex createAddressIndex;
    
	public CreateAddressIndex getCreateAddressIndex() {
		return createAddressIndex;
	}
	public void setCreateAddressIndex(CreateAddressIndex createAddressIndex) {
		this.createAddressIndex = createAddressIndex;
	}

    private ImsAddressSearchService service;
    
	public ImsAddressSearchService getService() {
		return service;
	}
	public void setService(ImsAddressSearchService service) {
		this.service = service;
	}

    private AddressIndex2Service addrIdx2Service;
    
	public AddressIndex2Service getAddrIdx2Service() {
		return addrIdx2Service;
	}
	public void setAddrIdx2Service(AddressIndex2Service addrIdx2Service) {
		this.addrIdx2Service = addrIdx2Service;
	}


	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {

		 String action = request.getParameter("action");

		 //AddressDTO[] addrArray = AddressHelper.searchAddress(keyword);
/*		 ImsAddrHelperDTO[] addrArray = addressHelper.searchAddress(keyword, lob);
		 logger.debug("addrArray:" + addrArray.toString());	
		 List<ImsAddrHelperDTO> addrList = Arrays.asList(addrArray);	
		 logger.info("List of address : "+addrList.toString());
		 JSONArray jsonArray = JSONArray.fromObject(addrList);
		 logger.info("Json address : "+jsonArray.size());
*/		
		 
		 try {
			 if (action != null && action.equalsIgnoreCase("CloseAddressIndexFile")) {
				 logger.debug(">>Close Index File");
				 addressHelper.closeIndexFile();
				 JSONArray jsonArray = new JSONArray();
				 jsonArray.add(
							"{\"return\":\"" + "success" +				
							"\"}");
				 return new ModelAndView("ajax_addrLookup", "jsonArray", jsonArray);
			 }
		 } catch (Exception e) {
			 throw new ServletException(e);
		 }
		 
		 try {
			 if (action != null && action.equalsIgnoreCase("CreateAddressIndexFile")) {
				 logger.debug(">>Create Index File");
				 
				 String parm1 = request.getParameter("parm1");
				 logger.info("parm1 : " + parm1);
				 
				 String hostIP = request.getRemoteAddr();
				 if(request.getHeader("X-Forwarded-For")!=null&&!"".equals(request.getHeader("X-Forwarded-For"))){
				 	 hostIP=request.getHeader("X-Forwarded-For");
					 logger.info("use  X-Forwarded-For: "+ hostIP );
				 }
				 logger.info("hostIP : "+ hostIP );

				 JSONArray jsonArray = new JSONArray();
				 if (addrIdx2Service.validIPAddress(hostIP).equalsIgnoreCase("Y")) {
					 createAddressIndex.buildIndexFile();
					 //addressHelper.closeIndexFile();
					 jsonArray.add(
							 "{\"return\":\"" + "success" + "\"}");
				 } else {
					 jsonArray.add(
							 "{\"return\":\"" + "failure due to invalid hostIP" + "\"}");
				 }
				 return new ModelAndView("ajax_addrLookup", "jsonArray", jsonArray);
			 }
		 } catch (Exception e) {
			 throw new ServletException(e);
		 }
		 
		 String keyword = URLDecoder.decode(request.getParameter("keyword"),
			"UTF-8");
		 String type = request.getParameter("type");
		 if (type.equalsIgnoreCase("dist")&&!keyword.equalsIgnoreCase("null")){
			 type= "address";
			 keyword = keyword.substring(keyword.indexOf(" "));
		 }
		 
		 AddressDTO[] addrSBArray = addressHelper.imsSearchAddress(keyword,type);
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
			 addrArray = service.getAddressBySB(sbList);
		 } else {
			 addrArray = null;
		 }

		 logger.debug("SB List = " + testSBList);
		 JSONArray jsonArray = JSONArray.fromObject(addrArray);
		 logger.info("Json address : "+jsonArray.size());
		 
		 return new ModelAndView("ajax_addrLookup", "jsonArray", jsonArray);
	 }
}
