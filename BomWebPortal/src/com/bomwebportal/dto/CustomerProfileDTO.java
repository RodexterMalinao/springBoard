package com.bomwebportal.dto;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openuri.www.CustomerBasicInfoDTO;
public class CustomerProfileDTO implements java.io.Serializable {

	private static final long serialVersionUID = -3311684335459264341L;
	private String orderId;
	private String systemId;
	private String customerType;
	private String serviceType;
	private String contactName;
	private String contactPhone;
	private String emailAddr;
	//private String address;
	private String customerNum;// CUST_NO VARCHAR2(8 BYTE),
	private String mobCustNum;
	private String bomCustNum; // MOB_CUST_NO VARCHAR2(8 BYTE),
	private String custFirstName;// FIRST_NAME VARCHAR2(40 BYTE),
	private String custLastName;// LAST_NAME VARCHAR2(40 BYTE),
	private String idDocType;// ID_DOC_TYPE VARCHAR2(4 BYTE),
	private String idDocNum;// ID_DOC_NUM VARCHAR2(30 BYTE),
	private boolean isValidCustomer;
	private boolean ignoreCustomerCheck;
	private boolean isBomWsAvailable;
	private String title;// TITLE VARCHAR2(4 BYTE),
	private String companyName;// COMPANY_NAME VARCHAR2(40 BYTE),
	private String industryType; // IND_TYPE VARCHAR2(10 BYTE),
	private String industrySubType;// IND_SUB_TYPE VARCHAR2(10 BYTE),
	private String nationality; // NATIONALITY VARCHAR2(3 BYTE),
	private String createDate; // CREATE_DATE DATE
	private Date dob;
	private String addrProofInd;
	private String lob;
	private String serviceNum;
	private String quickSearch;
	private String address1;
	private String address2;
	private String address3;
	private String flat;
	private String floor;
	private String serviceBoundaryNum;
	private String lotHouseInd;
	private String streetNum; //20110530 b4 call houseLotNum
	private String lotNum;
	private String buildingName;
	private String streetName;
	private String streetCatgDesc;
	private String streetCatgCode;
	private String sectionDesc;
	private String sectionCode;
	private String districtDesc;
	private String districtCode;;
	private String areaDesc;
	private String areaCode;
	private String dobStr;
	private boolean noEmailFlag=false;//add by wilson 20110302
	private boolean custAddressFlag=false;//add by wilson 20110302,false=quick search,  true self input
	private boolean custAddressFlag2=false;
	private boolean unlinkSectionFlag=false;//add by wilson 20110315,false=link ,  true self unlink
	private boolean noBillingAddressFlag=true;//add by wilson 20110513, false = noBillingAddress, true have billingAddress
	private boolean billingCustAddressFlag=false; //add 20110513, using quicksearch or not
	private String billingQuickSearch;//add 20110513
	private String billingFlat;//add 20110513
	private String billingFloor;//add 20110513
	private String billingLotNum;//add 20110513
	private String billingBuildingName;//add 20110513
	private String billingStreetNum;//add 20110513 , edit 20110530, b4 is billingHouseLotNum
	private String billingStreetName;//add 20110513
	private String billingStreetCatgDesc;//add 20110513
	private String billingStreetCatgCode;//add 20110513
	private String billingSectionDesc;//add 20110513
	private String billingSectionCode;//add 20110513
	private String billingDistrictDesc;//add 20110513
	private boolean billingUnlinkSectionFlag=false; //add 20110513
	private String billingDistrictCode;//add 20110513
	private String billingAreaDesc;//add 20110513
	private String billingAreaCode;//add 20110513
	private String billingLotHouseInd; //add 20110520
	private String smsLang;
	private String otherContactPhone; //add by herbert 20110720
	private String phInd;
	private String hktPremierAddr;
	private boolean foreignDomesticHelperInd; //add by wilson 20120221, for supportdoc, isForeignDomesticHelper
	private boolean byPassValidation=false;// add by wilson 20120301, for fraft-pre-pend order
	private boolean amend;
	private boolean privacyInd= false; //add by  20130321
	private Date privacyStampDate;
	private boolean suppressLocalTopUpInd = false;   //20130827
	private boolean suppressRoamTopUpInd = false;    //20130827
	private List<String> selectedBillMedia;//Paper bill Athena 20130925	
	private String dsMissDoc; //add by nancy 20131122
	private boolean mob0060OptOutInd = false;   //20140130
	private boolean csPortalBool;
	private String csPortalInd;
	private String csPortalStatus;
	//Company Authorized Representative
	private String repIdDocType;
	private String repIdDocNum;
	private String companyDoc;
	private String mrtSelection;
	
	private String bomCustAddrOverrideInd;
	private String activationCd;
	
	private String brand;
	private String simType;
	private String numType;
	private boolean mrtThresholdOverflow;
	private String mrtThresholdOverflowAuthInd;
	private CustomerBasicInfoDTO customerBasicInfoDTO;
	private String optOUtRemarks;
	private String dummyEmail;
	private boolean hktOptOut;
	private boolean clubOptOut;
	private boolean hktClubOptOut;
	private List<String> clubOptReaList;
	private String clubOptRea;
	private String clubOptRmk;
	
	private String sameAsCustInd;
	private boolean sameAsCust;
	private ActualUserDTO actualUserDTO;
	
	private String pcrfAlertEmail;
	private String sameAsEbillAddrInd;
	private boolean sameAsEbillAddr;
	private String smsOptOutFirstRoam;
	private String smsOptOutRoamHu;
	private String pcrfSmsNum;
	private String pcrfSmsRecipient;
	private String pcrfAlertType;
	private String pcrfMupAlert;
	private String secSrvNum;
	
	private boolean studentPlanSubInd = false;
	private String appDateStr;
	private String acctNum;
	private String acctName;
	private String billPeriod;
	private String billFreq;
	private String smsNum;
	private String activeMobileNum;
	private String srvNum;
	private String acctType;
	private String acctInfo;
	private String isNew;
	

	private String careStatus;
	private String careOptInd;
	private String careDmSupInd;
	
	private boolean customercareDmSupInd = false;
	private String oBiptStatus;
	private boolean careCashOrderSignOffInd;
	private boolean careCashRegisterTimeInd;
	private boolean combineAccountRegisterTimeInd;
	private String checkAcctInfo;
	
	// MIP.P4 modification
	private String ivOfferNature;
	
	String theClubLogin; // for club earn point 20170227
	String clubMemberId; // for club earn point 20170322
	
	private boolean checkIsWhiteList;
	private String customerTier;
	
	private String hkbnInd;
	
	public CustomerProfileDTO() {
		this.ignoreCustomerCheck = false;
		this.isBomWsAvailable = true;
		this.customerType = "Personal";
		this.serviceType = "3G";
		this.nationality = "HKG";
		this.systemId = "MOB";
		
		this.setSameAsCust(true);
		this.actualUserDTO = new ActualUserDTO();
		
		this.pcrfAlertType = "01";
		this.setSameAsEbillAddr(true);
		this.pcrfSmsRecipient = "00";
		this.smsOptOutFirstRoam = "N";
		this.smsOptOutRoamHu = "N";
		this.pcrfMupAlert = "0";
	}
	
	public boolean isMob0060OptOutInd() {
		return mob0060OptOutInd;
	}

	public void setMob0060OptOutInd(boolean mob0060OptOutInd) {
		this.mob0060OptOutInd = mob0060OptOutInd;
	}

	public boolean isSuppressLocalTopUpInd() {
		return suppressLocalTopUpInd;
	}

	public boolean isSuppressRoamTopUpInd() {
		return suppressRoamTopUpInd;
	}

	public void setSuppressLocalTopUpInd(boolean suppressLocalTopUpInd) {
		this.suppressLocalTopUpInd = suppressLocalTopUpInd;
	}

	public void setSuppressRoamTopUpInd(boolean suppressRoamTopUpInd) {
		this.suppressRoamTopUpInd = suppressRoamTopUpInd;
	}
	
	public boolean isPrivacyInd() {
		return privacyInd;
	}

	public Date getPrivacyStampDate() {
		return privacyStampDate;
	}

	public void setPrivacyInd(boolean privacyInd) {
		this.privacyInd = privacyInd;
	}

	public void setPrivacyStampDate(Date privacyStampDate) {
		this.privacyStampDate = privacyStampDate;
	}	
	
	public boolean isAmend() {
		return amend;
	}

	public void setAmend(boolean amend) {
		this.amend = amend;
	}

	public boolean isNoBillingAddressFlag() {
		return noBillingAddressFlag;
	}

	public void setNoBillingAddressFlag(boolean noBillingAddressFlag) {
		this.noBillingAddressFlag = noBillingAddressFlag;
	}

	private String billLang;
	
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

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
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

	public boolean getIsBomWsAvailable() {
		return isBomWsAvailable;
	}

	public void setIsBomWsAvailable(boolean isBomWsAvailable) {
		this.isBomWsAvailable = isBomWsAvailable;
	}

	public boolean isValidCustomer() {
		return isValidCustomer;
	}

	public void setValidCustomer(boolean isValidCustomer) {
		this.isValidCustomer = isValidCustomer;
	}

	public boolean getIgnoreCustomerCheck() {
		return ignoreCustomerCheck;
	}

	public void setIgnoreCustomerCheck(boolean ignoreCustomerCheck) {
		this.ignoreCustomerCheck = ignoreCustomerCheck;
	}
	
    public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		if (address1 != null)
			this.address1 = address1.toUpperCase();
	}
	
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		if (address2 != null)
			this.address2 = address2.toUpperCase();
	}
	
	
	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		if (address3 != null)
			this.address3 = address3.toUpperCase();
	}


	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		if (flat != null)
			this.flat = flat.toUpperCase();
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		if (floor != null)
		this.floor = floor.toUpperCase();
	}

	public String getServiceBoundaryNum() {
		return serviceBoundaryNum;
	}

	public void setServiceBoundaryNum(String serviceBoundaryNum) {
		this.serviceBoundaryNum = serviceBoundaryNum.toUpperCase();
	}

	public String getLotHouseInd() {
		return lotHouseInd;
	}

	public void setLotHouseInd(String lotHouseInd) {
		if (lotHouseInd != null)
		this.lotHouseInd = lotHouseInd.toUpperCase();
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		if (streetNum != null){
			this.streetNum = streetNum.toUpperCase();
		}
		if (streetNum != null && !"".equals(streetNum)) {
			this.lotHouseInd = "S";
			
		}
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		if (lotNum != null){
			this.lotNum = lotNum.toUpperCase();
		}
		
		if (lotNum != null && !"".equals(lotNum.trim())) {//assign lotHouseInd
			this.lotHouseInd = "L";
			
		}

	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		if (buildingName != null)
			this.buildingName = buildingName.toUpperCase();
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		if (streetName != null) {
			this.streetName = streetName.toUpperCase();
			//System.out.println("streetName != null");
		}else if("".equals(streetName)){
			//System.out.println("empty equals(streetName)");
			
			
		}
	}

	public String getStreetCatgDesc() {
		return streetCatgDesc;
	}

	public void setStreetCatgDesc(String streetCatgDesc) {
		if (streetCatgDesc != null)
		this.streetCatgDesc = streetCatgDesc.toUpperCase();
	}

	public String getStreetCatgCode() {
		return streetCatgCode;
	}

	public void setStreetCatgCode(String streetCatgCode) {
		if (streetCatgCode != null)
		this.streetCatgCode = streetCatgCode.toUpperCase();
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		if (sectionDesc != null)
		this.sectionDesc = sectionDesc.toUpperCase();
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		if (sectionCode != null)
		this.sectionCode = sectionCode.toUpperCase();
	}

	public String getDistrictDesc() {
		return districtDesc;
	}

	public void setDistrictDesc(String districtDesc) {
		if (districtDesc != null)
		this.districtDesc = districtDesc.toUpperCase();
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		if (districtCode != null)
		this.districtCode = districtCode.toUpperCase();
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		if (areaDesc != null)
		this.areaDesc = areaDesc.toUpperCase();
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		if (areaCode != null)
		this.areaCode = areaCode.toUpperCase();
	}

	public String getQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(String quickSearch) {
		if (quickSearch != null)
		this.quickSearch = quickSearch.toUpperCase();
	}
	
	public String getBillingQuickSearch() {
		return billingQuickSearch;
	}

	public void setBillingQuickSearch(String billingQuickSearch) {
		if (billingQuickSearch != null)
		this.billingQuickSearch = billingQuickSearch.toUpperCase();
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
		if (serviceNum != null)
		this.serviceNum = serviceNum.toUpperCase();
	}

	public String getAddrProofInd() {
		return addrProofInd;
	}

	public void setAddrProofInd(String addrProofInd) {
		this.addrProofInd = addrProofInd;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		if (contactName != null)
		this.contactName = contactName.toUpperCase();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setContactPhone(String contactPhone) {
		if (contactPhone != null)
		this.contactPhone = contactPhone.toUpperCase();
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setEmailAddr(String emailAddr) {
		if("".equals(emailAddr) || emailAddr ==null){
			noEmailFlag=true;
		}else{
			noEmailFlag=false;
			
		}
		this.emailAddr = emailAddr;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public String getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}

	public String getBomCustNum() {
		return bomCustNum;
	}

	public void setBomCustNum(String bomCustNum) {
		this.bomCustNum = bomCustNum;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		if (custFirstName != null)
		this.custFirstName = custFirstName.toUpperCase();
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		if (custLastName != null)
		this.custLastName = custLastName.toUpperCase();
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
		if (idDocNum != null)
		this.idDocNum = idDocNum.toUpperCase();
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		if (companyName != null)
			this.companyName = companyName.toUpperCase();
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getIndustrySubType() {
		return industrySubType;
	}

	public void setIndustrySubType(String industrySubType) {
		this.industrySubType = industrySubType;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getSingleLineAddress() {
		StringBuffer sb = new StringBuffer();

		if (this.flat != null && !"".equals(this.flat.trim())) {
			sb.append(this.flat);
			sb.append(" ");
		}

		if (this.floor != null && !"".equals(this.floor.trim())) {
			sb.append(this.floor);
			sb.append(" ");
		}

		if (this.buildingName != null && !"".equals(this.buildingName.trim())) {
			sb.append(this.buildingName);
			sb.append(" ");
		}

		if (this.lotNum != null && !"".equals(this.lotNum.trim())) {
			sb.append(this.lotNum);
			sb.append(" ");
		} else if (this.streetNum != null
				&& !"".equals(this.streetNum.trim())) {
			sb.append(this.streetNum);
			sb.append(" ");
		}

		if (this.streetName != null && !"".equals(this.streetName.trim())) {
			sb.append(this.streetName);
			sb.append(" ");
			sb.append(this.streetCatgDesc);
			sb.append(" ");
		}

		if (this.sectionDesc != null && !"".equals(this.sectionDesc.trim())) {
			sb.append(this.sectionDesc);
			sb.append(" ");
		}

		if (this.districtDesc != null && !"".equals(this.districtDesc.trim())) {
			sb.append(this.districtDesc);
			sb.append(" ");
		}

		if (this.areaDesc != null && !"".equals(this.areaDesc.trim())) {
			sb.append(this.areaDesc);
			sb.append(" ");
		}

		return sb.toString();
	}
	
	public String getBillingSingleLineAddress() {
		StringBuffer sb = new StringBuffer();

		if (this.billingFlat != null && !"".equals(this.billingFlat.trim())) {
			sb.append(this.billingFlat);
			sb.append(" ");
		}

		if (this.billingFloor != null && !"".equals(this.billingFloor.trim())) {
			sb.append(this.billingFloor);
			sb.append(" ");
		}

		if (this.billingBuildingName != null && !"".equals(this.billingBuildingName.trim())) {
			sb.append(this.billingBuildingName);
			sb.append(" ");
		}

		if (this.billingLotNum != null && !"".equals(this.billingLotNum.trim())) {
			sb.append(this.billingLotNum);
			sb.append(" ");
		} else if (this.billingStreetNum != null
				&& !"".equals(this.billingStreetNum.trim())) {
			sb.append(this.billingStreetNum);
			sb.append(" ");
		}

		if (this.billingStreetName != null && !"".equals(this.billingStreetName.trim())) {
			sb.append(this.billingStreetName);
			sb.append(" ");
			sb.append(this.billingStreetCatgDesc);
			sb.append(" ");
		}

		if (this.billingSectionDesc != null && !"".equals(this.billingSectionDesc.trim())) {
			sb.append(this.billingSectionDesc);
			sb.append(" ");
		}

		if (this.billingDistrictDesc != null && !"".equals(this.billingDistrictDesc.trim())) {
			sb.append(this.billingDistrictDesc);
			sb.append(" ");
		}

		if (this.billingAreaDesc != null && !"".equals(this.billingAreaDesc.trim())) {
			sb.append(this.billingAreaDesc);
			sb.append(" ");
		}

		return sb.toString();
	}

	public String getBillingAddress() {
		return getBillingSingleLineAddress();
	}

	public String getAddress() {
		return getSingleLineAddress();
	}

	public String getDobStr() {
		return dobStr;
	}

	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}

	public void setNoEmailFlag(boolean noEmailFlag) {
		this.noEmailFlag = noEmailFlag;
	}

	public boolean isNoEmailFlag() {
		return noEmailFlag;
	}

	public void setCustAddressFlag(boolean custAddressFlag) {
		this.custAddressFlag = custAddressFlag;
	}

	public boolean isCustAddressFlag() {
		return custAddressFlag;
	}

	
	
	public void setCustAddressFlag2(boolean custAddressFlag2) {
		this.custAddressFlag2 = custAddressFlag2;
	}

	public boolean isCustAddressFlag2() {
		return custAddressFlag2;
	}
	public void setUnlinkSectionFlag(boolean unlinkSectionFlag) {
		this.unlinkSectionFlag = unlinkSectionFlag;
	}

	public boolean isUnlinkSectionFlag() {
		return unlinkSectionFlag;
	}

	public void setBillingCustAddressFlag(boolean billingCustAddressFlag) {
		this.billingCustAddressFlag = billingCustAddressFlag;
	}

	public boolean isBillingCustAddressFlag() {
		return billingCustAddressFlag;
	}
	

	public String getBillingFlat() {
		return billingFlat;
	}

	public String getBillingFloor() {
		return billingFloor;
	}

	public String getBillingLotNum() {
		return billingLotNum;
	}

	public String getBillingBuildingName() {
		return billingBuildingName;
	}

	public String getBillingStreetNum() {
		return billingStreetNum;
	}

	public String getBillingStreetName() {
		return billingStreetName;
	}

	public String getBillingStreetCatgDesc() {
		return billingStreetCatgDesc;
	}

	public String getBillingStreetCatgCode() {
		return billingStreetCatgCode;
	}

	public String getBillingSectionDesc() {
		return billingSectionDesc;
	}

	public String getBillingSectionCode() {
		return billingSectionCode;
	}

	public String getBillingDistrictDesc() {
		return billingDistrictDesc;
	}

	public boolean isBillingUnlinkSectionFlag() {
		return billingUnlinkSectionFlag;
	}

	public String getBillingDistrictCode() {
		return billingDistrictCode;
	}

	public String getBillingAreaDesc() {
		return billingAreaDesc;
	}

	public String getBillingAreaCode() {
		return billingAreaCode;
	}

	public void setBillingFlat(String billingFlat) {
		if (billingFlat != null)
		this.billingFlat = billingFlat.toUpperCase();
	}

	public void setBillingFloor(String billingFloor) {
		if (billingFloor != null)
		this.billingFloor = billingFloor.toUpperCase();
	}

	public void setBillingLotNum(String billingLotNum) {
		if (billingLotNum != null)
		this.billingLotNum = billingLotNum.toUpperCase();
		
		if (billingLotNum != null && !"".equals(billingLotNum.trim())) {//assign billingLotHouseInd
			this.billingLotHouseInd = "L";
		}
	}

	public void setBillingBuildingName(String billingBuildingName) {
		if (billingBuildingName != null)
		this.billingBuildingName = billingBuildingName.toUpperCase();
	}

	public void setBillingStreetNum(String billingStreetNum) {
		if (billingStreetNum != null){
		this.billingStreetNum = billingStreetNum.toUpperCase();
		}
		
		if (billingStreetNum != null && !"".equals(billingStreetNum)) {
			this.billingLotHouseInd = "S";
			
		}
	}

	public void setBillingStreetName(String billingStreetName) {
		if (billingStreetName != null)
		this.billingStreetName = billingStreetName.toUpperCase();
	}

	public void setBillingStreetCatgDesc(String billingStreetCatgDesc) {
		if (billingStreetCatgDesc != null)
		this.billingStreetCatgDesc = billingStreetCatgDesc.toUpperCase();
	}

	public void setBillingStreetCatgCode(String billingStreetCatgCode) {
		if (billingStreetCatgCode != null)
		this.billingStreetCatgCode = billingStreetCatgCode.toUpperCase();
	}

	public void setBillingSectionDesc(String billingSectionDesc) {
		if (billingSectionDesc != null)
		this.billingSectionDesc = billingSectionDesc.toUpperCase();
	}

	public void setBillingSectionCode(String billingSectionCode) {
		if (billingSectionCode != null)
		this.billingSectionCode = billingSectionCode.toUpperCase();
	}

	public void setBillingDistrictDesc(String billingDistrictDesc) {
		if (billingDistrictDesc != null)
		this.billingDistrictDesc = billingDistrictDesc.toUpperCase();
	}

	public void setBillingUnlinkSectionFlag(boolean billingUnlinkSectionFlag) {
	
		this.billingUnlinkSectionFlag = billingUnlinkSectionFlag;
	}

	public void setBillingDistrictCode(String billingDistrictCode) {
		if (billingDistrictCode != null)
		this.billingDistrictCode = billingDistrictCode.toUpperCase();
	}

	public void setBillingAreaDesc(String billingAreaDesc) {
		if (billingAreaDesc != null)
		this.billingAreaDesc = billingAreaDesc.toUpperCase();
	}

	public void setBillingAreaCode(String billingAreaCode) {
		if (billingAreaCode != null)
		this.billingAreaCode = billingAreaCode.toUpperCase();
	}

	public void setBillingLotHouseInd(String billingLotHouseInd) {
		if (billingLotHouseInd != null)
			this.billingLotHouseInd = billingLotHouseInd.toUpperCase();
	}

	public String getBillingLotHouseInd() {
		return billingLotHouseInd;
	}

	public String getOtherContactPhone() {
		return otherContactPhone;
	}

	public void setOtherContactPhone(String otherContactPhone) {
		if (otherContactPhone != null)
			this.otherContactPhone = otherContactPhone.toUpperCase();
	}

	public String getPhInd() {
	    return phInd;
	}

	public void setPhInd(String phInd) {
	    this.phInd = phInd;
	}

	public String getHktPremierAddr() {
		return hktPremierAddr;
	}

	public void setHktPremierAddr(String hktPremierAddr) {
		this.hktPremierAddr = hktPremierAddr;
	}

	public boolean isForeignDomesticHelperInd() {
		return foreignDomesticHelperInd;
	}

	public void setForeignDomesticHelperInd(boolean foreignDomesticHelperInd) {
		this.foreignDomesticHelperInd = foreignDomesticHelperInd;
	}

	public boolean isByPassValidation() {
		return byPassValidation;
	}

	public void setByPassValidation(boolean byPassValidation) {
		this.byPassValidation = byPassValidation;
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

	public List<String> getSelectedBillMedia() { //Paper bill Athena 20130925
		return selectedBillMedia;
	}

	public void setSelectedBillMedia(List<String> selectedBillMedia) {//Paper bill Athena 20130925
		this.selectedBillMedia = selectedBillMedia;
	}	
	
	public String getDsMissDoc() {
		return dsMissDoc;
	}
	public void setDsMissDoc(String dsMissDoc) {
		this.dsMissDoc = dsMissDoc;
	}

	public String getCsPortalStatus() {
		return csPortalStatus;
	}

	public void setCsPortalStatus(String csPortalStatus) {
		this.csPortalStatus = csPortalStatus;
	}

	public String getRepIdDocType() {
		return repIdDocType;
	}

	public void setRepIdDocType(String repIdDocType) {
		this.repIdDocType = repIdDocType;
	}

	public String getRepIdDocNum() {
		return repIdDocNum;
	}

	public void setRepIdDocNum(String repIdDocNum) {
		this.repIdDocNum = repIdDocNum;
	}

	public String getCompanyDoc() {
		return companyDoc;
	}

	public void setCompanyDoc(String companyDoc) {
		this.companyDoc = companyDoc;
	}

	public String getMrtSelection() {
		return mrtSelection;
	}

	public void setMrtSelection(String mrtSelection) {
		this.mrtSelection = mrtSelection;
	}

	public String getBomCustAddrOverrideInd() {
		return bomCustAddrOverrideInd;
	}

	public void setBomCustAddrOverrideInd(String bomCustAddrOverrideInd) {
		this.bomCustAddrOverrideInd = bomCustAddrOverrideInd;
	}

	public String getActivationCd() {
		return activationCd;
	}

	public void setActivationCd(String activationCd) {
		this.activationCd = activationCd;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public String getNumType() {
		return numType;
	}

	public void setNumType(String numType) {
		this.numType = numType;
	}

	public boolean isMrtThresholdOverflow() {
		return mrtThresholdOverflow;
	}

	public void setMrtThresholdOverflow(boolean mrtThresholdOverflow) {
		this.mrtThresholdOverflow = mrtThresholdOverflow;
	}

	public String getMrtThresholdOverflowAuthInd() {
		return mrtThresholdOverflowAuthInd;
	}

	public void setMrtThresholdOverflowAuthInd(String mrtThresholdOverflowAuthInd) {
		this.mrtThresholdOverflowAuthInd = mrtThresholdOverflowAuthInd;
	}

	public CustomerBasicInfoDTO getCustomerBasicInfoDTO() {
		return customerBasicInfoDTO;
	}

	public void setCustomerBasicInfoDTO(CustomerBasicInfoDTO customerBasicInfoDTO) {
		this.customerBasicInfoDTO = customerBasicInfoDTO;
	}

	public String getOptOUtRemarks() {
		return optOUtRemarks;
	}

	public void setOptOUtRemarks(String optOUtRemarks) {
		this.optOUtRemarks = optOUtRemarks;
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

	public String getClubOptRea() {
		return clubOptRea;
	}

	public void setClubOptRea(String clubOptRea) {
		this.clubOptRea = clubOptRea;
	}

	public String getClubOptRmk() {
		return clubOptRmk;
	}

	public void setClubOptRmk(String clubOptRmk) {
		this.clubOptRmk = clubOptRmk;
	}

	public boolean isHktClubOptOut() {
		return hktClubOptOut;
	}

	public void setHktClubOptOut(boolean hktClubOptOut) {
		this.hktClubOptOut = hktClubOptOut;
	}

	public List<String> getClubOptReaList() {
		return clubOptReaList;
	}

	public void setClubOptReaList(List<String> clubOptReaList) {
		this.clubOptReaList = clubOptReaList;
	}

	public boolean isStudentPlanSubInd() {
		return studentPlanSubInd;
	}

	public void setStudentPlanSubInd(boolean studentPlanSubInd) {
		this.studentPlanSubInd = studentPlanSubInd;
	}

	public String getAppDateStr() {
		return appDateStr;
	}

	public void setAppDateStr(String appDateStr) {
		this.appDateStr = appDateStr;
	}
	
	public String getSameAsCustInd() {
		return sameAsCustInd;
	}

	public void setSameAsCustInd(String sameAsCustInd) {
		if ("Y".equalsIgnoreCase(sameAsCustInd)) {
			this.sameAsCust = true;
		}  else {
			this.sameAsCust = false;
		}
		this.sameAsCustInd = sameAsCustInd;
	}

	public boolean isSameAsCust() {
		return sameAsCust;
	}

	public void setSameAsCust(boolean sameAsCust) {
		if (sameAsCust == true) {
			this.sameAsCustInd = "Y";
		}  else {
			this.sameAsCustInd = "N";
		}
		this.sameAsCust = sameAsCust;
	}

	public ActualUserDTO getActualUserDTO() {
		return actualUserDTO;
	}

	public void setActualUserDTO(ActualUserDTO actualUserDTO) {
		this.actualUserDTO = actualUserDTO;
	}

	public String getPcrfAlertEmail() {
		return pcrfAlertEmail;
	}

	public void setPcrfAlertEmail(String pcrfAlertEmail) {
		this.pcrfAlertEmail = pcrfAlertEmail;
	}

	public String getSameAsEbillAddrInd() {
		return sameAsEbillAddrInd;
	}

	public void setSameAsEbillAddrInd(String sameAsEbillAddrInd) {
		if ("Y".equalsIgnoreCase(sameAsEbillAddrInd)) {
			this.sameAsEbillAddr = true;
		}  else {
			this.sameAsEbillAddr = false;
		}
		this.sameAsEbillAddrInd = sameAsEbillAddrInd;
	}

	public boolean isSameAsEbillAddr() {
		return sameAsEbillAddr;
	}

	public void setSameAsEbillAddr(boolean sameAsEbillAddr) {
		if (sameAsEbillAddr == true) {
			this.sameAsEbillAddrInd = "Y";
		}  else {
			this.sameAsEbillAddrInd = "N";
		}
		this.sameAsEbillAddr = sameAsEbillAddr;
	}

	public String getSmsOptOutFirstRoam() {
		return smsOptOutFirstRoam;
	}

	public void setSmsOptOutFirstRoam(String smsOptOutFirstRoam) {
		if (smsOptOutFirstRoam == null){
			smsOptOutFirstRoam = "N";
		}
		this.smsOptOutFirstRoam = smsOptOutFirstRoam;
	}

	public String getSmsOptOutRoamHu() {
		return smsOptOutRoamHu;
	}

	public void setSmsOptOutRoamHu(String smsOptOutRoamHu) {
		if (smsOptOutRoamHu == null){
			smsOptOutRoamHu = "N";
		}
		this.smsOptOutRoamHu = smsOptOutRoamHu;
	}

	public String getPcrfSmsNum() {
		return pcrfSmsNum;
	}

	public void setPcrfSmsNum(String pcrfSmsNum) {
		this.pcrfSmsNum = pcrfSmsNum;
	}

	public String getPcrfSmsRecipient() {
		return pcrfSmsRecipient;
	}

	public void setPcrfSmsRecipient(String pcrfSmsRecipient) {
		this.pcrfSmsRecipient = pcrfSmsRecipient;
	}

	public String getPcrfAlertType() {
		return pcrfAlertType;
	}

	public void setPcrfAlertType(String pcrfAlertType) {
		this.pcrfAlertType = pcrfAlertType;
	}

	public String getPcrfMupAlert() {
		return pcrfMupAlert;
	}

	public void setPcrfMupAlert(String pcrfMupAlert) {
		this.pcrfMupAlert = pcrfMupAlert;
	}

	public String getSecSrvNum() {
		return secSrvNum;
	}

	public void setSecSrvNum(String secSrvNum) {
		this.secSrvNum = secSrvNum;
	}

	public boolean isCareCashOrderSignOffInd() {
		return careCashOrderSignOffInd;
	}

	public void setCareCashOrderSignOffInd(boolean careCashOrderSignOffInd) {
		this.careCashOrderSignOffInd = careCashOrderSignOffInd;
	}

	public boolean isCareCashRegisterTimeInd() {
		return careCashRegisterTimeInd;
	}

	public void setCombineAccountRegisterTimeInd(boolean combineAccountRegisterTimeInd) {
		this.combineAccountRegisterTimeInd = combineAccountRegisterTimeInd;
	}

	public boolean isCombineAccountRegisterTimeInd() {
		return combineAccountRegisterTimeInd;
	}
	
	public void setCareCashRegisterTimeInd(boolean careCashRegisterTimeInd) {
		this.careCashRegisterTimeInd = careCashRegisterTimeInd;
	}
	
	public String getCareStatus() {
		return careStatus;
	}

	public void setCareStatus(String careStatus) {
		this.careStatus = careStatus;
	}

	public String getCareOptInd() {
		return careOptInd;
	}

	public void setCareOptInd(String careOptInd) {
		this.careOptInd = careOptInd;
	}

	public String getCareDmSupInd() {
		return careDmSupInd;
	}

	public void setCareDmSupInd(String careDmSupInd) {
		this.careDmSupInd = careDmSupInd;
	}

	public boolean isCustomercareDmSupInd() {
		return customercareDmSupInd;
	}

	public void setCustomercareDmSupInd(boolean customercareDmSupInd) {
		this.customercareDmSupInd = customercareDmSupInd;
	}

	public String getoBiptStatus() {
		return oBiptStatus;
	}

	public void setoBiptStatus(String oBiptStatus) {
		this.oBiptStatus = oBiptStatus;
	}
	

	public String getActiveMobileNum() {
		return activeMobileNum;
	}

	public void setActiveMobileNum(String activeMobileNum) {
		this.activeMobileNum = activeMobileNum;
	}

	public String getSmsNum() {
		return smsNum;
	}

	public void setSmsNum(String smsNum) {
		this.smsNum = smsNum;
	}

	public String getBillFreq() {
		return billFreq;
	}

	public void setBillFreq(String billFreq) {
		this.billFreq = billFreq;
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	
	public String getMobCustNum() {
		return mobCustNum;
	}

	public void setMobCustNum(String mobCustNum) {
		this.mobCustNum = mobCustNum;
	}

	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctInfo() {
		return acctInfo;
	}

	public void setAcctInfo(String acctInfo) {
		this.acctInfo = acctInfo;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getCheckAcctInfo() {
		return checkAcctInfo;
	}

	public void setCheckAcctInfo(String checkAcctInfo) {
		this.checkAcctInfo = checkAcctInfo;
	}

	// MIP.P4 modification
	public String getIvOfferNature() {
		return ivOfferNature;
	}
	// MIP.P4 modification
	public void setIvOfferNature(String ivOfferNature) {
		this.ivOfferNature = ivOfferNature;
	}

	public String getTheClubLogin() {
		return theClubLogin;
	}

	public void setTheClubLogin(String theClubLogin) {
		this.theClubLogin = theClubLogin;
	}

	public String getClubMemberId() {
		return clubMemberId;
	}

	public void setClubMemberId(String clubMemberId) {
		this.clubMemberId = clubMemberId;
	}

	public boolean getCheckIsWhiteList() {
		return checkIsWhiteList;
	}

	public void setCheckIsWhiteList(boolean checkIsWhiteList) {
		this.checkIsWhiteList = checkIsWhiteList;
	}

	public String getCustomerTier() {
		return customerTier;
	}

	public void setCustomerTier(String customerTier) {
		this.customerTier = customerTier;
	}

	public String getHkbnInd() {
		return hkbnInd;
	}

	public void setHkbnInd(String hkbnInd) {
		this.hkbnInd = hkbnInd;
	}

	public String getProjectEagleAddressLine1() {
		
		StringBuffer sb = new StringBuffer();
		
		if (StringUtils.isNotBlank(this.flat)) {
			sb.append("FLAT/ROOM ");
			sb.append(this.flat);
			sb.append(", ");
		}

		if (StringUtils.isNotBlank(this.floor)) {
			sb.append(this.floor);
			sb.append("/F, ");
		}
		
		if (StringUtils.isNotBlank(this.lotNum)) {
			sb.append(this.lotNum);
			sb.append(" ");
		}
		
		if (StringUtils.isNotBlank(this.buildingName)) {
			sb.append(this.buildingName);
			sb.append(", ");
		}

		return sb.toString().trim();
	}
	
	public String getProjectEagleAddressLine2() {
		
		StringBuffer sb = new StringBuffer();
			
		if (StringUtils.isBlank(this.lotHouseInd)
				|| !StringUtils.equalsIgnoreCase("L", this.lotHouseInd)) {
			
			if (StringUtils.isNotBlank(this.streetNum)) {
				sb.append(this.streetNum);
				sb.append(" ");
			}
			
			if (StringUtils.isNotBlank(this.streetName)) {
				sb.append(this.streetName);
				sb.append(" ");
			}

		}
		
		if (StringUtils.isNotBlank(this.streetCatgDesc)) {
			sb.append(this.streetCatgDesc);
			sb.append(", ");
		}
		
		if (StringUtils.isNotBlank(this.sectionDesc)
				&& !StringUtils.equalsIgnoreCase("NULL", this.sectionDesc)) {
			sb.append(this.sectionDesc);
			sb.append(", ");
		}
		
		return sb.toString().trim();
		
	}
	
	public String getProjectEagleAddressLine3() {
		
		StringBuffer sb = new StringBuffer();
			
		if (StringUtils.isNotBlank(this.districtDesc)) {
			sb.append(this.districtDesc);
			sb.append(", ");
		}
		
		if (StringUtils.isNotBlank(this.areaDesc)) {
			sb.append(this.areaDesc);
		}
		
		return sb.toString().trim();
		
	}
	
}