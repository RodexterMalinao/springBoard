package com.bomwebportal.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.PaymentService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BomCosOrderWsClient;
import com.bomwebportal.wsclient.exception.WsClientException;

public class PaymentController extends SimpleFormController{
	protected final Log logger = LogFactory.getLog(getClass());
	//M - cashDiv, C - creditCardDiv, A-autoPayDiv
	private PaymentService service;
	//private BomCosWsClient client;
	private BomCosOrderWsClient bomCosOrderWsClient;
	
	public BomCosOrderWsClient getBomCosOrderWsClient() {
		return bomCosOrderWsClient;
	}

	public void setBomCosOrderWsClient(BomCosOrderWsClient bomCosOrderWsClient) {
		this.bomCosOrderWsClient = bomCosOrderWsClient;
	}

	public PaymentService getService() {
		return service;
	}

	public void setService(PaymentService service) {
		this.service = service;
	}

	public PaymentController() {
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		PaymentDTO paymentDto = new PaymentDTO();		

		paymentDto = (PaymentDTO)request.getSession().getAttribute("currentPayment");
		//get the session in customer profile page
		CustomerProfileDTO customerProfile = (CustomerProfileDTO)request.getSession().getAttribute("customer");
		
		BasketDTO basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		String creditCardInd = basketDto.getCreditCardInd();
		
		
		if (paymentDto != null && !"".equals(paymentDto.getCeksSubmit().trim()) 
				&& "Y".equals(paymentDto.getCeksSubmit().trim())) {
			
			paymentDto.setCeksSubmit("N");
			paymentDto.setCreditCardInd(creditCardInd);
		} else {
			paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
			if(paymentDto == null) {
				
				paymentDto = new PaymentDTO();
				if("current".equalsIgnoreCase(customerProfile.getAcctType())){//current account
				try {
					
					bom.mob.schema.javabean.si.springboard.xsd.PaymentDTO bomAcctPaymentDTO = bomCosOrderWsClient.getAcctPayMthd(customerProfile.getAcctNum());
					paymentDto.setPayMethodType(bomAcctPaymentDTO.getPayMethodType());
					if(("C").equalsIgnoreCase(paymentDto.getPayMethodType())){
						paymentDto.setThirdPartyInd(bomAcctPaymentDTO.getThirdPartyInd());
						paymentDto.setCreditCardHolderName(bomAcctPaymentDTO.getCrCardHolderName());
						paymentDto.setCreditCardDocType(bomAcctPaymentDTO.getCrCardIdDocType());
						paymentDto.setCreditCardDocNum(bomAcctPaymentDTO.getCrCardIdDocNum());
						paymentDto.setCreditCardNum(bomAcctPaymentDTO.getCrCardNum().trim());
						paymentDto.setCreditCardType(bomAcctPaymentDTO.getCrCardType());
						String[] dateStrings=bomAcctPaymentDTO.getCrCardExpiryDate().split("/");
						paymentDto.setCreditExpiryMonth(dateStrings[0]);
						paymentDto.setCreditExpiryYear(dateStrings[1]);
						paymentDto.setCreditCardIssueBankCd(bomAcctPaymentDTO.getCrCardIssueBank());
					}
					else if (("A").equalsIgnoreCase(paymentDto.getPayMethodType())){
						paymentDto.setThirdPartyInd(bomAcctPaymentDTO.getThirdPartyInd());
						paymentDto.setBankAcctHolderIdNum(bomAcctPaymentDTO.getBankAcctHolderIdNum());
						paymentDto.setBankAcctHolderIdType(bomAcctPaymentDTO.getBankAcctHolderIdType());
						paymentDto.setBankCode(bomAcctPaymentDTO.getBankCode());
						paymentDto.setBranchCode(bomAcctPaymentDTO.getBranchCode());
						paymentDto.setBankAcctHolderName(bomAcctPaymentDTO.getBankAcctHolderName());
						//paymentDto.setAutopayUpperLimitAmt(bomAcctPaymentDTO.getAutopayUpperLimitAmt());
						//paymentDto.setAutopayUpperLimitAmt(null);
						paymentDto.setBankAcctNum(bomAcctPaymentDTO.getBankAcctNum().trim());
						paymentDto.setAutopayApplDateStr(bomAcctPaymentDTO.getAutopayApplDate());
					}
							
				} catch (WsClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				}else{//new account
					paymentDto = new PaymentDTO();
					paymentDto.setPayMethodType("C");
					paymentDto.setCreditCardInd(creditCardInd);
					paymentDto.setAutopayApplDateStr(Utility.date2String(new Date(), "dd/MM/yyyy"));
					logger.info("paymentDto PayMethodType: " + paymentDto.getPayMethodType());
					
					if(customerProfile!=null){
						//modify by eliot 20110621
						if("BS".equals(customerProfile.getIdDocType())){
							paymentDto.setCreditCardHolderName(customerProfile.getCompanyName());
							paymentDto.setBankAcctHolderName(customerProfile.getCompanyName());
						}
						else{
							paymentDto.setCreditCardHolderName(customerProfile.getContactName());//edit 20110607, b4 is customerProfile.getCustLastName()+" "+customerProfile.getCustFirstName()
							paymentDto.setBankAcctHolderName(customerProfile.getContactName());
						}
						
						paymentDto.setCreditCardDocType(customerProfile.getIdDocType());
						paymentDto.setCreditCardDocNum(customerProfile.getIdDocNum());
						
						paymentDto.setBankAcctHolderIdType(customerProfile.getIdDocType());
						paymentDto.setBankAcctHolderIdNum(customerProfile.getIdDocNum());
							
					}
				}
			}else{
				//M - Cash, C - Credit Card, A-AutoPay
				paymentDto.setCreditCardInd(creditCardInd);
				if (creditCardInd != null && !creditCardInd.trim().equalsIgnoreCase("")) {
					if (paymentDto.getPayMethodType() == null || paymentDto.getPayMethodType().equalsIgnoreCase("M")){
						paymentDto.setPayMethodType("C");
					}
				}
				
				if ("M".equals(paymentDto.getPayMethodType())){
					//modify by eliot 20110621
					if("BS".equals(customerProfile.getIdDocType()))
						paymentDto.setCreditCardHolderName(customerProfile.getCompanyName());
					else
						paymentDto.setCreditCardHolderName(customerProfile.getCustLastName()+" "+customerProfile.getCustFirstName());
					paymentDto.setCreditCardDocType(customerProfile.getIdDocType());
					paymentDto.setCreditCardDocNum(customerProfile.getIdDocNum());
					
					//add 20110603
					//modify by eliot 20110621
					if("BS".equals(customerProfile.getIdDocType()))
						paymentDto.setBankAcctHolderName(customerProfile.getCompanyName());
					else
						paymentDto.setBankAcctHolderName(customerProfile.getCustLastName()+" "+customerProfile.getCustFirstName());
					paymentDto.setBankAcctHolderIdType(customerProfile.getIdDocType());
					paymentDto.setBankAcctHolderIdNum(customerProfile.getIdDocNum());
					paymentDto.setAutopayApplDateStr(Utility.date2String(new Date(), "dd/MM/yyyy"));
	
				}
				if ("C".equals(paymentDto.getPayMethodType())){
					//modify by eliot 20110621
					if("BS".equals(customerProfile.getIdDocType()))
						paymentDto.setBankAcctHolderName(customerProfile.getCompanyName());
					else
						paymentDto.setBankAcctHolderName(customerProfile.getCustLastName()+" "+customerProfile.getCustFirstName());
					paymentDto.setBankAcctHolderIdType(customerProfile.getIdDocType());
					paymentDto.setBankAcctHolderIdNum(customerProfile.getIdDocNum());
					paymentDto.setAutopayApplDateStr(Utility.date2String(new Date(), "dd/MM/yyyy"));
				}
				
				if ("A".equals(paymentDto.getPayMethodType())){
					//modify by eliot 20110621
					if("BS".equals(customerProfile.getIdDocType()))
						paymentDto.setCreditCardHolderName(customerProfile.getCompanyName());
					else
						paymentDto.setCreditCardHolderName(customerProfile.getCustLastName()+" "+customerProfile.getCustFirstName());
					paymentDto.setCreditCardDocType(customerProfile.getIdDocType());
					paymentDto.setCreditCardDocNum(customerProfile.getIdDocNum());
				}

			}		

		}
		
		paymentDto.setValue("customer", customerProfile);
		
		if (customerProfile.isStudentPlanSubInd()){
			if("BS".equals(customerProfile.getIdDocType()))
				paymentDto.setCreditCardHolderName(customerProfile.getCompanyName());
			else
				paymentDto.setCreditCardHolderName(customerProfile.getContactName());
			paymentDto.setCreditCardDocType(customerProfile.getIdDocType());
			paymentDto.setCreditCardDocNum(customerProfile.getIdDocNum());
			
			paymentDto.setThirdPartyInd("N");
		}
		
		return paymentDto;
	}
	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		
		PaymentDTO payment = (PaymentDTO)command;
		String nextView = null;
		
		
		if (payment != null && !"".equals(payment.getCeksSubmit().trim()) 
				&& "Y".equals(payment.getCeksSubmit().trim())) {
			String uid=request.getParameter("sbuid");
			payment.setUid(uid);//set uid
			BomSalesUserDTO salesUser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			String sCeksUrl = service.initialCeksAccess(salesUser.getUsername(), request);
			request.getSession().setAttribute("sCeksUrl", sCeksUrl);
			request.getSession().setAttribute("currentPayment", payment);
			nextView = sCeksUrl;
			
		} else {
			
			//clean data
			if("A".equals(payment.getPayMethodType())){
				
				payment.setCreditCardHolderName("");
				payment.setCreditCardDocType("");
				payment.setCreditCardDocNum("");
				payment.setCreditCardType("");
				payment.setCreditCardNum("");
				payment.setCreditExpiryMonth("");
				payment.setCreditExpiryYear("");
				payment.setCreditCardIssueBankCd("");
				payment.setCreditCardIssueBank("");
				payment.setAutopayApplDate(Utility.string2Date(payment.getAutopayApplDateStr()));

				payment.setCreditCardIssueBankName(""); //add by herbert 20110721
			}
			
			if("C".equals(payment.getPayMethodType())){
				
				payment.setBankAcctHolderIdType("");
				payment.setBankAcctHolderIdNum("");
				payment.setBankCode("");
				payment.setBranchCode("");
				payment.setBankAcctHolderName("");
				//payment.setAutopayUpperLimitAmt("");
				payment.setBankAcctNum("");
				payment.setAutopayApplDateStr("");
				
				

			}
			
			if ("M".equals(payment.getPayMethodType())){
				
				payment.setCreditCardHolderName("");
				payment.setCreditCardDocType("");
				payment.setCreditCardDocNum("");
				payment.setCreditCardType("");
				payment.setCreditCardNum("");
				payment.setCreditExpiryMonth("");
				payment.setCreditExpiryYear("");
				payment.setCreditCardIssueBankCd("");
				payment.setCreditCardIssueBank("");
				
				payment.setCreditCardIssueBankName(""); //add by herbert 20110721
				
				payment.setBankAcctHolderIdType("");
				payment.setBankAcctHolderIdNum("");
				payment.setBankCode("");
				payment.setBranchCode("");
				payment.setBankAcctHolderName("");
				//payment.setAutopayUpperLimitAmt("");
				payment.setBankAcctNum("");
				payment.setAutopayApplDateStr("");
			}
			
			request.getSession().setAttribute("payment", payment);
			nextView = (String)request.getAttribute("nextView");
		}
		logger.info("Next View: " + nextView);
		
		/* test uid*/
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
		String attrUid="";
		attrUid=(String)request.getParameter("sbuid");
		modelAndView.addObject("sbuid", attrUid);
		/* test uid*/
		return modelAndView;
	}	
	
	
	protected Map<String, List<IssueBankDTO>> referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap<String, List<String>>();
		//Map<String, List<IssueBankDTO>> referenceData = new HashMap<String, List<IssueBankDTO>>();
		
		List<IssueBankDTO> issueBankList  = service.getIssueBankList();
		referenceData.put("issueBankList", issueBankList);
		
		List<IssueBankDTO> autopayIssueBankList  = LookupTableBean.getInstance().getAutopayIssueBankList();
		referenceData.put("autopayIssueBankList", autopayIssueBankList);
		
		List<BankBranchDTO> autopayBankBranchList  = LookupTableBean.getInstance().getAutopayBankBranchList();
		//referenceData.put("autopayBankBranchList", autopayBankBranchList);
		
	/*	Map<String,String> streetCatgList = new LinkedHashMap<String,String>();
		for( int i=0; i<LookupTableBean.getInstance().getAddressCategoryList().size(); i++){
			streetCatgList.put(LookupTableBean.getInstance().getAddressCategoryList().get(i).getCategoryCode(), LookupTableBean.getInstance().getAddressCategoryList().get(i).getCategoryDesc());
		}
		referenceData.put("streetCatgList", streetCatgList);*/

		referenceData.put( "bankScriptArrayString", getMobileScriptArrayLine0(autopayBankBranchList)+getMobileScriptArray(autopayBankBranchList)); //test return string
		
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
		if(paymentDto != null) { //get branchCode
			String selectedBranchCode= paymentDto.getBranchCode();
			referenceData.put("selectedBranchCode", selectedBranchCode);
		}
		
		CustomerProfileDTO customerProfile = (CustomerProfileDTO)request.getSession().getAttribute("customer");
		if (customerProfile.isStudentPlanSubInd()){
			referenceData.put("studentPlanSubInd", "Y");
		}else{
			referenceData.put("studentPlanSubInd", "N");
		}
		
		referenceData.put("acctType",customerProfile.getAcctType());
		return referenceData;
	}
	
	 //create java script using Array text
    public String getMobileScriptArray(List<BankBranchDTO> modelList){
    	String result="";
    	String brandId="";
    	
    	int j=1;
    	
    	for ( int i=0; i<modelList.size(); i++){
    		BankBranchDTO tempDTO = modelList.get(i);
			if (brandId!="" && !brandId.equals(tempDTO.getBankCode())){
	    		result +="\"]\n";
	    		j++;
	    	}
	    	if (!brandId.equals(tempDTO.getBankCode())){
	    		
		    	result +="model["+j+"]=[\"" ;
		    	brandId=tempDTO.getBankCode();
	    	}
	    	
	    	if (brandId.equals(tempDTO.getBankCode())){
	    		result +=tempDTO.getBranchCode()+"|"+tempDTO.getBranchName()+"\",\"";
	    		brandId =tempDTO.getBankCode();
	    	}
    	}
    	
    	if (!"".equals(result)){
    		result+="\"]\n";;
	    }
    	
    	result =result.replace(",\"\"]", "]");
    	return result;
    }
    
    //create java script using Array first line text
    public String getMobileScriptArrayLine0(List<BankBranchDTO> modelList){
    	String result="";
    	result +="model[0]=[]\n";
    	/*result +="model[0]=[\"" ;

    	for ( int i=0; i<modelList.size(); i++){
    		BankBranchDTO tempDTO = modelList.get(i);
    		result +=tempDTO.getBranchCode()+"|"+tempDTO.getBranchName()+"\",\"";
    	}
    	if (!"".equals(result)){
		    	result+="\"]\n";;
		    	result =result.replace(",\"\"]", "]");
		}*/
    	return result;
    }
}
