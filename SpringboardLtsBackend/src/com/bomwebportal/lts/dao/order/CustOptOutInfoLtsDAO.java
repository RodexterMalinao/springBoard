package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public class CustOptOutInfoLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 7235775485210908723L;
	
	private String orderId; // BOMWEB_CUST_OPTOUT_INFO.ORDER_ID
	private String custNo; // BOMWEB_CUST_OPTOUT_INFO.CUST_NO
	private String optInd; // BOMWEB_CUST_OPTOUT_INFO.OPT_IND
	private String directMailing; // BOMWEB_CUST_OPTOUT_INFO.DIRECT_MAILING
	private String outbound; // BOMWEB_CUST_OPTOUT_INFO.OUTBOUND
	private String sms; // BOMWEB_CUST_OPTOUT_INFO.SMS
	private String email; // BOMWEB_CUST_OPTOUT_INFO.EMAIL
	private String webBill; // BOMWEB_CUST_OPTOUT_INFO.WEB_BILL
	private String nonsalesSms; // BOMWEB_CUST_OPTOUT_INFO.NONSALES_SMS
	private String internet; // BOMWEB_CUST_OPTOUT_INFO.INTERNET
	private String createBy; // BOMWEB_CUST_OPTOUT_INFO.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_CUST_OPTOUT_INFO.CREATE_DATE
	private String lastUpdBy; // BOMWEB_CUST_OPTOUT_INFO.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_CUST_OPTOUT_INFO.LAST_UPD_DATE
	private String bm; // BOMWEB_CUST_OPTOUT_INFO.BM
	private String billInsert; // BOMWEB_CUST_OPTOUT_INFO.BILL_INSERT
	private String smsEye; // BOMWEB_CUST_OPTOUT_INFO.SMS_EYE
	private String cdOutdial; // BOMWEB_CUST_OPTOUT_INFO.CD_OUTDIAL
	private String updBomStatus; // BOMWEB_CUST_OPTOUT_INFO.UPD_BOM_STATUS;
	private String errMsg; // BOMWEB_CUST_OPTOUT_INFO.ERR_MSG;

	public CustOptOutInfoLtsDAO() {
		primaryKeyFields = new String[] {};
	}

	public String getTableName() {
		return "BOMWEB_CUST_OPTOUT_INFO";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public String getDirectMailing() {
		return this.directMailing;
	}

	public String getOutbound() {
		return this.outbound;
	}

	public String getSms() {
		return this.sms;
	}

	public String getEmail() {
		return this.email;
	}

	public String getWebBill() {
		return this.webBill;
	}

	public String getNonsalesSms() {
		return this.nonsalesSms;
	}

	public String getInternet() {
		return this.internet;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setCustNo(String pCustNo) {
		this.custNo = pCustNo;
	}

	public void setDirectMailing(String pDirectMailing) {
		this.directMailing = pDirectMailing;
	}

	public void setOutbound(String pOutbound) {
		this.outbound = pOutbound;
	}

	public void setSms(String pSms) {
		this.sms = pSms;
	}

	public void setEmail(String pEmail) {
		this.email = pEmail;
	}

	public void setWebBill(String pWebBill) {
		this.webBill = pWebBill;
	}

	public void setNonsalesSms(String pNonsalesSms) {
		this.nonsalesSms = pNonsalesSms;
	}

	public void setInternet(String pInternet) {
		this.internet = pInternet;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getBillInsert() {
		return billInsert;
	}

	public void setBillInsert(String billInsert) {
		this.billInsert = billInsert;
	}

	public String getSmsEye() {
		return smsEye;
	}

	public void setSmsEye(String smsEye) {
		this.smsEye = smsEye;
	}

	public String getCdOutdial() {
		return cdOutdial;
	}

	public void setCdOutdial(String cdOutdial) {
		this.cdOutdial = cdOutdial;
	}

	public String getOptInd() {
		return optInd;
	}

	public void setOptInd(String optInd) {
		this.optInd = optInd;
	}

	public String getUpdBomStatus() {
		return updBomStatus;
	}

	public void setUpdBomStatus(String updBomStatus) {
		this.updBomStatus = updBomStatus;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	
}
