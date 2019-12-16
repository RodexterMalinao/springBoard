package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BankBranchDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.PaymentService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BomCosOrderWsClient;
import com.bomwebportal.wsclient.exception.WsClientException;

public class MobCcsPaymentMonthyController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private BomCosOrderWsClient bomCosOrderWsClient;
	
	public BomCosOrderWsClient getBomCosOrderWsClient() {
		return bomCosOrderWsClient;
	}

	public void setBomCosOrderWsClient(BomCosOrderWsClient bomCosOrderWsClient) {
		this.bomCosOrderWsClient = bomCosOrderWsClient;
	}

	private PaymentService service;

	public PaymentService getService() {
		return service;
	}

	public void setService(PaymentService service) {
		this.service = service;
	}

	// add by add by herbert 20111228
	private CodeLkupService codeLkupService;

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public MobCcsPaymentMonthyController() {
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {

		// get session from hashMap session
		PaymentMonthyUI paymentMonthy = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");
		PaymentUpFrontUI paymentUpFront = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
		BasketDTO basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		String creditCardInd = basketDto.getCreditCardInd();
		String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");

		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		if (sessionCustomer != null && sessionCustomer.isStudentPlanSubInd()) {
			creditCardInd = null;
		}

		if (paymentMonthy != null && paymentMonthy.getCeksSubmit() != null
				&& !"".equals(paymentMonthy.getCeksSubmit().trim())
				&& "Y".equals(paymentMonthy.getCeksSubmit().trim())) {
			paymentMonthy.setCeksSubmit("N");

		} else {
			if (paymentMonthy == null) {
				paymentMonthy = new PaymentMonthyUI();
				
				if (sessionCustomer != null) {

					if ("BS".equals(sessionCustomer.getIdDocType())){
						paymentMonthy.setCreditCardHolderName(sessionCustomer.getCompanyName());
						paymentMonthy.setBankAcctHolderName(sessionCustomer.getCompanyName());
					}
					else{
						paymentMonthy.setCreditCardHolderName(sessionCustomer.getContactName());
						paymentMonthy.setBankAcctHolderName(sessionCustomer.getContactName());
					}
					
					paymentMonthy.setCreditCardDocType(sessionCustomer.getIdDocType());
					paymentMonthy.setCreditCardDocNum(sessionCustomer.getIdDocNum());
					
					paymentMonthy.setBankAcctHolderIdType(sessionCustomer.getIdDocType());
					paymentMonthy.setBankAcctHolderIdNum(sessionCustomer.getIdDocNum());
					
				}
				
				//Start of combine acct
				if("current".equalsIgnoreCase(sessionCustomer.getAcctType())){
				try {
					logger.info("ws value"+bomCosOrderWsClient.getAcctPayMthd(sessionCustomer.getAcctNum()));
					bom.mob.schema.javabean.si.springboard.xsd.PaymentDTO bomAcctPaymentDTO = bomCosOrderWsClient.getAcctPayMthd(sessionCustomer.getAcctNum());
					
					logger.info("paymentDto PayMethodType: in current account " + bomAcctPaymentDTO.getPayMethodType());
					paymentMonthy.setPayMethodType(bomAcctPaymentDTO.getPayMethodType());
					logger.info(paymentMonthy.getPayMethodType());
					if ("A".equals(paymentMonthy.getPayMethodType())) {
						paymentMonthy.setThirdPartyInd(bomAcctPaymentDTO.getThirdPartyInd());
						paymentMonthy.setBankAcctHolderIdType(bomAcctPaymentDTO.getBankAcctHolderIdType());
						paymentMonthy.setBankAcctHolderIdNum(bomAcctPaymentDTO.getBankAcctHolderIdNum());
						paymentMonthy.setBankCode(bomAcctPaymentDTO.getBankCode());
						paymentMonthy.setBranchCode(bomAcctPaymentDTO.getBranchCode());
						paymentMonthy.setBankAcctHolderName(bomAcctPaymentDTO.getBankAcctHolderName());
						//paymentMonthy.setAutopayUpperLimitAmt(bomAcctPaymentDTO.getAutopayUpperLimitAmt());
						paymentMonthy.setBankAcctNum(bomAcctPaymentDTO.getBankAcctNum().trim());
						paymentMonthy.setAutopayApplDateStr(bomAcctPaymentDTO.getAutopayApplDate());						
						if (sessionCustomer.isStudentPlanSubInd()) {
							paymentMonthy.setCreditCardVerifiedInd("Y");
						}
					}
	
					else if ("C".equals(paymentMonthy.getPayMethodType())) {
						paymentMonthy.setThirdPartyInd(bomAcctPaymentDTO.getThirdPartyInd());
						paymentMonthy.setCreditCardHolderName(bomAcctPaymentDTO.getCrCardHolderName());
						paymentMonthy.setCreditCardDocType(bomAcctPaymentDTO.getCrCardIdDocType());
						paymentMonthy.setCreditCardDocNum(bomAcctPaymentDTO.getCrCardIdDocNum());
						paymentMonthy.setCreditCardType(bomAcctPaymentDTO.getCrCardType());
						paymentMonthy.setCreditCardNum(bomAcctPaymentDTO.getCrCardNum().trim());
						String[] dateStrings=bomAcctPaymentDTO.getCrCardExpiryDate().split("/");
						paymentMonthy.setCreditExpiryMonth(dateStrings[0]);
						paymentMonthy.setCreditExpiryYear(dateStrings[1]);
						
						paymentMonthy.setCreditCardIssueBankCd(bomAcctPaymentDTO.getCrCardIssueBank());
					
						
					}
				}catch (WsClientException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				}
				//End of combine acct
				else{
					paymentMonthy = new PaymentMonthyUI();
					paymentMonthy.setmFlag("Y");
					paymentMonthy.setCcFlag("Y");
					paymentMonthy.setaFlag("Y");
					paymentMonthy.setPayMethodType("C");
					paymentMonthy.setCreditCardInd(creditCardInd);
					paymentMonthy.setAutopayApplDateStr(Utility.date2String(new Date(), "dd/MM/yyyy"));
					logger.info("paymentUI PayMethodType: " + paymentMonthy.getPayMethodType());
				}
				
//end of null
			} else if (paymentMonthy != null && paymentMonthy.isByPassValidation() == true) { // add
																								// by
																								// wilson																								// 20120316
				paymentMonthy.setmFlag("Y");
				paymentMonthy.setCcFlag("Y");
				paymentMonthy.setaFlag("Y");
				if (sessionCustomer != null) {

					if ("BS".equals(sessionCustomer.getIdDocType())){
						paymentMonthy.setCreditCardHolderName(sessionCustomer.getCompanyName());
						paymentMonthy.setBankAcctHolderName(sessionCustomer.getCompanyName());
					}
					else{
						paymentMonthy.setCreditCardHolderName(sessionCustomer.getContactName());
						paymentMonthy.setBankAcctHolderName(sessionCustomer.getContactName());
					}
					
					paymentMonthy.setCreditCardDocType(sessionCustomer.getIdDocType());
					paymentMonthy.setCreditCardDocNum(sessionCustomer.getIdDocNum());
					
					paymentMonthy.setBankAcctHolderIdType(sessionCustomer.getIdDocType());
					paymentMonthy.setBankAcctHolderIdNum(sessionCustomer.getIdDocNum());
				}

			} else { // not null
				paymentMonthy.setCreditCardInd(creditCardInd);
				paymentMonthy.setmFlag("Y");
				paymentMonthy.setCcFlag("Y");
				paymentMonthy.setaFlag("Y");

				if (paymentMonthy.getActionType() != null
						&& paymentMonthy.getActionType().equalsIgnoreCase("REFRESH")) {
					// Copy the Credit Card information from paymentUpFront
					paymentMonthy.setThirdPartyInd(paymentUpFront.getThirdPartyInd());
					if ("Y".equals(paymentUpFront.getThirdPartyInd())) {// if
																		// ThirdPartyInd==Y,
																		// clear
																		// the
																		// CreditCardDocNum
						paymentMonthy.setCreditCardDocNum("");
					}
					paymentMonthy.setCreditCardType(paymentUpFront.getCreditCardType());
					paymentMonthy.setCreditCardHolderName(paymentUpFront.getCreditCardHolderName());
					paymentMonthy.setCreditCardIssueBank(paymentUpFront.getCreditCardIssueBank());
					paymentMonthy.setCreditCardIssueBankCd(paymentUpFront.getCreditCardIssueBankCd());
					paymentMonthy.setCreditCardIssueBankName(paymentUpFront.getCreditCardIssueBankName());
					paymentMonthy.setCreditCardNum(paymentUpFront.getCreditCardNum());
					paymentMonthy.setCreditExpiryMonth(paymentUpFront.getCreditExpiryMonth());
					paymentMonthy.setCreditExpiryYear(paymentUpFront.getCreditExpiryYear());
				} else if (paymentMonthy.getActionType() != null
						&& paymentMonthy.getActionType().equalsIgnoreCase("REFRESH_SELF")) {
					// Copy the Credit Card information from Customer
					// Information

					if ("BS".equals(sessionCustomer.getIdDocType()))
						paymentMonthy.setCreditCardHolderName(sessionCustomer.getCompanyName());
					else
						paymentMonthy.setCreditCardHolderName(
								sessionCustomer.getCustLastName() + " " + sessionCustomer.getCustFirstName());
					paymentMonthy.setCreditCardDocType(sessionCustomer.getIdDocType());
					paymentMonthy.setCreditCardDocNum(sessionCustomer.getIdDocNum());

					if ("BS".equals(sessionCustomer.getIdDocType()))
						paymentMonthy.setBankAcctHolderName(sessionCustomer.getCompanyName());
					else
						paymentMonthy.setBankAcctHolderName(
								sessionCustomer.getCustLastName() + " " + sessionCustomer.getCustFirstName());
					paymentMonthy.setBankAcctHolderIdType(sessionCustomer.getIdDocType());
					paymentMonthy.setBankAcctHolderIdNum(sessionCustomer.getIdDocNum());

				} else {// Enter the page or Submit the form
					// M - Cash, C - Credit Card, A-AutoPay

					// default Cash
					if (paymentMonthy.getPayMethodType() == null) {
						paymentMonthy.setPayMethodType("M");
					}

					// Draft order recall, change the default cash to CreditCard
					// Payment
					if (creditCardInd != null && !creditCardInd.trim().equalsIgnoreCase("")) {
						if (paymentMonthy.getPayMethodType() == null
								|| paymentMonthy.getPayMethodType().equalsIgnoreCase("M")) {
							paymentMonthy.setPayMethodType("C");
						}
					}

					if ("M".equals(paymentMonthy.getPayMethodType())) {

						if ("BS".equals(sessionCustomer.getIdDocType()))
							paymentMonthy.setCreditCardHolderName(sessionCustomer.getCompanyName());
						else
							paymentMonthy.setCreditCardHolderName(
									sessionCustomer.getCustLastName() + " " + sessionCustomer.getCustFirstName());
						paymentMonthy.setCreditCardDocType(sessionCustomer.getIdDocType());
						paymentMonthy.setCreditCardDocNum(sessionCustomer.getIdDocNum());

						if ("BS".equals(sessionCustomer.getIdDocType()))
							paymentMonthy.setBankAcctHolderName(sessionCustomer.getCompanyName());
						else
							paymentMonthy.setBankAcctHolderName(
									sessionCustomer.getCustLastName() + " " + sessionCustomer.getCustFirstName());
						paymentMonthy.setBankAcctHolderIdType(sessionCustomer.getIdDocType());
						paymentMonthy.setBankAcctHolderIdNum(sessionCustomer.getIdDocNum());
						paymentMonthy.setAutopayApplDateStr(Utility.date2String(new Date(), "dd/MM/yyyy"));

					}
					if ("C".equals(paymentMonthy.getPayMethodType())) {

						if ("BS".equals(sessionCustomer.getIdDocType()))
							paymentMonthy.setBankAcctHolderName(sessionCustomer.getCompanyName());
						else
							paymentMonthy.setBankAcctHolderName(
									sessionCustomer.getCustLastName() + " " + sessionCustomer.getCustFirstName());
						paymentMonthy.setBankAcctHolderIdType(sessionCustomer.getIdDocType());
						paymentMonthy.setBankAcctHolderIdNum(sessionCustomer.getIdDocNum());
						paymentMonthy.setAutopayApplDateStr(Utility.date2String(new Date(), "dd/MM/yyyy"));
					}

					if ("A".equals(paymentMonthy.getPayMethodType())) {

						if ("BS".equals(sessionCustomer.getIdDocType()))
							paymentMonthy.setCreditCardHolderName(sessionCustomer.getCompanyName());
						else
							paymentMonthy.setCreditCardHolderName(
									sessionCustomer.getCustLastName() + " " + sessionCustomer.getCustFirstName());
						paymentMonthy.setCreditCardDocType(sessionCustomer.getIdDocType());
						paymentMonthy.setCreditCardDocNum(sessionCustomer.getIdDocNum());
					}

				}

			}

		}
		/** Student Plan **/
		boolean tapAndGoCardInd = false;
		if (sessionCustomer != null && sessionCustomer.isStudentPlanSubInd()) {
			List<CodeLkupDTO> codeLkupList = codeLkupService.getCodeLkupDTOALL("TAP_AND_GO_CARD_CD");

			if (codeLkupList.size() > 0) {
				for (int i = 0; i < codeLkupList.size(); i++) {
					if (ArrayUtils.isEmpty(selectedItemList) || selectedItemList.length == 0) {
						paymentMonthy.setPayMethodType("C");
						paymentMonthy.setmFlag("N");
						paymentMonthy.setaFlag("N");
						paymentMonthy.setValue("tapAndGoCardInd", "N");
					} else {
						if (tapAndGoCardInd == false) {
							for (int j = 0; j < selectedItemList.length; j++) {
								if (codeLkupList.get(i).getCodeId().equals(selectedItemList[j])) {
									paymentMonthy.setPayMethodType("M");
									paymentMonthy.setCcFlag("N");
									paymentMonthy.setaFlag("N");
									paymentMonthy.setValue("tapAndGoCardInd", "Y");
									tapAndGoCardInd = true;
									break;
								}
							}
						}
					}
				}
			}
			if (tapAndGoCardInd == false){
					paymentMonthy.setPayMethodType("C");
					paymentMonthy.setmFlag("N");
					paymentMonthy.setaFlag("N");
					paymentMonthy.setValue("tapAndGoCardInd", "N");
			}
		}
		String appDate = (String) request.getSession().getAttribute("appDate");// add
																				// appDate
																				// for
																				// validation
		paymentMonthy.setValue("appDate", Utility.string2Date(appDate)); // add
																			// appDate
																			// for
																			// validation
		paymentMonthy.setValue("customer", sessionCustomer);

		if (sessionCustomer.isStudentPlanSubInd()) {
			if ("BS".equals(sessionCustomer.getIdDocType()))
				paymentMonthy.setCreditCardHolderName(sessionCustomer.getCompanyName());
			else
				paymentMonthy.setCreditCardHolderName(sessionCustomer.getContactName());
			paymentMonthy.setCreditCardDocType(sessionCustomer.getIdDocType());
			paymentMonthy.setCreditCardDocNum(sessionCustomer.getIdDocNum());

			paymentMonthy.setThirdPartyInd("N");
		}

		return paymentMonthy;
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws ServletException {

		PaymentMonthyUI payment = (PaymentMonthyUI) command;
		String nextView = null;
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		/*
		 * if (payment != null && !"".equals(payment.getCeksSubmit().trim()) &&
		 * "Y".equals(payment.getCeksSubmit().trim())) { // BomSalesUserDTO
		 * salesUser = //
		 * (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		 * // String sCeksUrl =
		 * service.initialCeksAccess(salesUser.getUsername()); //
		 * request.getSession().setAttribute("sCeksUrl", sCeksUrl); //
		 * request.getSession().setAttribute("currentPayment", payment); //
		 * nextView = sCeksUrl;
		 * 
		 * } else {
		 */
		if ("A".equals(payment.getPayMethodType())) {
			payment.setCreditCardHolderName("");
			payment.setCreditCardDocType("");
			payment.setCreditCardDocNum("");
			payment.setCreditCardType("");
			payment.setCreditCardNum("");
			payment.setCreditExpiryMonth("");
			payment.setCreditExpiryYear("");
			payment.setCreditCardIssueBankCd("");
			payment.setCreditCardIssueBank("");
			// payment.setAutopayApplDate(Utility.string2Date(payment.getAutopayApplDateStr()));
			payment.setCreditCardIssueBankName(""); // add by herbert 20110721 }
			payment.setCreditCardVerifiedInd("");
		}

		if ("C".equals(payment.getPayMethodType())) {

			payment.setBankAcctHolderIdType("");
			payment.setBankAcctHolderIdNum("");
			payment.setBankCode("");
			payment.setBranchCode("");
			payment.setBankAcctHolderName("");
			//payment.setAutopayUpperLimitAmt("");
			payment.setBankAcctNum("");
			payment.setAutopayApplDateStr("");
			payment.setBranchName("");
			if (sessionCustomer.isStudentPlanSubInd()) {
				payment.setCreditCardVerifiedInd("Y");
			}
		}

		if ("M".equals(payment.getPayMethodType())) {

			payment.setCreditCardHolderName("");
			payment.setCreditCardDocType("");
			payment.setCreditCardDocNum("");
			payment.setCreditCardType("");
			payment.setCreditCardNum("");
			payment.setCreditExpiryMonth("");
			payment.setCreditExpiryYear("");
			payment.setCreditCardIssueBankCd("");
			payment.setCreditCardIssueBank("");
			payment.setCreditCardIssueBankName("");
			payment.setBankAcctHolderIdType("");
			payment.setBankAcctHolderIdNum("");
			payment.setBankCode("");
			payment.setBranchCode("");
			payment.setBankAcctHolderName("");
			//payment.setAutopayUpperLimitAmt("");
			payment.setBankAcctNum("");
			payment.setAutopayApplDateStr("");
			payment.setCreditCardVerifiedInd("");
			payment.setThirdPartyInd("");
			payment.setBankName("");
			payment.setBranchName("");
		}

		// }
		// System.out.println("On Submit:" + payment.getBankCode() + ";" +
		// payment.getBranchCode());
		MobCcsSessionUtil.setSession(request, "paymentMonthy", payment);
		nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);

		if ("REFRESH".equalsIgnoreCase(payment.getActionType())
				|| "REFRESH_SELF".equalsIgnoreCase(payment.getActionType())) {
			return new ModelAndView(new RedirectView("mobccspaymentmonthy.html"));
		}

		return new ModelAndView(new RedirectView(nextView));
	}

	protected Map<String, List<IssueBankDTO>> referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap<String, List<String>>();
		// Map<String, List<IssueBankDTO>> referenceData = new HashMap<String,
		// List<IssueBankDTO>>();

		List<IssueBankDTO> issueBankList = service.getIssueBankList();
		referenceData.put("issueBankList", issueBankList);

		List<IssueBankDTO> autopayIssueBankList = LookupTableBean.getInstance().getAutopayIssueBankList();
		referenceData.put("autopayIssueBankList", autopayIssueBankList);

		List<BankBranchDTO> autopayBankBranchList = LookupTableBean.getInstance().getAutopayBankBranchList();
		// referenceData.put("autopayBankBranchList", autopayBankBranchList);

		// add by herbert 20111228
		List<CodeLkupDTO> paymentCombinationList = codeLkupService.getCodeLkupDTOALL("PAY_METHOD");
		referenceData.put("paymentCombinationList", paymentCombinationList);
		List<CodeLkupDTO> creditCardTypeList = codeLkupService.getCodeLkupDTOALL("CARD_TYPE");
		referenceData.put("creditCardTypeList", creditCardTypeList);

		/*
		 * Map<String,String> streetCatgList = new
		 * LinkedHashMap<String,String>(); for( int i=0;
		 * i<LookupTableBean.getInstance().getAddressCategoryList().size();
		 * i++){ streetCatgList.put(LookupTableBean.getInstance().
		 * getAddressCategoryList ().get(i).getCategoryCode(),
		 * LookupTableBean.getInstance().getAddressCategoryList
		 * ().get(i).getCategoryDesc()); } referenceData.put("streetCatgList",
		 * streetCatgList);
		 */

		referenceData.put("bankScriptArrayString",
				getMobileScriptArrayLine0(autopayBankBranchList) + getMobileScriptArray(autopayBankBranchList));

		PaymentMonthyUI paymentMonthy = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");
		if (paymentMonthy != null) { // get branchCode
			String selectedBranchCode = paymentMonthy.getBranchCode();
			referenceData.put("selectedBranchCode", selectedBranchCode);
		}

		OrderDTO orderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		referenceData.put("orderDTO", orderDTO);

		referenceData.put("workStatus", MobCcsSessionUtil.getSession(request, "workStatus"));

		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		if (sessionCustomer.isStudentPlanSubInd()) {
			referenceData.put("studentPlanSubInd", "Y");
		} else {
			referenceData.put("studentPlanSubInd", "N");
		}
		referenceData.put("acctType",sessionCustomer.getAcctType());
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
				result += tempDTO.getBranchCode() + "|" + tempDTO.getBranchName() + "\",\"";
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
