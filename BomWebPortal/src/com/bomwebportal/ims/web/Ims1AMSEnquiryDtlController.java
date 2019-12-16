package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.service.Ims1AMSEnquiryService;
import com.google.gson.Gson;


import com.bomwebportal.ims.dto.Ims1AMSInfoWithoutPendingDTO;


public class Ims1AMSEnquiryDtlController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());

	Gson gson = new Gson();
	private Ims1AMSEnquiryService ims1AMSEnquiryService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String in_FSA = request.getParameter("fsa");
				
		Ims1AMSInfoWithoutPendingDTO ims1AMSInfoWithoutPendingDTO = ims1AMSEnquiryService.getIms1AMSInfoWithoutPending(in_FSA);
		
		String bandwidth =  ims1AMSEnquiryService.getIms1AMSBandwidth(ims1AMSInfoWithoutPendingDTO.getpID());
		
		
		JSONArray jsonArray = new JSONArray();
	
			jsonArray.add("{\"CustName\":\"" + ims1AMSInfoWithoutPendingDTO.getCustName()
					+ "\",\"Is1L1B\":\""	+ ims1AMSInfoWithoutPendingDTO.getIs1L1B()
					+ "\",\"IsEYE\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsEYE()
					+ "\",\"IsEYEX\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsEYEX()
					+ "\",\"IsILRC\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsILRC()
					+ "\",\"IsPCD\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsPCD()
					+ "\",\"IsPCDTV\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsPCDTV()
					+ "\",\"IsStandAloneEasyWatch\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsStandAloneEasyWatch()
					+ "\",\"IsStandAloneEYE\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsStandAloneEYE()
					+ "\",\"IsStandAloneTV\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsStandAloneTV()
					+ "\",\"IsTV\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsTV()
					+ "\",\"IsVI\":\""	+ ims1AMSInfoWithoutPendingDTO.getIsVI()
					+ "\",\"Ocid\":\""	+ ims1AMSInfoWithoutPendingDTO.getOcid()
					+ "\",\"OrderType\":\""	+ ims1AMSInfoWithoutPendingDTO.getOrderType()
					+ "\",\"pCDAccStatus\":\""	+ ims1AMSInfoWithoutPendingDTO.getpCDAccStatus()
					+ "\",\"pID\":\""	+ ims1AMSInfoWithoutPendingDTO.getpID()
					+ "\",\"pCDAccStatus\":\""	+ ims1AMSInfoWithoutPendingDTO.getpID()
					+ "\",\"SrdDate\":\""	+ ims1AMSInfoWithoutPendingDTO.getSrdDate()
					+ "\",\"vIAccStatus\":\""	+ ims1AMSInfoWithoutPendingDTO.getvIAccStatus()
					+ "\",\"lineType\":\""	+bandwidth
					+ "\"}");
				
		
		logger.info("jsonArray : " + jsonArray);
		
		return new ModelAndView("ajax_view", "jsonArray", jsonArray);
		
	}

	public Ims1AMSEnquiryService getIms1AMSEnquiryService() {
		return ims1AMSEnquiryService;
	}

	public void setIms1AMSEnquiryService(
			Ims1AMSEnquiryService ims1AMSEnquiryService) {
		this.ims1AMSEnquiryService = ims1AMSEnquiryService;
	}
	
	

}
