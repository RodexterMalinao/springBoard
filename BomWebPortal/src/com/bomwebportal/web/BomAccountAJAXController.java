package com.bomwebportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BomCosOrderWsClient;

import net.sf.json.JSONObject;

public class BomAccountAJAXController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private CustomerProfileService customerProfileService;
	private BomCosOrderWsClient bomCosOrderWsClient;
	
	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}
	
	public BomCosOrderWsClient getBomCosOrderWsClient() {
		return bomCosOrderWsClient;
	}

	public void setBomCosOrderWsClient(BomCosOrderWsClient bomCosOrderWsClient) {
		this.bomCosOrderWsClient = bomCosOrderWsClient;
	}
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//get back the parameter from the input 
		String activeMobileNum = request.getParameter("activeMobileNum");
		String idDocType = request.getParameter("idDocType");
		String idDocNum = request.getParameter("idDocNum");
		String brand = request.getParameter("brand");
		
	
		com.bomwebportal.dto.AccountDTO accountDTO = customerProfileService.getAccountInfo(activeMobileNum, idDocType, idDocNum, brand);
		JSONObject jsonObject = new JSONObject();
		if(accountDTO != null){			
			jsonObject.put("acctNum", accountDTO.getAcctNum());
			jsonObject.put("acctName", accountDTO.getAcctName());
			jsonObject.put("billPeriod", accountDTO.getBillPeriod());
			jsonObject.put("mobCustNum", accountDTO.getMobCustNum());
			jsonObject.put("bomCustNum", accountDTO.getBomCustNum());
			jsonObject.put("acctNum", accountDTO.getAcctNum());
			jsonObject.put("srvNum",accountDTO.getSrvNum());
			
			bom.mob.schema.javabean.si.springboard.xsd.PaymentDTO bomAcctPaymentDTO = bomCosOrderWsClient.getAcctPayMthd(accountDTO.getAcctNum());
			
			
			
			if (bomAcctPaymentDTO.getCrCardExpiryDate() != null) {
				String[] dateStrings=bomAcctPaymentDTO.getCrCardExpiryDate().split("/");
				String expiryMonth =  dateStrings[0];
				String expiryYear =  dateStrings[1];
				if ( "C".equals(bomAcctPaymentDTO.getPayMethodType()) ) {
					if(!Utility.validateCreditCardExpiryDate(expiryMonth, expiryYear)){
						jsonObject.put("isCreditExpiry","Y");
					}
					
				}
			}
				
			
			
		}
			
			
		/*
		 * else if ("pattern".equals(searchType)) { if
		 * (StringUtils.isNotEmpty(searchPattern) &&
		 * StringUtils.isNotEmpty(msisdnlvl)) {
		 * 
		 * List<CnpDTO> output = mnpService.getNewMsisdn( searchPattern,
		 * BomWebPortalConstant.CNM_STATUS_NORMAL, shopCd, msisdnlvl, numType,
		 * returnQty); if (output != null && !output.isEmpty()) { for (CnpDTO
		 * temp : output) { jsonArray.add("{\"msisdn\":\"" + temp.getMsisdn() +
		 * "\",\"numType\":\"" + temp.getNumType() + "\",\"msisdnlvl\":\"" +
		 * temp.getLevel() + "\"}");
		 * 
		 * } } logger.info("jsonArray : " + jsonArray); } else {
		 * jsonArray.add("ERROR"); } } } else { jsonArray.add("ERROR"); }
		 */

		return new ModelAndView("ajax_bomaccount", "json", jsonObject);

	}
}
