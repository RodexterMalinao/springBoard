package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.CsPortalTxnDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsPaymentFormDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7600088943652802371L;

	private String selectButton; //selected one
	private String newPayMethodType;
	private String existingPayMethodType;
	private String existingPayment;
	private String existingCreditCardNum;
	
	private String existBillAccNum;
	private String changeReason;

	private String custName;
	private String custDocType;
	private String custDocNum;
	
	//bank auto pay
	private Boolean thirdPartyBankAccount;
	private String bankAccHolderName;
	private String bankAccHolderDocType;
	private String bankAccHolderDocNum;
	private String bankCode;
	private String branchCode;
	private String bankAccNum;
	private String autoPayUpperLimit;
	private String applicationDate;
	private String branchCodeHidden;


	//credit card
	private Boolean thirdPartyCard;
	private String cardHolderName;
	private String cardHolderDocType;
	private String cardHolderDocNum;
	private String cardNum;
	private String cardType;
	private int expireMonth;
	private int expireYear;
	private boolean cardVerifyRequired;
	private boolean cardVerified;
	
	//payment
	private boolean salesMemoNumRequired;
	private String salesMemoNum;
	private ItemDetailDTO prePayItem;
	private ItemDetailDTO prePayItemE;
	private ItemDetailDTO prePayItemA;
	private ItemDetailDTO prePayItemC;
	private ItemDetailDTO prePayItemM;
	private List<String[]> prepayItemIdList;
	
	private boolean prepayItemSelectedE = false;
	private boolean prepayItemSelectedA = false;
	private boolean prepayItemSelectedC = false;
	
	//billing
	private boolean cspExclude;
	private boolean csPortalExist;
	private boolean clubMembExist;
	private String optOut;
	private String optOutReason;
	private String optOutRemark;
	private boolean enableCsp;
	private boolean phantomAcct;
	private String cspReplyCd;
 	private List<ItemDetailDTO> billItemList;
	private ItemDetailDTO paperBillItem;
	private ItemDetailDTO eBillItem;
	private ItemDetailDTO noBillItem;
	private ItemDetailDTO csPortalItem;
	private ItemDetailDTO viewBillItem;
	private String selectedBillItemId;
	private boolean cspNewReg;
	private String cspEmail;
	private String cspMobile;
	private String clubEmail;
	private String clubMobile;
	private boolean csldFailInd;
	private String dummyClubEmail;
	private boolean isDummy;
	private String clubRes;
	private String myHktRes;
	private CsPortalTxnDTO cspVaildateRsp;
	private String paperBillCharge;
	private String paperWaiveReason;
	private String paperBillWaiveOtherStaffId;
	private boolean allowModifyInd;
	private boolean recontractCase;
	private boolean docTypeBR;

	private String emailBillAddress;
	private ItemDetailDTO emailBillItem;
	
	//recontract
	private String recontractAccountNo;
	
	//billing address
	private boolean allowChangeBa;
	private boolean changeBa;
	private boolean instantUpdateBa;
	private String baQuickSearch;
	private String billingAddress;
	
	private String suspendReason;
	private String submitInd;
	
	private List<ItemDetailDTO> erChargeItemList;
	
	
	// redemption media
	private boolean requireRedemPremium;
	private String redemptionMedia;
	private String redemMediaSmsNum;
	private String redemMediaEmailAddr;
	private boolean allowRedemPaper;
	
	private PrepaymentLkupCriteriaDTO prepaymentLkupCriteria;
	
	
	
	public String getNewPayMethodType() {
		return newPayMethodType;
	}
	public void setNewPayMethodType(String newPayMethodType) {
		this.newPayMethodType = newPayMethodType;
	}
	public String getExistingPayment() {
		return existingPayment;
	}
	public void setExistingPayment(String existingPayment) {
		this.existingPayment = existingPayment;
	}
	public String getBankAccHolderName() {
		return bankAccHolderName;
	}
	public void setBankAccHolderName(String bankAccHolderName) {
		this.bankAccHolderName = bankAccHolderName;
	}
	public String getBankAccHolderDocType() {
		return bankAccHolderDocType;
	}
	public void setBankAccHolderDocType(String bankAccHolderDocType) {
		this.bankAccHolderDocType = bankAccHolderDocType;
	}
	public String getBankAccHolderDocNum() {
		return bankAccHolderDocNum;
	}
	public void setBankAccHolderDocNum(String bankAccHolderDocNum) {
		this.bankAccHolderDocNum = bankAccHolderDocNum;
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
	public String getBankAccNum() {
		return bankAccNum;
	}
	public void setBankAccNum(String bankAccNum) {
		this.bankAccNum = bankAccNum;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCardHolderDocType() {
		return cardHolderDocType;
	}
	public void setCardHolderDocType(String cardHolderDocType) {
		this.cardHolderDocType = cardHolderDocType;
	}
	public String getCardHolderDocNum() {
		return cardHolderDocNum;
	}
	public void setCardHolderDocNum(String cardHolderDocNum) {
		this.cardHolderDocNum = cardHolderDocNum;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public int getExpireMonth() {
		return expireMonth;
	}
	public void setExpireMonth(int expireMonth) {
		this.expireMonth = expireMonth;
	}
	public int getExpireYear() {
		return expireYear;
	}
	public void setExpireYear(int expireYear) {
		this.expireYear = expireYear;
	}
	public boolean isCardVerified() {
		return cardVerified;
	}
	public void setCardVerified(boolean cardVerified) {
		this.cardVerified = cardVerified;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getExistBillAccNum() {
		return existBillAccNum;
	}
	public void setExistBillAccNum(String existBillAccNum) {
		this.existBillAccNum = existBillAccNum;
	}
	public ItemDetailDTO getPrePayItem() {
		return prePayItem;
	}
	public void setPrePayItem(ItemDetailDTO prePayItem) {
		this.prePayItem = prePayItem;
	}
	public String getAutoPayUpperLimit() {
		return autoPayUpperLimit;
	}
	public void setAutoPayUpperLimit(String autoPayUpperLimit) {
		this.autoPayUpperLimit = autoPayUpperLimit;
	}
	public boolean isPrepayItemSelectedE() {
		return prepayItemSelectedE;
	}
	public void setPrepayItemSelectedE(boolean prepayItemSelectedE) {
		this.prepayItemSelectedE = prepayItemSelectedE;
	}
	public boolean isPrepayItemSelectedA() {
		return prepayItemSelectedA;
	}
	public void setPrepayItemSelectedA(boolean prepayItemSelectedA) {
		this.prepayItemSelectedA = prepayItemSelectedA;
	}
	public List<ItemDetailDTO> getBillItemList() {
		List<ItemDetailDTO> billItemList = new ArrayList<ItemDetailDTO>();
		if(this.paperBillItem != null){
			billItemList.add(this.paperBillItem);
		}
		if(this.noBillItem != null){
			billItemList.add(this.noBillItem);
		}
		if(this.eBillItem != null){
			billItemList.add(this.eBillItem);
		}
		if (this.emailBillItem != null) {
			billItemList.add(this.emailBillItem);
		}
		return billItemList.size() > 0 ? billItemList : null;
	}
	
	public void setBillItemList(List<ItemDetailDTO> billItemList) {
		for(ItemDetailDTO billItem: billItemList){
			if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL)){
				setPaperBillItem(billItem);
			}else if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE)){
				setPaperBillItem(billItem);
			}else if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE)){
				setPaperBillItem(billItem);
			}else if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_BR)){
				setPaperBillItem(billItem);
			}			
			else if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE)){
				setNoBillItem(billItem);
			}else if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_EBILL)){
				seteBillItem(billItem);
			}else if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_MYHKT_BILL)){
				setCsPortalItem(billItem);
			}else if(StringUtils.equals(billItem.getItemType(), LtsConstant.ITEM_TYPE_EMAIL_BILL)){
				setEmailBillItem(billItem);
			}
		}
//		this.billItemList = billItemList;
	}
	
	public ItemDetailDTO getPrePayItemE() {
		return prePayItemE;
	}
	public void setPrePayItemE(ItemDetailDTO prePayItemE) {
		this.prePayItemE = prePayItemE;
	}
	public ItemDetailDTO getPrePayItemA() {
		return prePayItemA;
	}
	public void setPrePayItemA(ItemDetailDTO prePayItemA) {
		this.prePayItemA = prePayItemA;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public String getBranchCodeHidden() {
		return branchCodeHidden;
	}
	public void setBranchCodeHidden(String branchCodeHidden) {
		this.branchCodeHidden = branchCodeHidden;
	}
	public String getSuspendReason() {
		return suspendReason;
	}
	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}
	public String getSubmitInd() {
		return submitInd;
	}
	public void setSubmitInd(String submitInd) {
		this.submitInd = submitInd;
	}
	public String getExistingPayMethodType() {
		return existingPayMethodType;
	}
	public void setExistingPayMethodType(String existingPayMethodType) {
		this.existingPayMethodType = existingPayMethodType;
	}
	public String getSelectButton() {
		return selectButton;
	}
	public void setSelectButton(String selectButton) {
		this.selectButton = selectButton;
	}
	public List<String[]> getPrepayItemIdList() {
		return prepayItemIdList;
	}
	public void setPrepayItemIdList(List<String[]> prepayItemIdList) {
		this.prepayItemIdList = prepayItemIdList;
	}
//	public String[] getSelectedItems() {
//		return selectedItems;
//	}
//	public void setSelectedItems(String[] selectedItems) {
//		this.selectedItems = selectedItems;
//	}
	public ItemDetailDTO getPrePayItemC() {
		return prePayItemC;
	}
	public void setPrePayItemC(ItemDetailDTO prePayItemC) {
		this.prePayItemC = prePayItemC;
	}
	public boolean isPrepayItemSelectedC() {
		return prepayItemSelectedC;
	}
	public void setPrepayItemSelectedC(boolean prepayItemSelectedC) {
		this.prepayItemSelectedC = prepayItemSelectedC;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustDocType() {
		return custDocType;
	}
	public void setCustDocType(String custDocType) {
		this.custDocType = custDocType;
	}
	public String getCustDocNum() {
		return custDocNum;
	}
	public void setCustDocNum(String custDocNum) {
		this.custDocNum = custDocNum;
	}
	public Boolean getThirdPartyBankAccount() {
		return thirdPartyBankAccount;
	}
	public void setThirdPartyBankAccount(Boolean thirdPartyBankAccount) {
		this.thirdPartyBankAccount = thirdPartyBankAccount;
	}
	public Boolean getThirdPartyCard() {
		return thirdPartyCard;
	}
	public void setThirdPartyCard(Boolean thirdPartyCard) {
		this.thirdPartyCard = thirdPartyCard;
	}
	public boolean isSalesMemoNumRequired() {
		return salesMemoNumRequired;
	}
	public void setSalesMemoNumRequired(boolean salesMemoNumRequired) {
		this.salesMemoNumRequired = salesMemoNumRequired;
	}
	public String getSalesMemoNum() {
		return salesMemoNum;
	}
	public void setSalesMemoNum(String salesMemoNum) {
		this.salesMemoNum = salesMemoNum;
	}
	public ItemDetailDTO getPaperBillItem() {
		return paperBillItem;
	}
	public void setPaperBillItem(ItemDetailDTO paperBillItem) {
		this.paperBillItem = paperBillItem;
	}
	public ItemDetailDTO geteBillItem() {
		return eBillItem;
	}
	public void seteBillItem(ItemDetailDTO eBillItem) {
		this.eBillItem = eBillItem;
	}
	public ItemDetailDTO getNoBillItem() {
		return noBillItem;
	}
	public void setNoBillItem(ItemDetailDTO noBillItem) {
		this.noBillItem = noBillItem;
	}
	public ItemDetailDTO getCsPortalItem() {
		return csPortalItem;
	}
	public void setCsPortalItem(ItemDetailDTO csPortalItem) {
		this.csPortalItem = csPortalItem;
	}
	public ItemDetailDTO getViewBillItem() {
		return viewBillItem;
	}
	public void setViewBillItem(ItemDetailDTO viewBillItem) {
		this.viewBillItem = viewBillItem;
	}
	public boolean isCsPortalExist() {
		return csPortalExist;
	}
	public void setCsPortalExist(boolean isCsPortalExist) {
		this.csPortalExist = isCsPortalExist;
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
	public boolean isClubMembExist() {
		return clubMembExist;
	}
	public void setClubMembExist(boolean clubMembExist) {
		this.clubMembExist = clubMembExist;
	}
	public String getOptOut() {
		return optOut;
	}
	public void setOptOut(String optOut) {
		this.optOut = optOut;
	}
	public String getOptOutReason() {
		return optOutReason;
	}
	public void setOptOutReason(String optOutReason) {
		this.optOutReason = optOutReason;
	}
	public String getOptOutRemark() {
		return optOutRemark;
	}
	public void setOptOutRemark(String optOutRemark) {
		this.optOutRemark = optOutRemark;
	}
	public boolean isPhantomAcct() {
		return phantomAcct;
	}
	public void setPhantomAcct(boolean phantomAcct) {
		this.phantomAcct = phantomAcct;
	}
	public String getClubEmail() {
		return clubEmail;
	}
	public void setClubEmail(String clubEmail) {
		this.clubEmail = clubEmail;
	}
	public String getClubMobile() {
		return clubMobile;
	}
	public void setClubMobile(String clubMobile) {
		this.clubMobile = clubMobile;
	}
	public boolean isCspNewReg() {
		return cspNewReg;
	}
	public void setCspNewReg(boolean cspNewReg) {
		this.cspNewReg = cspNewReg;
	}
	public String getRecontractAccountNo() {
		return recontractAccountNo;
	}
	public void setRecontractAccountNo(String recontractAccountNo) {
		this.recontractAccountNo = recontractAccountNo;
	}
	public boolean isAllowChangeBa() {
		return allowChangeBa;
	}
	public void setAllowChangeBa(boolean allowChangeBa) {
		this.allowChangeBa = allowChangeBa;
	}
	public boolean isChangeBa() {
		return changeBa;
	}
	public void setChangeBa(boolean changeBa) {
		this.changeBa = changeBa;
	}
	public String getBaQuickSearch() {
		return baQuickSearch;
	}
	public void setBaQuickSearch(String baQuickSearch) {
		this.baQuickSearch = baQuickSearch;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public boolean isInstantUpdateBa() {
		return instantUpdateBa;
	}
	public void setInstantUpdateBa(boolean instantUpdateBa) {
		this.instantUpdateBa = instantUpdateBa;
	}
	public boolean isCardVerifyRequired() {
		return cardVerifyRequired;
	}
	public void setCardVerifyRequired(boolean cardVerifyRequired) {
		this.cardVerifyRequired = cardVerifyRequired;
	}
	public List<ItemDetailDTO> getErChargeItemList() {
		return erChargeItemList;
	}
	public void setErChargeItemList(List<ItemDetailDTO> erChargeItemList) {
		this.erChargeItemList = erChargeItemList;
	}
	public String getEmailBillAddress() {
		return emailBillAddress;
	}
	public void setEmailBillAddress(String emailBillAddress) {
		this.emailBillAddress = emailBillAddress;
	}
	public String getSelectedBillItemId() {
		return selectedBillItemId;
	}
	public void setSelectedBillItemId(String selectedBillItemId) {
		this.selectedBillItemId = selectedBillItemId;
	}
	public ItemDetailDTO getEmailBillItem() {
		return emailBillItem;
	}
	public void setEmailBillItem(ItemDetailDTO emailBillItem) {
		this.emailBillItem = emailBillItem;
	}
	public String getRedemptionMedia() {
		return redemptionMedia;
	}
	public void setRedemptionMedia(String redemptionMedia) {
		this.redemptionMedia = redemptionMedia;
	}
	public String getRedemMediaSmsNum() {
		return redemMediaSmsNum;
	}
	public void setRedemMediaSmsNum(String redemMediaSmsNum) {
		this.redemMediaSmsNum = redemMediaSmsNum;
	}
	public String getRedemMediaEmailAddr() {
		return redemMediaEmailAddr;
	}
	public void setRedemMediaEmailAddr(String redemMediaEmailAddr) {
		this.redemMediaEmailAddr = redemMediaEmailAddr;
	}
	public boolean isRequireRedemPremium() {
		return requireRedemPremium;
	}
	public void setRequireRedemPremium(boolean requireRedemPremium) {
		this.requireRedemPremium = requireRedemPremium;
	}
	public boolean isCsldFailInd() {
		return csldFailInd;
	}
	public void setCsldFailInd(boolean csldFailInd) {
		this.csldFailInd = csldFailInd;
	}
	public String getDummyClubEmail() {
		return dummyClubEmail;
	}
	public void setDummyClubEmail(String dummyClubEmail) {
		this.dummyClubEmail = dummyClubEmail;
	}
	public boolean isDummy() {
		return isDummy;
	}
	public void setDummy(boolean isDummy) {
		this.isDummy = isDummy;
	}
	public String getClubRes() {
		return clubRes;
	}
	public void setClubRes(String clubRes) {
		this.clubRes = clubRes;
	}
	public String getMyHktRes() {
		return myHktRes;
	}
	public void setMyHktRes(String myHktRes) {
		this.myHktRes = myHktRes;
	}
	public String getCspReplyCd() {
		return cspReplyCd;
	}
	public void setCspReplyCd(String cspReplyCd) {
		this.cspReplyCd = cspReplyCd;
	}
	public CsPortalTxnDTO getCspVaildateRsp() {
		return cspVaildateRsp;
	}
	public void setCspVaildateRsp(CsPortalTxnDTO cspVaildateRsp) {
		this.cspVaildateRsp = cspVaildateRsp;
	}
	public boolean isCspExclude() {
		return cspExclude;
	}
	public void setCspExclude(boolean cspExclude) {
		this.cspExclude = cspExclude;
	}
	public boolean isEnableCsp() {
		return enableCsp;
	}
	public void setEnableCsp(boolean enableCsp) {
		this.enableCsp = enableCsp;
	}
	public String getPaperBillCharge() {
		return paperBillCharge;
	}
	public void setPaperBillCharge(String paperBillCharge) {
		this.paperBillCharge = paperBillCharge;
	}
	public boolean isAllowModifyInd() {
		return allowModifyInd;
	}
	public void setAllowModifyInd(boolean allowModifyInd) {
		this.allowModifyInd = allowModifyInd;
	}
	public String getPaperWaiveReason() {
		return paperWaiveReason;
	}
	public void setPaperWaiveReason(String paperWaiveReason) {
		this.paperWaiveReason = paperWaiveReason;
	}
	public boolean isRecontractCase() {
		return recontractCase;
	}
	public void setRecontractCase(boolean recontractCase) {
		this.recontractCase = recontractCase;
	}
	public boolean isDocTypeBR() {
		return docTypeBR;
	}
	public void setDocTypeBR(boolean docTypeBR) {
		this.docTypeBR = docTypeBR;
	}
	public String getPaperBillWaiveOtherStaffId() {
		return paperBillWaiveOtherStaffId;
	}
	public void setPaperBillWaiveOtherStaffId(String paperBillWaiveOtherStaffId) {
		this.paperBillWaiveOtherStaffId = paperBillWaiveOtherStaffId;
	}
	public PrepaymentLkupCriteriaDTO getPrepaymentLkupCriteria() {
		return prepaymentLkupCriteria;
	}
	public void setPrepaymentLkupCriteria(PrepaymentLkupCriteriaDTO prepaymentLkupCriteria) {
		this.prepaymentLkupCriteria = prepaymentLkupCriteria;
	}
	public ItemDetailDTO getPrePayItemM() {
		return prePayItemM;
	}
	public void setPrePayItemM(ItemDetailDTO prePayItemM) {
		this.prePayItemM = prePayItemM;
	}
	public String getExistingCreditCardNum() {
		return existingCreditCardNum;
	}
	public void setExistingCreditCardNum(String existingCreditCardNum) {
		this.existingCreditCardNum = existingCreditCardNum;
	}
	public boolean isAllowRedemPaper() {
		return allowRedemPaper;
	}
	public void setAllowRedemPaper(boolean allowRedemPaper) {
		this.allowRedemPaper = allowRedemPaper;
	}
	
}
