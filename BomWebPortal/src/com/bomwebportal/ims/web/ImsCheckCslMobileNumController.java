package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.google.gson.Gson;

import com.bomwebportal.ims.service.ValidateHKIDService;
import com.bomwebportal.util.Utility;

public class ImsCheckCslMobileNumController implements Controller {
    private ImsAddressInfoService service;
    
	public void setService(ImsAddressInfoService service) {
		this.service = service;
	}
	public ImsAddressInfoService getService() {
		return service;
	}
	private ValidateHKIDService validateHKIDService;

	public ValidateHKIDService getValidateHKIDService() {
		return validateHKIDService;
	}
	private MessageSource message;
	public void setValidateHKIDService(ValidateHKIDService validateHKIDService) {
		this.validateHKIDService = validateHKIDService;
	}
	protected final Log logger = LogFactory.getLog(getClass());
//	private final Gson gson = new Gson();
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		String idDocType = request.getParameter("IDDOCTYPE");
		String idDocNum = request.getParameter("IDDOCNUM");
		String mrt = request.getParameter("CSLMOBILENUM");
		String sbno = request.getParameter("SERBDYNO");
		String floor = request.getParameter("FLOOR");
		String unit = request.getParameter("UNIT");

		String hasPcdBeforeInd = null;
		String hasNonCslPCDInd = null;
		Map<String, String> map = null;
		String custFirstName = null;
		String custLastName = null;
		String pcdMonth = "";
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		boolean valid = true;
		String errorText = null;
		if(StringUtils.isBlank(sbno)||StringUtils.isBlank(floor)){
			valid = false;
//			errorText = "Insufficient information. Please fulfill the address(or SB) , floor, flat(if applicable).";
			errorText = message.getMessage("ims.pcd.addressinfo.msg.018", null, RequestContextUtils.getLocale(request));
		}else if(StringUtils.isBlank(idDocNum)){
			valid = false;
//			errorText = "Please input the document number.";
			errorText = message.getMessage("ims.pcd.addressinfo.msg.019", null, RequestContextUtils.getLocale(request));
		}else if (StringUtils.isBlank(mrt)){
			valid = false;
//			errorText = "Please input the mobile no.";
			errorText = message.getMessage("ims.pcd.addressinfo.msg.020", null, RequestContextUtils.getLocale(request));
		}else{
			if(idDocType.equalsIgnoreCase("HKID") ){
				logger.info("Validate HKID");
    			valid = validateHKIDService.validateHKID(idDocNum);
    			if(valid == false)  {      		
//        			errorText = "Invalid HKID";
    				errorText = message.getMessage("ims.pcd.addressinfo.msg.021", null, RequestContextUtils.getLocale(request));
    			}
			}else if(idDocType.equalsIgnoreCase("BS") ){
    			logger.info("Validate HKBR");
    			valid = Utility.validateHKBR(idDocNum);
    			if(valid == true)
    				valid = Utility.validateHKBRcheckDigit(idDocNum);    			
    			if(valid == false){
//        			errorText = "Invalid BR number";
        			errorText = message.getMessage("ims.pcd.addressinfo.msg.022", null, RequestContextUtils.getLocale(request));
        		}
    		}else if(idDocType.equalsIgnoreCase("PASS") ){
    			logger.info("Validate PASS");
    			if(StringUtils.isBlank(idDocNum)||idDocNum.length()<6){
    				valid = false;
//    				errorText = "Passport number must include at least 6 characters.";
    				errorText = message.getMessage("ims.pcd.addressinfo.msg.023", null, RequestContextUtils.getLocale(request));
    			}else{
    				valid = Utility.validatePassport(idDocNum);
    				if(valid == false)        		
//        			errorText = "Invalid Passport";
    				errorText = message.getMessage("ims.pcd.addressinfo.msg.024", null, RequestContextUtils.getLocale(request));
    			}
    		}
			valid = isHKmrtValid(mrt);
			if(valid == false){        		
//    			errorText = "Invalid Mobile Number";
    			errorText = message.getMessage("ims.pcd.addressinfo.msg.025", null, RequestContextUtils.getLocale(request));
			}
		}
		if(valid){
			try {
				map = service.getCslMobileCustomer(mrt, idDocType, idDocNum);
				if(map!= null){
					custFirstName = map.get("cust_first_name");
					custLastName  = map.get("cust_last_name");
				}else{
					valid = false;
//					errorText = "Input pair not exists in system";
					errorText = message.getMessage("ims.pcd.addressinfo.msg.026", null, RequestContextUtils.getLocale(request));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(valid){
				try {
					hasPcdBeforeInd = service.hasPcdBeforeCheckCsl(sbno, unit, floor);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					hasNonCslPCDInd = service.cslMobileNumPCDCheck(mrt,sessionOrder.getOrderId());
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					pcdMonth = service.getCslPcdMonthCheck();
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(hasPcdBeforeInd!=null && hasPcdBeforeInd.equalsIgnoreCase("Y") ){
					valid = false;
//					errorText = "Customer has PCD service in past "+pcdMonth+" months on this IA";
					errorText = message.getMessage("ims.pcd.addressinfo.msg.027", null, RequestContextUtils.getLocale(request))
					+pcdMonth+message.getMessage("ims.pcd.addressinfo.msg.027a", null, RequestContextUtils.getLocale(request));
				}
				
				if(hasNonCslPCDInd!=null && hasNonCslPCDInd.equalsIgnoreCase("Y") ){
					valid = false;
//					errorText = "Pending Springboard order exists for this mobile number";
					errorText = message.getMessage("ims.pcd.addressinfo.msg.028", null, RequestContextUtils.getLocale(request));
				}
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		jsonObject.put("hasPcdBeforeInd", hasPcdBeforeInd);
		jsonObject.put("hasNonCslPCDInd", hasNonCslPCDInd);
		jsonObject.put("custFirstName", custFirstName);
		jsonObject.put("custLastName", custLastName);
		jsonObject.put("valid", valid);
		jsonObject.put("errorText", errorText);

		jsonArray.element(jsonObject);
		
		logger.info("idDocType: " + idDocType);
		logger.info("idDocNum: " + idDocNum);
		logger.info("mrt: " + mrt);
		logger.info("sbno: " + sbno);
		logger.info("floor: " + floor);
		logger.info("unit: " + unit);
		logger.info("hasPcdBeforeInd: " + hasPcdBeforeInd);
		logger.info("hasNonCslPCDInd: " + hasNonCslPCDInd);
		logger.info("custFirstName: " + custFirstName);
		logger.info("custLastName: " + custLastName);
		logger.info("valid: " + valid);
		logger.info("errorText: " + errorText);
		logger.info(jsonArray);
		
		return new ModelAndView("ajax_imscheckcslmobilenum", "jsonArray", jsonArray);
		
		
	}
	
	
	 public static boolean isHKmrtValid(String mrt){
	     boolean isValid = false;

	     //Initialize reg ex for phone number.
	    String expression = "[456789][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
	    CharSequence inputStr = mrt;
	    Pattern pattern = Pattern.compile(expression);
	    Matcher matcher = pattern.matcher(inputStr);
	    if(matcher.matches())
	        isValid = true;
	    
	    return isValid;
	 }
	public void setMessage(MessageSource message) {
		this.message = message;
	}
	public MessageSource getMessage() {
		return message;
	}

}
