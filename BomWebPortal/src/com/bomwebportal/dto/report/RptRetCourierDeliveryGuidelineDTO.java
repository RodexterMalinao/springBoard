package com.bomwebportal.dto.report;

import java.util.List;

import com.bomwebportal.dto.MultipleMrtSimDTO;

public class RptRetCourierDeliveryGuidelineDTO extends ReportDTO {
	    
	    /**
	     * 
	     */
	    private static final long serialVersionUID = 2437795497544555557L;
	    
	    public static final String JASPER_TEMPLATE_CG = "MobRetCourierGuideline";

		public RptRetCourierDeliveryGuidelineDTO(){
			this.mnpInd = false;
			this.ssFormNewMrtInd = false;
			this.ssFormRenewalInd = false;
			this.neFormInd = false;
			this.ownershipFormInd = false;
			this.idDocCopy = false;
			this.addressProof = false;
			this.thirdPartyCreditCardAuthorization = false;
			this.thirdPartyCreditCardHolderIDDocumentCopy = false;
			this.mnpApplicationForm = false;
			this.creditCardCopy = false;
			this.studentCardCopy = false;
			this.changeOfServiceForm = false;
			this.validForeignDomesticHelperContractCopy = false;
			this.prepaidSIMcopy = false;
			this.prepaidSIMcopy = false;
			this.conciergeServiceAuthorizationLetter = false;
			this.authorizationLetterfor3rdPartycollection = false;
			this.authorizationLetterAndIDCollection = false;
			this.brCustomerBusinessNameCardOfContactPerson = false;
			this.brCustomerBusinessNameCardStaffCopy = false;
			this.twoNRegisterHKIDCopy = false; //20130827
			this.upFrontPaymentByCreditCard = false;
			this.throughDelivery = false;
			this.throughPickUp = false;
			this.haveMultiSim = false;
			this.brCust = false;
			this.receiveCopy = false;
			this.bankNameChi = "";
			this.mobileSafetyForm = false;
			this.setHasNFCSimForm(false);
			this.setHasOctopusSimForm(false);
			this.tradeInHSForm = false;
			this.noOfSign = "";
			this.setiGuardFormLDS(false);
			this.setiGuardFormAD(false);
			this.tngServiceFlag = false;
			this.yahooCouponSerial ="";
			this.yahooCouponInd = false;
			this.studentPlanFlag = false;
		 	this.hkbnInd=false;
		 	this.transcriptCopy=false;
	    }
	    
	    private String orderId;
	    private String iccid;
	    private String imei;
	    private String deliveryDayStr;
	    private String custIdDocNum;
	    private String custIdDocType;
	    private boolean mnpInd;
	    private boolean ssFormNewMrtInd;
	    private boolean ssFormRenewalInd;
	    private boolean neFormInd;
	    private boolean ownershipFormInd;
	    private boolean changeOfMobileNumInd;
	    private boolean idDocCopy;
	    private boolean addressProof;
	    private boolean thirdPartyCreditCardAuthorization;
	    private boolean thirdPartyCreditCardHolderIDDocumentCopy;
	    //private boolean creditCardInstallmentForm;
	    private boolean mnpApplicationForm;
	    //private boolean staffCopy;
	    private boolean creditCardCopy;
	    private boolean studentCardCopy;
	    private boolean changeOfServiceForm;
	    private boolean validForeignDomesticHelperContractCopy;
	    private boolean prepaidSIMcopy;
	    private boolean conciergeServiceAuthorizationLetter;
	    private boolean authorizationLetterfor3rdPartycollection;
	    private boolean brCustomerBusinessNameCardOfContactPerson;
	    private boolean brCustomerBusinessNameCardStaffCopy;
	    private boolean twoNRegisterHKIDCopy;  //20130827
	    private Double cash;
	    private boolean cashInd;
	    private boolean authorizationLetterAndIDCollection;
	    private String authorizationLetterName;
	    private String authorizationLetterContractNum;
	    //add by Eliot 20120426
	    private boolean upFrontPaymentByCreditCard;
	    private boolean throughDelivery;
	    private boolean throughPickUp;
	 	private boolean haveMultiSim;
	 	private List<MultipleMrtSimDTO> multipleMrtSimListOdd;
	 	private List<MultipleMrtSimDTO> multipleMrtSimListEven;
	 	private String deliveryContactName;
	 	private String deliveryContactNum;
	 	private String deliverySecondContactNum;
	 	private boolean brCust; // BS equals to id doc type
	 	private String customerContactName;
	 	private boolean iGuardForm;
	 	private boolean iGuardFormLDS;
	 	private boolean iGuardFormAD;
	 	private boolean mobileSafetyForm;
	 	private boolean hasNFCSimForm;
	 	private boolean hasOctopusSimForm;
	 	private boolean tradeInHSForm;
	 	private boolean receiveCopy;
	 	private String bankNameChi;
	 	private String noOfSign;
	 	private boolean ddaOSForm;
	 	private String octFlag; //Athena 20130923
	 	
	 	private String contactType;
	 	private String bsCustomerContactName;
	 	private String bsDeliveryContactName;
	 	
	 	private boolean tngServiceFlag;
	 	private String yahooCouponSerial;
	 	private boolean yahooCouponInd;
	 	
	 	private boolean studentPlanFlag;
	 	private boolean hkbnInd;
	 	private boolean jPrefixTngServiceInd;
	 	private boolean transcriptCopy;

		public boolean isiGuardFormLDS() {
			return iGuardFormLDS;
		}
		public void setiGuardFormLDS(boolean iGuardFormLDS) {
			this.iGuardFormLDS = iGuardFormLDS;
		}
		public boolean isiGuardFormAD() {
			return iGuardFormAD;
		}
		public void setiGuardFormAD(boolean iGuardFormAD) {
			this.iGuardFormAD = iGuardFormAD;
		}
		public String getOrderId() {
	        return orderId;
	    }
	    public void setOrderId(String orderId) {
	        this.orderId = orderId;
	    }
	    public String getIccid() {
	        return iccid;
	    }
	    public void setIccid(String iccid) {
	        this.iccid = iccid;
	    }
	    public String getImei() {
	        return imei;
	    }
	    public void setImei(String imei) {
	        this.imei = imei;
	    }
	    public String getDeliveryDayStr() {
	        return deliveryDayStr;
	    }
	    public void setDeliveryDayStr(String deliveryDayStr) {
	        this.deliveryDayStr = deliveryDayStr;
	    }
	    public boolean isMnpInd() {
	        return mnpInd;
	    }
	    public void setMnpInd(boolean mnpInd) {
	        this.mnpInd = mnpInd;
	    }
	    public boolean isSsFormNewMrtInd() {
	        return ssFormNewMrtInd;
	    }
	    public void setSsFormNewMrtInd(boolean ssFormNewMrtInd) {
	        this.ssFormNewMrtInd = ssFormNewMrtInd;
	    }
	    public boolean isSsFormRenewalInd() {
	        return ssFormRenewalInd;
	    }
	    public void setSsFormRenewalInd(boolean ssFormRenewalInd) {
	        this.ssFormRenewalInd = ssFormRenewalInd;
	    }
	    public boolean isNeFormInd() {
	        return neFormInd;
	    }
	    public void setNeFormInd(boolean neFormInd) {
	        this.neFormInd = neFormInd;
	    }
	    public boolean isOwnershipFormInd() {
	        return ownershipFormInd;
	    }
	    public void setOwnershipFormInd(boolean ownershipFormInd) {
	        this.ownershipFormInd = ownershipFormInd;
	    }
	    public boolean isIdDocCopy() {
	        return idDocCopy;
	    }
	    public void setIdDocCopy(boolean idDocCopy) {
	        this.idDocCopy = idDocCopy;
	    }
	    public boolean isAddressProof() {
	        return addressProof;
	    }
	    public void setAddressProof(boolean addressProof) {
	        this.addressProof = addressProof;
	    }
	    public boolean isThirdPartyCreditCardAuthorization() {
	        return thirdPartyCreditCardAuthorization;
	    }
	    public void setThirdPartyCreditCardAuthorization(
	    	boolean thirdPartyCreditCardAuthorization) {
	        this.thirdPartyCreditCardAuthorization = thirdPartyCreditCardAuthorization;
	    }
	    public boolean isThirdPartyCreditCardHolderIDDocumentCopy() {
	        return thirdPartyCreditCardHolderIDDocumentCopy;
	    }
	    public void setThirdPartyCreditCardHolderIDDocumentCopy(
	    	boolean thirdPartyCreditCardHolderIDDocumentCopy) {
	        this.thirdPartyCreditCardHolderIDDocumentCopy = thirdPartyCreditCardHolderIDDocumentCopy;
	    }   
	    public boolean isMnpApplicationForm() {
	        return mnpApplicationForm;
	    }
	    public void setMnpApplicationForm(boolean mnpApplicationForm) {
	        this.mnpApplicationForm = mnpApplicationForm;
	    }   
	    public boolean isStudentCardCopy() {
	        return studentCardCopy;
	    }
	    public void setStudentCardCopy(boolean studentCardCopy) {
	        this.studentCardCopy = studentCardCopy;
	    }
	    public boolean isChangeOfServiceForm() {
	        return changeOfServiceForm;
	    }
	    public void setChangeOfServiceForm(boolean changeOfServiceForm) {
	        this.changeOfServiceForm = changeOfServiceForm;
	    }
	    public boolean isValidForeignDomesticHelperContractCopy() {
	        return validForeignDomesticHelperContractCopy;
	    }
	    public void setValidForeignDomesticHelperContractCopy(
	    	boolean validForeignDomesticHelperContractCopy) {
	        this.validForeignDomesticHelperContractCopy = validForeignDomesticHelperContractCopy;
	    }
	    public boolean isPrepaidSIMcopy() {
	        return prepaidSIMcopy;
	    }
	    public void setPrepaidSIMcopy(boolean prepaidSIMcopy) {
	        this.prepaidSIMcopy = prepaidSIMcopy;
	    }
	    public boolean isConciergeServiceAuthorizationLetter() {
	        return conciergeServiceAuthorizationLetter;
	    }
	    public void setConciergeServiceAuthorizationLetter(
	    	boolean conciergeServiceAuthorizationLetter) {
	        this.conciergeServiceAuthorizationLetter = conciergeServiceAuthorizationLetter;
	    }
	    public boolean isAuthorizationLetterfor3rdPartycollection() {
	        return authorizationLetterfor3rdPartycollection;
	    }
	    public void setAuthorizationLetterfor3rdPartycollection(
	    	boolean authorizationLetterfor3rdPartycollection) {
	        this.authorizationLetterfor3rdPartycollection = authorizationLetterfor3rdPartycollection;
	    }
	    public boolean isBrCustomerBusinessNameCardOfContactPerson() {
	        return brCustomerBusinessNameCardOfContactPerson;
	    }
	    public void setBrCustomerBusinessNameCardOfContactPerson(
	    	boolean brCustomerBusinessNameCardOfContactPerson) {
	        this.brCustomerBusinessNameCardOfContactPerson = brCustomerBusinessNameCardOfContactPerson;
	    }
	    public boolean isBrCustomerBusinessNameCardStaffCopy() {
	        return brCustomerBusinessNameCardStaffCopy;
	    }
	    public void setBrCustomerBusinessNameCardStaffCopy(
	    	boolean brCustomerBusinessNameCardStaffCopy) {
	        this.brCustomerBusinessNameCardStaffCopy = brCustomerBusinessNameCardStaffCopy;
	    }
	    public boolean isTwoNRegisterHKIDCopy() {
			return twoNRegisterHKIDCopy;
		}
		public void setTwoNRegisterHKIDCopy(boolean twoNRegisterHKIDCopy) {
			this.twoNRegisterHKIDCopy = twoNRegisterHKIDCopy;
		}
	    public Double getCash() {
	        return cash;
	    }
	    public void setCash(Double cash) {
	        this.cash = cash;
	    }
	    public String getAuthorizationLetterName() {
	        return authorizationLetterName;
	    }
	    public void setAuthorizationLetterName(String authorizationLetterName) {
	        this.authorizationLetterName = authorizationLetterName;
	    }
	    public String getAuthorizationLetterContractNum() {
	        return authorizationLetterContractNum;
	    }
	    public void setAuthorizationLetterContractNum(
	    	String authorizationLetterContractNum) {
	        this.authorizationLetterContractNum = authorizationLetterContractNum;
	    }
	    public boolean isAuthorizationLetterAndIDCollection() {
	        return authorizationLetterAndIDCollection;
	    }
	    public void setAuthorizationLetterAndIDCollection(
	    	boolean authorizationLetterAndIDCollection) {
	        this.authorizationLetterAndIDCollection = authorizationLetterAndIDCollection;
	    }
	    public boolean isCreditCardCopy() {
	        return creditCardCopy;
	    }
	    public void setCreditCardCopy(boolean creditCardCopy) {
	        this.creditCardCopy = creditCardCopy;
	    }
	    public boolean isChangeOfMobileNumInd() {
	        return changeOfMobileNumInd;
	    }
	    public void setChangeOfMobileNumInd(boolean changeOfMobileNumInd) {
	        this.changeOfMobileNumInd = changeOfMobileNumInd;
	    }
	    public boolean isCashInd() {
	        return cashInd;
	    }
	    public void setCashInd(boolean cashInd) {
	        this.cashInd = cashInd;
	    }
		public boolean isUpFrontPaymentByCreditCard() {
			return upFrontPaymentByCreditCard;
		}
		public void setUpFrontPaymentByCreditCard(boolean upFrontPaymentByCreditCard) {
			this.upFrontPaymentByCreditCard = upFrontPaymentByCreditCard;
		}
		public boolean isHaveMultiSim() {
			return haveMultiSim;
		}
		public void setHaveMultiSim(boolean haveMultiSim) {
			this.haveMultiSim = haveMultiSim;
		}
		public String getDeliveryContactName() {
			return deliveryContactName;
		}
		public void setDeliveryContactName(String deliveryContactName) {
			this.deliveryContactName = deliveryContactName;
		}
		public String getDeliveryContactNum() {
			return deliveryContactNum;
		}
		public void setDeliveryContactNum(String deliveryContactNum) {
			this.deliveryContactNum = deliveryContactNum;
		}
		public String getDeliverySecondContactNum() {
			return deliverySecondContactNum;
		}
		public void setDeliverySecondContactNum(String deliverySecondContactNum) {
			this.deliverySecondContactNum = deliverySecondContactNum;
		}
		public String getCustomerContactName() {
			return customerContactName;
		}
		public void setCustomerContactName(String customerContactName) {
			this.customerContactName = customerContactName;
		}
		public boolean isThroughPickUp() {
			return throughPickUp;
		}
		public void setThroughPickUp(boolean throughPickUp) {
			this.throughPickUp = throughPickUp;
		}
		public boolean isThroughDelivery() {
			return throughDelivery;
		}
		public void setThroughDelivery(boolean throughDelivery) {
			this.throughDelivery = throughDelivery;
		}
		public boolean isBrCust() {
			return brCust;
		}
		public void setBrCust(boolean brCust) {
			this.brCust = brCust;
		}
		public List<MultipleMrtSimDTO> getMultipleMrtSimListOdd() {
			return multipleMrtSimListOdd;
		}
		public void setMultipleMrtSimListOdd(List<MultipleMrtSimDTO> multipleMrtSimListOdd) {
			this.multipleMrtSimListOdd = multipleMrtSimListOdd;
		}
		public List<MultipleMrtSimDTO> getMultipleMrtSimListEven() {
			return multipleMrtSimListEven;
		}
		public void setMultipleMrtSimListEven(List<MultipleMrtSimDTO> multipleMrtSimListEven) {
			this.multipleMrtSimListEven = multipleMrtSimListEven;
		}
		public boolean isiGuardForm() {
			return iGuardForm;
		}
		public void setiGuardForm(boolean iGuardForm) {
			this.iGuardForm = iGuardForm;
		}
		public boolean isReceiveCopy() {
			return receiveCopy;
		}
		public void setReceiveCopy(boolean receiveCopy) {
			this.receiveCopy = receiveCopy;
		}
		public String getBankNameChi() {
			return bankNameChi;
		}
		public void setBankNameChi(String bankNameChi) {
			this.bankNameChi = bankNameChi;
		}
		public String getNoOfSign() {
			return noOfSign;
		}
		public void setNoOfSign(String noOfSign) {
			this.noOfSign = noOfSign;
		}
		public boolean isDdaOSForm() {
			return ddaOSForm;
		}
		public void setDdaOSForm(boolean ddaOSForm) {
			this.ddaOSForm = ddaOSForm;
		}
		public boolean isMobileSafetyForm() {
			return mobileSafetyForm;
		}
		public void setMobileSafetyForm(boolean mobileSafetyForm) {
			this.mobileSafetyForm = mobileSafetyForm;
		}
		public boolean isTradeInHSForm() {
			return tradeInHSForm;
		}
		public void setTradeInHSForm(boolean tradeInHSForm) {
			this.tradeInHSForm = tradeInHSForm;
		}
		public boolean isHasNFCSimForm() {
			return hasNFCSimForm;
		}
		public void setHasNFCSimForm(boolean hasNFCSimForm) {
			this.hasNFCSimForm = hasNFCSimForm;
		}
		public boolean isHasOctopusSimForm() {
			return hasOctopusSimForm;
		}
		public void setHasOctopusSimForm(boolean hasOctopusSimForm) {
			this.hasOctopusSimForm = hasOctopusSimForm;
		}
	    public String getOctFlag() {//Athena 20130923
			return octFlag;
		}
		public void setOctFlag(String octFlag) {
			this.octFlag = octFlag;
		}
		public String getContactType() {
			return contactType;
		}
		public void setContactType(String contactType) {
			this.contactType = contactType;
		}
		public String getBsCustomerContactName() {
			return bsCustomerContactName;
		}
		public void setBsCustomerContactName(String bsCustomerContactName) {
			this.bsCustomerContactName = bsCustomerContactName;
		}
		public String getBsDeliveryContactName() {
			return bsDeliveryContactName;
		}
		public void setBsDeliveryContactName(String bsDeliveryContactName) {
			this.bsDeliveryContactName = bsDeliveryContactName;
		}
		public boolean isTngServiceFlag() {
			return tngServiceFlag;
		}
		public void setTngServiceFlag(boolean tngServiceFlag) {
			this.tngServiceFlag = tngServiceFlag;
		}
		public String getYahooCouponSerial() {
			return yahooCouponSerial;
		}
		public void setYahooCouponSerial(String yahooCouponSerial) {
			this.yahooCouponSerial = yahooCouponSerial;
		}
		public boolean isYahooCouponInd() {
			return yahooCouponInd;
		}
		public void setYahooCouponInd(boolean yahooCouponInd) {
			this.yahooCouponInd = yahooCouponInd;
		}
		public boolean isStudentPlanFlag() {
			return studentPlanFlag;
		}
		public void setStudentPlanFlag(boolean studentPlanFlag) {
			this.studentPlanFlag = studentPlanFlag;
		}
		public boolean isHkbnInd() {
			return hkbnInd;
		}
		public void setHkbnInd(boolean hkbnInd) {
			this.hkbnInd = hkbnInd;
		}
		public boolean isjPrefixTngServiceInd() {
			return jPrefixTngServiceInd;
		}
		public void setjPrefixTngServiceInd(boolean jPrefixTngServiceInd) {
			this.jPrefixTngServiceInd = jPrefixTngServiceInd;
		}
		public String getCustIdDocNum() {
			return custIdDocNum;
		}
		public void setCustIdDocNum(String custIdDocNum) {
			this.custIdDocNum = custIdDocNum;
		}
		public String getCustIdDocType() {
			return custIdDocType;
		}
		public void setCustIdDocType(String custIdDocType) {
			this.custIdDocType = custIdDocType;
		}
		public boolean isTranscriptCopy() {
			return transcriptCopy;
		}
		public void setTranscriptCopy(boolean transcriptCopy) {
			this.transcriptCopy = transcriptCopy;
		}
				
}
