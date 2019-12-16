package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class PrepayLtsDAO extends DaoBaseImpl {

	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3543008107268981387L;

	private String orderId; // BOMWEB_ORDER_LTS_PREPAY.ORDER_ID        
	private String prepayType; // BOMWEB_ORDER_LTS_PREPAY.PREPAY_TYPE
	private String acctNo; // BOMWEB_ORDER_LTS_PREPAY.ACCT_NO
	private String prepayAmt; // BOMWEB_ORDER_LTS_PREPAY.PREPAY_AMT
	private String mercahntCode; // BOMWEB_ORDER_LTS_PREPAY.MERCAHNT_CODE
	private String billType; // BOMWEB_ORDER_LTS_PREPAY.BILL_TYPE
	private String shopCode; // BOMWEB_ORDER_LTS_PREPAY.SHOP_CODE
	private String tranCode; // BOMWEB_ORDER_LTS_PREPAY.TRAN_CODE
	private String paymentStatus; // BOMWEB_ORDER_LTS_PREPAY.PAYMENT_STATUS
	private String createBy; // BOMWEB_ORDER_LTS_PREPAY.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_LTS_PREPAY.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_LTS_PREPAY.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_LTS_PREPAY.LAST_UPD_DATE
	private OraDate prepayDate = new OraDateYYYYMMDDHH24MISS(); //BOMWEB_ORDER_LTS_PREPAY.PREPAY_DATE

	

	
	public PrepayLtsDAO() {
		primaryKeyFields = new String[] { "orderId"};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_LTS_PREPAY";
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPrepayType() {
		return prepayType;
	}

	public void setPrepayType(String prepayType) {
		this.prepayType = prepayType;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(String prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getMercahntCode() {
		return mercahntCode;
	}

	public void setMercahntCode(String mercahntCode) {
		this.mercahntCode = mercahntCode;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getTranCode() {
		return tranCode;
	}

	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public void setCreateDate(OraDate createDate) {
		this.createDate = createDate;
	}

	public void setLastUpdDate(OraDate lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}
	
	public String getPrepayDate() {
		return this.prepayDate != null ? this.prepayDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}
	
	public OraDate getPrepayDateORACLE() {
		return this.prepayDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}


	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public void setPrepayDate(String pPrepayDate) {
		this.prepayDate = new OraDateYYYYMMDDHH24MISS(pPrepayDate);
	}
	

}
