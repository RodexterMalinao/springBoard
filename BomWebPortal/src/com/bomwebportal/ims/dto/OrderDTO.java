package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//eSignature CR by Eric Ng
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.jcraft.jsch.Logger;

public class OrderDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2832069237779831607L;

	protected final Log logger = LogFactory.getLog(getClass());
	
	private String OrderId;
	private String OcId;
	private String Src;
	private String OrderType;
	private String Msisdn;
	private String Msisdnlvl;
	private String MnpInd;
	private String ShopCd;
	private String BomCustNo;
	private String MobCustNo;
	private String AcctNo;
	private Date SrvReqDate;
	private String AgreeNum;
	private Date AppDate;
	private String SalesType;
	private String SalesCd;
	private String DepWaive;
	private String OnHoldInd;
	private String OnHoldReaCd;
	private String Imei;
	private Date WarrStartDate;
	private String WarrPeriod;	
	private String OrderStatus;
	private String ErrCd;
	private String ErrMsg;
	private String SalesName;
	private String AoInd;
	private String ReasonCd;
	private String Lob;
	private Date SignOffDate;
	private String StaffNum;
	private String SalesChannel;
	private String SalesContactNum;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;
	private String SalesTeam;
	private String CreateChannel;
	private String centreCd;
	
	
	private Date submitDate;
	
	//NOWTVSALES
	private String cpqTxnId;
	private String amendSeq;
	private String sessionReqId;
	
	public String getCreateChannel() {
		return CreateChannel;
	}
	public void setCreateChannel(String createChannel) {
		CreateChannel = createChannel;
	}
	//eSignature CR by Eric Ng
	private String esigEmailAddr;
	private String SMSno;
	private DisMode disMode;
	private CollectMethod collectMethod;

	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getOcId() {
		return OcId;
	}
	public void setOcId(String ocId) {
		OcId = ocId;
	}
	public String getSrc() {
		return Src;
	}
	public void setSrc(String src) {
		Src = src;
	}
	public String getOrderType() {
		return OrderType;
	}
	public void setOrderType(String orderType) {
		OrderType = orderType;
	}
	public String getMsisdn() {
		return Msisdn;
	}
	public void setMsisdn(String msisdn) {
		Msisdn = msisdn;
	}
	public String getMsisdnlvl() {
		return Msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		Msisdnlvl = msisdnlvl;
	}
	public String getMnpInd() {
		return MnpInd;
	}
	public void setMnpInd(String mnpInd) {
		MnpInd = mnpInd;
	}
	public String getShopCd() {
		return ShopCd;
	}
	public void setShopCd(String shopCd) {
		ShopCd = shopCd;
	}
	public String getBomCustNo() {
		return BomCustNo;
	}
	public void setBomCustNo(String bomCustNo) {
		BomCustNo = bomCustNo;
	}
	public String getMobCustNo() {
		return MobCustNo;
	}
	public void setMobCustNo(String mobCustNo) {
		MobCustNo = mobCustNo;
	}
	public String getAcctNo() {
		return AcctNo;
	}
	public void setAcctNo(String acctNo) {
		AcctNo = acctNo;
	}
	public Date getSrvReqDate() {
		return SrvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		if(srvReqDate!=null){
			SrvReqDate = srvReqDate;			
		}else{
			try{
				logger.error(this.getOrderId()+" is setting setSrvReqDate to null");				
			}catch(Exception ex){
				System.out.println(this.getOrderId()+" is setting setSrvReqDate to null");
			}
		}
	}
	public String getAgreeNum() {
		return AgreeNum;
	}
	public void setAgreeNum(String agreeNum) {
		AgreeNum = agreeNum;
	}
	public Date getAppDate() {
		return AppDate;
	}
	public void setAppDate(Date appDate) {
		AppDate = appDate;
	}
	public String getSalesType() {
		return SalesType;
	}
	public void setSalesType(String salesType) {
		SalesType = salesType;
	}
	public String getSalesCd() {
		return SalesCd;
	}
	public void setSalesCd(String salesCd) {
		SalesCd = salesCd;
	}
	public String getDepWaive() {
		return DepWaive;
	}
	public void setDepWaive(String depWaive) {
		DepWaive = depWaive;
	}
	public String getOnHoldInd() {
		return OnHoldInd;
	}
	public void setOnHoldInd(String onHoldInd) {
		OnHoldInd = onHoldInd;
	}
	public String getOnHoldReaCd() {
		return OnHoldReaCd;
	}
	public void setOnHoldReaCd(String onHoldReaCd) {
		OnHoldReaCd = onHoldReaCd;
	}
	public String getImei() {
		return Imei;
	}
	public void setImei(String imei) {
		Imei = imei;
	}
	public Date getWarrStartDate() {
		return WarrStartDate;
	}
	public void setWarrStartDate(Date warrStartDate) {
		WarrStartDate = warrStartDate;
	}
	public String getWarrPeriod() {
		return WarrPeriod;
	}
	public void setWarrPeriod(String warrPeriod) {
		WarrPeriod = warrPeriod;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getErrCd() {
		return ErrCd;
	}
	public void setErrCd(String errCd) {
		ErrCd = errCd;
	}
	public String getErrMsg() {
		return ErrMsg;
	}
	public void setErrMsg(String errMsg) {
		ErrMsg = errMsg;
	}
	public String getSalesName() {
		return SalesName;
	}
	public void setSalesName(String salesName) {
		SalesName = salesName;
	}
	public String getAoInd() {
		return AoInd;
	}
	public void setAoInd(String aoInd) {
		AoInd = aoInd;
	}
	public String getReasonCd() {
		return ReasonCd;
	}
	public void setReasonCd(String reasonCd) {
		ReasonCd = reasonCd;
	}
	public String getLob() {
		return Lob;
	}
	public void setLob(String lob) {
		Lob = lob;
	}
	public Date getSignOffDate() {
		return SignOffDate;
	}
	public void setSignOffDate(Date signOffDate) {
		SignOffDate = signOffDate;
	}
	public String getStaffNum() {
		return StaffNum;
	}
	public void setStaffNum(String staffNum) {
		StaffNum = staffNum;
	}
	public String getSalesChannel() {
		return SalesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		SalesChannel = salesChannel;
	}
	public String getSalesContactNum() {
		return SalesContactNum;
	}
	public void setSalesContactNum(String salesContactNum) {
		SalesContactNum = salesContactNum;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}	
	
	//eSignature CR by Eric Ng
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
	private EsigEmailLang esigEmailLang;
	
	public EsigEmailLang getEsigEmailLang() {
		return esigEmailLang;
	}
	public void setEsigEmailLang(EsigEmailLang esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}
	public void setSMSno(String sMSno) {
		SMSno = sMSno;
	}
	public String getSMSno() {
		return SMSno;
	}
	public void setSalesTeam(String salesTeam) {
		SalesTeam = salesTeam;
	}
	public String getSalesTeam() {
		return SalesTeam;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setCpqTxnId(String cpqTxnId) {
		this.cpqTxnId = cpqTxnId;
	}
	public String getCpqTxnId() {
		return cpqTxnId;
	}
	public String getAmendSeq() {
		return amendSeq;
	}
	public void setAmendSeq(String amendSeq) {
		this.amendSeq = amendSeq;
	}
	public void setSessionReqId(String sessionReqId) {
		this.sessionReqId = sessionReqId;
	}
	public String getSessionReqId() {
		return sessionReqId;
	}
	public String getCentreCd() {
		return centreCd;
	}
	public void setCentreCd(String centreCd) {
		this.centreCd = centreCd;
	}

}
