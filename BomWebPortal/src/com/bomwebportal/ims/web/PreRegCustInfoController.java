package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.GetImsCustomerService;
import com.bomwebportal.ims.service.IsImsBlacklistCustService;
import com.bomwebportal.ims.service.ValidateHKIDService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class PreRegCustInfoController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private GetImsCustomerService getImsCustomerService;
	private IsImsBlacklistCustService isImsBlacklistCustService;
	private ValidateHKIDService validateHKIDService;
	
	private MessageSource message;
	
	public GetImsCustomerService getGetImsCustomerService() {
		return getImsCustomerService;
	}

	public void setGetImsCustomerService(GetImsCustomerService getImsCustomerService) {
		this.getImsCustomerService = getImsCustomerService;
	}

	public IsImsBlacklistCustService getIsImsBlacklistCustService() {
		return isImsBlacklistCustService;
	}

	public void setIsImsBlacklistCustService(
			IsImsBlacklistCustService isImsBlacklistCustService) {
		this.isImsBlacklistCustService = isImsBlacklistCustService;
	}

	public ValidateHKIDService getValidateHKIDService() {
		return validateHKIDService;
	}

	public void setValidateHKIDService(ValidateHKIDService validateHKIDService) {
		this.validateHKIDService = validateHKIDService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String docType = request.getParameter("docType");
    	String idDocNum = request.getParameter("idDocNum");
    	String loginName = request.getParameter("loginName");
		
		
		JSONArray jsonArray = new JSONArray();
/*    	
    	if(idDocNum == null || idDocNum.equals("")){
    		imsLoginIdUi.setIdDocNum(idDocNum);
    		request.getSession().setAttribute("imsLoginIdUi", imsLoginIdUi);
    	}
*/  	
    	boolean isValid = true;
    	String errorText = null;
    	String reserveErrorText = null;
    	ImsInstallationUI cust = new ImsInstallationUI();
    	boolean isExisting = false;
    	
    	if((docType != null && !docType.equals("")) && (idDocNum != null && !idDocNum.equals(""))){
    		boolean isImsBlacklist = isImsBlacklistCustService.isImsBlacklistCust(docType, idDocNum);
    		/*
    		if(isImsBlacklist == true){
    			List<String> acctNum;
    			String osAmt;
    			acctNum = isImsBlacklistCustService.getImsOsBalanceAcct(docType, idDocNum);
    			logger.info(acctNum);
    			for(int i=0; i<acctNum.size(); i++){
    				osAmt = isImsBlacklistCustService.getImsOsBalance(acctNum.get(i));
    				jsonArray.add("{\"osAmt" + i + "\":\"" + osAmt
    						+ "\",\"acctNum\":\""	+ acctNum.get(i)
    						+ "\",\"i\":" + i
        					+ "}");
    			}
    		}
    		*/
    		
    		if(docType.equalsIgnoreCase("HKID")){
    			isValid = validateHKIDService.validateHKID(idDocNum);
    			errorText = "Invalid HKID";
//    			errorText = message.getMessage("ims.pcd.imsinstallation.msg.058", null, RequestContextUtils.getLocale(request));
    		}
    		
    		
    		
    		if(docType.equalsIgnoreCase("BS")){
    			isValid = Utility.validateHKBR(idDocNum);
    			if ( isValid )
    				isValid = Utility.validateHKBRcheckDigit(idDocNum);
//    			errorText = "Invalid HKBR";
    			errorText = message.getMessage("ims.pcd.imsinstallation.msg.059", null, RequestContextUtils.getLocale(request));
    		}
    		
    		if( !isValid ){
    			
    			jsonArray.add("{\"isValid\":" + isValid
    					+ ",\"errorText\":\""	+ errorText
    					+ "\"}");
    		}
    		
    		if(isValid){
    			
    			List<ImsInstallationUI> list = getImsCustomerService.getImsCustomer(docType, idDocNum);
    			
    			//added by steven 20130627 start
    			String isExistingStr = getImsCustomerService.checkImsCustomer(docType,idDocNum);
    			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
    			
    			if (order!=null){
    				if ("NTV-A".equals(order.getOrderType()) || order.isOrderTypeNowRet()) {
    					isExistingStr = getImsCustomerService.checkImsCustomerNowTV(docType,idDocNum);
    				}
    			}else{
    		    	logger.info("order is null, cannot set HasBBDailup, please tell steven chak");
    			}
    			//added by steven 20130627 end
    			
    			if (isExistingStr.equalsIgnoreCase("Y")){
    				isExisting = true;
    			}else{
    				isExisting = false;
    			}
    			
    			for(int i=0; i<list.size(); i++){
			
    				cust = list.get(i);
    				
    				
    				if(isImsBlacklist == true){
    	    			List<String> acctNum;
    	    			String osAmt;
    	    			acctNum = isImsBlacklistCustService.getImsOsBalanceAcct(docType, idDocNum);
    	    			for(int j=0; j<acctNum.size(); j++){
    	    				osAmt = isImsBlacklistCustService.getImsOsBalance(acctNum.get(j));
    	    				
    	    				jsonArray.add("{\"docType\":\"" + docType
    						+ "\",\"idDocNum\":\""	+ idDocNum
    						+ "\",\"title\":\""	+ cust.getTitle()
    						+ "\",\"lastName\":\""	+ cust.getLastName()
    						+ "\",\"firstName\":\""	+ cust.getFirstName()
    						+ "\",\"DOB\":\""	+ Utility.date2String(cust.getDob(), "dd/MM/yyyy")
    						+ "\",\"mobile\":\""	+ cust.getContact().getContactPhone()
    						+ "\",\"fixLine\":\""	+ cust.getContact().getOtherPhone()
    						+ "\",\"companyName\":\""	+ cust.getCompanyName()
    						+ "\",\"contactPersonName\":\""	+ cust.getContactPersonName()
    						+ "\",\"custNo\":\""	+ cust.getCustNo()
    						+ "\",\"acctNo\":\""	+ acctNum.get(j)
    						+ "\",\"osAmt\":\""	+ osAmt
    						+ "\",\"j\":"	+ j
    						+ ",\"isValid\":"	+ isValid
    						+ ",\"isExisting\":"	+ isExisting
    						+ ",\"errorText\":\""	+ errorText
    						+ "\",\"isImsBlacklist\":\""	+ isImsBlacklist
    						+ "\"}");
    	    			}
    				}else if(isImsBlacklist == false){
    					jsonArray.add("{\"docType\":\"" + docType
        						+ "\",\"idDocNum\":\""	+ idDocNum
        						+ "\",\"title\":\""	+ cust.getTitle()
        						+ "\",\"lastName\":\""	+ cust.getLastName()
        						+ "\",\"firstName\":\""	+ cust.getFirstName()
        						+ "\",\"DOB\":\""	+ Utility.date2String(cust.getDob(), "dd/MM/yyyy")
        						+ "\",\"mobile\":\""	+ cust.getContact().getContactPhone()
        						+ "\",\"fixLine\":\""	+ cust.getContact().getOtherPhone()
        						+ "\",\"companyName\":\""	+ cust.getCompanyName()
        						+ "\",\"contactPersonName\":\""	+ cust.getContactPersonName()
        						+ "\",\"custNo\":\""	+ cust.getCustNo()
        						+ "\",\"isValid\":"	+ isValid
        						+ ",\"isExisting\":"	+ isExisting
        						+ ",\"errorText\":\""	+ errorText
        						+ "\",\"isImsBlacklist\":\""	+ isImsBlacklist
        						+ "\"}");
    				}
    			}
    			cust.setIdDocType(docType);
    			cust.setIdDocNum(idDocNum);
    			cust.setDob(cust.getDob());
    		}
    	}
    	request.getSession().setAttribute("imsCustomer", cust);
    	logger.info(docType);
    	logger.info(idDocNum);
    	logger.info(jsonArray);

		return new ModelAndView("ajax_custinfo", "jsonArray", jsonArray);
	}

	public void setMessage(MessageSource message) {
		this.message = message;
	}

	public MessageSource getMessage() {
		return message;
	}
}
