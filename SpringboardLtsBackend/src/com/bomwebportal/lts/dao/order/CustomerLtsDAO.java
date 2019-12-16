package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;

public class CustomerLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -8755932537886422996L;
	
	private String orderId; // BOMWEB_CUSTOMER.ORDER_ID
	private String custNo; // BOMWEB_CUSTOMER.CUST_NO
	private String mobCustNo; // BOMWEB_CUSTOMER.MOB_CUST_NO
	private String firstName; // BOMWEB_CUSTOMER.FIRST_NAME
	private String lastName; // BOMWEB_CUSTOMER.LAST_NAME
	private String idDocType; // BOMWEB_CUSTOMER.ID_DOC_TYPE
	private String idDocNum; // BOMWEB_CUSTOMER.ID_DOC_NUM
	private OraDate dob = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_CUSTOMER.DOB
	private String title; // BOMWEB_CUSTOMER.TITLE
	private String companyName; // BOMWEB_CUSTOMER.COMPANY_NAME
	private String indType; // BOMWEB_CUSTOMER.IND_TYPE
	private String indSubType; // BOMWEB_CUSTOMER.IND_SUB_TYPE
	private String nationality; // BOMWEB_CUSTOMER.NATIONALITY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_CUSTOMER.CREATE_DATE
	private String addrProofInd; // BOMWEB_CUSTOMER.ADDR_PROOF_IND
	private String lob; // BOMWEB_CUSTOMER.LOB
	private String serviceNum; // BOMWEB_CUSTOMER.SERVICE_NUM
	private String langWritten; // BOMWEB_CUSTOMER.LANG_WRITTEN
	private String langSpoken; // BOMWEB_CUSTOMER.LANG_SPOKEN
	private String idVerifiedInd; // BOMWEB_CUSTOMER.ID_VERIFIED_IND
	private String blacklistInd; // BOMWEB_CUSTOMER.BLACKLIST_IND
	private String csPortalInd; // BOMWEB_CUSTOMER.CS_PORTAL_IND
	private String csPortalLogin; // BOMWEB_CUSTOMER.CS_PORTAL_LOGIN
	private String csPortalMobile; // BOMWEB_CUSTOMER.CS_PORTAL_MOBILE
	private String theClubLogin; // BOMWEB_CUSTOMER.THE_CLUB_LOGIN
	private String theClubMobile; // BOMWEB_CUSTOMER.THE_CLUB_MOBILE
	private String theClubInd; // BOMWEB_CUSTOMER.THE_CLUB_IND
	private String mismatchInd; // BOMWEB_CUSTOMER.MISMATCH_IND
	private String hktOptOut; //BOMWEB_CUSTOMER.HKT_OPT_OUT
	private String clubOptOut; //BOMWEB_CUSTOMER.CLUB_OPT_OUT
	private String clubOptRea; //BOMWEB_CUSTOMER.CLUB_OPT_REA
	private String clubOptRmk; //BOMWEB_CUSTOMER.CLUB_OPT_RMK
	
	public CustomerLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "custNo"};
	}
	
	public String getTableName() {
		return "BOMWEB_CUSTOMER";
	}
	
	public String getAddrProofInd() {
		return addrProofInd;
	}

	public void setAddrProofInd(String addrProofInd) {
		this.addrProofInd = addrProofInd;
	}

	public void setDob(OraDate dob) {
		this.dob = dob;
	}

	public void setCreateDate(OraDate createDate) {
		this.createDate = createDate;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public String getMobCustNo() {
		return this.mobCustNo;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getIdDocType() {
		return this.idDocType;
	}

	public String getIdDocNum() {
		return this.idDocNum;
	}

	public String getTitle() {
		return this.title;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public String getIndType() {
		return this.indType;
	}

	public String getIndSubType() {
		return this.indSubType;
	}

	public String getNationality() {
		return this.nationality;
	}

	public String getLob() {
		return this.lob;
	}

	public String getServiceNum() {
		return this.serviceNum;
	}

	public String getLangWritten() {
		return this.langWritten;
	}

	public String getLangSpoken() {
		return this.langSpoken;
	}

	public String getIdVerifiedInd() {
		return this.idVerifiedInd;
	}

	public String getBlacklistInd() {
		return this.blacklistInd;
	}

	public String getDob() {
		return this.dob != null ? this.dob.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public OraDate getDobORACLE() {
		return this.dob;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setCustNo(String pCustNo) {
		this.custNo = pCustNo;
	}

	public void setMobCustNo(String pMobCustNo) {
		this.mobCustNo = pMobCustNo;
	}

	public void setFirstName(String pFirstName) {
		this.firstName = pFirstName;
	}

	public void setLastName(String pLastName) {
		this.lastName = pLastName;
	}

	public void setIdDocType(String pIdDocType) {
		this.idDocType = pIdDocType;
	}

	public void setIdDocNum(String pIdDocNum) {
		this.idDocNum = pIdDocNum;
	}

	public void setTitle(String pTitle) {
		this.title = pTitle;
	}

	public void setCompanyName(String pCompanyName) {
		this.companyName = pCompanyName;
	}

	public void setIndType(String pIndType) {
		this.indType = pIndType;
	}

	public void setIndSubType(String pIndSubType) {
		this.indSubType = pIndSubType;
	}

	public void setNationality(String pNationality) {
		this.nationality = pNationality;
	}

	public void setLob(String pLob) {
		this.lob = pLob;
	}

	public void setServiceNum(String pServiceNum) {
		this.serviceNum = pServiceNum;
	}

	public void setLangWritten(String pLangWritten) {
		this.langWritten = pLangWritten;
	}

	public void setLangSpoken(String pLangSpoken) {
		this.langSpoken = pLangSpoken;
	}

	public void setIdVerifiedInd(String pIdVerifiedInd) {
		this.idVerifiedInd = pIdVerifiedInd;
	}

	public void setBlacklistInd(String pBlacklistInd) {
		this.blacklistInd = pBlacklistInd;
	}

	public void setDob(String pDob) {
		this.dob = new OraDateYYYYMMDDHH24MISS(pDob);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public String getCsPortalInd() {
		return csPortalInd;
	}

	public void setCsPortalInd(String csPortalInd) {
		this.csPortalInd = csPortalInd;
	}

	public String getCsPortalLogin() {
		return csPortalLogin;
	}

	public void setCsPortalLogin(String csPortalLogin) {
		this.csPortalLogin = csPortalLogin;
	}

	public String getCsPortalMobile() {
		return csPortalMobile;
	}

	public void setCsPortalMobile(String csPortalMobile) {
		this.csPortalMobile = csPortalMobile;
	}
	
	public String getTheClubLogin() {
		return theClubLogin;
	}

	public void setTheClubLogin(String theClubLogin) {
		this.theClubLogin = theClubLogin;
	}

	public String getTheClubMobile() {
		return theClubMobile;
	}

	public void setTheClubMobile(String theClubMobile) {
		this.theClubMobile = theClubMobile;
	}

	public String getTheClubInd() {
		return theClubInd;
	}

	public void setTheClubInd(String theClubInd) {
		this.theClubInd = theClubInd;
	}

	public String getMismatchInd() {
		return mismatchInd;
	}

	public void setMismatchInd(String mismatchInd) {
		this.mismatchInd = mismatchInd;
	}
	
	public String getHktOptOut() {
		return this.hktOptOut; 
	}
	
	public String getClubOptOut() { 
		return this.clubOptOut; 
	}
	
	public String getClubOptRea() { 
		return this.clubOptRea; 
	}
	
	public String getClubOptRmk() { 
		return this.clubOptRmk; 
	}
	
	public void setHktOptOut(String pHktOptOut) {
		this.hktOptOut = pHktOptOut; 
	}
	
	public void setClubOptOut(String pClubOptOut) { 
		this.clubOptOut = pClubOptOut; 
	}
	
	public void setClubOptRea(String pClubOptRea) { 
		this.clubOptRea = pClubOptRea; 
	}
	
	public void setClubOptRmk(String pClubOptRmk) {
		this.clubOptRmk = pClubOptRmk; 
	}

	
}
