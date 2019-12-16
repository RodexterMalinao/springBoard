package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;

public class OrderDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1263732260719697505L;
	
	public static enum DisMode {
		E // e-Signature
		, P // Paper
		, S // SMS
		, I //email
		, M //Postal
		, DS //email+SMS
		, N// nowTV Call Centre
		, IS //email+SMS(call center)
		, B
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
	private String salesContactNum;
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
	private String orderStatus;
	private String ocid;
	private String errorMessage;
	private String errorCode;
	private String todayOrderFlag;//add by wilson 20110301, for ordersummary plage use
	private String aoInd; 
	private String basketId; 
	private String basketType;
	private String lastUpdateBy;
	private String orderSumLob;
	private String orderSumServiceNum;
	private String imsLoginId;
	private String ReasonCd;
	private String orderSumCustName;
	private String lockInd;
	private Date lastUpdateDate;
	private Date createDate;
	private String care_opt_ind;
	private String upfrontInd;
	private Date CurContractEndDate;
	private String BrmHsHlInd;
	private String ceInd;
	
	public String getRefSalesName() {
		return refSalesName;
	}

	public void setRefSalesName(String refSalesName) {
		this.refSalesName = refSalesName;
	}

	public String getRefSalesId() {
		return refSalesId;
	}

	public void setRefSalesId(String refSalesId) {
		this.refSalesId = refSalesId;
	}

	public String getRefSalesCentre() {
		return refSalesCentre;
	}

	public void setRefSalesCentre(String refSalesCentre) {
		this.refSalesCentre = refSalesCentre;
	}

	public String getRefSalesTeam() {
		return refSalesTeam;
	}

	public void setRefSalesTeam(String refSalesTeam) {
		this.refSalesTeam = refSalesTeam;
	}

	private String checkPoint;
	private boolean allowAmendOrder = true;
	private String paidAmt;
	private Date deliveryDate;
	private String deliveryTimeSlot;
	private String allowCancelInd;
	private String cloneOrderId;
	private String histSeqNo;
	private String creditCardTrxInd; //Y/N
	private String bomOrderStatus;
	private Date actSrvReqDate;
	private String forceCancelRemainDays;
	private String srvNum;
	private String refSalesName;
	private String refSalesId;
	private String refSalesCentre;
	private String refSalesTeam;

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
	
	// add by wilson 20121009
	private String iGuardSerialNo;
	private boolean mobileSafetyPhone;
	//private boolean nfcSim;
	//private boolean octopusSim;
	private String nfcInd;
	
	// Mob staff sponsorship
	private MobSponsorshipDTO mobSponsorshipDTO;
	// Deposit Handling
	private DepositDTO depositDTOs[];

	private boolean multiSim;

	/**
	 * true - MOB Retail order<BR>
	 * false - MOB CCS order
	 * @return the isRetail
	 */
	
	private String superAppInd;
	private String orderAppInd;
	
	private String docUpldInd;
	
	private String paymentCheck;
	private String supportDocCheck;
	private String rejectReason;
	private Date paymtRecDate;
	private String reserveMrtInd;
	
	private String bomFrozenInd;
	private String orderType;
	private String orderNature;
	private String changeSimInd;
	
	private String brand;
	
	private String manualAfNo;
	private Date stockAssgnDate;
	
	// MBU2019003 -- Add Campaign_ID
	private String campaignId; 
	
	public String getDocUpldInd() {
		return docUpldInd;
	}

	public void setDocUpldInd(String docUpldInd) {
		this.docUpldInd = docUpldInd;
	}

	public String getSupportDocCheck() {
		return supportDocCheck;
	}

	public void setSupportDocCheck(String supportDocCheck) {
		this.supportDocCheck = supportDocCheck;
	}

	public String getManualAfNo() {
		return manualAfNo;
	}

	public void setManualAfNo(String manualAfNo) {
		this.manualAfNo = manualAfNo;
	}
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getChangeSimInd() {
		return changeSimInd;
	}
	public void setChangeSimInd(String changeSimInd) {
		this.changeSimInd = changeSimInd;
	}
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBomFrozenInd() {
		return bomFrozenInd;
	}
	public void setBomFrozenInd(String bomFrozenInd) {
		this.bomFrozenInd = bomFrozenInd;
	}
	public boolean isRetail() {
		boolean isRetail = true;
		if (StringUtils.startsWith(orderId, "C")){
			isRetail = false;
		}
		/*if (orderId != null && orderId.length() == 11 && "M".equalsIgnoreCase(""+orderId.charAt(4))) {
			if (StringUtils.startsWith(orderId, "R")) {
				isRetail = true;
			} else if (StringUtils.startsWith(orderId, "C")){
				isRetail = false;
			} else {
				isRetail = true;
			}
		} else {
			isRetail = true;
		}*/
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
	public boolean isMobileSafetyPhone() {
		return mobileSafetyPhone;
	}
	public void setMobileSafetyPhone(boolean mobileSafetyPhone) {
		this.mobileSafetyPhone = mobileSafetyPhone;
	}
	/*public boolean isOctopusSim() {
		return octopusSim;
	}
	public void setOctopusSim(boolean octopusSim) {
		this.octopusSim = octopusSim;
	}*/
	public boolean isMultiSim() {
		return multiSim;
	}
	public void setMultiSim(boolean multiSim) {
		this.multiSim = multiSim;
	}
	/*public boolean isNfcSim() {
		return nfcSim;
	}
	public void setNfcSim(boolean nfcSim) {
		this.nfcSim = nfcSim;
	}*/
	
	
	public String getNfcInd() {
		return nfcInd;
	}
	public void setNfcInd(String nfcInd) {
		this.nfcInd = nfcInd;
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
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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
	public String getSalesContactNum() {
		return salesContactNum;
	}
	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
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
	public String getBasketType() {
		return basketType;
	}
	public void setBasketType(String basketType) {
		this.basketType = basketType;
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
	
	private boolean isSboOrder;
	/**
	 * true - yes<BR>
	 * false - no
	 */
	public boolean isSboOrder() {
		if (orderId != null) {
			if (StringUtils.startsWith(orderId, "CSBOM")) {
				isSboOrder = true;
			} else {
				isSboOrder = false;
			}
		} else {
			isSboOrder = false;
		}
		return isSboOrder;
	}
	
	private String sboOrderInd;
	public String getSboOrderInd() {
		if (orderId != null) {
			if (StringUtils.startsWith(orderId, "CSBOM")) {
				return  "Y";
			} else {
				return  "N";
			}
		} else {
			return  "N";
		}
	}
	public void setSboOrderInd(String sboOrderInd) {
		this.sboOrderInd = sboOrderInd;
	}
	
	
	 private int reqId;
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	public MobSponsorshipDTO getMobSponsorshipDTO() {
		return mobSponsorshipDTO;
	}
	public void setMobSponsorshipDTO(MobSponsorshipDTO mobSponsorshipDTO) {
		this.mobSponsorshipDTO = mobSponsorshipDTO;
	}
	public DepositDTO[] getDepositDTOs() {
		return depositDTOs;
	}
	public void setDepositDTOs(DepositDTO[] depositDTOs) {
		this.depositDTOs = depositDTOs;
	}
	
	public String getSuperAppInd() {
		return superAppInd;
	}
	public void setSuperAppInd(String superAppInd) {
		this.superAppInd = superAppInd;
	}
	
	public String getOrderAppInd() {
		return orderAppInd;
	}
	public void setOrderAppInd(String orderAppInd) {
		this.orderAppInd = orderAppInd;
	}
	
	public String getPaymentCheck() {
		return paymentCheck;
	}
	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}
	
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	public Date getPaymtRecDate() {
		return paymtRecDate;
	}
	public void setPaymtRecDate(Date paymtRecDate) {
		this.paymtRecDate = paymtRecDate;
	}

	public String getReserveMrtInd() {
		return reserveMrtInd;
	}
	public void setReserveMrtInd(String reserveMrtInd) {
		this.reserveMrtInd = reserveMrtInd;
	}
	public String getCare_opt_ind() {
		return care_opt_ind;
	}

	public void setCare_opt_ind(String care_opt_ind) {
		this.care_opt_ind = care_opt_ind;
	}
	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getOrderNature() {
		return orderNature;
	}

	public void setOrderNature(String orderNature) {
		this.orderNature = orderNature;
	}

	public String getUpfrontInd() {
		return upfrontInd;
	}

	public void setUpfrontInd(String upfrontInd) {
		this.upfrontInd = upfrontInd;
	}

	public Date getStockAssgnDate() {
		return stockAssgnDate;
	}

	public void setStockAssgnDate(Date stockAssgnDate) {
		this.stockAssgnDate = stockAssgnDate;
	}

	public Date getCurContractEndDate() {
		return CurContractEndDate;
	}

	public void setCurContractEndDate(Date curContractEndDate) {
		CurContractEndDate = curContractEndDate;
	}

	public String getBrmHsHlInd() {
		return BrmHsHlInd;
	}

	public void setBrmHsHlInd(String brmHsHlInd) {
		BrmHsHlInd = brmHsHlInd;
	}

	// MBU2019003 -- Add Campaign_ID -- Start 
	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}
	// MBU2019003 -- Add Campaign_ID -- End 
	public void setCeInd(String ceInd) {
		this.ceInd = ceInd;
	}
	public String getCeInd() {
		return this.ceInd;
	}	
}