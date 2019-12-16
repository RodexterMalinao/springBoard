package com.bomwebportal.dto.report;

import java.util.ArrayList;
import java.util.Date;


public class RptAppMobileServPage1 extends ReportDTO {

	private static final long serialVersionUID = -3311684335459264341L;
	
	public static final String JASPER_TEMPLATE = "AppMobileServ";
	
	// DTO Not Found List
	//private String customerType; // eg. Personal
	//private String applicationNo; // eg. C3256875
	//private String applicationDate;  // eg. 01092010
	//private String serviceType; // eg. 3G
		
	//Refer from customerDTO
	private String idDocType;// ID_DOC_TYPE VARCHAR2(4 BYTE),
	private String idDocNum;// ID_DOC_NUM VARCHAR2(30 BYTE)
	private String firstName;// FIRST_NAME VARCHAR2(40 BYTE),
	private String lastName;// LAST_NAME VARCHAR2(40 BYTE),
	private Date dob;
	private String contactPhone;
	private String contactName;
	private String emailAddr;
	private String address;
	private String customerNum;// CUST_NO VARCHAR2(8 BYTE),
	private String bomCustNum; // MOB_CUST_NO VARCHAR2(8 BYTE),
	private String flat;
	private String floor;
	private String buildingName;
	private String houseLotNum;
	private String streetName;
	private String sectionDesc;
	private String districtDesc;
	private String areaDesc;
	private String dobDay;// DOB
	private String dobMonth;// DOB
	private String dobYear;// DOB	
	private String areaCode;
	private String addrProofInd;
	private String lob;
	private String serviceNum;	
	
	
	// Next Page Report
//	private ArrayList<RebateDetailDTO> rebateDetails;
	private String totalMonthlyFee;
	private String creditCardNo;
	private String creditCardHolderName;
	private String creditCardHKID;
	private String creditCardValidThru;
	private String contactTelNo;
	private String staffNo;
	private String staffName;
	private String shopCode;
	private String staffSignature;
	
	private ArrayList<RptVasDetailDTO> mainServDtls;
	private ArrayList<RptVasDetailDTO> vasDtls;
	private ArrayList<RebateDetailDTO> rebateDetails;
	
	public RptAppMobileServPage1() {
		this.setJasperName(JASPER_TEMPLATE);
	}

	public ArrayList<RebateDetailDTO> getRebateDetails() {
		return rebateDetails;
	}

	public void setRebateDetails(ArrayList<RebateDetailDTO> rebateDetails) {
		this.rebateDetails = rebateDetails;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getHouseLotNum() {
		return houseLotNum;
	}

	public void setHouseLotNum(String houseLotNum) {
		this.houseLotNum = houseLotNum;
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

	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public String getDobDay() {
		return dobDay;
	}

	public void setDobDay(String dobDay) {
		this.dobDay = dobDay;
	}

	public String getDobMonth() {
		return dobMonth;
	}

	public void setDobMonth(String dobMonth) {
		this.dobMonth = dobMonth;
	}

	public String getDobYear() {
		return dobYear;
	}

	public void setDobYear(String dobYear) {
		this.dobYear = dobYear;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public ArrayList<RptVasDetailDTO> getVasDtls() {
		return vasDtls;
	}

	public void setVasDtls(ArrayList<RptVasDetailDTO> vasDtls) {
		this.vasDtls = vasDtls;
	}

	public String getTotalMonthlyFee() {
		return totalMonthlyFee;
	}

	public void setTotalMonthlyFee(String totalMonthlyFee) {
		this.totalMonthlyFee = totalMonthlyFee;
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

	public String getCreditCardHKID() {
		return creditCardHKID;
	}

	public void setCreditCardHKID(String creditCardHKID) {
		this.creditCardHKID = creditCardHKID;
	}

	public String getCreditCardValidThru() {
		return creditCardValidThru;
	}

	public void setCreditCardValidThru(String creditCardValidThru) {
		this.creditCardValidThru = creditCardValidThru;
	}

	public String getContactTelNo() {
		return contactTelNo;
	}

	public void setContactTelNo(String contactTelNo) {
		this.contactTelNo = contactTelNo;
	}

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getStaffSignature() {
		return staffSignature;
	}

	public void setStaffSignature(String staffSignature) {
		this.staffSignature = staffSignature;
	}

	public ArrayList<RptVasDetailDTO> getMainServDtls() {
		return mainServDtls;
	}

	public void setMainServDtls(ArrayList<RptVasDetailDTO> mainServDtls) {
		this.mainServDtls = mainServDtls;
	}
}
