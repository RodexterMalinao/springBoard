package com.bomltsportal.dto.email;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class OrderDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1263732260719697505L;
	
	public static enum DisMode {
		E // e-Signature
		, P // Paper
		;
	}
	
	public static enum CollectMethod {
		D // Digital copy collect
		, P // Physical copy collect
		;
	}
	
	public static enum EsigEmailLang {
		CHN // Chinese
		, ENG // English
		;
	}



	public OrderDTO(){
		depositWaiveInd = "Y";	
	}
	private String orderId;
	private String source;
	private String busTxnType;
	private String msisdn;
	private String msisdnLvl;
	private String mnpInd;
	private String shopCode;
	private String bomCustNum;
	private String mobCustNum;
	private String acctNum;
	private Date srvReqDate;
	private String agreementNum;
	private Date appInDate;
	private String salesType;
	private String salesCd;
	//add by eliot 20110627
	private String salesName;
	private String depositWaiveInd;
	
	private String onHoldInd;
	private String onHoldReaCd;
	private String imei;
	private String warrantyStartDate;
	private String warrantPeriod;
	
	private MnpDTO mnpDTO;
	private SimDTO simDTO;
	private SubscriberDTO subscriberDTO;
	//private ProductDTO[] productDTO;
	
	private String orderStatus;
	private String ocid;//add by wilson 20110221
	private String errorMessage;//add by Wilson 20110122
	
	private String todayOrderFlag;//add by wilson 20110301, for ordersummary plage use
	
	private String aoInd; //add herbert 20110707, for Advance order use
	
	private String basketId; //for report recall.20110815
	
	private String lastUpdateBy; //add by Eliot 20110829
	
	// add by Joyce 20111025
	private String orderSumLob;
	private String orderSumServiceNum;
	private String imsLoginId;
	
	// add by Raymond 20111207
	private String ReasonCd;
	
	// add by Joyce 20111215
	private String orderSumCustName;
	private String lockInd;
	private Date lastUpdateDate;
	private Date createDate;
	
	private String checkPoint;
	
	// add by Jayson 2012 02 14 VD
	private boolean allowAmendOrder = true;
	private String paidAmt;//add by wilson 20120214
	
	//added by James 20120227
	private Date deliveryDate;
	
	//James 20120313
	private String deliveryTimeSlot;
	
	//add by wilson 20120415, for ccs order search page control cancel button show/hide
	private String allowCancelInd;
	
	private String cloneOrderId;
	private String histSeqNo;
	private String creditCardTrxInd; //Y/N
	//James 20120510
	private String bomOrderStatus;
	private Date actSrvReqDate;
	//James 20120517
	private String forceCancelRemainDays;
	/**
	 * To determine the color of Order Status on order search page
	 * Red : Fallout by Sales
	 * Black : Fallout by FnS
	 */
	private boolean salesFalloutFlag;
	
	// SupportDoc page 20120727
	private DisMode disMode;
	private CollectMethod collectMethod;
	private String esigEmailAddr;
	private EsigEmailLang esigEmailLang;
	
	private String dmsInd;
	
	private Date bomCreationDate;
	
	// add by Joyce 20120919, for MOB
	private boolean isRetail;
	
	// add by wilson 20121009
	private String iGuardSerialNo;
	/**
	 * true - MOB Retail order<BR>
	 * false - MOB CCS order
	 * @return the isRetail
	 */
	public boolean isRetail() {
		if (orderId != null && orderId.length() == 11 && "M".equalsIgnoreCase(""+orderId.charAt(4))) {
			if (StringUtils.startsWith(orderId, "R")) {
				isRetail = true;
			} else if (StringUtils.startsWith(orderId, "C")){
				isRetail = false;
			} else {
				isRetail = true;
			}
		} else {
			isRetail = true;
		}
		return isRetail;
	}
	/**
	 * @return the iGuardSerialNo
	 */
	public String getiGuardSerialNo() {
		return iGuardSerialNo;
	}
	/**
	 * @param iGuardSerialNo the iGuardSerialNo to set
	 */
	public void setiGuardSerialNo(String iGuardSerialNo) {
		this.iGuardSerialNo = iGuardSerialNo;
	}
	/**
	 * @return the salesFalloutFlag
	 */
	public boolean isSalesFalloutFlag() {
		return salesFalloutFlag;
	}
	/**
	 * @param salesFalloutFlag the salesFalloutFlag to set
	 */
	public void setSalesFalloutFlag(boolean salesFalloutFlag) {
		this.salesFalloutFlag = salesFalloutFlag;
	}
	/**
	 * @return the forceCancelRemainDays
	 */
	public String getForceCancelRemainDays() {
		return forceCancelRemainDays;
	}
	/**
	 * @param forceCancelRemainDays the forceCancelRemainDays to set
	 */
	public void setForceCancelRemainDays(String forceCancelRemainDays) {
		this.forceCancelRemainDays = forceCancelRemainDays;
	}
	/**
	 * @return the bomOrderStatus
	 */
	public String getBomOrderStatus() {
		return bomOrderStatus;
	}
	/**
	 * @param bomOrderStatus the bomOrderStatus to set
	 */
	public void setBomOrderStatus(String bomOrderStatus) {
		this.bomOrderStatus = bomOrderStatus;
	}
	/**
	 * @return the actSrvReqDate
	 */
	public Date getActSrvReqDate() {
		return actSrvReqDate;
	}
	/**
	 * @param actSrvReqDate the actSrvReqDate to set
	 */
	public void setActSrvReqDate(Date actSrvReqDate) {
		this.actSrvReqDate = actSrvReqDate;
	}
	public String getCreditCardTrxInd() {
		return creditCardTrxInd;
	}
	public void setCreditCardTrxInd(String creditCardTrxInd) {
		this.creditCardTrxInd = creditCardTrxInd;
	}
	public String getCloneOrderId() {
		return cloneOrderId;
	}
	public void setCloneOrderId(String cloneOrderId) {
		this.cloneOrderId = cloneOrderId;
	}
	public String getHistSeqNo() {
		return histSeqNo;
	}
	public void setHistSeqNo(String histSeqNo) {
		this.histSeqNo = histSeqNo;
	}
	public String getAllowCancelInd() {
		return allowCancelInd;
	}
	public void setAllowCancelInd(String allowCancelInd) {
		this.allowCancelInd = allowCancelInd;
	}
	/**
	 * @return the deliveryTimeSlot
	 */
	public String getDeliveryTimeSlot() {
		return deliveryTimeSlot;
	}
	/**
	 * @param deliveryTimeSlot the deliveryTimeSlot to set
	 */
	public void setDeliveryTimeSlot(String deliveryTimeSlot) {
		this.deliveryTimeSlot = deliveryTimeSlot;
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
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getWarrantyStartDate() {
		return warrantyStartDate;
	}
	public void setWarrantyStartDate(String warrantyStartDate) {
		this.warrantyStartDate = warrantyStartDate;
	}
	public String getWarrantPeriod() {
		return warrantPeriod;
	}
	public void setWarrantPeriod(String warrantPeriod) {
		this.warrantPeriod = warrantPeriod;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getBusTxnType() {
		return busTxnType;
	}
	public void setBusTxnType(String busTxnType) {
		this.busTxnType = busTxnType;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMnpInd() {
		return mnpInd;
	}
	public void setMnpInd(String mnpInd) {
		this.mnpInd = mnpInd;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getBomCustNum() {
		return bomCustNum;
	}
	public void setBomCustNum(String bomCustNum) {
		this.bomCustNum = bomCustNum;
	}
	public String getMobCustNum() {
		return mobCustNum;
	}
	public void setMobCustNum(String mobCustNum) {
		this.mobCustNum = mobCustNum;
	}
	public String getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}
	public Date getSrvReqDate() {
		return srvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}
	public String getAgreementNum() {
		return agreementNum;
	}
	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}
	public Date getAppInDate() {
		return appInDate;
	}
	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public String getSalesCd() {
		return salesCd;
	}
	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	
	public String getDepositWaiveInd() {
		return depositWaiveInd;
	}
	public void setDepositWaiveInd(String depositWaiveInd) {
		this.depositWaiveInd = depositWaiveInd;
	}
	public String getOnHoldInd() {
		return onHoldInd;
	}
	public void setOnHoldInd(String onHoldInd) {
		this.onHoldInd = onHoldInd;
	}
	public String getOnHoldReaCd() {
		return onHoldReaCd;
	}
	public void setOnHoldReaCd(String onHoldReaCd) {
		this.onHoldReaCd = onHoldReaCd;
	}
	public SimDTO getSimDTO() {
		return simDTO;
	}
	public void setSimDTO(SimDTO simDTO) {
		this.simDTO = simDTO;
	}
	public SubscriberDTO getSubscriberDTO() {
		return subscriberDTO;
	}
	public void setSubscriberDTO(SubscriberDTO subscriberDTO) {
		this.subscriberDTO = subscriberDTO;
	}
	

	public void setMsisdnLvl(String msisdnLvl) {
		this.msisdnLvl = msisdnLvl;
	}
	public String getMsisdnLvl() {
		return msisdnLvl;
	}
	public void setMnpDTO(MnpDTO mnpDTO) {
		this.mnpDTO = mnpDTO;
	}
	public MnpDTO getMnpDTO() {
		return mnpDTO;
	}
	/*
	public void setProductDTO(ProductDTO[] productDTO) {
		this.productDTO = productDTO;
	}
	public ProductDTO[] getProductDTO() {
		return productDTO;
	}*/
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	public String getOcid() {
		return ocid;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setTodayOrderFlag(String todayOrderFlag) {
		this.todayOrderFlag = todayOrderFlag;
	}
	public String getTodayOrderFlag() {
		return todayOrderFlag;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getAoInd() {
		return aoInd;
	}
	public void setAoInd(String aoInd) {
		this.aoInd = aoInd;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getBasketId() {
		return basketId;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getOrderSumLob() {
		return orderSumLob;
	}
	public void setOrderSumLob(String lob) {
		this.orderSumLob = lob;
	}
	public String getOrderSumServiceNum() {
		return orderSumServiceNum;
	}
	public void setOrderSumServiceNum(String serviceNum) {
		this.orderSumServiceNum = serviceNum;
	}
	public String getImsLoginId() {
		return imsLoginId;
	}
	public void setImsLoginId(String imsLoginId) {
		this.imsLoginId = imsLoginId;
	}
	public String getReasonCd() {
		return ReasonCd;
	}
	public void setReasonCd(String reasonCd) {
		ReasonCd = reasonCd;
	}
	public void setOrderSumCustName(String custName) {
		this.orderSumCustName = custName;
	}
	public String getOrderSumCustName() {
		return orderSumCustName;
	}
	public String getLockInd() {
		return lockInd;
	}
	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public boolean isAllowAmendOrder() {
		return allowAmendOrder;
	}
	public void setAllowAmendOrder(boolean allowAmendOrder) {
		this.allowAmendOrder = allowAmendOrder;
	}
	public String getPaidAmt() {
		return paidAmt;
	}
	public void setPaidAmt(String paidAmt) {
		this.paidAmt = paidAmt;
	}
	public DisMode getDisMode() {
		return disMode;
	}
	public void setDisMode(DisMode disMode) {
		this.disMode = disMode;
	}
	public CollectMethod getCollectMethod() {
		return collectMethod;
	}
	public void setCollectMethod(CollectMethod collectMethod) {
		this.collectMethod = collectMethod;
	}
	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}
	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}
	public EsigEmailLang getEsigEmailLang() {
		return esigEmailLang;
	}
	public void setEsigEmailLang(EsigEmailLang esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}
	public String getDmsInd() {
		return this.dmsInd;
	}
	public void setDmsInd(String dmsInd) {
		this.dmsInd = dmsInd;
	}
	public Date getBomCreationDate() {
		return bomCreationDate;
	}
	public void setBomCreationDate(Date bomCreationDate) {
		this.bomCreationDate = bomCreationDate;
	}
	
	
	
}