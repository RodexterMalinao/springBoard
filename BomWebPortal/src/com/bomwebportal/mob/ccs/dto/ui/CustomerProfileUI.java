package com.bomwebportal.mob.ccs.dto.ui;

import java.util.Date;

import com.bomwebportal.mob.ccs.dto.ContactDTO;

public class CustomerProfileUI implements java.io.Serializable {

	public CustomerProfileUI() {
		this.ignoreCustomerCheck = false;
		this.isBomWsAvailable = true;
		this.customerType = "Personal";
		this.serviceType = "3G";
		this.nationality = "HKG";
		this.systemId = "MOB";
		this.setPrimaryContact(new ContactDTO());
	}

	private ContactDTO primaryContact;

	private static final long serialVersionUID = -3311684335459264341L;

	private String orderId;
	private String systemId;
	private String customerType;
	private String serviceType;
	private String contactName;
	private String contactPhone;
	private String emailAddr;
	private String address;
	private String customerNum;// CUST_NO VARCHAR2(8 BYTE),
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
	private String flat;
	private String floor;
	private String serviceBoundaryNum;
	private String lotHouseInd;
	private String streetNum; // 20110530 b4 call houseLotNum
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
	private boolean noEmailFlag = false;//add by wilson 20110302
	private boolean custAddressFlag = false;//false=quick search==> true ,self==> input
	private boolean unlinkSectionFlag = false;//false=>link , true=> unlink
	// //20110513 add billing info
	private boolean noBillingAddressFlag = true;// false=> noBillingAddress, true ==>have billingAddress
	private String billingAddress; // one line address
	private boolean billingCustAddressFlag = false; //using quicksearch or not
	private String billingQuickSearch;// add 20110513
	private String billingFlat;// add 20110513
	private String billingFloor;// add 20110513
	private String billingLotNum;// add 20110513
	private String billingBuildingName;// add 20110513
	private String billingStreetNum;// add 20110513 , edit 20110530, b4 is billingHouseLotNum
	private String billingStreetName;// add 20110513
	private String billingStreetCatgDesc;// add 20110513
	private String billingStreetCatgCode;// add 20110513
	private String billingSectionDesc;// add 20110513
	private String billingSectionCode;// add 20110513
	private String billingDistrictDesc;// add 20110513
	private boolean billingUnlinkSectionFlag = false; // add 20110513
	private String billingDistrictCode;// add 20110513
	private String billingAreaDesc;// add 20110513
	private String billingAreaCode;// add 20110513
	private String billingLotHouseInd; // add 20110520
	private String smsLang;
	private String otherContactPhone; // add by herbert 20110720
	
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
		if (streetNum != null) {
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
		if (lotNum != null) {
			this.lotNum = lotNum.toUpperCase();
		}

		if (lotNum != null && !"".equals(lotNum.trim())) {// assign lotHouseInd
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
			// System.out.println("streetName != null");
		} else if ("".equals(streetName)) {
			// System.out.println("empty equals(streetName)");

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
		if ("".equals(emailAddr) || emailAddr == null) {
			noEmailFlag = true;
		} else {
			noEmailFlag = false;

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

	/*
	 * public String getDobDay() { return dobDay; }
	 * 
	 * public void setDobDay(String dobDay) { this.dobDay = dobDay; }
	 * 
	 * public String getDobMonth() { return dobMonth; }
	 * 
	 * public void setDobMonth(String dobMonth) { this.dobMonth = dobMonth; }
	 * 
	 * public String getDobYear() { return dobYear; }
	 * 
	 * public void setDobYear(String dobYear) { this.dobYear = dobYear; }
	 */

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
		} else if (this.streetNum != null && !"".equals(this.streetNum.trim())) {
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

		if (this.billingBuildingName != null
				&& !"".equals(this.billingBuildingName.trim())) {
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

		if (this.billingStreetName != null
				&& !"".equals(this.billingStreetName.trim())) {
			sb.append(this.billingStreetName);
			sb.append(" ");
			sb.append(this.billingStreetCatgDesc);
			sb.append(" ");
		}

		if (this.billingSectionDesc != null
				&& !"".equals(this.billingSectionDesc.trim())) {
			sb.append(this.billingSectionDesc);
			sb.append(" ");
		}

		if (this.billingDistrictDesc != null
				&& !"".equals(this.billingDistrictDesc.trim())) {
			sb.append(this.billingDistrictDesc);
			sb.append(" ");
		}

		if (this.billingAreaDesc != null
				&& !"".equals(this.billingAreaDesc.trim())) {
			sb.append(this.billingAreaDesc);
			sb.append(" ");
		}

		return sb.toString();
	}

	public String getBillingAddress() {
		return getBillingSingleLineAddress();
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = getBillingSingleLineAddress();
	}

	public String getAddress() {
		return getSingleLineAddress();
	}

	public void setAddress(String address) {
		this.address = getSingleLineAddress();
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

		if (billingLotNum != null && !"".equals(billingLotNum.trim())) {// assign
																		// billingLotHouseInd
			this.billingLotHouseInd = "L";
		}
	}

	public void setBillingBuildingName(String billingBuildingName) {
		if (billingBuildingName != null)
			this.billingBuildingName = billingBuildingName.toUpperCase();
	}

	public void setBillingStreetNum(String billingStreetNum) {
		if (billingStreetNum != null) {
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

	public void setPrimaryContact(ContactDTO primaryContact) {
		this.primaryContact = primaryContact;
	}

	public ContactDTO getPrimaryContact() {
		return primaryContact;
	}

}
