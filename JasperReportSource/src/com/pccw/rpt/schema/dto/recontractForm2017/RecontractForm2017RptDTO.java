package com.pccw.rpt.schema.dto.recontractForm2017;

import com.pccw.rpt.schema.dto.ReportDTO;



public class RecontractForm2017RptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4608125991956020267L;
	
	private String faxNum;
	private String srvNum;
	private boolean eyeSrv;
	private boolean callingCardSrv;
	private boolean mobileIDDSrv;
	private boolean fixedIDDSrv;
	
	private String eyeSrvChkbox;
	private String callingCardChkbox;
	private String mobIDDChkbox;
	private String fixedIDDChkbox;
	private String eyeSrvStrikeThrough;
	private String resSrvStrikeThrough;
	
	//section A -- New customer
	private String toCustMrStrikeThrough;
	private String toCustMsStrikeThrough;
	private String toCustOthersStrikeThrough;
	private String toCustOthersName;
	private String toCustFamilyName;
	private String toCustGivenName;
	private String toCustCompanyName;
	private String toCustHkidStrikeThrough;
	private String toCustPassStrikeThrough;
	private String toCustBrStrikeThrough;
	private String toCustDocNum;
	private String toCustVerifiedByStaff;
	private String toCustBillingAddr;
	private String toCustContactNum;
	private String toCustEmailAddr;
	private String toCustEmailAddr2;
	
	private String paperBillChkbox;
	private String eBillChkbox;
	
	private String brMrStrikeThrough;
	private String brMsStrikeThrough;
	private String brOthersStrikeThrough;
	private String brOthersName;
	private String brFamilyName;
	private String brGivenName;
	private String brTitle;
	
	//section A -- Current customer
	private String agreeChkBox;
	
	private String fromCustMrStrikeThrough;
	private String fromCustMsStrikeThrough;
	private String fromCustOthersStrikeThrough;
	private String fromCustOthersName;
	private String fromCustFamilyName;
	private String fromCustGivenName;
	private String fromCustCompanyName;
	private String fromCustHkidStrikeThrough;
	private String fromCustPassStrikeThrough;
	private String fromCustBrStrikeThrough;
	private String fromCustDocNum;
	private String fromCustVerifiedByStaff;
	private String fromCustContactNum;
	private String fromCustEmailAddr;
	
	//section B -- Deceased customer
	private String decToCustMrStrikeThrough;
	private String decToCustMsStrikeThrough;
	private String decToCustOthersStrikeThrough;
	private String decToCustFamilyName;
	private String decToCustGivenName;
	private String decToCustRelationship;
	
	private String decCustMrStrikeThrough;
	private String decCustMsStrikeThrough;
	private String decCustOthersStrikeThrough;
	private String decCustFamilyName;
	private String decCustGivenName;
	
	//section D
	private String toCustMobileNum;
	
	//seciton E
	private String srvChkBox;
	private String theClubHktChkBox;
	
	//section F
	private byte[] toCustSignature;
	private String toSignDate;
	private byte[] fromCustSignature;
	private String fromSignDate;
	private byte[] decToCustSignature;
	private String decToSignDate;
	
	//internal use
	private String svcChkBox;
	private String salesmanCode;
	private String staffNo;
	private String salesStaffName;
	private String teamCode;
	private String staffContactDetails;
	private String newSrvChkBox;
	private String afRefNo;
	private boolean printTermsCondition = true;
	
	public String getFaxNum() {
		return faxNum;
	}
	public void setFaxNum(String faxNum) {
		this.faxNum = faxNum;
	}
	public String getSrvNum() {
		return srvNum;
	}
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	public boolean isEyeSrv() {
		return eyeSrv;
	}
	public void setEyeSrv(boolean eyeSrv) {
		this.eyeSrv = eyeSrv;
	}
	public boolean isCallingCardSrv() {
		return callingCardSrv;
	}
	public void setCallingCardSrv(boolean callingCardSrv) {
		this.callingCardSrv = callingCardSrv;
	}
	public boolean isMobileIDDSrv() {
		return mobileIDDSrv;
	}
	public void setMobileIDDSrv(boolean mobileIDDSrv) {
		this.mobileIDDSrv = mobileIDDSrv;
	}
	public boolean isFixedIDDSrv() {
		return fixedIDDSrv;
	}
	public void setFixedIDDSrv(boolean fixedIDDSrv) {
		this.fixedIDDSrv = fixedIDDSrv;
	}
	public String getEyeSrvChkbox() {
		return eyeSrvChkbox;
	}
	public void setEyeSrvChkbox(String eyeSrvChkbox) {
		this.eyeSrvChkbox = eyeSrvChkbox;
	}
	public String getCallingCardChkbox() {
		return callingCardChkbox;
	}
	public void setCallingCardChkbox(String callingCardChkbox) {
		this.callingCardChkbox = callingCardChkbox;
	}
	public String getMobIDDChkbox() {
		return mobIDDChkbox;
	}
	public void setMobIDDChkbox(String mobIDDChkbox) {
		this.mobIDDChkbox = mobIDDChkbox;
	}
	public String getFixedIDDChkbox() {
		return fixedIDDChkbox;
	}
	public void setFixedIDDChkbox(String fixedIDDChkbox) {
		this.fixedIDDChkbox = fixedIDDChkbox;
	}
	public String getEyeSrvStrikeThrough() {
		return eyeSrvStrikeThrough;
	}
	public void setEyeSrvStrikeThrough(String eyeSrvStrikeThrough) {
		this.eyeSrvStrikeThrough = eyeSrvStrikeThrough;
	}
	public String getResSrvStrikeThrough() {
		return resSrvStrikeThrough;
	}
	public void setResSrvStrikeThrough(String resSrvStrikeThrough) {
		this.resSrvStrikeThrough = resSrvStrikeThrough;
	}
	public String getToCustMrStrikeThrough() {
		return toCustMrStrikeThrough;
	}
	public void setToCustMrStrikeThrough(String toCustMrStrikeThrough) {
		this.toCustMrStrikeThrough = toCustMrStrikeThrough;
	}
	public String getToCustMsStrikeThrough() {
		return toCustMsStrikeThrough;
	}
	public void setToCustMsStrikeThrough(String toCustMsStrikeThrough) {
		this.toCustMsStrikeThrough = toCustMsStrikeThrough;
	}
	public String getToCustOthersStrikeThrough() {
		return toCustOthersStrikeThrough;
	}
	public void setToCustOthersStrikeThrough(String toCustOthersStrikeThrough) {
		this.toCustOthersStrikeThrough = toCustOthersStrikeThrough;
	}
	public String getToCustOthersName() {
		return toCustOthersName;
	}
	public void setToCustOthersName(String toCustOthersName) {
		this.toCustOthersName = toCustOthersName;
	}
	public String getToCustFamilyName() {
		return toCustFamilyName;
	}
	public void setToCustFamilyName(String toCustFamilyName) {
		this.toCustFamilyName = toCustFamilyName;
	}
	public String getToCustGivenName() {
		return toCustGivenName;
	}
	public void setToCustGivenName(String toCustGivenName) {
		this.toCustGivenName = toCustGivenName;
	}
	public String getToCustCompanyName() {
		return toCustCompanyName;
	}
	public void setToCustCompanyName(String toCustCompanyName) {
		this.toCustCompanyName = toCustCompanyName;
	}
	public String getToCustHkidStrikeThrough() {
		return toCustHkidStrikeThrough;
	}
	public void setToCustHkidStrikeThrough(String toCustHkidStrikeThrough) {
		this.toCustHkidStrikeThrough = toCustHkidStrikeThrough;
	}
	public String getToCustPassStrikeThrough() {
		return toCustPassStrikeThrough;
	}
	public void setToCustPassStrikeThrough(String toCustPassStrikeThrough) {
		this.toCustPassStrikeThrough = toCustPassStrikeThrough;
	}
	public String getToCustBrStrikeThrough() {
		return toCustBrStrikeThrough;
	}
	public void setToCustBrStrikeThrough(String toCustBrStrikeThrough) {
		this.toCustBrStrikeThrough = toCustBrStrikeThrough;
	}
	public String getToCustDocNum() {
		return toCustDocNum;
	}
	public void setToCustDocNum(String toCustDocNum) {
		this.toCustDocNum = toCustDocNum;
	}
	public String getToCustVerifiedByStaff() {
		return toCustVerifiedByStaff;
	}
	public void setToCustVerifiedByStaff(String toCustVerifiedByStaff) {
		this.toCustVerifiedByStaff = toCustVerifiedByStaff;
	}
	public String getToCustBillingAddr() {
		return toCustBillingAddr;
	}
	public void setToCustBillingAddr(String toCustBillingAddr) {
		this.toCustBillingAddr = toCustBillingAddr;
	}
	public String getToCustContactNum() {
		return toCustContactNum;
	}
	public void setToCustContactNum(String toCustContactNum) {
		this.toCustContactNum = toCustContactNum;
	}
	public String getToCustEmailAddr() {
		return toCustEmailAddr;
	}
	public void setToCustEmailAddr(String toCustEmailAddr) {
		this.toCustEmailAddr = toCustEmailAddr;
	}
	public String getToCustEmailAddr2() {
		return toCustEmailAddr2;
	}
	public void setToCustEmailAddr2(String toCustEmailAddr2) {
		this.toCustEmailAddr2 = toCustEmailAddr2;
	}
	public String getPaperBillChkbox() {
		return paperBillChkbox;
	}
	public void setPaperBillChkbox(String paperBillChkbox) {
		this.paperBillChkbox = paperBillChkbox;
	}
	public String geteBillChkbox() {
		return eBillChkbox;
	}
	public void seteBillChkbox(String eBillChkbox) {
		this.eBillChkbox = eBillChkbox;
	}
	public String getBrMrStrikeThrough() {
		return brMrStrikeThrough;
	}
	public void setBrMrStrikeThrough(String brMrStrikeThrough) {
		this.brMrStrikeThrough = brMrStrikeThrough;
	}
	public String getBrMsStrikeThrough() {
		return brMsStrikeThrough;
	}
	public void setBrMsStrikeThrough(String brMsStrikeThrough) {
		this.brMsStrikeThrough = brMsStrikeThrough;
	}
	public String getBrOthersStrikeThrough() {
		return brOthersStrikeThrough;
	}
	public void setBrOthersStrikeThrough(String brOthersStrikeThrough) {
		this.brOthersStrikeThrough = brOthersStrikeThrough;
	}
	public String getBrOthersName() {
		return brOthersName;
	}
	public void setBrOthersName(String brOthersName) {
		this.brOthersName = brOthersName;
	}
	public String getBrFamilyName() {
		return brFamilyName;
	}
	public void setBrFamilyName(String brFamilyName) {
		this.brFamilyName = brFamilyName;
	}
	public String getBrGivenName() {
		return brGivenName;
	}
	public void setBrGivenName(String brGivenName) {
		this.brGivenName = brGivenName;
	}
	public String getBrTitle() {
		return brTitle;
	}
	public void setBrTitle(String brTitle) {
		this.brTitle = brTitle;
	}
	public String getAgreeChkBox() {
		return agreeChkBox;
	}
	public void setAgreeChkBox(String agreeChkBox) {
		this.agreeChkBox = agreeChkBox;
	}
	public String getFromCustMrStrikeThrough() {
		return fromCustMrStrikeThrough;
	}
	public void setFromCustMrStrikeThrough(String fromCustMrStrikeThrough) {
		this.fromCustMrStrikeThrough = fromCustMrStrikeThrough;
	}
	public String getFromCustMsStrikeThrough() {
		return fromCustMsStrikeThrough;
	}
	public void setFromCustMsStrikeThrough(String fromCustMsStrikeThrough) {
		this.fromCustMsStrikeThrough = fromCustMsStrikeThrough;
	}
	public String getFromCustOthersStrikeThrough() {
		return fromCustOthersStrikeThrough;
	}
	public void setFromCustOthersStrikeThrough(
			String fromCustOthersStrikeThrough) {
		this.fromCustOthersStrikeThrough = fromCustOthersStrikeThrough;
	}
	public String getFromCustOthersName() {
		return fromCustOthersName;
	}
	public void setFromCustOthersName(String fromCustOthersName) {
		this.fromCustOthersName = fromCustOthersName;
	}
	public String getFromCustFamilyName() {
		return fromCustFamilyName;
	}
	public void setFromCustFamilyName(String fromCustFamilyName) {
		this.fromCustFamilyName = fromCustFamilyName;
	}
	public String getFromCustGivenName() {
		return fromCustGivenName;
	}
	public void setFromCustGivenName(String fromCustGivenName) {
		this.fromCustGivenName = fromCustGivenName;
	}
	public String getFromCustCompanyName() {
		return fromCustCompanyName;
	}
	public void setFromCustCompanyName(String fromCustCompanyName) {
		this.fromCustCompanyName = fromCustCompanyName;
	}
	public String getFromCustHkidStrikeThrough() {
		return fromCustHkidStrikeThrough;
	}
	public void setFromCustHkidStrikeThrough(String fromCustHkidStrikeThrough) {
		this.fromCustHkidStrikeThrough = fromCustHkidStrikeThrough;
	}
	public String getFromCustPassStrikeThrough() {
		return fromCustPassStrikeThrough;
	}
	public void setFromCustPassStrikeThrough(String fromCustPassStrikeThrough) {
		this.fromCustPassStrikeThrough = fromCustPassStrikeThrough;
	}
	public String getFromCustBrStrikeThrough() {
		return fromCustBrStrikeThrough;
	}
	public void setFromCustBrStrikeThrough(String fromCustBrStrikeThrough) {
		this.fromCustBrStrikeThrough = fromCustBrStrikeThrough;
	}
	public String getFromCustDocNum() {
		return fromCustDocNum;
	}
	public void setFromCustDocNum(String fromCustDocNum) {
		this.fromCustDocNum = fromCustDocNum;
	}
	public String getFromCustVerifiedByStaff() {
		return fromCustVerifiedByStaff;
	}
	public void setFromCustVerifiedByStaff(String fromCustVerifiedByStaff) {
		this.fromCustVerifiedByStaff = fromCustVerifiedByStaff;
	}
	public String getFromCustContactNum() {
		return fromCustContactNum;
	}
	public void setFromCustContactNum(String fromCustContactNum) {
		this.fromCustContactNum = fromCustContactNum;
	}
	public String getFromCustEmailAddr() {
		return fromCustEmailAddr;
	}
	public void setFromCustEmailAddr(String fromCustEmailAddr) {
		this.fromCustEmailAddr = fromCustEmailAddr;
	}
	public String getDecToCustMrStrikeThrough() {
		return decToCustMrStrikeThrough;
	}
	public void setDecToCustMrStrikeThrough(String decToCustMrStrikeThrough) {
		this.decToCustMrStrikeThrough = decToCustMrStrikeThrough;
	}
	public String getDecToCustMsStrikeThrough() {
		return decToCustMsStrikeThrough;
	}
	public void setDecToCustMsStrikeThrough(String decToCustMsStrikeThrough) {
		this.decToCustMsStrikeThrough = decToCustMsStrikeThrough;
	}
	public String getDecToCustOthersStrikeThrough() {
		return decToCustOthersStrikeThrough;
	}
	public void setDecToCustOthersStrikeThrough(
			String decToCustOthersStrikeThrough) {
		this.decToCustOthersStrikeThrough = decToCustOthersStrikeThrough;
	}
	public String getDecToCustFamilyName() {
		return decToCustFamilyName;
	}
	public void setDecToCustFamilyName(String decToCustFamilyName) {
		this.decToCustFamilyName = decToCustFamilyName;
	}
	public String getDecToCustGivenName() {
		return decToCustGivenName;
	}
	public void setDecToCustGivenName(String decToCustGivenName) {
		this.decToCustGivenName = decToCustGivenName;
	}
	public String getDecToCustRelationship() {
		return decToCustRelationship;
	}
	public void setDecToCustRelationship(String decToCustRelationship) {
		this.decToCustRelationship = decToCustRelationship;
	}
	public String getDecCustMrStrikeThrough() {
		return decCustMrStrikeThrough;
	}
	public void setDecCustMrStrikeThrough(String decCustMrStrikeThrough) {
		this.decCustMrStrikeThrough = decCustMrStrikeThrough;
	}
	public String getDecCustMsStrikeThrough() {
		return decCustMsStrikeThrough;
	}
	public void setDecCustMsStrikeThrough(String decCustMsStrikeThrough) {
		this.decCustMsStrikeThrough = decCustMsStrikeThrough;
	}
	public String getDecCustOthersStrikeThrough() {
		return decCustOthersStrikeThrough;
	}
	public void setDecCustOthersStrikeThrough(String decCustOthersStrikeThrough) {
		this.decCustOthersStrikeThrough = decCustOthersStrikeThrough;
	}
	public String getDecCustFamilyName() {
		return decCustFamilyName;
	}
	public void setDecCustFamilyName(String decCustFamilyName) {
		this.decCustFamilyName = decCustFamilyName;
	}
	public String getDecCustGivenName() {
		return decCustGivenName;
	}
	public void setDecCustGivenName(String decCustGivenName) {
		this.decCustGivenName = decCustGivenName;
	}
	public String getToCustMobileNum() {
		return toCustMobileNum;
	}
	public void setToCustMobileNum(String toCustMobileNum) {
		this.toCustMobileNum = toCustMobileNum;
	}
	public String getSrvChkBox() {
		return srvChkBox;
	}
	public void setSrvChkBox(String srvChkBox) {
		this.srvChkBox = srvChkBox;
	}
	public String getTheClubHktChkBox() {
		return theClubHktChkBox;
	}
	public void setTheClubHktChkBox(String theClubHktChkBox) {
		this.theClubHktChkBox = theClubHktChkBox;
	}
	public byte[] getToCustSignature() {
		return toCustSignature;
	}
	public void setToCustSignature(byte[] toCustSignature) {
		this.toCustSignature = toCustSignature;
	}
	public String getToSignDate() {
		return toSignDate;
	}
	public void setToSignDate(String toSignDate) {
		this.toSignDate = toSignDate;
	}
	public byte[] getFromCustSignature() {
		return fromCustSignature;
	}
	public void setFromCustSignature(byte[] fromCustSignature) {
		this.fromCustSignature = fromCustSignature;
	}
	public String getFromSignDate() {
		return fromSignDate;
	}
	public void setFromSignDate(String fromSignDate) {
		this.fromSignDate = fromSignDate;
	}
	public byte[] getDecToCustSignature() {
		return decToCustSignature;
	}
	public void setDecToCustSignature(byte[] decToCustSignature) {
		this.decToCustSignature = decToCustSignature;
	}
	public String getDecToSignDate() {
		return decToSignDate;
	}
	public void setDecToSignDate(String decToSignDate) {
		this.decToSignDate = decToSignDate;
	}
	public String getSvcChkBox() {
		return svcChkBox;
	}
	public void setSvcChkBox(String svcChkBox) {
		this.svcChkBox = svcChkBox;
	}
	public String getSalesmanCode() {
		return salesmanCode;
	}
	public void setSalesmanCode(String salesmanCode) {
		this.salesmanCode = salesmanCode;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getSalesStaffName() {
		return salesStaffName;
	}
	public void setSalesStaffName(String salesStaffName) {
		this.salesStaffName = salesStaffName;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getStaffContactDetails() {
		return staffContactDetails;
	}
	public void setStaffContactDetails(String staffContactDetails) {
		this.staffContactDetails = staffContactDetails;
	}
	public String getNewSrvChkBox() {
		return newSrvChkBox;
	}
	public void setNewSrvChkBox(String newSrvChkBox) {
		this.newSrvChkBox = newSrvChkBox;
	}
	public String getAfRefNo() {
		return afRefNo;
	}
	public void setAfRefNo(String afRefNo) {
		this.afRefNo = afRefNo;
	}
	public boolean isPrintTermsCondition() {
		return printTermsCondition;
	}
	public void setPrintTermsCondition(boolean printTermsCondition) {
		this.printTermsCondition = printTermsCondition;
	}
	
	
	
	
	
	

}
