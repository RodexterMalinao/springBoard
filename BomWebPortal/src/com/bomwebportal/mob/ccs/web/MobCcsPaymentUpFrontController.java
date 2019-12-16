package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BankBranchDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.CreditCardInstDTO;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.PaymentService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsPaymentUpFrontController extends SimpleFormController {
    protected final Log logger = LogFactory.getLog(getClass());

    private PaymentService service;
    private VasDetailService vasDetailService;

    public PaymentService getService() {
	return service;
    }

    public void setService(PaymentService service) {
	this.service = service;
    }

    public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	// add by add by herbert 20111228
	private CodeLkupService codeLkupService;
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	// add by add by herbert 20120222
	private MobCcsPaymentUpfrontService paymentUpfrontService;
    public MobCcsPaymentUpfrontService getPaymentUpfrontService() {
		return paymentUpfrontService;
	}
	public void setPaymentUpfrontService(
			MobCcsPaymentUpfrontService paymentUpfrontService) {
		this.paymentUpfrontService = paymentUpfrontService;
	}

	public MobCcsPaymentUpFrontController() {
    }

    public Object formBackingObject(HttpServletRequest request)
	    throws ServletException {

	PaymentUpFrontUI paymentUpFront = new PaymentUpFrontUI();
	paymentUpFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
	BasketDTO basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
	BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
	/*String[] vasItem=(String[]) request.getSession().getAttribute("selectedVasItemList");*/
	String upfrontAmt = basketDto.getUpfrontAmt();
	/*if (vasItem!=null){
		for (int i= 0 ; i<vasItem.length;i++){
			upfrontAmt += paymentUpfrontService.getStandaloneHandSetPaymentUpfront(vasItem[i]);
		}
	}*/
	//String creditCardInd = basketDto.getCreditCardInd();
	String upfrontCCInd = basketDto.getUpfrontCCInd();
	String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
	String appDate = (String) request.getSession().getAttribute("appDate");
	Double vasHSAmt = vasDetailService.getVasHSAmt(selectedItemList, StringUtils.isNotBlank(appDate) ? appDate : Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT));
	Double totalHSAmt = Double.parseDouble(upfrontAmt) + vasHSAmt;
	CustomerProfileDTO  customerProfile = (CustomerProfileDTO ) MobCcsSessionUtil.getSession(request,"customer");
	//logger.info("basket:"+basketDto.getBasketId()+", upFrontAmt:"+upfrontAmt + ", CREDIT_CARD_IND:" + creditCardInd);
	logger.info("basket:"+basketDto.getBasketId()+", upFrontAmt:"+upfrontAmt + ", UPFRONT_CC_IND:" + upfrontCCInd);
	List<IssueBankDTO> creditCardInstBankList  = paymentUpfrontService.getCreditCardInstBankList(String.valueOf(totalHSAmt), salesUserDto.getChannelId());
	
	if (paymentUpFront != null && paymentUpFront.getCeksSubmit() != null
		&& !"".equals(paymentUpFront.getCeksSubmit().trim())
		&& "Y".equals(paymentUpFront.getCeksSubmit().trim())) {

	    paymentUpFront.setCeksSubmit("N");
	    
	} else {
	    paymentUpFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
	    if (paymentUpFront == null) {

			paymentUpFront = new PaymentUpFrontUI();
			paymentUpFront.setPayMethodType("C");
			//paymentUpFront.setUpfrontAmt(upfrontAmt);
			paymentUpFront.setUpfrontAmt(totalHSAmt.toString());
			//paymentUpFront.setCreditCardInd(creditCardInd);
			paymentUpFront.setUpfrontCCInd(upfrontCCInd);
			paymentUpFront.setCcLockFlag("N");
			paymentUpFront.setCcFlag("Y");
			
			if (creditCardInstBankList.size() > 0)
				paymentUpFront.setCcInstScheduleFlag("Y");
			else
				paymentUpFront.setCcInstScheduleFlag("N");
			//paymentUpFront.setAutopayApplDateStr(Utility.date2String(new Date(), "dd/MM/yyyy"));
			logger.info("paymentDto PayMethodType: " + paymentUpFront.getPayMethodType());
			
			if (customerProfile != null) {
		
			    if ("BS".equals(customerProfile.getIdDocType()))
				paymentUpFront.setCreditCardHolderName(customerProfile.getCompanyName());
			    else
				paymentUpFront.setCreditCardHolderName(customerProfile.getContactName());
			    

	//		    	paymentUpFront.setCreditCardDocType(customerProfile.getIdDocType());
	//		    	paymentUpFront.setCreditCardDocNum(customerProfile.getIdDocNum());
	//
	//		    if ("BS".equals(customerProfile.getIdDocType()))
	//			paymentUpFront.setBankAcctHolderName(customerProfile.getCompanyName());
	//		    else
	//			paymentUpFront.setBankAcctHolderName(customerProfile.getContactName());
	//		    	paymentUpFront.setBankAcctHolderIdType(customerProfile.getIdDocType());
	//		    	paymentUpFront.setBankAcctHolderIdNum(customerProfile.getIdDocNum());
			}
	    } //
	    //------------------------
	    else if (paymentUpFront != null && paymentUpFront.isByPassValidation()==true){
	    	//CustomerProfileDTO  customerProfile = (CustomerProfileDTO ) MobCcsSessionUtil.getSession(request,"customer");
			if (customerProfile != null) {
		
			    if ("BS".equals(customerProfile.getIdDocType()))
				paymentUpFront.setCreditCardHolderName(customerProfile.getCompanyName());
			    else
				paymentUpFront.setCreditCardHolderName(customerProfile.getContactName());
			}
	    	
	    	
	    }
	    //-----------------------
	    
	    else {
	  
	    	paymentUpFront.setCcInstScheduleFlag("Y");
	    	paymentUpFront.setCcFlag("Y");
	    	//M - Cash, C - Credit Card, I Credit Card Installment
	    	String workStatus = (String) MobCcsSessionUtil.getSession(request, "workStatus");
	    	int ccCnt = paymentUpfrontService.getMobCcsPaymentTransCCCntByOrderId(paymentUpFront.getOrderId());
	    	if (workStatus != null){
	    		paymentUpFront.setWorkStatus(workStatus);
	    	}	    	
			//paymentUpFront.setUpfrontAmt(upfrontAmt);
			paymentUpFront.setUpfrontAmt(totalHSAmt.toString());
			//paymentUpFront.setCreditCardInd(creditCardInd);
			paymentUpFront.setUpfrontCCInd(upfrontCCInd);
			logger.debug("workStatus:"+ workStatus +"; ccCnt:" +ccCnt);
			
			//Draft order recall, change the default cash to CreditCard Payment
			/*if (creditCardInd != null && !creditCardInd.trim().equalsIgnoreCase("")) {
				if (paymentUpFront.getPayMethodType() == null || paymentUpFront.getPayMethodType().equalsIgnoreCase("M")){
					paymentUpFront.setPayMethodType("C");
				}
			}*/
			if ("Y".equals(upfrontCCInd)) {
				if (paymentUpFront.getPayMethodType() == null || paymentUpFront.getPayMethodType().equalsIgnoreCase("M")){
					paymentUpFront.setPayMethodType("C");
				}
			}
			
			//If the payment of Credit Card is settled, user cannot change the payment details
			if (ccCnt > 0 && workStatus.equalsIgnoreCase("recall")) {
				paymentUpFront.setCcLockFlag("Y");
				String bankCode = paymentUpFront.getCreditCardIssueBankCd();
				//System.out.println("bankCode:"+bankCode);
				List<CreditCardInstDTO> creditCardInstList = paymentUpfrontService.getCreditCardInstList(bankCode, String.valueOf(totalHSAmt), salesUserDto.getChannelId());
				//add by Herbert 20120424
				//System.out.println("creditCardInstList:"+creditCardInstList.size());
				if (creditCardInstList.size() < 1){
					paymentUpFront.setRecallCCInstWarnInd("Y");
				} else {
					paymentUpFront.setRecallCCInstWarnInd("N");
				}
			} else {
				paymentUpFront.setCcLockFlag("N");
			}
			
			//Check is there any Installment Bank
			if (creditCardInstBankList.size() > 0)
				paymentUpFront.setCcInstScheduleFlag("Y");
			else
				paymentUpFront.setCcInstScheduleFlag("N");
			
			//default Cash
			if (paymentUpFront.getPayMethodType() == null) {
				paymentUpFront.setPayMethodType("M");
			}
			
			if ( paymentUpFront.getActionType()!= null && paymentUpFront.getActionType().equalsIgnoreCase("REFRESH_SELF")){
				//CustomerProfileDTO customerProfile = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request,"customer");
			    if ("BS".equals(customerProfile.getIdDocType()))
			    	paymentUpFront.setCreditCardHolderName(customerProfile.getCompanyName());
			    else
			    	paymentUpFront.setCreditCardHolderName(customerProfile.getCustLastName()
	                                        				+ " "
	                                        				+ customerProfile.getCustFirstName());
		             
			} else {

				if (StringUtils.isBlank(paymentUpFront.getCreditCardHolderName())) {
				//	CustomerProfileDTO customerProfile = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request,"customer");
						
					if ("C".equals(paymentUpFront.getPayMethodType())) {
			
					    if ("BS".equals(customerProfile.getIdDocType()))
					    	paymentUpFront.setCreditCardHolderName(customerProfile.getCompanyName());
					    else
					    	paymentUpFront.setCreditCardHolderName(customerProfile.getCustLastName()
			                                        				+ " "
			                                        				+ customerProfile.getCustFirstName());
					}
					if ("I".equals(paymentUpFront.getPayMethodType())) {
					    if ("BS".equals(customerProfile.getIdDocType()))
					    	paymentUpFront.setCreditCardHolderName(customerProfile.getCompanyName());
					    else
					    	paymentUpFront.setCreditCardHolderName(customerProfile.getCustLastName()
			                                        			+ " "
			                                        			+ customerProfile.getCustFirstName());
					}
				}
			}
	    }

	}
    /**Student Plan set payment method**/
    if (customerProfile.isStudentPlanSubInd()){
    	if (StringUtils.isNotEmpty(paymentUpFront.getOrderId())){
	    	if (!"SBO".equalsIgnoreCase(paymentUpFront.getOrderId().substring(1,4))){
	    		paymentUpFront.setPayMethodType("M");
	        	paymentUpFront.setCcInstScheduleFlag("N");
	        	paymentUpFront.setCcFlag("N");
	    	}
    	}else{
    		paymentUpFront.setPayMethodType("M");
        	paymentUpFront.setCcInstScheduleFlag("N");
        	paymentUpFront.setCcFlag("N");
    	}
    }
    /**Student Plan set payment method**/
	
	paymentUpFront.setValue("appDate", Utility.string2Date(appDate)); // add appDate for validation
	paymentUpFront.setValue("customer", customerProfile);
	return paymentUpFront;
    }

    public ModelAndView onSubmit(HttpServletRequest request,
	    HttpServletResponse response, Object command, BindException errors)
	    throws ServletException {

	PaymentUpFrontUI payment = (PaymentUpFrontUI) command;
	String nextView = null;
	
	
	    
			if("I".equals(payment.getPayMethodType())){
	    		payment.setBankCode("");
	    		payment.setBankName("");
	    		payment.setBranchName("");
	//        	payment.setCreditCardIssueBank(creditCardIssueBank);
	//        	payment.setCreditCardIssueBankCd(creditCardIssueBankCd);
	//        	payment.setCreditCardIssueBankName(creditCardIssueBankName);
	        	payment.setInAdvanceAmount("");
	        	payment.setPaymentCombination("");        	        	 
	    	}
        	 
	    	if("C".equals(payment.getPayMethodType())){
	    		payment.setBankCode("");
	    		payment.setBankName("");
	    		payment.setBranchName(""); 	
	    		payment.setCcInstSchedule("");
	    	}
        	 
    		if ("M".equals(payment.getPayMethodType())){
	    		payment.setBankCode("");
	    		payment.setBankName("");
	    		payment.setBranchName("");
	    		payment.setCeksSubmit("");
	    		payment.setCreditCardHolderName("");
	    		payment.setCreditCardIssueBank("");
	    		payment.setCreditCardIssueBankCd("");
	    		payment.setCreditCardIssueBankName("");
	    		payment.setCreditCardNum("");
	    		payment.setCreditCardType("");
	    		payment.setCreditExpiryMonth("");
	    		payment.setCreditExpiryYear("");
	    		payment.setInAdvanceAmount("");    		
	    		payment.setPaymentCombination("");    		
	    		payment.setThirdPartyInd("");
	    		payment.setCcInstSchedule("");
    	    }	 

	 
	
	    MobCcsSessionUtil.setSession(request, "paymentUpFront", payment);  
	    nextView = (String)request.getAttribute("nextView"); 
	    
	    logger.info("Next View: " + nextView);	
	    nextView = (String) request.getAttribute("nextView");
	
	    logger.info("Bank Code:"+payment.getBankName()+":"+payment.getBankCode() );
	    
		if ("REFRESH_SELF".equalsIgnoreCase(payment.getActionType())) {
			return new ModelAndView(new RedirectView("mobccspaymentupfront.html"));
		}

	return new ModelAndView(new RedirectView(nextView));
    }

    protected Map<String, List<IssueBankDTO>> referenceData(
	    HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap<String, List<String>>();
		// Map<String, List<IssueBankDTO>> referenceData = new HashMap<String,
		// List<IssueBankDTO>>();
	
		//List<IssueBankDTO> issueBankList = service.getIssueBankList();
		//referenceData.put("issueBankList", issueBankList);
	
		List<IssueBankDTO> autopayIssueBankList = LookupTableBean.getInstance().getAutopayIssueBankList();
		referenceData.put("autopayIssueBankList", autopayIssueBankList);
	
		List<BankBranchDTO> autopayBankBranchList = LookupTableBean.getInstance().getAutopayBankBranchList();
		// referenceData.put("autopayBankBranchList", autopayBankBranchList);
		
		//add by herbert 20111228
		List<CodeLkupDTO> paymentCombinationList = codeLkupService.getPayMethodCodeLkup();//.getCodeLkupDTOALLDesc("PAY_METHOD");
		referenceData.put("paymentCombinationList", paymentCombinationList);
		List<CodeLkupDTO> creditCardTypeList = codeLkupService.getCodeLkupDTOALL("CARD_TYPE");
		referenceData.put("creditCardTypeList", creditCardTypeList);
		
		//add by herbert 20120222
		/*String upfrontAmt = "3000";
		String bankCode = "003";
		List<CreditCardInstDTO> creditCardInstList = paymentUpfrontService.getCreditCardInstList(bankCode, upfrontAmt);
		referenceData.put("creditCardInstList", creditCardInstList);*/
		String workStatus = (String) MobCcsSessionUtil.getSession(request, "workStatus");
		PaymentUpFrontUI paymentUpFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		referenceData.put("workStatus", workStatus);
		
		if ((workStatus != null && workStatus.equalsIgnoreCase("recall")) || (paymentUpFront!=null && workStatus ==null)){
			//PaymentUpFrontUI paymentUpFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
			String bankCode = paymentUpFront.getCreditCardIssueBankCd();
			String upfrontAmt = paymentUpFront.getUpfrontAmt();
			logger.info("recall : " + bankCode + ":"+upfrontAmt);
			List<CreditCardInstDTO> creditCardInstList = paymentUpfrontService.getCreditCardInstList(bankCode, upfrontAmt, salesUserDto.getChannelId());
			referenceData.put("creditCardInstList",creditCardInstList);
		}
		//List<IssueBankDTO> creditCardInstBankList = paymentUpfrontService.getCreditCardInstBankList(upfrontAmt);
		//referenceData.put("creditCardInstBankList", creditCardInstBankList);
		/*
		 * Map<String,String> streetCatgList = new
		 * LinkedHashMap<String,String>(); for( int i=0;
		 * i<LookupTableBean.getInstance().getAddressCategoryList().size();
		 * i++){
		 * streetCatgList.put(LookupTableBean.getInstance().getAddressCategoryList
		 * ().get(i).getCategoryCode(),
		 * LookupTableBean.getInstance().getAddressCategoryList
		 * ().get(i).getCategoryDesc()); } referenceData.put("streetCatgList",
		 * streetCatgList);
		 */
	
		referenceData.put("bankScriptArrayString",
				getMobileScriptArrayLine0(autopayBankBranchList)
				+ getMobileScriptArray(autopayBankBranchList)); // test
										// return
										// string
	
		/*PaymentUpFrontUI paymentUpFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
		if (paymentUpFront != null) { // get branchCode
		    String selectedBranchCode = paymentUpFront.getBankCode();
		    referenceData.put("selectedBranchCode", selectedBranchCode);
		}*/
		
		OrderDTO orderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		referenceData.put("orderDTO", orderDTO);	
		
		
		CustomerProfileDTO customerProfile = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request,"customer");
		if (customerProfile.isStudentPlanSubInd()){
			referenceData.put("studentPlanSubInd", "Y");
		}else{
			referenceData.put("studentPlanSubInd", "N");
		}
		
		return referenceData;
    }

    // create java script using Array text
    public String getMobileScriptArray(List<BankBranchDTO> modelList) {
		String result = "";
		String brandId = "";
	
		int j = 1;
	
		for (int i = 0; i < modelList.size(); i++) {
		    BankBranchDTO tempDTO = modelList.get(i);
		    if (brandId != "" && !brandId.equals(tempDTO.getBankCode())) {
			result += "\"]\n";
			j++;
		    }
		    if (!brandId.equals(tempDTO.getBankCode())) {
	
			result += "model[" + j + "]=[\"";
			brandId = tempDTO.getBankCode();
		    }
	
		    if (brandId.equals(tempDTO.getBankCode())) {
			result += tempDTO.getBranchCode() + "|"
				+ tempDTO.getBranchName() + "\",\"";
			brandId = tempDTO.getBankCode();
		    }
		}
	
		if (!"".equals(result)) {
		    result += "\"]\n";
		    ;
		}
	
		result = result.replace(",\"\"]", "]");
		return result;
	  }
	
    // create java script using Array first line text
    public String getMobileScriptArrayLine0(List<BankBranchDTO> modelList) {
	String result = "";
	result += "model[0]=[]\n";
	/*
	 * result +="model[0]=[\"" ;
	 * 
	 * for ( int i=0; i<modelList.size(); i++){ BankBranchDTO tempDTO =
	 * modelList.get(i); result
	 * +=tempDTO.getBranchCode()+"|"+tempDTO.getBranchName()+"\",\""; } if
	 * (!"".equals(result)){ result+="\"]\n";; result
	 * =result.replace(",\"\"]", "]"); }
	 */
	return result;
}
    
}
