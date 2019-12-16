package com.bomwebportal.ims.dto.report;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.io.IOUtils;

import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.dto.report.RptIGuardCareCashDTO;

public class RptImsAppServDTO extends ReportDTO {
	private static final long serialVersionUID = -3311684335459264341L;

	
	private Boolean thirdPartyCC;
	private String appMethod;
	private String billPreference01;//Gary
	private String billPreference02;//Gary
	private String billPreference03;
	private String billPreference04;
	private String waivedQC; //DS
	private String notWaiveQC; //DS
	private Boolean isPreview;//DS
	private String afFooter;//Gary
	
	private String nowIDEmailSubject;
    private String nowIDCkBoxText;
    private String isRegNowID;
    private String nowID;
    private ArrayList<RptServiceInfoDTO>nowIDListDtls;
    private ArrayList<RptServiceInfoDTO> nowIDListDtls2;
    
    private RptIGuardCareCashDTO rptIGuardCareCashDTO;

    
	
	
	public String getNowIDEmailSubject() {
		return nowIDEmailSubject;
	}
	public void setNowIDEmailSubject(String nowIDEmailSubject) {
		this.nowIDEmailSubject = nowIDEmailSubject;
	}
	public String getNowIDCkBoxText() {
		return nowIDCkBoxText;
	}
	public void setNowIDCkBoxText(String nowIDCkBoxText) {
		this.nowIDCkBoxText = nowIDCkBoxText;
	}
	public String getIsRegNowID() {
		return isRegNowID;
	}
	public void setIsRegNowID(String isRegNowID) {
		this.isRegNowID = isRegNowID;
	}
	public String getNowID() {
		return nowID;
	}
	public void setNowID(String nowID) {
		this.nowID = nowID;
	}
	public ArrayList<RptServiceInfoDTO> getNowIDListDtls() {
		return nowIDListDtls;
	}
	public void setNowIDListDtls(ArrayList<RptServiceInfoDTO> nowIDListDtls) {
		this.nowIDListDtls = nowIDListDtls;
	}
	public ArrayList<RptServiceInfoDTO> getNowIDListDtls2() {
		return nowIDListDtls2;
	}
	public void setNowIDListDtls2(ArrayList<RptServiceInfoDTO> nowIDListDtls2) {
		this.nowIDListDtls2 = nowIDListDtls2;
	}
	public String getBillPreference01() {
		return billPreference01;
	}
	public void setBillPreference01(String billPreference01) {
		this.billPreference01 = billPreference01;
	}
	public String getBillPreference02() {
		return billPreference02;
	}
	public void setBillPreference02(String billPreference02) {
		this.billPreference02 = billPreference02;
	}


	private ArrayList<RptServiceInfoDTO> bomOrderRemarks;

	private String wq_Application_date;
	private String wq_Earliest_srd_reason;
	private String sms_email;
	public boolean isShow_Section_THINGS_TO_KNOW() {
		return show_Section_THINGS_TO_KNOW;
	}
	public void setShow_Section_THINGS_TO_KNOW(boolean show_Section_THINGS_TO_KNOW) {
		this.show_Section_THINGS_TO_KNOW = show_Section_THINGS_TO_KNOW;
	}
	public boolean isShow_Section_CUSTOMER_DETAILS() {
		return show_Section_CUSTOMER_DETAILS;
	}
	public void setShow_Section_CUSTOMER_DETAILS(
			boolean show_Section_CUSTOMER_DETAILS) {
		this.show_Section_CUSTOMER_DETAILS = show_Section_CUSTOMER_DETAILS;
	}
	public boolean isShow_Section_SERVICE_PROVIDER() {
		return show_Section_SERVICE_PROVIDER;
	}
	public void setShow_Section_SERVICE_PROVIDER(
			boolean show_Section_SERVICE_PROVIDER) {
		this.show_Section_SERVICE_PROVIDER = show_Section_SERVICE_PROVIDER;
	}
	public boolean isShow_Section_CORE_SERVICES() {
		return show_Section_CORE_SERVICES;
	}
	public void setShow_Section_CORE_SERVICES(boolean show_Section_CORE_SERVICES) {
		this.show_Section_CORE_SERVICES = show_Section_CORE_SERVICES;
	}
	public boolean isShow_Section_OPTIONAL_SERVICES() {
		return show_Section_OPTIONAL_SERVICES;
	}
	public void setShow_Section_OPTIONAL_SERVICES(
			boolean show_Section_OPTIONAL_SERVICES) {
		this.show_Section_OPTIONAL_SERVICES = show_Section_OPTIONAL_SERVICES;
	}
	public boolean isShow_Section_GIFTS_AND_PREMIUMS() {
		return show_Section_GIFTS_AND_PREMIUMS;
	}
	public void setShow_Section_GIFTS_AND_PREMIUMS(
			boolean show_Section_GIFTS_AND_PREMIUMS) {
		this.show_Section_GIFTS_AND_PREMIUMS = show_Section_GIFTS_AND_PREMIUMS;
	}
	public boolean isShow_Section_CHARGES_FOR_CORE_SERVICES() {
		return show_Section_CHARGES_FOR_CORE_SERVICES;
	}
	public void setShow_Section_CHARGES_FOR_CORE_SERVICES(
			boolean show_Section_CHARGES_FOR_CORE_SERVICES) {
		this.show_Section_CHARGES_FOR_CORE_SERVICES = show_Section_CHARGES_FOR_CORE_SERVICES;
	}
	public boolean isShow_Section_BILL_PREFERENCES() {
		return show_Section_BILL_PREFERENCES;
	}
	public void setShow_Section_BILL_PREFERENCES(
			boolean show_Section_BILL_PREFERENCES) {
		this.show_Section_BILL_PREFERENCES = show_Section_BILL_PREFERENCES;
	}
	public boolean isShow_Section_IMPORTANT_INFORMATION() {
		return show_Section_IMPORTANT_INFORMATION;
	}
	public void setShow_Section_IMPORTANT_INFORMATION(
			boolean show_Section_IMPORTANT_INFORMATION) {
		this.show_Section_IMPORTANT_INFORMATION = show_Section_IMPORTANT_INFORMATION;
	}
	public boolean isShow_Section_GLOSSARY() {
		return show_Section_GLOSSARY;
	}
	public void setShow_Section_GLOSSARY(boolean show_Section_GLOSSARY) {
		this.show_Section_GLOSSARY = show_Section_GLOSSARY;
	}
	public boolean isShow_Section_DETAILS() {
		return show_Section_DETAILS;
	}
	public void setShow_Section_DETAILS(boolean show_Section_DETAILS) {
		this.show_Section_DETAILS = show_Section_DETAILS;
	}
	public boolean isShow_Section_PAYMENT() {
		return show_Section_PAYMENT;
	}
	public void setShow_Section_PAYMENT(boolean show_Section_PAYMENT) {
		this.show_Section_PAYMENT = show_Section_PAYMENT;
	}
	public boolean isShow_Section_PERSONAL() {
		return show_Section_PERSONAL;
	}
	public void setShow_Section_PERSONAL(boolean show_Section_PERSONAL) {
		this.show_Section_PERSONAL = show_Section_PERSONAL;
	}
	public boolean isShow_Section_AGREEMENT() {
		return show_Section_AGREEMENT;
	}
	public void setShow_Section_AGREEMENT(boolean show_Section_AGREEMENT) {
		this.show_Section_AGREEMENT = show_Section_AGREEMENT;
	}
	public boolean isShow_Section_Interial_Use() {
		return show_Section_Interial_Use;
	}
	public void setShow_Section_Interial_Use(boolean show_Section_Interial_Use) {
		this.show_Section_Interial_Use = show_Section_Interial_Use;
	}


	private  boolean show_Section_THINGS_TO_KNOW=true;
	private  boolean show_Section_CUSTOMER_DETAILS=true;
	private  boolean show_Section_SERVICE_PROVIDER=true;
	private  boolean show_Section_CORE_SERVICES=true;
	private  boolean show_Section_OPTIONAL_SERVICES=true;
	private  boolean show_Section_GIFTS_AND_PREMIUMS=true;
	private  boolean show_Section_CHARGES_FOR_CORE_SERVICES=true;
	private  boolean show_Section_BILL_PREFERENCES=true;
	private  boolean show_Section_IMPORTANT_INFORMATION=true;
	private  boolean show_Section_GLOSSARY=true;
	private  boolean show_Section_DETAILS=true;
	private  boolean show_Section_PAYMENT=true;
	private  boolean show_Section_PERSONAL=true;
	private  boolean show_Section_AGREEMENT=true;
	private  boolean show_Section_Interial_Use=true;
	private  boolean cOrder=false;
	private String billingEmailAddr ;
	private String csLang;
	
	
	private ArrayList<RptServiceInfoDTO> kisDtls;
	
	private ArrayList<RptHSTradeDescDTO> mobTDODtLs;
	
	private Boolean showAllForTest;

	public void setShowAllForTest(Boolean showAllForTest) {
		this.showAllForTest = showAllForTest;
	}
	public Boolean getShowAllForTest() {
		return showAllForTest;
	}
	
	
	private Boolean showRegHKTLoginId;
	//Gary added for CS portal
	private String isRegHKTLoginId;	
	private String hktLoginId;
	private String hktLoginIdSubject;
	private String hktMobileNum;
	private String hktMobileNumSubject;
	private String hktPortalCkBoxText;
	private String hktClubOptOutInd;//Tony hktclub opt-in/opt-out
	private String optOutServiceSubject;
	private String optOutHktClubSubject;
	public String getHktLoginIdSubject() {
		return hktLoginIdSubject;
	}
	public void setHktLoginIdSubject(String hktLoginIdSubject) {
		this.hktLoginIdSubject = hktLoginIdSubject;
	}
	public String getHktMobileNumSubject() {
		return hktMobileNumSubject;
	}
	public void setHktMobileNumSubject(String hktMobileNumSubject) {
		this.hktMobileNumSubject = hktMobileNumSubject;
	}
	public String getHktPortalCkBoxText() {
		return hktPortalCkBoxText;
	}
	public void setHktPortalCkBoxText(String hktPortalCkBoxText) {
		this.hktPortalCkBoxText = hktPortalCkBoxText;
	}
	public String getHktClubOptOutInd() {
		return hktClubOptOutInd;
	}
	public void setHktClubOptOutInd(String hktClubOptOutInd) {
		this.hktClubOptOutInd = hktClubOptOutInd;
	}
	public String getOptOutServiceSubject() {
		return optOutServiceSubject;
	}
	public void setOptOutServiceSubject(String optOutServiceSubject) {
		this.optOutServiceSubject = optOutServiceSubject;
	}
	public String getOptOutHktClubSubject() {
		return optOutHktClubSubject;
	}
	public void setOptOutHktClubSubject(String optOutHktClubSubject) {
		this.optOutHktClubSubject = optOutHktClubSubject;
	}


	private Boolean needMobTDO;
	
	private String companyName;
	private Boolean isCC;
	
	
	private Boolean personalInfoExistingCust;
	public ArrayList<RptHSTradeDescDTO> getMobTDODtLs() {
		return mobTDODtLs;
	}
	public void setMobTDODtLs(ArrayList<RptHSTradeDescDTO> mobTDODtLs) {
		this.mobTDODtLs = mobTDODtLs;
	}
	public Boolean getNeedMobTDO() {
		return needMobTDO;
	}
	public void setNeedMobTDO(Boolean needMobTDO) {
		this.needMobTDO = needMobTDO;
	}


	private Boolean personalInfoNewCustOptIn;

	public Boolean getPersonalInfoExistingCust() {
		return personalInfoExistingCust;
	}
	public void setPersonalInfoExistingCust(Boolean personalInfoExistingCust) {
		this.personalInfoExistingCust = personalInfoExistingCust;
	}
	public Boolean getPersonalInfoNewCustOptIn() {
		return personalInfoNewCustOptIn;
	}
	public void setPersonalInfoNewCustOptIn(Boolean personalInfoNewCustOptIn) {
		this.personalInfoNewCustOptIn = personalInfoNewCustOptIn;
	}
	
	private String netvigatorLogo = null;	
	private String footerPccwLogo = null;	
	public String getFooterPccwLogo() {
		return footerPccwLogo;
	}
	public void setFooterPccwLogo(String footerPccwLogo) {
		this.footerPccwLogo = footerPccwLogo;
	}
	public String getFooterHktLogo() {
		return footerHktLogo;
	}
	public void setFooterHktLogo(String footerHktLogo) {
		this.footerHktLogo = footerHktLogo;
	}


	private String footerHktLogo = null;	

	public String getNetvigatorLogo() {
		return netvigatorLogo;
	}
	public void setNetvigatorLogo(String netvigatorLogo) {
		this.netvigatorLogo = netvigatorLogo;
	}


	private String locale;
	
	private String customerCopyInd;

	// Reference From OrderDTO
	private String appNo;
	private String appDate;
	private String salesCd;
	private String salesName;
	private String shopCd;
	private String createdBy;
	private String salesContactNum;
	private String programCode;
	private String uamsNo;
	private String salesChannel;
	// Customer Details	
	private String newCustInd;	// Y-New Customer, N-Exist Customer
	private String title;
	private String lastName;	// LAST_NAME VARCHAR2(40 BYTE),
	private String firstName;	// FIRST_NAME VARCHAR2(40 BYTE),
	private Boolean isNameTooLong; //Gary added
	private String dob;
	private String idDocType;		// ID_DOC_TYPE VARCHAR2(4 BYTE),
	private String idDocNum;		// ID_DOC_NUM VARCHAR2(30 BYTE)
	private String idDocVerifyInd;	// Y-Verified, N-without Verify
	private String contactPhone;
	private String otherPhone;
	private String fixedLineNum;
	private String fixedLineNumPccwInd;		// Y-PCCW, N-non PCCW
	private String emailAddr;
	private String contactEmail;
	
	// installation address
	private String flat;
	private String floor;
	private String buildingName;
	private String lotHouseInd;
	private String lotNum;
	private String streetNum;
	private String streetName;
	private String streetCatgDesc;
	private String sectionDesc;
	private String districtDesc;
	private String areaCode;

	// billing address
	private boolean noBillingAddressFlag=false;		//false = noBillingAddress, true have billingAddress
	private String billingFlat;
	private String billingFloor;
	private String billingBuildingName;
	private String billingLotHouseInd;
	private String billingLotNum;
	private String billingStreetNum;
	private String billingStreetName;
	private String billingStreetCatgDesc;
	private String billingSectionDesc;
	private String billingDistrictDesc;
	private String billingAreaCode;

	// Section C - Core Service
	private String fixedTerm;
	private String extenTerm;
	private String totalTerm;
	private String targetInstallDate;
	private String targetInstallTimeSlot;
	private String targetCommDate;
	private String loginId;
	private String bandwidth;
	private ArrayList<RptServiceDetailDTO> coreServDtls;

	private String preInstInd;
	private String nowTVPurchased;
	
	private String mobileOfferInd;
	
	private String retailMode;
	
	// Section D
	private ArrayList<RptServiceDetailDTO> vasAndOptDtls;
	//steven added
	// Section B
	private ArrayList<RptServiceInfoDTO> serviceProvideDtls;
	// Section L
	private ArrayList<RptServiceInfoDTO> personalInfoDtls;
	
	private ArrayList<RptServiceInfoDTO> personalInfoDtlsEC;
	

	private ArrayList<RptServiceInfoDTO> personalInfoDtlsOptIn;
	private ArrayList<RptServiceInfoDTO> personalInfoDtlsOptInBtm;
	

	private ArrayList<RptServiceInfoDTO> personalInfoDtlsOptOut;
	

	public void setPersonalInfoDtlsEC(ArrayList<RptServiceInfoDTO> personalInfoDtlsEC) {
		this.personalInfoDtlsEC = personalInfoDtlsEC;
	}
	public ArrayList<RptServiceInfoDTO> getPersonalInfoDtlsEC() {
		return personalInfoDtlsEC;
	}
	public void setPersonalInfoDtlsOptIn(ArrayList<RptServiceInfoDTO> personalInfoDtlsOptIn) {
		this.personalInfoDtlsOptIn = personalInfoDtlsOptIn;
	}
	public ArrayList<RptServiceInfoDTO> getPersonalInfoDtlsOptIn() {
		return personalInfoDtlsOptIn;
	}
	public void setPersonalInfoDtlsOptOut(ArrayList<RptServiceInfoDTO> personalInfoDtlsOptOut) {
		this.personalInfoDtlsOptOut = personalInfoDtlsOptOut;
	}
	public ArrayList<RptServiceInfoDTO> getPersonalInfoDtlsOptOut() {
		return personalInfoDtlsOptOut;
	}	
	// Section M
	private ArrayList<RptServiceInfoDTO> hktServPortalDtls;
	private ArrayList<RptServiceInfoDTO> hktServPortalDtls2;
	// Section N
	private ArrayList<RptServiceInfoDTO> custAgreeDtls;
	// Section K
	private ArrayList<RptServiceInfoDTO> creditCardDtls;
	public void setCreditCardDtls(ArrayList<RptServiceInfoDTO> creditCardDtls) {
		this.creditCardDtls = creditCardDtls;
	}
	public ArrayList<RptServiceInfoDTO> getCreditCardDtls() {
		return creditCardDtls;
	}
	public void setServiceProvideDtls(ArrayList<RptServiceInfoDTO> serviceProvideDtls) {
		this.serviceProvideDtls = serviceProvideDtls;
	}
	public ArrayList<RptServiceInfoDTO> getServiceProvideDtls() {
		return serviceProvideDtls;
	}
	public void setCustAgreeDtls(ArrayList<RptServiceInfoDTO> custAgreeDtls) {
		this.custAgreeDtls = custAgreeDtls;
	}
	public ArrayList<RptServiceInfoDTO> getCustAgreeDtls() {
		return custAgreeDtls;
	}
	//steven added end
	// Section F
	private ArrayList<RptServiceInfoDTO> coreChrgDtls;
	
	// Section G - bill preference
	private String billMedia;
	
	// Section H
	private ArrayList<RptServiceInfoDTO> imprtInfoDtls;

	// Section I
	private ArrayList<RptServiceInfoDTO> glossaryDtls;
	
	// Section J
	private ArrayList<RptServiceInfoDTO> servPlanDtls;

	// Section K - payment
	private String payMethodType;	//M - Cash, C - Credit Card
	private String creditCardType;
	private String creditCardHolderName;
	private String creditCardNum;
	private String creditExpiryDate;
	private String creditCardVerifyInd;		// Y-Verified, N-without Verified
	private String thirdPartyInd;
	private String cashOption;
	
	private String qosMeasureInd;
	private InputStream custSignature;
	private InputStream custSignaturePay;	
	private InputStream thirdPartySignature;
	private InputStream careCashSignature;
	
	private String custOptOutDirectMailingInd;
	private String custOptOutOutBoundInd;
	private String custOptOutSmsInd;
	private String custOptOutEmailInd;
	private String custOptOutWebBillInd;
	private String custOptOutNonSalesSmsInd;
	private String custOptOutInternetInd;
	
	private String mobileFuturePayment;
	
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getCustomerCopyInd() {
		return customerCopyInd;
	}
	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getSalesCd() {
		return salesCd;
	}
	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSalesContactNum() {
		return salesContactNum;
	}
	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
	}
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public String getUamsNo() {
		return uamsNo;
	}
	public void setUamsNo(String uamsNo) {
		this.uamsNo = uamsNo;
	}
	public String getNewCustInd() {
		return newCustInd;
	}
	public void setNewCustInd(String newCustInd) {
		this.newCustInd = newCustInd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
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
	public String getIdDocVerifyInd() {
		return idDocVerifyInd;
	}
	public void setIdDocVerifyInd(String idDocVerifyInd) {
		this.idDocVerifyInd = idDocVerifyInd;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getOtherPhone() {
		return otherPhone;
	}
	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}
	public String getFixedLineNum() {
		return fixedLineNum;
	}
	public void setFixedLineNum(String fixedLineNum) {
		this.fixedLineNum = fixedLineNum;
	}
	public String getFixedLineNumPccwInd() {
		return fixedLineNumPccwInd;
	}
	public void setFixedLineNumPccwInd(String fixedLineNumPccwInd) {
		this.fixedLineNumPccwInd = fixedLineNumPccwInd;
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
	public String getStreetCatgDesc() {
		return streetCatgDesc;
	}
	public void setStreetCatgDesc(String streetCatgDesc) {
		this.streetCatgDesc = streetCatgDesc;
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public boolean isNoBillingAddressFlag() {
		return noBillingAddressFlag;
	}
	public void setNoBillingAddressFlag(boolean noBillingAddressFlag) {
		this.noBillingAddressFlag = noBillingAddressFlag;
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
	public String getBillingLotNum() {
		return billingLotNum;
	}
	public void setBillingLotNum(String billingLotNum) {
		this.billingLotNum = billingLotNum;
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
	public String getFixedTerm() {
		return fixedTerm;
	}
	public void setFixedTerm(String fixedTerm) {
		this.fixedTerm = fixedTerm;
	}
	public String getExtenTerm() {
		return extenTerm;
	}
	public void setExtenTerm(String extenTerm) {
		this.extenTerm = extenTerm;
	}
	public String getTotalTerm() {
		return totalTerm;
	}
	public void setTotalTerm(String totalTerm) {
		this.totalTerm = totalTerm;
	}
	public String getTargetInstallDate() {
		return targetInstallDate;
	}
	public void setTargetInstallDate(String targetInstallDate) {
		this.targetInstallDate = targetInstallDate;
	}
	public String getTargetInstallTimeSlot() {
		return targetInstallTimeSlot;
	}
	public void setTargetInstallTimeSlot(String targetInstallTimeSlot) {
		this.targetInstallTimeSlot = targetInstallTimeSlot;
	}
	public String getTargetCommDate() {
		return targetCommDate;
	}
	public void setTargetCommDate(String targetCommDate) {
		this.targetCommDate = targetCommDate;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getBillMedia() {
		return billMedia;
	}
	public void setBillMedia(String billMedia) {
		this.billMedia = billMedia;
	}
	public String getPayMethodType() {
		return payMethodType;
	}
	public void setPayMethodType(String payMethodType) {
		this.payMethodType = payMethodType;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}
	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getCreditExpiryDate() {
		return creditExpiryDate;
	}
	public void setCreditExpiryDate(String creditExpiryDate) {
		this.creditExpiryDate = creditExpiryDate;
	}
	public String getCreditCardVerifyInd() {
		return creditCardVerifyInd;
	}
	public void setCreditCardVerifyInd(String creditCardVerifyInd) {
		this.creditCardVerifyInd = creditCardVerifyInd;
	}
	public String getThirdPartyInd() {
		return thirdPartyInd;
	}
	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}
	public String getCashOption() {
		return cashOption;
	}
	public void setCashOption(String cashOption) {
		this.cashOption = cashOption;
	}
	public String getQosMeasureInd() {
		return qosMeasureInd;
	}
	public void setQosMeasureInd(String qosMeasureInd) {
		this.qosMeasureInd = qosMeasureInd;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	public InputStream getCustSignaturePay() {
		return custSignaturePay;
	}
	public void setCustSignaturePay(InputStream custSignaturePay) {
		this.custSignaturePay = custSignaturePay;
	}
	public void setThirdPartySignature(InputStream thirdPartySignature) {
		this.thirdPartySignature = thirdPartySignature;
	}
	public InputStream getThirdPartySignature() {
		return thirdPartySignature;
	}
	public String getCustOptOutDirectMailingInd() {
		return custOptOutDirectMailingInd;
	}
	public void setCustOptOutDirectMailingInd(String custOptOutDirectMailingInd) {
		this.custOptOutDirectMailingInd = custOptOutDirectMailingInd;
	}
	public String getCustOptOutOutBoundInd() {
		return custOptOutOutBoundInd;
	}
	public void setCustOptOutOutBoundInd(String custOptOutOutBoundInd) {
		this.custOptOutOutBoundInd = custOptOutOutBoundInd;
	}
	public String getCustOptOutSmsInd() {
		return custOptOutSmsInd;
	}
	public void setCustOptOutSmsInd(String custOptOutSmsInd) {
		this.custOptOutSmsInd = custOptOutSmsInd;
	}
	public String getCustOptOutEmailInd() {
		return custOptOutEmailInd;
	}
	public void setCustOptOutEmailInd(String custOptOutEmailInd) {
		this.custOptOutEmailInd = custOptOutEmailInd;
	}
	public String getCustOptOutWebBillInd() {
		return custOptOutWebBillInd;
	}
	public void setCustOptOutWebBillInd(String custOptOutWebBillInd) {
		this.custOptOutWebBillInd = custOptOutWebBillInd;
	}
	public String getCustOptOutNonSalesSmsInd() {
		return custOptOutNonSalesSmsInd;
	}
	public void setCustOptOutNonSalesSmsInd(String custOptOutNonSalesSmsInd) {
		this.custOptOutNonSalesSmsInd = custOptOutNonSalesSmsInd;
	}
	public String getCustOptOutInternetInd() {
		return custOptOutInternetInd;
	}
	public void setCustOptOutInternetInd(String custOptOutInternetInd) {
		this.custOptOutInternetInd = custOptOutInternetInd;
	}
	public ArrayList<RptServiceDetailDTO> getCoreServDtls() {
		return coreServDtls;
	}
	public void setCoreServDtls(ArrayList<RptServiceDetailDTO> coreServDtls) {
		this.coreServDtls = coreServDtls;
	}
	public ArrayList<RptServiceDetailDTO> getVasAndOptDtls() {
		return vasAndOptDtls;
	}
	public void setVasAndOptDtls(ArrayList<RptServiceDetailDTO> vasAndOptDtls) {
		this.vasAndOptDtls = vasAndOptDtls;
	}
	public ArrayList<RptServiceInfoDTO> getCoreChrgDtls() {
		return coreChrgDtls;
	}
	public void setCoreChrgDtls(ArrayList<RptServiceInfoDTO> coreChrgDtls) {
		this.coreChrgDtls = coreChrgDtls;
	}
	public ArrayList<RptServiceInfoDTO> getImprtInfoDtls() {
		return imprtInfoDtls;
	}
	public void setImprtInfoDtls(ArrayList<RptServiceInfoDTO> imprtInfoDtls) {
		this.imprtInfoDtls = imprtInfoDtls;
	}
	public ArrayList<RptServiceInfoDTO> getGlossaryDtls() {
		return glossaryDtls;
	}
	public void setGlossaryDtls(ArrayList<RptServiceInfoDTO> glossaryDtls) {
		this.glossaryDtls = glossaryDtls;
	}
	public ArrayList<RptServiceInfoDTO> getServPlanDtls() {
		return servPlanDtls;
	}
	public void setServPlanDtls(ArrayList<RptServiceInfoDTO> servPlanDtls) {
		this.servPlanDtls = servPlanDtls;
	}
	public String getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	
	public void setHktServPortalDtls(ArrayList<RptServiceInfoDTO> hktServPortalDtls) {
		this.hktServPortalDtls = hktServPortalDtls;
	}
	public ArrayList<RptServiceInfoDTO> getHktServPortalDtls() {
		return hktServPortalDtls;
	}
	public void setIsRegHKTLoginId(String isRegHKTLoginId) {
		this.isRegHKTLoginId = isRegHKTLoginId;
	}
	public String getIsRegHKTLoginId() {
		return isRegHKTLoginId;
	}
	public void setHktLoginId(String hktLoginId) {
		this.hktLoginId = hktLoginId;
	}
	public String getHktLoginId() {
		return hktLoginId;
	}
	public void setHktMobileNum(String hktMobileNum) {
		this.hktMobileNum = hktMobileNum;
	}
	public String getHktMobileNum() {
		return hktMobileNum;
	}
	public void setKisDtls(ArrayList<RptServiceInfoDTO> kisDtls) {
		this.kisDtls = kisDtls;
	}
	public ArrayList<RptServiceInfoDTO> getKisDtls() {
		return kisDtls;
	}
	public void setShowRegHKTLoginId(Boolean showRegHKTLoginId) {
		this.showRegHKTLoginId = showRegHKTLoginId;
	}
	public Boolean getShowRegHKTLoginId() {
		return showRegHKTLoginId;
	}
	public void setPersonalInfoDtls(ArrayList<RptServiceInfoDTO> personalInfoDtls) {
		this.personalInfoDtls = personalInfoDtls;
	}
	public ArrayList<RptServiceInfoDTO> getPersonalInfoDtls() {
		return personalInfoDtls;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setIsCC(Boolean isCC) {
		this.isCC = isCC;
	}
	public Boolean getIsCC() {
		return isCC;
	}
	public void setBillingEmailAddr(String billingEmailAddr) {
		this.billingEmailAddr = billingEmailAddr;
	}
	public String getBillingEmailAddr() {
		return billingEmailAddr;
	}
	public void setCsLang(String csLang) {
		this.csLang = csLang;
	}
	public String getCsLang() {
		return csLang;
	}
	public void setcOrder(boolean cOrder) {
		this.cOrder = cOrder;
	}
	public boolean iscOrder() {
		return cOrder;
	}
	public void setBomOrderRemarks(ArrayList<RptServiceInfoDTO> bomOrderRemarks) {
		this.bomOrderRemarks = bomOrderRemarks;
	}
	public ArrayList<RptServiceInfoDTO> getBomOrderRemarks() {
		return bomOrderRemarks;
	}
	public void setWq_Earliest_srd_reason(String wq_Earliest_srd_reason) {
		this.wq_Earliest_srd_reason = wq_Earliest_srd_reason;
	}
	public String getWq_Earliest_srd_reason() {
		return wq_Earliest_srd_reason;
	}
	public void setWq_Application_date(String wq_Application_date) {
		this.wq_Application_date = wq_Application_date;
	}
	public String getWq_Application_date() {
		return wq_Application_date;
	}
	public void setAppMethod(String appMethod) {
		this.appMethod = appMethod;
	}
	public String getAppMethod() {
		return appMethod;
	}
	public void setThirdPartyCC(Boolean thirdPartyCC) {
		this.thirdPartyCC = thirdPartyCC;
	}
	public Boolean getThirdPartyCC() {
		return thirdPartyCC;
	}
	public void setIsNameTooLong(Boolean isNameTooLong) {
		this.isNameTooLong = isNameTooLong;
	}
	public Boolean getIsNameTooLong() {
		return isNameTooLong;
	}
	public void setWaivedQC(String waivedQC) {
		this.waivedQC = waivedQC;
	}
	public String getWaivedQC() {
		return waivedQC;
	}
	public void setNotWaiveQC(String notWaiveQC) {
		this.notWaiveQC = notWaiveQC;
	}
	public String getNotWaiveQC() {
		return notWaiveQC;
	}	
	public void setIsPreview(Boolean isPreview) {
		this.isPreview = isPreview;
	}
	public Boolean getIsPreview() {
		return isPreview;
	}
	public void setHktServPortalDtls2(ArrayList<RptServiceInfoDTO> hktServPortalDtls2) {
		this.hktServPortalDtls2 = hktServPortalDtls2;
	}
	public ArrayList<RptServiceInfoDTO> getHktServPortalDtls2() {
		return hktServPortalDtls2;
	}
	public void setAfFooter(String afFooter) {
		this.afFooter = afFooter;
	}
	public String getAfFooter() {
		return afFooter;
	}
	public void setSms_email(String sms_email) {
		this.sms_email = sms_email;
	}
	public String getSms_email() {
		return sms_email;
	}
	public void setPreInstInd(String preInstInd) {
		this.preInstInd = preInstInd;
	}
	public String getPreInstInd() {
		return preInstInd;
	}
	public void setNowTVPurchased(String nowTVPurchased) {
		this.nowTVPurchased = nowTVPurchased;
	}
	public String getNowTVPurchased() {
		return nowTVPurchased;
	}
	public void setCareCashSignature(InputStream careCashSignature) {
		this.careCashSignature = careCashSignature;
	}
	public InputStream getCareCashSignature() {
		return careCashSignature;
	}
	public void setRptIGuardCareCashDTO(RptIGuardCareCashDTO rptIGuardCareCashDTO) {
		this.rptIGuardCareCashDTO = rptIGuardCareCashDTO;
	}
	public RptIGuardCareCashDTO getRptIGuardCareCashDTO() {
		return rptIGuardCareCashDTO;
	}
	public void setMobileOfferInd(String mobileOfferInd) {
		this.mobileOfferInd = mobileOfferInd;
	}
	public String getMobileOfferInd() {
		return mobileOfferInd;
	}
	public void setBillPreference03(String billPreference03) {
		this.billPreference03 = billPreference03;
	}
	public String getBillPreference03() {
		return billPreference03;
	}
	public void setBillPreference04(String billPreference04) {
		this.billPreference04 = billPreference04;
	}
	public String getBillPreference04() {
		return billPreference04;
	}
	public void setRetailMode(String retailMode) {
		this.retailMode = retailMode;
	}
	public String getRetailMode() {
		return retailMode;
	}
	public void setMobileFuturePayment(String mobileFuturePayment) {
		this.mobileFuturePayment = mobileFuturePayment;
	}
	public String getMobileFuturePayment() {
		return mobileFuturePayment;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setPersonalInfoDtlsOptInBtm(ArrayList<RptServiceInfoDTO> personalInfoDtlsOptInBtm) {
		this.personalInfoDtlsOptInBtm = personalInfoDtlsOptInBtm;
	}
	public ArrayList<RptServiceInfoDTO> getPersonalInfoDtlsOptInBtm() {
		return personalInfoDtlsOptInBtm;
	}

}
