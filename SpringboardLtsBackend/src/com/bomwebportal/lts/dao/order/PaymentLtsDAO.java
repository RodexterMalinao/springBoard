package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class PaymentLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -5397275703585512009L;

	private String orderId; // BOMWEB_PAYMENT.ORDER_ID
	private String custNo; // BOMWEB_PAYMENT.CUST_NO
	private String payMtdKey; // BOMWEB_PAYMENT.PAY_MTD_KEY
	private String acctNo; // BOMWEB_PAYMENT.ACCT_NO
	private OraDate autopayAppDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_PAYMENT.AUTOPAY_APP_DATE
	private OraNumber autopayUpLimAmt; // BOMWEB_PAYMENT.AUTOPAY_UP_LIM_AMT
	private String bAcctNo; // BOMWEB_PAYMENT.B_ACCT_NO
	private String bAcctHoldName; // BOMWEB_PAYMENT.B_ACCT_HOLD_NAME
	private String bAcctHoldIdType; // BOMWEB_PAYMENT.B_ACCT_HOLD_ID_TYPE
	private String bAcctHoldIdNum; // BOMWEB_PAYMENT.B_ACCT_HOLD_ID_NUM
	private String branchCd; // BOMWEB_PAYMENT.BRANCH_CD
	private String bankCd; // BOMWEB_PAYMENT.BANK_CD
	private String payMtdType; // BOMWEB_PAYMENT.PAY_MTD_TYPE
	private String thirdPartyInd; // BOMWEB_PAYMENT.THIRD_PARTY_IND
	private String ccType; // BOMWEB_PAYMENT.CC_TYPE
	private String ccNum; // BOMWEB_PAYMENT.CC_NUM
	private String ccHoldName; // BOMWEB_PAYMENT.CC_HOLD_NAME
	private String ccExpDate; // BOMWEB_PAYMENT.CC_EXP_DATE
	private String ccIssueBank; // BOMWEB_PAYMENT.CC_ISSUE_BANK
	private String ccIdDocType; // BOMWEB_PAYMENT.CC_ID_DOC_TYPE
	private String ccIdDocNo; // BOMWEB_PAYMENT.CC_ID_DOC_NO
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_PAYMENT.CREATE_DATE
	private String createBy; // BOMWEB_PAYMENT.CREATE_BY
	private String lastUpdBy; // BOMWEB_PAYMENT.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_PAYMENT.LAST_UPD_DATE
	private String ccVerifiedInd; // BOMWEB_PAYMENT.CC_VERIFIED_IND
	private String action; // BOMWEB_PAYMENT.ACTION
	private String salesMemoNum; // BOMWEB_PAYMENT.SALES_MEMO_NUM
	private OraNumber prepayAmt; // BOMWEB_PAYMENT.SALES_MEMO_NUM
	private String autopayStatementInd; // BOMWEB_PAYMENT.AUTOPAY_STATEMENT_IND
	private String termCd; // BOMWEB_PAYMENT.TERM_CD
	private OraDate termDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_PAYMENT.TERM_DATE

	public PaymentLtsDAO() {
		primaryKeyFields = new String[] { "orderId", "acctNo", "payMtdType" };
	}

	public String getTableName() {
		return "BOMWEB_PAYMENT";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public String getPayMtdKey() {
		return this.payMtdKey;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public String getBAcctNo() {
		return this.bAcctNo;
	}

	public String getBAcctHoldName() {
		return this.bAcctHoldName;
	}

	public String getBAcctHoldIdType() {
		return this.bAcctHoldIdType;
	}

	public String getBAcctHoldIdNum() {
		return this.bAcctHoldIdNum;
	}

	public String getBranchCd() {
		return this.branchCd;
	}

	public String getBankCd() {
		return this.bankCd;
	}

	public String getCcType() {
		return this.ccType;
	}

	public String getCcNum() {
		return this.ccNum;
	}

	public String getCcHoldName() {
		return this.ccHoldName;
	}

	public String getCcExpDate() {
		return this.ccExpDate;
	}

	public String getCcIssueBank() {
		return this.ccIssueBank;
	}

	public String getCcIdDocType() {
		return this.ccIdDocType;
	}

	public String getCcIdDocNo() {
		return this.ccIdDocNo;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getCcVerifiedInd() {
		return this.ccVerifiedInd;
	}

	public String getAction() {
		return this.action;
	}

	public String getAutopayUpLimAmt() {
		return this.autopayUpLimAmt != null ? this.autopayUpLimAmt.toString()
				: null;
	}

	public String getAutopayAppDate() {
		return this.autopayAppDate != null ? this.autopayAppDate.toString()
				: null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}
	
	public String getTermDate() {
		return this.termDate != null ? this.termDate.toString() : null;
	}

	public OraDate getAutopayAppDateORACLE() {
		return this.autopayAppDate;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}
	
	public OraDate getTermDateORACLE() {
		return this.termDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setCustNo(String pCustNo) {
		this.custNo = pCustNo;
	}

	public void setPayMtdKey(String pPayMtdKey) {
		this.payMtdKey = pPayMtdKey;
	}

	public void setAcctNo(String pAcctNo) {
		this.acctNo = pAcctNo;
	}

	public void setBAcctNo(String pBAcctNo) {
		this.bAcctNo = pBAcctNo;
	}

	public void setBAcctHoldName(String pBAcctHoldName) {
		this.bAcctHoldName = pBAcctHoldName;
	}

	public void setBAcctHoldIdType(String pBAcctHoldIdType) {
		this.bAcctHoldIdType = pBAcctHoldIdType;
	}

	public void setBAcctHoldIdNum(String pBAcctHoldIdNum) {
		this.bAcctHoldIdNum = pBAcctHoldIdNum;
	}

	public void setBranchCd(String pBranchCd) {
		this.branchCd = pBranchCd;
	}

	public void setBankCd(String pBankCd) {
		this.bankCd = pBankCd;
	}

	public void setCcType(String pCcType) {
		this.ccType = pCcType;
	}

	public void setCcNum(String pCcNum) {
		this.ccNum = pCcNum;
	}

	public void setCcHoldName(String pCcHoldName) {
		this.ccHoldName = pCcHoldName;
	}

	public void setCcExpDate(String pCcExpDate) {
		this.ccExpDate = pCcExpDate;
	}

	public void setCcIssueBank(String pCcIssueBank) {
		this.ccIssueBank = pCcIssueBank;
	}

	public void setCcIdDocType(String pCcIdDocType) {
		this.ccIdDocType = pCcIdDocType;
	}

	public void setCcIdDocNo(String pCcIdDocNo) {
		this.ccIdDocNo = pCcIdDocNo;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCcVerifiedInd(String pCcVerifiedInd) {
		this.ccVerifiedInd = pCcVerifiedInd;
	}

	public void setAction(String pAction) {
		this.action = pAction;
	}

	public void setAutopayUpLimAmt(String pAutopayUpLimAmt) {
		this.autopayUpLimAmt = new OraNumber(pAutopayUpLimAmt);
	}

	public void setAutopayAppDate(String pAutopayAppDate) {
		this.autopayAppDate = new OraDateYYYYMMDDHH24MISS(pAutopayAppDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	
	public void setTermDate(String pTermDate) {
		this.termDate = new OraDateYYYYMMDDHH24MISS(pTermDate);
	}

	public String getPayMtdType() {
		return payMtdType;
	}

	public void setPayMtdType(String payMtdType) {
		this.payMtdType = payMtdType;
	}

	public String getThirdPartyInd() {
		return thirdPartyInd;
	}

	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}

	public String getSalesMemoNum() {
		return this.salesMemoNum;
	}

	public void setSalesMemoNum(String pSalesMemoNum) {
		this.salesMemoNum = pSalesMemoNum;
	}
	
	public String getPrepayAmt() {
		return this.prepayAmt != null ? this.prepayAmt.toString()
				: null;
	}
	
	public void setPrepayAmt(String pPrepayAmt) {
		this.prepayAmt = new OraNumber(pPrepayAmt);
	}

	public String getAutopayStatementInd() {
		return autopayStatementInd;
	}

	public void setAutopayStatementInd(String autopayStatementInd) {
		this.autopayStatementInd = autopayStatementInd;
	}

	public String getTermCd() {
		return termCd;
	}

	public void setTermCd(String termCd) {
		this.termCd = termCd;
	}
	
	
}
