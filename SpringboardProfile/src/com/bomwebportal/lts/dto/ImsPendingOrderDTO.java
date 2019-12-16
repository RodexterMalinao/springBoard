package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Date;

public class ImsPendingOrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6127065952791277595L;
	
	private String orderId = null;
	private String ocId = null;
	private String edfRef = null;
	private String erInd = null;
	private String srdStart = null;
	private String srdEnd = null;
	private String orderType = null;
	private String sbPendingInd = null;
	private String bomPendingInd = null;
	private String bossPendingInd = null;
	private String l2OrderNum = null;
	private String orderStatus = null;
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOcId(String ocId) {
		this.ocId = ocId;
	}
	public String getOcId() {
		return ocId;
	}
	public void setEdfRef(String edfRef) {
		this.edfRef = edfRef;
	}
	public String getEdfRef() {
		return edfRef;
	}
	public void setErInd(String erInd) {
		this.erInd = erInd;
	}
	public String getErInd() {
		return erInd;
	}
	public void setSrdStart(String srdStart) {
		this.srdStart = srdStart;
	}
	public String getSrdStart() {
		return srdStart;
	}
	public void setSrdEnd(String srdEnd) {
		this.srdEnd = srdEnd;
	}
	public String getSrdEnd() {
		return srdEnd;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setBomPendingInd(String bomPendingInd) {
		this.bomPendingInd = bomPendingInd;
	}
	public String getBomPendingInd() {
		return bomPendingInd;
	}
	public void setBossPendingInd(String bossPendingInd) {
		this.bossPendingInd = bossPendingInd;
	}
	public String getBossPendingInd() {
		return bossPendingInd;
	}
	public void setL2OrderNum(String l2OrderNum) {
		this.l2OrderNum = l2OrderNum;
	}
	public String getL2OrderNum() {
		return l2OrderNum;
	}
	public void setSbPendingInd(String sbPendingInd) {
		this.sbPendingInd = sbPendingInd;
	}
	public String getSbPendingInd() {
		return sbPendingInd;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
}
