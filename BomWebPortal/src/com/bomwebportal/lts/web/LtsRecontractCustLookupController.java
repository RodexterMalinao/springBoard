package com.bomwebportal.lts.web;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.util.Utility;

public class LtsRecontractCustLookupController implements Controller {

	CustomerProfileLtsService customerProfileLtsService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OrderCaptureDTO order = LtsSessionHelper.getOrderCapture(request);
		String reqType = request.getParameter("reqType");
		JSONObject jsonObject = new JSONObject();
		
		if("FIND_CUST".equals(reqType)){
			String docType = request.getParameter("docType");
			String docId = request.getParameter("docId");

			if(LtsConstant.DOC_TYPE_HKID.equals(docType)
					&& (!Utility.validateHKID(docId) || !Utility.validateHKIDcheckDigit(docId))){
				jsonObject.put("state", "2");
			}else{
				CustomerDetailProfileLtsDTO cust = customerProfileLtsService.getCustByDoc(docType, docId, "DRG");
				if(cust != null){
					// TODO: to check Transferor input is same with transferee input -- compare
					order.getLtsRecontractForm().setCustDetailProfile(cust);
					jsonObject.put("state", "1");
					jsonObject.put("title", cust.getTitle());
					jsonObject.put("firstName", cust.getFirstName());
					jsonObject.put("lastName", cust.getLastName());
					jsonObject.put("companyName", cust.getCompanyName());
					jsonObject.put("blacklist", cust.isBlacklistCustInd());
//					jsonObject.put("outstanding", cust.getLastName());
					
					String oldCustNum = order.getLtsServiceProfile().getPrimaryCust().getDocNum();
					String oldCustChkIdNum = StringUtils.replace(oldCustNum, "(", "");
					oldCustChkIdNum = StringUtils.replace(oldCustChkIdNum, ")", "");
					
					String newCustNum = cust.getDocNum();
					String newCustChkIdNum = StringUtils.replace(newCustNum, "(", "");
					newCustChkIdNum = StringUtils.replace(newCustChkIdNum, ")", "");
					
					if (StringUtils.equals("HKID", cust.getDocType()) &&
						StringUtils.equals("PASS", order.getLtsServiceProfile().getPrimaryCust().getDocType()) &&
						//(StringUtils.startsWith(chkIdNum, "#") && Utility.validateHKID(StringUtils.substring(chkIdNum, 1)))) &&
						StringUtils.contains(oldCustChkIdNum, newCustChkIdNum)) {
							String firstName = order.getLtsServiceProfile().getPrimaryCust().getFirstName();
							String lastName = order.getLtsServiceProfile().getPrimaryCust().getLastName();
							
							if (StringUtils.equalsIgnoreCase(cust.getFirstName(), firstName) &&
								StringUtils.equalsIgnoreCase(cust.getLastName(), lastName)) {
								jsonObject.put("updateDnProfile", "M"); 
							}
							
							else {
								jsonObject.put("updateDnProfile", "N"); 
							}
					}
					
					
					
				}
			}
		}else if("VALID_ACCT".equals(reqType)){
			
			if(order.getLtsRecontractForm() != null){
				String docType = order.getLtsRecontractForm().getDocType();
				String docId = order.getLtsRecontractForm().getDocId();
				String acctNo = request.getParameter("acctNo");
				
				AccountDetailProfileLtsDTO[] accts = customerProfileLtsService.getAcctCustByDoc(docType, docId, "DRG");
				if(accts != null && accts.length > 0){
					for(AccountDetailProfileLtsDTO acct: accts){
						if(acct.getAcctNum().equals(acctNo)){
							if(Arrays.asList(LtsConstant.RECONTRACT_ACCT_INVALID_STATUS).contains(acct.getCreditStatus()) ){
								jsonObject.put("state", "2");
								jsonObject.put("errMsg", "Account status is invalid.");
								break;
							}
							order.getLtsRecontractForm().setAccountDetailProfile(acct);
							order.getLtsRecontractForm().setCustDetailProfile(acct.getCustomerProfile());
							jsonObject.put("state", "1");
							break;
						}
					}
				}else{
					jsonObject.put("state", "2");
				}
			}
		}
		
		
		return new ModelAndView("ajax_ltsrecontractcustlookup", jsonObject);
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

}
