package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MobCcsPaymentTransDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4794931301100236879L;
	private String mrt;
	private String order_id;
	private Date trans_date;
	private String cc_type;
	private String pay_method;
	private BigDecimal pay_amount;
	private String ref_no;
	private String handled_by;
	
	private String ccNum;
	private Integer ccInstSchedule;
	private String approveCode;
	/**
	 * check whether the payment need to be approved
	 * if yes then approval code need to be entered
	 */
	private boolean approveFlag;
	/**
	 * check whether the batch number need to be entered
	 * if yes then batch number need to be entered
	 */
	private boolean batchNumFlag;
	/**
	 * Batch Number
	 */
	private String batchNum;
	
	private String msisdn;
	private Date deliveryDate;
	private String ccExpDate;
	private String ccIssueBank;
	private String createBy;
	private String lastUpdateBy;
	
	private String payTypeDesc;
	private Date bankInDate;
	
	private String epaylinkTxnId;
	
	public String getEpaylinkTxnId() {
		return epaylinkTxnId;
	}
	public void setEpaylinkTxnId(String epaylinkTxnId) {
		this.epaylinkTxnId = epaylinkTxnId;
	}
	public Date getBankInDate() {
		return bankInDate;
	}
	public void setBankInDate(Date bankInDate) {
		this.bankInDate = bankInDate;
	}
	/**
	 * @return the batchNumFlag
	 */
	public boolean isBatchNumFlag() {
		return batchNumFlag;
	}
	/**
	 * @param batchNumFlag the batchNumFlag to set
	 */
	public void setBatchNumFlag(boolean batchNumFlag) {
		this.batchNumFlag = batchNumFlag;
	}
	
	/**
	 * @return the batchNum
	 */
	public String getBatchNum() {
		return batchNum;
	}
	/**
	 * @param batchNum the batchNum to set
	 */
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	/**
	 * @return the payTypeDesc
	 */
	public String getPayTypeDesc() {
		return payTypeDesc;
	}
	/**
	 * @param payTypeDesc the payTypeDesc to set
	 */
	public void setPayTypeDesc(String payTypeDesc) {
		this.payTypeDesc = payTypeDesc;
	}
	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	/**
	 * @return the ccIssueBank
	 */
	public String getCcIssueBank() {
		return ccIssueBank;
	}
	/**
	 * @param ccIssueBank the ccIssueBank to set
	 */
	public void setCcIssueBank(String ccIssueBank) {
		this.ccIssueBank = ccIssueBank;
	}
	/**
	 * @return the ccExpDate
	 */
	public String getCcExpDate() {
		return ccExpDate;
	}
	/**
	 * @param ccExpDate the ccExpDate to set
	 */
	public void setCcExpDate(String ccExpDate) {
		this.ccExpDate = ccExpDate;
	}
	/**
	 * @return the deliveryDate
	 */
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate the deliveryDate to set
	 */
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the approveFlag
	 */
	public boolean isApproveFlag() {
		return approveFlag;
	}
	/**
	 * @param approveFlag the approveFlag to set
	 */
	public void setApproveFlag(boolean approveFlag) {
		this.approveFlag = approveFlag;
	}
	public String getMrt() {
		return mrt;
	}
	public void setMrt(String mrt) {
		this.mrt = mrt;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Date getTrans_date() {
		return trans_date;
	}
	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}
	public String getCc_type() {
		return cc_type;
	}
	public void setCc_type(String cc_type) {
		this.cc_type = cc_type;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	/**
	 * @return the pay_amount
	 */
	public BigDecimal getPay_amount() {
		return pay_amount;
	}
	/**
	 * @param pay_amount the pay_amount to set
	 */
	public void setPay_amount(BigDecimal pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getRef_no() {
		return ref_no;
	}
	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}
	public String getHandled_by() {
		return handled_by;
	}
	public void setHandled_by(String handled_by) {
		this.handled_by = handled_by;
	}
	public String getCcNum() {
		return ccNum;
	}
	public void setCcNum(String ccNum) {
		this.ccNum = ccNum;
	}
	public Integer getCcInstSchedule() {
		return ccInstSchedule;
	}
	public void setCcInstSchedule(Integer ccInstSchedule) {
		this.ccInstSchedule = ccInstSchedule;
	}
	public String getApproveCode() {
		return approveCode;
	}
	public void setApproveCode(String approveCode) {
		this.approveCode = approveCode;
	}
	
}
