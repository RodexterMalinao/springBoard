package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.MultipleMrtSimDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;

public class RptRetAppMobileServDTO extends ReportDTO {
	private static final long serialVersionUID = -3311684335459264341L;
	
	public static final String JASPER_TEMPLATE = "MobRetAppMobileServ";	
	//public static final String JASPER_TEMPLATE_NE = "AppNEServ";
	
	private String orderNature;
	private String contractNature;
	private String chgSimInd;
	private String billDate;
	
	// Customer Details Refer CustomerProfile	
	private String idDocType;// ID_DOC_TYPE VARCHAR2(4 BYTE),
	private String idDocNum;// ID_DOC_NUM VARCHAR2(30 BYTE)
	private String custFirstName;// FIRST_NAME VARCHAR2(40 BYTE),
	private String custLastName;// LAST_NAME VARCHAR2(40 BYTE),
	private Date dob;
	private String contactPhone;
	private String otherContactPhone;
	private String addrProofInd;
	private String lob;
	private String serviceNum;
	private String title;
	private String basketType;
	private String companyName;
	private String customerType;
	private String serviceType;
	private String emailAddr;
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
	
	private String locale;
	
	// Reference From MnpDTO
	private String mnpType;
	private String msisdn;
	private Date cutoverDate;
	private Date serviceReqDate;
	private String unicomMsisdn;	
	private String shopCd;
	
	// Reference From OrderDTO
	private String agreementNum;
	private Date appInDate;
	private String salesCd;
	private String salesName;
	
	private ArrayList<AdditionalChargeDTO> additionalChargeDtls;
	private ArrayList<MiscellaneousChargeDTO> miscellaneousChargeDtls;
	
	//add Eliot 20110722
	private String firstMonthServiceLicenceFee;

	// Customer Made Fields
	private ArrayList<RptVasDetailDTO> mainServDtls;
	private ArrayList<RptVasDetailDTO> vasDtls;
	//add eliot 20110726
	private ArrayList<RptVasDetailDTO> vasFreeGifsDtls;
	private ArrayList<RptVasDetailDTO> vasOptionalDtls;
	
	private String vasOnetimeAmtFee;
	private ArrayList<VasOnetimeAmtDTO> vasOnetimeAmtList;
	private ArrayList<RptVasDetailDTO> rebateList;
	private List<DepositDTO> depositList;// 20131031 Athena Deposit
	private String depositTotal;// 20131031 Athena Deposit
	
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

	private String smsLang;
	private String billLang;
	private String simType;
	private String nfcInd;
	private String octFlag; //Athena 20130923
	
	////20110513 add billing info
	private boolean noBillingAddressFlag=false;// false = noBillingAddress, true have billingAddress
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
	
	private boolean haveMultiSim;
	private List<MultipleMrtSimDTO> multipleMrtSimList;
	private List<RptMultiSimDTO> multiSimList; //MultiSim Athena 20140128
	
	private String custReminder;
	private String custReminderChi;
	private String staffReminder;
	private String staffReminderChi;
	/*
	 * S&S form: Documents Attached & Proof
	 * boolean to indicate whether tick is needed
	 */
	private boolean docAttMNP = false;
	private boolean docAttSecSrv = false;
	private boolean docAttThirdParty = false;
	private boolean docAttConci = false;
	private boolean docAttTradeDesc = false;
	private boolean docAttChgOfSrv = false;
	
	private boolean pfHKIDPass = false;
	private boolean pfAddrPf = false;
	private boolean pfBRCopy = false;
	private boolean pfBRNameCard = false;
	private boolean pfEmpCont = false;
	private boolean pfPreparidSIM = false;
	private boolean pfCCCopy = false;
	private boolean pfMNPDoc = false;
	
	private boolean privacyInd = false;
	private String personInfoCollectState1;
	private String personInfoCollectState2;
	private String custAgree;
	
	private SSSectionGDTO sectG = new SSSectionGDTO();
	private boolean csPortalBool;
	private String csPortalInd;
	private String csPortalStatus;
	private String csPortalLogin;
	
	private boolean suppressLocalTopUpInd = false;   //20130722
	private boolean suppressRoamTopUpInd = false;    //20130722
	private boolean mob0060OptOutInd = false;
	private Date appendTcStartDate;
	
	private String dummyEmail;
	private boolean hktOptOut;
	private boolean clubOptOut;
	private boolean hktClubOptOut;

	private String secSrvNum;
	
	public String getOrderNature() {
		return orderNature;
	}

	public void setOrderNature(String contractNature) {
		this.orderNature = contractNature;
	}
	
	public String getContractNature() {
		return contractNature;
	}

	public void setContractNature(String contractNature) {
		this.contractNature = contractNature;
	}
	
	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	
	public boolean isSuppressLocalTopUpInd() {
		return suppressLocalTopUpInd;
	}

	public void setSuppressLocalTopUpInd(boolean suppressLocalTopUpInd) {
		this.suppressLocalTopUpInd = suppressLocalTopUpInd;
	}

	public boolean isSuppressRoamTopUpInd() {
		return suppressRoamTopUpInd;
	}

	public void setSuppressRoamTopUpInd(boolean suppressRoamTopUpInd) {
		this.suppressRoamTopUpInd = suppressRoamTopUpInd;
	}
		
	public boolean isCsPortalBool() {
		return csPortalBool;
	}

	public void setCsPortalBool(boolean csPortalBool) {
		this.csPortalBool = csPortalBool;
	}
	
	public String getCsPortalInd() {
		return csPortalInd;
	}

	public void setCsPortalInd(String csPortalInd) {
		this.csPortalInd = csPortalInd;
	}

	public String getCsPortalStatus() {
		return csPortalStatus;
	}

	public void setCsPortalStatus(String csPortalStatus) {
		this.csPortalStatus = csPortalStatus;
	}

	public String getCsPortalLogin() {
		return csPortalLogin;
	}

	public void setCsPortalLogin(String csPortalLogin) {
		this.csPortalLogin = csPortalLogin;
	}

	public String getBasketType() {
		return basketType;
	}

	public void setBasketType(String basketType) {
		this.basketType = basketType;
	}

	public RptRetAppMobileServDTO() {
		super(JASPER_TEMPLATE);		
	}
	public RptRetAppMobileServDTO(String pLang) {
		super(JASPER_TEMPLATE, pLang);
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

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public String getNfcInd() {
		return nfcInd;
	}

	public void setNfcInd(String nfcInd) {
		this.nfcInd = nfcInd;
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
	
	public String getUnicomMsisdn() {
	    return unicomMsisdn;
	}

	public void setUnicomMsisdn(String unicomMsisdn) {
	    this.unicomMsisdn = unicomMsisdn;
	}

	public List<MultipleMrtSimDTO> getMultipleMrtSimList() {
		return multipleMrtSimList;
	}

	public void setMultipleMrtSimList(List<MultipleMrtSimDTO> multipleMrtSimList) {
		this.multipleMrtSimList = multipleMrtSimList;
	}

	public boolean isHaveMultiSim() {
		return haveMultiSim;
	}

	public void setHaveMultiSim(boolean haveMultiSim) {
		this.haveMultiSim = haveMultiSim;
	}

	/**
	 * For new number only, it is under the statement of customer signature
	 * @return the custReminder
	 */
	public String getCustReminder() {
		return custReminder;
	}

	/**
	 * For new number only, it is under the statement of customer signature
	 * @param custReminder the custReminder to set
	 */
	public void setCustReminder(String custReminder) {
		this.custReminder = custReminder;
	}

	/**
	 * Chinese version<br>
	 * For new number only, it is under the statement of customer signature
	 * @return the custReminderChi
	 */
	public String getCustReminderChi() {
		return custReminderChi;
	}

	/**
	 * Chinese version<br>
	 * For new number only, it is under the statement of customer signature
	 * @param custReminderChi the custReminderChi to set
	 */
	public void setCustReminderChi(String custReminderChi) {
		this.custReminderChi = custReminderChi;
	}

	public boolean isDocAttMNP() {
		return docAttMNP;
	}

	public void setDocAttMNP(boolean docAttMNP) {
		this.docAttMNP = docAttMNP;
	}

	public boolean isDocAttSecSrv() {
		return docAttSecSrv;
	}

	public void setDocAttSecSrv(boolean docAttSecSrv) {
		this.docAttSecSrv = docAttSecSrv;
	}

	public boolean isDocAttThirdParty() {
		return docAttThirdParty;
	}

	public void setDocAttThirdParty(boolean docAttThirdParty) {
		this.docAttThirdParty = docAttThirdParty;
	}

	public boolean isDocAttConci() {
		return docAttConci;
	}

	public void setDocAttConci(boolean docAttConci) {
		this.docAttConci = docAttConci;
	}

	public boolean isDocAttTradeDesc() {
		return docAttTradeDesc;
	}

	public void setDocAttTradeDesc(boolean docAttTradeDesc) {
		this.docAttTradeDesc = docAttTradeDesc;
	}

	public boolean isDocAttChgOfSrv() {
		return docAttChgOfSrv;
	}

	public void setDocAttChgOfSrv(boolean docAttChgOfSrv) {
		this.docAttChgOfSrv = docAttChgOfSrv;
	}

	public boolean isPfHKIDPass() {
		return pfHKIDPass;
	}

	public void setPfHKIDPass(boolean pfHKIDPass) {
		this.pfHKIDPass = pfHKIDPass;
	}

	public boolean isPfAddrPf() {
		return pfAddrPf;
	}

	public void setPfAddrPf(boolean pfAddrPf) {
		this.pfAddrPf = pfAddrPf;
	}

	public boolean isPfBRCopy() {
		return pfBRCopy;
	}

	public void setPfBRCopy(boolean pfBRCopy) {
		this.pfBRCopy = pfBRCopy;
	}

	public boolean isPfBRNameCard() {
		return pfBRNameCard;
	}
	
	public void setPfBRNameCard(boolean pfBRNameCard) {
		this.pfBRNameCard = pfBRNameCard;
	}

	public boolean isPfEmpCont() {
		return pfEmpCont;
	}

	public void setPfEmpCont(boolean pfEmpCont) {
		this.pfEmpCont = pfEmpCont;
	}

	public boolean isPfPreparidSIM() {
		return pfPreparidSIM;
	}

	public void setPfPreparidSIM(boolean pfPreparidSIM) {
		this.pfPreparidSIM = pfPreparidSIM;
	}

	public boolean isPfCCCopy() {
		return pfCCCopy;
	}

	public void setPfCCCopy(boolean pfCCCopy) {
		this.pfCCCopy = pfCCCopy;
	}

	public boolean isPfMNPDoc() {
		return pfMNPDoc;
	}

	public void setPfMNPDoc(boolean pfMNPDoc) {
		this.pfMNPDoc = pfMNPDoc;
	}

	public ArrayList<VasOnetimeAmtDTO> getVasOnetimeAmtList() {
		return vasOnetimeAmtList;
	}

	public void setVasOnetimeAmtList(ArrayList<VasOnetimeAmtDTO> vasOnetimeAmtList) {
		this.vasOnetimeAmtList = vasOnetimeAmtList;
	}

	public String getVasOnetimeAmtFee() {
		return vasOnetimeAmtFee;
	}

	public void setVasOnetimeAmtFee(String vasOnetimeAmtFee) {
		this.vasOnetimeAmtFee = vasOnetimeAmtFee;
	}

	public boolean isPrivacyInd() {
		return privacyInd;
	}

	public void setPrivacyInd(boolean privacyInd) {
		this.privacyInd = privacyInd;
	}

	public String getPersonInfoCollectState1() {
		return personInfoCollectState1;
	}

	public void setPersonInfoCollectState1(String personInfoCollectState1) {
		this.personInfoCollectState1 = personInfoCollectState1;
	}

	public String getPersonInfoCollectState2() {
		return personInfoCollectState2;
	}

	public void setPersonInfoCollectState2(String personInfoCollectState2) {
		this.personInfoCollectState2 = personInfoCollectState2;
	}

	public String getCustAgree() {
		return custAgree;
	}

	public void setCustAgree(String custAgree) {
		this.custAgree = custAgree;
	}

	public SSSectionGDTO getSectG() {
		return sectG;
	}

	public void setSectG(SSSectionGDTO sectG) {
		this.sectG = sectG;
	}
	
	public ArrayList<SSSectionGDTO> getSectionGDTOList() {
		ArrayList<SSSectionGDTO> rtnList = new ArrayList<SSSectionGDTO>();
		rtnList.add(this.sectG);
		return rtnList;
	}

	public String getOctFlag() {//Athena 20130923
		return octFlag;
	}

	public void setOctFlag(String octFlag) {
		this.octFlag = octFlag;
	}

	public boolean isMob0060OptOutInd() {
		return mob0060OptOutInd;
	}

	public void setMob0060OptOutInd(boolean mob0060OptOutInd) {
		this.mob0060OptOutInd = mob0060OptOutInd;
	}

	public List<DepositDTO> getDepositList() {
		return depositList;
	}

	public void setDepositList(List<DepositDTO> depositList) {
		this.depositList = depositList;
	}

	public String getDepositTotal() {
		return depositTotal;
	}

	public void setDepositTotal(String depositTotal) {
		this.depositTotal = depositTotal;
	}

	public List<RptMultiSimDTO> getMultiSimList() {
		return multiSimList;
	}

	public void setMultiSimList(List<RptMultiSimDTO> multiSimList) {
		this.multiSimList = multiSimList;
	}

	public String getChgSimInd() {
		return chgSimInd;
	}

	public void setChgSimInd(String chgSimInd) {
		this.chgSimInd = chgSimInd;
	}

	public Date getAppendTcStartDate() {
		return appendTcStartDate;
	}

	public void setAppendTcStartDate(Date appendTcStartDate) {
		this.appendTcStartDate = appendTcStartDate;
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

	public String getSecSrvNum() {
		return secSrvNum;
	}

	public void setSecSrvNum(String secSrvNum) {
		this.secSrvNum = secSrvNum;
	}

	public String getStaffReminder() {
		return staffReminder;
	}

	public void setStaffReminder(String staffReminder) {
		this.staffReminder = staffReminder;
	}

	public String getStaffReminderChi() {
		return staffReminderChi;
	}

	public void setStaffReminderChi(String staffReminderChi) {
		this.staffReminderChi = staffReminderChi;
	}

}
