package com.bomwebportal.lts.dto.order;

import java.io.Serializable;
import java.util.Date;


public class OrderStatusSynchDTO implements Serializable {
	
	private static final long serialVersionUID = 2317282232893881490L;
		
	private String rowId;
	private String orderId;
	private String dtlId;
	private String typeOfSrv;
	private String srvNum;
	private String orderType;
	private String ccServiceId;
	private String ccServiceMemNum;
	private String grpId;
	private String grpType;
	private String grpIoInd;
	private String sbStatus;
	private String ocId;
	private String bomDtlId;	
	private String bomStatus;
	private String bomReaCd;
	private Date   lastHistDate;
	private Date   srvReqDate;
	private Date   bomSrvReqDate;
	private String bomLegacyStatus;
	private String l1ReaCd;
	private String l1ReaDesc;
	private String l1OrdStatus;
	private String reaCd;
	private String errCd;
	private String errMsg;
	private String shopCd;
	private String backDateInd;
	private String autoCompleteInd;
	private long   srvNN;
	private String legacyOrdNum;
	private int    legacyActvSeq;
	private String dnSource;
	private boolean isPIPB;
	private String fromProd;
	private String toProd;
	private String createBy;
	
	public String getRowId() {
		return rowId;
	}
	
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getDtlId() {
		return dtlId;
	}
	
	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}
	
	public String getTypeOfSrv() {
		return typeOfSrv;
	}
	
	public void setTypeOfSrv(String typeOfSrv) {
		this.typeOfSrv = typeOfSrv;
	}
	
	public String getSrvNum() {
		return srvNum;
	}
	
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	
	public String getOrderType() {
		return orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public String getCcServiceId() {
		return ccServiceId;
	}
	
	public void setCcServiceId(String ccServiceId) {
		this.ccServiceId = ccServiceId;
	}

	public String getBomStatus() {
		return bomStatus;
	}

	public void setBomStatus(String bomStatus) {
		this.bomStatus = bomStatus;
	}

	public String getOcId() {
		return ocId;
	}

	public void setOcId(String ocId) {
		this.ocId = ocId;
	}

	public String getErrCd() {
		return errCd;
	}

	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getSbStatus() {
		return sbStatus;
	}

	public void setSbStatus(String sbStatus) {
		this.sbStatus = sbStatus;
	}

	public String getBomLegacyStatus() {
		return bomLegacyStatus;
	}

	public void setBomLegacyStatus(String bomLegacyStatus) {
		this.bomLegacyStatus = bomLegacyStatus;
	}

	public String getBomDtlId() {
		return bomDtlId;
	}

	public void setBomDtlId(String bomDtlId) {
		this.bomDtlId = bomDtlId;
	}

	public String getReaCd() {
		return reaCd;
	}

	public void setReaCd(String reaCd) {
		this.reaCd = reaCd;
	}

	/**
	 * @return the l1ReaCd
	 */
	public String getL1ReaCd() {
		return l1ReaCd;
	}

	/**
	 * @param reaCd the l1ReaCd to set
	 */
	public void setL1ReaCd(String reaCd) {
		l1ReaCd = reaCd;
	}

	/**
	 * @return the l1OrdStatus
	 */
	public String getL1OrdStatus() {
		return l1OrdStatus;
	}

	/**
	 * @param ordStatus the l1OrdStatus to set
	 */
	public void setL1OrdStatus(String ordStatus) {
		l1OrdStatus = ordStatus;
	}

	/**
	 * @return the l1ReaDesc
	 */
	public String getL1ReaDesc() {
		return l1ReaDesc;
	}

	/**
	 * @param reaDesc the l1ReaDesc to set
	 */
	public void setL1ReaDesc(String reaDesc) {
		l1ReaDesc = reaDesc;
	}


	/**
	 * @return the grpId
	 */
	public String getGrpId() {
		return grpId;
	}

	/**
	 * @param grpId the grpId to set
	 */
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	/**
	 * @return the grpType
	 */
	public String getGrpType() {
		return grpType;
	}

	/**
	 * @param grpType the grpType to set
	 */
	public void setGrpType(String grpType) {
		this.grpType = grpType;
	}

	/**
	 * @return the grpIoInd
	 */
	public String getGrpIoInd() {
		return grpIoInd;
	}

	/**
	 * @param grpIoInd the grpIoInd to set
	 */
	public void setGrpIoInd(String grpIoInd) {
		this.grpIoInd = grpIoInd;
	}

	/**
	 * @return the ccServiceMemNum
	 */
	public String getCcServiceMemNum() {
		return ccServiceMemNum;
	}

	/**
	 * @param ccServiceMemNum the ccServiceMemNum to set
	 */
	public void setCcServiceMemNum(String ccServiceMemNum) {
		this.ccServiceMemNum = ccServiceMemNum;
	}

	/**
	 * @return the lastHistDate
	 */
	public Date getLastHistDate() {
		return lastHistDate;
	}

	/**
	 * @param lastHistDate the lastHistDate to set
	 */
	public void setLastHistDate(Date lastHistDate) {
		this.lastHistDate = lastHistDate;
	}

	/**
	 * @return the srvReqDate
	 */
	public Date getSrvReqDate() {
		return srvReqDate;
	}

	/**
	 * @param srvReqDate the srvReqDate to set
	 */
	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}

	/**
	 * @return the bomSrvReqDate
	 */
	public Date getBomSrvReqDate() {
		return bomSrvReqDate;
	}

	/**
	 * @param bomSrvReqDate the bomSrvReqDate to set
	 */
	public void setBomSrvReqDate(Date bomSrvReqDate) {
		this.bomSrvReqDate = bomSrvReqDate;
	}

	/**
	 * @return the backDateInd
	 */
	public String getBackDateInd() {
		return backDateInd;
	}

	/**
	 * @param backDateInd the backDateInd to set
	 */
	public void setBackDateInd(String backDateInd) {
		this.backDateInd = backDateInd;
	}

	/**
	 * @return the autoCompleteInd
	 */
	public String getAutoCompleteInd() {
		return autoCompleteInd;
	}

	/**
	 * @param autoCompleteInd the autoCompleteInd to set
	 */
	public void setAutoCompleteInd(String autoCompleteInd) {
		this.autoCompleteInd = autoCompleteInd;
	}

	public String getBomReaCd() {
		return bomReaCd;
	}

	public void setBomReaCd(String bomReaCd) {
		this.bomReaCd = bomReaCd;
	}

	public long getSrvNN() {
		return srvNN;
	}

	public void setSrvNN(long srvNN) {
		this.srvNN = srvNN;
	}

	public String getLegacyOrdNum() {
		return legacyOrdNum;
	}

	public void setLegacyOrdNum(String legacyOrdNum) {
		this.legacyOrdNum = legacyOrdNum;
	}

	public int getLegacyActvSeq() {
		return legacyActvSeq;
	}

	public void setLegacyActvSeq(int legacyActvSeq) {
		this.legacyActvSeq = legacyActvSeq;
	}

	public String getDnSource() {
		return dnSource;
	}

	public void setDnSource(String dnSource) {
		this.dnSource = dnSource;
	}

	public boolean isPIPB() {
		return isPIPB;
	}

	public void setPIPB(boolean isPIPB) {
		this.isPIPB = isPIPB;
	}

	public void setPIPB(String isPIPB) {
		this.isPIPB = ("Y".equals(isPIPB)?true:false);
	}

	public String getFromProd() {
		return fromProd;
	}

	public void setFromProd(String fromProd) {
		this.fromProd = fromProd;
	}

	public String getToProd() {
		return toProd;
	}

	public void setToProd(String toProd) {
		this.toProd = toProd;
	}


	
}
