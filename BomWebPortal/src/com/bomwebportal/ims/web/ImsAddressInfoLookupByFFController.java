package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.ImsAddressInfo2Service;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.UimBlockageDTO;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import java.util.List;

public class ImsAddressInfoLookupByFFController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();

	
    private ImsAddressInfoService service;
    
	public void setService(ImsAddressInfoService service) {
		this.service = service;
	}
	public ImsAddressInfoService getService() {
		return service;
	}
    
    private ImsAddressInfo2Service service2;
    
	public void setService2(ImsAddressInfo2Service service2) {
		this.service2 = service2;
	}
	public ImsAddressInfo2Service getService2() {
		return service2;
	}
    
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		
		String sbNum = request.getParameter("SBNUM");
		String floor = request.getParameter("FLOOR");
		String unitNo = request.getParameter("UNITNO");
		String phInd = request.getParameter("PHIND");
		logger.debug("input=" + sbNum + floor + unitNo + phInd);
		
		UimBlockageDTO blockage = new UimBlockageDTO();
		blockage = service.getFiberBlockage(sbNum, floor, unitNo);
		
		List<UimBlockageDTO> blockageList = new ArrayList<UimBlockageDTO>();
		blockageList = service.getFiberBlockageByFloor(sbNum, floor);
		
		String blacklistAddr;
		if (phInd.equalsIgnoreCase("Y")) {
			blacklistAddr = service.getBlacklistAddrForPH(sbNum, floor, unitNo);
		} else {
			blacklistAddr = service.getBlacklistAddr(sbNum, floor);
		}
		
		List<String> osOrderList = new ArrayList<String>();
		osOrderList = service.getOsOrder(sbNum, floor, unitNo);
		
		List<String> osOrderList2 = new ArrayList<String>();
		osOrderList2 = service2.getOsOrderSB(sbNum, floor, unitNo);

		String txtWarning3 = "";
		
		if (osOrderList != null){
			for (int i=0; i < osOrderList.size(); i++) {
				txtWarning3 = txtWarning3 + "<p>Pending BOM order (OC ID: " + osOrderList.get(i) + ") exists</p>";
			}
		}
		if (osOrderList2 != null){
			for (int i=0; i < osOrderList2.size(); i++) {
				txtWarning3 = txtWarning3 + "<p>Pending Springboard order (order number: " + osOrderList2.get(i) + ") exists</p>";
			}
		}
		
		String txtWarning4 = "";
		String txtError2 = "";
		if (blockage==null){
		} else {
			if (blockage.getBlockageCode().trim().equals("30")) {
				txtError2 = "<p>Blockage Code " + blockage.getBlockageCode().trim() 
					+ ", " + blockage.getBlockageDesc().trim() 
					+ ", Blockage Date: " + Utility.date2String(blockage.getBlockageDate(), "yyyy/MM/dd")
					+ "</p>";
			} else {
				txtWarning4 = "<p><b>Blockage Code " + blockage.getBlockageCode().trim() 
					+ ", " + blockage.getBlockageDesc().trim() 
					+ ", Blockage Date: " + Utility.date2String(blockage.getBlockageDate(), "yyyy/MM/dd")
					+ "</b></p>";
			}
		}
		
		if (blockageList==null){
		} else {
			for (int i=0; i < blockageList.size(); i++) {
				if (!blockageList.get(i).getUnitnb().trim().equalsIgnoreCase(unitNo.trim())) {
					txtWarning4 += "<p>Flat " + blockageList.get(i).getUnitnb() + " - Blockage Code " + blockageList.get(i).getBlockageCode().trim() 
						+ ", " + blockageList.get(i).getBlockageDesc().trim() 
						+ ", Blockage Date: " + Utility.date2String(blockageList.get(i).getBlockageDate(), "yyyy/MM/dd")
						+ "</p>";
				}
			}
		}
		
		String txtWarning5 = "";
		if (blacklistAddr=="Y"){
			txtWarning5 = "Blacklist Address. Please provide address proof";
			if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
				txtWarning5 = "BL Address";
			}
		}
		
		String correctSBwithFlatFloor = service.getCorrectSBbyFlatFloor(sbNum, floor, unitNo);
		
		JSONArray jsonArray = new JSONArray();

		Object a = request.getSession().getAttribute(ImsConstants.IMS_NOW_RET_FLOW);
		if(a!=null){//nowRet flow
			logger.info("IMS_NOW_RET_FLOW:"+ gson.toJson(a));
			txtError2="";
			txtWarning4="";
			blacklistAddr="";
			txtWarning5="";
		}
		jsonArray.add(
				"{\"osOrderWarning3\":\""	+ txtWarning3 +
				"\",\"blockageError2\":\""	+ txtError2 + 
				"\",\"blockageWarning4\":\""	+ txtWarning4 + 
				"\",\"isBlacklistAddr\":\""	+ blacklistAddr + 
				"\",\"blAddrWarning5\":\""	+ txtWarning5 + 
				"\",\"correctSBwithFlatFloor\":\""	+ correctSBwithFlatFloor + 
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		
		long endTime = System.currentTimeMillis();
		long timeElapsed = endTime-startTime;
		logger.debug("imsaddressinfolookupbyff SB="+sbNum+" flat="+unitNo+" floor="+floor+" phInd"+phInd+" timeElapsed="+timeElapsed/1000+"s");
		if (timeElapsed >= 25000) {
			logger.error("imsaddressinfolookupbyff timeElapsed= "+timeElapsed/1000+"s >= 25s");
		}
		
		return new ModelAndView("ajax_addrInfoByFloorAndFlat", "jsonArray",	jsonArray);
	}
}
