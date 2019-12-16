package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class AccountLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 5102893229482780447L;
	private String orderId; // BOMWEB_ACCT.ORDER_ID
	private String custNo; // BOMWEB_ACCT.CUST_NO
	private String acctName; // BOMWEB_ACCT.ACCT_NAME
	private String billFreq; // BOMWEB_ACCT.BILL_FREQ
	private String billLang; // BOMWEB_ACCT.BILL_LANG
	private String smsNo; // BOMWEB_ACCT.SMS_NO
	private String emailAddr; // BOMWEB_ACCT.EMAIL_ADDR
	private String acctNo; // BOMWEB_ACCT.ACCT_NO
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ACCT.CREATE_DATE
	private OraNumber billPeriod; // BOMWEB_ACCT.BILL_PERIOD
	private String billMedia; // BOMWEB_ACCT.BILL_MEDIA
	private String billFmt;	// BOMWEB_ACCT.BILL_FMT
	private String autopayStatementInd;	// BOMWEB_ACCT.AUTOPAY_STATEMENT_IND
	private String existBillMedia; // BOMWEB_ACCT.EXIST_BILL_MEDIA
	private String existEmailAddr; // BOMWEB_ACCT.EXIST_EMAIL_ADDR
	private String redemptionMedia; // BOMWEB_ACCT.REDEMPTION_MEDIA
	private String redemptionEmailAddr; // BOMWEB_ACCT.REDEMPTION_EMAIL_ADDR
	private String redemptionSmsNo; // BOMWEB_ACCT.REDEMPTION_SMS_NO
	private String isNew; // BOMWEB_ACCT.IS_NEW
	private String acctWaivePaperInd; //BOMWEB_ACCT.ACCT_WAIVE_PAPER_IND 
	private String waivePaperReaCd;  //BOMWEB_ACCT.WAIVE_PAPER_REA_CD      VARCHAR2(2) 
	private String waivePaperRemarks; //BOMWEB_ACCT.WAIVE_PAPER_REMARKS    VARCHAR(400)
	
	
	public AccountLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "acctNo"};
	}
	
	public String getTableName() {
		return "BOMWEB_ACCT";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public String getAcctName() {
		return this.acctName;
	}

	public String getBillFreq() {
		return this.billFreq;
	}

	public String getBillLang() {
		return this.billLang;
	}

	public String getSmsNo() {
		return this.smsNo;
	}

	public String getEmailAddr() {
		return this.emailAddr;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public String getBillMedia() {
		return this.billMedia;
	}

	public String getBillPeriod() {
		return this.billPeriod != null ? this.billPeriod.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
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

	public void setAcctName(String pAcctName) {
		this.acctName = pAcctName;
	}

	public void setBillFreq(String pBillFreq) {
		this.billFreq = pBillFreq;
	}

	public void setBillLang(String pBillLang) {
		this.billLang = pBillLang;
	}

	public void setSmsNo(String pSmsNo) {
		this.smsNo = pSmsNo;
	}

	public void setEmailAddr(String pEmailAddr) {
		this.emailAddr = pEmailAddr;
	}

	public void setAcctNo(String pAcctNo) {
		this.acctNo = pAcctNo;
	}

	public void setBillMedia(String pBillMedia) {
		this.billMedia = pBillMedia;
	}

	public void setBillPeriod(String pBillPeriod) {
		this.billPeriod = new OraNumber(pBillPeriod);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public String getBillFmt() {
		return billFmt;
	}

	public void setBillFmt(String billFmt) {
		this.billFmt = billFmt;
	}

	public String getAutopayStatementInd() {
		return autopayStatementInd;
	}

	public void setAutopayStatementInd(String autopayStatementInd) {
		this.autopayStatementInd = autopayStatementInd;
	}

	public String getExistBillMedia() {
		return this.existBillMedia;
	}

	public void setExistBillMedia(String pExistBillMedia) {
		this.existBillMedia = pExistBillMedia;
	}

	public String getExistEmailAddr() {
		return existEmailAddr;
	}

	public void setExistEmailAddr(String existEmailAddr) {
		this.existEmailAddr = existEmailAddr;
	}

	public String getRedemptionMedia() {
		return redemptionMedia;
	}

	public void setRedemptionMedia(String redemptionMedia) {
		this.redemptionMedia = redemptionMedia;
	}

	public String getRedemptionEmailAddr() {
		return redemptionEmailAddr;
	}

	public void setRedemptionEmailAddr(String redemptionEmailAddr) {
		this.redemptionEmailAddr = redemptionEmailAddr;
	}

	public String getRedemptionSmsNo() {
		return redemptionSmsNo;
	}

	public void setRedemptionSmsNo(String redemptionSmsNo) {
		this.redemptionSmsNo = redemptionSmsNo;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getAcctWaivePaperInd() {
		return acctWaivePaperInd;
	}

	public void setAcctWaivePaperInd(String accWaivePaperInd) {
		this.acctWaivePaperInd = accWaivePaperInd;
	}

	public String getWaivePaperReaCd() {
		return waivePaperReaCd;
	}

	public void setWaivePaperReaCd(String waivePaperReaCd) {
		this.waivePaperReaCd = waivePaperReaCd;
	}

	public String getWaivePaperRemarks() {
		return waivePaperRemarks;
	}

	public void setWaivePaperRemarks(String waivePaperRemarks) {
		this.waivePaperRemarks = waivePaperRemarks;
	}
	
}
