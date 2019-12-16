package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.google.gson.Gson;


public class ImsCheckDigitalSignatureController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		logger.info("in ImsCheckDigitalSignatureController");
		
		String signatureInd="";
		boolean indcus=false;
		boolean indcc=false;
		boolean indtp=false;
		boolean indcare=false;
		
		List<String> log = new ArrayList<String>();
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		ImsSignoffDTO customerSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_CUST_SIGN);
		ImsSignoffDTO creditCardSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN);
		ImsSignoffDTO thirdPartySign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_3_PARTY_SIGN);
		ImsSignoffDTO careCashSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_CARE_CASH_SIGN);
		
		if(customerSign!=null&&ImsSignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN == customerSign.getSignatureType()){
			log.add    ("customerSign signature card not null");
//			logger.info("customerSign signature card not null");
			if (!"Y".equalsIgnoreCase(customerSign.getSignedInd())){
				indcus=false;
			}else{
				indcus=true;
			}
		}else{
			log.add    ("customerSign signature card null");
//			logger.info("customerSign signature card null");
			indcus=true;
		}
		
		if(creditCardSign!=null&&ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN == creditCardSign.getSignatureType()){
//			logger.info("credit signature card not null");
			log.add    ("credit signature card not null");
			if (!"Y".equalsIgnoreCase(creditCardSign.getSignedInd())){
				indcc=false;
			}else{
				indcc=true;
			}
		}else{
			log.add    ("credit signature card null");
//			logger.info("credit signature card null");
			indcc=true;
		}
		
		if(thirdPartySign!=null&&ImsSignoffDTO.SignatureTypeEnum.ThirdParty_SIGN == thirdPartySign.getSignatureType()){
//			logger.info("third party not null");
			log.add    ("third party not null");
			if (!"Y".equalsIgnoreCase(thirdPartySign.getSignedInd())){
				indtp=false;
			}else{
				indtp=true;
			}
		}else{
			log.add    ("third party null");
//			logger.info("third party null");
			indtp=true;
		}
		
		if(careCashSign!=null&&ImsSignoffDTO.SignatureTypeEnum.CareCash_SIGN == careCashSign.getSignatureType()){
//			logger.info("care cash signature not null");
			log.add    ("care cash signature not null");
			if (!"Y".equalsIgnoreCase(careCashSign.getSignedInd())){
				indcare=false;
			}else{
				indcare=true;
			}
		}else{
			log.add    ("care cash signature null");
//			logger.info("care cash signature null");
			indcare=true;
		}

		if (customerSign==null&&creditCardSign==null&&thirdPartySign==null&&careCashSign==null){
			signatureInd = "N";
		}else if (indcus && indcc && indtp && indcare){
			signatureInd = "Y";
		}else{
			signatureInd = "N";
		}

//		logger.info("cust sign: "+indcus);
//		logger.info("cc sign: "+indcc);
//		logger.info("3party sign: "+indtp);
		log.add    ("cust sign: "+indcus);
		log.add    ("cc sign: "+indcc);
		log.add    ("3party sign: "+indtp);
		log.add    (sessionOrder.getOrderId());
		

		logger.info(new Gson().toJson(log));
		
		JSONArray jsonArray = new JSONArray();
		
		jsonArray.add(
				
				"{\"signatureInd\":\"" +signatureInd + 
				"\"}");
		
		logger.info(jsonArray);
		
		return new ModelAndView("ajax_imscheckdigitalsignature", "jsonArray", jsonArray);
				
		
	}

}