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
import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.ImsLoginIDUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.COrderService;
import com.bomwebportal.ims.service.GetImsCustomerService;
import com.bomwebportal.ims.service.IsImsBlacklistCustService;
import com.bomwebportal.ims.service.ReleaseLoginIDService;
import com.bomwebportal.ims.service.ReserveLoginIDService;
import com.bomwebportal.ims.service.ValidateHKIDService;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.util.Utility;

public class CustInfoController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private GetImsCustomerService getImsCustomerService;
	private ReserveLoginIDService reserveLoginIDService;
	private ReleaseLoginIDService releaseLoginIDService;
	private IsImsBlacklistCustService isImsBlacklistCustService;
	private ValidateHKIDService validateHKIDService;

	private COrderService cOrderService;	
    private ConstantLkupService constantLkupService;
	private MessageSource message;

	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}

	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}
	
	public GetImsCustomerService getGetImsCustomerService() {
		return getImsCustomerService;
	}

	public void setGetImsCustomerService(GetImsCustomerService getImsCustomerService) {
		this.getImsCustomerService = getImsCustomerService;
	}

	public ReserveLoginIDService getReserveLoginIDService() {
		return reserveLoginIDService;
	}

	public void setReserveLoginIDService(ReserveLoginIDService reserveLoginIDService) {
		this.reserveLoginIDService = reserveLoginIDService;
	}

	public ReleaseLoginIDService getReleaseLoginIDService() {
		return releaseLoginIDService;
	}

	public void setReleaseLoginIDService(ReleaseLoginIDService releaseLoginIDService) {
		this.releaseLoginIDService = releaseLoginIDService;
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
	
	public void setcOrderService(COrderService cOrderService) {
		this.cOrderService = cOrderService;
	}

	public COrderService getcOrderService() {
		return cOrderService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ImsLoginIDUI imsLoginIdUi = new ImsLoginIDUI();
		
		if(request.getSession().getAttribute("imsLoginIdUi") != null){
			imsLoginIdUi = (ImsLoginIDUI)request.getSession().getAttribute("imsLoginIdUi");
		}
		
		String docType = request.getParameter("docType");
    	String idDocNum = request.getParameter("idDocNum");
    	String loginName = request.getParameter("loginName");
		int reserveResult = -99;
		int releaseResult = -99;
		
		GetCOrderDTO cOrderDTO = new GetCOrderDTO();
		
		logger.info("DocType: " + imsLoginIdUi.getDocType());
		logger.info("DocNum: " + imsLoginIdUi.getIdDocNum());
		logger.info("OldLoginName: " + imsLoginIdUi.getOldLoginName());
		if((imsLoginIdUi.getDocType() != null && !("").equals(imsLoginIdUi.getDocType()))
				&& (imsLoginIdUi.getIdDocNum() != null && !("").equals(imsLoginIdUi.getIdDocNum()))
				&& (imsLoginIdUi.getOldLoginName() != null && !("").equals(imsLoginIdUi.getOldLoginName()))
				&& (idDocNum != null && !("").equals(idDocNum))){
			releaseResult = releaseLoginIDService.releaseLoginID(imsLoginIdUi.getOldLoginName(), imsLoginIdUi.getIdDocNum(), imsLoginIdUi.getDocType());
		}
		
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
//    			errorText = "Invalid HKID";
    			errorText = message.getMessage("ims.pcd.imsinstallation.msg.058", null, RequestContextUtils.getLocale(request));
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
    			imsLoginIdUi.setDocType(docType);
				imsLoginIdUi.setIdDocNum(idDocNum);
				imsLoginIdUi.setLoginName(loginName);
				if((docType != null && !docType.equals("")) && (idDocNum != null && !idDocNum.equals(""))
						&& imsLoginIdUi.isValid() == true && imsLoginIdUi.isExist() == false
						&& (loginName != null && !("").equals(loginName))){
					reserveResult = reserveLoginIDService.reserveLoginID(loginName, idDocNum, docType);
					if(reserveResult == -7){
						//imsLoginIdUi.setValid(false);
//						reserveErrorText = "Cannot Reserve ILRC Login ID";
						reserveErrorText = message.getMessage("ims.pcd.imsinstallation.msg.060", null, RequestContextUtils.getLocale(request));
						jsonArray.add("{\"docType\":\"" + docType
        						+ "\",\"idDocNum\":\""	+ idDocNum
/*        						+ "\",\"isValid\":"	+ isValid
        						+ ",\"errorText\":\""	+ errorText
*/
        						+ "\",\"isImsBlacklist\":"	+ isImsBlacklist
        						+ ",\"reserveResult\":\""	+ reserveResult
        						+ "\",\"reserveErrorText\":\""	+ reserveErrorText
        						+ "\"}");
					}
					if(reserveResult == 0){
						//request.getSession().setAttribute("imsOldLoginName", loginName);
						imsLoginIdUi.setOldLoginName(imsLoginIdUi.getLoginName());
					}
				}
				request.getSession().setAttribute("imsLoginIdUi", imsLoginIdUi);
    			
    			List<ImsInstallationUI> list = getImsCustomerService.getImsCustomer(docType, idDocNum);
    			
    			//added by steven 20130627 start
    			String isExistingStr = getImsCustomerService.checkImsCustomer(docType,idDocNum);
    			String isDataPrivacyEmtpy = getImsCustomerService.checkImsDataPrivacy(docType, idDocNum, "PCD");
    			String hasBBDailup = "N";
    			if("Y".equalsIgnoreCase(isExistingStr)&&!"Y".equalsIgnoreCase(isDataPrivacyEmtpy)){
    				hasBBDailup = "Y";
    			}
    			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
    			if (order!=null){
    				if ("NTV-A".equals(order.getOrderType()) || order.isOrderTypeNowRet()) {
    					//martin, 20171024, check data privacy for TV in BOM
    					hasBBDailup = "N";
    					String isExistingStrNtv = getImsCustomerService.checkImsCustomerNowTV(docType,idDocNum);
    					if ("NTV-A".equals(order.getOrderType())) {
    						String isDataPrivacyEmtpyNtv = getImsCustomerService.checkImsDataPrivacy(docType, idDocNum, "TV");
    						if ("Y".equalsIgnoreCase(isExistingStrNtv)&&!"Y".equalsIgnoreCase(isDataPrivacyEmtpyNtv)) {
    							hasBBDailup = "Y";
    						}
    					} else {
    						hasBBDailup = isExistingStrNtv;
    					}
    				}
    				order.setHasBBDailup(hasBBDailup);
        			request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
    			}else{
    		    	logger.info("order is null, cannot set HasBBDailup, please tell steven chak");
    			}
    			//added by steven 20130627 end
    			
    			if (hasBBDailup.equalsIgnoreCase("Y")){
    				isExisting = true;
    			}else{
    				isExisting = false;
    			}
    			
    			for(int i=0; i<list.size(); i++){
			
    				cust = list.get(i);
    				
    				OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
    				
    				String disableWqInd = "N";
    				
    				if("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd()))
    				{
    					disableWqInd = constantLkupService.getDisableWQInd();
    				}
    				
    				String isNowTV = "N";
    				String createCOrderInd = "N";
    				String sharedFSA = "";
    				
    				if(sessionOrder.getProcessVim()!=null && "P".equals(sessionOrder.getProcessVim())){
    					 isNowTV="Y";
    				}else{
    					 isNowTV="N";
    				}
    				
    				logger.info(sessionOrder.getInstallAddress().getAddrInventory().getTechnology());
    				logger.info(cust.getCustNo());
    				logger.info(sessionOrder.getInstallAddress().getSerbdyno());
    				logger.info(sessionOrder.getInstallAddress().getUnitNo());
    				logger.info(sessionOrder.getInstallAddress().getFloorNo());
    				
    				
    				cOrderDTO = new GetCOrderDTO();
					
					//GetCOrderDTO cOrderDTO = cOrderService.getCOrder("Y", isNowTV, "PON", "", "228151", "", "41");
				
				// do not share FSA if pre-install order or mobile offer or csl shop or pre-use order
				if("Y".equalsIgnoreCase(sessionOrder.getPreInstallInd())||("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())&&"Y".equalsIgnoreCase(disableWqInd))
						||"Y".equalsIgnoreCase(sessionOrder.getCslShopCustInd())||"Y".equalsIgnoreCase(sessionOrder.getPreUseInd())){
					cOrderDTO.setO_create_c_order("N");
					cOrderDTO.setO_related_fsa(null);
					cOrderDTO.setO_reason(null);
				}else{
					cOrderDTO = cOrderService.getCOrder("Y", isNowTV, sessionOrder.getInstallAddress().getAddrInventory().getTechnology(), cust.getCustNo(), sessionOrder.getInstallAddress().getSerbdyno(), sessionOrder.getInstallAddress().getUnitNo(), sessionOrder.getInstallAddress().getFloorNo());
				}
				
    				sessionOrder.setcOrderDTO(cOrderDTO);
    				sessionOrder.setRide_on_FSA_Ind(cOrderDTO.getO_create_c_order());
    				sessionOrder.setRelated_FSA(cOrderDTO.getO_related_fsa());
    				sessionOrder.setRide_on_FSA_reason_Cd(cOrderDTO.getO_reason());
    				

    				request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);	
    				
    				createCOrderInd = cOrderDTO.getO_create_c_order();
    				String multiFSAInd = "N";
    				if ("Y".equalsIgnoreCase(createCOrderInd)){
    					sharedFSA = cOrderDTO.getO_related_fsa();
    					String nAcctNum = cOrderService.getNAcctByFSA(sharedFSA);
    					if(nAcctNum!=null){
    						multiFSAInd = cOrderService.checkMultipleFSAUnderAcct(nAcctNum);
    					}
    				}
    				
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
    						+ "\",\"isImsBlacklist\":"	+ isImsBlacklist
    						+ ",\"reserveResult\":\""	+ reserveResult
    						+ "\",\"reserveErrorText\":\"" + reserveErrorText
    						+ "\",\"createCOrderInd\":\"" +createCOrderInd 
    						+ "\",\"relatedFSA\":\"" + sharedFSA 
    						+ "\",\"multiFSAInd\":\"" + multiFSAInd 
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
        						+ "\",\"isImsBlacklist\":"	+ isImsBlacklist
        						+ ",\"reserveResult\":\""	+ reserveResult
        						+ "\",\"reserveErrorText\":\""	+ reserveErrorText
        						+ "\",\"createCOrderInd\":\"" +createCOrderInd 
        						+ "\",\"relatedFSA\":\"" + sharedFSA 
        						+ "\",\"multiFSAInd\":\"" + multiFSAInd 
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
