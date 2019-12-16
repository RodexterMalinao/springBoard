package com.bomwebportal.dto.report;

import java.util.ArrayList;
import java.util.Date;
import java.io.InputStream;

public class RptAppMobileServDTO extends ReportDTO {
	private static final long serialVersionUID = -3311684335459264341L;
	
	public static final String JASPER_TEMPLATE = "AppMobileServ";	
	public static final String JASPER_TEMPLATE_NE = "AppNEServ";
	
	// Customer Details Refer CustomerProfile	
	private String idDocType;// ID_DOC_TYPE VARCHAR2(4 BYTE),
	private String idDocNum;// ID_DOC_NUM VARCHAR2(30 BYTE)
	private String custFirstName;// FIRST_NAME VARCHAR2(40 BYTE),
	private String custLastName;// LAST_NAME VARCHAR2(40 BYTE),
	private Date dob;
	private String contactPhone;
	//add Eliot 20110722
	private String otherContactPhone;
	private String addrProofInd;
	private String lob;
	private String serviceNum;
	//add Eliot 20110721
	private String title;
	
	//add eliot 20110819
	private String basketType;
	
	//add Eliot 20110620
	private String companyName;
	
	private String customerType;
	private String serviceType;
	private String emailAddr;
	
	//add Eliot 20110707
	private String isConSrv;
	
	private String flat;
	private String floor;
	private String buildingName;
	private String streetNum;
	private String streetName;
	private String sectionDesc;
	private String districtDesc;
	private String streetCatgDesc;
	private String areaCode;
	private String lotHouseInd;
	private String lotNum;
	
	//add Eliot 20110801
	private String locale;
	
	// Reference From MnpDTO
	private String mnpType;
	private String msisdn;
	private Date cutoverDate;
	private Date serviceReqDate;
	//add Eliot 20110721
	private String shopCd;
	
	// Reference From OrderDTO
	private String agreementNum;
	private Date appInDate;
	private String salesCd;
	//add by eliot 20110617
	private String salesName;

	// Reference From PaymentDTO
	private String creditCardType;
	private String creditCardNum;
	private String creditCardNo;
	private String creditCardHolderName;
	private String creditCardDocNum;
	private String thirdPartyInd;
	private String creditExpiryMonth;
	private String creditExpiryYear;
	//add Eliot 20110722
	private String creditCardIssueBankName;
	
	//add Eliot 20110727
	private ArrayList<AdditionalChargeDTO> additionalChargeDtls;
	private ArrayList<MiscellaneousChargeDTO> miscellaneousChargeDtls;
	
	// add by Eliot 20110614 
	// Reference From PaymentDTO
	private String bankAcctHolderIdType; //autoPay
	private String bankAcctHolderIdNum; //autoPay
	private String bankCode; //autoPay
	private String branchCode; //autoPay
	private String bankAcctHolderName; //autoPay
	private String autopayUpperLimitAmt; //autoPay
	private String bankAcctNum;	 //autoPay	
	//M - Cash, C - Credit Card, A-AutoPay
	private String payMethodType;
	
	//add Eliot 20110722
	private String firstMonthServiceLicenceFee;

	// Customer Made Fields
	private ArrayList<RptVasDetailDTO> mainServDtls;
	private ArrayList<RptVasDetailDTO> vasDtls;
	//add eliot 20110726
	private ArrayList<RptVasDetailDTO> vasFreeGifsDtls;
	private ArrayList<RptVasDetailDTO> vasOptionalDtls;
	
	private ArrayList<RptVasDetailDTO> rebateList;

	private String iccid;
	private String handsetDeviceDescription;
	//private String smsBillLanguage;
	private String billMedia;
	private String imei;
	private String contractPeriod;
	private String penaltyType;
	private String penaltyAmt;
	private String username;
	private String handsetDeviceAmount;
	private String firstMonthFee;
	private String billPeriod;
	private String customerCopyInd;
	//add eliot 20110819
	private InputStream custSignature;
	private InputStream custSignatureAutoPay;	

	private String smsLang;
	private String billLang;
	
	////20110513 add billing info
	private boolean noBillingAddressFlag=false;//add by wilson 20110513, false = noBillingAddress, true have billingAddress
	private String billingAddress;  //one line address 
	private boolean billingCustAddressFlag=false; //add 20110513, using quicksearch or not
	

	private String billingFlat;//add 20110513
	private String billingFloor;//add 20110513
	private String billingLotNum;//add 20110513
	private String billingBuildingName;//add 20110513	
	private String billingLotHouseInd;//add 20110513
	private String billingStreetNum;//add 20110513
	private String billingStreetName;//add 20110513
	private String billingStreetCatgDesc;//add 20110513
	private String billingSectionDesc;//add 20110513
	private String billingDistrictDesc;//add 20110513
	private String billingAreaCode;//add 20110513
		
	
	private String dummyEmail;
	private boolean hktOptOut;
	private boolean clubOptOut;
	private boolean hktClubOptOut;
	private String csPortalLogin;
	
	public String getBasketType() {
		return basketType;
	}

	public void setBasketType(String basketType) {
		this.basketType = basketType;
	}

	public RptAppMobileServDTO() {
		super(JASPER_TEMPLATE);		
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNum() {
		return idDocNum;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getAddrProofInd() {
		return addrProofInd;
	}

	public void setAddrProofInd(String addrProofInd) {
		this.addrProofInd = addrProofInd;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	public String getDistrictDesc() {
		return districtDesc;
	}

	public void setDistrictDesc(String districtDesc) {
		this.districtDesc = districtDesc;
	}

	public String getStreetCatgDesc() {
		return streetCatgDesc;
	}

	public void setStreetCatgDesc(String streetCatgDesc) {
		this.streetCatgDesc = streetCatgDesc;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getLotHouseInd() {
		return lotHouseInd;
	}

	public void setLotHouseInd(String lotHouseInd) {
		this.lotHouseInd = lotHouseInd;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}

	public String getMnpType() {
		return mnpType;
	}

	public void setMnpType(String mnpType) {
		this.mnpType = mnpType;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public Date getCutoverDate() {
		return cutoverDate;
	}

	public void setCutoverDate(Date cutoverDate) {
		this.cutoverDate = cutoverDate;
	}

	public Date getServiceReqDate() {
		return serviceReqDate;
	}

	public void setServiceReqDate(Date serviceReqDate) {
		this.serviceReqDate = serviceReqDate;
	}

	public String getAgreementNum() {
		return agreementNum;
	}

	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	public Date getAppInDate() {
		return appInDate;
	}

	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}

	public String getCreditCardDocNum() {
		return creditCardDocNum;
	}

	public void setCreditCardDocNum(String creditCardDocNum) {
		this.creditCardDocNum = creditCardDocNum;
	}

	public String getThirdPartyInd() {
		return thirdPartyInd;
	}

	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}

	public String getCreditExpiryMonth() {
		return creditExpiryMonth;
	}

	public void setCreditExpiryMonth(String creditExpiryMonth) {
		this.creditExpiryMonth = creditExpiryMonth;
	}

	public String getCreditExpiryYear() {
		return creditExpiryYear;
	}

	public void setCreditExpiryYear(String creditExpiryYear) {
		this.creditExpiryYear = creditExpiryYear;
	}

	public ArrayList<RptVasDetailDTO> getMainServDtls() {
		return mainServDtls;
	}

	public void setMainServDtls(ArrayList<RptVasDetailDTO> mainServDtls) {
		this.mainServDtls = mainServDtls;
	}

	public ArrayList<RptVasDetailDTO> getVasDtls() {
		return vasDtls;
	}

	public void setVasDtls(ArrayList<RptVasDetailDTO> vasDtls) {
		this.vasDtls = vasDtls;
	}

	public ArrayList<RptVasDetailDTO> getRebateList() {
		return rebateList;
	}

	public void setRebateList(ArrayList<RptVasDetailDTO> rebateList) {
		this.rebateList = rebateList;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getHandsetDeviceDescription() {
		return handsetDeviceDescription;
	}

	public void setHandsetDeviceDescription(String handsetDeviceDescription) {
		this.handsetDeviceDescription = handsetDeviceDescription;
	}

	public String getBillMedia() {
		return billMedia;
	}

	public void setBillMedia(String billMedia) {
		this.billMedia = billMedia;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getPenaltyType() {
		return penaltyType;
	}

	public void setPenaltyType(String penaltyType) {
		this.penaltyType = penaltyType;
	}

	public String getPenaltyAmt() {
		return penaltyAmt;
	}

	public void setPenaltyAmt(String penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHandsetDeviceAmount() {
		return handsetDeviceAmount;
	}

	public void setHandsetDeviceAmount(String handsetDeviceAmount) {
		this.handsetDeviceAmount = handsetDeviceAmount;
	}

	public String getFirstMonthFee() {
		return firstMonthFee;
	}

	public void setFirstMonthFee(String firstMonthFee) {
		this.firstMonthFee = firstMonthFee;
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	public String getCustomerCopyInd() {
		return customerCopyInd;
	}

	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}

	public String getSmsLang() {
		return smsLang;
	}

	public void setSmsLang(String smsLang) {
		this.smsLang = smsLang;
	}

	public String getBillLang() {
		return billLang;
	}

	public void setBillLang(String billLang) {
		this.billLang = billLang;
	}

	public boolean isNoBillingAddressFlag() {
		return noBillingAddressFlag;
	}

	public void setNoBillingAddressFlag(boolean noBillingAddressFlag) {
		this.noBillingAddressFlag = noBillingAddressFlag;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public boolean isBillingCustAddressFlag() {
		return billingCustAddressFlag;
	}

	public void setBillingCustAddressFlag(boolean billingCustAddressFlag) {
		this.billingCustAddressFlag = billingCustAddressFlag;
	}

	public String getBillingFlat() {
		return billingFlat;
	}

	public void setBillingFlat(String billingFlat) {
		this.billingFlat = billingFlat;
	}

	public String getBillingFloor() {
		return billingFloor;
	}

	public void setBillingFloor(String billingFloor) {
		this.billingFloor = billingFloor;
	}

	public String getBillingLotNum() {
		return billingLotNum;
	}

	public void setBillingLotNum(String billingLotNum) {
		this.billingLotNum = billingLotNum;
	}

	public String getBillingBuildingName() {
		return billingBuildingName;
	}

	public void setBillingBuildingName(String billingBuildingName) {
		this.billingBuildingName = billingBuildingName;
	}

	public String getBillingLotHouseInd() {
		return billingLotHouseInd;
	}

	public void setBillingLotHouseInd(String billingLotHouseInd) {
		this.billingLotHouseInd = billingLotHouseInd;
	}

	public String getBillingStreetNum() {
		return billingStreetNum;
	}

	public void setBillingStreetNum(String billingStreetNum) {
		this.billingStreetNum = billingStreetNum;
	}
	
	public String getBillingStreetName() {
		return billingStreetName;
	}

	public void setBillingStreetName(String billingStreetName) {
		this.billingStreetName = billingStreetName;
	}
	
	public String getBillingStreetCatgDesc() {
		return billingStreetCatgDesc;
	}

	public void setBillingStreetCatgDesc(String billingStreetCatgDesc) {
		this.billingStreetCatgDesc = billingStreetCatgDesc;
	}

	public String getBillingSectionDesc() {
		return billingSectionDesc;
	}

	public void setBillingSectionDesc(String billingSectionDesc) {
		this.billingSectionDesc = billingSectionDesc;
	}

	public String getBillingDistrictDesc() {
		return billingDistrictDesc;
	}

	public void setBillingDistrictDesc(String billingDistrictDesc) {
		this.billingDistrictDesc = billingDistrictDesc;
	}

	public String getBillingAreaCode() {
		return billingAreaCode;
	}

	public void setBillingAreaCode(String billingAreaCode) {
		this.billingAreaCode = billingAreaCode;
	}
	
	public String getBankAcctHolderIdType() {
		return bankAcctHolderIdType;
	}

	public void setBankAcctHolderIdType(String bankAcctHolderIdType) {
		this.bankAcctHolderIdType = bankAcctHolderIdType;
	}

	public String getBankAcctHolderIdNum() {
		return bankAcctHolderIdNum;
	}

	public void setBankAcctHolderIdNum(String bankAcctHolderIdNum) {
		this.bankAcctHolderIdNum = bankAcctHolderIdNum;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBankAcctHolderName() {
		return bankAcctHolderName;
	}

	public void setBankAcctHolderName(String bankAcctHolderName) {
		this.bankAcctHolderName = bankAcctHolderName;
	}

	public String getAutopayUpperLimitAmt() {
		return autopayUpperLimitAmt;
	}

	public void setAutopayUpperLimitAmt(String autopayUpperLimitAmt) {
		this.autopayUpperLimitAmt = autopayUpperLimitAmt;
	}

	public String getBankAcctNum() {
		return bankAcctNum;
	}

	public void setBankAcctNum(String bankAcctNum) {
		this.bankAcctNum = bankAcctNum;
	}

	public void setPayMethodType(String payMethodType) {
		this.payMethodType = payMethodType;
	}

	public String getPayMethodType() {
		return payMethodType;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesName() {
		return salesName;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getIsConSrv() {
		return isConSrv;
	}

	public void setIsConSrv(String isConSrv) {
		this.isConSrv = isConSrv;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreditCardIssueBankName() {
		return creditCardIssueBankName;
	}

	public void setCreditCardIssueBankName(String creditCardIssueBankName) {
		this.creditCardIssueBankName = creditCardIssueBankName;
	}

	public String getOtherContactPhone() {
		return otherContactPhone;
	}

	public void setOtherContactPhone(String otherContactPhone) {
		this.otherContactPhone = otherContactPhone;
	}

	public String getFirstMonthServiceLicenceFee() {
		return firstMonthServiceLicenceFee;
	}

	public void setFirstMonthServiceLicenceFee(String firstMonthServiceLicenceFee) {
		this.firstMonthServiceLicenceFee = firstMonthServiceLicenceFee;
	}
	public ArrayList<RptVasDetailDTO> getVasFreeGifsDtls() {
		return vasFreeGifsDtls;
	}

	public void setVasFreeGifsDtls(ArrayList<RptVasDetailDTO> vasFreeGifsDtls) {
		this.vasFreeGifsDtls = vasFreeGifsDtls;
	}

	public ArrayList<RptVasDetailDTO> getVasOptionalDtls() {
		return vasOptionalDtls;
	}

	public void setVasOptionalDtls(ArrayList<RptVasDetailDTO> vasOptionalDtls) {
		this.vasOptionalDtls = vasOptionalDtls;
	}

	public ArrayList<AdditionalChargeDTO> getAdditionalChargeDtls() {
		return additionalChargeDtls;
	}

	public void setAdditionalChargeDtls(
			ArrayList<AdditionalChargeDTO> additionalChargeDtls) {
		this.additionalChargeDtls = additionalChargeDtls;
	}

	public ArrayList<MiscellaneousChargeDTO> getMiscellaneousChargeDtls() {
		return miscellaneousChargeDtls;
	}

	public void setMiscellaneousChargeDtls(
			ArrayList<MiscellaneousChargeDTO> miscellaneousChargeDtls) {
		this.miscellaneousChargeDtls = miscellaneousChargeDtls;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public InputStream getCustSignature() {
		return custSignature;
	}

	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	
	public InputStream getCustSignatureAutoPay() {
		return custSignatureAutoPay;
	}

	public void setCustSignatureAutoPay(InputStream custSignatureAutoPay) {
		this.custSignatureAutoPay = custSignatureAutoPay;
	}

	public String getDummyEmail() {
		return dummyEmail;
	}

	public void setDummyEmail(String dummyEmail) {
		this.dummyEmail = dummyEmail;
	}

	public boolean isHktOptOut() {
		return hktOptOut;
	}

	public void setHktOptOut(boolean hktOptOut) {
		this.hktOptOut = hktOptOut;
	}

	public boolean isClubOptOut() {
		return clubOptOut;
	}

	public void setClubOptOut(boolean clubOptOut) {
		this.clubOptOut = clubOptOut;
	}

	public boolean isHktClubOptOut() {
		return hktClubOptOut;
	}

	public void setHktClubOptOut(boolean hktClubOptOut) {
		this.hktClubOptOut = hktClubOptOut;
	}

	public String getCsPortalLogin() {
		return csPortalLogin;
	}

	public void setCsPortalLogin(String csPortalLogin) {
		this.csPortalLogin = csPortalLogin;
	}

}
