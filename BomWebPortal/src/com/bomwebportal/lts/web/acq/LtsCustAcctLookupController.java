package com.bomwebportal.lts.web.acq;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomCustomerVerificationDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.profile.CustomerContactProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CustomerInformationService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.lts.util.LtsSbHelper;

public class LtsCustAcctLookupController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	CustomerProfileLtsService customerProfileLtsService;
	CustomerInformationService customerInformationService;

   	public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
   		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		String docType = request.getParameter("docType");
		String docNum = request.getParameter("docNum").toUpperCase();
		String reqType = request.getParameter("reqType");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String basketType = request.getParameter("basketType");
		JSONObject jsonObject = new JSONObject();
		
		if("FIND_CUST".equals(reqType)){

			if(LtsConstant.DOC_TYPE_HKID.equals(docType)
					&& (!Utility.validateHKID(docNum) || !Utility.validateHKIDcheckDigit(docNum))){
				jsonObject.put("state", "2");
			}else{
				CustomerDetailProfileLtsDTO cust = customerProfileLtsService.getCustByDoc(docType, docNum, "DRG");
				if(cust != null){
					order.setCustomerDetailProfileLtsDTO(cust);
					jsonObject.put("state", "1");
					jsonObject.put("docNum", docNum);
					jsonObject.put("title", cust.getTitle());
					jsonObject.put("firstName", cust.getFirstName());
					jsonObject.put("lastName", cust.getLastName());
					jsonObject.put("companyName", cust.getCompanyName());
					jsonObject.put("blacklist", cust.isBlacklistCustInd());
					jsonObject.put("wip", cust.getWipInd());
					jsonObject.put("wipMessage", cust.getWipMessage());
     				jsonObject.put("custSpecialRemarks", cust.getCustSpecialRemarkContent() );
     				jsonObject.put("custNum", cust.getCustNum());
     				jsonObject.put("dateOfBirth", cust.getDob());
     				if(!"Y".equals(jsonObject.get("contactUpdAlert"))){
     					jsonObject.put("contactUpdAlert", "N");
     				}
     				
     				CustomerContactProfileLtsDTO[] contact = customerProfileLtsService.getCustContactInfo(cust.getCustNum(), "DRG");
     				if(contact != null)
     				{
     					for(CustomerContactProfileLtsDTO temp: contact){
     						if(StringUtils.equals(temp.getMediaType(), "E"))
     						{
     						    jsonObject.put("contactEmail", temp.getMediaNum());	
     		     				jsonObject.put("contactEmailDate", temp.getLastUpdDate());
     						    if(LtsSbHelper.checkLastUpdDate(temp.getLastUpdDate())){
     						    	jsonObject.put("alertContactMsg", "last upd date is "+temp.getLastUpdDate());
     						    	jsonObject.put("alertUpdEmailMsg", "last upd date is "+temp.getLastUpdDate());
     						    }
     						}
     						else if (StringUtils.equals(temp.getMediaType(), "M"))
     						{
     							jsonObject.put("mobileNo", temp.getMediaNum());
     		     				jsonObject.put("mobileNoDate", temp.getLastUpdDate());
                                if(LtsSbHelper.checkLastUpdDate(temp.getLastUpdDate())){
                                	jsonObject.put("alertContactMsg", "last upd date is "+temp.getLastUpdDate());
                                	jsonObject.put("alertUpdMobileNoMsg", "last upd date is "+temp.getLastUpdDate());
     						    }
     						}
     					}
     					
     				}
     				
				}
				else {
					jsonObject.put("state", "0");
					order.setCustomerDetailProfileLtsDTO(null);
				}
			}
//		}else if("VALID_ACCT".equals(reqType)){
//			
//			if(order.getLtsRecontractForm() != null){
//				String docType = order.getLtsRecontractForm().getDocType();
//				String docId = order.getLtsRecontractForm().getDocId();
//				String acctNo = request.getParameter("acctNo");
//				
//				AccountDetailProfileLtsDTO[] accts = customerProfileLtsService.getAcctCustByDoc(docType, docId, "DRG");
//				if(accts != null && accts.length > 0){
//					for(AccountDetailProfileLtsDTO acct: accts){
//						if(acct.getAcctNum().equals(acctNo)){
//							order.getLtsRecontractForm().setAccountDetailProfile(acct);
//							jsonObject.put("state", "1");
//							break;
//						}
//					}
//				}else{
//					jsonObject.put("state", "2");
//				}
//			}
//		}
		
			logger.info("Input LTS Acq Doc Num: "+docNum+" by Sales "+request.getParameter("sales"));
			
   	}else if("VALID_CUST".equals(reqType)){

	   		BomCustomerVerificationDTO custVerifi = new BomCustomerVerificationDTO();
	   		BomCustomerVerificationDTO ltsCustVerifi = new BomCustomerVerificationDTO();
	   		BomCustomerVerificationDTO imsCustVerifi = new BomCustomerVerificationDTO();
	   		
	   		if(LtsConstant.DOC_TYPE_HKID.equals(docType)
					&& (!Utility.validateHKID(docNum) || !Utility.validateHKIDcheckDigit(docNum))){
				jsonObject.put("state", "2");
			}else{
				if("EYE".equals(basketType)){
					custVerifi = customerInformationService.getBomCustomerVerificationResult(docType, docNum, firstName, lastName);
					if(!custVerifi.isMatchWithBomName()){
						ltsCustVerifi = customerInformationService.getLtsCustomerVerificationResult(docType, docNum, firstName, lastName);
						jsonObject.put("isMatchWithLtsName", ltsCustVerifi.isMatchWithBomName());
						imsCustVerifi = customerInformationService.getImsCustomerVerificationResult(docType, docNum, firstName, lastName);
						jsonObject.put("isMatchWithImsName", imsCustVerifi.isMatchWithBomName());						
					}
				}else{
					custVerifi = customerInformationService.getLtsCustomerVerificationResult(docType, docNum, firstName, lastName);
				}
				if(custVerifi != null){
					order.setBomVerifiedCustomerResult(custVerifi);
					jsonObject.put("state", "1");
					jsonObject.put("isMatchWithBomName", custVerifi.isMatchWithBomName());     				
     				
				}
				else {
					jsonObject.put("state", "1");
					jsonObject.put("isMatchWithBomName", false);
				}
			}	   		
	   		
   	}


		return new ModelAndView("ajax_ltscustomerlookup", jsonObject);
	}

   	
	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}


	public CustomerInformationService getCustomerInformationService() {
		return customerInformationService;
	}


	public void setCustomerInformationService(
			CustomerInformationService customerInformationService) {
		this.customerInformationService = customerInformationService;
	}
	

}
