package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsAcqPaymentMethodFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3621237844817483552L;
	/**
	 * 
	 */

	private String submitInd;
	private String suspendReason;
	private String suspendRemarks;
	
	private String custName;
	private String custDocType;
	private String custDocNum;
	
	private double ltsTenure;
	private double imsTenure;
	private String tenureCode;

	private boolean salesMemoNumRequired;
	
	private boolean iddDeposit;
	private boolean prepayment;
	private boolean acctNotMatch;
	
	private List<PaymentMethodDtl> paymentMethodDtlList;
	
	public class PaymentMethodDtl implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7204646515650670665L;

		private String acctNum;
		private boolean acctChrgTypeI;
		private AccountDetailProfileLtsDTO acctProfile;
		
		private String newPayMethodType;
		private String existingPayMethodType;
		private String existingPayMethodDisplay;

		private String salesMemoNum;
		
		private boolean allowKeepExistPayMtd;

		private ItemDetailDTO iddDepositItem;
		
		//cash
		private boolean allowCash;
		private ItemDetailDTO cashPrePayItem;
		
		//bank auto pay
		private boolean allowAutoPay;
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
		private ItemDetailDTO autopayPrePayItem;
		
		//credit card
		private boolean allowCreditCard;
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
		private ItemDetailDTO creditCardPrePayItem;
		
		private boolean allowAwaitPayment;
		
		private String termCd;
		private String termDate;
		
		
		public String getSalesMemoNum() {
			return salesMemoNum;
		}
		public void setSalesMemoNum(String salesMemoNum) {
			this.salesMemoNum = salesMemoNum;
		}
		
		public String getAcctNum() {
			return acctNum;
		}
		public void setAcctNum(String acctNum) {
			this.acctNum = acctNum;
		}
		public String getNewPayMethodType() {
			return newPayMethodType;
		}
		public void setNewPayMethodType(String newPayMethodType) {
			this.newPayMethodType = newPayMethodType;
		}
		public String getExistingPayMethodType() {
			return existingPayMethodType;
		}
		public void setExistingPayMethodType(String existingPayMethodType) {
			this.existingPayMethodType = existingPayMethodType;
		}
		public String getExistingPayMethodDisplay() {
			return existingPayMethodDisplay;
		}
		public void setExistingPayMethodDisplay(String existingPayMethodDisplay) {
			this.existingPayMethodDisplay = existingPayMethodDisplay;
		}
		public Boolean getThirdPartyBankAccount() {
			return thirdPartyBankAccount;
		}
		public void setThirdPartyBankAccount(Boolean thirdPartyBankAccount) {
			this.thirdPartyBankAccount = thirdPartyBankAccount;
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
		public String getAutoPayUpperLimit() {
			return autoPayUpperLimit;
		}
		public void setAutoPayUpperLimit(String autoPayUpperLimit) {
			this.autoPayUpperLimit = autoPayUpperLimit;
		}
		public String getApplicationDate() {
			return applicationDate;
		}
		public void setApplicationDate(String applicationDate) {
			this.applicationDate = applicationDate;
		}
		public String getBranchCodeHidden() {
			return branchCodeHidden;
		}
		public void setBranchCodeHidden(String branchCodeHidden) {
			this.branchCodeHidden = branchCodeHidden;
		}
		public Boolean getThirdPartyCard() {
			return thirdPartyCard;
		}
		public void setThirdPartyCard(Boolean thirdPartyCard) {
			this.thirdPartyCard = thirdPartyCard;
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
		public boolean isCardVerifyRequired() {
			return cardVerifyRequired;
		}
		public void setCardVerifyRequired(boolean cardVerifyRequired) {
			this.cardVerifyRequired = cardVerifyRequired;
		}
		public boolean isCardVerified() {
			return cardVerified;
		}
		public void setCardVerified(boolean cardVerified) {
			this.cardVerified = cardVerified;
		}
		public boolean isAllowCash() {
			return allowCash;
		}
		public void setAllowCash(boolean allowCash) {
			this.allowCash = allowCash;
		}
		public boolean isAllowAutoPay() {
			return allowAutoPay;
		}
		public void setAllowAutoPay(boolean allowAutoPay) {
			this.allowAutoPay = allowAutoPay;
		}
		public boolean isAllowCreditCard() {
			return allowCreditCard;
		}
		public boolean isAllowAwaitPayment() {
			return allowAwaitPayment;
		}
		public void setAllowAwaitPayment(boolean allowAwaitPayment) {
			this.allowAwaitPayment = allowAwaitPayment;
		}
		public void setAllowCreditCard(boolean allowCreditCard) {
			this.allowCreditCard = allowCreditCard;
		}
		public ItemDetailDTO getCashPrePayItem() {
			return cashPrePayItem;
		}
		public void setCashPrePayItem(ItemDetailDTO cashPrePayItem) {
			this.cashPrePayItem = cashPrePayItem;
		}
		public ItemDetailDTO getAutopayPrePayItem() {
			return autopayPrePayItem;
		}
		public void setAutopayPrePayItem(ItemDetailDTO autopayPrePayItem) {
			this.autopayPrePayItem = autopayPrePayItem;
		}
		public ItemDetailDTO getCreditCardPrePayItem() {
			return creditCardPrePayItem;
		}
		public void setCreditCardPrePayItem(ItemDetailDTO creditCardPrePayItem) {
			this.creditCardPrePayItem = creditCardPrePayItem;
		}
		public boolean isAllowKeepExistPayMtd() {
			return allowKeepExistPayMtd;
		}
		public void setAllowKeepExistPayMtd(boolean allowKeepExistPayMtd) {
			this.allowKeepExistPayMtd = allowKeepExistPayMtd;
		}
		public AccountDetailProfileLtsDTO getAcctProfile() {
			return acctProfile;
		}
		public void setAcctProfile(AccountDetailProfileLtsDTO acctProfile) {
			this.acctProfile = acctProfile;
		}
		public ItemDetailDTO getIddDepositItem() {
			return iddDepositItem;
		}
		public void setIddDepositItem(ItemDetailDTO iddDepositItem) {
			this.iddDepositItem = iddDepositItem;
		}		
		public boolean isAcctChrgTypeI() {
			return acctChrgTypeI;
		}
		public void setAcctChrgTypeI(boolean acctChrgTypeI) {
			this.acctChrgTypeI = acctChrgTypeI;
		}
		public String getTermCd() {
			return termCd;
		}
		public void setTermCd(String termCd) {
			this.termCd = termCd;
		}
		public String getTermDate() {
			return termDate;
		}
		public void setTermDate(String termDate) {
			this.termDate = termDate;
		}
		public ItemDetailDTO getSelectedBasketPrepayItem(){
			if(LtsConstant.PAYMENT_TYPE_CASH.equals(newPayMethodType)
					&& cashPrePayItem != null){
				return cashPrePayItem.isSelected()?  cashPrePayItem : null;
			}else if(LtsConstant.PAYMENT_TYPE_AUTO_PAY.equals(newPayMethodType)
					&& autopayPrePayItem != null){
				return autopayPrePayItem.isSelected()?  autopayPrePayItem : null;
			}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(newPayMethodType)
					&& !StringUtils.startsWith(cardNum, LtsConstant.TNG_CARD_PREFIX)
					&& creditCardPrePayItem != null){
				return creditCardPrePayItem.isSelected()?  creditCardPrePayItem : null;
			}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(newPayMethodType)
					&& StringUtils.startsWith(cardNum, LtsConstant.TNG_CARD_PREFIX)
					&& cashPrePayItem != null){
				return cashPrePayItem.isSelected()?  cashPrePayItem : null;
			}
			return null;
		}

		public boolean isPrepayItemSelected(){
			
			boolean basketPrepaySelected = false;
			
			basketPrepaySelected = (getSelectedBasketPrepayItem() != null);
			
			return basketPrepaySelected;
		}
	}

	public List<PaymentMethodDtl> getPaymentMethodDtlList() {
		return paymentMethodDtlList;
	}

	public void setPaymentMethodDtlList(List<PaymentMethodDtl> paymentMethodDtlList) {
		this.paymentMethodDtlList = paymentMethodDtlList;
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

	public double getLtsTenure() {
		return ltsTenure;
	}

	public void setLtsTenure(double ltsTenure) {
		this.ltsTenure = ltsTenure;
	}

	public double getImsTenure() {
		return imsTenure;
	}

	public void setImsTenure(double imsTenure) {
		this.imsTenure = imsTenure;
	}

	public String getTenureCode() {
		return tenureCode;
	}

	public void setTenureCode(String tenureCode) {
		this.tenureCode = tenureCode;
	}
	
	public boolean isSalesMemoNumRequired() {
		return salesMemoNumRequired;
	}
	
	public void setSalesMemoNumRequired(boolean salesMemoNumRequired) {
		this.salesMemoNumRequired = salesMemoNumRequired;
	}

	public String getSubmitInd() {
		return submitInd;
	}

	public void setSubmitInd(String submitInd) {
		this.submitInd = submitInd;
	}

	public String getSuspendReason() {
		return suspendReason;
	}

	public String getSuspendRemarks() {
		return suspendRemarks;
	}

	public void setSuspendRemarks(String suspendRemarks) {
		this.suspendRemarks = suspendRemarks;
	}

	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}
	
	public boolean isIddDeposit() {
		return iddDeposit;
	}

	public void setIddDeposit(boolean iddDeposit) {
		this.iddDeposit = iddDeposit;
	}

	public boolean isPrepayment() {
		return prepayment;
	}

	public void setPrepayment(boolean prepayment) {
		this.prepayment = prepayment;
	}

	public boolean isAcctNotMatch() {
		return acctNotMatch;
	}

	public void setAcctNotMatch(boolean acctNotMatch) {
		this.acctNotMatch = acctNotMatch;
	}

	public PaymentMethodDtl getPrimaryPaymentMethodDtl(){
		if(paymentMethodDtlList.size() == 1){
			return paymentMethodDtlList.get(0);
		}
		
		for(PaymentMethodDtl dtl: paymentMethodDtlList){
			if(dtl.getAcctProfile().isPrimaryAcctInd()){
				return dtl;
			}
		}
		return null;
	}
	
	public ItemDetailDTO getSelectedBasketPrepayItem(){
		for(PaymentMethodDtl dtl: paymentMethodDtlList){
			ItemDetailDTO basketPrepayItem = dtl.getSelectedBasketPrepayItem();
			if(basketPrepayItem != null){
				return basketPrepayItem;
			}
		}
		return null;
	}
	
	public boolean isPrepayItemSelected(){
		if(paymentMethodDtlList == null){
			return false;
		}
		
		for(PaymentMethodDtl dtl: paymentMethodDtlList){
			if(dtl.isPrepayItemSelected()){
				return true;
			}
		}
		
		return false;
	}
	
}
