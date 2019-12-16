package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bomwebportal.ims.dto.ui.SubscribedItemUI;

public class CustomerDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3101488332783993790L;
	
	
	private String OrderId;
	private String CustNo;
	private String MobCustNo;
	private String FirstName;
	private String LastName;
	private String IdDocType;
	private String IdDocNum;
	private Date Dob;
	private String Title;
	private String CompanyName;
	private String IndType;
	private String IndSubType;
	private String Nationality;	
	private String AddrProofInd;
	private String Lob;
	private String ServiceNum;
	private String IdVerified;
	private String BlacklistInd;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;
	
	//Gary 
	private String CsPortalInd;
	private String csPortalLogin;
	private String csPortalMobile;
	//Tony
	private String TheClubInd;
	private String theClubLogin;
	private String theClubMoblie;
	private String CsPortalTheClubInd;
	private String csPortalTheClubLogin;

	private String csPortalOptOutInd;
	private String theClubOptOutInd;
	private String csPortalTheClubOptOutInd;

	private String noEmailInd;
	private String noMobileInd;
	
	
	private String optoutTheClubReason;
	private String optoutTheClubRmk;

	private String ExistingCustomerConflictWithName;//Tony added
	
	private String nowId;
	private String isRegNowId;
	
	//NOWTVSALES
	private List<GetCOrderDTO> cOrder;
	private String cOrderReason;
	private String cOrderLineType;
	private List<SubscribedItemUI> removedItems;
	
	private String careCashInd;
	private String careCashOptOutInd;
	private String careCashEmail;
	private String careCashMobile;
	private String careCashRegStatus;
	private String careCashRtnMsg;

	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getCustNo() {
		return CustNo;
	}
	public void setCustNo(String custNo) {
		CustNo = custNo;
	}
	public String getMobCustNo() {
		return MobCustNo;
	}
	public void setMobCustNo(String mobCustNo) {
		MobCustNo = mobCustNo;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getIdDocType() {
		return IdDocType;
	}
	public void setIdDocType(String idDocType) {
		IdDocType = idDocType;
	}
	public String getIdDocNum() {
		return IdDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		IdDocNum = idDocNum;
	}
	public Date getDob() {
		return Dob;
	}
	public void setDob(Date dob) {
		Dob = dob;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getIndType() {
		return IndType;
	}
	public void setIndType(String indType) {
		IndType = indType;
	}
	public String getIndSubType() {
		return IndSubType;
	}
	public void setIndSubType(String indSubType) {
		IndSubType = indSubType;
	}
	public String getNationality() {
		return Nationality;
	}
	public void setNationality(String nationality) {
		Nationality = nationality;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getAddrProofInd() {
		return AddrProofInd;
	}
	public void setAddrProofInd(String addrProofInd) {
		AddrProofInd = addrProofInd;
	}
	public String getLob() {
		return Lob;
	}
	public void setLob(String lob) {
		Lob = lob;
	}
	public String getServiceNum() {
		return ServiceNum;
	}
	public void setServiceNum(String serviceNum) {
		ServiceNum = serviceNum;
	}
	public String getIdVerified() {
		return IdVerified;
	}
	public void setIdVerified(String idVerified) {
		IdVerified = idVerified;
	}
	public String getBlacklistInd() {
		return BlacklistInd;
	}
	public void setBlacklistInd(String blacklistInd) {
		BlacklistInd = blacklistInd;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public void setCsPortalInd(String csPortalInd) {
		CsPortalInd = csPortalInd;
	}
	public String getCsPortalInd() {
		return CsPortalInd;
	}
	public void setCsPortalLogin(String cs_PortalLogin) {
		csPortalLogin = cs_PortalLogin;
	}
	public String getCsPortalLogin() {
		return csPortalLogin;
	}
	public void setCsPortalMobile(String cs_PortalMobile) {
		csPortalMobile = cs_PortalMobile;
	}
	public String getCsPortalMobile() {
		return csPortalMobile;
	}
	public void setExistingCustomerConflictWithName(
			String existingCustomerConflictWithName) {
		ExistingCustomerConflictWithName = existingCustomerConflictWithName;
	}
	public String getExistingCustomerConflictWithName() {
		return ExistingCustomerConflictWithName;
	}
	public void setTheClubInd(String theClubInd) {
		TheClubInd = theClubInd;
	}
	public String getTheClubInd() {
		return TheClubInd;
	}
	public void setTheClubLogin(String the_ClubLogin) {
		theClubLogin = the_ClubLogin;
	}
	public String getTheClubLogin() {
		return theClubLogin;
	}
	public void setCsPortalTheClubInd(String csPortalTheClubInd) {
		CsPortalTheClubInd = csPortalTheClubInd;
	}
	public String getCsPortalTheClubInd() {
		return CsPortalTheClubInd;
	}
	public void setCsPortalTheClubLogin(String cs_PortalTheClubLogin) {
		csPortalTheClubLogin = cs_PortalTheClubLogin;
	}
	public String getCsPortalTheClubLogin() {
		return csPortalTheClubLogin;
	}
	public void setTheClubMoblie(String the_ClubMoblie) {
		theClubMoblie = the_ClubMoblie;
	}
	public String getTheClubMoblie() {
		return theClubMoblie;
	}
	public void setNowId(String nowId) {
		this.nowId = nowId;
	}
	public String getNowId() {
		return nowId;
	}
	public void setIsRegNowId(String isRegNowId) {
		this.isRegNowId = isRegNowId;
	}
	public String getIsRegNowId() {
		return isRegNowId;
	}
	public List<GetCOrderDTO> getcOrder() {
		return cOrder;
	}
	public void setcOrder(List<GetCOrderDTO> cOrder) {
		this.cOrder = cOrder;
	}
	public String getcOrderReason() {
		return cOrderReason;
	}
	public void setcOrderReason(String cOrderReason) {
		this.cOrderReason = cOrderReason;
	}
	public String getcOrderLineType() {
		return cOrderLineType;
	}
	public void setcOrderLineType(String cOrderLineType) {
		this.cOrderLineType = cOrderLineType;
	}
	public List<SubscribedItemUI> getRemovedItems() {
		return removedItems;
	}
	public void setRemovedItems(List<SubscribedItemUI> removedItems) {
		this.removedItems = removedItems;
	}
	public void setOptoutTheClubReason(String optoutTheClubReason) {
		this.optoutTheClubReason = optoutTheClubReason;
	}
	public String getOptoutTheClubReason() {
		return optoutTheClubReason;
	}
	public void setOptoutTheClubRmk(String optoutTheClubRmk) {
		this.optoutTheClubRmk = optoutTheClubRmk;
	}
	public String getOptoutTheClubRmk() {
		return optoutTheClubRmk;
	}
	public void setCsPortalOptOutInd(String cs_PortalOptOutInd) {
		csPortalOptOutInd = cs_PortalOptOutInd;
	}
	public String getCsPortalOptOutInd() {
		return csPortalOptOutInd;
	}
	public void setTheClubOptOutInd(String ClubOptOutInd) {
		theClubOptOutInd = ClubOptOutInd;
	}
	public String getTheClubOptOutInd() {
		return theClubOptOutInd;
	}
	public void setCsPortalTheClubOptOutInd(String portalTheClubOptOutInd) {
		csPortalTheClubOptOutInd = portalTheClubOptOutInd;
	}
	public String getCsPortalTheClubOptOutInd() {
		return csPortalTheClubOptOutInd;
	}
	public void setNoEmailInd(String noEmailInd) {
		this.noEmailInd = noEmailInd;
	}
	public String getNoEmailInd() {
		return noEmailInd;
	}
	public void setNoMobileInd(String noMobileInd) {
		this.noMobileInd = noMobileInd;
	}
	public String getNoMobileInd() {
		return noMobileInd;
	}
	public void setCareCashInd(String careCashInd) {
		this.careCashInd = careCashInd;
	}
	public String getCareCashInd() {
		return careCashInd;
	}
	public void setCareCashOptOutInd(String careCashOptOutInd) {
		this.careCashOptOutInd = careCashOptOutInd;
	}
	public String getCareCashOptOutInd() {
		return careCashOptOutInd;
	}

	public void setCareCashEmail(String careCashEmail) {
		this.careCashEmail = careCashEmail;
	}

	public String getCareCashEmail() {
		return careCashEmail;
	}

	public void setCareCashMobile(String careCashMobile) {
		this.careCashMobile = careCashMobile;
	}

	public String getCareCashMobile() {
		return careCashMobile;
	}
	public void setCareCashRegStatus(String careCashRegStatus) {
		this.careCashRegStatus = careCashRegStatus;
	}
	public String getCareCashRegStatus() {
		return careCashRegStatus;
	}
	public void setCareCashRtnMsg(String careCashRtnMsg) {
		this.careCashRtnMsg = careCashRtnMsg;
	}
	public String getCareCashRtnMsg() {
		return careCashRtnMsg;
	}
	
	
}
