package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;

public class LtsAcqBillMediaBillLangFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3119580413240894793L;
	

	private List<BillMediaDtl> billMediaDtlList;
	private boolean redemMediaRequired;
	private String dummyEmailMobInd;
	private String carecashExistOpt;
	private boolean showIguard;
	private String oIdStatus;
	private boolean allowRedemPaper;
	
	public class BillMediaDtl implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8824129075168911572L;
		private String custNum;
		private String acctNum;
		private boolean primaryAcct;
		private String billLang;
		private String redemMedia;
		private String redemMediaEmail;
		private String redemSms;
		private String selectedBillItem;
		private ItemDetailDTO csPortalItem;
		private boolean csPortalExist;
		private String cspEmail;		
		private String cspMobile;
		private String clubEmail;		
		private String clubMobile;		
		private String contactEmail;
		private String ContactMobileNo;
	    private ItemDetailDTO emailBillItem;
	    private String billMediaEmail;
		private ItemDetailDTO paperBillItem;
		private String csAcctInd;
		private boolean isDocValid;
		private String optInOutInd;
		private String optOutReason;
		private String optOutRemarks;
		private String sysGenEmail;
		private String dummyEmailInd;
		private String dummyMobileInd;
		private String existingCspEmail;

		private String profileBillMedia;
		private String custExistingPaperBillInd;
		private String selectedBillCharging;
		private String selectedWaiveReason;
		private String paperBillWaiveOtherStaffId;
		
		private String ContactFixedLineNo;
		
		private String carecashRegisterOption;
		private String carecashEmail;
		private String carecashContactNo;
		private String carecashOptInOutInd;
		
		/**
		 * @return the custNum
		 */
		public String getCustNum() {
			return custNum;
		}
		/**
		 * @param custNum the custNum to set
		 */
		public void setCustNum(String custNum) {
			this.custNum = custNum;
		}
		public String getAcctNum() {
			return acctNum;
		}
		public void setAcctNum(String acctNum) {
			this.acctNum = acctNum;
		}
		public String getBillLang() {
			return billLang;
		}
		public void setBillLang(String billLang) {
			this.billLang = billLang;
		}
		public String getRedemMedia() {
			return redemMedia;
		}
		public void setRedemMedia(String redemMedia) {
			this.redemMedia = redemMedia;
		}
		public String getRedemMediaEmail() {
			return redemMediaEmail;
		}
		public void setRedemMediaEmail(String redemMediaEmail) {
			this.redemMediaEmail = redemMediaEmail;
		}
		public String getRedemSms() {
			return redemSms;
		}
		public void setRedemSms(String redemSms) {
			this.redemSms = redemSms;
		}
		public String getSelectedBillItem() {
			return selectedBillItem;
		}
		public void setSelectedBillItem(String selectedBillItem) {
			this.selectedBillItem = selectedBillItem;
		}
		public ItemDetailDTO getCsPortalItem() {
			return csPortalItem;
		}
		public void setCsPortalItem(ItemDetailDTO csPortalItem) {
			this.csPortalItem = csPortalItem;
		}
		public boolean isCsPortalExist() {
			return csPortalExist;
		}
		public void setCsPortalExist(boolean csPortalExist) {
			this.csPortalExist = csPortalExist;
		}
		public String getCspEmail() {
			return cspEmail;
		}
		public void setCspEmail(String cspEmail) {
			this.cspEmail = cspEmail;
		}
		public String getCspMobile() {
			return cspMobile;
		}
		public void setCspMobile(String cspMobile) {
			this.cspMobile = cspMobile;
		}		
		public ItemDetailDTO getEmailBillItem() {
			return emailBillItem;
		}
		public void setEmailBillItem(ItemDetailDTO emailBillItem) {
			this.emailBillItem = emailBillItem;
		}
		public String getBillMediaEmail() {
			return billMediaEmail;
		}
		public void setBillMediaEmail(String billMediaEmail) {
			this.billMediaEmail = billMediaEmail;
		}
		public ItemDetailDTO getPaperBillItem() {
			return paperBillItem;
		}
		public void setPaperBillItem(ItemDetailDTO paperBillItem) {
			this.paperBillItem = paperBillItem;
		}
		public boolean isPrimaryAcct() {
			return primaryAcct;
		}
		public void setPrimaryAcct(boolean primaryAcct) {
			this.primaryAcct = primaryAcct;
		}
		public String getContactEmail() {
			return contactEmail;
		}
		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}
		public String getContactMobileNo() {
			return ContactMobileNo;
		}
		public void setContactMobileNo(String contactMobileNo) {
			ContactMobileNo = contactMobileNo;
		}
		/**
		 * @return the clubEmail
		 */
		public String getClubEmail() {
			return clubEmail;
		}
		/**
		 * @param clubEmail the clubEmail to set
		 */
		public void setClubEmail(String clubEmail) {
			this.clubEmail = clubEmail;
		}
		/**
		 * @return the clubMobile
		 */
		public String getClubMobile() {
			return clubMobile;
		}
		/**
		 * @param clubMobile the clubMobile to set
		 */
		public void setClubMobile(String clubMobile) {
			this.clubMobile = clubMobile;
		}
		/**
		 * @return the csAcctInd
		 */
		public String getCsAcctInd() {
			return csAcctInd;
		}
		/**
		 * @param csAcctInd the csAcctInd to set
		 */
		public void setCsAcctInd(String csAcctInd) {
			this.csAcctInd = csAcctInd;
		}
		/**
		 * @return the isDocValid
		 */
		public boolean isDocValid() {
			return isDocValid;
		}
		/**
		 * @param isDocValid the isDocValid to set
		 */
		public void setDocValid(boolean isDocValid) {
			this.isDocValid = isDocValid;
		}
		public String getOptInOutInd() {
			return optInOutInd;
		}
		public void setOptInOutInd(String optInOutInd) {
			this.optInOutInd = optInOutInd;
		}
		public String getOptOutReason() {
			return optOutReason;
		}
		public void setOptOutReason(String optOutReason) {
			this.optOutReason = optOutReason;
		}
		public String getOptOutRemarks() {
			return optOutRemarks;
		}
		public void setOptOutRemarks(String optOutRemarks) {
			this.optOutRemarks = optOutRemarks;
		}
		public String getSysGenEmail() {
			return sysGenEmail;
		}
		public void setSysGenEmail(String sysGenEmail) {
			this.sysGenEmail = sysGenEmail;
		}
		public String getDummyEmailInd() {
			return dummyEmailInd;
		}
		public void setDummyEmailInd(String dummyEmailInd) {
			this.dummyEmailInd = dummyEmailInd;
		}
		public String getDummyMobileInd() {
			return dummyMobileInd;
		}
		public void setDummyMobileInd(String dummyMobileInd) {
			this.dummyMobileInd = dummyMobileInd;
		}
		public String getExistingCspEmail() {
			return existingCspEmail;
		}
		public void setExistingCspEmail(String existingCspEmail) {
			this.existingCspEmail = existingCspEmail;
		}
		public String getSelectedBillCharging() {
			return selectedBillCharging;
		}
		public void setSelectedBillCharging(String selectedBillCharging) {
			this.selectedBillCharging = selectedBillCharging;
		}
		public String getSelectedWaiveReason() {
			return selectedWaiveReason;
		}
		public void setSelectedWaiveReason(String selectedWaiveReason) {
			this.selectedWaiveReason = selectedWaiveReason;
		}
		public String getCustExistingPaperBillInd() {
			return custExistingPaperBillInd;
		}
		public void setCustExistingPaperBillInd(String custExistingPaperBillInd) {
			this.custExistingPaperBillInd = custExistingPaperBillInd;
		}
		public String getProfileBillMedia() {
			return profileBillMedia;
		}
		public void setProfileBillMedia(String profileBillMedia) {
			this.profileBillMedia = profileBillMedia;
		}
		public String getPaperBillWaiveOtherStaffId() {
			return paperBillWaiveOtherStaffId;
		}
		public void setPaperBillWaiveOtherStaffId(
				String paperBillWaiveOtherStaffId) {
			this.paperBillWaiveOtherStaffId = paperBillWaiveOtherStaffId;
		}
				
		public String getContactFixedLineNo() {
			return ContactFixedLineNo;
		}
		public void setContactFixedLineNo(String contactFixedLineNo) {
			ContactFixedLineNo = contactFixedLineNo;
		}
		public String getCarecashRegisterOption() {
			return carecashRegisterOption;
		}
		public void setCarecashRegisterOption(String carecashRegisterOption) {
			this.carecashRegisterOption = carecashRegisterOption;
		}
		public String getCarecashEmail() {
			return carecashEmail;
		}
		public void setCarecashEmail(String carecashEmail) {
			this.carecashEmail = carecashEmail;
		}
		public String getCarecashContactNo() {
			return carecashContactNo;
		}
		public void setCarecashContactNo(String carecashContactNo) {
			this.carecashContactNo = carecashContactNo;
		}
		public String getCarecashOptInOutInd() {
			return carecashOptInOutInd;
		}
		public void setCarecashOptInOutInd(String carecashOptInOutInd) {
			this.carecashOptInOutInd = carecashOptInOutInd;
		}
		
	}

	public List<BillMediaDtl> getBillMediaDtlList() {
		return billMediaDtlList;
	}

	public void setBillMediaDtlList(List<BillMediaDtl> billMediaDtlList) {
		this.billMediaDtlList = billMediaDtlList;
	}
	
	public BillMediaDtl getPrimaryBillMediaDtl(){
		if(billMediaDtlList.size() == 1){
			return billMediaDtlList.get(0);
		}
		
		for(BillMediaDtl dtl: this.billMediaDtlList){
			if (dtl.isPrimaryAcct()) return dtl; 
		}
		return null;
	}

	public boolean isRedemMediaRequired() {
		return redemMediaRequired;
	}

	public void setRedemMediaRequired(boolean redemMediaRequired) {
		this.redemMediaRequired = redemMediaRequired;
	}

	public String getDummyEmailMobInd() {
		return dummyEmailMobInd;
	}

	public void setDummyEmailMobInd(String dummyEmailMobInd) {
		this.dummyEmailMobInd = dummyEmailMobInd;
	}

	public String getCarecashExistOpt() {
		return carecashExistOpt;
	}

	public void setCarecashExistOpt(String carecashExistOpt) {
		this.carecashExistOpt = carecashExistOpt;
	}

	public boolean isShowIguard() {
		return showIguard;
	}

	public void setShowIguard(boolean showIguard) {
		this.showIguard = showIguard;
	}

	public String getoIdStatus() {
		return oIdStatus;
	}

	public void setoIdStatus(String oIdStatus) {
		this.oIdStatus = oIdStatus;
	}

	public boolean isAllowRedemPaper() {
		return allowRedemPaper;
	}

	public void setAllowRedemPaper(boolean allowRedemPaper) {
		this.allowRedemPaper = allowRedemPaper;
	}

	
	
}
