package com.bomwebportal.mob.ccs.web;

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
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.mob.ccs.dto.CreditCardInstDTO;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.service.PaymentService;

public class MobCcsPaymentUpFrontAJAXController implements Controller{
    
    protected final Log logger = LogFactory.getLog(getClass());
 
    
    private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
    public MobCcsPaymentUpfrontService getMobCcsPaymentUpfrontService() {
		return mobCcsPaymentUpfrontService;
	}
	public void setMobCcsPaymentUpfrontService(MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService) {
		this.mobCcsPaymentUpfrontService = mobCcsPaymentUpfrontService;
	}

	private PaymentService paymentService;
	public PaymentService getPaymentService() {
		return paymentService;
	}
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("MobCcsPaymentUpFrontAJAXController");
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String type = this.getStringValue(request, "type");
		JSONArray jsonArray = new JSONArray();	
		logger.info("type:"+type);
		if ("CcInstList".equals(type)) {
		    
		    //parameter1 = bankCd, parameter2 = upfrontAmt
		    List<CreditCardInstDTO> creditCardInstList = mobCcsPaymentUpfrontService.getCreditCardInstList(this.getStringValue(request, "bankCd"), this.getStringValue(request, "upfrontAmt"), salesUserDto.getChannelId());
		    logger.info(this.getStringValue(request, "bankCd")+":"+this.getStringValue(request, "upfrontAmt"));
		    for (CreditCardInstDTO item : creditCardInstList){
		    	logger.info("Bank code:"+item.getBankCode()+";"+item.getInstSchedule());
		    	jsonArray.add("{\"bankCd\":\""	+ item.getBankCode()
					+ "\",\"instSchedule\":\""	+ item.getInstSchedule()
					+ "\"}");
		    }	 
		}
		
		if ("CcInstBankList".equals(type)) {
		    
		    //parameter1 = payMtdType, parameter2 = upfrontAmt
			String payMtdType = this.getStringValue(request, "payMtdType");
			String upfrontAmt = this.getStringValue(request, "upfrontAmt");
			logger.info(payMtdType+":"+upfrontAmt);
			
			List<IssueBankDTO> creditCardInstBankList = new ArrayList<IssueBankDTO>();
			
			if (payMtdType.equalsIgnoreCase("I")) { //Credit Card Inst. Payment
				creditCardInstBankList = mobCcsPaymentUpfrontService.getCreditCardInstBankList(upfrontAmt, salesUserDto.getChannelId());
			} else { //Credit Card Payment
				creditCardInstBankList = paymentService.getIssueBankList();
			}
		    
		    for (IssueBankDTO item : creditCardInstBankList){
		    	//logger.info("Bank code:"+item.getBankCd()+";"+item.getBankName());
		    	jsonArray.add("{\"bankCd\":\""	+ item.getBankCd()
					+ "\",\"bankName\":\""	+ item.getBankName()
					+ "\"}");
		    }
	 
		}
	
		logger.info("jsonArray : " + jsonArray);
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
    
    private Integer getIntegerValue(HttpServletRequest request, String name) {
	Integer value = null;
	try {
		value = new Integer(request.getParameter(name));
	} catch (NumberFormatException nfe) {}
	return value;
    }
    
}
